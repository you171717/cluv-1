package com.gsitm.intern.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemDiscountDto {

    private Long id;

    private String itemNm;

    private String itemDetail;

    private String imgUrl;

    private Integer price;

    private Integer discountRate;

    @QueryProjection
    public ItemDiscountDto(Long id, String itemNm, String itemDetail, String imgUrl, Integer price, Integer discountRate) {
        this.id = id;
        this.itemNm = itemNm;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
        this.discountRate = discountRate;
    }
}
