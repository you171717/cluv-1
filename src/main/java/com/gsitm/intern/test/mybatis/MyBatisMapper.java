package com.gsitm.intern.test.mybatis;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description :
 *
 * @author leejinho
 * @version 1.0
 */

@Mapper
public interface MyBatisMapper {
    List<MyBatisTestDto> selectAll();
}
