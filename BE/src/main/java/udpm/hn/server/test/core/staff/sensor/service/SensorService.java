package udpm.hn.server.test.core.staff.sensor.service;

import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.entity.Sensor;

public interface SensorService {
    ResponseObject<?> getAllSensor();

    ResponseObject<?> addSensor(Sensor sensor);

    ResponseObject<?> updateSensor(String id, Sensor sensor);

    ResponseObject<?> deleteSensor(String id);

    ResponseObject<?> findByIdSensor(String id);
}
