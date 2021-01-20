package com.fms.currencyapi.controller;

import com.fms.currencyapi.dto.ExchangeResponse;
import com.fms.currencyapi.service.ExchangeService;
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

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ExchangeController.class)
public class ExchangeControllerTest {

    @InjectMocks
    private ExchangeController exchangerController;

    @Mock
    private ExchangeService exchangeService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(exchangerController).build();
    }

    @Test
    public void when_zeroAmount() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/exchange/EUR/0/USD")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("0.0"));
    }

    @Test
    public void when_exchange() throws Exception {
        ExchangeResponse response = new ExchangeResponse("EUR", "USD", 10D, 10D, Instant.now());

        when(exchangeService.exchange("EUR", 10D, "USD")).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/exchange/EUR/10/USD")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("10.0"));
    }
}
