package com.bcits.bfm.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jettison.json.JSONException;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.model.Address;
import com.bcits.bfm.model.BatchBillsEntity;
import com.bcits.bfm.model.BillDocEntity;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.PostLedgerEntity;
import com.bcits.bfm.model.PrePaidDailyData;
import com.bcits.bfm.model.PrePaidMeters;
import com.bcits.bfm.model.PrepaidBillDetails;
import com.bcits.bfm.model.PrepaidBillDocument;
import com.bcits.bfm.model.PrepaidCalcuStoreEntity;
import com.bcits.bfm.model.PrepaidCalculationBasisEntity;
import com.bcits.bfm.model.PrepaidTxnChargesEntity;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.BatchBillService;
import com.bcits.bfm.service.BillDocService;
import com.bcits.bfm.service.PostpaidBillService;
import com.bcits.bfm.service.PrePaidInstantDataService;
import com.bcits.bfm.service.PrePaidMeterService;
import com.bcits.bfm.service.PrepaidBillDocService;
import com.bcits.bfm.service.PrepaidBillService;
import com.bcits.bfm.service.PrepaidCalcuStoreService;
import com.bcits.bfm.service.PrepaidPaymentAdjustmentService;
import com.bcits.bfm.service.PrepaidServiceMasterSs;
import com.bcits.bfm.service.PrepaidTransactionService;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.util.JsonResponse;
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

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.sun.media.sound.InvalidFormatException;

@Controller
public class PrePaidBillGenerationController {
	
	final Logger logger=LoggerFactory.getLogger(PrePaidBillGenerationController.class);
	
	@PersistenceContext(unitName="bfm")
    protected EntityManager entityManager;
	
	@Autowired
	private PrepaidBillService prepaidBillService;
	
	@Autowired
	private AdjustmentService adjustmentService; 
	
	@Autowired
	private PrepaidBillDocService prepaidBillDocService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private PrepaidTransactionService transactionService;
	
	@Autowired
	private PrePaidMeterService prepaidMeterService;
	
	@Autowired
	private PrepaidPaymentAdjustmentService prepaidPaymentAdjustmentService;
	@Autowired
	private PrePaidInstantDataService prePaidInstantDataService; 
	@Autowired
	private PrepaidCalcuStoreService  prepaidCalcuStoreService;
	@Autowired
	private PrepaidServiceMasterSs prepaidServiceMasterSs;
	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private PrepaidTransactionService prepaidTransactionService;
	
	@Autowired
	private BillDocService billDocService;
	
	@Autowired
	private BatchBillService batchbillService;
	
	@Autowired
	private PostpaidBillService postpaidBillService;
	
	@RequestMapping(value="/Calculate")
	public String index(){
		return "serviceChargesCalcu";
	}
	
	 @RequestMapping(value="/serviceChargeCalcu/getMeterNumberUrl", method={RequestMethod.GET,RequestMethod.POST})
	 public @ResponseBody List<?> readMeterNums(){
		 
		 List<Map<String, Object>> resultList=new ArrayList<>();
		 Map<String, Object> mapList=null;
		 List<?> adjustList=prepaidPaymentAdjustmentService.readMeters();
		 for(Iterator<?> iterator=adjustList.iterator();iterator.hasNext();){
			 final Object[] value=(Object[]) iterator.next();
			 mapList=new HashMap<>();
			// mapList.put("propertyId", value[0]);
			 mapList.put("consumerId", value[1]);
			// mapList.put("propertyId", value[2]);
			 resultList.add(mapList);
		 }
		 
		 return resultList;
	 }
	
