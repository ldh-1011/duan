package udpm.hn.server.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.core.staff.iso.model.response.IsoResponse;
import udpm.hn.server.test.entity.Iso;

import java.util.List;
import java.util.Optional;

@Repository
public interface IsoRepository extends JpaRepository<Iso, String> {
    @Query(value = "SELECT id AS id, iso_name AS isoName FROM iso",nativeQuery = true)
    List<IsoResponse> findAllIso();

    Optional<Iso> findIsoByIsoName(String isoName);
}
