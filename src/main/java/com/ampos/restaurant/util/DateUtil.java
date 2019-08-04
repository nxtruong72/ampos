package com.ampos.restaurant.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	/**
	 * convert String to date
	 */
	public static Date convertStringToDate(String date,String timeZone) throws ParseException {
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(timeZone));
		return DATE_FORMAT.parse(date);
	}

}
