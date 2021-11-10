package com.shop.mapper;

import com.shop.dto.ItemDto;
import com.shop.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface ItemMapper {

    void insertItem(ItemDto itemDto);

    List<ItemDto> findByItemNm(String itemNm);

    List<ItemDto> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<ItemDto> findByPriceLessThan(Integer price);

    List<ItemDto> findByPriceLessThanOrderByPriceDesc(Integer price);

    List<ItemDto> findByItemDetail(String itemDetail);

    List<ItemDto> findByItemDetailByNative(String itemDetail);
}
