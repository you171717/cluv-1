package com.shop.controller;

import com.shop.constant.OAuth2ProviderType;
import com.shop.dto.OAuth2FormDto;
import com.shop.entity.Member;
import com.shop.entity.OAuth2Member;
import com.shop.service.NaverOAuth2Service;
import com.shop.service.OAuth2Service;
import com.shop.service.OAuth2ServiceType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequestMapping("/oauth2")
@Controller
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;

    @GetMapping(value = "/login/{code}")
    public String oauthRedirect(@PathVariable("code") String code) {
        OAuth2ProviderType providerType = OAuth2ProviderType.valueOf(code.toUpperCase());

        return "redirect:" + oAuth2Service.getRedirectURL(providerType);
    }

    @GetMapping(value = "/callback/{code}")
    public String oauthCallback(@PathVariable("code") String code, @RequestParam Map<String,String> paramMap, Model model) throws Exception {
        OAuth2ProviderType providerType = OAuth2ProviderType.valueOf(code.toUpperCase());
        OAuth2ServiceType service = oAuth2Service.getProviderService(providerType);

        String authorizationCode = paramMap.get("code");
        String accessToken;

        if(service instanceof NaverOAuth2Service) {
            String stateCode = paramMap.get("state");

            accessToken = ((NaverOAuth2Service) service).getToken(stateCode, authorizationCode);
        } else {
            accessToken = service.getToken(authorizationCode);
        }

        Map<String, String> userInfo = service.getUserInfo(accessToken);

        String email = userInfo.get("email");
        String name = userInfo.get("name");

        if(email == null || name == null || email.isBlank() || name.isBlank()) {
            throw new IllegalArgumentException("이메일이나 이름을 확인할 수 없습니다.");
        }

        Authentication authentication = oAuth2Service.getAuthentication(email);

        if(authentication == null) {
            OAuth2FormDto oAuth2FormDto = new OAuth2FormDto();
            oAuth2FormDto.setName(name);
            oAuth2FormDto.setEmail(email);

            model.addAttribute("OAuth2FormDto", oAuth2FormDto);
            model.addAttribute("code", code);

            return "member/memberSocialForm";
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }

    @PostMapping(value = "/new/{code}")
    public String oauthForm(@PathVariable("code") String code, @Valid OAuth2FormDto oAuth2FormDto, BindingResult bindingResult, Model model) {
        OAuth2ProviderType providerType = OAuth2ProviderType.valueOf(code.toUpperCase());

        model.addAttribute("code", code);

        if(bindingResult.hasErrors()) {
            return "member/memberSocialForm";
        }

        try {
            OAuth2Member oAuth2Member = OAuth2Member.createOAuth2Member(oAuth2FormDto, providerType);

            oAuth2Service.saveOAuth2User(oAuth2Member);

            Authentication authentication = oAuth2Service.getAuthentication(oAuth2Member);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch(IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "member/memberSocialForm";
        }

        return "redirect:/";
    }

}
