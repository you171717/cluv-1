//package com.gsitm.intern.controller;
//
////import com.gsitm.intern.service.EmailSendService;
//import com.gsitm.intern.dto.MemberFormDto;
//import com.gsitm.intern.entity.EmailMessage;
//import com.gsitm.intern.service.EmailService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import java.io.UnsupportedEncodingException;
//
//@RestController
//@RequiredArgsConstructor
//public class EmailController {
//
//    private final EmailService emailService;
//
//    @RequestMapping("/sendEmail")
//    public ResponseEntity sendMail() {
//        MemberFormDto memberFormDto;
//        EmailMessage emailMessage = EmailMessage.builder()
//                .to("sky585456@naver.com")
//                .subject("테스트 메일 제목")
//                .message("테스트 메일 본문")
//                .build();
//        emailService.sendMail(memberFormDto, emailMessage);
//    }
//}