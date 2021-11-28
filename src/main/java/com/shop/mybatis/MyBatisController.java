package com.shop.mybatis;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/item/best")
@RequiredArgsConstructor
public class MyBatisController {

    private final MyBatisService myBatisService;

    @GetMapping("/bestItem")
    public String findAll(Model model) {
        List<MybatisBestItemDto> items = myBatisService.getBestItems();
        model.addAttribute("items", items);
        return "item/bestItem";
    }
}
