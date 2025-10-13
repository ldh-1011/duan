package udpm.hn.server.test.core.staff.productDetail.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateProductDetailRequest{

    private String productName;

    private BigDecimal price;

    private String colorName;

    private String versionName;

    private String storageCapacityName;
}
