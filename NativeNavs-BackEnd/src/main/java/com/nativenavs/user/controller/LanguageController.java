package com.nativenavs.user.controller;

import com.nativenavs.user.service.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/language")
@RequiredArgsConstructor
@CrossOrigin("*") //
@Tag(name = "언어 API", description = "전체 언어 조회")
public class LanguageController {

    // DI -----------------------------------------------------------------------------------------------------------------
    private final LanguageService languageService;

    // -----------------------------------------------------------------------------------------------------------------


    @Operation(summary = "전체 언어 조회 API", description = "전체 언어 목록을 조회")
    @GetMapping
    public ResponseEntity<?> searchAllLanguage() {
        try{
            return new ResponseEntity<>(languageService.searchAllLanguage(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 입니다");
        }

    }
}
