package udpm.hn.server.test.core.staff.categoty.service;

import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.entity.Category;

public interface CategoryService {
    ResponseObject<?> getAllCategory();

    ResponseObject<?> findCategoryId(String id);

    ResponseObject<?> addCategory(Category category);

    ResponseObject<?> updateCategory(String id, Category category);

    ResponseObject<?> deleteCategory(String id);
}
