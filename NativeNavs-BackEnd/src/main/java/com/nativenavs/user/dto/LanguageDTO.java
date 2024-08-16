package com.nativenavs.user.dto;

import com.nativenavs.user.entity.LanguageEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LanguageDTO {
    private int id;
    private String name;

    public static LanguageDTO toLanguageDTO(LanguageEntity languageEntity) {
        LanguageDTO languageDTO = new LanguageDTO();
        languageDTO.setId(languageEntity.getId());
        languageDTO.setName(languageEntity.getName());
        return languageDTO;
    }
}
