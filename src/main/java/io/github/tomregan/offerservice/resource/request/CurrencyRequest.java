package io.github.tomregan.offerservice.resource.request;


import io.github.tomregan.offerservice.validation.CurrencyCodeExists;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@SuppressWarnings("unused")
public class CurrencyRequest {

    @NotNull @PositiveOrZero private BigDecimal amount;
    @NotNull @CurrencyCodeExists private String code;

    static CurrencyRequest currencyRequest(Number amount, String code) {
        if (amount == null || code == null) {
            return null;
        }
        return new CurrencyRequest(new BigDecimal(amount.toString()), code);
    }

    public CurrencyRequest() {
    }

    private CurrencyRequest(BigDecimal amount, String code) {
        this.amount = amount;
        this.code = code;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override public String toString() {
        return "CurrencyRequest{" +
                "amount=" + amount +
                ", code=" + code +
                '}';
    }
}
