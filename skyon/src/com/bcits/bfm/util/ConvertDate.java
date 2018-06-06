package com.bcits.bfm.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Manjunath Kotagi
 *
 */
public class ConvertDate {

	/**
	 * Developer Reference
	 * 
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		System.out.println();
	}

	/**
	 * Converting Date To GMT Format
	 * @param dateString : Date as String
	 * @return
	 */
	public static Timestamp formattedDate(String dateString) {
		System.out.println(dateString);
		TimeZone tz = TimeZone.getTimeZone("GMT");
		Calendar cal = Calendar.getInstance(tz);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		sdf.setCalendar(cal);
		Timestamp ts = null;
		try {
			cal.setTime(sdf.parse(dateString));
			Date date = cal.getTime();

			String convertString = date.toString();

			String dateStr = "";

			java.util.Date date2 = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH)
					.parse(convertString);
			dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date2);
			ts = Timestamp.valueOf(dateStr);

		} catch (Exception e) {
			e.getMessage();
		}
		return ts;
	}

	public static Timestamp formattedDate1(String dateString) {
		System.out.println(dateString);
		TimeZone tz = TimeZone.getTimeZone("GMT");
		Calendar cal = Calendar.getInstance(tz);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		sdf.setCalendar(cal);
		Timestamp ts = null;
		try {
			cal.setTime(sdf.parse(dateString));
			Date date = cal.getTime();

			String convertString = date.toString();

			String dateStr = "";

			java.util.Date date2 = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH)
					.parse(convertString);
			dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date2);
			ts = Timestamp.valueOf(dateStr);

		} catch (Exception e) {
			e.getMessage();
		}
		return ts;
	}
	
	/**
	 * Timestamp to Date String
	 * @param myTimestamp : timestamp as DateString
	 * @return
	 */
	public static String TimeStampString(Timestamp myTimestamp) {
		String S = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(myTimestamp);
		return S;
	}

	/**
	 * Convert String to Timestamp
	 * 
	 * @param str : Date String
	 * @return Timestamp
	 */
	public static Timestamp getDate(String str) {
		Timestamp ts = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			Date date = sdf.parse(str);
			DateFormat formatter = new SimpleDateFormat(
					"dd MMM yyyy HH:mm:ss z");
			formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
			formatter.format(date);
			formatter.setTimeZone(TimeZone.getTimeZone("IST"));
			formatter.format(date);
			Date sqlDate = new Date(date.getTime());
			ts = new Timestamp(sqlDate.getTime());
		} catch (Exception e) {
			e.getMessage();
		}
		return ts;
	}
}
