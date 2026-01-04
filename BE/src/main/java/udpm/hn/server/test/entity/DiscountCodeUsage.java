package udpm.hn.server.test.entity;

import jakarta.persistence.*;
import lombok.*;
import udpm.hn.server.test.entity.base.PrimaryEntity;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "discount_code_usage")
public class DiscountCodeUsage extends PrimaryEntity {

    @ManyToOne
    @JoinColumn(name = "discount_code_id")
    private DiscountCode discountCode;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "used_at")
    private LocalDateTime usedAt;
}