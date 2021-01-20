package com.fms.currencyapi.controller;

import com.fms.currencyapi.dto.ExchangeResponse;
import com.fms.currencyapi.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;


    @GetMapping(value = "/{fromCurrency}/{amount}/{toCurrency}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExchangeResponse> exchangeCurrency(@PathVariable("fromCurrency") String fromCurrency,
                                                             @PathVariable("amount") Double amount,
                                                             @PathVariable("toCurrency") String toCurrency) {
        if (StringUtils.isEmpty(fromCurrency) || StringUtils.isEmpty(toCurrency))
            return ResponseEntity.badRequest().build();
        if (amount == 0)
            return ResponseEntity.ok(buildResponse(fromCurrency, toCurrency, 0D, 0D));
        if (fromCurrency.equals(toCurrency))
            return ResponseEntity.ok(buildResponse(fromCurrency, toCurrency, amount, amount));

        ExchangeResponse response = exchangeService.exchange(fromCurrency, amount, toCurrency);
        if (null == response)
            return ResponseEntity.ok(new ExchangeResponse(fromCurrency, toCurrency, amount, "Could not convert."));

        return ResponseEntity.ok(response);
    }

    private ExchangeResponse buildResponse(String fromCurrency, String toCurrency, Double amount, Double result) {
        return new ExchangeResponse(fromCurrency, toCurrency, amount, result, Instant.now());
    }
}