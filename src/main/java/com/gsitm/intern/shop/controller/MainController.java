package com.gsitm.intern.shop.controller;

import com.gsitm.intern.shop.dto.ItemSearchDto;
import com.gsitm.intern.shop.dto.MainItemDto;
import com.gsitm.intern.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Log
@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @GetMapping(value = "/")
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "main";
    }

    
//  신품페이지 겟맵핑
    @GetMapping(value = "/nitem")
    public String newitem(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){
        log.info("================/newitem 로 들어왔음============");
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainItemDto> items = itemService.getNewItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "newitem";
    }

//   중고상품페이지 겟맵핑
    @GetMapping(value = "/uitem")
    public String useitem(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){
        log.info("================/Useitem 로 들어왔음============");
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainItemDto> items = itemService.getOldItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "useitem";
    }

}