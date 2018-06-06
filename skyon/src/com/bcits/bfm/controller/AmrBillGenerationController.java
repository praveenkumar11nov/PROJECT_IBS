package com.bcits.bfm.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jettison.json.JSONException;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.amr.service.AMREntityService;
import com.bcits.bfm.amr.service.AMRPropertyService;
import com.bcits.bfm.model.AMRDataEntity;
import com.bcits.bfm.model.ELRateMaster;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityAMRBillService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceMasterService;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;

@Controller
public class AmrBillGenerationController {

	static Logger logger = LoggerFactory.getLogger(AmrBillGenerationController.class);
	
	@Autowired
	TariffCalculationService tariffCalculationService;
	
	@Autowired
	ServiceMasterService serviceMasterService;
	
	@Autowired
	AMRPropertyService amrService;
	
	@Autowired
	ElectricityAMRBillService amrBillService;
	
	@Autowired
	 BillGenerationController billGenerationController;
	
	
	@Autowired
	private AMREntityService amrEntityService;

	
	
	@RequestMapping(value = "/billAmr/getPropertyNo", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<?> getPropertyId(HttpServletRequest req) {
		int blockId = Integer.parseInt(req.getParameter("blockId"));

		return tariffCalculationService.findPropertyNoAmr(blockId);
	}
	@RequestMapping(value = "/bill/generateAmrBill", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public HashMap<Object, Object> calculateTariff(HttpServletRequest req,
			HttpServletResponse response, @RequestBody String body)
			throws ParseException, JSONException, IOException {
		logger.info("In Amr Bill Generation Method");
		int blocId=Integer.parseInt(req.getParameter("blocId"));
		String blockName=req.getParameter("blockName");
		Date presentdate = new SimpleDateFormat("dd/MM/yyyy").parse(req.getParameter("presentdate"));
		String propertyId = req.getParameter("propertyId");
		String columnName =null;
		
		String tower =null;
		String tableName = null;
		String tableNAmeDg=null;
		String propertyNo="";
		PrintWriter out;
		
		logger.info("::::::blockId::"+blocId+":::::::::blockName:::"+blockName+":::::::::::presentdate::::"+presentdate+"::::::propertyId::::"+propertyId);
		String serviceTypeList="Electricity";
		
		int accountId = 0;
		Date getbilldate = null;
		Integer billableDays = 0;
		String accountNo = null;
		int propId = 0;
		
	    Float previousraeding=0f;
		float meterconstant=0f;
		String dgApplicable="No";
		String todApplicable="No";
		float dgmeterConstant=0f;
		float dgpreciousreadiong=0f;
		String previousStatus=null;
		Integer blockI=null;
		String blockN=null;
		try {
			if (blockName.equals("All Blocks")) {
				
				//Map<String,Object> blockdetail=tariffCalculationService.getallBlocksAmr();
				List<Map<String, Object>> blockdetail = tariffCalculationService.getallBlocksAmr();

				//for (Entry<String, Object> blok : blockdetail.entrySet()) {
					for (Iterator iterator = blockdetail.iterator(); iterator
							.hasNext();) {
						Map<String, Object> map = (Map<String, Object>) iterator
								.next();
						
						for (Entry<String, Object> blok : map.entrySet()) {

					if (blok.getKey().equals("blockId")) {
						blockI = (Integer) blok.getValue();
					}
					if (blok.getKey().equals("blockName")) {
						blockN = (String) blok.getValue();
					}
						}
			        logger.info("blockI::::::"+blockI+"::::::::::blockName"+blockN);
					tower =	blockN.substring(blockN.length() - 1); 
					logger.info("tower ========= "+tower);
				       tableName = "Tower_"+tower+"_EB_kwh";
				       tableNAmeDg="Tower_"+tower+"_DG_kwh";
				       logger.info("tableName ====== "+tableName);
					logger.info("::::::blockId:::::::" + blockI);
					List<Integer> properties = tariffCalculationService.getProertyId(blockI);
					for (Integer integer : properties) {
						propId = integer;
						logger.info("::::::::::propId::::::::" + propId);
						columnName = amrService.getColumnName(blocId,propId);
						logger.info("columnName =========================== "+columnName);					
						Integer accountIdOnProp=tariffCalculationService.getAccountIdOnPropertyId(propId,serviceTypeList);
							logger.info("------------accountId-----------"+ accountIdOnProp);
							if (accountIdOnProp != 0) {
								accountNo = tariffCalculationService.getAccontNoONAccountId(accountIdOnProp);
								getbilldate = tariffCalculationService.getBillDate(accountIdOnProp, serviceTypeList);
								if (getbilldate != null) {
									LocalDate fromDate = new LocalDate(getbilldate);
									LocalDate toDate = new LocalDate(presentdate);
									billableDays = Days.daysBetween(fromDate, toDate).getDays();
									logger.info("::::::::::::::Billable Days::::::::::"+ billableDays);
									if (billableDays  > 0) {
										String currentBillStatus = tariffCalculationService.getPreviousBillStatus(accountIdOnProp,serviceTypeList,presentdate);
										logger.info("::::::::::::::::currentBillStatus:::::::::"+ currentBillStatus);
										if (currentBillStatus
												.equalsIgnoreCase("Posted")
												|| currentBillStatus
														.equalsIgnoreCase("Cancelled")
												|| currentBillStatus
														.equalsIgnoreCase("Not Generated")) {
											Map<Object, Object> previousVal = tariffCalculationService
													.getPreviousBillReadingAmr(
															accountIdOnProp,
															serviceTypeList);
											for (Entry<Object, Object> entry : previousVal
													.entrySet()) {

												logger.info(entry.getKey() + "/"
														+ entry.getValue());

												logger.info(entry.getKey() + "/"
														+ entry.getValue());

												if (entry.getKey().equals(
														"InitialReading")) {

													String previou = (String) entry.getValue();
													previousraeding = Float.parseFloat(previou);

													logger.info("previousraeding:::::::::::"
															+ previou
															+ ":::::::::::"
															+ previousraeding);
												} else if (entry.getKey().equals(
														"MeterConstant")) {

													String metercon = (String) entry
															.getValue();
													meterconstant = Float
															.parseFloat(metercon);
													logger.info(":::::::::::meterconstant:::::::::::"
															+ meterconstant);
												} else if (entry.getKey().equals(
														"previousStatus")) {
													previousStatus = (String) entry
															.getValue();
													logger.info(":::::::::::previousStatus:::::::::::"
															+ previousStatus);
												} else if (entry.getKey().equals(
														"dgapplicable")) {
													dgApplicable = (String) entry
															.getValue();
													if(dgApplicable.equalsIgnoreCase("Yes")){
														if (entry.getKey().equals("DGInitialReading")) {
															String dgInitialReading = (String) entry.getValue();
															dgpreciousreadiong = Float.parseFloat(dgInitialReading);
															logger.info(":::::::::::meterconstant:::::::::::"+ dgInitialReading);
														}
														if (entry.getKey().equals("DGMeterConstant")) {

															String dgMeterConstant = (String) entry.getValue();
															dgmeterConstant = Float.parseFloat(dgMeterConstant);
															logger.info(":::::::::::meterconstant:::::::::::"+ dgmeterConstant);
														}
													}
												} else if (entry.getKey().equals("todApplicable")) {
													todApplicable = (String) entry.getValue();
													logger.info(":::::::::::todApplicable:::::::::::"+ todApplicable);
												}
											}

										} else {
											
												out = response.getWriter();
												out.write("Bill For "
														+ accountNo
														+ " has not been generated because bill status "
														+ currentBillStatus
														+ "/n");
											

										}
										
										List<ELRateMaster> elRateMasterList = new ArrayList<>();
										int tariffId = serviceMasterService.getServiceMasterByAccountNumber(accountIdOnProp,serviceTypeList);
										float presentUOMReading = amrBillService.getPresentReading(tableName,columnName, getbilldate,presentdate);
										float presentDGreading=amrBillService.getPresentDGReading(tableNAmeDg, columnName, getbilldate, presentdate);
										
										logger.info(":::::::::::::::presentDGreading::::::::::::::::"+presentDGreading);
										logger.info("presentUOMReading:::::::::::::::"+presentUOMReading+":::::::::::previousraeding"+previousraeding);
										if (presentUOMReading < previousraeding) {
												out = response.getWriter();
												out.write("Bill For "+ accountNo+ " has not been generated because Previous Reading Is Greater Than Present Reading "+ "\n");
										}else{
											float uomValue = presentUOMReading
													- previousraeding;
											float dgUnit=presentDGreading-dgpreciousreadiong;
											elRateMasterList = tariffCalculationService
													.getElectricityRateMaster(tariffId);
											logger.info("::::::::::::presentUOMReading::::::::::"
													+ presentUOMReading);
											logger.info(":::::::::::: uomValue::::::::::"
													+ uomValue);
											billGenerationController.saveElectricityBillParameters(
															elRateMasterList,
															accountIdOnProp,
															serviceTypeList,
															getbilldate,
															presentdate, uomValue,
															billableDays, "Normal",
															"Normal",
															meterconstant,
															presentUOMReading,
															previousraeding, 0, 0,
															0, dgUnit,todApplicable, dgApplicable,
															 dgpreciousreadiong, presentDGreading,
															dgmeterConstant, 0d, 0, "Normal", 0,
															0, "Bill", "0");	
										}
									}
								}else{
									logger.info("Service For That Account has Not been activated");
								}
							
							}else{
								logger.info("Service For That Account Is Not There");
							}
						//}

					}
				
				}
			}

			else if (propertyId.equals("0")) {//One Block All Property
				logger.info("@@@@@@@@@@@@@@@@@In All Proerty method @@@@@@@@@@@@@@@@@@@@@@-"+ propertyId);
				tower =	blockName.substring(blockName.length() - 1); 
				logger.info("tower ========= "+tower);
			       tableName = "Tower_"+tower+"_EB_kwh";
			       tableNAmeDg="Tower_"+tower+"_DG_kwh";
			       logger.info("tableName ====== "+tableName);
				List<Integer> properties = tariffCalculationService.getProertyId(blocId);
				logger.info("properties.size:::"+properties.size());
				for (Integer integer : properties) {
					propId = integer;
					logger.info("::::::::::propId::::::::" + propId);
					columnName = amrService.getColumnName(blocId,propId);
					logger.info("columnName =========================== "+columnName);				
					if (columnName == null){
						propertyNo=tariffCalculationService.getpropertyNoOnPropertyId(propId);
						out = response.getWriter();
						out.write("Property No. "+propertyNo +" has not been Integrated With Amr Setting"+ "\n");	
					}
					else{
						Integer accountIdOnProp = tariffCalculationService
								.getAccountIdOnPropertyId(propId, serviceTypeList);
						if (accountIdOnProp != 0) {
							accountNo = tariffCalculationService
									.getAccontNoONAccountId(accountIdOnProp);
							getbilldate = tariffCalculationService.getBillDate(
									accountIdOnProp, serviceTypeList);
							logger.info("###:::::::::::::::::::::::::"
									+ getbilldate + "::::::::::::##########");
							if (getbilldate != null) {
								LocalDate fromDate = new LocalDate(getbilldate);
								LocalDate toDate = new LocalDate(presentdate);
								billableDays = Days.daysBetween(fromDate, toDate)
										.getDays();
								logger.info("::::::::::::::Billable Days::::::::::"
										+ billableDays);
								if (billableDays > 0) {

									logger.info("::::::::::::getbilldate:::::::::::"
											+ getbilldate);
									String currentBillStatus = tariffCalculationService
											.getPreviousBillStatus(accountIdOnProp,
													serviceTypeList, presentdate);
									if (currentBillStatus
											.equalsIgnoreCase("Posted")
											|| currentBillStatus
													.equalsIgnoreCase("Cancelled")
											|| currentBillStatus
													.equalsIgnoreCase("Not Generated")) {
										Map<Object, Object> previousVal = tariffCalculationService
												.getPreviousBillReadingAmr(
														accountIdOnProp,
														serviceTypeList);
										for (Entry<Object, Object> entry : previousVal
												.entrySet()) {
											logger.info(entry.getKey() + "/"
													+ entry.getValue());

											if (entry.getKey().equals(
													"InitialReading")) {

												String previou = (String) entry
														.getValue();
												previousraeding = Float
														.parseFloat(previou);

												logger.info("previousraeding:::::::::::"
														+ previou
														+ ":::::::::::"
														+ previousraeding);
											}
											if (entry.getKey().equals(
													"MeterConstant")) {

												String metercon = (String) entry
														.getValue();
												meterconstant = Float
														.parseFloat(metercon);
												logger.info(":::::::::::meterconstant:::::::::::"
														+ meterconstant);
											}
											if (entry.getKey().equals(
													"previousStatus")) {
												previousStatus = (String) entry
														.getValue();
												logger.info(":::::::::::previousStatus:::::::::::"
														+ previousStatus);
											}
											if (entry.getKey().equals(
													"dgapplicable")) {
												dgApplicable = (String) entry
														.getValue();
												logger.info(":::::::::::dgapplicable:::::::::::"+ dgApplicable);
												if(dgApplicable.equalsIgnoreCase("Yes")){
													if (entry.getKey().equals(
															"DGInitialReading")) {
														
														
														String dgInitialReading = (String) entry
																.getValue();
														dgpreciousreadiong = Float
																.parseFloat(dgInitialReading);
														logger.info(":::::::::::meterconstant:::::::::::"
																+ dgInitialReading);
													}
													if (entry.getKey().equals(
															"DGMeterConstant")) {

														String dgMeterConstant = (String) entry
																.getValue();
														dgmeterConstant = Float
																.parseFloat(dgMeterConstant);
														logger.info(":::::::::::meterconstant:::::::::::"
																+ dgmeterConstant);
													}
												}
											}
											if (entry.getKey().equals(
													"todApplicable")) {
												todApplicable = (String) entry
														.getValue();
												logger.info(":::::::::::todApplicable:::::::::::"
														+ todApplicable);
											}
										}
										List<ELRateMaster> elRateMasterList = new ArrayList<>();
										int tariffId = serviceMasterService
												.getServiceMasterByAccountNumber(
														accountIdOnProp,
														serviceTypeList);
										float presentUOMReading = amrBillService
												.getPresentReading(tableName,
														columnName, getbilldate,
														presentdate);
										float presentDGreading=amrBillService.getPresentDGReading(tableNAmeDg, columnName, getbilldate, presentdate);
										logger.info(":::::::::::::::presentDGreading::::::::::::::::"+presentDGreading);
										if (presentUOMReading < previousraeding) {
											try {
												out = response.getWriter();
												out.write("Bill For "
														+ accountNo
														+ " has not been generated because Previous Reading Is Greater Than Present Reading "
														+ "\n");
											} catch (IOException e) {
												e.printStackTrace();
											}
										} else {
											float uomValue = presentUOMReading
													- previousraeding;
											float dgUnit=presentDGreading-dgpreciousreadiong;
											elRateMasterList = tariffCalculationService
													.getElectricityRateMaster(tariffId);
											logger.info("::::::::::::presentUOMReading::::::::::"
													+ presentUOMReading);
											logger.info(":::::::::::: uomValue::::::::::"
													+ uomValue);
											billGenerationController.saveElectricityBillParameters(
													elRateMasterList,
													accountIdOnProp,
													serviceTypeList,
													getbilldate,
													presentdate, uomValue,
													billableDays, "Normal",
													"Normal",
													meterconstant,
													presentUOMReading,
													previousraeding, 0, 0,
													0,  dgUnit,todApplicable, dgApplicable,
													 dgpreciousreadiong, presentDGreading,
													dgmeterConstant, 0d, 0, "Normal", 0,
													0, "Bill", "0");
										}

									} else {

										out = response.getWriter();
										out.write("Bill For "
												+ accountNo
												+ " has not been generated because bill status "
												+ currentBillStatus + "\n");

									}
								}
							} else {
								out = response.getWriter();
								out.write("Service for " + accountNo
										+ " has not been started" + "\n");
							}
						} else {
							logger.info("Account for this service is not there");
						}
						//}
					}
				}
			} else {//
				tower =	blockName.substring(blockName.length() - 1); 
				logger.info("tower ========= "+tower);
			       tableName = "Tower_"+tower+"_EB_kwh";
			       tableNAmeDg="Tower_"+tower+"_DG_kwh";
			       logger.info("tableName ====== "+tableName);
				String comma = ",";
				String[] trancode = propertyId.split(comma);
				Integer personId = 0;
				
				
				for (int i = 0; i < trancode.length; i++) {
					int propid = Integer.parseInt(trancode[i]);
					columnName = amrService.getColumnName(blocId,propid);
					logger.info("columnName =========================== "+columnName);
					if (columnName == null){
						propertyNo=tariffCalculationService.getpropertyNoOnPropertyId(propid);
						out = response.getWriter();
						out.write("Property No. "+propertyNo +" has not been Integrated With Amr Setting"+ "\n");
						
					}
						else {
						//List<Integer> personIdList = tariffCalculationService.findPersonDetail(propid);
						Integer accountIdOnProp = tariffCalculationService
								.getAccountIdOnPropertyId(propid, serviceTypeList);
						if (accountIdOnProp != 0) {
							accountNo = tariffCalculationService
									.getAccontNoONAccountId(accountIdOnProp);
							getbilldate = tariffCalculationService.getBillDate(
									accountIdOnProp, serviceTypeList);
							logger.info("###:::::::::::::::::::::::::"
									+ getbilldate + "::::::::::::##########");
							if (getbilldate != null) {
								LocalDate fromDate = new LocalDate(getbilldate);
								LocalDate toDate = new LocalDate(presentdate);
								billableDays = Days.daysBetween(fromDate, toDate)
										.getDays();
								logger.info("::::::::::::::Billable Days::::::::::"
										+ billableDays);
								if (billableDays > 0) {

									logger.info("::::::::::::getbilldate:::::::::::"
											+ getbilldate);
									String currentBillStatus = tariffCalculationService
											.getPreviousBillStatus(accountIdOnProp,
													serviceTypeList, presentdate);
									if (currentBillStatus
											.equalsIgnoreCase("Posted")
											|| currentBillStatus
													.equalsIgnoreCase("Cancelled")
											|| currentBillStatus
													.equalsIgnoreCase("Not Generated")) {
										Map<Object, Object> previousVal = tariffCalculationService
												.getPreviousBillReadingAmr(
														accountIdOnProp,
														serviceTypeList);
										for (Entry<Object, Object> entry : previousVal
												.entrySet()) {
											logger.info(entry.getKey() + "/"
													+ entry.getValue());

											if (entry.getKey().equals(
													"InitialReading")) {

												String previou = (String) entry
														.getValue();
												previousraeding = Float
														.parseFloat(previou);

												logger.info("previousraeding:::::::::::"
														+ previou
														+ ":::::::::::"
														+ previousraeding);
											}
											if (entry.getKey().equals(
													"MeterConstant")) {

												String metercon = (String) entry
														.getValue();
												meterconstant = Float
														.parseFloat(metercon);
												logger.info(":::::::::::meterconstant:::::::::::"
														+ meterconstant);
											}
											if (entry.getKey().equals(
													"previousStatus")) {
												previousStatus = (String) entry
														.getValue();
												logger.info(":::::::::::previousStatus:::::::::::"
														+ previousStatus);
											}
											if (entry.getKey().equals(
													"dgapplicable")) {
												dgApplicable = (String) entry
														.getValue();
												logger.info("::::::::::::dgApplicable::::::::::::::::"+dgApplicable);
											
											}
											if(dgApplicable.equalsIgnoreCase("Yes")){
												logger.info(":::::::::::::::Dg Appliable Block::::::::::::");
												if (entry.getKey().equals(
														"DGInitialReading")) {
													
													
													String dgInitialReading = (String) entry
															.getValue();
													dgpreciousreadiong = Float
															.parseFloat(dgInitialReading);
													logger.info(":::::::::::dgpreciousreadiong:::::::::::"
															+ dgInitialReading);
												}
												if (entry.getKey().equals(
														"DGMeterConstant")) {

													String dgMeterConstant = (String) entry
															.getValue();
													dgmeterConstant = Float
															.parseFloat(dgMeterConstant);
													logger.info(":::::::::::meterconstant:::::::::::"
															+ dgmeterConstant);
												}
											}
											if (entry.getKey().equals(
													"todApplicable")) {
												todApplicable = (String) entry
														.getValue();
												logger.info(":::::::::::todApplicable:::::::::::"
														+ todApplicable);
											}
										}
										List<ELRateMaster> elRateMasterList = new ArrayList<>();
										int tariffId = serviceMasterService
												.getServiceMasterByAccountNumber(
														accountIdOnProp,
														serviceTypeList);
										float presentUOMReading = amrBillService
												.getPresentReading(tableName,
														columnName, getbilldate,
														presentdate);
										float presentDGreading=amrBillService.getPresentDGReading(tableNAmeDg, columnName, getbilldate, presentdate);
										logger.info(":::::::::::::::presentDGreading::::::::::::::::"+presentDGreading);
										if (presentUOMReading < previousraeding) {
											try {
												out = response.getWriter();
												out.write("Bill For "
														+ accountNo
														+ " has not been generated because Previous Reading Is Greater Than Present Reading "
														+ "\n");
											} catch (IOException e) {
												e.printStackTrace();
											}
										} else {
											float uomValue = presentUOMReading
													- previousraeding;
											float dgUnit=presentDGreading-dgpreciousreadiong;
											elRateMasterList = tariffCalculationService
													.getElectricityRateMaster(tariffId);
											logger.info("::::::::::::presentUOMReading::::::::::"
													+ presentUOMReading);
											logger.info(":::::::::::: uomValue::::::::::"
													+ uomValue);
											billGenerationController.saveElectricityBillParameters(
													elRateMasterList,
													accountIdOnProp,
													serviceTypeList,
													getbilldate,
													presentdate, uomValue,
													billableDays, "Normal",
													"Normal",
													meterconstant,
													presentUOMReading,
													previousraeding, 0, 0,
													0, dgUnit,todApplicable, dgApplicable,
													 dgpreciousreadiong, presentDGreading,
													dgmeterConstant, 0d, 0, "Normal", 0,
													0, "Bill", "0");
										}

									} else {

										out = response.getWriter();
										out.write("Bill For "
												+ accountNo
												+ " has not been generated because bill status "
												+ currentBillStatus + "\n");

									}
								}

								/*billGenerationController.saveElectricityBillParameters(elRateMasterList, accountID,
										typeOfService, previousBillDate, currentBillDate,
										uomValue, billableDays, presentMeterStaus,
										previousMeterStaus, meterConstant, presentUOMReading,
										previousUOMReading, todT1, todT2, todT3, dgunit,
										todApplicable, dgApplicable, previousDgreading,
										presentDgReading, dgmeterconstant, avgUnits, avgCount,
										billType, pfReading, connectedLoad, bill,
										meterchangeUnit);
								 */

							} else {
								out = response.getWriter();
								out.write("Service for " + accountNo
										+ " has not been started" + "\n");
							}
						}
					}
			
			
				}
				
			/*	float presentReading = */
						
						
				
			}
			out=response.getWriter();
			out.write("Bill Has been Calculated"+"\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out=response.getWriter();
			out.write("Bill for Account No."+ accountNo +"has not been Generated "+"\n");
			//out.write("There Seems to be some problem please try again later"+"\n");
			//e.printStackTrace();
		}
	return null;
	}
	
	
	/*@RequestMapping(value = "/bill/ExportAmrreading/{blocId}/{blockName}/{propertyId/{presentdate}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public void downloadAllBills(HttpServletRequest req,
			HttpServletResponse response,@PathVariable String blocId,@PathVariable String blockName,@PathVariable String propertyId,@PathVariable String presentdate,@RequestBody String body)
			throws ParseException, JSONException, IOException {*/
		
		@RequestMapping(value = "/bill/ExportAmrreading/{blocId}/{blockName}/{propertyId}/{presentdate}", method = {
				RequestMethod.POST, RequestMethod.GET })
		public void downloadAllBills(HttpServletRequest req,
				HttpServletResponse response,@PathVariable String blocId,@PathVariable String blockName,@PathVariable String propertyId,@PathVariable String presentdate,@RequestBody String body)
				throws ParseException, JSONException, IOException {
		
		logger.info("In Amr Bill Generation Method");
		int bId=Integer.parseInt(blocId);
		//String blockName=req.getParameter("blockName");
		Date pdate = new SimpleDateFormat("dd-MM-yyyy").parse(presentdate);
		//String propertyId = req.getParameter("propertyId");
		
		logger.info("blocId"+blocId);
		logger.info("blockName"+blockName);
		//logger.info("presentdate"+presentdate);
		logger.info("propertyId"+propertyId);
		
		ServletOutputStream servletOutputStream=null;
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
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
	    	
	    	
			
	    	header.createCell(0).setCellValue("Sl NO");
	        header.createCell(1).setCellValue("Account No");
	        header.createCell(2).setCellValue("Property No");
	        header.createCell(3).setCellValue("Block Name");
	        header.createCell(4).setCellValue("Present Utility Reading"); 
	        header.createCell(5).setCellValue("Previous Utility Reading"); 
	        header.createCell(6).setCellValue("Present DG Reading");
	        header.createCell(7).setCellValue("Previous DG Readiong");
	        header.createCell(8).setCellValue("Present Date");				        
	        	
	        
	        
	     
	    
	        for(int j = 0; j<=8; j++){
	    		header.getCell(j).setCellStyle(style);
	            sheet.setColumnWidth(j, 8000); 
	            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
	        }
	        List<Map<String, Object>> result=(List<Map<String, Object>>) amrData( bId, blockName, pdate, propertyId);
	        System.err.println("result size::::1"+result.size());
	        int count = 1;
	        for (Map<String, Object> map : result) {
	        	System.out.println(":::::::::::::::::::000"+map.get("propertyNo"));
	        	System.out.println(map.get("accountNo"));
	        	XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(String.valueOf(count));
	        	
	        	
					row.createCell(1).setCellValue((String) map.get("accountNo"));						
					
					row.createCell(2).setCellValue((String) map.get("propertyNo"));				
					
					row.createCell(3).setCellValue((String) map.get("blockName"));
					//row.createCell(4).setCellValue((String) map.get("propertyNo"));
						
						
					if(map.containsKey("presentReading") && map.get("presentReading")!=null ){
						row.createCell(4).setCellValue((Float) map.get("presentReading"));
						}
					if(map.containsKey("previousReading")  && map.get("previousReading")!=null){
						row.createCell(5).setCellValue((Float) map.get("previousReading"));
						}
					if(map.containsKey("presentDGreading")  && map.get("presentDGreading")!=null){
						row.createCell(6).setCellValue((Float) map.get("presentDGreading"));
						}
					
					if(map.containsKey("dgPreciousReadiong")  && map.get("dgPreciousReadiong")!=null){
						row.createCell(7).setCellValue((Float) map.get("dgPreciousReadiong"));
						}
					if(map.containsKey("presentDate")  && map.get("presentDate")!=null){
						String S = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)map.get("presentDate"));
						System.out.println(S+"::::::::::::"+map.get("presentDate"));
						row.createCell(8).setCellValue((String)S);
						}
					
			
			count++;
	        }
	
	    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
	    	wb.write(fileOut);
	    	fileOut.flush();
	    	fileOut.close();
	        
	       

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Amr Data.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

	
		
	//return null;
	}
	
	public List<Map<String, Object>> amrData(int blocId, String blockName,
			Date presentdate, String propertyId) {
		Map<String, Object> amrDataresult;
		List<Map<String, Object>> amrdataList = new ArrayList<Map<String, Object>>();
		logger.info("blocId" + blocId);
		logger.info("blockName" + blockName);
		logger.info("presentdate" + presentdate);
		logger.info("propertyId" + propertyId);

		String columnName = null;
		String tower = null;
		String tableName = null;
		String tableNAmeDg = null;
		String propertyNo = "";
		PrintWriter out;

		logger.info("::::::blockId::" + blocId + ":::::::::blockName:::"
				+ blockName + ":::::::::::presentdate::::" + presentdate
				+ "::::::propertyId::::" + propertyId);
		String serviceTypeList = "Electricity";

		int accountId = 0;
		Date getbilldate = null;
		Integer billableDays = 0;
		String accountNo = null;
		int propId = 0;

		Float previousraeding = 0f;
		float meterconstant = 0f;
		String dgApplicable = "No";
		String todApplicable = "No";
		float dgmeterConstant = 0f;
		float dgpreciousreadiong = 0f;
		String previousStatus = null;
		Integer blockI = null;
		String blockN = null;

		try {
			if (blockName.equals("All Blocks")) {
				amrDataresult=	new HashMap<String, Object>();
			List<Map<String, Object>> blockdetail = tariffCalculationService.getallBlocksAmr();

				//for (Entry<String, Object> blok : blockdetail.entrySet()) {
					for (Map<String, Object> map : blockdetail) {
						System.out.println(":::::::::::::::::::"+map.get("blockName"));
						
						for (Entry<String, Object> blok : map.entrySet()) {

					if (blok.getKey().equalsIgnoreCase("blockId")) {
						blockI = (Integer) blok.getValue();
					}
				 if (blok.getKey().equalsIgnoreCase("blockName")) {
						blockN = (String) blok.getValue();
						System.out.println("_______________"+blockN);
					}
						}
					logger.info("blockI::::::" + blockI + "::::::::::blockName"
							+ blockN);
					tower = blockN.substring(blockN.length() - 1);
					logger.info("tower ========= " + tower);
					tableName = "Tower_" + tower + "_EB_kwh";
					tableNAmeDg = "Tower_" + tower + "_DG_kwh";
					logger.info("tableName ====== " + tableName);
					logger.info("::::::blockId:::::::" + blockI);
					List<Integer> properties = tariffCalculationService
							.getProertyId(blockI);
					for (Integer integer : properties) {
						propId = integer;
						logger.info("::::::::::propId::::::::" + propId);
						columnName = amrService.getColumnName(blocId, propId);
						logger.info("columnName =========================== "
								+ columnName);
						Integer accountIdOnProp = tariffCalculationService
								.getAccountIdOnPropertyId(propId,
										serviceTypeList);
						logger.info("------------accountId-----------"
								+ accountIdOnProp);
						if (accountIdOnProp != 0) {
							accountNo = tariffCalculationService
									.getAccontNoONAccountId(accountIdOnProp);
							getbilldate = tariffCalculationService.getBillDate(
									accountIdOnProp, serviceTypeList);
							if (getbilldate != null) {
								LocalDate fromDate = new LocalDate(getbilldate);
								LocalDate toDate = new LocalDate(presentdate);
								billableDays = Days.daysBetween(fromDate,
										toDate).getDays();
								logger.info("::::::::::::::Billable Days::::::::::"
										+ billableDays);
								if (billableDays > 0) {
									String currentBillStatus = tariffCalculationService
											.getPreviousBillStatus(
													accountIdOnProp,
													serviceTypeList,
													presentdate);
									logger.info("::::::::::::::::currentBillStatus:::::::::"
											+ currentBillStatus);
									if (currentBillStatus
											.equalsIgnoreCase("Posted")
											|| currentBillStatus
													.equalsIgnoreCase("Cancelled")
											|| currentBillStatus
													.equalsIgnoreCase("Not Generated")) {
										Map<Object, Object> previousVal = tariffCalculationService
												.getPreviousBillReadingAmr(
														accountIdOnProp,
														serviceTypeList);
										for (Entry<Object, Object> entry : previousVal
												.entrySet()) {

											logger.info(entry.getKey() + "/"
													+ entry.getValue());

											logger.info(entry.getKey() + "/"
													+ entry.getValue());

											if (entry.getKey().equals(
													"InitialReading")) {

												String previou = (String) entry
														.getValue();
												previousraeding = Float
														.parseFloat(previou);

												logger.info("previousraeding:::::::::::"
														+ previou
														+ ":::::::::::"
														+ previousraeding);
											}  else if (entry.getKey().equals(
													"dgapplicable")) {
												dgApplicable = (String) entry
														.getValue();
												if (dgApplicable
														.equalsIgnoreCase("Yes")) {
													if (entry.getKey().equals(
															"DGInitialReading")) {
														String dgInitialReading = (String) entry
																.getValue();
														dgpreciousreadiong = Float
																.parseFloat(dgInitialReading);
														logger.info(":::::::::::meterconstant:::::::::::"
																+ dgInitialReading);
													}
												
												}
											} else if (entry.getKey().equals(
													"todApplicable")) {
												todApplicable = (String) entry
														.getValue();
												logger.info(":::::::::::todApplicable:::::::::::"
														+ todApplicable);
											}
										}

									} else {
                                    System.err.println("Bill For " + accountNo +" has not been generated because bill status "+ currentBillStatus );
										/*
										 * out = response.getWriter();
										 * out.write("Bill For " + accountNo +
										 * " has not been generated because bill status "
										 * + currentBillStatus + "/n");
										 */
									}

									List<ELRateMaster> elRateMasterList = new ArrayList<>();
									int tariffId = serviceMasterService
											.getServiceMasterByAccountNumber(
													accountIdOnProp,
													serviceTypeList);
									float presentUOMReading = amrBillService
											.getPresentReading(tableName,
													columnName, getbilldate,
													presentdate);
									float presentDGreading = amrBillService
											.getPresentDGReading(tableNAmeDg,
													columnName, getbilldate,
													presentdate);

									logger.info(":::::::::::::::presentDGreading::::::::::::::::"
											+ presentDGreading);
									logger.info("presentUOMReading:::::::::::::::"
											+ presentUOMReading
											+ ":::::::::::previousraeding"
											+ previousraeding);
									if (presentUOMReading < previousraeding) {
										
										  //out = response.getWriter();
										System.err.println("Bill For "+ accountNo+
										 " has not been generated because Previous Reading Is Greater Than Present Reading "
										  + "\n");
										 
									} else {
										float uomValue = presentUOMReading
												- previousraeding;
										float dgUnit = presentDGreading
												- dgpreciousreadiong;
										elRateMasterList = tariffCalculationService
												.getElectricityRateMaster(tariffId);
										logger.info("::::::::::::presentUOMReading::::::::::"
												+ presentUOMReading);
										logger.info(":::::::::::: uomValue::::::::::"
												+ uomValue);

										accountNo = tariffCalculationService
												.getAccontNoONAccountId(accountIdOnProp);
										propertyNo = tariffCalculationService
												.getpropertyNoOnPropertyId(propId);
										amrDataresult.put("accountNo",
												accountNo);
										amrDataresult.put("presentReading",
												presentUOMReading);
										amrDataresult.put("previousReading",
												previousraeding);
										amrDataresult.put("propertyNo",
												propertyNo);
										amrDataresult.put("dgPreciousReadiong",
												dgpreciousreadiong);
										amrDataresult.put("presentDGreading",
												presentDGreading);
										amrDataresult.put("presentDate",
												presentdate);
										amrDataresult.put("presentDate",
												presentdate);
										amrDataresult.put("blockName", blockN);
								
									}
								}
							} else {
								logger.info("Service For That Account has Not been activated");
							}

						} else {
							logger.info("Service For That Account Is Not There");
						}
						// }

					}
					amrdataList.add(amrDataresult);
					
				}
			}

			else if (propertyId.equals("0")) {// One Block All Property
				
				logger.info("@@@@@@@@@@@@@@@@@In All Proerty method @@@@@@@@@@@@@@@@@@@@@@-"
						+ propertyId);
				tower = blockName.substring(blockName.length() - 1);
				logger.info("tower ========= " + tower);
				tableName = "Tower_" + tower + "_EB_kwh";
				tableNAmeDg = "Tower_" + tower + "_DG_kwh";
				logger.info("tableName ====== " + tableName);
				List<Integer> properties = tariffCalculationService
						.getProertyId(blocId);
				logger.info("properties.size:::" + properties.size());
				for (Integer integer : properties) {
					propId = integer;
					logger.info("::::::::::propId::::::::" + propId);
					columnName = amrService.getColumnName(blocId, propId);
					logger.info("columnName =========================== "
							+ columnName);
					if (columnName == null) {
						propertyNo = tariffCalculationService
								.getpropertyNoOnPropertyId(propId);
						
						  
						System.err.println("Property No. "+propertyNo
					 +" has not been Integrated With Amr Setting"+ "\n");
						 
					} else {
						Integer accountIdOnProp = tariffCalculationService
								.getAccountIdOnPropertyId(propId,
										serviceTypeList);
						if (accountIdOnProp != 0) {
							accountNo = tariffCalculationService
									.getAccontNoONAccountId(accountIdOnProp);
							getbilldate = tariffCalculationService.getBillDate(
									accountIdOnProp, serviceTypeList);
							logger.info("###:::::::::::::::::::::::::"
									+ getbilldate + "::::::::::::##########");
							if (getbilldate != null) {
								LocalDate fromDate = new LocalDate(getbilldate);
								LocalDate toDate = new LocalDate(presentdate);
								billableDays = Days.daysBetween(fromDate,
										toDate).getDays();
								logger.info("::::::::::::::Billable Days::::::::::"
										+ billableDays);
								if (billableDays > 0) {

									logger.info("::::::::::::getbilldate:::::::::::"
											+ getbilldate);
									String currentBillStatus = tariffCalculationService
											.getPreviousBillStatus(
													accountIdOnProp,
													serviceTypeList,
													presentdate);
									if (currentBillStatus
											.equalsIgnoreCase("Posted")
											|| currentBillStatus
													.equalsIgnoreCase("Cancelled")
											|| currentBillStatus
													.equalsIgnoreCase("Not Generated")) {
										Map<Object, Object> previousVal = tariffCalculationService
												.getPreviousBillReadingAmr(
														accountIdOnProp,
														serviceTypeList);
										for (Entry<Object, Object> entry : previousVal
												.entrySet()) {
											logger.info(entry.getKey() + "/"
													+ entry.getValue());

											if (entry.getKey().equals(
													"InitialReading")) {

												String previou = (String) entry
														.getValue();
												previousraeding = Float
														.parseFloat(previou);

												logger.info("previousraeding:::::::::::"
														+ previou
														+ ":::::::::::"
														+ previousraeding);
											}
											
											
											if (entry.getKey().equals(
													"dgapplicable")) {
												dgApplicable = (String) entry
														.getValue();
												logger.info(":::::::::::dgapplicable:::::::::::"
														+ dgApplicable);
												if (dgApplicable
														.equalsIgnoreCase("Yes")) {
													if (entry.getKey().equals(
															"DGInitialReading")) {

														String dgInitialReading = (String) entry
																.getValue();
														dgpreciousreadiong = Float
																.parseFloat(dgInitialReading);
														logger.info(":::::::::::meterconstant:::::::::::"
																+ dgInitialReading);
													}
													
												}
											}
											if (entry.getKey().equals(
													"todApplicable")) {
												todApplicable = (String) entry
														.getValue();
												logger.info(":::::::::::todApplicable:::::::::::"
														+ todApplicable);
											}
										}
										List<ELRateMaster> elRateMasterList = new ArrayList<>();
										int tariffId = serviceMasterService
												.getServiceMasterByAccountNumber(
														accountIdOnProp,
														serviceTypeList);
										float presentUOMReading = amrBillService
												.getPresentReading(tableName,
														columnName,
														getbilldate,
														presentdate);
										float presentDGreading = amrBillService
												.getPresentDGReading(
														tableNAmeDg,
														columnName,
														getbilldate,
														presentdate);
										logger.info(":::::::::::::::presentDGreading::::::::::::::::"
												+ presentDGreading);
												  //out = response.getWriter();
												/*System.err.println("Bill For " +
												  accountNo +
												  " has not been generated because Previous Reading Is Greater Than Present Reading "
												  + "\n");*/
											
											float uomValue = presentUOMReading
													- previousraeding;
											float dgUnit = presentDGreading
													- dgpreciousreadiong;
											elRateMasterList = tariffCalculationService
													.getElectricityRateMaster(tariffId);
											logger.info("::::::::::::presentUOMReading::::::::::"
													+ presentUOMReading);
											logger.info(":::::::::::: uomValue::::::::::"
													+ uomValue);

											accountNo = tariffCalculationService
													.getAccontNoONAccountId(accountIdOnProp);
											propertyNo = tariffCalculationService
													.getpropertyNoOnPropertyId(propId);
											amrDataresult=	new HashMap<String, Object>();
											amrDataresult.put("accountNo",
													accountNo);
											amrDataresult.put("presentReading",
													presentUOMReading);
											amrDataresult.put(
													"previousReading",
													previousraeding);
											amrDataresult.put("propertyNo",
													propertyNo);
											amrDataresult.put(
													"dgPreciousReadiong",
													dgpreciousreadiong);
											amrDataresult.put(
													"presentDGreading",
													presentDGreading);
											amrDataresult.put("presentDate",
													presentdate);
											amrDataresult.put("presentDate",
													presentdate);
											amrDataresult.put("blockName",
													tower);
											amrdataList.add(amrDataresult);
											
										

									} else {

									
										 // out = response.getWriter();
										System.err.println("Bill For " + accountNo +
										 " has not been generated because bill status "
										  + currentBillStatus + "\n");
										 

									}
								}else{
									System.err.println("Billable Days are less than");	
								}
							} else {
								
								// out = response.getWriter();
								System.err.println("Service for " + accountNo +
								  " has not been started" + "\n");
							}
						} else {
							System.err.println("Account for this service is not there");
						}
						// }
					}
					//amrdataList.add(amrDataresult);
				}
				
			} else {
                   System.out.println("In Else Method");
				tower = blockName.substring(blockName.length() - 1);
				logger.info("tower ========= " + tower);
				String comma = ",";
				String[] trancode = propertyId.split(comma);
				Calendar c = Calendar.getInstance(); 
				c.setTime(presentdate); 
				c.add(Calendar.DATE, 1);
				String strDate = new SimpleDateFormat("YYYY-MM-dd").format(presentdate);
				String pesentDate = new SimpleDateFormat("YYYY-MM-dd").format(c.getTime());
				for (int i = 0; i < trancode.length; i++) {
					int propid = Integer.parseInt(trancode[i]);
					Integer accountIdOnProp = 0;
					float presentDGreading = 0.0f;
					float presentUOMReading = 0.0f;
					Timestamp date=null;
					List<AMRDataEntity> accountDetails = amrEntityService
							.getAccountDetailsOnPropertyId(propid, strDate,
									pesentDate);
					for (Iterator iterator = accountDetails.iterator(); iterator
							.hasNext();) {
						AMRDataEntity amrDataEntity = (AMRDataEntity) iterator
								.next();
						accountIdOnProp = amrDataEntity.getAccountId();
						accountNo = amrDataEntity.getAccountobj()
								.getAccountNo();
						presentDGreading = (float) amrDataEntity.getDgReading();
						presentUOMReading = (float) amrDataEntity
								.getElReading();
						date=amrDataEntity.getReadingDate();
						propertyNo=amrDataEntity.getPropobj().getProperty_No();
					}

					if (accountIdOnProp != 0) {
						
						getbilldate = tariffCalculationService.getBillDate(
								accountIdOnProp, serviceTypeList);
						logger.info("###:::::::::::::::::::::::::"+ getbilldate + "::::::::::::##########");
						if (getbilldate != null) {
							LocalDate fromDate = new LocalDate(getbilldate);
							LocalDate toDate = new LocalDate(presentdate);
							billableDays = Days.daysBetween(fromDate, toDate)
									.getDays();
							logger.info("::::::::::::::Billable Days::::::::::"+ billableDays);
							if (billableDays > 0) {

								logger.info("::::::::::::getbilldate:::::::::::"+ getbilldate);
								String currentBillStatus = tariffCalculationService
										.getPreviousBillStatus(accountIdOnProp,
												serviceTypeList, presentdate);
								if (currentBillStatus
										.equalsIgnoreCase("Posted")
										|| currentBillStatus
												.equalsIgnoreCase("Cancelled")
										|| currentBillStatus
												.equalsIgnoreCase("Not Generated")) {
									Map<Object, Object> previousVal = tariffCalculationService
											.getPreviousBillReadingAmr(
													accountIdOnProp,
													serviceTypeList);
									for (Entry<Object, Object> entry : previousVal
											.entrySet()) {
										logger.info(entry.getKey() + "/"
												+ entry.getValue());

										if (entry.getKey().equals(
												"InitialReading")) {

											String previou = (String) entry
													.getValue();
											previousraeding = Float
													.parseFloat(previou);

											logger.info("previousraeding:::::::::::"
													+ previou
													+ ":::::::::::"
													+ previousraeding);
										}
										if (entry.getKey().equals(
												"MeterConstant")) {

											String metercon = (String) entry
													.getValue();
											meterconstant = Float
													.parseFloat(metercon);
											logger.info(":::::::::::meterconstant:::::::::::"
													+ meterconstant);
										}
										if (entry.getKey().equals(
												"previousStatus")) {
											previousStatus = (String) entry
													.getValue();
											logger.info(":::::::::::previousStatus:::::::::::"
													+ previousStatus);
										}
										if (entry.getKey().equals(
												"dgapplicable")) {
											dgApplicable = (String) entry
													.getValue();
											logger.info("::::::::::::dgApplicable::::::::::::::::"
													+ dgApplicable);

										}
										if (dgApplicable
												.equalsIgnoreCase("Yes")) {
											logger.info(":::::::::::::::Dg Appliable Block::::::::::::");
											if (entry.getKey().equals(
													"DGInitialReading")) {

												String dgInitialReading = (String) entry
														.getValue();
												dgpreciousreadiong = Float
														.parseFloat(dgInitialReading);
												logger.info(":::::::::::dgpreciousreadiong:::::::::::"
														+ dgInitialReading);
											}
											if (entry.getKey().equals(
													"DGMeterConstant")) {

												String dgMeterConstant = (String) entry
														.getValue();
												dgmeterConstant = Float
														.parseFloat(dgMeterConstant);
												logger.info(":::::::::::meterconstant:::::::::::"
														+ dgmeterConstant);
											}
										}
										if (entry.getKey().equals(
												"todApplicable")) {
											todApplicable = (String) entry
													.getValue();
											logger.info(":::::::::::todApplicable:::::::::::"
													+ todApplicable);
										}
									}
									List<ELRateMaster> elRateMasterList = new ArrayList<>();
									logger.info(":::::::::::::::presentDGreading::::::::::::::::"+ presentDGreading);

									float uomValue = presentUOMReading	- previousraeding;
									float dgUnit = presentDGreading	- dgpreciousreadiong;
									
									logger.info("::::::::::::presentUOMReading::::::::::"+ presentUOMReading);
									logger.info(":::::::::::: uomValue::::::::::"+ uomValue);
									amrDataresult = new HashMap<String, Object>();
									amrDataresult = new HashMap<String, Object>();
									amrDataresult.put("accountNo", accountNo);
									amrDataresult.put("presentReading",
											presentUOMReading);
									amrDataresult.put("previousReading",
											previousraeding);
									amrDataresult.put("propertyNo", propertyNo);
									amrDataresult.put("dgPreciousReadiong",
											dgpreciousreadiong);
									amrDataresult.put("presentDGreading",
											presentDGreading);
									amrDataresult.put("presentDate",
											presentdate);
									//amrDataresult.put("presentDate",presentdate);
									logger.info("date:::::::::::::"+date);
									amrDataresult.put("presentDate",date);
									amrDataresult.put("blockName", tower);
									amrdataList.add(amrDataresult);

								} else {
                               System.err.println("Bill Are Not Posted");
								}
							}else{
								System.err.println("Billable Days Are Less Than Zeo ");	
							}

						} else {
							System.err.println("Billable Date is Null");

						}
					}
				}

			}
				

			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
System.err.println("amr Data List Size"+amrdataList.size());
		return amrdataList;
	}
	
	
	@RequestMapping(value = "/bill/generateAmrBillNew", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public HashMap<Object, Object> generateAmrBill(HttpServletRequest req,
			HttpServletResponse response, @RequestBody String body)
			throws ParseException, JSONException, IOException {
		logger.info("In Amr Bill Generation Method");
		int blocId=Integer.parseInt(req.getParameter("blocId"));
		String blockName=req.getParameter("blockName");
		Date presentdate = new SimpleDateFormat("dd/MM/yyyy").parse(req.getParameter("presentdate"));
		String propertyId = req.getParameter("propertyId");
		String columnName =null;
		
		String tower =null;
		String tableName = null;
		String tableNAmeDg=null;
		String propertyNo="";
		PrintWriter out;
		
		logger.info("::::::blockId::"+blocId+":::::::::blockName:::"+blockName+":::::::::::presentdate::::"+presentdate+"::::::propertyId::::"+propertyId);
		String serviceTypeList="Electricity";
		
		int accountId = 0;
		Date getbilldate = null;
		Integer billableDays = 0;
		String accountNo = null;
		int propId = 0;
		
	    Float previousraeding=0f;
		float meterconstant=0f;
		String dgApplicable="No";
		String todApplicable="No";
		float dgmeterConstant=0f;
		float dgpreciousreadiong=0f;
		String previousStatus=null;
		Integer blockI=null;
		String blockN=null;
		synchronized (this) {
			try {
				if (blockName.equals("All Blocks")) {

					List<Map<String, Object>> blockdetail = tariffCalculationService
							.getallBlocksAmr();

					for (Iterator iterator = blockdetail.iterator(); iterator
							.hasNext();) {
						Map<String, Object> map = (Map<String, Object>) iterator
								.next();

						for (Entry<String, Object> blok : map.entrySet()) {

							if (blok.getKey().equals("blockId")) {
								blockI = (Integer) blok.getValue();
							}
							if (blok.getKey().equals("blockName")) {
								blockN = (String) blok.getValue();
							}
						}
						logger.info("blockI::::::" + blockI
								+ "::::::::::blockName" + blockN);
						tower = blockN.substring(blockN.length() - 1);
						logger.info("tower ========= " + tower);
						tableName = "Tower_" + tower + "_EB_kwh";
						tableNAmeDg = "Tower_" + tower + "_DG_kwh";
						logger.info("tableName ====== " + tableName);
						logger.info("::::::blockId:::::::" + blockI);
						List<Integer> properties = tariffCalculationService
								.getProertyId(blockI);
						for (Integer integer : properties) {
							propId = integer;
							logger.info("::::::::::propId::::::::" + propId);
							columnName = amrService.getColumnName(blocId,
									propId);
							logger.info("columnName =========================== "
									+ columnName);
							Integer accountIdOnProp = tariffCalculationService
									.getAccountIdOnPropertyId(propId,
											serviceTypeList);
							logger.info("------------accountId-----------"
									+ accountIdOnProp);
							if (accountIdOnProp != 0) {
								accountNo = tariffCalculationService
										.getAccontNoONAccountId(accountIdOnProp);
								getbilldate = tariffCalculationService
										.getBillDate(accountIdOnProp,
												serviceTypeList);
								if (getbilldate != null) {
									LocalDate fromDate = new LocalDate(
											getbilldate);
									LocalDate toDate = new LocalDate(
											presentdate);
									billableDays = Days.daysBetween(fromDate,
											toDate).getDays();
									logger.info("::::::::::::::Billable Days::::::::::"
											+ billableDays);
									if (billableDays > 0) {
										String currentBillStatus = tariffCalculationService
												.getPreviousBillStatus(
														accountIdOnProp,
														serviceTypeList,
														presentdate);
										logger.info("::::::::::::::::currentBillStatus:::::::::"
												+ currentBillStatus);
										if (currentBillStatus
												.equalsIgnoreCase("Posted")
												|| currentBillStatus
														.equalsIgnoreCase("Cancelled")
												|| currentBillStatus
														.equalsIgnoreCase("Not Generated")) {
											Map<Object, Object> previousVal = tariffCalculationService
													.getPreviousBillReadingAmr(
															accountIdOnProp,
															serviceTypeList);
											for (Entry<Object, Object> entry : previousVal
													.entrySet()) {

												logger.info(entry.getKey()
														+ "/"
														+ entry.getValue());

												logger.info(entry.getKey()
														+ "/"
														+ entry.getValue());

												if (entry.getKey().equals(
														"InitialReading")) {

													String previou = (String) entry
															.getValue();
													previousraeding = Float
															.parseFloat(previou);

													logger.info("previousraeding:::::::::::"
															+ previou
															+ ":::::::::::"
															+ previousraeding);
												} else if (entry
														.getKey()
														.equals("MeterConstant")) {

													String metercon = (String) entry
															.getValue();
													meterconstant = Float
															.parseFloat(metercon);
													logger.info(":::::::::::meterconstant:::::::::::"
															+ meterconstant);
												} else if (entry
														.getKey()
														.equals("previousStatus")) {
													previousStatus = (String) entry
															.getValue();
													logger.info(":::::::::::previousStatus:::::::::::"
															+ previousStatus);
												} else if (entry.getKey()
														.equals("dgapplicable")) {
													dgApplicable = (String) entry
															.getValue();
													if (dgApplicable
															.equalsIgnoreCase("Yes")) {
														if (entry
																.getKey()
																.equals("DGInitialReading")) {
															String dgInitialReading = (String) entry
																	.getValue();
															dgpreciousreadiong = Float
																	.parseFloat(dgInitialReading);
															logger.info(":::::::::::meterconstant:::::::::::"
																	+ dgInitialReading);
														}
														if (entry
																.getKey()
																.equals("DGMeterConstant")) {

															String dgMeterConstant = (String) entry
																	.getValue();
															dgmeterConstant = Float
																	.parseFloat(dgMeterConstant);
															logger.info(":::::::::::meterconstant:::::::::::"
																	+ dgmeterConstant);
														}
													}
												} else if (entry
														.getKey()
														.equals("todApplicable")) {
													todApplicable = (String) entry
															.getValue();
													logger.info(":::::::::::todApplicable:::::::::::"
															+ todApplicable);
												}
											}

										} else {

											out = response.getWriter();
											out.write("Bill For "
													+ accountNo
													+ " has not been generated because bill status "
													+ currentBillStatus + "/n");

										}

										List<ELRateMaster> elRateMasterList = new ArrayList<>();
										int tariffId = serviceMasterService
												.getServiceMasterByAccountNumber(
														accountIdOnProp,
														serviceTypeList);
										float presentUOMReading = amrBillService
												.getPresentReading(tableName,
														columnName,
														getbilldate,
														presentdate);
										float presentDGreading = amrBillService
												.getPresentDGReading(
														tableNAmeDg,
														columnName,
														getbilldate,
														presentdate);

										logger.info(":::::::::::::::presentDGreading::::::::::::::::"
												+ presentDGreading);
										logger.info("presentUOMReading:::::::::::::::"
												+ presentUOMReading
												+ ":::::::::::previousraeding"
												+ previousraeding);
										if (presentUOMReading < previousraeding) {
											out = response.getWriter();
											out.write("Bill For "
													+ accountNo
													+ " has not been generated because Previous Reading Is Greater Than Present Reading "
													+ "\n");
										} else {
											float uomValue = presentUOMReading
													- previousraeding;
											float dgUnit = presentDGreading
													- dgpreciousreadiong;
											elRateMasterList = tariffCalculationService
													.getElectricityRateMaster(tariffId);
											logger.info("::::::::::::presentUOMReading::::::::::"
													+ presentUOMReading);
											logger.info(":::::::::::: uomValue::::::::::"
													+ uomValue);

											billGenerationController
													.saveElectricityBillParameters(
															elRateMasterList,
															accountIdOnProp,
															serviceTypeList,
															getbilldate,
															presentdate,
															uomValue,
															billableDays,
															"Normal", "Normal",
															meterconstant,
															presentUOMReading,
															previousraeding, 0,
															0, 0, dgUnit,
															todApplicable,
															dgApplicable,
															dgpreciousreadiong,
															presentDGreading,
															dgmeterConstant,
															0d, 0, "Normal", 0,
															0, "Bill", "0");
										}
									}
								} else {
									logger.info("Service For That Account has Not been activated");
								}

							} else {
								logger.info("Service For That Account Is Not There");
							}
							//}

						}

					}
				}

				else if (propertyId.equals("0")) {//One Block All Property
					logger.info("@@@@@@@@@@@@@@@@@In All Proerty method @@@@@@@@@@@@@@@@@@@@@@-"
							+ propertyId);
					tower = blockName.substring(blockName.length() - 1);
					logger.info("tower ========= " + tower);
					String comma = ",";
					String[] trancode = propertyId.split(comma);
					Integer personId = 0;
					DateTime dtOrg = new DateTime();
					//DateTime minusone = dtOrg.minusDays(1);
					DateTime minusone = dtOrg.plusDays(1);
					Calendar c = Calendar.getInstance();
					c.setTime(presentdate);
					c.add(Calendar.DATE, -1);
					String strDate = new SimpleDateFormat("YYYY-MM-dd")
							.format(c.getTime());
					String pesentDate = new SimpleDateFormat("YYYY-MM-dd")
							.format(presentdate);

					for (int i = 0; i < trancode.length; i++) {
						int propid = Integer.parseInt(trancode[i]);
						logger.info("columnName =========================== "
								+ columnName);
						Integer accountIdOnProp = 0;
						float presentDGreading = 0.0f;
						float presentUOMReading = 0.0f;
						List<AMRDataEntity> accountDetails = amrEntityService
								.getAccountDetailsOnPropertyId(propid, strDate,
										pesentDate);
						for (Iterator iterator = accountDetails.iterator(); iterator
								.hasNext();) {
							AMRDataEntity amrDataEntity = (AMRDataEntity) iterator
									.next();
							accountIdOnProp = amrDataEntity.getAccountId();
							accountNo = amrDataEntity.getAccountobj()
									.getAccountNo();
							presentDGreading = (float) amrDataEntity
									.getDgReading();
							presentUOMReading = (float) amrDataEntity
									.getElReading();
						}

						if (accountIdOnProp != 0) {
							//accountNo = tariffCalculationService.getAccontNoONAccountId(accountIdOnProp);
							getbilldate = tariffCalculationService.getBillDate(
									accountIdOnProp, serviceTypeList);
							logger.info("###:::::::::::::::::::::::::"
									+ getbilldate + "::::::::::::##########");
							if (getbilldate != null) {
								LocalDate fromDate = new LocalDate(getbilldate);
								LocalDate toDate = new LocalDate(presentdate);
								billableDays = Days.daysBetween(fromDate,
										toDate).getDays();
								logger.info("::::::::::::::Billable Days::::::::::"
										+ billableDays);
								if (billableDays > 0) {

									logger.info("::::::::::::getbilldate:::::::::::"
											+ getbilldate);
									String currentBillStatus = tariffCalculationService
											.getPreviousBillStatus(
													accountIdOnProp,
													serviceTypeList,
													presentdate);
									if (currentBillStatus
											.equalsIgnoreCase("Posted")
											|| currentBillStatus
													.equalsIgnoreCase("Cancelled")
											|| currentBillStatus
													.equalsIgnoreCase("Not Generated")) {
										Map<Object, Object> previousVal = tariffCalculationService
												.getPreviousBillReadingAmr(
														accountIdOnProp,
														serviceTypeList);
										for (Entry<Object, Object> entry : previousVal
												.entrySet()) {
											logger.info(entry.getKey() + "/"
													+ entry.getValue());

											if (entry.getKey().equals(
													"InitialReading")) {

												String previou = (String) entry
														.getValue();
												previousraeding = Float
														.parseFloat(previou);

												logger.info("previousraeding:::::::::::"
														+ previou
														+ ":::::::::::"
														+ previousraeding);
											}
											if (entry.getKey().equals(
													"MeterConstant")) {

												String metercon = (String) entry
														.getValue();
												meterconstant = Float
														.parseFloat(metercon);
												logger.info(":::::::::::meterconstant:::::::::::"
														+ meterconstant);
											}
											if (entry.getKey().equals(
													"previousStatus")) {
												previousStatus = (String) entry
														.getValue();
												logger.info(":::::::::::previousStatus:::::::::::"
														+ previousStatus);
											}
											if (entry.getKey().equals(
													"dgapplicable")) {
												dgApplicable = (String) entry
														.getValue();
												logger.info("::::::::::::dgApplicable::::::::::::::::"
														+ dgApplicable);

											}
											if (dgApplicable
													.equalsIgnoreCase("Yes")) {
												logger.info(":::::::::::::::Dg Appliable Block::::::::::::");
												if (entry.getKey().equals(
														"DGInitialReading")) {

													String dgInitialReading = (String) entry
															.getValue();
													dgpreciousreadiong = Float
															.parseFloat(dgInitialReading);
													logger.info(":::::::::::dgpreciousreadiong:::::::::::"
															+ dgInitialReading);
												}
												if (entry.getKey().equals(
														"DGMeterConstant")) {

													String dgMeterConstant = (String) entry
															.getValue();
													dgmeterConstant = Float
															.parseFloat(dgMeterConstant);
													logger.info(":::::::::::meterconstant:::::::::::"
															+ dgmeterConstant);
												}
											}
											if (entry.getKey().equals(
													"todApplicable")) {
												todApplicable = (String) entry
														.getValue();
												logger.info(":::::::::::todApplicable:::::::::::"
														+ todApplicable);
											}
										}
										List<ELRateMaster> elRateMasterList = new ArrayList<>();
										int tariffId = serviceMasterService
												.getServiceMasterByAccountNumber(
														accountIdOnProp,
														serviceTypeList);

										logger.info(":::::::::::::::presentDGreading::::::::::::::::"
												+ presentDGreading);
										if (presentUOMReading < previousraeding) {
											try {
												out = response.getWriter();
												out.write("Bill For "
														+ accountNo
														+ " has not been generated because Previous Reading Is Greater Than Present Reading "
														+ "\n");
											} catch (IOException e) {
												e.printStackTrace();
											}
										} else {
											float uomValue = presentUOMReading
													- previousraeding;
											float dgUnit = presentDGreading
													- dgpreciousreadiong;
											elRateMasterList = tariffCalculationService
													.getElectricityRateMaster(tariffId);
											logger.info("::::::::::::presentUOMReading::::::::::"
													+ presentUOMReading);
											logger.info(":::::::::::: uomValue::::::::::"
													+ uomValue);
											billGenerationController
													.saveElectricityBillParameters(
															elRateMasterList,
															accountIdOnProp,
															serviceTypeList,
															getbilldate,
															presentdate,
															uomValue,
															billableDays,
															"Normal", "Normal",
															meterconstant,
															presentUOMReading,
															previousraeding, 0,
															0, 0, dgUnit,
															todApplicable,
															dgApplicable,
															dgpreciousreadiong,
															presentDGreading,
															dgmeterConstant,
															0d, 0, "Normal", 0,
															0, "Bill", "0");
										}

									} else {

										out = response.getWriter();
										out.write("Bill For "
												+ accountNo
												+ " has not been generated because bill status "
												+ currentBillStatus + "\n");

									}
								}

							} else {
								out = response.getWriter();
								out.write("Service for " + accountNo
										+ " has not been started" + "\n");
							}

						}

					}
					out = response.getWriter();
					out.write("Bill Has been Calculated" + "\n");
				}

				else {//
					tower = blockName.substring(blockName.length() - 1);
					logger.info("tower ========= " + tower);
					String comma = ",";
					String[] trancode = propertyId.split(comma);
					Calendar c = Calendar.getInstance();
					c.setTime(presentdate);
					c.add(Calendar.DATE, 1);
					String strDate = new SimpleDateFormat("YYYY-MM-dd")
							.format(presentdate);
					String pesentDate = new SimpleDateFormat("YYYY-MM-dd")
							.format(c.getTime());

					for (int i = 0; i < trancode.length; i++) {
						int propid = Integer.parseInt(trancode[i]);
						Integer accountIdOnProp = 0;
						float presentDGreading = 0.0f;
						float presentUOMReading = 0.0f;
						Timestamp date = null;
						List<AMRDataEntity> accountDetails = amrEntityService
								.getAccountDetailsOnPropertyId(propid, strDate,
										pesentDate);
						for (Iterator iterator = accountDetails.iterator(); iterator
								.hasNext();) {
							AMRDataEntity amrDataEntity = (AMRDataEntity) iterator
									.next();
							accountIdOnProp = amrDataEntity.getAccountId();
							accountNo = amrDataEntity.getAccountobj()
									.getAccountNo();
							presentDGreading = (float) amrDataEntity
									.getDgReading();
							presentUOMReading = (float) amrDataEntity
									.getElReading();

						}

						if (accountIdOnProp != 0) {
							//accountNo = tariffCalculationService.getAccontNoONAccountId(accountIdOnProp);
							getbilldate = tariffCalculationService.getBillDate(
									accountIdOnProp, serviceTypeList);
							logger.info("###:::::::::::::::::::::::::"
									+ getbilldate + "::::::::::::##########");
							if (getbilldate != null) {
								LocalDate fromDate = new LocalDate(getbilldate);
								LocalDate toDate = new LocalDate(presentdate);
								billableDays = Days.daysBetween(fromDate,
										toDate).getDays();
								logger.info("::::::::::::::Billable Days::::::::::"
										+ billableDays);
								if (billableDays > 0) {

									logger.info("::::::::::::getbilldate:::::::::::"
											+ getbilldate);
									String currentBillStatus = tariffCalculationService
											.getPreviousBillStatus(
													accountIdOnProp,
													serviceTypeList,
													presentdate);
									if (currentBillStatus
											.equalsIgnoreCase("Posted")
											|| currentBillStatus
													.equalsIgnoreCase("Cancelled")
											|| currentBillStatus
													.equalsIgnoreCase("Not Generated")) {
										Map<Object, Object> previousVal = tariffCalculationService
												.getPreviousBillReadingAmr(
														accountIdOnProp,
														serviceTypeList);
										for (Entry<Object, Object> entry : previousVal
												.entrySet()) {
											logger.info(entry.getKey() + "/"
													+ entry.getValue());

											if (entry.getKey().equals(
													"InitialReading")) {

												String previou = (String) entry
														.getValue();
												previousraeding = Float
														.parseFloat(previou);

												logger.info("previousraeding:::::::::::"
														+ previou
														+ ":::::::::::"
														+ previousraeding);
											}
											if (entry.getKey().equals(
													"MeterConstant")) {

												String metercon = (String) entry
														.getValue();
												meterconstant = Float
														.parseFloat(metercon);
												logger.info(":::::::::::meterconstant:::::::::::"
														+ meterconstant);
											}
											if (entry.getKey().equals(
													"previousStatus")) {
												previousStatus = (String) entry
														.getValue();
												logger.info(":::::::::::previousStatus:::::::::::"
														+ previousStatus);
											}
											if (entry.getKey().equals(
													"dgapplicable")) {
												dgApplicable = (String) entry
														.getValue();
												logger.info("::::::::::::dgApplicable::::::::::::::::"
														+ dgApplicable);

											}
											if (dgApplicable
													.equalsIgnoreCase("Yes")) {
												logger.info(":::::::::::::::Dg Appliable Block::::::::::::");
												if (entry.getKey().equals(
														"DGInitialReading")) {

													String dgInitialReading = (String) entry
															.getValue();
													dgpreciousreadiong = Float
															.parseFloat(dgInitialReading);
													logger.info(":::::::::::dgpreciousreadiong:::::::::::"
															+ dgInitialReading);
												}
												if (entry.getKey().equals(
														"DGMeterConstant")) {

													String dgMeterConstant = (String) entry
															.getValue();
													dgmeterConstant = Float
															.parseFloat(dgMeterConstant);
													logger.info(":::::::::::meterconstant:::::::::::"
															+ dgmeterConstant);
												}
											}
											if (entry.getKey().equals(
													"todApplicable")) {
												todApplicable = (String) entry
														.getValue();
												logger.info(":::::::::::todApplicable:::::::::::"
														+ todApplicable);
											}
										}
										List<ELRateMaster> elRateMasterList = new ArrayList<>();
										int tariffId = serviceMasterService
												.getServiceMasterByAccountNumber(
														accountIdOnProp,
														serviceTypeList);

										logger.info(":::::::::::::::presentDGreading::::::::::::::::"
												+ presentDGreading);
										if (presentUOMReading < previousraeding) {
											try {
												out = response.getWriter();
												out.write("Bill For "
														+ accountNo
														+ " has not been generated because Previous Reading Is Greater Than Present Reading "
														+ "\n");
											} catch (IOException e) {
												e.printStackTrace();
											}
										} else {
											float uomValue = presentUOMReading
													- previousraeding;
											float dgUnit = presentDGreading
													- dgpreciousreadiong;
											elRateMasterList = tariffCalculationService
													.getElectricityRateMaster(tariffId);
											logger.info("::::::::::::presentUOMReading::::::::::"
													+ presentUOMReading);
											logger.info(":::::::::::: uomValue::::::::::"
													+ uomValue);
											billGenerationController
													.saveElectricityBillParameters(
															elRateMasterList,
															accountIdOnProp,
															serviceTypeList,
															getbilldate,
															presentdate,
															uomValue,
															billableDays,
															"Normal", "Normal",
															meterconstant,
															presentUOMReading,
															previousraeding, 0,
															0, 0, dgUnit,
															todApplicable,
															dgApplicable,
															dgpreciousreadiong,
															presentDGreading,
															dgmeterConstant,
															0d, 0, "Normal", 0,
															0, "Bill", "0");
										}

									} else {

										out = response.getWriter();
										out.write("Bill For "
												+ accountNo
												+ " has not been generated because bill status "
												+ currentBillStatus + "\n");

									}
								}

							} else {
								out = response.getWriter();
								out.write("Service for " + accountNo
										+ " has not been started" + "\n");
							}

						}

					}
					out = response.getWriter();
					out.write("Bill Has been Calculated" + "\n");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				out = response.getWriter();
				out.write("Bill for Account No." + accountNo
						+ "has not been Generated " + "\n");
				//out.write("There Seems to be some problem please try again later"+"\n");
				//e.printStackTrace();
			}
		}
	return null;
	}
}
