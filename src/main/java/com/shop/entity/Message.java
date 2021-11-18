package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Message extends BaseTimeEntity{

    @Id
    @Column(name ="sms_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sms_id;

    // 문자 메시지 엔티티 다대일 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String send_name;

    private String recv_name;

    private String phone;

    private String context;

}
