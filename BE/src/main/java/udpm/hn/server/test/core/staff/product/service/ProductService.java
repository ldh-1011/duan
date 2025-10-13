package udpm.hn.server.test.core.staff.product.service;

import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.core.staff.product.model.request.CreateUpdateProductRequest;
import udpm.hn.server.test.core.staff.product.model.request.ProductRequest;

public interface ProductService {
    ResponseObject<?> getAllProducts(ProductRequest productRequest);

    ResponseObject<?> getAllProcessor();

    ResponseObject<?> getAllCategory();

    ResponseObject<?> getAllIso();

    ResponseObject<?> getAllResolution();

    ResponseObject<?> getAllSensor();

    ResponseObject<?> findByProductId(String id);

    ResponseObject<?> addProduct(CreateUpdateProductRequest request);

    ResponseObject<?> updateProduct(String id, CreateUpdateProductRequest request);

    ResponseObject<?> deleteProduct(String id);

    ResponseObject<?> statusChange(String id);
}
