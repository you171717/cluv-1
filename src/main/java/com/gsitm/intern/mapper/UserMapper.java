//package com.gsitm.intern.mapper;
//
//
//import com.gsitm.intern.dto.Itemdto;
//import com.gsitm.intern.entity.Item;
//import com.gsitm.intern.test.UserDto;
//import org.apache.ibatis.annotations.Mapper;
//
//import java.util.List;
//
//@Mapper
//public interface UserMapper {
//
//    void insertItem(Itemdto itemDto);
//    List<Itemdto> findByItemNm(String itemNm);
//
//    List<Itemdto> findByItemNmOrItemDetail(String itemNm, String itemDetail);
//
//    List<Itemdto> findByPriceLessThan(Integer price);
//
//    List<Itemdto> findByPriceLessThanOrderByPriceDesc(Integer price);
//
//    List<Itemdto> findByItemDetail(String itemDetail);
//
//    List<Itemdto> findByItemDetailByNative(String itemDetail);
//
//}
