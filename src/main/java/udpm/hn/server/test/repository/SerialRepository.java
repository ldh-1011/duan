package udpm.hn.server.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.entity.Serial;

@Repository
public interface SerialRepository extends JpaRepository<Serial, String> {
}
