package udpm.hn.server.test.core.staff.product.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.entity.Sensor;
import udpm.hn.server.test.repository.SensorRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorProductRepository extends SensorRepository {
    Optional<Sensor> findBySensorName(String sensorName);

    @Query(value = "SELECT sensor_name AS sensorName FROM sensor" , nativeQuery = true)
    List<String> getSensorNames();
}
