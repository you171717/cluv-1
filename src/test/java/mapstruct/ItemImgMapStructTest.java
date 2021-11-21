//package mapstruct;
//
//import com.shop.dto.ItemImgDto;
//import com.shop.entity.ItemImg;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.transaction.annotation.Transactional;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
//public class ItemImgMapStructTest {
//    @Test
//    @DisplayName("ItemImgMapstruct 변환 테스트")
//    public void toDtoTest() {
//        ItemImg itemImg = new ItemImg();
//        itemImg.setImgName("img.png");
//        itemImg.setImgUrl("C:/shop/item/img.png");
//        itemImg.setOriImgName("ori.png");
//        itemImg.setRepimgYn("Y");
//
//        ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
//
//        assertEquals(itemImg.getImgName(), itemImgDto.getImgName());
//        assertEquals(itemImg.getImgUrl(), itemImgDto.getImgUrl());
//        assertEquals(itemImg.getOriImgName(), itemImgDto.getOriImgName());
//        assertEquals(itemImg.getRepimgYn(), itemImgDto.getRepImgYn());
//    }
//}
