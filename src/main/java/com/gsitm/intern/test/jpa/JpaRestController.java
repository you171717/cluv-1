package com.gsitm.intern.test.jpa;

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
@RequestMapping("/api/test/jpa")
@RequiredArgsConstructor
public class JpaRestController {

    private final JpaService jpaService;

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok(jpaService.listAll());
    }
}
