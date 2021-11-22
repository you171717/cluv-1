package com.shop.dto;

import com.shop.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class OrderItemDto {

    public OrderItemDto(OrderItem orderItem, String imgUrl){
        this.orderItemId = orderItem.getId();
        this.itemNm = orderItem.getItem().getItemNm();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.reviewYn = orderItem.getReviewYn();
        this.imgUrl = imgUrl;
        this.comment = orderItem.getComment();
    }

    private Long orderItemId;
    private String itemNm; //상품명
    private int count; //주문 수량
    private String reviewYn;
    private int orderPrice; //주문 금액
    private String imgUrl; //상품 이미지 경로
    private String comment; //상품 후기

}