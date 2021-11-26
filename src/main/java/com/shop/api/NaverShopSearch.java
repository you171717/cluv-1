package com.shop.api;

import com.shop.dto.CategoryDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class NaverShopSearch {
    public List<CategoryDto> search(String query) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "KpepXFXCaXQ7GHnE6X3T");
        headers.add("X-Naver-Client-Secret", "qlcG3q_XkO");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        // 넘겨받은 query로 검색 요청
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query=" + query,
                HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
//        System.out.println("Response status: " + status);
//        System.out.println(response);

        return fromJSONtoItems(response.toString());
    }

    public List<CategoryDto> fromJSONtoItems(String result) {
        JSONObject rjson = new JSONObject(result);
        JSONArray items = rjson.getJSONArray("items");
        List<CategoryDto> ret = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            JSONObject itemJson = items.getJSONObject(i);
            CategoryDto categoryDto = new CategoryDto(itemJson);
            ret.add(categoryDto);
        }
        for (int i=0; i < ret.size(); i++) {

            System.out.println(ret.get(i).getTitle());
            System.out.println(ret.get(i).getLprice());
        }
        return ret;
    }


    public List<CategoryDto> search2(String query) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "KpepXFXCaXQ7GHnE6X3T");
        headers.add("X-Naver-Client-Secret", "qlcG3q_XkO");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        // 넘겨받은 query로 검색 요청
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query=" + query,
                HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
//        System.out.println("Response status: " + status);
//        System.out.println(response);

        return fromJSONtoItems2(response.toString());
    }

    public List<CategoryDto> fromJSONtoItems2(String result) {
        JSONObject rjson = new JSONObject(result);
        JSONArray items = rjson.getJSONArray("items");
        List<CategoryDto> ret = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            JSONObject itemJson = items.getJSONObject(i);
            CategoryDto categoryDto = new CategoryDto(itemJson);
            ret.add(categoryDto);
        }


        for (int i=0; i < ret.size(); i++) {

            System.out.println(ret.get(i).getTitle());
            System.out.println(ret.get(i).getLprice());
        }


        return ret;
    }



}
