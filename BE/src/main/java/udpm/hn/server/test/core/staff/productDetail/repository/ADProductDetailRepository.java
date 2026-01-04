package udpm.hn.server.test.core.staff.productDetail.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.core.staff.productDetail.model.response.ProductDetailOneResponse;
import udpm.hn.server.test.core.staff.productDetail.model.response.ProductDetailResponse;
import udpm.hn.server.test.repository.ProductDetailRepository;

import java.math.BigDecimal;

@Repository
public interface ADProductDetailRepository extends ProductDetailRepository {

    @Query(value = """
            SELECT pd.id                    AS id,
                   pd.price                 AS price,
                   p.product_name           AS productName,
                   p.product_code           AS productCode,
                   i.link_image             AS img,
                   c.color_name             AS colorName,
                   v.version_name           AS versionName,
                   sc.storage_capacity_name AS storageCapacityName,
                   pd.status                AS status
            FROM product_detail pd
                     LEFT JOIN img i ON pd.id = i.product_detail_id
                     LEFT JOIN color c ON pd.color_id = c.id
                     LEFT JOIN version v ON pd.version_id = v.id
                     LEFT JOIN storage_capacity sc ON pd.storage_capacity_id = sc.id
                     LEFT JOIN product p ON pd.product_id = p.id
            WHERE (:minPrice IS NULL OR pd.price >= :minPrice)
              AND (:maxPrice IS NULL OR pd.price <= :maxPrice)
              AND (:productCode IS NULL OR p.product_code LIKE CONCAT('%', :productCode, '%'))
              AND (:productName IS NULL OR p.product_name LIKE CONCAT('%', :productName, '%'))
              AND (:colorName IS NULL OR c.color_name LIKE CONCAT('%', :colorName, '%'))
              AND (:versionName IS NULL OR v.version_name LIKE CONCAT('%', :versionName, '%'))
              AND (:storageCapacityName IS NULL OR sc.storage_capacity_name LIKE CONCAT('%', :storageCapacityName, '%'))
              AND (:status IS NULL OR pd.status = :status)
            """, nativeQuery = true)
    Page<ProductDetailResponse> getAllProductDetail(
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("productName") String productName,
            @Param("productCode") String productCode,
            @Param("colorName") String colorName,
            @Param("versionName") String versionName,
            @Param("storageCapacityName") String storageCapacityName,
            @Param("status") Integer status,
            Pageable pageable
    );

    @Query("""
    SELECT new udpm.hn.server.test.core.staff.productDetail.model.response.ProductDetailOneResponse(
        pd.id,
        pd.price,
        p.productName,
        i.linkImage,
        c.colorName,
        v.versionName,
        sc.storageCapacityName,
        pd.status,
        pd.stockQuantity,
        COUNT(s.id)
    )
    FROM ProductDetail pd
        LEFT JOIN Img i ON i.productDetail.id = pd.id
        LEFT JOIN pd.product p
        LEFT JOIN pd.color c
        LEFT JOIN pd.version v
        LEFT JOIN pd.storageCapacity sc
        LEFT JOIN Serial s ON s.productDetail = pd
    WHERE pd.id = :id
    GROUP BY pd.id, pd.price, p.productName, i.linkImage, c.colorName, v.versionName, sc.storageCapacityName, pd.status
""")
    ProductDetailOneResponse getOneProductDetail(@Param("id") String id);

}
