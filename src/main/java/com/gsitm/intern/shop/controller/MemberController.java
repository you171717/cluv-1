package com.gsitm.intern.shop.controller;

import com.gsitm.intern.shop.dto.MemberFormDto;
import com.gsitm.intern.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller
@RequiredArgsConstructor

public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }
}
