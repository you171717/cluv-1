package com.gsitm.intern.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.gsitm.intern.dto.QItemDiscountDto is a Querydsl Projection type for ItemDiscountDto
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QItemDiscountDto extends ConstructorExpression<ItemDiscountDto> {

    private static final long serialVersionUID = -1339323323L;

    public QItemDiscountDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> itemNm, com.querydsl.core.types.Expression<String> itemDetail, com.querydsl.core.types.Expression<String> imgUrl, com.querydsl.core.types.Expression<Integer> price, com.querydsl.core.types.Expression<Integer> discountRate) {
        super(ItemDiscountDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, int.class, int.class}, id, itemNm, itemDetail, imgUrl, price, discountRate);
    }

}

