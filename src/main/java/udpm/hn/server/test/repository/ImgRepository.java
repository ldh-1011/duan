package udpm.hn.server.test.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.core.staff.img.model.response.ImgResponse;
import udpm.hn.server.test.entity.Img;

import java.util.List;

@Repository
public interface ImgRepository extends CrudRepository<Img, String> {
    @Query(value = """
            SELECT id AS id,
                   link_image AS img
            FROM img
            WHERE product_detail_id = :productDetailId
            """ , nativeQuery = true)
    List<ImgResponse> findByProductDetailId(@Param("productDetailId") String productDetailId);
}
