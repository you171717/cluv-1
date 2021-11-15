package com.gsitm.intern.test.mybatis;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description :
 *
 * @author leejinho
 * @version 1.0
 */

@Log
@Service
@Transactional
@RequiredArgsConstructor
public class MyBatisService {

    private final MyBatisMapper myBatisMapper;

    public List<MyBatisTestDto> findAll() {
        return myBatisMapper.selectAll();
    }

}
