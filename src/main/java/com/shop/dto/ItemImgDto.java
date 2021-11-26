//상품 저장 후 상품 이미지에 대한 데이터를 전달하는 클래스

package com.shop.dto;

import com.shop.entity.ItemImg;
//import com.shop.mapstruct.ItemImgMapStruct;
//import com.shop.mapstruct.ItemImgMapStructImpl;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemImgDto {
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    //private static ItemImgMapStruct itemImgMapStruct = new ItemImgMapStructImpl();

    public static ItemImgDto of(ItemImg itemImg){
        return modelMapper.map(itemImg, ItemImgDto.class);
    }
//    public static ItemImgDto of(ItemImg itemImg){
//        return itemImgMapStruct.toDto(itemImg);
//    }
}
