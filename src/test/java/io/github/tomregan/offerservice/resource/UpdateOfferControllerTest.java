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

import static io.github.tomregan.offerservice.datastore.OfferData.offerBuilder;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UpdateOfferController.class)
class UpdateOfferControllerTest {

    @SuppressWarnings("unused") @MockBean private OfferService offerService;
    @SuppressWarnings("unused") @Autowired private MockMvc mvc;
    @SuppressWarnings("unused") @Autowired private ObjectMapper objectMapper;

    @Test void cancelOffer() throws Exception {
        doReturn(cancelledOfferWithId(3)).when(offerService).cancel(3L);
        mvc.perform(put("/offers/3/cancel"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.valid", is(false)));
    }

    private static OfferData cancelledOfferWithId(@SuppressWarnings("SameParameterValue") int id) {
        return offerBuilder()
                .id(id)
                .description("some-description")
                .expiry(Instant.now())
                .valid(false)
                .currency(8, "GBP")
                .build();
    }

}