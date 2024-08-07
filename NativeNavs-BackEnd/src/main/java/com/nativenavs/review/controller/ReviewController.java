package com.nativenavs.review.controller;

import com.nativenavs.auth.jwt.JwtTokenProvider;
import com.nativenavs.review.dto.GuideReviewDTO;
import com.nativenavs.review.dto.ReviewRequestDTO;
import com.nativenavs.review.dto.TourReviewDTO;
import com.nativenavs.review.dto.TravReviewDTO;
import com.nativenavs.review.entity.ReviewEntity;
import com.nativenavs.review.service.ReviewService;
import com.nativenavs.user.entity.UserEntity;
import com.nativenavs.user.repository.UserRepository;
import com.nativenavs.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin("*")
@Tag(name = "review API", description = "review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "리뷰 등록 API", description = "리뷰를 등록할때 사용하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> reviewSave(@RequestHeader("Authorization") String token,
                                        @RequestPart("review") ReviewRequestDTO reviewDTO,
                                        @RequestPart("reviewImages") List<MultipartFile> images){
        try {
            int userId = getUserIdFromJWT(token); // JWT에서 사용자 ID 추출
            UserEntity reviewer = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            ReviewEntity review = reviewService.addReview(reviewDTO, reviewer, images);
            //반환 여부 토론
            return ResponseEntity.ok("리뷰 작성 완료");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 실패");
        }
    }

    @GetMapping("/tour/{tourId}")
    @Operation(summary = "투어 리뷰 조회 API", description = "투어 리뷰를 조회할때 사용하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> reviewFindByTourId(@Parameter(description = "조회 기준이 될 투어 ID", required = true, example = "1")
                                                    @PathVariable("tourId") int tourId){
    //jwt 사용자 정보가 필요한가?? 보류
        try {
            TourReviewDTO tourReviewDTO = reviewService.findReviewByTourId(tourId);
            return ResponseEntity.ok(tourReviewDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 조회 실패");
        }

    }

    @GetMapping("/guide/{guideId}")
    @Operation(summary = "가이드 리뷰 조회 API", description = "가이드 리뷰를 조회할때 사용하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> reviewFindByGuideId(@Parameter(description = "조회 기준이 될 가이드 ID", required = true, example = "10")
                                                @PathVariable("guideId") int guideId){
        //jwt 사용자 정보가 필요한가?? 보류
        try {
            GuideReviewDTO guideReviewDTO = reviewService.findReviewByGuideId(guideId);
            return ResponseEntity.ok(guideReviewDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 조회 실패");
        }

    }

    @GetMapping("/user/{travId}")
    @Operation(summary = "Trav 리뷰 조회 API", description = "Trav가 쓴 리뷰를 조회할때 사용하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> reviewFindByTravId(@Parameter(description = "조회 기준이 될 가이드 ID", required = true, example = "10")
                                                    @PathVariable("travId") int travId){
        //jwt 사용자 정보가 필요한가?? 보류
        try {
            TravReviewDTO travReviewDTO = reviewService.findReviewByUserId(travId);
            return ResponseEntity.ok(travReviewDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 조회 실패");
        }

    }

    //JWT에서 이메일 받아 id로 치환
    private int getUserIdFromJWT(String token){
        String jwtToken = token.replace("Bearer ", ""); // "Bearer " 부분 제거
        String email = JwtTokenProvider.getEmailFromToken(jwtToken);
        return userService.changeEmailToId(email);
    }
}
