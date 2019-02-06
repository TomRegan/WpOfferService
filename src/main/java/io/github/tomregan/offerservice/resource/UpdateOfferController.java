package io.github.tomregan.offerservice.resource;

import io.github.tomregan.offerservice.datastore.OfferData;
import io.github.tomregan.offerservice.datastore.OfferService;
import io.github.tomregan.offerservice.resource.response.OfferResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static io.github.tomregan.offerservice.resource.response.OfferResponse.offerResponse;

@Api(tags = "offers", description = "Offer management API")
@RestController class UpdateOfferController {

    private static final Logger logger = LoggerFactory.getLogger(UpdateOfferController.class);
    @SuppressWarnings("unused") @Autowired private OfferService offerService;

    @ApiOperation(value = "Cancel an offer")
    @SuppressWarnings("unused")
    @PutMapping(path = "/offers/{id}/cancel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    OfferResponse cancel(@PathVariable Long id) {
        logger.info("Updating validity of offer with id {} to false", id);
        if (id.compareTo(0L) < 0) {
            logger.warn("id {} is not valid", id);
            throw new IllegalArgumentException();
        }
        OfferData updatedOffer = offerService.cancel(id);
        logger.info("Set validity of offer with id {} to false", id);
        return offerResponse(updatedOffer);
    }
}
