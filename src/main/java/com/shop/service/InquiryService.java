package com.shop.service;

import com.shop.dto.FAQDto;
import com.shop.dto.InquiryFormDto;
import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemImgDto;
import com.shop.entity.FAQ;
import com.shop.entity.Inquiry;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.InquiryRepository;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    @Transactional
    public Inquiry saveInquiry(InquiryFormDto inquiryFormDto){
        return inquiryRepository.save(inquiryFormDto.toEntity());
    }


    @Transactional
    public List<InquiryFormDto> getInquiryList(String email) {
        List<Inquiry> inquiryList = inquiryRepository.findbyCreateBy(email);
        List<InquiryFormDto> inquiryDtoList = new ArrayList<>();

        for(Inquiry inquiry : inquiryList) {
            InquiryFormDto inquiryFormDto=InquiryFormDto.builder()
                    .id(inquiry.getId())
                    .title(inquiry.getTitle())
                    .content(inquiry.getContent())
                    .regTime(inquiry.getRegTime())
                    .build();
            inquiryDtoList.add(inquiryFormDto);
        }

        return inquiryDtoList;
    }

    @Transactional
    public InquiryFormDto getInquiryDtl(Long itemId){
        Inquiry inquiry = inquiryRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        InquiryFormDto inquiryFormDto=InquiryFormDto.of(inquiry);
        return inquiryFormDto;
    }

    @Transactional
    public Long updateInquiry(InquiryFormDto inquiryFormDto){
        Inquiry inquiry=inquiryRepository.findById(inquiryFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        inquiry.updateInquiry(inquiryFormDto);
        return inquiry.getId();
    }

    @Transactional
    public void deleteInquiry(Long id){
        inquiryRepository.deleteById(id);
    }

    @Transactional
    public List<InquiryFormDto> getALLInquiryList() {
        List<Inquiry> inquiryList = inquiryRepository.findAll();
        List<InquiryFormDto> inquiryDtoList = new ArrayList<>();

        for(Inquiry inquiry : inquiryList) {
            InquiryFormDto inquiryFormDto=InquiryFormDto.builder()
                    .id(inquiry.getId())
                    .title(inquiry.getTitle())
                    .content(inquiry.getContent())
                    .regTime(inquiry.getRegTime())
                    .createdBy(inquiry.getCreatedBy())
                    .build();
            inquiryDtoList.add(inquiryFormDto);
        }

        return inquiryDtoList;
    }



}
