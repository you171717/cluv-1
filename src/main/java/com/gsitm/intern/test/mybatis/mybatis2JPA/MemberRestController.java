package com.gsitm.intern.test.mybatis.mybatis2JPA;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member/test")
@RequiredArgsConstructor
public class MemberRestController {

    private final MemverService memberService;

    @GetMapping("/allUser")
    public ResponseEntity memberListAll(){
        return ResponseEntity.ok(memberService.memberListAll());
    }
}
