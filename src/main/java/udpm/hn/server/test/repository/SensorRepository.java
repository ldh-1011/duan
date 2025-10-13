package udpm.hn.server.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.core.staff.sensor.model.response.SensorRequest;
import udpm.hn.server.test.entity.Sensor;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, String> {
    @Query(value = "SELECT id AS id , sensor_name AS sensorName FROM sensor",nativeQuery = true)
    List<SensorRequest> findAllSensors();

    Optional<Sensor> findBySensorName(String sensorName);
}
