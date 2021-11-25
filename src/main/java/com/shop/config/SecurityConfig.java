package com.shop.config;

import com.shop.config.auth.CustomOAuth2UserService;
import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    @Autowired
    CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/members/login") //로그인 페이지 URL 설정
                .defaultSuccessUrl("/")     //로그인 성곡싱 이동할 URL
                .usernameParameter("email")     //로그인 시 사용할 파라미터 이름으로 email을 지정
                .failureUrl("/members/login/error")     //로그인 실패 시 이동할 URL
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))     //로그아웃 URL을 설정
                .logoutSuccessUrl("/")      //로그아웃 성공시 URL을 설정
                .and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
        ;

        http.authorizeRequests()    //시큐리티 처리에 HttpServleRequest이용
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**", "/oauth2/**","/detailSearch/**").permitAll()  //모든 사용자가 로그인 없이 해당 경로 접근 가능
                .mvcMatchers("/admin/**").hasRole("ADMIN")      //admin으로 시작하는 경로는 해당 계정이 ADMIN ROLE일 경우만 접근 가능
                .anyRequest().authenticated()       //설정한 경로 외 접근시 모두 인증 요구
        ;

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())     // 인증되지 않은 사용자 접근 시 수행되는 핸들러 등록
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //인증
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");     //static 디레터리의 하위 파일 인증을 무시하도록 설정
    }


}
