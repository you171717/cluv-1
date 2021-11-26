package com.gsitm.intern.dto;

import com.gsitm.intern.constant.DiscountTime;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter @Setter
public class CartDetailDto {

    private Long cartItemId; // 장바구니 상품 아이디

    private String itemNm; // 상품명

    private int price; // 상품 금액

    private int count; // 수량

    private String imgUrl; // 상품 이미지 경로

    private String discountDate;

    private String discountTime;

    private Integer discountRate;

    public CartDetailDto(Long cartItemId, String itemNm, int price, int count, String imgUrl, LocalDate discountDate, DiscountTime discountTime, Integer discountRate) {
        this.cartItemId = cartItemId;
        this.itemNm = itemNm;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;

        this.discountDate = discountDate != null ? discountDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
        if(discountTime != null) {
            switch (discountTime) {
                case ONE: this.discountTime = "03:59:59"; break;
                case TWO: this.discountTime = "07:59:59"; break;
                case THREE: this.discountTime = "11:59:59"; break;
                case FOUR: this.discountTime = "15:59:59"; break;
                case FIVE: this.discountTime = "19:59:59"; break;
                case SIX: this.discountTime = "23:59:59"; break;
                default: this.discountTime = null;
            }
        } else {
            this.discountTime = null;
        }
        this.discountRate = discountRate;
    }

}
