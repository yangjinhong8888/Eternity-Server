package com.jinhongs.eternity.dao.mysql.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class BaseInfoMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insertFill开始注入createTime 和 updateTime");
        Long currentTimeMills = System.currentTimeMillis();
        this.strictInsertFill(metaObject, "createTime", Long.class, currentTimeMills);
        this.strictInsertFill(metaObject, "updateTime", Long.class, currentTimeMills);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("updateFill开始注入updateTime");
        this.strictUpdateFill(metaObject, "updateTime", Long.class, System.currentTimeMillis());
    }

}
