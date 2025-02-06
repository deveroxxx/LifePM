package bakos.life_pm.validators;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OwnershipValidator implements ConstraintValidator<ValidOwner, UUID> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entityClass;

    @Override
    public void initialize(ValidOwner constraintAnnotation) {
        this.entityClass = constraintAnnotation.entity();
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext context) {
        if (id == null) return true;

        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Object entity = entityManager.find(entityClass, id);

        if (entity == null) {
            return buildValidationMessage(context, "Resource does not exists!");
        }

        String owner = getUserNameFromEntity(entity);
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

    private String getUserNameFromEntity(Object entity) {
        try {
            return (String) entity.getClass().getMethod("getUserName").invoke(entity);
        } catch (Exception e) {
            throw new IllegalStateException("Entity must have a getUserName() method", e);
        }
    }
}