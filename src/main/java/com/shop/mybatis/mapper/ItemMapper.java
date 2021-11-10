package com.shop.mybatis.mapper;

import com.shop.mybatis.dto.ItemDTO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface ItemMapper {

    void createItem(ItemDTO itemDTO);
    List<ItemDTO> findByItemNm(String itemNm);
    List<ItemDTO> findByItemNmOrItemDetail(@Param("itemNm") String itemNm,@Param("itemDetail") String itemDetail);
    List<ItemDTO> findByPriceLessThan(Integer price);
    List<ItemDTO> findByPriceLessThanOrderByPriceDesc(Integer price);
    List<ItemDTO> findByItemDetail(String itemDetail);
    List<ItemDTO> findByItemDetailByNative(String itemDetail);


}
