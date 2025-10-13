package udpm.hn.server.test.core.staff.productDetail.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.core.staff.productDetail.model.request.CreateUpdateProductDetailRequest;
import udpm.hn.server.test.core.staff.productDetail.model.request.ProductDetailRequest;
import udpm.hn.server.test.core.staff.productDetail.repository.*;
import udpm.hn.server.test.core.staff.productDetail.service.ProductDetailService;
import udpm.hn.server.test.entity.*;
import udpm.hn.server.test.infrastructure.constant.EntityStatus;
import udpm.hn.server.test.utils.Helper;

import java.math.BigDecimal;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ADProductDetailRepository productDetailRepository;

    private final ProductDetailColorRepository detailColorRepository;

    private final ProductDetailStorageCapacityRepository productDetailStorageCapacityRepository;

    private final ProductDetailVersionRepository productDetailVersionRepository;

    private final ProductsRepository productsRepository;

    @Override
    public ResponseObject<?> getAllProductDetails(ProductDetailRequest request) {
        Pageable pageable = Helper.createPageable(request, "id");
        BigDecimal minPrice = request.getMinPrice();
        BigDecimal maxPrice = request.getMaxPrice();

        if (minPrice == null && maxPrice != null) {
            minPrice = BigDecimal.ZERO;
        } else if (maxPrice == null && minPrice != null) {
            maxPrice = new BigDecimal("999999999999");
        }

        return new ResponseObject<>(
                productDetailRepository.getAllProductDetail(
                        minPrice,
                        maxPrice,
                        request.getProductName(),
                        request.getProductCode(),
                        request.getColorName(),
                        request.getVersionName(),
                        request.getStorageCapacityName(),
                        request.getStatus(),
                        pageable
                ),
                HttpStatus.OK,
                "Lấy thành công product detail"
        );
    }

    @Override
    public ResponseObject<?> addProductDetail(CreateUpdateProductDetailRequest request){
        if (request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return new ResponseObject<>(
                    null,
                    HttpStatus.BAD_REQUEST,
                    "Giá sản phẩm phải lớn hơn 0"
            );
        }

        Optional<Color> optionalColor = detailColorRepository.findByColorName(request.getColorName());
        if (optionalColor.isEmpty()) {
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND ,"khong tim thay color" );
        }

        Optional<StorageCapacity> optionalStorageCapacity = productDetailStorageCapacityRepository.findByStorageCapacityName(request.getStorageCapacityName());
        if (optionalStorageCapacity.isEmpty()) {
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND ,"khong tim thay storage capacity" );
        }

        Optional<Version> optionalVersion = productDetailVersionRepository.findByVersionName(request.getVersionName());
        if (optionalVersion.isEmpty()) {
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND ,"khong tim thay version" );
        }

        Optional<Product> optionalProduct = productsRepository.findByProductName(request.getProductName());
        if (optionalProduct.isEmpty()) {
            return new ResponseObject<>(new Product(), HttpStatus.NOT_FOUND ,"khong tim thay product" );
        }
        ProductDetail productDetail = new ProductDetail();
        productDetail.setPrice(request.getPrice());

        productDetail.setColor(optionalColor.get());
        productDetail.setStorageCapacity(optionalStorageCapacity.get());
        productDetail.setVersion(optionalVersion.get());
        productDetail.setProduct(optionalProduct.get());
        productDetailRepository.save(productDetail);

        return new ResponseObject<>(productDetail , HttpStatus.CREATED , "them thanh cong product detail" );
    }

    @Override
    public ResponseObject<?> updateProductDetail(String id, CreateUpdateProductDetailRequest request){
        Optional<ProductDetail> optionalProductDetail = productDetailRepository.findById(id);
        if (optionalProductDetail.isEmpty()) {
            return new ResponseObject<>(new ProductDetail(), HttpStatus.NOT_FOUND ,"khong tim thay product detail" );
        }

        if (request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return new ResponseObject<>(
                    null,
                    HttpStatus.BAD_REQUEST,
                    "Giá sản phẩm phải lớn hơn 0"
            );
        }

        Optional<Color> optionalColor = detailColorRepository.findByColorName(request.getColorName());
        if (optionalColor.isEmpty()) {
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND ,"khong tim thay color" );
        }

        Optional<StorageCapacity> optionalStorageCapacity = productDetailStorageCapacityRepository.findByStorageCapacityName(request.getStorageCapacityName());
        if (optionalStorageCapacity.isEmpty()) {
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND ,"khong tim thay storage capacity" );
        }

        Optional<Version> optionalVersion = productDetailVersionRepository.findByVersionName(request.getVersionName());
        if (optionalVersion.isEmpty()) {
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND ,"khong tim thay version" );
        }

        Optional<Product> optionalProduct = productsRepository.findByProductName(request.getProductName());
        if (optionalProduct.isEmpty()) {
            return new ResponseObject<>(new Product(), HttpStatus.NOT_FOUND ,"khong tim thay product" );
        }

        ProductDetail productDetail = optionalProductDetail.get();
        productDetail.setPrice(request.getPrice());
        productDetail.setColor(optionalColor.get());
        productDetail.setStorageCapacity(optionalStorageCapacity.get());
        productDetail.setVersion(optionalVersion.get());
        productDetail.setProduct(optionalProduct.get());
        productDetailRepository.save(productDetail);

        return new ResponseObject<>(null , HttpStatus.CREATED , "update thanh cong product detail" );
    }

    @Override
    public ResponseObject<?> deleteProductDetail(String id){
        Optional<ProductDetail> optionalProductDetail = productDetailRepository.findById(id);
        if (optionalProductDetail.isEmpty()) {
            return new ResponseObject<>(new ProductDetail(), HttpStatus.NOT_FOUND ,"khong tim thay product detail" );
        }
        productDetailRepository.deleteById(id);

        return new ResponseObject<>(null , HttpStatus.CREATED , "delete thanh cong product detail" );
    }

    @Override
    public ResponseObject<?> changeStatus(String id){
        Optional<ProductDetail> optionalProductDetail = productDetailRepository.findById(id);

        if (optionalProductDetail.isEmpty()) {
            return new ResponseObject<>(new ProductDetail(), HttpStatus.NOT_FOUND ,"khong tim thay product detail" );
        }

        ProductDetail productDetail = optionalProductDetail.get();
        if(productDetail.getStatus() == EntityStatus.ACTIVE){
            productDetail.setStatus(EntityStatus.INACTIVE);
        }else {
            productDetail.setStatus(EntityStatus.ACTIVE);
        }

        productDetailRepository.save(productDetail);

        return new ResponseObject<>(null , HttpStatus.ACCEPTED , "thay doi trang thai thanh cong" );
    }
}
