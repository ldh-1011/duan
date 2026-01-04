package udpm.hn.server.test.core.staff.discount.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCodeResponse {
    private String code;
    private String type;
}
