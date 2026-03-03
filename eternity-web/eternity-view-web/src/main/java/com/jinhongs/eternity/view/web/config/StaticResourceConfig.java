package com.jinhongs.eternity.view.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 静态资源映射配置：将本地 uploads 目录映射为 /uploads/** 路径
 */
@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Value("${eternity.upload.dir:./uploads}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = uploadDir.startsWith("./")
                ? "file:" + System.getProperty("user.dir") + "/" + uploadDir.substring(2) + "/"
                : "file:" + uploadDir + "/";
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(location);
    }
}
