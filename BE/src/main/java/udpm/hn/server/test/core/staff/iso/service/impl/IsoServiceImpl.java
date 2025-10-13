package udpm.hn.server.test.core.staff.iso.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.core.staff.iso.service.IsoService;
import udpm.hn.server.test.entity.Iso;
import udpm.hn.server.test.repository.IsoRepository;
import udpm.hn.server.test.repository.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IsoServiceImpl implements IsoService {

    private final IsoRepository isoRepository;

    private final ProductRepository productRepository;

    @Override
    public ResponseObject<?> getAllIso(){
        return new ResponseObject<>(
                isoRepository.findAllIso(),
                HttpStatus.OK,
                "Lay thanh cong IsoService"
        );
    }

    @Override
    public ResponseObject<?> addIso(Iso iso){
        Optional<Iso> optionalIso = isoRepository.findIsoByIsoName(iso.getIsoName());
        if(optionalIso.isPresent()){
            return new ResponseObject<>(null , HttpStatus.CONFLICT, "ios nay da ton tai");
        }
        iso.setIsoName(iso.getIsoName());
        isoRepository.save(iso);

        return new ResponseObject<>(null , HttpStatus.CREATED, "them thanh cong");
    }

    @Override
    public ResponseObject<?> updateIso(String id, Iso iso) {
        Optional<Iso> optionalIso = isoRepository.findById(id);
        if(optionalIso.isEmpty()){
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND , "khong ton tai ");
        }

        Optional<Iso> optionalIsoName = isoRepository.findIsoByIsoName(iso.getIsoName());
        if(optionalIsoName.isPresent() && optionalIso.get().getId().equals(id)){
            return new ResponseObject<>(null , HttpStatus.CONFLICT, "da ton tai");
        }

        Iso isoUpdate = optionalIso.get();
        isoUpdate.setIsoName(iso.getIsoName());
        isoRepository.save(isoUpdate);

        return new ResponseObject<>(isoUpdate , HttpStatus.OK, "update thanh cong");
    }

    @Override
    @Transactional
    public ResponseObject<?> deleteIso(String id) {
        Optional<Iso> optionalIso = isoRepository.findById(id);
        if (optionalIso.isEmpty()) {
            return new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Không tìm thấy ISO");
        }

        boolean isUsed = productRepository.existsByIsoId(id);
        if (isUsed) {
            return new ResponseObject<>(null, HttpStatus.BAD_REQUEST,
                    "Không thể xoá ISO này vì đang được sử dụng trong sản phẩm!");
        }

        isoRepository.deleteById(id);
        return new ResponseObject<>(null, HttpStatus.OK, "Xoá ISO thành công");
    }


    @Override
    public ResponseObject<?> findIsoById(String id){
        return new ResponseObject<>(null , HttpStatus.OK, "lay iso chi tiet thanh cong");
    }
}
