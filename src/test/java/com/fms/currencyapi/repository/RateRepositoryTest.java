package com.fms.currencyapi.repository;

import com.fms.currencyapi.entity.Rate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RateRepositoryTest {

    @Autowired
    private RateRepository rateRepository;


    @Test
    public void whenCreateRate() {
        Rate rate = new Rate();
        rate.setFromCurrency("EUR");
        rate.setToCurrency("USD");
        rate.setCoefficient(1.06D);
        rate.setUpdateDate(Instant.now());

        Rate savedRate = rateRepository.save(rate);
        assertNotNull(savedRate);
        assertEquals("EUR", savedRate.getFromCurrency());
        assertEquals("USD", savedRate.getToCurrency());
        assertEquals(1.06D, savedRate.getCoefficient(), 0);

    }

    @Test
    public void whenFindAll() {
        Rate rate = new Rate();
        rate.setFromCurrency("EUR");
        rate.setToCurrency("USD");
        rate.setCoefficient(1.06D);
        rate.setUpdateDate(Instant.now());

        Rate savedRate = rateRepository.save(rate);

        List<Rate> rates = (List<Rate>) rateRepository.findAll();
        assertNotNull(rates);
        assertEquals("EUR", rates.get(0).getFromCurrency());
        assertEquals("USD", rates.get(0).getToCurrency());
        assertEquals(1.06D, rates.get(0).getCoefficient(), 0);
    }

    @Test
    public void whenUpdateRate() {
        Rate rate = new Rate();
        rate.setFromCurrency("EUR");
        rate.setToCurrency("USD");
        rate.setCoefficient(1.06D);
        rate.setUpdateDate(Instant.now());

        Rate savedRate = rateRepository.save(rate);
        savedRate.setCoefficient(1.08D);
        savedRate = rateRepository.save(rate);
        assertNotNull(savedRate);
        assertEquals("EUR", savedRate.getFromCurrency());
        assertEquals("USD", savedRate.getToCurrency());
        assertEquals(1.08D, savedRate.getCoefficient(), 0);
    }
}
