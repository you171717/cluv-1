package com.shop.mybatis;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log
@Service
@Transactional
@RequiredArgsConstructor
public class MyBatisService {

    private final MyBatisBestItemMapper myBatisBestItemMapper;

    public List<MybatisBestItemDto> getBestItems() {
        return myBatisBestItemMapper.getBestItemInfo();
    }
}
