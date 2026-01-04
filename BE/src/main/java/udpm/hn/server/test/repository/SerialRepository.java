package udpm.hn.server.test.repository;

import org.hibernate.loader.ast.internal.CacheEntityLoaderHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.entity.ProductDetail;
import udpm.hn.server.test.entity.Serial;
import udpm.hn.server.test.infrastructure.constant.EntityStatus;

import java.util.List;

@Repository
public interface SerialRepository extends JpaRepository<Serial, String> {
    @Query("SELECT u FROM Serial u WHERE u.productDetail = ?1 AND u.status = ?2")
    List<Serial> findByProductDetail(ProductDetail productDetail, EntityStatus entityStatus);
}
