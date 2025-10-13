package udpm.hn.server.test.core.staff.storageCapacity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udpm.hn.server.test.core.staff.storageCapacity.service.StorageCapacityService;
import udpm.hn.server.test.entity.StorageCapacity;
import udpm.hn.server.test.infrastructure.constant.MappingConstants;
import udpm.hn.server.test.utils.Helper;

@RestController
@RequestMapping(MappingConstants.API_STAFF_STORAGE_CAPACITY)
@RequiredArgsConstructor
public class StorageCapacityController {
    private final StorageCapacityService storageCapacityService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllStorageCapacity() {
        return Helper.createResponseEntity(storageCapacityService.getAllStorageCapacity());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStorageCapacity(@RequestBody StorageCapacity storageCapacity) {
        return Helper.createResponseEntity(storageCapacityService.addStorageCapacity(storageCapacity));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStorageCapacity(@PathVariable String id, @RequestBody StorageCapacity storageCapacity) {
        return Helper.createResponseEntity(storageCapacityService.updateStorageCapacity(id, storageCapacity));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStorageCapacity(@PathVariable String id) {
        return Helper.createResponseEntity(storageCapacityService.deleteStorageCapacity(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStorageCapacity(@PathVariable String id) {
        return Helper.createResponseEntity(storageCapacityService.findByIdStorageCapacity(id));
    }
}
