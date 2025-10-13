package udpm.hn.server.test.core.staff.iso.service;

import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.entity.Iso;

public interface IsoService {
    ResponseObject<?> getAllIso();

    ResponseObject<?> addIso(Iso iso);

    ResponseObject<?> updateIso(String id, Iso iso);

    ResponseObject<?> deleteIso(String id);
}
