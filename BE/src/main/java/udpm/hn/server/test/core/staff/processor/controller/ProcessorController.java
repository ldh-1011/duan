package udpm.hn.server.test.core.staff.processor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udpm.hn.server.test.core.staff.processor.service.ProcessorService;
import udpm.hn.server.test.entity.Processor;
import udpm.hn.server.test.infrastructure.constant.MappingConstants;
import udpm.hn.server.test.utils.Helper;

@RestController
@RequestMapping(MappingConstants.API_STAFF_PROCESSOR)
@RequiredArgsConstructor
public class ProcessorController {
    private final ProcessorService techSpecService;

    @GetMapping("/all")
    private ResponseEntity<?> getAllTechSpecs() {
        return Helper.createResponseEntity(techSpecService.getAllTechSpec());
    }

    @PostMapping("/add")
    private ResponseEntity<?> addTechSpec(@RequestBody Processor processor) {
        return Helper.createResponseEntity(techSpecService.addTechSpec(processor));
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<?> updateTechSpec(@PathVariable String id, @RequestBody Processor processor) {
        return Helper.createResponseEntity(techSpecService.updateTechSpec(id, processor));
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<?> deleteTechSpec(@PathVariable String id) {
        return Helper.createResponseEntity(techSpecService.deleteTechSpec(id));
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getTechSpec(@PathVariable String id) {
        return Helper.createResponseEntity(techSpecService.findProcessorID(id));
    }
}
