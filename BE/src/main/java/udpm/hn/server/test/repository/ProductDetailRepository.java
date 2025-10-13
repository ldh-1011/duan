package udpm.hn.server.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.entity.ProductDetail;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, String> {
    @Query("SELECT COUNT(pd) > 0 FROM ProductDetail pd WHERE pd.color.id = :colorId")
    boolean existsByColorId(@Param("colorId") String colorId);

    @Query("SELECT COUNT(pd) > 0 FROM ProductDetail pd WHERE pd.version.id = :versionId")
    boolean existsByVersionId(@Param("versionId") String versionId);
}
