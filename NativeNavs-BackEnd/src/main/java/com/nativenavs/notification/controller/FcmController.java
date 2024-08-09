package com.nativenavs.notification.controller;

import com.nativenavs.notification.service.FcmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
@CrossOrigin("*")
@Slf4j
public class FcmController {
    private final FcmService fcmService;

    public FcmController(FcmService fcmService) {
        this.fcmService = fcmService;
    }

//    @PostMapping("/send")
//    public ResponseEntity<String>  pushMessage(@RequestBody @Validated FcmSendDTO fcmSendDTO) throws IOException {
//        try {
//            int result = fcmService.sendMessageTo(fcmSendDTO);
//
//            if (result == 1) {
//                return ResponseEntity.ok("푸시 메시지 전송에 성공했습니다.");
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("푸시 메시지 전송에 실패했습니다.");
//            }
//        } catch (Exception e) {
//            log.error("푸시 메시지 전송 중 오류 발생", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류로 인해 푸시 메시지 전송에 실패했습니다.");
//        }
//    }
}
