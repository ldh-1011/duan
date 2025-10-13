package udpm.hn.server.test.core.staff.version.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.core.staff.version.service.VersionService;
import udpm.hn.server.test.entity.Version;
import udpm.hn.server.test.repository.VersionRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VersionServiceImpl implements VersionService {
    private final VersionRepository versionRepository;

    @Override
    public ResponseObject<?> getAllVersion(){
        return new ResponseObject<>(
                versionRepository.findAllVersions(),
                HttpStatus.OK,
                "Lay thanh cong versions"
        );
    }

    @Override
    public ResponseObject<?> addVersion(Version version){
        Optional<Version> optionalVersion = versionRepository.findByVersionName(version.getVersionName());
        if(optionalVersion.isPresent()){
            return new ResponseObject<>(null, HttpStatus.CONFLICT, "da ton tai Version");
        }
        version.setVersionName(version.getVersionName());
        versionRepository.save(version);

        return new ResponseObject<>(null, HttpStatus.CREATED, "them thanh cong");
    }

    @Override
    public ResponseObject<?> updateVersion(String id, Version version){
        Optional<Version> optionalVersion = versionRepository.findById(id);
        if(optionalVersion.isEmpty()){
            return new ResponseObject<>(null, HttpStatus.NOT_FOUND, "khong ton tai Version");
        }

        Version oldVersion = optionalVersion.get();
        oldVersion.setVersionName(version.getVersionName());
        versionRepository.save(oldVersion);

        return new ResponseObject<>(oldVersion, HttpStatus.OK, "update thanh cong");
    }

    @Override
    public ResponseObject<?> deleteVersion(String id){
        Optional<Version> optionalVersion = versionRepository.findById(id);
        if(optionalVersion.isEmpty()){
            return new ResponseObject<>(null, HttpStatus.NOT_FOUND, "khong ton tai Version");
        }

        versionRepository.deleteById(id);
        return new ResponseObject<>(null, HttpStatus.OK, "delete thanh cong");
    }
}
