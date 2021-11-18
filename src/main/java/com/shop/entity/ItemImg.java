//상품의 이미지를 저장하는 상품 이미지 엔티티
//이미지 파일명, 원본 이미지 파일명, 이미지 조회 경로, 대표 이미지 여부 갖음
//대표 이미지 여부 y 인 경우 메인 페이지에 상품 보여줄때 사용
package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table(name ="item_img")
@Getter
@Setter
public class ItemImg extends BaseEntity{
    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repimgYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
