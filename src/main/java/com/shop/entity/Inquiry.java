package com.shop.entity;

import com.shop.dto.InquiryFormDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="inquiry")
@Getter @Setter
@ToString
@NoArgsConstructor
public class Inquiry extends BaseEntity{

    @Id
    @Column(name = "inquiry_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;



    @Builder
    public Inquiry(Long id, String title, String content) {
        this.id=id;
        this.title = title;
        this.content = content;

    }

    public void updateInquiry(InquiryFormDto inquiryRepository){
        this.title=inquiryRepository.getTitle();
        this.content=inquiryRepository.getContent();
    }


}
