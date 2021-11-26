package com.gsitm.intern.dto;

import com.gsitm.intern.constant.DiscountTime;
import com.gsitm.intern.constant.ItemSellStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class ItemMngDto {
    private Long id;
    private String itemNm;
    private Integer price;
    private String itemDetail;
    private Integer stockNumber;
    private String itemSellStatus;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
    private String createdBy;
    private String modifiedBy;
    private Long discountId;
    private LocalDate discountDate;
    private String discountTime;
    private Integer discountRate;

    @QueryProjection
    public ItemMngDto(Long id, String itemNm, Integer price, String itemDetail, ItemSellStatus itemSellStatus, Integer stockNumber, LocalDateTime regTime,
                      LocalDateTime updateTime, String createdBy, String modifiedBy, Long discountId, LocalDate discountDate, DiscountTime discountTime, Integer discountRate) {
        this.id = id;
        this.itemNm = itemNm;
        this.price = price;
        this.itemDetail = itemDetail;
        switch (itemSellStatus) {
            case SELL: this.itemSellStatus = "SELL"; break;
            case SOLD_OUT: this.itemSellStatus = "SOLD_OUT"; break;
        }
        this.stockNumber = stockNumber;
        this.regTime = regTime;
        this.updateTime = updateTime;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;

        this.discountId = discountId;
        this.discountDate = discountDate;
        if(discountTime != null) {
            switch (discountTime) {
                case ONE: this.discountTime = "00:00:00 ~ 03:59:59"; break;
                case TWO: this.discountTime = "04:00:00 ~ 07:59:59"; break;
                case THREE: this.discountTime = "08:00:00 ~ 11:59:59"; break;
                case FOUR: this.discountTime = "12:00:00 ~ 15:59:59"; break;
                case FIVE: this.discountTime = "16:00:00 ~ 19:59:59"; break;
                case SIX: this.discountTime = "20:00:00 ~ 23:59:59"; break;
                default: this.discountTime = null;
            }
        } else {
            this.discountTime = null;
        }
        this.discountRate = discountRate;
    }
}
