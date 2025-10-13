package udpm.hn.server.test.core.staff.categoty.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.core.staff.categoty.model.response.CategoryResponse;
import udpm.hn.server.test.entity.Category;
import udpm.hn.server.test.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ADCategoryRepository extends CategoryRepository {
    @Query(value = """
     SELECT id AS id,
            category_code AS categoryCode,
            category_name AS categoryName
     FROM category
     """ , nativeQuery = true)
    List<CategoryResponse> getCategories();

    Optional<Category> findByCategoryCode(String code);

    @Query(value = """
     SELECT id AS id,
            category_code AS categoryCode,
            category_name AS categoryName
     FROM category
     WHERE id = :idCategory
     """ , nativeQuery = true)
    Optional<CategoryResponse> findByCategoryId(@Param("idCategory") String idCategory);

}
