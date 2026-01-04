package udpm.hn.server.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.entity.Order;
import udpm.hn.server.test.entity.OrdersDetail;

import java.util.List;

@Repository
public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, String> {
    List<OrdersDetail> findByOrder(Order order);
}
