package com.jinhongs.eternity.service.service.impl;

import com.jinhongs.eternity.common.constant.LoginPlatform;
import com.jinhongs.eternity.common.constant.RedisConstants;
import com.jinhongs.eternity.common.constant.UserInfoConstants;
import com.jinhongs.eternity.common.enums.RegisterIdentityTypeEnum;
import com.jinhongs.eternity.common.exception.GeneralException;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl implements UserService {

    private final RedisClient redisClient;

    private final UserInfoRepository userInfoRepository;

    private final UserAuthRepository userAuthRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(RedisClient redisClient, UserInfoRepository userInfoRepository, UserAuthRepository userAuthRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.redisClient = redisClient;
        this.userInfoRepository = userInfoRepository;
        this.userAuthRepository = userAuthRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


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
        UserInfo userInfo;
        String username;
        if (RegisterIdentityTypeEnum.USERNAME.is(userRegisterDTO.getIdentityType())) {
            // 使用用户名验证方式登录，用户名为输入凭证
            username = userRegisterDTO.getIdentifier();
            boolean existUsername = userInfoRepository.isExistUsername(username);
            if (existUsername) {
                throw new GeneralException("用户名已存在");
            }
        } else {
            // 使用其他方式登录， 用户名为随机字符串
            username = UserInfoConstants.USERNAME + RandomStringUtils.secureStrong().next(10, true, true);
            boolean existUsername = userInfoRepository.isExistUsername(username);
            // 尝试生成三次随机字符串，如果全都重复则返回异常
            for (int i = 0; i < 3 && !existUsername; ++i) {
                username = UserInfoConstants.USERNAME + RandomStringUtils.secureStrong().next(10, true, true);
                existUsername = userInfoRepository.isExistUsername(username);
            }
            if (existUsername) {
                throw new GeneralException("网络异常，请重试");
            }
        }
        userRegisterDTO.setUsername(username);
        userInfo = ServiceUserConverter.INSTANCE.toUserInfo(userRegisterDTO);
        boolean save = userInfoRepository.save(userInfo);
        if (!save) {
            throw new GeneralException("用户信息保存失败");
        }

        userRegisterDTO.setUserId(userInfo.getId());
        UserAuth userAuth = ServiceUserConverter.INSTANCE.toUserAuth(userRegisterDTO);

        return userAuthRepository.save(userAuth);
    }

    @Override
    public String adminLogin(UserLoginDTO userLoginDTO) {
        // 登录认证
        SecurityUserDetailsImpl userDetails = usernameLoginAuthentication(userLoginDTO);

        // 生成一个uuid，并把对应的用户信息存储在redis中
        String uuid = java.util.UUID.randomUUID().toString();

        // 设置用户会话token
        String sessionTokenKey = RedisConstants.getAdminSessionToken(uuid);
        redisClient.setEx(sessionTokenKey, userDetails, 15, TimeUnit.DAYS);

        // 添加登录记录并清理超限会话
        String userSessionKey = RedisConstants.getAdminSessionUser(LoginPlatform.WEB, userDetails.getId());
        redisClient.addToZSet(userSessionKey, uuid, System.currentTimeMillis());

        // 直接清理，保持最多WEB_NUMBER个会话
        cleanExcessSessions(userSessionKey, LoginPlatform.WEB_NUMBER, RedisConstants::getAdminSessionToken);

        log.info("admin登录成功：{}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return uuid;
    }

    @Override
    public String viewLogin(UserLoginDTO userLoginDTO) {
        // 登录认证
        SecurityUserDetailsImpl userDetails = usernameLoginAuthentication(userLoginDTO);

        // 生成一个uuid，并把对应的用户信息存储在redis中
        String uuid = java.util.UUID.randomUUID().toString();

        // 设置用户会话token
        String sessionTokenKey = RedisConstants.getViewSessionToken(uuid);
        redisClient.setEx(sessionTokenKey, userDetails, 15, TimeUnit.DAYS);

        // 添加登录记录并清理超限会话
        String userSessionKey = RedisConstants.getViewSessionUser(LoginPlatform.WEB, userDetails.getId());
        redisClient.addToZSet(userSessionKey, uuid, System.currentTimeMillis());

        // 直接清理，保持最多WEB_NUMBER个会话
        cleanExcessSessions(userSessionKey, LoginPlatform.WEB_NUMBER, RedisConstants::getViewSessionToken);

        log.info("view登录成功：{}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return uuid;
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

        // 2. 触发完整认证流程
        // 这里会调用默认的UsernamePasswordAuthenticationToken处理Provider DaoAuthenticationProvider进行用户名密码的验证处理
        // 验证失败则抛出异常(security已经封装好了UsernamePasswordAuthentication常用的异常处理，这里直接抛出就行，不用try)，验证成功时返回一个验证成功的对象
        Authentication authentication = authenticationManager.authenticate(authRequest);

        // 3. 手动设置上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. 返回用户信息
        return (SecurityUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 清理超出数量限制的会话，保留最新的指定数量
     */
    private void cleanExcessSessions(String userSessionKey, int maxSessions, Function<String, String> getRedisKey) {
        // 当前会话数量
        Long sessionCount = redisClient.getZSetSize(userSessionKey);
        if (sessionCount != null && sessionCount > maxSessions) {
            // 需要删除的会话数量
            long sessionsToRemove = sessionCount - maxSessions;
            // 获取需要删除的cookie
            Set<String> expiredUuids = redisClient.getZSetRange(userSessionKey, 0, sessionsToRemove - 1)
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.toSet());

            // 需要删除的cookie不为空
            if (!expiredUuids.isEmpty()) {
                // 构建删除key
                Set<String> tokenKeys = expiredUuids.stream()
                        .map(getRedisKey)
                        .collect(Collectors.toSet());
                // 删除cookie
                long removedCount = redisClient.removeZSetRangeByRank(userSessionKey, 0, sessionsToRemove - 1);
                redisClient.deleteBatch(tokenKeys);

                log.info("会话数量超限，移除{}个最早登录的会话", removedCount);
            }
        }
    }
}
