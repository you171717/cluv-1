package com.shop.dto;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
public class CategoryDto {

    private String title;
    private int lprice;

    public CategoryDto(JSONObject itemJson) {
        this.title = itemJson.getString("title");
        this.lprice = itemJson.getInt("lprice");
    }


}
