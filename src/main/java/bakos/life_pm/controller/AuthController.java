package bakos.life_pm.controller;

import bakos.life_pm.dto.LoginResponse;
import bakos.life_pm.dto.request.AuthRequest;
import bakos.life_pm.dto.request.RegisterUserRequest;
import bakos.life_pm.entity.User;
import bakos.life_pm.service.AuthService;
import bakos.life_pm.service.JwtService;
import bakos.life_pm.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authService;
    private final UserService userService;

    public AuthController(JwtService jwtService, AuthService authService, UserService userService) {
        this.jwtService = jwtService;
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserRequest request) {
        User registeredUser = userService.createUser(request.getUsername(), request.getPassword(), request.getEmail());
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthRequest request) {
        User authenticatedUser = authService.login(request.getUsername(), request.getPassword());
        String jwtToken = jwtService.generateToken(authenticatedUser.getUserName());
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime() );
        return ResponseEntity.ok(loginResponse);
    }
}