package com.jinhongs.eternity.admin.web.model.converter;

import com.jinhongs.eternity.admin.web.model.dto.params.ArticleCreateParams;
import com.jinhongs.eternity.admin.web.model.dto.params.ArticleUpdateParams;
import com.jinhongs.eternity.service.model.dto.ArticleCreateDTO;
import com.jinhongs.eternity.service.model.dto.ArticleUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ControllerArticleConverter {

    ControllerArticleConverter INSTANCE = Mappers.getMapper(ControllerArticleConverter.class);

    ArticleCreateDTO toArticleCreateDTO(ArticleCreateParams params);

    ArticleUpdateDTO toArticleUpdateDTO(ArticleUpdateParams params);
}
