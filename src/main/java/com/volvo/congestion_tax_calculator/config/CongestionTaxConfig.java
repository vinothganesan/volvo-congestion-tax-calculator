package com.volvo.congestion_tax_calculator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
 
@Configuration
public class CongestionTaxConfig {
 
	@Value("${congestion.tax.publicHolidays}")
    private String publicHolidaysString;
 
    @Value("${congestion.tax.exemptVehicleTypes}")
    private String exemptVehicleTypesString;
 
    @Value("${congestion.tax.city}")
    private String city;
    
    @Value("${congestion.tax.year}")
    private Integer year;
 
    // Getter methods for the properties
    public String[] getPublicHolidays() {
        return publicHolidaysString.split(",");
    }
 
    public String[] getExemptVehicleTypes() {
        return exemptVehicleTypesString.split(",");
    }
 
    public String getCity() {
        return city;
    }
    
    public Integer getYear() {
        return year;
    }
}
