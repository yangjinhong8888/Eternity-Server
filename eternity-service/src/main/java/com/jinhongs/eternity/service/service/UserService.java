package com.jinhongs.eternity.service.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    public String getUserName() {
        log.info("JinHongs 访问");
        System.out.println(LoggerFactory.getILoggerFactory().getClass().getName());
        return "JinHongs";
    }
}
