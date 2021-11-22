package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    // 상품 저장 후 수정할 때 상품 이미지 저장 리스트
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    // 상품의 이미지 아이디 저장 리스트
    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();
//    private static ItemFormMapStruct itemFormMapStruct = new ItemFormMapStructImpl();

    // 엔티티 객체와 DTO 객체 간의 데이터 복사, 복사한 개체 반환
    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }

//    public Item createItem(){
//        return itemFormMapStruct.toEntity(this);
//    }

    public static ItemFormDto of(Item item){
        return modelMapper.map(item, ItemFormDto.class);
    }

//    public static ItemFormDto of(Item item){
//        return itemFormMapStruct.toDto(item);
//    }


}
