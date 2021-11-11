package com.shop.repository;

import com.shop.dto.InquiryFormDto;
import com.shop.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry,Long> {

    @Query("select i from Inquiry i where i.createdBy like %:createdBy%")
    List<Inquiry> findbyCreateBy(@Param("createdBy")String createdBy);


}
