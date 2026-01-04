package udpm.hn.server.test.core.staff.discount.service;

import org.springframework.scheduling.annotation.Scheduled;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.core.staff.discount.model.request.AddDiscountCode;
import udpm.hn.server.test.core.staff.discount.model.request.DiscountCodeRequest;
import udpm.hn.server.test.entity.DiscountCode;

public interface DiscountCodeService {
    ResponseObject<?> search(DiscountCodeRequest request);

    ResponseObject<?> getAllDiscountCode(DiscountCodeRequest request);

    ResponseObject<?> getAllDiscountCodeAndOrder(DiscountCodeRequest request);

    ResponseObject<?> addAndUpdateDiscountCode(AddDiscountCode addDiscountCode);

    ResponseObject<?> changeStatus(DiscountCodeRequest request);

    ResponseObject<?> getDetailDiscountCode(DiscountCodeRequest request);
}
