package com.jinhongs.eternity.service.model.converter;


import com.jinhongs.eternity.dao.mysql.model.dto.UserEntity;
import com.jinhongs.eternity.model.entity.UserAuth;
import com.jinhongs.eternity.model.entity.UserInfo;
import com.jinhongs.eternity.service.model.dto.SecurityUserInfoDTO;
import com.jinhongs.eternity.service.model.dto.UserRegisterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

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

    SecurityUserInfoDTO toSecurityUserInfoDTO(UserEntity userEntity);
}