	@RequestMapping(value="/serviceChargeCalcu/calculateCharges",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object generateBill(HttpServletRequest req,
			HttpServletResponse response)
			throws ParseException, JSONException, IOException{
		
		  String serviceID=req.getParameter("serviceId");
		  String fromDate= req.getParameter("fromDate");
		  String toDate=req.getParameter("toDate");
		  String propertyId=req.getParameter("propertyId");
		  String blockName=req.getParameter("blockName");
		  int blockId=Integer.parseInt(req.getParameter("blocId"));
		  JsonResponse erResponse=new JsonResponse();
		  PrintWriter out;
		  Date minDate=null;
		  Date maxDate=null;
		  logger.info("serviceID "+serviceID+", "+"fromDate "+fromDate+", "+"toDate "+toDate+", "+"blockName "+blockName +", "+"propertyId "+propertyId);
		  long   serviceid=(long) entityManager.createQuery("select COUNT(p.serviceId) from PrepaidServiceMaster p where p.serviceId="+serviceID+"and p.status='Active'").getSingleResult();
		  if(serviceid!=0){
		  List<Map<String, Object>> datelist=prepaidServiceMasterSs.getMinMaxDate(Integer.parseInt(serviceID));
		  for (Map<String, Object> date : datelist) {
			  for(Entry<String, Object> maEntry : date.entrySet()){
				  if(maEntry.getKey().equalsIgnoreCase("fromDate")){
					  minDate=(Date) maEntry.getValue();
					  System.err.println(minDate);
				  }
				  if(maEntry.getKey().equalsIgnoreCase("toDate")){
					  maxDate=(Date) maEntry.getValue();System.out.println("************************"+maxDate+"*********************************");
					  System.err.println(maxDate);
				  }
			  }
		   }
		  Date frDate=new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
		  Date todate=new SimpleDateFormat("dd/MM/yyyy").parse(toDate);
		  Calendar cal=Calendar.getInstance();
		  cal.setTime(frDate);
		  int noofDays=cal.getActualMaximum(Calendar.DAY_OF_YEAR);
		  if(maxDate.compareTo(frDate)<0){
			 erResponse.setResult("Charges Rates Valid From "+minDate+ "Valid To "+maxDate);
			 return erResponse;
		  }else if(maxDate.compareTo(todate)<0){
			  erResponse.setResult("Charges Rates Valid From "+minDate+ "Valid To "+maxDate);
				 return erResponse;
		  }
		  else{
		    String camma=",";
		    String[] propList=propertyId.split(camma);
		    int count1=0;
		 // List<String> meterId=prepaidMeterService.getMeterNumber();
		  String cbName=(String) entityManager.createQuery("select pc.cbName from PrepaidCalculationBasisEntity pc where pc.sId="+serviceID).getSingleResult();
	      List<PrepaidTxnChargesEntity> prEntities=transactionService.getall(Integer.parseInt(serviceID));
		   for(int i=0; i<propList.length;i++){	System.out.println(propList[i]+"  ??????????????????????????????????????????????????????????");
			int propId=Integer.parseInt(propList[i]);
			Object[] prePaidMeters=prepaidMeterService.getserviceStartDate(propId);
			if(prePaidMeters[0]!=null){
			String meterNumber=prepaidMeterService.getMtrNo(propId);
			String service_Name=(String) entityManager.createQuery("select p.service_Name from PrepaidServiceMaster p where p.serviceId="+serviceID).getSingleResult();
			Date maxdate=prepaidCalcuStoreService.getMAxDate(meterNumber,service_Name);
			System.err.println("MAX DATE :"+maxdate);
		
			/*if(maxdate!=null && maxdate.compareTo(frDate)>0){ 
			
				out=response.getWriter();
				out.write("From Date Should Be Greater than Previous Bill Date " +maxdate+" Meter Number "+meterNumber+"/n");
			}else if(maxdate!=null && maxdate.compareTo(frDate)==0){
				out=response.getWriter();
				out.write("From Date Should Be Greater than Previous Bill Date " +maxdate+" Meter Number "+meterNumber+"/n");
			}else{*/
				
				double ebUnit=0.0;
				double dgUnit=0.0;
				 double camfxdchrg=0.0;
				 double rechargeAmt=0.0;
				 int flag=1;
				PrePaidDailyData prePaidDailyData=prePaidInstantDataService.getConsumptionData(fromDate,meterNumber,flag);
				//logger.info("prePaidDailyData   "+prePaidDailyData);
				if(prePaidDailyData!=null){
					System.out.println("*&*&*&*&&&&&&***********&&&&&&&&&&&&&&&&&&&&&");
					ebUnit=Double.parseDouble(prePaidDailyData.getCum_Kwh_Main());
					logger.info("ebUnit "+ebUnit);
					dgUnit=Double.parseDouble(prePaidDailyData.getCum_Kwh_Dg());
					camfxdchrg=Double.parseDouble(prePaidDailyData.getCum_Fixed_Charg_Dg());
					//rechargeAmt=Double.parseDouble(prePaidDailyData.getCum_Recharge_Amount());
				}else{
					int flag1=2;
					PrePaidDailyData prePaidDailyData1=prePaidInstantDataService.getConsumptionData(fromDate,meterNumber,flag1);
					if(prePaidDailyData1!=null){
					ebUnit=Double.parseDouble(prePaidDailyData1.getCum_Kwh_Main());
					logger.info("ebUnit "+ebUnit);
					dgUnit=Double.parseDouble(prePaidDailyData1.getCum_Kwh_Dg());
					camfxdchrg=Double.parseDouble(prePaidDailyData1.getCum_Fixed_Charg_Dg());
					//rechargeAmt=Double.parseDouble(prePaidDailyData1.getCum_Recharge_Amount());
					}
				}
			List<Object[]> dailyData=new ArrayList<>();
			dailyData=prePaidInstantDataService.getConsumptionDetails(fromDate,toDate,meterNumber);
			
			System.out.println("list size++++++++++"+dailyData.size());
			if(dailyData.size()!=0){
			int area=0;
			int personId=prepaidMeterService.getPersonId(propId);
			
			 area=(int) entityManager.createQuery("select p.area from Property p where p.propertyId="+propId).getSingleResult();
			 /*if(cbName.equalsIgnoreCase("AREA")){
				 Date startDate=new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
				    Date endDate=new SimpleDateFormat("dd/MM/yyyy").parse(toDate);
				    Calendar start = Calendar.getInstance();
					start.setTime(startDate);
					//start.add(Calendar.DATE, 1);
					Date d=new Date();
					Calendar end = Calendar.getInstance();
					end.setTime(endDate);
					end.add(Calendar.DATE, 1);
				 for (Date date1 = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date1 = start.getTime()) {
					 String date=new SimpleDateFormat("dd/MM/yyyy").format(date1);
					 long count=prepaidCalcuStoreService.checkDataExistence(propId,date,service_Name);
					 if(!(count>0)){
					 List<PrepaidCalcuStoreEntity> list=new ArrayList<>();
					   double  consumAmt=0.0;
					   for (PrepaidTxnChargesEntity prepaidTxnChargesEntity : prEntities) {
						  String chargeTpe=prepaidTxnChargesEntity.getCharge_Type(); 
						   if(chargeTpe.equals("Charge")){
							   double  rate=prepaidTxnChargesEntity.getRate();
							   consumAmt=(area*rate*12)/noofDays;
						   }
					   }
					   for (PrepaidTxnChargesEntity prepaidTxnChargesEntity : prEntities) {
						   PrepaidCalcuStoreEntity prStoreEntity=new PrepaidCalcuStoreEntity();
					    	double rate=prepaidTxnChargesEntity.getRate();
					    	String chargeName=prepaidTxnChargesEntity.getCharge_Name();
					    	String chargeTpe=prepaidTxnChargesEntity.getCharge_Type(); 
					   	   String   serviceName=(String) entityManager.createQuery("select p.service_Name from PrepaidServiceMaster p where p.serviceId="+prepaidTxnChargesEntity.getSid()).getSingleResult();
					    
					   if(chargeTpe.equals("Charge")){
						prStoreEntity.setRate(String.valueOf(rate));
						prStoreEntity.setDaily_Consumption_Amt(consumAmt);
						prStoreEntity.setChargeName(chargeName);
						
					   }else{
						   
						  double  consumAmt1=(consumAmt*rate)/100;
						    prStoreEntity.setRate(String.valueOf(rate));
						    prStoreEntity.setDaily_Consumption_Amt(consumAmt1);
						    prStoreEntity.setChargeName(chargeName);
					   }
					   prStoreEntity.setArea(area);
					   prStoreEntity.setPersonId(personId);
					   prStoreEntity.setPropertyId(propId);
					   prStoreEntity.setCbName(cbName);
					   prStoreEntity.setServiceName(serviceName);
					   prStoreEntity.setDaily_Units_Consumed(0.0);
					   prStoreEntity.setMeterNo(meterNumber);
					   prStoreEntity.setReadingDate(date);
					   list.add(prStoreEntity);
					   prepaidCalcuStoreService.save(prStoreEntity);
					   }
					 }	 
			 }
			 }*/
			
			for (Object[] objects : dailyData) {
				 double camchrg=Double.parseDouble(objects[6]+"");
				 double egCal=Double.parseDouble(objects[8]+"");
				 double dgreading=Double.parseDouble(objects[9]+"");
				 double dailyRechamt=Double.parseDouble(objects[9]+"");
				   double EBreading=egCal-ebUnit;	
				   double dgDailyUnits=dgreading-dgUnit;
				   double camfixedCharges=camchrg-camfxdchrg;
				   //double dailyRechargeamt=dailyRechamt-rechargeAmt;
				   camfxdchrg=camchrg;
				   ebUnit=egCal;
				   dgUnit=dgreading;
				   rechargeAmt=dailyRechamt;
				long count=prepaidCalcuStoreService.checkDataExistence(propId,objects[2]+"",service_Name);
				logger.info("count "+count);
				if(!(count>0)){
		   if(cbName.equalsIgnoreCase("AREA")){
			   List<PrepaidCalcuStoreEntity> list=new ArrayList<>();
			   double  consumAmt=0.0;
			   for (PrepaidTxnChargesEntity prepaidTxnChargesEntity : prEntities) {
				  String chargeTpe=prepaidTxnChargesEntity.getCharge_Type(); 
				   if(chargeTpe.equals("Charge")){
					   double  rate=prepaidTxnChargesEntity.getRate();
					   consumAmt=(camfixedCharges/1.15);
					   logger.info("CAM consumAmt "+consumAmt);
					   //consumAmt=(area*rate*12)/noofDays;
				   }
			   }
			   for (PrepaidTxnChargesEntity prepaidTxnChargesEntity : prEntities) {
				   PrepaidCalcuStoreEntity prStoreEntity=new PrepaidCalcuStoreEntity();
			    	double rate=prepaidTxnChargesEntity.getRate();
			    	String chargeName=prepaidTxnChargesEntity.getCharge_Name();
			    	String chargeTpe=prepaidTxnChargesEntity.getCharge_Type(); 
			   	   String   serviceName=(String) entityManager.createQuery("select p.service_Name from PrepaidServiceMaster p where p.serviceId="+prepaidTxnChargesEntity.getSid()).getSingleResult();

	//********************************************  Added By Praveen Kumar FOR AREA *******************************************************
				System.out.println("");
				System.out.println("");
				System.out.println("****************************  Multiple Tariff FOR AREA  ****************************");
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date serviceFrom = new SimpleDateFormat("dd/MM/yyyy").parse(sdf.format(prepaidTxnChargesEntity.getValidFrom()));
				Date serviceTo = new SimpleDateFormat("dd/MM/yyyy").parse(sdf.format(prepaidTxnChargesEntity.getValidTo()));
				Date dailyDate = new SimpleDateFormat("dd/MM/yyyy").parse(objects[2]+""); 
				System.out.println("**************************** entered dates are: "+ sdf.format(frDate)       +" & "+ sdf.format(todate)    +" **************************");
				System.out.println("**************************** service dates are: "+ sdf.format(serviceFrom)  +" & "+ sdf.format(serviceTo) +" **************************");
				System.out.println("****************************      service name: "+ prepaidTxnChargesEntity.getCharge_Name()+" *****************");
				System.out.println("**************************** Daily Data Reading Date is "+objects[2]+"**************************");
				System.out.println("");

			if((serviceFrom.compareTo(dailyDate)<0 && serviceTo.compareTo(dailyDate)>0)||serviceFrom.compareTo(dailyDate)==0 || serviceTo.compareTo(dailyDate)==0)
			{       
				System.out.println("**************************** Result of IF Condition: "+serviceFrom.compareTo(dailyDate) +" & "+ serviceTo.compareTo(dailyDate)+" ****************************");	
				System.out.println("**************************** "+sdf.format(serviceFrom)+" < "+sdf.format(dailyDate)+" < "+sdf.format(serviceTo)+" ****************************");
	//*******************************************************************************************************************************
			   if(chargeTpe.equals("Charge")){
				prStoreEntity.setRate(String.valueOf(rate));
				prStoreEntity.setDaily_Consumption_Amt(consumAmt);
				prStoreEntity.setChargeName(chargeName);
				
			   }else{
				   
				  double  consumAmt1=(consumAmt*rate)/100;
				    prStoreEntity.setRate(String.valueOf(rate));
				    prStoreEntity.setDaily_Consumption_Amt(consumAmt1);
				    prStoreEntity.setChargeName(chargeName);
			   }
			   prStoreEntity.setArea(area);
			   prStoreEntity.setPersonId(personId);
			   prStoreEntity.setPropertyId(propId);
			   prStoreEntity.setCbName(cbName);
			   prStoreEntity.setServiceName(serviceName);
			   prStoreEntity.setDaily_Units_Consumed(0.0);
			   prStoreEntity.setMeterNo(objects[1]+"");
			   prStoreEntity.setReadingDate(objects[2]+"");
			   list.add(prStoreEntity);
			   prepaidCalcuStoreService.save(prStoreEntity);
		  }
	     }
	   }else if(cbName.equalsIgnoreCase("UNIT")){
			  
			   double consumAmt=0.0;
			   double units=0.0;
			   for (PrepaidTxnChargesEntity prepaidTxnChargesEntity : prEntities) {
				  String chargeTpe=prepaidTxnChargesEntity.getCharge_Type();
				  String   serviceName=(String) entityManager.createQuery("select p.service_Name from PrepaidServiceMaster p where p.serviceId="+prepaidTxnChargesEntity.getSid()).getSingleResult();
				 
				  
				  
				  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				  Date serviceFrom = new SimpleDateFormat("dd/MM/yyyy").parse(sdf.format(prepaidTxnChargesEntity.getValidFrom()));
					Date serviceTo = new SimpleDateFormat("dd/MM/yyyy").parse(sdf.format(prepaidTxnChargesEntity.getValidTo()));
					Date dailyDate = new SimpleDateFormat("dd/MM/yyyy").parse(objects[2]+""); 
				  if((serviceFrom.compareTo(dailyDate)<0 && serviceTo.compareTo(dailyDate)>0)||serviceFrom.compareTo(dailyDate)==0 || serviceTo.compareTo(dailyDate)==0)
				  {
				  if(chargeTpe.equals("Charge")){
					   if(serviceName.equalsIgnoreCase("DG")){
					   double  rate=prepaidTxnChargesEntity.getRate();
					    //units=Double.parseDouble(objects[11]+"");
					   consumAmt=rate*dgDailyUnits;
					   }else{
						   double  rate=prepaidTxnChargesEntity.getRate();
						   // units=Double.parseDouble(objects[10]+"");
						   consumAmt=rate*EBreading;
					   }
				   }
			   }
			   }
			   for (PrepaidTxnChargesEntity prepaidTxnChargesEntity : prEntities) {
				   PrepaidCalcuStoreEntity prStoreEntity=new PrepaidCalcuStoreEntity();
			    	double rate=prepaidTxnChargesEntity.getRate();
			    	String chargeName=prepaidTxnChargesEntity.getCharge_Name();
			    	String chargeTpe=prepaidTxnChargesEntity.getCharge_Type(); 
			    	
			   	   String   serviceName=(String) entityManager.createQuery("select p.service_Name from PrepaidServiceMaster p where p.serviceId="+prepaidTxnChargesEntity.getSid()).getSingleResult();

	//********************************************  Added By Praveen Kumar FOR UNIT *******************************************************
			System.out.println("");
			System.out.println("");
			System.out.println("****************************  Multiple Tariff FOR UNIT  ****************************");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date serviceFrom = new SimpleDateFormat("dd/MM/yyyy").parse(sdf.format(prepaidTxnChargesEntity.getValidFrom()));
			Date serviceTo = new SimpleDateFormat("dd/MM/yyyy").parse(sdf.format(prepaidTxnChargesEntity.getValidTo()));
			Date dailyDate = new SimpleDateFormat("dd/MM/yyyy").parse(objects[2]+""); 
		    System.out.println("**************************** entered dates are: "+ sdf.format(frDate)       +" & "+ sdf.format(todate)    +" **************************");
			System.out.println("**************************** service dates are: "+ sdf.format(serviceFrom)  +" & "+ sdf.format(serviceTo) +" **************************");
			System.out.println("****************************      service name: "+ prepaidTxnChargesEntity.getCharge_Name()+" *****************");
			System.out.println("**************************** Daily Data Reading Date is "+objects[2]+" **************************");
			System.out.println("");

			if((serviceFrom.compareTo(dailyDate)<0 && serviceTo.compareTo(dailyDate)>0)||serviceFrom.compareTo(dailyDate)==0 || serviceTo.compareTo(dailyDate)==0)
			{       
			   System.out.println("**************************** Result of IF Condition: "+serviceFrom.compareTo(dailyDate) +" & "+ serviceTo.compareTo(dailyDate)+" ****************************");	
			   System.out.println("**************************** "+sdf.format(serviceFrom)+" < "+sdf.format(dailyDate)+" < "+sdf.format(serviceTo)+" ****************************");
	//*******************************************************************************************************************************
			   if(chargeTpe.equals("Charge")){
				prStoreEntity.setRate(String.valueOf(rate));
				long data= Math.round(consumAmt);
			    double cunsumptData=Double.parseDouble(String.valueOf(data));
				prStoreEntity.setDaily_Consumption_Amt(cunsumptData);
				prStoreEntity.setChargeName(chargeName);
				
			   }else{
				   
				  double  consumAmt1=(consumAmt*rate)/100;
				     long data= Math.round(consumAmt1);
				    double cunsumptData=Double.parseDouble(String.valueOf(data));
				    prStoreEntity.setRate(String.valueOf(rate));
				    prStoreEntity.setDaily_Consumption_Amt(cunsumptData);
				    prStoreEntity.setChargeName(chargeName);
			   }
			   prStoreEntity.setArea(area);
			   prStoreEntity.setPersonId(personId);
			   prStoreEntity.setPropertyId(propId);
			   prStoreEntity.setCbName(cbName);
			   prStoreEntity.setServiceName(serviceName);
			   if(serviceName.equalsIgnoreCase("DG")){
			   prStoreEntity.setDaily_Units_Consumed(dgDailyUnits);
			   }else{
				   prStoreEntity.setDaily_Units_Consumed(EBreading);
			   }
			   prStoreEntity.setMeterNo(objects[1]+"");
			   prStoreEntity.setReadingDate(objects[2]+"");
			  
			   prepaidCalcuStoreService.save(prStoreEntity);
			   }
			 } 
		   }else if(cbName.equalsIgnoreCase("FLATRATE") || cbName.equalsIgnoreCase("LUMPSUM")){
			  
			   for (PrepaidTxnChargesEntity prepaidTxnChargesEntity : prEntities) {
				   PrepaidCalcuStoreEntity prStoreEntity=new PrepaidCalcuStoreEntity();
			    	double rate=prepaidTxnChargesEntity.getRate();
			    	String chargeName=prepaidTxnChargesEntity.getCharge_Name();
			    	String chargeTpe=prepaidTxnChargesEntity.getCharge_Type(); 
			    	
			   	   String   serviceName=(String) entityManager.createQuery("select p.service_Name from PrepaidServiceMaster p where p.serviceId="+prepaidTxnChargesEntity.getSid()).getSingleResult();
			    
			   if(chargeTpe.equals("Charge")){
				   
				prStoreEntity.setRate(String.valueOf(rate));
				prStoreEntity.setDaily_Consumption_Amt(rate);
				prStoreEntity.setChargeName(chargeName);
				
			   }/*else{
				   
				  double  consumAmt1=(consumAmt*rate)/100;
				    prStoreEntity.setRate(String.valueOf(rate));
				    prStoreEntity.setDaily_Consumption_Amt(consumAmt1);
				    prStoreEntity.setChargeName(chargeName);
			   }*/
			   prStoreEntity.setArea(area);
			   prStoreEntity.setPersonId(personId);
			   prStoreEntity.setPropertyId(propId);
			   prStoreEntity.setCbName(cbName);
			   prStoreEntity.setServiceName(serviceName);
			   prStoreEntity.setDaily_Units_Consumed(0.0);
			   prStoreEntity.setMeterNo(objects[1]+"");
			   prStoreEntity.setReadingDate(objects[2]+"");
			
			   prepaidCalcuStoreService.save(prStoreEntity);
			   }
			 
		   }
			}
			}
			
			}else{
				count1--;
			out=response.getWriter();
			out.write("Consumption Data Not Available for meterNumber "+meterNumber+"\n");
			}
			/*else{
				//erResponse.setStatus("NOCONSUMPTION");
				erResponse.setResult("Consumption Data Not Available for meterNumber "+meterNumber);
				return erResponse;
			}*/
		
			
			/*out=response.getWriter();
			out.write("Charges Calculation Done Successfully");*/
			//}
			}else{
				count1--;
				out=response.getWriter();
				out.write("Service Not Started for Property"+prepaidMeterService.getPropertyNo(propId)+"\n");
			}
		  
		   count1++;
		   }
		   out=response.getWriter();
			out.write( "Charges Calculation Done Successfully for "+count1+"Properties"+"\n");
		}
		
		}else{
			//erResponse.setStatus("STATUS");
			erResponse.setResult("Your Selected Service Status is Inactivated");
			return erResponse;
			/*out=response.getWriter();
			out.write("Your Selected Service Status is Inactivated");*/
		}
		  return null;}
	@RequestMapping(value="/serviceChargeCalcu/readUrl",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> readData(){
		List<Map<String, Object>> resultList=new ArrayList<>();
		Map<String, Object> mapList=null;
		List<PrepaidCalcuStoreEntity> pEntities=prepaidCalcuStoreService.getAllStoreData();
		for (PrepaidCalcuStoreEntity prepaidCalcuStoreEntity : pEntities) {
			mapList=new HashMap<>();
			mapList.put("sccId",prepaidCalcuStoreEntity.getPcsId());
		
			if(prepaidCalcuStoreEntity.getPersonId()!=0){
			 List<?> peList=prepaidMeterService.getOwnerName(prepaidCalcuStoreEntity.getPersonId());
		   	   for(Iterator<?> iterator2=peList.iterator();iterator2.hasNext();){
		   	      final Object[] person=(Object[]) iterator2.next();
		       mapList.put("customer_Name", person[0]+""+person[1]);
		   	   }
			}
			if(prepaidCalcuStoreEntity.getPropertyId()!=0){
			mapList.put("property_No",prepaidMeterService.getPropertyNo(prepaidCalcuStoreEntity.getPropertyId()) );
			}
			mapList.put("area",prepaidCalcuStoreEntity.getArea());
			mapList.put("reading_Date", prepaidCalcuStoreEntity.getReadingDate());
			mapList.put("service_Name",prepaidCalcuStoreEntity.getServiceName() );
			mapList.put("charge_Name", prepaidCalcuStoreEntity.getChargeName());
			mapList.put("rate", prepaidCalcuStoreEntity.getRate());
			mapList.put("calculation_Basis",prepaidCalcuStoreEntity.getCbName() );
			mapList.put("daily_units_consumed", prepaidCalcuStoreEntity.getDaily_Units_Consumed());
			mapList.put("daily_Consumption_amt",prepaidCalcuStoreEntity.getDaily_Consumption_Amt() );
			mapList.put("daily_Rech_amt", prepaidCalcuStoreEntity.getDaily_Recharge_Amt());
			mapList.put("daily_balance", prepaidCalcuStoreEntity.getDaily_Balance());
			
			resultList.add(mapList);
		}
		
		return resultList;
	}
	
	public String exportConsumptionHtyToPdf(HttpServletRequest request,HttpServletResponse response) throws ParseException, DocumentException, MalformedURLException, IOException{
		logger.info("in side Export PDF");
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
		logger.info("FromMonth " +request.getParameter("fromDate"));
		logger.info("ToMonth " +request.getParameter("consumerID"));
		logger.info("PropertyId " +request.getParameter("propertyId"));
		
		String fromDate= request.getParameter("fromDate");
		String consumerId=request.getParameter("consumerID");
		int propertyId=Integer.parseInt(request.getParameter("propertyId"));
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
		//Date todate = formatter.parse(presentdate);
		Date fromdate=formatter.parse(fromDate);
		System.out.println("date from to date"+fromDate);
		List<Object[]> dailyData=new ArrayList<>();
		dailyData=prePaidInstantDataService.getConsumption(fromdate,fromdate,consumerId);
		System.out.println("list size++++++++++"+dailyData.size());
		 Document document = new Document(PageSize.A4, 20, 20, 50, 50);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	      PdfWriter writer=PdfWriter.getInstance(document, baos);
	      Rectangle rect = new Rectangle(30, 30, 300, 810);
	      writer.setBoxSize("art", rect);
	     
	      com.bcits.bfm.util.HeaderFooterPageEvent event=new com.bcits.bfm.util.HeaderFooterPageEvent();
	      writer.setPageEvent(event);
	        document.open(); 
	        Paragraph p = new Paragraph(" ");
	        p.setAlignment(Element.ALIGN_CENTER);
	        document.add(p);
	        Image image = Image.getInstance("C:/skyon.png");
	        image.scaleAbsolute(80,80);
	        image.setAbsolutePosition(10f, 765f);
	        image.setAlignment(image.LEFT);
	        document.add(image);
	        String mobileNo=null;
	        String emailID=null;
	       Person person=personService.find(prepaidMeterService.getPersonId(propertyId));
	        Set<Contact> contactsList = person.getContacts();
			for (Contact contact : contactsList) {
				if(contact.getContactPrimary().equals("Yes")){
					if(contact.getContactType().equals("Email")){
						contact.getContactContent();
						//payUForm.setCustomerEmail(contact.getContactContent());
					}else{
						mobileNo=contact.getContactContent();
						//payUForm.setCustomerMobile(contact.getContactContent());
					}
				}
			}
			 
	        Paragraph paragraph = new Paragraph(
	        		"Consumer Name: "+person.getFirstName()+person.getLastName(),new Font(Font.FontFamily.HELVETICA, 8));
	        Paragraph paragraph1= new Paragraph(
	        		"Property Number: "+prepaidMeterService.getPropertyNo(propertyId),new Font(Font.FontFamily.HELVETICA, 8));
	        Paragraph paragrap2 = new Paragraph(
	        		"Consumer ID      :  "+consumerId,new Font(Font.FontFamily.HELVETICA, 8));
	        Paragraph paragrap3 = new Paragraph(
	        		"Mobile Number   : "+mobileNo,new Font(Font.FontFamily.HELVETICA, 8));
	        document.add(paragraph);
	        document.add(paragraph1);
	        document.add(paragrap2);
	        document.add(paragrap3);
	        document.add(new Paragraph("Consumption Month : "+fromDate,new Font(Font.FontFamily.HELVETICA, 8)));
	        document.add(Chunk.SPACETABBING);
	        PdfPTable table = new PdfPTable(8);
	        Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
	        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
	        
	    	
	        
	        table.addCell(new Paragraph("Consumer ID",font1));
	        table.addCell(new Paragraph("Reading Date",font1));
	        table.addCell(new Paragraph("Balance",font1));
	        table.addCell(new Paragraph("Daily Consumed Amount",font1));
	        table.addCell(new Paragraph("Cum. kWh",font1));
	        table.addCell(new Paragraph("Cum.kWh(DG)",font1));
	        table.addCell(new Paragraph("Cum.FixChrg",font1));
	        table.addCell(new Paragraph("Cum.FixChrg(DG)",font1));
	      
	        int i=0;
			/*Date date=null;
			int month=0;
			int year=0;
			String postType="";
			String ledgerType="";*/
	        //XSSFCell cell = null;
	    	for (Object[] consumptionHtry :dailyData) {
	    		

	    		
	        PdfPCell cell1 = new PdfPCell(new Paragraph((String)consumptionHtry[1],font3));
	        PdfPCell cell2 = new PdfPCell(new Paragraph((String)consumptionHtry[2],font3));
	        PdfPCell cell3 = new PdfPCell(new Paragraph((String)consumptionHtry[3],font3));
	        PdfPCell cell4 = new PdfPCell(new Paragraph((String)consumptionHtry[4],font3));
	        PdfPCell cell5 = new PdfPCell(new Paragraph((String)consumptionHtry[8],font3));
	        PdfPCell cell6 = new PdfPCell(new Paragraph((String)consumptionHtry[9],font3));
	        PdfPCell cell7 = new PdfPCell(new Paragraph((String)consumptionHtry[5],font3));
	        PdfPCell cell8 = new PdfPCell(new Paragraph((String)consumptionHtry[6],font3));
	        table.addCell(cell1);
	        table.addCell(cell2);
	        table.addCell(cell3);
	        table.addCell(cell4);
	        table.addCell(cell5);
	        table.addCell(cell6);
	        table.addCell(cell7);
	        table.addCell(cell8);
	        
	        table.setWidthPercentage(100);
	        float[] columnWidths = {14f, 14f, 10f, 19f, 12f, 15f, 15f,18f};

	        table.setWidths(columnWidths);
	    		
			}
	        
	         document.add(table);
	        document.close();

	    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
	    	baos.writeTo(fileOut);
	    	fileOut.flush();
	    	fileOut.close();
	        
	        ServletOutputStream servletOutputStream;

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline;filename=\"Consumption History.pdf"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();
		return null;
	}
	
	
	//@RequestMapping(value = "/bill/getbilltablePDF", method = {
		//	RequestMethod.POST, RequestMethod.GET })
/*	public void viewBillPDF(HttpServletResponse res, Locale locale) {
		logger.info("in side viewBillPDF");
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("previousBal",1000.00);
		param.put("rechargeAmt",500.00);
		param.put("closingBal",1500.00);
		param.put("consumptionAmt",200.00);
		param.put( "title",ResourceBundle.getBundle("utils").getString("report.title")); 
		param.put("companyAddress",ResourceBundle.getBundle("utils").getString("report.address"));
   	    param.put("point1",ResourceBundle.getBundle("utils").getString("report.point1"));
   	    param.put("point2",ResourceBundle.getBundle("utils").getString("report.point2"));
		param.put("point3.1", ResourceBundle.getBundle("utils").getString("report.point3.1"));
		param.put("point3.2",ResourceBundle.getBundle("utils").getString("report.point3.2"));
		param.put("point3.3", ResourceBundle.getBundle("utils").getString("report.point3.3"));
		param.put("point4",ResourceBundle.getBundle("utils").getString("report.point4"));
		param.put("note",ResourceBundle.getBundle("utils").getString("report.note")); 
		param.put("realPath", "reports/");
		param.put("address", "Bcits");
		param.put("city", "Bengalore");
		param.put("mainUnit",1.2);
		param.put("dgUnit", 1.00);
		param.put("elRate", 7.3);
		
		param.put("name", "vema");
		param.put("propertyNo", "SA-C-01-01");
		param.put("dgRate", 18.3);
		param.put("mainAmt",100.00);
		param.put("dgAmt", 50.00);
		param.put("totalAmt", 150.00);
	
		param.put("billingPeriod",DateFormatUtils.format((new Date()),"dd MMM. yyyy")	+ " To "+ DateFormatUtils.format((new Date()),"dd MMM. yyyy"));
		
		
		param.put("serviceName", "CAM");
		param.put("chargeName", "CAM Rate");
		param.put("rate",3.5);
		param.put("cbName", "AREA");
		param.put("calculatedAmt",100.00);
		param.put("totalAmt1",200.00);
		
		//param.put("grossconsumption", 300);
		param.put("area", 12);
		param.put("billNo", "BL1200");
		param.put("billDate",new Date() );
		param.put("mtrNo", "6000123");
		
		param.put("secondaryAddress", "Grand Arch ,Sector-58");
		JRBeanCollectionDataSource jre = new JRBeanCollectionDataSource(bliList);
		JREmptyDataSource jre = new JREmptyDataSource();
		JasperPrint jasperPrint;
		JasperReport report;
		try {
			logger.info("---------- filling the report -----------");
		
				jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/Bill.jasper"),param, jre);
			
			removeBlankPage(jasperPrint.getPages());
			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
			InputStream myInputStream = new ByteArrayInputStream(bytes);
			Blob blob = Hibernate.createBlob(myInputStream);
			BillDocument billDocument = new BillDocument(electricityBillEntity, blob);
			billDocumentService.save(billDocument);
			try {
				logger.info("in side try block1");
				if (blob != null) {
					res.setHeader("Content-Disposition", "inline; filename="
							+"CAM" + "_"
							+"SA-C-01-01" + ".pdf");
					OutputStream out = res.getOutputStream();
					res.setContentType("application/pdf");
					IOUtils.copy(blob.getBinaryStream(), out);
					out.flush();
					out.close();
				} else {
					logger.info("::::::::::::: else block");
					OutputStream out = res.getOutputStream();
					IOUtils.write("File Not Found", out);
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("----------------- IOException");
		}
	}
	*/
	private void removeBlankPage(List<JRPrintPage> pages) {
		logger.info("in sdie removeblank");
		for (Iterator<JRPrintPage> i = pages.iterator(); i.hasNext();) {
			JRPrintPage page = i.next();
			if (page.getElements().size() == 4)
				i.remove();
		}
	}
	
	@RequestMapping(value = "/prepaidBill/getPropertyNo", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<?> getPropertyIds(HttpServletRequest req) {
		int blockId = Integer.parseInt(req.getParameter("blockId"));

		return prepaidMeterService.getAllProp(blockId);
	}
	
	
	@RequestMapping(value = "/prepaidDataprop/getPropertyNo", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<?> getPropertyIdsforBill(HttpServletRequest req) {
		int blockId = Integer.parseInt(req.getParameter("blockId"));

		return prepaidMeterService.getPropOnlyforChargesCalcu(blockId);
	}
	
	@RequestMapping(value = "/prepaidBillingModule/getPropertyNo", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<?> getPropertyIdsforMailBill(HttpServletRequest req) {
		int blockId = Integer.parseInt(req.getParameter("blockId"));

		return prepaidMeterService.getPropForMailBill(blockId);
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="/prepaid/generateBillNew",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> generatePrepaidBill(HttpServletRequest req,HttpServletResponse res, Locale locale) throws Exception{
		String alert="";
		int blocId		  =	Integer.parseInt(req.getParameter("blocId"));
		String blockName  =	req.getParameter("blockName");
		Date presentdate  = new SimpleDateFormat("MMMM yyyy").parse(req.getParameter("presentdate"));
		String propertyId =	req.getParameter("propertyId");

		String tower =null;
		PrintWriter out=res.getWriter();
		List<Map<String, Object>> reList=new ArrayList<>();
		Map<String, Object> mapList=null;
		logger.info("blockId="+blocId+"\nblockName="+blockName+"\npresentdate="+presentdate+"\npropertyId="+propertyId);

		if(blockName!=null || blockName!=""){
			tower = blockName.substring(blockName.length() - 1);
			logger.info("tower ========= " + tower);
			String comma = ",";
			int count=0;
			String[] trancode = propertyId.split(comma);
			for (int i = 0; i < trancode.length; i++) 
			{
				int propid = Integer.parseInt(trancode[i]);
				String mtrNo=prepaidMeterService.getMtrNo(propid);
				String propertyNo=prepaidMeterService.getPropertyNo(propid);
				long consumpCount=prePaidInstantDataService.checkConsumptionAvailability(mtrNo,presentdate);
				long dataCount=prepaidBillService.getCount(propid, presentdate);
				if(dataCount>0){
					out.write("Bill Already Generated This Month for Property "+propertyNo+"/n ");
				}else
				{
					/*.........................new codes for bill generation.........................*/
					/*..................rates applicable after november....................*/
					String mainrate 	   = "6.67";
					String dgrate   	   = "19.62";
					String RECHARGE        = "";
					String MAIN_UNITS 	   = "";
					String MAIN_CONSMP_AMT = "";
					String DG_UNITS  	   = "";
					String DG_CONSMP_AMT   = "";
					String TOTAL_AMT       = "";
					
					String meterNo=prepaidMeterService.getMtrNo(propid);

					Calendar cal=Calendar.getInstance();
					cal.setTime(presentdate);
					int month = cal.get(Calendar.MONTH);
					int m1    = month+1;
					int year  = cal.get(Calendar.YEAR);
					//String fromDate = cal.getMinimalDaysInFirstWeek()+"/"+m1+"/"+year;
					//String toDate   = cal.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+m1+"/"+year;
					try {
						String MinMaxDate = 
								"SELECT MIN(READING_DATE_TIME),MAX(READING_DATE_TIME) FROM PREPAID_DAILY_DATA WHERE METER_SR_NO='"+meterNo+"' AND "
										+ "EXTRACT(month FROM TO_DATE(READING_DATE_TIME,'dd/MM/yyyy'))="+m1+" AND "
										+ "EXTRACT(year FROM TO_DATE(READING_DATE_TIME,'dd/MM/yyyy'))="+year;
						logger.info("MinMaxDate="+MinMaxDate);
						Object[] minmax = (Object[]) entityManager.createNativeQuery(MinMaxDate).getSingleResult();
						logger.info("Mindate="+minmax[0]+"  & MaxDate="+minmax[1]);

						String consmp="SELECT "
								+ "  X.RECHARGE,"
								+ "  X.MAIN_UNITS,"
								+ " (X.MAIN_UNITS*"+mainrate+")AS MAIN_CONSMP_AMT,"
								+ "  X.DG_UNITS,"
								+ " (X.DG_UNITS*"+dgrate+")AS DG_CONSMP_AMT,"
								+ "((X.MAIN_UNITS*"+mainrate+")+(X.DG_UNITS*"+dgrate+"))AS TOTAL_AMT " +
								"FROM( " +
								"SELECT (B.CUSTOMER_RECHRGE_AMOUNT-A.CUSTOMER_RECHRGE_AMOUNT)AS RECHARGE,(B.CUM_KWH_MAIN-A.CUM_KWH_MAIN)AS MAIN_UNITS,(B.CUM_KWH_DG-A.CUM_KWH_DG)AS DG_UNITS FROM " +
								"(SELECT CUSTOMER_RECHRGE_AMOUNT,CUM_KWH_MAIN,CUM_KWH_DG FROM PREPAID_DAILY_DATA WHERE To_date(READING_DATE_TIME,'dd/MM/yyyy')=To_date('"+minmax[0].toString()+"','dd/MM/yyyy')AND METER_SR_NO='"+meterNo+"')A, " +
								"(SELECT CUSTOMER_RECHRGE_AMOUNT,CUM_KWH_MAIN,CUM_KWH_DG FROM PREPAID_DAILY_DATA WHERE To_date(READING_DATE_TIME,'dd/MM/yyyy')=To_date('"+minmax[1].toString()+"','dd/MM/yyyy')AND METER_SR_NO='"+meterNo+"')B " +
								")X";
						System.err.println("consmp="+consmp);
						logger.info("ConsmpQuery="+consmp);

						logger.info("getting data for meter number : "+meterNo);
						List<?> consmpmeter=entityManager.createNativeQuery(consmp).getResultList();
						for (Iterator iterator = consmpmeter.iterator(); iterator.hasNext();){
							Object[] value  = (Object[]) iterator.next();
							RECHARGE        = value[0].toString();
							MAIN_UNITS 	    = value[1].toString();
							MAIN_CONSMP_AMT = value[2].toString();
							DG_UNITS  	    = value[3].toString();
							DG_CONSMP_AMT   = value[4].toString();
							TOTAL_AMT       = value[5].toString();
							System.err.println("RECHARGE="+RECHARGE+"\nMAIN_UNITS="+MAIN_UNITS+"\nMAIN_CONSMP_AMT="+MAIN_CONSMP_AMT+"\nDG_UNITS="+DG_UNITS+"\nDG_CONSMP_AMT="+DG_CONSMP_AMT+"\nTOTAL_AMT="+TOTAL_AMT);
							logger.info("RECHARGE="+RECHARGE+"\nMAIN_UNITS="+MAIN_UNITS+"\nMAIN_CONSMP_AMT="+MAIN_CONSMP_AMT+"\nDG_UNITS="+DG_UNITS+"\nDG_CONSMP_AMT="+DG_CONSMP_AMT+"\nTOTAL_AMT="+TOTAL_AMT);
						}

						HashMap<String, Object> param = new HashMap<String, Object>();
						int  area=(int) entityManager.createQuery("select p.area from Property p where p.propertyId="+propid).getSingleResult();
						Person person=personService.find(prepaidMeterService.getPersonId(propid));
						Set<Address> address=person.getAddresses();
						String custaddress=null;
						for (Address address2 : address) {
							String d1=address2.getAddress1();
							String d2="";
							if(address2.getAddress2()!=null){
								d2=address2.getAddress2();
							}
							custaddress=d1+","+d2;
						}
						String CustomerName=person.getFirstName()+""+person.getLastName();
						String billNo=genrateAdjustmentNumber();
						double dgunits=0.0;
						double allSum=0.0;
						double ebunits=0.0;
						/*---------------------------------------*/
						param.put("trf1","Electricity Charge");
						param.put("ellrate1",mainrate);
						param.put("unit1",MAIN_UNITS);
						param.put("ellamt1",MAIN_CONSMP_AMT);
						/*---------------------------------------*/
						param.put("trf2","DG Charges");
						param.put("ellrate2",dgrate);
						param.put("unit2",DG_UNITS);
						param.put("ellamt2",DG_CONSMP_AMT);
						/*---------------------------------------*/
						param.put("fulltotal",TOTAL_AMT);
						param.put("consumptionAmt",Math.round(Double.parseDouble(TOTAL_AMT)));
						param.put("rechargeAmt",RECHARGE);
						/*---------------------------------------*/
						param.put("grossConsumptionAmt", Math.round(prepaidCalcuStoreService.getSumofAmt(presentdate, propid)));
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
						param.put("name", CustomerName);
						param.put("propertyNo",propertyNo);
						param.put("address",propertyNo +","+blockName);
						param.put("city", "Gurugram");
						param.put("secondaryAddress", custaddress);
						param.put("area", area);
						param.put("sumAmt", Math.round(Double.parseDouble(TOTAL_AMT)));
						param.put("billNo", billNo);

						Calendar start = Calendar.getInstance();
						start.setTime(presentdate);
						start.add(Calendar.MONTH, 1);

						param.put("billDate",new SimpleDateFormat("dd-MMM-yyyy").format(start.getTime()));
						param.put("mtrNo", meterNo);
						param.put("billingPeriod",DateFormatUtils.format((new SimpleDateFormat("dd/MM/yyyy").parse(minmax[0].toString())),"dd MMM. yyyy")	
								+ " To "+ DateFormatUtils.format((new SimpleDateFormat("dd/MM/yyyy").parse(minmax[1].toString())),"dd MMM. yyyy"));

						Integer numToWord= (int) Math.round(Double.parseDouble(TOTAL_AMT));
						String amountInWords = NumberToWord.convertNumberToWords(numToWord.intValue());
						param.put("amountInWords", amountInWords + " Only");

						PrepaidBillDetails billDetails=new PrepaidBillDetails();
						billDetails.setBill_Amt(Math.round(Double.parseDouble(TOTAL_AMT)));
						billDetails.setBill_Month(presentdate);
						billDetails.setBillNo(billNo);
						billDetails.setPropertyId(propid);
						billDetails.setGrossConsumptionAmt(Math.round(Double.parseDouble(TOTAL_AMT)));
						billDetails.setEbReading(Double.parseDouble(MAIN_UNITS));
						billDetails.setDgReading(Double.parseDouble(DG_UNITS));
						billDetails.setRechargedAmt(Double.parseDouble(RECHARGE));
						billDetails.setCamCharges(allSum);
						prepaidBillService.save(billDetails);

						JREmptyDataSource jre = new JREmptyDataSource();
						JasperPrint jasperPrint;
						JasperReport report;
						try {
							logger.info("---------- filling the report -----------");

							jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/Bill.jasper"),param, jre);

							removeBlankPage(jasperPrint.getPages());
							byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
							InputStream myInputStream = new ByteArrayInputStream(bytes);
							Blob blob = Hibernate.createBlob(myInputStream);
							PrepaidBillDocument billDocument = new PrepaidBillDocument(propid, presentdate, billNo, blob);
							prepaidBillDocService.save(billDocument);

						} catch (JRException e) {
							e.printStackTrace();
						} catch (IOException e) {
							logger.error("----------------- IOException");
						}
						count++;
					} 
					catch (Exception e) {
						logger.info("Exceptin while generating bill for meter number "+meterNo);
						out.write("Exceptin while generating bill for meter number "+meterNo+"\n");
						e.printStackTrace();
					}
				}
			}
			if(count!=0){
				out=res.getWriter();
				out.write(count+"Bill generated Succefully"+ "\n" + alert);
			}
		}
		return null; 
	}
	
	/*
	@SuppressWarnings("unused")
	@RequestMapping(value="/prepaid/generateBillNew",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> generatePrepaidBill(HttpServletRequest req,HttpServletResponse res, Locale locale) throws Exception{
		String alert="";
		int blocId		  =	Integer.parseInt(req.getParameter("blocId"));
		String blockName  =	req.getParameter("blockName");
		Date presentdate  = new SimpleDateFormat("MMMM yyyy").parse(req.getParameter("presentdate"));
		String propertyId =	req.getParameter("propertyId");
		
		String tower =null;
		PrintWriter out;
		List<Map<String, Object>> reList=new ArrayList<>();
		Map<String, Object> mapList=null;
		logger.info("::::::blockId::"+null+":::::::::blockName:::"+blockName+":::::::::::presentdate::::"+presentdate+"::::::propertyId::::"+propertyId);
		           if(blockName!=null || blockName!=""){
					tower = blockName.substring(blockName.length() - 1);
					logger.info("tower ========= " + tower);
					String comma = ",";
					int count=0;
					String[] trancode = propertyId.split(comma);
					for (int i = 0; i < trancode.length; i++) 
					{
						int propid = Integer.parseInt(trancode[i]);
						String mtrNo=prepaidMeterService.getMtrNo(propid);
						String propertyNo=prepaidMeterService.getPropertyNo(propid);
						long consumpCount=prePaidInstantDataService.checkConsumptionAvailability(mtrNo,presentdate);
						 if(consumpCount>=28){}
					    long dataCount=prepaidBillService.getCount(propid, presentdate);
					    if(dataCount>0){
					    	out=res.getWriter();
							out.write("Bill Already Generated This Month for Property "+propertyNo+"/n ");
					    }else
					    {
					    	Object[] prePaidMeters = prepaidMeterService.getserviceStartDate(propid);
					    	HashMap<String, Object> param = new HashMap<String, Object>();
					    	int  area=(int) entityManager.createQuery("select p.area from Property p where p.propertyId="+propid).getSingleResult();
					    	Person person=personService.find(prepaidMeterService.getPersonId(propid));
					    	Set<Address> address=person.getAddresses();
					    	String custaddress=null;
					    	for (Address address2 : address) {
					    		String d1=address2.getAddress1();
					    		String d2="";
					    		if(address2.getAddress2()!=null){
					    			d2=address2.getAddress2();
					    		}
					    		custaddress=d1+","+d2;
					    	}
					    	String CustomerName=person.getFirstName()+""+person.getLastName();
					    	String meterNo=prepaidMeterService.getMtrNo(propid);
					    	String billNo=genrateAdjustmentNumber();
					    	double dgunits=0.0;
					    	double allSum=0.0;
					    	double ebunits=0.0;
					    	List<?> resultList=prepaidCalcuStoreService.getSumofAllCharges(presentdate,propid);
					    	
					    	
					    	double fulltotal = 0.0;
					    	if(!(resultList.isEmpty())){
					    		for(Iterator<?> iterator=resultList.iterator();iterator.hasNext();){
					    			final Object[] values=(Object[]) iterator.next();
					    			//System.err.println("*********************START********");
					    			// Double.parseDouble(values[8]+"")+Double.parseDouble(values[8])
					    			double dgAmt=0.0;
					    			double mainAMT=0.0;


					    			
					    			if(values[3].equals("DG")){
					    				param.put("dgUnit", Double.parseDouble(values[7]+""));
					    				dgunits=Double.parseDouble(values[7]+"");
					    				param.put("dgRate", Double.parseDouble(values[5]+""));
					    				param.put("dgAmt", Double.parseDouble(values[8]+""));
					    				if(values[8]!=null){
					    					dgAmt=Double.parseDouble(values[8]+"");
					    				}
					    				
		*//*********************************************************************************************************//*
					    				if(values[4].equals("DG Charges")){
					    					param.put("trf1", values[4]+"");
					    					param.put("ellrate1",values[5]+"");
					    					param.put("unit1",values[7]+"" );
					    					param.put("ellamt1", values[8]+"");
					    					fulltotal = fulltotal + Double.parseDouble(values[8]+"");
					    				}
					    				else if(values[4].equals("DG Charge")){
					    					param.put("trf2", values[4]+"");
					    					param.put("ellrate2",values[5]+"");
					    					param.put("unit2",values[7]+"" );
					    					param.put("ellamt2", values[8]+"");
					    					fulltotal = fulltotal + Double.parseDouble(values[8]+"");
					    				}
		*//*********************************************************************************************************//*

					    			}
					    			else if(values[3].equals("CAM")){
					    				if(values[4].equals("CAM RATE")){
					    					param.put("serviceName", values[3]+"");
					    					param.put("chargeName",values[4]+"" );
					    					param.put("rate",Double.parseDouble(values[5]+""));
					    					param.put("cbName", values[6]+""+"(PSF)");
					    					//param.put("calculatedAmt",Double.parseDouble(values[8]+""));
					    					param.put("totalAmt1",Double.parseDouble(values[8]+""));

					    				}else if(values[4].equals("Service Tax")){
					    					param.put("serviceName1", values[3]+"");
					    					param.put("chargeName1",values[4]+"" );
					    					param.put("rate1",Double.parseDouble(values[5]+""));
					    					param.put("cbName1", "%");
					    					//param.put("calculatedAmt1",Double.parseDouble(values[8]+""));
					    					param.put("totalAmt2",Double.parseDouble(values[8]+""));


					    				}else if(values[4].equals("Swach Bharat Cess")){
					    					param.put("serviceName2", values[3]+"");
					    					param.put("chargeName2",values[4]+"" );
					    					param.put("rate2",Double.parseDouble(values[5]+""));
					    					param.put("cbName2", "%");
					    					//param.put("calculatedAmt2",Double.parseDouble(values[8]+""));
					    					param.put("totalAmt3",Double.parseDouble(values[8]+""));

					    				}else if(values[4].equals("Krishi Kalyan Cess")){
					    					param.put("serviceName3", values[3]+"");
					    					param.put("chargeName3",values[4]+"" );
					    					param.put("rate3",Double.parseDouble(values[5]+""));
					    					param.put("cbName3", "%");
					    					//param.put("calculatedAmt3",Double.parseDouble(values[8]+""));
					    					param.put("totalAmt4",Double.parseDouble(values[8]+""));

					    				}
					    				allSum+=Double.parseDouble(values[8]+"");
					    				//	System.err.println(camamt1+camamt2+camamt3+camamt4);
					    			}
					    			else{
					    				param.put("mainUnit",Double.parseDouble(values[7]+""));
					    				ebunits=Double.parseDouble(values[7]+"");
					    				param.put("elRate", Double.parseDouble(values[5]+""));
					    				param.put("mainAmt",Double.parseDouble(values[8]+""));
					    				if(values[8]!=null){
					    					mainAMT=Double.parseDouble(values[8]+"");
					    				}
					    				
		*//*********************************************************************************************************//*
					    				if(values[4].equals("Electricity Charge")){
					    					param.put("trf3", values[4]+"");
					    					param.put("ellrate3",values[5]+"");
					    					param.put("unit3",values[7]+"" );
					    					param.put("ellamt3", values[8]+"");
					    					fulltotal = fulltotal + Double.parseDouble(values[8]+"");
					    				}
					    				else if(values[4].equals("Electricity Charges")){
					    					param.put("trf4", values[4]+"");
					    					param.put("ellrate4",values[5]+"");
					    					param.put("unit4",values[7]+"" );
					    					param.put("ellamt4", values[8]+"");
					    					fulltotal = fulltotal + Double.parseDouble(values[8]+"");
					    				}
		*//*********************************************************************************************************//*
					    			}
					    			System.err.println("totalAmt="+dgAmt+mainAMT+"(dgAmt="+dgAmt+"+ mainAMT="+mainAMT+")");
					    			param.put("totalAmt",dgAmt+mainAMT);

					    		}
					    		param.put("fulltotal",fulltotal+"");
					    		
					    		//System.out.println("listSize "+list1.size()+"**********"+allSum);
					    		param.put("grossConsumptionAmt", Math.round(prepaidCalcuStoreService.getSumofAmt(presentdate, propid)));
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
					    		param.put("name", CustomerName);
					    		param.put("propertyNo",propertyNo);
					    		param.put("address",propertyNo +","+blockName);
					    		param.put("city", "Gurugram");
					    		param.put("secondaryAddress", custaddress);
					    		param.put("area", area);
					    		param.put("sumAmt", Math.round(allSum));
					    		param.put("billNo", billNo);
					    		Calendar start = Calendar.getInstance();
					    		start.setTime(presentdate);
					    		start.add(Calendar.MONTH, 1);
					    		param.put("billDate",new SimpleDateFormat("dd-MMM-yyyy").format(start.getTime()));
					    		List<Object[]> list=prePaidInstantDataService.getMinMaxDate(presentdate, meterNo);
						Date fDate=null;
						Date tDate=null;
						if(!(list.isEmpty())){
						for (Object[] objects : list) {
							fDate=new SimpleDateFormat("dd/MM/yyyy").parse(objects[0]+"");
							tDate=new SimpleDateFormat("dd/MM/yyyy").parse(objects[1]+"");
						}
						}

					    		param.put("mtrNo", meterNo);

					    		List<Object[]> objects=prepaidCalcuStoreService.getMinMaxDate(presentdate,meterNo,propid);
					    		Date minDate=null;
					    		Date maxDate=null;
					    		if(!(objects.isEmpty())){
					    			for (Object[] objects2 : objects) {
					    				minDate=new SimpleDateFormat("yyyy-MM-dd").parse(objects2[0]+"");
					    				maxDate=new SimpleDateFormat("yyyy-MM-dd").parse(objects2[1]+"");
					    			}

					    		}
					    		param.put("billingPeriod",DateFormatUtils.format((minDate),"dd MMM. yyyy")	+ " To "+ DateFormatUtils.format((maxDate),"dd MMM. yyyy"));
					    		String preBal=prePaidInstantDataService.getMinBalance(minDate, meterNo);
					    		//String preBal=prePaidInstantDataService.getMaxMinBalance(presentdate, meterNo);
					    		System.err.println("prepBal "+preBal);
					    		String prevoiusBal="";
					    		String closingBal="";
					    		if(preBal!=null){
					    			param.put("previousBal",Double.parseDouble(preBal));
					    			prevoiusBal=preBal;

					    		}else{
					    			param.put("previousBal",Double.parseDouble(prePaidMeters[3]+""));
					    			prevoiusBal=prePaidMeters[3]+"";
					    		}
					    		String clBal=prePaidInstantDataService.getMaxBalance(maxDate, meterNo);
					    		if(clBal!=null){

					    			param.put("closingBal",Double.parseDouble(clBal));
					    			closingBal=clBal;


					    		}
		======================================================================================================================			    		
					 		   String frooom = minDate.toString();
							   SimpleDateFormat sdf=new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
							   Date from = (Date)sdf.parse(frooom);
							   Calendar cal1 = Calendar.getInstance();
							   cal1.setTime(from);
							   String formateddate1=cal1.get(Calendar.DATE)+"/"+(cal1.get(Calendar.MONTH) + 1)+"/"+cal1.get(Calendar.YEAR);
							   System.err.println("from : " + formateddate1);
							   
					 		   String to=maxDate.toString();
							   Date toooooo = (Date)sdf.parse(to);
							   Calendar cal2 = Calendar.getInstance();
							   cal2.setTime(toooooo);
							   String formateddate2=cal2.get(Calendar.DATE)+"/"+(cal2.get(Calendar.MONTH) + 1)+"/"+cal2.get(Calendar.YEAR);
							   System.err.println("to : " + formateddate2);
					    		
					    		String recharge="SELECT X.RECHARGE FROM( "
								+ "SELECT (B.CUM_RECHARGE_AMT-A.CUM_RECHARGE_AMT)AS RECHARGE FROM "
								+ "(SELECT CUM_RECHARGE_AMT FROM PREPAID_DAILY_DATA WHERE To_date(READING_DATE_TIME,'dd/MM/yyyy')= "
								+ "To_date('"+formateddate1+"','dd/MM/yyyy')AND METER_SR_NO='"+meterNo+"')A, "
								+ "(SELECT CUM_RECHARGE_AMT FROM PREPAID_DAILY_DATA WHERE To_date(READING_DATE_TIME,'dd/MM/yyyy')= "
								+ "To_date('"+formateddate2+"','dd/MM/yyyy')AND METER_SR_NO='"+meterNo+"')B)X ";
					    		String rechargeamt = "";
					    		BigDecimal bigamt=null;
					    		System.err.println("recharge="+recharge);
					    		try {
					    			bigamt = (BigDecimal) entityManager.createNativeQuery(recharge).getSingleResult();
					    			rechargeamt=bigamt.doubleValue()+"";
					    			System.err.println("try Block------------->rechargeamt="+rechargeamt);
								} catch (Exception e) {
									System.err.println("catch Block------------->rechargeamt="+rechargeamt);
									rechargeamt="0";
									alert="Issue came while taking recharge amount for "+meterNo;
									e.printStackTrace();
								}
			======================================================================================================================			    		
					    		
					    		//String rechargeamt=prePaidInstantDataService.getRechargeAmt(presentdate, meterNo);
					    		double rechargeAMT=0.0;
					    		if(rechargeamt!=null || rechargeamt!=""){
					    			rechargeAMT=Double.parseDouble(rechargeamt);
					    			param.put("rechargeAmt",rechargeamt);
					    		}
					    		Integer numToWord= (int) Math.round(prepaidCalcuStoreService.getSumofAmt(presentdate, propid));
					    		String amountInWords = NumberToWord.convertNumberToWords(numToWord.intValue());
					    		param.put("amountInWords", amountInWords + " Only");
					    		long bal=0;
					    		if((clBal!=null && preBal!=null)||(prevoiusBal!="" && closingBal!="")){
					    			bal=Math.round((Double.parseDouble(prevoiusBal)+rechargeAMT)-Math.round(Double.parseDouble(closingBal)));
					    			//param.put("consumptionAmt",bal);
					    			param.put("consumptionAmt",fulltotal);
					    		}

					    		PrepaidBillDetails billDetails=new PrepaidBillDetails();
					    		billDetails.setBill_Amt(bal);
					    		billDetails.setBill_Month(presentdate);
					    		billDetails.setBillNo(billNo);
					    		billDetails.setPropertyId(propid);
					    		if(prevoiusBal!=""){
					    			billDetails.setPreviousBal(Double.parseDouble(prevoiusBal));
					    		}else{
					    			billDetails.setPreviousBal(0.0);
					    		}
					    		if(closingBal!=""){
					    			billDetails.setClosingBal(Double.parseDouble(closingBal));
					    		}else{
					    			billDetails.setClosingBal(0.0);
					    		}
					    		billDetails.setGrossConsumptionAmt(Math.round(prepaidCalcuStoreService.getSumofAmt(presentdate, propid)));
					    		billDetails.setEbReading(ebunits);
					    		billDetails.setDgReading(dgunits);
					    		billDetails.setRechargedAmt(rechargeAMT);
					    		billDetails.setCamCharges(allSum);
					    		//billDetails.setEbReading(ebReading);
					    		prepaidBillService.save(billDetails);

					    			JRBeanCollectionDataSource jre = new JRBeanCollectionDataSource(bliList);
					    		JREmptyDataSource jre = new JREmptyDataSource();
					    		JasperPrint jasperPrint;
					    		JasperReport report;
					    		try {
					    			logger.info("---------- filling the report -----------");

					    			jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/Bill.jasper"),param, jre);

					    			removeBlankPage(jasperPrint.getPages());
					    			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
					    			InputStream myInputStream = new ByteArrayInputStream(bytes);
					    			Blob blob = Hibernate.createBlob(myInputStream);
					    			PrepaidBillDocument billDocument = new PrepaidBillDocument(propid, presentdate, billNo, blob);
					    			prepaidBillDocService.save(billDocument);

					    		} catch (JRException e) {
					    			e.printStackTrace();
					    		} catch (IOException e) {
					    			logger.error("----------------- IOException");
					    		}
					    		count++;
					    	}else{
					    		out=res.getWriter();
					    		out.write("No Data Found for Property "+propertyNo+"/n ");
					    	}
					    }
						 else{
							 out=res.getWriter();
							 out.write("Bill could not be generated of property  "+propertyNo+ ",because Consumption data is  available only for "+consumpCount+" "+"Days !"+"\n");
							 
						 }
					}
					if(count!=0){
					out=res.getWriter();
					out.write(count+"Bill generated Succefully"+"\n "+alert);
					}
		           }
	return null; 
	}
	*/
	 public String genrateAdjustmentNumber() throws Exception {  
			/*Random generator = new Random();  
			generator.setSeed(System.currentTimeMillis());  
			   
			int num = generator.nextInt(99999) + 99999;  
			if (num < 100000 || num > 999999) {  
			num = generator.nextInt(99999) + 99999;  
			if (num < 100000 || num > 999999) {  
			throw new Exception("Unable to generate PIN at this time..");  
			}  
			}  
			return "AD"+num;*/ 
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String year = sdf.format(new Date());
			int nextSeqVal = adjustmentService.autoGeneratedAdjustmentNumber();	 
			
			return "BILL"+nextSeqVal;		   
		}
	 
	 @RequestMapping(value="/prepaidBillGen")
		public String index1(@ModelAttribute("prepaidBillDetails") PrepaidBillDetails prepaidBillDetails){
		 logger.info("in side index2");
			return "prepaidBill";
		} 
	 
	 @RequestMapping(value="/prpaidBill/prePaidELBillReadUrl",method={RequestMethod.GET,RequestMethod.POST})
	 public @ResponseBody List<?> readAllData(){
		 List<PrepaidBillDetails> details=prepaidBillService.getData();
		 List<Map<String, Object>> resultList=new ArrayList<>();
			Map<String, Object> maplist=null;
			if(details!=null){
			for(PrepaidBillDetails prepaidBillDetails:details){
				maplist=new HashMap<>();
				maplist.put("preBillId", prepaidBillDetails.getPreBillId());
				maplist.put("mtrNo", prepaidMeterService.getMtrNo(prepaidBillDetails.getPropertyId()));
				Person person=personService.find(prepaidMeterService.getPersonId(prepaidBillDetails.getPropertyId()));
				String CustomerName=person.getFirstName()+""+person.getLastName();
				maplist.put("personName", CustomerName);
				maplist.put("propertyNo", prepaidMeterService.getPropertyNo(prepaidBillDetails.getPropertyId()));
				String date=new SimpleDateFormat("MMM yyyy").format(prepaidBillDetails.getBill_Month());
				maplist.put("billMonth", date);
				maplist.put("netAmount", prepaidBillDetails.getBill_Amt());
				maplist.put("billNo", prepaidBillDetails.getBillNo());
				maplist.put("previousBal", prepaidBillDetails.getPreviousBal());
				maplist.put("closingBal", prepaidBillDetails.getClosingBal());
				maplist.put("mailStatus", prepaidBillDetails.getMailStatus());
				maplist.put("grossAmount", prepaidBillDetails.getGrossConsumptionAmt());
				resultList.add(maplist);
			}
			}
			return resultList;
	 }
	 
	 @RequestMapping(value="/prpaidBill/prePaiddestroyUrl",method={RequestMethod.GET,RequestMethod.POST})
	 public @ResponseBody Object deleteBillData(HttpServletRequest request){
		 logger.info("in side Bill Delete Methode "+request.getParameter("preBillId"));
		 int preBillId=Integer.parseInt(request.getParameter("preBillId"));
		 PrepaidBillDetails billDetails=prepaidBillService.find(preBillId);
		 prepaidBillService.delete(preBillId);
		 return billDetails;
	 }
	 
	 @RequestMapping(value = "/prepaidBill/getbilltablePDF/{preBillId}", method = {
				RequestMethod.POST, RequestMethod.GET })
		public void viewBillPDF(@PathVariable int preBillId,
				HttpServletResponse res, Locale locale) {
			PrepaidBillDetails prepaidBillDetails = prepaidBillService.find(preBillId);
			//getBillDoc(electricityBillEntity.getElBillId(), locale);
			Blob blob = prepaidBillDocService.getBlog(prepaidBillDetails.getBillNo());
			Property property = propertyService.find(prepaidBillDetails.getPropertyId());
			try {
				if (blob != null) {
					res.setHeader("Content-Disposition", "inline; filename="
							+"PrepaidBill"+ "_"
							+ property.getProperty_No() + ".pdf");
					OutputStream out = res.getOutputStream();
					res.setContentType("application/pdf");
					IOUtils.copy(blob.getBinaryStream(), out);
					out.flush();
					out.close();
				} else {
					logger.info("::::::::::::: else block");
					OutputStream out = res.getOutputStream();
					IOUtils.write("File Not Found", out);
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	 
	 @RequestMapping(value = "/prepaid/generateManualBillNew", method = {
				RequestMethod.POST, RequestMethod.GET })
	 public @ResponseBody String generateManualBill(HttpServletRequest request,HttpServletResponse response) throws Exception{
		 //MANUALBILLING
		 String propertyId	 = request.getParameter("propertyId");
		 Date presentdate 	 = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fromDate"));
		 double ebreading	 = Double.parseDouble(request.getParameter("ebReading"));
		 double dgreading	 = Double.parseDouble(request.getParameter("dgReading"));
		 double camcharges	 = Double.parseDouble(request.getParameter("camCharges"));
		 double previousbal  = Double.parseDouble(request.getParameter("previousBal"));
		 double Closingbal	 = Double.parseDouble(request.getParameter("closingBal"));
		 double rechargeAmt  = Double.parseDouble(request.getParameter("rechargeamt"));
		 String rechargeAMT  = request.getParameter("rechargeamt");
		 int propid		 	 = Integer.parseInt(request.getParameter("propertyId"));
		 String fromDate  	 = request.getParameter("fromDate");
		 String toDate	     = request.getParameter("toDate");

		 System.err.println("propid="+propid+"\npresentdate="+presentdate+"\nebreading="+ebreading+"\ndgreading="+dgreading+
		     "\npreviousbal="+previousbal+"\nClosingbal="+Closingbal+"\nrechargeAmt="+rechargeAmt+"\nfromDate="+fromDate+"\ntoDate="+toDate);
		 
		 String mainrate  = "6.67";
		 String dgrate    = "19.62";
		 String mtrNo=prepaidMeterService.getMtrNo(propid);
		 String propertyNo=prepaidMeterService.getPropertyNo(propid);
		 String block = (String)entityManager.createNativeQuery("SELECT BLOCK_NAME FROM BLOCK WHERE BLOCK_ID=(SELECT BLOCK_ID FROM PROPERTY WHERE PROPERTY_ID='"+propid+"')").getSingleResult();
		 int  area=(int) entityManager.createQuery("select p.area from Property p where p.propertyId="+propid).getSingleResult();
			Person person=personService.find(prepaidMeterService.getPersonId(propid));
			Set<Address> address=person.getAddresses();
			String custaddress=null;
			for (Address address2 : address) {
				String d1=address2.getAddress1();
				String d2="";
				if(address2.getAddress2()!=null){
					d2=address2.getAddress2();
				}
				custaddress=d1+","+d2;
			}
		 String CustomerName=person.getFirstName()+""+person.getLastName();
		 String meterNo=prepaidMeterService.getMtrNo(propid);
		 String billNo=genrateAdjustmentNumber();
		 HashMap<String, Object> param = new HashMap<String, Object>();
		 
		 try {
			 /*---------------------------------------*/
			 param.put("trf1","Electricity Charge");
			 param.put("ellrate1",mainrate);
			 param.put("unit1",ebreading+"");
			 param.put("ellamt1",(Double.parseDouble(mainrate)*ebreading)+"");
			 /*---------------------------------------*/
			 param.put("trf2","DG Charges");
			 param.put("ellrate2",dgrate);
			 param.put("unit2",dgreading+"");
			 param.put("ellamt2",(Double.parseDouble(dgrate)*dgreading)+"");
			 /*---------------------------------------*/
			 param.put("fulltotal",(Double.parseDouble(mainrate)*ebreading + Double.parseDouble(dgrate)*dgreading)+"");
			 param.put("consumptionAmt",Math.round((Double.parseDouble(mainrate)*ebreading + Double.parseDouble(dgrate)*dgreading)));
			 param.put("rechargeAmt",rechargeAMT);
			 /*---------------------------------------*/
			 param.put("grossConsumptionAmt", Math.round(Double.parseDouble(mainrate)*ebreading + Double.parseDouble(dgrate)*dgreading));
			 param.put("title",ResourceBundle.getBundle("utils").getString("report.title")); 
			 param.put("companyAddress",ResourceBundle.getBundle("utils").getString("report.address"));
			 param.put("point1",ResourceBundle.getBundle("utils").getString("report.point1"));
			 param.put("point2",ResourceBundle.getBundle("utils").getString("report.point2"));
			 param.put("point3.1",ResourceBundle.getBundle("utils").getString("report.point3.1"));
			 param.put("point3.2",ResourceBundle.getBundle("utils").getString("report.point3.2"));
			 param.put("point3.3",ResourceBundle.getBundle("utils").getString("report.point3.3"));
			 param.put("point4",ResourceBundle.getBundle("utils").getString("report.point4"));
			 param.put("point5",ResourceBundle.getBundle("utils").getString("report.point5"));
			 param.put("note",ResourceBundle.getBundle("utils").getString("report.note")); 
			 param.put("realPath", "reports/");
			 param.put("name", CustomerName);
			 param.put("propertyNo",propertyNo);
			 param.put("address",propertyNo +","+block);
			 param.put("city", "Gurugram");
			 param.put("secondaryAddress", custaddress);
			 param.put("area", area);
			 param.put("sumAmt", Math.round(Double.parseDouble(mainrate)*ebreading + Double.parseDouble(dgrate)*dgreading));
			 param.put("billNo", billNo);

			 Calendar start = Calendar.getInstance();
			 start.setTime(presentdate);
			 start.add(Calendar.MONTH, 1);

			 param.put("billDate",new SimpleDateFormat("dd-MMM-yyyy").format(start.getTime()));
			 param.put("mtrNo", meterNo);
			 param.put("billingPeriod",DateFormatUtils.format((new SimpleDateFormat("dd/MM/yyyy").parse(fromDate)),"dd MMM. yyyy")	
					 + " To "+ DateFormatUtils.format((new SimpleDateFormat("dd/MM/yyyy").parse(toDate)),"dd MMM. yyyy"));

			 Integer numToWord= (int) Math.round(Double.parseDouble(mainrate)*ebreading + Double.parseDouble(dgrate)*dgreading);
			 String amountInWords = NumberToWord.convertNumberToWords(numToWord.intValue());
			 param.put("amountInWords", amountInWords + " Only");

			 PrepaidBillDetails billDetails=new PrepaidBillDetails();
			 billDetails.setBill_Amt(Math.round(Double.parseDouble(mainrate)*ebreading + Double.parseDouble(dgrate)*dgreading));
			 billDetails.setBill_Month(presentdate);
			 billDetails.setBillNo(billNo);
			 billDetails.setPropertyId(propid);
			 billDetails.setGrossConsumptionAmt(Math.round(Double.parseDouble(mainrate)*ebreading + Double.parseDouble(dgrate)*dgreading));
			 billDetails.setEbReading(ebreading);
			 billDetails.setDgReading(dgreading);
			 billDetails.setRechargedAmt(rechargeAmt);
			 billDetails.setCamCharges(0);
			 prepaidBillService.save(billDetails);

			 JREmptyDataSource jre = new JREmptyDataSource();
			 JasperPrint jasperPrint;
			 JasperReport report;
			 try {
				 logger.info("---------- filling the report -----------");

				 jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/Bill.jasper"),param, jre);

				 removeBlankPage(jasperPrint.getPages());
				 byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
				 InputStream myInputStream = new ByteArrayInputStream(bytes);
				 Blob blob = Hibernate.createBlob(myInputStream);
				 PrepaidBillDocument billDocument = new PrepaidBillDocument(propid, presentdate, billNo, blob);
				 prepaidBillDocService.save(billDocument);
				 return "Bill Generated Successfully!";
			 } catch (JRException e) {
				 e.printStackTrace();
				 return "JRE Exception";
			 } catch (IOException e) {
				 logger.error("----------------- IOException");
				 return "I O Exception";
			 }
		 }catch(Exception e) {
			e.printStackTrace();
			return "Issue Came While Generating Bill";
		 }
	 }
	 
	/* @RequestMapping(value = "/prepaid/generateManualBillNew", method = {
				RequestMethod.POST, RequestMethod.GET })
	 public @ResponseBody String generateManualBill(HttpServletRequest request,HttpServletResponse response) throws Exception{
		 
		 System.out.println("propid" +request.getParameter("propertyId"));
		 System.out.println("mtrno" +request.getParameter("meternumber"));
		 System.out.println("ebunits" +request.getParameter("ebReading"));
		 System.out.println("dgunits" +request.getParameter("dgReading"));
		 System.out.println("prevbal" +request.getParameter("previousBal"));
		 System.out.println("closingbal" +request.getParameter("closingBal"));
		 System.out.println("rechargeamt" +request.getParameter("rechargeamt"));
		 System.out.println("camcharges" +request.getParameter("camCharges"));
		 System.out.println("fromdate" +request.getParameter("fromDate"));
		 System.out.println("todate" +request.getParameter("toDate"));
		 
		 String propertyId =request.getParameter("propertyId");
		 Date presentdate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fromDate"));
		 
		 	double ebreading=Double.parseDouble(request.getParameter("ebReading"));
			double dgreading=Double.parseDouble(request.getParameter("dgReading"));
			double camcharges=Double.parseDouble(request.getParameter("camCharges"));
			double previousbal=Double.parseDouble(request.getParameter("previousBal"));
			double Closingbal=Double.parseDouble(request.getParameter("closingBal"));
			double rechargeAmt=Double.parseDouble(request.getParameter("rechargeamt"));
			String rechargeAMT=request.getParameter("rechargeamt");
			
			 int propid=Integer.parseInt(request.getParameter("propertyId"));
			 String fromDate=request.getParameter("fromDate");
			 String toDate=request.getParameter("toDate");
			 PrintWriter out;
			 List<Map<String, Object>> reList=new ArrayList<>();
				Map<String, Object> mapList=null;
		   if(propertyId!=null || propertyId!=""){
			   try{
          	 String meterNo=prepaidMeterService.getMtrNo(propid);
				 String propertyNo=prepaidMeterService.getPropertyNo(propid);						
			    long dataCount=prepaidBillService.getCount(propid, presentdate);
			    if(dataCount>0){
			    	out=response.getWriter();
					out.write("Bill Already Generated This Month for Property "+propertyNo+"/n ");
			    }else{
			    	HashMap<String, Object> param = new HashMap<String, Object>();
					int  area=(int) entityManager.createQuery("select p.area from Property p where p.propertyId="+propid).getSingleResult();
					Person person=personService.find(prepaidMeterService.getPersonId(propid));
					Set<Address> address=person.getAddresses();
					String custaddress=null;
					for (Address address2 : address) {
						String d1=address2.getAddress1();
						String d2="";
						if(address2.getAddress2()!=null){
							 d2=address2.getAddress2();
						
						}
						custaddress=d1+","+d2;
					}
					String CustomerName=person.getFirstName()+""+person.getLastName();
					String billNo=genrateAdjustmentNumber();
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
					param.put("name", CustomerName);
					param.put("propertyNo",propertyNo);
					param.put("address",propertyNo +","+"");
					param.put("city", "Gurugram");
					param.put("secondaryAddress", custaddress);
					param.put("area", area);
					param.put("billNo", billNo);
					Calendar start = Calendar.getInstance();
					start.setTime(presentdate);
					start.add(Calendar.MONTH, 1);
					param.put("billDate",new SimpleDateFormat("dd-MMM-yyyy").format(start.getTime()));
					param.put("mtrNo", meterNo);
					
				    Date minDate=new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
					Date maxDate=new SimpleDateFormat("dd/MM/yyyy").parse(toDate);
					param.put("billingPeriod",DateFormatUtils.format((minDate),"dd MMM. yyyy")	+ " To "+ DateFormatUtils.format((maxDate),"dd MMM. yyyy"));
					param.put("previousBal",previousbal);
					param.put("closingBal",Closingbal);
					param.put("rechargeAmt",rechargeAMT);
					long bal=Math.round(previousbal+rechargeAmt-Closingbal);
					logger.info("Consumption AMt "+bal);
					param.put("consumptionAmt",bal);
					int temp=0;
					int temp1=1;
					int temp2=2;
					double dgAmt=0.0;
					double mainAMT=0.0;
					if(temp==0){
						PrepaidTxnChargesEntity prChargesEntity=prepaidTransactionService.getCharges(temp);
						System.out.println("service Name "+prChargesEntity.getCharge_Name());
						param.put("mainUnit",ebreading);
						 //ebunits=Double.parseDouble(values[7]+"");
						 param.put("elRate", prChargesEntity.getRate());
						
						 mainAMT=Double.parseDouble(new DecimalFormat("##.##").format((ebreading*prChargesEntity.getRate())));
						 param.put("mainAmt",mainAMT);
						
					}
					if(temp1==1){
						List<PrepaidTxnChargesEntity> prChargesEntity=prepaidTransactionService.getCAMCharges(temp1);
						double camamount=0.0;
						for (PrepaidTxnChargesEntity prepaidTxnChargesEntity : prChargesEntity) {
							if(prepaidTxnChargesEntity.getCharge_Name().equals("CAM RATE")){
							  
								//param.put("calculatedAmt",Double.parseDouble(values[8]+""));
								camamount=(camcharges/1.15);
							
								
						 }
						}
						
						for (PrepaidTxnChargesEntity prepaidTxnChargesEntity : prChargesEntity) {
							
						if(prepaidTxnChargesEntity.getCharge_Name().equals("CAM RATE")){
						    param.put("serviceName", "CAM");
							param.put("chargeName",prepaidTxnChargesEntity.getCharge_Name());
							param.put("rate",prepaidTxnChargesEntity.getRate());
							param.put("cbName", "AREA"+"/"+"(PSF)");
							//param.put("calculatedAmt",Double.parseDouble(values[8]+""));

							param.put("totalAmt1",Double.parseDouble(new DecimalFormat("##.##").format(camamount)));
							
					 }else if(prepaidTxnChargesEntity.getCharge_Name().equals("Service Tax")){
						    param.put("serviceName1", "CAM");
							param.put("chargeName1",prepaidTxnChargesEntity.getCharge_Name());
							param.put("rate1",prepaidTxnChargesEntity.getRate());
							param.put("cbName1", "%");
							//param.put("calculatedAmt1",Double.parseDouble(values[8]+""));

							param.put("totalAmt2",Double.parseDouble(new DecimalFormat("##.##").format((camamount*prepaidTxnChargesEntity.getRate())/100)));

					 }else if(prepaidTxnChargesEntity.getCharge_Name().equals("Swach Bharat Cess")){
						    param.put("serviceName2", "CAM");
							param.put("chargeName2",prepaidTxnChargesEntity.getCharge_Name());
							param.put("rate2",prepaidTxnChargesEntity.getRate());
							param.put("cbName2", "%");
							//param.put("calculatedAmt2",Double.parseDouble(values[8]+""));

							param.put("totalAmt3",Double.parseDouble(new DecimalFormat("##.##").format((camamount*prepaidTxnChargesEntity.getRate())/100)));

					 }else if(prepaidTxnChargesEntity.getCharge_Name().equals("Krishi Kalyan Cess")){
						    param.put("serviceName3", "CAM");
							param.put("chargeName3",prepaidTxnChargesEntity.getCharge_Name());
							param.put("rate3",prepaidTxnChargesEntity.getRate());
							param.put("cbName3", "%");
							//param.put("calculatedAmt3",Double.parseDouble(values[8]+""));

							param.put("totalAmt4",Double.parseDouble(new DecimalFormat("##.##").format((camamount*prepaidTxnChargesEntity.getRate())/100)));

					 }
						}
					}
					if(temp2==2){
						PrepaidTxnChargesEntity prChargesEntity=prepaidTransactionService.getCharges(temp2);
						param.put("dgUnit",dgreading);
						 //dgunits=Double.parseDouble(values[7]+"");
						System.out.println("service Name "+prChargesEntity.getCharge_Name());
						 param.put("dgRate", prChargesEntity.getRate());
						 dgAmt=Double.parseDouble(new DecimalFormat("##.##").format((dgreading*prChargesEntity.getRate())));
						 param.put("dgAmt", dgAmt);
						
					}
					 param.put("totalAmt",dgAmt+mainAMT);
					 param.put("sumAmt", Math.round(camcharges));
					 param.put("grossConsumptionAmt", Math.round(camcharges+dgAmt+mainAMT));
						Integer numToWord= (int) Math.round(camcharges+dgAmt+mainAMT);
						String amountInWords = NumberToWord.convertNumberToWords(numToWord.intValue());
						param.put("amountInWords", amountInWords + " Only");
						long bill_Amt=Math.round(camcharges+dgAmt+mainAMT);
						logger.info("BILL AMT "+bill_Amt);
						PrepaidBillDetails billDetails=new PrepaidBillDetails();
						billDetails.setBill_Amt(bal);
						billDetails.setBill_Month(presentdate);
						billDetails.setBillNo(billNo);
						billDetails.setPropertyId(propid);
						billDetails.setPreviousBal(previousbal);
						billDetails.setClosingBal(Closingbal);
						billDetails.setGrossConsumptionAmt(Math.round(camcharges+dgAmt+mainAMT));
						billDetails.setEbReading(ebreading);
						billDetails.setDgReading(dgreading);
						billDetails.setRechargedAmt(rechargeAmt);
						billDetails.setCamCharges(camcharges);
						prepaidBillService.save(billDetails);
						
						JRBeanCollectionDataSource jre = new JRBeanCollectionDataSource(bliList);
						JREmptyDataSource jre = new JREmptyDataSource();
						JasperPrint jasperPrint;
						JasperReport report;
						try {
							logger.info("---------- filling the report -----------");
						
								jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/Bill.jasper"),param, jre);
							
							removeBlankPage(jasperPrint.getPages());
							byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
							InputStream myInputStream = new ByteArrayInputStream(bytes);
							Blob blob = Hibernate.createBlob(myInputStream);
							PrepaidBillDocument billDocument = new PrepaidBillDocument(propid, presentdate, billNo, blob);
							prepaidBillDocService.save(billDocument);
							
						} catch (JRException e) {
							e.printStackTrace();
						} catch (IOException e) {
							logger.error("----------------- IOException");
						}
					if(){
						PrepaidTxnChargesEntity prChargesEntity=prepaidTransactionService.getCharges(temp);
					}
						out=response.getWriter();
						out.write("Bill Generated Successfully");
			    }
			   }catch(Exception e){
				   e.printStackTrace();
				   return "Bill generation failed due to network issue";
			   }
           }
		   return null;
	 }*/
	 
	 
	 
	 
	/*++++++++++++++++++++++++++++++++++++++ Export to Excel (Vijju)  ++++++++++++++++++++++++++++++++++++++*/
	 
 @RequestMapping(value = "/prepaidBillGeneration/exportPrepaidBillDatasToExcel", method = {RequestMethod.POST,RequestMethod.GET})
	   public String exportprepaidBillGeneration(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
			
			
		 logger.info("---------------Hello Excel  ----------------");
	 System.out.println("Exporting Prepaid Bill Details");
	 
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+".xlsx";
	        
		
	               
	        String sheetName = "Templates";//name of sheet

	    	XSSFWorkbook wb = new XSSFWorkbook();
	    	XSSFSheet sheet = wb.createSheet(sheetName) ;
	    	
	    	XSSFRow header = sheet.createRow(0);    	
	    	
	    	XSSFCellStyle style = wb.createCellStyle();
	    	style.setWrapText(true);
	    	XSSFFont font = wb.createFont();
	    	font.setFontName(HSSFFont.FONT_ARIAL);
	    	font.setFontHeightInPoints((short)10);
	    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    	style.setFont(font);
	    	
	        header.createCell(0).setCellValue("Meter No");
	        header.createCell(1).setCellValue("Person Name");
	        header.createCell(2).setCellValue("Property No");
	        header.createCell(3).setCellValue("Consumption Amount");
	        header.createCell(4).setCellValue("Gross Consumption Amount");
	        header.createCell(5).setCellValue("Bill No");
	        header.createCell(6).setCellValue("Bill Month");
	        header.createCell(7).setCellValue("Previous Balance");
	        header.createCell(8).setCellValue("Closing Balance");
	        header.createCell(9).setCellValue("EB Reading");
	        header.createCell(10).setCellValue("DG Reading");
	        header.createCell(11).setCellValue("Recharge Amount");
	   
	        for(int j = 0; j<=11; j++){
	    		header.getCell(j).setCellStyle(style);
	            sheet.setColumnWidth(j, 8000); 
	            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:Q200"));
	        }
	        
	        int count = 1;
	      
	        List<PrepaidBillDetails> details=prepaidBillService.getData();
	        
	        for(PrepaidBillDetails prepaidBillDetails:details){
	    		
	        	XSSFRow row = sheet.createRow(count);
	    		
	    			
	    		row.createCell(0).setCellValue((String)prepaidMeterService.getMtrNo(prepaidBillDetails.getPropertyId()));
	    		
	    		
	    		Person person=personService.find(prepaidMeterService.getPersonId(prepaidBillDetails.getPropertyId()));
				String CustomerName=person.getFirstName()+""+person.getLastName();
	    			 
	    		    row.createCell(1).setCellValue((String)CustomerName);
	    		
		    			 
	  	    		row.createCell(2).setCellValue((String)prepaidMeterService.getPropertyNo(prepaidBillDetails.getPropertyId()));
	  	    		
	  	    		 
	  	    		long amt=prepaidBillDetails.getBill_Amt();
	  	    	
	  	    		row.createCell(3).setCellValue(String.valueOf(amt));
	  	    		
	  	    		
	    		    long grossAmt=prepaidBillDetails.getGrossConsumptionAmt();
		    			 
	  	    		row.createCell(4).setCellValue(String.valueOf(grossAmt));
	  	    		
	  	    		
		    			 
	  	    		row.createCell(5).setCellValue((String)prepaidBillDetails.getBillNo());
	  	    		
	  	    
	  	    		String date=new SimpleDateFormat("MMM yyyy").format(prepaidBillDetails.getBill_Month());
		    			 
	  	    		row.createCell(6).setCellValue((String)date);
	  	    		
	    			 
	  	    		row.createCell(7).setCellValue(String.valueOf(prepaidBillDetails.getPreviousBal()));
	  	    		
	  	    		row.createCell(8).setCellValue(String.valueOf(prepaidBillDetails.getClosingBal()));
	    		  
	  	    		
	  	    		
	  	    		row.createCell(9).setCellValue(String.valueOf(prepaidBillDetails.getEbReading()));
	  	    		
	  	    		row.createCell(10).setCellValue(String.valueOf(prepaidBillDetails.getDgReading()));
	  	    		
	  	    		row.createCell(11).setCellValue(String.valueOf(prepaidBillDetails.getRechargedAmt()));
	  	    		
	  	    		
	    		count ++;
			}
	    	
	    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
	    	wb.write(fileOut);
	    	fileOut.flush();
	    	fileOut.close();
	        
	        ServletOutputStream servletOutputStream;

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Prepaid Bill"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();
			
			return null;
		}
 
 @RequestMapping(value="/postpaidBillGen")
	public String index2(@ModelAttribute("prepaidBillDetails") PrepaidBillDetails prepaidBillDetails){
	 logger.info("in side index2");
		return "postpaidBill";
	} 

@RequestMapping(value="/postpaidBill/postPaidELBillReadUrl",method={RequestMethod.GET,RequestMethod.POST})
public @ResponseBody List<?> readAllData1(){
	 List<BatchBillsEntity> details=prepaidBillService.getPostData();
	 List<Map<String, Object>> resultList=new ArrayList<>();
		Map<String, Object> maplist=null;
		if(details!=null){
		for(BatchBillsEntity prepaidBillDetails:details){
			maplist=new HashMap<>();
			maplist.put("preBillId", prepaidBillDetails.getId());
			maplist.put("mtrNo", prepaidBillDetails.getMeterNo());
			//Person person=personService.find(prepaidMeterService.getPersonId(prepaidBillDetails.getPropertyId()));
		//	String CustomerName=person.getFirstName()+""+person.getLastName();
			maplist.put("personName", prepaidBillDetails.getName());
			maplist.put("propertyNo", prepaidBillDetails.getProperty());
			//String date=new SimpleDateFormat("MMM yyyy").format(prepaidBillDetails.getBill_Month());
			maplist.put("billMonth", prepaidBillDetails.getPresentReadingDate());
			maplist.put("netAmount", "99999");
			maplist.put("billNo", "121");
			maplist.put("previousBal", "00");
			maplist.put("closingBal", "00");
			maplist.put("mailStatus", "00");
			maplist.put("grossAmount", "00");
			resultList.add(maplist);
		}
		}
		return resultList;
}

@RequestMapping(value="/uploadExcelFileToGenBill",method={RequestMethod.POST,RequestMethod.GET})
public String xlSheetSave(@RequestParam("xlsheet") MultipartFile file,HttpServletRequest request,Model model,@ModelAttribute("prepaidBillDetails") PrepaidBillDetails prepaidBillDetails) throws InvalidFormatException, IOException, ParseException, SQLException
{
	
List<BatchBillsEntity> lstUser = new ArrayList<>();

List<PostLedgerEntity> lstLed=new ArrayList<>();	
	Workbook workbook=null;
	try {
		workbook = WorkbookFactory.create(file.getInputStream());
	} catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		
	
   Sheet sheet=workbook.getSheetAt(0);

   DataFormatter dataFormatter = new DataFormatter();

   JasperReportTest call=new JasperReportTest();
   Iterator<Row> rowIterator = sheet.rowIterator();
  


while (rowIterator.hasNext()) {
    Row row = rowIterator.next();
    BatchBillsEntity uploadMaster=new BatchBillsEntity();
    PostLedgerEntity ledGen=new PostLedgerEntity();
    BillDocEntity billDocEnt=new BillDocEntity();

    if(row.getRowNum()==0)
    {
    	continue;
    }
    
    Iterator<Cell> cellIterator = row.cellIterator();

    while (cellIterator.hasNext()) {
        Cell cell = cellIterator.next();
      
        String cellValue = dataFormatter.formatCellValue(cell);
        
        if(cellValue == null)
        {   System.out.println(row.getRowNum()+"this is row no"+cellValue);
        	 model.addAttribute("msg", "File Upload Fail,Some columns are Empty or NULL !");
        	 return "prepaidBill";
        }
        if(cellValue.equalsIgnoreCase(""))
        {
        	break;
        }
        else{
       
       
        if(cell.getColumnIndex()==0)
        {
        	uploadMaster.setAccountNo(cellValue);
        }
       
        else if(cell.getColumnIndex()==1)
        {
        	uploadMaster.setName(cellValue);
        }
       else if(cell.getColumnIndex()==2)
        {
      	  uploadMaster.setAddress(cellValue);
        }
        else if(cell.getColumnIndex()==3)
        {
      	  uploadMaster.setProperty(cellValue);
        }
        else if(cell.getColumnIndex()==4)
        {
      	  uploadMaster.setMeterNo(cellValue);
        }  
        else if(cell.getColumnIndex()==5)
        {
      	  uploadMaster.setRate(cellValue);
        } 
        else if(cell.getColumnIndex()==6)
        {
      	  uploadMaster.setPaymentDueDate(cellValue);
        } 
        else if(cell.getColumnIndex()==7)
        {
      	  uploadMaster.setMiscellanous(cellValue);
        } 
        
        else if(cell.getColumnIndex()==8)
        {
        	uploadMaster.setServiceType(cellValue);
        }
       else if(cell.getColumnIndex()==9)
        {
      	  uploadMaster.setPresentReadingDate((cellValue));
        }
        else if(cell.getColumnIndex()==10)
        {
      	  uploadMaster.setPresentReadingStatus(cellValue);
        }
        else if(cell.getColumnIndex()==11)
        {
      	  uploadMaster.setPresentReading(cellValue);
        }  
        else if(cell.getColumnIndex()==12)
        {
      	  uploadMaster.setDGpresentReading(cellValue);
        } 
        else if(cell.getColumnIndex()==13)
        {
      	  uploadMaster.setPFReading(cellValue);
        } 
        else if(cell.getColumnIndex()==14)
        {
      	  uploadMaster.setRecordedDemand(cellValue);
        } 
        else if(cell.getColumnIndex()==15)
        {
      	  uploadMaster.setTOD1(cellValue);
        }
        else if(cell.getColumnIndex()==16)
        {
      	  uploadMaster.setTOD2(cellValue);
        } 
        else if(cell.getColumnIndex()==17)
        {
      	  uploadMaster.setTOD3(cellValue);
        } 
        
        else if(cell.getColumnIndex()==18)
        {
      	  uploadMaster.setPreviousBillDate(cellValue);
        } 
        else if(cell.getColumnIndex()==19)
        {
      	  uploadMaster.setPreviousStatus(cellValue);
        } 
        
        else if(cell.getColumnIndex()==20)
        {
      	  uploadMaster.setPreviousReading(cellValue);
        } 
        
        else if(cell.getColumnIndex()==21)
        {
      	  uploadMaster.setDGPreviousReading(cellValue);
        } 
        
        else if(cell.getColumnIndex()==22)
        {
      	  uploadMaster.setMeterConstant(cellValue);
        } 
        else if(cell.getColumnIndex()==23)
        {
        	uploadMaster.setDGMeterConstant(cellValue);
        } 
        else 
        {
      	 
        } 

    } 
   
}lstUser.add(uploadMaster);

    if(uploadMaster.getAccountNo() != null)
    {
    	  //#############  bill generation ##################
	    double val=Math.random()+1.91;
	  	String bno=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());	
	  	long sixdgbill=(Long.parseLong(bno)+(long)val)%1000000;
	  
	  	
	    //#############  bill generation ##################
    	
	  uploadMaster.setReceiptNo(String.valueOf(sixdgbill));
      Blob blob=call.generatePostElectricityBill(uploadMaster);
     
      BatchBillsEntity batchbill=batchbillService.save(uploadMaster);
      /*ledGen.setId(batchbill.getId());
      
      ledGen.setAccountNo(uploadMaster.getAccountNo());   
      ledGen.setPropertyNo(uploadMaster.getProperty());
      ledGen.setDate(uploadMaster.getPaymentDueDate());		     
        double unitsVal=Double.parseDouble(uploadMaster.getPresentReading())-(Double.parseDouble(uploadMaster.getPreviousReading()));
		double units = Math.round(unitsVal * 100.0) / 100.0;
		
		
		double rateVal=Double.parseDouble(uploadMaster.getRate());
		
		
	    double amtVal=units*rateVal;
	    double amt = Math.round(amtVal * 100.0) / 100.0;
	    ledGen.setAmount(String.valueOf(amt));*/
    //  PostLedgerEntity ple=postLedgerService.save(ledGen);
      
      
      
    
      billDocEnt.setBid(batchbill.getId());
      billDocEnt.setBlob(blob);
      billDocService.save(billDocEnt);
      
      //lstLed.add(ledGen);
      
    }
}
// postLedgerService.batchSave(lstLed);
// batchbillService.batchSave(lstUser);
System.out.println(lstUser.size());
model.addAttribute("msg", "Successfully Saved !");

return "postpaidBill";
}


@RequestMapping(value ="/postpaidBill/getbilltablePDF/{postpaidBillId}", method = {RequestMethod.POST, RequestMethod.GET })
public void viewBillPDF1(@PathVariable int postpaidBillId,HttpServletResponse res, Locale locale) 
{
	 
	           System.err.println(postpaidBillId);
	          /* System.out.println(req.getParameter("SelectedRowId"));
	           System.out.println(req.getParameter("postpaidBillId"));*/
	 
	     BigDecimal docid=(BigDecimal) entityManager.createNativeQuery("SELECT BILLDOCID FROM BILL_DOC WHERE BID='"+postpaidBillId+"'").getSingleResult(); 
	           
		BillDocEntity prepaidBillDetails = postpaidBillService.find(docid.intValue());
		System.out.println("prepaidBillDetails="+prepaidBillDetails);
		Blob blob = prepaidBillDetails.getBlob();
		try {
			if (blob != null) {
				res.setHeader("Content-Disposition", "inline; filename="
						+"PostPaidBill"+ "_"
						+ prepaidBillDetails.getBid() + ".pdf");
				OutputStream out = res.getOutputStream();
				res.setContentType("application/pdf");
				IOUtils.copy(blob.getBinaryStream(), out);
				out.flush();
				out.close();
			} else {
				logger.info("::::::::::::: else block");
				OutputStream out = res.getOutputStream();
				IOUtils.write("File Not Found", out);
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


 }



