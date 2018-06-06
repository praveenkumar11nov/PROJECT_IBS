package com.bcits.bfm.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.codehaus.jettison.json.JSONException;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.model.BatchUpdate;
import com.bcits.bfm.model.BillData;
import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.DocumentElement;
import com.bcits.bfm.model.ELRateMaster;
import com.bcits.bfm.model.ElectricityMetersEntity;
import com.bcits.bfm.model.GasRateMaster;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.model.ServiceParametersEntity;
import com.bcits.bfm.model.SolidWasteRateMaster;
import com.bcits.bfm.model.WTRateMaster;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.electricityMetersManagement.ElectricityMetersService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceParameterService;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class BatchUpdateController 
{

	private static final Log logger = LogFactory.getLog(BatchUpdateController.class);
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@Autowired
	TariffCalculationService tariffCalculationService;
	
	@Autowired
	ServiceMasterService serviceMasterService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	BillGenerationController billGenerationController;
	
	@Autowired
	ElectricityMetersService electricityMetersService;
	
	@Autowired
	ServiceParameterService serviceParameterService;
	
	@RequestMapping("/batchUpdate")
	public String accessCardsIndex(HttpServletRequest request,Model model)
	{
		logger.info("---------------- In Batch updated controller ------------------");
		model.addAttribute("ViewName", "Bills Generation");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Bills Generation", 1, request);
		breadCrumbService.addNode("Manage Utility Batch Bills", 2, request);
		return "electricityBills/batchUpdate";
	}
	
	@RequestMapping(value = "/batchUpdate/batchRead", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> readBatch()
	{
	    return null;
	}
	
	public @ResponseBody List<?> readPresentStatus(Locale locale)
	{
		String[] status = messageSource.getMessage("presentStatus", null, locale).split(",");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final String string : status)
		{
			 result.add(new HashMap<String, Object>() 
			            {/**
							 * 
							 */
							private static final long serialVersionUID = 1L;

						{	
			            	put("presentStatus", string);
			            }});
		}
	    return result;
	}
    
    @RequestMapping(value = "/batchUpdate/batchCreate", method = RequestMethod.POST)
    public @ResponseBody List<BatchUpdate> batchCreate(@RequestBody ArrayList<Map<String, Object>> models) throws ParseException {
        List<BatchUpdate> batchUpdates;
        synchronized (this) {
			batchUpdates = new ArrayList<BatchUpdate>();
			for (Map<String, Object> model : models) {
				BatchUpdate batchUpdate = new BatchUpdate();
				if (model.get("batchUpdateId") != null) {
					batchUpdate.setBatchUpdateId((int) model
							.get("batchUpdateId"));
				}

				if (model.get("accountNo") != null) {
					batchUpdate.setAccountNo((String) model.get("accountNo"));
				}

				if (model.get("serviceType") != null) {
					batchUpdate.setServiceType((String) model
							.get("serviceType"));
				}

				if (model.get("presentBillDate") != null) {
					batchUpdate.setPresentBillDate(ConvertDate
							.formattedDate1(model.get("presentBillDate")
									.toString()));
				}

				if (model.get("presentStatus") != null) {
					if (!model.get("presentStatus").equals("NA")) {
						batchUpdate.setPresentStatus((String) model
								.get("presentStatus"));
					}
				}

				if (model.get("presentReading") != null) {
					if (!model.get("presentReading").equals("NA")) {
						batchUpdate.setPresentReading(Float.parseFloat(model
								.get("presentReading").toString()));
					}
				}

				if (model.get("pfReading") != null) {
					if (!model.get("pfReading").equals("NA")) {
						batchUpdate.setPfReading(Float.parseFloat(model.get(
								"pfReading").toString()));
					}
				}

				if (model.get("recordedDemand") != null) {
					if (!model.get("recordedDemand").equals("NA")) {
						batchUpdate.setRecordedDemand(Float.parseFloat(model
								.get("recordedDemand").toString()));
					}
				}

				if (model.get("previousBillDate") != null) {
					if (!model.get("previousBillDate").equals("NA")) {
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd");
						batchUpdate.setPreviousBillDate(formatter.parse(model
								.get("previousBillDate").toString()));
					}
				}

				if (model.get("previousStatus") != null) {
					if (!model.get("previousStatus").equals("NA")) {
						batchUpdate.setPreviousStatus((String) model
								.get("previousStatus"));
					}
				}

				if (model.get("previousReading") != null) {
					if (!model.get("previousReading").equals("NA")) {
						batchUpdate.setPreviousReading(Float.parseFloat(model
								.get("previousReading").toString()));
					}
				}

				if (model.get("units") != null) {
					if (!model.get("units").equals("NA")) {
						batchUpdate.setUnits(Float.parseFloat(model
								.get("units").toString()));
					}

				}

				if (model.get("meterConstant") != null) {
					if (!model.get("meterConstant").equals("NA")) {
						batchUpdate.setMeterConstant(Float.parseFloat(model
								.get("meterConstant").toString()));
					}

				}

				if (model.get("tod1") != null) {
					if (!model.get("tod1").equals("NA")) {
						batchUpdate.setTod1(Float.parseFloat(model.get("tod1")
								.toString()));
					}

				}

				if (model.get("tod2") != null) {
					if (!model.get("tod2").equals("NA")) {
						batchUpdate.setTod2(Float.parseFloat(model.get("tod2")
								.toString()));
					}

				}

				if (model.get("tod3") != null) {
					if (!model.get("tod3").equals("NA")) {
						batchUpdate.setTod3(Float.parseFloat(model.get("tod3")
								.toString()));
					}

				}

				if (model.get("dgMeterConstant") != null) {
					if (!model.get("dgMeterConstant").equals("NA")) {
						batchUpdate.setDgMeterConstant(Float.parseFloat(model
								.get("dgMeterConstant").toString()));
					}

				}

				if (model.get("dgPreviousReading") != null) {
					if (!model.get("dgPreviousReading").equals("NA")) {
						batchUpdate.setDgPreviousReading(Float.parseFloat(model
								.get("dgPreviousReading").toString()));
					}

				}

				if (model.get("dgPresentReading") != null) {
					if (!model.get("dgPresentReading").equals("NA")) {
						batchUpdate.setDgPresentReading(Float.parseFloat(model
								.get("dgPresentReading").toString()));
					}

				}

				if (model.get("dgUnits") != null) {
					if (!model.get("dgUnits").equals("NA")) {
						batchUpdate.setDgUnits(Float.parseFloat(model.get(
								"dgUnits").toString()));
					}

				}

				batchUpdates.add(batchUpdate);
			}
			for (BatchUpdate batchUpdate : batchUpdates) {
				/*new Thread(new BatchUpDateUsingThread(batchUpdate.getBatchUpdateId(), batchUpdate.getAccountNo(), batchUpdate.getServiceType(), batchUpdate.getPresentBillDate(),
						batchUpdate.getPresentStatus(), batchUpdate.getPresentReading(), batchUpdate.getPfReading(), batchUpdate.getRecordedDemand(), batchUpdate.getPreviousBillDate(),
						batchUpdate.getPreviousStatus(), batchUpdate.getPreviousReading(), batchUpdate.getUnits(), batchUpdate.getAverageUnits(), batchUpdate.getMeterConstant(), 
						batchUpdate.getTod1(), batchUpdate.getTod2(), batchUpdate.getTod3(), batchUpdate.getDgMeterConstant(), batchUpdate.getDgPreviousReading(),
						batchUpdate.getPresentReading(), batchUpdate.getDgUnits())).start();	*/

				List<ELRateMaster> elRateMasterList = new ArrayList<>();
				List<WTRateMaster> waterRateMasterList = new ArrayList<>();
				List<GasRateMaster> gasRateMasterList = new ArrayList<>();
				List<SolidWasteRateMaster> solidWasterRateMasterList = new ArrayList<>();
				int accountID = accountService.getAccountIdByNo(batchUpdate
						.getAccountNo());
				Integer avgCount = 0;

				LocalDate fromDate = new LocalDate(
						batchUpdate.getPreviousBillDate());
				LocalDate toDate = new LocalDate(
						batchUpdate.getPresentBillDate());
				Integer billableDays = Days.daysBetween(fromDate, toDate)
						.getDays();
				logger.info("::::::::::::: Total number of billable days are  :::::::::::::::::::: "
						+ billableDays);
				String billType = "Normal";
				Object dgApplicable = "No";
				String postType = "Bill";
				String meterchangeUnit = "Unit Changed:";
				if (batchUpdate.getServiceType().trim()
						.equalsIgnoreCase("Electricity")) {
					logger.info("::::::::::::: Electricity service type ::::::::::::: ");
					int tariffId = serviceMasterService
							.getServiceMasterByAccountNumber(accountID,
									batchUpdate.getServiceType());
					logger.info(":::::::::::::" + batchUpdate.getServiceType()
							+ " Tairff id is ::::::::::::: " + tariffId);
					elRateMasterList = tariffCalculationService
							.getElectricityRateMaster(tariffId);
					List<Object> list = tariffCalculationService.todApplicable(
							batchUpdate.getServiceType(), accountID);
					Integer todT1 = 0;
					Integer todT2 = 0;
					Integer todT3 = 0;
					Float dgunit = 0f;
					Object todApplicable = list.get(0);
					dgApplicable = list.get(1);
					if (todApplicable.equals("Yes")) {
						todT1 = (int) batchUpdate.getTod1();
						todT2 = (int) batchUpdate.getTod2();
						todT3 = (int) batchUpdate.getTod3();
						logger.info(" todT1::" + todT1 + " todT2::" + todT2
								+ " todT3::" + todT3);
					}

					else {
						logger.info(":::::::::Tod Is not Applicable::::::::::");
					}
					float dgUomValue = 0;
					if (dgApplicable != null) {
						if (dgApplicable.equals("Yes")) {

							dgunit = batchUpdate.getDgUnits();

							if (batchUpdate.getDgMeterConstant() == 1) {
								logger.info("::::::::::::: Meter constant is Normal :::::::::::::");
								dgUomValue = batchUpdate.getDgPresentReading()
										- batchUpdate.getDgPreviousReading();
							} else if (batchUpdate.getDgMeterConstant() < 1) {
								logger.info("::::::::::::: Meter constant is Slow :::::::::::::");
								dgUomValue = batchUpdate.getDgPresentReading()
										- batchUpdate.getDgPreviousReading();
								dgUomValue = dgUomValue
										- getMeterConstant(batchUpdate
												.getDgMeterConstant());
							} else if (batchUpdate.getDgMeterConstant() > 1) {
								logger.info("::::::::::::: Meter constant is Fast :::::::::::::");
								dgUomValue = batchUpdate.getDgPresentReading()
										- batchUpdate.getDgPreviousReading();
								dgUomValue = dgUomValue
										- getMeterConstant(batchUpdate
												.getDgMeterConstant());
							}
							logger.info("dgunit:::::::::" + dgunit);
							dgunit = dgUomValue;
						} else {
							dgApplicable = "No";
						}
					}

					float uomValue = 0;
					if (batchUpdate.getMeterConstant() == 1) {
						logger.info("::::::::::::: Meter constant is Normal :::::::::::::");
						uomValue = batchUpdate.getPresentReading()
								- batchUpdate.getPreviousReading();
					} else if (batchUpdate.getMeterConstant() < 1) {
						logger.info("::::::::::::: Meter constant is Slow :::::::::::::");
						uomValue = batchUpdate.getPresentReading()
								- batchUpdate.getPreviousReading();
						uomValue = uomValue
								- getMeterConstant(batchUpdate
										.getMeterConstant());
					} else if (batchUpdate.getMeterConstant() > 1) {
						logger.info("::::::::::::: Meter constant is Fast :::::::::::::");
						uomValue = batchUpdate.getPresentReading()
								- batchUpdate.getPreviousReading();
						uomValue = uomValue
								- getMeterConstant(batchUpdate
										.getMeterConstant());
					}

					billGenerationController.saveElectricityBillParameters(
							elRateMasterList, accountID,
							batchUpdate.getServiceType(),
							batchUpdate.getPreviousBillDate(),
							batchUpdate.getPresentBillDate(), uomValue,
							billableDays, batchUpdate.getPresentStatus(),
							batchUpdate.getPreviousStatus(),
							batchUpdate.getMeterConstant(),
							batchUpdate.getPresentReading(),
							batchUpdate.getPreviousReading(), todT1, todT2,
							todT3, dgunit, todApplicable, dgApplicable,
							batchUpdate.getDgPreviousReading(),
							batchUpdate.getDgPresentReading(),
							batchUpdate.getDgMeterConstant(),
							(double) batchUpdate.getAverageUnits(), avgCount,
							billType, batchUpdate.getPfReading(),
							batchUpdate.getRecordedDemand(), postType,
							meterchangeUnit);
				}

				if (batchUpdate.getServiceType().trim()
						.equalsIgnoreCase("Water")) {
					float uomValue = 0;
					if (batchUpdate.getMeterConstant() == 1) {
						logger.info("::::::::::::: Meter constant is Normal :::::::::::::");
						uomValue = batchUpdate.getPresentReading()
								- batchUpdate.getPreviousReading();
					} else if (batchUpdate.getMeterConstant() < 1) {
						logger.info("::::::::::::: Meter constant is Slow :::::::::::::");
						uomValue = batchUpdate.getPresentReading()
								- batchUpdate.getPreviousReading();
						uomValue = uomValue
								- getMeterConstant(batchUpdate
										.getMeterConstant());
					} else if (batchUpdate.getMeterConstant() > 1) {
						logger.info("::::::::::::: Meter constant is Fast :::::::::::::");
						uomValue = batchUpdate.getPresentReading()
								- batchUpdate.getPreviousReading();
						uomValue = uomValue
								- getMeterConstant(batchUpdate
										.getMeterConstant());
					}
					logger.info("::::::::::::: Water service type ::::::::::::: ");
					int waterTariffId = serviceMasterService.getWaterTariffId(
							accountID, batchUpdate.getServiceType());
					logger.info(":::::::::::::" + batchUpdate.getServiceType()
							+ " Tairff id is ::::::::::::: " + waterTariffId);
					waterRateMasterList = tariffCalculationService
							.getWaterRateMaster(waterTariffId);
					billGenerationController.saveWaterBillParameters(
							waterRateMasterList, accountID,
							batchUpdate.getServiceType(),
							batchUpdate.getPreviousBillDate(),
							batchUpdate.getPresentBillDate(), uomValue,
							billableDays, batchUpdate.getPresentStatus(),
							batchUpdate.getPreviousStatus(),
							batchUpdate.getMeterConstant(),
							batchUpdate.getPresentReading(),
							batchUpdate.getPreviousReading(),
							(double) batchUpdate.getAverageUnits(), avgCount,
							billType, postType, meterchangeUnit);
				}

				if (batchUpdate.getServiceType().trim().equalsIgnoreCase("Gas")) {
					float uomValue = 0;
					if (batchUpdate.getMeterConstant() == 1) {
						logger.info("::::::::::::: Meter constant is Normal :::::::::::::");
						uomValue = batchUpdate.getPresentReading()
								- batchUpdate.getPreviousReading();
					} else if (batchUpdate.getMeterConstant() < 1) {
						logger.info("::::::::::::: Meter constant is Slow :::::::::::::");
						uomValue = batchUpdate.getPresentReading()
								- batchUpdate.getPreviousReading();
						uomValue = uomValue
								- getMeterConstant(batchUpdate
										.getMeterConstant());
					} else if (batchUpdate.getMeterConstant() > 1) {
						logger.info("::::::::::::: Meter constant is Fast :::::::::::::");
						uomValue = batchUpdate.getPresentReading()
								- batchUpdate.getPreviousReading();
						uomValue = uomValue
								- getMeterConstant(batchUpdate
										.getMeterConstant());
					}

					logger.info("::::::::::::: Gas service type ::::::::::::: "
							+ accountID + " ---------------- "
							+ batchUpdate.getServiceType());
					int gasTariffId = serviceMasterService.getGasTariffId(
							accountID, batchUpdate.getServiceType());
					logger.info(":::::::::::::" + batchUpdate.getServiceType()
							+ " Tairff id is ::::::::::::: " + gasTariffId);
					gasRateMasterList = tariffCalculationService
							.getGasRateMaster(gasTariffId);
					billGenerationController.saveGasBillParameters(
							gasRateMasterList, accountID,
							batchUpdate.getServiceType(),
							batchUpdate.getPreviousBillDate(),
							batchUpdate.getPresentBillDate(), uomValue,
							billableDays, batchUpdate.getPresentStatus(),
							batchUpdate.getPreviousStatus(),
							batchUpdate.getMeterConstant(),
							batchUpdate.getPresentReading(),
							batchUpdate.getPreviousReading(),
							(double) batchUpdate.getAverageUnits(), avgCount,
							billType, postType, meterchangeUnit);
				}

				if (batchUpdate.getServiceType().trim()
						.equalsIgnoreCase("Others")) {
					logger.info("::::::::::::: Othres service type ::::::::::::: ");
					billGenerationController.saveOthersBillParameters(
							accountID, batchUpdate.getServiceType(), postType,
							batchUpdate.getPreviousBillDate(),
							batchUpdate.getPresentBillDate(),
							(double) batchUpdate.getAverageUnits(), avgCount);
				}

				if (batchUpdate.getServiceType().trim()
						.equalsIgnoreCase("Solid Waste")) {
					logger.info("::::::::::::: Solid Waste service type ::::::::::::: "
							+ accountID
							+ "----- "
							+ batchUpdate.getServiceType());
					int solidWasteTariffId = serviceMasterService
							.getSolidWasteTariffId(accountID,
									batchUpdate.getServiceType());
					logger.info(":::::::::::::" + batchUpdate.getServiceType()
							+ " Tairff id is ::::::::::::: "
							+ solidWasteTariffId);
					solidWasterRateMasterList = tariffCalculationService
							.getSolidWasteRateMaster(solidWasteTariffId);
					billGenerationController.saveSolidWasteBillParameters(
							solidWasterRateMasterList,
							accountID,
							batchUpdate.getServiceType(),
							batchUpdate.getPreviousBillDate(),
							batchUpdate.getPresentBillDate(),
							batchUpdate.getPresentReading()
									- batchUpdate.getPreviousReading(),
							billableDays, batchUpdate.getPresentStatus(),
							batchUpdate.getPreviousStatus(),
							batchUpdate.getMeterConstant(),
							batchUpdate.getPresentReading(),
							batchUpdate.getPreviousReading(),
							(double) batchUpdate.getAverageUnits(), avgCount,
							billType, postType);
				}
			}
		}
		return batchUpdates;
    }
    
    private Float getMeterConstant(Float meterConstantValue) {
		return (float) ((meterConstantValue - 1) * 100);
	}
    @RequestMapping(value = "/batchUpdate/exportToExcel/{serviceTypeToExport}/{presentbilldateToExport}/{blockIdToExport}", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void exportToExcel(HttpServletRequest req,HttpServletResponse res, @PathVariable String serviceTypeToExport,@PathVariable String presentbilldateToExport,@PathVariable String blockIdToExport)throws ParseException, JSONException, IOException {
    	logger.info("Export to Excel "+"------"+serviceTypeToExport+"-----------"+presentbilldateToExport+"-----------"+blockIdToExport);

		logger.info("::::::::::::::::::: Creating Batch Method :::::::::::::::::::::");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date presentbilldate = formatter.parse(presentbilldateToExport); 
        int accountId=0;  
        String blockNameToExport = null;
        int blockId = 0;
		List<Map<String, Object>> servicemasterMap =  new LinkedList<Map<String, Object>>();
		logger.info("(blockIdToExport==null) --------- "+(blockIdToExport==null));
		if(blockIdToExport.equalsIgnoreCase("null"))
		{
			logger.info("-------------------- Getting all block information--------------------");
			blockNameToExport = "All Blocks";	
		}
		else
		{
			blockId = Integer.parseInt(blockIdToExport);
			blockNameToExport="";
		}
		
		Map<String, Object> map = new HashMap<>();
		logger.info("blockNameToExport ------------- "+blockNameToExport);
		if(blockNameToExport!=null)
		{
			if (blockNameToExport.trim().equalsIgnoreCase("All Blocks")) 
			{
				logger.info("------------ Getting All Blocks account id -------------------");
				List<Integer> list = tariffCalculationService.getallBlocks();
				for (int i = 0; i < list.size(); i++) {
					
					
					Integer blockIde=list.get(i);
					List<Integer> properties = tariffCalculationService.getProertyId(blockIde);
					for (final Integer propartyId : properties)
					{
						List<Integer> personIdList = tariffCalculationService.findPersonDetail(propartyId);
						for (final Integer personId : personIdList)
						{
							accountId=tariffCalculationService.findAccountDetailByServiceType(personId, propartyId,serviceTypeToExport);
							logger.info("accountId ---------------- "+accountId);
							if (accountId != 0)
							{
								ServiceMastersEntity serviceMastersEntities = serviceMasterService.getServiceMasterServicType(accountId, serviceTypeToExport);
								if(serviceMastersEntities!=null)
								{
									//logger.info("account Number ==> "+serviceMastersEntities.getAccountObj().getAccountNo() +"   propartyId ==> "+propartyId+" Block Number ==> "+blocks.getBlockName());
									map = tariffCalculationService.getBillDetails(serviceMastersEntities,serviceMastersEntities.getAccountObj(),serviceTypeToExport,presentbilldate);
									if(!map.isEmpty())
									{
										servicemasterMap.add(map);
									}
								}
							}
						}
					}
				}
			}
			else 
			{
				logger.info("------------ Getting Single blocks account id -------------------");
				List<Integer> properties = tariffCalculationService.getProertyId(blockId);
				for (Integer propartyId : properties) {
					int propId = propartyId;
					List<Integer> personIdList = tariffCalculationService.findPersonDetail(propId);
					for (Integer personId  : personIdList) {
						accountId = tariffCalculationService.findAccountDetailByServiceType(personId, propartyId,serviceTypeToExport);
						logger.info("accountId -------------------- "+accountId);
						if (accountId != 0) {
							ServiceMastersEntity serviceMastersEntities = serviceMasterService.getServiceMasterServicType(accountId, serviceTypeToExport);
							if(serviceMastersEntities!=null)
							{
								logger.info("account Number ==> "+serviceMastersEntities.getAccountObj().getAccountNo() +"   propartyId ==> "+propartyId+" Block Number ==> "+blockIdToExport);
								map=tariffCalculationService.getBillDetails(serviceMastersEntities,serviceMastersEntities.getAccountObj(),serviceTypeToExport,presentbilldate);
								if(!map.isEmpty())
								{
									servicemasterMap.add(map);
								}
							}
						}
					}
				}
			}
		}
		logger.info("servicemasterMap size --------------- "+servicemasterMap.size());
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Batch Bills");
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("Account Number");
		header.createCell(1).setCellValue("Service Type");
		header.createCell(2).setCellValue("Present Bill Date");
		header.createCell(3).setCellValue("Present Status");
		header.createCell(4).setCellValue("Present Reading");
		header.createCell(5).setCellValue("DG Present Reading");
		header.createCell(6).setCellValue("PF Reading");
		header.createCell(7).setCellValue("Recorded Demand");
		header.createCell(8).setCellValue("TOD1");
		header.createCell(9).setCellValue("TOD2");
		header.createCell(10).setCellValue("TOD3");
		header.createCell(11).setCellValue("Previous Bill Date");
		header.createCell(12).setCellValue("Previous Status");
		header.createCell(13).setCellValue("Previous Reading");
		header.createCell(14).setCellValue("DG Previous reading");
		header.createCell(15).setCellValue("Meter Constant");
		header.createCell(16).setCellValue("DG Meter Constant");
		
       /* HSSFFont my_font=workbook.createFont();
        my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        my_style.setFont(my_font);
         At this stage, we have a bold style created which we can attach to a cell 
        */
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		sheet.autoSizeColumn(8);
		sheet.autoSizeColumn(9);
		sheet.autoSizeColumn(10);
		sheet.autoSizeColumn(11);
		sheet.autoSizeColumn(12);
		sheet.autoSizeColumn(13);
		sheet.autoSizeColumn(14);
		sheet.autoSizeColumn(15);
		sheet.autoSizeColumn(16);
		
		SimpleDateFormat excelDateFormate = new SimpleDateFormat("dd-MMM-yyyy");
		int rownum = 1;
		for (Map<String, Object> map3 : servicemasterMap)
		{
			 Row row = sheet.createRow(rownum++);
	            
	            for (Entry<String, Object> entry : map3.entrySet()) {
	            	if(entry.getKey().equalsIgnoreCase("accountNo"))
	            	{
	            		row.createCell(0).setCellValue((String)entry.getValue());
	            	}
	            	if(entry.getKey().equalsIgnoreCase("serviceType"))
	            	{
	            		row.createCell(1).setCellValue((String)entry.getValue());
	            	}
	            	if(entry.getKey().equalsIgnoreCase("presentbilldate"))
	            	{
	            		if(!(entry.getValue().equals("NA")))
		        		{
	            			row.createCell(2).setCellValue(excelDateFormate.format((Date)entry.getValue()));
		        		}
	            	}
	            	
	            	/*cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));
	            	row.createCell(2).setCellValue(excelDateFormate.format(new Date()));
        			row.createCell(2).setCellStyle(cellStyle);*/
        			
	            	if(entry.getKey().equalsIgnoreCase("presentStatus"))
	            	{
	            		row.createCell(3).setCellValue((String)entry.getValue());
	            	}
	            	
	            	if(entry.getKey().equalsIgnoreCase("presentReading"))
	            	{
	            		row.createCell(4).setCellValue((String)entry.getValue());
	            	}
	            	
	            	if(entry.getKey().equalsIgnoreCase("dgPresentReading"))
	            	{
	            		row.createCell(5).setCellValue((String)entry.getValue());
	            	}
	            	
	            	if(entry.getKey().equalsIgnoreCase("pfReading"))
	            	{
	            		row.createCell(6).setCellValue((String)entry.getValue());
	            	}
	            	
	            	if(entry.getKey().equalsIgnoreCase("recordedDemand"))
	            	{
	            		row.createCell(7).setCellValue((String)entry.getValue());
	            	}
	            	
	            	if(entry.getKey().equalsIgnoreCase("tod1"))
	            	{
	            		row.createCell(8).setCellValue((String)entry.getValue());
	            	}
	            	
	            	if(entry.getKey().equalsIgnoreCase("tod2"))
	            	{
	            		row.createCell(9).setCellValue((String)entry.getValue());
	            	}
	            	
	            	if(entry.getKey().equalsIgnoreCase("tod3"))
	            	{
	            		row.createCell(10).setCellValue((String)entry.getValue());
	            	}
	            	
	            	if(entry.getKey().equalsIgnoreCase("previousBillDate"))
	            	{
	            		if(!(entry.getValue().equals("NA")))
		        		{
	            			row.createCell(11).setCellValue(excelDateFormate.format((Date)entry.getValue()));
		        		}
	            	}
	            	
	            	if(entry.getKey().equalsIgnoreCase("previousStatus"))
	            	{
	            		row.createCell(12).setCellValue((String)entry.getValue());
	            	}
	            	
	            	if(entry.getKey().equalsIgnoreCase("previousReading"))
	            	{
	            		row.createCell(13).setCellValue((String)entry.getValue());
	            	}
	            	
	            	if(entry.getKey().equalsIgnoreCase("dgPreviousReading"))
	            	{
	            		row.createCell(14).setCellValue((String)entry.getValue());
	            	}
	            	
	            	if(entry.getKey().equalsIgnoreCase("meterConstant"))
	            	{
	            		row.createCell(15).setCellValue((String)entry.getValue());
	            	}
	            	
	            	if(entry.getKey().equalsIgnoreCase("dgMeterConstant"))
	            	{
	            		row.createCell(16).setCellValue((String)entry.getValue());
	            	}
	            }
		}
			
		res.setContentType("application/ms-excel");
		res.setHeader("Content-Disposition", "attachment; filename=BatchBills.xls");
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		byte[] outArray = outByteStream.toByteArray();
		OutputStream outStream = res.getOutputStream();
		outStream.write(outArray);
		outStream.flush();
	}
    
	@RequestMapping(value = "/batchUpdate/createBatch", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<?> generateBulkBill(HttpServletRequest req,HttpServletResponse response, @RequestBody String body)throws ParseException, JSONException {
		logger.info("::::::::::::::::::: Creating Batch Method :::::::::::::::::::::");
		String blockName = req.getParameter("blockName");
		final String serviceType = req.getParameter("serviceTypeList");
		int blockId = Integer.parseInt(req.getParameter("blocId"));
		//SimpleDateFormat formatter = new SimpleDateFormat("MMM yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Date presentbilldate = formatter.parse(req.getParameter("presentbilldate")); 
        int accountId=0;  
		List<Map<String, Object>> servicemasterMap =  new LinkedList<Map<String, Object>>(); 
		Map<String, Object> map = new HashMap<>();
		if(blockName!=null)
		{
			if (blockName.trim().equalsIgnoreCase("All Blocks")) 
			{
				logger.info("------------ Getting All Blocks account id -------------------");
				List<Integer> list = tariffCalculationService.getallBlocks();
				for (int i = 0; i < list.size(); i++) {
				Integer blockIde=list.get(i);
					List<Integer> properties = tariffCalculationService.getProertyId(blockIde);
					for (final Integer propartyId : properties)
					{
						List<Integer> personIdList = tariffCalculationService.findPersonDetail(propartyId);
						for (final Integer personId : personIdList)
						{
							accountId=tariffCalculationService.findAccountDetailByServiceType(personId, propartyId,serviceType);
							if (accountId != 0)
							{
								ServiceMastersEntity serviceMastersEntities = serviceMasterService.getServiceMasterServicType(accountId, serviceType);
								if(serviceMastersEntities!=null)
								{
									map = tariffCalculationService.getBillDetails(serviceMastersEntities,serviceMastersEntities.getAccountObj(),serviceType,presentbilldate);
									if(!map.isEmpty())
									{
										map.put("presentBillDate", DateFormatUtils.format(presentbilldate, "dd/MM/yyyy"));
										map.put("blockIdToExport", null);
										servicemasterMap.add(map);
									}
								}
							}
						}
					}
				}
			}
			else 
			{
				logger.info("------------ Getting Single blocks account id -------------------");
				List<Integer> properties = tariffCalculationService.getProertyId(blockId);
				for (Integer propartyId : properties) {
					int propId = propartyId;
					List<Integer> personIdList = tariffCalculationService.findPersonDetail(propId);
					for (Integer personId  : personIdList) {
						accountId = tariffCalculationService.findAccountDetailByServiceType(personId, propartyId,serviceType);
						if (accountId != 0) {
							ServiceMastersEntity serviceMastersEntities = serviceMasterService.getServiceMasterServicType(accountId, serviceType);
							if(serviceMastersEntities!=null)
							{
								map=tariffCalculationService.getBillDetails(serviceMastersEntities,serviceMastersEntities.getAccountObj(),serviceType,presentbilldate);
								if(!map.isEmpty())
								{
									map.put("blockIdToExport", blockId);
									map.put("presentBillDate", DateFormatUtils.format(presentbilldate, "dd/MM/yyyy"));
									servicemasterMap.add(map);
								}
							}
						}
					}
				}
			}
		}
		return servicemasterMap;
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "/batchUpdate/uploadBatchBills", method = RequestMethod.POST)
	public @ResponseBody
	void uploadBatchDocument(@RequestParam MultipartFile files,HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
		logger.info("--------------- In Uploading Batch file method -------------------");
		synchronized (this) {
			HSSFWorkbook workbook = new HSSFWorkbook(files.getInputStream()); //Read the Excel Workbook in a instance object    
			HSSFSheet sheet = workbook.getSheetAt(0);
			/*XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);*/
			String meterchangeUnit = "Unit Changed:";
			String naString = "NA";
			Iterator rows = sheet.rowIterator();
			int i = 0;
			List<BatchUpdate> batchUpdates = new ArrayList<BatchUpdate>();
			while (rows.hasNext()) {
				HSSFRow row = (HSSFRow) rows.next();
				/*XSSFRow row = ((XSSFRow) rows.next());*/
				BatchUpdate batchUpdate = new BatchUpdate();
				if (row.getRowNum() == 0) {
					continue; // just skip the rows if row number is 0 or 1
				}

				//getting account numbre
				if (!StringUtils.isEmpty(row.getCell(0).getStringCellValue())) {
					batchUpdate.setAccountNo(row.getCell(0)
							.getStringCellValue().trim());
				} else {
					logger.info("FAILED");
					i = 1;
				}

				//getting service type
				if (!StringUtils.isEmpty(row.getCell(1).getStringCellValue())) {
					batchUpdate.setServiceType((row.getCell(1)
							.getStringCellValue()).trim());
				} else {
					logger.info("FAILED");
					i = 1;
				}

				//getting present bill date
				String presentBillDate = row.getCell(2).getStringCellValue();
				if (!StringUtils.isEmpty(presentBillDate)) {
					DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
					/*DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");*/
					batchUpdate.setPresentBillDate((Date) formatter
							.parse(presentBillDate));
				} else {
					logger.info("FAILED");
					i = 1;
				}

				//getting present status
				if (!StringUtils.isEmpty(row.getCell(3).getStringCellValue())) {
					if ((row.getCell(3).getStringCellValue()).trim() != null) {
						if (!(row.getCell(3).getStringCellValue()).trim()
								.equalsIgnoreCase("NA")) {
							batchUpdate.setPresentStatus((row.getCell(3)
									.getStringCellValue()).trim());
						}
					}

				} else {
					logger.info("FAILED");
					i = 1;
				}

				// getting present reading
				if (row.getCell(4) != null) {
					String cell4value = row.getCell(4).toString();
					if (!(cell4value.trim().equalsIgnoreCase(naString))) {
						batchUpdate.setPresentReading(Float.parseFloat((row
								.getCell(4).toString())));
					}
				} else {
					logger.info("FAILED");
					i = 1;
				}

				// getting DG present reading
				if (row.getCell(5) != null) {
					String cell5value = row.getCell(5).toString();
					if (!(cell5value.trim().equalsIgnoreCase(naString))) {
						batchUpdate.setDgPresentReading(Float.parseFloat((row
								.getCell(5).toString())));
					}
				} else {
					logger.info("FAILED");
					i = 1;
				}

				// getting PF reading
				if (row.getCell(6) != null) {
					String cell6value = row.getCell(6).toString();
					if (!(cell6value.trim().equalsIgnoreCase(naString))) {
						batchUpdate.setPfReading(Float.parseFloat((row
								.getCell(6).toString())));
					}
				} else {
					logger.info("FAILED");
					i = 1;
				}

				// getting Recorded Demand reading
				if (row.getCell(7) != null) {
					String cell7value = row.getCell(7).toString();
					if (!(cell7value.trim().equalsIgnoreCase(naString))) {
						batchUpdate.setRecordedDemand(Float.parseFloat((row
								.getCell(7).toString())));
					}
				} else {
					logger.info("FAILED");
					i = 1;
				}

				// getting TOD1 reading
				if (row.getCell(8) != null) {
					String cell8value = row.getCell(8).toString();
					if (!(cell8value.trim().equalsIgnoreCase(naString))) {
						batchUpdate.setTod1(Float.parseFloat((row.getCell(8)
								.toString())));
					}
				} else {
					logger.info("FAILED");
					i = 1;
				}

				// getting TOD2 reading
				if (row.getCell(9) != null) {
					String cell9value = row.getCell(9).toString();
					if (!(cell9value.trim().equalsIgnoreCase(naString))) {
						batchUpdate.setTod2(Float.parseFloat((row.getCell(9)
								.toString())));
					}
				} else {
					logger.info("FAILED");
					i = 1;
				}

				// getting TOD2 reading
				if (row.getCell(10) != null) {
					String cell10value = row.getCell(10).toString();
					if (!(cell10value.trim().equalsIgnoreCase(naString))) {
						batchUpdate.setTod3(Float.parseFloat((row.getCell(10)
								.toString())));
					}
				} else {
					logger.info("FAILED");
					i = 1;
				}

				// getting previous Bill date reading
				String previousBillDate = row.getCell(11).toString();
				if (!StringUtils.isEmpty(previousBillDate)) {

					if (!(previousBillDate.trim().equalsIgnoreCase(naString))) {
						/*DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");*/
						DateFormat formatter = new SimpleDateFormat(
								"dd-MMM-yyyy");
						batchUpdate.setPreviousBillDate((Date) formatter
								.parse(previousBillDate));
					}
				} else {
					logger.info("FAILED");
					i = 1;
				}

				//getting previous status
				if (!StringUtils.isEmpty(row.getCell(12).getStringCellValue())) {
					if ((row.getCell(12).getStringCellValue()).trim() != null) {
						if (!(row.getCell(12).getStringCellValue()).trim()
								.equalsIgnoreCase("NA")) {
							batchUpdate.setPreviousStatus((row.getCell(12)
									.getStringCellValue()).trim());
						}
					}

				} else {
					logger.info("FAILED");
					i = 1;
				}

				// getting previous reading
				if (row.getCell(13) != null) {
					String cell13value = row.getCell(13).toString();

					if (!(cell13value.trim().equalsIgnoreCase(naString))) {
						batchUpdate.setPreviousReading(Float.parseFloat(row
								.getCell(13).toString()));
					}
				} else {
					logger.info("FAILED");
					i = 1;
				}

				// getting DG Previous reading
				if (row.getCell(14) != null) {
					String cell14value = row.getCell(14).toString();
					if (!(cell14value.trim().equalsIgnoreCase(naString))) {
						batchUpdate.setDgPreviousReading(Float.parseFloat(row
								.getCell(14).toString()));
					}
				} else {
					logger.info("FAILED");
					i = 1;
				}

				// getting Meter constant reading
				if (row.getCell(15) != null) {
					String cell15value = row.getCell(15).toString();
					if (!(cell15value.trim().equalsIgnoreCase(naString))) {
						batchUpdate.setMeterConstant(Float.parseFloat(row
								.getCell(15).toString()));
					}
				} else {
					logger.info("FAILED");
					i = 1;
				}

				// getting DG Meter constant reading
				if (row.getCell(16) != null) {
					String cell16value = row.getCell(16).toString();
					if (!(cell16value.trim().equalsIgnoreCase(naString))) {
						batchUpdate.setDgMeterConstant(Float.parseFloat(row
								.getCell(16).toString()));
					}
				} else {
					logger.info("FAILED");
					i = 1;
				}
				batchUpdates.add(batchUpdate);
			}
			for (BatchUpdate batchUpdate2 : batchUpdates) {
				/*			logger.info("batchUpdate2.getAccountNo() ----------------- "+batchUpdate2.getAccountNo());
				 logger.info("batchUpdate2.getServiceType() ----------------- "+batchUpdate2.getServiceType());
				 logger.info("batchUpdate2.getPresentBillDate() ----------------- "+batchUpdate2.getPresentBillDate());
				 logger.info("batchUpdate2.getPresentStatus() ----------------- "+batchUpdate2.getPresentStatus());
				 logger.info("batchUpdate2.getPresentReading() ----------------- "+batchUpdate2.getPresentReading());
				 logger.info("batchUpdate2.getDgPresentReading() ----------------- "+batchUpdate2.getDgPresentReading());
				 logger.info("batchUpdate2.getPfReading() ----------------- "+batchUpdate2.getPfReading());
				 logger.info("batchUpdate2.getRecordedDemand() ----------------- "+batchUpdate2.getRecordedDemand());
				 logger.info("batchUpdate2.getTod1() ----------------- "+batchUpdate2.getTod1());
				 logger.info("batchUpdate2.getTod2() ----------------- "+batchUpdate2.getTod2());
				 logger.info("batchUpdate2.getTod3() ----------------- "+batchUpdate2.getTod3());
				 logger.info("batchUpdate2.getPreviousBillDate() ----------------- "+batchUpdate2.getPreviousBillDate());
				 logger.info("batchUpdate2.getPreviousStatus() ----------------- "+batchUpdate2.getPreviousStatus());
				 logger.info("batchUpdate2.getPreviousReading() ----------------- "+batchUpdate2.getPreviousReading());
				 logger.info("batchUpdate2.getDgPreviousReading() ----------------- "+batchUpdate2.getDgPreviousReading());
				 logger.info("batchUpdate2.getMeterConstant() ----------------- "+batchUpdate2.getMeterConstant());
				 logger.info("batchUpdate2.getDgMeterConstant() ----------------- "+batchUpdate2.getDgMeterConstant());*/

				/*new Thread(new BatchUpDateUsingThread(batchUpdate.getBatchUpdateId(), batchUpdate.getAccountNo(), batchUpdate.getServiceType(), batchUpdate.getPresentBillDate(),
						batchUpdate.getPresentStatus(), batchUpdate.getPresentReading(), batchUpdate.getPfReading(), batchUpdate.getRecordedDemand(), batchUpdate.getPreviousBillDate(),
						batchUpdate.getPreviousStatus(), batchUpdate.getPreviousReading(), batchUpdate.getUnits(), batchUpdate.getAverageUnits(), batchUpdate.getMeterConstant(), 
						batchUpdate.getTod1(), batchUpdate.getTod2(), batchUpdate.getTod3(), batchUpdate.getDgMeterConstant(), batchUpdate.getDgPreviousReading(),
						batchUpdate.getPresentReading(), batchUpdate.getDgUnits())).start();	*/

				List<ELRateMaster> elRateMasterList = new ArrayList<>();
				List<WTRateMaster> waterRateMasterList = new ArrayList<>();
				List<GasRateMaster> gasRateMasterList = new ArrayList<>();
				List<SolidWasteRateMaster> solidWasterRateMasterList = new ArrayList<>();
				int accountID = accountService.getAccountIdByNo(batchUpdate2
						.getAccountNo());
				Integer avgCount = 0;

				LocalDate fromDate = new LocalDate(
						batchUpdate2.getPreviousBillDate());
				LocalDate toDate = new LocalDate(
						batchUpdate2.getPresentBillDate());
				Integer billableDays = Days.daysBetween(fromDate, toDate)
						.getDays();
				logger.info("::::::::::::: Total number of billable days are  :::::::::::::::::::: "
						+ billableDays);
				String billType = "Normal";
				Object dgApplicable = "No";
				String postType = "Bill";

				if (batchUpdate2.getServiceType().trim()
						.equalsIgnoreCase("Electricity")) {

					logger.info("::::::::::::: Electricity service type ::::::::::::: ");
					int tariffId = serviceMasterService
							.getServiceMasterByAccountNumber(accountID,
									batchUpdate2.getServiceType());
					logger.info(":::::::::::::" + batchUpdate2.getServiceType()
							+ " Tairff id is ::::::::::::: " + tariffId);
					elRateMasterList = tariffCalculationService
							.getElectricityRateMaster(tariffId);
					List<Object> list = tariffCalculationService.todApplicable(
							batchUpdate2.getServiceType(), accountID);
					Integer todT1 = 0;
					Integer todT2 = 0;
					Integer todT3 = 0;
					Float dgunit = 0f;
					Object todApplicable = list.get(0);
					dgApplicable = list.get(1);

					if (todApplicable.equals("Yes")) {
						todT1 = (int) batchUpdate2.getTod1();
						todT2 = (int) batchUpdate2.getTod2();
						todT3 = (int) batchUpdate2.getTod3();
						logger.info(" todT1::" + todT1 + " todT2::" + todT2
								+ " todT3::" + todT3);
					}

					else {
						logger.info(":::::::::Tod Is not Applicable::::::::::");
					}
					float dgUomValue = 0;
					if (dgApplicable != null) {
						if (dgApplicable.equals("Yes")) {

							dgunit = batchUpdate2.getDgUnits();

							if (batchUpdate2.getDgMeterConstant() == 1) {
								logger.info("::::::::::::: Meter constant is Normal :::::::::::::");
								dgUomValue = batchUpdate2.getDgPresentReading()
										- batchUpdate2.getDgPreviousReading();
							} else if (batchUpdate2.getDgMeterConstant() < 1) {
								logger.info("::::::::::::: Meter constant is Slow :::::::::::::");
								dgUomValue = batchUpdate2.getDgPresentReading()
										- batchUpdate2.getDgPreviousReading();
								dgUomValue = dgUomValue
										- getMeterConstant(batchUpdate2
												.getDgMeterConstant());
							} else if (batchUpdate2.getDgMeterConstant() > 1) {
								logger.info("::::::::::::: Meter constant is Fast :::::::::::::");
								dgUomValue = batchUpdate2.getDgPresentReading()
										- batchUpdate2.getDgPreviousReading();
								dgUomValue = dgUomValue
										- getMeterConstant(batchUpdate2
												.getDgMeterConstant());
							}
							logger.info("dgunit:::::::::" + dgunit);
							dgunit = dgUomValue;
						} else {
							dgApplicable = "No";
						}
					}

					float uomValue = 0;
					if (batchUpdate2.getMeterConstant() == 1) {
						logger.info("::::::::::::: Meter constant is Normal :::::::::::::");
						uomValue = batchUpdate2.getPresentReading()
								- batchUpdate2.getPreviousReading();
					} else if (batchUpdate2.getMeterConstant() < 1) {
						logger.info("::::::::::::: Meter constant is Slow :::::::::::::");
						uomValue = batchUpdate2.getPresentReading()
								- batchUpdate2.getPreviousReading();
						uomValue = uomValue
								- getMeterConstant(batchUpdate2
										.getMeterConstant());
					} else if (batchUpdate2.getMeterConstant() > 1) {
						logger.info("::::::::::::: Meter constant is Fast :::::::::::::");
						uomValue = batchUpdate2.getPresentReading()
								- batchUpdate2.getPreviousReading();
						uomValue = uomValue
								- getMeterConstant(batchUpdate2
										.getMeterConstant());
					}
					logger.info("batchUpdate2.getPfReading() --------------------- "
							+ batchUpdate2.getPfReading());
					billGenerationController.saveElectricityBillParameters(
							elRateMasterList, accountID,
							batchUpdate2.getServiceType(),
							batchUpdate2.getPreviousBillDate(),
							batchUpdate2.getPresentBillDate(), uomValue,
							billableDays, batchUpdate2.getPresentStatus(),
							batchUpdate2.getPreviousStatus(),
							batchUpdate2.getMeterConstant(),
							batchUpdate2.getPresentReading(),
							batchUpdate2.getPreviousReading(), todT1, todT2,
							todT3, dgunit, todApplicable, dgApplicable,
							batchUpdate2.getDgPreviousReading(),
							batchUpdate2.getDgPresentReading(),
							batchUpdate2.getDgMeterConstant(),
							(double) batchUpdate2.getAverageUnits(), avgCount,
							billType, batchUpdate2.getPfReading(),
							batchUpdate2.getRecordedDemand(), postType,
							meterchangeUnit);
				}

				if (batchUpdate2.getServiceType().trim()
						.equalsIgnoreCase("Water")) {
					float uomValue = 0;
					if (batchUpdate2.getMeterConstant() == 1) {
						logger.info("::::::::::::: Meter constant is Normal :::::::::::::");
						uomValue = batchUpdate2.getPresentReading()
								- batchUpdate2.getPreviousReading();
					} else if (batchUpdate2.getMeterConstant() < 1) {
						logger.info("::::::::::::: Meter constant is Slow :::::::::::::");
						uomValue = batchUpdate2.getPresentReading()
								- batchUpdate2.getPreviousReading();
						uomValue = uomValue
								- getMeterConstant(batchUpdate2
										.getMeterConstant());
					} else if (batchUpdate2.getMeterConstant() > 1) {
						logger.info("::::::::::::: Meter constant is Fast :::::::::::::");
						uomValue = batchUpdate2.getPresentReading()
								- batchUpdate2.getPreviousReading();
						uomValue = uomValue
								- getMeterConstant(batchUpdate2
										.getMeterConstant());
					}
					logger.info("::::::::::::: Water service type ::::::::::::: ");
					int waterTariffId = serviceMasterService.getWaterTariffId(
							accountID, batchUpdate2.getServiceType());
					logger.info(":::::::::::::" + batchUpdate2.getServiceType()
							+ " Tairff id is ::::::::::::: " + waterTariffId);
					waterRateMasterList = tariffCalculationService
							.getWaterRateMaster(waterTariffId);
					billGenerationController.saveWaterBillParameters(
							waterRateMasterList, accountID,
							batchUpdate2.getServiceType(),
							batchUpdate2.getPreviousBillDate(),
							batchUpdate2.getPresentBillDate(), uomValue,
							billableDays, batchUpdate2.getPresentStatus(),
							batchUpdate2.getPreviousStatus(),
							batchUpdate2.getMeterConstant(),
							batchUpdate2.getPresentReading(),
							batchUpdate2.getPreviousReading(),
							(double) batchUpdate2.getAverageUnits(), avgCount,
							billType, postType, meterchangeUnit);
				}

				if (batchUpdate2.getServiceType().trim()
						.equalsIgnoreCase("Gas")) {
					float uomValue = 0;
					if (batchUpdate2.getMeterConstant() == 1) {
						logger.info("::::::::::::: Meter constant is Normal :::::::::::::");
						uomValue = batchUpdate2.getPresentReading()
								- batchUpdate2.getPreviousReading();
					} else if (batchUpdate2.getMeterConstant() < 1) {
						logger.info("::::::::::::: Meter constant is Slow :::::::::::::");
						uomValue = batchUpdate2.getPresentReading()
								- batchUpdate2.getPreviousReading();
						uomValue = uomValue
								- getMeterConstant(batchUpdate2
										.getMeterConstant());
					} else if (batchUpdate2.getMeterConstant() > 1) {
						logger.info("::::::::::::: Meter constant is Fast :::::::::::::");
						uomValue = batchUpdate2.getPresentReading()
								- batchUpdate2.getPreviousReading();
						uomValue = uomValue
								- getMeterConstant(batchUpdate2
										.getMeterConstant());
					}

					logger.info("::::::::::::: Gas service type ::::::::::::: "
							+ accountID + " ---------------- "
							+ batchUpdate2.getServiceType());
					int gasTariffId = serviceMasterService.getGasTariffId(
							accountID, batchUpdate2.getServiceType());
					logger.info(":::::::::::::" + batchUpdate2.getServiceType()
							+ " Tairff id is ::::::::::::: " + gasTariffId);
					gasRateMasterList = tariffCalculationService
							.getGasRateMaster(gasTariffId);
					billGenerationController.saveGasBillParameters(
							gasRateMasterList, accountID,
							batchUpdate2.getServiceType(),
							batchUpdate2.getPreviousBillDate(),
							batchUpdate2.getPresentBillDate(), uomValue,
							billableDays, batchUpdate2.getPresentStatus(),
							batchUpdate2.getPreviousStatus(),
							batchUpdate2.getMeterConstant(),
							batchUpdate2.getPresentReading(),
							batchUpdate2.getPreviousReading(),
							(double) batchUpdate2.getAverageUnits(), avgCount,
							billType, postType, meterchangeUnit);
				}

				if (batchUpdate2.getServiceType().trim()
						.equalsIgnoreCase("Others")) {
					logger.info("::::::::::::: Othres service type ::::::::::::: ");
					billGenerationController.saveOthersBillParameters(
							accountID, batchUpdate2.getServiceType(), postType,
							batchUpdate2.getPreviousBillDate(),
							batchUpdate2.getPresentBillDate(),
							(double) batchUpdate2.getAverageUnits(), avgCount);
				}

				if (batchUpdate2.getServiceType().trim()
						.equalsIgnoreCase("Solid Waste")) {
					logger.info("::::::::::::: Solid Waste service type ::::::::::::: "
							+ accountID
							+ "----- "
							+ batchUpdate2.getServiceType());
					int solidWasteTariffId = serviceMasterService
							.getSolidWasteTariffId(accountID,
									batchUpdate2.getServiceType());
					logger.info(":::::::::::::" + batchUpdate2.getServiceType()
							+ " Tairff id is ::::::::::::: "
							+ solidWasteTariffId);
					solidWasterRateMasterList = tariffCalculationService
							.getSolidWasteRateMaster(solidWasteTariffId);
					billGenerationController.saveSolidWasteBillParameters(
							solidWasterRateMasterList,
							accountID,
							batchUpdate2.getServiceType(),
							batchUpdate2.getPreviousBillDate(),
							batchUpdate2.getPresentBillDate(),
							batchUpdate2.getPresentReading()
									- batchUpdate2.getPreviousReading(),
							billableDays, batchUpdate2.getPresentStatus(),
							batchUpdate2.getPreviousStatus(),
							batchUpdate2.getMeterConstant(),
							batchUpdate2.getPresentReading(),
							batchUpdate2.getPreviousReading(),
							(double) batchUpdate2.getAverageUnits(), avgCount,
							billType, postType);
				}

			}
		}
	}
	
	@RequestMapping(value = "/batchUpdate/uploadXMLBills", method = RequestMethod.POST)
	public @ResponseBody
	Object uploadXMLDocument(@RequestParam MultipartFile files,HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException, JAXBException {
		logger.info("--------------------- In Reading XML file Method ----------------------------------------------");
		JAXBContext jaxbContext = JAXBContext.newInstance(DocumentElement.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		DocumentElement documentElement = (DocumentElement) jaxbUnmarshaller.unmarshal(files.getInputStream());
		
		for (BillData billData : documentElement.getBillDatas())
		{
			ElectricityMetersEntity electricityMetersEntity = null;
			String bill =  "Bill";
			Integer todT1 = 0;
			Integer todT2 = 0;
			Integer todT3 = 0;
			Float dgunit = 0f;
			Object dgApplicable = "No";
			Float previousDgreading = 0f;
			Float presentDgReading = 0f;
			Float dgmeterconstant = 0f;
			Double avgUnits = 0d;
			Integer avgCount = 0;
			Float presentUOMReading = billData.getActiveEnergy();
			String presentMeterStaus = "Normal";
			String previousMeterStaus = null;
			Float previousUOMReading = 0f;
			Float meterConstant = 0f;
			Date previousBillDate = null;
			Float uomValue = 0f;
			float pfReading = 0;
			float connectedLoad = 0;
			String billType = "Normal";
			String meterchangeUnit="Unit Changed:";
		
			if(billData.getMtrSrNo()!=null)
			{
				electricityMetersEntity = electricityMetersService.getMeterByMeterSerialNo(billData.getMtrSrNo());
			}
			
			Map<Object, Object> billDetails;
			Integer serviceId = tariffCalculationService.getservicIdByAccountIdServiceType(electricityMetersEntity.getAccount().getAccountId(),electricityMetersEntity.getTypeOfServiceForMeters());
			billDetails = tariffCalculationService.getServiceStatusCheck(electricityMetersEntity.getAccount().getAccountId(), electricityMetersEntity.getTypeOfServiceForMeters(), serviceId);
			for (Entry<Object, Object>  map : billDetails.entrySet())
			{
				if ((map.getKey().equals("InitialReading")|| map.getKey().equals(null))) 
				{
					previousUOMReading = Float.parseFloat(map.getValue().toString());
				}
				
				if ((map.getKey().equals("MeterConstant")|| map.getKey().equals(null))) 
				{
					meterConstant = Float.parseFloat(map.getValue().toString());
				}
				
				if ((map.getKey().equals("billdate")|| map.getKey().equals(null))) 
				{
					previousBillDate = (Date) map.getValue();
				}
				
				if ((map.getKey().equals("DGInitialReading")|| map.getKey().equals(null))) 
				{
					previousDgreading = Float.parseFloat(map.getValue().toString());
				}
				
				if ((map.getKey().equals("DGMeterConstant")|| map.getKey().equals(null))) 
				{
					dgmeterconstant =Float.parseFloat(map.getValue().toString());
				}
				if ((map.getKey().equals("previousStatus")|| map.getKey().equals(null))) 
				{
					previousMeterStaus =(String) map.getValue();
				}
			}
			
			if(!billData.getPf().trim().equalsIgnoreCase("-"))
			{
				pfReading = Float.parseFloat(billData.getPf());
			}
			
			if(!billData.getActiveEnergyDG().trim().equalsIgnoreCase("-"))
			{
				presentDgReading = Float.parseFloat(billData.getActiveEnergyDG());
			}
			
			ServiceMastersEntity mastersEntity1 = serviceMasterService.getServiceMasterServicType(electricityMetersEntity.getAccount().getAccountId(), electricityMetersEntity.getTypeOfServiceForMeters());
			List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService.findAllByParentId(mastersEntity1.getServiceMasterId());
			for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1) {

				if(serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Sanctioned KW"))
				{
					logger.info(" billData.getMaxDemandKW() ----- "+ billData.getMaxDemandKW());
					connectedLoad = billData.getMaxDemandKW();
					logger.info("connectedLoad ------------------ "+connectedLoad);
				}
				if(serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Sanctioned KVA"))
				{
					logger.info("Sanctioned Load in ------------- "+serviceParametersEntity.getServiceParameterMaster().getSpmName());
					connectedLoad = billData.getMaxDemandKVA();
				}
				
			}
			
			if(electricityMetersEntity!=null)
			{
				List<ELRateMaster> elRateMasterList = new ArrayList<>();
				List<WTRateMaster> waterRateMasterList = new ArrayList<>();
				List<GasRateMaster> gasRateMasterList = new ArrayList<>();
				
				if (meterConstant == 1) {
					logger.info("::::::::::::: Meter constant is Normal :::::::::::::");
					uomValue = presentUOMReading - previousUOMReading;
					
				} else if (meterConstant < 1) {
					logger.info("::::::::::::: Meter constant is Slow :::::::::::::");
					uomValue = presentUOMReading - previousUOMReading;
					uomValue = uomValue - getMeterConstant(meterConstant);
				} else if (meterConstant > 1) {
					logger.info("::::::::::::: Meter constant is Fast :::::::::::::");
					uomValue = presentUOMReading - previousUOMReading;
					uomValue = uomValue - getMeterConstant(meterConstant);
				}
				
				LocalDate fromDate = new LocalDate(previousBillDate);
				LocalDate toDate = new LocalDate(billData.getReadingDT());
				Integer billableDays = Days.daysBetween(fromDate, toDate).getDays();
				logger.info("::::::::::::: Total number of billable days are  :::::::::::::::::::: "+ billableDays);
				
				int accountId=electricityMetersEntity.getAccount().getAccountId();
				String previousBillStatus = tariffCalculationService.getPreviousBillStatus(accountId,electricityMetersEntity.getTypeOfServiceForMeters(),previousBillDate);
				//String currentBillStatus = tariffCalculationService.getPreviousBillStatus(accountId,electricityMetersEntity.getTypeOfServiceForMeters(), billData.getReadingDT());
			/*	if (currentBillStatus.equalsIgnoreCase("Generated")|| previousBillStatus.equalsIgnoreCase("Approved") || previousBillStatus.equalsIgnoreCase("Cancelled") ) {*/
				if (previousBillStatus.equalsIgnoreCase("Generated")|| previousBillStatus.equalsIgnoreCase("Approved")) {
				if (electricityMetersEntity.getTypeOfServiceForMeters().equalsIgnoreCase("Electricity")) {
					int tariffId = serviceMasterService.getServiceMasterByAccountNumber(electricityMetersEntity.getAccount().getAccountId(), electricityMetersEntity.getTypeOfServiceForMeters());
					elRateMasterList = tariffCalculationService.getElectricityRateMaster(tariffId);
					List<Object> list = tariffCalculationService.todApplicable(electricityMetersEntity.getTypeOfServiceForMeters(), electricityMetersEntity.getAccount().getAccountId());
					Object todApplicable = list.get(0);
					if (todApplicable.equals("Yes")) {/*
						todT1 = Integer.parseInt(req.getParameter("presentTod1"));
						todT2 = Integer.parseInt(req.getParameter("presentTod2"));
						todT3 = Integer.parseInt(req.getParameter("presentTod3"));
						logger.info(" todT1::" + todT1 + " todT2::" + todT2+ " todT3::" + todT3);
					*/}

					else {
						logger.info(":::::::::Tod Is not Applicable::::::::::");
					}
					List<Object> dglist = tariffCalculationService.todApplicable(electricityMetersEntity.getTypeOfServiceForMeters(), electricityMetersEntity.getAccount().getAccountId());
					logger.info("::::::::::dglist.get(1);::::::::"+dglist.get(1));
					dgApplicable = dglist.get(1);
					if (dgApplicable != null) {
						
					}else{
						dgApplicable="NO";
					}
					
					if (dgApplicable.equals("Yes")) {

						if(!billData.getActiveEnergyDG().trim().equalsIgnoreCase("-"))
						{
							presentDgReading =Float.parseFloat(billData.getActiveEnergyDG());
							dgunit =  Float.parseFloat(billData.getActiveEnergyDG())- previousDgreading;
						}
						float dgUomValue = 0;
						if (dgmeterconstant == 1) {
							logger.info("::::::::::::: Meter constant is Normal :::::::::::::");
							dgUomValue = presentDgReading - previousDgreading;
						} else if (dgmeterconstant < 1) {
							logger.info("::::::::::::: Meter constant is Slow :::::::::::::");
							dgUomValue = presentDgReading - previousDgreading;
							dgUomValue = dgUomValue - getMeterConstant(dgmeterconstant);
						} else if (dgmeterconstant > 1) {
							logger.info("::::::::::::: Meter constant is Fast :::::::::::::");
							dgUomValue = presentDgReading - previousDgreading;
							dgUomValue = dgUomValue - getMeterConstant(dgmeterconstant);
						}

						dgunit = dgUomValue;
					}
					
					logger.info("uomValue ------------------ "+uomValue);
					
					logger.info("elRateMasterList ---------------------------------------------------- "+elRateMasterList);
					logger.info("electricityMetersEntity.getAccount() -------------------------------- "+electricityMetersEntity.getAccount().getAccountId());
					logger.info("electricityMetersEntity.getTypeOfServiceForMeters() ----------------- "+electricityMetersEntity.getTypeOfServiceForMeters());
					logger.info("previousBillDate ---------------------------------------------------- "+previousBillDate);
					logger.info("billData.getReadingDT() --------------------------------------------- "+billData.getReadingDT());
					logger.info("uomValue ------------------------------------------------------------ "+uomValue);
					logger.info("billableDays -------------------------------------------------------- "+billableDays);
					logger.info("presentMeterStaus --------------------------------------------------- "+presentMeterStaus);
					logger.info("previousMeterStaus -------------------------------------------------- "+previousMeterStaus);
					logger.info("meterConstant ------------------------------------------------------- "+meterConstant);
					logger.info("billData.getActiveEnergy() ------------------------------------------ "+billData.getActiveEnergy()); 
					logger.info("previousUOMReading -------------------------------------------------- "+previousUOMReading);
					logger.info("dgunit -------------------------------------------------------------- "+dgunit);
					logger.info("todApplicable ------------------------------------------------------- "+todApplicable);
					logger.info("dgApplicable -------------------------------------------------------- "+dgApplicable);
					logger.info("previousDgreading --------------------------------------------------- "+previousDgreading);
					logger.info("presentDgReading ---------------------------------------------------- "+presentDgReading);
					logger.info("dgmeterconstant ----------------------------------------------------- "+dgmeterconstant);
					logger.info("pfReading ----------------------------------------------------------- "+pfReading);
					logger.info("connectedLoad ------------------------------------------------------- "+connectedLoad);
					
					
					billGenerationController.saveElectricityBillParameters(elRateMasterList, electricityMetersEntity.getAccount().getAccountId(),
							electricityMetersEntity.getTypeOfServiceForMeters(), previousBillDate, billData.getReadingDT(), uomValue,
							billableDays, presentMeterStaus, previousMeterStaus,
							meterConstant, presentUOMReading, previousUOMReading,
							todT1, todT2, todT3, dgunit, todApplicable, dgApplicable,
							previousDgreading, presentDgReading, dgmeterconstant,
							avgUnits, avgCount, billType, pfReading, connectedLoad,
							bill,meterchangeUnit);
				}
				
				if (electricityMetersEntity.getTypeOfServiceForMeters().equalsIgnoreCase("Water")) {
					logger.info("::::::::::::: Water service type ::::::::::::: ");
					int waterTariffId = serviceMasterService.getWaterTariffId(electricityMetersEntity.getAccount().getAccountId(), electricityMetersEntity.getTypeOfServiceForMeters());
					logger.info(":::::::::::::" + electricityMetersEntity.getTypeOfServiceForMeters()+ " Tairff id is ::::::::::::: " + waterTariffId);
					waterRateMasterList = tariffCalculationService.getWaterRateMaster(waterTariffId);
					billGenerationController.saveWaterBillParameters(waterRateMasterList, electricityMetersEntity.getAccount().getAccountId(),
							electricityMetersEntity.getTypeOfServiceForMeters(), previousBillDate,  billData.getReadingDT(), uomValue,
							billableDays, presentMeterStaus, previousMeterStaus,
							meterConstant, presentUOMReading, previousUOMReading,
							avgUnits, avgCount, billType, bill,meterchangeUnit);
				}
				
				if (electricityMetersEntity.getTypeOfServiceForMeters().equalsIgnoreCase("Gas")) {
					logger.info("::::::::::::: Gas service type ::::::::::::: "+ electricityMetersEntity.getAccount().getAccountId() + " ---------------- " + electricityMetersEntity.getTypeOfServiceForMeters());
					int gasTariffId = serviceMasterService.getGasTariffId(electricityMetersEntity.getAccount().getAccountId(),electricityMetersEntity.getTypeOfServiceForMeters());
					logger.info(":::::::::::::" + electricityMetersEntity.getTypeOfServiceForMeters()+ " Tairff id is ::::::::::::: " + gasTariffId);
					gasRateMasterList = tariffCalculationService.getGasRateMaster(gasTariffId);
					billGenerationController.saveGasBillParameters(gasRateMasterList, electricityMetersEntity.getAccount().getAccountId(), electricityMetersEntity.getTypeOfServiceForMeters(),
							previousBillDate, billData.getReadingDT(), uomValue, billableDays,
							presentMeterStaus, previousMeterStaus, meterConstant,
							presentUOMReading, previousUOMReading, avgUnits, avgCount,
							billType, bill,meterchangeUnit);
				}
				
			}else{
				logger.info("Bill for "+accountService.find(accountId).getAccountNo() + " has been not posted Please Post To Generate Next Bill");
			}
				/*}else{
				logger.info("Bill for "+accountService.find(accountId).getAccountNo()+ " has been All ready generated Please Post To Generate Next Bill");	
				}*/
		}
		}
		return null;
	}

	@RequestMapping(value = "/batchUpdate/getBillDate", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	Map<Object, Object> getBillDate(HttpServletRequest req, HttpServletResponse response)
			throws ParseException {
		PrintWriter out;
		
		String blockName = req.getParameter("blockName");
		int blockId = Integer.parseInt(req.getParameter("blockid"));
		String serviceTypeList = req.getParameter("serviceTypeList");
		
		//List<Object> list = tariffCalculationService.getBatchBillDateOnBlockId(blockId);
		return tariffCalculationService.getBatchBillDateOnBlockId(blockId,blockName,serviceTypeList);
	}
	
	
}
