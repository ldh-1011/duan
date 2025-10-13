package udpm.hn.server.test.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import udpm.hn.server.test.core.common.base.PageableRequest;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.infrastructure.constant.PaginationConstant;

@Slf4j
public class Helper {

    public static Pageable createPageable(PageableRequest request, String defaultSortBy) {
        return PageRequest.of(
                request.getPage() - 1,
                request.getSize() == 0 ? PaginationConstant.DEFAULT_SIZE : request.getSize(),
                Sort.by(
                        (Sort.Direction.fromString(
                                request.getOrderBy()) == Sort.Direction.DESC ||
                                request.getOrderBy() == null
                        ) ? Sort.Direction.DESC : Sort.Direction.ASC,
                        (request.getSortBy() == null
                                || request.getSortBy().isEmpty()
                        ) ? defaultSortBy : request.getSortBy()
                ));
    }

    public static ResponseEntity<?> createResponseEntity(ResponseObject<?> responseObject) {
        return new ResponseEntity<>(responseObject, responseObject.getStatus());
    }
}