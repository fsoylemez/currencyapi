package com.fms.currencyapi.service;

import com.fms.currencyapi.dto.ExchangeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ExchangeService {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeService.class );


    private static final int COULD_NOT_CONVERT = -1;

    @Autowired
    private RateService rateService;

    public double convert(String from, double amount, String to) {
        if (from.equals(to))
            return amount;

        CurrencyGraph graph = new CurrencyGraph(rateService.findAll());

        return graph.convert(from, amount, to);
    }

    public ExchangeResponse exchange(String from, double amount, String to) {
        double result = convert(from, amount, to);
        if (COULD_NOT_CONVERT == result) {
            logger.warn("Could not convert {} to {} with amount = {}",from,to,amount);
            return null;
        }
        logger.info("Converted {} into {} with amount = {}.result = {}",from,to,amount,result);
        return new ExchangeResponse(from, to, amount, result, Instant.now());
    }
}
