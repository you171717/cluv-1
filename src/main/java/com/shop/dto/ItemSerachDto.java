package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSerachDto {

    private String searchDateType;               // 현재 시간과 상품 등록일 비교

    private ItemSellStatus searchSellStatus;     // 상품 판매 상태 기준

    private String searchBy;                     // 상품 유형

    private String searchQuery = "";             // 조회할 검색어 저장 변수
}
