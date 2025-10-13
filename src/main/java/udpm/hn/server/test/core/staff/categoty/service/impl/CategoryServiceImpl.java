package udpm.hn.server.test.core.staff.categoty.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import udpm.hn.server.test.core.staff.categoty.repository.ADCategoryRepository;
import udpm.hn.server.test.core.staff.categoty.service.CategoryService;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.entity.Category;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final ADCategoryRepository categoryRepository;

    @Override
    public ResponseObject<?> getAllCategory(){
        return new ResponseObject<>(
                categoryRepository.getCategories(),
                HttpStatus.OK,
                "Lấy thành công category"
        );
    }

    @Override
    public ResponseObject<?> findCategoryId(String id){
        return new ResponseObject<>(
                categoryRepository.findByCategoryId(id),
                HttpStatus.OK,
                "Lay thanh cong category id"
        );
    }

    @Override
    public ResponseObject<?> addCategory(Category category){
        Optional<Category> optionalCategory = categoryRepository.findByCategoryCode(category.getCategoryCode());
        if(optionalCategory.isPresent()){
            return new ResponseObject<>(null , HttpStatus.CONFLICT , "mã category này đã tồn tại");
        }
        category.setCategoryCode(category.getCategoryCode());
        category.setCategoryName(category.getCategoryName());
        categoryRepository.save(category);

        return new ResponseObject<>(null , HttpStatus.OK , "Thêm thành công");
    }

    @Override
    public ResponseObject<?> updateCategory(String id, Category category) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            return new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Không tìm thấy category");
        }

        Category old = optional.get();

        Optional<Category> duplicate = categoryRepository.findByCategoryCode(category.getCategoryCode());
        if (duplicate.isPresent() && !duplicate.get().getId().equals(id)) {
            return new ResponseObject<>(null, HttpStatus.CONFLICT, "Mã category đã tồn tại");
        }

        old.setCategoryName(category.getCategoryName());
        old.setCategoryCode(category.getCategoryCode());

        categoryRepository.save(old);
        return new ResponseObject<>(old, HttpStatus.OK, "Cập nhật thành công");
    }

    @Override
    public ResponseObject<?> deleteCategory(String id){
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND , "Không tìm thấy category");
        }
        categoryRepository.deleteById(id);

        return new ResponseObject<>(null , HttpStatus.OK , "xóa thành công");
    }

}
