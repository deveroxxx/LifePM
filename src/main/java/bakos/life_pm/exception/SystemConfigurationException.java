package bakos.life_pm.exception;

/**
 * For exceptions where we don't want to expose the concrete error to the user.
 */
public class SystemConfigurationException extends RuntimeException {

    public SystemConfigurationException(String errorMessage) {
        super(errorMessage);
    }

}
