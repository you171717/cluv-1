package com.gsitm.intern.test.mybatis.mybatis2JPA;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMemberMapper {
    List<TestMemberDto> memberListAll();

}
