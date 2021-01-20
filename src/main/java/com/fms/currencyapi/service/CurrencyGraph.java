package com.fms.currencyapi.service;

import com.fms.currencyapi.entity.Rate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyGraph {

    private Map<String, Map<String, Double>> currencies = new HashMap<String, Map<String, Double>>();

    private boolean rateFound;

    public CurrencyGraph(List<Rate> rates) {
        addCurrencies(rates);
    }

    public Map<String, Map<String, Double>> getCurrencies() {
        return currencies;
    }

    private void addCurrency(String from, String to, double rate) {
        if (currencies.get(from) == null) {
            Map<String, Double> directRates = new HashMap<String, Double>();
            directRates.put(to, rate);
            currencies.put(from, directRates);
        } else if (currencies.get(from).get(to) == null) {
            currencies.get(from).put(to, rate);
        }
    }

    public void addCurrencies(List<Rate> rates) {
        for (Rate r : rates) {
            addCurrency(r.getFromCurrency(), r.getToCurrency(), r.getCoefficient());
        }
    }

    double convert(String src, double amount, String dst) {
        if (src.equals(dst))
            return amount;

        if (currencies.get(src) == null) {
            return -1;
        }

        List<String> visited = new ArrayList<String>();
        visited.add(src);
        double rate = findRate(visited, src, dst, 1);
        if (rateFound)
            return rate * amount;
        return -1;
    }

    private double findRate(List<String> visited, String src, String dst, double rate) {
        if (rateFound == true) {
            return rate;
        }

        if (currencies.get(src).get(dst) != null) {
            rateFound = true;
            return rate * currencies.get(src).get(dst);
        }

        double origRate = rate;
        for (String adjacent : currencies.get(src).keySet()) {
            if (visited.contains(adjacent)) {
                continue;
            }
            visited.add(adjacent);
            if (currencies.get(adjacent) == null) {
                continue;
            }
            rate = findRate(visited, adjacent, dst, rate * currencies.get(src).get(adjacent));
            if (rateFound == true) {
                return rate;
            }
            visited.remove(adjacent);
            rate = origRate;
        }

        return origRate;
    }
}