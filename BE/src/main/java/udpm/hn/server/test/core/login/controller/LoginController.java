package udpm.hn.server.test.core.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import udpm.hn.server.test.core.login.model.request.LoginRequest;
import udpm.hn.server.test.core.login.model.request.RegisterRequest;
import udpm.hn.server.test.core.login.service.UserService;
import udpm.hn.server.test.infrastructure.constant.MappingConstants;


@RestController
@RequestMapping(MappingConstants.API_AUTH_PREFIX)
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/register-customer")
    public ResponseEntity<?> registerCustomer(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.registerCustomer(request));
    }

    @PostMapping("/register-staff")
    public ResponseEntity<?> registerStaff(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.registerStaff(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
}
