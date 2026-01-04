package udpm.hn.server.test.core.staff.discount.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import udpm.hn.server.test.infrastructure.constant.LoaiGiamGia;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddDiscountCode {

    private String id;

    @NotBlank(message = "Mã giảm giá không được để trống")
    @Size(max = 50, message = "Mã giảm giá không được vượt quá 50 ký tự")
    private String code;

    @NotNull(message = "Giá trị giảm tối đa không được để trống")
    @DecimalMin(value = "0", inclusive = false,
            message = "Giá trị giảm tối đa phải lớn hơn 0")
    private BigDecimal maxDiscountAmount;

    @NotNull(message = "Điều kiện giảm không được để trống")
    @DecimalMin(value = "0",
            message = "Điều kiện giảm phải lớn hơn hoặc bằng 0")
    private BigDecimal reducedConditions;

    @NotNull(message = "Số lượng mã giảm giá không được để trống")
    @Min(value = 1, message = "Số lượng mã giảm giá phải lớn hơn hoặc bằng 1")
    private Integer quantity;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @NotNull(message = "Ngày kết thúc không được để trống")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    @NotNull(message = "Loại trạng thái giảm giá không được để trống")
    private LoaiGiamGia loaiGiamGia;

    @NotNull(message = "Phần trăm giảm không được để trống")
    @DecimalMin(value = "0", inclusive = false, message = "Phần trăm giảm phải lớn hơn 0")
    @DecimalMax(value = "90", message = "Phần trăm giảm không được vượt quá 90%")
    private BigDecimal discountPercent;
}


