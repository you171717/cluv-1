package com.shop.dto;

import com.shop.constant.GiftStatus;
import com.shop.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {

    public OrderItemDto(OrderItem orderItem, String imgUrl){
        this.itemNm = orderItem.getItem().getItemNm();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
        this.giftStatus = orderItem.getGiftStatus();
    }

    private String itemNm;    // 상품명

    private int count;      // 주문 수량

    private int orderPrice; // 주문 금액

    private String imgUrl;  // 상품 이미지 경로

    private GiftStatus giftStatus;          // 구매 선물 상태


}
