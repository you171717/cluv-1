package com.gsitm.intern.repository;

import com.gsitm.intern.dto.*;
import com.gsitm.intern.entity.Item;
import com.gsitm.intern.entity.ItemDiscount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<ItemMngDto> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<ItemDiscountDto> getDiscountItemPage(Pageable pageable);

    ItemDiscountPopupDto getItemDiscountPopup(Long id);

    ItemDiscount getItemDiscount(Long id);

    Item getItem(Long id);

    ItemMngDto getDiscountItemDtl(Long id);
}
