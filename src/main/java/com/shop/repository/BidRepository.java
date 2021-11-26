package com.shop.repository;

import com.shop.entity.Bid;
import com.shop.entity.ReverseAuction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long>, BidRepositoryCustom {

    Bid findByReverseAuctionAndApprovedYn(ReverseAuction reverseAuction, String approvedYn);

}
