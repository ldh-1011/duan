package udpm.hn.server.test.core.staff.product.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udpm.hn.server.test.core.staff.product.model.request.CreateUpdateProductRequest;
import udpm.hn.server.test.core.staff.product.model.request.ProductRequest;
import udpm.hn.server.test.core.staff.product.service.ProductService;
import udpm.hn.server.test.infrastructure.constant.MappingConstants;
import udpm.hn.server.test.utils.Helper;

@RestController
@RequestMapping(MappingConstants.API_STAFF_PRODUCT)
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(ProductRequest productRequest) {
        return Helper.createResponseEntity(productService.getAllProducts(productRequest));
    }

    @GetMapping("/all-category")
    public ResponseEntity<?> getAllCategory() {
        return Helper.createResponseEntity(productService.getAllCategory());
    }

    @GetMapping("/all-iso")
    public ResponseEntity<?> getAllIso() {
        return Helper.createResponseEntity(productService.getAllIso());
    }

    @GetMapping("/all-processor")
    public ResponseEntity<?> getAllProcessor() {
        return Helper.createResponseEntity(productService.getAllProcessor());
    }

    @GetMapping("/all-resolution")
    public ResponseEntity<?> getResolution(){
        return Helper.createResponseEntity(productService.getAllResolution());
    }

    @GetMapping("/all-sensor")
    public ResponseEntity<?> getAllSensor(){
        return Helper.createResponseEntity(productService.getAllSensor());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        return Helper.createResponseEntity(productService.findByProductId(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody CreateUpdateProductRequest request) {
        return Helper.createResponseEntity(productService.addProduct(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody CreateUpdateProductRequest request) {
        return Helper.createResponseEntity(productService.updateProduct(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return Helper.createResponseEntity(productService.deleteProduct(id));
    }

    @PutMapping("/change_status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable String id) {
        return Helper.createResponseEntity(productService.statusChange(id));
    }
}
