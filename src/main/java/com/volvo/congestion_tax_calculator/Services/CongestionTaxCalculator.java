package com.volvo.congestion_tax_calculator.Services;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;
import com.volvo.congestion_tax_calculator.config.CongestionTaxConfig;
import com.volvo.congestion_tax_calculator.modal.TaxCalculatorResponse;

@Service
public class CongestionTaxCalculator {
 
	private final CongestionTaxConfig config;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public CongestionTaxCalculator(CongestionTaxConfig config) {
        this.config = config;
    }
    
    public TaxCalculatorResponse getTax(String vehicle, Date[] dates, String city) {
    	TaxCalculatorResponse response = new TaxCalculatorResponse();
    	
    	if (dates == null || dates.length == 0) {
    		response.setMessage("Please provide Date & Tim");
    		return response;
        }
    	
    	//validate city
    	if(!config.getCity().equals(city.toLowerCase())) {
    		response.setMessage("Please provide valid city");
    		return response;
    	}
    	
    	//validate dates are in single day
    	if(!areDatesInSameDay(dates)) {
    		response.setMessage("Please provide single day Date & Time");
    		return response;
    	}
    	
        Arrays.sort(dates); // Sort the dates in ascending order
        response.setTaxedDay(dateFormat.format(dates[0]));
        Date intervalStart = dates[0];
        int totalFee = 0;
        int highestFeeInInterval = 0;
     
        for (int i = 0; i < dates.length; i++) {
            Date date = dates[i];
            int nextFee = getTollFee(date, vehicle);
     
            long diffInMillies = date.getTime() - intervalStart.getTime();
            long minutes = diffInMillies / (1000 * 60);
     
            if (minutes <= 60) {
                if (nextFee > highestFeeInInterval) {
                    highestFeeInInterval = nextFee;
                }
            } else {
                totalFee += highestFeeInInterval;
                highestFeeInInterval = nextFee;
                intervalStart = date;
            }
        }
     
        totalFee += highestFeeInInterval;
     
        if (totalFee > 60) {
            totalFee = 60;
        }
     
        response.setTaxAmount(totalFee);
		return response;
    }

    public int getTollFee(Date date, String vehicle)
    {
    	
        if (isExemptVehicle(vehicle) || isWeekend(date) || isJuly(date) 
        		|| isPublicHoliday(date) || isDayBeforePublicHoliday(date) || isTaxYear(date)) return 0;

        int hour = date.getHours();
        int minute = date.getMinutes();

        // Vinoth - hour and minute corrected
        if (hour == 6 && minute >= 0 && minute <= 29) {
            return 8;
        } else if (hour == 6 && minute >= 30 && minute <= 59) {
            return 13;
        } else if (hour == 7 && minute >= 0 && minute <= 59) {
            return 18;
        } else if (hour == 8 && minute >= 0 && minute <= 29) {
            return 13;
        } else if (hour >= 8 && hour <= 14 && minute >= 30 && minute <= 59) {
            return 8;
        } else if (hour == 15 && minute >= 0 && minute <= 29) {
            return 13;
        } else if (hour >= 15 && hour <= 16 && minute >= 30 && minute <= 59) {
            return 18;
        } else if (hour == 17 && minute >= 0 && minute <= 59) {
            return 13;
        } else if (hour == 18 && minute >= 0 && minute <= 29) {
            return 8;
        } else {
            return 0;
        }
    }
    
    public static boolean areDatesInSameDay(Date[] dates) {
        if (dates == null || dates.length == 0) {
            return false;
        }
 
        // Get Calendar instance to extract year, month, and day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dates[0]);
        int referenceYear = calendar.get(Calendar.YEAR);
        int referenceMonth = calendar.get(Calendar.MONTH);
        int referenceDay = calendar.get(Calendar.DAY_OF_MONTH);
 
        // Check if all other dates have the same year, month, and day
        for (int i = 1; i < dates.length; i++) {
            calendar.setTime(dates[i]);
            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonth = calendar.get(Calendar.MONTH);
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
 
            if (currentYear != referenceYear || currentMonth != referenceMonth || currentDay != referenceDay) {
                return false; // Dates are not in the same day
            }
        }
 
        return true; // All dates are in the same day
    }
    
    private boolean isExemptVehicle(String vehicleType) {
        // List of exempt vehicle types
        String[] exemptVehicleTypes = config.getExemptVehicleTypes();
 
        // Convert vehicle type to lowercase for case-insensitive comparison
        String lowerCaseVehicleType = vehicleType.toLowerCase();
 
        return Arrays.asList(exemptVehicleTypes).contains(lowerCaseVehicleType);
    }

    private boolean isWeekend(Date date) {
        int day = date.getDay(); // 0 represents Sunday, and 6 represents Saturday.
        if (day == 0 || day == 6)  return true;
        return false;
    }
 
    private boolean isPublicHoliday(Date date) {
        // List of public holidays
        String[] publicHolidays = config.getPublicHolidays();
        String dateString = dateFormat.format(date);
     
        return Arrays.asList(publicHolidays).contains(dateString);
    }
     
    private boolean isDayBeforePublicHoliday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1); // Subtract one day to check the day before the given date.
     
        return isPublicHoliday(calendar.getTime());
    }
 
    private boolean isJuly(Date date) {
    	int month = date.getMonth() + 1;
    	if (month == 7) return true;
        return false;
    }
    
    private boolean isTaxYear(Date date) {
    	int year = date.getYear() + 1900;
    	if(year != config.getYear()) return true;
    	return false;
    }
   
}
