package com.gsitm.intern.dto;


import com.gsitm.intern.constant.ReturnStatus;
import com.gsitm.intern.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class OrderItemDto {

    public OrderItemDto(OrderItem orderItem, String imgUrl){
        this.itemNm = orderItem.getItem().getItemNm();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;

        this.returnCount = orderItem.getReturnCount();
        this.returnPrice = orderItem.getReturnPrice();
        if( orderItem.getReturnReqDate() != null ) {
            this.returnReqDate = orderItem.getReturnReqDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
        if( orderItem.getReturnConfirmDate() != null ) {
            this.returnConfirmDate = orderItem.getReturnConfirmDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }

        this.returnStatus = orderItem.getReturnStatus();
    }

    private String itemNm; // 상품명

    private int count; // 주문 수량

    private int orderPrice; // 주문 금액

    private String imgUrl; // 상품 이미지 경로

    //반품 갯수
    private int returnCount;

    //반품 금액
    private int returnPrice;

    //반품 요청일
    private String returnReqDate;

    //반품 확정일
    private String returnConfirmDate;

    //반품 여부
    private ReturnStatus returnStatus;
}