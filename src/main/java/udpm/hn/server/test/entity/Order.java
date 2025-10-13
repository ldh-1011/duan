package udpm.hn.server.test.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import udpm.hn.server.test.entity.base.PrimaryEntity;
import udpm.hn.server.test.infrastructure.constant.OrderStatus;
import udpm.hn.server.test.infrastructure.constant.PaymentStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicUpdate
@Entity
@Table(name = "orders")
public class Order extends PrimaryEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer customerId;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "order_status")
    private OrderStatus orderStatus;
}
