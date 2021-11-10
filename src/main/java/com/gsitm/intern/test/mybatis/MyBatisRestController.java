package com.gsitm.intern.test.mybatis;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description :
 *
 * @author leejinho
 * @version 1.0
 */

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class MyBatisRestController {

    private final MyBatisService myBatisService;

    @GetMapping("/mybatis")
    public ResponseEntity findAll() {
        return ResponseEntity.ok(myBatisService.findAll());
    }

}
