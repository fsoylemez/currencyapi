package com.fms.currencyapi.dto;

import java.io.Serializable;

public class RateDto implements Serializable {

    private String from;

    private String to;

    private Double rate;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
