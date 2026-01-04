package udpm.hn.server.test.core.staff.serial.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.core.staff.serial.service.SerialService;
import udpm.hn.server.test.entity.ProductDetail;
import udpm.hn.server.test.entity.Serial;
import udpm.hn.server.test.infrastructure.constant.EntityStatus;
import udpm.hn.server.test.repository.ProductDetailRepository;
import udpm.hn.server.test.repository.SerialRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SerialServiceImpl implements SerialService {

    private final SerialRepository serialRepository;

    private final ProductDetailRepository productDetailRepository;

    @Override
    public ResponseObject<?> uploadSerialInactive(MultipartFile file, String productDetailId) {
        if (file == null || file.isEmpty()) {
            return new ResponseObject<>(null, HttpStatus.BAD_REQUEST, "File không được để trống");
        }

        Optional<ProductDetail> optionalProductDetail = productDetailRepository.findById(productDetailId);
        if (optionalProductDetail.isEmpty()) {
            return new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Không tìm thấy Product Detail với ID: " + productDetailId);
        }
        ProductDetail productDetail = optionalProductDetail.get();

        List<String> serialList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell cell = row.getCell(0); // cột 0 là Serial
                if (cell != null) {
                    cell.setCellType(CellType.STRING);
                    String serial = cell.getStringCellValue().trim();
                    if (!serial.isEmpty()) {
                        serialList.add(serial);
                    }
                }
            }

            if (serialList.isEmpty()) {
                return new ResponseObject<>(null, HttpStatus.BAD_REQUEST, "Không có serial hợp lệ trong file");
            }

            for (String serial : serialList) {
                Serial entity = new Serial();
                entity.setSerialNumber(serial);
                entity.setProductDetail(productDetail);
                entity.setStatus(EntityStatus.INACTIVE);
                serialRepository.save(entity);
            }

            int newQuantity = (productDetail.getQuantitySeril() == null ? 0 : productDetail.getQuantitySeril()) + serialList.size();
            if(newQuantity > productDetail.getStockQuantity()){
                return new ResponseObject<>(null , HttpStatus.CONFLICT , "Số lượng serial k đc lớn hơn số lượng tồn kho");
            }
            productDetail.setQuantitySeril(newQuantity);
            productDetailRepository.save(productDetail);

            return new ResponseObject<>(
                    serialList,
                    HttpStatus.OK,
                    "Upload thành công " + serialList.size() + " serial cho ProductDetail ID: " + productDetailId
            );

        } catch (IOException e) {
            return new ResponseObject<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi đọc file Excel");
        } catch (Exception e) {
            return new ResponseObject<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi xử lý dữ liệu: " + e.getMessage());
        }
    }
}
