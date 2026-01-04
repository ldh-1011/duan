package udpm.hn.server.test.core.staff.discount.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiscountResponse {
    private String code;
    private String type;
    private BigDecimal billTotal;
}
