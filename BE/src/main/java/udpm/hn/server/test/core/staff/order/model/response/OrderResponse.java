package udpm.hn.server.test.core.staff.order.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import udpm.hn.server.test.infrastructure.constant.OrderTypeEnum;
import udpm.hn.server.test.infrastructure.constant.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String id;
    private String code;
    private BigDecimal totalAmount;
    private LocalDateTime bookingDate;
    private OrderTypeEnum orderType;
    private PaymentStatus paymentStatus;
}

