package com.gsitm.intern.test.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description :
 *
 * @author leejinho
 * @version 1.0
 */

@Service
@Transactional
@RequiredArgsConstructor
public class JpaService {

    private final TestMemberRepository memberRepository;

    public List<JpaEntity> listAll() {
        return memberRepository.findAll();
    }
}
