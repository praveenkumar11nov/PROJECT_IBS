package com.bcits.bfm.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.PrepaidBillDocument;
import com.bcits.bfm.model.PrepaidRechargeEntity;
import com.bcits.bfm.util.NumberToWord;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
public class AccountStatement 
{
	@PersistenceContext(unitName="bfm")
	protected EntityManager entityManager;
	
	@RequestMapping(value="/accountStmtPage")
	public String accountMethod()
	{
		return "accountStatement";
	}
	
	@RequestMapping(value="/accountStatementDetail")
	public @ResponseBody List<Map<String,Object>> getAccountStatement() 
	{
		List<?> list1 =entityManager.createNativeQuery("SELECT DISTINCT(p.METER_NUMBER) FROM PREPAID_METERS p").getResultList();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> wizardMap = null;
		int noOfMeters=0;
		
		for(int i=0;i<list1.size();i++)
		{
			//System.out.println("For meter Number="+list1.get(i)+",Account statement is----->");
			String query2=	
					"SELECT b.READING_DATE_TIME,B.PROPERTY_NO,b.METER_SR_NO as meter_number,b.EB_UNITS,b.DG_UNITS,c.CONSUMPTION,"+
					" b.BALANCE,b.TOTAL_RECHARGE_AMOUNT,b.CUM_FIXED_CHARGE_DG  FROM " +
					"(	SELECT PD.READING_DATE_TIME,PD.CUM_KWH_MAIN as EB_UNITS,PD.CUM_KWH_DG " +
					"	as DG_UNITS,PD.CUSTOMER_RECHRGE_AMOUNT " +
					"	as TOTAL_RECHARGE_AMOUNT,PD.METER_SR_NO " +
					"	,PD.BALANCE,p.PROPERTY_NO,PD.CUM_FIXED_CHARGE_DG " +
					"	FROM PREPAID_DAILY_DATA pd,PROPERTY p,PREPAID_METERS pm " +
					"	WHERE PD.METER_SR_NO=PM.METER_NUMBER and PM.PROPERTY_ID=p.PROPERTY_ID AND PD.METER_SR_NO="+list1.get(i)+
					"	AND to_date(READING_DATE_TIME,'dd/MM/yyyy')IN( " +
					"				SELECT MAX(TO_DATE(PDD.READING_DATE_TIME,'dd/MM/yyyy'))AS maxdate FROM PREPAID_DAILY_DATA pdd " +
					"				WHERE pdd.METER_SR_NO="+list1.get(i)+" AND "+
				    " 					TO_DATE(PDD.READING_DATE_TIME,'dd/MM/yyyy')<=(TO_DATE('31/03/2017','dd/MM/yyyy')) "+
				    "				GROUP BY PDD.METER_SR_NO " +
					"			) " +
					")B, " +
					"(	SELECT sum(DAILY_CONSUMPTION_AMOUNT) as CONSUMPTION ,METER_SR_NO " +
					"	FROM PREPAID_DAILY_DATA WHERE METER_SR_NO="+list1.get(i)+
					"	AND	to_date(READING_DATE_TIME,'dd/MM/yyyy')<=( " +
					"				SELECT MAX(TO_DATE(PDD.READING_DATE_TIME,'dd/MM/yyyy'))AS maxdate FROM PREPAID_DAILY_DATA pdd " +
					"				WHERE pdd.METER_SR_NO="+list1.get(i)+" AND "+
				    " 					TO_DATE(PDD.READING_DATE_TIME,'dd/MM/yyyy')<=(TO_DATE('31/03/2017','dd/MM/yyyy')) "+
				    "				GROUP BY PDD.METER_SR_NO " +
					"			) " +
					"	GROUP BY METER_SR_NO " +
					")C  WHERE B.METER_SR_NO=C.METER_SR_NO(+)";
			
			List<?> list2 =entityManager.createNativeQuery(query2).getResultList();
			for (Iterator<?> iterator = list2.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				wizardMap = new HashMap<String, Object>();
				wizardMap.put("readingDateTime",	 values[0]);	
				wizardMap.put("propertyNo",			 values[1]);
				wizardMap.put("meterNo",			 values[2]);	
				wizardMap.put("ebUnits", 			 values[3]);
				wizardMap.put("dgUnits", 			 values[4]);
				wizardMap.put("consumption",		 values[5]);
				wizardMap.put("balance",			 values[6]);	
				wizardMap.put("totalRechargeAmount", values[7]);
				wizardMap.put("cumFixedChargeDG", 	 values[8]);
				result.add(wizardMap);
			}
			noOfMeters++;
		}
		System.out.println("Total Number of Meters="+noOfMeters);
		return result;
	}
	
