package bakos.life_pm.validators;

import bakos.life_pm.entity.CustomerRelated;
import bakos.life_pm.service.UserNameResolver;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OwnershipValidator implements ConstraintValidator<ValidEditor, UUID> {

    private final UserNameResolver userNameResolver;
    private Class<? extends CustomerRelated> entityClass;

    public OwnershipValidator(UserNameResolver userNameResolver) {
        this.userNameResolver = userNameResolver;
    }

    @Override
    public void initialize(ValidEditor constraintAnnotation) {
        this.entityClass = constraintAnnotation.entity();
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext context) {
        if (id == null) return true;

        String owner = userNameResolver.getUserName(id, entityClass);
        if (owner == null) {
            return buildValidationMessage(context, "Resource does not exists!");
        }

        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!currentUser.equals(owner)) {
            return buildValidationMessage(context, "You do not have permission to modify this resource!");
        }

        return true;
    }

    private boolean buildValidationMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}