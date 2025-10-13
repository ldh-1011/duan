package udpm.hn.server.test.core.login.service;

import udpm.hn.server.test.core.login.model.request.LoginRequest;
import udpm.hn.server.test.core.login.model.request.RegisterRequest;

import java.util.Map;

public interface UserService {
    Map<String, Object> registerCustomer(RegisterRequest request);

    Map<String, Object> registerStaff(RegisterRequest request);

    Map<String, Object> login(LoginRequest request);

}
