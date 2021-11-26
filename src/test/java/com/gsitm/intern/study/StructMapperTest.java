package com.gsitm.intern.study;

import com.gsitm.intern.constant.ItemSellStatus;
import com.gsitm.intern.dto.ItemDto;
import com.gsitm.intern.dto.ItemFormDto;
import com.gsitm.intern.entity.Item;
import com.gsitm.intern.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
public class StructMapperTest {

    @Test
    @DisplayName("Item에서 ItemFormDto로 변환 테스트")
    public void toDtoTest() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(10);
        item.setPrice(10000);

        ItemFormDto itemFormDto = ItemFormDto.of(item);

        assertEquals(item.getItemNm(), itemFormDto.getItemNm());
        assertEquals(item.getItemDetail(), itemFormDto.getItemDetail());
        assertEquals(item.getItemSellStatus(), itemFormDto.getItemSellStatus());
        assertEquals(item.getStockNumber(), itemFormDto.getStockNumber());
        assertEquals(item.getPrice(), itemFormDto.getPrice());
    }

    @Test
    @DisplayName("ItemFormDto에서 Item로 변환 테스트")
    public void toEntityTest() {
        ItemFormDto itemFormDto = new ItemFormDto();
        itemFormDto.setItemNm("테스트 상품");
        itemFormDto.setItemDetail("테스트 상품 상세 설명");
        itemFormDto.setItemSellStatus(ItemSellStatus.SELL);
        itemFormDto.setStockNumber(10);
        itemFormDto.setPrice(10000);

        Item item = itemFormDto.createItem();

        assertEquals(itemFormDto.getItemNm(), item.getItemNm());
        assertEquals(itemFormDto.getItemDetail(), item.getItemDetail());
        assertEquals(itemFormDto.getItemSellStatus(), item.getItemSellStatus());
        assertEquals(itemFormDto.getStockNumber(), item.getStockNumber());
        assertEquals(itemFormDto.getPrice(), item.getPrice());
    }

}
