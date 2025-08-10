package bakos.life_pm.dto.response;

public record ErrorResponse(
        String errorCode,
        String errorMessage) {
}