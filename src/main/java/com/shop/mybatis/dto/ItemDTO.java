package com.shop.mybatis.dto;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ItemDTO {

        private Long id;      // 상품 코드
        private String itemNm;    // 상품명
        private int price;     // 가격
        private int stockNumber;     // 재고 수량
        private String itemDetail;        // 상품 상세 설명
        private ItemSellStatus itemSellStatus;    // 상품 판매 상태
        private LocalDateTime regTime;      // 등록 시간
        private LocalDateTime updateTime;   // 수정시간

}
