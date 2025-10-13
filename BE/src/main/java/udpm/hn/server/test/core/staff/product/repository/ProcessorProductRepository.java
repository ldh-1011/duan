package udpm.hn.server.test.core.staff.product.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.entity.Processor;
import udpm.hn.server.test.repository.ProcessorRepository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProcessorProductRepository extends ProcessorRepository {
    Optional<Processor> findByProcessorName(String processorName);

    @Query(value = "SELECT processor_name AS processorName FROM processor" , nativeQuery = true)
    List<String> findAllProcessorNames();
}
