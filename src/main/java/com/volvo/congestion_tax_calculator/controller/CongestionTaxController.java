package com.volvo.congestion_tax_calculator.controller;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.volvo.congestion_tax_calculator.Services.CongestionTaxCalculator;
import com.volvo.congestion_tax_calculator.modal.TaxCalculatorRequest;

@RestController
@RequestMapping(path = "/api/congestion")
public class CongestionTaxController {

	@Autowired
	CongestionTaxCalculator congestionTaxCalculator;
	
	@PostMapping(path = "/calculate")
	public ResponseEntity<Object> getTollFee(@RequestBody TaxCalculatorRequest request) {
		HashMap<String, String> body = new HashMap<String, String>();
		try {
			
			return new ResponseEntity<>(congestionTaxCalculator.getTax(request.getVehicleType(), request.getDates(), request.getCity()), HttpStatus.OK);
			
		}catch(Exception e) {
			body.put("message", "Bad Request");
			return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		}
	}
}
