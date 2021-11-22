package com.gsitm.intern.service;

import com.gsitm.intern.dto.*;
import com.gsitm.intern.entity.Item;
import com.gsitm.intern.entity.ItemDiscount;
import com.gsitm.intern.entity.ItemImg;
import com.gsitm.intern.repository.ItemDiscountRepository;
import com.gsitm.intern.repository.ItemImgRepository;
import com.gsitm.intern.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ItemDiscountService {

    private final ItemRepository itemRepository;
    private final ItemDiscountRepository itemDiscountRepository;
    private final ItemImgRepository itemImgRepository;

    @Transactional(readOnly = true)
    public Page<ItemDiscountDto> getDiscountItemPage(Pageable pageable) {
        return itemRepository.getDiscountItemPage(pageable);
    }

    @Transactional(readOnly = true)
    public ItemDiscountPopupDto getItemDiscountPopup(Long itemDiscountId) {
        return itemDiscountRepository.getItemDiscountPopup(itemDiscountId);
    }

    public Long updateDiscount(ItemDiscountPopupDto itemDiscountPopupDto) {
        ItemDiscount itemDiscount = itemDiscountRepository.getItemDiscount(itemDiscountPopupDto.getId());

        itemDiscount.updateItemDiscount(itemDiscountPopupDto);
        return itemDiscount.getId();
    }

    public Long saveDiscount(ItemDiscountPopupDto itemDiscountPopupDto) {
        Item item = itemRepository.getItem(itemDiscountPopupDto.getItemId());

        ItemDiscount itemDiscount = itemDiscountPopupDto.createItemDiscount(item);
        itemDiscountRepository.save(itemDiscount);

        return itemDiscount.getId();
    }

    public void deleteDiscount(Long discountId) {
        ItemDiscount itemDiscount = itemDiscountRepository.getItemDiscount(discountId);

        itemDiscountRepository.delete(itemDiscount);
    }

    @Transactional(readOnly = true)
    public ItemDiscountDtlDto getDiscountItemDtl(Long itemId) {
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        for(ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        ItemMngDto itemMngDto = itemDiscountRepository.getDiscountItemDtl(itemId);
        ItemDiscountDtlDto itemFormDto = ItemDiscountDtlDto.of(itemMngDto);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }
}
