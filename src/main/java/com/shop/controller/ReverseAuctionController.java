package com.shop.controller;

import com.shop.dto.ItemFormDto;
import com.shop.dto.ReverseAuctionFormDto;
import com.shop.dto.ReverseAuctionSearchDto;
import com.shop.service.ReverseAuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ReverseAuctionController {

    private final ReverseAuctionService reverseAuctionService;

    @GetMapping(value = {"/admin/rauctions", "/admin/rauctions/{page}"})
    public String reverseAuctionManage(ReverseAuctionSearchDto reverseAuctionSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        // 최근 등록 순, 가격높은 순, 가격낮은 순 + 상품명으로 검색
        Pageable pageable = PageRequest.of(page.orElse(0), 6);

        return "reverseAuction/reverseAuctionMng";
    }

    @GetMapping(value = "/admin/rauction/new")
    public String reverseAuctionForm(Model model) {
        model.addAttribute("reverseAuctionFormDto", new ReverseAuctionFormDto());

        return "reverseAuction/reverseAuctionForm";
    }

    @PostMapping(value = "/admin/rauction/new")
    public String reverseAuctionNew(@Valid ReverseAuctionFormDto reverseAuctionFormDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "reverseAuction/reverseAuctionForm";
        }

        try {
            // reverseAuctionService.saveReverseAuction(reverseAuctionFormDto);
        } catch(Exception e) {
            model.addAttribute("errorMessage", "역경매 상품 등록 중 에러가 발생하였습니다.");

            return "reverseAuction/reverseAuctionForm";
        }

        return "redirect:/admin/rauctions";
    }

}
