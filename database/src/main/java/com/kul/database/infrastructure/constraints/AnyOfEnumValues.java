package com.kul.database.infrastructure.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {AnyOfEnumValuesValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface AnyOfEnumValues {
    String message() default "must match any of expected values";
    Class<?>[] groups() default {};
    Class<? extends Enum<?>> value();
    Class<? extends Payload>[] payload() default {};
}

