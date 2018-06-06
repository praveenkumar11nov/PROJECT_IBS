package com.bcits.bfm.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.tempuri.IREOService;
import org.xml.sax.InputSource;

import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.BillData;
import com.bcits.bfm.model.ConsumerDetailsEntity;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.DailyData;
import com.bcits.bfm.model.DocumentElement;
import com.bcits.bfm.model.DocumentElementData;
import com.bcits.bfm.model.GenusMtrNoNotGetting;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.PrePaidGenusBilldata;
import com.bcits.bfm.model.PrePaidDailyData;
import com.bcits.bfm.model.PrepaidRechargeEntity;
import com.bcits.bfm.model.SmsEmailForLowBal;
import com.bcits.bfm.service.ConsumerDetailsService;
import com.bcits.bfm.service.ConsumptionNotGettingMtrService;
import com.bcits.bfm.service.LowBalanceStoreService;
import com.bcits.bfm.service.PrePaidGenusBillDataservice;
import com.bcits.bfm.service.PrePaidInstantDataService;
import com.bcits.bfm.service.PrePaidMeterService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.util.EmailCredentialsDetailsBean;
import com.bcits.bfm.util.SendMailForOwnersDetails;
import com.bcits.bfm.util.SendSMSForStatus;
import com.bcits.bfm.util.SmsCredentialsDetailsBean;
import com.bcits.bfm.view.BreadCrumbTreeService;
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
public class PrePaidConsumptionManagementController {

	final Logger logger=LoggerFactory.getLogger(PrePaidConsumptionManagementController.class);
	
	@Autowired
	private PrePaidGenusBillDataservice  prePaidHistoricalData;
	
	@Autowired
    private	ConsumerDetailsService consumerDetailsService;
	
	@Autowired
	private PrePaidInstantDataService prePaidInstantDataService; 
	
	@Autowired
	private AccountService  accountService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private LowBalanceStoreService lowBalanceStoreService;
	
	@Autowired
	private PrePaidMeterService prepaidMeterService;
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private ConsumptionNotGettingMtrService consumptionNotGettingMtrService;
	
	@RequestMapping(value="/consumptionHty")
	public String consumptionHty(Model model,HttpServletRequest request){
		logger.info("in side consumption histy method");
		model.addAttribute("ViewName", "Consumption History");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Prepaid Billing", 1, request);
		breadCrumbService.addNode("Manage Consumption History", 2, request);
		 
		return "consumptionHistory";
	}
	
	@RequestMapping(value="/blankConsumptionData")
	public String blankConsumptionData(Model model,HttpServletRequest request){
		logger.info("in side blankConsumptionData method");
		return "BlankConsumptionData";
	}
	
