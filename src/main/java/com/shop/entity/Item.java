package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
//@IdClass(ItemID.class)
public class Item extends BaseEntity{

    @Id   // 기본키 지정
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)   // 기본키 생성 전략 자동으로 설정
    private Long id;      // 상품 코드

    @Column(nullable = false, length = 50)          // not null 설정
    private String itemNm;    // 상품명

    @Column(name="price", nullable = false)
    private int price;     // 가격

    @Column(nullable = false)
    private int stockNumber;     // 재고 수량

    @Lob
    @Column(nullable = false)
    private String itemDetail;        // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;    // 상품 판매 상태

    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

}
