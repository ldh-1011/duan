package udpm.hn.server.test.core.staff.product.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateProductRequest {

    String productCode;

    String productName;

    String description;

    String productId;

    String categoryId;

    String categoryCode;

    String sensorName;

    String sensorId;

    String isoName;

    String isoId;

    String processorName;

    String processorId;

    String resolutionName;

    String resolutionId;
}
