package udpm.hn.server.test.core.staff.img.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.core.staff.img.service.ImgService;
import udpm.hn.server.test.entity.Img;
import udpm.hn.server.test.entity.ProductDetail;
import udpm.hn.server.test.repository.ImgRepository;
import udpm.hn.server.test.repository.ProductDetailRepository;

import java.io.File;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImgServiceImpl implements ImgService {

    private final ImgRepository imgRepository;

    private final ProductDetailRepository productDetailRepository;

    @Override
    public ResponseObject<?> getAllImgAndId(String id) {
        return new ResponseObject<>(
                imgRepository.findByProductDetailId(id),
                HttpStatus.OK,
                "Lấy thành công danh sách ảnh của product detail"
        );
    }

    @Override
    public ResponseObject<?> uploadImgFile(String productDetailId, MultipartFile file) {
        try {
            Optional<ProductDetail> optionalProductDetail = productDetailRepository.findById(productDetailId);
            if (optionalProductDetail.isEmpty()) {
                return new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Không tìm thấy product detail để gán ảnh");
            }

            // ✅ 1. Lấy đường dẫn tuyệt đối đến thư mục upload trong project
            String uploadDirPath = System.getProperty("user.dir") + File.separator + "uploads";
            File uploadDir = new File(uploadDirPath);

            // ✅ 2. Tạo thư mục nếu chưa tồn tại
            if (!uploadDir.exists()) {
                boolean created = uploadDir.mkdirs();
                if (!created) {
                    return new ResponseObject<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Không thể tạo thư mục upload");
                }
            }

            // ✅ 3. Tạo đường dẫn file đầy đủ
            String fileName = file.getOriginalFilename();
            File destinationFile = new File(uploadDir, fileName);

            // ✅ 4. Lưu file vào thư mục uploads thực tế (trong project)
            file.transferTo(destinationFile);

            // ✅ 5. Lưu link ảnh (đường dẫn truy cập API)
            Img img = new Img();
            img.setLinkImage("/uploads/" + fileName);
            img.setProductDetail(optionalProductDetail.get());
            imgRepository.save(img);

            return new ResponseObject<>(img, HttpStatus.CREATED, "Upload ảnh thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseObject<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi upload ảnh: " + e.getMessage());
        }
    }

}
