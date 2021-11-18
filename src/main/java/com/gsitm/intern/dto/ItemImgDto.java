package com.gsitm.intern.dto;

import com.gsitm.intern.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ItemImgDto {

    private Long id;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;
    private static ModelMapper modelmapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg) {
        return modelmapper.map(itemImg, ItemImgDto.class);
        //ItemImg 엔티티 객체를 파라미터로 받아서
        //ItemImg 객체의 자료형과 멤버변수 이름이 같을 때 ItemImgDto로 값을 복사해서 반환
    }
}
