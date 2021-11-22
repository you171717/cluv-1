package com.shop.service;

import com.shop.dto.ReverseAuctionDto;
import com.shop.dto.ReverseAuctionFormDto;
import com.shop.dto.ReverseAuctionSearchDto;
import com.shop.entity.ReverseAuction;
import com.shop.mapstruct.ReverseAuctionFormMapper;
import com.shop.repository.ReverseAuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ReverseAuctionService {

    private final ReverseAuctionFormMapper reverseAuctionFormMapper;

    private final ReverseAuctionRepository reverseAuctionRepository;

    public ReverseAuction saveReverseAuction(ReverseAuctionFormDto reverseAuctionFormDto) {
        ReverseAuction reverseAuction = reverseAuctionFormMapper.toEntity(reverseAuctionFormDto);

        reverseAuction.setStartTime(LocalDateTime.now());

        return reverseAuctionRepository.save(reverseAuction);
    }

    @Transactional(readOnly = true)
    public ReverseAuctionFormDto getReserveAuctionDtl(Long id) {
        ReverseAuction reverseAuction = reverseAuctionRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return reverseAuctionFormMapper.toDto(reverseAuction);
    }

    public Long updateReserveAuction(ReverseAuctionFormDto reverseAuctionFormDto) {
        ReverseAuction reverseAuction = reverseAuctionRepository.findById(reverseAuctionFormDto.getId()).orElseThrow(EntityNotFoundException::new);

        reverseAuctionFormMapper.updateFromDto(reverseAuctionFormDto, reverseAuction);

        return reverseAuction.getId();
    }

    @Transactional(readOnly = true)
    public Page<ReverseAuctionDto> getAdminReverseAuctionPage(ReverseAuctionSearchDto reverseAuctionSearchDto, Pageable pageable) {
        return reverseAuctionRepository.getAdminReverseAuctionPage(reverseAuctionSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<ReverseAuctionDto> getUserReverseAuctionPage(ReverseAuctionSearchDto reverseAuctionSearchDto, Pageable pageable) {
        return reverseAuctionRepository.getUserReverseAuctionPage(reverseAuctionSearchDto, pageable);
    }

}
