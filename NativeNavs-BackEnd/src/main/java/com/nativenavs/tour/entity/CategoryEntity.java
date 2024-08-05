package com.nativenavs.tour.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name="category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "english_name", nullable = false)
    private String englishName;

    @Column(name = "category_image", nullable = false)
    private String categoryImage;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<TourCategoryEntity> tourCategories;
}