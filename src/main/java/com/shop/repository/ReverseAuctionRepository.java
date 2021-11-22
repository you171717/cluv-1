package com.shop.repository;

import com.shop.dto.ReverseAuctionSearchDto;
import com.shop.entity.ReverseAuction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReverseAuctionRepository extends JpaRepository<ReverseAuction, Long>, ReverseAuctionRepositoryCustom {

}
