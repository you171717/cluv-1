package com.gsitm.intern.test.mybatis.mybatis2JPA;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log
@Service
@Transactional
@RequiredArgsConstructor
public class TestMemverService {

    private final TestMemberMapper memberMapper;

    public List<TestMemberDto> memberListAll(){
        return memberMapper.memberListAll();
    }
}
