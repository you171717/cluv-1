package com.shop.dto;

import com.shop.entity.ItemImg;
import com.shop.mapper.ItemImgMapStruct;
import com.shop.mapper.ItemImgMapStructImpl;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ItemImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

//    private static ModelMapper modelMapper = new ModelMapper();    // 멤버 변수로 모델 객체 추가
    private static ItemImgMapStruct itemImgMapStruct = new ItemImgMapStructImpl();


//    // ItemImg 객체 자료형과 멤버 변수 이름이 같을 때 ItemImgDto로 값을 복사, 반환
//    public static ItemImgDto of(ItemImg itemImg){
//        return  modelMapper.map(itemImg, ItemImgDto.class);
//
//    }

    public static ItemImgDto of(ItemImg itemImg){
        return  itemImgMapStruct.toDto(itemImg);

    }
}
