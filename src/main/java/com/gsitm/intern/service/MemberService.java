package com.gsitm.intern.service;

import com.gsitm.intern.dto.MemberFormDto;
import com.gsitm.intern.entity.Member;
import com.gsitm.intern.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member){    //이미 가입된 회원 IllegalStateException 예외 발생
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Member member = memberRepository.findByEmail(email);

        if(member == null){
            throw new UsernameNotFoundException(email);
        }

        return User.builder()   //UserDetail을 구현하고 있는 User 개체 반환
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
    //이메일, 이름 일치 확인 메소드
    public boolean checkEmailAndName(String email, String name) {
        Member member = memberRepository.findByEmail(email);

        if(member == null || !member.getName().equals(name)) {
            throw new IllegalStateException("이메일과 이름이 일치하지 않습니다.");
        }
        return true;
    }
    //비밀번호 변경 메소드
    public void updatePassword(Long memberId, String password) {
        memberRepository.updatePassword(memberId, passwordEncoder.encode(password));
    }
}
