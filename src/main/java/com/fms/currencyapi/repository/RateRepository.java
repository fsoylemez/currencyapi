package com.fms.currencyapi.repository;

import com.fms.currencyapi.entity.Rate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends CrudRepository<Rate, Long> {

    List<Rate> findByFromCurrencyAndToCurrency(String from, String to);

}
