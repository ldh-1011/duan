package udpm.hn.server.test.core.staff.productDetail.repository;

import org.springframework.stereotype.Repository;
import udpm.hn.server.test.entity.Version;
import udpm.hn.server.test.repository.VersionRepository;

import java.util.Optional;

@Repository
public interface ProductDetailVersionRepository extends VersionRepository {
    Optional<Version> findByVersionName(String versionName);
}
