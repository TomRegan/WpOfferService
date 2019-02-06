package io.github.tomregan.offerservice.datastore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static io.github.tomregan.offerservice.datastore.OfferData.offerBuilder;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional @SpringBootTest class OfferServiceIntegrationTest {

    @SuppressWarnings("unused") @Autowired private OfferRepository offerRepository;
    @SuppressWarnings("unused") @Autowired private OfferService offerService;

    @Test void retrieveOfferById() {
        long id = offerService.allOffers().findFirst()
                .map(OfferData::getId)
                .orElseThrow(() -> new AssertionError("Precondition: an offer must exist!"));
        Optional<OfferData> offer = offerService.offerWithId(id);
        assertThat(offer.map(OfferData::getId).orElse(null), is((equalTo(id))));
    }

    @Test void retrieveOfferByMissingId() {
        assertTrue(offerService.allOffers().findFirst().isPresent(),
                "Precondition: some offer must exist!");
        assertFalse(offerService.offerWithId(853211).isPresent());
    }

    @Test void allOffers() {
        List<String> offers = offerService.allOffers().map(OfferData::getDescription).collect(toList());
        assertThat(offers, hasItems("firstOffer", "secondOffer"));
    }

    @Test void createOffer() {
        assertFalse(offerService.allOffers().map(OfferData::getDescription).anyMatch(it -> it.equalsIgnoreCase("createOffer")),
                "Precondition: offer to be created must not exist!");
        OfferData createdOffer = offerService.createOffer(offerBuilder()
                .description("createOffer")
                .expiry(Instant.now())
                .valid(true)
                .currency(5, "GBP")
                .build());
        assertThat(offerService.offerWithId(createdOffer.getId()).map(OfferData::getDescription).orElse(null), is(equalTo("createOffer")));
    }

    @Test void validateExpiryOnCreation() {
        OfferData createdOffer = offerService.createOffer(offerBuilder()
                .description("validateExpiry")
                .expiry(Instant.MIN)
                .valid(true)
                .currency(5, "GBP")
                .build());
        assertAll(() -> assertThat(createdOffer.isValid(), is(false)));
    }

    @Test void validateExpiryOnRetrieveAll() {
        assertTrue(offerService.allOffers().allMatch(OfferData::isValid),
                "Precondition: all existing offers must be valid!");
        OfferData expiredOffer = offerBuilder()
                .description("validateExpiry")
                .expiry(Instant.MIN)
                .valid(true)
                .currency(5, "GBP")
                .build();
        offerRepository.save(expiredOffer);
        assertTrue(expiredOffer.isValid(), "Condition: expired offer is valid before retrieval");
        assertFalse(offerService.allOffers().allMatch(OfferData::isValid));
    }

    @Test void validateExpiryOnRetrieveOne() {
        assertTrue(offerService.allOffers().allMatch(OfferData::isValid),
                "Precondition: all existing offers must be valid!");
        OfferData expiredOffer = offerBuilder()
                .description("validateExpiry")
                .expiry(Instant.MIN)
                .valid(true)
                .currency(5, "GBP")
                .build();
        long id = offerRepository.save(expiredOffer).getId();
        assertTrue(expiredOffer.isValid(), "Condition: expired offer is valid before retrieval");
        assertFalse(offerService.offerWithId(id).filter(OfferData::isValid).isPresent());
    }

    @Test void cancelOffer() {
        assertTrue(offerService.allOffers().allMatch(OfferData::isValid),
                "Precondition: all existing offers must be valid!");
        long id = offerService.allOffers().findFirst()
                .map(OfferData::getId)
                .orElseThrow(() -> new AssertionError("Precondition: an offer must exist!"));
        assertTrue(offerService.offerWithId(id).map(OfferData::isValid).orElse(Boolean.FALSE),
                "Condition: offer is valid before it is cancelled");
        offerService.cancel(id);
        assertFalse(offerService.offerWithId(id).map(OfferData::isValid).orElse(Boolean.TRUE));
    }

    @Test void cancelMissingOffer() {
        assertFalse(offerService.offerWithId(112358).isPresent(),
                "Precondition: offer must not exist");
        assertThrows(NoSuchElementException.class, () -> offerService.cancel(112358));
    }
}