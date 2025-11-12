package com.jinhongs.eternity.view.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan({
        "com.jinhongs.eternity.dao.mysql.mapper",
})
@ComponentScan({
        "com.jinhongs.eternity.service",
        "com.jinhongs.eternity.common",
        "com.jinhongs.eternity.view.web",
        "com.jinhongs.eternity.dao.redis.config",
        "com.jinhongs.eternity.dao.redis.client",
        "com.jinhongs.eternity.dao.mysql.config", // Component注解都要放在这里扫描
        "com.jinhongs.eternity.dao.mysql.repository"
})
public class EternityViewWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(EternityViewWebApplication.class, args);
    }

}
