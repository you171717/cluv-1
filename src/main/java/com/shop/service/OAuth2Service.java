package com.shop.service;

import com.shop.constant.OAuth2ProviderType;
import com.shop.entity.Member;
import com.shop.entity.OAuth2MemberInfo;
import com.shop.repository.MemberRepository;
import com.shop.repository.OAuth2MemberInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final MemberRepository memberRepository;

    private final OAuth2MemberInfoRepository oAuth2MemberInfoRepository;

    private final NaverOAuth2Service naverOAuth2Service;

    public OAuth2ServiceType getProviderService(OAuth2ProviderType providerType) {
        switch(providerType) {
            case NAVER: return naverOAuth2Service;
        }

        throw new IllegalArgumentException("Invalid OAuth2 Provider Type");
    }

    public String getRedirectURL(OAuth2ProviderType providerType) {
        OAuth2ServiceType service = this.getProviderService(providerType);

        return service.getRedirectURL();
    }

    public Authentication getAuthenticationByEmail(String email) {
        OAuth2MemberInfo oAuth2MemberInfo = oAuth2MemberInfoRepository.findByEmail(email);

        if(oAuth2MemberInfo != null) {
            Member member = memberRepository.findByEmail(email);

            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

            GrantedAuthority grantedAuthority = new GrantedAuthority() {
                public String getAuthority() {
                    return "ROLE_" + member.getRole().toString();
                }
            };

            grantedAuthorities.add(grantedAuthority);

            return new UsernamePasswordAuthenticationToken(member.getEmail(), null, grantedAuthorities);
        }

        return null;
    }

    public Member saveOAuth2User(Member member, OAuth2MemberInfo oAuth2MemberInfo) {
        if(!StringUtils.equals(member.getEmail(), oAuth2MemberInfo.getEmail())) {
            throw new IllegalStateException("이메일이 올바르지 않습니다.");
        }

        validateDuplicateMember(member);
        validateDuplicateOAuth2Member(oAuth2MemberInfo);

        memberRepository.save(member);

        oAuth2MemberInfoRepository.save(oAuth2MemberInfo);

        return member;
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());

        if(findMember != null) {
            throw new IllegalStateException("해당 이메일은 사이트 회원 가입에 사용된 이메일입니다, 일반 로그인을 이용해 주세요.");
        }
    }

    private void validateDuplicateOAuth2Member(OAuth2MemberInfo oAuth2MemberInfo) {
        OAuth2MemberInfo findMember = oAuth2MemberInfoRepository.findByEmail(oAuth2MemberInfo.getEmail());

        if(findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

}
