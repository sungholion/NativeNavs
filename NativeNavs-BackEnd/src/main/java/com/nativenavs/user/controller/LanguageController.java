package com.nativenavs.user.controller;

import com.nativenavs.user.service.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/language")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "언어 API", description = "전체 언어 조회")
public class LanguageController {

    // DI --------------------------------------------------------------------------------------------------------------

    private final LanguageService languageService;

    // API -------------------------------------------------------------------------------------------------------------

    @Operation(summary = "전체 언어 조회 API", description = "전체 언어 목록을 조회")
    @GetMapping("search/all")
    public ResponseEntity<?> searchAllLanguage() {
        log.info("전체 언어 목록 조회 요청");
        try{
            var languages = languageService.searchAllLanguage();
            log.info("전체 언어 목록 조회 성공: {}개의 언어 조회됨", languages.size());
            return new ResponseEntity<>(languages, HttpStatus.OK);
        } catch (Exception e){
            log.error("전체 언어 목록 조회 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 입니다");
        }
    }
}

/*
    리팩토링 사항
    1. User와 Language Mapping 하기 - Language List로 입력받아 저장하기
    2. 1에 따른 CRUD API 만들

 */