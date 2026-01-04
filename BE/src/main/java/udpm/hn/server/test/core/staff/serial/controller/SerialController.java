package udpm.hn.server.test.core.staff.serial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import udpm.hn.server.test.core.staff.serial.service.SerialService;
import udpm.hn.server.test.infrastructure.constant.MappingConstants;

@RestController
@RequestMapping("/api/serial")
@RequiredArgsConstructor
public class SerialController {
    private final SerialService serialService;

    @PostMapping("/upload-serial/inactive/{productDetailId}")
    public ResponseEntity<?> uploadSerialInactive(
            @RequestParam("file") MultipartFile file,
            @PathVariable("productDetailId") String productDetailId) {
        return ResponseEntity.ok(serialService.uploadSerialInactive(file, productDetailId));
    }

//    @PostMapping("/changeStatus/serial")

}