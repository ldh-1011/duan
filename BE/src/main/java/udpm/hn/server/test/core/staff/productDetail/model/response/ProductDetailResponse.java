package udpm.hn.server.test.core.staff.productDetail.model.response;

import java.math.BigDecimal;

public interface ProductDetailResponse {
    String getId();

    BigDecimal getPrice();

    String getImg();

    String getColorName();

    String getVersionName();

    String getStorageCapacityName();

    String getStatus();
}
