package udpm.hn.server.test.core.staff.discount.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import udpm.hn.server.test.core.staff.discount.model.request.AddDiscountCode;
import udpm.hn.server.test.core.staff.discount.model.request.DiscountCodeRequest;
import udpm.hn.server.test.core.staff.discount.service.DiscountCodeService;
import udpm.hn.server.test.utils.Helper;

@RestController
@RequestMapping("/api/discount-code")
@RequiredArgsConstructor
public class DiscountCodeController {
    private final DiscountCodeService discountCodeService;


    @GetMapping("/all")
    public ResponseEntity<?> getAll(DiscountCodeRequest request) {
        return Helper.createResponseEntity(discountCodeService.search(request));
    }

    @GetMapping("/allCodeAndDiscountPercent")
    public ResponseEntity<?> getAllDiscountCodes(DiscountCodeRequest request) {
        return Helper.createResponseEntity(discountCodeService.getAllDiscountCode(request));
    }

    @PostMapping("/saveDiscountCode")
    public ResponseEntity<?> addDiscountCode(@RequestBody @Validated AddDiscountCode request) {
        return Helper.createResponseEntity(discountCodeService.addAndUpdateDiscountCode(request));
    }

    @PostMapping("/getAllDiscountCodeAndOrder")
    public ResponseEntity<?> getAllDiscountCodeAndOrder(@RequestBody DiscountCodeRequest request) {
        return Helper.createResponseEntity(discountCodeService.getAllDiscountCodeAndOrder(request));
    }

    @PostMapping("/changeStatus")
    private ResponseEntity<?> changeStatus(@RequestBody DiscountCodeRequest request) {
        return Helper.createResponseEntity(discountCodeService.changeStatus(request));
    }

    @PostMapping("/detailDiscountCode")
    public ResponseEntity<?> detailDiscountCode(@RequestBody DiscountCodeRequest request) {
        return Helper.createResponseEntity(discountCodeService.getDetailDiscountCode(request));
    }
}
