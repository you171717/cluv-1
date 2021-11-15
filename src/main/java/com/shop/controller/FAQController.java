package com.shop.controller;

import com.shop.dto.FAQDto;
import com.shop.dto.FAQSearchDto;
import com.shop.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FAQController {
    private final FAQService faqService;

    //faq 리스트 불러오는 기능
    @GetMapping(value = "/cscenter")
    public String faqlist(Model model, FAQSearchDto faqSearchDto){
        List<FAQDto> FAQDtoList = faqService.getFAQList();

        //faq 검색 폼
        model.addAttribute("faqsearchdto",new FAQSearchDto());

        //faq 리스트
        model.addAttribute("FAQList", FAQDtoList);
        return "cscenter/faq";
    }

    //admin계정에서 리스트 불러오는 기능
    @GetMapping(value = "/admin/cscenter")
    public String adminfaqlist(Model model){
        List<FAQDto> FAQDtoList = faqService.getFAQList();

        //faq 검색 폼
        model.addAttribute("faqsearchdto",new FAQSearchDto());

        //faq 리스트
        model.addAttribute("FAQList", FAQDtoList);
        return "cscenter/adminfaq";
    }


    //검색결과를 반환하는 기능
    @PostMapping(value = "/cscenter/search")
    public String search(FAQSearchDto faqSearchDto, Model model){
        List<FAQDto> FAQDtoList=faqService.getSearchResult(faqSearchDto);

        //faq 검색 폼
        model.addAttribute("faqsearchdto",new FAQSearchDto());

        //faq 검색 결과 리스트
        model.addAttribute("FAQDtoList",FAQDtoList);
        return "cscenter/faqsearch";
    }

    //admin에서 검색결과를 반환하는 기능
    @PostMapping(value = "/admin/cscenter/search")
    public String adminsearch(FAQSearchDto faqSearchDto, Model model){
        List<FAQDto> FAQDtoList=faqService.getSearchResult(faqSearchDto);

        //faq 검색 폼
        model.addAttribute("faqsearchdto",new FAQSearchDto());

        //faq 검색 결과 리스트
        model.addAttribute("FAQDtoList",FAQDtoList);
        return "cscenter/adminfaqsearch";
    }
}
