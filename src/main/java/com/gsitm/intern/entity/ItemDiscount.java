package com.gsitm.intern.entity;

import com.gsitm.intern.constant.DiscountTime;
import com.gsitm.intern.dto.ItemDiscountPopupDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "item_discount")
@Getter @Setter
@ToString
public class ItemDiscount extends BaseEntity {
    @Id
    @Column(name = "item_discount_id")
    @GeneratedValue
    private Long id;

    private LocalDate discountDate;

    @Enumerated(EnumType.STRING)
    private DiscountTime discountTime;

    private int discountRate;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemDiscount(ItemDiscountPopupDto itemDiscountPopupDto) {
        this.discountDate = LocalDate.parse(itemDiscountPopupDto.getDiscountDate(), DateTimeFormatter.ISO_DATE);
        this.discountTime = DiscountTime.valueOf(itemDiscountPopupDto.getDiscountTime());
        this.discountRate = itemDiscountPopupDto.getDiscountRate().intValue();
    }
}
