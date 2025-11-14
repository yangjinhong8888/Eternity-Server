package com.jinhongs.eternity.admin.web.model.converter;

import com.jinhongs.eternity.admin.web.model.dto.params.UserLoginParams;
import com.jinhongs.eternity.admin.web.model.dto.params.UserRegisterParams;
import com.jinhongs.eternity.service.model.dto.UserLoginDTO;
import com.jinhongs.eternity.service.model.dto.UserRegisterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ControllerUserConverter {

    ControllerUserConverter INSTANCE = Mappers.getMapper(ControllerUserConverter.class);

    @Mapping(target = "userId", ignore = true)
    UserRegisterDTO toUserRegisterDTO(UserRegisterParams userRegisterParams);

    UserLoginDTO toUserLoginDTO(UserLoginParams userLoginParams);

}
