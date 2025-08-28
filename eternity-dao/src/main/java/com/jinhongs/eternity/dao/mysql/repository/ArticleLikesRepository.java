package com.jinhongs.eternity.dao.mysql.repository;

import com.jinhongs.eternity.model.entity.ArticleLikes;
import com.jinhongs.eternity.dao.mysql.mapper.ArticleLikesMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 文章点赞表 服务实现类
 * </p>
 */
@Component
public class ArticleLikesRepository extends CrudRepository<ArticleLikesMapper, ArticleLikes> {

}
