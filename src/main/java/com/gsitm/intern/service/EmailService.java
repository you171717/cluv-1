package com.gsitm.intern.service;

import com.gsitm.intern.dto.CartOrderDto;
import com.gsitm.intern.dto.MemberFormDto;
import com.gsitm.intern.dto.OrderDto;
import com.gsitm.intern.entity.*;
import com.gsitm.intern.repository.AuthTokenRepository;
import com.gsitm.intern.repository.ItemRepository;
import com.gsitm.intern.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final AuthTokenService authTokenService;
    private final ItemRepository itemRepository;
    private final AuthTokenRepository authTokenRepository;
    private final OrderRepository orderRepository;

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
        String text = "[GS SHOP] 주문 상품 내역입니다.\n" + "주문 상품 : " + item.getItemNm() + "\n주문 수량 : " + orderDto.getCount() +
                "\n주문 금액 : " + item.getPrice() * orderDto.getCount() + "원 입니다.\n";
        sendEmail(email, subject, text);
    }
    //장바구니 주문 시 이메일 전송 메소드
    public void sendCartOrderEmail(String email, Long orderId){

        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        String subject = "주문 상품 내역입니다.";
        String text = "[GS SHOP] 주문 상품 내역입니다.\n\n";

        //TODO. 장바구니 데이터 불러오기
        for(OrderItem orderItem : order.getOrderItems()) {
            text += orderItem.getItem().getItemNm()
                    + "(" + orderItem.getItem().getPrice()
                    + " 원) x " + orderItem.getCount() + "개\n";
        }

        text += "\n주문 금액 : " + order.getTotalPrice()+ "원 입니다.\n";

        sendEmail(email, subject, text);
    }

    public void sendPasswordEmail(String email){
        //url에 get방식으로 토큰을 붙인다
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MemberFormDto memberFormDto = new MemberFormDto();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(email); // 메일 수신자
            mimeMessageHelper.setSubject("비밀번호를 변경해주세요"); // 메일 제목
            mimeMessageHelper.setText(
                    "<a href='http://localhost:8080/members/updatePassword?code=" + authTokenService.createToken(email).getCode() + "'>비밀번호 변경페이지</a>", true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}