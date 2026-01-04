package udpm.hn.server.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udpm.hn.server.test.entity.SerialSold;

public interface SerialSoldRepository extends JpaRepository<SerialSold, String> {
}
