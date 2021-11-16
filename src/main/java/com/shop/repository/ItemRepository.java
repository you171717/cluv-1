package com.shop.repository;

import com.shop.entity.Item;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

// JpaRepository <엔티티 타입 클래스, 기본키 타입>
public interface ItemRepository extends JpaRepository<Item, Long>,
        QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {

    List<Item> findByItemNm(String itemNm);   // find + (엔티티) + By + 변수이름

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);   // price 변수보다 값이 작은 상품 데이터를 조회

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);  // OrderBy + 속성명 + Asc/Desc

    @Query("select i " +
             "from Item i " +
            "where i.itemDetail " +
            " like %:itemDetail% " +
            "order by i.price desc") // JPQL 쿼리문

    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail); // JPQL에 들어갈 변수 지정
}



