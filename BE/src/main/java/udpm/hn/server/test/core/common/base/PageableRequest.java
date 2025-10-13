package udpm.hn.server.test.core.common.base;

import lombok.*;
import udpm.hn.server.test.infrastructure.constant.PaginationConstant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageableRequest {

    private int page = PaginationConstant.DEFAULT_PAGE;

    private int size = PaginationConstant.DEFAULT_SIZE;

    private String orderBy = PaginationConstant.DEFAULT_ORDER_BY;

    private String sortBy = PaginationConstant.DEFAULT_SORT_BY;

    private String q = PaginationConstant.DEFAULT_Q;

}