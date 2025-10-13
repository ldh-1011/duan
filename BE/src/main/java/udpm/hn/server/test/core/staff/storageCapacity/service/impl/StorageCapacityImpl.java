package udpm.hn.server.test.core.staff.storageCapacity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.core.staff.storageCapacity.service.StorageCapacityService;
import udpm.hn.server.test.entity.StorageCapacity;
import udpm.hn.server.test.repository.StorageCapacityRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageCapacityImpl implements StorageCapacityService {

    private final StorageCapacityRepository storageCapacityRepository;

    @Override
    public ResponseObject<?> getAllStorageCapacity() {
        return new ResponseObject<>(
                storageCapacityRepository.getAllStorageCapacityRepository(),
                HttpStatus.OK,
                "lay thanh cong"
        );
    }

    @Override
    public ResponseObject<?> addStorageCapacity(StorageCapacity storageCapacity) {
        Optional<StorageCapacity> storageCapacityOptional = storageCapacityRepository.findByStorageCapacityName(storageCapacity.getStorageCapacityName());
        if (storageCapacityOptional.isPresent()) {
            return new ResponseObject<>(null , HttpStatus.CONFLICT , "ten Storage Capacity da ton tai");
        }

        storageCapacity.setStorageCapacityName(storageCapacity.getStorageCapacityName());
        storageCapacityRepository.save(storageCapacity);

        return new ResponseObject<>(storageCapacity , HttpStatus.CREATED , "them thanh cong Storage Capacity");
    }

    @Override
    public ResponseObject<?> updateStorageCapacity(String id, StorageCapacity storageCapacity){
        Optional<StorageCapacity> storageCapacityOptional = storageCapacityRepository.findById(id);
        if (storageCapacityOptional.isEmpty()) {
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND , "Storage Capacity khong da tai");
        }

        Optional<StorageCapacity> storageCapacityName = storageCapacityRepository.findByStorageCapacityName(storageCapacity.getStorageCapacityName());
        if (storageCapacityName.isPresent() && storageCapacityOptional.get().getId().equals(id)) {
            return new ResponseObject<>(null , HttpStatus.CONFLICT , "ten Storage Capacity da tai");
        }

        StorageCapacity storageCapacityUpdate = storageCapacityOptional.get();
        storageCapacityUpdate.setStorageCapacityName(storageCapacity.getStorageCapacityName());
        storageCapacityRepository.save(storageCapacityUpdate);

        return new ResponseObject<>(storageCapacityUpdate , HttpStatus.CREATED , "update thanh cong Storage Capacity");
    }

    @Override
    public ResponseObject<?> deleteStorageCapacity(String id){
        Optional<StorageCapacity> storageCapacityOptional = storageCapacityRepository.findById(id);
        if (storageCapacityOptional.isEmpty()) {
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND , "Storage Capacity khong da tai");
        }

        storageCapacityRepository.deleteById(id);

        return new ResponseObject<>(null , HttpStatus.OK , "delete thanh cong Storage Capacity");
    }
}
