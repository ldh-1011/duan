package udpm.hn.server.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.core.staff.color.model.response.ColorResponse;
import udpm.hn.server.test.entity.Color;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Color, String> {
    @Query(value = "SELECT id AS id , color_name AS colorName FROM color",nativeQuery = true)
    List<ColorResponse> findAllColors();

    Optional<Color> findByColorName(String colorName);

    @Query(value = "SELECT id AS id , color_name AS colorName FROM color WHERE id = :idColor",nativeQuery = true)
    Optional<ColorResponse> findByIdColor(@Param("idColor") String idColor);
}
