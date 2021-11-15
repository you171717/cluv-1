package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})     // auditing 적용
@MappedSuperclass               // 공통 매핑 정보
@Getter @Setter
public class BaseTimeEntity {

    @CreatedDate                // 엔티티가 생성 시간 자동 저장
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate           // 엔티티 수정 시간 자동 저장
    private LocalDateTime updateTime;

}
