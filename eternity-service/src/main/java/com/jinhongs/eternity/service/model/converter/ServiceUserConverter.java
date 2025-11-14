package com.jinhongs.eternity.service.model.converter;


import com.jinhongs.eternity.dao.mysql.model.dto.UserEntity;
import com.jinhongs.eternity.model.entity.UserAuth;
import com.jinhongs.eternity.model.entity.UserInfo;
import com.jinhongs.eternity.service.model.dto.UserRegisterDTO;
import com.jinhongs.eternity.service.model.dto.security.SecurityUserDetailsImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ServiceUserConverter {

    ServiceUserConverter INSTANCE = Mappers.getMapper(ServiceUserConverter.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    UserInfo toUserInfo(UserRegisterDTO userRegisterDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    UserAuth toUserAuth(UserRegisterDTO userRegisterDTO);

    @Named("toGrantedAuthority")
    // 这里必须使用 GrantedAuthority 否则会因为泛型类型不一致会导致 MapStruct 无法识别
    default List<GrantedAuthority> toGrantedAuthority(List<String> authorities) {
        if (authorities == null || authorities.isEmpty()) {
            return Collections.emptyList();
        }
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.trim()))
                .collect(Collectors.toList());
    }

    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "accountNonExpired", ignore = true)
    @Mapping(target = "accountNonLocked", ignore = true)
    @Mapping(target = "credentialsNonExpired", ignore = true)
    @Mapping(source = "authorities", target = "authorities", qualifiedByName = "toGrantedAuthority")
    SecurityUserDetailsImpl toSecurityUserDetailsImpl(UserEntity userEntity);
}
