package com.jinhongs.eternity.service.model.converter;

import com.jinhongs.eternity.model.entity.Articles;
import com.jinhongs.eternity.model.entity.Tags;
import com.jinhongs.eternity.service.model.dto.ArticleCreateDTO;
import com.jinhongs.eternity.service.model.vo.ArticleDetailVO;
import com.jinhongs.eternity.service.model.vo.ArticleListVO;
import com.jinhongs.eternity.service.model.vo.TagVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ServiceArticleConverter {

    ServiceArticleConverter INSTANCE = Mappers.getMapper(ServiceArticleConverter.class);

    /**
     * 创建 DTO -> 实体（content 字段映射到 contentMd 列）
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "contentMd", source = "content")
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    Articles toArticles(ArticleCreateDTO dto);

    /**
     * 实体 -> 详情 VO（contentMd 列映射到 content 字段）
     */
    @Mapping(target = "content", source = "contentMd")
    @Mapping(target = "tags", ignore = true)
    ArticleDetailVO toArticleDetailVO(Articles articles);

    /**
     * 实体 -> 列表项 VO
     */
    @Mapping(target = "tags", ignore = true)
    ArticleListVO toArticleListVO(Articles articles);

    /**
     * Tags 实体 -> TagVO
     */
    TagVO toTagVO(Tags tags);
}
