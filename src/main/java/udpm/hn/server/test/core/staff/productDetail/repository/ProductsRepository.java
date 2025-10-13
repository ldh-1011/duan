package udpm.hn.server.test.core.staff.productDetail.repository;

import org.springframework.stereotype.Repository;
import udpm.hn.server.test.entity.Product;
import udpm.hn.server.test.repository.ProductRepository;

import java.util.Optional;

@Repository
public interface ProductsRepository extends ProductRepository {
    Optional<Product> findByProductName(String productName);
}
