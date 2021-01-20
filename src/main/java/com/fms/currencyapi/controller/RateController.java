package com.fms.currencyapi.controller;

import com.fms.currencyapi.dto.RateDto;
import com.fms.currencyapi.entity.Rate;
import com.fms.currencyapi.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rates")
public class RateController {

    @Autowired
    private RateService rateService;

    @PostMapping
    public ResponseEntity<Rate> addOrUpdate(@RequestBody RateDto rateDto) {
        if (rateDto == null || StringUtils.isEmpty(rateDto.getFrom()) || StringUtils.isEmpty(rateDto.getTo()) || rateDto.getRate() == null || rateDto.getRate().doubleValue() == 0)
            return ResponseEntity.badRequest().build();
        Rate savedRate = rateService.saveRate(rateDto);

        return ResponseEntity.ok(savedRate);
    }
}
