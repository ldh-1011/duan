package udpm.hn.server.test.core.staff.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.core.staff.product.model.request.CreateUpdateProductRequest;
import udpm.hn.server.test.core.staff.product.model.request.ProductRequest;
import udpm.hn.server.test.core.staff.product.repository.*;
import udpm.hn.server.test.core.staff.product.service.ProductService;
import udpm.hn.server.test.entity.*;
import udpm.hn.server.test.infrastructure.constant.EntityStatus;
import udpm.hn.server.test.utils.Helper;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ADProductRepository productRepository;

    private final CategoryProductRepository categoryProductRepository;

    private final IsoProductRepository isoProductRepository;

    private final ProcessorProductRepository processorProductRepository;

    private final ResolutionProductRepository resolutionProductRepository;

    private final SensorProductRepository sensorProductRepository;


    @Override
    public ResponseObject<?> getAllProducts(ProductRequest productRequest) {
        Pageable pageable = Helper.createPageable(productRequest , "id");

        return new ResponseObject<>(
                productRepository.getAllProducts(
                        productRequest.getKeyword(),
                        productRequest.getStatus(),
                        pageable
                ),
                HttpStatus.OK,
                "lây thành công product"
        );
    }

    @Override
    public ResponseObject<?> getAllProcessor(){
        return new ResponseObject<>(
                processorProductRepository.findAllProcessorNames(),
                HttpStatus.OK,
                "lay thang cong"
        );
    }

    @Override
    public ResponseObject<?> getAllCategory(){
        return new ResponseObject<>(
                categoryProductRepository.findAllCategoryCode(),
                HttpStatus.OK,
                "lat thanh cong category"
        );
    }

    @Override
    public ResponseObject<?> getAllIso(){
        return new ResponseObject<>(
                isoProductRepository.findIsoNames(),
                HttpStatus.OK,
                "lay thanh cong iso"
        );
    }

    @Override
    public  ResponseObject<?> getAllResolution(){
        return new ResponseObject<>(
                resolutionProductRepository.findResolutionNames(),
                HttpStatus.OK,
                "lay thanh cong resolution"
        );
    }

    @Override
    public ResponseObject<?> getAllSensor(){
        return new ResponseObject<>(
                sensorProductRepository.getSensorNames(),
                HttpStatus.OK,
                "lay thanh cong sensor"
        );
    }

    @Override
    public ResponseObject<?> findByProductId(String id){
        return new ResponseObject<>(
                productRepository.findByProductId(id),
                HttpStatus.OK,
                "lay thanh con chi tiet"
        );
    }

    @Override
    public ResponseObject<?> addProduct(CreateUpdateProductRequest request){
        Optional<Product> optionalProduct = productRepository.findByProductCode(request.getProductCode());
        if(optionalProduct.isPresent()){
            return new ResponseObject<>(null , HttpStatus.CONFLICT , "ma product nay da ton tai");
        }

        Optional<Category> optionalCategory = categoryProductRepository.findByCategoryCode(request.getCategoryCode());
        if(optionalCategory.isEmpty()){
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND , "Không tìm thấy category này");
        }

        Optional<Iso> optionalIso = isoProductRepository.findByIsoName(request.getIsoName());
        if(optionalIso.isEmpty()){
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND , "khong tim thay iso");
        }

        Optional<Processor> optionalProcessor = processorProductRepository.findByProcessorName(request.getProcessorName());
        if(optionalProcessor.isEmpty()){
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND , "khong tim thay processor");
        }

        Optional<Resolution> optionalResolution = resolutionProductRepository.findByResolutionName(request.getResolutionName());
        if(optionalResolution.isEmpty()){
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND , "khong tim thay resolution");
        }

        Optional<Sensor> optionalSensor = sensorProductRepository.findBySensorName(request.getSensorName());
        if(optionalSensor.isEmpty()){
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND , "khong tim thay sensor");
        }

        Product product = new Product();
        product.setProductCode(request.getProductCode());
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());

        product.setCategory(optionalCategory.get());
        product.setIso(optionalIso.get());
        product.setProcessor(optionalProcessor.get());
        product.setResolution(optionalResolution.get());
        product.setSensor(optionalSensor.get());

        productRepository.save(product);

        return new ResponseObject<>(null , HttpStatus.ACCEPTED , "them thanh cong");
    }

    @Override
    public ResponseObject<?> updateProduct(String id, CreateUpdateProductRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Không tìm thấy product");
        }

        Product product = optionalProduct.get();

        Category category = categoryProductRepository.findByCategoryCode(request.getCategoryCode())
                .orElseThrow(() -> new RuntimeException("Category không tồn tại"));

        Iso iso = isoProductRepository.findByIsoName(request.getIsoName())
                .orElseThrow(() -> new RuntimeException("ISO không tồn tại"));

        Processor processor = processorProductRepository.findByProcessorName(request.getProcessorName())
                .orElseThrow(() -> new RuntimeException("Processor không tồn tại"));

        Resolution resolution = resolutionProductRepository.findByResolutionName(request.getResolutionName())
                .orElseThrow(() -> new RuntimeException("Resolution không tồn tại"));

        Sensor sensor = sensorProductRepository.findBySensorName(request.getSensorName())
                .orElseThrow(() -> new RuntimeException("Sensor không tồn tại"));

        product.setProductCode(request.getProductCode());
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setCategory(category);
        product.setIso(iso);
        product.setProcessor(processor);
        product.setResolution(resolution);
        product.setSensor(sensor);

        productRepository.save(product);
        return new ResponseObject<>(product, HttpStatus.ACCEPTED, "Update thành công");
    }

    @Override
    public ResponseObject<?> deleteProduct(String id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            return new ResponseObject<>(null ,HttpStatus.NOT_FOUND , "id nay k ton tai");
        }
        productRepository.deleteById(id);

        return new ResponseObject<>(null , HttpStatus.OK , "xoa thanh cong");
    }

    @Override
    public ResponseObject<?> statusChange(String id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            return new ResponseObject<>(null ,HttpStatus.NOT_FOUND , "id nay k ton tai");
        }

        Product product = optionalProduct.get();
        if(product.getStatus() == EntityStatus.ACTIVE){
            product.setStatus(EntityStatus.INACTIVE);
        } else {
            product.setStatus(EntityStatus.ACTIVE);
        }
        productRepository.save(product);

        return new ResponseObject<>(null , HttpStatus.ACCEPTED , "thay doi trang thai thanh cong");
    }
}
