package com.gsitm.intern.repository;

import com.gsitm.intern.constant.ItemSellStatus;
import com.gsitm.intern.entity.Item;
import com.gsitm.intern.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    //영속성 컨텍스트 사용하기 위함
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("상품 등록 테스트")
    public void createItemTest() {

        for(int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000);
            item.setItemDetail("테스트 상품 상세 설명");
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
            System.out.println(savedItem.toString());
        }
    }

    @Test
    @DisplayName("상품 등록 테스트2")
    public void createItemTest2() {
        System.out.println("상품 등록 테스트2");

        for(int i = 1; i <= 5; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
            System.out.println(savedItem.toString());
        }
        for(int i = 6; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
            System.out.println(savedItem.toString());
        }
        System.out.println("");
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {
        //this.createItemTest();
        System.out.println("상품명 조회 테스트");
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
        System.out.println("");
    }

    @Test
    @DisplayName("상품명 or 상품상세설명 테스트")
    public void findByItemNmOrItemDetailTest() {
        System.out.println("상품명 or 상품상세설명 테스트");
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트상품1", "테스트 상품");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
        System.out.println("");
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest() {
        System.out.println("가격 LessThan 테스트");
        List<Item> itemList = itemRepository.findByPriceLessThan(20000);
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
        System.out.println("");
    }

    @Test
    @DisplayName("가격 Order by 테스트")
    public void findByPriceLessThanOrderByPriceDescTest() {
        System.out.println("가격 Order by 테스트");
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(20000);
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
        System.out.println("");
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest() {
        System.out.println("@Query를 이용한 상품 조회 테스트");
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
        System.out.println("");
    }

    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
    public void findByItemDetailByNativeTest() {
        System.out.println("nativeQuery 속성을 이용한 상품 조회 테스트");
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
        System.out.println("");
    }

    @Test
    @DisplayName("Querydsl 조회 테스트")
    public void queryDslTest() {
        System.out.println("Querydsl 조회 테스트");
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qitem = QItem.item;

        JPAQuery<Item> query = queryFactory.selectFrom(qitem)
                .where(qitem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qitem.itemDetail.like("%" + "테스트 상품" + "%"))
                .orderBy(qitem.price.desc());

        List<Item> itemList = query.fetch();

        for(Item item : itemList) {
            System.out.println(item.toString());
        }

        System.out.println("");
    }

    @Test
    @DisplayName("Querydsl 조회 테스트 2")
    public void queryDslTest2() {
        System.out.println("Querydsl 조회 테스트2");

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem item = QItem.item;
        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStat = "SELL";

        booleanBuilder.and(item.itemDetail.like("%" + itemDetail + "%"));
        // goe (크거나 같은) , loe (작거나 같은), gt (큰) , lt (작은)
        booleanBuilder.and(item.price.gt(price));

        if(StringUtils.equals(itemSellStat, ItemSellStatus.SELL)) {
            booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder,pageable);

        System.out.println("total elements : " + itemPagingResult.getTotalElements());

        List<Item> resultItemList = itemPagingResult.getContent();

        for(Item resultItem : resultItemList) {
            System.out.println(resultItem.toString());
        }

        System.out.println("");
    }
}