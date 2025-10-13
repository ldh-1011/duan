package udpm.hn.server.test.core.staff.sensor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.core.staff.sensor.service.SensorService;
import udpm.hn.server.test.entity.Sensor;
import udpm.hn.server.test.repository.SensorRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {
    private final SensorRepository sensorRepository;

    @Override
    public ResponseObject<?> getAllSensor(){
        return new ResponseObject<>(
                sensorRepository.findAllSensors(),
                HttpStatus.OK,
                "Lay thanh cong sensor"
        );
    }

    @Override
    public ResponseObject<?> addSensor(Sensor sensor){
        Optional<Sensor> optionalSensor = sensorRepository.findBySensorName(sensor.getSensorName());
        if(optionalSensor.isPresent()){
            return new ResponseObject<>(null , HttpStatus.CONFLICT, "da tai sensor");
        }
        sensor.setSensorName(sensor.getSensorName());
        sensorRepository.save(sensor);

        return new ResponseObject<>(null , HttpStatus.ACCEPTED , "them thanh cong");
    }

    @Override
    public ResponseObject<?> updateSensor(String id, Sensor sensor){
        Optional<Sensor> optionalSensor = sensorRepository.findById(id);
        if(optionalSensor.isEmpty()){
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND, "khong tim thay id nay");
        }

        Optional<Sensor> optionalSensorName = sensorRepository.findBySensorName(sensor.getSensorName());
        if(optionalSensorName.isPresent() && !optionalSensor.get().getId().equals(id)){
            return new ResponseObject<>(null , HttpStatus.CONFLICT, "da ton tai");
        }

        Sensor savedSensor = optionalSensor.get();
        savedSensor.setSensorName(sensor.getSensorName());
        sensorRepository.save(savedSensor);

        return new ResponseObject<>(savedSensor , HttpStatus.ACCEPTED , "update thanh cong");
    }

    @Override
    public ResponseObject<?> deleteSensor(String id){
        Optional<Sensor> optionalSensor = sensorRepository.findById(id);
        if(optionalSensor.isEmpty()){
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND, "khong tim thay id");
        }
        sensorRepository.deleteById(id);

        return new ResponseObject<>(null , HttpStatus.ACCEPTED , "delete thanh cong");
    }
}
