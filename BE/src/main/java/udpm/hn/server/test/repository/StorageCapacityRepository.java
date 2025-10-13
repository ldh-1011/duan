package udpm.hn.server.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.core.staff.storageCapacity.model.response.StorageCapacityResponse;
import udpm.hn.server.test.entity.StorageCapacity;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorageCapacityRepository extends JpaRepository<StorageCapacity, String> {
    @Query(value = "SELECT id AS id , storage_capacity_name AS storageCapacityName  FROM storage_capacity" , nativeQuery = true)
    List<StorageCapacityResponse> getAllStorageCapacityRepository();

    Optional<StorageCapacity> findByStorageCapacityName(String storageCapacityName);
}
