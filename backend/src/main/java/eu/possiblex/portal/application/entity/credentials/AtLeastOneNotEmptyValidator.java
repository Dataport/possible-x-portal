package eu.possiblex.portal.application.entity.credentials;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class AtLeastOneNotEmptyValidator implements ConstraintValidator<AtLeastOneNotEmpty, Object> {

    @Override
    public void initialize(AtLeastOneNotEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj == null) {
            return false;
        }

        try {
            for (Field field : obj.getClass().getDeclaredFields()) {
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                field.setAccessible(true);
                if (field.get(obj) != null && !field.get(obj).toString().isEmpty()) {
                    return true;
                }
            }
        } catch (IllegalAccessException e) {
            log.warn("Failed to access field", e);
        }

        return false;
    }
}