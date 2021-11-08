package com.gsitm.intern.controller;

import com.gsitm.intern.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "/test")
    public UserDto test() {
        UserDto userDto = new UserDto();
        userDto.setAge(20);
        userDto.setName("Soyoung");

        return userDto;
    }

}
