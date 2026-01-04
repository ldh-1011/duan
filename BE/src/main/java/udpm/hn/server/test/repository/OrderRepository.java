package udpm.hn.server.test.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import udpm.hn.server.test.core.staff.order.model.response.OrderResponse;
import udpm.hn.server.test.entity.Customer;
import udpm.hn.server.test.entity.Order;
import udpm.hn.server.test.infrastructure.constant.OrderStatus;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("""
            SELECT new udpm.hn.server.test.core.staff.order.model.response.OrderResponse(
                u.id,
                u.invoiceCode,
                u.totalAmount,
                u.bookingDate,
                u.orderType,
                u.paymentStatus
            )
            FROM Order u
            WHERE u.status IN :statusList
             AND (:code IS NULL OR u.invoiceCode LIKE %:code%)
            """)
    Page<OrderResponse> findAllOrder(String code, List<OrderStatus> statusList, Pageable pageable);

    Optional<Order> findByCustomerIdAndSalesStaff_IdAndOrderStatus(
            Customer customer,
            String staffId,
            OrderStatus orderStatus
    );


}
