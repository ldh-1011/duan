package udpm.hn.server.test.entity;

import jakarta.persistence.*;
import lombok.*;
import udpm.hn.server.test.entity.base.PrimaryEntity;
import udpm.hn.server.test.infrastructure.constant.OrderStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "order_history")
public class OrderHistory extends PrimaryEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "old_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus oldStatus;

    @Column(name = "new_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus newStatus;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "link_image")
    private String linkImage;

    @Column(name = "color")
    private String color;

    @Column(name = "note")
    private String note;

    @Column(name = "changed_at")
    private LocalDateTime changedAt;

    @Column(name = "changed_by")
    private String changedBy; // SYSTEM / STAFF / USER
}
