package udpm.hn.server.test.core.staff.order.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import udpm.hn.server.test.infrastructure.constant.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddAndUpdateRequest {
    private String orderId;
    private String orderDetailId;
    private String nguoiThanhToan;
    private BigDecimal tongTienThanhToan;
    private PaymentStatus paymentStatus;
    private BigDecimal tienKhachDua;
    private BigDecimal tienThua;
    private BigDecimal soTienBanDau;
    private String phieuGiamGiaId;
    private String productDetailId;
    private String khachHangId;
    private String maHoaDon;
    private List<ProductItem> products;
}

