package com.jinhongs.eternity.service.service;

import com.jinhongs.eternity.dao.redis.client.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    public RedisClient redisClient;

    @Autowired
    public UserService(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    public String getUserName() {
        log.info("JinHongs 访问");
        System.out.println(LoggerFactory.getILoggerFactory().getClass().getName());
        return "JinHongs";
    }

    public void testRedis() {
        redisClient.setStringValue("test", "test");
        redisClient.setStringValue("test:1001:1001", "test");
    }
}
