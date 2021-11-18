package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Many;

import javax.persistence.*;

@Entity
@Table(name="item_img")
@Getter @Setter
public class ItemImg extends BaseEntity{

    @Id
    @Column(name ="item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;       // 이미지 파일명

    private String oriImgName;    // 원본 이미지 파일명명

    private String imgUrl;        // 이미지 조회 경로

    private String repimgYn;      // 대표 이미지 여부 - Y인 경우 메인 페이지에 보임

    @ManyToOne(fetch = FetchType.LAZY)       // 다대일 단방향 매핑, 지연로딩
    @JoinColumn(name = "item_id")
    private Item item;

    // 이미지 정보 업데이트
    public void updateItemImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
