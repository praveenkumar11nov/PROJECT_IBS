package com.bcits.bfm.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

@Component 
public class DateTimeCalender
{
	/**
	 * @param 	dateString	"2014-01-27T05:22:39.190Z" 	(Input format from JSP)
	 * @return 				2014-01-27 00:00:00			(Storing format to DB)
	 */
	public java.sql.Date getDate1(String dateString)
	{
		System.out.println(">>>>>>>>>>>>."+dateString);
		String[] splitDate = dateString.split("T");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		java.util.Date finalDate = null;
		
		try 
		{
		   finalDate = sdf.parse(splitDate[0] + " " + splitDate[1]);
		} 
		
		catch (Exception e) 
		{
		   e.printStackTrace();
		}
		
		return new java.sql.Date(finalDate.getTime());
		
	}
	
	/**
	 * @param 	dateString	"27/01/2014" 				(Input format from JSP)
	 * @return 				2014-01-27 00:00:00			(Storing format to DB)
	 */
	public java.sql.Date getDate2(String dateString)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
		String temp[] = dateString.split("/");
		
		StringBuffer newDob = new StringBuffer();
		for (int i = temp.length-1; i >= 0; i--)
		{
			newDob.append(temp[i]).append("-");
		}
		String temp1 = newDob.substring(0, newDob.length()-1);
		temp1 =  temp1 + " 00:00:00";
		Date newDate = null;
		try 
		{
			newDate = sdf.parse(temp1);
		} 
		
		catch (Exception e) 
		{
		   e.printStackTrace();
		}

