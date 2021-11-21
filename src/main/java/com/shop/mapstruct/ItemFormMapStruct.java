package com.shop.mapstruct;

import com.shop.dto.ItemFormDto;
import com.shop.entity.Item;
import org.mapstruct.Mapper;

@Mapper
public interface ItemFormMapStruct extends EntityMapper<ItemFormDto, Item> {
}
