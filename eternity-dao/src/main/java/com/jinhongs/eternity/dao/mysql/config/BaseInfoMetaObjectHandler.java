package com.jinhongs.eternity.dao.mysql.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;


@Component
public class BaseInfoMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Long currentTimeMills = System.currentTimeMillis();
        this.strictInsertFill(metaObject, "createTime", Long.class, currentTimeMills);
        this.strictInsertFill(metaObject, "updateTime", Long.class, currentTimeMills);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", Long.class, System.currentTimeMillis());
    }

}
