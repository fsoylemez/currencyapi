package com.fms.currencyapi.dto;

import java.io.Serializable;
import java.time.Instant;

public class ExchangeResponse implements Serializable {

    private String fromCurrency;

    private String toCurrency;

    private Double amount;

    private Double result;

    private Instant rateUpdateDate;

    private Instant exchangeDate;

    private String message;

    public ExchangeResponse(String fromCurrency, String toCurrency, Double amount, Double result, Instant exchangeDate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.result = result;
        this.exchangeDate = exchangeDate;
    }

    public ExchangeResponse(String fromCurrency, String toCurrency, Double amount,String message) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.message = message;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Instant getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(Instant exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public Instant getRateUpdateDate() {
        return rateUpdateDate;
    }

    public void setRateUpdateDate(Instant rateUpdateDate) {
        this.rateUpdateDate = rateUpdateDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
