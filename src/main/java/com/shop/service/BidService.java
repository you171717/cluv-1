package com.shop.service;

import com.shop.constant.BidDepositType;
import com.shop.dto.BidDto;
import com.shop.dto.BidFormDto;
import com.shop.dto.BidSearchDto;
import com.shop.dto.ReverseAuctionDto;
import com.shop.entity.Bid;
import com.shop.entity.Member;
import com.shop.entity.ReverseAuction;
import com.shop.mapstruct.BidFormMapper;
import com.shop.repository.BidRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.ReverseAuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class BidService {

    private final BidFormMapper bidFormMapper;

    private final BidRepository bidRepository;

    private final MemberRepository memberRepository;

    private final ReverseAuctionRepository reverseAuctionRepository;

    public Bid saveBid(String email, Long reverseAuctionId, BidDepositType bidDepositType) {
        Member member = memberRepository.findByEmail(email);

        ReverseAuction reverseAuction = reverseAuctionRepository.findById(reverseAuctionId).orElseThrow(EntityNotFoundException::new);

        ReverseAuctionDto reverseAuctionDto = new ReverseAuctionDto(
                null,
                null,
                reverseAuction.getItem().getPrice(),
                reverseAuction.getStartTime(),
                reverseAuction.getPriceUnit(),
                reverseAuction.getTimeUnit(),
                reverseAuction.getMaxRate(),
                null
        );

        Bid bid = new Bid();
        bid.setDepositAmount(reverseAuctionDto.getCurrentPrice());
        bid.setDepositName(member.getEmail());
        bid.setMember(member);
        bid.setReverseAuction(reverseAuction);
        bid.setDepositType(bidDepositType);

        if(bidDepositType.equals(BidDepositType.KAKAO_PAY)) {
            bid.setApprovedYn("Y");
            bid.setApprovedTime(LocalDateTime.now());
        }

        return bidRepository.save(bid);
    }

    public Long approveBid(Long id) {
        Bid bid = bidRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        bid.setApprovedYn("Y");
        bid.setApprovedTime(LocalDateTime.now());

        return bid.getId();
    }

    @Transactional(readOnly = true)
    public Page<BidDto> getAdminBidPage(BidSearchDto bidSearchDto, Pageable pageable) {
        return bidRepository.getAdminBidPage(bidSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<BidDto> getUserBidPage(BidSearchDto bidSearchDto, Pageable pageable) {
        return bidRepository.getAdminBidPage(bidSearchDto, pageable);
    }

}
