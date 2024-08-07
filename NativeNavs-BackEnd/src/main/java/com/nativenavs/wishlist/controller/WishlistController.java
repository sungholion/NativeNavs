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
@CrossOrigin("*")
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
    , @Parameter(description = "위시에 등록할 여행지 ID", example = "1") @RequestParam int tourId
    ){
        try {
            int userIdFromEmail = getUserIdFromJWT(token);
            wishlistService.addWishlist(userIdFromEmail, tourId);
            return ResponseEntity.ok("위시리스트 등록 성공");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("위시리스트 등록 실패");
        }
    }

    //조회
    @Operation(summary = "위시리스트 조회 API", description = "위시리스트에서 조회할때 사용하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.", content = @Content(mediaType = "application/json"))
    @GetMapping
    public ResponseEntity<?> wishlistFind( @RequestHeader("Authorization") String token){
        try {
            int userIdFromEmail = getUserIdFromJWT(token);
            List<TourDTO> wishlist = wishlistService.findWishlist(userIdFromEmail);
            return ResponseEntity.ok(wishlist);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("위시리스트 조회 실패");
        }
    }

    //삭제
    @Operation(summary = "위시리스트 삭제 API", description = "위시리스트에서 삭제할때 사용하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "투어를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    @DeleteMapping("/{tourId}")
    public ResponseEntity<?> wishlistRemvove( @RequestHeader("Authorization") String token,
                                              @Parameter(description = "투어 ID", required = true, example = "1") @PathVariable int tourId ){
        try{
            int userIdFromEmail = getUserIdFromJWT(token); // JWT에서 이���일 받아 id로 ��환
            wishlistService.removeWishlist(userIdFromEmail, tourId);
            return ResponseEntity.ok("위시리스트 삭제 성공");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("위시리스트 삭제 실패");
        }
    }

    //불린체크
    @Operation(summary = "위시리스트 체크 API", description = "특정투어가 위시리스트에 있는지 체크하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "투어를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    @GetMapping("/check/{tourId}")
    public ResponseEntity<?> isWishlistCheck( @RequestHeader("Authorization") String token,
                                              @Parameter(description = "투어 ID", required = true, example = "1") @PathVariable int tourId){

        try {
            int userIdFromEmail = getUserIdFromJWT(token); // JWT에서 이�일 받아 id
            boolean isWishlist = wishlistService.checkIsWishlist(userIdFromEmail, tourId);
            return ResponseEntity.ok(isWishlist);
        }
        catch (Exception e){
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("위시리스트  체크 실패");
        }
    }


    //JWT에서 이메일 받아 id로 치환
    private int getUserIdFromJWT(String token){
        String jwtToken = token.replace("Bearer ", ""); // "Bearer " 부분 제거
        String email = JwtTokenProvider.getEmailFromToken(jwtToken);
       return userService.changeEmailToId(email);
    }



}
