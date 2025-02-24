package bakos.life_pm.validators;

import bakos.life_pm.entity.CustomerRelated;
import bakos.life_pm.enums.BoardPermissionEnum;
import bakos.life_pm.service.BoardPermissionService;
import bakos.life_pm.service.Utils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class OwnershipValidator implements ConstraintValidator<ValidEditor, UUID> {

    private final BoardPermissionService boardPermissionService;
    private Class<? extends CustomerRelated> entityClass;
    private boolean requireEditor = true;

    public OwnershipValidator(BoardPermissionService boardPermissionService) {
        this.boardPermissionService = boardPermissionService;
    }

    @Override
    public void initialize(ValidEditor constraintAnnotation) {
        this.entityClass = constraintAnnotation.entity();
        this.requireEditor = constraintAnnotation.requireEditor();
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext context) {
        if (id == null) return true;

        Optional<BoardPermissionEnum> permission = boardPermissionService.getBoardPermission(id, this.entityClass, Utils.getUserFromSecurityContext());
        if (permission.isPresent()) {
            BoardPermissionEnum boardPermissionEnum = permission.get();
            if (requireEditor && (boardPermissionEnum == BoardPermissionEnum.VIEWER)) {
                return buildValidationMessage(context, "You need to have editor permission to modify this resource!");
            }
            return true;
        } else {
            return buildValidationMessage(context, "You do not have permission to modify this resource!");
        }

    }

    private boolean buildValidationMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }


}