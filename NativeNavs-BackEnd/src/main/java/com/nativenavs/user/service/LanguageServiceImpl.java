package com.nativenavs.user.service;

import com.nativenavs.user.dto.LanguageDTO;
import com.nativenavs.user.entity.LanguageEntity;
import com.nativenavs.user.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageRepository languageRepository;



    @Override
    public List<LanguageDTO> searchAllLanguage() {
        List<LanguageEntity> languageEntities = languageRepository.findAll();
        return languageEntities.stream()
                .map(LanguageDTO::toLanguageDTO)
                .collect(Collectors.toList());
    }
}