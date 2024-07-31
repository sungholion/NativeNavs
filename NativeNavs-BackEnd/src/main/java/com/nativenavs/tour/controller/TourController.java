package com.nativenavs.tour.controller;

import com.nativenavs.tour.dto.TourDTO;
import com.nativenavs.tour.service.TourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tours")
public class TourController {
    private final TourService tourService;


    @Tag(name = "tour API", description = "tour")
    @Operation(summary = "투어 등록 API", description = "여행 계획을 등록할때 사용하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.", content = @Content(mediaType = "application/json"))
    @PostMapping
    public ResponseEntity<?> tourSave(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = ".",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "  \"userId\": 10,\n" +
                                            "  \"title\": \"Summer Vacation\",\n" +
                                            "  \"thumbnailImage\": \"http://example.com/image.jpg\",\n" +
                                            "  \"description\": \"A relaxing summer vacation tour\",\n" +
                                            "  \"location\": \"서울특별시 종로구\",\n" +
                                            "  \"price\": 500000,\n" +
                                            "  \"startDate\": \"2024-08-01\",\n" +
                                            "  \"endDate\": \"2024-08-15\",\n" +
                                            "  \"reviewAverage\": 0.0,\n" +
                                            "  \"reviewCount\": 0,\n" +
                                            "  \"maxParticipants\": 6,\n" +
                                            "  \"removed\": false\n" +
                                            "}"
                            )
                    )
            )
            @RequestBody TourDTO tourDTO){

        System.out.println("tourDTO : " + tourDTO);
        try {
            tourService.addTour(tourDTO);
            return ResponseEntity.ok("여행 등록 완료");
        } catch (Exception e) {
            e.printStackTrace();  // 실제 코드에서는 로그를 사용하세요
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 등록 실패");
        }
    }

    @Tag(name = "tour API", description = "tour")
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
//
//    @GetMapping("/{tour_id}")


}
