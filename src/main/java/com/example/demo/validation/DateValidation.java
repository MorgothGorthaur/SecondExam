package com.example.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy =  DateValidationImpl.class)
public @interface DateValidation {
    String message() default "Birth year must be before today`s date!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
