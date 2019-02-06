package io.github.tomregan.offerservice.resource;

import io.github.tomregan.offerservice.datastore.OfferService;
import io.github.tomregan.offerservice.resource.response.OfferResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

@Api(tags = "offers", description = "Offer management API")
@RestController final class RetrieveOfferController {

    private static final Logger logger = LoggerFactory.getLogger(RetrieveOfferController.class);
    @SuppressWarnings("unused") @Autowired private OfferService offerService;

    @ApiOperation(value = "Retrieve all offers")
    @SuppressWarnings("unused")
    @GetMapping(path = "/offers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Collection<OfferResponse> all() {
        logger.info("Retrieving all offers");
        List<OfferResponse> offers = offerService.allOffers()
                .map(OfferResponse::offerResponse)
                .collect(toList());
        logger.info("Retrieved {} offers", offers.size());
        return offers;
    }

    @ApiOperation(value = "Retrieve an offer")
    @ApiResponses(
            @ApiResponse(code = 404, message = "Not Found")
    )
    @SuppressWarnings("unused")
    @GetMapping(path = "/offers/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    OfferResponse one(@PathVariable Long id) {
        logger.info("Retrieving offer with id {}", id);
        if (id.compareTo(0L) < 0) {
            logger.warn("Cannot serve offer request because id {} must be positive", id);
            throw new IllegalArgumentException();
        }
        OfferResponse offers = offerService.offerWithId(id)
                .map(OfferResponse::offerResponse)
                .orElseThrow(() -> {
                    logger.info("Cannot serve offer request because offer {} does not exist", id);
                    return new NoSuchElementException();
                });
        logger.info("Retrieved offer with id {}", id);
        return offers;
    }

}
