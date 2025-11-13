package com.jinhongs.eternity.dao.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.jackson2.CoreJackson2Module;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 字符串序列化器
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();


        // 创建 ObjectMapper 并添加默认配置
        ObjectMapper objectMapper = new ObjectMapper();
        // 序列化所有字段
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 默认类型推断功能 此项必须配置，否则如果序列化的对象里边还有对象，则会无法反序列化成功：(使用@JsonTypeInfo自定义所有对象信息也行 mapper.addMixIn(Authentication.class, AuthenticationMixIn.class);)
        //     class java.util.LinkedHashMap cannot be cast to class xxxx
        objectMapper.activateDefaultTyping(
                objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        );
        // 添加Security提供的Jackson Mixin ,序列化和反序列化Spring Security内的一些无无参构造类（Authentication等）
        objectMapper.registerModule(new CoreJackson2Module());

        // Long 类型序列化为字符串（避免 JS 精度问题）
        objectMapper.registerModule(new SimpleModule().addSerializer(Long.class, ToStringSerializer.instance));


        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // Hash的 key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);


        // Value 序列化方式：JSON 序列化
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);

        // 设置支持事务
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }
}
