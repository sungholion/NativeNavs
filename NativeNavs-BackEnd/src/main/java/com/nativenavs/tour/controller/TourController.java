package com.nativenavs.tour.controller;

import com.nativenavs.auth.jwt.JwtTokenProvider;
import com.nativenavs.tour.dto.CategoryDTO;
import com.nativenavs.tour.dto.TourDTO;
import com.nativenavs.tour.dto.TourRequestDTO;
import com.nativenavs.tour.service.CategoryService;
import com.nativenavs.tour.service.TourService;
import com.nativenavs.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/tours")
@Tag(name = "tour API", description = "tour")
public class TourController {
    private final TourService tourService;
    private final CategoryService categoryService;
    private final UserService userService;


    @Operation(summary = "투어 등록 API", description = "여행 계획을 등록할때 사용하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.", content = @Content(mediaType = "application/json"))
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> tourSave(
            @RequestHeader("Authorization") String token,

            @ModelAttribute TourRequestDTO tourRequestDTO){
        try {

            int userId = getUserIdFromJWT(token);

            tourService.addTour(tourRequestDTO,userId);
            return ResponseEntity.ok("여행 등록 완료");
        } catch (Exception e) {
            e.printStackTrace();  // 실제 코드에서는 로그를 사용하세요
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 등록 실패");
        }
    }

    @Operation(summary = "투어 리스트 조회 API", description = "전체 투어 리스트를 조회하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.", content = @Content(mediaType = "application/json"))
    @GetMapping("/category")
    public ResponseEntity<?> categoryList(){
        try{
            List<CategoryDTO> categoryList = categoryService.getAllCategories();
            return ResponseEntity.ok(categoryList);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("카테고리 조회 실패");
        }
    }

    @Operation(summary = "투어 리스트 조회 API", description = "전체 투어 리스트를 조회하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.", content = @Content(mediaType = "application/json"))
    @GetMapping
    public ResponseEntity<?> tourList(){
        try{
            List<TourDTO> tourDTOList = tourService.findAllTours();
            return ResponseEntity.ok(tourDTOList);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("투어리스트 조회 실패");
        }
    }

    @Operation(summary = "투어 상세조회 API", description = "투어 상세정보를 조회하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "투어를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    @GetMapping("/{id}")
    public ResponseEntity<?> tourDeatils(
            @Parameter(description = "투어 ID", required = true, example = "1")
            @PathVariable int id) {
        try {
            TourDTO tourDTO = tourService.findTourById(id);
            return ResponseEntity.ok(tourDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("투어 상세조회 실패");
        }
    }


    @Operation(summary = "투어 수정 API", description = "투어 정보를 수정하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.", content = @Content(mediaType = "application/json"))
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> tourModify(
            @RequestHeader("Authorization") String token,
            @PathVariable int id,
            @ModelAttribute TourRequestDTO tourRequestDTO) {
        try {
            tourService.modifyTour(id, tourRequestDTO);
            return ResponseEntity.ok("투어 수정 완료");
        } catch (Exception e) {
            e.printStackTrace(); // 실제 코드에서는 로그를 사용하세요
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("투어 수정 실패");
        }
    }


    @Operation(summary = "투어 삭제 API", description = "투어를 삭제하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "투어를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    @DeleteMapping("/{id}")
    public ResponseEntity<?> tourRemove(
            @Parameter(description = "투어 ID", required = true, example = "2")
            @PathVariable int id) {
        try {
            tourService.removeTour(id);
            return ResponseEntity.ok("투어 삭제 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("투어 삭제 실패");
        }
    }

    @GetMapping("/search")
    @Operation(summary = "투어 검색 API", description = "투어를 검색하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> tourSearch(

            @Parameter(description = "위치 검색어", example = "서울")
            @RequestParam(required = false) String location,

            @Parameter(description = "검색할 날짜", example = "2024-08-15")
            @RequestParam(required = false) LocalDate date,

            @Parameter(description = "카테고리 ID", example = "7")
            @RequestParam(required = false) Integer categoryId) {

        try{
            List<TourDTO> tourDTOList = tourService.searchTours(location, date, categoryId);
            return ResponseEntity.ok(tourDTOList);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("투어 검색 실패");
        }
    }


    //JWT에서 이메일 받아 id로 치환
    private int getUserIdFromJWT(String token){
        String jwtToken = token.replace("Bearer ", ""); // "Bearer " 부분 제거
        String email = JwtTokenProvider.getEmailFromToken(jwtToken);
        return userService.changeEmailToId(email);
    }

}
