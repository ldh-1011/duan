package udpm.hn.server.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.core.staff.version.model.response.VersionResponse;
import udpm.hn.server.test.entity.Version;

import java.util.List;
import java.util.Optional;

@Repository
public interface VersionRepository extends JpaRepository<Version, String> {
    @Query(value = "SELECT id AS id , version_name AS versionName FROM version" , nativeQuery = true)
    List<VersionResponse> findAllVersions();

    Optional<Version> findByVersionName(String versionName);

    @Query(value = "SELECT id AS id , version_name AS versionName FROM version WHERE id = :idVersion" , nativeQuery = true)
    Optional<VersionResponse> findByIdVersion(@Param("idVersion") String idVersion);
}
