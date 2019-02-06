package io.github.tomregan.offerservice.resource.request;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static io.github.tomregan.offerservice.ValidationMatcher.isInvalid;
import static io.github.tomregan.offerservice.ValidationMatcher.isValid;
import static io.github.tomregan.offerservice.datastore.OfferData.offerBuilder;
import static io.github.tomregan.offerservice.resource.request.OfferRequest.offerRequest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class OfferRequestTest {

    @Test void descriptionValidity() {
        OfferRequest anyDescription = offerRequest(offerBuilder().description("any-description").expiry(Instant.MAX).valid(true).currency(10, "EUR").build());
        OfferRequest emptyDescription = offerRequest(offerBuilder().description("").expiry(Instant.MAX).valid(true).currency(10, "EUR").build());
        OfferRequest nullDescription = offerRequest(offerBuilder().description(null).expiry(Instant.MAX).valid(true).currency(10, "EUR").build());

        assertAll(
                () -> assertThat(anyDescription, isValid()),
                () -> assertThat(emptyDescription, isInvalid()),
                () -> assertThat(nullDescription, isInvalid()));
    }

    @Test void expiryDateValidity() {

        OfferRequest futureExpiry = offerRequest(offerBuilder().description("any-description").expiry(Instant.MAX).valid(true).currency(10, "EUR").build());
        OfferRequest pastExpiry = offerRequest(offerBuilder().description("any-description").expiry(Instant.MIN).valid(true).currency(10, "EUR").build());
        OfferRequest nullExpiry = offerRequest(offerBuilder().description("any-description").expiry(null).valid(true).currency(10, "EUR").build());

        assertAll(
                () -> assertThat(futureExpiry, isValid()),
                () -> assertThat(pastExpiry, isInvalid()),
                () -> assertThat(nullExpiry, isInvalid()));
    }

    @Test void validityValidity() {

        OfferRequest trueValidity = offerRequest(offerBuilder().description("any-description").expiry(Instant.MAX).valid(true).currency(10, "EUR").build());
        OfferRequest falseValidity = offerRequest(offerBuilder().description("any-description").expiry(Instant.MAX).valid(false).currency(10, "EUR").build());
        OfferRequest nullValidity = offerRequest(offerBuilder().description("any-description").expiry(Instant.MAX).valid(null).currency(10, "EUR").build());

        assertAll(
                () -> assertThat(trueValidity, isValid()),
                () -> assertThat(falseValidity, isValid()),
                () -> assertThat(nullValidity, isInvalid()));
    }

    @Test void currencyValidity() {

        OfferRequest anyCurrency = offerRequest(offerBuilder().description("any-description").expiry(Instant.MAX).valid(true).currency(10, "EUR").build());
        OfferRequest nullCurrency = offerRequest(offerBuilder().description("any-description").expiry(Instant.MAX).valid(false).currency(null, null).build());

        assertAll(
                () -> assertThat(anyCurrency, isValid()),
                () -> assertThat(nullCurrency, isInvalid()));
    }

}