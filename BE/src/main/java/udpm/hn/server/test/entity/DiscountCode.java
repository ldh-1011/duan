package udpm.hn.server.test.entity;

import jakarta.persistence.*;
import lombok.*;
import udpm.hn.server.test.entity.base.PrimaryEntity;
import udpm.hn.server.test.infrastructure.constant.LoaiGiamGia;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "discount_code")
public class DiscountCode extends PrimaryEntity implements Serializable {

    @Column(name = "code", unique = true, nullable = false)
    private String code; // Mã giảm giá (VD: SALE10)

    @Column(name = "discount_percent")
    private BigDecimal discountPercent;

    @Column(name = "min_total_price")
    private BigDecimal maxDiscountAmount;

    @Column(name = "reduced_conditions")
    private BigDecimal reducedConditions;

    @Column(name = "quantity")
    private Integer quantity;
    // Số lượt sử dụng còn lại

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "status_discount_code")
    @Enumerated(EnumType.ORDINAL)
    private LoaiGiamGia statusDiscountCode;
}
