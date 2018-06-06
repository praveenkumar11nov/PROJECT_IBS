package com.bcits.bfm.util;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.model.Address;
import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.City;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Country;
import com.bcits.bfm.model.DrGroupId;
import com.bcits.bfm.model.Owner;
import com.bcits.bfm.model.OwnerProperty;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.model.State;
import com.bcits.bfm.model.Tenant;
import com.bcits.bfm.model.TenantProperty;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.ReportOutputFormat;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.mysql.jdbc.Driver;

public class Test {

	
@Autowired 	
TariffCalculationService tariffCalculationService;	
/**
 * @param args
 * @throws ParseException
 * @throws JRException
 */
public static void main(String[] args) throws ParseException, JRException {

	// Generate Jasper Report
	/*Connection con=null;
	String databaseURL ="jdbc:oracle:thin:@1.23.144.187:1521:JVVNLNEW";
	String UserName="BSMARTJVVNL";
	String Password="jvvnlstaging";
	
	Scanner sc=new Scanner(System.in);
	System.out.println("Enter a number:");
	int i = sc.nextInt();
	
	if(i==1){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(databaseURL,UserName,Password);
			String reportPath="C:\\Users\\User\\Desktop\\Jvvnl Jasper\\OUT4ABS.jrxml";
			JasperReport jr = JasperCompileManager.compileReport(reportPath);
			JasperPrint jp = JasperFillManager.fillReport(jr,null,con);
			JasperViewer.viewReport(jp);
			
			byte[] bytes = JasperExportManager.exportReportToPdf(jp);
			InputStream myInputStream = new ByteArrayInputStream(bytes);
			Blob blob = Hibernate.createBlob(myInputStream);
			InputStream is = blob.getBinaryStream();
			FileOutputStream fos = new FileOutputStream("C:\\Users\\User\\Desktop\\Jvvnl Jasper\\OUT4ABS.pdf");
			int b = 0;
			while ((b = is.read()) != -1)
			{
				fos.write(b); 
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	if(i==2){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(databaseURL,UserName,Password);
			HashMap<String, Object> param=new HashMap<String, Object>();
			SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yy");
			java.sql.Date sqldate= new java.sql.Date(sdf.parse("03/01/18").getTime());
			param.put("Reading_Month",sqldate);
			param.put("Binder",6);
			param.put("currentdate","3rd Feb 2018");
			param.put("feeder_name","11KV MCM/ BHAGAWAN DEVI FEEDER");
			
			String reportPath="C:\\Users\\User\\Desktop\\Jvvnl Jasper\\MeterChanges.jrxml";
			JasperReport jr = JasperCompileManager.compileReport(reportPath);
			JasperPrint jp = JasperFillManager.fillReport(jr,param,con);
			JasperViewer.viewReport(jp);
			
			byte[] bytes = JasperExportManager.exportReportToPdf(jp);
			InputStream myInputStream = new ByteArrayInputStream(bytes);
			Blob blob = Hibernate.createBlob(myInputStream);
			InputStream is = blob.getBinaryStream();
			FileOutputStream fos = new FileOutputStream("C:\\Users\\User\\Desktop\\Jvvnl Jasper\\MeterChanges.pdf");
			int b = 0;
			while ((b = is.read()) != -1)
			{
				fos.write(b); 
			}
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	
	
	
	
	
	
	
	/*
	
	        checkvalid date format             
	String value = "26/12/2018";
	Date date = null;
	try {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    date = sdf.parse(value);
	    if (!value.equals(sdf.format(date))) {
	        date = null;
	    }
	} catch (Exception e) { 
	    e.printStackTrace();
	}
	if (date == null) {
	    System.out.println("false"+" ///// date="+date);
	} else {
	    System.out.println("true"+" ///// date="+date);
	}
	
	
Scanner sc=new Scanner(System.in);
System.out.println("Enter a Date:");
String givendate = sc.next();
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
Date serviceFrom = new SimpleDateFormat("dd/MM/yyyy").parse("01/06/2017");
Date serviceTo   = new SimpleDateFormat("dd/MM/yyyy").parse("19/06/2017");
Date dailyDate   = new SimpleDateFormat("dd/MM/yyyy").parse(givendate);
System.out.println("fromdate="+sdf.format(serviceFrom)+"  todate="+sdf.format(serviceTo)+"  givendate="+sdf.format(dailyDate)); 
int fro 	 = Integer.parseInt(sdf.format(serviceFrom).charAt(0) + "" +  sdf.format(serviceFrom).charAt(1));
int to  	 = Integer.parseInt(sdf.format(serviceTo).charAt(0)   + "" +  sdf.format(serviceTo).charAt(1));
int gd  	 = Integer.parseInt(sdf.format(dailyDate).charAt(0)   + "" +  sdf.format(dailyDate).charAt(1));
int froMonth = Integer.parseInt(sdf.format(serviceFrom).charAt(3) + "" +  sdf.format(serviceFrom).charAt(4));
int toMonth  = Integer.parseInt(sdf.format(serviceTo).charAt(3)   + "" +  sdf.format(serviceTo).charAt(4));
int gdMonth  = Integer.parseInt(sdf.format(dailyDate).charAt(3)   + "" +  sdf.format(dailyDate).charAt(4));
//System.err.println(fro+"/"+froMonth+"  "+to+"/"+toMonth+"  "+gd+"/"+gdMonth);
if((gdMonth>froMonth && gdMonth<toMonth)||gdMonth==froMonth||gdMonth==toMonth){
	if((gd>fro && gd<to)||gd==fro||gd==to){
		System.err.println("given date applicable for calculation");
	}else{System.out.println("date out of range");}
}else{System.out.println("month out of range");}


	Test t=new Test();
	t.getBlocks();
	
*/
	}



/*public void getBlocks(){
	List<Integer> list = tariffCalculationService.getallBlocks();
	//for (Blocks blocks : list) {
	for (int i = 0; i <= list.size(); i++) {
		
	
		int blockI = i;
		System.out.println("blockI"+blockI);
	}
}*/

}