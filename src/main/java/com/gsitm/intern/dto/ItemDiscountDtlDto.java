package com.gsitm.intern.dto;

import com.gsitm.intern.constant.ItemSellStatus;
import com.gsitm.intern.entity.Item;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemDiscountDtlDto {
    private Long id;

    private String itemNm;

    private Integer price;

    private String itemDetail;

    private Integer stockNumber;

    private String itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<ItemImgDto>();

    private List<Long> itemImgIds = new ArrayList<>();

    private String discountTime;

    private Integer discountRate;

    public static ItemDiscountDtlDto of(ItemMngDto itemMngDto) {
        ItemDiscountDtlDto itemDiscountDtlDto = new ItemDiscountDtlDto();

        itemDiscountDtlDto.id = itemMngDto.getId();
        itemDiscountDtlDto.itemNm = itemMngDto.getItemNm();
        itemDiscountDtlDto.price = itemMngDto.getPrice();
        itemDiscountDtlDto.itemDetail = itemMngDto.getItemDetail();
        itemDiscountDtlDto.stockNumber = itemMngDto.getStockNumber();
        itemDiscountDtlDto.itemSellStatus = itemMngDto.getItemSellStatus();
        itemDiscountDtlDto.discountTime = itemMngDto.getDiscountDate() + " " +  itemMngDto.getDiscountTime().substring(11);
        itemDiscountDtlDto.discountRate = itemMngDto.getDiscountRate();

        return itemDiscountDtlDto;
    }
}
