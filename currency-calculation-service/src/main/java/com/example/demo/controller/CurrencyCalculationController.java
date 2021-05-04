package com.example.demo.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.facade.CurrencyExchangeProxy;
import com.example.demo.model.CalculatedAmount;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class CurrencyCalculationController {
	Logger logger= LoggerFactory.getLogger(CurrencyCalculationController.class);
	
	@Autowired
	CurrencyExchangeProxy currencyExchangeProxy;

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	@CircuitBreaker(name="currencyretry", fallbackMethod = "calculateAmountRetry")
	public CalculatedAmount calculateAmount(@PathVariable String from , @PathVariable String to, @PathVariable BigDecimal quantity) {
		CalculatedAmount calcAmount=currencyExchangeProxy.retrieveExchangeValue(from, to);
		logger.info("exchange value : {}", calcAmount);
		return new CalculatedAmount(calcAmount.getId(), calcAmount.getFrom(), calcAmount.getTo(), 
				calcAmount.getConversionMultiple(), quantity,quantity.multiply(calcAmount.getConversionMultiple()),calcAmount.getPort());
	}
	
	public CalculatedAmount calculateAmountRetry(String from ,String to,BigDecimal quantity,Throwable t){
		logger.info("\n\n In fallback method \n\n");
		return new CalculatedAmount(100L, "INR", "USD", BigDecimal.valueOf(80), BigDecimal.valueOf(50), BigDecimal.valueOf(4000), 0);
	}
}
