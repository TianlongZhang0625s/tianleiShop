package com.tianlei.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class TianleiCORSConfiguration {

    @Bean
    public CorsFilter corsFilter() {
        // 添加cors配置信息
        CorsConfiguration config = new CorsConfiguration();
        // 允许的域不要写*，否则cookie无法使用
        config.addAllowedOrigin("http://manage.tianlei.com");
        // 是否发送cookie信息
        config.setAllowCredentials(true);

        // 允许的请求方式

        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");

        // 添加允许的头信息
        config.addAllowedHeader("*");

        //添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", config);

        // 返回corsFilter
        return new CorsFilter(configurationSource);


    }
}
