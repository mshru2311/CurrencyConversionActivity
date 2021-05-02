package com.example.demo.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.facade.CurrencyExchangeProxy;
import com.example.demo.model.CalculatedAmount;

@RestController
public class CurrencyCalculationController {
	
	@Autowired
	CurrencyExchangeProxy currencyExchangeProxy;

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CalculatedAmount calculateAmount(@PathVariable String from , @PathVariable String to, @PathVariable BigDecimal quantity) {
		CalculatedAmount calcAmount=currencyExchangeProxy.retrieveExchangeValue(from, to);
		
		return new CalculatedAmount(calcAmount.getId(), calcAmount.getFrom(), calcAmount.getTo(), 
				calcAmount.getConversionMultiple(), quantity,quantity.multiply(calcAmount.getConversionMultiple()));
	}
}
