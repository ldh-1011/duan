package udpm.hn.server.test.infrastructure.cloudinary.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloudinaryResponse {
    private String publicId;

    private String url;
}
