package com.bcits.bfm.controller;

import java.io.ByteArrayOutputStream;
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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.BillParameterMasterEntity;
import com.bcits.bfm.model.CommonServicesRateMaster;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.ElectricityBillLineItemEntity;
import com.bcits.bfm.model.ElectricityBillParametersEntity;
import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.model.ElectricityMeterLocationEntity;
import com.bcits.bfm.model.ElectricityMeterParametersEntity;
import com.bcits.bfm.model.ElectricityMetersEntity;
import com.bcits.bfm.model.ElectricitySubLedgerEntity;
import com.bcits.bfm.model.MeterHistoryEntity;
import com.bcits.bfm.model.PaymentAdjustmentEntity;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.model.ServiceParametersEntity;
import com.bcits.bfm.model.ServicePointEntity;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.accountsManagement.ElectricityLedgerService;
import com.bcits.bfm.service.accountsManagement.ElectricitySubLedgerService;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentService;
import com.bcits.bfm.service.billingCollectionManagement.ChequeBounceService;
import com.bcits.bfm.service.commonServicesTariffManagement.CommonServicesRateMasterService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillLineItemService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillParameterService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.electricityMetersManagement.ElectricityMeterLocationsService;
import com.bcits.bfm.service.electricityMetersManagement.ElectricityMeterParametersService;
import com.bcits.bfm.service.electricityMetersManagement.ElectricityMetersService;
import com.bcits.bfm.service.facilityManagement.BillingParameterMasterService;
import com.bcits.bfm.service.serviceMasterManagement.MeterHistoryService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceParameterService;
import com.bcits.bfm.service.servicePoints.ServicePointService;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.bcits.bfm.service.transactionMaster.TransactionMasterService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
public class ElectricityBillsManagementController {

	static Logger logger = LoggerFactory.getLogger(ElectricityBillsManagementController.class);

	DateTimeCalender dateTimeCalender = new DateTimeCalender();

	@PersistenceContext(unitName="bfm")
	protected EntityManager entityManager;

	@Autowired
	private BreadCrumbTreeService breadCrumbService;

	@Autowired
	private ElectricityBillsService electricityBillsService;

	@Autowired
	private ServicePointService servicePointService;

	@Autowired
	private ElectricityBillLineItemService electricityBillLineItemService;

	@Autowired
	private ElectricityBillParameterService electricityBillParameterService;

	@Autowired
	private ElectricityMetersService electricityMetersService;

	@Autowired
	private ElectricityMeterParametersService electricityMeterParametersService;

	@Autowired
	private ElectricityMeterLocationsService electricityMeterLocationsService;

	@Autowired
	private CommonController commonController;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ElectricityLedgerService electricityLedgerService;

	@Autowired
	private ElectricitySubLedgerService electricitySubLedgerService;

	@Autowired
	TransactionMasterService transactionMasterService;

	@Autowired
	BillingParameterMasterService billingParameterMasterService;
	
	@Autowired
	private AdjustmentService adjustmentService; 
	
	@Autowired
	ElectricityBillParameterService billParameterService;
	
	@Autowired
	private MeterHistoryService meterHistoryService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CommonServicesRateMasterService commonServicesRateMasterService;
	
	@Autowired
	private BillGenerationController billGenerationController;
	
	@Autowired
	ServiceMasterService serviceMasterService;
	
	@Autowired
	TariffCalculationService tariffCalculationService;
	
	@Autowired
	ServiceParameterService serviceParameterService;
	
	@Autowired
	private ChequeBounceService chequeBounceService;
	
	@Autowired
	BillController billController;

	@RequestMapping("/tariffMasterDashBoard")
	public String tariffMasterDashBoard(HttpServletRequest request, Model model) {
		logger.info("In Tariff Master DashBoard Method");
		model.addAttribute("ViewName", "Dashboard");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Dashboard", 1, request);

		return "electricityBills/dashBoard";
	}

	@RequestMapping(value = "/electricity", method = RequestMethod.GET)
	public String electricityMenu(ModelMap model, HttpServletRequest request) {
		logger.info("In ElectricityMenu  Method");
		model.addAttribute("ViewName", "Electricity");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Electricity", 1, request);
		model.addAttribute("username",
				SessionData.getUserDetails().get("userID"));

		return "electricityBills/electricity";
	}

	@RequestMapping("/electrictyBills")
	public String electrictyBills(HttpServletRequest request, Model model) {
		logger.info("::::::::::::::::::::::: In Electricity Bills Method :::::::::::::::::::");
		model.addAttribute("ViewName", "Bills Generation");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Bills Generation", 1, request);
		breadCrumbService.addNode("Manage Generated Bills", 2, request);
		model.addAttribute("transactionName", populateCategories("Others"));
		model.addAttribute("billParameterName", populateCategories1("Others"));
		return "electricityBills/electricityBills";
	}

	private Object populateCategories1(String typeOfService) {

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult = null;

		List<String> attributesList = new ArrayList<String>();
		attributesList.add("bvmId");
		attributesList.add("bvmName");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serviceType", typeOfService);

		Map<String, Object> orderByList = new HashMap<String, Object>();
		orderByList.put("bvmName", "ASC");

		List<?> categoriesList = commonService.selectQueryOrderBy(
				"BillParameterMasterEntity", attributesList, params,
				orderByList);
	
		for (Iterator<?> i = categoriesList.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();
			mapResult = new HashMap<String, Object>();
			mapResult.put("bvmId", values[0]);
			mapResult.put("bvmName", values[1]);
			listResult.add(mapResult);
		}
		return listResult;

	}

	@RequestMapping(value = "/commonServiceTransactionMaster/getToTransactionMasterComboBoxUrl/{billPostType}/{billServiceType}/{transactionId}", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> getToTariffMasterComboBoxUrl(@PathVariable String billPostType,@PathVariable String  billServiceType,@PathVariable int transactionId) 
	{
		logger.info("----------- Bill Type is ------------- "+billPostType+"------ "+billServiceType+"------- "+transactionId);
		if(billPostType.trim().equalsIgnoreCase("Back Bill")){
			
			List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
			Map<String, Object> mapResult = null;
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("transactionCode");
			attributesList.add("transactionName");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("typeOfService", billServiceType);
			Map<String, Object> orderByList = new HashMap<String, Object>();
			orderByList.put("transactionName", "ASC");
			List<?> categoriesList = commonService.selectQueryOrderBy("TransactionMasterEntity", attributesList, params, orderByList);
			for (Iterator<?> i = categoriesList.iterator(); i.hasNext();) {
				final Object[] values = (Object[]) i.next();
				mapResult = new HashMap<String, Object>();
				mapResult.put("transactionCode", values[0]);
				mapResult.put("transactionName", values[1]);
				String transactionCode = (String) values[0];
			    ElectricityBillLineItemEntity billLineItemEntities=electricityBillLineItemService.findByTransactionCode(transactionId,transactionCode);
			    if(billLineItemEntities == null)
			    {
			       listResult.add(mapResult);
			    }
			}
			return listResult;
			
			
		}else if(billPostType.trim().equalsIgnoreCase("Deposit")){
			List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
			Map<String, Object> mapResult = null;
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("transactionCode");
			attributesList.add("transactionName");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("typeOfService", billServiceType);
			params.put("groupType", billPostType);
			Map<String, Object> orderByList = new HashMap<String, Object>();
			orderByList.put("transactionName", "ASC");
			List<?> categoriesList = commonService.selectQueryOrderBy("TransactionMasterEntity", attributesList, params, orderByList);
			for (Iterator<?> i = categoriesList.iterator(); i.hasNext();) {
				final Object[] values = (Object[]) i.next();
				mapResult = new HashMap<String, Object>();
				mapResult.put("transactionCode", values[0]);
				mapResult.put("transactionName", values[1]);
				String transactionCode = (String) values[0];
			    ElectricityBillLineItemEntity billLineItemEntities=electricityBillLineItemService.findByTransactionCode(transactionId,transactionCode);
			    if(billLineItemEntities == null)
			    {
			       listResult.add(mapResult);
			    }
			}
			return listResult;
		}
		else 
		{

			ElectricityBillEntity billEntity = electricityBillsService.find(transactionId);
			ServiceMastersEntity mastersEntity1 = serviceMasterService.getServiceMasterServicType(billEntity.getAccountId(), billEntity.getTypeOfService());
			List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService.findAllByParentId(mastersEntity1.getServiceMasterId());
			
			List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
			Map<String, Object> mapResult = null;
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("transactionCode");
			attributesList.add("transactionName");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("typeOfService", billServiceType);
			Map<String, Object> orderByList = new HashMap<String, Object>();
			orderByList.put("transactionName", "ASC");
			List<?> categoriesList = electricityBillLineItemService.getTaransactionCodeForOthers(billServiceType);
			for (Iterator<?> i = categoriesList.iterator(); i.hasNext();) {
				final Object[] values = (Object[]) i.next();
				mapResult = new HashMap<String, Object>();
				
				for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1)
				{
					if ((serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase((String)values[1])) && serviceParametersEntity.getServiceParameterValue().equalsIgnoreCase("Yes"))
					{
						mapResult.put("transactionCode", values[0]);
						mapResult.put("transactionName", values[1]);
						String transactionCode = (String) values[0];
					    ElectricityBillLineItemEntity billLineItemEntities = electricityBillLineItemService.findByTransactionCode(transactionId,transactionCode);
					    if(billLineItemEntities == null)
					    {
					       listResult.add(mapResult);
					    }
					}
				}
			}
			return listResult;
		
		}
	}

/*	@RequestMapping(value = "/commonServiceTransactionMaster/getToBillParaMeterMasteMasterComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> getToBillParaMeterMasterMasterComboBoxUrl() {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> listResult = (List<Map<String, Object>>) populateCategories1("Others");
		return listResult;
	}*/

	private List<?> populateCategories(String typeOfService) {

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult = null;

		List<String> attributesList = new ArrayList<String>();
		attributesList.add("transactionCode");
		attributesList.add("transactionName");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("typeOfService", typeOfService);

		Map<String, Object> orderByList = new HashMap<String, Object>();
		orderByList.put("transactionName", "ASC");

		List<?> categoriesList = commonService.selectQueryOrderBy(
				"TransactionMasterEntity", attributesList, params, orderByList);
		for (Iterator<?> i = categoriesList.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();
			mapResult = new HashMap<String, Object>();
			mapResult.put("transactionCode", values[0]);
			mapResult.put("transactionName", values[1]);
			listResult.add(mapResult);
		}
		return listResult;
	}

