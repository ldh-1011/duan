package udpm.hn.server.test.core.staff.color.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udpm.hn.server.test.core.staff.color.service.ColorService;
import udpm.hn.server.test.entity.Color;
import udpm.hn.server.test.infrastructure.constant.MappingConstants;
import udpm.hn.server.test.utils.Helper;

@RestController
@RequestMapping(MappingConstants.API_STAFF_COLOR)
@RequiredArgsConstructor
public class ColorController {
    private final ColorService colorService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllColors() {
        return Helper.createResponseEntity(colorService.getAllColor());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addColor(@RequestBody Color color) {
        return Helper.createResponseEntity(colorService.addColor(color));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateColor(@PathVariable String id, @RequestBody Color color) {
        return Helper.createResponseEntity(colorService.updateColor(id, color));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteColor(@PathVariable String id) {
        return Helper.createResponseEntity(colorService.deleteColor(id));
    }

}
