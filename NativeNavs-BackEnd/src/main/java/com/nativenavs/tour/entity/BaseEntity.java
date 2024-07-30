package com.nativenavs.tour.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false, name = "created_at", nullable = false)
    private LocalDateTime createdAt;  // 투어 생성 일시

    @UpdateTimestamp
    @Column(insertable = false, name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;  // 투어 정보 수정 일시
}
