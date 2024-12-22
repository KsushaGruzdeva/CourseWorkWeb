package rut.miit.beautySalon.utils;

import java.util.Set;

import jakarta.validation.ConstraintViolation;

public interface ValidationUtil {
    <E> boolean isValid(E object);
    <E> Set<ConstraintViolation<E>> violations(E object);
}
