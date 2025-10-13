package udpm.hn.server.test.core.staff.resolution.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.core.staff.resolution.service.ResolutionService;
import udpm.hn.server.test.entity.Resolution;
import udpm.hn.server.test.repository.ProductRepository;
import udpm.hn.server.test.repository.ResolutionRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResolutionServiceImpl implements ResolutionService {

    private final ResolutionRepository resolutionRepository;

    private final ProductRepository productRepository;

    @Override
    public ResponseObject<?> getAll() {
        return new ResponseObject<>(
                resolutionRepository.findAllResolutions(),
                HttpStatus.OK,
                "lay thanh cong Resolution"
        );
    }

    @Override
    public ResponseObject<?> addResolution(Resolution resolution) {
        Optional<Resolution> optionalResolution = resolutionRepository.findByResolutionName(resolution.getResolutionName());
        if (optionalResolution.isPresent()) {
            return new ResponseObject<>(null, HttpStatus.CONFLICT, "ten nay da ton tai");
        }
        resolution.setResolutionName(resolution.getResolutionName());
        resolutionRepository.save(resolution);

        return new ResponseObject<>(null, HttpStatus.ACCEPTED, "them thanh cong");
    }

    @Override
    public ResponseObject<?> updateResolution(String id , Resolution resolution){
        Optional<Resolution> optionalResolution = resolutionRepository.findById(id);
        if (optionalResolution.isEmpty()) {
            return new ResponseObject<>(null, HttpStatus.NOT_FOUND, "id nay khong ton tai");
        }

        Optional<Resolution> optionalResolutionName = resolutionRepository.findByResolutionName(resolution.getResolutionName());
        if (optionalResolutionName.isPresent() && !optionalResolution.get().getId().equals(id)) {
            return new ResponseObject<>(null, HttpStatus.CONFLICT, "ten nay da ton tai");
        }

        Resolution updateResolution = optionalResolution.get();
        updateResolution.setResolutionName(resolution.getResolutionName());
        resolutionRepository.save(updateResolution);

        return new ResponseObject<>(updateResolution, HttpStatus.ACCEPTED, "update thanh cong");
    }

    @Override
    public ResponseObject<?> deleteResolution(String id){
        Optional<Resolution> optionalResolution = resolutionRepository.findById(id);
        if (optionalResolution.isEmpty()) {
            return new ResponseObject<>(null, HttpStatus.NOT_FOUND, "id nay khong ton tai");
        }

        boolean isResolution =  productRepository.existsByResolutionId(id);
        if (isResolution) {
            return new ResponseObject<>(null, HttpStatus.BAD_REQUEST,
                    "Không thể xoá resolution này vì đang được sử dụng trong sản phẩm!");
        }

        resolutionRepository.deleteById(id);
        return new ResponseObject<>(null, HttpStatus.ACCEPTED, "delete thanh cong");
    }

    @Override
    public ResponseObject<?> findByResolutionId(String id){
        return new ResponseObject<>(
                resolutionRepository.findByIdResolution(id),
                HttpStatus.OK,
                "lay thanh cong chi tiet resolution"
        );
    }

}
