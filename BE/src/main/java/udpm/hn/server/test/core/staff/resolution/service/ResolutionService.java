package udpm.hn.server.test.core.staff.resolution.service;

import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.entity.Resolution;

public interface ResolutionService {
    ResponseObject<?> getAll();

    ResponseObject<?> addResolution(Resolution resolution);

    ResponseObject<?> updateResolution(String id, Resolution resolution);

    ResponseObject<?> deleteResolution(String id);

    ResponseObject<?> findByResolutionId(String id);
}
