package com.gsitm.intern.repository;

import com.gsitm.intern.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
public interface ItemRepository extends JpaRepository<Item, Long>,QuerydslPredicateExecutor<Item>, ItemRepositoryCustom{

    List<Item> findByItemNm(String itemNm); // find 뒤 엔티티 이름 생략 가능

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail); // 상픔을 상품명과 상품 상세 설명을 OR 조건을 이용하여 조회하는 쿼리 메소드

    List<Item> findByPriceLessThan(Integer price); // 파라미터로 넘어온 price 변수보다 값이 작은 상품 데이터를 조회하는 쿼리 메소드

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    // JPQL로 작성한 쿼리문을 넣어준다.
    // from 뒤에는 엔티티 클래스로 작성한 item을 지정해주었고, Item으로부터 데이터를 select하겠다는 것을 의미
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
    // 파라미터에 @Param 어노테이션을 이용하여 파라미터로 넘어온 값을 JPQL에 들어갈 변수로 지정
    // 현재는 itemDetail 변수를 "like % %" 사이에 ".itemDetail"로 값이 들어가도록 작성하였다.

    @Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);

}
