package com.gsitm.intern.controller;

import com.gsitm.intern.exception.ForbiddenException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ExceptionHandlingController {
    @GetMapping("/internalerror")
    public void internalerror() {
        throw new RuntimeException("500 Internal Error !!");
    }

    @GetMapping("/forbidden")
    public void forbidden() {
        throw new ForbiddenException("403 Forbidden !!");
    }
}
