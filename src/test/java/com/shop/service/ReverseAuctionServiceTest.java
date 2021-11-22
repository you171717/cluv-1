package com.shop.service;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ReverseAuctionDto;
import com.shop.dto.ReverseAuctionFormDto;
import com.shop.dto.ReverseAuctionSearchDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.entity.ReverseAuction;
import com.shop.mapstruct.ReverseAuctionFormMapper;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.ReverseAuctionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class ReverseAuctionServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    @Autowired
    ReverseAuctionRepository reverseAuctionRepository;

    @Autowired
    ReverseAuctionFormMapper reverseAuctionFormMapper;

    public Item createItem() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(100000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        Item savedItem = itemRepository.save(item);

        ItemImg itemImg = new ItemImg();
        itemImg.setItem(item);
        itemImg.setImgUrl("C:/shop/item/test.png");
        itemImg.setRepImgYn("Y");

        itemImgRepository.save(itemImg);

        return savedItem;
    }

    public ReverseAuction createReverseAuction() {
        Item item = this.createItem();

        ReverseAuction reverseAuction = new ReverseAuction();
        reverseAuction.setStartTime(LocalDateTime.now().minusHours(51));
        reverseAuction.setPriceUnit(1000);
        reverseAuction.setTimeUnit(1);
        reverseAuction.setMaxRate(50);
        reverseAuction.setItem(item);

        return reverseAuctionRepository.save(reverseAuction);
    }

    @Test
    @DisplayName("역경매 등록 테스트")
    public void createReverseAuctionTest() {
        Item item = this.createItem();

        ReverseAuction reverseAuction = new ReverseAuction();
        reverseAuction.setStartTime(LocalDateTime.now());
        reverseAuction.setPriceUnit(1000);
        reverseAuction.setTimeUnit(1);
        reverseAuction.setMaxRate(50);
        reverseAuction.setItem(item);

        ReverseAuction savedReverseAuction = reverseAuctionRepository.save(reverseAuction);
    }

    @Test
    @DisplayName("역경매 조회 테스트")
    public void selectReverseAuctionTest() {
        this.createReverseAuction();
        this.createReverseAuction();
        this.createReverseAuction();
        this.createReverseAuction();

        ReverseAuctionSearchDto reverseAuctionSearchDto = new ReverseAuctionSearchDto();

        PageRequest pageRequest = PageRequest.of(0, 6);

        Page<ReverseAuctionDto> reverseAuctionDtoList = reverseAuctionRepository.getAdminReverseAuctionPage(reverseAuctionSearchDto, pageRequest);

        for(ReverseAuctionDto reverseAuctionDto : reverseAuctionDtoList) {
            System.out.println(reverseAuctionDto.getCurrentPrice());
            System.out.println(reverseAuctionDto.getCurrentDiscountPrice());
            System.out.println(reverseAuctionDto.getCurrentDiscountRate());
        }
    }

    @Test
    @DisplayName("역경매 수정 테스트")
    public void updateReverseAuctionTest() {
        ReverseAuction reverseAuction = this.createReverseAuction();

        ReverseAuctionFormDto reverseAuctionFormDto = reverseAuctionFormMapper.toDto(reverseAuction);
        reverseAuctionFormDto.setMaxRate(60);

        reverseAuctionFormMapper.updateFromDto(reverseAuctionFormDto, reverseAuction);

        reverseAuctionRepository.save(reverseAuction);

        em.flush();
        em.clear();

        ReverseAuction savedReverseAuction = reverseAuctionRepository.findById(reverseAuction.getId()).orElseThrow(EntityNotFoundException::new);

        assertEquals(savedReverseAuction.getMaxRate(), 60);
    }

    @Test
    @DisplayName("역경매 삭제 테스트")
    public void deleteReverseAuctionTest() {
        ReverseAuction reverseAuction = this.createReverseAuction();

        reverseAuctionRepository.save(reverseAuction);

        em.flush();
        em.clear();

        ReverseAuction savedReverseAuction = reverseAuctionRepository.findById(reverseAuction.getId()).orElseThrow(EntityNotFoundException::new);

        reverseAuctionRepository.delete(savedReverseAuction);
    }

}