package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>,
        QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {

    //상품명으로 데이터를 조회
    List<Item> findByItemNm(String itemNm);

    //상품을 상품명과 상품 상세 설명을 OR 조건을 이용하여 조회하는 쿼리 메소드
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    //파라미터로 넘어온 price 변수보다 값이 작은 상품 데이터를 조회하는 쿼리 메소드
    List<Item> findByPriceLessThan(Integer price);

    //상품의 가격이 높은 순으로 조회하는 쿼리 메소드
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    //상품 상세 설명을 파라미터로 받아 해당 내용을 상품 상세 설명에 포함하고 있는 데이터를 조회하며, 정렬 순서는 가격이 높은 순으로 조회하는 쿼리 메소드
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    @Query(value="select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);

}
