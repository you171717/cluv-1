package com.shop.service;

import com.shop.constant.BidDepositType;
import com.shop.constant.ItemSellStatus;
import com.shop.dto.*;
import com.shop.entity.*;
import com.shop.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;

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

    @Test
    @DisplayName("사용자 입찰 등록 테스트")
    public void createBidTest() {
        Member member = this.createMember();

        ReverseAuction reverseAuction = this.createReverseAuction();

        Bid bid = bidService.saveBid(member.getEmail(), reverseAuction.getId(), BidDepositType.KAKAO_PAY);

        em.flush();
        em.clear();

        Bid savedBid = bidRepository.findById(bid.getId()).orElseThrow(EntityNotFoundException::new);

        System.out.println(savedBid);
    }

    @Test
    @DisplayName("입찰 결제 완료 테스트")
    public void approveBidTest() {
        Member member = this.createMember();

        ReverseAuction reverseAuction = this.createReverseAuction();

        Bid bid = bidService.saveBid(member.getEmail(), reverseAuction.getId(), BidDepositType.TRANSFER);
        Bid bid2 = bidService.saveBid(member.getEmail(), reverseAuction.getId(), BidDepositType.TRANSFER);

        bidService.approveBid(bid.getId());

        try {
            bidService.approveBid(bid2.getId());

            fail();
        } catch(Exception e) {
            assertEquals(e instanceof IllegalStateException, true);
        }
    }

    @Test
    @DisplayName("낙찰 후 결과 반영 테스트")
    public void getAdminPageApplyTest() {
        this.createReverseAuction();
        this.createReverseAuction();
        this.createReverseAuction();

        Member member = this.createMember();
        ReverseAuction reverseAuction = this.createReverseAuction();
        Bid bid = bidService.saveBid(member.getEmail(), reverseAuction.getId(), BidDepositType.TRANSFER);
        bidService.approveBid(bid.getId());

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
    @DisplayName("관리자 결제 관리 조회 테스트")
    public void getAdminPageTest() {
        Member member = this.createMember();

        ReverseAuction reverseAuction = this.createReverseAuction();

        Bid bid = bidService.saveBid(member.getEmail(), reverseAuction.getId(), BidDepositType.TRANSFER);
        Bid bid2 = bidService.saveBid(member.getEmail(), reverseAuction.getId(), BidDepositType.TRANSFER);

        bidService.approveBid(bid.getId());

        BidSearchDto bidSearchDto = new BidSearchDto();

        PageRequest pageRequest = PageRequest.of(0, 6);

        Page<BidDto> bidDtoList = bidService.getAdminBidPage(bidSearchDto, pageRequest);

        for(BidDto bidDto : bidDtoList) {
            System.out.println(bidDto.toString());
        }
    }

    @Test
    @DisplayName("사용자 참여 내역 조회 테스트")
    public void getUserPageTest() {
        Member member = this.createMember();
        Member member2 = this.createMember("hi@test.com");

        ReverseAuction reverseAuction = this.createReverseAuction();

        Bid bid = bidService.saveBid(member.getEmail(), reverseAuction.getId(), BidDepositType.TRANSFER);
        Bid bid2 = bidService.saveBid(member2.getEmail(), reverseAuction.getId(), BidDepositType.TRANSFER);

        bidService.approveBid(bid.getId());

        BidSearchDto bidSearchDto = new BidSearchDto();

        PageRequest pageRequest = PageRequest.of(0, 6);

        Page<BidDto> bidDtoList = bidService.getUserBidPage(member2.getEmail(), bidSearchDto, pageRequest);

        for(BidDto bidDto : bidDtoList) {
            System.out.println(bidDto.toString());
        }
    }

}