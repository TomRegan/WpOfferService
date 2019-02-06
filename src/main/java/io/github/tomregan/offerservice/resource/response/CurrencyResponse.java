package io.github.tomregan.offerservice.resource.response;

import java.math.BigDecimal;

@SuppressWarnings("unused") public final class CurrencyResponse {
    private BigDecimal amount;
    private String code;

    private CurrencyResponse(BigDecimal amount, String code) {

        this.amount = amount;
        this.code = code;
    }

    static CurrencyResponse currencyResponse(Number amount, String code) {
        return new CurrencyResponse(new BigDecimal(amount.toString()), code);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCode() {
        return code;
    }
}
