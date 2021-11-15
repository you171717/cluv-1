package com.gsitm.intern.repository;

import com.gsitm.intern.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {
    //SELECT * FROM ITEM WHERE ITEMNM = 'itemNm';
    List<Item> findByItemNm(String itemNm);
    //SELECT * FROM ITEM WHERE ITEMNM = 'itemNm' OR ITEMDETAIL = 'itemDetail';
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);
    //SELECT * FROM ITEM WHERE PRICE < price;
    List<Item> findByPriceLessThan(Integer price);
    //SELECT * FROM ITEM WHERE PRICE < price ORDER BY PRICE DESC;
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    //@Query
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    //기존 DB에서 사용하는 Query 그대로 사용
    @Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
}
