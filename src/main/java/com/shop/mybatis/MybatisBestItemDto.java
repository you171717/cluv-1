package com.shop.mybatis;

import lombok.Data;

@Data
public class MybatisBestItemDto {
    private String itemId;
    private String itemNm;
    private String itemDetail;
    private String imgUrl;
    private int price;
}
