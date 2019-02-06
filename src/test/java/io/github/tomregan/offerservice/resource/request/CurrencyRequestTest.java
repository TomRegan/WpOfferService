package io.github.tomregan.offerservice.resource.request;

import org.junit.jupiter.api.Test;

import static io.github.tomregan.offerservice.resource.request.CurrencyRequest.currencyRequest;
import static io.github.tomregan.offerservice.ValidationMatcher.isInvalid;
import static io.github.tomregan.offerservice.ValidationMatcher.isValid;
import static javax.money.Monetary.isCurrencyAvailable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CurrencyRequestTest {

    @Test void amountValidity() {
        assertAll(
                () -> assertThat(currencyRequest(8, "EUR"), isValid()),
                () -> assertThat(currencyRequest(0, "EUR"), isValid()),
                () -> assertThat(currencyRequest(-1, "EUR"), isInvalid()));
    }

    @Test void codeValidity() {
        assertFalse(isCurrencyAvailable("XZY"), "This test is broken! This test asserts that XYZ " +
                "is not a valid currency, but on your system it appears to be valid.");
        assertAll(
                () -> assertThat(currencyRequest(8, "EUR"), isValid()),
                () -> assertThat(currencyRequest(8, "XYZ"), isInvalid()));
    }
}