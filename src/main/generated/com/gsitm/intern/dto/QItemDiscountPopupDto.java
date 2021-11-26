package com.gsitm.intern.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.gsitm.intern.dto.QItemDiscountPopupDto is a Querydsl Projection type for ItemDiscountPopupDto
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QItemDiscountPopupDto extends ConstructorExpression<ItemDiscountPopupDto> {

    private static final long serialVersionUID = -313338131L;

    public QItemDiscountPopupDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<Long> itemId, com.querydsl.core.types.Expression<java.time.LocalDate> discountDate, com.querydsl.core.types.Expression<com.gsitm.intern.constant.DiscountTime> discountTime, com.querydsl.core.types.Expression<Integer> discountRate) {
        super(ItemDiscountPopupDto.class, new Class<?>[]{long.class, long.class, java.time.LocalDate.class, com.gsitm.intern.constant.DiscountTime.class, int.class}, id, itemId, discountDate, discountTime, discountRate);
    }

}

