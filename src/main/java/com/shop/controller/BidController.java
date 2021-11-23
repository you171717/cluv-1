package com.shop.controller;

import com.shop.constant.BidDepositType;
import com.shop.dto.ReverseAuctionDto;
import com.shop.entity.Bid;
import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import com.shop.service.BidService;
import com.shop.service.ItemService;
import com.shop.service.MemberService;
import com.shop.service.ReverseAuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Locale;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BidController {

    private final MemberRepository memberRepository;

    private final ItemService itemService;

    private final ReverseAuctionService reverseAuctionService;

    private final BidService bidService;

    @GetMapping(value = "/bid/{reverseAuctionId}")
    public String bidPayment(@PathVariable("reverseAuctionId") Long reverseAuctionId, Principal principal, Model model) {
        Member member = memberRepository.findByEmail(principal.getName());
        ReverseAuctionDto reverseAuctionDto = reverseAuctionService.getReverseAuctionDtl(reverseAuctionId);

        model.addAttribute("member", member);
        model.addAttribute("reverseAuctionDto", reverseAuctionDto);

        return "bid/bidPayment";
    }

    @PostMapping(value = "/bid/{reverseAuctionId}")
    public String bidPaymentProcess(@PathVariable("reverseAuctionId") Long reverseAuctionId, @RequestParam Map<String, String> paramMap, Principal principal, Model model) {
        BidDepositType bidDepositType = BidDepositType.valueOf(paramMap.get("bidDepositType").toUpperCase());

        Member member = memberRepository.findByEmail(principal.getName());

        Bid bid = bidService.saveBid(member.getEmail(), reverseAuctionId, bidDepositType);

        // TODO. When if deposit type is KAKAO_PAY, add record to database

        model.addAttribute("bid", bid);

        return "bid/bidComplete";
    }

}
