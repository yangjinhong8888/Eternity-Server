package com.jinhongs.eternity.view.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Eternity接口文档")
                        .description("Eternity接口文档")
                        .version("V0.0.1")
                        .contact(new Contact().name("YangJinHong"))
                );
    }

}