package com.jinhongs.eternity.dao.mysql.model.converter;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DaoUserConverter {

    DaoUserConverter INSTANCE = Mappers.getMapper(DaoUserConverter.class);
}
