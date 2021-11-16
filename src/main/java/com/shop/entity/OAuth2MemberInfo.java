package com.shop.entity;

import com.shop.constant.OAuth2ProviderType;
import com.shop.constant.Role;
import com.shop.dto.OAuth2FormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="oauth")
@Getter
@Setter
@ToString
public class OAuth2MemberInfo extends BaseTimeEntity {

    @Id
    @Column(name="email_social")
    private String email;

    private String name;

    @Enumerated(EnumType.STRING)
    private OAuth2ProviderType type;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Member member;

    public static Member createMember(OAuth2FormDto oAuth2FormDto) {
        Member member = new Member();
        member.setName(oAuth2FormDto.getName());
        member.setEmail(oAuth2FormDto.getEmail());
        member.setAddress(oAuth2FormDto.getAddress());
        member.setRole(Role.USER);

        return member;
    }

    public static OAuth2MemberInfo createOAuth2MemberInfo(OAuth2FormDto oAuth2FormDto, OAuth2ProviderType type) {
        OAuth2MemberInfo oAuth2MemberInfo = new OAuth2MemberInfo();
        oAuth2MemberInfo.setEmail(oAuth2FormDto.getEmail());
        oAuth2MemberInfo.setName(oAuth2FormDto.getName());
        oAuth2MemberInfo.setType(type);

        return oAuth2MemberInfo;
    }

}
