package udpm.hn.server.test.core.staff.categoty.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udpm.hn.server.test.core.staff.categoty.service.CategoryService;
import udpm.hn.server.test.entity.Category;
import udpm.hn.server.test.infrastructure.constant.MappingConstants;
import udpm.hn.server.test.utils.Helper;

@RestController
@RequestMapping(MappingConstants.API_STAFF_CATEGORY)
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCategories() {
       return Helper.createResponseEntity(categoryService.getAllCategory());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        return Helper.createResponseEntity(categoryService.addCategory(category));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody Category category) {
        return Helper.createResponseEntity(categoryService.updateCategory(id , category));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        return Helper.createResponseEntity(categoryService.deleteCategory(id));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCategory(@PathVariable String id) {
        return Helper.createResponseEntity(categoryService.findCategoryId(id));
    }

}
