package com.gsitm.intern.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity  //어노테이션 통해 SpringSecurityFilterChain 자동으로 포함
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    }
    @Bean
    public PasswordEncoder passwordEncoder(){   //BCryptPasswordEncoder의 해시 함수 이용해서 암호화 저장
        return new BCryptPasswordEncoder();
    }
}
