package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter @Setter
@ToString
public class Member extends BaseEntity{

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

//    @Builder
//    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
//        Member member = new Member();
//        member.setName(memberFormDto.getName());
//        member.setEmail(memberFormDto.getEmail());
//        member.setAddress(memberFormDto.getAddress());
//        String password = passwordEncoder.encode(memberFormDto.getPassword());
//        member.setPassword(password);
//        member.setRole(Role.USER);
//        return member;
//    }

//  ADMIN 계정 테스트용. 테스트 후 반드시 원상복귀(삭제)****
    @Builder
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        return member;
    }

}
