package com.nativenavs.wishlist.controller;

import com.nativenavs.auth.jwt.JwtTokenProvider;
import com.nativenavs.tour.dto.TourDTO;
import com.nativenavs.user.service.UserService;
import com.nativenavs.wishlist.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@Tag(name = "wishlist API", description = "wishlist")
public class WishlistController {
    private final WishlistService wishlistService;
    public WishlistController(WishlistService wishlistService){
        this.wishlistService = wishlistService;

    }

    @Autowired
    private UserService userService;


    @Operation(summary = "위시리스트 등록 API", description = "원하는 투어를 위시리스트에 등록할때 사용하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.", content = @Content(mediaType = "application/json"))
    @PostMapping
    public ResponseEntity<?> wishlistRegister( @RequestHeader("Authorization") String token
    , @Parameter(description = "위시에 등록할 여행지 ID", example = "1") @RequestBody int tourId
    ){
        try {
            String jwtToken = token.replace("Bearer ", ""); // "Bearer " 부분 제거
            String email = JwtTokenProvider.getEmailFromToken(jwtToken);
            int userIdFromEmail = userService.changeEmailToId(email);
            wishlistService.addWishlist(userIdFromEmail, tourId);
            return ResponseEntity.ok("위시리스트 등록 성공");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("위시리스트 등록 실패");
        }
    }

    //조회


    //삭제
    @Operation(summary = "위시리스트 삭제 API", description = "위시리스트에서 삭제할때 사용하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "투어를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    @DeleteMapping("/{tourId}")
    public ResponseEntity<?> wishlistRemvove( @RequestHeader("Authorization") String token,
                                              @Parameter(description = "투어 ID", required = true, example = "1") @PathVariable int tourId ){
        try{
            String jwtToken = token.replace("Bearer ", ""); // "Bearer " 부분 제거
            String email = JwtTokenProvider.getEmailFromToken(jwtToken);
            int userIdFromEmail = userService.changeEmailToId(email);
            wishlistService.removeWishlist(userIdFromEmail, tourId);
            return ResponseEntity.ok("위시리스트 삭제 성공");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("위시리스트 삭제 실패");
        }
    }

    //불린체크




}
