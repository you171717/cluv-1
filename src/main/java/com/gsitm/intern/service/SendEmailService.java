package com.gsitm.intern.service;

import com.gsitm.intern.dto.MailDto;
import com.gsitm.intern.repository.MemberRepository;
import com.gsitm.intern.utils.EncryptionUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendEmailService {

    @Autowired
    MemberRepository memberRepository;

    private JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "dnwlswkd17@gmail.com";

    public void mailSend(MailDto mailDto) {
        System.out.println("이메일 전송 완료!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(SendEmailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        javaMailSender.send(message);
    }
}
