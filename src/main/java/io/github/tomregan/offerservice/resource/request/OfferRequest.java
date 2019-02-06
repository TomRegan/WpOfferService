package io.github.tomregan.offerservice.resource.request;

import io.github.tomregan.offerservice.datastore.OfferData;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

import static io.github.tomregan.offerservice.resource.request.CurrencyRequest.currencyRequest;

@SuppressWarnings("unused") public class OfferRequest {

    @NotEmpty private String description;
    @NotNull @Future private Instant expiry;
    @NotNull private Boolean valid;
    @Valid @NotNull private CurrencyRequest currency;

    public OfferRequest() {
    }

    private OfferRequest(String description, Instant expiry, Boolean valid, CurrencyRequest currency) {
        this.description = description;
        this.expiry = expiry;
        this.valid = valid;
        this.currency = currency;
    }

    public static OfferRequest offerRequest(OfferData offer) {
        return new OfferRequest(
                offer.getDescription(),
                offer.getExpiry(),
                offer.isValid(),
                currencyRequest(
                        offer.getCurrencyAmount(),
                        offer.getCurrencyCode()));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getExpiry() {
        return expiry;
    }

    public void setExpiry(Instant expiry) {
        this.expiry = expiry;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public CurrencyRequest getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyRequest currency) {
        this.currency = currency;
    }

    @Override public String toString() {
        return "OfferRequest{" +
                "description='" + description + '\'' +
                ", expiry=" + expiry +
                ", valid=" + valid +
                ", currency=" + currency +
                '}';
    }
}
