package com.fms.currencyapi.service;

import com.fms.currencyapi.entity.Rate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ExchangeServiceTest {

    @InjectMocks
    private ExchangeService exchangeService;

    @Mock
    private RateService rateService;

    @Test
    public void when_noValidRateAvailable() {
        Rate rate = new Rate();
        rate.setFromCurrency("EUR");
        rate.setToCurrency("USD");
        rate.setCoefficient(1.06D);
        rate.setUpdateDate(Instant.now());

        when(rateService.findAll()).thenReturn(Arrays.asList(rate));

        double result = exchangeService.convert("EUR", 10D, "TRY");
        assertEquals(-1, result, 0);
    }

    @Test
    public void when_directRateAvailable() {
        Rate rate = new Rate();
        rate.setFromCurrency("EUR");
        rate.setToCurrency("USD");
        rate.setCoefficient(1.06D);
        rate.setUpdateDate(Instant.now());

        when(rateService.findAll()).thenReturn(Arrays.asList(rate));

        double result = exchangeService.convert("EUR", 10D, "USD");
        assertEquals(10.6D, result, 0.03);
    }

    @Test
    public void when_inDirectRateAvailable() {
        Rate rate = new Rate();
        rate.setFromCurrency("EUR");
        rate.setToCurrency("TRY");
        rate.setCoefficient(7.08D);
        rate.setUpdateDate(Instant.now());

        Rate rate2 = new Rate();
        rate2.setFromCurrency("TRY");
        rate2.setToCurrency("USD");
        rate2.setCoefficient(0.15D);
        rate2.setUpdateDate(Instant.now());

        when(rateService.findAll()).thenReturn(Arrays.asList(rate, rate2));

        double result = exchangeService.convert("EUR", 10D, "USD");
        assertEquals(10.6D, result, 0.03);
    }

    @Test
    public void when_chainConversionNeeded() {
        Rate rate = new Rate();
        rate.setFromCurrency("EUR");
        rate.setToCurrency("USD");
        rate.setCoefficient(1.06D);
        rate.setUpdateDate(Instant.now());

        Rate rate2 = new Rate();
        rate2.setFromCurrency("USD");
        rate2.setToCurrency("GBP");
        rate2.setCoefficient(0.82D);
        rate2.setUpdateDate(Instant.now());

        Rate rate3 = new Rate();
        rate3.setFromCurrency("GBP");
        rate3.setToCurrency("AUD");
        rate3.setCoefficient(2.02D);
        rate3.setUpdateDate(Instant.now());

        Rate rate4 = new Rate();
        rate4.setFromCurrency("AUD");
        rate4.setToCurrency("JPY");
        rate4.setCoefficient(65.73D);
        rate4.setUpdateDate(Instant.now());

        Rate rate5 = new Rate();
        rate5.setFromCurrency("JPY");
        rate5.setToCurrency("TRY");
        rate5.setCoefficient(0.059D);
        rate5.setUpdateDate(Instant.now());

        when(rateService.findAll()).thenReturn(Arrays.asList(rate, rate2, rate3, rate4, rate5));

        double result = exchangeService.convert("EUR", 10D, "TRY");
        assertEquals(68.09D, result, 0.03);
    }
}
