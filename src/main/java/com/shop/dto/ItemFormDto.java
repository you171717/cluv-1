package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import com.shop.mapstruct.ItemFormMapper;
import com.shop.mapstruct.ItemFormMapperImpl;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "상품 상세 설명은 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    @NotNull(message = "배송비는 필수 입력 값입니다.")
    private Integer shippingFee;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    /*
    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }
    */

    private static ItemFormMapper itemFormMapper = new ItemFormMapperImpl();

    public Item createItem() {
        return itemFormMapper.toEntity(this);
    }

    public static ItemFormDto of(Item item) {
        return itemFormMapper.toDto(item);
    }

}
