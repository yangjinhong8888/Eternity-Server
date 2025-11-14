package com.jinhongs.eternity.view.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 密码编码器 Bean
     * 使用 BCrypt 算法对密码进行加密和匹配
     *
     * @return PasswordEncoder 实例
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 定义 AuthenticationManager Bean（正确依赖注入）
     * 用于构建认证管理器，配置用户详情服务和密码编码器
     *
     * @param http               HttpSecurity对象，用于获取共享的AuthenticationManagerBuilder
     * @param userDetailsService 用户详情服务，用于加载用户信息
     * @param passwordEncoder    密码编码器，用于密码加密和匹配
     * @return AuthenticationManager 认证管理器实例
     * @throws Exception 配置过程中可能抛出的异常
     */
    @Bean
    public AuthenticationManager authManager(
            HttpSecurity http,
            UserDetailsService userDetailsService, // 注入已存在的 Bean
            PasswordEncoder passwordEncoder         // 注入已存在的 Bean
    ) throws Exception {
        // 从 HttpSecurity 中获取共享的 AuthenticationManagerBuilder 实例
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        // 配置用户详情服务和密码编码器
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        // 构建并返回 AuthenticationManager 实例
        return authenticationManagerBuilder.build();
    }

    /*
      自定义自定义认证过滤器
     */
    @Bean
    public CookieAuthenticationFilter cookieAuthenticationFilter() {
        return new CookieAuthenticationFilter();
    }

    /**
     * 创建 JsonAuthenticationEntryPoint Bean
     * 用于处理认证失败的请求
     *
     * @return JsonAuthenticationEntryPoint 实例
     */
    @Bean
    public JsonAuthenticationEntryPoint authenticationEntryPoint() {
        return new JsonAuthenticationEntryPoint();
    }

    /**
     * 创建 JsonAccessDeniedHandler Bean
     * 用于处理没有权限的请求
     *
     * @return JsonAccessDeniedHandler 实例
     */
    @Bean
    public JsonAccessDeniedHandler accessDeniedHandler() {
        return new JsonAccessDeniedHandler();
    }


    /**
     * 配置安全过滤器链，定义HTTP请求的安全策略
     *
     * @param http HttpSecurity对象，用于配置Web安全
     * @return SecurityFilterChain 安全过滤器链
     * @throws Exception 配置过程中可能抛出的异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .exceptionHandling(handling -> handling
                        // 处理未登录的 401 响应
                        .authenticationEntryPoint(authenticationEntryPoint())
                        // 处理权限不足的 403 响应
                        .accessDeniedHandler(accessDeniedHandler())
                )
                // 开启授权保护
                .authorizeHttpRequests(authorize -> authorize
                        // 不需要认证的地址有哪些
                        .requestMatchers(
                                "/v2/api-docs/**",
                                "/v3/api-docs/**",
                                "/doc.html",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/user/login",
                                "/user/register"
                        ).permitAll() // 允许访问的资源
                        // 对所有请求开启授权保护
                        .anyRequest()
                        // 已认证的请求会被自动授权
                        .authenticated()
                )
                // 禁用默认表单登录验证 使用REST接口进行登录验证
                .formLogin(AbstractHttpConfigurer::disable)
                // 禁用“记住我”功能
                .rememberMe(AbstractHttpConfigurer::disable)
                // 关闭 csrf CSRF（跨站请求伪造）是一种网络攻击，攻击者通过欺骗已登录用户，诱使他们在不知情的情况下向受信任的网站发送请求。
                .csrf(AbstractHttpConfigurer::disable) // 基于token，不需要csrf
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 基于token，不需要session

        // 接口请求时，Cookie校验过滤器
        http.addFilterBefore(cookieAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}