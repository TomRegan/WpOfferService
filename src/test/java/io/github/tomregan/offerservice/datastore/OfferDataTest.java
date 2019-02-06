package io.github.tomregan.offerservice.datastore;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class OfferDataTest {

    @Test
    void equalsAndHashcode() {
        // because this class will be stored in a collection, the equals contract
        // should be correctly implemented - I've made this simple by making the class effectively
        // immutable using the builder pattern, and using final declarations for the class and its properties
        EqualsVerifier.forClass(OfferData.class).verify();
    }
}