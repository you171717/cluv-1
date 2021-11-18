package com.gsitm.intern.shop.repository;


import com.gsitm.intern.shop.dto.ItemSearchDto;
import com.gsitm.intern.shop.dto.MainItemDto;
import com.gsitm.intern.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}
