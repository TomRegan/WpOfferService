package io.github.tomregan.offerservice.datastore;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static io.github.tomregan.offerservice.datastore.OfferInvalidator.checkExpiry;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class OfferInvalidatorTest {

    @Test void expiryInPast() {
        OfferData invalidOffer = OfferData.offerBuilder()
                .description("pastExpiry")
                .expiry(Instant.MIN)
                .valid(true)
                .currency(3, "AUD")
                .build();
        assertThat(checkExpiry(invalidOffer).isValid(), is(false));
    }

    @Test void expiryInFuture() {
        OfferData invalidOffer = OfferData.offerBuilder()
                .description("futureExpiry")
                .expiry(Instant.MAX)
                .valid(true)
                .currency(3, "AUD")
                .build();
        assertThat(checkExpiry(invalidOffer).isValid(), is(true));
    }

    @Test void invalidFutureExpiry() {
        OfferData invalidOffer = OfferData.offerBuilder()
                .description("invalidOffer")
                .expiry(Instant.MAX)
                .valid(false)
                .currency(3, "AUD")
                .build();
        assertThat(checkExpiry(invalidOffer).isValid(), is(false));
    }
}