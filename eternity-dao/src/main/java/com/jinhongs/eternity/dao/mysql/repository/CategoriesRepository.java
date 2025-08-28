package com.jinhongs.eternity.dao.mysql.repository;

import com.jinhongs.eternity.model.entity.Categories;
import com.jinhongs.eternity.dao.mysql.mapper.CategoriesMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 */
@Component
public class CategoriesRepository extends CrudRepository<CategoriesMapper, Categories> {

}
