package com.ampos.restaurant.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	/**
	 * Convert String to Date
	 * @param date
	 * @param timeZone
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String date,String timeZone) throws ParseException {
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(timeZone));
		return DATE_FORMAT.parse(date);
	}

	/**
	 * Convert Date to String
	 * @param date
	 * @param timezone
	 * @return
	 */
	public static String convertDateToString(Date date, String timezone) {
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(timezone));
		return DATE_FORMAT.format(date);
	}
}
