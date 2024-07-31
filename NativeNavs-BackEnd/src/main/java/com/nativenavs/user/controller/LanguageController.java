package com.nativenavs.user.controller;

import com.nativenavs.user.model.Language;
import com.nativenavs.user.model.User;
import com.nativenavs.user.service.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/language")
@CrossOrigin("*") //
@Tag(name = "language API", description = "언어 관련 API ")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @Operation(summary = "전체 언어 조회 API", description = "전체 언어 목록을 조회합니다")
    @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @GetMapping
    public ResponseEntity<?> searchAllLanguage() {
        return new ResponseEntity<>(languageService.searchAllLanguage(), HttpStatus.OK);
    }
}
