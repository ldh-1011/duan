package udpm.hn.server.test.core.staff.processor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import udpm.hn.server.test.core.staff.processor.repository.ADProcessorRepository;
import udpm.hn.server.test.core.staff.processor.service.ProcessorService;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.entity.Processor;
import udpm.hn.server.test.repository.ProcessorRepository;
import udpm.hn.server.test.repository.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProcessorServiceImpl implements ProcessorService {
    private final ADProcessorRepository techSpecRepository;

    private final ProductRepository productRepository;

    @Override
    public ResponseObject<?> getAllTechSpec() {
        return new ResponseObject<>(
                techSpecRepository.getAllTechSpecs(),
                HttpStatus.OK,
                "Lấy thành công tech spec"
        );
    }

    @Override
    public ResponseObject<?> addTechSpec(Processor processor) {

        Optional<Processor> optionalProcessor = techSpecRepository.findByProcessorName(processor.getProcessorName());
        if (optionalProcessor.isPresent()) {
            return new ResponseObject<>(null , HttpStatus.CONFLICT , "Đã tồn tại Processor");
        }
        processor.setProcessorName(processor.getProcessorName());
         techSpecRepository.save(processor);

        return new ResponseObject<>(null , HttpStatus.ACCEPTED , "Thêm thành cong");
    }

    @Override
    public ResponseObject<?> updateTechSpec(String id, Processor processor) {
        Optional<Processor> optionalProcessor = techSpecRepository.findById(id);
        if (optionalProcessor.isEmpty()) {
            return new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Không tìm thấy Tech Spec");
        }

        Optional<Processor> optionalProcessorName = techSpecRepository.findByProcessorName(processor.getProcessorName());
        if (optionalProcessorName.isPresent() && !optionalProcessorName.get().getId().equals(id)) {
            return new ResponseObject<>(null, HttpStatus.CONFLICT, "Đã tồn tại Processor");
        }

        Processor processorToUpdate = optionalProcessor.get();

        processorToUpdate.setProcessorName(processor.getProcessorName());

        techSpecRepository.save(processorToUpdate);

        return new ResponseObject<>(processorToUpdate, HttpStatus.ACCEPTED, "Cập nhật thành công");
    }


    @Override
    public ResponseObject<?> deleteTechSpec(String id) {
        Optional<Processor> optionalTechSpec = techSpecRepository.findById(id);
        if (optionalTechSpec.isEmpty()) {
            return new ResponseObject<>(null , HttpStatus.NOT_FOUND , "không tìm thấy id này");
        }

        boolean isProcessor = productRepository.existsByProcessorId(id);
        if (isProcessor) {
            return new ResponseObject<>(null, HttpStatus.BAD_REQUEST,
                    "Không thể xoá processor này vì đang được sử dụng trong sản phẩm!");
        }

        techSpecRepository.deleteById(id);
        return new ResponseObject<>(null , HttpStatus.ACCEPTED , "xóa thành công");
    }

    @Override
    public ResponseObject<?> findProcessorID(String id){
        return new ResponseObject<>(
                techSpecRepository.findByIdProcessor(id),
                HttpStatus.OK,
                "Lây thành công processor"
        );
    }
}
