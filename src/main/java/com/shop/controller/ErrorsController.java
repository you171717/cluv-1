package com.shop.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorsController implements ErrorController {

    private static String IMAGE_PATH = "static/images/error/";

    @ExceptionHandler(Throwable.class)
    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String statusMsg = status.toString();

        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(statusMsg));

        model.addAttribute("msg", statusMsg + " " + httpStatus.getReasonPhrase());
        model.addAttribute("src", IMAGE_PATH + "error1.png");

        return "errors/error";
    }

}
