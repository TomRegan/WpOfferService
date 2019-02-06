package io.github.tomregan.offerservice.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * The annotated element must be a known currency.
 *
 * {@code null} elements are considered valid.
 */
@SuppressWarnings("unused")
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { CurrencyCodeValidator.class })
public @interface CurrencyCodeExists {
    String message() default "Invalid currency code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
