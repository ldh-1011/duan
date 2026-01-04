package udpm.hn.server.test.core.staff.order.service;

import jakarta.transaction.Transactional;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.core.staff.order.model.request.AddAndUpdateRequest;
import udpm.hn.server.test.core.staff.order.model.request.OrderRequest;

public interface OrderService {
    ResponseObject<?> getAllOrder(OrderRequest request);

    ResponseObject<?> addOrderTaiQuay(AddAndUpdateRequest request);

    @Transactional
    ResponseObject<?> updateOrderTaiQuay(AddAndUpdateRequest request);
}
