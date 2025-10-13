package udpm.hn.server.test.core.staff.productDetail.repository;

import org.springframework.stereotype.Repository;
import udpm.hn.server.test.entity.Color;
import udpm.hn.server.test.repository.ColorRepository;

import java.util.Optional;

@Repository
public interface ProductDetailColorRepository extends ColorRepository {
    Optional<Color> findByColorName(String name);
}
