package com.shop.controller;

import com.shop.dto.CommentFormDto;
import com.shop.dto.InquiryFormDto;
import com.shop.entity.Comment;
import com.shop.entity.Inquiry;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.service.CommentService;
import com.shop.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final InquiryService inquiryService;


    @PostMapping(value = "/admin/cscenter/voclist/{id}")
    public String comment(@PathVariable("id")Long inquiryid,@Valid CommentFormDto commentFormDto, BindingResult bindingResult, Model model){
        commentService.saveComment(inquiryid,commentFormDto);
        return "redirect:/admin/cscenter/voclist/{id}";

    }
}
