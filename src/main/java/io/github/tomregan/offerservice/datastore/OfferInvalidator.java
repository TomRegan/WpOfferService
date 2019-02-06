package io.github.tomregan.offerservice.datastore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

import static io.github.tomregan.offerservice.datastore.OfferData.copyBuilder;

final class OfferInvalidator {
    private static final Logger logger = LoggerFactory.getLogger(OfferInvalidator.class);

    static OfferData checkExpiry(OfferData offer) {
        Instant now = Instant.now();
        Instant expiry = offer.getExpiry();
        if (expiry.isBefore(now)) {
            logger.info("Offer {} has expired: {} is after {}", offer.getId(), now, expiry);
            return copyBuilder(offer).valid(false).build();
        }
        return offer;
    }
}
