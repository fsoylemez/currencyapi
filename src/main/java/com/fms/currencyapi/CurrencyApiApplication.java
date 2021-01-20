package com.fms.currencyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Currency api application is a simple rest api
 * that provides exchange between currencies
 *
 * @author Fatih Soylemez
 * @version 1.0
 * @since 2020-03-26
 */
@SpringBootApplication
@ComponentScan("com.fms.currencyapi")
@EnableAsync
public class CurrencyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyApiApplication.class, args);
    }

}
