package com.bcits.bfm.controller;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;


public class PraveenKumar {
	//Reference   https://gist.github.com/madan712/3912272
	public static void main(String[] args) throws IOException, ParseException {
		
		DecimalFormat df = new DecimalFormat("#.##");
		System.err.println(df.format(0.1058630136986301));
		System.err.println(df.format(0.11 * 1535 * 57.0));
		
		/*Calendar cal1=Calendar.getInstance();
		Calendar cal2=Calendar.getInstance();
		
		cal1.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("2017-12-01"));
		cal2.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("2018-01-01"));
		
		int	yearDiff  = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR); 
		int monthDiff = yearDiff * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
		
		System.err.println("month difference=========>"+monthDiff);
		*/
		
		/*
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date lastBillDate = sdf.parse("2017-12-01");
			Date currentBillDate = sdf.parse("2018-03-01");
 			System.out.println("BILLDATE = "+currentBillDate+"   FROMDATE = "+lastBillDate);
			
			Calendar cal2=Calendar.getInstance();
			cal2.setTime(lastBillDate);
			cal2.set(Calendar.DAY_OF_MONTH, cal2.getActualMaximum(Calendar.DAY_OF_MONTH));
 			cal2.add(Calendar.DATE, 1);
			
 			Calendar cal = Calendar.getInstance();
 			cal.setTime(currentBillDate);
 			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
 			cal.add(Calendar.DATE, 1);
			
 			System.out.println(cal.getTime()+"  "+cal2.getTime());
 			System.out.println("cal.get(Calendar.MONTH)="+(cal.get(Calendar.MONTH)+1)+"     cal2.get(Calendar.MONTH)="+(cal2.get(Calendar.MONTH)+1));
			int months = (cal.get(Calendar.MONTH)+1) - (cal2.get(Calendar.MONTH)+1);
			System.out.println("MonthsDifference="+months);
		*/
			    
		//readXLSXFile();
	/*	Scanner scn=new Scanner(System.in);
		System.out.println("Enter DOB in dd/MM/yyyy");
		String DOB=scn.next();
		knowYourAge(DOB);*/
	}
	
	public static void knowYourAge(String DOB) throws IOException, ParseException{
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(DOB));
		int day   = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH)+1;
		int year  = cal.get(Calendar.YEAR);
	   
	   LocalDate birthdate = new LocalDate (year, month, day);
	   LocalDate now = new LocalDate();
	   Period period = new Period(birthdate, now, PeriodType.yearMonthDay());
	   System.out.println(period.getYears()+" years "+period.getMonths()+" months " +period.getDays()+" days");
	}
	
	
	public static void readXLSXFile() throws IOException{
		InputStream ExcelFileToRead = new FileInputStream("C:/Users/User/Desktop/contactList2.xlsx");
		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
		XSSFWorkbook test = new XSSFWorkbook(); 
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell;
		Iterator rows = sheet.rowIterator();
		int count=1;
		while (rows.hasNext()){
			row=(XSSFRow) rows.next();
			System.out.println(count++ +". "+row.getCell(0).getStringCellValue());
			/*
			Iterator cells = row.cellIterator();
			while (cells.hasNext()){
				cell=(XSSFCell) cells.next();
				//System.out.print(cell.getStringCellValue()+"..........");
				if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
					System.out.print(cell.getStringCellValue()+"  ");
				}else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
					System.out.print(cell.getNumericCellValue()+"  ");
				}else{
					//System.err.print("...");
				}
			}*/
			//System.err.println(".......RowENDS");
		}
	}
}