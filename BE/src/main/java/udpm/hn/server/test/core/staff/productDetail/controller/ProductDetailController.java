package udpm.hn.server.test.core.staff.productDetail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import udpm.hn.server.test.core.staff.productDetail.model.request.CreateUpdateProductDetailRequest;
import udpm.hn.server.test.core.staff.productDetail.model.request.ProductDetailRequest;
import udpm.hn.server.test.core.staff.productDetail.service.ProductDetailService;
import udpm.hn.server.test.infrastructure.constant.MappingConstants;
import udpm.hn.server.test.utils.Helper;

@RestController
@RequestMapping(MappingConstants.API_STAFF_STORAGE_PRODUCT_DETAIL)
@RequiredArgsConstructor
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllProductDetail(ProductDetailRequest request){
        return Helper.createResponseEntity(productDetailService.getAllProductDetails(request));
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProductDetail(@RequestPart("data") CreateUpdateProductDetailRequest request, @RequestPart(value = "images", required = false) MultipartFile[] images) {
        return Helper.createResponseEntity(
                productDetailService.addProductDetail(request, images)
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProductDetail(@PathVariable String id ,@RequestBody CreateUpdateProductDetailRequest request){
        return Helper.createResponseEntity(productDetailService.updateProductDetail(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductDetail(@PathVariable String id){
        return Helper.createResponseEntity(productDetailService.deleteProductDetail(id));
    }

    @PutMapping("/change_status/{id}")
    public ResponseEntity<?> changeProductStatus(@PathVariable String id){
        return Helper.createResponseEntity(productDetailService.changeStatus(id));
    }

    @PostMapping("/chi-tiet")
    private ResponseEntity<?> chiTiet(@RequestBody CreateUpdateProductDetailRequest request){
        return Helper.createResponseEntity(productDetailService.getOneProductDetail(request.getId()));
    }
}
