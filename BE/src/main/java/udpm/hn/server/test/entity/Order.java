package udpm.hn.server.test.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import udpm.hn.server.test.entity.base.PrimaryEntity;
import udpm.hn.server.test.infrastructure.constant.OrderStatus;
import udpm.hn.server.test.infrastructure.constant.OrderTypeEnum;
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

    @Column(name = "invoice_code")
    private String invoiceCode;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "order_type")
    @Enumerated(EnumType.STRING)
    private OrderTypeEnum orderType;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;// ngày tạo đơn

    @ManyToOne
    @JoinColumn(name = "discountCode_id")
    private DiscountCode discountCode;

    @Column(name = "money_given_by_guests")
    private BigDecimal moneyGivenByGuests;

    @Column(name = "change")
    private BigDecimal change;

    @Column(name = "money_reduced")
    private BigDecimal moneyReduced;

    @ManyToOne
    @JoinColumn(name = "sales_staff_id")
    private Staff salesStaff;
}
