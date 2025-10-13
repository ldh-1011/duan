package udpm.hn.server.test.core.staff.processor.service;

import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.entity.Processor;

public interface ProcessorService {
    ResponseObject<?> getAllTechSpec();

    ResponseObject<?> addTechSpec(Processor processor);

    ResponseObject<?> updateTechSpec(String id, Processor processor);

    ResponseObject<?> deleteTechSpec(String id);
}
