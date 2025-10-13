package udpm.hn.server.test.core.staff.product.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.entity.Resolution;
import udpm.hn.server.test.repository.ResolutionRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResolutionProductRepository extends ResolutionRepository {
    Optional<Resolution> findByResolutionName(String resolutionName);

    @Query(value = "SELECT resolution_name AS resolutionName FROM resolution", nativeQuery = true)
    List<String> findResolutionNames();
}
