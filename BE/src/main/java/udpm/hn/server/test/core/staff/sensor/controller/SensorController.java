package udpm.hn.server.test.core.staff.sensor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udpm.hn.server.test.core.staff.sensor.service.SensorService;
import udpm.hn.server.test.entity.Sensor;
import udpm.hn.server.test.infrastructure.constant.MappingConstants;
import udpm.hn.server.test.utils.Helper;

@RestController
@RequestMapping(MappingConstants.API_STAFF_SENSOR)
@RequiredArgsConstructor
public class SensorController {
    private final SensorService sensorService;

    @GetMapping("/all")
    private ResponseEntity<?> getAllSensors() {
        return Helper.createResponseEntity(sensorService.getAllSensor());
    }

    @PostMapping("/add")
    private ResponseEntity<?> addSensor(@RequestBody Sensor sensor) {
        return Helper.createResponseEntity(sensorService.addSensor(sensor));
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<?> updateSensor(@PathVariable String id, @RequestBody Sensor sensor) {
        return Helper.createResponseEntity(sensorService.updateSensor(id, sensor));
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<?> deleteSensor(@PathVariable String id) {
        return Helper.createResponseEntity(sensorService.deleteSensor(id));
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> findSensorById(@PathVariable String id) {
        return Helper.createResponseEntity(sensorService.findByIdSensor(id));
    }
}
