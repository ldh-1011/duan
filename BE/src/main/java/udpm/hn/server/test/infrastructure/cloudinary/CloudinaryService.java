package udpm.hn.server.test.infrastructure.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import udpm.hn.server.test.infrastructure.cloudinary.response.CloudinaryResponse;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryResponse uploadImage(MultipartFile file, String folder) {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder,
                            "resource_type", "image"
                    )
            );

            return new CloudinaryResponse(
                    uploadResult.get("public_id").toString(),
                    uploadResult.get("secure_url").toString()
            );
        } catch (Exception e) {
            throw new RuntimeException("Upload image failed", e);
        }
    }
}
