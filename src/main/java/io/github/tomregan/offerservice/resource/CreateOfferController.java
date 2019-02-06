package io.github.tomregan.offerservice.resource;

import io.github.tomregan.offerservice.datastore.OfferService;
import io.github.tomregan.offerservice.resource.request.OfferRequest;
import io.github.tomregan.offerservice.resource.response.OfferResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static io.github.tomregan.offerservice.datastore.OfferData.offerBuilder;
import static io.github.tomregan.offerservice.resource.response.OfferResponse.offerResponse;

@Api(tags = "offers", description = "Offer management API")
@RestController final class CreateOfferController {

    private static final Logger logger = LoggerFactory.getLogger(CreateOfferController.class);
    private final OfferService offerService;

    public CreateOfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @ApiOperation(value = "Create an offer")
    @ApiResponses(
            @ApiResponse(code = 400, message = "Bad Request")
    )
    @SuppressWarnings("unused")
    @PutMapping(path = "/offers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    OfferResponse create(@Valid @RequestBody OfferRequest offer) {
        logger.info("Creating offer {}", offer);
        OfferResponse response = offerResponse(offerService.createOffer(offerBuilder()
                .description(offer.getDescription())
                .expiry(offer.getExpiry())
                .valid(offer.getValid())
                .currency(
                        offer.getCurrency().getAmount(),
                        offer.getCurrency().getCode())
                .build()));
        logger.info("Created offer with id {}", response.getId());
        return response;

    }
}
