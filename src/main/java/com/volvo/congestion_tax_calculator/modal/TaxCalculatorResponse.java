package com.volvo.congestion_tax_calculator.modal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaxCalculatorResponse {
	
	@JsonProperty("tax_amount")
	private int taxAmount;
	
	@JsonProperty("taxed_day")
	private String taxedDay;
	
	private String message;

	public int getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(int taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getTaxedDay() {
		return taxedDay;
	}

	public void setTaxedDay(String taxedDay) {
		this.taxedDay = taxedDay;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
