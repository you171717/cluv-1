package com.shop.controller;

import com.shop.dto.CommentFormDto;
import com.shop.dto.FAQDto;
import com.shop.dto.FAQSearchDto;
import com.shop.dto.InquiryFormDto;
import com.shop.entity.Inquiry;
import com.shop.service.CommentService;
import com.shop.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class InquiryController {
    private final InquiryService inquiryService;
    private final CommentService commentService;

    @GetMapping(value = "/cscenter/voc")
    public String voc(Model model){
        model.addAttribute("inquiryFormDto",new InquiryFormDto());
        model.addAttribute("faqsearchdto",new FAQSearchDto());
        return "cscenter/inquiryForm";
    }

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

    @GetMapping(value = "/cscenter/voclist")
    public String inquirylist(Model model, Principal principal){
        List<InquiryFormDto> InquiryList = inquiryService.getInquiryList(principal.getName());
        model.addAttribute("InquiryList", InquiryList);
        model.addAttribute("faqsearchdto",new FAQSearchDto());
        return "cscenter/vocList";
    }

    @GetMapping(value = "/cscenter/voclist/{id}")
    public String inquiryDtl(Model model, @PathVariable("id")Long id){
        InquiryFormDto inquiryFormDto=inquiryService.getInquiryDtl(id);
        List<CommentFormDto> commentFormDto=  commentService.getCommentList(id);
        model.addAttribute("faqsearchdto",new FAQSearchDto());
        model.addAttribute("inquiry", inquiryFormDto);
        model.addAttribute("commentlist",commentFormDto);
        return "cscenter/inquiryDtl";
    }

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

    @GetMapping(value = "/cscenter/voclist/delete/{id}")
    public String inquiryDelete(@PathVariable("id") Long id){
        inquiryService.deleteInquiry(id);
        return "redirect:/cscenter/voclist/";
    }

    @GetMapping(value = "/admin/cscenter/voclist")
    public String admininquirylist(Model model){
        List<InquiryFormDto> InquiryList = inquiryService.getALLInquiryList();
        model.addAttribute("faqsearchdto",new FAQSearchDto());
        model.addAttribute("InquiryList", InquiryList);
        return "cscenter/adminvocList";
    }

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
