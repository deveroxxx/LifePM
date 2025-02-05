package bakos.life_pm.controller;

import bakos.life_pm.dto.LoginResponse;
import bakos.life_pm.dto.request.AuthRequest;
import bakos.life_pm.dto.request.RegisterUserRequest;
import bakos.life_pm.entity.Customer;
import bakos.life_pm.entity.RefreshToken;
import bakos.life_pm.service.AuthService;
import bakos.life_pm.service.JwtService;
import bakos.life_pm.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

import static bakos.life_pm.auth.JwtAuthFilter.AUTH_COOKIE_NAME;
import static bakos.life_pm.auth.JwtAuthFilter.REFRESH_COOKIE_NAME;
import static bakos.life_pm.service.Utils.*;
import static bakos.life_pm.service.Utils.asJson;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authService;
    private final UserService userService;

    @Value("${security.jwt.access-token-expiration-sec}")
    private long accessTokenExpirationSec;


    public AuthController(JwtService jwtService, AuthService authService, UserService userService) {
        this.jwtService = jwtService;
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Customer> register(@RequestBody RegisterUserRequest request) {
        Customer registeredUser = userService.createUser(request.getUsername(), request.getPassword(), request.getEmail());
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthRequest request, HttpServletResponse response) {
        try {
            Customer authenticatedUser = authService.login(request.getUsername(), request.getPassword());

            String accessToken = jwtService.generateToken(authenticatedUser.getUserName());
            String refreshToken = UUID.randomUUID().toString();

            authService.storeRefreshToken(authenticatedUser.getUserName(), refreshToken);

            setCookie(response, AUTH_COOKIE_NAME, accessToken);
            setCookie(response, REFRESH_COOKIE_NAME, refreshToken);

            LoginResponse loginResponse = new LoginResponse(null, accessToken, accessTokenExpirationSec);
            return ResponseEntity.ok(loginResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Bad credentials", null, null));
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = getTokenFromCookie(request, REFRESH_COOKIE_NAME);

        if (refreshToken != null) {
            authService.revokeRefreshToken(refreshToken);
        }

        clearAuthCookies(response);
        return ResponseEntity.ok(asJson("Logged out successfully"));
    }

    private void clearAuthCookies(HttpServletResponse response) {
        Cookie authCookie = new Cookie(AUTH_COOKIE_NAME, "");
        authCookie.setHttpOnly(true);
        authCookie.setSecure(false);  //TODO
        authCookie.setPath("/");
        authCookie.setMaxAge(0);
        response.addCookie(authCookie);

        Cookie refreshCookie = new Cookie(REFRESH_COOKIE_NAME, "");
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false); //TODO
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(0);
        response.addCookie(refreshCookie);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = getTokenFromCookie(request, REFRESH_COOKIE_NAME);

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Refresh token missing", null, null));
        }

        Optional<RefreshToken> storedToken = authService.validateRefreshToken(refreshToken);

        if (storedToken.isPresent()) {
            String newAccessToken = jwtService.generateToken(storedToken.get().getUserName());
            setCookie(response, AUTH_COOKIE_NAME, newAccessToken);
            return ResponseEntity.ok(new LoginResponse(null, newAccessToken, accessTokenExpirationSec));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid or expired refresh token", null, null));
    }
}