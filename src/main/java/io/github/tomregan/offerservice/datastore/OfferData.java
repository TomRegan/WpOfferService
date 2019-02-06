package io.github.tomregan.offerservice.datastore;

import io.github.tomregan.offerservice.validation.CurrencyCodeExists;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity public final class OfferData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty private String description;
    @NotNull private Instant expiry;
    @NotNull private Boolean valid;
    @NotNull @PositiveOrZero private BigDecimal currencyAmount;
    @NotNull @CurrencyCodeExists private String currencyCode;

    public OfferData() {
    }

    private OfferData(Long id,
                      String description,
                      Instant expiry,
                      Boolean valid,
                      BigDecimal currencyAmount,
                      String currencyCode) {
        this.id = id;
        this.description = description;
        this.expiry = expiry;
        this.valid = valid;
        this.currencyAmount = currencyAmount;
        this.currencyCode = currencyCode;
    }

    public static OfferData.Builder offerBuilder() {
        return new Builder();
    }

    static OfferData.Builder copyBuilder(OfferData offer) {
        return OfferData.offerBuilder()
                .id(offer.getId())
                .description(offer.getDescription())
                .expiry(offer.getExpiry())
                .valid(offer.isValid())
                .currency(offer.getCurrencyAmount(), offer.getCurrencyCode());
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

    public Boolean isValid() {
        return valid;
    }

    public BigDecimal getCurrencyAmount() {
        return currencyAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OfferData offer = (OfferData) o;
        return Objects.equals(id, offer.id) &&
                Objects.equals(description, offer.description) &&
                Objects.equals(expiry, offer.expiry) &&
                Objects.equals(valid, offer.valid) &&
                Objects.equals(currencyAmount, offer.currencyAmount) &&
                Objects.equals(currencyCode, offer.currencyCode);
    }

    @Override public int hashCode() {
        return Objects.hash(id, description, expiry, valid, currencyAmount, currencyCode);
    }

    @Override public String toString() {
        return "OfferData{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", expiry=" + expiry +
                ", valid=" + valid +
                ", currencyAmount=" + currencyAmount +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String description;
        private Instant expires;
        private Boolean valid;
        private BigDecimal currencyAmount;
        private String currencyCode;

        public Builder id(Number id) {
            this.id = id == null ? null : id.longValue();
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder expiry(Instant expires) {
            this.expires = expires;
            return this;
        }

        public Builder valid(Boolean valid) {
            this.valid = valid;
            return this;
        }

        public Builder currency(Number currencyAmount, String currencyCode) {
            if (currencyAmount != null) {
                this.currencyAmount = new BigDecimal(currencyAmount.toString());
            }
            if (currencyCode != null) {
                this.currencyCode = currencyCode;
            }
            return this;
        }

        public OfferData build() {
            return new OfferData(id, description, expires, valid, currencyAmount, currencyCode);
        }
    }
}
