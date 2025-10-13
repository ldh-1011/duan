package udpm.hn.server.test.core.staff.product.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import udpm.hn.server.test.core.common.base.PageableRequest;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest extends PageableRequest {
    String keyword;

    Integer status;
}
