package udpm.hn.server.test.core.staff.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udpm.hn.server.test.core.staff.order.model.request.AddAndUpdateRequest;
import udpm.hn.server.test.core.staff.order.model.request.OrderRequest;
import udpm.hn.server.test.core.staff.order.service.OrderService;
import udpm.hn.server.test.infrastructure.constant.MappingConstants;
import udpm.hn.server.test.utils.Helper;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/all")
    public ResponseEntity<?> getAllOrders(@RequestBody OrderRequest request) {
        return Helper.createResponseEntity(orderService.getAllOrder(request));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@RequestBody AddAndUpdateRequest request) {
        return Helper.createResponseEntity(orderService.addOrderTaiQuay(request));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateOrder(@RequestBody AddAndUpdateRequest request) {
        return Helper.createResponseEntity(orderService.updateOrderTaiQuay(request));
    }
}
