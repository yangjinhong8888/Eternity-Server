package com.jinhongs.eternity.dao.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;

/**
 * 批量插入接口，需要使用批量插入的Mapper可以使用此接口替换BaseMapper，即可实现批量插入
 *
 * @param <T> 实体类
 */
public interface BatchBaseMapper<T> extends BaseMapper<T> {
    /**
     * 批量插入 仅适用于mysql
     *
     * @param entityList 实体列表
     * @return 影响行数
     */
    Integer insertBatchSomeColumn(Collection<T> entityList);
}
