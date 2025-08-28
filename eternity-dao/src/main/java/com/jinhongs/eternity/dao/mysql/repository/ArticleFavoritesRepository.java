package com.jinhongs.eternity.dao.mysql.repository;

import com.jinhongs.eternity.model.entity.ArticleFavorites;
import com.jinhongs.eternity.dao.mysql.mapper.ArticleFavoritesMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 文章收藏表 服务实现类
 * </p>
 */
@Component
public class ArticleFavoritesRepository extends CrudRepository<ArticleFavoritesMapper, ArticleFavorites> {

}
