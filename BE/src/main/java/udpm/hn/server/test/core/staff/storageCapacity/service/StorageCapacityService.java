package udpm.hn.server.test.core.staff.storageCapacity.service;

import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.entity.StorageCapacity;

public interface StorageCapacityService {
    ResponseObject<?> getAllStorageCapacity();

    ResponseObject<?> addStorageCapacity(StorageCapacity storageCapacity);

    ResponseObject<?> updateStorageCapacity(String id, StorageCapacity storageCapacity);

    ResponseObject<?> deleteStorageCapacity(String id);
}
