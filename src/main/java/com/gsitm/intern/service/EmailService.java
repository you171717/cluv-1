package com.gsitm.intern.service;

import com.gsitm.intern.dto.CartOrderDto;
import com.gsitm.intern.dto.MemberFormDto;
import com.gsitm.intern.dto.OrderDto;
import com.gsitm.intern.entity.AuthToken;
import com.gsitm.intern.entity.Item;
import com.gsitm.intern.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final AuthTokenService authTokenService;
    private final ItemRepository itemRepository;

    @Async
    public void sendEmail(String to, String subject, String text) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MemberFormDto memberFormDto = new MemberFormDto();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(to); // 메일 수신자
            mimeMessageHelper.setSubject(subject); // 메일 제목
            mimeMessageHelper.setText(text, false); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEmailAuthCode(String email) {
        String subject = "인증코드입니다.";
        AuthToken authToken = authTokenService.getToken(email);
        String text = "인증코드는 " + authToken.getCode() + "입니다.";
        sendEmail(email, subject, text);
    }

    public void sendOrderEmail(String email, OrderDto orderDto){
        String subject = "주문 상품 내역입니다.";
        Item item = itemRepository.findById(orderDto.getItemId()).
                orElseThrow(EntityNotFoundException::new);
        String text = "주문 상품 : " + item.getItemNm() + "\n주문 수량 : " + orderDto.getCount() +
                        "\n주문 금액 : " + item.getPrice() * orderDto.getCount() + "입니다.";
        sendEmail(email, subject, text);
    }

    public void sendCartOrderEmail(String email, CartOrderDto cartOrderDto){
        String subject = "주문 상품 내역입니다.";
        Item item = itemRepository.findById(cartOrderDto.getCartItemId()).
                orElseThrow(EntityNotFoundException::new);
        String text = "주문 상품 : " + item.getItemNm() + "\n주문 상품 종류 개수 : " + cartOrderDto.getCartOrderDtoList().size()
                +"입니다.";
        sendEmail(email, subject, text);
    }

    public void sendPasswordEmail(String email){
        //url에 get방식으로 토큰을 붙인다

    }

}