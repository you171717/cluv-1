package com.gsitm.intern.entity;

import com.gsitm.intern.constant.AuthCodeExpireStatus;
import com.gsitm.intern.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.annotations.Many;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auth_code")
@Getter
@Setter
@ToString
public class AuthCode extends BaseEntity {
    @Id
    @Column(name = "auth_code_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private String content;

    private LocalDateTime expireTime;

    @Enumerated(EnumType.STRING)
    private AuthCodeExpireStatus authCodeExpireStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;



}
