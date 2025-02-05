package bakos.life_pm.service;

import bakos.life_pm.entity.Customer;
import bakos.life_pm.entity.RefreshToken;
import bakos.life_pm.repository.RefreshTokenRepository;
import bakos.life_pm.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    //TODO:Use Secure Cookies (cookie.setSecure(true)) in production.
    //TODO: Encrypt refresh tokens before storing them in the DB.
    //TODO: Monitor refresh token usage (e.g., log when a refresh token is used).
    //TODO: Implement IP/device binding to detect stolen tokens.

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${security.jwt.refresh-token-expiration-sec}")
    private long refreshTokenExpirationSec;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public Customer login(String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authToken);
        return userRepository.findByUserNameOrThrow(username);
    }

    @Transactional
    public void storeRefreshToken(String username, String token) {
        refreshTokenRepository.deleteByUserName(username);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserName(username);
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(refreshTokenExpirationSec));
        refreshTokenRepository.save(refreshToken);
    }


    public Optional<RefreshToken> validateRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .filter(rt -> !rt.isExpired());
    }

    @Transactional
    public void revokeRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    public void revokeAllTokensForUser(String username) {
        refreshTokenRepository.deleteByUserName(username);
    }
}
