package com.tianlei.cconfig;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class TianleiUploadCORSConfig {

    public CorsFilter corsFilter() {

        // 添加cors信息
        CorsConfiguration config = new CorsConfiguration();
        // 允许的领域不要写
        config.addAllowedOrigin("http://manage.tianlei.com");
        // 是否发送cookie信息
        config.setAllowCredentials(false);
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("POST");

        config.addAllowedHeader("*");

        // 添加映射路径,拦截一切请求
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", config);

        // 返回新的CorsFilter

        return new CorsFilter(configurationSource);


    }

}
