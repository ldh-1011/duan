package udpm.hn.server.test.core.login.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import udpm.hn.server.test.core.login.model.request.LoginRequest;
import udpm.hn.server.test.core.login.model.request.RegisterRequest;
import udpm.hn.server.test.core.login.service.UserService;
import udpm.hn.server.test.entity.Customer;
import udpm.hn.server.test.entity.Role;
import udpm.hn.server.test.entity.Staff;
import udpm.hn.server.test.entity.User;
import udpm.hn.server.test.infrastructure.security.jwt.JwtUtils;
import udpm.hn.server.test.repository.CustomerRepository;
import udpm.hn.server.test.repository.RoleRepository;
import udpm.hn.server.test.repository.StaffRepository;
import udpm.hn.server.test.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    @Override
    public Map<String, Object> registerCustomer(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return Map.of("status", HttpStatus.CONFLICT, "message", "Email đã tồn tại");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        Customer customer = new Customer();
        customer.setFullName(request.getFullName());
        customer.setUser(user);
        customerRepository.save(customer);

        return Map.of("status", HttpStatus.CREATED, "message", "Tạo tài khoản khách hàng thành công");
    }

    @Override
    public Map<String, Object> registerStaff(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return Map.of("status", HttpStatus.CONFLICT, "message", "Email đã tồn tại");
        }

        Role role = roleRepository.findByCode("STAFF")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role STAFF"));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        userRepository.save(user);

        Staff staff = new Staff();
        staff.setFullName(request.getFullName());
        staff.setUser(user);
        staffRepository.save(staff);

        return Map.of("status", HttpStatus.CREATED, "message", "Tạo tài khoản nhân viên thành công");
    }

    @Override
    public Map<String, Object> login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

            String authority = (user.getRole() != null) ? user.getRole().getCode() : "CUSTOMER";
            String token = jwtUtils.generateToken(user.getEmail(), authority);

            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("email", user.getEmail());
            result.put("role", authority);
            result.put("status", HttpStatus.OK);
            result.put("message", "Đăng nhập thành công");

            return result;
        } catch (AuthenticationException e) {
            return Map.of("status", HttpStatus.UNAUTHORIZED, "message", "Sai email hoặc mật khẩu");
        }
    }

}
