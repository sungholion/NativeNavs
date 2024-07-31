package com.nativenavs.user.mapper;

import com.nativenavs.user.model.Language;
import com.nativenavs.user.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LanguageMapper {
    public List<Language> searchAllLanguage();
}
