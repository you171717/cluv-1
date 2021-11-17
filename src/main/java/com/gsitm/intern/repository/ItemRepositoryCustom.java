package com.gsitm.intern.repository;

import com.gsitm.intern.dto.ItemSearchDto;
import com.gsitm.intern.dto.MainItemDto;
import com.gsitm.intern.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
