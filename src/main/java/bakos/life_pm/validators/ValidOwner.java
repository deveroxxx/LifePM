package bakos.life_pm.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OwnershipValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOwner {
    Class<?> entity();
    String message() default "Unauthorized modification attempt.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
