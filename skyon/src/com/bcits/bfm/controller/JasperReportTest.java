package com.bcits.bfm.controller;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.hibernate.Hibernate;

import com.bcits.bfm.model.BatchBillsEntity;
import com.bcits.bfm.util.NumberToWord;

public class JasperReportTest {
	public static void main(String[] args) throws SQLException {
		/*JasperReportTest call=new JasperReportTest();
		call.generatePostElectricityBill();*/
	}
	
	public  Blob generatePostElectricityBill(BatchBillsEntity entity) throws SQLException{
		JREmptyDataSource jre = new JREmptyDataSource();
		JasperPrint jasperPrint;int count=0;
		try{
			System.out.println("filling the report..........");count++;
			System.out.println("Account no:---------"+entity.getAccountNo()+"count= "+count);
			
			
			jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/PostPaidBill.jasper"),fillDataInJasper(entity),jre);
			removeBlankPage(jasperPrint.getPages());
			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
			InputStream myInputStream = new ByteArrayInputStream(bytes);
			Blob blob = Hibernate.createBlob(myInputStream);
			return blob;
			/*=====================================to save the BLOB as PDF in the given path=====================================*/
				/*InputStream is = blob.getBinaryStream();
				FileOutputStream fos = new FileOutputStream("C:/SKYON_STATEMENT/"+entity.getAccountNo()+"_PostElectricityBill.pdf");
				int b = 0;
				while ((b = is.read()) != -1){
					fos.write(b); 
				}*/
			
		}catch(JRException e){
			e.printStackTrace();
		}catch(IOException e){
			System.out.println("IOException.............");
			e.printStackTrace();
		}
		return null;
	}
	
