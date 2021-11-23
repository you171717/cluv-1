package com.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class DiscountDto {

    private Integer currentPrice;

    private Integer currentDiscountRate;

    private Integer currentDiscountPrice;

    public DiscountDto(LocalDateTime startTime, Integer startPrice, Integer priceUnit) {
        long diffHours = ChronoUnit.HOURS.between(startTime, LocalDateTime.now());

        this.currentPrice = (int) (startPrice - (diffHours * priceUnit));
        this.currentDiscountPrice = startPrice - this.currentPrice;
        this.currentDiscountRate = (int) Math.round((double) this.currentDiscountPrice / startPrice * 100);
    }

}