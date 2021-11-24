package com.shop.service;

import com.shop.constant.BidDepositType;
import com.shop.constant.ItemSellStatus;
import com.shop.dto.*;
import com.shop.entity.*;
import com.shop.mapstruct.ReverseAuctionFormMapper;
import com.shop.repository.BidRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    BidRepository bidRepository;

    @Autowired
    BidService bidService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberService memberService;

    public Member createMember() {
        return this.createMember(null);
    }

    public Member createMember(String email) {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail(email == null ? "test@email.com" : email);
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

    public ReverseAuction createInProgressReverseAuction(String email) {
        Member member = this.createMember(email);

        ReverseAuction reverseAuction = this.createReverseAuction();

        Bid bid = bidService.saveBid(member.getEmail(), reverseAuction.getId());

        return reverseAuction;
    }

    public ReverseAuction createFinishedReverseAuction(String email) {
        Member member = this.createMember(email);

        ReverseAuction reverseAuction = this.createReverseAuction();

        Bid bid = bidService.saveBid(member.getEmail(), reverseAuction.getId());

        bidService.approveBid(bid.getId());

        return reverseAuction;
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
            System.out.println(reverseAuctionDto.toString());
            System.out.println(reverseAuctionDto.getDiscountDto().getCurrentPrice());
            System.out.println(reverseAuctionDto.getDiscountDto().getCurrentDiscountPrice());
            System.out.println(reverseAuctionDto.getDiscountDto().getCurrentDiscountRate());
        }
    }

    @Test
    @DisplayName("진행중인 역경매 조회 테스트")
    public void inProgressReverseAuctionTest() {
        this.createInProgressReverseAuction("test1@test.com");
        this.createInProgressReverseAuction("test2@test.com");
        this.createInProgressReverseAuction("test3@test.com");
        this.createInProgressReverseAuction("test4@test.com");
        this.createFinishedReverseAuction("test5@test.com");

        ReverseAuctionSearchDto reverseAuctionSearchDto = new ReverseAuctionSearchDto();

        PageRequest pageRequest = PageRequest.of(0, 6);

        Page<ReverseAuctionDto> reverseAuctionDtoList = reverseAuctionRepository.getUserReverseAuctionPage(reverseAuctionSearchDto, pageRequest);

        for(ReverseAuctionDto reverseAuctionDto : reverseAuctionDtoList) {
            System.out.println(reverseAuctionDto.toString());
        }
    }

    @Test
    @DisplayName("이전 역경매 조회 테스트")
    public void previousReverseAuctionTest() {
        this.createInProgressReverseAuction("test1@test.com");
        this.createInProgressReverseAuction("test2@test.com");
        this.createInProgressReverseAuction("test3@test.com");
        this.createInProgressReverseAuction("test4@test.com");
        this.createFinishedReverseAuction("test5@test.com");

        List<ReverseAuctionHistoryDto> reverseAuctionDtoList = reverseAuctionRepository.getPreviousReverseAuctionPage();

        for(ReverseAuctionHistoryDto reverseAuctionDto : reverseAuctionDtoList) {
            System.out.println(reverseAuctionDto.toString());
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