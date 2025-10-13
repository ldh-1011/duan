package udpm.hn.server.test.core.staff.version.controllor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udpm.hn.server.test.core.staff.version.service.VersionService;
import udpm.hn.server.test.entity.Version;
import udpm.hn.server.test.infrastructure.constant.MappingConstants;
import udpm.hn.server.test.utils.Helper;

@RestController
@RequestMapping(MappingConstants.API_STAFF_VERSION)
@RequiredArgsConstructor
public class VersionController {
    private final VersionService versionService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllVersions() {
        return Helper.createResponseEntity(versionService.getAllVersion());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVersion(@RequestBody Version version) {
        return Helper.createResponseEntity(versionService.addVersion(version));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateVersion(@PathVariable String id, @RequestBody Version version) {
        return Helper.createResponseEntity(versionService.updateVersion(id, version));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVersion(@PathVariable String id) {
        return Helper.createResponseEntity(versionService.deleteVersion(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVersionId(@PathVariable String id) {
        return Helper.createResponseEntity(versionService.findVersionId(id));
    }
}
