package udpm.hn.server.test.core.staff.resolution.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udpm.hn.server.test.core.staff.resolution.service.ResolutionService;
import udpm.hn.server.test.entity.Resolution;
import udpm.hn.server.test.infrastructure.constant.MappingConstants;
import udpm.hn.server.test.utils.Helper;

@RestController
@RequestMapping(MappingConstants.API_STAFF_RESOLUTION)
@RequiredArgsConstructor
public class ResolutionController {
    private final ResolutionService resolutionService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllResolutions() {
        return Helper.createResponseEntity(resolutionService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addResolution(@RequestBody Resolution resolution) {
        return Helper.createResponseEntity(resolutionService.addResolution(resolution));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateResolution( @PathVariable String id , @RequestBody Resolution resolution) {
        return Helper.createResponseEntity(resolutionService.updateResolution(id, resolution));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteResolution(@PathVariable String id) {
        return Helper.createResponseEntity(resolutionService.deleteResolution(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findResolutionId(@PathVariable String id) {
        return Helper.createResponseEntity(resolutionService.findByResolutionId(id));
    }
}
