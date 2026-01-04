package udpm.hn.server.test.core.staff.productDetail.service;

import org.springframework.web.multipart.MultipartFile;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.core.staff.productDetail.model.request.CreateUpdateProductDetailRequest;
import udpm.hn.server.test.core.staff.productDetail.model.request.ProductDetailRequest;

public interface ProductDetailService {
    ResponseObject<?> getAllProductDetails(ProductDetailRequest request);

    ResponseObject<?> addProductDetail(CreateUpdateProductDetailRequest request , MultipartFile[] images);

    ResponseObject<?> updateProductDetail(String id, CreateUpdateProductDetailRequest request);

    ResponseObject<?> deleteProductDetail(String id);

    ResponseObject<?> changeStatus(String id);

    ResponseObject<?> getOneProductDetail(String id);
}
