package bakos.life_pm.dto.response;


public record LoginResponse(
        String error,
        String token,
        Long expiresInSec) {
}