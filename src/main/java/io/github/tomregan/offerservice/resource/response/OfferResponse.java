package io.github.tomregan.offerservice.resource.response;

import io.github.tomregan.offerservice.datastore.OfferData;

import java.time.Instant;

import static io.github.tomregan.offerservice.resource.response.CurrencyResponse.currencyResponse;

@SuppressWarnings("unused") public final class OfferResponse {
    private Long id;
    private String description;
    private Instant expiry;
    private Boolean valid;
    private final CurrencyResponse currency;

    private OfferResponse(Long id, String description, Instant expiry, Boolean valid, CurrencyResponse currency) {
        this.id = id;
        this.description = description;
        this.expiry = expiry;
        this.valid = valid;
        this.currency = currency;
    }

    public static OfferResponse offerResponse(OfferData offer) {
        return new OfferResponse(
                offer.getId(),
                offer.getDescription(),
                offer.getExpiry(),
                offer.isValid(),
                currencyResponse(
                        offer.getCurrencyAmount(),
                        offer.getCurrencyCode()));
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Instant getExpiry() {
        return expiry;
    }

    public Boolean getValid() {
        return valid;
    }

    public CurrencyResponse getCurrency() {
        return currency;
    }
}
