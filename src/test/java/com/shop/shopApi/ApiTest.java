package com.shop.shopApi;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class ApiTest {

    @Test
    @DisplayName("test")
    public void search() {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "KpepXFXCaXQ7GHnE6X3T");
        headers.add("X-Naver-Client-Secret", "qlcG3q_XkO");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        // 넘겨받은 query로 검색 요청
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query=노트북", HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);


    }


}
