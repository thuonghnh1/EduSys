package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XDate {
	static SimpleDateFormat formater = new SimpleDateFormat();

	public static Date toDate(String date, String...pattern) {
//		try {
//			formater.applyPattern(pattern);
//			return formater.parse(date);
//		} catch (ParseException e) {
//			throw new RuntimeException(e);
//		}
		 try {
	            if(pattern.length > 0){
	            	formater.applyPattern(pattern[0]);
	            }
	            if(date == null){
	                return XDate.now();
	            }
	            return formater.parse(date);
	        } 
	        catch (ParseException ex) {
	            throw new RuntimeException(ex);
	        }
	}

	public static String toString(Date date, String...pattern) {
//		formater.applyPattern(pattern);
//		return formater.format(date);
		if(pattern.length > 0){
			formater.applyPattern(pattern[0]);
        }
        if(date == null){
            date = XDate.now();
        }
        return formater.format(date);
	}
	
	public static Date now() {
        return new Date();
    }

	public static Date addDay(Date date, Long days) {
		date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
		return date;
	}
	
	public static Date add(int days) {
        Date now = XDate.now();
        now.setTime(now.getTime() + days*24*60*60*1000);
        return now;
    }
}
