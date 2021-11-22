package com.shop.service;

import com.shop.constant.BidDepositType;
import com.shop.constant.ItemSellStatus;
import com.shop.dto.MemberFormDto;
import com.shop.entity.*;
import com.shop.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class BidServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    @Autowired
    ReverseAuctionRepository reverseAuctionRepository;

    @Autowired
    BidRepository bidRepository;

    @Autowired
    BidService bidService;

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");

        Member member = Member.createMember(memberFormDto, passwordEncoder);

        return memberService.saveMember(member);
    }

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
    @DisplayName("사용자 입찰 등록")
    public void createBidTest() {
        Member member = this.createMember();

        ReverseAuction reverseAuction = this.createReverseAuction();

        Bid bid = bidService.saveBid(member.getEmail(), reverseAuction.getId(), BidDepositType.KAKAO_PAY);

        em.flush();
        em.clear();

        Bid savedBid = bidRepository.findById(bid.getId()).orElseThrow(EntityNotFoundException::new);

        System.out.println(savedBid);
    }

}