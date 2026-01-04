package udpm.hn.server.test.core.staff.order.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import udpm.hn.server.test.core.common.base.PageableRequest;
import udpm.hn.server.test.infrastructure.constant.OrderStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest extends PageableRequest {
    private String code;
    private List<OrderStatus> statusList;
}
