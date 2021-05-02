package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ExchangeValue;
import com.example.demo.repository.ExchangeValueRepository;

@RestController
public class CurrencyExchangeController {

	@Autowired
	ExchangeValueRepository exchangeValueRepo;

	@GetMapping("/currency-exchange/retrieve")
	public List<ExchangeValue> getValues() {
		return exchangeValueRepo.findAll();
	}

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue exchangeValue = exchangeValueRepo.findByFromAndTo(from, to);
		return exchangeValue;
		// return new ExchangeValue(1000L,from,to,BigDecimal.valueOf(70));
	}
	
	/*{
	    "id": 1004,
	    "from" : "GBP",
	    "to" : "EURO",
	    "conversionMultiple": 1.15
	}*/
	@PostMapping("/currency-exchange/add")
	public ExchangeValue addExchangeValue(@RequestBody ExchangeValue exchangeValue) {
		return exchangeValueRepo.save(exchangeValue);
		/*
		 * if (excValue != null) return new ResponseEntity<ExchangeValue>(excValue,
		 * HttpStatus.FOUND); else return new
		 * ResponseEntity<ExchangeValue>(HttpStatus.NOT_FOUND);
		 */
	}
	
	@PutMapping("/currency-exchange/update/from/{from}/to/{to}/conversionFactor/{conversionFactor}")
	public ExchangeValue updateExchangeValue(@PathVariable String from, @PathVariable String to, 
			@PathVariable BigDecimal conversionFactor) {
		ExchangeValue exchangeValue = exchangeValueRepo.findByFromAndTo(from, to);
		exchangeValue.setConversionMultiple(conversionFactor);
		exchangeValueRepo.save(exchangeValue);
		return exchangeValue;
		
		
	}
}
