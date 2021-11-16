package com.shop.repository;

import com.shop.dto.ItemSerachDto;
import com.shop.dto.MainItemDto;
import com.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSerachDto itemSerachDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSerachDto itemSerachDto, Pageable pageable);
}
