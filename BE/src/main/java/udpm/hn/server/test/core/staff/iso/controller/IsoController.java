package udpm.hn.server.test.core.staff.iso.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udpm.hn.server.test.core.staff.iso.service.IsoService;
import udpm.hn.server.test.entity.Iso;
import udpm.hn.server.test.infrastructure.constant.MappingConstants;
import udpm.hn.server.test.utils.Helper;

@RestController
@RequestMapping(MappingConstants.API_STAFF_ISO)
@RequiredArgsConstructor
public class IsoController {
    private final IsoService isoService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllIso(){
        return Helper.createResponseEntity(isoService.getAllIso());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addIso(@RequestBody Iso iso){
        return Helper.createResponseEntity(isoService.addIso(iso));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateIso( @PathVariable String id ,@RequestBody Iso iso){
        return Helper.createResponseEntity(isoService.updateIso(id , iso));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteIso(@PathVariable String id){
        return Helper.createResponseEntity(isoService.deleteIso(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIso(@PathVariable String id){
        return Helper.createResponseEntity(isoService.findIsoById(id));
    }
}
