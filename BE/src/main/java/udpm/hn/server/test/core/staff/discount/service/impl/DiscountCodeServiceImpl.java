package udpm.hn.server.test.core.staff.discount.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.core.staff.discount.model.request.AddDiscountCode;
import udpm.hn.server.test.core.staff.discount.model.request.DiscountCodeRequest;
import udpm.hn.server.test.core.staff.discount.service.DiscountCodeService;
import udpm.hn.server.test.entity.DiscountCode;
import udpm.hn.server.test.infrastructure.constant.LoaiGiamGia;
import udpm.hn.server.test.repository.DiscountCodeRepository;
import udpm.hn.server.test.utils.Helper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DiscountCodeServiceImpl implements DiscountCodeService {
    private final DiscountCodeRepository discountCodeRepository;

    @Override
    public ResponseObject<?> search(DiscountCodeRequest request){
        Pageable pageable = Helper.createPageable(request, "created_date");
        return new ResponseObject<>(
                discountCodeRepository.search(request.getCode() , request.getMinMoney() , request.getPercent() , pageable),
                HttpStatus.OK,
                "Lấy thành công phiếu giảm giá");
    }

    @Override
    public ResponseObject<?> getAllDiscountCode(DiscountCodeRequest request){
        return new ResponseObject<>(
                discountCodeRepository.searchDiscountCodeResponse(request.getKeyword()),
                HttpStatus.OK,
                "Lấy thành công phiếu giảm giá");
    }

    @Override
    public ResponseObject<?> getAllDiscountCodeAndOrder(DiscountCodeRequest request){
        return new ResponseObject<>(
                discountCodeRepository.findDiscountCodeByBillTotal(request.getBillTotal()),
                HttpStatus.OK,
                "Lấy thành công phiếu giảm giá theo giá order"
        );
    }

    @Override
    public ResponseObject<?> addAndUpdateDiscountCode(AddDiscountCode request){
        if (!request.getEndDate().isAfter(request.getStartDate())) {
            return new  ResponseObject<>(null , HttpStatus.CONFLICT , "Ngày kết thúc phải sau ngày bắt đầu");
        }

        DiscountCode discountCode;
        if(request.getId() != null && !request.getId().isBlank()){
            discountCode = discountCodeRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Mã giảm giá không tồn tại"));
            discountCode.setMaxDiscountAmount(request.getMaxDiscountAmount());
            discountCode.setReducedConditions(request.getReducedConditions());
            discountCode.setQuantity(request.getQuantity());
            discountCode.setDiscountPercent(request.getDiscountPercent());
            discountCode.setStartDate(request.getStartDate());
            discountCode.setEndDate(request.getEndDate());
            discountCode.setStatusDiscountCode(request.getLoaiGiamGia());
            discountCodeRepository.save(discountCode);
            return new  ResponseObject<>(discountCode , HttpStatus.OK , "Cập nhật mã giảm giá thành công");
        }

        if (discountCodeRepository.existsByCode(request.getCode())) {
            return new  ResponseObject<>(null , HttpStatus.CONFLICT , "Mã giảm giá đã tồn tại");
        }
        discountCode = new DiscountCode();
        discountCode.setCode(request.getCode());
        discountCode.setMaxDiscountAmount(request.getMaxDiscountAmount());
        discountCode.setReducedConditions(request.getReducedConditions());
        discountCode.setQuantity(request.getQuantity());
        discountCode.setDiscountPercent(request.getDiscountPercent());
        discountCode.setStartDate(request.getStartDate());
        discountCode.setEndDate(request.getEndDate());
        discountCode.setStatusDiscountCode(request.getLoaiGiamGia());
        discountCodeRepository.save(discountCode);
        return new  ResponseObject<>(discountCode , HttpStatus.OK , "Thêm mã giảm giá thành công");
    }

    @Override
    public ResponseObject<?> changeStatus(DiscountCodeRequest request){
        DiscountCode discountCode = discountCodeRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Không tìm thấy id mã giảm giá"));
        if (discountCode.getStatusDiscountCode() == LoaiGiamGia.END) {
            return new  ResponseObject<>(null , HttpStatus.CONFLICT , "Mã giảm giá này đã kết thúc");
        }

        if (discountCode.getStatusDiscountCode() == LoaiGiamGia.ONGOING) {
            discountCode.setStatusDiscountCode(LoaiGiamGia.COMING_SOON);
        } else {
            discountCode.setStatusDiscountCode(LoaiGiamGia.ONGOING);
        }
        discountCodeRepository.save(discountCode);
        return new  ResponseObject<>(discountCode , HttpStatus.OK , "Đổi trạng thái thành công");
    }

    @Scheduled(cron = "0 59 23 * * *", zone = "Asia/Ho_Chi_Minh")
    public void autoUpdateDiscountCodeStatus() {
        LocalDateTime now = LocalDateTime.now();
        //  Quá hạn → END
        discountCodeRepository.autoSetEnd(now);
        // Tới ngày → ONGOING
        discountCodeRepository.autoSetOngoing(now);
    }

    @Override
    public ResponseObject<?> getDetailDiscountCode(DiscountCodeRequest request){
        return new  ResponseObject<>(discountCodeRepository.getDetailDiscountCode(request.getId()) , HttpStatus.OK , "Lấy thành công chi tiết discount code");
    }
}
