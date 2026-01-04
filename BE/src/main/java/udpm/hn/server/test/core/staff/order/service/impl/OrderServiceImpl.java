package udpm.hn.server.test.core.staff.order.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.core.staff.order.model.request.AddAndUpdateRequest;
import udpm.hn.server.test.core.staff.order.model.request.OrderRequest;
import udpm.hn.server.test.core.staff.order.service.OrderService;
import udpm.hn.server.test.core.staff.productDetail.repository.ADProductDetailRepository;
import udpm.hn.server.test.entity.*;
import udpm.hn.server.test.infrastructure.constant.EntityStatus;
import udpm.hn.server.test.infrastructure.constant.OrderStatus;
import udpm.hn.server.test.infrastructure.constant.OrderTypeEnum;
import udpm.hn.server.test.infrastructure.constant.PaymentStatus;
import udpm.hn.server.test.repository.*;
import udpm.hn.server.test.utils.Helper;
import udpm.hn.server.test.utils.SecurityUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ADProductDetailRepository productDetailRepository;
    private final DiscountCodeRepository discountCodeRepository;
    private final OrdersDetailRepository ordersDetailRepository;
    private final SerialSoldRepository serialSoldRepository;
    private final CustomerRepository customerRepository;
    private final StaffRepository staffRepository;
    private final SerialRepository serialRepository;

    @Override
    public ResponseObject<?> getAllOrder(OrderRequest request){
        Pageable pageable = Helper.createPageable(request, "booking_date");
        return new ResponseObject<>(orderRepository.findAllOrder(request.getCode() , request.getStatusList() , pageable),
                HttpStatus.OK ,
                "Lấy thành công order");
    }

    @Override
    public ResponseObject<?> addOrderTaiQuay(AddAndUpdateRequest request) {
        String staffId = SecurityUtil.getCurrentStaffId();
        Staff staff = staffRepository.findById(staffId).orElseThrow();
        Customer customer = customerRepository.findById(request.getKhachHangId()).orElseThrow();

        Order order = new Order();
        order.setCustomerId(customer);
        order.setInvoiceCode(request.getMaHoaDon());
        order.setBookingDate(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))); // Giờ Việt Nam
        order.setPaymentStatus(request.getPaymentStatus());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderType(OrderTypeEnum.AT_THE_COUNTER);
        order.setSalesStaff(staff);

        // Tạo order detail và tính tổng tiền
        List<OrdersDetail> details = new ArrayList<>();
        BigDecimal tongTien = BigDecimal.ZERO;

        for (var item : request.getProducts()) {
            ProductDetail pd = productDetailRepository.findById(item.getProductDetailId())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sản phẩm"));

            List<Serial> serials = serialRepository.findByProductDetail(pd, EntityStatus.ACTIVE);

            if (serials.size() < item.getQuantity()) {
                throw new RuntimeException("Không đủ serial để bán cho sản phẩm");
            }

            BigDecimal thanhTien = pd.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            tongTien = tongTien.add(thanhTien);

            OrdersDetail detail = new OrdersDetail();
            detail.setOrder(order);
            detail.setProductDetail(pd);
            detail.setQuantity(item.getQuantity());
            detail.setUnitPrice(pd.getPrice());
            detail.setNote(item.getNote());
            details.add(detail);
            for (int i = 0; i < item.getQuantity(); i++) {
                Serial serial = serials.get(i);

                SerialSold serialSold = new SerialSold();
                serialSold.setSerialNumber(serial.getSerialNumber());
                serialSold.setOrdersDetail(detail);
                serialSoldRepository.save(serialSold);
                serial.setStatus(EntityStatus.INACTIVE);
                serialRepository.save(serial);
            }
        }
        order.setTotalAmount(tongTien);
        //  Tính giảm giá nếu có
        if (request.getPhieuGiamGiaId() != null) {
            DiscountCode dc = discountCodeRepository.findById(request.getProductDetailId())
                    .orElseThrow(() -> new EntityNotFoundException("Mã giảm giá không tồn tại"));
            if (dc.getQuantity() <= 0) {
                throw new RuntimeException("Mã giảm giá đã hết lượt sử dụng");
            }
            if (tongTien.compareTo(dc.getReducedConditions()) < 0) {
                throw new RuntimeException("Tổng tiền chưa đủ điều kiện áp mã giảm giá");
            }
            BigDecimal tienGiam = tongTien.multiply(dc.getDiscountPercent().divide(BigDecimal.valueOf(100)));
            if (tienGiam.compareTo(dc.getMaxDiscountAmount()) > 0) {
                tienGiam = dc.getMaxDiscountAmount();
            }
            order.setDiscountCode(dc);
            order.setMoneyReduced(tienGiam);
            order.setTotalAmount(tongTien.subtract(tienGiam));
            dc.setQuantity(dc.getQuantity() - 1);
            discountCodeRepository.save(dc);
        }
        BigDecimal finalPrice = order.getTotalAmount(); // Giá cuối cần thanh toán
        //  Xử lý thanh toán và tính tiền thừa nếu là CASH
        if (order.getPaymentStatus() == PaymentStatus.CASH) {
            BigDecimal cash = request.getTienKhachDua();
            if (cash == null) {
                throw new RuntimeException("Thiếu tiền khách đưa");
            }
            order.setTotalAmount(finalPrice);
            order.setMoneyGivenByGuests(cash);
            if (cash.compareTo(finalPrice) < 0) {
                order.setChange(BigDecimal.ZERO);
            } else {
                BigDecimal refund = cash.subtract(finalPrice);
                order.setChange(refund);
            }
        } else {
            // Các hình thức khác → không có tiền thừa
            order.setTotalAmount(finalPrice);
            order.setChange(BigDecimal.ZERO);
            order.setMoneyGivenByGuests(BigDecimal.ZERO);
        }
        // Lưu order và order detail
        orderRepository.save(order);
        ordersDetailRepository.saveAll(details);

        return new ResponseObject<>(null, HttpStatus.OK , "thêm thành công order");
    }

    @Override
    @Transactional
    public ResponseObject<?> updateOrderTaiQuay(AddAndUpdateRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đơn hàng"));
        Customer customer = customerRepository.findById(request.getKhachHangId())
                .orElseThrow();

        order.setCustomerId(customer);
        order.setPaymentStatus(request.getPaymentStatus());
        // Map order detail cũ
        List<OrdersDetail> oldDetails = ordersDetailRepository.findByOrder(order);
        Map<String, OrdersDetail> detailMap = oldDetails.stream()
                .collect(Collectors.toMap(
                        d -> d.getProductDetail().getId(),
                        d -> d
                ));

        BigDecimal tongTien = BigDecimal.ZERO;
        List<OrdersDetail> needSave = new ArrayList<>();
        for (var item : request.getProducts()) {
            ProductDetail pd = productDetailRepository.findById(item.getProductDetailId())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sản phẩm"));

            OrdersDetail detail;
            // update
            if (detailMap.containsKey(pd.getId())) {
                detail = detailMap.get(pd.getId());

                int oldQty = detail.getQuantity();
                int newQty = item.getQuantity();

                // Nếu tăng số lượng → bán thêm serial
                if (newQty > oldQty) {
                    int needMore = newQty - oldQty;

                    List<Serial> serials = serialRepository
                            .findByProductDetail(pd, EntityStatus.ACTIVE);

                    if (serials.size() < needMore) {
                        throw new RuntimeException("Không đủ serial để bán thêm");
                    }

                    for (int i = 0; i < needMore; i++) {
                        Serial serial = serials.get(i);
                        SerialSold serialSold = new SerialSold();
                        serialSold.setSerialNumber(serial.getSerialNumber());
                        serialSold.setOrdersDetail(detail);
                        serialSoldRepository.save(serialSold);

                        serial.setStatus(EntityStatus.INACTIVE);
                        serialRepository.save(serial);
                    }
                }

                detail.setQuantity(newQty);
                detail.setNote(item.getNote());
            }
            else {
                detail = new OrdersDetail();
                detail.setOrder(order);
                detail.setProductDetail(pd);
                detail.setQuantity(item.getQuantity());
                detail.setUnitPrice(pd.getPrice());
                detail.setNote(item.getNote());

                List<Serial> serials = serialRepository
                        .findByProductDetail(pd, EntityStatus.ACTIVE);

                if (serials.size() < item.getQuantity()) {
                    throw new RuntimeException("Không đủ serial để bán");
                }

                for (int i = 0; i < item.getQuantity(); i++) {
                    Serial serial = serials.get(i);

                    SerialSold serialSold = new SerialSold();
                    serialSold.setSerialNumber(serial.getSerialNumber());
                    serialSold.setOrdersDetail(detail);
                    serialSoldRepository.save(serialSold);
                    // update trang theai serial
                    serial.setStatus(EntityStatus.INACTIVE);
                    serialRepository.save(serial);
                }
            }
            BigDecimal thanhTien =
                    detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getQuantity()));
            tongTien = tongTien.add(thanhTien);

            needSave.add(detail);
        }
        // tong tien ban dau
        order.setTotalAmount(tongTien);
        order.setMoneyReduced(BigDecimal.ZERO);
        order.setDiscountCode(null);
        // giam gia
        if (request.getPhieuGiamGiaId() != null) {
            DiscountCode dc = discountCodeRepository.findById(request.getPhieuGiamGiaId())
                    .orElseThrow(() -> new EntityNotFoundException("Mã giảm giá không tồn tại"));

            if (dc.getQuantity() <= 0) {
                throw new RuntimeException("Mã giảm giá đã hết lượt sử dụng");
            }
            if (tongTien.compareTo(dc.getReducedConditions()) < 0) {
                throw new RuntimeException("Tổng tiền chưa đủ điều kiện áp mã");
            }
            BigDecimal tienGiam =
                    tongTien.multiply(dc.getDiscountPercent().divide(BigDecimal.valueOf(100)));

            if (tienGiam.compareTo(dc.getMaxDiscountAmount()) > 0) {
                tienGiam = dc.getMaxDiscountAmount();
            }
            order.setDiscountCode(dc);
            order.setMoneyReduced(tienGiam);
            order.setTotalAmount(tongTien.subtract(tienGiam));
        }
        BigDecimal finalPrice = order.getTotalAmount();
        if (order.getPaymentStatus() == PaymentStatus.CASH) {
            BigDecimal cash = request.getTienKhachDua();
            if (cash == null) {
                throw new RuntimeException("Thiếu tiền khách đưa");
            }

            order.setMoneyGivenByGuests(cash);
            if (cash.compareTo(finalPrice) < 0) {
                order.setChange(BigDecimal.ZERO);
            } else {
                order.setChange(cash.subtract(finalPrice));
            }
        } else {
            order.setMoneyGivenByGuests(BigDecimal.ZERO);
            order.setChange(BigDecimal.ZERO);
        }
        orderRepository.save(order);
        ordersDetailRepository.saveAll(needSave);
        return new ResponseObject<>(null, HttpStatus.OK, "Cập nhật order thành công");
    }

}
