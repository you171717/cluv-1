package com.gsitm.intern.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.gsitm.intern.dto.QItemMngDto is a Querydsl Projection type for ItemMngDto
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QItemMngDto extends ConstructorExpression<ItemMngDto> {

    private static final long serialVersionUID = 79522130L;

    public QItemMngDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> itemNm, com.querydsl.core.types.Expression<Integer> price, com.querydsl.core.types.Expression<String> itemDetail, com.querydsl.core.types.Expression<com.gsitm.intern.constant.ItemSellStatus> itemSellStatus, com.querydsl.core.types.Expression<Integer> stockNumber, com.querydsl.core.types.Expression<java.time.LocalDateTime> regTime, com.querydsl.core.types.Expression<java.time.LocalDateTime> updateTime, com.querydsl.core.types.Expression<String> createdBy, com.querydsl.core.types.Expression<String> modifiedBy, com.querydsl.core.types.Expression<Long> discountId, com.querydsl.core.types.Expression<java.time.LocalDate> discountDate, com.querydsl.core.types.Expression<com.gsitm.intern.constant.DiscountTime> discountTime, com.querydsl.core.types.Expression<Integer> discountRate) {
        super(ItemMngDto.class, new Class<?>[]{long.class, String.class, int.class, String.class, com.gsitm.intern.constant.ItemSellStatus.class, int.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, String.class, String.class, long.class, java.time.LocalDate.class, com.gsitm.intern.constant.DiscountTime.class, int.class}, id, itemNm, price, itemDetail, itemSellStatus, stockNumber, regTime, updateTime, createdBy, modifiedBy, discountId, discountDate, discountTime, discountRate);
    }

}

