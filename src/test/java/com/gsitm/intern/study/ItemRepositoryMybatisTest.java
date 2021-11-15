package com.gsitm.intern.study;

import com.gsitm.intern.constant.ItemSellStatus;
import com.gsitm.intern.dto.ItemDto;
import com.gsitm.intern.entity.Item;
import com.gsitm.intern.mapper.ItemMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
public class ItemRepositoryMybatisTest {

    @Autowired
    ItemMapper itemMapper;

    //JPA에서 테이블 생성으로 기본키 Generation.AUTO 전략을 따르면서 id값이 증가하는데
    //이를 MyBatis에서도 id값을 1부터 증가시키기 위해서 static으로 1L값으로 선언 후 ++로 증가시킨다
    static Long IncrementId = 1L;

    @Test   //아래 메소드 테스트 대상으로 지정
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        ItemDto item = new ItemDto();   //ItemDto로 item 선언
        item.setId(IncrementId++);
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        itemMapper.insertItem(item);    //Repository 활용대신 Mapper의 insertItem 메소드 활용
        System.out.println(item.toString());
    }

    public void createItemList() {
        for (int i = 1; i <= 10; i++) {
            ItemDto item = new ItemDto();
            item.setId(IncrementId++);
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemMapper.insertItem(item);
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {
        this.createItemList();
        List<ItemDto> itemList = itemMapper.findByItemNm("테스트 상품1");
        for (ItemDto item : itemList) {
            System.out.println(item.toString());    //조회결과 얻은 item객체 출력
        }
    }
    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest() {
        this.createItemList();
        List<ItemDto> itemList = itemMapper.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for(ItemDto item : itemList) {
            System.out.println(item.toString());
        }
    }
    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest() {
        this.createItemList();
        List<ItemDto> itemList = itemMapper.findByPriceLessThan(10005);
        for(ItemDto item : itemList) {
            System.out.println(item.toString());
        }
    }
    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc() {
        this.createItemList();
        List<ItemDto> itemList = itemMapper.findByPriceLessThanOrderByPriceDesc(10005);
        for(ItemDto item : itemList) {
            System.out.println(item.toString());
        }
    }
    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
    public void findByItemDetailByNative() {
        this.createItemList();
        List<ItemDto> itemList = itemMapper.findByItemDetailByNative("테스트 상품 상세 설명");
        for(ItemDto item : itemList) {
            System.out.println(item.toString());
        }
    }
}
