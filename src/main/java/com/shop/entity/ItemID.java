package com.shop.entity;

import javax.persistence.*;
import java.io.Serializable;


public class ItemID implements Serializable {

    @Id   // 기본키 지정
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)   // 기본키 생성 전략 자동으로 설정
    private Long id;      // 상품 코드

    @Column(nullable = false, length = 50)          // not null 설정
    private String itemNm;    // 상품명
}
