package io.github.tomregan.offerservice.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.tomregan.offerservice.datastore.OfferData;
import io.github.tomregan.offerservice.datastore.OfferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Stream;

import static io.github.tomregan.offerservice.datastore.OfferData.offerBuilder;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RetrieveOfferController.class)
class RetrieveOfferControllerTest {
    @SuppressWarnings("unused") @Autowired private MockMvc mvc;
    @SuppressWarnings("unused") @Autowired private ObjectMapper objectMapper;
    @SuppressWarnings("unused") @MockBean private OfferService offerService;

    @Test void retrieveOneOffer() throws Exception {
        doReturn(presentOfferWithId(1)).when(offerService).offerWithId(eq(1L));

        mvc.perform(get("/offers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test void retrieveAllOffers() throws Exception {
        doReturn(Stream.of(offerWithId(1), offerWithId(2))).when(offerService).allOffers();

        mvc.perform(get("/offers"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test void validParameters() throws Exception {
        mvc.perform(get("/offers/-1"))
                .andExpect(status().isBadRequest());
    }

    @Test void exists() throws Exception {
        mvc.perform(get("/offers/112358"))
                .andExpect(status().isNotFound());
    }

    private static Optional<OfferData> presentOfferWithId(@SuppressWarnings("SameParameterValue") int id) {
        return Optional.of(offerWithId(id));
    }

    private static OfferData offerWithId(int id) {
        return offerBuilder()
                .id(id)
                .description("some-description")
                .expiry(Instant.now())
                .valid(true)
                .currency(8, "GBP")
                .build();
    }
}