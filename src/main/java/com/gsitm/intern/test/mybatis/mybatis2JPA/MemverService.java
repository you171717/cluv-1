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
public class MemverService {

    private final MemberMapper memberMapper;

    public List<MemberDto> memberListAll(){
        return memberMapper.memberListAll();
    }
}
