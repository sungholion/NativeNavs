package com.nativenavs.chat.entity;

import com.nativenavs.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_room")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_room_id")
    private int userRoomId;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false)
    private RoomEntity room;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
}
