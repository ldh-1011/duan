package udpm.hn.server.test.infrastructure.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        final Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dcsc0x1qq");
        config.put("api_key", "664669599186274");
        config.put("api_secret", "I9HjUt1ptysm6BQ2RYyoLUA9yW0");
        return new Cloudinary(config);
    }
}
