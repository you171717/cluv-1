package com.shop.dto;

import com.shop.constant.BidSearchSortColumn;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class BidSearchDto {

    private String searchQuery = "";

    private BidSearchSortColumn sortColumn = BidSearchSortColumn.REG_TIME;

}