	public HashMap<String, Object> fillDataInJasper(BatchBillsEntity entity){
		HashMap<String, Object> param = new HashMap<String, Object>();
		try{
		double unitsVal=Double.parseDouble(entity.getPresentReading())-(Double.parseDouble(entity.getPreviousReading()));
		double units = Math.round(unitsVal * 100.0) / 100.0;
		
		double dgUnitsVal=Double.parseDouble(entity.getDGpresentReading())-(Double.parseDouble(entity.getDGPreviousReading()));
		double dgUnits = Math.round(dgUnitsVal * 100.0) / 100.0;
		
		double rateVal=Double.parseDouble(entity.getRate());
		double rate = Math.round(rateVal * 100.0) / 100.0;
		
	    double amtVal=(units*rateVal)+(dgUnits+dgUnitsVal);
	    double amt = Math.round(amtVal * 100.0) / 100.0;
	    
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		long prevRdgDate=(sdf.parse(entity.getPreviousBillDate()).getTime())/(24 * 60 * 60 * 1000);
	    
		
		long presRdgDate=(sdf.parse(entity.getPresentReadingDate()).getTime())/(24 * 60 * 60 * 1000);


/*
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(entity.getPresentReadingDate())); // Now use today date.
		c.add(Calendar.DATE, 5); // Adding 5 days
		String output = new SimpleDateFormat("dd MMM. yyyy").format(c.getTime());
*/

		
	//	System.err.println(prevRdgDate +"---"+presRdgDate+"---units= "+units+"---amt = "+amt);
		
		double arrAmt=0.0;
		long days=presRdgDate-prevRdgDate;
		double latePaymentAmt=((amt+arrAmt+(Double.parseDouble(entity.getMiscellanous())))/(100))*2;
		 double latePayment = Math.round(latePaymentAmt * 100.0) / 100.0;
		double amtPayableAmt=amt+arrAmt+(Double.parseDouble(entity.getMiscellanous()))+latePayment;
		double amtPayable = Math.round(amtPayableAmt * 100.0) / 100.0;
		
		param.put("realPath",".//reports//");
		// $P{realPath}+"grandArch.jpg"
		param.put("title","Skyon Condominium Owners Welfare Association");
		param.put("companyAddress","Sector-60, Gurgaon, Haryana - 122102");
		param.put("serviceType","Electricity");
		
		param.put("name",entity.getName());
		param.put("address",entity.getAddress());
		param.put("secondaryAddress",entity.getProperty());
		param.put("city","Gurgaon");
		param.put("caNo","NA");
		param.put("tariffCategory","Single Point Connection");
		param.put("billBasis","Normal");
		
		param.put("sanctionedUtility","3 KW");
		param.put("sanctionedDG","-");
		param.put("voltageLevel","230 V");
		param.put("meterMake","");
		param.put("meterSrNo",entity.getMeterNo());
		param.put("meterStatus","Normal");
		
		param.put("amountPayble",String.valueOf(amt));
		param.put("dueDate",entity.getPaymentDueDate());
		param.put("DueDate",entity.getPaymentDueDate());
		param.put("surcharge",String.valueOf(latePayment));
		param.put("amountAfterDueDate",String.valueOf(amtPayable));
		param.put("billNo",entity.getReceiptNo());
		param.put("billDate",new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
		param.put("billingPeriod",entity.getPreviousBillDate()+" To "+entity.getPresentReadingDate());
		param.put("panNo","AAPAS0918P");
		param.put("sTaxNo","06AAPAS0918P1ZV");
		
		param.put("energyType","Electricity");
		param.put("dgEnergyType","DG");
		param.put("mdi","0");
		param.put("dgmdi","0");
		param.put("presentBillDate",entity.getPresentReadingDate());
		param.put("previousBillDate",entity.getPreviousBillDate());
		
		param.put("presentReading",entity.getPresentReading());
		param.put("previousReading",entity.getPreviousReading());
		
		param.put("dgPresentReading",entity.getDGpresentReading());
		param.put("dgPreviousReading",entity.getDGPreviousReading());
		
		param.put("mf","1");
		param.put("dgMeterConstant","-");
		param.put("units",String.valueOf(units));
		param.put("dgUnits",String.valueOf(dgUnits));
		param.put("days",String.valueOf(days));
		DecimalFormat df=new DecimalFormat("#.##");
		String roundOffAmt=df.format(1-(amt-Math.floor(amt)));
		System.err.println("roundOffVal= "+roundOffAmt);
		param.put("elenergy",String.valueOf(amt));
		param.put("elroudoff",roundOffAmt);
		param.put("slabunits",String.valueOf(units));
		param.put("rates",String.valueOf(rate));
		param.put("amounts",String.valueOf(df.format(amt)));
		
		param.put("arrearsAmount",String.valueOf(arrAmt));
		param.put("clearedAmount",entity.getMiscellanous());
		param.put("billAmount",String.valueOf((amt+arrAmt+(Double.parseDouble(entity.getMiscellanous())))+Double.parseDouble(roundOffAmt)));
		//param.put("dueDate","Electricity");
		param.put("amountInWords",NumberToWord.convertNumberToWords((int) amt));
		
		param.put("point1","1. Payment can be made by crossed account payee cheque or bank draft drawn in favour of \"SKYON CONDOMINUM OWENERS WELFARE ASSOCIATION\" payable at Delhi/Gurgaon. No cash payments shall be accepted. Cheque can be submitted at Estate Office, Skyon, Gurugram - 122102, Haryana");
		param.put("point2","2. Add Rs.50.00 for outstation cheques and bank charges of Rs. 150.00 shall be levied on dishonoured cheques");
		param.put("point3","3. Rs.500 will be charged payable by DD or crossed cheque for testing of energy meter");
		param.put("point4","4. In case the user fails to pay the bill on or before due date indicated in the bill this will be deemed to be a notice and the electricity supply to the premises and the maintenance services to the user shall, without prejudice to the right of SCOWA recover such charges as of the bill by suit, be disconnected after the expiry of seven days from the due date mentioned in the bill together with interest @ 24% p.a. and reconnection charges of (Rs.) 500 payable by the user");
		param.put("point5","5. GST is applicable as per govt. norms");
		param.put("point6","6. This is a computer generated Invoice and does not require a physical signature");
		param.put("note","Notes : Skyon Condominium Owners Welfare Association");
		
		return param; 
		}catch(Exception e)
		{
			e.printStackTrace();return null;
		} 
		
	}
	
	private static void removeBlankPage(List<JRPrintPage> pages) {
		System.out.println("=====================================In Side RemoveBlankPage()=====================================");
		for (Iterator<JRPrintPage> i = pages.iterator(); i.hasNext();) {
			JRPrintPage page = i.next();
			if (page.getElements().size() == 4)
				i.remove();
		}
	}
}