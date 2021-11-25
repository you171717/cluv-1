package com.shop.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class CustomErrorController implements ErrorController {



    private final String ERROR_404_PAGE_PATH = "error/404";
    private final String ERROR_500_PAGE_PATH = "error/500";
    private final String ERROR_ETC_PAGE_PATH = "error/error";

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request, Model model) {

//        System.out.println("뭘봐");
        // 에러 코드를 획득한다.
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        //에러 코드에 대한 상태 정보
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));

        if (status != null) {
            // HttpStatus와 비교해 페이지 분기를 나누기 위한 변수
            int statusCode = Integer.valueOf(status.toString());
            // 404 error
            if (statusCode == HttpStatus.NOT_FOUND.value()) {

                model.addAttribute("code", status.toString());
                model.addAttribute("msg", httpStatus.getReasonPhrase());
                model.addAttribute("timestamp", new Date());

                return "error/404";
            }
            // 500 error 서버에러
            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                return "error/500";
            }
        }
        // 정의 에러 이외 에러 처리
        return "error/error";
    }



}
