package com.jinhongs.eternity.view.web.model.converter;

import com.jinhongs.eternity.view.web.security.SecurityUserDetailsImpl;
import com.jinhongs.eternity.service.model.dto.SecurityUserInfoDTO;
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
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);



    @Named("stringToGrantedAuthority")
    // 这里必须使用 GrantedAuthority 否则会因为泛型类型不一致会导致 MapStruct 无法识别
    default List<GrantedAuthority> stringToGrantedAuthority(List<String> authorities) {
        if (authorities == null || authorities.isEmpty()) {
            return Collections.emptyList();
        }
        return authorities.stream()
            .map(authority -> new SimpleGrantedAuthority(authority.trim()))
            .collect(Collectors.toList());
    }

    @Mapping(source = "userId", target = "id")
    @Mapping(source = "authorities", target = "authorities", qualifiedByName = "stringToGrantedAuthority")
    SecurityUserDetailsImpl securityUserInfoDTOTosecurityUserDetailsImpl(SecurityUserInfoDTO securityUserInfoDTO);
}
