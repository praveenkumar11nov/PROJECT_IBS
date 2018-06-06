package com.bcits.bfm.model;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.tempuri.IREOService;
import org.xml.sax.InputSource;
public class ConvertXmlToObject {
	public static void main(String[] args) {

		
		long d1=81;
		IREOService s1=new IREOService();
		Calendar c1=Calendar.getInstance();
		c1.add(Calendar.DATE, -1);
		Date c=c1.getTime();
		System.out.println("Previous date"+c);
		String s=new SimpleDateFormat("dd/MM/yyyy").format(c);
		Date d=new Date();
		String date=new SimpleDateFormat("dd/MM/yyyy").format(d);
		System.out.println("current Date"+date);
		String data=s1.getBasicHttpBindingIService1().instantData("UATG1234", "03/11/2016", "skyon_genus",  "admin123$", d1, "6000105", "10/11/2016",date);
		      
		//System.out.println(data);
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(DocumentElementData.class);
			  Unmarshaller jaxUnmarshaller = jaxbContext.createUnmarshaller();
		        InputSource inputSource = new InputSource(new ByteArrayInputStream(data.getBytes()));
		        inputSource.setEncoding("UTF-8");
		        DocumentElementData tallyResponse = (DocumentElementData) jaxUnmarshaller.unmarshal(inputSource); 
		       // System.out.println(tallyResponse);
		        List<DailyData> list=tallyResponse.getInstantDatas();
		      System.out.println(list);
		        for(Iterator<?> iterator=tallyResponse.getInstantDatas().iterator();iterator.hasNext();){
		        System.out.println(iterator.next());
		        
		        }
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
		}
}
