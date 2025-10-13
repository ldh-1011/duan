package udpm.hn.server.test.core.staff.product.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.entity.Category;
import udpm.hn.server.test.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryProductRepository extends CategoryRepository {
    Optional<Category> findByCategoryCode(String categoryCode);

    @Query(value = "SELECT category_code AS categoryCode FROM category" , nativeQuery = true)
    List<String> findAllCategoryCode();
}
