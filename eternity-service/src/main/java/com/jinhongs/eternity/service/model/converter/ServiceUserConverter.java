package com.jinhongs.eternity.service.model.converter;


import com.jinhongs.eternity.model.entity.UserInfo;
import com.jinhongs.eternity.service.model.dto.UserInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ServiceUserConverter {

    ServiceUserConverter INSTANCE = Mappers.getMapper(ServiceUserConverter.class);

    @Mapping(source = "id", target = "userId")
    UserInfoDTO userInfoToUserInfoDTO(UserInfo userInfo);
}
