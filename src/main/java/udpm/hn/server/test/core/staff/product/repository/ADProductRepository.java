package udpm.hn.server.test.core.staff.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.core.staff.product.model.response.ProductResponse;
import udpm.hn.server.test.entity.Product;
import udpm.hn.server.test.repository.ProductRepository;

import java.util.Optional;

@Repository
public interface ADProductRepository extends ProductRepository {
    @Query(value = """
            SELECT
                p.id AS id,
                p.product_code AS productCode,
                p.product_name AS productName,
                i.iso_name AS iso,
                ps.processor_name AS processor,
                r.resolution_name AS resolution,
                s.sensor_name AS sensor,
                p.description  AS description,
                c.category_code AS categoryCode,
                p.status AS status
            FROM product p
                     LEFT JOIN iso i ON p.iso_id = i.id
                     LEFT JOIN processor ps ON p.processor_id = ps.id
                     LEFT JOIN resolution r ON p.resolution_id = r.id
                     LEFT JOIN sensor s ON p.sensor_id = s.id
                     LEFT JOIN category c ON p.category_id = c.id
            WHERE
                (:keyword IS NULL OR p.product_code LIKE CONCAT('%', :keyword, '%')
                                               OR p.product_name LIKE CONCAT('%', :keyword, '%'))
              AND (:status IS NULL OR p.status = :status)
            """, nativeQuery = true)
    Page<ProductResponse> getAllProducts(@Param("keyword") String keyword,
                                         @Param("status") Integer status,
                                         Pageable pageable);

    Optional<Product> findByProductCode(String productCode);

    @Query(value = """
            SELECT
                p.id AS id,
                p.product_code AS productCode,
                p.product_name AS productName,
                i.iso_name AS iso,
                ps.processor_name AS processor,
                r.resolution_name AS resolution,
                s.sensor_name AS sensor,
                p.description  AS description,
                c.category_code AS categoryCode,
                p.status AS status
            FROM product p
                     LEFT JOIN iso i ON p.iso_id = i.id
                     LEFT JOIN processor ps ON p.processor_id = ps.id
                     LEFT JOIN resolution r ON p.resolution_id = r.id
                     LEFT JOIN sensor s ON p.sensor_id = s.id
                     LEFT JOIN category c ON p.category_id = c.id
            WHERE p.id = :idProduct
            """ , nativeQuery = true)
    Optional<ProductResponse> findByProductId(@Param("idProduct") String idProduct);
}
