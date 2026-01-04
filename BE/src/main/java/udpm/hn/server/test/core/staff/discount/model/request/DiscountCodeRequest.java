package udpm.hn.server.test.core.staff.discount.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.repository.query.Param;
import udpm.hn.server.test.core.common.base.PageableRequest;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCodeRequest extends PageableRequest {
    private String id;
    private String keyword;
    private BigDecimal billTotal;
    private String code;
    private BigDecimal minMoney;
    private BigDecimal percent;
}
