package udpm.hn.server.test.core.staff.serial.service;

import org.springframework.web.multipart.MultipartFile;
import udpm.hn.server.test.core.common.base.ResponseObject;

public interface SerialService {

    ResponseObject<?> uploadSerial(MultipartFile file, String productDetailId);
}
