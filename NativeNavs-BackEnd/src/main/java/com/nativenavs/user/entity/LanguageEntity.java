package com.nativenavs.user.entity;

import com.nativenavs.user.dto.LanguageDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name ="language")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String name;

    public static LanguageEntity toSaveEntity(LanguageDTO languageDTO){
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setId(languageDTO.getId());
        languageEntity.setName(languageDTO.getName());

        return languageEntity;
    }
}
