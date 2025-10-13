package udpm.hn.server.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.iso.id = :isoId")
    boolean existsByIsoId(@Param("isoId") String isoId);

    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.resolution.id = :resolutionId")
    boolean existsByResolutionId(@Param("resolutionId") String resolutionId);

    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.processor.id = :processorId")
    boolean existsByProcessorId(@Param("processorId") String processorId);

}
