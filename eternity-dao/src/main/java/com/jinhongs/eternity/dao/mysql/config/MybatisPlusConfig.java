package com.jinhongs.eternity.dao.mysql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    @Bean
    public BatchSqlInjector sqlInjector() {
        return new BatchSqlInjector();
    }
}
