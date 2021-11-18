package com.gsitm.intern.controller;

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

    @RequestMapping(value = "/error")
    public String error(HttpServletRequest request, Model model){
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(statusCode != null) {
            int status = Integer.valueOf(statusCode.toString());

            if(status == HttpStatus.UNAUTHORIZED.value()) { //401에러
                model.addAttribute("message", "로그인 상태를 확인해 주세요.");
            }else if(status == HttpStatus.FORBIDDEN.value()) { //403에러
                model.addAttribute("message", "페이지를 볼 수 있는 접근 권한이 없습니다.");
            }else if(status == HttpStatus.NOT_FOUND.value()) { //404에러
                model.addAttribute("message", "요청한 페이지를 찾을 수 없습니다.");
            }else if(status == HttpStatus.INTERNAL_SERVER_ERROR.value()){  //500에러
                model.addAttribute("message", "서버 에러입니다.");
            }
        }
        return "error/error";
    }
}