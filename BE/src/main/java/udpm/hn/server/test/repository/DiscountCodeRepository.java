package udpm.hn.server.test.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import udpm.hn.server.test.core.staff.discount.model.response.DiscountCodeResponse;
import udpm.hn.server.test.entity.DiscountCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface DiscountCodeRepository extends JpaRepository<DiscountCode, String> {

    @Query("""
            SELECT u
            FROM DiscountCode u
            WHERE (:code IS NULL
                   OR LOWER(u.code) LIKE LOWER(CONCAT('%', :code, '%')))
              AND (:minMoney IS NULL
                   OR u.reducedConditions <= :minMoney)
              AND (:percent IS NULL
                   OR u.discountPercent = :percent)
            """)
    Page<DiscountCode> search(
            @Param("code") String code,
            @Param("minMoney") BigDecimal minMoney,
            @Param("percent") BigDecimal percent ,
            Pageable pageable
    );

    @Query("""
            SELECT u
            FROM DiscountCode u
            WHERE u.id = :id
            """)
    DiscountCode getDetailDiscountCode(String id);

    @Query("""
    SELECT new udpm.hn.server.test.core.staff.discount.model.response.DiscountCodeResponse(
        d.code,
        CONCAT(
            'Giảm ',
            d.discountPercent,
            '%',
            CASE
                WHEN d.maxDiscountAmount IS NOT NULL
                    THEN CONCAT(' - Tối đa ', d.maxDiscountAmount, 'đ')
                ELSE ''
            END,
            CASE
                WHEN d.reducedConditions IS NOT NULL
                    THEN CONCAT(' - Đơn ≥ ', d.reducedConditions, 'đ')
                ELSE ''
            END
        )
    )
    FROM DiscountCode d
    WHERE (:keyword IS NULL OR :keyword = ''
           OR LOWER(d.code) LIKE LOWER(CONCAT('%', :keyword, '%')))
    """)
    List<DiscountCodeResponse> searchDiscountCodeResponse(
            @Param("keyword") String keyword
    );

    @Query("""
    SELECT new udpm.hn.server.test.core.staff.discount.model.response.DiscountCodeResponse(
        d.code,
        CONCAT(
            'Giảm ',
            d.discountPercent,
            '%',
            CASE
                WHEN d.maxDiscountAmount IS NOT NULL
                    THEN CONCAT(' - Tối đa ', d.maxDiscountAmount, 'đ')
                ELSE ''
            END,
            CASE
                WHEN d.reducedConditions IS NOT NULL
                    THEN CONCAT(' - Đơn ≥ ', d.reducedConditions, 'đ')
                ELSE ''
            END
        )
    )
    FROM DiscountCode d
    WHERE d.quantity > 0
      AND (
            d.reducedConditions IS NULL
            OR d.reducedConditions <= :billTotal
          )
""")
    List<DiscountCodeResponse> findDiscountCodeByBillTotal(
            @Param("billTotal") BigDecimal billTotal
    );

    boolean existsByCode(String code);

    @Modifying
    @Transactional
    @Query("""
        UPDATE DiscountCode d
        SET d.statusDiscountCode = udpm.hn.server.test.infrastructure.constant.LoaiGiamGia.END
        WHERE d.endDate < :now
          AND d.statusDiscountCode <> udpm.hn.server.test.infrastructure.constant.LoaiGiamGia.END
    """)
    void autoSetEnd(LocalDateTime now);

    // Tới startDate → ONGOING
    @Modifying
    @Transactional
    @Query("""
        UPDATE DiscountCode d
        SET d.statusDiscountCode = udpm.hn.server.test.infrastructure.constant.LoaiGiamGia.ONGOING
        WHERE d.startDate <= :now
          AND d.endDate >= :now
          AND d.statusDiscountCode = udpm.hn.server.test.infrastructure.constant.LoaiGiamGia.COMING_SOON
    """)
    void autoSetOngoing(LocalDateTime now);
}
