package com.example.demo.facade;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.CalculatedAmount;

//@FeignClient(name="currencyexchange", url="http://currencyexchange:8000/")
@FeignClient(name="currencyexchange", url="http://localhost:8000/")
public interface CurrencyExchangeProxy {

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CalculatedAmount retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);
}
