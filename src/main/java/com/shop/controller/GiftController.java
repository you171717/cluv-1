package com.shop.controller;

import com.shop.dto.OrderDto;
import com.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class GiftController {

    private final OrderService orderService;

    @ResponseBody
    @PostMapping(value = "/gift")
    public ResponseEntity gift(@RequestBody @Valid OrderDto orderDto,
                             BindingResult bindingResult, Principal principal) throws Exception { // 휴대폰 문자보내기

        String api_key = "NCSMCDCTEEVBPIN2";
        String api_secret = "J09RZVDL04D93B4TRE2KGL77JK3T864S";
        Message coolsms = new Message(api_key,api_secret);

        HashMap<String, String> params = new HashMap<String, String>();

            if (bindingResult.hasErrors()) {                // orderDTO 객체에 데이터 바인딩시 에러 검사
                StringBuilder sb = new StringBuilder();
                List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                for (FieldError fieldError : fieldErrors) {
                    sb.append(fieldError.getDefaultMessage());
                }
                return new ResponseEntity<String>(sb.toString(),
                        HttpStatus.BAD_REQUEST);           // 에러 정보를 ResponseEntity 객체에 담아서 반환
            }

            String email = principal.getName();           // principal 객체에서 현재 로그인한 회원의 이메일 정보 조회
            Long orderId;

            try{
                orderId = orderService.gift(orderDto, email);          // 주문 로직 호출
            }catch(Exception e){
                return new ResponseEntity<String>(e.getMessage(),
                        HttpStatus.BAD_REQUEST);
            }


        params.put("to", ""); // 수신번호
        params.put("from", orderDto.getFrom()); // 수신번호
//        params.put("from", "01053636153"); // 수신번호
        params.put("text", orderDto.getText()); // 문자내용
//        params.put("text", "test"); // 문자내용
        params.put("type", "sms"); // 문자 타입
        params.put("app_version", "JAVA SDK v2.2"); // application name and version

        System.out.println(params);

//        JSONObject result = coolsms.send(params); // 보내기&전송결과받기


        System.out.println("===============================");
//        System.out.println("result : "+result);
        System.out.println("result : OK ");
        System.out.println("===============================");

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);    // HTTP 응답 상태 코드 반환
    }
}
