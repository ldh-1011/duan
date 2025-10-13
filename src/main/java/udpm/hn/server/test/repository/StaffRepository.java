package udpm.hn.server.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
}
