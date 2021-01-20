package com.fms.currencyapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.currencyapi.dto.RateDto;
import com.fms.currencyapi.entity.Rate;
import com.fms.currencyapi.service.RateService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RateController.class)
public class RateControllerTest {

    @InjectMocks
    private RateController rateController;

    @Mock
    private RateService rateService;

    private MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(rateController).build();
    }

    @Test
    public void whenSave() throws Exception {
        RateDto rateDto = new RateDto();
        rateDto.setFrom("EUR");
        rateDto.setTo("USD");
        rateDto.setRate(1.08D);

        Rate rate = new Rate();
        rate.setFromCurrency("EUR");
        rate.setToCurrency("USD");
        rate.setCoefficient(1.06D);
        rate.setUpdateDate(Instant.now());

        when(rateService.saveRate(any(RateDto.class))).thenReturn(rate);

        mockMvc.perform(MockMvcRequestBuilders.post("/rates")
                .content(asJsonString(rateDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.coefficient").value("1.06"));
    }
}