	@RequestMapping("/electrictyMeters")
	public String electrictyMeters(HttpServletRequest request, Model model) {
		logger.info("In ElectrictyMeters Method");
		model.addAttribute("ViewName", "Masters");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Masters", 1, request);
		breadCrumbService.addNode("Manage Meters", 2, request);
		model.addAttribute("servicePoints", commonController
				.populateCategories("servicePointId", "typeOfService",
						"ServicePointEntity"));

		return "electricityBills/electricityMeters";
	}
	@RequestMapping(value = "/electricityBills/billRead", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<ElectricityBillEntity> readElBillData() {
		logger.info("In Electricity Bill Read Method");
		List<ElectricityBillEntity> billEntities = electricityBillsService.findALL();
		return billEntities;
	}

	@RequestMapping(value = "/electricityBills/billCreate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object saveBillDetails(@ModelAttribute("billEntity") ElectricityBillEntity billEntity,	BindingResult bindingResult, ModelMap model,
			HttpServletRequest req, SessionStatus sessionStatus,
			final Locale locale) throws ParseException {

		billEntity.setAccountId(billEntity.getAccountId());
		billEntity.setTypeOfService(billEntity.getTypeOfService());
		billEntity.setStatus("Generated");
		billEntity.setBillNo("BILL" + billParameterService.getSequencyNumber());
		billEntity.setPostType("Back Bill");
		billEntity.setBillType("Normal");
		billEntity.setBillDate(dateTimeCalender.getDateToStore(req.getParameter("toDateBackBill")));
		billEntity.setFromDate(dateTimeCalender.getDateToStore(req.getParameter("fromDateBackBill")));
		billEntity.setElBillDate(new Timestamp(dateTimeCalender.getDateToStore(req.getParameter("billDate")).getTime()));
		
		Date billDueDate = addDays(billEntity.getBillDate(), 15);
		
		billEntity.setBillDueDate(new java.sql.Date(billDueDate.getTime()));
		billEntity.setBillMonth(dateTimeCalender.getDateToStore(req.getParameter("billDate")));

		electricityBillsService.save(billEntity);
		
		ElectricityBillParametersEntity billParametersEntity = new ElectricityBillParametersEntity();
		
		billParametersEntity.setBillParameterMasterEntity(electricityBillParameterService.getMasterObjBasedOnName("Units",billEntity.getTypeOfService()));
		billParametersEntity.setElBillParameterValue(""+billEntity.getUnitsBackBill());
		billParametersEntity.setElectricityBillEntity(billEntity);
		billParametersEntity.setStatus("Active");
		
		electricityBillParameterService.save(billParametersEntity);
		
		LocalDate fromDate = new LocalDate(dateTimeCalender.getDateToStore(req.getParameter("fromDateBackBill")));
		LocalDate toDate = new LocalDate(dateTimeCalender.getDateToStore(req.getParameter("toDateBackBill")));
		
		int noDays = Days.daysBetween(fromDate,toDate).getDays();
		
		ElectricityBillParametersEntity parametersEntity = new ElectricityBillParametersEntity();
		
		parametersEntity.setBillParameterMasterEntity(electricityBillParameterService.getMasterObjBasedOnName("Number of days",billEntity.getTypeOfService()));
		parametersEntity.setElBillParameterValue(""+noDays);
		parametersEntity.setElectricityBillEntity(billEntity);
		parametersEntity.setStatus("Active");
		
		electricityBillParameterService.save(parametersEntity);
		
		return billEntity;
	}

	@RequestMapping(value = "/electricityBills/approveBill/{elBillId}/{operation}/{totalBillAmount}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void tariffStatus(@PathVariable int elBillId,
			@PathVariable String operation,
			@PathVariable float totalBillAmount, HttpServletResponse response)
			throws IOException {
		logger.info(":::::::::::: Approve the Bill With ID ::::::::::::::::: "+ elBillId);
		logger.info("operation :::::::::: " + operation);
		logger.info("totalBillAmount :::: " + totalBillAmount);
		PrintWriter out = response.getWriter();
	
		if (operation.equalsIgnoreCase("Generated")) {
			if (totalBillAmount <= 0) {
				out.print("Bill Amount is 0.00 you can't approve");
			} else {
				electricityBillsService.setApproveBill(elBillId, operation,
						response);
			}
		} else {
			electricityBillsService.setApproveBill(elBillId, operation,
					response);
		}

	}

	@RequestMapping(value = "/electricityBills/cancelApproveBill/{elBillId}/{operation}/{accountId}/{serviceType}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void cancelApproveBill(@PathVariable int elBillId,
			@PathVariable String operation,@PathVariable int accountId,@PathVariable String serviceType, HttpServletResponse response)
			throws IOException {
		if (operation.equalsIgnoreCase("Generated")|| operation.equalsIgnoreCase("Approved")) {
			electricityBillsService.cancelApproveBill(elBillId, operation,response,accountId,serviceType);
		} else
		{
			electricityBillsService.cancelApproveBill(elBillId, operation,response,accountId,serviceType);
		}
	}

	// ****************************** Bill Line Item Read and Create methods
	// ********************************//

	@RequestMapping(value = "/electricityBills/billLineItemRead/{elBillId}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<ElectricityBillLineItemEntity> readBillLineItem(@PathVariable int elBillId) {
		logger.info("In Electricity Bill Line Item read Method");
		List<ElectricityBillLineItemEntity> lineItemEntities = electricityBillLineItemService.findAllById(elBillId);
		return lineItemEntities;
	}

	@SuppressWarnings({ "static-access", "unused" })
	@RequestMapping(value = "/electricityBills/billLineItemCreate/{elBillId}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object saveBillLineItem(@ModelAttribute("lineItemEntity") ElectricityBillLineItemEntity lineItemEntity,
			BindingResult bindingResult, @PathVariable int elBillId,
			ModelMap model, HttpServletRequest req,
			SessionStatus sessionStatus, final Locale locale)
			throws ParseException 
	  {
		
		float totalAmountForTax=0;
		float totalAmount = 0;
		ElectricityBillEntity billEntity = new ElectricityBillEntity();
		billEntity = electricityBillsService.getBillEntityById(elBillId);
		
		if(billEntity.getPostType().equalsIgnoreCase("Deposit"))
		{
			lineItemEntity.setStatus("Inactive");
			lineItemEntity.setTransactionCode(lineItemEntity.getTransactionName());
			billEntity.setBillAmount(billEntity.getBillAmount()+ lineItemEntity.getBalanceAmount());
			billEntity.setNetAmount(billEntity.getBillAmount()+billEntity.getArrearsAmount());
			billEntity.setAvgAmount(billEntity.getAvgAmount());
			electricityBillsService.update(billEntity);
			lineItemEntity.setElectricityBillEntity(billEntity);
			electricityBillLineItemService.save(lineItemEntity);
		}
	   if(billEntity.getPostType().equalsIgnoreCase("Back Bill"))
		{
		    lineItemEntity.setStatus("Inactive");
			lineItemEntity.setTransactionCode(lineItemEntity.getTransactionName());
			billEntity.setBillAmount(billEntity.getBillAmount()+ lineItemEntity.getBalanceAmount());
			billEntity.setNetAmount(billEntity.getBillAmount()+billEntity.getArrearsAmount());
			billEntity.setAvgAmount(billEntity.getAvgAmount());
			electricityBillsService.update(billEntity);
			lineItemEntity.setElectricityBillEntity(billEntity);
			electricityBillLineItemService.save(lineItemEntity);
		}
		if(billEntity.getTypeOfService().equalsIgnoreCase("Others"))
		{
			new ArrayList<>();
			electricityBillLineItemService.findAllLineItemsByIdUpdate(elBillId);
			/*for (ElectricityBillLineItemEntity electricityBillLineItemEntity : billLineItemEntities) 
			{
				totalAmountForTax += (float) (electricityBillLineItemEntity.getBalanceAmount());
				
			}*/
			totalAmountForTax = billEntity.getBillAmount().floatValue();
			logger.info("totalAmountForTax --------- "+totalAmountForTax);
			lineItemEntity.setStatus("Inactive");
			lineItemEntity.setTransactionCode(lineItemEntity.getTransactionName());
			billEntity.setBillAmount(billEntity.getBillAmount()+ lineItemEntity.getBalanceAmount());
			logger.info("billEntity.getBillAmount() --------- "+billEntity.getBillAmount());
			billEntity.setNetAmount(billEntity.getBillAmount()+billEntity.getArrearsAmount());
			billEntity.setAvgAmount(billEntity.getAvgAmount());
			electricityBillsService.update(billEntity);
			lineItemEntity.setElectricityBillEntity(billEntity);
			electricityBillLineItemService.update(lineItemEntity);
			
			float taxAmount=0;
			CommonServicesRateMaster commonServicesRateMaster = commonServicesRateMasterService.getTaxCommonServiceMaster("Tax");
			
			/*if(commonServicesRateMaster!=null)
			{
				List<CommonServicesRateSlab> commonServicesRateSlabs = commonServiceRateSlabService.findRateSlabByID(commonServicesRateMaster.getCsRmId());
				for (CommonServicesRateSlab commonServicesRateSlab : commonServicesRateSlabs)
				{
					tax = commonServicesRateSlab.getCsRate();
				}
			}*/
			
			totalAmountForTax = (float) (totalAmountForTax + lineItemEntity.getBalanceAmount());
			logger.info("totalAmountForTax -------2------- "+totalAmountForTax);
			taxAmount = taxAmount+ billGenerationController.otherServicesTaxAmount(commonServicesRateMaster, billEntity.getFromDate(),billEntity.getBillDate(), totalAmountForTax);
			List<ElectricityBillLineItemEntity> taxComponentDelete = electricityBillLineItemService.findLineItemBasedOnTransactionCode(elBillId, "OT_TAX");
			ElectricityBillLineItemEntity taxComponent=null;
			if(!taxComponentDelete.isEmpty())
			{
				for (ElectricityBillLineItemEntity electricityBillLineItemEntity : taxComponentDelete)
				{
						taxComponent = electricityBillLineItemEntity;
				}
			}
			else
			{
				taxComponent = new ElectricityBillLineItemEntity();
			}
			taxComponent.setStatus("Inactive");
			taxComponent.setTransactionCode("OT_TAX");
			taxComponent.setBalanceAmount(taxAmount);
			billEntity.setBillAmount(billEntity.getBillAmount()+ taxComponent.getBalanceAmount());
			logger.info("billEntity.getBillAmount() ---------2 "+billEntity.getBillAmount());
			billEntity.setNetAmount(billEntity.getBillAmount() + billEntity.getArrearsAmount());
			billEntity.setAvgAmount(billEntity.getAvgAmount());
			taxComponent.setElectricityBillEntity(billEntity);
			electricityBillsService.update(billEntity);
			electricityBillLineItemService.update(taxComponent);
			
			totalAmount = (float) (totalAmount + totalAmountForTax +taxComponent.getBalanceAmount());
			float roundoffValue = (billGenerationController.roundOff(totalAmount, 0)- totalAmount);
			
			ElectricityBillLineItemEntity roundoff = null;
			List<ElectricityBillLineItemEntity> roundOffComponentDelete = electricityBillLineItemService.findLineItemBasedOnTransactionCode(elBillId, "OT_ROF");
			if(!roundOffComponentDelete.isEmpty())
			{
				for (ElectricityBillLineItemEntity electricityBillLineItemEntity : roundOffComponentDelete)
				{
					roundoff = electricityBillLineItemEntity;
				}
			}
			else
			{
				roundoff = new ElectricityBillLineItemEntity();
			}
			
			roundoff.setStatus("Inactive");
			roundoff.setTransactionCode("OT_ROF");
			roundoff.setBalanceAmount(roundoffValue);
			billEntity.setBillAmount((double)totalAmount+roundoffValue);
			billEntity.setNetAmount(billEntity.getBillAmount()+billEntity.getArrearsAmount());
			billEntity.setAvgAmount(billEntity.getAvgAmount());
			roundoff.setElectricityBillEntity(billEntity);
			electricityBillsService.update(billEntity);
			if(roundoffValue>0)
			{
				electricityBillLineItemService.update(roundoff);
			}
			
			List<ElectricityBillLineItemEntity> billLineItemEntities1 = new ArrayList<>();
			billLineItemEntities1.add(lineItemEntity);
			billLineItemEntities1.add(taxComponent);
			if(roundoffValue>0)
			{
				billLineItemEntities1.add(roundoff);	
			}
			
			List<BillParameterMasterEntity> billParameterMasterEntitiesList = billingParameterMasterService.getBillParameterMasterByServiceType(billEntity.getTypeOfService());
			LocalDate fromDate = new LocalDate(billEntity.getFromDate());
			LocalDate toDate = new LocalDate(billEntity.getBillDate());
			Integer billableDays = Days.daysBetween(fromDate,toDate).getDays();
			for (BillParameterMasterEntity billParameterMasterEntity : billParameterMasterEntitiesList) 
			{
				if (billParameterMasterEntity.getBvmName().equalsIgnoreCase("Number of days")) {
					ElectricityBillParametersEntity noOfDays=null;
					List<ElectricityBillParametersEntity> noOfDaysList =  electricityBillParameterService.findByBillId(billEntity.getElBillId());
					if(noOfDaysList.isEmpty())
					{
					  noOfDays = new ElectricityBillParametersEntity("Integer", billableDays.toString(), "Active");
					}
					else 
					{
						for (ElectricityBillParametersEntity electricityBillParametersEntity : noOfDaysList)
						{
							noOfDays = electricityBillParametersEntity;
						}
					}
					
					noOfDays.setBillParameterMasterEntity(billParameterMasterEntity);
					noOfDays.setElectricityBillEntity(billEntity);
					billParameterService.update(noOfDays);
				}
			}
		
			billController.getBillDoc(billEntity.getElBillId(), locale,1);
		}
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<ElectricityBillLineItemEntity> billLineItemEntities1 = new ArrayList<>();
		billLineItemEntities1.add(lineItemEntity);

		for (final ElectricityBillLineItemEntity lineItemEntity2 : billLineItemEntities1) {
			result.add(new HashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{
					put("elBillLineId", lineItemEntity2.getElBillLineId());
					put("transactionCode", lineItemEntity2.getTransactionCode());
					put("creditAmount", lineItemEntity2.getCreditAmount());
					put("debitAmount", lineItemEntity2.getDebitAmount());
					put("balanceAmount", lineItemEntity2.getBalanceAmount());
					put("transactionName", lineItemEntity2.getTransactionName());
				}
			});
		}
		return result;
	}

