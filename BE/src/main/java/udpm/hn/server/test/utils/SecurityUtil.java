package udpm.hn.server.test.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import udpm.hn.server.test.repository.UserRepository;

public class SecurityUtil {

    public static String getCurrentStaffId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }

        // Principal của bạn hiện tại đang là email (String)
        String email = authentication.getName();

        // Bạn query DB để lấy ID theo email
        return SpringContext.getBean(UserRepository.class)
                .findByEmail(email)
                .map(user -> user.getStaff().getId())
                .orElse(null);
    }
}
