package com.jinhongs.eternity.dao.mysql.repository;

import com.jinhongs.eternity.model.entity.Articles;
import com.jinhongs.eternity.dao.mysql.mapper.ArticlesMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 */
@Component
public class ArticlesRepository extends CrudRepository<ArticlesMapper, Articles> {

}