	@RequestMapping(value="/readConsumptionBlankData", method = RequestMethod.POST)
	public @ResponseBody List<?> readConsumptionBlankData(HttpServletRequest request)
	{
		logger.info("in side readConsumptionBlankData method");
		String readingDate = request.getParameter("readingDate");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<?> gotResult = consumptionNotGettingMtrService.getAllMeterBlankData(readingDate);
		
		 Map<String, Object> record = null;
		 for (Iterator<?> iterator = gotResult.iterator(); iterator.hasNext();)
		 {
				final Object[] values = (Object[]) iterator.next();
				record = new HashMap<String, Object>();
				
				record.put("meterNo", (String)values[1]);
				record.put("readingDate", (String)values[2]);
				record.put("createdDate", (Date)values[3]);
			
				result.add(record);
	     }
		 return result;
	}
	 @Scheduled(cron = "${scheduling.job.prepaidbilldata}")
		public void escalationSheduler1() throws Exception{
			logger.info("in side scheduling methode");
			List<?> liEntities=prepaidMeterService.getConsumerIDs();
		
			int count1=0;
			for (Iterator<?> iterator=liEntities.iterator();iterator.hasNext();) {
				final Object[] vaObjects =(Object[]) iterator.next();
				 logger.info("Meter Number: "+vaObjects[1]);
				
				
					/*Date d=new Date();
					Calendar end = Calendar.getInstance();
					end.setTime(d);
					end.add(Calendar.DATE, -1);
				  String date=new SimpleDateFormat("dd/MM/yyyy").format(end.getTime());*/
				/* Date todayDate=new Date();
					Calendar start = Calendar.getInstance();
					start.setTime(todayDate);
					start.add(Calendar.DATE, -2);*/
					Date d=new Date();
					Calendar end = Calendar.getInstance();
					end.setTime(d);
					end.add(Calendar.DATE, -1);
					    String date=new SimpleDateFormat("dd/MM/yyyy").format(end.getTime());
					     System.out.println("DATE       "+date+"vaObjects[1] "+vaObjects[1]);
					    long count=prePaidInstantDataService.checkDataExistence(date,vaObjects[1]+"");
					if(count==0){
					
						JAXBContext jaxbContext;
						 count1=count1+1;
						String t1=String.valueOf(count1);
				try {
						long d1=81;
						IREOService s1=new IREOService();
						String data	=s1.getBasicHttpBindingIService1().dailyData(t1, date, "harjeet", "ireo@123", d1, vaObjects[1]+"",date,date);
						
						 if(data.equalsIgnoreCase("Meter Bill not available.")){
							
							    logger.info("mete number "+vaObjects[1]);
						    	logger.info("Reading Date "+date);
						    	 long count2=prePaidInstantDataService.GenusMtrNoNotGettingExistence(date,vaObjects[1]+"");
						    	 if(count2==0){
						    	GenusMtrNoNotGetting genusMtrNoNotGetting=new GenusMtrNoNotGetting();
						    	genusMtrNoNotGetting.setMeterNo(vaObjects[1]+"");
						    	genusMtrNoNotGetting.setReadingDate(date);
						    	genusMtrNoNotGetting.setCreatedDate(new Date());
						    	genusMtrNoNotGetting.setStatus(data);
						    	consumptionNotGettingMtrService.save(genusMtrNoNotGetting);
						    	 }
						    }else{
						    	
						jaxbContext = JAXBContext.newInstance(DocumentElementData.class);
						  Unmarshaller jaxUnmarshaller = jaxbContext.createUnmarshaller();
						  InputSource inputSource = new InputSource(new ByteArrayInputStream(data.getBytes()));
						   inputSource.setEncoding("UTF-8");
						  DocumentElementData tallyResponse = (DocumentElementData) jaxUnmarshaller.unmarshal(inputSource); 
						  
						    List<DailyData> list=tallyResponse.getInstantDatas();
						  
						   for (DailyData dailyData:list) {
							   double rechargeAmt=0.0;
							   PrePaidDailyData prePaidDailyData1=prePaidInstantDataService.getMaxReadingDate(dailyData.getMtrSrNo());
							   PrePaidDailyData prDailyData=new PrePaidDailyData();
							   prDailyData.setMtrSrNo(dailyData.getMtrSrNo());
							   prDailyData.setBalance(dailyData.getBalance());
							   prDailyData.setDaily_Cons_Amt(dailyData.getDaily_Consum_Amount());
							   prDailyData.setReadingDT(dailyData.getReadingDT());
							   prDailyData.setCum_Fixed_Charg_Main(dailyData.getCum_FixChrg_Main());
							   prDailyData.setCum_Fixed_Charg_Dg(dailyData.getCum_FixChrg_Dg());
							   prDailyData.setCum_Recharge_Amount(dailyData.getCum_Recharge_Amount());
							   prDailyData.setCum_Kwh_Main(dailyData.getCum_kWh_Main());
							   prDailyData.setCum_Kwh_Dg(dailyData.getCum_kWh_Dg());
							  // String prePaidDailyData1.getCum_Fixed_Charg_Main()="0.0";
							   if(prePaidDailyData1!=null){
							    
							    rechargeAmt=Math.ceil(Double.parseDouble(dailyData.getCum_Recharge_Amount()))-Math.ceil(Double.parseDouble(prePaidDailyData1.getCum_Recharge_Amount()));

							   }else{
								 
								  rechargeAmt=Double.parseDouble(dailyData.getCum_Recharge_Amount());
							   }
							  
							  
							   prDailyData.setCum_Recharge_Amount1(new DecimalFormat("##.##").format(rechargeAmt));
							   prePaidInstantDataService.save(prDailyData);
							
						}
						    }
				} catch (JAXBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
			
		}
			
			
		
			}
			
	 }

	 @Async
	private int getGenusConsumptionforLastTwoDays(int count1, final Object[] vaObjects, String date) {
		
		return count1;
	}
	   
	//@RequestMapping(value="/consumptionHty/prepaidbilldataDaily",method={RequestMethod.POST,RequestMethod.GET})
	  /*@Scheduled(cron = "${scheduling.job.prepaidbilldata}")
		public void escalationSheduler1() throws Exception{
			logger.info("in side scheduling methode");
			List<?> liEntities=prepaidMeterService.getConsumerIDs();
			for (Iterator<?> iterator=liEntities.iterator();iterator.hasNext();) {
				final Object[] vaObjects =(Object[]) iterator.next();
				 logger.info("Meter Number: "+vaObjects[1]);
				 String tid=String.valueOf(vaObjects[0]+"");
				//PrePaidDailyData prePaidDailyData=prePaidInstantDataService.getMaxReadingDate(vaObjects[1]+""); 
				Date minDate=prePaidInstantDataService.getminReadingDate(vaObjects[1]+"");
				if(minDate!=null){
					
				//=================================== Getting 1st Date of this month ======================
		 			Date todayDate=new Date();
		 			Calendar cal = Calendar.getInstance();
		 			cal.setTime(todayDate);
		 			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		 			System.out.println(cal.getTime());
		 		//==========================================================================================
					
					Calendar start = Calendar.getInstance();
					start.setTime(cal.getTime());
					start.add(Calendar.DATE, 1);
					Date d=new Date();
					Calendar end = Calendar.getInstance();
					end.setTime(d);
					end.add(Calendar.DATE, -1);
               int count1=0;
			for (Date date1 = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date1 = start.getTime()) {
					   
					    String date=new SimpleDateFormat("dd/MM/yyyy").format(date1);
					    System.out.println("DATE       "+date);
					    long count=prePaidInstantDataService.checkDataExistence(date,vaObjects[1]+"");
					if(count==0){
			     JAXBContext jaxbContext;
			     count1=count1+1;
			    String t1=String.valueOf(count1);
			try {
				long d1=81;
				IREOService s1=new IREOService();
				String data	=s1.getBasicHttpBindingIService1().dailyData(t1, date, "harjeet", "ireo@123", d1, vaObjects[1]+"",date,date);
				 if(data.equalsIgnoreCase("Meter Bill not available.")){
					    logger.info("mete number "+vaObjects[1]);
				    	logger.info("Reading Date "+date);
				    	GenusMtrNoNotGetting genusMtrNoNotGetting=new GenusMtrNoNotGetting();
				    	genusMtrNoNotGetting.setMeterNo(vaObjects[1]+"");
				    	genusMtrNoNotGetting.setReadingDate(date);
				    	genusMtrNoNotGetting.setCreatedDate(new Date());
				    	consumptionNotGettingMtrService.save(genusMtrNoNotGetting);
				    }else{
				jaxbContext = JAXBContext.newInstance(DocumentElementData.class);
				  Unmarshaller jaxUnmarshaller = jaxbContext.createUnmarshaller();
			      InputSource inputSource = new InputSource(new ByteArrayInputStream(data.getBytes()));
			       inputSource.setEncoding("UTF-8");
			      DocumentElementData tallyResponse = (DocumentElementData) jaxUnmarshaller.unmarshal(inputSource); 
			      
			        List<DailyData> list=tallyResponse.getInstantDatas();
			      
			       for (DailyData dailyData:list) {
			    	   double rechargeAmt=0.0;
			    	   PrePaidDailyData prePaidDailyData1=prePaidInstantDataService.getMaxReadingDate(dailyData.getMtrSrNo());
			    	   PrePaidDailyData prDailyData=new PrePaidDailyData();
			    	   prDailyData.setMtrSrNo(dailyData.getMtrSrNo());
			    	   prDailyData.setBalance(dailyData.getBalance());
			    	   prDailyData.setDaily_Cons_Amt(dailyData.getDaily_Consum_Amount());
			    	   prDailyData.setReadingDT(dailyData.getReadingDT());
			    	   prDailyData.setCum_Fixed_Charg_Main(dailyData.getCum_FixChrg_Main());
			    	   prDailyData.setCum_Fixed_Charg_Dg(dailyData.getCum_FixChrg_Dg());
			    	   prDailyData.setCum_Recharge_Amount(dailyData.getCum_Recharge_Amount());
			    	   prDailyData.setCum_Kwh_Main(dailyData.getCum_kWh_Main());
			    	   prDailyData.setCum_Kwh_Dg(dailyData.getCum_kWh_Dg());
			    	  // String prePaidDailyData1.getCum_Fixed_Charg_Main()="0.0";
			    	   if(prePaidDailyData1!=null){
			    	    
			    	    rechargeAmt=Math.ceil(Double.parseDouble(dailyData.getCum_Recharge_Amount()))-Math.ceil(Double.parseDouble(prePaidDailyData1.getCum_Recharge_Amount()));

			    	   }else{
			    		 
			    		  rechargeAmt=Double.parseDouble(dailyData.getCum_Recharge_Amount());
			    	   }
			    	  
			    	  
			    	   prDailyData.setCum_Recharge_Amount1(new DecimalFormat("##.##").format(rechargeAmt));
					   prePaidInstantDataService.save(prDailyData);
			    	
				}
				    }
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			//}
			} }else{
					 JAXBContext jaxbContext;
				     
						try {
							long d1=81;
							IREOService s1=new IREOService();
							Calendar c1=Calendar.getInstance();
							c1.add(Calendar.DATE, -1);
							Date c=c1.getTime();
							String s=new SimpleDateFormat("dd/MM/yyyy").format(c);
							System.out.println("Previous date"+s);
							Date d=new Date();
							String date=new SimpleDateFormat("dd/MM/yyyy").format(d);
							System.out.println("current Date"+date);
							//IREOService s1=new IREOService();
							//long d1=81;
							String data	=s1.getBasicHttpBindingIService1().dailyData(tid, s, "harjeet", "ireo@123", d1, vaObjects[1]+"",s,s);
						    // System.out.println(data);
							 if(data.equalsIgnoreCase("Meter Bill not available.")){
								    logger.info("mete number "+vaObjects[1]);
							    	logger.info("Reading Date "+date);
							    	GenusMtrNoNotGetting genusMtrNoNotGetting=new GenusMtrNoNotGetting();
							    	genusMtrNoNotGetting.setMeterNo(vaObjects[1]+"");
							    	genusMtrNoNotGetting.setReadingDate(date);
							    	genusMtrNoNotGetting.setCreatedDate(new Date());
							    	consumptionNotGettingMtrService.save(genusMtrNoNotGetting);
							    }else{
							jaxbContext = JAXBContext.newInstance(DocumentElementData.class);
							  Unmarshaller jaxUnmarshaller = jaxbContext.createUnmarshaller();
						      InputSource inputSource = new InputSource(new ByteArrayInputStream(data.getBytes()));
						       inputSource.setEncoding("UTF-8");
						      DocumentElementData tallyResponse = (DocumentElementData) jaxUnmarshaller.unmarshal(inputSource); 
						       System.out.println(tallyResponse);
						        List<DailyData> list=tallyResponse.getInstantDatas();
						        System.out.println(list);
						       for (DailyData dailyData : list) {
						    	  
						    	   double rechargeAmt=0.0;
						    	   PrePaidDailyData prePaidDailyData1=prePaidInstantDataService.getMaxReadingDate(dailyData.getMtrSrNo());
						    	   PrePaidDailyData prDailyData=new PrePaidDailyData();
						    	   prDailyData.setMtrSrNo(dailyData.getMtrSrNo());
						    	   prDailyData.setBalance(dailyData.getBalance());
						    	   prDailyData.setDaily_Cons_Amt(dailyData.getDaily_Consum_Amount());
						    	   prDailyData.setReadingDT(dailyData.getReadingDT());
						    	   prDailyData.setCum_Fixed_Charg_Main(dailyData.getCum_FixChrg_Main());
						    	   prDailyData.setCum_Fixed_Charg_Dg(dailyData.getCum_FixChrg_Dg());
						    	   prDailyData.setCum_Recharge_Amount(dailyData.getCum_Recharge_Amount());
						    	   prDailyData.setCum_Kwh_Main(dailyData.getCum_kWh_Main());
						    	   prDailyData.setCum_Kwh_Dg(dailyData.getCum_kWh_Dg());
						    	   double initailReading=prepaidMeterService.getInitialReading(dailyData.getMtrSrNo());
						    	   logger.info("initailReading  and meterNumber " +initailReading+" and "+dailyData.getMtrSrNo());
						    		  rechargeAmt=Double.parseDouble(dailyData.getCum_Recharge_Amount());
						    	  // String prePaidDailyData1.getCum_Fixed_Charg_Main()="0.0";
						    	   if(prePaidDailyData1!=null){
						    	  
						    	   
						    	    rechargeAmt=Double.parseDouble(dailyData.getCum_Recharge_Amount())-Double.parseDouble(prePaidDailyData1.getCum_Recharge_Amount());

						    	  }else{
						    		 
						    		  rechargeAmt=Double.parseDouble(dailyData.getCum_Recharge_Amount());
						    	   }
						    	  
						    	   
						    	  
						    	   prDailyData.setCum_Recharge_Amount1(new DecimalFormat("##.##").format(rechargeAmt));
								   prePaidInstantDataService.save(prDailyData);
						    	
							}
							    }
						} catch (JAXBException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 }
			}
		}*/
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "/prepaidConsumerData/uploadPrePaidConsumerData", method = RequestMethod.POST)
	public @ResponseBody
	void uploadTransactionDocument(@RequestParam MultipartFile files,HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
		logger.info("--------------- In TransactionHistory Batch file method -------------------");
		
			HSSFWorkbook workbook = new HSSFWorkbook(files.getInputStream()); //Read the Excel Workbook in a instance object    
			HSSFSheet sheet = workbook.getSheetAt(0);
			//XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
			//XSSFSheet sheet = workbook.getSheetAt(0);
			
			Iterator rows = sheet.rowIterator();
			int i = 0;
		
			List<ConsumerDetailsEntity> batchUpdates = new ArrayList<ConsumerDetailsEntity>();
			while (rows.hasNext()) {
				HSSFRow row = (HSSFRow) rows.next();
				//XSSFRow row = ((XSSFRow) rows.next());
				
			    
			  if(row.getRowNum()==0){
				  
				continue;
			  }
			  ConsumerDetailsEntity bankHistory = new ConsumerDetailsEntity();
				  String   txnId=row.getCell(1).getStringCellValue();
			       logger.info("transaction  id-----------------"+row.getCell(0));
			       System.out.println(txnId);
			       if (!StringUtils.isEmpty(txnId)) {
						bankHistory.setConsumerId(txnId);
					} else {
						logger.info("FAILED");
						i = 1;
					}

			       String   mid=row.getCell(2).getStringCellValue();
			       if (!StringUtils.isEmpty(mid)) {
						bankHistory.setFirst_Name(mid);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			       String  txn_Date =row.getCell(9).getStringCellValue();
			       if (txn_Date!=null) {
			    	   try{
			    	   Date date=formatter.parse(txn_Date);
						
						bankHistory.setInstall_Date(date);
			    	   }catch(Exception e){}
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   txn_Channel=row.getCell(3).getStringCellValue();
			       if (!StringUtils.isEmpty(txn_Channel)) {
						bankHistory.setLast_Name(txn_Channel);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   txn_Amount=row.getCell(8).getStringCellValue();
			       if (!StringUtils.isEmpty(txn_Amount)) {
						bankHistory.setAddress_Communication(txn_Amount);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   currency_Type=row.getCell(4).getStringCellValue();
			       if (!StringUtils.isEmpty(currency_Type)) {
						bankHistory.setCategory_Name(currency_Type);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   order_Id=row.getCell(5).getStringCellValue();
			       if (!StringUtils.isEmpty(order_Id)) {
						bankHistory.setMeter_Id(order_Id);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       
			       String   sanct_load=row.getCell(7).getStringCellValue();
			       if (!StringUtils.isEmpty(sanct_load)) {
			    	   int sanct=Integer.parseInt(sanct_load);
						bankHistory.setSanct_Load(sanct);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			     
			     
			       String   status=row.getCell(6).getStringCellValue();
			       if (!StringUtils.isEmpty(status)) {
						bankHistory.setStatus(status);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			      
			       String   txn_Updated_Date=row.getCell(10).getStringCellValue();
			       if(txn_Updated_Date!=null){
			        	 try{
			        		 bankHistory.setConnection_Date(formatter.parse(txn_Updated_Date));
			        	 }catch(Exception e){
			        		// e.printStackTrace();
			        	 }
			       }
					 else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       //String accountid=bankTxnHstyservice.getAccountID(cust_ID);
			       //System.out.println("accountid*******************"+accountid);
				   //  bankHistory.setAccount_Id(accountid);
				batchUpdates.add(bankHistory);
				System.out.println(batchUpdates);
				
				consumerDetailsService.save(bankHistory);
				
			}
			
			  }
	
	@Scheduled(cron = "${scheduling.job.prepaidLowBalanceAlert}")
	  public void escalationSheduler(){
		System.out.println("in side scheduling-------");
		EmailCredentialsDetailsBean emailCredentialsDetailsBean=null;
		  try {
		  emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
		  SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
		List<?> resultList=prePaidInstantDataService.SendingLowBalanceStatus();
		System.out.println(resultList);
		
		for(Iterator<?> iterator=resultList.iterator();iterator.hasNext();){
			
		     final Object[] value=(Object[]) iterator.next();
		     int instId=Integer.parseInt(value[0].toString());
		     System.out.println("id  === "+instId);
		    // String personMessage = "Dear "+""+", Your accounts has been "+"Activated"+". Your account details are username : "+""+" & password : "+""+" - Skyon RWA  Administration services.";	
		        double balance=Double.parseDouble(value[1].toString());
		        int personId=Integer.parseInt(value[3].toString());
		        String meterNo=value[2].toString();
		        String rechrg_amt=value[4].toString();
		        int propId=Integer.parseInt(value[5].toString());
		        System.out.println(balance+", "+personId+",  "+meterNo+", "+rechrg_amt+", "+propId);
		        String propertyNO=prepaidMeterService.getPropertyNo(propId);
		        String mobile1=null;
		        String email1=null;
		      //  Account account = accountService.find(accountId);
		  		Person person = personService.find(personId);
		  		Set<Contact> contactsList = person.getContacts();
		  		for (Contact contact : contactsList) {
		  			if(contact.getContactPrimary().equals("Yes")){
		  				if(contact.getContactType().equals("Email")){
		  					email1=contact.getContactContent();
		  				}else{
		  				mobile1=contact.getContactContent();
		  				
		  				}
		  			}
		  		}
		  		int personID=lowBalanceStoreService.getEstateManager();
		  		Person person1 = personService.find(personID);
		  		String email=null;
		  		String mob=null;
		  		Set<Contact> contactsList1 = person1.getContacts();
		  		for (Contact contact : contactsList1) {
		  			if(contact.getContactPrimary().equals("Yes")){
		  				if(contact.getContactType().equals("Email")){
		  					email=contact.getContactContent();
		  				}else{
		  					mob=contact.getContactContent();
		  				
		  				}
		  			}
		  		}
		  		
		      if((balance<=1000) && (balance>750)){
		    	  System.out.println("1 st attempt");
		    	 List<SmsEmailForLowBal> entry=lowBalanceStoreService.getAllData(meterNo, rechrg_amt);
		    	 if(entry.isEmpty()){
		  		SmsEmailForLowBal lowBal=new SmsEmailForLowBal();
		  		lowBal.setTxn_Id(rechrg_amt);
		  		lowBal.setMeterNo(meterNo);
		  		lowBal.setStatus("s1");
		  		lowBalanceStoreService.save(lowBal);
		  		String personMessage="Dear Resident,"+"<br/><br>"+"your prepaid Utility balance of Flat no. "+propertyNO+" is getting low. Kindly recharge at the earliest. your present Utility balance is Rs."+balance+"/-."+"-Skyon RWA Administration Services.";
		  		 //String personMessage = "Dear Resident,"+"<br/><br>"+"Your prepaid electricity balance is low. Kindly recharge at the earliest. Present available balance is Rs."+balance+"/-."+"-Skyon RWA  Administration services";
		  		 String personMessage1 = "Dear Resident,"
		  		                         +"<br><br>"
		  				                 +"your prepaid Utility balance of Flat no. "+propertyNO+" is getting low. Kindly recharge at the earliest. Present available balance is Rs."+balance+"/-."
		  		                         +"<br><br>"
		  				                 +"<br/>Best Regards,<br/>"
							             + "SCOWA, SKYON,<br/>"
		  				                 +"Sec 60, Gurgaon.<br/>"
							             +"<p><font size=1>This is an auto generated Email. Please don't revert back.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Serviced by Newage Living Solutions</font></p>"
							             +"</body></html>";	
		  		 
		  		sendLowBalanceAlertForCustomers(propertyNO,mobile1,email1,person,personMessage,personMessage1,emailCredentialsDetailsBean,smsCredentialsDetailsBean);
		  		sendLowBalanceAlertForEstateManager(propertyNO,mob,email,person1,personMessage,personMessage1,emailCredentialsDetailsBean,smsCredentialsDetailsBean);
		  		
		    	 }
		      }else if ((balance<=750) && (balance>500)) {
		    	  System.out.println("2 nd attempt");
		    	  List<SmsEmailForLowBal> entry=lowBalanceStoreService.getAllData1(meterNo, rechrg_amt);
		    	  if(entry.isEmpty()){
				  		SmsEmailForLowBal lowBal=new SmsEmailForLowBal();
				  		lowBal.setTxn_Id(rechrg_amt);
				  		lowBal.setMeterNo(meterNo);
				  		lowBal.setStatus("s2");
				  		lowBalanceStoreService.save(lowBal);
				  		String personMessage="Dear Resident,"+"<br><br>"+"your prepaid Utility balance of Flat no. "+propertyNO+" is getting low.  Kindly recharge at the earliest for uninterrupted power supply. Present available balance is Rs."+balance+"/-."+"-Skyon RWA  Administration services";
				  			                
				  		String personMessage1 = "Dear Resident,"
  		                                          +"<br><br>"
  				                                  +"your prepaid Utility balance of Flat no. "+propertyNO+" is getting low. Kindly recharge at the earliest for uninterrupted power supply. Present available balance is Rs."+balance+"/-."
  		                                          +"<br><br>"
  				                                  +"<br/>Best Regards,<br/>"
					                              + "SCOWA, SKYON,<br/>"
  				                                  +"Sec 60, Gurgaon.<br/>"
					                              +"<p><font size=1>This is an auto generated Email. Please don't revert back.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Serviced by Newage Living Solutions</font></p>"
					                              +"</body></html>";	
				  		sendLowBalanceAlertForCustomers(propertyNO,mobile1,email1,person,personMessage,personMessage1,emailCredentialsDetailsBean,smsCredentialsDetailsBean);
				  		sendLowBalanceAlertForEstateManager(propertyNO,mob,email,person1,personMessage,personMessage1,emailCredentialsDetailsBean,smsCredentialsDetailsBean);
				    	 }
			}else if(balance<=500){
				  System.out.println("3rd attempt");
				 List<SmsEmailForLowBal> entry=lowBalanceStoreService.getAllData2(meterNo, rechrg_amt);
				 if(entry.isEmpty()){
				  		SmsEmailForLowBal lowBal=new SmsEmailForLowBal();
				  		lowBal.setTxn_Id(rechrg_amt);
				  		lowBal.setMeterNo(meterNo);
				  		lowBal.setStatus("s3");
				  		lowBalanceStoreService.save(lowBal);
				  		String personMessage="Dear Resident,"+"<br><br>"+"your prepaid Utility balance of Flat no. "+propertyNO+" is getting low. Kindly recharge at the earliest for uninterrupted power supply. Present available balance is Rs."+balance+"/-."+"-Skyon RWA  Administration services";
				  			                
				  		 String personMessage1 = "Dear Resident,"
  		                                        +"<br><br>"
  				                                +"your prepaid Utility balance of Flat no. "+propertyNO+" is getting low. Kindly recharge at the earliest for uninterrupted power supply. Present available balance is Rs."+balance+"/-."
  		                                        +"<br><br>"
  				                                +"<br/>Best Regards,<br/>"
					                            + "SCOWA, SKYON,<br/>"
  				                                +"Sec 60, Gurgaon.<br/>"
					                            +"<p><font size=1>This is an auto generated Email. Please don't revert back.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Serviced by Newage Living Solutions</font></p>"
					                            +"</body></html>";	
				  		sendLowBalanceAlertForCustomers(propertyNO,mobile1,email1,person,personMessage,personMessage1,emailCredentialsDetailsBean,smsCredentialsDetailsBean);
				  		sendLowBalanceAlertForEstateManager(propertyNO,mob,email,person1,personMessage,personMessage1,emailCredentialsDetailsBean,smsCredentialsDetailsBean);
				    	 }
			}
		       
		      
		}
	}
	

	private void sendLowBalanceAlertForEstateManager(String propertyNO, String mob, String email,
			Person person1,String personMessage,String personMessage1,EmailCredentialsDetailsBean emailCredentialsDetailsBean,SmsCredentialsDetailsBean smsCredentialsDetailsBean) {
		logger.info("ïn side sendLowBalanceAlertForEstateManager methode");
		System.err.println("email "+email +"            "+"mob  "+mob);
		smsCredentialsDetailsBean.setNumber(mob);
  		smsCredentialsDetailsBean.setUserName(person1.getFirstName());
  		smsCredentialsDetailsBean.setMessage(personMessage);
  		new Thread(new SendSMSForStatus(smsCredentialsDetailsBean)).start();
  		emailCredentialsDetailsBean.setMailSubject("SCOWA SKYON");
	    emailCredentialsDetailsBean.setToAddressEmail(email);
		emailCredentialsDetailsBean.setMessageContent(personMessage1);
		new Thread(new SendMailForOwnersDetails(emailCredentialsDetailsBean)).start();
	}




	private void sendLowBalanceAlertForCustomers(String propertyNO, String mobile1, String email1,
			Person person,String personMessage,String personMessage1,EmailCredentialsDetailsBean emailCredentialsDetailsBean,SmsCredentialsDetailsBean smsCredentialsDetailsBean) {
		logger.info("in side sendLowBalanceAlertForCustomers methode");
		
		System.out.println("mobile Number "+mobile1+"   "+" emailId "+email1);
			smsCredentialsDetailsBean.setNumber(mobile1);
	  		smsCredentialsDetailsBean.setUserName(person.getFirstName());
	  		smsCredentialsDetailsBean.setMessage(personMessage);
	  		new Thread(new SendSMSForStatus(smsCredentialsDetailsBean)).start();
	  		emailCredentialsDetailsBean.setMailSubject("SCOWA SKYON");
		    emailCredentialsDetailsBean.setToAddressEmail(email1);
			emailCredentialsDetailsBean.setMessageContent(personMessage1);
			new Thread(new SendMailForOwnersDetails(emailCredentialsDetailsBean)).start();
		   
	  		
		
	}

	@RequestMapping(value = "/prepaidDataprops/getPropertyNos", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<?> getPropertyIdsforBill(HttpServletRequest req) {
		int blockId = Integer.parseInt(req.getParameter("blockId"));

		return prepaidMeterService.getGenusData(blockId);
	}


	@RequestMapping(value="/consumptionHistory/readData",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> readLedgerData(HttpServletRequest request) throws ParseException{
		logger.info("in side Ledger Read Methode");
	
	System.out.println("From Date  === "+request.getParameter("fromDate"));
	System.out.println("To Date === "+request.getParameter("toDate"));
	System.out.println("consumerId : "+request.getParameter("consumerID"));
	String fromDate= request.getParameter("fromDate");
	String presentdate=request.getParameter("toDate");
	SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
	Date todate = formatter.parse(presentdate);
	Date fromdate=formatter.parse(fromDate);
	System.out.println("date from to date"+fromDate+" "+presentdate);
	List<Map<String,Object>> maplist=new ArrayList<Map<String,Object>>();
	List<Object[]> dailyData=new ArrayList<>();
	dailyData=prePaidInstantDataService.getConsumption(fromdate,todate,request.getParameter("consumerID"));
	System.out.println("list size++++++++++"+dailyData.size());
	Map<String,Object> map=null;
	for (Object[] prePaidDailyData:dailyData) {
		System.out.println("12");
		map=new HashMap<>();
		map.put("prePaidId",prePaidDailyData[0]);
		map.put("mtrSrNo",prePaidDailyData[1]);
		map.put("readingDT", prePaidDailyData[2]);
		map.put("balance", prePaidDailyData[3]);
		map.put("daily_Cons_Amt", prePaidDailyData[4]);
		map.put("cum_Fixed_Charg_Main", prePaidDailyData[5]);
		map.put("cum_Fixed_Charg_Dg", prePaidDailyData[6]);
		map.put("cum_Recharge_Amount", prePaidDailyData[7]);
		map.put("cum_Kwh_Main", prePaidDailyData[8]);
		map.put("cum_Kwh_Dg", prePaidDailyData[9]);
		maplist.add(map);
	}
		return  maplist;
	}
	
	@RequestMapping(value="/getconsumptionHistirySearchByMonth",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> getConsumptionDayWiseForAll(HttpServletRequest request) throws ParseException{
		System.out.println("From Date  === "+request.getParameter("fromDate"));
		System.out.println("To Date === "+request.getParameter("toDate"));
		String fromDate= request.getParameter("fromDate");
		String presentdate=request.getParameter("toDate");
		List<Map<String,Object>> maplist=new ArrayList<Map<String,Object>>();
		List<Object[]> dailyData=new ArrayList<>();
		dailyData=prePaidInstantDataService.getConsumptionDayWise(fromDate,presentdate);
		System.out.println("list size++++++++++"+dailyData.size());
		Map<String,Object> map=null;
		for (Object[] prePaidDailyData:dailyData) {
			System.out.println("12");
			map=new HashMap<>();
			map.put("prePaidId",prePaidDailyData[0]);
			map.put("mtrSrNo",prePaidDailyData[1]);
			map.put("readingDT", prePaidDailyData[2]);
			map.put("balance", prePaidDailyData[3]);
			map.put("daily_Cons_Amt", prePaidDailyData[4]);
			map.put("cum_Fixed_Charg_Main", prePaidDailyData[5]);
			map.put("cum_Fixed_Charg_Dg", prePaidDailyData[6]);
			map.put("cum_Recharge_Amount", prePaidDailyData[7]);
			map.put("cum_Kwh_Main", prePaidDailyData[8]);
			map.put("cum_Kwh_Dg", prePaidDailyData[9]);
			maplist.add(map);
		}
			return  maplist;
	}
	
	@RequestMapping(value="/getGenusConsumption",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> getGenusConsumption(HttpServletRequest request) throws ParseException{
		System.out.println("From Date  === "+request.getParameter("fromDate"));
		System.out.println("To Date === "+request.getParameter("toDate"));
		System.out.println("propertyID === "+request.getParameter("propertyId"));
		//System.out.println("meterNo === "+request.getParameter("meterNo"));
		String fromDate= request.getParameter("fromDate");
		String presentdate=request.getParameter("toDate");
		//String meterNum=request.getParameter("meterNo");
		String propertyId=request.getParameter("propertyId");
		logger.info("::::::blockId::"+null+":::::::::blockName:::"+request.getParameter("blockName")+"::::::propertyId::::"+propertyId);

		//List<Map<String,Object>> maplist=new ArrayList<Map<String,Object>>();
		String comma = ",";
		String[] trancode = propertyId.split(comma);
		for (int j = 0; j < trancode.length; j++) {
			int propid = Integer.parseInt(trancode[j]);
			String mtrNo=prepaidMeterService.getMtrNo(propid);

			//*************************************************************
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date startDate = sdf.parse(fromDate);
				Date endDate   = sdf.parse(presentdate);
				
				System.out.println("startDate="+startDate+"  & endDate="+endDate);
				
				Calendar start = Calendar.getInstance();
				start.setTime(startDate);
				//start.add(Calendar.DATE, 1);
				
				Calendar end = Calendar.getInstance();
				end.setTime(endDate);
				//end.add(Calendar.DATE, -1);
				
				System.out.println("startDate="+start.getTime()+"  & endDate="+end.getTime());
			//*************************************************************
			
			for (Date date1 = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date1 = start.getTime()) 
			{
				String date=new SimpleDateFormat("dd/MM/yyyy").format(date1);
				System.out.println("DATE       "+date);
				long count=prePaidInstantDataService.checkDataExistence(date,mtrNo+"");
				if(count==0)
				{System.out.println(count);
			//*************************************************************
					JAXBContext jaxbContext;
				  try {
				
				long d1=81;
				IREOService s1=new IREOService();
				
				String data	=s1.getBasicHttpBindingIService1().dailyData("1", date, "harjeet", "ireo@123", d1,mtrNo,date,date);
			    // System.out.println(data);
				jaxbContext = JAXBContext.newInstance(DocumentElementData.class);
				  Unmarshaller jaxUnmarshaller = jaxbContext.createUnmarshaller();
			      InputSource inputSource = new InputSource(new ByteArrayInputStream(data.getBytes()));
			       inputSource.setEncoding("UTF-8");
			      DocumentElementData tallyResponse = (DocumentElementData) jaxUnmarshaller.unmarshal(inputSource); 
			       //System.out.println(tallyResponse);
			        List<DailyData> list=tallyResponse.getInstantDatas();
			        System.out.println(list);
			        
			        for (DailyData dailyData:list) {
				    	   double rechargeAmt=0.0;
				    	   PrePaidDailyData prePaidDailyData1=prePaidInstantDataService.getMaxReadingDate(dailyData.getMtrSrNo());
				    	   PrePaidDailyData prDailyData=new PrePaidDailyData();
				    	   prDailyData.setMtrSrNo(dailyData.getMtrSrNo());
				    	   prDailyData.setBalance(dailyData.getBalance());
				    	   prDailyData.setDaily_Cons_Amt(dailyData.getDaily_Consum_Amount());
				    	   prDailyData.setReadingDT(dailyData.getReadingDT());
				    	   prDailyData.setCum_Fixed_Charg_Main(dailyData.getCum_FixChrg_Main());
				    	   prDailyData.setCum_Fixed_Charg_Dg(dailyData.getCum_FixChrg_Dg());
				    	   prDailyData.setCum_Recharge_Amount(dailyData.getCum_Recharge_Amount());
				    	   prDailyData.setCum_Kwh_Main(dailyData.getCum_kWh_Main());
				    	   prDailyData.setCum_Kwh_Dg(dailyData.getCum_kWh_Dg());
				    	  // String prePaidDailyData1.getCum_Fixed_Charg_Main()="0.0";
				    	   if(prePaidDailyData1!=null){
				    	    
				    	    rechargeAmt=Math.ceil(Double.parseDouble(dailyData.getCum_Recharge_Amount()))-Math.ceil(Double.parseDouble(prePaidDailyData1.getCum_Recharge_Amount()));

				    	   }else{
				    		 
				    		  rechargeAmt=Double.parseDouble(dailyData.getCum_Recharge_Amount());
				    	   }
				    	  
				    	  
				    	   prDailyData.setCum_Recharge_Amount1(new DecimalFormat("##.##").format(rechargeAmt));
						   prePaidInstantDataService.save(prDailyData);
				    	
					}
			   
			}catch(Exception e){
				e.printStackTrace();
			}
		 }
			}
		}
			return  null;
	}
	
	@RequestMapping(value = "/consumptionHistory/exportConsumptionData", method = {RequestMethod.POST,RequestMethod.GET})
	   public String exportConsumptionHistoryFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
			
			
		 logger.info("hi_________________________----------------");
	 
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
	        
	 System.out.println("FromDate "+request.getParameter("date1"));      
	 System.out.println( "ToDate "+ request.getParameter("date2"));   
	 String fromDate= request.getParameter("date1");
		String presentdate=request.getParameter("date2");
		List<Object[]> dailyData=new ArrayList<>();
		dailyData=prePaidInstantDataService.getConsumptionDayWise(fromDate,presentdate);
		System.out.println("list size++++++++++"+dailyData.size());
		
	               
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
	    	
	        header.createCell(0).setCellValue("Meter Number");
	        header.createCell(1).setCellValue("Reading Date");
	        header.createCell(2).setCellValue("Balance");
	        header.createCell(3).setCellValue("Daily Consumed Amount");
	        header.createCell(4).setCellValue("Cum. kWh");
	        header.createCell(5).setCellValue("Cum.kWh(DG)");
	        header.createCell(6).setCellValue("Cum FixChgMain");
	        header.createCell(7).setCellValue("Cum FixChgDg");
	     
	       
	       
	        for(int j = 0; j<=7; j++){
	    		header.getCell(j).setCellStyle(style);
	            sheet.setColumnWidth(j, 8000); 
	            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:Q200"));
	        }
	        
	        int count = 1;
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm ");
	        for(Object[] prePaidDailyData:dailyData){
	 	      // final Object[] values=(Object[]) iterator.next();
	    		
	    		XSSFRow row = sheet.createRow(count);
	    		
	    		 if(prePaidDailyData[1]!=null){
	    			
	    		row.createCell(0).setCellValue((String)prePaidDailyData[1]);
	    		 }
	    		  if(prePaidDailyData[2]!=null){
	    			 
	    		row.createCell(1).setCellValue((String)prePaidDailyData[2]);
	    		
	    		  }
	    		 
	    		  if(prePaidDailyData[3]!=null){
		    			 
	  	    		row.createCell(2).setCellValue((String)prePaidDailyData[3]);
	  	    		
	  	    		  }
	    		  if(prePaidDailyData[4]!=null){
		    			 
	  	    		row.createCell(3).setCellValue((String)prePaidDailyData[4]);
	  	    		
	  	    		  }
	    		  if(prePaidDailyData[8]!=null){
		    			 
	  	    		row.createCell(4).setCellValue((String)prePaidDailyData[8]);
	  	    		
	  	    		  }
	    		  if(prePaidDailyData[9]!=null){
		    			 
	  	    		row.createCell(5).setCellValue((String)prePaidDailyData[9]);
	  	    		
	  	    		  }
	    		  if(prePaidDailyData[5]!=null){
		    			 
	  	    		row.createCell(6).setCellValue((String)prePaidDailyData[5]);
	  	    		
	  	    		  }
	    		  if(prePaidDailyData[6]!=null){
		    			 
	  	    		row.createCell(7).setCellValue((String)prePaidDailyData[6]);
	  	    		
	  	    		  }
	    		  
	    		count ++;
			}
	    	
	    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
	    	wb.write(fileOut);
	    	fileOut.flush();
	    	fileOut.close();
	        
	        ServletOutputStream servletOutputStream;

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Consumption History.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();
			
			return null;
		}
	
	@RequestMapping(value="/consumptionHistory/exportPDFConsumptionData",method={RequestMethod.POST,RequestMethod.GET})
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
	     
	     /* com.bcits.bfm.util.HeaderFooterPageEvent event=new com.bcits.bfm.util.HeaderFooterPageEvent();
	      writer.setPageEvent("");*/
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
	        		"Meter Number     :  "+consumerId,new Font(Font.FontFamily.HELVETICA, 8));
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
	        
	    	
	        
	        table.addCell(new Paragraph("Meter Number",font1));
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

}
