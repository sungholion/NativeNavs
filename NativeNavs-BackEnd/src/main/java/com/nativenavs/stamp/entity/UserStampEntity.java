package com.nativenavs.stamp.entity;

import com.nativenavs.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stamp")
public class UserStampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userStampId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stamp_id", nullable = false)
    private StampEntity stamp;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate; // 리뷰 작성 일시



}
