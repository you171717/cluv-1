package com.gsitm.intern.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ItemDto {
    private Long id;
    private String itemNm;
    private Integer price;
    private String itemDetail;
    private String sellStatus;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}
