package udpm.hn.server.test.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, String> {
}
