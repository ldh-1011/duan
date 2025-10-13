package udpm.hn.server.test.core.staff.productDetail.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import udpm.hn.server.test.core.common.base.PageableRequest;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailRequest extends PageableRequest {

    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String colorName;
    private String versionName;
    private String storageCapacityName;
    private Integer status;
    private String productName;
    private String productCode;
}
