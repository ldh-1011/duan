package udpm.hn.server.test.core.staff.img.service;

import org.springframework.web.multipart.MultipartFile;
import udpm.hn.server.test.core.common.base.ResponseObject;

public interface ImgService {
    ResponseObject<?> getAllImgAndId(String id);

    ResponseObject<?> uploadImgFile(String id, MultipartFile file);

}
