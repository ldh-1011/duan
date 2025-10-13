package udpm.hn.server.test.core.staff.productDetail.repository;

import org.springframework.stereotype.Repository;
import udpm.hn.server.test.entity.StorageCapacity;
import udpm.hn.server.test.repository.StorageCapacityRepository;

import java.util.Optional;

@Repository
public interface ProductDetailStorageCapacityRepository extends StorageCapacityRepository {
    Optional<StorageCapacity> findByStorageCapacityName(String name);
}