	@RequestMapping(value = "/accountStatement/getPdfReport", method = {RequestMethod.POST,RequestMethod.GET})
	public  void getPdfReport(HttpServletRequest request,HttpServletResponse response) throws ParseException, DocumentException, MalformedURLException, IOException, SQLException
	{
		System.out.println("*****************inside getPdfReport*****************");
		String a1 = request.getParameter("prePaidId");
		String a2 = request.getParameter("meterNo");
		String a3 = request.getParameter("propertyNo");
		String a4 = request.getParameter("readingDateTime");
		String a5 = request.getParameter("consumption");
		String a6 = request.getParameter("totalRechargeAmount");
		String a7 = request.getParameter("balance");
		String a8 = request.getParameter("ebUnits");
		String a9 = request.getParameter("dgUnits");
		String a10 = request.getParameter("cumFixedChargeDG");
		
		System.out.println("***************** "+a1+" "+a2+" "+a3+" "+a4+" "+a5+" "+a6+" "+a7+" "+a8+" "+a9+" "+a10+" *****************");
		/*
		String address =
		"SELECT (p.FIRST_NAME||' '||p.LAST_NAME) AS NAME,c.CONTACT_CONTENT FROM PERSON p,CONTACT c,PREPAID_METERS pm " +
		"WHERE p.PERSON_ID=c.PERSON_ID AND PM.PERSON_ID=c.PERSON_ID AND c.CONTACT_TYPE='Mobile' AND pm.METER_NUMBER="+a2;
		Object[] nameAndAddress = (Object[]) entityManager.createNativeQuery(address).getSingleResult();

		//************************************Generating Normal PDF for Account Statement***********************************************
		String fileName = ResourceBundle.getBundle("application").getString("skyon.accountStatement")+"AccountStatement("+a3+")"+".pdf";
		System.out.println("fileName="+fileName);
		Document document = new Document(PageSize.A4, 20, 20, 50, 50);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer=PdfWriter.getInstance(document, baos);
		Rectangle rect = new Rectangle(30, 30, 300, 810);
		writer.setBoxSize("art", rect);

		com.bcits.bfm.util.HeaderFooterForAccStatement event = new com.bcits.bfm.util.HeaderFooterForAccStatement();
		writer.setPageEvent(event);
		document.open(); 
		Paragraph p = new Paragraph(" ");
		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);
		Image image = Image.getInstance("C:/Users/user/Desktop/Not To Delete/skyon.png");
		image.scaleAbsolute(80,80);
		image.setAbsolutePosition(10f, 765f);
		image.setAlignment(image.LEFT);
		document.add(image);
		
		Paragraph paragraph 	  = new Paragraph("Consumer Name    : "+nameAndAddress[0],new Font(Font.FontFamily.HELVETICA, 8));
		Paragraph paragraph1	  = new Paragraph("Property Number  : "+a3,new Font(Font.FontFamily.HELVETICA, 8));
		Paragraph paragraph2	  = new Paragraph("Meter Number     : "+a2,new Font(Font.FontFamily.HELVETICA, 8));
		Paragraph paragraph3 	  = new Paragraph("Mobile Number    : "+nameAndAddress[1],new Font(Font.FontFamily.HELVETICA, 8));
		
		document.add(Chunk.SPACETABBING);
		document.add(Chunk.SPACETABBING);
		document.add(paragraph);
		document.add(paragraph1);
		document.add(paragraph2);
		document.add(paragraph3);
		document.add(Chunk.SPACETABBING);

		PdfPTable table = new PdfPTable(9);
		Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
		Font font3 = new Font(Font.FontFamily.HELVETICA, 8);

		table.addCell(new Paragraph("Meter Number",font1));
		table.addCell(new Paragraph("Property Number",font1));
		table.addCell(new Paragraph("Reading Date",font1));
		table.addCell(new Paragraph("Consumption",font1));
		table.addCell(new Paragraph("Total Recharge Amount",font1));
		table.addCell(new Paragraph("Balance",font1));
		table.addCell(new Paragraph("EB Units",font1));
		table.addCell(new Paragraph("DG Units",font1));
		table.addCell(new Paragraph("Cam Charges",font1));

		PdfPCell cell1 = new PdfPCell(new Paragraph((String)a2,font3));
		PdfPCell cell2 = new PdfPCell(new Paragraph((String)a3,font3));
		PdfPCell cell3 = new PdfPCell(new Paragraph((String)a4,font3));
		PdfPCell cell4 = new PdfPCell(new Paragraph((String)a5,font3));
		PdfPCell cell5 = new PdfPCell(new Paragraph((String)a6,font3));
		PdfPCell cell6 = new PdfPCell(new Paragraph((String)a7,font3));
		PdfPCell cell7 = new PdfPCell(new Paragraph((String)a8,font3));
		PdfPCell cell8 = new PdfPCell(new Paragraph((String)a9,font3));
		PdfPCell cell9 = new PdfPCell(new Paragraph((String)a10,font3));
		
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		table.addCell(cell5);
		table.addCell(cell6);
		table.addCell(cell7);
		table.addCell(cell8);
		table.addCell(cell9);

		table.setWidthPercentage(100);
		float[] columnWidths = {14f, 14f, 14f, 19f, 12f, 15f, 15f,18f,18f};

		table.setWidths(columnWidths);

		document.add(table);
		document.close();

		FileOutputStream fileOut = new FileOutputStream(fileName);    	
		baos.writeTo(fileOut);
		fileOut.flush();
		fileOut.close();

		ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline;filename=\"AccountStatement("+a3+").pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);		
		servletOutputStream.flush();
		servletOutputStream.close();*/

		//*****************************************************Nomal PDF Ends************************************************************
		
		System.out.println("********************************Account Statement for Skyon Jasper Report*********************************");
		String address = "	SELECT (p.FIRST_NAME||' '||p.LAST_NAME) AS NAME,c.CONTACT_CONTENT,a.ADDRESS1,a.ADDRESS2"
				+ "	FROM PERSON p,CONTACT c,PREPAID_METERS pm ,ADDRESS a "
				+ " WHERE p.PERSON_ID=c.PERSON_ID AND PM.PERSON_ID=c.PERSON_ID AND a.PERSON_ID=p.PERSON_ID "
				+ " AND c.CONTACT_TYPE='Mobile' AND pm.METER_NUMBER="+a2;
		String previousBalance=
				"SELECT BALANCE FROM PREPAID_DAILY_DATA WHERE METER_SR_NO="+a2+" AND TO_DATE(READING_DATE_TIME,'dd/MM/yyyy')"
			  + "=(SELECT MIN(TO_DATE(READING_DATE_TIME,'dd/MM/yyyy')) FROM PREPAID_DAILY_DATA WHERE METER_SR_NO="+a2+")";
		
		Object[] nameAndAddress1 = (Object[]) entityManager.createNativeQuery(address).getSingleResult();
		String preBal = (String) entityManager.createNativeQuery(previousBalance).getSingleResult();
		
		String fileName1 = ResourceBundle.getBundle("application").getString("skyon.accountStatement")+"AccountStatement("+a3+")"+".pdf";
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put( "title",ResourceBundle.getBundle("utils").getString("report.title")); 
		param.put("companyAddress",ResourceBundle.getBundle("utils").getString("report.address"));
   	    param.put("point1",ResourceBundle.getBundle("utils").getString("report.point1")); 
   	    param.put("point2",ResourceBundle.getBundle("utils").getString("report.point2"));
		param.put("point3.1", ResourceBundle.getBundle("utils").getString("report.point3.1"));
		param.put("point3.2",ResourceBundle.getBundle("utils").getString("report.point3.2"));
		param.put("point3.3", ResourceBundle.getBundle("utils").getString("report.point3.3"));
		param.put("point4",ResourceBundle.getBundle("utils").getString("report.point4"));
		param.put("point5",ResourceBundle.getBundle("utils").getString("report.point5"));
		param.put("note",ResourceBundle.getBundle("utils").getString("report.note")); 
		param.put("realPath", "reports/");
		param.put("name",nameAndAddress1[0]);
		param.put("propertyNo",a3);
		String[] XYZ = a3.split("-");
		param.put("address",a3+" Tower-"+XYZ[1]);
		param.put("city", "Gurugram");
		param.put("secondaryAddress",nameAndAddress1[2]+" "+nameAndAddress1[3]);
		int  area=(int) entityManager.createQuery("select p.area from Property p where p.property_No='"+a3+"'").getSingleResult();
		param.put("area",area);
		//param.put("sumAmt",Math.round(0.0));
		param.put("billNo", "N/A");			
		Calendar start = Calendar.getInstance();
		param.put("billDate",new SimpleDateFormat("dd-MMM-yyyy").format(start.getTime()));
		
		param.put("mtrNo",a2);
		
		param.put("previousBal",Double.parseDouble(preBal));
		param.put("rechargeAmt",a6);
		long bal=(long) Double.parseDouble(a5);
		param.put("consumptionAmt",bal);
		param.put("closingBal",Double.parseDouble(a7));
		/*
		param.put("mainUnit",Double.parseDouble(a8));
		param.put("dgUnit",Double.parseDouble(a9));
		param.put("elRate",12.0);
		param.put("dgRate",12.0);
		
		param.put("mainAmt",Double.parseDouble(12*Double.parseDouble(a8)+""));
		param.put("dgAmt",  Double.parseDouble(12*Double.parseDouble(a9)+""));
	    param.put("totalAmt",Double.parseDouble(12*Double.parseDouble(a8)+"") + Double.parseDouble(12*Double.parseDouble(a9)+""));
	    */
		
		Object[] read = (Object[]) entityManager.createNativeQuery("SELECT INITIAL_READING,DG_READING,TO_CHAR(READING_DATE,'dd/MM/yyyy') FROM PREPAID_METERS WHERE METER_NUMBER="+a2).getSingleResult();
		System.out.println("************ "+Double.parseDouble(read[0]+"")+" **************** "+Double.parseDouble(read[1]+"")+" *****************");
		Date servicedate=new SimpleDateFormat("dd/MM/yyyy").parse(read[2]+"");
		param.put("billingPeriod",new SimpleDateFormat("dd MMMM yyyy").format(servicedate)+ " To 31 March 2017");
		param.put("serviceDate",read[2]+"");
		param.put("ebReadInit", read[0]+"");
		param.put("ebReadFinal",a8);
		param.put("ebConsUnits",String.valueOf(Math.round((Double.parseDouble(a8)-Double.parseDouble(read[0]+"")))));    // ebReadFinal-ebReadInit
		param.put("ebInitRate", "12.0");    												   // 12
		param.put("ebConsAmt",  String.valueOf(Math.round((Double.parseDouble(a8)-Double.parseDouble(read[0]+""))*12))); // (ebReadFinal-ebReadInit)*12
		
		param.put("dgReadInit", read[1]+"");
		param.put("dgReadFinal",a9);
		param.put("dgConsUnits",String.valueOf(Math.round(Double.parseDouble(a9)-Double.parseDouble(read[1]+""))));
		param.put("dgInitRate", "12.0");
		param.put("dgConsAmt",  String.valueOf(Math.round((Double.parseDouble(a9)-Double.parseDouble(read[1]+""))*12)));
		
		param.put("grossTotal",String.valueOf(Math.round(((Double.parseDouble(a9)-Double.parseDouble(read[1]+""))*12)+((Double.parseDouble(a8)-Double.parseDouble(read[0]+""))*12))));
		
		Integer numToWord= (int) Math.round(((Double.parseDouble(a9)-Double.parseDouble(read[1]+""))*12)+((Double.parseDouble(a8)-Double.parseDouble(read[0]+""))*12));
		String amountInWords = NumberToWord.convertNumberToWords(numToWord.intValue());
		param.put("amountInWords", amountInWords + " Only");
		
		JREmptyDataSource jre = new JREmptyDataSource();
		JasperPrint jasperPrint;
		JasperReport report;
		try 
		{
			System.out.println("===================================== filling the report =====================================");
			jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/AccountStatement.jasper"),param,jre);

			removeBlankPage(jasperPrint.getPages());
			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
			InputStream myInputStream = new ByteArrayInputStream(bytes);
			Blob blob = Hibernate.createBlob(myInputStream);
			/*=====================================to save the BLOB as PDF in the given path=====================================*/
				InputStream is = blob.getBinaryStream();
				FileOutputStream fos = new FileOutputStream(fileName1);
				int b = 0;
				while ((b = is.read()) != -1)
				{
					fos.write(b); 
				}
				System.out.println("===================================== File Saved at <"+fileName1+">=====================================");
			/*=====================================to display the the saved PDF file=============================================*/
				ServletOutputStream servletOutputStream1;
				servletOutputStream1 = response.getOutputStream();
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "inline;filename=\"AccountStatement("+a3+").pdf"+"\"");
				FileInputStream input1 = new FileInputStream(fileName1);
				IOUtils.copy(input1, servletOutputStream1);		
				servletOutputStream1.flush();
				servletOutputStream1.close();
				
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("===================================== IOException =====================================");
		}			
		
		//*******************************************************************************************************************************
	}
	private void removeBlankPage(List<JRPrintPage> pages) {
		System.out.println("=====================================In Side RemoveBlankPage()=====================================");
		for (Iterator<JRPrintPage> i = pages.iterator(); i.hasNext();) {
			JRPrintPage page = i.next();
			if (page.getElements().size() == 4)
				i.remove();
		}
	}
}
