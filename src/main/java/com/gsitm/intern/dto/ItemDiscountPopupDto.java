package com.gsitm.intern.dto;

import com.gsitm.intern.constant.DiscountTime;
import com.gsitm.intern.entity.Item;
import com.gsitm.intern.entity.ItemDiscount;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class ItemDiscountPopupDto {
    private Long id;

    private Long itemId;

    @NotBlank(message = "할인일자는 필수 입력 값입니다.")
    private String discountDate;

    private String discountTime;

    @NotNull(message = "할인율을 필수 입력 값입니다.")
    @Range(min = 1, max = 100, message = "할인율은 1% ~ 100% 사이로 입력해주세요.")
    private Integer discountRate;

    @QueryProjection
    public ItemDiscountPopupDto(Long id, Long itemId, LocalDate discountDate, DiscountTime discountTime, Integer discountRate) {
        this.id = id;
        this.itemId = itemId;
        this.discountDate = discountDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        switch (discountTime) {
            case ONE : this.discountTime = "ONE"; break;
            case TWO : this.discountTime = "TWO"; break;
            case THREE : this.discountTime = "THREE"; break;
            case FOUR : this.discountTime = "FOUR"; break;
            case FIVE : this.discountTime = "FIVE"; break;
            case SIX : this.discountTime = "SIX"; break;
        }

        this.discountRate = discountRate;
    }

    public ItemDiscount createItemDiscount(Item item) {
        ItemDiscount itemDiscount = new ItemDiscount();

        itemDiscount.setDiscountDate(LocalDate.parse(this.discountDate, DateTimeFormatter.ISO_DATE));
        itemDiscount.setDiscountTime(DiscountTime.valueOf(discountTime));
        itemDiscount.setDiscountRate(discountRate.intValue());

        item.setItemDiscount(itemDiscount);
        itemDiscount.setItem(item);
        item.setItemDiscount(itemDiscount);

        return itemDiscount;
    }
}