	// ****************************** Bill Parameter Read and Create methods
	// ********************************//

	@RequestMapping(value = "/electricityBills/billParameterRead/{elBillId}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<ElectricityBillParametersEntity> readBillParameter(
			@PathVariable int elBillId) {
		logger.info("In Electricity Bill Parameter read Method");
		List<ElectricityBillParametersEntity> parametersEntities = electricityBillParameterService.findAllById(elBillId);
		return parametersEntities;
	}

	@RequestMapping(value = "/electricityBills/billParameterCreate/{elBillId}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object saveBillParameter(
			@ModelAttribute("billParameters") ElectricityBillParametersEntity billParametersEntity,
			BindingResult bindingResult, @PathVariable int elBillId,
			ModelMap model, HttpServletRequest req,
			SessionStatus sessionStatus, final Locale locale)
			throws ParseException {

		billParametersEntity.setStatus("Active");

		ElectricityBillEntity billEntity = new ElectricityBillEntity();
		billEntity = electricityBillsService.getBillEntityById(elBillId);
		billParametersEntity.setElectricityBillEntity(billEntity);

		System.out.println("#################"
				+ billParametersEntity.getBvmId());
		billParametersEntity
				.setBillParameterMasterEntity(billingParameterMasterService
						.find(billParametersEntity.getBvmId()));

		electricityBillParameterService.save(billParametersEntity);

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<ElectricityBillParametersEntity> billParametersEntities = new ArrayList<>();
		billParametersEntities.add(billParametersEntity);

		for (final ElectricityBillParametersEntity billParametersEntity2 : billParametersEntities) {
			result.add(new HashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{
					put("elBillParameterId",
							billParametersEntity2.getElBillParameterId());
					put("elBillParameterDataType",
							billParametersEntity2.getElBillParameterDataType());
					put("elBillParameterValue",
							billParametersEntity2.getElBillParameterValue());
					put("notes", billParametersEntity2.getNotes());
					// put("bvmName",billParametersEntity2.getBillParameterMasterEntity().getBvmName());
				}
			});
		}
		return result;
		// return billParametersEntity;
	}

	// ****************************** Bills Filters Data methods
	// ********************************//

	@RequestMapping(value = "/bills/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getBillsDataForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("ElectricityBillEntity", attributeList, null));

		return set;
	}

	// ****************************** Bill Line Item Filters Data methods
	// ********************************//

	@RequestMapping(value = "/billLineItem/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getBillLineItemDataForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery(
				"ElectricityBillLineItemEntity", attributeList, null));

		return set;
	}

	// ****************************** Bill Parameter Filters Data methods
	// ********************************//

	@RequestMapping(value = "/billParameter/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getBillParameterDataForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery(
				"ElectricityBillParametersEntity", attributeList, null));

		return set;
	}

	// ****************************** Electricity Meters
	// Read,Create,Update,Delete methods ********************************//

	@RequestMapping(value = "/electrictyMeters/elMeterRead", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<?> readElectricityMetersData() {
		logger.info("In readElectricityMetersData Method");
		List<?> metersEntities = electricityMetersService.findALL();
		return metersEntities;
	}

	@RequestMapping(value = "/electrictyMeters/metersStatus/{elMeterId}/{operation}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void metersStatus(@PathVariable int elMeterId,
			@PathVariable String operation, HttpServletResponse response) {

		logger.info("In metersStatus Status Change Method");
		if (operation.equalsIgnoreCase("activate"))
			electricityMetersService.setMetersStatus(elMeterId, operation,
					response);
		else
			electricityMetersService.setMetersStatus(elMeterId, operation,
					response);
	}

	@RequestMapping(value = "/electrictyMeters/propertyNameFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> propertyNameFilterUrl() {	
		return electricityMetersService.proPertyName();
	}
	
	@RequestMapping(value = "/electrictyMeters/electricityMetertCreate", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object saveElectricityMeter(@ModelAttribute("metersEntity") ElectricityMetersEntity metersEntity,BindingResult bindingResult, ModelMap model,
			HttpServletRequest req, SessionStatus sessionStatus,final Locale locale) throws ParseException {

		logger.info("In ElectricityMeter Create Method");
		metersEntity.setMeterStatus("In Stock");

		electricityMetersService.save(metersEntity);
		return metersEntity;
	}

	@RequestMapping(value = "/electrictyMeters/electricityMeterUpdate", method = RequestMethod.GET)
	public @ResponseBody
	Object editElectricityMeter(
			@ModelAttribute("metersEntity") ElectricityMetersEntity metersEntity,
			BindingResult bindingResult, ModelMap model,
			SessionStatus sessionStatus, final Locale locale,
			HttpServletRequest req) throws ParseException {

		logger.info("In ElectricityMeter Update Method");
		metersEntity.setAccount(accountService.find(metersEntity.getAccountId()));
		//metersEntity.setPointEntity(servicePointService.find(metersEntity.getServicePointId()));
		electricityMetersService.update(metersEntity);

		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("elMeterId", metersEntity.getElMeterId());
		parameterMap.put("typeOfService", metersEntity.getTypeOfServiceForMeters());
		parameterMap.put("meterSerialNo", metersEntity.getMeterSerialNo());
		parameterMap.put("meterType", metersEntity.getMeterType());
		parameterMap.put("meterOwnerShip", metersEntity.getMeterOwnerShip());
		parameterMap.put("status", metersEntity.getMeterStatus());
		// parameterMap.put("servicePointId", metersEntity.getCreatedBy());
		parameterMap.put("accountId", metersEntity.getAccountId());
		// parameterMap.put("accountNo", metersEntity.getLastUpdatedDT());
		return parameterMap;
	}

	@RequestMapping(value = "/electrictyMeters/electricityMeterDestroy", method = RequestMethod.GET)
	public @ResponseBody
	Object deleteElectricityMeter(
			@ModelAttribute("metersEntity") ElectricityMetersEntity metersEntity,
			BindingResult bindingResult, ModelMap model, SessionStatus ss) {

		logger.info("In deleteElectricityMeter Destroy Method");
		JsonResponse errorResponse = new JsonResponse();
		final Locale locale = null;
		if (metersEntity.getMeterStatus().equalsIgnoreCase("In Service")) {
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				/**
					 * 
					 */
				private static final long serialVersionUID = 1L;

				{
					put("AciveMeterDestroyError", messageSource.getMessage(
							"ElectrictyMeters.AciveMeterDestroyError", null,
							locale));
				}
			};
			errorResponse.setStatus("AciveMeterDestroyError");
			errorResponse.setResult(errorMapResponse);

			return errorResponse;
		} else {
			try {
				electricityMetersService.delete(metersEntity.getElMeterId());
			} catch (Exception e) {

				errorResponse.setStatus("CHILD");
				return errorResponse;
			}
			ss.setComplete();
			return metersEntity;
		}
	}

	// ****************************** Electricity Meters Filters Data methods
	// ********************************/

	@RequestMapping(value = "/electrictyMeters/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getElectrictyMetersContentsForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery(
				"ElectricityMetersEntity", attributeList, null));

		return set;
	}
	
	@RequestMapping(value = "/electrictyMeters/getPersonListForFileter", method = RequestMethod.GET)
	public @ResponseBody List<?> readPersonNamesInMeters() {
		logger.info("In person data filter method");
		List<?> accountPersonList = electricityMetersService.findPersonForFilters();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<?> i = accountPersonList.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();
			String personName = "";
			personName = personName.concat((String) values[1]);
			if (((String) values[2]) != null) {
				personName = personName.concat(" ");
				personName = personName.concat((String) values[2]);
			}

			map = new HashMap<String, Object>();
			map.put("personId", (Integer) values[0]);
			map.put("personName", personName);
			map.put("personType", (String) values[3]);
			map.put("personStyle", (String) values[4]);

			result.add(map);
		}
		return result;
	}

	// ****************************** Electricity Parameters
	// Read,Create,Update,Delete methods ********************************//

	@RequestMapping(value = "/electrictyMeters/meterParameterRead/{elMeterId}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<ElectricityMeterParametersEntity> readMeterParameters(@PathVariable int elMeterId) {
		logger.info("In Meter Parameters read Method");
		List<ElectricityMeterParametersEntity> parametersEntities = electricityMeterParametersService.findAllById(elMeterId);
		return parametersEntities;
	}

	@RequestMapping(value = "/electrictyMeters/meterParameterCreate/{elMeterId}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object saveMeterParameters(
			@ModelAttribute("parametersEntity") ElectricityMeterParametersEntity parametersEntity,
			BindingResult result, @PathVariable int elMeterId, ModelMap model,
			HttpServletRequest req, SessionStatus sessionStatus,
			final Locale locale) throws ParseException {

		logger.info("In Meter Parameters Create Method");
		parametersEntity.setStatus("Inactive");
		// parametersEntity.setElMeterId(elMeterId);
		parametersEntity.setElectricityMetersEntity(electricityMetersService
				.find(elMeterId));

		electricityMeterParametersService.save(parametersEntity);
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("elMeterParameterId",
				parametersEntity.getElMeterParameterId());
		parameterMap.put("mpmId", parametersEntity.getMpmId());
		parameterMap.put("elMasterParameterDataType",
				parametersEntity.getElMasterParameterDataType());
		parameterMap.put("elMeterParameterValue",
				parametersEntity.getElMeterParameterValue());
		parameterMap.put("notes", parametersEntity.getNotes());
		parameterMap.put("elMeterParameterSequence",
				parametersEntity.getElMeterParameterSequence());
		parameterMap.put("status", parametersEntity.getStatus());
		parameterMap.put("createdBy", parametersEntity.getCreatedBy());
		parameterMap.put("lastUpdatedBy", parametersEntity.getLastUpdatedBy());
		parameterMap.put("lastUpdatedDT", parametersEntity.getLastUpdatedDT());

		return parameterMap;
	}

	@RequestMapping(value = "/electrictyMeters/meterParameterUpdate/{elMeterId}", method = RequestMethod.GET)
	public @ResponseBody
	Object editMeterParameter(
			@ModelAttribute("parametersEntity") ElectricityMeterParametersEntity parametersEntity,
			BindingResult bindingResult, @PathVariable int elMeterId,
			ModelMap model, SessionStatus sessionStatus, final Locale locale,
			HttpServletRequest req) throws ParseException {

		logger.info("In Meter Parameters Update Method");
		parametersEntity.setElectricityMetersEntity(electricityMetersService
				.find(elMeterId));
		electricityMeterParametersService.update(parametersEntity);
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("elMeterParameterId",
				parametersEntity.getElMeterParameterId());
		parameterMap.put("mpmId", parametersEntity.getMpmId());
		parameterMap.put("elMasterParameterDataType",
				parametersEntity.getElMasterParameterDataType());
		parameterMap.put("elMeterParameterValue",
				parametersEntity.getElMeterParameterValue());
		parameterMap.put("notes", parametersEntity.getNotes());
		parameterMap.put("elMeterParameterSequence",
				parametersEntity.getElMeterParameterSequence());
		parameterMap.put("status", parametersEntity.getStatus());
		parameterMap.put("createdBy", parametersEntity.getCreatedBy());
		parameterMap.put("lastUpdatedBy", parametersEntity.getLastUpdatedBy());
		parameterMap.put("lastUpdatedDT", parametersEntity.getLastUpdatedDT());
		return parameterMap;
	}

	@RequestMapping(value = "/electrictyMeters/meterParameterDestroy", method = RequestMethod.GET)
	public @ResponseBody
	Object deleteMeterParameter(
			@ModelAttribute("parametersEntity") ElectricityMeterParametersEntity parametersEntity,
			BindingResult bindingResult, ModelMap model, SessionStatus ss) {

		logger.info("In Meter Parameters Destroy Method");
		JsonResponse errorResponse = new JsonResponse();
		final Locale locale = null;
		if (parametersEntity.getStatus().equalsIgnoreCase("Active")) {
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				/**
					 * 
					 */
				private static final long serialVersionUID = 1L;

				{
					put("AciveParameterDestroyError", messageSource.getMessage(
							"ElectrictyMeters.AciveParameterDestroyError",
							null, locale));
				}
			};
			errorResponse.setStatus("AciveParameterDestroyError");
			errorResponse.setResult(errorMapResponse);

			return errorResponse;
		} else {
			try {
				electricityMeterParametersService.delete(parametersEntity
						.getElMeterParameterId());
			} catch (Exception e) {
				errorResponse.setStatus("CHILD");
				return errorResponse;
			}
			ss.setComplete();
			return parametersEntity;
		}
	}

	@RequestMapping(value = "/meterParameter/parameterStatusUpdateFromInnerGrid/{elMeterParameterId}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void parameterStatusUpdateFromInnerGrid(
			@PathVariable int elMeterParameterId, HttpServletResponse response) {
		electricityMeterParametersService.updateParameterStatusFromInnerGrid(
				elMeterParameterId, response);
	}

	// ****************************** Electricity Parameters Filter Data methods
	// ********************************/

	@RequestMapping(value = "/meterParameter/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getMeterParameterContentsForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery(
				"ElectricityMeterParametersEntity", attributeList, null));

		return set;
	}

	// ****************************** Electricity Meter Location
	// Read,Create,Update,Delete methods ********************************//

	@RequestMapping(value = "/electrictyMeters/meterLocationsRead/{elMeterId}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<ElectricityMeterLocationEntity> readMeterLocations(
			@PathVariable int elMeterId) {
		logger.info("In Meter Locations read Method");
		List<ElectricityMeterLocationEntity> parametersEntities = electricityMeterLocationsService
				.findAllById(elMeterId);
		return parametersEntities;
	}

	@RequestMapping(value = "/electrictyMeters/meterLocationCreate/{elMeterId}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object saveMeterLocation(
			@ModelAttribute("meterLocationEntity") ElectricityMeterLocationEntity meterLocationEntity,
			BindingResult result, @PathVariable int elMeterId, ModelMap model,
			HttpServletRequest req, SessionStatus sessionStatus,
			final Locale locale) throws ParseException {

		logger.info("In Meter Location Create Method");
		// meterLocationEntity.setElMeterId(elMeterId);
		ElectricityMetersEntity electricityMetersEntity = electricityMetersService.find(elMeterId);
		meterLocationEntity.setElectricityMetersEntity(electricityMetersEntity);
		meterLocationEntity.setMeterFixedDate(dateTimeCalender
				.getDateToStore(req.getParameter("meterFixedDate")));
		meterLocationEntity.setAccount(accountService.find(meterLocationEntity.getAccountId()));

		electricityMeterLocationsService.save(meterLocationEntity);
		
		electricityMetersEntity.setAccount(accountService.find(meterLocationEntity.getAccountId()));
		electricityMetersEntity.setMeterStatus("In Service");
		
		List<ServiceMastersEntity> serviceMasterList = electricityMeterLocationsService.getServiceMasterObj(meterLocationEntity.getAccountId(),electricityMetersEntity.getTypeOfServiceForMeters());
		
		MeterHistoryEntity meterHistoryEntity = new MeterHistoryEntity();
		
		meterHistoryEntity.setServiceMastersEntity(serviceMasterList.get(0));
		meterHistoryEntity.setElectricityMetersEntity(electricityMetersEntity);
		meterHistoryEntity.setElectricityMeterLocationEntity(meterLocationEntity);
		
		meterHistoryService.save(meterHistoryEntity);
		
		electricityMetersService.update(electricityMetersEntity);

		Map<String, Object> locationMap = new HashMap<String, Object>();
		locationMap.put("elMeterLocationId",
				meterLocationEntity.getElMeterLocationId());
		locationMap.put("meterFixedDate",
				meterLocationEntity.getMeterFixedDate());
		locationMap.put("meterFixedBy", meterLocationEntity.getMeterFixedBy());
		locationMap
				.put("intialReading", meterLocationEntity.getIntialReading());
		locationMap.put("finalReading", meterLocationEntity.getFinalReading());
		locationMap.put("dgIntitalReading", meterLocationEntity.getDgIntitalReading());
		locationMap.put("dgFinalReading", meterLocationEntity.getDgFinalReading());
		locationMap.put("locationStatus",
				meterLocationEntity.getLocationStatus());
		locationMap.put("createdBy", meterLocationEntity.getCreatedBy());
		locationMap
				.put("lastUpdatedBy", meterLocationEntity.getLastUpdatedBy());
		locationMap
				.put("lastUpdatedDT", meterLocationEntity.getLastUpdatedDT());

		return locationMap;
	}

	@RequestMapping(value = "/electrictyMeters/meterLocationUpdate/{elMeterId}", method = RequestMethod.GET)
	public @ResponseBody
	Object editMeterLocation(@ModelAttribute("meterLocationEntity") ElectricityMeterLocationEntity meterLocationEntity,BindingResult bindingResult, @PathVariable int elMeterId,
			ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

		logger.info("In Meter Location Update Method");
		meterLocationEntity.setMeterFixedDate(dateTimeCalender.getDateToStore(req.getParameter("meterFixedDate")));
		meterLocationEntity.setElectricityMetersEntity(electricityMetersService.find(elMeterId));
		meterLocationEntity.setAccount(accountService.find(meterLocationEntity.getAccountId()));
		meterLocationEntity.setMeterReleaseDate(electricityMeterLocationsService.find(meterLocationEntity.getElMeterLocationId()).getMeterReleaseDate());
		electricityMeterLocationsService.update(meterLocationEntity);

		
		Map<String, Object> locationMap = new HashMap<String, Object>();
		locationMap.put("elMeterLocationId",meterLocationEntity.getElMeterLocationId());
		locationMap.put("meterFixedDate",meterLocationEntity.getMeterFixedDate());
		locationMap.put("meterFixedBy",meterLocationEntity.getMeterFixedBy());
		locationMap.put("intialReading",meterLocationEntity.getIntialReading());
		locationMap.put("finalReading",meterLocationEntity.getFinalReading());
		locationMap.put("dgIntitalReading", meterLocationEntity.getDgIntitalReading());
		locationMap.put("dgFinalReading", meterLocationEntity.getDgFinalReading());
		locationMap.put("locationStatus",meterLocationEntity.getLocationStatus());
		locationMap.put("createdBy", meterLocationEntity.getCreatedBy());
		locationMap.put("lastUpdatedBy",meterLocationEntity.getLastUpdatedBy());
		locationMap.put("lastUpdatedDT",meterLocationEntity.getLastUpdatedDT());
		 
		return locationMap;
	}

	@RequestMapping(value = "/electrictyMeters/meterLocationDestroy", method = RequestMethod.GET)
	public @ResponseBody
	Object deleteMeterLocation(
			@ModelAttribute("meterLocationEntity") ElectricityMeterLocationEntity meterLocationEntity,
			BindingResult bindingResult, ModelMap model, SessionStatus ss) {

		logger.info("In Meter Location Destroy Method");
		try {
			electricityMeterLocationsService.delete(meterLocationEntity
					.getElMeterLocationId());
		} catch (Exception e) {
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return meterLocationEntity;
	}

	// ****************************** Electricity Location Filter Data methods
	// ********************************/

	@RequestMapping(value = "/meterLocation/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getMeterLocationContentsForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery(
				"ElectricityMeterLocationEntity", attributeList, null));

		return set;
	}
	
	@RequestMapping(value = "/meterLocation/getServiceTypeList", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getServiceType() {
		return electricityMeterLocationsService.getAllServiceTypes();
	}

	@RequestMapping(value = "/meterLocation/getServiceTypesUrl", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> getServiceTypes() {
		List<Map<String, Object>> serviceTypeList = new ArrayList<Map<String, Object>>();
		Map<String, Object> serviceTypeMap = null;
		List<ServicePointEntity> serviceseList = servicePointService.findAllServicePoints();

		for (Iterator<ServicePointEntity> iterator1 = serviceseList.iterator(); iterator1.hasNext();) {
			ServicePointEntity servicePointEntity = (ServicePointEntity) iterator1.next();

			serviceTypeMap = new HashMap<String, Object>();
			serviceTypeMap.put("servicePointId",servicePointEntity.getServicePointId());
			serviceTypeMap.put("typeOfService",	servicePointEntity.getTypeOfService());

			serviceTypeList.add(serviceTypeMap);
		}
		return serviceTypeList;
	}
	
	@RequestMapping(value = "/meters/accountNumberAutocomplete", method = RequestMethod.GET)
	public @ResponseBody List<?> accountNumberAutocomplete() {
		return electricityMeterLocationsService.getAllAccuntNumbers();
	}
	
	@RequestMapping(value = "/meters/releaseMeterStatusClick", method = { RequestMethod.GET, RequestMethod.POST })
	public void releaseMeterStatusClick(HttpServletRequest request,HttpServletResponse response,@RequestBody String body) throws ParseException
	{	
		Date releaseDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("releaseDate"));
		int elMeterLocationId = Integer.parseInt(request.getParameter("selectedMeterLocationId"));
		double finalReading = Double.parseDouble(request.getParameter("finalReadingMeter"));
		
		double dgFinalReading = 0;
		if(!request.getParameter("dgFinalReadingMeter").isEmpty()){
			dgFinalReading = Double.parseDouble(request.getParameter("dgFinalReadingMeter"));
		}
		
		logger.info("relese meter"+finalReading+"::::"+releaseDate);
		
		try
		{
			PrintWriter out = response.getWriter();
			ElectricityMeterLocationEntity meterLocationEntity = electricityMeterLocationsService.find(elMeterLocationId);
			ElectricityMetersEntity electricityMetersEntity = meterLocationEntity.getElectricityMetersEntity();
			if(electricityMetersEntity.getMeterStatus().equalsIgnoreCase("In Repair")){
				out.write("Already released this meter");
			}else {
				
				electricityMetersEntity.setMeterStatus("In Repair");
				meterLocationEntity.setFinalReading(finalReading);
				meterLocationEntity.setDgFinalReading(dgFinalReading);
				meterLocationEntity.setMeterReleaseDate(new java.sql.Date(releaseDate.getTime()));
				electricityMetersEntity.setAccount(null);
				//electricityMetersEntity.setPointEntity(null);
				electricityMeterLocationsService.update(meterLocationEntity);
				electricityMetersService.update(electricityMetersEntity);
				out.write("Meter released succefully");
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		//electricityMeterLocationsService.releaseMeterStatusClick(elMeterLocationId,new java.sql.Date(releaseDate.getTime()),finalReading,response);
	}

	@RequestMapping(value = "/electrictyMeters/getMeterParameterNamesList/{typeOfService}/{meterId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getMeterParameterList(@PathVariable String typeOfService,@PathVariable int meterId) {
		return electricityMeterParametersService.getMeterParameterNamesList(typeOfService,meterId);
	}

	// Bill data posted to ledger code here

	@RequestMapping(value = "/electricityBills/setBillStatus/{elBillId}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
	public void setBillStatus(@PathVariable int elBillId,@PathVariable String operation, HttpServletResponse response) throws Exception {

		if (operation.equalsIgnoreCase("activate")) {
			electricityBillsService.setBillStatus(elBillId, operation, response);
		} else {

			synchronized (this) {
				
				ElectricityBillEntity billEntity = electricityBillsService.find(elBillId);

				if (billEntity.getStatus().equals("Approved")) {
					
					List<String> transactionCodeList = null;
					if(billEntity.getTypeOfService().equals("Telephone Broadband")){
						
						List<ElectricityLedgerEntity> list = electricityLedgerService.getTeleBroadbandLedgerDetails(billEntity.getAccountId(),billEntity.getTypeOfService()+" Ledger");
						if(list==null || list.isEmpty()){
							
							ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();						
							ledgerEntity.setTransactionSequence((electricityLedgerService.getLedgerSequence(billEntity.getAccountObj().getAccountId()).intValue())+1);
							ledgerEntity.setAccountId(billEntity.getAccountObj().getAccountId());
							ledgerEntity.setLedgerType(billEntity.getTypeOfService()+" Ledger");
							ledgerEntity.setPostType("INIT");
							
							if(billEntity.getTypeOfService().equals("Telephone Broadband")){
								ledgerEntity.setTransactionCode("TEL");
								transactionCodeList = electricitySubLedgerService.getTransactionCodesForTb(billEntity.getTypeOfService());
							}
							
							int currentYear = Calendar.getInstance().get(Calendar.YEAR);
							
							Calendar calLast = Calendar.getInstance();
							int lastYear = calLast.get(Calendar.YEAR)-1;

							ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
							ledgerEntity.setLedgerDate(billEntity.getBillDueDate());
							ledgerEntity.setAmount(0.00);
							ledgerEntity.setBalance(0.00);
							ledgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
							ledgerEntity.setStatus("Approved");
							ledgerEntity.setRemarks("Generated "+billEntity.getTypeOfService()+" Bill");
							
							electricityLedgerService.save(ledgerEntity);
							
							List<ElectricitySubLedgerEntity> batchSubLedgerList = new ArrayList<ElectricitySubLedgerEntity>();
						    for (String code : transactionCodeList) {
						    	ElectricitySubLedgerEntity subLedgerEntity = new ElectricitySubLedgerEntity();
						    	subLedgerEntity.setTransactionCode(code);
						    	subLedgerEntity.setAmount(0.00);
						    	subLedgerEntity.setBalanceAmount(0.00);
						    	subLedgerEntity.setStatus("Approved");
						    	subLedgerEntity.setElectricityLedgerEntity(ledgerEntity);
						    	
						    	batchSubLedgerList.add(subLedgerEntity);
						    	//electricitySubLedgerService.save(subLedgerEntity);
							}
								electricitySubLedgerService.batchSave(batchSubLedgerList);
							
						}
						
					}
						
						int lastTransactionLedgerId = electricityLedgerService.getLastLedgerTransactionAmount(billEntity.getAccountId(),billEntity.getTypeOfService()+" Ledger");

						String typeOfService = billEntity.getTypeOfService();
						ElectricityLedgerEntity lastTransactionLedgerEntity = electricityLedgerService.find(lastTransactionLedgerId);

						ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();

						ledgerEntity.setTransactionSequence((electricityLedgerService.getLedgerSequence(billEntity.getAccountId()).intValue()) + 1);
						ledgerEntity.setAccountId(billEntity.getAccountId());
						ledgerEntity.setLedgerType(typeOfService + " Ledger");
						ledgerEntity.setPostType("BILL");
						if (typeOfService.equals("Electricity")) {
							ledgerEntity.setTransactionCode("EL");
							transactionCodeList = electricitySubLedgerService.getTransactionCodesForElectricity(typeOfService);
						}
						if (typeOfService.equals("Gas")) {
							ledgerEntity.setTransactionCode("GA");
							transactionCodeList = electricitySubLedgerService.getTransactionCodesForGas(typeOfService);
						}
						if (typeOfService.equals("Solid Waste")) {
							ledgerEntity.setTransactionCode("SW");
							transactionCodeList = electricitySubLedgerService.getTransactionCodesForSolidWaste(typeOfService);
						}
						if (typeOfService.equals("Water")) {
							ledgerEntity.setTransactionCode("WT");
							transactionCodeList = electricitySubLedgerService.getTransactionCodesForWater(typeOfService);
						}
						if (typeOfService.equals("Others")) {
							ledgerEntity.setTransactionCode("OT");
							transactionCodeList = electricitySubLedgerService.getTransactionCodesForOthers(typeOfService);
						}
						if (typeOfService.equals("CAM")) {
							ledgerEntity.setTransactionCode("CAM");
							transactionCodeList = electricitySubLedgerService.getTransactionCodesForCam(typeOfService);
						}
						if (typeOfService.equals("Telephone Broadband")) {
							ledgerEntity.setTransactionCode("TEL");
							transactionCodeList = electricitySubLedgerService.getTransactionCodesForTb(typeOfService);
						}

						int currentYear = Calendar.getInstance().get(Calendar.YEAR);
						
						Calendar calLast = Calendar.getInstance();
						int lastYear = calLast.get(Calendar.YEAR)-1;

						ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
						ledgerEntity.setPostReference(billEntity.getBillNo());
						ledgerEntity.setLedgerDate(billEntity.getBillDueDate());
						ledgerEntity.setAmount(billEntity.getBillAmount());
						ledgerEntity.setBalance(lastTransactionLedgerEntity.getBalance() + billEntity.getBillAmount());
						ledgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
						ledgerEntity.setStatus("Approved");
						ledgerEntity.setRemarks("Generated "+billEntity.getTypeOfService()+" Bill");

						electricityLedgerService.save(ledgerEntity);
						
						for (String code : transactionCodeList) {
							
							ElectricitySubLedgerEntity subLedgerEntity = new ElectricitySubLedgerEntity();
							List<ElectricityBillLineItemEntity> lineItemEntity = electricityBillLineItemService.findLineItemBasedOnTransactionCode(elBillId,code);
							if (!lineItemEntity.isEmpty()) {
								
								subLedgerEntity.setTransactionCode(lineItemEntity.get(0).getTransactionCode());
								subLedgerEntity.setAmount(lineItemEntity.get(0).getBalanceAmount());
								subLedgerEntity.setBalanceAmount(subLedgerEntity.getAmount());
								subLedgerEntity.setStatus("Approved");
							} else {
								subLedgerEntity.setTransactionCode(code);
								subLedgerEntity.setAmount(0);
								subLedgerEntity.setBalanceAmount(subLedgerEntity.getAmount());
								subLedgerEntity.setStatus("Approved");
							}
							subLedgerEntity.setElectricityLedgerEntity(ledgerEntity);
							electricitySubLedgerService.save(subLedgerEntity);
						}
					
					if(billEntity.getNetAmount()>0){
						electricityBillsService.setBillStatus(elBillId, operation, response);
					}else{
						electricityBillsService.setBillStatusAsPaid(billEntity.getElBillId(),operation,response);
					}
					
					/*List<AdvanceCollectionEntity> collectionEntitiesList = advanceCollectionService.testUniqueAccount(billEntity.getAccountId());
					if(!collectionEntitiesList.isEmpty()){
						AdvanceCollectionEntity advanceCollectionEntity = collectionEntitiesList.get(0);
						if(advanceCollectionEntity.getBalanceAmount()!=0){
							PaymentAdjustmentEntity adjustmentEntity = new PaymentAdjustmentEntity();
							
							adjustmentEntity.setAccountId(billEntity.getAccountId());
							if(advanceCollectionEntity.getBalanceAmount()>billEntity.getNetAmount()){
								adjustmentEntity.setAdjustmentAmount(billEntity.getNetAmount());
								adjustmentEntity.setClearedStatus("Yes");
							}else if(advanceCollectionEntity.getBalanceAmount()<billEntity.getNetAmount()){
								adjustmentEntity.setAdjustmentAmount(advanceCollectionEntity.getBalanceAmount());
								adjustmentEntity.setClearedStatus("No");
							}
							adjustmentEntity.setAdjustmentDate(new java.sql.Date(new Date().getTime()));
							adjustmentEntity.setAdjustmentLedger(billEntity.getTypeOfService()+" Ledger");
							adjustmentEntity.setAdjustmentNo(genrateAdjustmentNumber());
							adjustmentEntity.setPostedToGl("No");
							adjustmentEntity.setStatus("Created");
							adjustmentEntity.setAdjustmentType("Credit Adjustment");
							adjustmentEntity.setTallystatus("Not Posted");
							
							adjustmentService.save(adjustmentEntity);
							
							advanceBillAdjustmentDetailsPostedToLedger(adjustmentEntity,billEntity);
							
							adjustmentEntity.setPostedToGl("Yes");
							adjustmentEntity.setStatus("Posted");
							adjustmentEntity.setPostedGlDate(new Timestamp(new Date().getTime()));
							adjustmentEntity.setClearedStatus("Yes");
							
							adjustmentService.update(adjustmentEntity);
							
							ClearedPayments clearedPayments = new ClearedPayments();
							
							clearedPayments.setPaymentAdjustmentEntity(adjustmentEntity);
							clearedPayments.setAmount(adjustmentEntity.getAdjustmentAmount());
							clearedPayments.setAdvanceCollectionEntity(advanceCollectionEntity);
							clearedPayments.setBillId(billEntity.getBillNo());
							clearedPayments.setClearedDate(new java.sql.Date(new Date().getTime()));
							
							clearedPaymentsService.save(clearedPayments);
							
							advanceCollectionEntity.setAmountCleared(advanceCollectionEntity.getAmountCleared()+clearedPayments.getAmount());
							advanceCollectionEntity.setBalanceAmount(advanceCollectionEntity.getTotalAmount()-(advanceCollectionEntity.getAmountCleared()));
							advanceCollectionService.update(advanceCollectionEntity);
							
							billEntity.setAdvanceClearedAmount(clearedPayments.getAmount());
							billEntity.setNetAmount(billEntity.getNetAmount()-billEntity.getAdvanceClearedAmount());
							if(billEntity.getNetAmount()==0){
								billEntity.setStatus("Paid");
							}else{
								billEntity.setStatus("Posted");
							}
								electricityBillsService.update(billEntity);
						}
					}*/
					
					chequeBounceService.updateChequeBounceDetailsStatusBasedOnBillsPosting(billEntity.getAccountId());
					
				} else {
					electricityBillsService.setBillStatus(elBillId, operation, response);
				}
			}
			
		}
	}
	
	@RequestMapping(value = "/electricityBills/postAllBillToLedger", method =  RequestMethod.POST )
	public @ResponseBody List<?> postAllBillToLedger(HttpServletResponse response,HttpServletRequest request) throws Exception {
		
        logger.info("Post All Bill Ledger");
        String serviceType=request.getParameter("serviceTypePost");
        String presentdate=request.getParameter("presentdatePost");
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
		Date date = formatter.parse(presentdate);
		synchronized (this) {
			
        	List<ElectricityBillEntity> billEntityList = electricityBillsService.findAllBillData(date,serviceType);
    		for (ElectricityBillEntity billEntity : billEntityList) {

    			if (billEntity.getStatus().equals("Approved")) {
    				
    				List<String> transactionCodeList = null;
    				if(billEntity.getTypeOfService().equals("Telephone Broadband")){
    					
    					List<ElectricityLedgerEntity> list = electricityLedgerService.getTeleBroadbandLedgerDetails

    							(billEntity.getAccountId(),billEntity.getTypeOfService()+" Ledger");
    					if(list==null || list.isEmpty()){
    						
    						ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();			

			
    						ledgerEntity.setTransactionSequence((electricityLedgerService.getLedgerSequence

(billEntity.getAccountObj().getAccountId()).intValue())+1);
    						ledgerEntity.setAccountId(billEntity.getAccountObj().getAccountId());
    						ledgerEntity.setLedgerType(billEntity.getTypeOfService()+" Ledger");
    						ledgerEntity.setPostType("INIT");
    						
    						if(billEntity.getTypeOfService().equals("Telephone Broadband")){
    							ledgerEntity.setTransactionCode("TEL");
    							transactionCodeList = electricitySubLedgerService.getTransactionCodesForTb

(billEntity.getTypeOfService());
    						}
    						
    						int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    						
    						Calendar calLast = Calendar.getInstance();
    						int lastYear = calLast.get(Calendar.YEAR)-1;

    						ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
    						ledgerEntity.setLedgerDate(billEntity.getBillDueDate());
    						ledgerEntity.setAmount(0.00);
    						ledgerEntity.setBalance(0.00);
    						ledgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
    						ledgerEntity.setStatus("Approved");
    						ledgerEntity.setRemarks("Generated "+billEntity.getTypeOfService()+" Bill");
    						
    						electricityLedgerService.save(ledgerEntity);
    						
    						List<ElectricitySubLedgerEntity> batchSubLedgerList = new 

ArrayList<ElectricitySubLedgerEntity>();
    					    for (String code : transactionCodeList) {
    					    	ElectricitySubLedgerEntity subLedgerEntity = new ElectricitySubLedgerEntity();
    					    	subLedgerEntity.setTransactionCode(code);
    					    	subLedgerEntity.setAmount(0.00);
    					    	subLedgerEntity.setBalanceAmount(0.00);
    					    	subLedgerEntity.setStatus("Approved");
    					    	subLedgerEntity.setElectricityLedgerEntity(ledgerEntity);
    					    	
    					    	batchSubLedgerList.add(subLedgerEntity);
    						}
    							electricitySubLedgerService.batchSave(batchSubLedgerList);
    						
    					}
    					
    				}
    					
    					int lastTransactionLedgerId = electricityLedgerService.getLastLedgerTransactionAmount

(billEntity.getAccountId(),billEntity.getTypeOfService()+" Ledger");

    					String typeOfService = billEntity.getTypeOfService();
    					ElectricityLedgerEntity lastTransactionLedgerEntity = electricityLedgerService.find

(lastTransactionLedgerId);

    					ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();

    					ledgerEntity.setTransactionSequence((electricityLedgerService.getLedgerSequence

(billEntity.getAccountId()).intValue()) + 1);
    					ledgerEntity.setAccountId(billEntity.getAccountId());
    					ledgerEntity.setLedgerType(typeOfService + " Ledger");
    					ledgerEntity.setPostType("BILL");
    					if (typeOfService.equals("Electricity")) {
    						ledgerEntity.setTransactionCode("EL");
    						transactionCodeList = electricitySubLedgerService.getTransactionCodesForElectricity

(typeOfService);
    					}
    					if (typeOfService.equals("Gas")) {
    						ledgerEntity.setTransactionCode("GA");
    						transactionCodeList = electricitySubLedgerService.getTransactionCodesForGas

(typeOfService);
    					}
    					if (typeOfService.equals("Solid Waste")) {
    						ledgerEntity.setTransactionCode("SW");
    						transactionCodeList = electricitySubLedgerService.getTransactionCodesForSolidWaste

(typeOfService);
    					}
    					if (typeOfService.equals("Water")) {
    						ledgerEntity.setTransactionCode("WT");
    						transactionCodeList = electricitySubLedgerService.getTransactionCodesForWater

(typeOfService);
    					}
    					if (typeOfService.equals("Others")) {
    						ledgerEntity.setTransactionCode("OT");
    						transactionCodeList = electricitySubLedgerService.getTransactionCodesForOthers

(typeOfService);
    					}
    					if (typeOfService.equals("CAM")) {
    						ledgerEntity.setTransactionCode("CAM");
    						transactionCodeList = electricitySubLedgerService.getTransactionCodesForCam

(typeOfService);
    					}
    					if (typeOfService.equals("Telephone Broadband")) {
    						ledgerEntity.setTransactionCode("TEL");
    						transactionCodeList = electricitySubLedgerService.getTransactionCodesForTb

(typeOfService);
    					}

    					int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    					
    					Calendar calLast = Calendar.getInstance();
    					int lastYear = calLast.get(Calendar.YEAR)-1;

    					ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
    					ledgerEntity.setPostReference(billEntity.getBillNo());
    					ledgerEntity.setLedgerDate(billEntity.getBillDueDate());
    					ledgerEntity.setAmount(billEntity.getBillAmount());
    					ledgerEntity.setBalance(lastTransactionLedgerEntity.getBalance() + billEntity.getBillAmount

());
    					ledgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
    					ledgerEntity.setStatus("Approved");
    					ledgerEntity.setRemarks("Generated "+billEntity.getTypeOfService()+" Bill");

    					electricityLedgerService.save(ledgerEntity);
    					
    					for (String code : transactionCodeList) {
    						
    						ElectricitySubLedgerEntity subLedgerEntity = new ElectricitySubLedgerEntity();
    						List<ElectricityBillLineItemEntity> lineItemEntity = 

    						electricityBillLineItemService.findLineItemBasedOnTransactionCode(billEntity.getElBillId(),code);
    						if (!lineItemEntity.isEmpty()) {
    							
    							subLedgerEntity.setTransactionCode(lineItemEntity.get(0).getTransactionCode

());
    							subLedgerEntity.setAmount(lineItemEntity.get(0).getBalanceAmount());
    							subLedgerEntity.setBalanceAmount(subLedgerEntity.getAmount());
    							subLedgerEntity.setStatus("Approved");
    						} else {
    							subLedgerEntity.setTransactionCode(code);
    							subLedgerEntity.setAmount(0);
    							subLedgerEntity.setBalanceAmount(subLedgerEntity.getAmount());
    							subLedgerEntity.setStatus("Approved");
    						}
    						subLedgerEntity.setElectricityLedgerEntity(ledgerEntity);
    						electricitySubLedgerService.save(subLedgerEntity);
    					}
    				
    					if(billEntity.getNetAmount()>0){
    						electricityBillsService.setElectricityBillStatusAsPosted(billEntity.getElBillId(),response);
    					}else{
    						electricityBillsService.setElectricityBillStatusAsPaid(billEntity.getElBillId(),response);
    					}
    				
    				
    				chequeBounceService.updateChequeBounceDetailsStatusBasedOnBillsPosting(billEntity.getAccountId());
    			} 
    			
    		}
        	
		}
return null;
        
	}
	
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
		return "AD"+num; */ 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new java.util.Date());
		int nextSeqVal = adjustmentService.autoGeneratedAdjustmentNumber();	 
		
		return "AD"+year+nextSeqVal; 
	}
	
	public void advanceBillAdjustmentDetailsPostedToLedger(PaymentAdjustmentEntity adjustmentEntity,ElectricityBillEntity billEntity){
		
		int lastTransactionLedgerId = electricityLedgerService.getLastLedgerTransactionAmount(adjustmentEntity.getAccountId(),billEntity.getTypeOfService()+" Ledger");
		ElectricityLedgerEntity lastTransactionLedgerEntity = electricityLedgerService.find(lastTransactionLedgerId);

		ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();
		ledgerEntity.setTransactionSequence((electricityLedgerService.getLedgerSequence(adjustmentEntity.getAccountId()).intValue()) + 1);
		ledgerEntity.setAccountId(adjustmentEntity.getAccountId());

		String segmentType = adjustmentEntity.getAdjustmentLedger();
		ledgerEntity.setLedgerType(segmentType);
		ledgerEntity.setPostType("ADJUSTMENT");

		if (segmentType.equals("Electricity Ledger")) {
			ledgerEntity.setTransactionCode("EL");
		}
		if (segmentType.equals("Gas Ledger")) {
			ledgerEntity.setTransactionCode("GA");
		}
		if (segmentType.equals("Solid Waste Ledger")) {
			ledgerEntity.setTransactionCode("SW");
		}
		if (segmentType.equals("Water Ledger")) {
			ledgerEntity.setTransactionCode("WT");
		}
		if (segmentType.equals("Common Ledger")) {
			ledgerEntity.setTransactionCode("OT");
		}
		if (segmentType.equals("CAM Ledger")) {
			ledgerEntity.setTransactionCode("CAM");
		}
		if (segmentType.equals("Telephone Broadband Ledger")) {
			ledgerEntity.setTransactionCode("TEL");
		}

		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		Calendar calLast = Calendar.getInstance();
		int lastYear = calLast.get(Calendar.YEAR)-1;

		ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
		ledgerEntity.setPostReference(adjustmentEntity.getAdjustmentNo());
		ledgerEntity.setLedgerDate(new java.sql.Date(new Date().getTime()));
		ledgerEntity.setLedgerDate(adjustmentEntity.getAdjustmentDate());
		ledgerEntity.setAmount(adjustmentEntity.getAdjustmentAmount());
		ledgerEntity.setBalance((lastTransactionLedgerEntity.getBalance()) - (adjustmentEntity.getAdjustmentAmount()));
		ledgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
		ledgerEntity.setStatus("Approved");
		ledgerEntity.setRemarks(adjustmentEntity.getAdjustmentType()+" - Customer advance amount adjusted to utility bills");

		electricityLedgerService.save(ledgerEntity);

	}
	
	@RequestMapping(value = "/electricityBills/approveAllBillToLedger", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody void  approveAllBillToLedger(HttpServletResponse response,HttpServletRequest request) throws ParseException	{		
		String serviceType=request.getParameter("serviceType");
		String presentdate=request.getParameter("presentdate");
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
		Date date = formatter.parse(presentdate);
		electricityBillsService.setStatusApproved(response,date,serviceType);
		
	}
	
	@RequestMapping(value = "/electricityBills/readServiceTypes", method = RequestMethod.GET)
	public @ResponseBody List<?> readServiceTypes() {		
		return electricityBillsService.findServiceTypes();
	}
	
	@RequestMapping(value = "/electricityBills/readServiceTypesForBackBill", method = RequestMethod.GET)
	public @ResponseBody List<?> readServiceTypesForBackBill() {		
		return electricityBillsService.findServiceTypesForBackBill();
	}
	
	public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	/*@Scheduled(cron = "${scheduling.job.generationRemaindersCron}")
	public void paymentRemainderSheduler() throws Exception{
		logger.info("inside mail===============");
		SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
		EmailCredentialsDetailsBean emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
	
		List<ElectricityBillEntity> billsBeforeDueDateList = electricityBillsService.getBillsNearToBillDueDate();
		List<ElectricityBillEntity> billsDueDateList = electricityBillsService.getBillsOnBillDueDate();
		List<ElectricityBillEntity> billsAfterDueDateList = electricityBillsService.getBillsAfterBillDueDate();
		
		for (ElectricityBillEntity electricityBillEntity : billsAfterDueDateList) {
			Person p = openNewTicketService.getPersons(electricityBillEntity.getAccountObj().getPerson().getPersonId());
			
			String toAddressEmail = "";
			String toAddressMobile = "";
			
			for (Contact contact : p.getContacts()) {
				if(contact.getContactType().equals("Email")&& contact.getContactPrimary().equals("Yes")){
					toAddressEmail = contact.getContactContent();
				}
				if(contact.getContactType().equals("Mobile") && contact.getContactPrimary().equals("Yes")){
					toAddressMobile = contact.getContactContent();
				}
			}
			
			String personName = p.getFirstName();
			String serviceType = electricityBillEntity.getTypeOfService();
			String mailSubject="Exceeded due date to pay for your "+serviceType+" bill";
			
			String messagePerson = "Skyon RWA payment desk.The bill dated "+DateFormatUtils.format(electricityBillEntity.getBillDate(), "dd-MMM-yyyy")+" for an amount of Rs "+electricityBillEntity.getNetAmount()+" towards your "+electricityBillEntity.getTypeOfService()+" bill is due for payment already exceded due date("+DateFormatUtils.format(electricityBillEntity.getBillDueDate(), "dd-MMM-yyyy")+"). Please pay the amount immediately."
							+"If you have any queries,contact to our payment desk service";
			
			String messagePerson="Your electricity bill for the month of "+DateFormatUtils.format(electricityBillEntity.getBillDate(), "dd-MMM-yyyy")+"is  overdue. Kindly make the payments at your earliest convenience to avoid service interruptions .Please ignore if already paid.";
			
			String messageContentForPerson = "<html>"						   
					+ "<style type=\"text/css\">"
					+ "table.hovertable {"
					+ "font-family: verdana,arial,sans-serif;"
					+ "font-size:11px;"
					+ "color:#333333;"
					+ "border-width: 1px;"
					+ "border-color: #999999;"
					+ "border-collapse: collapse;"
					+ "}"
					+ "table.hovertable th {"
					+ "background-color:#c3dde0;"
					+ "border-width: 1px;"
					+ "padding: 8px;"
					+ "border-style: solid;"
					+ "border-color: #394c2e;"
					+ "}"
					+ "table.hovertable tr {"
					+ "background-color:#88ab74;"
					+ "}"
					+ "table.hovertable td {"
					+ "border-width: 1px;"
					+ "padding: 8px;"
					+ "border-style: solid;"
					+ "border-color: #394c2e;"
					+ "}"
					+ "</style>"
					+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon RWA Payment Desk Service</h2> <br /> Dear "+personName+", <br/> <br/> "
					+"Your electricity bill for the month of "+DateFormatUtils.format(electricityBillEntity.getBillDate(), "dd-MMM-yyyy")+"is  overdue. Kindly make the payments at your earliest convenience to avoid service interruptions .Please ignore if already paid.<br/> <br/>"
					+"Thank you very much,and have a nice day.<br/><br/>"
					+"<br/>Payment Services,<br/>"
					+"Skyon RWA<br/><br/> <h7>This is an auto generated Email.Please do not revert back</h7>"
					+"</body></html>";
			
		if(toAddressEmail == null || toAddressEmail.equals("")){
			
		}else {	
			
			smsCredentialsDetailsBean.setNumber(toAddressMobile);
			smsCredentialsDetailsBean.setUserName(personName);
			smsCredentialsDetailsBean.setMessage(messagePerson);
			
			emailCredentialsDetailsBean.setToAddressEmail(toAddressEmail);
			emailCredentialsDetailsBean.setMessageContent(messageContentForPerson);
			
			new Thread(new PaymentRemainderMailSender(emailCredentialsDetailsBean,mailSubject)).start();
			new Thread(new SendPaymentRemainderInfoSMS(smsCredentialsDetailsBean)).start();
		}
		}
		
		for (ElectricityBillEntity electricityBillEntity : billsDueDateList) {
			Person p = openNewTicketService.getPersons(electricityBillEntity.getAccountObj().getPerson().getPersonId());
			
			String toAddressEmail = "";
			String toAddressMobile = "";
			for (Contact contact : p.getContacts()) {
				if(contact.getContactType().equals("Email")&& contact.getContactPrimary().equals("Yes")){
					toAddressEmail = contact.getContactContent();
				}
				if(contact.getContactType().equals("Mobile") && contact.getContactPrimary().equals("Yes")){
					toAddressMobile = contact.getContactContent();
				}
			}
			
			String personName = p.getFirstName();
			String serviceType = electricityBillEntity.getTypeOfService();
			String mailSubject="Last day to pay for your "+serviceType+" bill";
			
			String messagePerson = "Skyon RWA payment desk.The bill dated "+DateFormatUtils.format(electricityBillEntity.getBillDate(), "dd-MMM-yyyy")+" for an amount of Rs "+electricityBillEntity.getNetAmount()+" towards your "+electricityBillEntity.getTypeOfService()+" bill is due for payment today. Please pay the amount immediately to avoid late fee charges."
							+"If you have any queries,contact to our payment desk service";
			
			String messageContentForPerson = "<html>"						   
					+ "<style type=\"text/css\">"
					+ "table.hovertable {"
					+ "font-family: verdana,arial,sans-serif;"
					+ "font-size:11px;"
					+ "color:#333333;"
					+ "border-width: 1px;"
					+ "border-color: #999999;"
					+ "border-collapse: collapse;"
					+ "}"
					+ "table.hovertable th {"
					+ "background-color:#c3dde0;"
					+ "border-width: 1px;"
					+ "padding: 8px;"
					+ "border-style: solid;"
					+ "border-color: #394c2e;"
					+ "}"
					+ "table.hovertable tr {"
					+ "background-color:#88ab74;"
					+ "}"
					+ "table.hovertable td {"
					+ "border-width: 1px;"
					+ "padding: 8px;"
					+ "border-style: solid;"
					+ "border-color: #394c2e;"
					+ "}"
					+ "</style>"
					+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon RWA Payment Desk Service</h2> <br /> Dear "+personName+", <br/> <br/> "
					+"The bill dated "+DateFormatUtils.format(electricityBillEntity.getBillDate(), "dd-MMM-yyyy")+" for an amount of Rs "+electricityBillEntity.getNetAmount()+" towards your "+electricityBillEntity.getTypeOfService()+" bill is due for payment today. Please pay the amount immediately to avoid late fee charges.<br/> <br/>"
					+"Thank you very much, and have a nice day.<br/><br/>"
					+"<br/>Payment Services,<br/>"
					+"Skyon RWA<br/><br/> <h7>This is an auto generated Email.Please dont revert back</h7>"
					+"</body></html>";
			
		if(toAddressEmail == null || toAddressEmail.equals("")){
			
		}else {	
			
			smsCredentialsDetailsBean.setNumber(toAddressMobile);
			smsCredentialsDetailsBean.setUserName(personName);
			smsCredentialsDetailsBean.setMessage(messagePerson);
			
			emailCredentialsDetailsBean.setToAddressEmail(toAddressEmail);
			emailCredentialsDetailsBean.setMessageContent(messageContentForPerson);
			
			new Thread(new PaymentRemainderMailSender(emailCredentialsDetailsBean,mailSubject)).start();
			new Thread(new SendPaymentRemainderInfoSMS(smsCredentialsDetailsBean)).start();
		}
		}
		
		for (ElectricityBillEntity electricityBillEntity : billsBeforeDueDateList) {
			
			Person p = openNewTicketService.getPersons(electricityBillEntity.getAccountObj().getPerson().getPersonId());
			
			String toAddressEmail = "";
			String toAddressMobile = "";
			for (Contact contact : p.getContacts()) {
				if(contact.getContactType().equals("Email")&& contact.getContactPrimary().equals("Yes")){
					toAddressEmail = contact.getContactContent();
				}
				if(contact.getContactType().equals("Mobile") && contact.getContactPrimary().equals("Yes")){
					toAddressMobile = contact.getContactContent();
				}
			}
			
			String personName = p.getFirstName();
			String serviceType = electricityBillEntity.getTypeOfService();
			String mailSubject="Payment due date is approaching for your "+serviceType+" bill";
			
			String messagePerson = "Skyon RWA payment desk.The bill dated "+DateFormatUtils.format(electricityBillEntity.getBillDate(), "dd-MMM-yyyy")+" for an amount of Rs "+electricityBillEntity.getNetAmount()+" towards your "+electricityBillEntity.getTypeOfService()+" bill has been dispatched. The due date for the payment is "+DateFormatUtils.format(electricityBillEntity.getBillDueDate(), "dd-MMM-yyyy")+". Please pay ontime to avoid late fee."
							+"If you have any queries,contact to our payment desk service";
			String messagePerson="Your due date for the payment of electricity bill is  Kindly ensure payment is made on time to avoid late payment charges";
			
			String messageContentForPerson = "<html>"						   
					+ "<style type=\"text/css\">"
					+ "table.hovertable {"
					+ "font-family: verdana,arial,sans-serif;"
					+ "font-size:11px;"
					+ "color:#333333;"
					+ "border-width: 1px;"
					+ "border-color: #999999;"
					+ "border-collapse: collapse;"
					+ "}"
					+ "table.hovertable th {"
					+ "background-color:#c3dde0;"
					+ "border-width: 1px;"
					+ "padding: 8px;"
					+ "border-style: solid;"
					+ "border-color: #394c2e;"
					+ "}"
					+ "table.hovertable tr {"
					+ "background-color:#88ab74;"
					+ "}"
					+ "table.hovertable td {"
					+ "border-width: 1px;"
					+ "padding: 8px;"
					+ "border-style: solid;"
					+ "border-color: #394c2e;"
					+ "}"
					+ "</style>"
					+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon RWA Payment Desk Service</h2> <br /> Dear "+personName+", <br/> <br/> "
					+"The bill dated "+DateFormatUtils.format(electricityBillEntity.getBillDate(), "dd-MMM-yyyy")+" for an amount of Rs "+electricityBillEntity.getNetAmount()+" towards your "+electricityBillEntity.getTypeOfService()+" bill has been dispatched. The due date for the payment is "+DateFormatUtils.format(electricityBillEntity.getBillDueDate(), "dd-MMM-yyyy")+".<br/>Please pay ontime to avoid late fee.<br/> <br/>"
					+"Thank you very much, and have a nice day.<br/><br/>"
					+"<br/>Payment Services,<br/>"
					+"Skyon RWA<br/><br/> <h7>This is an auto generated Email.Please dont revert back</h7>"
					+"</body></html>";
			
		if(toAddressEmail == null || toAddressEmail.equals("")){
			
		}else {	
			
			smsCredentialsDetailsBean.setNumber(toAddressMobile);
			smsCredentialsDetailsBean.setUserName(personName);
			smsCredentialsDetailsBean.setMessage(messagePerson);
			
			emailCredentialsDetailsBean.setToAddressEmail(toAddressEmail);
			emailCredentialsDetailsBean.setMessageContent(messageContentForPerson);
			
			new Thread(new PaymentRemainderMailSender(emailCredentialsDetailsBean,mailSubject)).start();
			new Thread(new SendPaymentRemainderInfoSMS(smsCredentialsDetailsBean)).start();
		}
			
		}
	}*/
	
	@RequestMapping(value = "/electricityMeterTemplate/electricityMeterTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileElectricityMeter(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
       
		List<ElectricityMetersEntity> electricityMeterTemplateEntities = electricityMetersService.findALLBillMeters();
	
               
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
    	
    	header.createCell(0).setCellValue("Account No");
        header.createCell(1).setCellValue("Person Name");
        header.createCell(2).setCellValue("Service Type");
        header.createCell(3).setCellValue("Meter Serial No");
        header.createCell(4).setCellValue("Meter Type");
        header.createCell(5).setCellValue("Meter Ownership");
        header.createCell(6).setCellValue("Current Status"); 
            
        
    
        for(int j = 0; j<=6; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:G200"));
        }
        
        int count = 1;
        //XSSFCell cell = null;
    	for (ElectricityMetersEntity electricityBillMeter :electricityMeterTemplateEntities ) {
    		
    		XSSFRow row = sheet.createRow(count);
    		
    		//int value = count+1;
    		String str="";
    		if(electricityBillMeter.getAccount()!=null){
    			if(electricityBillMeter.getAccount().getAccountNo()!=null){
    				str=electricityBillMeter.getAccount().getAccountNo();
    			}
    			else{
    				str="";
    			}
    			
    		}
    		else{
    			str="";
    		}

    		row.createCell(0).setCellValue(str);
    		String personName = "";
    		if(electricityBillMeter.getAccount()!=null){
    			if(electricityBillMeter.getAccount().getPerson()!=null){
    				if(electricityBillMeter.getAccount().getPerson().getFirstName()!=null){
    					personName=personName.concat(electricityBillMeter.getAccount().getPerson().getFirstName());
    				}
    				else{
    					personName="";
    				}
    				if(electricityBillMeter.getAccount().getPerson().getLastName()!=null){
    					personName=personName.concat(" ");
    					personName=personName.concat(electricityBillMeter.getAccount().getPerson().getLastName());
    				}
    				else{
    					personName="";
    				}
    			
    			}
    			else{
    				personName="";;
    			}
    			
    		}
    		else{
    			personName="";
    		}

			
    
    		row.createCell(1).setCellValue(personName);
    
    		if(electricityBillMeter.getTypeOfServiceForMeters()!=null){
    		
    			str=electricityBillMeter.getTypeOfServiceForMeters();
    			}
    			else{
    				str="";
    			}

    		row.createCell(2).setCellValue(str);
    		if(electricityBillMeter.getMeterSerialNo()!=null){
        		
    			str=electricityBillMeter.getMeterSerialNo();
    			}
    			else{
    				str="";
    			}

    		
    		row.createCell(3).setCellValue(str);
    		if(electricityBillMeter.getMeterType()!=null){
        		
    			str=electricityBillMeter.getMeterType();
    			}
    			else{
    				str="";
    			}

    		row.createCell(4).setCellValue(str);
    		if(electricityBillMeter.getMeterOwnerShip()!=null){
        		
    			str=electricityBillMeter.getMeterOwnerShip();
    			}
    			else{
    				str="";
    			}


    		row.createCell(5).setCellValue(str);
    		if(electricityBillMeter.getMeterStatus()!=null){
        		
    			str=electricityBillMeter.getMeterStatus();
    			}
    			else{
    				str="";
    			}

    	    row.createCell(6).setCellValue(str);
    		
    		
    		
    		
    		count ++;
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"MetersTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/electricityMeterPdfTemplate/electricityMeterPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileBillPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
        
       
		List<ElectricityMetersEntity> electricityMeterTemplateEntities = electricityMetersService.findALLBillMeters();
	
              
      
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
      
       
        PdfPTable table = new PdfPTable(7);
        Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
        
        table.addCell(new Paragraph("Account No",font1));
        table.addCell(new Paragraph("Person Name",font1));
        table.addCell(new Paragraph("Service Type",font1));
        table.addCell(new Paragraph("Meter Serial No",font1));
        table.addCell(new Paragraph("Meter Type",font1));
        table.addCell(new Paragraph("Meter Ownership",font1));
        table.addCell(new Paragraph("Current Status",font1));
        //XSSFCell cell = null;
    	for (ElectricityMetersEntity electricityBillMeter :electricityMeterTemplateEntities ) {
    		String str="";
    		if(electricityBillMeter.getAccount()!=null){
    			if(electricityBillMeter.getAccount().getAccountNo()!=null){
    				str=electricityBillMeter.getAccount().getAccountNo();
    			}
    			else{
    				str="";
    			}
    			
    		}
    		else{
    			str="";
    		}	
        
        PdfPCell cell1 = new PdfPCell(new Paragraph(str,font3));
        String personName = "";
		if(electricityBillMeter.getAccount()!=null){
			if(electricityBillMeter.getAccount().getPerson()!=null){
				if(electricityBillMeter.getAccount().getPerson().getFirstName()!=null){
					personName=personName.concat(electricityBillMeter.getAccount().getPerson().getFirstName());
				}
				else{
					personName="";
				}
				if(electricityBillMeter.getAccount().getPerson().getLastName()!=null){
					personName=personName.concat(" ");
					personName=personName.concat(electricityBillMeter.getAccount().getPerson().getLastName());
				}
				else{
					personName="";
				}
			
			}
			else{
				personName="";;
			}
			
		}
		else{
			personName="";
		}

        PdfPCell cell2 = new PdfPCell(new Paragraph(personName,font3));
        if(electricityBillMeter.getTypeOfServiceForMeters()!=null){
    		
			str=electricityBillMeter.getTypeOfServiceForMeters();
			}
			else{
				str="";
			}
    
        PdfPCell cell3 = new PdfPCell(new Paragraph(str,font3));
        if(electricityBillMeter.getMeterSerialNo()!=null){
    		
			str=electricityBillMeter.getMeterSerialNo();
			}
			else{
				str="";
			}

		
       
        PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
        if(electricityBillMeter.getMeterType()!=null){
    		
			str=electricityBillMeter.getMeterType();
			}
			else{
				str="";
			}

   
        PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));
        if(electricityBillMeter.getMeterOwnerShip()!=null){
    		
			str=electricityBillMeter.getMeterOwnerShip();
			}
			else{
				str="";
			}

        PdfPCell cell6 = new PdfPCell(new Paragraph(str,font3));
        if(electricityBillMeter.getMeterStatus()!=null){
    		
			str=electricityBillMeter.getMeterStatus();
			}
			else{
				str="";
			}

        
        PdfPCell cell7 = new PdfPCell(new Paragraph(str,font3));
       

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.setWidthPercentage(100);
        float[] columnWidths = {7f, 7f, 7f, 8f, 8f, 8f, 8f};

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
		response.setHeader("Content-Disposition", "inline;filename=\"MetersTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/electricityBills/getDuplicateBills/{month}/{ServiceType}", method = RequestMethod.POST)
	public @ResponseBody List<?> searcchByMonth(HttpServletRequest req,@PathVariable String month,@PathVariable String ServiceType) {
		
		/* String month=req.getParameter("presentdate");
		 String ServiceType=req.getParameter("serviceType")*/;
		logger.info("in duplicate Bill serach Month method -- "+month+":::::::::::::"+ServiceType);
		List<?> bills = new ArrayList<>();
		try {
			Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
			 bills=electricityBillsService.readDuplicateBillMonthWise(monthToShow,ServiceType);
		} catch (ParseException e) {
			logger.info("Error while parsing date");
			e.printStackTrace();
		}
		return bills;
	}
	@RequestMapping(value = "/electricityBills/cancelAllBills", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody void  cancelAllBills(HttpServletResponse response,HttpServletRequest request) throws ParseException, IOException	{		
		String serviceType=request.getParameter("serviceTypeCancel");
		String presentdate=request.getParameter("presentdateCancel");
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
		Date date = formatter.parse(presentdate);
		electricityBillsService.cancelAllBillsByMonth(response, serviceType, date);
		
	}
	

	@RequestMapping(value = "/electricityBills/saveLatePayment/{month}/{ServiceType}", method = RequestMethod.POST)
	public @ResponseBody List<?> saveLatePayment(HttpServletRequest req,@PathVariable String month,@PathVariable String ServiceType) {
		
		/* String month=req.getParameter("presentdate");
		 String ServiceType=req.getParameter("serviceType")*/;
		logger.info("in duplicate Bill serach Month method -- "+month+":::::::::::::"+ServiceType);
		new ArrayList<>();
		try {
			Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
			 electricityBillsService.saveLatePaymentWise(monthToShow,ServiceType);
		} catch (ParseException e) {
			logger.info("Error while parsing date");
			e.printStackTrace();
		}
		return null;
	}
	
	

}
