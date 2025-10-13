package udpm.hn.server.test.core.staff.product.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.entity.Iso;
import udpm.hn.server.test.repository.IsoRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IsoProductRepository extends IsoRepository {
    Optional<Iso> findByIsoName(String isoName);
    @Query(value = "SELECT iso_name AS isoName FROM iso" , nativeQuery = true)
    List<String> findIsoNames();
}
