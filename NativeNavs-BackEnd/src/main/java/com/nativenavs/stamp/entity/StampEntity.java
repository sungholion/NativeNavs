package com.nativenavs.stamp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stamp")
public class StampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

//    @OneToMany(mappedBy = "stamp", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<> images; // 리뷰 이미지
//

}



