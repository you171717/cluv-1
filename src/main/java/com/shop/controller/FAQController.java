package com.shop.controller;

import com.shop.dto.FAQDto;
import com.shop.dto.FAQSearchDto;
import com.shop.dto.InquiryFormDto;
import com.shop.dto.MemberFormDto;
import com.shop.entity.Inquiry;
import com.shop.entity.Member;
import com.shop.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FAQController {
    private final FAQService faqService;

    @GetMapping(value = "/cscenter")
    public String faqlist(Model model, FAQSearchDto faqSearchDto){
        List<FAQDto> FAQDtoList = faqService.getFAQList();
        model.addAttribute("faqsearchdto",new FAQSearchDto());
        model.addAttribute("FAQList", FAQDtoList);
        return "cscenter/faq";
    }

    @GetMapping(value = "/admin/cscenter")
    public String adminfaqlist(Model model){
        List<FAQDto> FAQDtoList = faqService.getFAQList();
        model.addAttribute("faqsearchdto",new FAQSearchDto());
        model.addAttribute("FAQList", FAQDtoList);
        return "cscenter/adminfaq";
    }


    @PostMapping(value = "/cscenter/search")
    public String search(FAQSearchDto faqSearchDto, Model model){
        List<FAQDto> FAQDtoList=faqService.getSearchResult(faqSearchDto);
        model.addAttribute("faqsearchdto",new FAQSearchDto());
        model.addAttribute("FAQDtoList",FAQDtoList);
        return "cscenter/faqsearch";
    }

    @PostMapping(value = "/admin/cscenter/search")
    public String adminsearch(FAQSearchDto faqSearchDto, Model model){
        List<FAQDto> FAQDtoList=faqService.getSearchResult(faqSearchDto);
        model.addAttribute("faqsearchdto",new FAQSearchDto());
        model.addAttribute("FAQDtoList",FAQDtoList);
        return "cscenter/adminfaqsearch";
    }
}
