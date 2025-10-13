package udpm.hn.server.test.core.staff.processor.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import udpm.hn.server.test.core.staff.processor.model.response.ProcessorResponse;
import udpm.hn.server.test.entity.Processor;
import udpm.hn.server.test.repository.ProcessorRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ADProcessorRepository extends ProcessorRepository {
    @Query(value = """
            SELECT
                id AS id,
                processor_name AS processor
            FROM processor
            """ , nativeQuery = true)
    List<ProcessorResponse> getAllTechSpecs();

    Optional<Processor> findByProcessorName(String processorName);

    @Query(value = """
            SELECT
                id AS id,
                processor_name AS processor
            FROM processor 
            WHERE id = :idProcessor
            """ , nativeQuery = true)
    Optional<ProcessorResponse> findByIdProcessor(@Param("idProcessor") String idProcessor);
}
