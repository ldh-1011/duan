package udpm.hn.server.test.core.staff.order.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductItem {
    private String productDetailId;
    private Integer quantity;
    private String note;
}
