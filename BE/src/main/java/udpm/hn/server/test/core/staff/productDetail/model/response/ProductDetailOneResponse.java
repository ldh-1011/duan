package udpm.hn.server.test.core.staff.productDetail.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import udpm.hn.server.test.infrastructure.constant.EntityStatus;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailOneResponse {
    private String id;
    private BigDecimal price;
    private String name;
    private String img;
    private String color;
    private String versionName;
    private String storageCapacityName;
    private EntityStatus status;
    private int quantity;
    private long quantitySerial;
}
