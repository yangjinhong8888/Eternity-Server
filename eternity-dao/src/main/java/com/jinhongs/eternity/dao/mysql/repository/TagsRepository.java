package com.jinhongs.eternity.dao.mysql.repository;

import com.jinhongs.eternity.model.entity.Tags;
import com.jinhongs.eternity.dao.mysql.mapper.TagsMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 */
@Component
public class TagsRepository extends CrudRepository<TagsMapper, Tags> {

}
