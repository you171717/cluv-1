package com.gsitm.intern.controller;

import com.gsitm.intern.dto.*;
import com.gsitm.intern.entity.ItemDiscount;
import com.gsitm.intern.service.ItemDiscountService;
import com.gsitm.intern.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemDiscountController {
    private final ItemDiscountService itemDiscountService;

    @GetMapping("/itemDiscount")
    public String itemDiscount(Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<ItemDiscountDto> items = itemDiscountService.getDiscountItemPage(pageable);

        String discountTime = "";
        switch (LocalTime.now().getHour()) {
            case 0: case 1: case 2: case 3: discountTime = "00:00:00 ~ 03:59:59"; break;
            case 4: case 5: case 6: case 7: discountTime = "04:00:00 ~ 07:59:59"; break;
            case 8: case 9: case 10: case 11: discountTime = "08:00:00 ~ 11:59:59"; break;
            case 12: case 13: case 14: case 15: discountTime = "12:00:00 ~ 15:59:59"; break;
            case 16: case 17: case 18: case 19: discountTime = "16:00:00 ~ 19:59:59"; break;
            case 20: case 21: case 22: case 23: discountTime = "20:00:00 ~ 23:59:59"; break;
        }

        model.addAttribute("items", items);
        model.addAttribute("maxPage", 5);
        model.addAttribute("discountTime", discountTime);

        return "item/itemDiscount";
    }

    @GetMapping("/admin/item/DeleteDiscount")
    public String itemDiscountDelete(@RequestParam(value = "itemId") Long id, Model model, HttpServletResponse response) throws Exception {
        ItemDiscountPopupDto itemDiscount = itemDiscountService.getItemDiscountPopup(id);
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        if(itemDiscount == null) {
            out.println("<script>alert('해당 상품은 할인정보가 등록되지 않았습니다.'); history.go(-1);</script>");
            out.flush();
            return null;
        }

        itemDiscountService.deleteDiscount(itemDiscount.getId());
        out.println("<script>alert('할인정보 삭제를 성공했습니다.'); history.go(-1);</script>");
        out.flush();
        return null;
    }

    @GetMapping("/admin/item/discountPopup")
    public String itemDiscountPopup(@RequestParam(value = "itemId", required = false) Long id, Model model) {
        ItemDiscountPopupDto itemDiscount = itemDiscountService.getItemDiscountPopup(id);

        model.addAttribute("itemDiscountPopupDto", Objects.requireNonNullElseGet(itemDiscount, ItemDiscountPopupDto::new));
        model.addAttribute("itemId", id);

        return "item/itemDiscountPopup";
    }

    @PostMapping("/admin/item/discountPopup")
    public String itemDiscountPopupUpdate(@Valid ItemDiscountPopupDto itemDiscountPopupDto, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            return "item/itemDiscountPopup";
        }

        System.out.println(itemDiscountPopupDto.getItemId());
        ItemDiscountPopupDto itemDiscount = itemDiscountService.getItemDiscountPopup(itemDiscountPopupDto.getItemId());
        if(itemDiscount != null) {
            System.out.println("할인정보 수정");
            try {
                itemDiscountService.updateDiscount(itemDiscountPopupDto);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "할인 정보 수정 중 에러가 발생했습니다.");
                return "item/itemDiscountPopup";
            }
        } else {
            System.out.println("할인정보 등록");
            try {
                itemDiscountService.saveDiscount(itemDiscountPopupDto);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "할인 정보 등록 중 에러가 발생했습니다.");
                return "item/itemDiscountPopup";
            }
        }
        model.addAttribute("successMessage", "할인 정보 저장을 성공했습니다.");
        return "item/itemDiscountPopup";
    }

    @GetMapping(value = "/itemDiscount/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId, HttpServletResponse response) throws Exception {
        ItemDiscountDtlDto itemDiscountDtlDto = itemDiscountService.getDiscountItemDtl(itemId);
        String discountTime = itemDiscountDtlDto.getDiscountTime();
        String discountEndTime = "";

        switch (LocalTime.now().getHour()) {
            case 0: case 1: case 2: case 3: discountEndTime = "03:59:59"; break;
            case 4: case 5: case 6: case 7: discountEndTime = "07:59:59"; break;
            case 8: case 9: case 10: case 11: discountEndTime = "11:59:59"; break;
            case 12: case 13: case 14: case 15: discountEndTime = "15:59:59"; break;
            case 16: case 17: case 18: case 19: discountEndTime = "19:59:59"; break;
            case 20: case 21: case 22: case 23: discountEndTime = "23:59:59"; break;
        }

        if (discountTime.contains(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) && discountTime.contains(discountEndTime)) {
            model.addAttribute("item", itemDiscountDtlDto);
            return "item/itemDiscountDtl";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('해당 상품은 현재 할인 기간이 아닙니다.'); history.go(-1);</script>");
            out.flush();
            return null;
        }
    }
}
