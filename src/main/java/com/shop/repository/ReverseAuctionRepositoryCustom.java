package com.shop.repository;

import com.shop.dto.ReverseAuctionDto;
import com.shop.dto.ReverseAuctionSearchDto;
import com.shop.entity.ReverseAuction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReverseAuctionRepositoryCustom {

    Page<ReverseAuctionDto> getAdminReverseAuctionPage(ReverseAuctionSearchDto reverseAuctionSearchDto, Pageable pageable);

    Page<ReverseAuctionDto> getUserReverseAuctionPage(ReverseAuctionSearchDto reverseAuctionSearchDto, Pageable pageable);

}
