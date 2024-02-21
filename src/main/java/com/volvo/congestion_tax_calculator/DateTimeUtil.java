package com.volvo.congestion_tax_calculator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
	
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat dateAndTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static boolean isDateInBetweenIncludingEndPoints(final Date min, final Date max, final Date date){
        return !(date.before(min) || date.after(max));
    }

    public static String removeTime(Date date) {
        return dateFormat.format(date);
    }

    public static Date objectToDate(Object date) throws ParseException {
        if(date instanceof String)
            return dateAndTimeFormat.parse(date.toString());
        else
            return dateAndTimeFormat.parse(dateAndTimeFormat.format(((Date)date)));
    }
    
}