		return new java.sql.Date(newDate.getTime());
		
	}
	
	/**
	 * @param 	dateString	2014/01/27				(Input format from DB)
	 * @return 				"27/01/2014"			(Display format to JSP)
	 */
	public String getDate3(Date date)
	{
		 SimpleDateFormat formatDateJava = new SimpleDateFormat("dd/MM/yyyy");
		 String date_to_string = formatDateJava.format(date);
		 return date_to_string;

	}
	
	/**
	 * @param 	dateString	2014-01-27				(Input format from DB)
	 * @return 				"2014-01-27"			(Display format to JSP)
	 */
	public String getDate5(Date date)
	{
		 SimpleDateFormat formatDateJava = new SimpleDateFormat("yyyy-MM-dd");
		 String date_to_string = formatDateJava.format(date);
		 return date_to_string;

	}
	
	/**
	 * @param 	dateString	"2014-03-02T18:30:00.000Z" 				(Input format from JSP)
	 * @return 				2014-01-27 00:00:00			(Storing format to DB)
	 */
	public static Timestamp getDate(String str) {	
		Timestamp ts = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");		
			Date date=sdf.parse(str);		
			DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");  
			formatter.setTimeZone(TimeZone.getTimeZone("GMT"));  
			formatter.format(date);  
			formatter.setTimeZone(TimeZone.getTimeZone("IST")); 
			formatter.format(date);				
			Date sqlDate=new Date(date.getTime());
			ts = new Timestamp(sqlDate.getTime());
		}catch(Exception e){
			e.getMessage();
		}
		return ts;
		}
		
	
	/**
	 * @param 	dateString	"2014-03-02T18:30:00.000Z" 				(Input format from JSP)
	 * @return 				2014-03-02 00:00:00			(Storing format to DB)
	 */
	public java.sql.Date getDate4(String str) {		
		TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
		Calendar cal = Calendar.getInstance(tz);		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		sdf.setCalendar(cal);		
		try {
			cal.setTime(sdf.parse(str));
		} catch (ParseException e) {e.printStackTrace();}		
		Date date = cal.getTime();		
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
		return sqlDate;
	}
	
	public Integer getAgeFromDob(Date dob)
	{
		String dobString = getDate5(dob);  

		 int yearDOB = Integer.parseInt(dobString.substring(0, 4));
		// int monthDOB = Integer.parseInt(dobString.substring(5, 7));
		// int dayDOB = Integer.parseInt(dobString.substring(8, 10));

		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		 Date date = new Date();
		 int thisYear = Integer.parseInt(dateFormat.format(date));

		 dateFormat = new SimpleDateFormat("MM");
		 date = new java.util.Date();
		// int thisMonth = Integer.parseInt(dateFormat.format(date));

		 dateFormat = new SimpleDateFormat("dd");
		 date = new java.util.Date();
		// int thisDay = Integer.parseInt(dateFormat.format(date));

		 int age = thisYear-yearDOB;
		 /*if(thisMonth < monthDOB){
		 age = age-1;
		 }
		 if(thisMonth == monthDOB && thisDay < dayDOB){
		 age = age-1;
		 }*/
		 return age;
	}
	
	public Timestamp getTimestampFromDateAndTime(Date poRecDate,
			String poRecTime) throws ParseException
	{
		Date finalDate = null;
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if((poRecTime.length() != 0) && (poRecTime.length() > 6))
			finalDate = dateTimeFormatter.parse(dateFormatter.format(poRecDate).concat(" ").concat(poRecTime.substring(poRecTime.indexOf(":")-2, poRecTime.lastIndexOf(":")+3)));
		else if(poRecTime.length() != 0)
			finalDate = dateTimeFormatter.parse(dateFormatter.format(poRecDate).concat(" ").concat(poRecTime.concat(":00")));
		else
			finalDate = dateTimeFormatter.parse(dateFormatter.format(poRecDate).concat(" ").concat(poRecTime.concat("00:00:00")));
		
		return new Timestamp(finalDate.getTime());
	}

	/*public Timestamp getTimestampFromDateAndTime(Date poRecDate,
			String poRecTime) throws ParseException
	{
		Date dummy = poRecDate;
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss");
		SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat formatter2 = new SimpleDateFormat("EEE MMM dd yyyy");
		Date dateFlag = new Date();
		
		System.out.println("exact date>>>>"+poRecDate.toString());
		
		if(poRecTime.length() > 6)
		{
			System.out.println("Tue Apr 22 2014 01:30:00      "+poRecTime.substring(0, poRecTime.lastIndexOf(":")+3));
			dateFlag = formatter.parse(poRecTime.substring(0, poRecTime.lastIndexOf(":")+3));
		}
		else
		{
			dateFlag = formatter1.parse(formatter2.format(dummy).concat(" ").concat(poRecTime.concat(":00")));
		}
		System.out.println("Old date>>>>"+poRecDate);
		poRecDate.setTime(dateFlag.getTime());
		System.out.println("new date>>>>"+poRecDate);
		return new Timestamp(poRecDate.getTime());
	}*/

	public java.sql.Date getDateFromString(String string)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		java.util.Date finalDate = null;
		
		try 
		{
		   finalDate = sdf.parse(string);
		} 
		
		catch (Exception e) 
		{
		   e.printStackTrace();
		}
		
		return new java.sql.Date(finalDate.getTime());
		
	}

	public String getTimeFromString(String string)
	{
		String str = string.substring(string.indexOf(":")-2, string.lastIndexOf(":"));
		return str;
	}
	
	
	/**
	 * @param 	dateString	"Sun Feb 25 1990 00:00:00 GMT+0530 (India Standard Time)" 	(Input format from JSP)
	 * @return 				2014-03-02 00:00:00			(Storing format to DB)
	 */
	public java.sql.Date getDateToStore(String dateString) throws ParseException
	{

		String dateStr ="";
		try{
		java.util.Date date = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(dateString);
		dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return getDateFromString(dateStr);
		
	}
	
	public java.sql.Date getdateFormat(String datestring) throws ParseException {
	    String datestr = "";
	    try {
	        java.util.Date date = new SimpleDateFormat("yyyy/MM/dd ",
	                Locale.ENGLISH).parse(datestring);
	        datestr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return getDateFromString(datestr);

	}
	
	public java.sql.Date storeStringDateFormat(String datestring) throws ParseException {
	    String datestr = "";
	    try {
	        java.util.Date date = new SimpleDateFormat("dd/MM/yyyy ",
	                Locale.ENGLISH).parse(datestring);
	        datestr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return getDateFromString(datestr);

	}
	
	
	public java.sql.Date kendoDateIssue(String datestring) throws ParseException{
	  
		TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
		Calendar cal = Calendar.getInstance(tz);		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		sdf.setCalendar(cal);		
		try {
			cal.setTime(sdf.parse(datestring));
			cal.add(Calendar.DATE, 1);
		} catch (ParseException e) {e.printStackTrace();}		
		Date date = cal.getTime();		
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
		
		return sqlDate;

	}

	
}
