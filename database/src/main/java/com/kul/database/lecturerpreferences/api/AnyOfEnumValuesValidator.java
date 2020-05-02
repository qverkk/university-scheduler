package com.kul.database.lecturerpreferences.api;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AnyOfEnumValuesValidator implements ConstraintValidator<AnyOfEnumValues, String> {
    private Set<String> values;

    @Override
    public void initialize(AnyOfEnumValues constraintAnnotation) {
        values = Stream.of(constraintAnnotation.value().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = values.contains(value);

        if (!result) {
            context.buildConstraintViolationWithTemplate("unexpected value").addConstraintViolation();
        }

        return result;
    }
}
