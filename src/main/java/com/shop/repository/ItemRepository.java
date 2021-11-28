package com.shop.repository;

import com.shop.dto.MainItemDto;
import com.shop.dto.OrderItemDto;
import com.shop.entity.Item;
import com.shop.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ItemRepository extends JpaRepository<Item, Long>,
        QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {

    List<Item> findByItemNm(String itemNm);

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query(value = "SELECT * FROM it1661.order_item " +
            "where date_format(reg_time,'%Y%m%d')=date_format(now(), '%Y%m%d')",nativeQuery = true)
    List<OrderItemDto> findByRegTime(String today);

    @Query("select i from Item i where i.itemDetail like " +
            "%:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    @Query(value="select * from item i where i.item_detail like " +
            "%:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);

//    @Query(value = "select i.item_id, i.item_nm, i.item_detail, img.img_url, i.price from (select * from it1661.item where item_sell_status = 'SELL') i " +
//            "join it1661.order_item oi on oi.item_id " +
//            "join it1661.item_img img on img.item_id " +
//            "where date_format(oi.reg_time,'%Y%m%d') = date_format(now(),'%Y%m%d') " +
//            "group by i.item_id " +
//            "order by count(oi.order_id) desc"
//            , nativeQuery = true
//            ,countQuery = "SELECT COUNT(*) FROM (select i.item_id, i.item_nm, i.item_detail, img.img_url, i.price from (select * from it1661.item where item_sell_status = 'SELL') i " +
//                        "join it1661.order_item oi on oi.item_id " +
//                        "join it1661.item_img img on img.item_id " +
//                        "where date_format(oi.reg_time,'%Y%m%d') = date_format(now(),'%Y%m%d') " +
//                        "group by i.item_id " +
//                        "order by count(oi.order_id))")
//    Page<MainItemDto> getBestItemPage(Pageable pageable);

    @Query(value = "select i.item_id, i.item_nm, i.item_detail, img.img_url, i.price from (select * from it1661.item where item_sell_status = 'SELL') i " +
            "join it1661.order_item oi on oi.item_id " +
            "join it1661.item_img img on img.item_id " +
            "group by i.item_id " +
            "order by count(oi.order_id) desc"
            , nativeQuery = true
            ,countQuery = "SELECT COUNT(*) FROM (select i.item_id, i.item_nm, i.item_detail, img.img_url, i.price from (select * from it1661.item where item_sell_status = 'SELL') i " +
            "join it1661.order_item oi on oi.item_id " +
            "join it1661.item_img img on img.item_id " +
            "group by i.item_id " +
            "order by count(oi.order_id))")
    Page<MainItemDto> getBestItemPage(Pageable pageable);

}