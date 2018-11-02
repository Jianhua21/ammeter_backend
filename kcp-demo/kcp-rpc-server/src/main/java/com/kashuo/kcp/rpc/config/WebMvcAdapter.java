package com.kashuo.kcp.rpc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcAdapter extends WebMvcConfigurerAdapter {


    @Autowired
    private UserSecurityInterceptor userSecurityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(userSecurityInterceptor).addPathPatterns("/device/**","/user/**","/position/**","/warning/**","/system/**")
                .excludePathPatterns("/user/add/**","/nbiot/**","/**/export/**")
        ;

//        registry.addInterceptor(new PermissionInterceptorAdapter());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(false)
                .allowedHeaders("Content-Type", "X-AUTH-TIME", "X-AUTH-APPKEY", "X-AUTH-TOKEN", "X-USER-TOKEN")
                .allowedMethods("PUT", "POST", "GET", "OPTIONS", "DELETE");
    }
}
