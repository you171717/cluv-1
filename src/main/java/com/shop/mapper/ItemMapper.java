package com.shop.mapper;

import com.shop.dto.ItemDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface ItemMapper{

    void createItem(ItemDto itemDto);
    List<ItemDto> findByItemNm(String itemNm);
    List<ItemDto> findByItemNmOrItemDetail(String itemNm, String itemDetail);
    List<ItemDto> findByPriceLessThan(Integer price);
    List<ItemDto> findByPriceLessThanOrderByPriceDesc(Integer price);
    List<ItemDto> findByItemDetail(@Param("itemDetail") String itemDetail);
    List<ItemDto> findByItemDetailByNative(@Param("itemDetail") String itemDetail);

}
