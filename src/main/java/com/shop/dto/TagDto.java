package com.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TagDto {

    private String tagNm;

    private int totalSell;

    @QueryProjection
    public TagDto(String tagNm, Integer totalSell){
        this.tagNm= tagNm;
        this.totalSell = totalSell;
    }

    private List<TagDto> tagDtoList = new ArrayList<>();
}
