package rut.miit.beautySalon.utils;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Component
public class ValidationUtilImpl implements ValidationUtil {

    private final Validator validator;

    @Autowired
    public ValidationUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E object) {
        return this.validator.validate(object).isEmpty();
    }

    @Override
    public <E> Set <ConstraintViolation<E>> violations(E object) {
        return this.validator.validate(object);
    }
}
