package com.fms.currencyapi.service;

import com.fms.currencyapi.dto.RateDto;
import com.fms.currencyapi.entity.Rate;
import com.fms.currencyapi.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;

@Service
public class RateService {

    @Autowired
    private RateRepository rateRepository;

    public List<Rate> findAll() {
        return (List<Rate>) rateRepository.findAll();
    }

    public Rate saveRate(RateDto rateDto) {
        Rate rateToSave = null;
        List<Rate> existingRates = rateRepository.findByFromCurrencyAndToCurrency(rateDto.getFrom(), rateDto.getTo());
        if (!CollectionUtils.isEmpty(existingRates)) {
            rateToSave = existingRates.get(0);
            rateToSave.setCoefficient(rateDto.getRate());
        } else {
            rateToSave = new Rate(rateDto.getFrom(), rateDto.getTo(), rateDto.getRate());
        }
        rateToSave.setUpdateDate(Instant.now());

        return rateRepository.save(rateToSave);
    }
}
