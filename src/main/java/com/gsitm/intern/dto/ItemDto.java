package com.gsitm.intern.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class ItemDto {
    private Long id;

    private String itemNm;

    private Integer price;

    private String itemDetail;

    private String sellStatCd;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}
//
//import com.gsitm.intern.constant.ItemSellStatus;
//import lombok.Data;
//
//import java.time.LocalDateTime;
//
//@Data
//public class Itemdto {
//
//    private Long id;//상품 코드
//
//    private String itemNm;//상품명
//
//    private int price;//가격
//
//    private int stockNumber;//재고수량
//
//    private String itemDetail;//상품 상세 설명
//
//    private ItemSellStatus itemSellStatus;//상품 판매 상태
//
//    private LocalDateTime regTime;//등록 시간
//
//    private LocalDateTime updateTime;//수정 시간
//}
