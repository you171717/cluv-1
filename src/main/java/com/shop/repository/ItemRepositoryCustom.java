package com.shop.repository;

import com.shop.dto.ItemSearchDto;

import com.shop.dto.MainItemDto;
import com.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSerachDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSerachDto, Pageable pageable);
}
