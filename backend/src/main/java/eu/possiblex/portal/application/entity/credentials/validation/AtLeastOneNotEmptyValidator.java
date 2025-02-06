package eu.possiblex.portal.application.entity.credentials.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class AtLeastOneNotEmptyValidator implements ConstraintValidator<AtLeastOneNotEmpty, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {

        // object must be non-null
        if (obj == null) {
            return false;
        }

        try {
            // iterate over all (non-inherited and non-static) fields of the object
            for (Field field : obj.getClass().getDeclaredFields()) {
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                // allow access and check if field is not null and not empty
                field.setAccessible(true);
                if (field.get(obj) != null && !field.get(obj).toString().isEmpty()) {
                    return true;
                }
            }
        } catch (IllegalAccessException e) {
            log.warn("Failed to access field", e);
        }

        // if we iterated over all fields and none was non-empty, return false
        return false;
    }
}