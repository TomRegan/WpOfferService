package io.github.tomregan.offerservice;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static java.lang.String.format;

public abstract class ValidationMatcher<T> extends TypeSafeDiagnosingMatcher<T> {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> ValidationMatcher<T> isValid() {
        return new IsValid<>();
    }

    public static <T> ValidationMatcher<T> isInvalid() {
        return new IsInvalid<>();
    }

    private static final class IsValid<T> extends ValidationMatcher<T> {

        @Override protected boolean matchesSafely(T item, Description mismatchDescription) {
            Set<ConstraintViolation<T>> violations = validator.validate(item);
            if (violations != null && violations.size() != 0) {
                mismatchDescription.appendText(format("failed with message '%s'", flatten(violations)));
                return false;
            }
            return true;
        }

        @Override public void describeTo(Description description) {
            description.appendText("a valid result");
        }
    }

    private static final class IsInvalid<T> extends ValidationMatcher<T> {

        @Override protected boolean matchesSafely(T item, Description mismatchDescription) {
            Set<ConstraintViolation<T>> violations = validator.validate(item);
            if (violations == null || violations.size() == 0) {
                mismatchDescription.appendText("did not fail any validation");
                return false;
            }
            return true;
        }

        @Override public void describeTo(Description description) {
            description.appendText("an invalid result");
        }

    }

    private static <T> String flatten(Set<ConstraintViolation<T>> violations) {
        return violations.stream().findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse(null);
    }
}
