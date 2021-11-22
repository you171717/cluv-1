package com.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartOrderDto {

    private Long cartItemId;

    private List<CartOrderDto> cartOrderDtoList;  //장바구니에서 여러 상품을 주문하므로 CartOrderDto 클래스가 자기를 list로 가짐

}