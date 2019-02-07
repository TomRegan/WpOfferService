package io.github.tomregan.offerservice.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.tomregan.offerservice.datastore.OfferData;
import io.github.tomregan.offerservice.datastore.OfferService;
import io.github.tomregan.offerservice.resource.request.OfferRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.time.Instant;

import static io.github.tomregan.offerservice.datastore.OfferData.offerBuilder;
import static io.github.tomregan.offerservice.resource.request.OfferRequest.offerRequest;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CreateOfferController.class)
class CreateOfferControllerTest {

    @SuppressWarnings("unused") @Autowired private MockMvc mvc;
    @SuppressWarnings("unused") @Autowired private ObjectMapper objectMapper;
    @SuppressWarnings("unused") @MockBean private OfferService offerService;

    @Test void validOffer() throws Exception {
        doReturn(anyOfferData()).when(offerService).createOffer(any(OfferData.class));

        mvc.perform(post("/offers")
                .content(json(anyOfferRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description", is(equalTo("some-description"))))
                .andExpect(jsonPath("$.valid", is(equalTo(true))));
    }

    @Test void invalidOffer() throws Exception {
        mvc.perform(post("/offers")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test void missingContentType() throws Exception {
        mvc.perform(post("/offers")
                .content(json(anyOfferRequest())))
                .andExpect(status().isUnsupportedMediaType());
    }

    private String json(OfferRequest offerRequest) throws JsonProcessingException {
        return objectMapper.writeValueAsString(offerRequest);
    }

    private static OfferRequest anyOfferRequest() {
        return offerRequest(anyOfferData());
    }

    private static OfferData anyOfferData() {
        return offerBuilder()
                .id(1)
                .description("some-description")
                .expiry(Instant.now().plus(Duration.ofDays(1)))
                .valid(true)
                .currency(8, "EUR")
                .build();
    }
}