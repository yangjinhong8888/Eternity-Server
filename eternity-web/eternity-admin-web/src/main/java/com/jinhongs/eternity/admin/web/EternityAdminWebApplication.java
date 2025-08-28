package com.jinhongs.eternity.admin.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan({
        "com.jinhongs.eternity.dao.mysql.repository"
})
@ComponentScan({
        "com.jinhongs.eternity.service",
        "com.jinhongs.eternity.common",
        "com.jinhongs.eternity.admin.web"
})
public class EternityAdminWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(EternityAdminWebApplication.class, args);
    }

}
