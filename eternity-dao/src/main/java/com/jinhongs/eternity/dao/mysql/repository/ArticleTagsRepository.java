package com.jinhongs.eternity.dao.mysql.repository;

import com.jinhongs.eternity.model.entity.ArticleTags;
import com.jinhongs.eternity.dao.mysql.mapper.ArticleTagsMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 文章标签表 服务实现类
 * </p>
 */
@Component
public class ArticleTagsRepository extends CrudRepository<ArticleTagsMapper, ArticleTags> {

}
