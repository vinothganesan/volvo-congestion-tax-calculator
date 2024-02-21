package com.volvo.congestion_tax_calculator.modal;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TaxCalculatorRequest {
	
	private String city;
		
	private String vehicleType;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Amsterdam")
	private Date[] dates;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@JsonProperty("vehicle_type")
	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Date[] getDates() {
		return dates;
	}

	public void setDates(Date[] dates) {
		this.dates = dates;
	}
	
}
