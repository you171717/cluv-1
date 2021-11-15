package com.shop.service;

import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

//    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


//    public Review saveReview(Review review){
//        return reviewRepository.save(review);
//    }
//
//    public Long updateReview(ReviewDto reviewDto){
//        Review review = reviewRepository.findById(reviewDto.getId()).orElseThrow(EntityNotFoundException::new);
//        review.updateReview(reviewDto);
//
//        return review.getId();
//    }


}
