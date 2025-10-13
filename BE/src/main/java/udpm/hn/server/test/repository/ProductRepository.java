package udpm.hn.server.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
