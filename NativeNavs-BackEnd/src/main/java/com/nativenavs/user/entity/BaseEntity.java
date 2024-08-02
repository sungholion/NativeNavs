package com.nativenavs.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass   // @Entity 대신 사용. 부모 클래스는 테이블과 매핑하지 않고, 오로지 부모 클래스를 상속 받는 자식 클래스에게 부모 클래스가 가지는 칼럼만 매핑정보로 제공하고 싶을때
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseEntity {
    @CreatedDate
    @Column(updatable = false, name = "created_at", nullable = false)
    private LocalDateTime createdAt;  // 회원가입 일시

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // 회원 정보 수정 일시
}
