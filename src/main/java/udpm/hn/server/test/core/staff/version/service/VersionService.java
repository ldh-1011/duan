package udpm.hn.server.test.core.staff.version.service;


import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.entity.Version;

public interface VersionService {
    ResponseObject<?> getAllVersion();

    ResponseObject<?> addVersion(Version version);

    ResponseObject<?> updateVersion(String id, Version version);

    ResponseObject<?> deleteVersion(String id);
}
