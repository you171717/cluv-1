package com.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class ReverseAuctionDto {

    private String imgUrl;

    private String itemNm;

    private Integer startPrice;

    private LocalDateTime startTime;

    private Integer priceUnit;

    private Integer timeUnit;

    private Integer maxRate;

    private String approvedYn;

    private Integer currentPrice;

    private Integer currentDiscountRate;

    private Integer currentDiscountPrice;

    @QueryProjection
    public ReverseAuctionDto(String imgUrl, String itemNm, Integer startPrice, LocalDateTime startTime, Integer priceUnit, Integer timeUnit, Integer maxRate, String approvedYn) {
        this.imgUrl = imgUrl;
        this.itemNm = itemNm;
        this.startPrice = startPrice;
        this.startTime = startTime;
        this.priceUnit = priceUnit;
        this.timeUnit = timeUnit;
        this.maxRate = maxRate;
        this.approvedYn = approvedYn;

        long diffHours = ChronoUnit.HOURS.between(startTime, LocalDateTime.now());

        this.currentPrice = (int) (startPrice - (diffHours * priceUnit));
        this.currentDiscountPrice = startPrice - this.currentPrice;
        this.currentDiscountRate = (int) Math.round((double) this.currentDiscountPrice / this.startPrice * 100);
    }

}
