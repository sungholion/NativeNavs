package com.nativenavs.user.service;

import com.nativenavs.user.mapper.LanguageMapper;
import com.nativenavs.user.mapper.UserMapper;
import com.nativenavs.user.model.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageMapper languageMapper;

    @Override
    public List<Language> searchAllLanguage() {
        return languageMapper.searchAllLanguage();
    }
}
