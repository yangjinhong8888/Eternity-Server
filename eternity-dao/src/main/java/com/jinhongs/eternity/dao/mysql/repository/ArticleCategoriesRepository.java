package com.jinhongs.eternity.dao.mysql.repository;

import com.jinhongs.eternity.model.entity.ArticleCategories;
import com.jinhongs.eternity.dao.mysql.mapper.ArticleCategoriesMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 文章分类表 服务实现类
 * </p>
 */
@Component
public class ArticleCategoriesRepository extends CrudRepository<ArticleCategoriesMapper, ArticleCategories> {

}
