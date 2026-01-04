package udpm.hn.server.test.infrastructure.constant;

public enum OrderStatus {
    PENDING,        // Khách đặt, chờ shop xác nhận
    CONFIRMED,      // Shop xác nhận
    PACKED,         // Đóng gói
    SHIPPING,       // Đang giao
    COMPLETED,      // Giao thành công
    CANCELLED,      // hủy
    PROCESSING      // ban tai quay
}
