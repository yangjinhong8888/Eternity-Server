package com.jinhongs.eternity.service.service.impl;

import com.jinhongs.eternity.common.constant.LoginPlatform;
import com.jinhongs.eternity.common.constant.RedisConstants;
import com.jinhongs.eternity.common.constant.UserInfoConstants;
import com.jinhongs.eternity.common.enums.RegisterIdentityTypeEnum;
import com.jinhongs.eternity.common.exception.ClientException;
import com.jinhongs.eternity.dao.mysql.repository.UserAuthRepository;
import com.jinhongs.eternity.dao.mysql.repository.UserInfoRepository;
import com.jinhongs.eternity.dao.redis.client.RedisClient;
import com.jinhongs.eternity.model.entity.UserAuth;
import com.jinhongs.eternity.model.entity.UserInfo;
import com.jinhongs.eternity.service.model.converter.ServiceUserConverter;
import com.jinhongs.eternity.service.model.dto.UserLoginDTO;
import com.jinhongs.eternity.service.model.dto.UserRegisterDTO;
import com.jinhongs.eternity.service.model.dto.security.SecurityUserDetailsImpl;
import com.jinhongs.eternity.service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final int SESSION_EXPIRE_DAYS = 15;

    private final RedisClient redisClient;

    private final UserInfoRepository userInfoRepository;

    private final UserAuthRepository userAuthRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,  // 传播行为（默认：REQUIRED）
            isolation = Isolation.DEFAULT,        // 隔离级别（默认：数据库默认级别）
            timeout = 30,                         // 超时时间（秒）
            readOnly = false,                     // 是否只读事务
            rollbackFor = {SQLException.class}    // 触发回滚的异常类型
    )
    public boolean register(UserRegisterDTO userRegisterDTO) {
        // 加密原始密码
        userRegisterDTO.setCredential(passwordEncoder.encode(userRegisterDTO.getCredential()));

        // 用户信息处理
        String username = resolveUsername(userRegisterDTO);
        userRegisterDTO.setUsername(username);

        UserInfo userInfo = ServiceUserConverter.INSTANCE.toUserInfo(userRegisterDTO);
        saveUserInfo(userInfo);

        userRegisterDTO.setUserId(userInfo.getId());
        UserAuth userAuth = ServiceUserConverter.INSTANCE.toUserAuth(userRegisterDTO);

        return userAuthRepository.save(userAuth);
    }

    @Override
    public String adminLogin(UserLoginDTO userLoginDTO) {
        return login(userLoginDTO,
                RedisConstants::getAdminSessionToken,
                userId -> RedisConstants.getAdminSessionUser(LoginPlatform.WEB, userId),
                "admin");
    }

    @Override
    public String viewLogin(UserLoginDTO userLoginDTO) {
        return login(userLoginDTO,
                RedisConstants::getViewSessionToken,
                userId -> RedisConstants.getViewSessionUser(LoginPlatform.WEB, userId),
                "view");
    }

    /**
     * login认证
     */
    private SecurityUserDetailsImpl usernameLoginAuthentication(UserLoginDTO userLoginDTO) {
        // 1. 创建未认证的 Token
        Authentication authRequest = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getUsername(),
                userLoginDTO.getPassword()
        );
        Authentication authentication;
        try {
            // 2. 触发完整认证流程
            // 这里会调用默认的UsernamePasswordAuthenticationToken处理Provider DaoAuthenticationProvider进行用户名密码的验证处理
            // 验证失败则抛出异常(security已经封装好了UsernamePasswordAuthentication常用的异常处理，这里直接抛出就行，不用try)，验证成功时返回一个验证成功的对象
            authentication = authenticationManager.authenticate(authRequest);
        } catch (RuntimeException e) {
            log.error("RuntimeException: {}", e.getMessage(), e);
            throw new ClientException("用户名或密码错误");
        }

        // 3. 手动设置上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. 返回用户信息
        return (SecurityUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 清理超出数量限制的会话，保留最新的指定数量
     */
    private void cleanExcessSessions(String userSessionKey, int maxSessions, Function<String, String> getRedisKey) {
        Long sessionCount = redisClient.getZSetSize(userSessionKey);
        if (sessionCount == null || sessionCount <= maxSessions) {
            return;
        }

        long sessionsToRemove = sessionCount - maxSessions;
        Set<String> expiredUuids = redisClient.getZSetRange(userSessionKey, 0, sessionsToRemove - 1)
                .stream()
                .map(Object::toString)
                .collect(Collectors.toSet());

        if (expiredUuids.isEmpty()) {
            return;
        }

        Set<String> tokenKeys = expiredUuids.stream()
                .map(getRedisKey)
                .collect(Collectors.toSet());
        long removedCount = redisClient.removeZSetRangeByRank(userSessionKey, 0, sessionsToRemove - 1);
        redisClient.deleteBatch(tokenKeys);

        log.info("会话数量超限，移除{}个最早登录的会话", removedCount);
    }

    /**
     * 登录的共用流程
     */
    private String login(UserLoginDTO userLoginDTO,
                         Function<String, String> sessionTokenKeyProvider,
                         Function<Long, String> sessionUserKeyProvider,
                         String loginTag) {
        SecurityUserDetailsImpl userDetails = usernameLoginAuthentication(userLoginDTO);

        String uuid = java.util.UUID.randomUUID().toString();
        String sessionTokenKey = sessionTokenKeyProvider.apply(uuid);
        redisClient.setEx(sessionTokenKey, userDetails, SESSION_EXPIRE_DAYS, TimeUnit.DAYS);

        String userSessionKey = sessionUserKeyProvider.apply(userDetails.getId());
        redisClient.addToZSet(userSessionKey, uuid, System.currentTimeMillis());
        cleanExcessSessions(userSessionKey, LoginPlatform.WEB_NUMBER, sessionTokenKeyProvider);

        log.info("{}登录成功：{}", loginTag, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return uuid;
    }

    /**
     * 根据注册方式获取用户名
     */
    private String resolveUsername(UserRegisterDTO userRegisterDTO) {
        if (RegisterIdentityTypeEnum.USERNAME.is(userRegisterDTO.getIdentityType())) {
            String username = userRegisterDTO.getIdentifier();
            boolean existUsername = userInfoRepository.isExistUsername(username);
            if (existUsername) {
                throw new ClientException("用户名已存在");
            }
            return username;
        }
        return generateRandomUsername();
    }

    /**
     * 持久化用户信息
     */
    private void saveUserInfo(UserInfo userInfo) {
        boolean save = userInfoRepository.save(userInfo);
        if (!save) {
            throw new ClientException("用户信息保存失败");
        }
    }

    /**
     * 生成随机用户名
     */
    private String generateRandomUsername() {
        for (int i = 0; i < 3; i++) {
            String username = randomUsername();
            if (!userInfoRepository.isExistUsername(username)) {
                return username;
            }
        }
        throw new ClientException("网络异常，请重试");
    }

    /**
     * 生成带前缀的随机用户名
     */
    private String randomUsername() {
        return UserInfoConstants.USERNAME + RandomStringUtils.secureStrong().next(10, true, true);
    }
}
