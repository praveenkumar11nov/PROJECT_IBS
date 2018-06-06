package com.bcits.bfm.util;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.tempuri.IREOService;
import org.xml.sax.InputSource;

import com.bcits.bfm.model.DailyData;
import com.bcits.bfm.model.DocumentElementData;

public class TestWebService {

	public static void main(String[] args) throws JAXBException, ParseException {

JAXBContext jaxbContext;

try {
	IREOService s1=new IREOService();
	Calendar c1=Calendar.getInstance();
	c1.add(Calendar.DATE, -1);
	Date c=c1.getTime();
	String s=new SimpleDateFormat("dd/MM/yyyy").format(c);
	System.out.println("Previous date"+s);
	long d1=81;
String data	=s1.getBasicHttpBindingIService1().dailyData("104", "01/03/2017", "harjeet", "ireo@123", d1, "6000389", "01/03/2016", "24/03/2017");
System.out.println(data);
	jaxbContext = JAXBContext.newInstance(DocumentElementData.class);
	Unmarshaller jaxUnmarshaller = jaxbContext.createUnmarshaller();
	InputSource inputSource = new InputSource(new ByteArrayInputStream(data.getBytes()));
	 inputSource.setEncoding("UTF-8");
	DocumentElementData tallyResponse = (DocumentElementData) jaxUnmarshaller.unmarshal(inputSource); 
	 System.out.println(tallyResponse);
	 List<DailyData> list=tallyResponse.getInstantDatas();
	 System.out.println(list);
	 for (DailyData dailyData : list) {
		System.out.println(dailyData);
	}
} catch (JAXBException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}  

     Date fromdate=new Date();
		Date todate=fromdate;
		 Calendar cal=Calendar.getInstance();
		 cal.setTime(fromdate);
		 System.out.println(cal.getFirstDayOfWeek());
		int month=cal.get(Calendar.MONTH);
		 int fromMonth=month+1;
		 int fromYear=cal.get(Calendar.YEAR);
		 cal.setTime(todate);
		 System.out.println(cal.getActualMaximum(cal.DAY_OF_MONTH));
		 int month1=cal.get(Calendar.MONTH);
		 int toMonth=month1+1;
		 String fr=cal.getMinimalDaysInFirstWeek()+"/"+fromMonth+"/"+fromYear;
		 String to=cal.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+fromMonth+"/"+fromYear;
		// System.out.println(fr);
		// System.out.println(to);
		 int d=12;
		 String s1="1-Jan-2017";
		// String s=new SimpleDateFormat("dd-MMM-yyyy").format(s1);
		 DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		// formatter.parse(s1);
		 System.out.println((Date)formatter.parse(s1));
		// String id=String.valueOf(12+"");
		// System.out.println(id );
	}
		 /*double x = 125.45;
		   double y = 0.4873;

		   // call ceal for these these numbers
		   System.out.println("Math.ceil(" + x + ")=" + Math.ceil(x));
		   System.out.println("Math.ceil(" + y + ")=" + Math.ceil(y));
		   System.out.println("Math.ceil(-45.65)=" + Math.ceil(-45.65));
		   System.out.println(Math.floor(x));
		      System.out.println(Math.floor(y)); 
		      

		  	

           float x1 = ((float)234/100);  // You should type cast. Otherwise results 23
           System.out.println(Math.ceil(x1));
           System.out.println(Math.floor(x1));

	

           float total = ((float)157/32);
             System.out.println(Math.floor(total));
             double t1=Math.ceil(157/32);
             System.out.println(t1);
             
             System.out.println(" Math round  " +Math.round(-10.4667f));
             float bigDecima = (float) Math.round(123.567);
             System.out.println(bigDecima);
             
             float previousraeding=0f;

				String previou = "1234.56";
						
				previousraeding =Math.round(Float
						.parseFloat(previou));

				System.out.println("previousraeding:::::::::::"
						+ previou
						+ ":::::::::::"
						+ previousraeding);
*/
	
}
	
	
	
