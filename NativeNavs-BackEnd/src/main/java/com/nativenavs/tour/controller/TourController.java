package com.nativenavs.tour.controller;

import com.nativenavs.tour.dto.TourDTO;
import com.nativenavs.tour.service.TourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
                                            "  \"removed\": false,\n" +
                                            "  \"categoryIds\": [1, 2],\n" +
                                            "  \"plans\": [\n" +
                                            "    {\n" +
                                            "      \"id\": 1,\n" +
                                            "      \"field\": \"Field 1\",\n" +
                                            "      \"description\": \"Description of plan 1\",\n" +
                                            "      \"image\": \"http://example.com/plan1.jpg\",\n" +
                                            "      \"latitude\": 37.5665,\n" +
                                            "      \"longitude\": 126.978,\n" +
                                            "      \"addressFull\": \"123 Example Street\"\n" +
                                            "    },\n" +
                                            "    {\n" +
                                            "      \"id\": 2,\n" +
                                            "      \"field\": \"Field 2\",\n" +
                                            "      \"description\": \"Description of plan 2\",\n" +
                                            "      \"image\": \"http://example.com/plan2.jpg\",\n" +
                                            "      \"latitude\": 37.567,\n" +
                                            "      \"longitude\": 126.979,\n" +
                                            "      \"addressFull\": \"456 Example Avenue\"\n" +
                                            "    }\n" +
                                            "  ]\n" +
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

    @Tag(name = "tour API", description = "tour")
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


    @Tag(name = "tour API", description = "tour")
    @Operation(summary = "투어 수정 API", description = "투어 정보를 수정하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.", content = @Content(mediaType = "application/json"))
    @PutMapping("/{id}")
    public ResponseEntity<?> tourModify(
            @Parameter(description = "투어 ID", required = true, example = "10")
            @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = ".", required = true, content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = "{\n" +
                                    "  \"userId\": 10,\n" +
                                    "  \"title\": \"Summer Vacation2\",\n" +
                                    "  \"thumbnailImage\": \"http://example.com/image.jpg2\",\n" +
                                    "  \"description\": \"A relaxing summer vacation tour2\",\n" +
                                    "  \"location\": \"서울특별시 종로구\",\n" +
                                    "  \"price\": 7777,\n" +
                                    "  \"startDate\": \"2024-08-01\",\n" +
                                    "  \"endDate\": \"2024-08-15\",\n" +
                                    "  \"reviewAverage\": 0.0,\n" +
                                    "  \"reviewCount\": 0,\n" +
                                    "  \"maxParticipants\": 10,\n" +
                                    "  \"removed\": false,\n" +
                                    "  \"categoryIds\": [2, 3],\n" +
                                    "  \"plans\": [\n" +
                                    "    {\n" +
                                    "      \"id\": 3,\n" +
                                    "      \"field\": \"Field 3\",\n" +
                                    "      \"description\": \"Description of plan 3\",\n" +
                                    "      \"image\": \"http://example.com/plan1.jpg\",\n" +
                                    "      \"latitude\": 37.5665,\n" +
                                    "      \"longitude\": 126.978,\n" +
                                    "      \"addressFull\": \"123 Example Street\"\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"id\": 4,\n" +
                                    "      \"field\": \"Field 4\",\n" +
                                    "      \"description\": \"Description of plan 4\",\n" +
                                    "      \"image\": \"http://example.com/plan2.jpg\",\n" +
                                    "      \"latitude\": 37.567,\n" +
                                    "      \"longitude\": 126.979,\n" +
                                    "      \"addressFull\": \"456 Example Avenue\"\n" +
                                    "    }\n" +
                                    "  ]\n" +
                                    "}"
                    )
            )
            )
            @RequestBody TourDTO tourDTO) {
        try {
            tourService.modifyTour(id, tourDTO);
            return ResponseEntity.ok("투어 수정 완료");
        } catch (Exception e) {
            e.printStackTrace(); // 실제 코드에서는 로그를 사용하세요
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("투어 수정 실패");
        }
    }


    @Tag(name = "tour API", description = "tour")
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
}
