package com.jinhongs.eternity.dao.mysql.repository;

import com.jinhongs.eternity.model.entity.Comments;
import com.jinhongs.eternity.dao.mysql.mapper.CommentsMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 */
@Component
public class CommentsRepository extends CrudRepository<CommentsMapper, Comments> {

}
