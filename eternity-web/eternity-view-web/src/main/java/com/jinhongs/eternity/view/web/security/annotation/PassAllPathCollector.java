package com.jinhongs.eternity.view.web.security.annotation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 收集被 @PassAll 标记的接口路径
 */
@Component
@RequiredArgsConstructor
public class PassAllPathCollector {

    private final RequestMappingHandlerMapping handlerMapping;

    /**
     * 收集类级或方法级标记的路径模式，交给Spring Security进行匹配
     */
    public List<String> collect() {
        List<String> paths = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMapping.getHandlerMethods().entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            boolean methodAnnotated = handlerMethod.hasMethodAnnotation(PassAll.class);
            boolean classAnnotated = handlerMethod.getBeanType().isAnnotationPresent(PassAll.class);
            if (!methodAnnotated && !classAnnotated) {
                continue;
            }
            paths.addAll(entry.getKey().getPatternValues());
        }
        return paths;
    }
}
