package udpm.hn.server.test.core.staff.img.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import udpm.hn.server.test.core.staff.img.service.ImgService;
import udpm.hn.server.test.infrastructure.constant.MappingConstants;
import udpm.hn.server.test.utils.Helper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping(MappingConstants.API_STAFF_IMG)
@RequiredArgsConstructor
@CrossOrigin("*")
public class ImgController {

    private final ImgService imgService;

    @GetMapping("/all_img/{id}")
    public ResponseEntity<?> getAllImgAndId(@PathVariable String id) {
        return Helper.createResponseEntity(imgService.getAllImgAndId(id));
    }

    @PostMapping("/upload/{productDetailId}")
    public ResponseEntity<?> uploadImage(
            @PathVariable("productDetailId") String productDetailId,
            @RequestParam("file") MultipartFile file
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File không được để trống");
        }
        return Helper.createResponseEntity(imgService.uploadImgFile(productDetailId, file));
    }

    @GetMapping("/view/{filename:.+}")
    public ResponseEntity<Resource> viewImage(@PathVariable String filename) {
        try {
            Path filePath = new File("uploads/" + filename).toPath();
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(filePath.toUri());
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) contentType = "application/octet-stream";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
