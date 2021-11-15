package com.shop.controller;

import com.shop.dto.CommentFormDto;
import com.shop.dto.FAQSearchDto;
import com.shop.dto.InquiryFormDto;
import com.shop.service.CommentService;
import com.shop.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class InquiryController {
    private final InquiryService inquiryService;
    private final CommentService commentService;

    //문의 사항 입력 폼 이동 기능
    @GetMapping(value = "/cscenter/voc")
    public String voc(Model model){

        //문의 사항 폼
        model.addAttribute("inquiryFormDto",new InquiryFormDto());

        //faq 검색 폼
        model.addAttribute("faqsearchdto",new FAQSearchDto());
        return "cscenter/inquiryForm";
    }

    //문의 사항 저장 기능
    @PostMapping(value="/cscenter/voc")
    public String newinquiry(@Valid InquiryFormDto inquiryFormDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "cscenter/inquiryForm";
        }
        try{
            inquiryService.saveInquiry(inquiryFormDto);
        }catch(IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "cscenter/inquiryForm";
        }
        return "redirect:/cscenter/voclist";
    }

    //문의 사항 리스트 가져오는 기능
    @GetMapping(value = "/cscenter/voclist")
    public String inquirylist(Model model, Principal principal){

        //현재 로그인한 사람의 문의 사항 리스트만 가져옴
        List<InquiryFormDto> InquiryList = inquiryService.getInquiryList(principal.getName());
        model.addAttribute("InquiryList", InquiryList);
        model.addAttribute("faqsearchdto",new FAQSearchDto());
        return "cscenter/vocList";
    }

    //문의 사항 세부페이지 기능
    @GetMapping(value = "/cscenter/voclist/{id}")
    public String inquiryDtl(Model model, @PathVariable("id")Long id){
        InquiryFormDto inquiryFormDto=inquiryService.getInquiryDtl(id);
        List<CommentFormDto> commentFormDto=  commentService.getCommentList(id);
        model.addAttribute("faqsearchdto",new FAQSearchDto());
        model.addAttribute("inquiry", inquiryFormDto);
        model.addAttribute("commentlist",commentFormDto);
        return "cscenter/inquiryDtl";
    }

    //문의 사항 수정 폼 기능
    @GetMapping(value = "/cscenter/voclist/edit/{id}")
    public String inquiryEdit(Model model,@PathVariable("id")Long id){
        InquiryFormDto inquiryFormDto=inquiryService.getInquiryDtl(id);
        model.addAttribute("inquiryFormDto", inquiryFormDto);
        model.addAttribute("faqsearchdto",new FAQSearchDto());
        return "cscenter/inquiryEdit";
    }

    @PostMapping(value = "/cscenter/voclist/edit/{id}")
    public String inquiryUpdate(@Valid InquiryFormDto inquiryFormDto,Model model){
        inquiryService.updateInquiry(inquiryFormDto);
        return "redirect:/cscenter/voclist/";
    }


    //문의 사항 삭제 기능
    @GetMapping(value = "/cscenter/voclist/delete/{id}")
    public String inquiryDelete(@PathVariable("id") Long id){
        commentService.deleteComment(id);
        inquiryService.deleteInquiry(id);
        return "redirect:/cscenter/voclist/";
    }

    //admin에서 모든 문의 사항 리스트를 불러오는 기능
    @GetMapping(value = "/admin/cscenter/voclist")
    public String admininquirylist(Model model){
        List<InquiryFormDto> InquiryList = inquiryService.getALLInquiryList();
        model.addAttribute("faqsearchdto",new FAQSearchDto());
        model.addAttribute("InquiryList", InquiryList);
        return "cscenter/adminvocList";
    }

    //admin에서 문의사항 세부 페이지 기능
    @GetMapping(value = "/admin/cscenter/voclist/{id}")
    public String admininquiryDtl(Model model, @PathVariable("id")Long id){
        InquiryFormDto inquiryFormDto=inquiryService.getInquiryDtl(id);
        List<CommentFormDto> commentFormDto=  commentService.getCommentList(id);
        model.addAttribute("inquiry", inquiryFormDto);
        model.addAttribute("comment",new CommentFormDto());
        model.addAttribute("faqsearchdto",new FAQSearchDto());
        model.addAttribute("commentlist",commentFormDto);

        return "cscenter/admininquiryDtl";
    }
}
