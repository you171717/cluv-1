package com.shop.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}")        // uploadPath 프로퍼티 값
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/images/**")    // uploadPath에 설정한 폴더 기준으로 읽어옴
                .addResourceLocations(uploadPath);              // 로컬 컴퓨터에 저장된 파일을 불러올 root 경로를 설정
    }
}
