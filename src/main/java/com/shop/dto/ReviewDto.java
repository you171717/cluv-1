package com.shop.dto;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class ReviewDto {

    private Long id;

    @NotBlank(message = "후기 작성은 필수 입력 값입니다.")
    private String comment;

    private static ModelMapper modelMapper = new ModelMapper();

//    public Review createReview(){
//        return modelMapper.map(this, Review.class);
//    }

}
