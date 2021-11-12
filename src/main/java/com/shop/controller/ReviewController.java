package com.shop.controller;

import com.shop.dto.*;
import com.shop.entity.Item;
import com.shop.entity.OrderItem;
import com.shop.service.OrderService;
import com.shop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final OrderService orderService;
    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public String reviews(@PathVariable("page") Optional<Integer> page, Principal principal, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
        Page<OrderHistDto> ordersHistDtoList = orderService.getOrderList(principal.getName(), pageable);

        model.addAttribute("orders", ordersHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "review/reviewList";
    }

    @GetMapping("/reviews/new/{itemId}")
    public String reviewForm(@PathVariable("itemId") Long orderItemId ,Model model){
        model.addAttribute("reviewDto", new ReviewDto());
        return "review/reviewWrite";
    }

//    @PostMapping("/reviews/new/{orderItemId}")
//    public String reviewNew(@Valid ReviewDto reviewDto, @Valid Item item, OrderItem orderItem) {
//        Review review = Review.createReview(reviewDto, item, orderItem);
//        reviewService.saveReview(review);
//        return "redirect:/";
//    }
//
//    @GetMapping("/reviews/update")
//    public String reviewUpdate(ReviewDto reviewDto){
//        reviewService.updateReview(reviewDto);
//        return "review/reviewWrite";
//    }



}
