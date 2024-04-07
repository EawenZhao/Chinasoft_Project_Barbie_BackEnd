package com.group21.chinasoft_project_barbie_backend.config;

import com.group21.chinasoft_project_barbie_backend.interceptor.JwtTokenInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    JwtTokenInterceptor jwtTokenInterceptor;

    /**
     *注册自定义拦截器
     */
    protected void addInterceptors(InterceptorRegistry registry){
        log.info("开始注册自定义拦截器");
        registry.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/user/**");
    }
}