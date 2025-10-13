package udpm.hn.server.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.core.staff.resolution.model.response.ResolutionResponse;
import udpm.hn.server.test.entity.Resolution;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResolutionRepository extends JpaRepository<Resolution, String> {
    @Query(value = "SELECT id AS id , resolution_name AS resolutionName  FROM resolution" , nativeQuery = true)
    List<ResolutionResponse> findAllResolutions();

    Optional<Resolution> findByResolutionName(String name);

    @Query(value = "SELECT id AS id , resolution_name AS resolutionName  FROM resolution WHERE id = :idResolution" , nativeQuery = true)
    Optional<ResolutionResponse> findByIdResolution(@Param("idResolution") String idResolution);
}
