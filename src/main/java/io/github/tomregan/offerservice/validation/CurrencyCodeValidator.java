package io.github.tomregan.offerservice.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static javax.money.Monetary.isCurrencyAvailable;

public class CurrencyCodeValidator implements ConstraintValidator<CurrencyCodeExists, String> {

    @Override public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || isCurrencyAvailable(value);
    }
}
