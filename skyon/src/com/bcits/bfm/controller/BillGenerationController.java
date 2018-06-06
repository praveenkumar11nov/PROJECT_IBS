package com.bcits.bfm.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
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
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.model.BillParameterMasterEntity;
import com.bcits.bfm.model.CommonServicesRateMaster;
import com.bcits.bfm.model.ELRateMaster;
import com.bcits.bfm.model.ELRateSlabs;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.ElectricityBillLineItemEntity;
import com.bcits.bfm.model.ElectricityBillParametersEntity;
import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.model.ElectricitySubLedgerEntity;
import com.bcits.bfm.model.GasRateMaster;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.model.ServiceParametersEntity;
import com.bcits.bfm.model.SolidWasteRateMaster;
import com.bcits.bfm.model.WTRateMaster;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.accountsManagement.ElectricityLedgerService;
import com.bcits.bfm.service.accountsManagement.ElectricitySubLedgerService;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentCalcLineService;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentService;
import com.bcits.bfm.service.billingCollectionManagement.PaymentService;
import com.bcits.bfm.service.commonAreaMaintenance.CamConsolidationService;
import com.bcits.bfm.service.customerOccupancyManagement.AddressService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillLineItemService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillParameterService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.facilityManagement.BillingParameterMasterService;
import com.bcits.bfm.service.postInvoiceToTallyService.TallyInvoicePostService;
import com.bcits.bfm.service.postInvoiceToTallyServiceImpl.TallyAllInvoicePostServiceImpl;
import com.bcits.bfm.service.serviceMasterManagement.ServiceMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceParameterService;
import com.bcits.bfm.service.tariffManagement.ELRateMasterService;
import com.bcits.bfm.service.tariffManagement.ELRateSlabService;
import com.bcits.bfm.service.tariffManagement.ELTariffMasterService;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.bcits.bfm.service.transactionMaster.InterestSettingService;
import com.bcits.bfm.service.transactionMaster.TransactionMasterService;
import com.bcits.bfm.service.waterTariffManagement.WTRateSlabService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


@Controller
public class BillGenerationController {

	static Logger logger = LoggerFactory.getLogger(BillGenerationController.class);
    
	@PersistenceContext(unitName="bfm")
	protected EntityManager entityManager;

	@Autowired
	TariffCalculationService tariffCalculationService;
	
	@Autowired
	private TallyInvoicePostService tallyInvoicePostService;

	DateTimeCalender dateTimeCalender = new DateTimeCalender();

	@Autowired
	TariffCalculationController calculationController;

	@Autowired
	ElectricityBillsService electricityBillsService;

	@Autowired
	ElectricityBillLineItemService electricityBillLineItemService;

	@Autowired
	BillingParameterMasterService billingParameterMasterService;

	@Autowired
	ElectricityBillParameterService billParameterService;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	TariffCalculationService calculationService;

	@Autowired
	ElectricityLedgerService electricityLedgerService;

	@Autowired
	ElectricitySubLedgerService electricitySubLedgerService;

	@Autowired
	ServiceParameterService serviceParameterService;

	@Autowired
	AdjustmentService adjustmentService;

	@Autowired
	AdjustmentCalcLineService adjustmentCalcLineService;

	@Autowired
	AccountService accountService;

	@Autowired
	ELRateSlabService elRateSlabService;

	@Autowired
	WTRateSlabService wtRateSlabService;

	@Autowired
	InterestSettingService interestSettingService;

	@Autowired
	ELRateMasterService rateMasterService;

	@Autowired
	ELRateSlabService rateSlabService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AddressService addressService;

	@Autowired
	private TransactionMasterService transactionMasterService;

	/*@Autowired
	private TallyInvoicePostService tallyInvoicePostService;*/

	@Autowired
	private CamConsolidationService camConsolidationService;
	
	@Autowired
	private ELTariffMasterService elTariffMasterService;
	
	@Autowired
	private BillController billController;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private TallyAllInvoicePostServiceImpl tallyAllInvoicePostServiceImpl;
	
	@Autowired
   CAMBillsController cambilcontroller;
	

	@RequestMapping(value = "/billGeneration", method = RequestMethod.GET)
	public String getRateDetail(ModelMap model, HttpServletRequest request) {
		model.addAttribute("rateName", new String[] { "EC", "FC" });
		return "billGeneration/billGeneration";
	}

	@RequestMapping(value = "/bills/commonFilterForAccountNumbersUrl", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> commonFilterForAccountNumbersUrl() {
		return electricityBillsService.commonFilterForAccountNumbersUrl();
	}

	@RequestMapping(value = "/electricityBills/searcchByMonth/{month}", method = RequestMethod.POST)
	public @ResponseBody List<?> searcchByMonth(@PathVariable String month,HttpServletRequest req) {
		logger.info("search process pay roll for particular month -- "+month);
		List<?> bills = new ArrayList<>();
		try {
			Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
			 bills=electricityBillsService.readBillsMonthWise(monthToShow);
		} catch (ParseException e) {
			logger.info("Error while parsing date");
			e.printStackTrace();
		}
		return bills;
	}
	
	@RequestMapping(value = "/bills/getPersonListForFileter", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readPersonNames() {
		logger.info("In person data filter method");
		List<?> accountPersonList = electricityBillsService
				.findPersonForFilters();
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

	@RequestMapping(value = "/bill/getServiceName", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<?> readTariffName(HttpServletRequest req) {
		int accountId = Integer.parseInt(req.getParameter("accountId"));
		logger.info("accountId" + accountId);
		List<TreeMap<String, Object>> result = new ArrayList<TreeMap<String, Object>>();
		for (final Object[] tariffcal : tariffCalculationService
				.getServiceName(accountId)) {
			result.add(new TreeMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{
					put("serviceMasterId", (Integer) tariffcal[0]);
					put("typeOfService", (String) tariffcal[1]);
				}
			});
		}
		return result;

	}

	@RequestMapping(value = "/bill/getPropertyNo", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<?> getPropertyId(HttpServletRequest req) {
		int blockId = Integer.parseInt(req.getParameter("blockId"));

		return tariffCalculationService.findPropertyNo(blockId);
	}

	@RequestMapping(value = "/bill/getBillDate", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody
	List<?> getBillDate(HttpServletRequest req, HttpServletResponse response)
			throws ParseException {
		PrintWriter out;
		Date previousBillDate = new SimpleDateFormat("dd/MM/yyyy").parse(req.getParameter("endPicker"));
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);

		int month1 = cal.get(Calendar.MONTH);
		int month = month1 + 1;
		int year = cal.get(Calendar.YEAR);
		String typeOfService = req.getParameter("rateName");
		int accountid = Integer.parseInt(req.getParameter("accountId"));

		String monthString = new DateFormatSymbols().getMonths()[month - 1];
		List<Object> list = tariffCalculationService.getBillOnDate(month, year,typeOfService, accountid);
		logger.info("" + list.size());
		if (list.size() > 0) {
			for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
				Object[] values = (Object[]) iterator.next();
				logger.info((String) values[0] + "type of service"+ (String) values[1] + "" + (String) values[2] + ""	+ (Double) values[3]);
				try {
					out = response.getWriter();
					out.write("Bill of " + (String) values[0]
							+ " for Account No " + (String) values[1] + " of "
							+ monthString + " has been already Generated.\n"
							+ "Reference Bill No.-" + (String) values[2]
							+ " Bill Amount= " + (Double) values[3]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				out = response.getWriter();
				out.write("calculate");

			} catch (IOException e) {

				e.printStackTrace();
			}

			logger.info("Bill Has Not bee Calculated");
		}

		return null;
	}

	@RequestMapping(value = "/bill/accountNumberAutocomplete", method = RequestMethod.GET)
	public @ResponseBody
	List<?> accountNumberAutocomplete() {
		return tariffCalculationService.getAllAccuntNumbers();
	}

	@RequestMapping(value = "/bill/getTodUnits", method = RequestMethod.GET)
	public @ResponseBody
	List<?> geTTodUnits(HttpServletRequest request, HttpServletResponse response) {

		Integer tod1 = Integer.parseInt(request.getParameter("todval1"));
		Integer todval2 = Integer.parseInt(request.getParameter("todval2"));
		Integer todval3 = Integer.parseInt(request.getParameter("todval3"));
		Integer unit = Integer.parseInt(request.getParameter("unit"));

		int todunit = (tod1 + todval2 + todval3);

		PrintWriter out;
		try {
			out = response.getWriter();
			if (todunit < unit) {
				out.println("Less");
			} else if (todunit > unit) {
				out.println("More");
			} else {
				out.println("Equal");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/bill/getBillStatus", method = RequestMethod.GET)
	public @ResponseBody
	/* List<Map<String, Object>> */Map<Object, Object> getDetailsOnNodeId(
			HttpServletRequest request, HttpServletResponse response) {
		String serviceType = request.getParameter("rateName");
		Integer accountId = Integer.parseInt(request.getParameter("billId"));
		logger.info("request.getParameter " + request.getParameter("serviceId"));
		Integer seviceId = Integer.parseInt(request.getParameter("serviceId"));

		return tariffCalculationService.getServiceStatusCheck(accountId,serviceType, seviceId);

	}

	@RequestMapping(value = "/bill/getCamPreviousStatus", method = RequestMethod.GET)
	public @ResponseBody
	String getCamBillStatus(HttpServletRequest request,
			HttpServletResponse response) {
		String status = tariffCalculationService.getCamPreviousBillstatus();
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(status);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;

	}

	@RequestMapping(value = "/bill/getCamPreviousStatusSpecific", method = RequestMethod.GET)
	public @ResponseBody
	String getCamBillSpecific(HttpServletRequest request,
			HttpServletResponse response) {
		Integer accountId = Integer.parseInt(request.getParameter("accountId"));
		String status = tariffCalculationService
				.getCamPreviousBillstatusSpecific(accountId);
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(status);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/bill/getpfApplicable", method = RequestMethod.GET)
	public @ResponseBody
	Map<Object, Object> getPfApplicable(HttpServletRequest request,
			HttpServletResponse response) {

		String serviceType = request.getParameter("rateName");
		Integer accountId = Integer.parseInt(request.getParameter("billId"));
		logger.info("request.getParameter " + request.getParameter("serviceId"));
		String typesofmeter = tariffCalculationService.getMeterType(accountId,
				serviceType);
		logger.info("::::::::typesofmeter::::::::::" + typesofmeter);
		PrintWriter out;
		if (typesofmeter.trim().equalsIgnoreCase("Trivector Meter")) {
			try {
				out = response.getWriter();
				out.write("Yes");
			} catch (IOException e) {

				e.printStackTrace();
			}
		} else {
			try {
				out = response.getWriter();
				out.write("No");
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/bill/getMeterChangeReading", method = RequestMethod.GET)
	public @ResponseBody
	Map<Object, Object> getMeterChangeReading(HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		String presentMeterStaus = request.getParameter("presentStaus");
		Date currentBillDate = new SimpleDateFormat("dd/MM/yyyy").parse(request
				.getParameter("presentbilldate"));
		Date previousBillDAte = new SimpleDateFormat("dd/MM/yyyy")
				.parse(request.getParameter("previousbilldate"));
		String strDate = new SimpleDateFormat("YYYY-MM-dd")
				.format(previousBillDAte);
		String pesentDate = new SimpleDateFormat("YYYY-MM-dd")
				.format(currentBillDate);
		float previousReding = Float.parseFloat(request
				.getParameter("previousReading"));
		float presentReading = Float.parseFloat(request
				.getParameter("presentReading"));
		String typeOfService = request.getParameter("rateName");
		int accountId = Integer.parseInt(request.getParameter("accountId"));
		int serviceId = Integer.parseInt(request.getParameter("serviceId"));

		String meterchangeUnit = "Not generated";
		Map<Object, Object> list = tariffCalculationService.getNewMeterDetails(
				pesentDate, strDate, typeOfService, accountId, previousReding,
				presentReading);
		Map<Object, Object> units = new HashMap<>();
		for (Entry<Object, Object> map : list.entrySet()) {
			if (map.getKey().equals("Units")) {
				units.put(map.getKey(), map.getValue());
				logger.info("::::::::::map.getKey()::::::::::" + map.getKey()
						+ "::::::::::map.getValue::::::::::" + map.getValue());
			} else if (map.getKey().equals("meterConstant")) {
				units.put(map.getKey(), map.getValue());
			}
		}
		float avgUnit = getAverageUnit(currentBillDate, typeOfService,
				accountId, serviceId);
		units.put("avgUnit", avgUnit);
		
		return units;

	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "bill/getDGMeterChangeReading", method = RequestMethod.GET)
	public @ResponseBody
	Map<Object, Object> getDGMeterChangeReading(HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		int serviceId = Integer.parseInt(request.getParameter("serviceId"));
		Date currentBillDate = new SimpleDateFormat("dd/MM/yyyy").parse(request
				.getParameter("presentbilldate"));
		String pesentDate = new SimpleDateFormat("YYYY-MM-dd")
		.format(currentBillDate);
		Date previousBillDAte = new SimpleDateFormat("dd/MM/yyyy")
		.parse(request.getParameter("previousbilldate"));
		String strDate = new SimpleDateFormat("YYYY-MM-dd")
		.format(previousBillDAte);
		String typeOfService = request.getParameter("rateName");
		int accountId = Integer.parseInt(request.getParameter("accountId"));
		float presentreading=Float.parseFloat(request.getParameter("dgReading"));
	    float previousreading=Float.parseFloat(request.getParameter("previousDGReading"));
	    Map<Object, Object> list = tariffCalculationService.getDGNewMeterDetails(
				pesentDate, strDate, typeOfService, accountId, previousreading,
				presentreading);
	    Map<Object, Object> units = new HashMap<>();
		for (Entry<Object, Object> map : list.entrySet()) {
			if (map.getKey().equals("Units")) {
				units.put(map.getKey(), map.getValue());
				logger.info("::::::::::map.getKey()::::::::::" + map.getKey()
						+ "::::::::::map.getValue::::::::::" + map.getValue());
			} else if (map.getKey().equals("meterConstant")) {
				units.put(map.getKey(), map.getValue());
			}
		}
		float avgUnit = getAverageUnit(currentBillDate, typeOfService,
				accountId, serviceId);
		units.put("avgUnit", avgUnit);
		
		return units;
		

	}
	

	@RequestMapping(value = "/bill/getPreviousStatusAlert", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<Object> getPreviousStatusAler(HttpServletRequest request,
			HttpServletResponse response) {
		String serviceType = request.getParameter("rateName");
		Integer accountId = Integer.parseInt(request.getParameter("billId"));
		logger.info("request.getParameter " + request.getParameter("serviceId"));
		Integer seviceId = Integer.parseInt(request.getParameter("serviceId"));
		logger.info("serviceType" + serviceType + "accountId" + accountId
				+ "seviceId" + seviceId);
		Map<String, String> responseMap = tariffCalculationService
				.getPreviousStatusAlert(accountId, serviceType, seviceId);
		Set<Map.Entry<String, String>> entrySet = responseMap.entrySet();
		PrintWriter out;

		for (@SuppressWarnings("rawtypes")
		Entry entry : entrySet) {
			logger.info("key: " + entry.getKey() + " value: "
					+ entry.getValue());
			try {
				out = response.getWriter();
				out.write("" + entry.getValue() + "</br>");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@RequestMapping(value = "/bill/getTod", method = RequestMethod.GET)
	public @ResponseBody
	List<Object> getTodApplicable(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("In Get Tod Method");
		String serviceType = request.getParameter("rateText");
		Integer accountId = Integer.parseInt(request.getParameter("accountId"));
		List<Object> list = tariffCalculationService.todApplicable(serviceType,
				accountId);

		int x = 0;
		if (list.get(1) != null && list.get(0) != null) {
			if (list.get(1).equals("Yes") && list.get(0).equals("No")) {
				logger.info(":::::::::::Only DG Applicable::::::::::::: ");
				x = 1;
			} else if (list.get(1).equals("Yes") && list.get(0).equals("Yes")) {
				logger.info(":::::::::::Tod & DG Both Applicable::::::::::::: ");
				x = 2;
			} else if (list.get(1).equals("No") && list.get(0).equals("Yes")) {
				logger.info(":::::::::::only Tod Applicable ::::::::::::: ");
				x = 3;
			} else {
				x = 0;
			}

		}

		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(x);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/bill/getSolidWasteStatus", method = RequestMethod.GET)
	public @ResponseBody
	Map<Object, Object> getSoidWasteStatus(HttpServletRequest request,
			HttpServletResponse response) {

		String serviceType = request.getParameter("rateName");
		Integer accountId = Integer.parseInt(request.getParameter("billId"));
		Integer seviceId = Integer.parseInt(request.getParameter("serviceId"));

		LinkedList<Map<String, Object>> list = (LinkedList<Map<String, Object>>) tariffCalculationService
				.getServiceStatus(accountId, serviceType, seviceId);
		Date previousDate = (Date) (list.get(list.size() - 2));
		logger.info("previousDate" + previousDate);
		@SuppressWarnings("rawtypes")
		List lastDate = new ArrayList();
		lastDate.add(previousDate);
		return tariffCalculationService.getServiceStatusCheck(accountId,
				serviceType, seviceId);
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/bill/getAvgUnit", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public HashMap<Object, Object> getAvgunits(HttpServletRequest req,
			HttpServletResponse response, @RequestBody String body)
			throws ParseException, JSONException {
		logger.info("::::::::::::::::::: In Bill Generation Method :::::::::::::::::::::");

		String presentMeterStaus = req.getParameter("presentStaus");
		Date currentBillDate = new SimpleDateFormat("dd/MM/yyyy").parse(req
				.getParameter("presentbilldate"));

		String typeOfService = req.getParameter("rateName");
		int accountId = Integer.parseInt(req.getParameter("accountId"));
		int serviceID = Integer.parseInt(req.getParameter("serviceID"));

		Float uomValue = 0.0f;
		if (presentMeterStaus.equalsIgnoreCase("Door Lock")
				|| presentMeterStaus.equalsIgnoreCase("Meter Not Reading")
				|| presentMeterStaus.equalsIgnoreCase("Direct Connection")
				|| presentMeterStaus.equalsIgnoreCase("Burnt")
				|| presentMeterStaus.equalsIgnoreCase("Sticky")
				|| presentMeterStaus.equalsIgnoreCase("Meter Change")) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(currentBillDate);
			cal1.add(Calendar.YEAR, -1);
			Date lastYear = cal1.getTime();
			List<String> serviceParameterMasterValueList = serviceMasterService
					.getServiceParameterValueBasedOnMasterId(serviceID);
			if (serviceParameterMasterValueList.isEmpty()) {
				uomValue = getAvgUnit(currentBillDate, typeOfService, accountId);

			} else {
				logger.info("--------------- Average units present in service master -----------------");
				uomValue = Float.parseFloat(serviceParameterMasterValueList
						.get(0));
			}
		}
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(uomValue);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/bill/getAvgAlert", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public HashMap<Object, Object> getAvgAlert(HttpServletRequest req,
			HttpServletResponse response, @RequestBody String body)
			throws ParseException, JSONException {
		logger.info("::::::::::::::::::: In Bill Generation Method :::::::::::::::::::::");
		Date currentBillDate = new SimpleDateFormat("dd/MM/yyyy").parse(req
				.getParameter("presentbilldate"));
		String typeOfService = req.getParameter("rateName");
		int accountId = Integer.parseInt(req.getParameter("accountId"));
		float unit = Float.parseFloat(req.getParameter("unit"));
		Float uomValue = 0.0f;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(currentBillDate);
		cal1.add(Calendar.YEAR, -1);
		Date lastYear = cal1.getTime();
		int bvmId = billingParameterMasterService.getServicMasterId(
				typeOfService, "Units");
		List<ElectricityBillEntity> billIdList = electricityBillsService
				.getBillEntityByAccountId(accountId, typeOfService);
		List<String> listValus = new ArrayList<>();
		float avgUnits = 0;
		if (!billIdList.isEmpty()) {
			for (ElectricityBillEntity electricityBillEntity : billIdList) {
				listValus = billParameterService.getAverageUnits(
						electricityBillEntity.getElBillId(), bvmId, lastYear,
						currentBillDate);
				for (String string : listValus) {
					avgUnits += Float.parseFloat(string);
				}
			}
			uomValue = avgUnits / billIdList.size();

		}

		float uomPercentage = (25 * uomValue) / 100;
		float subNoormal = uomValue - uomPercentage;
		float abNoormal = uomValue + uomPercentage;

		String alert = "Normal";
		if (unit <= subNoormal) {
			logger.info("subNoormal calculation");
			alert = "You are Going To Generate Subnormal Bill";
		} else if (unit >= abNoormal) {
			logger.info("Abnormal calculation");
			alert = "You are Going to Generate Abnormal Bill";
		} else {
			logger.info("Normal Bill Calculation");
		}
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(alert);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/bill/getunits", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<Object> getunits(HttpServletRequest req,
			HttpServletResponse response, @RequestBody String body)
			throws ParseException, JSONException {
		logger.info("::::::::::::::::::: In Bill Generation Method :::::::::::::::::::::");

		Float averageUnits = 50f;
		Float presentUOMReading = 0f;
		Float uomValue = 0.0f;
		String presentMeterStaus = req.getParameter("presentStaus");
		Float previousUOMReading = Float.parseFloat(req
				.getParameter("previousReading"));
		Float meterConstant = Float.parseFloat(req.getParameter("meterFactor"));
		int serviceID = Integer.parseInt(req.getParameter("serviceId"));

		Date currentBillDate = new SimpleDateFormat("dd/MM/yyyy").parse(req
				.getParameter("presentbilldate"));
		String typeOfService = req.getParameter("rateName");
		int accountId = Integer.parseInt(req.getParameter("accountId"));
		if (req.getParameter("presentReading").equals("")
				|| req.getParameter("presentReading").equals(null)
				|| req.getParameter("presentReading").isEmpty()) {
			logger.info("::::::::::::::::::: Present reading is null ::::::::::::::::::");
			presentUOMReading = 0f;
		} else {
			logger.info("::::::::::::::::::: Present reading is Not null ::::::::::::::::::");
			presentUOMReading = Float.parseFloat(req
					.getParameter("presentReading"));
		}

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

		if (presentMeterStaus.equalsIgnoreCase("Door Lock")
				|| presentMeterStaus.equalsIgnoreCase("Meter Not Reading")
				|| presentMeterStaus.equalsIgnoreCase("Direct Connection")
				|| presentMeterStaus.equalsIgnoreCase("Burnt")
				|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
			logger.info(presentMeterStaus);
			uomValue = averageUnits;
		}
		Float avgUnit = 0.0f;
		List<String> serviceParameterMasterValueList = serviceMasterService
				.getServiceParameterValueBasedOnMasterId(serviceID);
		if (serviceParameterMasterValueList.isEmpty()) {
			avgUnit = getAvgUnit(currentBillDate, typeOfService, accountId);
			logger.info(":::::::::::::::avgUnit::::::::::::" + avgUnit);

		} else {

			avgUnit = Float.parseFloat(serviceParameterMasterValueList.get(0));
			logger.info(":::::::::::::::avgUnit::::::::::::" + avgUnit);
		}
		List<Object> list = new ArrayList<Object>();

		if (uomValue < 0) {
			list.add("Present");
		} else {
			list.add(uomValue);
		}
		list.add(avgUnit);

		return list;
	}

	@RequestMapping(value = "/bill/generateBill", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public HashMap<Object, Object> calculateTariff(HttpServletRequest req,
			HttpServletResponse response, @RequestBody String body)
			throws ParseException, JSONException {
		logger.info("::::::::::::::::::: In Bill Generation Method :::::::::::::::::::::");
		Float presentUOMReading = 0f;
		String presentMeterStaus = null;
		String previousMeterStaus = null;
		String bill = null;
		Float previousUOMReading = 0f;
		Float meterConstant = 0f;
		Date previousBillDate = null;
		Float uomValue;
		float pfReading = 0;
		float connectedLoad = 0;
		PrintWriter out = null;
		String meterchangeUnit = "Unit Changed:";
		try {
			String typeOfService = req.getParameter("rateName");
			int accountID = Integer.parseInt(req.getParameter("accountId"));
			Date currentBillDate = new SimpleDateFormat("dd/MM/yyyy").parse(req
					.getParameter("presentbilldate"));
			String billType = "Normal";

			Object dgApplicable = "No";
			Float previousDgreading = 0f;
			Float presentDgReading = 0f;
			Float dgmeterconstant = 0f;
			Double avgUnits = 0d;
			Integer avgCount = 0;
			List<Object> dglist = tariffCalculationService.todApplicable(typeOfService, accountID);
			logger.info("::::::::::dglist.get(1);::::::::" + dglist.get(1));
			dgApplicable = dglist.get(1);
			if (dgApplicable != null) {
				if (dgApplicable.equals("Yes")) {
					if (req.getParameter("previousDgreading").equals("")
							|| req.getParameter("previousDgreading").equals(
									null)
							|| req.getParameter("previousDgreading").isEmpty()) {
						logger.info("::::::::::::::::::: previousDgreading  reading is null ::::::::::::::::::");
						previousDgreading = 0f;
					} else {
						previousDgreading = Float.parseFloat(req
								.getParameter("previousDgreading"));
						logger.info("::::::::::::::::::: previousDgreading reading is Not null ::::::::::::::::::"
								+ previousDgreading);
					}

					if (req.getParameter("presentDgReading").equals("")
							|| req.getParameter("presentDgReading")
									.equals(null)
							|| req.getParameter("presentDgReading").isEmpty()) {
						logger.info("::::::::::::::::::: presentDgReading  reading is null ::::::::::::::::::");
						presentDgReading = 0f;
					} else {
						presentDgReading = Float.parseFloat(req
								.getParameter("presentDgReading"));
						logger.info("::::::::::::::::::: presentDgReading reading is Not null :::::::::::::::::: "
								+ presentDgReading);
					}
					if (req.getParameter("dgmeterconstant").equals("")
							|| req.getParameter("dgmeterconstant").equals(null)
							|| req.getParameter("dgmeterconstant").isEmpty()) {
						logger.info("::::::::::::::::::: dgmeterconstant is null ::::::::::::::::::");
						dgmeterconstant = 0f;
					} else {
						dgmeterconstant = Float.parseFloat(req
								.getParameter("dgmeterconstant"));
						logger.info("::::::::::::::::::: dgmeterconstant is Not null ::::::::::::::::::"
								+ dgmeterconstant);
					}
				}
			} else {
				dgApplicable = "NO";
			}

			if (req.getParameter("presentReading").equals("")
					|| req.getParameter("presentReading").equals(null)
					|| req.getParameter("presentReading").isEmpty()) {
				logger.info("::::::::::::::::::: Present Reading  reading is null ::::::::::::::::::");
				presentUOMReading = 0f;
			} else {
				logger.info("::::::::::::::::::: Present Reading reading is Not null ::::::::::::::::::");
				presentUOMReading = Float.parseFloat(req
						.getParameter("presentReading"));
			}

			if (req.getParameter("presentReading").equals("")
					|| req.getParameter("presentReading").equals(null)
					|| req.getParameter("presentReading").isEmpty()) {
				logger.info("::::::::::::::::::: units  reading is null ::::::::::::::::::");
				presentUOMReading = 0f;
			} else {
				uomValue = Float.parseFloat(req.getParameter("presentReading"));
				logger.info("::::::::::::::::::: units reading is Not null :::::::::::::::::: "
						+ uomValue);
			}

			// billType
			if (req.getParameter("previousStaus").equals("")
					|| req.getParameter("previousStaus").equals(null)
					|| req.getParameter("previousStaus").isEmpty()) {
				logger.info("::::::::::::::::::: Previous Staus reading is null ::::::::::::::::::");
			} else {
				logger.info("::::::::::::::::::: Previous Staus reading is Not null ::::::::::::::::::");
				previousMeterStaus = req.getParameter("previousStaus");
			}

			if (req.getParameter("postType").equals("")
					|| req.getParameter("postType").equals(null)
					|| req.getParameter("postType").isEmpty()) {
				logger.info("::::::::::::::::::: postType reading is null ::::::::::::::::::");
			} else {
				logger.info("::::::::::::::::::: postType reading is Not null ::::::::::::::::::");
				bill = req.getParameter("postType");
				if (bill.equalsIgnoreCase("Normal Bill")) {
					bill = "Bill";

				} else {
					bill = "Provisional Bill";
				}
			}

			if (req.getParameter("previousReading").equals("")
					|| req.getParameter("previousReading").equals(null)
					|| req.getParameter("previousReading").isEmpty()) {
				logger.info("::::::::::::::::::: Previous Reading reading is null ::::::::::::::::::");
			} else {
				logger.info("::::::::::::::::::: Previous Reading reading is Not null ::::::::::::::::::");
				previousUOMReading = Float.parseFloat(req
						.getParameter("previousReading"));
			}

			if (req.getParameter("meterFactor").equals("")
					|| req.getParameter("meterFactor").equals(null)
					|| req.getParameter("meterFactor").isEmpty()) {
				logger.info("::::::::::::::::::: Meter Factor reading is null ::::::::::::::::::");
			} else {
				logger.info("::::::::::::::::::: Meter Factor reading is Not null ::::::::::::::::::");
				meterConstant = Float.parseFloat(req
						.getParameter("meterFactor"));
			}

			if (req.getParameter("previousbillDate").equals("")
					|| req.getParameter("previousbillDate").equals(null)
					|| req.getParameter("previousbillDate").isEmpty()) {
				logger.info("::::::::::::::::::: Meter Factor reading is null ::::::::::::::::::");
			} else {
				logger.info("::::::::::::::::::: Meter Factor reading is Not null ::::::::::::::::::");
				previousBillDate = new SimpleDateFormat("dd/MM/yyyy").parse(req
						.getParameter("previousbillDate"));
			}

			if (req.getParameter("sWasteDate").equals("")
					|| req.getParameter("sWasteDate").equals(null)
					|| req.getParameter("sWasteDate").isEmpty()) {
				logger.info("::::::::::::::::::: Meter Factor reading is null ::::::::::::::::::");
			} else {
				logger.info("::::::::::::::::::: Meter Factor reading is Not null ::::::::::::::::::");
				previousBillDate = new SimpleDateFormat("dd/MM/yyyy").parse(req
						.getParameter("sWasteDate"));
			}

			if (req.getParameter("units").equals("")
					|| req.getParameter("units").equals(null)
					|| req.getParameter("units").isEmpty()) {
				logger.info("::::::::::::::::::: uomValue reading is null ::::::::::::::::::");
				uomValue = 0f;
			} else {
				logger.info("::::::::::::::::::: uomValue reading is Not null ::::::::::::::::::");
				uomValue = Float.parseFloat(req.getParameter("units"));
			}

			if (req.getParameter("pfReading").equals("")
					|| req.getParameter("pfReading").equals(null)
					|| req.getParameter("pfReading").isEmpty()) {
				logger.info("::::::::::::::::::: power factor reading is null ::::::::::::::::::");
			} else {
				logger.info("::::::::::::::::::: Power factor reading is Not null ::::::::::::::::::");
				pfReading = Float.parseFloat(req.getParameter("pfReading"));
			}

			if (req.getParameter("connectedLoad").equals("")
					|| req.getParameter("connectedLoad").equals(null)
					|| req.getParameter("connectedLoad").isEmpty()) {
				logger.info("::::::::::::::::::: Connected Load reading is null ::::::::::::::::::");
			} else {
				logger.info("::::::::::::::::::: Connected Load reading is Not null ::::::::::::::::::");
				connectedLoad = Float.parseFloat(req
						.getParameter("connectedLoad"));
			}

			if (req.getParameter("presentStaus").equals("")
					|| req.getParameter("presentStaus").equals(null)
					|| req.getParameter("presentStaus").isEmpty()) {
				logger.info("::::::::::::::::::: Present Staus reading is null ::::::::::::::::::");
			} else {
				logger.info("::::::::::::::::::: Present Staus reading is Not null ::::::::::::::::::");
				presentMeterStaus = req.getParameter("presentStaus");
				if (presentMeterStaus.equalsIgnoreCase("Meter Change")) {

					String strDate = new SimpleDateFormat("YYYY-MM-dd")
							.format(previousBillDate);
					String pesentDate = new SimpleDateFormat("YYYY-MM-dd")
							.format(currentBillDate);

					Map<Object, Object> hmap = tariffCalculationService
							.getNewMeterDetails(pesentDate, strDate,
									typeOfService, accountID,
									previousUOMReading, presentUOMReading);
					for (Entry<Object, Object> map : hmap.entrySet()) {
						if (map.getKey().equals("Units")) {

							logger.info("::::::::::map.getKey()::::::::::"
									+ map.getKey()
									+ "::::::::::map.getValue::::::::::"
									+ map.getValue());
						} else if (map.getKey().equals("meterConstant")) {
							logger.info("::::::::::map.getKey()::::::::::"
									+ map.getKey()
									+ "::::::::::map.getValue::::::::::"
									+ map.getValue());
						} else {
							logger.info("::::::::::map.getKey()::::::::::"
									+ map.getKey()
									+ "::::::::::map.getValue::::::::::"
									+ map.getValue());
							if (meterchangeUnit
									.equalsIgnoreCase("Unit Changed:")) {
								meterchangeUnit = meterchangeUnit
										+ map.getValue();
							} else {
								meterchangeUnit = meterchangeUnit.concat(",")
										+ map.getValue();
							}
							logger.info(":::::::::::meterchangeUnit::::::"
									+ meterchangeUnit);
						}
					}
					
				/*:::::::::::::::::::DG Meter Change Unit::::::::::::::::::::::*/
					
					/*Float previousDgreading = 0f;
					Float presentDgReading = 0f;
					Float dgmeterconstant = 0f;*/
					
					
					
					
					if (dgApplicable.equals("Yes")) {
						Map<Object, Object> hmapdg = tariffCalculationService.getDGNewMeterDetails(pesentDate, strDate,
										typeOfService, accountID,
										previousDgreading, presentDgReading);
						for (Entry<Object, Object> map : hmap.entrySet()) {
							if (map.getKey().equals("Units")) {

								logger.info("::::::::::map.getKey()::::::::::"
										+ map.getKey()
										+ "::::::::::map.getValue::::::::::"
										+ map.getValue());
							} else if (map.getKey().equals("meterConstant")) {
								logger.info("::::::::::map.getKey()::::::::::"
										+ map.getKey()
										+ "::::::::::map.getValue::::::::::"
										+ map.getValue());
							} else {
								logger.info("::::::::::map.getKey()::::::::::"
										+ map.getKey()
										+ "::::::::::map.getValue::::::::::"
										+ map.getValue());
								if (meterchangeUnit.equalsIgnoreCase("Unit Changed:")) {
									//meterchangeUnit = meterchangeUnit+ map.getValue();
								} else {
								/*	meterchangeUnit = meterchangeUnit
											.concat(",") + map.getValue();*/
								}
								logger.info(":::::::::::meterchangeUnit::::::"
										+ meterchangeUnit);
							}
						}
						
						
					}
					/*	End DG Meter Change Unit*/		
				}
			}

			LocalDate fromDate = new LocalDate(previousBillDate);
			LocalDate toDate = new LocalDate(currentBillDate);
			Integer billableDays = Days.daysBetween(fromDate, toDate).getDays();
			logger.info("::::::::::::: Total number of billable days are  :::::::::::::::::::: "
					+ billableDays);

			List<ELRateMaster> elRateMasterList = new ArrayList<>();
			List<WTRateMaster> waterRateMasterList = new ArrayList<>();
			List<GasRateMaster> gasRateMasterList = new ArrayList<>();
			List<SolidWasteRateMaster> solidWasterRateMasterList = new ArrayList<>();

			if (typeOfService.equalsIgnoreCase("Electricity")) {
				logger.info("::::::::::::: Electricity service type ::::::::::::: ");
				int tariffId = serviceMasterService
						.getServiceMasterByAccountNumber(accountID,
								typeOfService);
				logger.info(":::::::::::::" + typeOfService
						+ " Tairff id is ::::::::::::: " + tariffId);

				elRateMasterList = tariffCalculationService
						.getElectricityRateMaster(tariffId);

				List<Object> list = tariffCalculationService.todApplicable(
						typeOfService, accountID);
				Integer todT1 = 0;
				Integer todT2 = 0;
				Integer todT3 = 0;
				Float dgunit = 0f;
				Object todApplicable = list.get(0);
				if (todApplicable.equals("Yes")) {
					todT1 = Integer.parseInt(req.getParameter("presentTod1"));
					todT2 = Integer.parseInt(req.getParameter("presentTod2"));
					todT3 = Integer.parseInt(req.getParameter("presentTod3"));
					logger.info(" todT1::" + todT1 + " todT2::" + todT2
							+ " todT3::" + todT3);
				}

				else {
					logger.info(":::::::::Tod Is not Applicable::::::::::");
				}
				if (dgApplicable.equals("Yes")) {

					dgunit = Float.parseFloat(req.getParameter("dgUnit"));
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
						dgUomValue = dgUomValue
								- getMeterConstant(dgmeterconstant);
					}

					dgunit = dgUomValue;
					if (presentMeterStaus.equalsIgnoreCase("Meter Change")) {
						dgunit= Float.parseFloat(req.getParameter("dgUnit"));
					}
				}

				saveElectricityBillParameters(elRateMasterList, accountID,
						typeOfService, previousBillDate, currentBillDate,
						uomValue, billableDays, presentMeterStaus,
						previousMeterStaus, meterConstant, presentUOMReading,
						previousUOMReading, todT1, todT2, todT3, dgunit,
						todApplicable, dgApplicable, previousDgreading,
						presentDgReading, dgmeterconstant, avgUnits, avgCount,
						billType, pfReading, connectedLoad, bill,
						meterchangeUnit);
			}

			if (typeOfService.equalsIgnoreCase("Water")) {
				logger.info("::::::::::::: Water service type ::::::::::::: ");
				int waterTariffId = serviceMasterService.getWaterTariffId(
						accountID, typeOfService);
				logger.info(":::::::::::::" + typeOfService
						+ " Tairff id is ::::::::::::: " + waterTariffId);
				waterRateMasterList = tariffCalculationService
						.getWaterRateMaster(waterTariffId);
				saveWaterBillParameters(waterRateMasterList, accountID,
						typeOfService, previousBillDate, currentBillDate,
						uomValue, billableDays, presentMeterStaus,
						previousMeterStaus, meterConstant, presentUOMReading,
						previousUOMReading, avgUnits, avgCount, billType, bill,
						meterchangeUnit);
			}

			if (typeOfService.equalsIgnoreCase("Gas")) {
				logger.info("::::::::::::: Gas service type ::::::::::::: "
						+ accountID + " ---------------- " + typeOfService);
				int gasTariffId = serviceMasterService.getGasTariffId(
						accountID, typeOfService);
				logger.info(":::::::::::::" + typeOfService
						+ " Tairff id is ::::::::::::: " + gasTariffId);
				gasRateMasterList = tariffCalculationService
						.getGasRateMaster(gasTariffId);
				saveGasBillParameters(gasRateMasterList, accountID,
						typeOfService, previousBillDate, currentBillDate,
						uomValue, billableDays, presentMeterStaus,
						previousMeterStaus, meterConstant, presentUOMReading,
						previousUOMReading, avgUnits, avgCount, billType, bill,
						meterchangeUnit);
			}

			if (typeOfService.equalsIgnoreCase("Others")) {
				String postType = "Bill";
				logger.info("::::::::::::: Othres service type ::::::::::::: ");
				saveOthersBillParameters(accountID, typeOfService, postType,
						previousBillDate, currentBillDate, avgUnits, avgCount);
			}

			if (typeOfService.equalsIgnoreCase("Solid Waste")) {
				logger.info("::::::::::::: Solid Waste service type ::::::::::::: "+ accountID + "----- " + typeOfService);
				int solidWasteTariffId = serviceMasterService
						.getSolidWasteTariffId(accountID, typeOfService);
				logger.info(":::::::::::::" + typeOfService
						+ " Tairff id is ::::::::::::: " + solidWasteTariffId);
				solidWasterRateMasterList = tariffCalculationService
						.getSolidWasteRateMaster(solidWasteTariffId);
				saveSolidWasteBillParameters(solidWasterRateMasterList,
						accountID, typeOfService, previousBillDate,
						currentBillDate, presentUOMReading, billableDays,
						presentMeterStaus, previousMeterStaus, meterConstant,
						presentUOMReading, previousUOMReading, avgUnits,
						avgCount, billType, bill);
			}

			if (presentMeterStaus != null) {
				if (presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus
								.equalsIgnoreCase("Meter Not Reading")
						|| presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus.equalsIgnoreCase("Burnt")
						|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
					presentUOMReading = previousUOMReading;

					logger.info(":::::::::::::: Average consumption units  ::::::::::::: "
							+ uomValue);
				}
			}
		} catch (Exception e) {
			try {
				out = response.getWriter();
				out.write("There seems to be Some Problem Out there.Please Try Again");
				e.printStackTrace();
				return null;
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		try {
			out = response.getWriter();
			out.write("Your Bill Has Been Calculated");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/bill/raiseDepositDemand", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<Object> raiseDepositDemand(HttpServletRequest req,
			HttpServletResponse response, @RequestBody String body)
			throws ParseException, JSONException {
		String serviceName = req.getParameter("serviceName");
		int accountId = Integer.parseInt(req.getParameter("accountId"));
		String accountNo = req.getParameter("accountNo");
		int serviceID = Integer.parseInt(req.getParameter("serviceID"));
		Date presentdate = new SimpleDateFormat("dd/MM/yyyy").parse(req
				.getParameter("presentdate"));
		Date previousDate = new SimpleDateFormat("dd/MM/yyyy").parse(req
				.getParameter("previousdate"));

		logger.info(":::::::::serviceName::::::" + serviceName);
		logger.info(":::::::::accountId::::::" + accountId);
		logger.info(":::::::::accountNo::::::" + accountNo);
		logger.info(":::::::::presentdate::::::" + presentdate);
		logger.info(":::::::::serviceID::::::" + serviceID);
		logger.info(":::::::::previousDate::::::" + previousDate);

		String postType = "Deposit";
		saveDepositBill(accountId, serviceName, postType, previousDate,
				presentdate);

		PrintWriter out;
		try {
			out = response.getWriter();
			out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void saveDepositBill(int accountId, String serviceName,
			String postType, Date previousDate, Date presentdate) {
		ElectricityBillEntity billEntity = new ElectricityBillEntity();
		billEntity.setAccountId(accountId);
		billEntity.setArrearsAmount(0d);
		billEntity.setBillAmount(0d);
		billEntity.setAvgAmount(0d);
		billEntity.setBillType("Normal");
		billEntity
				.setNetAmount((double) (billEntity.getArrearsAmount() + billEntity
						.getBillAmount()));
		billEntity.setElBillDate(new Timestamp(new java.util.Date().getTime()));
		billEntity.setPostType(postType);
		billEntity.setFromDate(new java.sql.Date(previousDate.getTime()));
		billEntity.setBillDate(new java.sql.Date(presentdate.getTime()));
		Date billDueDate = addDays(presentdate, 15);
		billEntity.setBillDueDate(new java.sql.Date(billDueDate.getTime()));
		Calendar c = Calendar.getInstance();
		c.setTime(presentdate);
		c.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date billMonth = new java.sql.Date(c.getTime().getTime());
		logger.info("billMonth ::::::::::::::: " + billMonth);
		billEntity.setBillMonth(billMonth);
		billEntity.setStatus("Generated");
		billEntity.setCreatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		billEntity.setLastUpdatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		billEntity
				.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
		billEntity.setTypeOfService(serviceName);
		billEntity.setBillNo("BILL" + billParameterService.getSequencyNumber());
		logger.info("billEntity.getBillDueDate() ::::::::::::::: "
				+ billEntity.getBillDueDate());
		billEntity.setAvgCount(0);
		billEntity.setAvgUnits(0);
		electricityBillsService.save(billEntity);

	}

	@RequestMapping(value = "/bill/getpreviousdddate", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<Object> getpreviousdddate(HttpServletRequest req,
			HttpServletResponse response, @RequestBody String body)
			throws ParseException, JSONException {
		String serviceName = req.getParameter("serviceNmae");
		int accountId = Integer.parseInt(req.getParameter("accountId"));

		Date previousdate = tariffCalculationService.getPreviousddDate(
				accountId, serviceName);

		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(previousdate);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void saveOthersBillParameters(int accountID, String typeOfService,
			String postType, Date previousBillDate, Date currentBillDate,
			Double avgUnits, Integer avgCount) {
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		List<ElectricityBillLineItemEntity> billLineItemEntities = new ArrayList<>();
		int commonServiceId = serviceMasterService.getCommonServicesTariffId(
				accountID, typeOfService);
		logger.info("commonServiceId -------- " + commonServiceId);
		List<CommonServicesRateMaster> commonServicesRateMasters = tariffCalculationService
				.getCommonServicesRateMaster(commonServiceId);
		logger.info("commonServicesRateMasters.size() "
				+ commonServicesRateMasters.size());

		ElectricityBillEntity billEntityForArrears = new ElectricityBillEntity();
		billEntityForArrears.setAccountId(accountID);
		billEntityForArrears.setArrearsAmount(getLastBillArrearsAmount(
				"ARREARS", accountID, typeOfService));
		billEntityForArrears.setElBillDate(new java.sql.Timestamp(
				currentBillDate.getTime()));
		billEntityForArrears.setPostType("Bill");
		billEntityForArrears.setBillDate(new java.sql.Date(currentBillDate
				.getTime()));
		String configName = "Due Days";
		String status = "Active";
		String dueDays = electricityBillsService.getBillingConfigValue(configName,status);
		int days=0;
		if(dueDays!=null)
		{
			days = Integer.parseInt(dueDays);
		}
		
		Date billDueDate = addDays(currentBillDate, days);
		billEntityForArrears.setBillDueDate(new java.sql.Date(billDueDate
				.getTime()));
		Calendar c = Calendar.getInstance();
		c.setTime(currentBillDate);
		c.set(Calendar.DAY_OF_MONTH, 1);

		java.sql.Date billMonth = new java.sql.Date(c.getTime().getTime());
		logger.info("billMonth ::::::::::::::: " + billMonth);
		billEntityForArrears.setBillMonth(billMonth);
		billEntityForArrears.setStatus("Generated");
		billEntityForArrears.setCreatedBy((String) SessionData.getUserDetails()
				.get("userID"));
		billEntityForArrears.setLastUpdatedBy((String) SessionData
				.getUserDetails().get("userID"));
		billEntityForArrears.setLastUpdatedDT(new java.sql.Timestamp(new Date()
				.getTime()));
		billEntityForArrears.setTypeOfService(typeOfService);
		billEntityForArrears.setBillNo("BILL"
				+ billParameterService.getSequencyNumber());
		logger.info("billEntity.getBillDueDate() ::::::::::::::: "
				+ billEntityForArrears.getBillDueDate());
		storeArrears("ARREARS", billEntityForArrears);

		float interestOnArrearsAmount = 0;
		float interestOnTaxAmount = 0;
		ElectricityBillLineItemEntity interestOnTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity interestOnBillAmount = new ElectricityBillLineItemEntity();
		consolidatedBill = interestCalculationCommonService(accountID,
				typeOfService, previousBillDate, commonServicesRateMasters,
				currentBillDate);
		for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
			if (map.getKey().equals("interestOnArrearsAmount")) {
				interestOnArrearsAmount = interestOnArrearsAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnArrearsAmount);
				interestOnBillAmount.setTransactionCode("OT_INTEREST");
				interestOnBillAmount.setBalanceAmount(interestOnArrearsAmount);
				interestOnBillAmount.setCreditAmount(0);
				interestOnBillAmount.setDebitAmount(0);
				interestOnBillAmount.setStatus("Not Approved");
				interestOnBillAmount.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				if (interestOnArrearsAmount > 0) {
					billLineItemEntities.add(interestOnBillAmount);
				}
			}
			if (map.getKey().equals("interestOnTaxAmount")) {
				interestOnTaxAmount = interestOnTaxAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnTaxAmount);
				interestOnTax.setTransactionCode("OT_TAX_INTEREST");
				interestOnTax.setBalanceAmount(interestOnTaxAmount);
				interestOnTax.setCreditAmount(0);
				interestOnTax.setDebitAmount(0);
				interestOnTax.setStatus("Not Approved");
				interestOnTax.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				if (interestOnTaxAmount > 0) {
					billLineItemEntities.add(interestOnTax);
				}
			}
		}

		ElectricityBillEntity billEntity = new ElectricityBillEntity();
		billEntity.setAccountId(accountID);
		billEntity.setArrearsAmount(getLastBillArrearsAmount("ARREARS",
				accountID, typeOfService));
		billEntity.setBillAmount(0d);
		billEntity.setAvgAmount(0d);
		billEntity.setBillType("Normal");
		billEntity
				.setNetAmount((double) (billEntity.getArrearsAmount() + billEntity
						.getBillAmount()));
		billEntity.setElBillDate(new Timestamp(new java.util.Date().getTime()));
		billEntity.setPostType(postType);
		billEntity.setFromDate(new java.sql.Date(previousBillDate.getTime()));
		billEntity.setBillDate(new java.sql.Date(currentBillDate.getTime()));
		Date billDueDate1 = addDays(currentBillDate, 15);
		billEntity.setBillDueDate(new java.sql.Date(billDueDate1.getTime()));
		Calendar c1 = Calendar.getInstance();
		c1.setTime(currentBillDate);
		c1.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date billMonth1 = new java.sql.Date(c1.getTime().getTime());
		logger.info("billMonth ::::::::::::::: " + billMonth1);
		billEntity.setBillMonth(billMonth1);
		billEntity.setStatus("Generated");
		billEntity.setCreatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		billEntity.setLastUpdatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		billEntity
				.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
		billEntity.setTypeOfService(typeOfService);
		billEntity.setBillNo("BILL" + billParameterService.getSequencyNumber());
		billEntity.setAvgCount(avgCount);
		billEntity.setAvgUnits(avgUnits);
		logger.info("billEntity.getBillDueDate() ::::::::::::::: "
				+ billEntity.getBillDueDate());
		electricityBillsService.save(billEntity);

		ElectricityBillLineItemEntity roundOff = new ElectricityBillLineItemEntity();
		roundOff.setTransactionCode("OT_ROF");
		roundOff.setBalanceAmount(roundOff(
				(interestOnArrearsAmount + interestOnTaxAmount), 0)
				- (interestOnArrearsAmount + interestOnTaxAmount));
		roundOff.setCreditAmount(0);
		roundOff.setDebitAmount(0);
		roundOff.setStatus("Not Approved");
		roundOff.setCreatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		roundOff.setLastUpdatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		roundOff.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
		if (roundOff((interestOnArrearsAmount + interestOnTaxAmount), 0)
				- (interestOnArrearsAmount + interestOnTaxAmount) > 0) {
			billLineItemEntities.add(roundOff);
		}
		;

		for (ElectricityBillLineItemEntity commonServicesRateMaster : billLineItemEntities) {
			billEntity.setBillAmount(billEntity.getBillAmount()
					+ commonServicesRateMaster.getBalanceAmount());
			billEntity.setNetAmount(billEntity.getBillAmount()
					+ billEntity.getArrearsAmount());
			electricityBillsService.update(billEntity);
			commonServicesRateMaster.setElectricityBillEntity(billEntity);
			electricityBillLineItemService.save(commonServicesRateMaster);
		}
		Locale locale = null;
		billController.getBillDoc(billEntity.getElBillId(), locale,1);
	}

	HashMap<Object, Object> interestCalculationCommonService(int accountID,
			String typeOfService, Date previousBillDate,
			List<CommonServicesRateMaster> commonServicesRateMasters,
			Date currentBillDate) {
		logger.info("::::::::::::::: In interest on calculation Solid Waste :::::::::::::::");
		HashMap<Object, Object> intersts = new LinkedHashMap<>();
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float previousTaxAmount = 0f;
		float rateOfInterest = 0;
		float interestOnTax = 0;
		float interestOnArrearsAmount = 0f;
		float interestOnTaxAmount = 0f;

		float previousTaxAmountL = 0f;
		float rateOfInterestL = 0;
		float interestOnTaxL = 0;

		/*List<InterestSettingsEntity> interestSettingsEntities = interestSettingService.findAllData();
		String interestType = null;
		for (InterestSettingsEntity interestSettingsEntity : interestSettingsEntities) {
			interestType = interestSettingsEntity.getInterestBasedOn();
			logger.info(" Interest Calculation base on -->" + interestType);
		}*/
		String configName = "Interest Calculation";
		String status = "Active";
		String interestType = electricityBillsService.getBillingConfigValue(configName,status);
		logger.info("interestType ==================== "+interestType);

		if (interestType != null) {
			if (interestType.trim().equalsIgnoreCase("Daywise")) {

				ElectricityBillEntity billEntity = electricityBillsService
						.getPreviousBill(accountID, typeOfService,
								previousBillDate, "Bill");
				ElectricityLedgerEntity electricityLedgerEntity;
				if (billEntity != null) {
					String transactionCode = "OT";
					electricityLedgerEntity = electricityLedgerService
							.getPreviousLedger(accountID, previousBillDate,
									transactionCode);

					if (electricityLedgerEntity.getPostType().trim()
							.equalsIgnoreCase("PAYMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF "
								+ electricityLedgerEntity.getPostType());
						Date ledgerPostDate = new LocalDate(
								electricityLedgerEntity.getPostedBillDate())
								.toDateMidnight().toDate();
						Date billDueDate = new LocalDate(
								billEntity.getBillDueDate()).toDateMidnight()
								.toDate();
						if ((billDueDate.compareTo(ledgerPostDate)) == 0) {
							logger.info("======== payment done on same date of due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ billEntity.getArrearsAmount());
							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService.getPreviousLedgerPayments(accountID,previousBillDate,transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("OT_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"OT_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"OT_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (CommonServicesRateMaster commMaster : commonServicesRateMasters) {
											if (commMaster
													.getCsRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												consolidatedBill = calculationController
														.interstAmountCommonService(
																commMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (CommonServicesRateMaster commMaster : commonServicesRateMasters) {
											if (commMaster
													.getCsRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountCommonService(
																commMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}

							if (billEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						} else if (ledgerPostDate.compareTo(billDueDate) > 0) {
							logger.info("======== payment done on after due date ===============");

							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("OT_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"OT_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"OT_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (CommonServicesRateMaster commMaster : commonServicesRateMasters) {
											if (commMaster
													.getCsRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												logger.info("arrersAmount "
														+ arrersAmount);
												consolidatedBill = calculationController
														.interstAmountCommonService(
																commMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}
											}
										}

										for (CommonServicesRateMaster commMaster : commonServicesRateMasters) {
											if (commMaster
													.getCsRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountCommonService(
																commMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
							if (billEntity.getBillAmount() > 0) {
								logger.info("billEntity.getBillAmount() "
										+ billEntity.getBillAmount());
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getBillAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDueDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										for (ElectricityBillLineItemEntity billLineItemEntity : billEntity
												.getBillLineItemEntities()) {
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("OT_TAX")) {
												previousTaxAmount = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"OT_INTEREST")) {
												rateOfInterest = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"OT_TAX_INTEREST")) {
												interestOnTax = (float) billLineItemEntity
														.getBalanceAmount();
											}
										}
										logger.info("rateOfInterest "
												+ rateOfInterest);
										logger.info("previousTaxAmount "
												+ previousTaxAmount);
										logger.info("interestOnTax "
												+ interestOnTax);
										for (CommonServicesRateMaster commMaster : commonServicesRateMasters) {
											if (commMaster
													.getCsRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getBillAmount() - (rateOfInterest
														+ previousTaxAmount + interestOnTax));
												consolidatedBill = calculationController
														.interstAmountCommonService(
																commMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
										for (CommonServicesRateMaster commMaster : commonServicesRateMasters) {
											if (commMaster
													.getCsRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmount;
												consolidatedBill = calculationController
														.interstAmountCommonService(
																commMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
						} else {
							logger.info("======== payment done on before due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ billEntity.getArrearsAmount());
							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("OT_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"OT_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"OT_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (CommonServicesRateMaster commMaster : commonServicesRateMasters) {
											if (commMaster
													.getCsRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												logger.info("arrersAmount "
														+ arrersAmount);
												consolidatedBill = calculationController
														.interstAmountCommonService(
																commMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
										for (CommonServicesRateMaster commMaster : commonServicesRateMasters) {
											if (commMaster
													.getCsRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountCommonService(
																commMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
							if (billEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						}
					} else if (electricityLedgerEntity.getPostType().trim()
							.equalsIgnoreCase("ADJUSTMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF Else "
								+ electricityLedgerEntity.getPostType());
					} else {

						logger.info("electricityLedgerEntity.getPostType ---- in Else "
								+ electricityLedgerEntity.getPostType());
						if (billEntity.getArrearsAmount() > 0) {
							logger.info("billEntity.getArrearsAmount() "
									+ billEntity.getArrearsAmount());
							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(billEntity.getBillDate());
							Days d = Days.daysBetween(startDate, endDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "
									+ days);

							int lastLedgerId = electricityLedgerService
									.getLastArrearsLedgerBasedOnPayment(
											accountID, previousBillDate,
											transactionCode).intValue();
							ElectricityLedgerEntity ledgerEntity = electricityLedgerService
									.find(lastLedgerId);
							logger.info("ledgerEntity ---------- "
									+ ledgerEntity.getPostType());
							List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
									.findAllById1(ledgerEntity.getElLedgerid());
							for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("OT_TAX")) {
									previousTaxAmountL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("OT_INTEREST")) {
									rateOfInterestL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("OT_TAX_INTEREST")) {
									interestOnTaxL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
							}

							logger.info("rateOfInterestL " + rateOfInterestL);
							logger.info("previousTaxAmountL "
									+ previousTaxAmountL);
							logger.info("interestOnTaxL " + interestOnTaxL);

							for (CommonServicesRateMaster commMaster : commonServicesRateMasters) {
								if (commMaster.getCsRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (billEntity
											.getArrearsAmount() - (rateOfInterestL
											+ previousTaxAmountL + interestOnTaxL));
									logger.info("arrersAmount " + arrersAmount);
									consolidatedBill = calculationController
											.interstAmountCommonService(
													commMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							for (CommonServicesRateMaster commMaster : commonServicesRateMasters) {
								if (commMaster.getCsRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmountL;
									consolidatedBill = calculationController
											.interstAmountCommonService(
													commMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}
						}
						if (billEntity.getBillAmount() > 0) {
							logger.info("billEntity.getBillAmount() "
									+ billEntity.getBillAmount());

							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(billEntity.getBillDueDate());
							Days d = Days.daysBetween(endDate, startDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "
									+ days);
							for (ElectricityBillLineItemEntity billLineItemEntity : billEntity
									.getBillLineItemEntities()) {
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("OT_TAX")) {
									previousTaxAmount = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("OT_INTEREST")) {
									rateOfInterest = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim()
										.equalsIgnoreCase("OT_TAX_INTEREST")) {
									interestOnTax = (float) billLineItemEntity
											.getBalanceAmount();
								}
							}

							for (CommonServicesRateMaster commMaster : commonServicesRateMasters) {
								if (commMaster.getCsRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (billEntity
											.getBillAmount() - (rateOfInterest
											+ previousTaxAmount + interestOnTax));
									consolidatedBill = calculationController
											.interstAmountCommonService(
													commMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							logger.info("rateOfInterest " + rateOfInterest);
							logger.info("previousTaxAmount "
									+ previousTaxAmount);
							logger.info("interestOnTax " + interestOnTax);

							for (CommonServicesRateMaster commMaster : commonServicesRateMasters) {
								if (commMaster.getCsRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmount;
									consolidatedBill = calculationController
											.interstAmountCommonService(
													commMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}
								}
							}
						}
					}
				} else {
					logger.info("------------------ First Month Bill -----------------------");
				}
			} else {
				logger.info("--------------------- Interest calculation based on ----"
						+ interestType);
				ElectricityBillEntity previouBillEntity = electricityBillsService
						.getPreviousBill(accountID, typeOfService,
								previousBillDate, "Bill");
				ElectricityLedgerEntity electricityLedgerEntity;
				if (previouBillEntity != null) {
					String transactionCode = "OT";
					electricityLedgerEntity = electricityLedgerService
							.getPreviousLedger(accountID, previousBillDate,
									transactionCode);
					if (electricityLedgerEntity.getPostType().trim()
							.equalsIgnoreCase("PAYMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF "
								+ electricityLedgerEntity.getPostType());
						Date ledgerPostDate = new LocalDate(
								electricityLedgerEntity.getPostedBillDate())
								.toDateMidnight().toDate();
						Date billDueDate = new LocalDate(
								previouBillEntity.getBillDueDate())
								.toDateMidnight().toDate();
						if ((billDueDate.compareTo(ledgerPostDate)) == 0) {
							logger.info("======== payment done on same date of due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ previouBillEntity.getArrearsAmount());
							if (previouBillEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										/*
										 * startDate = new LocalDate(
										 * ledgerEntityPayments
										 * .getPostedBillDate());
										 */
										startDate = new LocalDate(
												currentBillDate);
										endDate = new LocalDate(
												previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("OT_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"OT_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"OT_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (CommonServicesRateMaster elRateMaster : commonServicesRateMasters) {
											if (elRateMaster
													.getCsRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (previouBillEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												consolidatedBill = calculationController
														.interstAmountCommonServiceForMonthWise(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (CommonServicesRateMaster elRateMaster : commonServicesRateMasters) {
											if (elRateMaster
													.getCsRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountCommonServiceForMonthWise(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}

							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						} else if (ledgerPostDate.compareTo(billDueDate) > 0) {
							logger.info("======== payment done on after due date ===============");

							if (previouBillEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										/*
										 * startDate = new LocalDate(
										 * ledgerEntityPayments
										 * .getPostedBillDate());
										 */
										startDate = new LocalDate(
												currentBillDate);
										endDate = new LocalDate(
												previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("OT_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"OT_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"OT_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (CommonServicesRateMaster elRateMaster : commonServicesRateMasters) {
											if (elRateMaster
													.getCsRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (previouBillEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												logger.info("arrersAmount "
														+ arrersAmount);
												consolidatedBill = calculationController
														.interstAmountCommonServiceForMonthWise(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (CommonServicesRateMaster elRateMaster : commonServicesRateMasters) {
											if (elRateMaster
													.getCsRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountCommonServiceForMonthWise(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("billEntity.getBillAmount() "
										+ previouBillEntity.getBillAmount());
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getBillAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;

										startDate = new LocalDate(
												currentBillDate);

										endDate = new LocalDate(
												previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										for (ElectricityBillLineItemEntity billLineItemEntity : previouBillEntity
												.getBillLineItemEntities()) {
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("OT_TAX")) {
												previousTaxAmount = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"OT_INTEREST")) {
												rateOfInterest = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"OT_TAX_INTEREST")) {
												interestOnTax = (float) billLineItemEntity
														.getBalanceAmount();
											}
										}
										logger.info("rateOfInterest "
												+ rateOfInterest);
										logger.info("previousTaxAmount "
												+ previousTaxAmount);
										logger.info("interestOnTax "
												+ interestOnTax);
										for (CommonServicesRateMaster elRateMaster : commonServicesRateMasters) {
											if (elRateMaster
													.getCsRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (previouBillEntity
														.getBillAmount() - (rateOfInterest
														+ previousTaxAmount + interestOnTax));
												consolidatedBill = calculationController
														.interstAmountCommonServiceForMonthWise(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
										for (CommonServicesRateMaster elRateMaster : commonServicesRateMasters) {
											if (elRateMaster
													.getCsRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmount;
												consolidatedBill = calculationController
														.interstAmountCommonServiceForMonthWise(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
						} else {
							logger.info("======== payment done on before due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ previouBillEntity.getArrearsAmount());
							if (previouBillEntity.getArrearsAmount() > 0) {/*
																			 * List<
																			 * ElectricityLedgerEntity
																			 * >
																			 * ledgerEntities
																			 * =
																			 * electricityLedgerService
																			 * .
																			 * getPreviousLedgerPayments
																			 * (
																			 * accountID
																			 * ,
																			 * previousBillDate
																			 * ,
																			 * transactionCode
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "========= Number of payments done in last month ============= "
																			 * +
																			 * ledgerEntities
																			 * .
																			 * size
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * for
																			 * (
																			 * ElectricityLedgerEntity
																			 * ledgerEntityPayments
																			 * :
																			 * ledgerEntities
																			 * )
																			 * {
																			 * if
																			 * (
																			 * previouBillEntity
																			 * .
																			 * getArrearsAmount
																			 * (
																			 * )
																			 * +
																			 * ledgerEntityPayments
																			 * .
																			 * getAmount
																			 * (
																			 * )
																			 * ==
																			 * 0
																			 * )
																			 * {
																			 * LocalDate
																			 * startDate
																			 * =
																			 * null
																			 * ;
																			 * LocalDate
																			 * endDate
																			 * =
																			 * null
																			 * ;
																			 * startDate
																			 * =
																			 * new
																			 * LocalDate
																			 * (
																			 * ledgerEntityPayments
																			 * .
																			 * getPostedBillDate
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * startDate
																			 * =
																			 * new
																			 * LocalDate
																			 * (
																			 * currentBillDate
																			 * )
																			 * ;
																			 * endDate
																			 * =
																			 * new
																			 * LocalDate
																			 * (
																			 * previouBillEntity
																			 * .
																			 * getBillDate
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * Days
																			 * d
																			 * =
																			 * Days
																			 * .
																			 * daysBetween
																			 * (
																			 * startDate
																			 * ,
																			 * endDate
																			 * )
																			 * ;
																			 * int
																			 * days
																			 * =
																			 * d
																			 * .
																			 * getDays
																			 * (
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "============ Arrears Amount interst days ========= "
																			 * +
																			 * days
																			 * )
																			 * ;
																			 * 
																			 * int
																			 * lastLedgerId
																			 * =
																			 * electricityLedgerService
																			 * .
																			 * getLastArrearsLedgerBasedOnPayment
																			 * (
																			 * accountID
																			 * ,
																			 * previousBillDate
																			 * ,
																			 * transactionCode
																			 * )
																			 * .
																			 * intValue
																			 * (
																			 * )
																			 * ;
																			 * ElectricityLedgerEntity
																			 * ledgerEntity
																			 * =
																			 * electricityLedgerService
																			 * .
																			 * find
																			 * (
																			 * lastLedgerId
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "ledgerEntity ---------- "
																			 * +
																			 * ledgerEntity
																			 * .
																			 * getPostType
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * List
																			 * <
																			 * ElectricitySubLedgerEntity
																			 * >
																			 * subLedgerEntities
																			 * =
																			 * electricitySubLedgerService
																			 * .
																			 * findAllById1
																			 * (
																			 * ledgerEntity
																			 * .
																			 * getElLedgerid
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * for
																			 * (
																			 * ElectricitySubLedgerEntity
																			 * electricitySubLedgerEntity
																			 * :
																			 * subLedgerEntities
																			 * )
																			 * {
																			 * if
																			 * (
																			 * electricitySubLedgerEntity
																			 * .
																			 * getTransactionCode
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "EL_TAX"
																			 * )
																			 * )
																			 * {
																			 * previousTaxAmountL
																			 * =
																			 * (
																			 * float
																			 * )
																			 * electricitySubLedgerEntity
																			 * .
																			 * getBalanceAmount
																			 * (
																			 * )
																			 * ;
																			 * }
																			 * if
																			 * (
																			 * electricitySubLedgerEntity
																			 * .
																			 * getTransactionCode
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "EL_INTEREST"
																			 * )
																			 * )
																			 * {
																			 * rateOfInterestL
																			 * =
																			 * (
																			 * float
																			 * )
																			 * electricitySubLedgerEntity
																			 * .
																			 * getBalanceAmount
																			 * (
																			 * )
																			 * ;
																			 * }
																			 * if
																			 * (
																			 * electricitySubLedgerEntity
																			 * .
																			 * getTransactionCode
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "EL_TAX_INTEREST"
																			 * )
																			 * )
																			 * {
																			 * interestOnTaxL
																			 * =
																			 * (
																			 * float
																			 * )
																			 * electricitySubLedgerEntity
																			 * .
																			 * getBalanceAmount
																			 * (
																			 * )
																			 * ;
																			 * }
																			 * }
																			 * 
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "rateOfInterestL "
																			 * +
																			 * rateOfInterestL
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "previousTaxAmountL "
																			 * +
																			 * previousTaxAmountL
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "interestOnTaxL "
																			 * +
																			 * interestOnTaxL
																			 * )
																			 * ;
																			 * 
																			 * for
																			 * (
																			 * CommonServicesRateMaster
																			 * elRateMaster
																			 * :
																			 * commonServicesRateMasters
																			 * )
																			 * {
																			 * if
																			 * (
																			 * elRateMaster
																			 * .
																			 * getCsRateName
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "Rate of interest"
																			 * )
																			 * )
																			 * {
																			 * float
																			 * arrersAmount
																			 * =
																			 * (
																			 * float
																			 * )
																			 * (
																			 * previouBillEntity
																			 * .
																			 * getArrearsAmount
																			 * (
																			 * )
																			 * -
																			 * (
																			 * rateOfInterestL
																			 * +
																			 * previousTaxAmountL
																			 * +
																			 * interestOnTaxL
																			 * )
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "arrersAmount "
																			 * +
																			 * arrersAmount
																			 * )
																			 * ;
																			 * consolidatedBill
																			 * =
																			 * calculationController
																			 * .
																			 * interstAmountCommonServiceForMonthWise
																			 * (
																			 * elRateMaster
																			 * ,
																			 * endDate
																			 * .
																			 * toDateMidnight
																			 * (
																			 * )
																			 * .
																			 * toDate
																			 * (
																			 * )
																			 * ,
																			 * startDate
																			 * .
																			 * toDateMidnight
																			 * (
																			 * )
																			 * .
																			 * toDate
																			 * (
																			 * )
																			 * ,
																			 * arrersAmount
																			 * )
																			 * ;
																			 * for
																			 * (
																			 * Entry
																			 * <
																			 * Object
																			 * ,
																			 * Object
																			 * >
																			 * map
																			 * :
																			 * consolidatedBill
																			 * .
																			 * entrySet
																			 * (
																			 * )
																			 * )
																			 * {
																			 * if
																			 * (
																			 * map
																			 * .
																			 * getKey
																			 * (
																			 * )
																			 * .
																			 * equals
																			 * (
																			 * "total"
																			 * )
																			 * )
																			 * {
																			 * interestOnArrearsAmount
																			 * =
																			 * interestOnArrearsAmount
																			 * +
																			 * (
																			 * Float
																			 * )
																			 * map
																			 * .
																			 * getValue
																			 * (
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * ":::::::::::::::: Interest  for Arrears Amount "
																			 * +
																			 * arrersAmount
																			 * +
																			 * " is "
																			 * +
																			 * (
																			 * Float
																			 * )
																			 * map
																			 * .
																			 * getValue
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * }
																			 * }
																			 * 
																			 * }
																			 * }
																			 * for
																			 * (
																			 * CommonServicesRateMaster
																			 * elRateMaster
																			 * :
																			 * commonServicesRateMasters
																			 * )
																			 * {
																			 * if
																			 * (
																			 * elRateMaster
																			 * .
																			 * getCsRateName
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "Rate of interest"
																			 * )
																			 * )
																			 * {
																			 * float
																			 * taxAmount
																			 * =
																			 * previousTaxAmountL
																			 * ;
																			 * consolidatedBill
																			 * =
																			 * calculationController
																			 * .
																			 * interstAmountCommonServiceForMonthWise
																			 * (
																			 * elRateMaster
																			 * ,
																			 * endDate
																			 * .
																			 * toDateMidnight
																			 * (
																			 * )
																			 * .
																			 * toDate
																			 * (
																			 * )
																			 * ,
																			 * startDate
																			 * .
																			 * toDateMidnight
																			 * (
																			 * )
																			 * .
																			 * toDate
																			 * (
																			 * )
																			 * ,
																			 * taxAmount
																			 * )
																			 * ;
																			 * for
																			 * (
																			 * Entry
																			 * <
																			 * Object
																			 * ,
																			 * Object
																			 * >
																			 * map
																			 * :
																			 * consolidatedBill
																			 * .
																			 * entrySet
																			 * (
																			 * )
																			 * )
																			 * {
																			 * if
																			 * (
																			 * map
																			 * .
																			 * getKey
																			 * (
																			 * )
																			 * .
																			 * equals
																			 * (
																			 * "total"
																			 * )
																			 * )
																			 * {
																			 * interestOnTaxAmount
																			 * =
																			 * interestOnTaxAmount
																			 * +
																			 * (
																			 * Float
																			 * )
																			 * map
																			 * .
																			 * getValue
																			 * (
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * ":::::::::::::::: Interest for Tax Amount "
																			 * +
																			 * taxAmount
																			 * +
																			 * " is "
																			 * +
																			 * (
																			 * Float
																			 * )
																			 * map
																			 * .
																			 * getValue
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * }
																			 * }
																			 * 
																			 * }
																			 * }
																			 * }
																			 * }
																			 */
							}
							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						}

					} else if (electricityLedgerEntity.getPostType().trim()
							.equalsIgnoreCase("ADJUSTMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF Else "
								+ electricityLedgerEntity.getPostType());
					} else {
						logger.info("electricityLedgerEntity.getPostType ---- in Else "
								+ electricityLedgerEntity.getPostType());
						if (previouBillEntity.getArrearsAmount() > 0) {
							logger.info("billEntity.getArrearsAmount() "
									+ previouBillEntity.getArrearsAmount());
							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(
									previouBillEntity.getBillDate());
							Days d = Days.daysBetween(startDate, endDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "
									+ days);

							int lastLedgerId = electricityLedgerService
									.getLastArrearsLedgerBasedOnPayment(
											accountID, previousBillDate,
											transactionCode).intValue();
							ElectricityLedgerEntity ledgerEntity = electricityLedgerService
									.find(lastLedgerId);
							logger.info("ledgerEntity ---------- "
									+ ledgerEntity.getPostType());
							List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
									.findAllById1(ledgerEntity.getElLedgerid());
							for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("OT_TAX")) {
									previousTaxAmountL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("OT_INTEREST")) {
									rateOfInterestL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("OT_TAX_INTEREST")) {
									interestOnTaxL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
							}

							logger.info("rateOfInterestL " + rateOfInterestL);
							logger.info("previousTaxAmountL "
									+ previousTaxAmountL);
							logger.info("interestOnTaxL " + interestOnTaxL);

							for (CommonServicesRateMaster elRateMaster : commonServicesRateMasters) {
								if (elRateMaster.getCsRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (previouBillEntity
											.getArrearsAmount() - (rateOfInterestL
											+ previousTaxAmountL + interestOnTaxL));
									logger.info("arrersAmount " + arrersAmount);
									consolidatedBill = calculationController
											.interstAmountCommonServiceForMonthWise(
													elRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							for (CommonServicesRateMaster elRateMaster : commonServicesRateMasters) {
								if (elRateMaster.getCsRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmountL;
									consolidatedBill = calculationController
											.interstAmountCommonServiceForMonthWise(
													elRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}
						}
						if (previouBillEntity.getBillAmount() > 0) {
							logger.info("billEntity.getBillAmount() "
									+ previouBillEntity.getBillAmount());

							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(
									previouBillEntity.getBillDate());
							Days d = Days.daysBetween(endDate, startDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "
									+ days);
							for (ElectricityBillLineItemEntity billLineItemEntity : previouBillEntity
									.getBillLineItemEntities()) {
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("OT_TAX")) {
									previousTaxAmount = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("OT_INTEREST")) {
									rateOfInterest = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim()
										.equalsIgnoreCase("OT_TAX_INTEREST")) {
									interestOnTax = (float) billLineItemEntity
											.getBalanceAmount();
								}
							}

							for (CommonServicesRateMaster elRateMaster : commonServicesRateMasters) {
								if (elRateMaster.getCsRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (previouBillEntity
											.getBillAmount() - (rateOfInterest
											+ previousTaxAmount + interestOnTax));
									consolidatedBill = calculationController
											.interstAmountCommonServiceForMonthWise(
													elRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							logger.info("rateOfInterest " + rateOfInterest);
							logger.info("previousTaxAmount "
									+ previousTaxAmount);
							logger.info("interestOnTax " + interestOnTax);

							for (CommonServicesRateMaster elRateMaster : commonServicesRateMasters) {
								if (elRateMaster.getCsRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmount;
									consolidatedBill = calculationController
											.interstAmountCommonServiceForMonthWise(
													elRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}
						}
					}
				} else {
					logger.info("------------------ First Month Bill -----------------------");
				}
			}

		}
		intersts.put("interestOnTaxAmount", interestOnTaxAmount);
		intersts.put("interestOnArrearsAmount", interestOnArrearsAmount);
		return intersts;
	}

	public HashMap<String, Object> saveSolidWasteBillParameters(
			List<SolidWasteRateMaster> solidWasterRateMasterList,
			int accountID, String typeOfService, Date previousBillDate,
			Date currentBillDate, Float uomValue, Integer billableDays,
			String presentMeterStaus, String previousMeterStaus,
			Float meterConstant, Float presentUOMReading,
			Float previousUOMReading, Double avgUnits, Integer avgCount,
			String billType, String postType) {

		float ecAmount = 0;
		float fcAmount = 0;
		float taxAmount = 0;
		float totalAmount;
		Float roundoffValue = 0f;
		float rateOfInterest = 0f;
		float arrearsAmount = 0f;
		Double avgAmount = 0d;
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();

		ElectricityBillLineItemEntity residentialCollection = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity solidWasteTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity solidWasteFixedCharges = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity roundOffBillLineItemEntity = new ElectricityBillLineItemEntity();

		Set<ElectricityBillLineItemEntity> billLineItemEntities = new LinkedHashSet<>();

		for (SolidWasteRateMaster solidWasteRateMaster : solidWasterRateMasterList) {
			logger.info("solidWasteRateMaster.getSolidWasteRateName() ---------------------- "+ solidWasteRateMaster.getSolidWasteRateName());
			if (solidWasteRateMaster.getSolidWasteRateName().equalsIgnoreCase("Residential Collection")) {
				logger.info(":::::::::::: In "+ solidWasteRateMaster.getSolidWasteRateName()+ "::::::::::");
				residentialCollection.setTransactionCode("SW_RES");
				ecAmount = ecAmount+ calculationController.solidWasteTariffAmount(
								solidWasteRateMaster, previousBillDate,
								currentBillDate, uomValue);
				residentialCollection.setBalanceAmount(ecAmount);

				residentialCollection.setCreditAmount(0);
				residentialCollection.setDebitAmount(0);
				residentialCollection.setStatus("Not Approved");
				residentialCollection.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				residentialCollection.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				residentialCollection.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(residentialCollection);
			}

			if (solidWasteRateMaster.getSolidWasteRateName().equalsIgnoreCase(
					"Fixed charges")) {
				LocalDate fromDate = new LocalDate(previousBillDate);
				LocalDate toDate = new LocalDate(currentBillDate);
				PeriodType monthDay = PeriodType.yearMonthDay()
						.withYearsRemoved();
				Period difference = new Period(fromDate, toDate, monthDay);
				float billableMonths = difference.getMonths();
				int daysAfterMonth = difference.getDays();
				Calendar cal = Calendar.getInstance();
				cal.setTime(previousBillDate);
				float daysToMonths = (float) daysAfterMonth
						/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				float netMonth = daysToMonths + billableMonths;
				netMonth = Math.round(netMonth * 100) / 100;
				logger.info(":::::::::::: In "
						+ solidWasteRateMaster.getSolidWasteRateName()
						+ "::::::::::");

				fcAmount = fcAmount
						+ calculationController.solidWasteTariffAmount(
								solidWasteRateMaster, previousBillDate,
								currentBillDate, 1 * netMonth);

				solidWasteFixedCharges.setTransactionCode("SW_FC");
				solidWasteFixedCharges.setBalanceAmount(fcAmount);
				solidWasteFixedCharges.setCreditAmount(0);
				solidWasteFixedCharges.setDebitAmount(0);
				solidWasteFixedCharges.setStatus("Not Approved");
				solidWasteFixedCharges.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				solidWasteFixedCharges.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				solidWasteFixedCharges.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(solidWasteFixedCharges);
			}
		}

		float interestOnArrearsAmount = 0;
		float interestOnTaxAmount = 0;
		ElectricityBillLineItemEntity interestOnTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity interestOnBillAmount = new ElectricityBillLineItemEntity();
		consolidatedBill = interestCalculationSolidWaste(accountID,
				typeOfService, previousBillDate, solidWasterRateMasterList,
				currentBillDate);
		for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
			if (map.getKey().equals("interestOnArrearsAmount")) {
				interestOnArrearsAmount = interestOnArrearsAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnArrearsAmount);
				interestOnBillAmount.setTransactionCode("SW_INTEREST");
				interestOnBillAmount.setBalanceAmount(interestOnArrearsAmount);
				interestOnBillAmount.setCreditAmount(0);
				interestOnBillAmount.setDebitAmount(0);
				interestOnBillAmount.setStatus("Not Approved");
				interestOnBillAmount.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnBillAmount);
			}
			if (map.getKey().equals("interestOnTaxAmount")) {
				interestOnTaxAmount = interestOnTaxAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnTaxAmount);
				interestOnTax.setTransactionCode("SW_TAX_INTEREST");
				interestOnTax.setBalanceAmount(interestOnTaxAmount);
				interestOnTax.setCreditAmount(0);
				interestOnTax.setDebitAmount(0);
				interestOnTax.setStatus("Not Approved");
				interestOnTax.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnTax);
			}
		}

		for (SolidWasteRateMaster solidWasteRateMaster : solidWasterRateMasterList) {
			if (solidWasteRateMaster.getSolidWasteRateName().equalsIgnoreCase(
					"Tax")) {
				logger.info(":::::::::::: In "
						+ solidWasteRateMaster.getSolidWasteRateName()
						+ "::::::::::");
				solidWasteTax.setTransactionCode("SW_TAX");
				taxAmount = taxAmount
						+ calculationController.solidWasteTariffAmount(
								solidWasteRateMaster, previousBillDate,
								currentBillDate, ecAmount);
				logger.info("taxAmount ::::::::: " + taxAmount);
				solidWasteTax.setBalanceAmount(taxAmount);
				solidWasteTax.setCreditAmount(0);
				solidWasteTax.setDebitAmount(0);
				solidWasteTax.setStatus("Not Approved");
				solidWasteTax.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				solidWasteTax.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				solidWasteTax.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(solidWasteTax);
			}
		}
		totalAmount = roundOff(ecAmount + fcAmount + taxAmount + arrearsAmount
				+ rateOfInterest + interestOnArrearsAmount
				+ interestOnTaxAmount, 0);
		roundoffValue = roundOff(
				totalAmount
						- (ecAmount + fcAmount + taxAmount + arrearsAmount
								+ rateOfInterest + interestOnArrearsAmount + interestOnTaxAmount),
				2);
		logger.info("::::::::::::::::::::::::: Round off value is ::::::::::::::: "
				+ roundoffValue);

		ElectricityBillEntity billEntity = new ElectricityBillEntity();
		billEntity.setAccountId(accountID);
		billEntity.setArrearsAmount(getLastBillArrearsAmount("ARREARS",
				accountID, typeOfService));
		billEntity.setBillAmount((double) totalAmount);
		billEntity
				.setNetAmount((double) (billEntity.getArrearsAmount() + totalAmount));
		billEntity.setElBillDate(new Timestamp(new java.util.Date().getTime()));
		billEntity.setPostType("Bill");
		billEntity.setBillDate(new java.sql.Date(currentBillDate.getTime()));
		String configName = "Due Days";
		String status = "Active";
		String dueDays = electricityBillsService.getBillingConfigValue(configName,status);
		int days=0;
		if(dueDays!=null)
		{
			days = Integer.parseInt(dueDays);
		}
		
		Date billDueDate = addDays(currentBillDate, days);
		billEntity.setBillDueDate(new java.sql.Date(billDueDate.getTime()));
		Calendar c = Calendar.getInstance();
		c.setTime(currentBillDate);
		c.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date billMonth = new java.sql.Date(c.getTime().getTime());
		logger.info("billMonth ::::::::::::::: " + billMonth);
		billEntity.setBillMonth(billMonth);
		billEntity.setStatus("Generated");
		billEntity.setCreatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		billEntity.setLastUpdatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		billEntity
				.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
		billEntity.setTypeOfService(typeOfService);
		billEntity.setBillNo("BILL" + billParameterService.getSequencyNumber());
		logger.info("billEntity.getBillDueDate() ::::::::::::::: "
				+ billEntity.getBillDueDate());

		roundOffBillLineItemEntity.setCreditAmount(0);
		roundOffBillLineItemEntity.setDebitAmount(0);
		roundOffBillLineItemEntity.setStatus("Not Approved");
		roundOffBillLineItemEntity.setCreatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
				new Date().getTime()));
		roundOffBillLineItemEntity.setBalanceAmount(roundoffValue);
		roundOffBillLineItemEntity.setTransactionCode("EL_ROF");
		billLineItemEntities.add(roundOffBillLineItemEntity);

		ElectricityBillEntity billEntity1 = saveBill(accountID, totalAmount,
				previousBillDate, currentBillDate, typeOfService,
				arrearsAmount, avgUnits, avgCount, billType, avgAmount,
				postType, 2);/*  rate Master Id harcoded */
		saveBillLineItems(billEntity1, billLineItemEntities, roundoffValue,
				arrearsAmount);
		saveBillSolidWasteParameters(billEntity1, solidWasterRateMasterList,
				accountID, typeOfService, previousBillDate, currentBillDate,
				uomValue, billableDays, presentMeterStaus, previousMeterStaus,
				meterConstant, presentUOMReading, previousUOMReading);

		Locale locale = null;
		billController.getBillDoc(billEntity1.getElBillId(), locale,1);
		HashMap<String, Object> bill = new HashMap<>();
		bill.put("accountID", accountID);
		bill.put("billId", billEntity1.getElBillId());
		bill.put("typeOfService", typeOfService);
		bill.put("previousBillDate", previousBillDate);
		bill.put("currentBillDate", currentBillDate);
		bill.put("uomValue", uomValue);
		bill.put("billableDays", billableDays);
		bill.put("presentMeterStaus", presentMeterStaus);
		bill.put("previousMeterStaus", previousMeterStaus);
		bill.put("meterConstant", meterConstant);
		bill.put("fcAmount  ", fcAmount);
		bill.put("arrearsAmount", billEntity1.getArrearsAmount());
		bill.put("fcAmount ", fcAmount);
		bill.put("taxAmount ", taxAmount);
		bill.put("interestOnTaxAmount  ", interestOnTaxAmount);
		bill.put("billDueDate  ", billEntity1.getBillDueDate());
		bill.put("interestOnArrearsAmount  ", interestOnArrearsAmount);
		bill.put("totalAmount", totalAmount);
		return bill;

	}

	private HashMap<Object, Object> interestCalculationSolidWaste(
			int accountID, String typeOfService, Date previousBillDate,
			List<SolidWasteRateMaster> solidWasterRateMasterList,
			Date currentBillDate) {

		logger.info("::::::::::::::: In interest on calculation Solid Waste :::::::::::::::");
		HashMap<Object, Object> intersts = new LinkedHashMap<>();
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float previousTaxAmount = 0f;
		float rateOfInterest = 0;
		float interestOnTax = 0;
		float interestOnArrearsAmount = 0f;
		float interestOnTaxAmount = 0f;

		float previousTaxAmountL = 0f;
		float rateOfInterestL = 0;
		float interestOnTaxL = 0;

		/*List<InterestSettingsEntity> interestSettingsEntities = interestSettingService.findAllData();
		String interestType = null;
		for (InterestSettingsEntity interestSettingsEntity : interestSettingsEntities) {
			interestType = interestSettingsEntity.getInterestBasedOn();
			logger.info(" Interest Calculation base on -->" + interestType);
		}*/
		String configName = "Interest Calculation";
		String status = "Active";
		String interestType = electricityBillsService.getBillingConfigValue(configName,status);
		logger.info("interestType ==================== "+interestType);

		if (interestType != null) {
			if (interestType.trim().equalsIgnoreCase("Daywise")) {
				ElectricityBillEntity billEntity = electricityBillsService
						.getPreviousBill(accountID, typeOfService,
								previousBillDate, "Bill");
				ElectricityLedgerEntity electricityLedgerEntity;
				if (billEntity != null) {
					String transactionCode = "SW";
					electricityLedgerEntity = electricityLedgerService
							.getPreviousLedger(accountID, previousBillDate,
									transactionCode);

					if (electricityLedgerEntity.getPostType().trim()
							.equalsIgnoreCase("PAYMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF "
								+ electricityLedgerEntity.getPostType());
						Date ledgerPostDate = new LocalDate(
								electricityLedgerEntity.getPostedBillDate())
								.toDateMidnight().toDate();
						Date billDueDate = new LocalDate(
								billEntity.getBillDueDate()).toDateMidnight()
								.toDate();
						if ((billDueDate.compareTo(ledgerPostDate)) == 0) {
							logger.info("======== payment done on same date of due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ billEntity.getArrearsAmount());
							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("SW_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"SW_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"SW_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (SolidWasteRateMaster solidWasteRateMaster : solidWasterRateMasterList) {
											if (solidWasteRateMaster
													.getSolidWasteRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												consolidatedBill = calculationController
														.interstAmountSolidWaste(
																solidWasteRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (SolidWasteRateMaster solidWasteRateMaster : solidWasterRateMasterList) {
											if (solidWasteRateMaster
													.getSolidWasteRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountSolidWaste(
																solidWasteRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}

							if (billEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						} else if (ledgerPostDate.compareTo(billDueDate) > 0) {
							logger.info("======== payment done on after due date ===============");

							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("SW_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"SW_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"SW_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (SolidWasteRateMaster solidWasteRateMaster : solidWasterRateMasterList) {
											if (solidWasteRateMaster
													.getSolidWasteRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												logger.info("arrersAmount "
														+ arrersAmount);
												consolidatedBill = calculationController
														.interstAmountSolidWaste(
																solidWasteRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (SolidWasteRateMaster solidWasteRateMaster : solidWasterRateMasterList) {
											if (solidWasteRateMaster
													.getSolidWasteRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountSolidWaste(
																solidWasteRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
							if (billEntity.getBillAmount() > 0) {
								logger.info("billEntity.getBillAmount() "
										+ billEntity.getBillAmount());
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getBillAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDueDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										for (ElectricityBillLineItemEntity billLineItemEntity : billEntity
												.getBillLineItemEntities()) {
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("SW_TAX")) {
												previousTaxAmount = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"SW_INTEREST")) {
												rateOfInterest = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"SW_TAX_INTEREST")) {
												interestOnTax = (float) billLineItemEntity
														.getBalanceAmount();
											}
										}
										logger.info("rateOfInterest "
												+ rateOfInterest);
										logger.info("previousTaxAmount "
												+ previousTaxAmount);
										logger.info("interestOnTax "
												+ interestOnTax);
										for (SolidWasteRateMaster solidWasteRateMaster : solidWasterRateMasterList) {
											if (solidWasteRateMaster
													.getSolidWasteRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getBillAmount() - (rateOfInterest
														+ previousTaxAmount + interestOnTax));
												consolidatedBill = calculationController
														.interstAmountSolidWaste(
																solidWasteRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
										for (SolidWasteRateMaster solidWasteRateMaster : solidWasterRateMasterList) {
											if (solidWasteRateMaster
													.getSolidWasteRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmount;
												consolidatedBill = calculationController
														.interstAmountSolidWaste(
																solidWasteRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
						} else {
							logger.info("======== payment done on before due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ billEntity.getArrearsAmount());
							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("SW_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"SW_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"SW_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (SolidWasteRateMaster solidWasteRateMaster : solidWasterRateMasterList) {
											if (solidWasteRateMaster
													.getSolidWasteRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												logger.info("arrersAmount "
														+ arrersAmount);
												consolidatedBill = calculationController
														.interstAmountSolidWaste(
																solidWasteRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
										for (SolidWasteRateMaster SolidWasteRateMaster : solidWasterRateMasterList) {
											if (SolidWasteRateMaster
													.getSolidWasteRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountSolidWaste(
																SolidWasteRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
							if (billEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						}
					} else if (electricityLedgerEntity.getPostType().trim()
							.equalsIgnoreCase("ADJUSTMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF Else "
								+ electricityLedgerEntity.getPostType());
					} else {

						logger.info("electricityLedgerEntity.getPostType ---- in Else "
								+ electricityLedgerEntity.getPostType());
						if (billEntity.getArrearsAmount() > 0) {
							logger.info("billEntity.getArrearsAmount() "
									+ billEntity.getArrearsAmount());
							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(billEntity.getBillDate());
							Days d = Days.daysBetween(startDate, endDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "
									+ days);

							if (electricityLedgerService
									.getLastArrearsLedgerBasedOnPayment(
											accountID, previousBillDate,
											transactionCode) != null) {
								int lastLedgerId = electricityLedgerService
										.getLastArrearsLedgerBasedOnPayment(
												accountID, previousBillDate,
												transactionCode).intValue();
								ElectricityLedgerEntity ledgerEntity = electricityLedgerService
										.find(lastLedgerId);

								logger.info("ledgerEntity ---------- "
										+ ledgerEntity.getPostType());
								List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
										.findAllById1(ledgerEntity
												.getElLedgerid());
								for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
									if (electricitySubLedgerEntity
											.getTransactionCode().trim()
											.equalsIgnoreCase("SW_TAX")) {
										previousTaxAmountL = (float) electricitySubLedgerEntity
												.getBalanceAmount();
									}
									if (electricitySubLedgerEntity
											.getTransactionCode().trim()
											.equalsIgnoreCase("SW_INTEREST")) {
										rateOfInterestL = (float) electricitySubLedgerEntity
												.getBalanceAmount();
									}
									if (electricitySubLedgerEntity
											.getTransactionCode()
											.trim()
											.equalsIgnoreCase("SW_TAX_INTEREST")) {
										interestOnTaxL = (float) electricitySubLedgerEntity
												.getBalanceAmount();
									}
								}
							}

							logger.info("rateOfInterestL " + rateOfInterestL);
							logger.info("previousTaxAmountL "
									+ previousTaxAmountL);
							logger.info("interestOnTaxL " + interestOnTaxL);

							for (SolidWasteRateMaster solidWasteRateMaster : solidWasterRateMasterList) {
								if (solidWasteRateMaster
										.getSolidWasteRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (billEntity
											.getArrearsAmount() - (rateOfInterestL
											+ previousTaxAmountL + interestOnTaxL));
									logger.info("arrersAmount " + arrersAmount);
									consolidatedBill = calculationController
											.interstAmountSolidWaste(
													solidWasteRateMaster,
													endDate.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							for (SolidWasteRateMaster solidWasteRateMaster : solidWasterRateMasterList) {
								if (solidWasteRateMaster
										.getSolidWasteRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmountL;
									consolidatedBill = calculationController
											.interstAmountSolidWaste(
													solidWasteRateMaster,
													endDate.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}
						}
						if (billEntity.getBillAmount() > 0) {
							logger.info("billEntity.getBillAmount() "
									+ billEntity.getBillAmount());

							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(billEntity.getBillDueDate());
							Days d = Days.daysBetween(endDate, startDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "
									+ days);
							for (ElectricityBillLineItemEntity billLineItemEntity : billEntity
									.getBillLineItemEntities()) {
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("SW_TAX")) {
									previousTaxAmount = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("SW_INTEREST")) {
									rateOfInterest = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim()
										.equalsIgnoreCase("SW_TAX_INTEREST")) {
									interestOnTax = (float) billLineItemEntity
											.getBalanceAmount();
								}
							}

							for (SolidWasteRateMaster solidWasteRateMaster : solidWasterRateMasterList) {
								if (solidWasteRateMaster
										.getSolidWasteRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (billEntity
											.getBillAmount() - (rateOfInterest
											+ previousTaxAmount + interestOnTax));
									consolidatedBill = calculationController
											.interstAmountSolidWaste(
													solidWasteRateMaster,
													endDate.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							logger.info("rateOfInterest " + rateOfInterest);
							logger.info("previousTaxAmount "
									+ previousTaxAmount);
							logger.info("interestOnTax " + interestOnTax);

							for (SolidWasteRateMaster solidWasteRateMaster : solidWasterRateMasterList) {
								if (solidWasteRateMaster
										.getSolidWasteRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmount;
									consolidatedBill = calculationController
											.interstAmountSolidWaste(
													solidWasteRateMaster,
													endDate.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}

									}
								}
							}
						}
					}
				} else {
					logger.info("------------------ First Month Bill -----------------------");
				}
			} else {
				logger.info("--------------------- Interest calculation based on ----"
						+ interestType);
				ElectricityBillEntity previouBillEntity = electricityBillsService
						.getPreviousBill(accountID, typeOfService,
								previousBillDate, "Bill");
				ElectricityLedgerEntity electricityLedgerEntity;
				if (previouBillEntity != null) {
					String transactionCode = "SW";
					electricityLedgerEntity = electricityLedgerService
							.getPreviousLedger(accountID, previousBillDate,
									transactionCode);
					if (electricityLedgerEntity.getPostType().trim()
							.equalsIgnoreCase("PAYMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF "
								+ electricityLedgerEntity.getPostType());
						Date ledgerPostDate = new LocalDate(
								electricityLedgerEntity.getPostedBillDate())
								.toDateMidnight().toDate();
						Date billDueDate = new LocalDate(
								previouBillEntity.getBillDueDate())
								.toDateMidnight().toDate();
						if ((billDueDate.compareTo(ledgerPostDate)) == 0) {
							logger.info("======== payment done on same date of due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ previouBillEntity.getArrearsAmount());
							if (previouBillEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										/*
										 * startDate = new LocalDate(
										 * ledgerEntityPayments
										 * .getPostedBillDate());
										 */
										startDate = new LocalDate(
												currentBillDate);
										endDate = new LocalDate(
												previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("SW_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"SW_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"SW_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (SolidWasteRateMaster swRateMaster : solidWasterRateMasterList) {
											if (swRateMaster
													.getSolidWasteRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (previouBillEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												consolidatedBill = calculationController
														.interstAmountSolidWasteForMonthWise(
																swRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (SolidWasteRateMaster swRateMaster : solidWasterRateMasterList) {
											if (swRateMaster
													.getSolidWasteRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountSolidWasteForMonthWise(
																swRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}

							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						} else if (ledgerPostDate.compareTo(billDueDate) > 0) {
							logger.info("======== payment done on after due date ===============");

							if (previouBillEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										/*
										 * startDate = new LocalDate(
										 * ledgerEntityPayments
										 * .getPostedBillDate());
										 */
										startDate = new LocalDate(
												currentBillDate);
										endDate = new LocalDate(
												previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("SW_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"SW_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"SW_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (SolidWasteRateMaster swRateMaster : solidWasterRateMasterList) {
											if (swRateMaster
													.getSolidWasteRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (previouBillEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												logger.info("arrersAmount "
														+ arrersAmount);
												consolidatedBill = calculationController
														.interstAmountSolidWasteForMonthWise(
																swRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (SolidWasteRateMaster swRateMaster : solidWasterRateMasterList) {
											if (swRateMaster
													.getSolidWasteRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountSolidWasteForMonthWise(
																swRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("billEntity.getBillAmount() "
										+ previouBillEntity.getBillAmount());
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getBillAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										/*
										 * startDate = new LocalDate(
										 * ledgerEntityPayments
										 * .getPostedBillDate());
										 */
										startDate = new LocalDate(
												currentBillDate);
										/*
										 * endDate = new LocalDate(
										 * billEntity.getBillDueDate());
										 */
										endDate = new LocalDate(
												previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										for (ElectricityBillLineItemEntity billLineItemEntity : previouBillEntity
												.getBillLineItemEntities()) {
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("SW_TAX")) {
												previousTaxAmount = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"SW_INTEREST")) {
												rateOfInterest = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"SW_TAX_INTEREST")) {
												interestOnTax = (float) billLineItemEntity
														.getBalanceAmount();
											}
										}
										logger.info("rateOfInterest "
												+ rateOfInterest);
										logger.info("previousTaxAmount "
												+ previousTaxAmount);
										logger.info("interestOnTax "
												+ interestOnTax);
										for (SolidWasteRateMaster swRateMaster : solidWasterRateMasterList) {
											if (swRateMaster
													.getSolidWasteRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (previouBillEntity
														.getBillAmount() - (rateOfInterest
														+ previousTaxAmount + interestOnTax));
												consolidatedBill = calculationController
														.interstAmountSolidWasteForMonthWise(
																swRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
										for (SolidWasteRateMaster swRateMaster : solidWasterRateMasterList) {
											if (swRateMaster
													.getSolidWasteRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmount;
												consolidatedBill = calculationController
														.interstAmountSolidWasteForMonthWise(
																swRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
						} else {
							logger.info("======== payment done on before due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ previouBillEntity.getArrearsAmount());
							if (previouBillEntity.getArrearsAmount() > 0) {/*
																			 * List<
																			 * ElectricityLedgerEntity
																			 * >
																			 * ledgerEntities
																			 * =
																			 * electricityLedgerService
																			 * .
																			 * getPreviousLedgerPayments
																			 * (
																			 * accountID
																			 * ,
																			 * previousBillDate
																			 * ,
																			 * transactionCode
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "========= Number of payments done in last month ============= "
																			 * +
																			 * ledgerEntities
																			 * .
																			 * size
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * for
																			 * (
																			 * ElectricityLedgerEntity
																			 * ledgerEntityPayments
																			 * :
																			 * ledgerEntities
																			 * )
																			 * {
																			 * if
																			 * (
																			 * previouBillEntity
																			 * .
																			 * getArrearsAmount
																			 * (
																			 * )
																			 * +
																			 * ledgerEntityPayments
																			 * .
																			 * getAmount
																			 * (
																			 * )
																			 * ==
																			 * 0
																			 * )
																			 * {
																			 * LocalDate
																			 * startDate
																			 * =
																			 * null
																			 * ;
																			 * LocalDate
																			 * endDate
																			 * =
																			 * null
																			 * ;
																			 * startDate
																			 * =
																			 * new
																			 * LocalDate
																			 * (
																			 * ledgerEntityPayments
																			 * .
																			 * getPostedBillDate
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * startDate
																			 * =
																			 * new
																			 * LocalDate
																			 * (
																			 * currentBillDate
																			 * )
																			 * ;
																			 * endDate
																			 * =
																			 * new
																			 * LocalDate
																			 * (
																			 * previouBillEntity
																			 * .
																			 * getBillDate
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * Days
																			 * d
																			 * =
																			 * Days
																			 * .
																			 * daysBetween
																			 * (
																			 * startDate
																			 * ,
																			 * endDate
																			 * )
																			 * ;
																			 * int
																			 * days
																			 * =
																			 * d
																			 * .
																			 * getDays
																			 * (
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "============ Arrears Amount interst days ========= "
																			 * +
																			 * days
																			 * )
																			 * ;
																			 * 
																			 * int
																			 * lastLedgerId
																			 * =
																			 * electricityLedgerService
																			 * .
																			 * getLastArrearsLedgerBasedOnPayment
																			 * (
																			 * accountID
																			 * ,
																			 * previousBillDate
																			 * ,
																			 * transactionCode
																			 * )
																			 * .
																			 * intValue
																			 * (
																			 * )
																			 * ;
																			 * ElectricityLedgerEntity
																			 * ledgerEntity
																			 * =
																			 * electricityLedgerService
																			 * .
																			 * find
																			 * (
																			 * lastLedgerId
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "ledgerEntity ---------- "
																			 * +
																			 * ledgerEntity
																			 * .
																			 * getPostType
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * List
																			 * <
																			 * ElectricitySubLedgerEntity
																			 * >
																			 * subLedgerEntities
																			 * =
																			 * electricitySubLedgerService
																			 * .
																			 * findAllById1
																			 * (
																			 * ledgerEntity
																			 * .
																			 * getElLedgerid
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * for
																			 * (
																			 * ElectricitySubLedgerEntity
																			 * electricitySubLedgerEntity
																			 * :
																			 * subLedgerEntities
																			 * )
																			 * {
																			 * if
																			 * (
																			 * electricitySubLedgerEntity
																			 * .
																			 * getTransactionCode
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "EL_TAX"
																			 * )
																			 * )
																			 * {
																			 * previousTaxAmountL
																			 * =
																			 * (
																			 * float
																			 * )
																			 * electricitySubLedgerEntity
																			 * .
																			 * getBalanceAmount
																			 * (
																			 * )
																			 * ;
																			 * }
																			 * if
																			 * (
																			 * electricitySubLedgerEntity
																			 * .
																			 * getTransactionCode
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "EL_INTEREST"
																			 * )
																			 * )
																			 * {
																			 * rateOfInterestL
																			 * =
																			 * (
																			 * float
																			 * )
																			 * electricitySubLedgerEntity
																			 * .
																			 * getBalanceAmount
																			 * (
																			 * )
																			 * ;
																			 * }
																			 * if
																			 * (
																			 * electricitySubLedgerEntity
																			 * .
																			 * getTransactionCode
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "EL_TAX_INTEREST"
																			 * )
																			 * )
																			 * {
																			 * interestOnTaxL
																			 * =
																			 * (
																			 * float
																			 * )
																			 * electricitySubLedgerEntity
																			 * .
																			 * getBalanceAmount
																			 * (
																			 * )
																			 * ;
																			 * }
																			 * }
																			 * 
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "rateOfInterestL "
																			 * +
																			 * rateOfInterestL
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "previousTaxAmountL "
																			 * +
																			 * previousTaxAmountL
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "interestOnTaxL "
																			 * +
																			 * interestOnTaxL
																			 * )
																			 * ;
																			 * 
																			 * for
																			 * (
																			 * SolidWasteRateMaster
																			 * swRateMaster
																			 * :
																			 * solidWasterRateMasterList
																			 * )
																			 * {
																			 * if
																			 * (
																			 * swRateMaster
																			 * .
																			 * getSolidWasteRateName
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "Rate of interest"
																			 * )
																			 * )
																			 * {
																			 * float
																			 * arrersAmount
																			 * =
																			 * (
																			 * float
																			 * )
																			 * (
																			 * previouBillEntity
																			 * .
																			 * getArrearsAmount
																			 * (
																			 * )
																			 * -
																			 * (
																			 * rateOfInterestL
																			 * +
																			 * previousTaxAmountL
																			 * +
																			 * interestOnTaxL
																			 * )
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "arrersAmount "
																			 * +
																			 * arrersAmount
																			 * )
																			 * ;
																			 * consolidatedBill
																			 * =
																			 * calculationController
																			 * .
																			 * interstAmountSolidWaste
																			 * (
																			 * swRateMaster
																			 * ,
																			 * endDate
																			 * .
																			 * toDateMidnight
																			 * (
																			 * )
																			 * .
																			 * toDate
																			 * (
																			 * )
																			 * ,
																			 * startDate
																			 * .
																			 * toDateMidnight
																			 * (
																			 * )
																			 * .
																			 * toDate
																			 * (
																			 * )
																			 * ,
																			 * arrersAmount
																			 * )
																			 * ;
																			 * for
																			 * (
																			 * Entry
																			 * <
																			 * Object
																			 * ,
																			 * Object
																			 * >
																			 * map
																			 * :
																			 * consolidatedBill
																			 * .
																			 * entrySet
																			 * (
																			 * )
																			 * )
																			 * {
																			 * if
																			 * (
																			 * map
																			 * .
																			 * getKey
																			 * (
																			 * )
																			 * .
																			 * equals
																			 * (
																			 * "total"
																			 * )
																			 * )
																			 * {
																			 * interestOnArrearsAmount
																			 * =
																			 * interestOnArrearsAmount
																			 * +
																			 * (
																			 * Float
																			 * )
																			 * map
																			 * .
																			 * getValue
																			 * (
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * ":::::::::::::::: Interest  for Arrears Amount "
																			 * +
																			 * arrersAmount
																			 * +
																			 * " is "
																			 * +
																			 * (
																			 * Float
																			 * )
																			 * map
																			 * .
																			 * getValue
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * }
																			 * }
																			 * 
																			 * }
																			 * }
																			 * for
																			 * (
																			 * SolidWasteRateMaster
																			 * swRateMaster
																			 * :
																			 * solidWasterRateMasterList
																			 * )
																			 * {
																			 * if
																			 * (
																			 * swRateMaster
																			 * .
																			 * getSolidWasteRateName
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "Rate of interest"
																			 * )
																			 * )
																			 * {
																			 * float
																			 * taxAmount
																			 * =
																			 * previousTaxAmountL
																			 * ;
																			 * consolidatedBill
																			 * =
																			 * calculationController
																			 * .
																			 * interstAmountSolidWaste
																			 * (
																			 * swRateMaster
																			 * ,
																			 * endDate
																			 * .
																			 * toDateMidnight
																			 * (
																			 * )
																			 * .
																			 * toDate
																			 * (
																			 * )
																			 * ,
																			 * startDate
																			 * .
																			 * toDateMidnight
																			 * (
																			 * )
																			 * .
																			 * toDate
																			 * (
																			 * )
																			 * ,
																			 * taxAmount
																			 * )
																			 * ;
																			 * for
																			 * (
																			 * Entry
																			 * <
																			 * Object
																			 * ,
																			 * Object
																			 * >
																			 * map
																			 * :
																			 * consolidatedBill
																			 * .
																			 * entrySet
																			 * (
																			 * )
																			 * )
																			 * {
																			 * if
																			 * (
																			 * map
																			 * .
																			 * getKey
																			 * (
																			 * )
																			 * .
																			 * equals
																			 * (
																			 * "total"
																			 * )
																			 * )
																			 * {
																			 * interestOnTaxAmount
																			 * =
																			 * interestOnTaxAmount
																			 * +
																			 * (
																			 * Float
																			 * )
																			 * map
																			 * .
																			 * getValue
																			 * (
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * ":::::::::::::::: Interest for Tax Amount "
																			 * +
																			 * taxAmount
																			 * +
																			 * " is "
																			 * +
																			 * (
																			 * Float
																			 * )
																			 * map
																			 * .
																			 * getValue
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * }
																			 * }
																			 * 
																			 * }
																			 * }
																			 * }
																			 * }
																			 */
							}
							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						}

					} else if (electricityLedgerEntity.getPostType().trim()
							.equalsIgnoreCase("ADJUSTMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF Else "
								+ electricityLedgerEntity.getPostType());
					} else {
						logger.info("electricityLedgerEntity.getPostType ---- in Else "
								+ electricityLedgerEntity.getPostType());
						if (previouBillEntity.getArrearsAmount() > 0) {
							logger.info("billEntity.getArrearsAmount() "
									+ previouBillEntity.getArrearsAmount());
							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(
									previouBillEntity.getBillDate());
							Days d = Days.daysBetween(startDate, endDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "
									+ days);

							int lastLedgerId = electricityLedgerService
									.getLastArrearsLedgerBasedOnPayment(
											accountID, previousBillDate,
											transactionCode).intValue();
							ElectricityLedgerEntity ledgerEntity = electricityLedgerService
									.find(lastLedgerId);
							logger.info("ledgerEntity ---------- "
									+ ledgerEntity.getPostType());
							List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
									.findAllById1(ledgerEntity.getElLedgerid());
							for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("SW_TAX")) {
									previousTaxAmountL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("SW_INTEREST")) {
									rateOfInterestL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("SW_TAX_INTEREST")) {
									interestOnTaxL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
							}

							logger.info("rateOfInterestL " + rateOfInterestL);
							logger.info("previousTaxAmountL "
									+ previousTaxAmountL);
							logger.info("interestOnTaxL " + interestOnTaxL);

							for (SolidWasteRateMaster swRateMaster : solidWasterRateMasterList) {
								if (swRateMaster.getSolidWasteRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (previouBillEntity
											.getArrearsAmount() - (rateOfInterestL
											+ previousTaxAmountL + interestOnTaxL));
									logger.info("arrersAmount " + arrersAmount);
									consolidatedBill = calculationController
											.interstAmountSolidWasteForMonthWise(
													swRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							for (SolidWasteRateMaster swRateMaster : solidWasterRateMasterList) {
								if (swRateMaster.getSolidWasteRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmountL;
									consolidatedBill = calculationController
											.interstAmountSolidWasteForMonthWise(
													swRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}
						}
						if (previouBillEntity.getBillAmount() > 0) {
							logger.info("billEntity.getBillAmount() "
									+ previouBillEntity.getBillAmount());

							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(
									previouBillEntity.getBillDate());
							Days d = Days.daysBetween(endDate, startDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "
									+ days);
							for (ElectricityBillLineItemEntity billLineItemEntity : previouBillEntity
									.getBillLineItemEntities()) {
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("SW_TAX")) {
									previousTaxAmount = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("SW_INTEREST")) {
									rateOfInterest = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim()
										.equalsIgnoreCase("SW_TAX_INTEREST")) {
									interestOnTax = (float) billLineItemEntity
											.getBalanceAmount();
								}
							}

							for (SolidWasteRateMaster swRateMaster : solidWasterRateMasterList) {
								if (swRateMaster.getSolidWasteRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (previouBillEntity
											.getBillAmount() - (rateOfInterest
											+ previousTaxAmount + interestOnTax));
									consolidatedBill = calculationController
											.interstAmountSolidWasteForMonthWise(
													swRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							logger.info("rateOfInterest " + rateOfInterest);
							logger.info("previousTaxAmount "
									+ previousTaxAmount);
							logger.info("interestOnTax " + interestOnTax);

							for (SolidWasteRateMaster swRateMaster : solidWasterRateMasterList) {
								if (swRateMaster.getSolidWasteRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmount;
									consolidatedBill = calculationController
											.interstAmountSolidWasteForMonthWise(
													swRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}
						}
					}
				} else {
					logger.info("------------------ First Month Bill -----------------------");
				}

			}
		}

		intersts.put("interestOnTaxAmount", interestOnTaxAmount);
		intersts.put("interestOnArrearsAmount", interestOnArrearsAmount);
		return intersts;

	}

	private void saveBillSolidWasteParameters(
			ElectricityBillEntity billEntity1,
			List<SolidWasteRateMaster> solidWasterRateMasterList,
			int accountID, String typeOfService, Date previousBillDate,
			Date currentBillDate, Float uomValue, Integer billableDays,
			String presentMeterStaus, String previousMeterStaus,
			Float meterConstant, Float presentUOMReading,
			Float previousUOMReading) {
		List<BillParameterMasterEntity> billParameterMasterEntitiesList = billingParameterMasterService
				.getBillParameterMasterByServiceType(typeOfService);
		logger.info("billParameterMasterEntitiesList ::::::::::: "
				+ billParameterMasterEntitiesList.size());

		for (BillParameterMasterEntity billParameterMasterEntity : billParameterMasterEntitiesList) {
			logger.info("Bill parameters servic type :::"
					+ billParameterMasterEntity.getServiceType());

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Number of days")) {
				ElectricityBillParametersEntity noOfDays = new ElectricityBillParametersEntity(
						"Integer", billableDays.toString(), "Active");
				noOfDays.setBillParameterMasterEntity(billParameterMasterEntity);
				noOfDays.setElectricityBillEntity(billEntity1);
				billParameterService.save(noOfDays);
			}

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Volume(KG)")) {
				ElectricityBillParametersEntity units = new ElectricityBillParametersEntity(
						"Integer", uomValue.toString(), "Active");
				units.setBillParameterMasterEntity(billParameterMasterEntity);
				units.setElectricityBillEntity(billEntity1);
				billParameterService.save(units);
			}
		}
	}

	public HashMap<String, Object> saveGasBillParameters(
			List<GasRateMaster> gasRateMasterList, int accountID,
			String typeOfService, Date previousBillDate, Date currentBillDate,
			Float uomValue, Integer billableDays, String presentMeterStaus,
			String previousMeterStaus, Float meterConstant,
			Float presentUOMReading, Float previousUOMReading, Double avgUnits,
			Integer avgCount, String billType, String postType,
			String meterchangeUnit) {

		float ecAmount = 0;
		float fcAmount = 0;
		float taxAmount = 0;
		float totalAmount;
		Float roundoffValue = 0f;
		float rateOfInterest = 0f;
		float arrearsAmount = 0f;
		float distributionLossAmount = 0f;
		Double avgAmount = 0d;
		String gaString = null;
		float lossDistributionUnit = 0.0f;
		ElectricityBillEntity billEntityForArrears = new ElectricityBillEntity();
		billEntityForArrears.setAccountId(accountID);
		billEntityForArrears.setArrearsAmount(getLastBillArrearsAmount(
				"ARREARS", accountID, typeOfService));
		billEntityForArrears.setElBillDate(new java.sql.Timestamp(
				currentBillDate.getTime()));
		billEntityForArrears.setPostType("Bill");
		billEntityForArrears.setBillDate(new java.sql.Date(currentBillDate
				.getTime()));
		String configName = "Due Days";
		String status = "Active";
		String dueDays = electricityBillsService.getBillingConfigValue(configName,status);
		int days=0;
		if(dueDays!=null)
		{
			days = Integer.parseInt(dueDays);
		}
		
		Date billDueDate = addDays(currentBillDate, days);
		billEntityForArrears.setBillDueDate(new java.sql.Date(billDueDate
				.getTime()));
		Calendar c = Calendar.getInstance();
		c.setTime(currentBillDate);
		c.set(Calendar.DAY_OF_MONTH, 1);

		java.sql.Date billMonth = new java.sql.Date(c.getTime().getTime());
		logger.info("billMonth ::::::::::::::: " + billMonth);
		billEntityForArrears.setBillMonth(billMonth);
		billEntityForArrears.setStatus("Generated");
		billEntityForArrears.setCreatedBy((String) SessionData.getUserDetails()
				.get("userID"));
		billEntityForArrears.setLastUpdatedBy((String) SessionData
				.getUserDetails().get("userID"));
		billEntityForArrears.setLastUpdatedDT(new java.sql.Timestamp(new Date()
				.getTime()));
		billEntityForArrears.setTypeOfService(typeOfService);
		billEntityForArrears.setBillNo("BILL"
				+ billParameterService.getSequencyNumber());
		logger.info("billEntity.getBillDueDate() ::::::::::::::: "
				+ billEntityForArrears.getBillDueDate());
		storeArrears("ARREARS", billEntityForArrears);

		ElectricityBillLineItemEntity gasDomestic = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity distributionLosses = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity gasFixedCharges = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity gasTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity roundOffBillLineItemEntity = new ElectricityBillLineItemEntity();

		Set<ElectricityBillLineItemEntity> billLineItemEntities = new LinkedHashSet<>();

		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		ElectricityBillEntity previousBillEntity = electricityBillsService
				.getPreviousBill(accountID, typeOfService, previousBillDate,
						"Bill");

		for (GasRateMaster gasRateMaster : gasRateMasterList) {
			if (gasRateMaster.getGasRateName().equalsIgnoreCase("Domestic")) {
				logger.info(":::::::::::: In " + gasRateMaster.getGasRateName()
						+ "::::::::::");
				lossDistributionUnit = tariffCalculationService
						.getdistributionLossUnit(previousBillDate, accountID);

				if (lossDistributionUnit != 0) {
					logger.info(":::::::::::lossDistributionUnit:::::"
							+ lossDistributionUnit);
					consolidatedBill = calculationController.gasTariffAmount(
							gasRateMaster, previousBillDate, currentBillDate,
							lossDistributionUnit);

					for (Entry<Object, Object> map : consolidatedBill
							.entrySet()) {
						if (map.getKey().equals("total")) {
							distributionLossAmount = (Float) map.getValue();
							logger.info("distributionLossAmount ------------------------------- IN Gas "+ distributionLossAmount);
						}
						if (map.getKey().equals("gaString")) {
							gaString = (String) map.getValue();
						}
					}
					distributionLosses.setTransactionCode("GA_LOSS");
					distributionLosses.setBalanceAmount(distributionLossAmount);
					distributionLosses.setCreditAmount(0);
					distributionLosses.setDebitAmount(0);
					distributionLosses.setStatus("Not Approved");
					distributionLosses.setCreatedBy((String) SessionData
							.getUserDetails().get("userID"));
					distributionLosses.setLastUpdatedBy((String) SessionData
							.getUserDetails().get("userID"));
					distributionLosses.setLastUpdatedDT(new java.sql.Timestamp(
							new Date().getTime()));
					billLineItemEntities.add(distributionLosses);
				}

				if (presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus
								.equalsIgnoreCase("Meter Not Reading")
						|| presentMeterStaus
								.equalsIgnoreCase("Direct Connection")
						|| presentMeterStaus.equalsIgnoreCase("Burnt")
						|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
					consolidatedBill = calculationController.gasTariffAmount(
							gasRateMaster, previousBillDate, currentBillDate,
							uomValue);
				} else {
					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCountGas(gasRateMaster,
											previousBillDate, currentBillDate,
											uomValue,
											previousBillEntity.getAvgCount());
						} else {
							consolidatedBill = calculationController
									.gasTariffAmount(gasRateMaster,
											previousBillDate, currentBillDate,
											uomValue);
						}
					} else {
						consolidatedBill = calculationController
								.gasTariffAmount(gasRateMaster,
										previousBillDate, currentBillDate,
										uomValue);
					}
				}
				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						ecAmount = (Float) map.getValue();
						logger.info("ecAmount ------------------------------- IN Gas "
								+ ecAmount);
					}
					if (map.getKey().equals("gaString")) {
						gaString = (String) map.getValue();
					}
				}
				gasDomestic.setTransactionCode("GA_DOM");
				gasDomestic.setBalanceAmount(ecAmount);
				gasDomestic.setCreditAmount(0);
				gasDomestic.setDebitAmount(0);
				gasDomestic.setStatus("Not Approved");
				gasDomestic.setCreatedBy((String) SessionData.getUserDetails()
						.get("userID"));
				gasDomestic.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				gasDomestic.setLastUpdatedDT(new java.sql.Timestamp(new Date()
						.getTime()));
				billLineItemEntities.add(gasDomestic);
			}

			if (gasRateMaster.getGasRateName()
					.equalsIgnoreCase("Fixed charges")) {
				logger.info(":::::::::::: In " + gasRateMaster.getGasRateName()
						+ "::::::::::");
				LocalDate fromDate = new LocalDate(previousBillDate);
				LocalDate toDate = new LocalDate(currentBillDate);
				PeriodType monthDay = PeriodType.yearMonthDay()
						.withYearsRemoved();
				Period difference = new Period(fromDate, toDate, monthDay);
				float billableMonths = difference.getMonths();
				int daysAfterMonth = difference.getDays();
				Calendar cal = Calendar.getInstance();
				cal.setTime(previousBillDate);
				float daysToMonths = (float) daysAfterMonth
						/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				float netMonth = daysToMonths + billableMonths;
				netMonth = Math.round(netMonth * 100) / 100;
				if (presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus
								.equalsIgnoreCase("Meter Not Reading")
						|| presentMeterStaus
								.equalsIgnoreCase("Direct Connection")
						|| presentMeterStaus.equalsIgnoreCase("Burnt")
						|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
					consolidatedBill = calculationController.gasTariffAmount(
							gasRateMaster, previousBillDate, currentBillDate,
							1 * netMonth);
				} else {
					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCountGas(gasRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth,
											previousBillEntity.getAvgCount());
						} else {
							consolidatedBill = calculationController
									.gasTariffAmount(gasRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth);
						}
					} else {
						consolidatedBill = calculationController
								.gasTariffAmount(gasRateMaster,
										previousBillDate, currentBillDate,
										1 * netMonth);
					}
				}
				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						fcAmount = (Float) map.getValue();
					}
				}
				gasFixedCharges.setTransactionCode("GA_FC");
				gasFixedCharges.setBalanceAmount(fcAmount);
				gasFixedCharges.setCreditAmount(0);
				gasFixedCharges.setDebitAmount(0);
				gasFixedCharges.setStatus("Not Approved");
				gasFixedCharges.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				gasFixedCharges.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				gasFixedCharges.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(gasFixedCharges);
			}

		}

		float interestOnArrearsAmount = 0;
		float interestOnTaxAmount = 0;
		ElectricityBillLineItemEntity interestOnTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity interestOnBillAmount = new ElectricityBillLineItemEntity();
		consolidatedBill = interestCalculationGas(accountID, typeOfService,
				previousBillDate, gasRateMasterList, currentBillDate);

		for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
			if (map.getKey().equals("interestOnArrearsAmount")) {
				interestOnArrearsAmount = interestOnArrearsAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnArrearsAmount);
				interestOnBillAmount.setTransactionCode("GA_INTEREST");
				interestOnBillAmount.setBalanceAmount(interestOnArrearsAmount);
				interestOnBillAmount.setCreditAmount(0);
				interestOnBillAmount.setDebitAmount(0);
				interestOnBillAmount.setStatus("Not Approved");
				interestOnBillAmount.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnBillAmount);
			}
			if (map.getKey().equals("interestOnTaxAmount")) {
				interestOnTaxAmount = interestOnTaxAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnTaxAmount);
				interestOnTax.setTransactionCode("GA_TAX_INTEREST");
				interestOnTax.setBalanceAmount(interestOnTaxAmount);
				interestOnTax.setCreditAmount(0);
				interestOnTax.setDebitAmount(0);
				interestOnTax.setStatus("Not Approved");
				interestOnTax.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnTax);
			}
		}

		for (GasRateMaster gasRateMaster : gasRateMasterList) {
			if (gasRateMaster.getGasRateName().equalsIgnoreCase("Tax")) {
				logger.info(":::::::::::: In " + gasRateMaster.getGasRateName()
						+ "::::::::::");
				gasTax.setTransactionCode("GA_TAX");
				consolidatedBill = calculationController.gasTariffAmount(
						gasRateMaster, previousBillDate, currentBillDate,
						(ecAmount));

				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						taxAmount = (Float) map.getValue();
					}
				}
				logger.info("taxAmount ::::::::: " + taxAmount);
				gasTax.setBalanceAmount(taxAmount);
				gasTax.setCreditAmount(0);
				gasTax.setDebitAmount(0);
				gasTax.setStatus("Not Approved");
				gasTax.setCreatedBy((String) SessionData.getUserDetails().get(
						"userID"));
				gasTax.setLastUpdatedBy((String) SessionData.getUserDetails()
						.get("userID"));
				gasTax.setLastUpdatedDT(new java.sql.Timestamp(new Date()
						.getTime()));
				billLineItemEntities.add(gasTax);
			}
		}

		totalAmount = roundOff(ecAmount + distributionLossAmount + fcAmount
				+ taxAmount + arrearsAmount + rateOfInterest
				+ interestOnArrearsAmount + interestOnTaxAmount, 0);
		roundoffValue = roundOff(totalAmount
				- (ecAmount + distributionLossAmount + taxAmount + fcAmount
						+ arrearsAmount + rateOfInterest
						+ interestOnArrearsAmount + interestOnTaxAmount), 2);
		logger.info("::::::::::::::::::::::::: Round off value is ::::::::::::::: "
				+ roundoffValue);

		roundOffBillLineItemEntity.setCreditAmount(0);
		roundOffBillLineItemEntity.setDebitAmount(0);
		roundOffBillLineItemEntity.setStatus("Not Approved");
		roundOffBillLineItemEntity.setCreatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
				new Date().getTime()));
		roundOffBillLineItemEntity.setBalanceAmount(roundoffValue);
		roundOffBillLineItemEntity.setTransactionCode("EL_ROF");
		billLineItemEntities.add(roundOffBillLineItemEntity);

		if (presentMeterStaus.equalsIgnoreCase("Door Lock")
				|| presentMeterStaus.equalsIgnoreCase("Meter Not Reading")
				|| presentMeterStaus.equalsIgnoreCase("Direct Connection")
				|| presentMeterStaus.equalsIgnoreCase("Burnt")
				|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
			logger.info("-------------- Average bill generation for gas -------------------------");
			presentUOMReading = previousUOMReading;
			ElectricityBillEntity billEntity1 = electricityBillsService
					.getPreviousBill(accountID, typeOfService,
							previousBillDate, "Bill");
			logger.info("billEntity1 ----------- " + billEntity1);
			if (billEntity1 != null) {
				avgUnits = billEntity1.getAvgUnits() + uomValue;
				avgCount = billEntity1.getAvgCount() + 1;
				billType = "Average";
			}
		} else {
			ElectricityBillEntity billEntity1 = electricityBillsService
					.getPreviousBill(accountID, typeOfService,
							previousBillDate, "Bill");
			List<ElectricityBillEntity> billEntities = new ArrayList<>();
			if (billEntity1 != null) {
				if (billEntity1.getAvgCount() >= 1) {
					List<BigDecimal> billIds = electricityBillsService
							.getLastAvgBills(accountID, typeOfService,
									previousBillDate, "Bill",
									billEntity1.getAvgCount());
					for (BigDecimal bigDecimal : billIds) {
						billEntities.add(electricityBillsService
								.find(bigDecimal.intValue()));
					}
				}

				for (ElectricityBillEntity electricityBillEntity : billEntities) {
					logger.info("------------- electricityBillEntity.getBillAmount() "
							+ electricityBillEntity.getBillAmount());
					avgAmount = avgAmount
							+ electricityBillEntity.getBillAmount();
					logger.info("--------------- avgAmount " + avgAmount);
				}
			}
		}

		ElectricityBillEntity billEntity1 = saveBill(accountID, totalAmount,
				previousBillDate, currentBillDate, typeOfService,
				arrearsAmount, avgUnits, avgCount, billType, avgAmount,
				postType,2);
		saveBillLineItems(billEntity1, billLineItemEntities, roundoffValue,
				arrearsAmount);
		saveBillGasParameters(billEntity1, gasRateMasterList, accountID,
				typeOfService, previousBillDate, currentBillDate, uomValue,
				billableDays, presentMeterStaus, previousMeterStaus,
				meterConstant, presentUOMReading, previousUOMReading, gaString,
				lossDistributionUnit, meterchangeUnit);

		Locale locale = null;
		billController.getBillDoc(billEntity1.getElBillId(), locale,1);
		HashMap<String, Object> bill = new HashMap<>();
		bill.put("billId", billEntity1.getElBillId());
		return bill;
	}

	private HashMap<Object, Object> interestCalculationGas(int accountID,
			String typeOfService, Date previousBillDate,
			List<GasRateMaster> gasRateMasterList, Date currentBillDate) {

		logger.info("::::::::::::::: In interest on calculation Gas :::::::::::::::");
		HashMap<Object, Object> intersts = new LinkedHashMap<>();
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float previousTaxAmount = 0f;
		float rateOfInterest = 0;
		float interestOnTax = 0;
		float interestOnArrearsAmount = 0f;
		float interestOnTaxAmount = 0f;

		float previousTaxAmountL = 0f;
		float rateOfInterestL = 0;
		float interestOnTaxL = 0;
/*
		List<InterestSettingsEntity> interestSettingsEntities = interestSettingService.findAllData();
		String interestType = null;
		for (InterestSettingsEntity interestSettingsEntity : interestSettingsEntities) {
			interestType = interestSettingsEntity.getInterestBasedOn();
			logger.info(" Interest Calculation base on -->" + interestType);
		}
*/
		String configName = "Interest Calculation";
		String status = "Active";
		String interestType = electricityBillsService.getBillingConfigValue(configName,status);
		logger.info("interestType ==================== "+interestType);
		if (interestType != null) {
			if (interestType.trim().equalsIgnoreCase("Daywise")) {
				ElectricityBillEntity billEntity = electricityBillsService
						.getPreviousBill(accountID, typeOfService,
								previousBillDate, "Bill");
				ElectricityLedgerEntity electricityLedgerEntity;
				if (billEntity != null) {
					String transactionCode = "GA";
					electricityLedgerEntity = electricityLedgerService
							.getPreviousLedger(accountID, previousBillDate,
									transactionCode);

					if (electricityLedgerEntity.getPostType().trim()
							.equalsIgnoreCase("PAYMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF "
								+ electricityLedgerEntity.getPostType());
						Date ledgerPostDate = new LocalDate(
								electricityLedgerEntity.getPostedBillDate())
								.toDateMidnight().toDate();
						Date billDueDate = new LocalDate(
								billEntity.getBillDueDate()).toDateMidnight()
								.toDate();
						if ((billDueDate.compareTo(ledgerPostDate)) == 0) {
							logger.info("======== payment done on same date of due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ billEntity.getArrearsAmount());
							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("GA_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"GA_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"GA_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (GasRateMaster gasRateMaster : gasRateMasterList) {
											if (gasRateMaster
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												consolidatedBill = calculationController
														.interstAmountGas(
																gasRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (GasRateMaster gasRateMaster : gasRateMasterList) {
											if (gasRateMaster
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountGas(
																gasRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}

							if (billEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						} else if (ledgerPostDate.compareTo(billDueDate) > 0) {
							logger.info("======== payment done on after due date ===============");

							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("GA_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"GA_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"GA_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (GasRateMaster gasRateMaster : gasRateMasterList) {
											if (gasRateMaster
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												logger.info("arrersAmount "
														+ arrersAmount);
												consolidatedBill = calculationController
														.interstAmountGas(
																gasRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (GasRateMaster gasRateMaster : gasRateMasterList) {
											if (gasRateMaster
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountGas(
																gasRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
							if (billEntity.getBillAmount() > 0) {
								logger.info("billEntity.getBillAmount() "
										+ billEntity.getBillAmount());
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getBillAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDueDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										for (ElectricityBillLineItemEntity billLineItemEntity : billEntity
												.getBillLineItemEntities()) {
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("GA_TAX")) {
												previousTaxAmount = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"GA_INTEREST")) {
												rateOfInterest = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"GA_TAX_INTEREST")) {
												interestOnTax = (float) billLineItemEntity
														.getBalanceAmount();
											}
										}
										logger.info("rateOfInterest "
												+ rateOfInterest);
										logger.info("previousTaxAmount "
												+ previousTaxAmount);
										logger.info("interestOnTax "
												+ interestOnTax);
										for (GasRateMaster gasRateMaster : gasRateMasterList) {
											if (gasRateMaster
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getBillAmount() - (rateOfInterest
														+ previousTaxAmount + interestOnTax));
												consolidatedBill = calculationController
														.interstAmountGas(
																gasRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
										for (GasRateMaster gasRateMaster : gasRateMasterList) {
											if (gasRateMaster
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmount;
												consolidatedBill = calculationController
														.interstAmountGas(
																gasRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
						} else {
							logger.info("======== payment done on before due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ billEntity.getArrearsAmount());
							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("GA_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"GA_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"GA_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (GasRateMaster gasRateMaster : gasRateMasterList) {
											if (gasRateMaster
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												logger.info("arrersAmount "
														+ arrersAmount);
												consolidatedBill = calculationController
														.interstAmountGas(
																gasRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
										for (GasRateMaster gasRateMaster : gasRateMasterList) {
											if (gasRateMaster
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountGas(
																gasRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
							if (billEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						}
					} else if (electricityLedgerEntity.getPostType().trim()
							.equalsIgnoreCase("ADJUSTMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF Else "
								+ electricityLedgerEntity.getPostType());
					} else {

						logger.info("electricityLedgerEntity.getPostType ---- in Else "
								+ electricityLedgerEntity.getPostType());
						if (billEntity.getArrearsAmount() > 0) {
							logger.info("billEntity.getArrearsAmount() "
									+ billEntity.getArrearsAmount());
							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(billEntity.getBillDate());
							int lastLedgerId = electricityLedgerService
									.getLastArrearsLedgerBasedOnPayment(
											accountID, previousBillDate,
											transactionCode).intValue();
							ElectricityLedgerEntity ledgerEntity = electricityLedgerService
									.find(lastLedgerId);
							logger.info("ledgerEntity ---------- "
									+ ledgerEntity.getPostType());
							List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
									.findAllById1(ledgerEntity.getElLedgerid());
							for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("GA_TAX")) {
									previousTaxAmountL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("GA_INTEREST")) {
									rateOfInterestL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("GA_TAX_INTEREST")) {
									interestOnTaxL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
							}

							logger.info("rateOfInterestL " + rateOfInterestL);
							logger.info("previousTaxAmountL "
									+ previousTaxAmountL);
							logger.info("interestOnTaxL " + interestOnTaxL);

							for (GasRateMaster gasRateMaster : gasRateMasterList) {
								if (gasRateMaster.getGasRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (billEntity
											.getArrearsAmount() - (rateOfInterestL
											+ previousTaxAmountL + interestOnTaxL));
									logger.info("arrersAmount " + arrersAmount);
									consolidatedBill = calculationController
											.interstAmountGas(gasRateMaster,
													endDate.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							for (GasRateMaster gasRateMaster : gasRateMasterList) {
								if (gasRateMaster.getGasRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmountL;
									consolidatedBill = calculationController
											.interstAmountGas(gasRateMaster,
													endDate.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}
						}
						if (billEntity.getBillAmount() > 0) {
							logger.info("billEntity.getBillAmount() "
									+ billEntity.getBillAmount());

							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(billEntity.getBillDueDate());
							Days d = Days.daysBetween(endDate, startDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "
									+ days);
							for (ElectricityBillLineItemEntity billLineItemEntity : billEntity
									.getBillLineItemEntities()) {
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("GA_TAX")) {
									previousTaxAmount = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("GA_INTEREST")) {
									rateOfInterest = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim()
										.equalsIgnoreCase("GA_TAX_INTEREST")) {
									interestOnTax = (float) billLineItemEntity
											.getBalanceAmount();
								}
							}

							for (GasRateMaster gasRateMaster : gasRateMasterList) {
								if (gasRateMaster.getGasRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (billEntity
											.getBillAmount() - (rateOfInterest
											+ previousTaxAmount + interestOnTax));
									consolidatedBill = calculationController
											.interstAmountGas(gasRateMaster,
													endDate.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							logger.info("rateOfInterest " + rateOfInterest);
							logger.info("previousTaxAmount "
									+ previousTaxAmount);
							logger.info("interestOnTax " + interestOnTax);

							for (GasRateMaster gasRateMaster : gasRateMasterList) {
								if (gasRateMaster.getGasRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmount;
									consolidatedBill = calculationController
											.interstAmountGas(gasRateMaster,
													endDate.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}
								}
							}
						}
					}
				} else {
					logger.info("------------------ First Month Bill -----------------------");
				}
			}

			else {
				logger.info("--------------------- Interest calculation based on ----"
						+ interestType);
				ElectricityBillEntity previouBillEntity = electricityBillsService
						.getPreviousBill(accountID, typeOfService,
								previousBillDate, "Bill");
				ElectricityLedgerEntity electricityLedgerEntity;
				if (previouBillEntity != null) {
					String transactionCode = "GA";
					electricityLedgerEntity = electricityLedgerService
							.getPreviousLedger(accountID, previousBillDate,
									transactionCode);
					if (electricityLedgerEntity.getPostType().trim()
							.equalsIgnoreCase("PAYMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF "
								+ electricityLedgerEntity.getPostType());
						Date ledgerPostDate = new LocalDate(
								electricityLedgerEntity.getPostedBillDate())
								.toDateMidnight().toDate();
						Date billDueDate = new LocalDate(
								previouBillEntity.getBillDueDate())
								.toDateMidnight().toDate();
						if ((billDueDate.compareTo(ledgerPostDate)) == 0) {
							logger.info("======== payment done on same date of due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ previouBillEntity.getArrearsAmount());
							if (previouBillEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										/*
										 * startDate = new LocalDate(
										 * ledgerEntityPayments
										 * .getPostedBillDate());
										 */
										startDate = new LocalDate(
												currentBillDate);
										endDate = new LocalDate(
												previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("GA_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"GA_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"GA_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (GasRateMaster gasRateMaster : gasRateMasterList) {
											if (gasRateMaster
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (previouBillEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												consolidatedBill = calculationController
														.interstAmountGasForMonthWise(
																gasRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (GasRateMaster gasRateMaster : gasRateMasterList) {
											if (gasRateMaster
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountGasForMonthWise(
																gasRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}

							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						} else if (ledgerPostDate.compareTo(billDueDate) > 0) {
							logger.info("======== payment done on after due date ===============");

							if (previouBillEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										/*
										 * startDate = new LocalDate(
										 * ledgerEntityPayments
										 * .getPostedBillDate());
										 */
										startDate = new LocalDate(
												currentBillDate);
										endDate = new LocalDate(
												previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("GA_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"GA_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"GA_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (GasRateMaster gasRateMaster : gasRateMasterList) {
											if (gasRateMaster
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (previouBillEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												logger.info("arrersAmount "
														+ arrersAmount);
												consolidatedBill = calculationController
														.interstAmountGasForMonthWise(
																gasRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (GasRateMaster gasRateMaster : gasRateMasterList) {
											if (gasRateMaster
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountGasForMonthWise(
																gasRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("billEntity.getBillAmount() "
										+ previouBillEntity.getBillAmount());
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getBillAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										/*
										 * startDate = new LocalDate(
										 * ledgerEntityPayments
										 * .getPostedBillDate());
										 */
										startDate = new LocalDate(
												currentBillDate);
										/*
										 * endDate = new LocalDate(
										 * billEntity.getBillDueDate());
										 */
										endDate = new LocalDate(
												previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										for (ElectricityBillLineItemEntity billLineItemEntity : previouBillEntity
												.getBillLineItemEntities()) {
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("GA_TAX")) {
												previousTaxAmount = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"GA_INTEREST")) {
												rateOfInterest = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"GA_TAX_INTEREST")) {
												interestOnTax = (float) billLineItemEntity
														.getBalanceAmount();
											}
										}
										logger.info("rateOfInterest "
												+ rateOfInterest);
										logger.info("previousTaxAmount "
												+ previousTaxAmount);
										logger.info("interestOnTax "
												+ interestOnTax);
										for (GasRateMaster gasRateMaster : gasRateMasterList) {
											if (gasRateMaster
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (previouBillEntity
														.getBillAmount() - (rateOfInterest
														+ previousTaxAmount + interestOnTax));
												consolidatedBill = calculationController
														.interstAmountGasForMonthWise(
																gasRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
										for (GasRateMaster gasRateMaster : gasRateMasterList) {
											if (gasRateMaster
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmount;
												consolidatedBill = calculationController
														.interstAmountGasForMonthWise(
																gasRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
						} else {
							logger.info("======== payment done on before due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ previouBillEntity.getArrearsAmount());
							if (previouBillEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										/*
										 * startDate = new LocalDate(
										 * ledgerEntityPayments
										 * .getPostedBillDate());
										 */
										startDate = new LocalDate(
												currentBillDate);
										endDate = new LocalDate(
												previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("GA_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"GA_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"GA_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (GasRateMaster gasRateMaster : gasRateMasterList) {
											if (gasRateMaster
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (previouBillEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												logger.info("arrersAmount "
														+ arrersAmount);
												consolidatedBill = calculationController
														.interstAmountGasForMonthWise(
																gasRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
										for (GasRateMaster gasRateMaster : gasRateMasterList) {
											if (gasRateMaster
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountGasForMonthWise(
																gasRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						}

					} else if (electricityLedgerEntity.getPostType().trim()
							.equalsIgnoreCase("ADJUSTMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF Else "
								+ electricityLedgerEntity.getPostType());
					} else {
						logger.info("electricityLedgerEntity.getPostType ---- in Else "
								+ electricityLedgerEntity.getPostType());
						if (previouBillEntity.getArrearsAmount() > 0) {
							logger.info("billEntity.getArrearsAmount() "
									+ previouBillEntity.getArrearsAmount());
							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(
									previouBillEntity.getBillDate());
							Days d = Days.daysBetween(startDate, endDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "
									+ days);

							int lastLedgerId = electricityLedgerService
									.getLastArrearsLedgerBasedOnPayment(
											accountID, previousBillDate,
											transactionCode).intValue();
							ElectricityLedgerEntity ledgerEntity = electricityLedgerService
									.find(lastLedgerId);
							logger.info("ledgerEntity ---------- "
									+ ledgerEntity.getPostType());
							List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
									.findAllById1(ledgerEntity.getElLedgerid());
							for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("GA_TAX")) {
									previousTaxAmountL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("GA_INTEREST")) {
									rateOfInterestL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("GA_TAX_INTEREST")) {
									interestOnTaxL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
							}

							logger.info("rateOfInterestL " + rateOfInterestL);
							logger.info("previousTaxAmountL "
									+ previousTaxAmountL);
							logger.info("interestOnTaxL " + interestOnTaxL);

							for (GasRateMaster gasRateMaster : gasRateMasterList) {
								if (gasRateMaster.getGasRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (previouBillEntity
											.getArrearsAmount() - (rateOfInterestL
											+ previousTaxAmountL + interestOnTaxL));
									logger.info("arrersAmount " + arrersAmount);
									consolidatedBill = calculationController
											.interstAmountGasForMonthWise(
													gasRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							for (GasRateMaster gasRateMaster : gasRateMasterList) {
								if (gasRateMaster.getGasRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmountL;
									consolidatedBill = calculationController
											.interstAmountGasForMonthWise(
													gasRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}
						}
						if (previouBillEntity.getBillAmount() > 0) {
							logger.info("billEntity.getBillAmount() "
									+ previouBillEntity.getBillAmount());

							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(
									previouBillEntity.getBillDate());
							Days d = Days.daysBetween(endDate, startDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "
									+ days);
							for (ElectricityBillLineItemEntity billLineItemEntity : previouBillEntity
									.getBillLineItemEntities()) {
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("GA_TAX")) {
									previousTaxAmount = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("GA_INTEREST")) {
									rateOfInterest = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim()
										.equalsIgnoreCase("GA_TAX_INTEREST")) {
									interestOnTax = (float) billLineItemEntity
											.getBalanceAmount();
								}
							}

							for (GasRateMaster gasRateMaster : gasRateMasterList) {
								if (gasRateMaster.getGasRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (previouBillEntity
											.getBillAmount() - (rateOfInterest
											+ previousTaxAmount + interestOnTax));
									consolidatedBill = calculationController
											.interstAmountGasForMonthWise(
													gasRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}
								}
							}

							logger.info("rateOfInterest " + rateOfInterest);
							logger.info("previousTaxAmount "
									+ previousTaxAmount);
							logger.info("interestOnTax " + interestOnTax);

							for (GasRateMaster gasRateMaster : gasRateMasterList) {
								if (gasRateMaster.getGasRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmount;
									consolidatedBill = calculationController
											.interstAmountGasForMonthWise(
													gasRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}
						}
					}
				} else {
					logger.info("------------------ First Month Bill -----------------------");
				}
			}

		}
		intersts.put("interestOnTaxAmount", interestOnTaxAmount);
		intersts.put("interestOnArrearsAmount", interestOnArrearsAmount);
		return intersts;

	}

	private void saveBillGasParameters(ElectricityBillEntity billEntity,
			List<GasRateMaster> gasRateMasterList, int accountID,
			String typeOfService, Date previousBillDate, Date currentBillDate,
			Float uomValue, Integer billableDays, String presentMeterStaus,
			String previousMeterStaus, Float meterConstant,
			Float presentUOMReading, Float previousUOMReading, String gaString,
			Float lossDistributionUnit, String meterchangeUnit) {

		List<BillParameterMasterEntity> billParameterMasterEntitiesList = billingParameterMasterService
				.getBillParameterMasterByServiceType(typeOfService);
		logger.info("billParameterMasterEntitiesList ::::::::::: "
				+ billParameterMasterEntitiesList.size());

		for (BillParameterMasterEntity billParameterMasterEntity : billParameterMasterEntitiesList) {
			logger.info("Bill parameters servic type :::"
					+ billParameterMasterEntity.getServiceType());

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Number of days")) {
				ElectricityBillParametersEntity noOfDays = new ElectricityBillParametersEntity(
						"Integer", billableDays.toString(), "Active");
				noOfDays.setBillParameterMasterEntity(billParameterMasterEntity);
				noOfDays.setElectricityBillEntity(billEntity);
				billParameterService.save(noOfDays);
			}
			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Previous status")) {
				ElectricityBillParametersEntity previousStatus = new ElectricityBillParametersEntity(
						"String", previousMeterStaus, "Active");
				previousStatus
						.setBillParameterMasterEntity(billParameterMasterEntity);
				previousStatus.setElectricityBillEntity(billEntity);
				billParameterService.save(previousStatus);
			}

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Present Status")) {
				if (presentMeterStaus.equalsIgnoreCase("Meter Change")) {
					ElectricityBillParametersEntity presentStatus = new ElectricityBillParametersEntity(
							"String", presentMeterStaus, "Active",
							meterchangeUnit);
					presentStatus
							.setBillParameterMasterEntity(billParameterMasterEntity);
					presentStatus.setElectricityBillEntity(billEntity);
					billParameterService.update(presentStatus);
				} else {
					ElectricityBillParametersEntity presentStatus = new ElectricityBillParametersEntity(
							"String", presentMeterStaus, "Active");
					presentStatus
							.setBillParameterMasterEntity(billParameterMasterEntity);
					presentStatus.setElectricityBillEntity(billEntity);
					billParameterService.update(presentStatus);
				}

			}
			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Previous reading")) {
				ElectricityBillParametersEntity reviousReading = new ElectricityBillParametersEntity(
						"Integer", previousUOMReading.toString(), "Active");
				reviousReading
						.setBillParameterMasterEntity(billParameterMasterEntity);
				reviousReading.setElectricityBillEntity(billEntity);
				billParameterService.save(reviousReading);
			}

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Present reading")) {
				ElectricityBillParametersEntity presentReading = new ElectricityBillParametersEntity(
						"Integer", presentUOMReading.toString().toString(),
						"Active");
				presentReading
						.setBillParameterMasterEntity(billParameterMasterEntity);
				presentReading.setElectricityBillEntity(billEntity);
				billParameterService.save(presentReading);
			}

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Meter Constant")) {
				ElectricityBillParametersEntity meterConstantObject = new ElectricityBillParametersEntity(
						"Date", meterConstant.toString(), "Active");
				meterConstantObject
						.setBillParameterMasterEntity(billParameterMasterEntity);
				meterConstantObject.setElectricityBillEntity(billEntity);
				billParameterService.save(meterConstantObject);
			}

			if (billParameterMasterEntity.getBvmName()
					.equalsIgnoreCase("Units")) {
				ElectricityBillParametersEntity units = new ElectricityBillParametersEntity(
						"Integer", uomValue.toString(), "Active");
				units.setBillParameterMasterEntity(billParameterMasterEntity);
				units.setElectricityBillEntity(billEntity);
				billParameterService.save(units);
			}
			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"GA Slab")) {
				if (gaString != null) {
					gaString = gaString.replace("null", " ");
					gaString = gaString.replace("</br>", " ");
					ElectricityBillParametersEntity slabs = new ElectricityBillParametersEntity(
							"String", "NA", "Active");
					slabs.setNotes(gaString);
					slabs.setBillParameterMasterEntity(billParameterMasterEntity);
					slabs.setElectricityBillEntity(billEntity);
					billParameterService.update(slabs);
				}
			}
			if (lossDistributionUnit > 0) {
				if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
						"Distribution Loss Unit")) {
					ElectricityBillParametersEntity distributionUnit = new ElectricityBillParametersEntity(
							"String", lossDistributionUnit.toString(), "Active");
					distributionUnit
							.setBillParameterMasterEntity(billParameterMasterEntity);
					distributionUnit.setElectricityBillEntity(billEntity);
					billParameterService.save(distributionUnit);
				}
				/*
				 * if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
				 * "GA Slab")) { for (GasRateMaster gasRateMaster :
				 * gasRateMasterList) { if
				 * (gasRateMaster.getGasRateName().equalsIgnoreCase(
				 * "Domestic")) { String slab = calculationController
				 * .tariffAmountGasSlab(gasRateMaster, previousBillDate,
				 * currentBillDate, uomValue); slab = slab.replace("null", "");
				 * slab = slab.replace("</br>", "");
				 * ElectricityBillParametersEntity slabs = new
				 * ElectricityBillParametersEntity( "String", "NA", "Active");
				 * slabs.setNotes(slab);
				 * slabs.setBillParameterMasterEntity(billParameterMasterEntity
				 * ); slabs.setElectricityBillEntity(billEntity);
				 * billParameterService.save(slabs); } } }
				 */
			}
		}
	}

	public HashMap<String, Object> saveWaterBillParameters(
			List<WTRateMaster> waterRateMasterList, int accountID,
			String typeOfService, Date previousBillDate, Date currentBillDate,
			Float uomValue, Integer billableDays, String presentMeterStaus,
			String previousMeterStaus, Float meterConstant,
			Float presentUOMReading, Float previousUOMReading, Double avgUnits,
			Integer avgCount, String billType, String postType,
			String meterchangeUnit) {

		ElectricityBillEntity previousBillEntity = electricityBillsService
				.getPreviousBill(accountID, typeOfService, previousBillDate,
						"Bill");
		float wcAmount = 0;
		float scAmount = 0;
		float solAmount = 0;
		float fcAmount = 0;
		float roAmount = 0f;
		float taxAmount = 0;
		float totalAmount;
		Float roundoffValue = 0f;
		float rateOfInterest = 0f;
		float arrearsAmount = 0f;
		String wtSlabString = null;
		HashMap<String, Object> consolidatedBill = new LinkedHashMap<>();
		Double avgAmount = 0d;

		ElectricityBillEntity billEntityForArrears = new ElectricityBillEntity();
		billEntityForArrears.setAccountId(accountID);
		billEntityForArrears.setArrearsAmount(getLastBillArrearsAmount(
				"ARREARS", accountID, typeOfService));
		billEntityForArrears.setElBillDate(new java.sql.Timestamp(
				currentBillDate.getTime()));
		billEntityForArrears.setPostType("Bill");
		billEntityForArrears.setBillDate(new java.sql.Date(currentBillDate
				.getTime()));
		String configName = "Due Days";
		String status = "Active";
		String dueDays = electricityBillsService.getBillingConfigValue(configName,status);
		int days=0;
		if(dueDays!=null)
		{
			days = Integer.parseInt(dueDays);
		}
		
		Date billDueDate = addDays(currentBillDate, days);
		billEntityForArrears.setBillDueDate(new java.sql.Date(billDueDate
				.getTime()));
		Calendar c = Calendar.getInstance();
		c.setTime(currentBillDate);
		c.set(Calendar.DAY_OF_MONTH, 1);

		java.sql.Date billMonth = new java.sql.Date(c.getTime().getTime());
		logger.info("billMonth ::::::::::::::: " + billMonth);
		billEntityForArrears.setBillMonth(billMonth);
		billEntityForArrears.setStatus("Generated");
		billEntityForArrears.setCreatedBy((String) SessionData.getUserDetails()
				.get("userID"));
		billEntityForArrears.setLastUpdatedBy((String) SessionData
				.getUserDetails().get("userID"));
		billEntityForArrears.setLastUpdatedDT(new java.sql.Timestamp(new Date()
				.getTime()));
		billEntityForArrears.setTypeOfService(typeOfService);
		billEntityForArrears.setBillNo("BILL"
				+ billParameterService.getSequencyNumber());
		logger.info("billEntity.getBillDueDate() ::::::::::::::: "
				+ billEntityForArrears.getBillDueDate());
		storeArrears("ARREARS", billEntityForArrears);

		Set<ElectricityBillLineItemEntity> billLineItemEntities = new LinkedHashSet<>();
		ElectricityBillLineItemEntity waterCharges = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity sweageCharges = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity roCharges = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity waterTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity waterFixcedCharages = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity solorCharages = new ElectricityBillLineItemEntity();

		ElectricityBillLineItemEntity roundOffBillLineItemEntity = new ElectricityBillLineItemEntity();

		for (WTRateMaster waterRateMaster : waterRateMasterList) {
			if (waterRateMaster.getWtRateName().equalsIgnoreCase(
					"Water charges")) {
				if (presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus
								.equalsIgnoreCase("Meter Not Reading")
						|| presentMeterStaus
								.equalsIgnoreCase("Direct Connection")
						|| presentMeterStaus.equalsIgnoreCase("Burnt")
						|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
					consolidatedBill = calculationController.waterTariffAmount(
							waterRateMaster, previousBillDate, currentBillDate,
							uomValue);
				} else {
					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCountWater(waterRateMaster,
											previousBillDate, currentBillDate,
											uomValue,
											previousBillEntity.getAvgCount());

						} else {
							consolidatedBill = calculationController
									.waterTariffAmount(waterRateMaster,
											previousBillDate, currentBillDate,
											uomValue);
						}
					} else {
						consolidatedBill = calculationController
								.waterTariffAmount(waterRateMaster,
										previousBillDate, currentBillDate,
										uomValue);
					}
				}
				for (Entry<String, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						wcAmount = (Float) map.getValue();

					}
					if (map.getKey().equals("wtSlabString")) {
						wtSlabString = (String) map.getValue();
					}
				}
				logger.info(":::::::::::: In "
						+ waterRateMaster.getWtRateName() + "::::::::::");
				waterCharges.setTransactionCode("WT_WC");
				logger.info("wcAmount ::::::::: " + wcAmount);
				waterCharges.setBalanceAmount(wcAmount);
				waterCharges.setCreditAmount(0);
				waterCharges.setDebitAmount(0);
				waterCharges.setStatus("Not Approved");
				waterCharges.setCreatedBy((String) SessionData.getUserDetails()
						.get("userID"));
				waterCharges.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				waterCharges.setLastUpdatedDT(new java.sql.Timestamp(new Date()
						.getTime()));
				billLineItemEntities.add(waterCharges);
			}

			if (waterRateMaster.getWtRateName().equalsIgnoreCase(
					"Solar charges")) {
				String solApplicable = null;
				ServiceMastersEntity mastersEntity1 = serviceMasterService
						.getServiceMasterServicType(accountID, typeOfService);
				List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService
						.findAllByParentId(mastersEntity1.getServiceMasterId());
				for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1) {

					if (serviceParametersEntity.getServiceParameterMaster()
							.getSpmName().equalsIgnoreCase("Solar charges")) {
						solApplicable = serviceParametersEntity
								.getServiceParameterValue();
					}
				}
				logger.info("solApplicable --------------- " + solApplicable);
				if (solApplicable != null
						&& solApplicable.equalsIgnoreCase("Yes")) {

					LocalDate fromDate = new LocalDate(previousBillDate);
					LocalDate toDate = new LocalDate(currentBillDate);
					PeriodType monthDay = PeriodType.yearMonthDay()
							.withYearsRemoved();
					Period difference = new Period(fromDate, toDate, monthDay);
					float billableMonths = difference.getMonths();
					int daysAfterMonth = difference.getDays();

					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(currentBillDate);
					float daysToMonths = (float) daysAfterMonth
							/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
					float netMonth = daysToMonths + billableMonths;

					if (presentMeterStaus.equalsIgnoreCase("Door Lock")
							|| presentMeterStaus
									.equalsIgnoreCase("Meter Not Reading")
							|| presentMeterStaus
									.equalsIgnoreCase("Direct Connection")
							|| presentMeterStaus.equalsIgnoreCase("Burnt")
							|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
						consolidatedBill = calculationController
								.waterTariffAmount(waterRateMaster,
										previousBillDate, currentBillDate,
										1 * netMonth);
					} else {
						if (previousBillEntity != null) {
							if (previousBillEntity.getAvgCount() >= 1) {
								consolidatedBill = calculationController
										.tariffAmountAvgCountWater(
												waterRateMaster,
												previousBillDate,
												currentBillDate, 1 * netMonth,
												previousBillEntity
														.getAvgCount());

							} else {
								consolidatedBill = calculationController
										.waterTariffAmount(waterRateMaster,
												previousBillDate,
												currentBillDate, 1 * netMonth);
							}
						} else {
							consolidatedBill = calculationController
									.waterTariffAmount(waterRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth);
						}
					}
					for (Entry<String, Object> map : consolidatedBill
							.entrySet()) {
						if (map.getKey().equals("total")) {
							solAmount = (Float) map.getValue();
						}
					}
					solorCharages.setTransactionCode("WT_SOL");
					solorCharages.setBalanceAmount(solAmount);
					solorCharages.setCreditAmount(0);
					solorCharages.setDebitAmount(0);
					solorCharages.setStatus("Not Approved");
					solorCharages.setCreatedBy((String) SessionData
							.getUserDetails().get("userID"));
					solorCharages.setLastUpdatedBy((String) SessionData
							.getUserDetails().get("userID"));
					solorCharages.setLastUpdatedDT(new java.sql.Timestamp(
							new Date().getTime()));
					billLineItemEntities.add(solorCharages);
				}
			}

			if (waterRateMaster.getWtRateName().equalsIgnoreCase("RO charges")) {
				String roApplicable = null;
				ServiceMastersEntity mastersEntity1 = serviceMasterService
						.getServiceMasterServicType(accountID, typeOfService);
				List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService
						.findAllByParentId(mastersEntity1.getServiceMasterId());
				for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1) {

					if (serviceParametersEntity.getServiceParameterMaster()
							.getSpmName().equalsIgnoreCase("RO charges")) {
						roApplicable = serviceParametersEntity
								.getServiceParameterValue();
					}
				}
				logger.info("roApplicable --------------- " + roApplicable);
				if (roApplicable != null
						&& roApplicable.equalsIgnoreCase("Yes")) {

					LocalDate fromDate = new LocalDate(previousBillDate);
					LocalDate toDate = new LocalDate(currentBillDate);
					PeriodType monthDay = PeriodType.yearMonthDay()
							.withYearsRemoved();
					Period difference = new Period(fromDate, toDate, monthDay);
					float billableMonths = difference.getMonths();
					int daysAfterMonth = difference.getDays();

					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(currentBillDate);
					float daysToMonths = (float) daysAfterMonth
							/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
					float netMonth = daysToMonths + billableMonths;

					if (presentMeterStaus.equalsIgnoreCase("Door Lock")
							|| presentMeterStaus
									.equalsIgnoreCase("Meter Not Reading")
							|| presentMeterStaus
									.equalsIgnoreCase("Direct Connection")
							|| presentMeterStaus.equalsIgnoreCase("Burnt")
							|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
						consolidatedBill = calculationController
								.waterTariffAmount(waterRateMaster,
										previousBillDate, currentBillDate,
										1 * netMonth);
					} else {
						if (previousBillEntity != null) {
							if (previousBillEntity.getAvgCount() >= 1) {
								consolidatedBill = calculationController
										.tariffAmountAvgCountWater(
												waterRateMaster,
												previousBillDate,
												currentBillDate, 1 * netMonth,
												previousBillEntity
														.getAvgCount());

							} else {
								consolidatedBill = calculationController
										.waterTariffAmount(waterRateMaster,
												previousBillDate,
												currentBillDate, 1 * netMonth);
							}
						} else {
							consolidatedBill = calculationController
									.waterTariffAmount(waterRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth);
						}
					}
					for (Entry<String, Object> map : consolidatedBill
							.entrySet()) {
						if (map.getKey().equals("total")) {
							roAmount = (Float) map.getValue();
						}
					}
					roCharges.setTransactionCode("WT_RO");
					roCharges.setBalanceAmount(roAmount);
					roCharges.setCreditAmount(0);
					roCharges.setDebitAmount(0);
					roCharges.setStatus("Not Approved");
					roCharges.setCreatedBy((String) SessionData
							.getUserDetails().get("userID"));
					roCharges.setLastUpdatedBy((String) SessionData
							.getUserDetails().get("userID"));
					roCharges.setLastUpdatedDT(new java.sql.Timestamp(
							new Date().getTime()));
					billLineItemEntities.add(roCharges);
				}
			}

			if (waterRateMaster.getWtRateName().equalsIgnoreCase(
					"Sewage charges")) {

				ServiceMastersEntity mastersEntity1 = serviceMasterService
						.getServiceMasterServicType(accountID, typeOfService);
				List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService
						.findAllByParentId(mastersEntity1.getServiceMasterId());
				String sewageApplicable = null;
				for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1) {

					if (serviceParametersEntity.getServiceParameterMaster()
							.getSpmName().equalsIgnoreCase("Sewage charges")) {
						sewageApplicable = serviceParametersEntity
								.getServiceParameterValue();
					}
				}
				logger.info("sewageApplicable --------------- "
						+ sewageApplicable);
				if (sewageApplicable != null
						&& sewageApplicable.equalsIgnoreCase("Yes")) {

					LocalDate fromDate = new LocalDate(previousBillDate);
					LocalDate toDate = new LocalDate(currentBillDate);
					PeriodType monthDay = PeriodType.yearMonthDay()
							.withYearsRemoved();
					Period difference = new Period(fromDate, toDate, monthDay);
					float billableMonths = difference.getMonths();
					int daysAfterMonth = difference.getDays();

					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(currentBillDate);
					float daysToMonths = (float) daysAfterMonth
							/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
					float netMonth = daysToMonths + billableMonths;

					if (presentMeterStaus.equalsIgnoreCase("Door Lock")
							|| presentMeterStaus
									.equalsIgnoreCase("Meter Not Reading")
							|| presentMeterStaus
									.equalsIgnoreCase("Direct Connection")
							|| presentMeterStaus.equalsIgnoreCase("Burnt")
							|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
						consolidatedBill = calculationController
								.waterTariffAmount(waterRateMaster,
										previousBillDate, currentBillDate,
										1 * netMonth);
					} else {
						if (previousBillEntity != null) {
							if (previousBillEntity.getAvgCount() >= 1) {
								consolidatedBill = calculationController
										.tariffAmountAvgCountWater(
												waterRateMaster,
												previousBillDate,
												currentBillDate, 1 * netMonth,
												previousBillEntity
														.getAvgCount());

							} else {
								consolidatedBill = calculationController
										.waterTariffAmount(waterRateMaster,
												previousBillDate,
												currentBillDate, 1 * netMonth);
							}
						} else {
							consolidatedBill = calculationController
									.waterTariffAmount(waterRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth);
						}
					}

					for (Entry<String, Object> map : consolidatedBill
							.entrySet()) {
						if (map.getKey().equals("total")) {
							scAmount = (Float) map.getValue();
						}
					}
					sweageCharges.setTransactionCode("WT_SC");
					sweageCharges.setBalanceAmount(scAmount);
					sweageCharges.setCreditAmount(0);
					sweageCharges.setDebitAmount(0);
					sweageCharges.setStatus("Not Approved");
					sweageCharges.setCreatedBy((String) SessionData
							.getUserDetails().get("userID"));
					sweageCharges.setLastUpdatedBy((String) SessionData
							.getUserDetails().get("userID"));
					sweageCharges.setLastUpdatedDT(new java.sql.Timestamp(
							new Date().getTime()));
					billLineItemEntities.add(sweageCharges);
				}
			}

			if (waterRateMaster.getWtRateName().equalsIgnoreCase(
					"Fixed charges")) {
				LocalDate fromDate = new LocalDate(previousBillDate);
				LocalDate toDate = new LocalDate(currentBillDate);
				PeriodType monthDay = PeriodType.yearMonthDay()
						.withYearsRemoved();
				Period difference = new Period(fromDate, toDate, monthDay);
				float billableMonths = difference.getMonths();
				int daysAfterMonth = difference.getDays();

				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(currentBillDate);
				float daysToMonths = (float) daysAfterMonth
						/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
				float netMonth = daysToMonths + billableMonths;

				if (presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus
								.equalsIgnoreCase("Meter Not Reading")
						|| presentMeterStaus
								.equalsIgnoreCase("Direct Connection")
						|| presentMeterStaus.equalsIgnoreCase("Burnt")
						|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
					consolidatedBill = calculationController.waterTariffAmount(
							waterRateMaster, previousBillDate, currentBillDate,
							1 * netMonth);
				} else {
					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCountWater(waterRateMaster,
											previousBillDate, currentBillDate,
											0.0f,
											previousBillEntity.getAvgCount());

						} else {
							consolidatedBill = calculationController
									.waterTariffAmount(waterRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth);
						}
					} else {
						consolidatedBill = calculationController
								.waterTariffAmount(waterRateMaster,
										previousBillDate, currentBillDate,
										1 * netMonth);
					}
				}
				for (Entry<String, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						fcAmount = (Float) map.getValue();
					}
				}
				waterFixcedCharages.setTransactionCode("WT_FC");
				waterFixcedCharages.setBalanceAmount(fcAmount);
				waterFixcedCharages.setCreditAmount(0);
				waterFixcedCharages.setDebitAmount(0);
				waterFixcedCharages.setStatus("Not Approved");
				waterFixcedCharages.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				waterFixcedCharages.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				waterFixcedCharages.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(waterFixcedCharages);
			}

		}

		float interestOnArrearsAmount = 0;
		float interestOnTaxAmount = 0;
		ElectricityBillLineItemEntity interestOnTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity interestOnBillAmount = new ElectricityBillLineItemEntity();
		consolidatedBill = interestCalculationWater(accountID, typeOfService,
				previousBillDate, waterRateMasterList, currentBillDate);
		for (Entry<String, Object> map : consolidatedBill.entrySet()) {
			if (map.getKey().equals("interestOnArrearsAmount")) {
				interestOnArrearsAmount = interestOnArrearsAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnArrearsAmount);
				interestOnBillAmount.setTransactionCode("WT_INTEREST");
				interestOnBillAmount.setBalanceAmount(interestOnArrearsAmount);
				interestOnBillAmount.setCreditAmount(0);
				interestOnBillAmount.setDebitAmount(0);
				interestOnBillAmount.setStatus("Not Approved");
				interestOnBillAmount.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnBillAmount);
			}
			if (map.getKey().equals("interestOnTaxAmount")) {
				interestOnTaxAmount = interestOnTaxAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnTaxAmount);
				interestOnTax.setTransactionCode("WT_TAX_INTEREST");
				interestOnTax.setBalanceAmount(interestOnTaxAmount);
				interestOnTax.setCreditAmount(0);
				interestOnTax.setDebitAmount(0);
				interestOnTax.setStatus("Not Approved");
				interestOnTax.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnTax);
			}
		}

		logger.info("wcAmount -------------- " + wcAmount);
		logger.info("scAmount -------------- " + scAmount);
		logger.info("roAmount -------------- " + roAmount);
		logger.info("solAmount -------------- " + solAmount);
		logger.info("fcAmount -------------- " + fcAmount);
		logger.info("rateOfInterest -------------- " + rateOfInterest);
		logger.info("arrearsAmount -------------- " + arrearsAmount);
		logger.info("(float) interestOnTax.getBalanceAmount() -------------- "
				+ (float) interestOnTax.getBalanceAmount());
		logger.info("(float) interestOnBillAmount.getBalanceAmount() -------------- "
				+ (float) interestOnBillAmount.getBalanceAmount());

		float totalExcludeTax = (float) (wcAmount + roundoffValue);
		logger.info(":::::::::::::: Total Amount Excludeing tax ::::::::::::: "
				+ totalExcludeTax);

		for (WTRateMaster waterRateMaster : waterRateMasterList) {
			if (waterRateMaster.getWtRateName().equalsIgnoreCase("Tax")) {
				logger.info(":::::::::::: In "
						+ waterRateMaster.getWtRateName() + "::::::::::");
				waterTax.setTransactionCode("WT_TAX");
				consolidatedBill = calculationController.waterTariffAmount(
						waterRateMaster, previousBillDate, currentBillDate,
						totalExcludeTax);

				for (Entry<String, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						taxAmount = (Float) map.getValue();
					}
				}
				logger.info("taxAmount ::::::::: " + taxAmount);
				waterTax.setBalanceAmount(taxAmount);
				waterTax.setCreditAmount(0);
				waterTax.setDebitAmount(0);
				waterTax.setStatus("Not Approved");
				waterTax.setCreatedBy((String) SessionData.getUserDetails()
						.get("userID"));
				waterTax.setLastUpdatedBy((String) SessionData.getUserDetails()
						.get("userID"));
				waterTax.setLastUpdatedDT(new java.sql.Timestamp(new Date()
						.getTime()));
				billLineItemEntities.add(waterTax);
			}

		}
		logger.info("wcAmount " + wcAmount);
		logger.info("scAmount " + scAmount);
		logger.info("solAmount " + solAmount);
		logger.info("fcAmount " + fcAmount);
		logger.info("roAmount " + roAmount);
		logger.info("taxAmount " + taxAmount);
		logger.info("rateOfInterest " + rateOfInterest);
		logger.info("arrearsAmount " + arrearsAmount);
		logger.info("interestOnTaxAmount " + interestOnTax.getBalanceAmount());
		logger.info("interestOnArrears "
				+ interestOnBillAmount.getBalanceAmount());
		logger.info("Total without round off "
				+ (wcAmount + roAmount + solAmount + scAmount + fcAmount
						+ taxAmount + rateOfInterest + arrearsAmount
						+ interestOnTax.getBalanceAmount() + interestOnBillAmount
							.getBalanceAmount()));

		totalAmount = roundOff(wcAmount + solAmount + scAmount + roAmount
				+ taxAmount + fcAmount + rateOfInterest + arrearsAmount
				+ (float) interestOnTax.getBalanceAmount()
				+ (float) interestOnBillAmount.getBalanceAmount(), 0);
		logger.info("totalAmount :::::::::::::: " + totalAmount);
		roundoffValue = totalAmount
				- (wcAmount + scAmount + roAmount + fcAmount + taxAmount
						+ rateOfInterest + arrearsAmount
						+ (float) interestOnTax.getBalanceAmount() + (float) interestOnBillAmount
							.getBalanceAmount());
		logger.info("::::::::::::::::::::::::: Round off value is ::::::::::::::: "
				+ roundoffValue);

		roundOffBillLineItemEntity.setCreditAmount(0);
		roundOffBillLineItemEntity.setDebitAmount(0);
		roundOffBillLineItemEntity.setStatus("Not Approved");
		roundOffBillLineItemEntity.setCreatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
				new Date().getTime()));
		roundOffBillLineItemEntity.setBalanceAmount(roundoffValue);
		roundOffBillLineItemEntity.setTransactionCode("WT_ROF");
		billLineItemEntities.add(roundOffBillLineItemEntity);

		if (presentMeterStaus.equalsIgnoreCase("Door Lock")
				|| presentMeterStaus.equalsIgnoreCase("Meter Not Reading")
				|| presentMeterStaus.equalsIgnoreCase("Direct Connection")
				|| presentMeterStaus.equalsIgnoreCase("Burnt")
				|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
			presentUOMReading = previousUOMReading;
			ElectricityBillEntity billEntity = electricityBillsService
					.getPreviousBill(accountID, typeOfService,
							previousBillDate, "Bill");
			if (billEntity != null) {
				avgUnits = billEntity.getAvgUnits() + uomValue;
				avgCount = billEntity.getAvgCount() + 1;
				billType = "Average";
			}
		} else {
			ElectricityBillEntity billEntity = electricityBillsService
					.getPreviousBill(accountID, typeOfService,
							previousBillDate, "Bill");
			List<ElectricityBillEntity> billEntities = new ArrayList<>();
			if (billEntity != null) {
				if (billEntity.getAvgCount() >= 1) {
					List<BigDecimal> billIds = electricityBillsService
							.getLastAvgBills(accountID, typeOfService,
									previousBillDate, "Bill",
									billEntity.getAvgCount());
					for (BigDecimal bigDecimal : billIds) {
						billEntities.add(electricityBillsService
								.find(bigDecimal.intValue()));
					}
				}

				for (ElectricityBillEntity electricityBillEntity : billEntities) {
					avgAmount = avgAmount
							+ electricityBillEntity.getBillAmount();
				}
			}
		}

		ElectricityBillEntity billEntity = saveBill(accountID, totalAmount,
				previousBillDate, currentBillDate, typeOfService,
				arrearsAmount, avgUnits, avgCount, billType, avgAmount,
				postType,2);
		saveBillLineItems(billEntity, billLineItemEntities, roundoffValue,
				arrearsAmount);
		saveBillWaterParameters(billEntity, waterRateMasterList, accountID,
				typeOfService, previousBillDate, currentBillDate, uomValue,
				billableDays, presentMeterStaus, previousMeterStaus,
				meterConstant, presentUOMReading, previousUOMReading,
				wtSlabString, meterchangeUnit);

		Locale locale = null;
		billController.getBillDoc(billEntity.getElBillId(), locale,1);
		
		HashMap<String, Object> bill = new HashMap<>();
		bill.put("billId", billEntity.getElBillId());
		return bill;
	}

	private HashMap<String, Object> interestCalculationWater(int accountID,
			String typeOfService, Date previousBillDate,
			List<WTRateMaster> waterRateMasterList, Date currentBillDate) {

		logger.info("::::::::::::::: In interest on calculation water :::::::::::::::");
		HashMap<String, Object> intersts = new LinkedHashMap<>();
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float previousTaxAmount = 0f;
		float rateOfInterest = 0;
		float interestOnTax = 0;
		float interestOnArrearsAmount = 0f;
		float interestOnTaxAmount = 0f;

		float previousTaxAmountL = 0f;
		float rateOfInterestL = 0;
		float interestOnTaxL = 0;

	/*	List<InterestSettingsEntity> interestSettingsEntities = interestSettingService.findAllData();
		String interestType = null;
		for (InterestSettingsEntity interestSettingsEntity : interestSettingsEntities) {
			interestType = interestSettingsEntity.getInterestBasedOn();
			logger.info(" Interest Calculation base on -->" + interestType);
		}*/
		String configName = "Interest Calculation";
		String status = "Active";
		String interestType = electricityBillsService.getBillingConfigValue(configName,status);
		logger.info("interestType ==================== "+interestType);

		if (interestType != null) {
			if (interestType.trim().equalsIgnoreCase("Daywise")) {
				ElectricityBillEntity billEntity = electricityBillsService
						.getPreviousBill(accountID, typeOfService,
								previousBillDate, "Bill");
				ElectricityLedgerEntity electricityLedgerEntity;
				if (billEntity != null) {
					String transactionCode = "WT";
					electricityLedgerEntity = electricityLedgerService
							.getPreviousLedger(accountID, previousBillDate,
									transactionCode);

					if (electricityLedgerEntity.getPostType().trim()
							.equalsIgnoreCase("PAYMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF "
								+ electricityLedgerEntity.getPostType());
						Date ledgerPostDate = new LocalDate(
								electricityLedgerEntity.getPostedBillDate())
								.toDateMidnight().toDate();
						Date billDueDate = new LocalDate(
								billEntity.getBillDueDate()).toDateMidnight()
								.toDate();
						if ((billDueDate.compareTo(ledgerPostDate)) == 0) {
							logger.info("======== payment done on same date of due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ billEntity.getArrearsAmount());
							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("WT_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"WT_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"WT_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (WTRateMaster wtRateMaster : waterRateMasterList) {
											if (wtRateMaster
													.getWtRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												consolidatedBill = calculationController
														.interstAmountWater(
																wtRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (WTRateMaster wtRateMaster : waterRateMasterList) {
											if (wtRateMaster
													.getWtRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountWater(
																wtRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}

							if (billEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						} else if (ledgerPostDate.compareTo(billDueDate) > 0) {
							logger.info("======== payment done on after due date ===============");

							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("WT_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"WT_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"WT_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (WTRateMaster wtRateMaster : waterRateMasterList) {
											if (wtRateMaster
													.getWtRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												logger.info("arrersAmount "
														+ arrersAmount);
												consolidatedBill = calculationController
														.interstAmountWater(
																wtRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (WTRateMaster wtRateMaster : waterRateMasterList) {
											if (wtRateMaster
													.getWtRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountWater(
																wtRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
							if (billEntity.getBillAmount() > 0) {
								logger.info("billEntity.getBillAmount() "
										+ billEntity.getBillAmount());
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getBillAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDueDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										for (ElectricityBillLineItemEntity billLineItemEntity : billEntity
												.getBillLineItemEntities()) {
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("WT_TAX")) {
												previousTaxAmount = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"WT_INTEREST")) {
												rateOfInterest = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"WT_TAX_INTEREST")) {
												interestOnTax = (float) billLineItemEntity
														.getBalanceAmount();
											}
										}
										logger.info("rateOfInterest "
												+ rateOfInterest);
										logger.info("previousTaxAmount "
												+ previousTaxAmount);
										logger.info("interestOnTax "
												+ interestOnTax);
										for (WTRateMaster wtRateMaster : waterRateMasterList) {
											if (wtRateMaster
													.getWtRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getBillAmount() - (rateOfInterest
														+ previousTaxAmount + interestOnTax));
												consolidatedBill = calculationController
														.interstAmountWater(
																wtRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
										for (WTRateMaster wtRateMaster : waterRateMasterList) {
											if (wtRateMaster
													.getWtRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmount;
												consolidatedBill = calculationController
														.interstAmountWater(
																wtRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
						} else {
							logger.info("======== payment done on before due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ billEntity.getArrearsAmount());
							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("WT_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"WT_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"WT_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (WTRateMaster wtRateMaster : waterRateMasterList) {
											if (wtRateMaster
													.getWtRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												logger.info("arrersAmount "
														+ arrersAmount);
												consolidatedBill = calculationController
														.interstAmountWater(
																wtRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
													if (map.getKey().equals("total")) {
														interestOnArrearsAmount = interestOnArrearsAmount+ (Float) map.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "+ arrersAmount+ " is "+ (Float) map.getValue());
													}
												}

											}
										}
										for (WTRateMaster wtRateMaster : waterRateMasterList) {
											if (wtRateMaster
													.getWtRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountWater(
																wtRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
							if (billEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						}
					} else if (electricityLedgerEntity.getPostType().trim()
							.equalsIgnoreCase("ADJUSTMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF Else "
								+ electricityLedgerEntity.getPostType());
					} else {

						logger.info("electricityLedgerEntity.getPostType ---- in Else "
								+ electricityLedgerEntity.getPostType());
						if (billEntity.getArrearsAmount() > 0) {
							logger.info("billEntity.getArrearsAmount() "
									+ billEntity.getArrearsAmount());
							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(billEntity.getBillDate());
							Days d = Days.daysBetween(startDate, endDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "
									+ days);

							int lastLedgerId = electricityLedgerService
									.getLastArrearsLedgerBasedOnPayment(
											accountID, previousBillDate,
											transactionCode).intValue();
							ElectricityLedgerEntity ledgerEntity = electricityLedgerService
									.find(lastLedgerId);
							logger.info("ledgerEntity ---------- "
									+ ledgerEntity.getPostType());
							List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
									.findAllById1(ledgerEntity.getElLedgerid());
							for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("WT_TAX")) {
									previousTaxAmountL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("WT_INTEREST")) {
									rateOfInterestL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("WT_TAX_INTEREST")) {
									interestOnTaxL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
							}

							logger.info("rateOfInterestL " + rateOfInterestL);
							logger.info("previousTaxAmountL "
									+ previousTaxAmountL);
							logger.info("interestOnTaxL " + interestOnTaxL);

							for (WTRateMaster wtRateMaster : waterRateMasterList) {
								if (wtRateMaster.getWtRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (billEntity
											.getArrearsAmount() - (rateOfInterestL
											+ previousTaxAmountL + interestOnTaxL));
									logger.info("arrersAmount " + arrersAmount);
									consolidatedBill = calculationController
											.interstAmountWater(wtRateMaster,
													endDate.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							for (WTRateMaster wtRateMaster : waterRateMasterList) {
								if (wtRateMaster.getWtRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmountL;
									consolidatedBill = calculationController
											.interstAmountWater(wtRateMaster,
													endDate.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}
						}
						if (billEntity.getBillAmount() > 0) {
							logger.info("billEntity.getBillAmount() "
									+ billEntity.getBillAmount());

							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(billEntity.getBillDueDate());
							Days d = Days.daysBetween(endDate, startDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "
									+ days);
							for (ElectricityBillLineItemEntity billLineItemEntity : billEntity
									.getBillLineItemEntities()) {
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("WT_TAX")) {
									previousTaxAmount = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("WT_INTEREST")) {
									rateOfInterest = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim()
										.equalsIgnoreCase("WT_TAX_INTEREST")) {
									interestOnTax = (float) billLineItemEntity
											.getBalanceAmount();
								}
							}

							for (WTRateMaster wtRateMaster : waterRateMasterList) {
								if (wtRateMaster.getWtRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (billEntity
											.getBillAmount() - (rateOfInterest
											+ previousTaxAmount + interestOnTax));
									consolidatedBill = calculationController
											.interstAmountWater(wtRateMaster,
													endDate.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							logger.info("rateOfInterest " + rateOfInterest);
							logger.info("previousTaxAmount "
									+ previousTaxAmount);
							logger.info("interestOnTax " + interestOnTax);

							for (WTRateMaster wtRateMaster : waterRateMasterList) {
								if (wtRateMaster.getWtRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmount;
									consolidatedBill = calculationController
											.interstAmountWater(wtRateMaster,
													endDate.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}

									}
								}
							}
						}
					}
				} else {
					logger.info("------------------ First Month Bill -----------------------");
				}
			} else {
				logger.info("--------------------- Interest calculation based on ----"
						+ interestType);

				ElectricityBillEntity previouBillEntity = electricityBillsService
						.getPreviousBill(accountID, typeOfService,
								previousBillDate, "Bill");
				ElectricityLedgerEntity electricityLedgerEntity;
				if (previouBillEntity != null) {
					String transactionCode = "WT";
					electricityLedgerEntity = electricityLedgerService
							.getPreviousLedger(accountID, previousBillDate,
									transactionCode);
					if (electricityLedgerEntity.getPostType().trim()
							.equalsIgnoreCase("PAYMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF "
								+ electricityLedgerEntity.getPostType());
						Date ledgerPostDate = new LocalDate(
								electricityLedgerEntity.getPostedBillDate())
								.toDateMidnight().toDate();
						Date billDueDate = new LocalDate(
								previouBillEntity.getBillDueDate())
								.toDateMidnight().toDate();
						if ((billDueDate.compareTo(ledgerPostDate)) == 0) {
							logger.info("======== payment done on same date of due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ previouBillEntity.getArrearsAmount());
							if (previouBillEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										/*
										 * startDate = new LocalDate(
										 * ledgerEntityPayments
										 * .getPostedBillDate());
										 */
										startDate = new LocalDate(
												currentBillDate);
										endDate = new LocalDate(
												previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("WT_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"WT_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"WT_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (WTRateMaster wtRateMaster : waterRateMasterList) {
											if (wtRateMaster
													.getWtRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (previouBillEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												consolidatedBill = calculationController
														.interstAmountWaterForMonthWise(
																wtRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (WTRateMaster wtRateMaster : waterRateMasterList) {
											if (wtRateMaster
													.getWtRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountWaterForMonthWise(
																wtRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}

							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						} else if (ledgerPostDate.compareTo(billDueDate) > 0) {
							logger.info("======== payment done on after due date ===============");

							if (previouBillEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										/*
										 * startDate = new LocalDate(
										 * ledgerEntityPayments
										 * .getPostedBillDate());
										 */
										startDate = new LocalDate(
												currentBillDate);
										endDate = new LocalDate(
												previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("WT_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"WT_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"WT_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (WTRateMaster wtRateMaster : waterRateMasterList) {
											if (wtRateMaster
													.getWtRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (previouBillEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												logger.info("arrersAmount "
														+ arrersAmount);
												consolidatedBill = calculationController
														.interstAmountWaterForMonthWise(
																wtRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (WTRateMaster wtRateMaster : waterRateMasterList) {
											if (wtRateMaster
													.getWtRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountWaterForMonthWise(
																wtRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("billEntity.getBillAmount() "
										+ previouBillEntity.getBillAmount());
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getBillAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										/*
										 * startDate = new LocalDate(
										 * ledgerEntityPayments
										 * .getPostedBillDate());
										 */
										startDate = new LocalDate(
												currentBillDate);
										/*
										 * endDate = new LocalDate(
										 * billEntity.getBillDueDate());
										 */
										endDate = new LocalDate(
												previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										for (ElectricityBillLineItemEntity billLineItemEntity : previouBillEntity
												.getBillLineItemEntities()) {
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("WT_TAX")) {
												previousTaxAmount = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"WT_INTEREST")) {
												rateOfInterest = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"WT_TAX_INTEREST")) {
												interestOnTax = (float) billLineItemEntity
														.getBalanceAmount();
											}
										}
										logger.info("rateOfInterest "
												+ rateOfInterest);
										logger.info("previousTaxAmount "
												+ previousTaxAmount);
										logger.info("interestOnTax "
												+ interestOnTax);
										for (WTRateMaster wtRateMaster : waterRateMasterList) {
											if (wtRateMaster
													.getWtRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (previouBillEntity
														.getBillAmount() - (rateOfInterest
														+ previousTaxAmount + interestOnTax));
												consolidatedBill = calculationController
														.interstAmountWaterForMonthWise(
																wtRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
										for (WTRateMaster wtRateMaster : waterRateMasterList) {
											if (wtRateMaster
													.getWtRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmount;
												consolidatedBill = calculationController
														.interstAmountWaterForMonthWise(
																wtRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
						} else {
							logger.info("======== payment done on before due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ previouBillEntity.getArrearsAmount());
							if (previouBillEntity.getArrearsAmount() > 0) {/*
																			 * List<
																			 * ElectricityLedgerEntity
																			 * >
																			 * ledgerEntities
																			 * =
																			 * electricityLedgerService
																			 * .
																			 * getPreviousLedgerPayments
																			 * (
																			 * accountID
																			 * ,
																			 * previousBillDate
																			 * ,
																			 * transactionCode
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "========= Number of payments done in last month ============= "
																			 * +
																			 * ledgerEntities
																			 * .
																			 * size
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * for
																			 * (
																			 * ElectricityLedgerEntity
																			 * ledgerEntityPayments
																			 * :
																			 * ledgerEntities
																			 * )
																			 * {
																			 * if
																			 * (
																			 * previouBillEntity
																			 * .
																			 * getArrearsAmount
																			 * (
																			 * )
																			 * +
																			 * ledgerEntityPayments
																			 * .
																			 * getAmount
																			 * (
																			 * )
																			 * ==
																			 * 0
																			 * )
																			 * {
																			 * LocalDate
																			 * startDate
																			 * =
																			 * null
																			 * ;
																			 * LocalDate
																			 * endDate
																			 * =
																			 * null
																			 * ;
																			 * 
																			 * startDate
																			 * =
																			 * new
																			 * LocalDate
																			 * (
																			 * ledgerEntityPayments
																			 * .
																			 * getPostedBillDate
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * 
																			 * startDate
																			 * =
																			 * new
																			 * LocalDate
																			 * (
																			 * currentBillDate
																			 * )
																			 * ;
																			 * endDate
																			 * =
																			 * new
																			 * LocalDate
																			 * (
																			 * previouBillEntity
																			 * .
																			 * getBillDate
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * Days
																			 * d
																			 * =
																			 * Days
																			 * .
																			 * daysBetween
																			 * (
																			 * startDate
																			 * ,
																			 * endDate
																			 * )
																			 * ;
																			 * int
																			 * days
																			 * =
																			 * d
																			 * .
																			 * getDays
																			 * (
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "============ Arrears Amount interst days ========= "
																			 * +
																			 * days
																			 * )
																			 * ;
																			 * 
																			 * int
																			 * lastLedgerId
																			 * =
																			 * electricityLedgerService
																			 * .
																			 * getLastArrearsLedgerBasedOnPayment
																			 * (
																			 * accountID
																			 * ,
																			 * previousBillDate
																			 * ,
																			 * transactionCode
																			 * )
																			 * .
																			 * intValue
																			 * (
																			 * )
																			 * ;
																			 * ElectricityLedgerEntity
																			 * ledgerEntity
																			 * =
																			 * electricityLedgerService
																			 * .
																			 * find
																			 * (
																			 * lastLedgerId
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "ledgerEntity ---------- "
																			 * +
																			 * ledgerEntity
																			 * .
																			 * getPostType
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * List
																			 * <
																			 * ElectricitySubLedgerEntity
																			 * >
																			 * subLedgerEntities
																			 * =
																			 * electricitySubLedgerService
																			 * .
																			 * findAllById1
																			 * (
																			 * ledgerEntity
																			 * .
																			 * getElLedgerid
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * for
																			 * (
																			 * ElectricitySubLedgerEntity
																			 * electricitySubLedgerEntity
																			 * :
																			 * subLedgerEntities
																			 * )
																			 * {
																			 * if
																			 * (
																			 * electricitySubLedgerEntity
																			 * .
																			 * getTransactionCode
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "EL_TAX"
																			 * )
																			 * )
																			 * {
																			 * previousTaxAmountL
																			 * =
																			 * (
																			 * float
																			 * )
																			 * electricitySubLedgerEntity
																			 * .
																			 * getBalanceAmount
																			 * (
																			 * )
																			 * ;
																			 * }
																			 * if
																			 * (
																			 * electricitySubLedgerEntity
																			 * .
																			 * getTransactionCode
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "EL_INTEREST"
																			 * )
																			 * )
																			 * {
																			 * rateOfInterestL
																			 * =
																			 * (
																			 * float
																			 * )
																			 * electricitySubLedgerEntity
																			 * .
																			 * getBalanceAmount
																			 * (
																			 * )
																			 * ;
																			 * }
																			 * if
																			 * (
																			 * electricitySubLedgerEntity
																			 * .
																			 * getTransactionCode
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "EL_TAX_INTEREST"
																			 * )
																			 * )
																			 * {
																			 * interestOnTaxL
																			 * =
																			 * (
																			 * float
																			 * )
																			 * electricitySubLedgerEntity
																			 * .
																			 * getBalanceAmount
																			 * (
																			 * )
																			 * ;
																			 * }
																			 * }
																			 * 
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "rateOfInterestL "
																			 * +
																			 * rateOfInterestL
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "previousTaxAmountL "
																			 * +
																			 * previousTaxAmountL
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "interestOnTaxL "
																			 * +
																			 * interestOnTaxL
																			 * )
																			 * ;
																			 * 
																			 * for
																			 * (
																			 * WTRateMaster
																			 * wtRateMaster
																			 * :
																			 * waterRateMasterList
																			 * )
																			 * {
																			 * if
																			 * (
																			 * wtRateMaster
																			 * .
																			 * getWtRateName
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "Rate of interest"
																			 * )
																			 * )
																			 * {
																			 * float
																			 * arrersAmount
																			 * =
																			 * (
																			 * float
																			 * )
																			 * (
																			 * previouBillEntity
																			 * .
																			 * getArrearsAmount
																			 * (
																			 * )
																			 * -
																			 * (
																			 * rateOfInterestL
																			 * +
																			 * previousTaxAmountL
																			 * +
																			 * interestOnTaxL
																			 * )
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "arrersAmount "
																			 * +
																			 * arrersAmount
																			 * )
																			 * ;
																			 * consolidatedBill
																			 * =
																			 * calculationController
																			 * .
																			 * interstAmountWater
																			 * (
																			 * wtRateMaster
																			 * ,
																			 * endDate
																			 * .
																			 * toDateMidnight
																			 * (
																			 * )
																			 * .
																			 * toDate
																			 * (
																			 * )
																			 * ,
																			 * startDate
																			 * .
																			 * toDateMidnight
																			 * (
																			 * )
																			 * .
																			 * toDate
																			 * (
																			 * )
																			 * ,
																			 * arrersAmount
																			 * )
																			 * ;
																			 * for
																			 * (
																			 * Entry
																			 * <
																			 * Object
																			 * ,
																			 * Object
																			 * >
																			 * map
																			 * :
																			 * consolidatedBill
																			 * .
																			 * entrySet
																			 * (
																			 * )
																			 * )
																			 * {
																			 * if
																			 * (
																			 * map
																			 * .
																			 * getKey
																			 * (
																			 * )
																			 * .
																			 * equals
																			 * (
																			 * "total"
																			 * )
																			 * )
																			 * {
																			 * interestOnArrearsAmount
																			 * =
																			 * interestOnArrearsAmount
																			 * +
																			 * (
																			 * Float
																			 * )
																			 * map
																			 * .
																			 * getValue
																			 * (
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * ":::::::::::::::: Interest  for Arrears Amount "
																			 * +
																			 * arrersAmount
																			 * +
																			 * " is "
																			 * +
																			 * (
																			 * Float
																			 * )
																			 * map
																			 * .
																			 * getValue
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * }
																			 * }
																			 * 
																			 * }
																			 * }
																			 * for
																			 * (
																			 * WTRateMaster
																			 * wtRateMaster
																			 * :
																			 * waterRateMasterList
																			 * )
																			 * {
																			 * if
																			 * (
																			 * wtRateMaster
																			 * .
																			 * getWtRateName
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "Rate of interest"
																			 * )
																			 * )
																			 * {
																			 * float
																			 * taxAmount
																			 * =
																			 * previousTaxAmountL
																			 * ;
																			 * consolidatedBill
																			 * =
																			 * calculationController
																			 * .
																			 * interstAmountWater
																			 * (
																			 * wtRateMaster
																			 * ,
																			 * endDate
																			 * .
																			 * toDateMidnight
																			 * (
																			 * )
																			 * .
																			 * toDate
																			 * (
																			 * )
																			 * ,
																			 * startDate
																			 * .
																			 * toDateMidnight
																			 * (
																			 * )
																			 * .
																			 * toDate
																			 * (
																			 * )
																			 * ,
																			 * taxAmount
																			 * )
																			 * ;
																			 * for
																			 * (
																			 * Entry
																			 * <
																			 * Object
																			 * ,
																			 * Object
																			 * >
																			 * map
																			 * :
																			 * consolidatedBill
																			 * .
																			 * entrySet
																			 * (
																			 * )
																			 * )
																			 * {
																			 * if
																			 * (
																			 * map
																			 * .
																			 * getKey
																			 * (
																			 * )
																			 * .
																			 * equals
																			 * (
																			 * "total"
																			 * )
																			 * )
																			 * {
																			 * interestOnTaxAmount
																			 * =
																			 * interestOnTaxAmount
																			 * +
																			 * (
																			 * Float
																			 * )
																			 * map
																			 * .
																			 * getValue
																			 * (
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * ":::::::::::::::: Interest for Tax Amount "
																			 * +
																			 * taxAmount
																			 * +
																			 * " is "
																			 * +
																			 * (
																			 * Float
																			 * )
																			 * map
																			 * .
																			 * getValue
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * }
																			 * }
																			 * 
																			 * }
																			 * }
																			 * }
																			 * }
																			 */
							}
							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						}

					} else if (electricityLedgerEntity.getPostType().trim()
							.equalsIgnoreCase("ADJUSTMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF Else "
								+ electricityLedgerEntity.getPostType());
					} else {
						logger.info("electricityLedgerEntity.getPostType ---- in Else "
								+ electricityLedgerEntity.getPostType());
						if (previouBillEntity.getArrearsAmount() > 0) {
							logger.info("billEntity.getArrearsAmount() "
									+ previouBillEntity.getArrearsAmount());
							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(
									previouBillEntity.getBillDate());
							Days d = Days.daysBetween(startDate, endDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "
									+ days);

							int lastLedgerId = electricityLedgerService
									.getLastArrearsLedgerBasedOnPayment(
											accountID, previousBillDate,
											transactionCode).intValue();
							ElectricityLedgerEntity ledgerEntity = electricityLedgerService
									.find(lastLedgerId);
							logger.info("ledgerEntity ---------- "
									+ ledgerEntity.getPostType());
							List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
									.findAllById1(ledgerEntity.getElLedgerid());
							for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("WT_TAX")) {
									previousTaxAmountL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("WT_INTEREST")) {
									rateOfInterestL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("WT_TAX_INTEREST")) {
									interestOnTaxL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
							}

							logger.info("rateOfInterestL " + rateOfInterestL);
							logger.info("previousTaxAmountL "
									+ previousTaxAmountL);
							logger.info("interestOnTaxL " + interestOnTaxL);

							for (WTRateMaster wtRateMaster : waterRateMasterList) {
								if (wtRateMaster.getWtRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (previouBillEntity
											.getArrearsAmount() - (rateOfInterestL
											+ previousTaxAmountL + interestOnTaxL));
									logger.info("arrersAmount " + arrersAmount);
									consolidatedBill = calculationController
											.interstAmountWaterForMonthWise(
													wtRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							for (WTRateMaster wtRateMaster : waterRateMasterList) {
								if (wtRateMaster.getWtRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmountL;
									consolidatedBill = calculationController
											.interstAmountWaterForMonthWise(
													wtRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}
						}
						if (previouBillEntity.getBillAmount() > 0) {
							logger.info("billEntity.getBillAmount() "
									+ previouBillEntity.getBillAmount());

							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(
									previouBillEntity.getBillDate());
							Days d = Days.daysBetween(endDate, startDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "
									+ days);
							for (ElectricityBillLineItemEntity billLineItemEntity : previouBillEntity
									.getBillLineItemEntities()) {
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("WT_TAX")) {
									previousTaxAmount = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("WT_INTEREST")) {
									rateOfInterest = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim()
										.equalsIgnoreCase("WT_TAX_INTEREST")) {
									interestOnTax = (float) billLineItemEntity
											.getBalanceAmount();
								}
							}

							for (WTRateMaster wtRateMaster : waterRateMasterList) {
								if (wtRateMaster.getWtRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (previouBillEntity
											.getBillAmount() - (rateOfInterest
											+ previousTaxAmount + interestOnTax));
									consolidatedBill = calculationController
											.interstAmountWaterForMonthWise(
													wtRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							logger.info("rateOfInterest " + rateOfInterest);
							logger.info("previousTaxAmount "
									+ previousTaxAmount);
							logger.info("interestOnTax " + interestOnTax);

							for (WTRateMaster wtRateMaster : waterRateMasterList) {
								if (wtRateMaster.getWtRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmount;
									consolidatedBill = calculationController
											.interstAmountWaterForMonthWise(
													wtRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}
						}
					}
				} else {
					logger.info("------------------ First Month Bill -----------------------");
				}
			}
		}
		intersts.put("interestOnTaxAmount", interestOnTaxAmount);
		intersts.put("interestOnArrearsAmount", interestOnArrearsAmount);
		return intersts;
	}

	private void saveBillWaterParameters(ElectricityBillEntity billEntity,
			List<WTRateMaster> waterRateMasterList, int accountID,
			String typeOfService, Date previousBillDate, Date currentBillDate,
			Float uomValue, Integer billableDays, String presentMeterStaus,
			String previousMeterStaus, Float meterConstant,
			Float presentUOMReading, Float previousUOMReading,
			String wtSlabString, String meterchangeUnit) {
		List<BillParameterMasterEntity> billParameterMasterEntitiesList = billingParameterMasterService
				.getBillParameterMasterByServiceType(typeOfService);
		logger.info("billParameterMasterEntitiesList ::::::::::: "
				+ billParameterMasterEntitiesList.size());

		for (BillParameterMasterEntity billParameterMasterEntity : billParameterMasterEntitiesList) {
			logger.info("Bill parameters servic type :::"
					+ billParameterMasterEntity.getServiceType());

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Number of days")) {
				ElectricityBillParametersEntity noOfDays = new ElectricityBillParametersEntity(
						"Integer", billableDays.toString(), "Active");
				noOfDays.setBillParameterMasterEntity(billParameterMasterEntity);
				noOfDays.setElectricityBillEntity(billEntity);
				billParameterService.save(noOfDays);
			}
			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Previous status")) {
				ElectricityBillParametersEntity previousStatus = new ElectricityBillParametersEntity(
						"String", previousMeterStaus, "Active");
				previousStatus
						.setBillParameterMasterEntity(billParameterMasterEntity);
				previousStatus.setElectricityBillEntity(billEntity);
				billParameterService.save(previousStatus);
			}
			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Present Status")) {
				if (presentMeterStaus.equalsIgnoreCase("Meter Change")) {
					ElectricityBillParametersEntity presentStatus = new ElectricityBillParametersEntity(
							"String", presentMeterStaus, "Active",
							meterchangeUnit);
					presentStatus
							.setBillParameterMasterEntity(billParameterMasterEntity);
					presentStatus.setElectricityBillEntity(billEntity);
					billParameterService.update(presentStatus);
				} else {
					ElectricityBillParametersEntity presentStatus = new ElectricityBillParametersEntity(
							"String", presentMeterStaus, "Active");
					presentStatus
							.setBillParameterMasterEntity(billParameterMasterEntity);
					presentStatus.setElectricityBillEntity(billEntity);
					billParameterService.update(presentStatus);
				}

			}
			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Previous reading")) {
				ElectricityBillParametersEntity reviousReading = new ElectricityBillParametersEntity(
						"Integer", previousUOMReading.toString(), "Active");
				reviousReading
						.setBillParameterMasterEntity(billParameterMasterEntity);
				reviousReading.setElectricityBillEntity(billEntity);
				billParameterService.save(reviousReading);
			}

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Present reading")) {
				ElectricityBillParametersEntity presentReading = new ElectricityBillParametersEntity(
						"Integer", presentUOMReading.toString().toString(),
						"Active");
				presentReading
						.setBillParameterMasterEntity(billParameterMasterEntity);
				presentReading.setElectricityBillEntity(billEntity);
				billParameterService.save(presentReading);
			}

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Meter Constant")) {
				ElectricityBillParametersEntity meterConstantObject = new ElectricityBillParametersEntity(
						"Date", meterConstant.toString(), "Active");
				meterConstantObject
						.setBillParameterMasterEntity(billParameterMasterEntity);
				meterConstantObject.setElectricityBillEntity(billEntity);
				billParameterService.save(meterConstantObject);
			}

			if (billParameterMasterEntity.getBvmName()
					.equalsIgnoreCase("Units")) {
				ElectricityBillParametersEntity units = new ElectricityBillParametersEntity(
						"Integer", uomValue.toString(), "Active");
				units.setBillParameterMasterEntity(billParameterMasterEntity);
				units.setElectricityBillEntity(billEntity);
				billParameterService.save(units);
			}

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"WC Slabs")) {

				if (wtSlabString != null) {
					wtSlabString = wtSlabString.replace("null", " ");
					wtSlabString = wtSlabString.replace("</br>", " ");
					ElectricityBillParametersEntity slabs = new ElectricityBillParametersEntity(
							"String", "NA", "Active");
					slabs.setNotes(wtSlabString);
					slabs.setBillParameterMasterEntity(billParameterMasterEntity);
					slabs.setElectricityBillEntity(billEntity);
					billParameterService.update(slabs);
				}
			}
		}
	}

	
	@SuppressWarnings({ "unused", "rawtypes" })
	@Transactional(propagation=Propagation.REQUIRED)
	public HashMap<String, Object> saveElectricityBillParameters(
			List<ELRateMaster> elRateMasterList, int accountID,
			String typeOfService, Date previousBillDate, Date currentBillDate,
			Float uomValue, Integer billableDays, String presentMeterStaus,
			String previousMeterStaus, Float meterConstant,
			Float presentUOMReading, Float previousUOMReading, Integer todT1,
			Integer todT2, Integer todT3, Float dgunit, Object todApplicable,
			Object dgApplicable, Float previousDgreading,
			Float PresentDgReading, Float dgmeterconstant, Double avgUnits,
			Integer avgCount, String billType, float pfReading,
			float connectedLoad, String postType, String meterchangeUnit) {
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		ElectricityBillEntity previousBillEntity = electricityBillsService.getPreviousBill(accountID, typeOfService, previousBillDate,"Bill");

		float ecAmount = 0;
		float mmcAmount = 0;
		float taxAmount = 0;
		float solarRebate = 0;
		float totalAmount = 0;
		Float roundoffValue = 0f;
		float fsaAmount = 0f;
		float rateOfInterest = 0f;
		float arrearsAmount = 0f;
		float fcAmount = 0f;
		float octroiAmount = 0f;
		float dgAMount = 0f;
		Double avgAmount = 0d;
		float mdiPenaltyAmount = 0;
		float pfRebate = 0;
		float pfPenalty = 0;
		String ecString = "";
		String fsaString = "";
		String todString = "";
		float dgFixcedAmount = 0;
		String tariffChange = null;
		Float latePaymentAnount=0f;
		int rateMasterID = 0;
		int rateMasterIdRateOfInterest=0;
		double checkAmount=0;
		double bankcharge=0;
		double previouslatepaymentamount=0;
		float totalbankcharge=0;
		Set<ElectricityBillLineItemEntity> billLineItemEntities = new LinkedHashSet<>();
		
		ElectricityBillLineItemEntity ecBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity mmcBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity taxBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity roundOffBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity fuelBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity solarBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity fcBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity octroiBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity dgBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity dgFixcedBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity pfPenaltyBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity pfRebateBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity mdiPenalty = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity latePaymentCharges = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity checkbounceAmount = new ElectricityBillLineItemEntity();
		double arrearAmoun=getLastBillArrearsAmount("ARREARS", accountID, typeOfService);
		
		
    /*    if(arrearAmoun > 0){
        List<?> checkbouncedetail=tariffCalculationService.getcheckbounceDetail(accountID);	
        for (Iterator iterator = checkbouncedetail.iterator(); iterator.hasNext();) {
			Object values[] = (Object[]) iterator.next();
			if (((String) values[0]).equalsIgnoreCase("Electricity Ledger")) {
				checkAmount = (Double) values[0];
				bankcharge = (Double) values[1];
				previouslatepaymentamount = (Double) values[2];
				latePaymentAnount = (float) (latePaymentAnount + previouslatepaymentamount);
				totalbankcharge = (float) (checkAmount + bankcharge);
				logger.info("checkAmount:::::::::" + checkAmount
						+ ":::::::::cbankcharge" + bankcharge
						+ "previouslatepaymentamount"
						+ previouslatepaymentamount);
				latePaymentCharges.setBalanceAmount(latePaymentAnount);
				latePaymentCharges.setTransactionCode("EL_LPC");
				latePaymentCharges.setCreditAmount(0);
				latePaymentCharges.setDebitAmount(0);
				latePaymentCharges.setStatus("Not Approved");
				latePaymentCharges.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				latePaymentCharges.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				latePaymentCharges.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(latePaymentCharges);
				checkbounceAmount.setBalanceAmount(totalbankcharge);
				checkbounceAmount.setTransactionCode("EL_CP");
				checkbounceAmount.setCreditAmount(0);
				checkbounceAmount.setDebitAmount(0);
				checkbounceAmount.setStatus("Not Approved");
				checkbounceAmount.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				checkbounceAmount.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				checkbounceAmount.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(checkbounceAmount);
			}
			
		}
        
        }*/
		
            String ledgertype=null;
            List<?> checkbouncedetail=tariffCalculationService.getcheckbounceDetail(accountID);	
            for (Iterator iterator = checkbouncedetail.iterator(); iterator.hasNext();) {
    			Object values[] = (Object[]) iterator.next();
    			ledgertype=((String) values[4]);
    			checkAmount = checkAmount+(Double) values[1];
				bankcharge = bankcharge+(Double) values[2];
				previouslatepaymentamount = previouslatepaymentamount+(Double) values[3];
				latePaymentAnount = (float) (latePaymentAnount + previouslatepaymentamount);
				totalbankcharge = (float) (checkAmount + bankcharge);
				logger.info("checkAmount:::::::::" + checkAmount
						+ ":::::::::cbankcharge" + bankcharge
						+ "previouslatepaymentamount"
						+ previouslatepaymentamount);
    			
    			
    		}
            if (checkbouncedetail.size()>0) {
				if ((ledgertype).equalsIgnoreCase("Electricity Ledger")) {

					latePaymentCharges.setBalanceAmount(latePaymentAnount);
					latePaymentCharges.setTransactionCode("EL_LPC");
					latePaymentCharges.setCreditAmount(0);
					latePaymentCharges.setDebitAmount(0);
					latePaymentCharges.setStatus("Not Approved");
					latePaymentCharges.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
					latePaymentCharges.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
					latePaymentCharges.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
					billLineItemEntities.add(latePaymentCharges);
					checkbounceAmount.setBalanceAmount(totalbankcharge);
					checkbounceAmount.setTransactionCode("EL_CP");
					checkbounceAmount.setCreditAmount(0);
					checkbounceAmount.setDebitAmount(0);
					checkbounceAmount.setStatus("Not Approved");
					checkbounceAmount.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
					checkbounceAmount.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
					checkbounceAmount.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
					billLineItemEntities.add(checkbounceAmount);
				} 
			}
		ElectricityBillEntity billEntityForArrears = new ElectricityBillEntity();
		billEntityForArrears.setAccountId(accountID);
		billEntityForArrears.setArrearsAmount(arrearAmoun);
		logger.info("currentBillDate =================== "+currentBillDate);
		billEntityForArrears.setElBillDate(new java.sql.Timestamp(currentBillDate.getTime()));
		billEntityForArrears.setPostType("Bill");
		billEntityForArrears.setBillDate(new java.sql.Date(currentBillDate.getTime()));
		String configName = "Due Days";
		String status = "Active";
		String dueDays = electricityBillsService.getBillingConfigValue(configName,status);
		int days=0;
		if(dueDays!=null)
		{
			days = Integer.parseInt(dueDays);
		}
		
		Date billDueDate = addDays(currentBillDate, days);
		billEntityForArrears.setBillDueDate(new java.sql.Date(billDueDate.getTime()));
		Calendar c = Calendar.getInstance();
		c.setTime(currentBillDate);
		c.set(Calendar.DAY_OF_MONTH, 1);

		java.sql.Date billMonth = new java.sql.Date(c.getTime().getTime());
		logger.info("billMonth ::::::::::::::: " + billMonth);
		billEntityForArrears.setBillMonth(billMonth);
		billEntityForArrears.setStatus("Generated");
		billEntityForArrears.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
		billEntityForArrears.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		billEntityForArrears.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
		billEntityForArrears.setTypeOfService(typeOfService);
		billEntityForArrears.setBillNo("BILL"+ billParameterService.getSequencyNumber());
		logger.info("billEntity.getBillDueDate() ::::::::::::::: "+ billEntityForArrears.getBillDueDate());
		storeArrears("ARREARS", billEntityForArrears);
		
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		Float uomValueTest=0f;
		if(uomValue==0){
			uomValueTest=0f;
		}else{
			uomValueTest =  netMonth /uomValue;	
		}
		for (ELRateMaster elRateMaster : elRateMasterList) {
			rateMasterID = elRateMaster.getElrmid();
			
			if (elRateMaster.getRateName().equalsIgnoreCase("Rate of interest")) {
				rateMasterIdRateOfInterest=elRateMaster.getElrmid();
			}
			if (elRateMaster.getRateName().equalsIgnoreCase("EC")) {

				String hariyanaTairff = elTariffMasterService.getStateName(elRateMaster.getElTariffID());
				logger.info("hariyanaTairff =========== "+hariyanaTairff);
				ELRateMaster elRateMaster1 =null;
				String category = "None";
				if(hariyanaTairff!=null){
					if(hariyanaTairff.equalsIgnoreCase("Haryana")){
						logger.info("uomValueTest ============= "+uomValueTest);
						logger.info(" IF 1"+(uomValueTest>=0 && uomValueTest<=800));
						logger.info(":::::::rate master category:::::::"+elRateMaster.getRateNameCategory());
						if (elRateMaster.getRateNameCategory().equalsIgnoreCase("None")) {
							elRateMaster1 = rateMasterService.getRateMasterByRateName(elRateMaster.getElTariffID(),"EC", category);
						}else{
							if (uomValueTest >= 0 && uomValueTest <= 800) {
								logger.info(" ============ EC category 1 tariff ======================");
								category = ResourceBundle.getBundle("utils")
										.getString("category1");
								logger.info("category1Tariff ================= "+ category);
								if (category != null) {
									elRateMaster1 = rateMasterService
											.getRateMasterByRateName(
													elRateMaster
															.getElTariffID(),
													"EC", category);
								}
							} else if (uomValueTest > 800) {
								category = ResourceBundle.getBundle("utils").getString("category2");
								logger.info("category1Tariff ================= "+ category);
								if (category != null) {
									elRateMaster1 = rateMasterService.getRateMasterByRateName(elRateMaster.getElTariffID(),"EC", category);
								}
							}	
						}
					}else {
						logger.info("IF not hariyan =============== ");
						elRateMaster1 = elRateMaster;
						}
				}
				
				ecBillLineItemEntity.setTransactionCode("EL_EC");
				if (todApplicable.equals("Yes")) {
					logger.info("::::::::::::::Tod is applicable::::::::::::");
					consolidatedBill = tariffCalculationService.tariffTod(
							elRateMaster1, todT1, todT2, todT3,
							previousBillDate, currentBillDate);
					for (Entry<Object, Object> map : consolidatedBill
							.entrySet()) {
						if (map.getKey().equals("total")) {
							ecAmount = ecAmount + (Float) map.getValue();
						}
						if (map.getKey().equals("todslab")) {
							todString = todString + (String) map.getValue();
							logger.info("::::::::todString:::::" + todString);
						}
						logger.info("::::::::todString:::::" + todString);
					}
					consolidatedBill = calculationController.tariffAmount(
							elRateMaster1, previousBillDate, currentBillDate,
							uomValue, 0.0f,category);
					for (Entry<Object, Object> map : consolidatedBill
							.entrySet()) {
						if (map.getKey().equals("slabString")) {
							ecString = (String) map.getValue();
							logger.info("");
						}
					}
				} else {
					if (presentMeterStaus.equalsIgnoreCase("Door Lock")
							|| presentMeterStaus
									.equalsIgnoreCase("Meter Not Reading")
							|| presentMeterStaus
									.equalsIgnoreCase("Direct Connection")
							|| presentMeterStaus.equalsIgnoreCase("Burnt")
							|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
						consolidatedBill = calculationController.tariffAmount(
								elRateMaster1, previousBillDate,
								currentBillDate, uomValue, 0,category);
					} else {
						if (previousBillEntity != null) {
							if (previousBillEntity.getAvgCount() >= 1) {
								consolidatedBill = calculationController
										.tariffAmountAvgCount(elRateMaster1,
												previousBillDate,
												currentBillDate,
												previousBillEntity.getAvgCount(),
												uomValue, 0.0f,category);
							} else {
								consolidatedBill = calculationController.tariffAmount(elRateMaster1,previousBillDate,
												currentBillDate, uomValue, 0,category);
							}
						} else {
							consolidatedBill = calculationController
									.tariffAmount(elRateMaster1,
											previousBillDate, currentBillDate,
											uomValue, 0,category);
						}
					}
					for (Entry<Object, Object> map : consolidatedBill
							.entrySet()) {

						if (map.getKey().equals("totalAmount")) {
							ecAmount = (Float) map.getValue();
						}
						if (map.getKey().equals("slabString")) {
							ecString = (String) map.getValue();
						}

						if (map.getKey().equals("tariffChange")) {
							tariffChange = (String) map.getValue();
						}
					}
				}
				ecBillLineItemEntity.setBalanceAmount(ecAmount);
				ecBillLineItemEntity.setCreditAmount(0);
				ecBillLineItemEntity.setDebitAmount(0);
				ecBillLineItemEntity.setStatus("Not Approved");
				ecBillLineItemEntity.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				ecBillLineItemEntity.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				ecBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(ecBillLineItemEntity);
			}

			if (elRateMaster.getRateName().equalsIgnoreCase("Octroi")) {
				octroiBillLineItemEntity.setTransactionCode("EL_OCTROI");
				consolidatedBill = calculationController.tariffAmount(
						elRateMaster, previousBillDate, currentBillDate,
						ecAmount, 0,elRateMaster.getRateNameCategory());
				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("totalAmount")) {
						octroiAmount = (Float) map.getValue();
					}
				}
				octroiBillLineItemEntity.setBalanceAmount(octroiAmount);
				octroiBillLineItemEntity.setBalanceAmount(octroiAmount);
				octroiBillLineItemEntity.setCreditAmount(0);
				octroiBillLineItemEntity.setDebitAmount(0);
				octroiBillLineItemEntity.setStatus("Not Approved");
				octroiBillLineItemEntity.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				octroiBillLineItemEntity.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				octroiBillLineItemEntity
						.setLastUpdatedDT(new java.sql.Timestamp(new Date()
								.getTime()));
				billLineItemEntities.add(octroiBillLineItemEntity);
			}

			if (elRateMaster.getRateName().equalsIgnoreCase("MMC")) {
				
				ServiceMastersEntity mastersEntity1 = serviceMasterService
						.getServiceMasterServicType(accountID, typeOfService);
				List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService
						.findAllByParentId(mastersEntity1.getServiceMasterId());
				float fcUomValue = 0;
				for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1) {

					if (serviceParametersEntity.getServiceParameterMaster()
							.getSpmName().equalsIgnoreCase("Sanctioned KW")
							|| serviceParametersEntity
									.getServiceParameterMaster().getSpmName()
									.equalsIgnoreCase("Sanctioned KVA")
							|| serviceParametersEntity
									.getServiceParameterMaster().getSpmName()
									.equalsIgnoreCase("Sanctioned HP")) {
						fcUomValue = Float.parseFloat(serviceParametersEntity
								.getServiceParameterValue());
					}
				}

				if (presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus
								.equalsIgnoreCase("Meter Not Reading")
						|| presentMeterStaus
								.equalsIgnoreCase("Direct Connection")
						|| presentMeterStaus.equalsIgnoreCase("Burnt")
						|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
					consolidatedBill = calculationController.tariffAmount(
							elRateMaster, previousBillDate, currentBillDate,
							fcUomValue, 0,elRateMaster.getRateNameCategory());
				} else {
					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCount(elRateMaster,
											previousBillDate, currentBillDate,
											previousBillEntity.getAvgCount(),
											fcUomValue, 0.0f,elRateMaster.getRateNameCategory());
						} else {
							consolidatedBill = calculationController
									.tariffAmount(elRateMaster,
											previousBillDate, currentBillDate,
											fcUomValue, 0,elRateMaster.getRateNameCategory());
						}
					} else {
						consolidatedBill = calculationController.tariffAmount(
								elRateMaster, previousBillDate,
								currentBillDate, fcUomValue, 0,elRateMaster.getRateNameCategory());
					}
				}
				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("totalAmount")) {
						mmcAmount = (Float) map.getValue();
						logger.info("fcAmount :::::::::: " + fcAmount);
					}
				}
				mmcBillLineItemEntity.setTransactionCode("EL_MMC");
				mmcBillLineItemEntity.setBalanceAmount(mmcAmount);
				mmcBillLineItemEntity.setCreditAmount(0);
				mmcBillLineItemEntity.setDebitAmount(0);
				mmcBillLineItemEntity.setStatus("Not Approved");
				mmcBillLineItemEntity.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				mmcBillLineItemEntity.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				mmcBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(mmcBillLineItemEntity);
			}

			if (elRateMaster.getRateName().equalsIgnoreCase("FC")) {
				ServiceMastersEntity mastersEntity1 = serviceMasterService
						.getServiceMasterServicType(accountID, typeOfService);
				List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService
						.findAllByParentId(mastersEntity1.getServiceMasterId());
				float fcUomValue = 0;
				for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1) {

					if (serviceParametersEntity.getServiceParameterMaster()
							.getSpmName().equalsIgnoreCase("Sanctioned KW")
							|| serviceParametersEntity
									.getServiceParameterMaster().getSpmName()
									.equalsIgnoreCase("Sanctioned KVA")
							|| serviceParametersEntity
									.getServiceParameterMaster().getSpmName()
									.equalsIgnoreCase("Sanctioned HP")) {
						fcUomValue = Float.parseFloat(serviceParametersEntity
								.getServiceParameterValue());
					}
				}

				if (presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus
								.equalsIgnoreCase("Meter Not Reading")
						|| presentMeterStaus
								.equalsIgnoreCase("Direct Connection")
						|| presentMeterStaus.equalsIgnoreCase("Burnt")
						|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
					consolidatedBill = calculationController.tariffAmount(
							elRateMaster, previousBillDate, currentBillDate,
							fcUomValue, 0,elRateMaster.getRateNameCategory());
				} else {
					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCount(elRateMaster,
											previousBillDate, currentBillDate,
											previousBillEntity.getAvgCount(),
											fcUomValue, 0.0f,elRateMaster.getRateNameCategory());
						} else {
							consolidatedBill = calculationController
									.tariffAmount(elRateMaster,
											previousBillDate, currentBillDate,
											fcUomValue, 0,elRateMaster.getRateNameCategory());
						}
					} else {
						consolidatedBill = calculationController.tariffAmount(
								elRateMaster, previousBillDate,
								currentBillDate, fcUomValue, 0,elRateMaster.getRateNameCategory());
					}
				}
				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("totalAmount")) {
						fcAmount = (Float) map.getValue();
						logger.info("fcAmount :::::::::: " + fcAmount);
					}
				}
				fcBillLineItemEntity.setTransactionCode("EL_FC");
				fcBillLineItemEntity.setBalanceAmount(fcAmount);
				fcBillLineItemEntity.setCreditAmount(0);
				fcBillLineItemEntity.setDebitAmount(0);
				fcBillLineItemEntity.setStatus("Not Approved");
				fcBillLineItemEntity.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				fcBillLineItemEntity.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				fcBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(fcBillLineItemEntity);
			}

			if (elRateMaster.getRateName().equalsIgnoreCase("Solar Rebate")) {
				ServiceMastersEntity mastersEntity = serviceMasterService
						.getServiceMasterServicType(accountID, typeOfService);
				List<ServiceParametersEntity> serviceParametersEntities = serviceParameterService
						.findAllByParentId(mastersEntity.getServiceMasterId());

				for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities) {

					if (serviceParametersEntity.getServiceParameterMaster()
							.getSpmName().equalsIgnoreCase("Solar Rebate")) {
						solarBillLineItemEntity
								.setTransactionCode("EL_Rebate_EC");

						String solarrebateType = getreabateType(elRateMaster
								.getElrmid());
						logger.info("::::::::::::solarrebateType:::::::::"
								+ solarrebateType);
					
						if (elRateMaster.getRateType().equalsIgnoreCase(
								"Fixed Slab")) {
							List<ELRateSlabs> elRateSlabs = elRateSlabService
									.findRateSlabByID(elRateMaster.getElrmid());
                         		    consolidatedBill = calculationController
									.tariffAmount(elRateMaster,
											previousBillDate, currentBillDate,
											1.f, 0,elRateMaster.getRateNameCategory());
							for (Entry<Object, Object> map : consolidatedBill
									.entrySet()) {
								if (map.getKey().equals("totalAmount")) {
									solarRebate = (Float) map.getValue();
									logger.info("::::::::solarRebate::::::::::"
											+ solarRebate);
								}
							}

						} else {
							consolidatedBill = calculationController
									.tariffAmount(elRateMaster,
											previousBillDate, currentBillDate,
											ecAmount, 0,elRateMaster.getRateNameCategory());
							for (Entry<Object, Object> map : consolidatedBill
									.entrySet()) {
								if (map.getKey().equals("totalAmount")) {
									solarRebate = (Float) map.getValue();
									logger.info("solarRebate ::::::else:::: "
											+ solarRebate);
								}
							}
						}
						solarBillLineItemEntity.setBalanceAmount(solarRebate);

						solarBillLineItemEntity.setCreditAmount(0);
						solarBillLineItemEntity.setDebitAmount(0);
						solarBillLineItemEntity.setStatus("Not Approved");
						solarBillLineItemEntity
								.setCreatedBy((String) SessionData
										.getUserDetails().get("userID"));
						solarBillLineItemEntity
								.setLastUpdatedBy((String) SessionData
										.getUserDetails().get("userID"));
						solarBillLineItemEntity
								.setLastUpdatedDT(new java.sql.Timestamp(
										new Date().getTime()));
						billLineItemEntities.add(solarBillLineItemEntity);

					} else {
						logger.info("::::::::::::::Solar Rebate Not Applicable::::::::::::::");
					}
				}
			}

			if (elRateMaster.getRateName().equalsIgnoreCase("FSA")) {

			String hariyanaTairff = elTariffMasterService.getStateName(elRateMaster.getElTariffID());
				logger.info("hariyanaTairff =========== "+hariyanaTairff);
				ELRateMaster elRateMaster1 = null;
				String category = "None";
				if(hariyanaTairff!=null){
					if(hariyanaTairff.equalsIgnoreCase("Haryana")){
						if (elRateMaster.getRateNameCategory().equalsIgnoreCase("None")) {
							elRateMaster1 = rateMasterService
									.getRateMasterByRateName(
											elRateMaster
													.getElTariffID(),
											"FSA", category);
						}else{
							if (uomValueTest >= 0 && uomValueTest <= 100) {
								logger.info(" ============ FSA category 1 tariff ======================");
								category = ResourceBundle.getBundle("utils")
										.getString("category1");
								logger.info("category1Tariff ================= "
										+ category);
								if (category != null) {
									elRateMaster1 = rateMasterService
											.getRateMasterByRateName(
													elRateMaster
															.getElTariffID(),
													"FSA", category);
								}
							} else if (uomValueTest > 100
									&& uomValueTest <= 800) {
								logger.info(" ============ FSA category 2 tariff ======================");
								category = ResourceBundle.getBundle("utils")
										.getString("category2");
								logger.info("category1Tariff ================= "
										+ category);
								if (category != null) {
									elRateMaster1 = rateMasterService
											.getRateMasterByRateName(
													elRateMaster
															.getElTariffID(),
													"FSA", category);
								}
							} else if (uomValueTest > 800) {
								logger.info(" ============ FSA category 3 tariff ======================");
								category = ResourceBundle.getBundle("utils")
										.getString("category3");
								logger.info("category1Tariff ================= "
										+ category);
								if (category != null) {
									elRateMaster1 = rateMasterService
											.getRateMasterByRateName(
													elRateMaster
															.getElTariffID(),
													"FSA", category);
								}
							}	
						}
					}else {
						 elRateMaster1 = elRateMaster;
					}
				}
				
/*				String hariyanaTairff = elTariffMasterService.getStateName(elRateMaster.getElTariffID());
				logger.info("hariyanaTairff =========== "+hariyanaTairff);
				ELRateMaster elRateMaster1 =null;
				String category = "None";
				if(hariyanaTairff!=null){
					if(hariyanaTairff.equalsIgnoreCase("Haryana")){
						logger.info("uomValueTest ============= "+uomValueTest);
						logger.info(" IF 1"+(uomValueTest>=0 && uomValueTest<=800));
						logger.info(":::::::rate master category:::::::"+elRateMaster.getRateNameCategory());
						if (elRateMaster.getRateNameCategory().equalsIgnoreCase("None")) {
							
							elRateMaster1 = rateMasterService
									.getRateMasterByRateName(
											elRateMaster
													.getElTariffID(),
											"EC", category);
						}else{
							if (uomValueTest >= 0 && uomValueTest <= 800) {
								logger.info(" ============ EC category 1 tariff ======================");
								category = ResourceBundle.getBundle("utils")
										.getString("category1");
								logger.info("category1Tariff ================= "
										+ category);
								if (category != null) {
									elRateMaster1 = rateMasterService
											.getRateMasterByRateName(
													elRateMaster
															.getElTariffID(),
													"EC", category);
								}
							} else if (uomValueTest > 800) {
								category = ResourceBundle.getBundle("utils")
										.getString("category2");
								logger.info("category1Tariff ================= "
										+ category);
								if (category != null) {
									elRateMaster1 = rateMasterService
											.getRateMasterByRateName(
													elRateMaster
															.getElTariffID(),
													"EC", category);
								}
							}	
						}
					}else {
						logger.info("IF not hariyan =============== ");
						elRateMaster1 = elRateMaster;
						}
				}*/
				fuelBillLineItemEntity.setTransactionCode("EL_FSA");
				if (presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus
								.equalsIgnoreCase("Meter Not Reading")
						|| presentMeterStaus
								.equalsIgnoreCase("Direct Connection")
						|| presentMeterStaus.equalsIgnoreCase("Burnt")
						|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
					consolidatedBill = calculationController.tariffAmount(
							elRateMaster, previousBillDate, currentBillDate,
							uomValue, 0,elRateMaster.getRateNameCategory());
				} else {
					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCount(elRateMaster,
											previousBillDate, currentBillDate,
											previousBillEntity.getAvgCount(),
											uomValue, 0.0f,category);
						} else {
							consolidatedBill = calculationController
									.tariffAmount(elRateMaster,
											previousBillDate, currentBillDate,
											uomValue, 0,category);
						}
					} else {
						consolidatedBill = calculationController.tariffAmount(
								elRateMaster, previousBillDate,
								currentBillDate, uomValue, 0,category);
					}
				}

				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("totalAmount")) {
						fsaAmount = (Float) map.getValue();
					}
					if (map.getKey().equals("slabString")) {
						fsaString = (String) map.getValue();
					}
				}
				fuelBillLineItemEntity.setBalanceAmount(fsaAmount);
				fuelBillLineItemEntity.setCreditAmount(0);
				fuelBillLineItemEntity.setDebitAmount(0);
				fuelBillLineItemEntity.setStatus("Not Approved");
				fuelBillLineItemEntity.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				fuelBillLineItemEntity.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				fuelBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(fuelBillLineItemEntity);
			}
			logger.info(":::::::::::::::::::::::::::::" + dgApplicable);
			String dgstring = "";
			if (dgApplicable != null) {
				if (dgApplicable.equals("Yes")) {
					if (elRateMaster.getRateName().equalsIgnoreCase("DG")) {
						dgBillLineItemEntity.setTransactionCode("EL_DG");
						consolidatedBill = calculationController.tariffAmount(
								elRateMaster, previousBillDate,
								currentBillDate, dgunit, 0,elRateMaster.getRateNameCategory());
						for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
							if (map.getKey().equals("total")) {
								dgAMount = dgAMount + (Float) map.getValue();
								logger.info("::::::::dgAMount::::::::::"+ dgAMount);
							}
							if (map.getKey().equals("slabString")) {
								dgstring = (String) map.getValue();
								logger.info(":::::::::::dgstring::::::::::"+ dgstring);
							}
						}
						dgBillLineItemEntity.setBalanceAmount(dgAMount);
						dgBillLineItemEntity.setCreditAmount(0);
						dgBillLineItemEntity.setDebitAmount(0);
						dgBillLineItemEntity.setStatus("Not Approved");
						dgBillLineItemEntity.setCreatedBy((String) SessionData
								.getUserDetails().get("userID"));
						dgBillLineItemEntity
								.setLastUpdatedBy((String) SessionData
										.getUserDetails().get("userID"));
						dgBillLineItemEntity
								.setLastUpdatedDT(new java.sql.Timestamp(
										new Date().getTime()));
						billLineItemEntities.add(dgBillLineItemEntity);
					}
					if (elRateMaster.getRateName().equalsIgnoreCase("DG Fixed Charge")) {
						List<ELRateSlabs> elRateSlabs = elRateSlabService
								.findRateSlabByID(elRateMaster.getElrmid());
						float dgfixecharge = 0;
						consolidatedBill = calculationController.tariffAmount(
								elRateMaster, previousBillDate,
								currentBillDate, 1.f, 0,elRateMaster.getRateNameCategory());
						for (Entry<Object, Object> map : consolidatedBill
								.entrySet()) {
							if (map.getKey().equals("totalAmount")) {
								dgFixcedAmount = (Float) map.getValue();
								logger.info("::::::::dgFixcedAmount::::::::::"
										+ dgFixcedAmount);
							}
						}

						dgFixcedBillLineItemEntity
								.setTransactionCode("EL_DG_FC");
						dgFixcedBillLineItemEntity
								.setBalanceAmount(dgFixcedAmount);
						dgFixcedBillLineItemEntity.setCreditAmount(0);
						dgFixcedBillLineItemEntity.setDebitAmount(0);
						dgFixcedBillLineItemEntity.setStatus("Not Approved");
						dgFixcedBillLineItemEntity
								.setCreatedBy((String) SessionData
										.getUserDetails().get("userID"));
						dgFixcedBillLineItemEntity
								.setLastUpdatedBy((String) SessionData
										.getUserDetails().get("userID"));
						dgFixcedBillLineItemEntity
								.setLastUpdatedDT(new java.sql.Timestamp(
										new Date().getTime()));
						billLineItemEntities.add(dgFixcedBillLineItemEntity);
					}
				}
			}

			String typesofmeter = tariffCalculationService.getMeterType(
					accountID, typeOfService);
			logger.info("::::::::typesofmeter::::::::::" + typesofmeter);
			if (typesofmeter != null) {
				if (typesofmeter.trim().equalsIgnoreCase("Trivector Meter")) {
					if (elRateMaster.getRateName().equalsIgnoreCase(
							"PF Penalty")) {
						logger.info("--------- PF penalty applicable ---------------");

						if (presentMeterStaus.equalsIgnoreCase("Door Lock")
								|| presentMeterStaus
										.equalsIgnoreCase("Meter Not Reading")
								|| presentMeterStaus
										.equalsIgnoreCase("Direct Connection")
								|| presentMeterStaus.equalsIgnoreCase("Burnt")
								|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
							consolidatedBill = calculationController
									.tariffAmount(elRateMaster,
											previousBillDate, currentBillDate,
											uomValue, pfReading,elRateMaster.getRateNameCategory());
						} else {
							if (previousBillEntity != null) {
								if (previousBillEntity.getAvgCount() >= 1) {
									consolidatedBill = calculationController
											.tariffAmountAvgCount(elRateMaster,
													previousBillDate,
													currentBillDate,
													previousBillEntity
															.getAvgCount(),
													uomValue, pfReading,elRateMaster.getRateNameCategory());
								} else {
									consolidatedBill = calculationController
											.tariffAmount(elRateMaster,
													previousBillDate,
													currentBillDate, uomValue,
													pfReading,elRateMaster.getRateNameCategory());
								}
							} else {
								consolidatedBill = calculationController
										.tariffAmount(elRateMaster,
												previousBillDate,
												currentBillDate, uomValue,
												pfReading,elRateMaster.getRateNameCategory());
							}
						}

						for (Entry<Object, Object> map : consolidatedBill
								.entrySet()) {
							if (map.getKey().equals("rebate")) {
								pfRebate = (Float) map.getValue();
							}

							if (map.getKey().equals("penalty")) {
								pfPenalty = (Float) map.getValue();
							}
						}

						if (pfRebate != 0) {
							pfRebateBillLineItemEntity
									.setTransactionCode("EL_PF_R");
							pfRebateBillLineItemEntity
									.setBalanceAmount(-pfRebate);
							pfRebateBillLineItemEntity.setCreditAmount(0);
							pfRebateBillLineItemEntity.setDebitAmount(0);
							pfRebateBillLineItemEntity
									.setStatus("Not Approved");
							pfRebateBillLineItemEntity
									.setCreatedBy((String) SessionData
											.getUserDetails().get("userID"));
							pfRebateBillLineItemEntity
									.setLastUpdatedBy((String) SessionData
											.getUserDetails().get("userID"));
							pfRebateBillLineItemEntity
									.setLastUpdatedDT(new java.sql.Timestamp(
											new Date().getTime()));
							billLineItemEntities
									.add(pfRebateBillLineItemEntity);
						}

						if (pfPenalty != 0) {
							pfPenaltyBillLineItemEntity
									.setTransactionCode("EL_PF_P");
							pfPenaltyBillLineItemEntity
									.setBalanceAmount(pfPenalty);
							pfPenaltyBillLineItemEntity.setCreditAmount(0);
							pfPenaltyBillLineItemEntity.setDebitAmount(0);
							pfPenaltyBillLineItemEntity
									.setStatus("Not Approved");
							pfPenaltyBillLineItemEntity
									.setCreatedBy((String) SessionData
											.getUserDetails().get("userID"));
							pfPenaltyBillLineItemEntity
									.setLastUpdatedBy((String) SessionData
											.getUserDetails().get("userID"));
							pfPenaltyBillLineItemEntity
									.setLastUpdatedDT(new java.sql.Timestamp(
											new Date().getTime()));
							billLineItemEntities
									.add(pfPenaltyBillLineItemEntity);
						}
					}

					if (elRateMaster.getRateName().equalsIgnoreCase("MDI")) {
						logger.info("-------------- MDI Penalty applicable ----------------- ");
						ServiceMastersEntity mastersEntity1 = serviceMasterService
								.getServiceMasterServicType(accountID,
										typeOfService);
						List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService
								.findAllByParentId(mastersEntity1
										.getServiceMasterId());
						float mdiValue = 0;
						for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1) {

							if (serviceParametersEntity
									.getServiceParameterMaster().getSpmName()
									.equalsIgnoreCase("Sanctioned KW")
									|| serviceParametersEntity
											.getServiceParameterMaster()
											.getSpmName()
											.equalsIgnoreCase("Sanctioned KVA")
									|| serviceParametersEntity
											.getServiceParameterMaster()
											.getSpmName()
											.equalsIgnoreCase("Sanctioned HP")) {
								mdiValue = Float
										.parseFloat(serviceParametersEntity
												.getServiceParameterValue());
							}
						}

						if (presentMeterStaus.equalsIgnoreCase("Door Lock")
								|| presentMeterStaus
										.equalsIgnoreCase("Meter Not Reading")
								|| presentMeterStaus
										.equalsIgnoreCase("Direct Connection")
								|| presentMeterStaus.equalsIgnoreCase("Burnt")
								|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
							consolidatedBill = calculationController
									.tariffAmount(elRateMaster,
											previousBillDate, currentBillDate,
											mdiValue, connectedLoad,elRateMaster.getRateNameCategory());
						} else {
							if (previousBillEntity != null) {
								if (previousBillEntity.getAvgCount() >= 1) {
									consolidatedBill = calculationController
											.tariffAmountAvgCount(elRateMaster,
													previousBillDate,
													currentBillDate,
													previousBillEntity
															.getAvgCount(),
													mdiValue, connectedLoad,elRateMaster.getRateNameCategory());
								} else {
									consolidatedBill = calculationController
											.tariffAmount(elRateMaster,
													previousBillDate,
													currentBillDate, mdiValue,
													connectedLoad,elRateMaster.getRateNameCategory());
								}
							} else {
								consolidatedBill = calculationController
										.tariffAmount(elRateMaster,
												previousBillDate,
												currentBillDate, mdiValue,
												connectedLoad,elRateMaster.getRateNameCategory());
							}
						}
						for (Entry<Object, Object> map : consolidatedBill
								.entrySet()) {
							if (map.getKey().equals("totalAmount")) {
								mdiPenaltyAmount = (Float) map.getValue();
								logger.info("mdi penalty ::::::::::--------------------- "
										+ mdiPenaltyAmount);
							}
						}
						mdiPenalty.setBalanceAmount(mdiPenaltyAmount);
						mdiPenalty.setTransactionCode("EL_MDI");
						mdiPenalty.setCreditAmount(0);
						mdiPenalty.setDebitAmount(0);
						mdiPenalty.setStatus("Not Approved");
						mdiPenalty.setCreatedBy((String) SessionData
								.getUserDetails().get("userID"));
						mdiPenalty.setLastUpdatedBy((String) SessionData
								.getUserDetails().get("userID"));
						mdiPenalty.setLastUpdatedDT(new java.sql.Timestamp(
								new Date().getTime()));
						billLineItemEntities.add(mdiPenalty);
					}
				}
			}
		}

		float interestOnArrearsAmount = 0;
		float interestOnTaxAmount = 0;
		ElectricityBillLineItemEntity interestOnTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity interestOnBillAmount = new ElectricityBillLineItemEntity();

		/*:::::::::::::::::::::::For Late Payment::::::::::::::::::::::::::::::*/
		
		//consolidatedBill = interestCalculation(accountID, typeOfService,previousBillDate, elRateMasterList, currentBillDate);
		
		logger.info(":::::::consolidatedBill::::"+consolidatedBill);
		
		/*:::::::::::::::::::::::For Late Payment::::::::::::::::::::::::::::::*/

		for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
			if (map.getKey().equals("interestOnArrearsAmount")) {
				interestOnArrearsAmount = interestOnArrearsAmount+ (Float) map.getValue();
				logger.info("------ " + interestOnArrearsAmount);
				interestOnBillAmount.setTransactionCode("EL_INTEREST");
				interestOnBillAmount.setBalanceAmount(interestOnArrearsAmount);
				interestOnBillAmount.setCreditAmount(0);
				interestOnBillAmount.setDebitAmount(0);
				interestOnBillAmount.setStatus("Not Approved");
				interestOnBillAmount.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
				billLineItemEntities.add(interestOnBillAmount);
			}
			if (map.getKey().equals("interestOnTaxAmount")) {
				interestOnTaxAmount = interestOnTaxAmount+ (Float) map.getValue();
				logger.info("------ " + interestOnTaxAmount);
				interestOnTax.setTransactionCode("EL_TAX_INTEREST");
				interestOnTax.setBalanceAmount(interestOnTaxAmount);
				interestOnTax.setCreditAmount(0);
				interestOnTax.setDebitAmount(0);
				interestOnTax.setStatus("Not Approved");
				interestOnTax.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
				billLineItemEntities.add(interestOnTax);
			}
		}

		ElectricityBillEntity previousBillEntity1 = electricityBillsService.getPreviousBill(accountID, typeOfService,previousBillDate, "Bill");
		System.err.println(":::::::::::"+previousBillEntity1+":::::::size");
		if (previousBillEntity1!=null) {
			if (previousBillEntity1.getStatus().equalsIgnoreCase("Posted")) {
				//List<?> latePayment =  paymentService.latePaymentAmount(accountID);
				double previousTax = 0;
				double previousInterest = 0;
				double previousInterestOnTax = 0;
				for (ElectricityBillLineItemEntity electricityBillLineItemEntity : previousBillEntity1
						.getBillLineItemEntities()) {
                      
					if (electricityBillLineItemEntity.getTransactionCode()
							.equalsIgnoreCase("EL_TAX")) {
						previousTax = electricityBillLineItemEntity
								.getBalanceAmount();
					}
					if (electricityBillLineItemEntity.getTransactionCode()
							.equalsIgnoreCase("EL_INTEREST")) {
						previousInterest = electricityBillLineItemEntity
								.getBalanceAmount();
					}
					if (electricityBillLineItemEntity.getTransactionCode()
							.equalsIgnoreCase("EL_TAX_INTEREST")) {
						previousInterestOnTax = electricityBillLineItemEntity
								.getBalanceAmount();
					}
				}
				float amountForInteresetCal = (float) (previousBillEntity1
						.getNetAmount() - (previousTax + previousInterest + previousInterestOnTax));

				logger.info(":::::::::::rateMasterIdFor Rate Of Interst"+rateMasterIdRateOfInterest);
				if(rateMasterIdRateOfInterest!=0){
				/*latePaymentAnount = billController.interestCalculationEL(
						rateMasterIdRateOfInterest, amountForInteresetCal,
						previousBillEntity1);*/
					try{
					latePaymentAnount=previousBillEntity1.getLatePaymentAmount().floatValue();
					}catch(NullPointerException e){
						latePaymentAnount=0f;
					}
				}
				//System.err.println(":::::::::::"+latePaymentAnount+":::::::size");
                    logger.info("latePaymentAnount"+latePaymentAnount);
				latePaymentCharges.setBalanceAmount(latePaymentAnount);
				latePaymentCharges.setTransactionCode("EL_LPC");
				latePaymentCharges.setCreditAmount(0);
				latePaymentCharges.setDebitAmount(0);
				latePaymentCharges.setStatus("Not Approved");
				latePaymentCharges.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				latePaymentCharges.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				latePaymentCharges.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(latePaymentCharges);
			}
		}
		float totalExcludeTax = (float) (ecAmount + dgAMount + fsaAmount + roundoffValue);
		logger.info(":::::::::::::: Total Amount Excludeing tax ::::::::::::: "+ totalExcludeTax);

		for (ELRateMaster elRateMaster : elRateMasterList) {
			if (elRateMaster.getRateName().equalsIgnoreCase("Tax")) {
				taxBillLineItemEntity.setTransactionCode("EL_TAX");
				consolidatedBill = calculationController.tariffAmount(
						elRateMaster, previousBillDate, currentBillDate,
						uomValue, 0,elRateMaster.getRateNameCategory());
				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("totalAmount")) {
						taxAmount = (Float) map.getValue();
						logger.info("=========== Tax for toatal amount  "
								+ totalExcludeTax + " is " + taxAmount);
					}
				}
				taxBillLineItemEntity.setBalanceAmount(taxAmount);
				taxBillLineItemEntity.setCreditAmount(0);
				taxBillLineItemEntity.setDebitAmount(0);
				taxBillLineItemEntity.setStatus("Not Approved");
				taxBillLineItemEntity.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				taxBillLineItemEntity.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				taxBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(taxBillLineItemEntity);
			}
		}

		logger.info("ecAmount " + ecAmount);
		logger.info("taxAmount " + taxAmount);
		logger.info("mmcAmount " + mmcAmount);
		logger.info("rateOfInterest " + rateOfInterest);
		logger.info("fcAmount " + fcAmount);
		logger.info("octroiAmount " + octroiAmount);
		logger.info("arrearsAmount " + arrearsAmount);
		logger.info("dgAMount " + dgAMount);
		logger.info("solarRebate " + solarRebate);
		logger.info("interestOnTaxAmount " + interestOnTax.getBalanceAmount());
		logger.info("fsaAmount " + fsaAmount);
		logger.info("interestOnArrears "+ interestOnBillAmount.getBalanceAmount());
		logger.info("pfPenalty " + pfPenalty);
		logger.info("pfRebate " + pfRebate);
		logger.info("mdiPenaltyAmount " + mdiPenaltyAmount);
		logger.info("latePaymentAnount " + latePaymentAnount);
		logger.info("Total without round off "
				+ (ecAmount + mdiPenaltyAmount + mmcAmount + pfPenalty
						- pfRebate + avgAmount.floatValue() + taxAmount
						+ rateOfInterest + fcAmount + octroiAmount
						+ arrearsAmount + dgAMount - solarRebate
						+ interestOnTax.getBalanceAmount() + fsaAmount + interestOnBillAmount.getBalanceAmount()+latePaymentAnount));

		totalAmount = roundOff(
				ecAmount + mmcAmount + taxAmount + mdiPenaltyAmount + pfPenalty+totalbankcharge
						- pfRebate + rateOfInterest + fcAmount + dgFixcedAmount
						+ octroiAmount + arrearsAmount + dgAMount - solarRebate
						+ (float) interestOnTax.getBalanceAmount()
						+ (float) interestOnBillAmount.getBalanceAmount()
						+ fsaAmount+latePaymentAnount, 0);
		logger.info("totalAmount :::::::::::::: " + totalAmount);
		roundoffValue = totalAmount
				- (ecAmount + pfPenalty + mmcAmount + mdiPenaltyAmount+totalbankcharge
						- pfRebate + taxAmount + rateOfInterest + fcAmount
						+ fsaAmount + dgFixcedAmount + octroiAmount
						+ arrearsAmount + dgAMount - solarRebate
						+ (float) interestOnTax.getBalanceAmount() + (float) interestOnBillAmount.getBalanceAmount()+latePaymentAnount);
		logger.info("::::::::::::::::::::::::: Round off value is ::::::::::::::: "+ roundoffValue);

		roundOffBillLineItemEntity.setCreditAmount(0);
		roundOffBillLineItemEntity.setDebitAmount(0);
		roundOffBillLineItemEntity.setStatus("Not Approved");
		roundOffBillLineItemEntity.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
		roundOffBillLineItemEntity.setBalanceAmount(roundoffValue);
		roundOffBillLineItemEntity.setTransactionCode("EL_ROF");
		billLineItemEntities.add(roundOffBillLineItemEntity);

		if (presentMeterStaus.equalsIgnoreCase("Door Lock")
				|| presentMeterStaus.equalsIgnoreCase("Meter Not Reading")
				|| presentMeterStaus.equalsIgnoreCase("Direct Connection")
				|| presentMeterStaus.equalsIgnoreCase("Burnt")
				|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
			presentUOMReading = previousUOMReading;
			ElectricityBillEntity billEntity = electricityBillsService.getPreviousBill(accountID, typeOfService,previousBillDate, "Bill");
			if (billEntity != null) {
				avgUnits = billEntity.getAvgUnits() + uomValue;
				avgCount = billEntity.getAvgCount() + 1;
				billType = "Average";
			}
		} else {
			ElectricityBillEntity billEntity = electricityBillsService.getPreviousBill(accountID, typeOfService,previousBillDate, "Bill");
			List<ElectricityBillEntity> billEntities = new ArrayList<>();
			if (billEntity != null) {
				if (billEntity.getAvgCount() >= 1) {
					List<BigDecimal> billIds = electricityBillsService.getLastAvgBills(accountID, typeOfService,previousBillDate, "Bill",billEntity.getAvgCount());
					for (BigDecimal bigDecimal : billIds) {
						billEntities.add(electricityBillsService.find(bigDecimal.intValue()));
					}
				}

				for (ElectricityBillEntity electricityBillEntity : billEntities) {
					avgAmount = avgAmount+ electricityBillEntity.getBillAmount();
				}
			}
		}

		ElectricityBillEntity billEntity = saveBill(accountID, totalAmount,
				previousBillDate, currentBillDate, typeOfService,
				arrearsAmount, avgUnits, avgCount, billType, avgAmount,
				postType,rateMasterIdRateOfInterest);
		saveBillLineItems(billEntity, billLineItemEntities, roundoffValue,arrearsAmount);
		saveBillParameters(billEntity, elRateMasterList, accountID,
				typeOfService, previousBillDate, currentBillDate, uomValue,
				billableDays, presentMeterStaus, previousMeterStaus,
				meterConstant, presentUOMReading, previousUOMReading,
				previousBillEntity1, ecString, fsaString, previousDgreading,
				PresentDgReading, dgmeterconstant, dgApplicable, dgunit,
				todString, todApplicable, pfReading, connectedLoad,
				meterchangeUnit, tariffChange);
        Locale locale = null;
		billController.getBillDoc(billEntity.getElBillId(), locale,1);
		
		HashMap<String, Object> bill = new HashMap<>();
		//bill.put("billId", billEntity.getElBillId());
		return bill;
	}

	private String getreabateType(int elrmid) {
		List<ELRateSlabs> elRateSlabs = elRateSlabService
				.findRateSlabByID(elrmid);
		String rebateType = "";
		for (ELRateSlabs elRateSlabs2 : elRateSlabs) {
			rebateType = elRateSlabs2.getSlabType();
		}
		return rebateType;
	}

	@SuppressWarnings("unused")
	private HashMap<Object, Object> interestCalculation(int accountID,
			String typeOfService, Date previousBillDate,
			List<ELRateMaster> elRateMasterList, Date currentBillDate) {
		logger.info("::::::::::::::: In interest on calculation :::::::::::::::");
		HashMap<Object, Object> intersts = new LinkedHashMap<>();
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float previousTaxAmount = 0f;
		float rateOfInterest = 0;
		float interestOnTax = 0;
		float interestOnArrearsAmount = 0f;
		float interestOnTaxAmount = 0f;

		float previousTaxAmountL = 0f;
		float rateOfInterestL = 0;
		float interestOnTaxL = 0;

		/*List<InterestSettingsEntity> interestSettingsEntities = interestSettingService.findAllData();
		String interestType = null;
		for (InterestSettingsEntity interestSettingsEntity : interestSettingsEntities) {
			interestType = interestSettingsEntity.getInterestBasedOn();
			logger.info(" Interest Calculation base on -->" + interestType);
		}*/
		String configName = "Interest Calculation";
		String status = "Active";
		String interestType = electricityBillsService.getBillingConfigValue(configName,status);
		logger.info("interestType ==================== "+interestType);

		if (interestType != null) {
			if (interestType.trim().equalsIgnoreCase("Daywise")) {
				logger.info("--------------------- Interest calculation based on ----"+ interestType);

				ElectricityBillEntity billEntity = electricityBillsService.getPreviousBill(accountID, typeOfService,previousBillDate, "Bill");
				ElectricityLedgerEntity electricityLedgerEntity;
				if (billEntity != null) {
					String transactionCode = "EL";
					electricityLedgerEntity = electricityLedgerService.getPreviousLedger(accountID, previousBillDate,transactionCode);

					if (electricityLedgerEntity.getPostType().trim().equalsIgnoreCase("PAYMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF "	+ electricityLedgerEntity.getPostType());
						Date ledgerPostDate = new LocalDate(electricityLedgerEntity.getPostedBillDate()).toDateMidnight().toDate();
						Date billDueDate = new LocalDate(billEntity.getBillDueDate()).toDateMidnight().toDate();
						if ((billDueDate.compareTo(ledgerPostDate)) == 0) {
							logger.info("======== payment done on same date of due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ billEntity.getArrearsAmount());
							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("EL_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"EL_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"EL_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (ELRateMaster elRateMaster : elRateMasterList) {
											if (elRateMaster
													.getRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												consolidatedBill = calculationController
														.interstAmount(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (ELRateMaster elRateMaster : elRateMasterList) {
											if (elRateMaster
													.getRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmount(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}

							if (billEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						} else if (ledgerPostDate.compareTo(billDueDate) > 0) {
							logger.info("======== payment done on after due date ===============");

							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("EL_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"EL_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"EL_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (ELRateMaster elRateMaster : elRateMasterList) {
											if (elRateMaster
													.getRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												logger.info("arrersAmount "
														+ arrersAmount);
												consolidatedBill = calculationController
														.interstAmount(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (ELRateMaster elRateMaster : elRateMasterList) {
											if (elRateMaster
													.getRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmount(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
							if (billEntity.getBillAmount() > 0) {
								logger.info("billEntity.getBillAmount() "
										+ billEntity.getBillAmount());
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getBillAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDueDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										for (ElectricityBillLineItemEntity billLineItemEntity : billEntity
												.getBillLineItemEntities()) {
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("EL_TAX")) {
												previousTaxAmount = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"EL_INTEREST")) {
												rateOfInterest = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"EL_TAX_INTEREST")) {
												interestOnTax = (float) billLineItemEntity
														.getBalanceAmount();
											}
										}
										logger.info("rateOfInterest "
												+ rateOfInterest);
										logger.info("previousTaxAmount "
												+ previousTaxAmount);
										logger.info("interestOnTax "
												+ interestOnTax);
										for (ELRateMaster elRateMaster : elRateMasterList) {
											if (elRateMaster
													.getRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getBillAmount() - (rateOfInterest
														+ previousTaxAmount + interestOnTax));
												consolidatedBill = calculationController
														.interstAmount(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
										for (ELRateMaster elRateMaster : elRateMasterList) {
											if (elRateMaster
													.getRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmount;
												consolidatedBill = calculationController
														.interstAmount(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
						} else {
							logger.info("======== payment done on before due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ billEntity.getArrearsAmount());
							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												ledgerEntityPayments
														.getPostedBillDate());
										endDate = new LocalDate(
												billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("EL_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"EL_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"EL_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (ELRateMaster elRateMaster : elRateMasterList) {
											if (elRateMaster
													.getRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (billEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												logger.info("arrersAmount "
														+ arrersAmount);
												consolidatedBill = calculationController
														.interstAmount(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
										for (ELRateMaster elRateMaster : elRateMasterList) {
											if (elRateMaster
													.getRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmount(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
							if (billEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						}
					} else if (electricityLedgerEntity.getPostType().trim().equalsIgnoreCase("ADJUSTMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF Else "+ electricityLedgerEntity.getPostType());
					} else {

						logger.info("electricityLedgerEntity.getPostType ---- in Else "
								+ electricityLedgerEntity.getPostType());
						if (billEntity.getArrearsAmount() > 0) {
							logger.info("billEntity.getArrearsAmount() "
									+ billEntity.getArrearsAmount());
							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(billEntity.getBillDate());
							Days d = Days.daysBetween(startDate, endDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "
									+ days);

							int lastLedgerId = electricityLedgerService
									.getLastArrearsLedgerBasedOnPayment(
											accountID, previousBillDate,
											transactionCode).intValue();
							ElectricityLedgerEntity ledgerEntity = electricityLedgerService
									.find(lastLedgerId);
							logger.info("ledgerEntity ---------- "
									+ ledgerEntity.getPostType());
							List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
									.findAllById1(ledgerEntity.getElLedgerid());
							for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("EL_TAX")) {
									previousTaxAmountL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("EL_INTEREST")) {
									rateOfInterestL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("EL_TAX_INTEREST")) {
									interestOnTaxL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
							}

							logger.info("rateOfInterestL " + rateOfInterestL);
							logger.info("previousTaxAmountL "
									+ previousTaxAmountL);
							logger.info("interestOnTaxL " + interestOnTaxL);

							for (ELRateMaster elRateMaster : elRateMasterList) {
								if (elRateMaster.getRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (billEntity
											.getArrearsAmount() - (rateOfInterestL
											+ previousTaxAmountL + interestOnTaxL));
									logger.info("arrersAmount " + arrersAmount);
									consolidatedBill = calculationController
											.interstAmount(elRateMaster,
													endDate.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							for (ELRateMaster elRateMaster : elRateMasterList) {
								if (elRateMaster.getRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmountL;
									consolidatedBill = calculationController
											.interstAmount(elRateMaster,
													endDate.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}
						}
						if (billEntity.getBillAmount() > 0) {
							logger.info("billEntity.getBillAmount() "
									+ billEntity.getBillAmount());

							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(billEntity.getBillDueDate());
							Days d = Days.daysBetween(endDate, startDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "+ days);
							for (ElectricityBillLineItemEntity billLineItemEntity : billEntity
									.getBillLineItemEntities()) {
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("EL_TAX")) {
									previousTaxAmount = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("EL_INTEREST")) {
									rateOfInterest = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim()
										.equalsIgnoreCase("EL_TAX_INTEREST")) {
									interestOnTax = (float) billLineItemEntity
											.getBalanceAmount();
								}
							}

							for (ELRateMaster elRateMaster : elRateMasterList) {
								if (elRateMaster.getRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (billEntity
											.getBillAmount() - (rateOfInterest
											+ previousTaxAmount + interestOnTax));
									consolidatedBill = calculationController.interstAmount(elRateMaster,endDate.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "+ arrersAmount+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							logger.info("rateOfInterest " + rateOfInterest);
							logger.info("previousTaxAmount "
									+ previousTaxAmount);
							logger.info("interestOnTax " + interestOnTax);

							for (ELRateMaster elRateMaster : elRateMasterList) {
								if (elRateMaster.getRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmount;
									consolidatedBill = calculationController
											.interstAmount(elRateMaster,
													endDate.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}
						}
					}
				} else {
					logger.info("------------------ First Month Bill -----------------------");
				}
			} else {
				logger.info("--------------------- Interest calculation based on ----"+ interestType);

				ElectricityBillEntity previouBillEntity = electricityBillsService.getPreviousBill(accountID, typeOfService,previousBillDate, "Bill");
				ElectricityLedgerEntity electricityLedgerEntity;
				if (previouBillEntity != null) {
					String transactionCode = "EL";
					electricityLedgerEntity = electricityLedgerService.getPreviousLedger(accountID, previousBillDate,transactionCode);
					if (electricityLedgerEntity.getPostType().trim().equalsIgnoreCase("PAYMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF "	+ electricityLedgerEntity.getPostType());
						Date ledgerPostDate = new LocalDate(electricityLedgerEntity.getPostedBillDate()).toDateMidnight().toDate();
						Date billDueDate = new LocalDate(previouBillEntity.getBillDueDate()).toDateMidnight().toDate();
						if ((billDueDate.compareTo(ledgerPostDate)) == 0) {
							logger.info("======== payment done on same date of due date ===============");
							logger.info("billEntity.getArrearsAmount() "+ previouBillEntity.getArrearsAmount());
							if (previouBillEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(
												currentBillDate);
										endDate = new LocalDate(
												previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("EL_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"EL_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"EL_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (ELRateMaster elRateMaster : elRateMasterList) {
											if (elRateMaster
													.getRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (previouBillEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												consolidatedBill = calculationController
														.interstAmountForMonthWise(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (ELRateMaster elRateMaster : elRateMasterList) {
											if (elRateMaster
													.getRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountForMonthWise(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}

							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						} else if (ledgerPostDate.compareTo(billDueDate) > 0) {
							logger.info("======== payment done on after due date ===============");

							if (previouBillEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getArrearsAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										/*
										 * startDate = new LocalDate(
										 * ledgerEntityPayments
										 * .getPostedBillDate());
										 */
										startDate = new LocalDate(
												currentBillDate);
										endDate = new LocalDate(
												previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										int lastLedgerId = electricityLedgerService
												.getLastArrearsLedgerBasedOnPayment(
														accountID,
														previousBillDate,
														transactionCode)
												.intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService
												.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "
												+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
												.findAllById1(ledgerEntity
														.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("EL_TAX")) {
												previousTaxAmountL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"EL_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
											if (electricitySubLedgerEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"EL_TAX_INTEREST")) {
												interestOnTaxL = (float) electricitySubLedgerEntity
														.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL "
												+ rateOfInterestL);
										logger.info("previousTaxAmountL "
												+ previousTaxAmountL);
										logger.info("interestOnTaxL "
												+ interestOnTaxL);

										for (ELRateMaster elRateMaster : elRateMasterList) {
											if (elRateMaster
													.getRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (previouBillEntity
														.getArrearsAmount() - (rateOfInterestL
														+ previousTaxAmountL + interestOnTaxL));
												logger.info("arrersAmount "
														+ arrersAmount);
												consolidatedBill = calculationController
														.interstAmountForMonthWise(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}

										for (ELRateMaster elRateMaster : elRateMasterList) {
											if (elRateMaster
													.getRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmountL;
												consolidatedBill = calculationController
														.interstAmountForMonthWise(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("billEntity.getBillAmount() "
										+ previouBillEntity.getBillAmount());
								List<ElectricityLedgerEntity> ledgerEntities = electricityLedgerService
										.getPreviousLedgerPayments(accountID,
												previousBillDate,
												transactionCode);
								logger.info("========= Number of payments done in last month ============= "
										+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getBillAmount()
											+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										/*
										 * startDate = new LocalDate(
										 * ledgerEntityPayments
										 * .getPostedBillDate());
										 */
										startDate = new LocalDate(
												currentBillDate);
										/*
										 * endDate = new LocalDate(
										 * billEntity.getBillDueDate());
										 */
										endDate = new LocalDate(
												previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,
												endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "
												+ days);

										for (ElectricityBillLineItemEntity billLineItemEntity : previouBillEntity
												.getBillLineItemEntities()) {
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase("EL_TAX")) {
												previousTaxAmount = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"EL_INTEREST")) {
												rateOfInterest = (float) billLineItemEntity
														.getBalanceAmount();
											}
											if (billLineItemEntity
													.getTransactionCode()
													.trim()
													.equalsIgnoreCase(
															"EL_TAX_INTEREST")) {
												interestOnTax = (float) billLineItemEntity
														.getBalanceAmount();
											}
										}
										logger.info("rateOfInterest "
												+ rateOfInterest);
										logger.info("previousTaxAmount "
												+ previousTaxAmount);
										logger.info("interestOnTax "
												+ interestOnTax);
										for (ELRateMaster elRateMaster : elRateMasterList) {
											if (elRateMaster
													.getRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float arrersAmount = (float) (previouBillEntity
														.getBillAmount() - (rateOfInterest
														+ previousTaxAmount + interestOnTax));
												consolidatedBill = calculationController
														.interstAmountForMonthWise(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																arrersAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnArrearsAmount = interestOnArrearsAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest  for Arrears Amount "
																+ arrersAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
										for (ELRateMaster elRateMaster : elRateMasterList) {
											if (elRateMaster
													.getRateName()
													.trim()
													.equalsIgnoreCase(
															"Rate of interest")) {
												float taxAmount = previousTaxAmount;
												consolidatedBill = calculationController
														.interstAmountForMonthWise(
																elRateMaster,
																endDate.toDateMidnight()
																		.toDate(),
																startDate
																		.toDateMidnight()
																		.toDate(),
																taxAmount);
												for (Entry<Object, Object> map : consolidatedBill
														.entrySet()) {
													if (map.getKey().equals(
															"total")) {
														interestOnTaxAmount = interestOnTaxAmount
																+ (Float) map
																		.getValue();
														logger.info(":::::::::::::::: Interest for Tax Amount "
																+ taxAmount
																+ " is "
																+ (Float) map
																		.getValue());
													}
												}

											}
										}
									}
								}
							}
						} else {
							logger.info("======== payment done on before due date ===============");
							logger.info("billEntity.getArrearsAmount() "
									+ previouBillEntity.getArrearsAmount());
							if (previouBillEntity.getArrearsAmount() > 0) {/*
																			 * List<
																			 * ElectricityLedgerEntity
																			 * >
																			 * ledgerEntities
																			 * =
																			 * electricityLedgerService
																			 * .
																			 * getPreviousLedgerPayments
																			 * (
																			 * accountID
																			 * ,
																			 * previousBillDate
																			 * ,
																			 * transactionCode
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "========= Number of payments done in last month ============= "
																			 * +
																			 * ledgerEntities
																			 * .
																			 * size
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * for
																			 * (
																			 * ElectricityLedgerEntity
																			 * ledgerEntityPayments
																			 * :
																			 * ledgerEntities
																			 * )
																			 * {
																			 * if
																			 * (
																			 * previouBillEntity
																			 * .
																			 * getArrearsAmount
																			 * (
																			 * )
																			 * +
																			 * ledgerEntityPayments
																			 * .
																			 * getAmount
																			 * (
																			 * )
																			 * ==
																			 * 0
																			 * )
																			 * {
																			 * LocalDate
																			 * startDate
																			 * =
																			 * null
																			 * ;
																			 * LocalDate
																			 * endDate
																			 * =
																			 * null
																			 * ;
																			 * 
																			 * startDate
																			 * =
																			 * new
																			 * LocalDate
																			 * (
																			 * ledgerEntityPayments
																			 * .
																			 * getPostedBillDate
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * 
																			 * startDate
																			 * =
																			 * new
																			 * LocalDate
																			 * (
																			 * currentBillDate
																			 * )
																			 * ;
																			 * endDate
																			 * =
																			 * new
																			 * LocalDate
																			 * (
																			 * previouBillEntity
																			 * .
																			 * getBillDate
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * Days
																			 * d
																			 * =
																			 * Days
																			 * .
																			 * daysBetween
																			 * (
																			 * startDate
																			 * ,
																			 * endDate
																			 * )
																			 * ;
																			 * int
																			 * days
																			 * =
																			 * d
																			 * .
																			 * getDays
																			 * (
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "============ Arrears Amount interst days ========= "
																			 * +
																			 * days
																			 * )
																			 * ;
																			 * 
																			 * int
																			 * lastLedgerId
																			 * =
																			 * electricityLedgerService
																			 * .
																			 * getLastArrearsLedgerBasedOnPayment
																			 * (
																			 * accountID
																			 * ,
																			 * previousBillDate
																			 * ,
																			 * transactionCode
																			 * )
																			 * .
																			 * intValue
																			 * (
																			 * )
																			 * ;
																			 * ElectricityLedgerEntity
																			 * ledgerEntity
																			 * =
																			 * electricityLedgerService
																			 * .
																			 * find
																			 * (
																			 * lastLedgerId
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "ledgerEntity ---------- "
																			 * +
																			 * ledgerEntity
																			 * .
																			 * getPostType
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * List
																			 * <
																			 * ElectricitySubLedgerEntity
																			 * >
																			 * subLedgerEntities
																			 * =
																			 * electricitySubLedgerService
																			 * .
																			 * findAllById1
																			 * (
																			 * ledgerEntity
																			 * .
																			 * getElLedgerid
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * for
																			 * (
																			 * ElectricitySubLedgerEntity
																			 * electricitySubLedgerEntity
																			 * :
																			 * subLedgerEntities
																			 * )
																			 * {
																			 * if
																			 * (
																			 * electricitySubLedgerEntity
																			 * .
																			 * getTransactionCode
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "EL_TAX"
																			 * )
																			 * )
																			 * {
																			 * previousTaxAmountL
																			 * =
																			 * (
																			 * float
																			 * )
																			 * electricitySubLedgerEntity
																			 * .
																			 * getBalanceAmount
																			 * (
																			 * )
																			 * ;
																			 * }
																			 * if
																			 * (
																			 * electricitySubLedgerEntity
																			 * .
																			 * getTransactionCode
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "EL_INTEREST"
																			 * )
																			 * )
																			 * {
																			 * rateOfInterestL
																			 * =
																			 * (
																			 * float
																			 * )
																			 * electricitySubLedgerEntity
																			 * .
																			 * getBalanceAmount
																			 * (
																			 * )
																			 * ;
																			 * }
																			 * if
																			 * (
																			 * electricitySubLedgerEntity
																			 * .
																			 * getTransactionCode
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "EL_TAX_INTEREST"
																			 * )
																			 * )
																			 * {
																			 * interestOnTaxL
																			 * =
																			 * (
																			 * float
																			 * )
																			 * electricitySubLedgerEntity
																			 * .
																			 * getBalanceAmount
																			 * (
																			 * )
																			 * ;
																			 * }
																			 * }
																			 * 
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "rateOfInterestL "
																			 * +
																			 * rateOfInterestL
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "previousTaxAmountL "
																			 * +
																			 * previousTaxAmountL
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "interestOnTaxL "
																			 * +
																			 * interestOnTaxL
																			 * )
																			 * ;
																			 * 
																			 * for
																			 * (
																			 * ELRateMaster
																			 * elRateMaster
																			 * :
																			 * elRateMasterList
																			 * )
																			 * {
																			 * if
																			 * (
																			 * elRateMaster
																			 * .
																			 * getRateName
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "Rate of interest"
																			 * )
																			 * )
																			 * {
																			 * float
																			 * arrersAmount
																			 * =
																			 * (
																			 * float
																			 * )
																			 * (
																			 * previouBillEntity
																			 * .
																			 * getArrearsAmount
																			 * (
																			 * )
																			 * -
																			 * (
																			 * rateOfInterestL
																			 * +
																			 * previousTaxAmountL
																			 * +
																			 * interestOnTaxL
																			 * )
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * "arrersAmount "
																			 * +
																			 * arrersAmount
																			 * )
																			 * ;
																			 * consolidatedBill
																			 * =
																			 * calculationController
																			 * .
																			 * interstAmount
																			 * (
																			 * elRateMaster
																			 * ,
																			 * endDate
																			 * .
																			 * toDateMidnight
																			 * (
																			 * )
																			 * .
																			 * toDate
																			 * (
																			 * )
																			 * ,
																			 * startDate
																			 * .
																			 * toDateMidnight
																			 * (
																			 * )
																			 * .
																			 * toDate
																			 * (
																			 * )
																			 * ,
																			 * arrersAmount
																			 * )
																			 * ;
																			 * for
																			 * (
																			 * Entry
																			 * <
																			 * Object
																			 * ,
																			 * Object
																			 * >
																			 * map
																			 * :
																			 * consolidatedBill
																			 * .
																			 * entrySet
																			 * (
																			 * )
																			 * )
																			 * {
																			 * if
																			 * (
																			 * map
																			 * .
																			 * getKey
																			 * (
																			 * )
																			 * .
																			 * equals
																			 * (
																			 * "total"
																			 * )
																			 * )
																			 * {
																			 * interestOnArrearsAmount
																			 * =
																			 * interestOnArrearsAmount
																			 * +
																			 * (
																			 * Float
																			 * )
																			 * map
																			 * .
																			 * getValue
																			 * (
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * ":::::::::::::::: Interest  for Arrears Amount "
																			 * +
																			 * arrersAmount
																			 * +
																			 * " is "
																			 * +
																			 * (
																			 * Float
																			 * )
																			 * map
																			 * .
																			 * getValue
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * }
																			 * }
																			 * 
																			 * }
																			 * }
																			 * for
																			 * (
																			 * ELRateMaster
																			 * elRateMaster
																			 * :
																			 * elRateMasterList
																			 * )
																			 * {
																			 * if
																			 * (
																			 * elRateMaster
																			 * .
																			 * getRateName
																			 * (
																			 * )
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * .
																			 * equalsIgnoreCase
																			 * (
																			 * "Rate of interest"
																			 * )
																			 * )
																			 * {
																			 * float
																			 * taxAmount
																			 * =
																			 * previousTaxAmountL
																			 * ;
																			 * consolidatedBill
																			 * =
																			 * calculationController
																			 * .
																			 * interstAmount
																			 * (
																			 * elRateMaster
																			 * ,
																			 * endDate
																			 * .
																			 * toDateMidnight
																			 * (
																			 * )
																			 * .
																			 * toDate
																			 * (
																			 * )
																			 * ,
																			 * startDate
																			 * .
																			 * toDateMidnight
																			 * (
																			 * )
																			 * .
																			 * toDate
																			 * (
																			 * )
																			 * ,
																			 * taxAmount
																			 * )
																			 * ;
																			 * for
																			 * (
																			 * Entry
																			 * <
																			 * Object
																			 * ,
																			 * Object
																			 * >
																			 * map
																			 * :
																			 * consolidatedBill
																			 * .
																			 * entrySet
																			 * (
																			 * )
																			 * )
																			 * {
																			 * if
																			 * (
																			 * map
																			 * .
																			 * getKey
																			 * (
																			 * )
																			 * .
																			 * equals
																			 * (
																			 * "total"
																			 * )
																			 * )
																			 * {
																			 * interestOnTaxAmount
																			 * =
																			 * interestOnTaxAmount
																			 * +
																			 * (
																			 * Float
																			 * )
																			 * map
																			 * .
																			 * getValue
																			 * (
																			 * )
																			 * ;
																			 * logger
																			 * .
																			 * info
																			 * (
																			 * ":::::::::::::::: Interest for Tax Amount "
																			 * +
																			 * taxAmount
																			 * +
																			 * " is "
																			 * +
																			 * (
																			 * Float
																			 * )
																			 * map
																			 * .
																			 * getValue
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * }
																			 * }
																			 * 
																			 * }
																			 * }
																			 * }
																			 * }
																			 */
							}
							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						}

					} else if (electricityLedgerEntity.getPostType().trim().equalsIgnoreCase("ADJUSTMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF Else "+ electricityLedgerEntity.getPostType());
					} else {
						logger.info("electricityLedgerEntity.getPostType ---- in Else "+ electricityLedgerEntity.getPostType());
						if (previouBillEntity.getArrearsAmount() > 0) {
							logger.info("billEntity.getArrearsAmount() "+ previouBillEntity.getArrearsAmount());
							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(previouBillEntity.getBillDate());
							Days d = Days.daysBetween(startDate, endDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "+ days);

							int lastLedgerId = electricityLedgerService
									.getLastArrearsLedgerBasedOnPayment(
											accountID, previousBillDate,
											transactionCode).intValue();
							ElectricityLedgerEntity ledgerEntity = electricityLedgerService
									.find(lastLedgerId);
							logger.info("ledgerEntity ---------- "+ ledgerEntity.getPostType());
							List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService
									.findAllById1(ledgerEntity.getElLedgerid());
							for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("EL_TAX")) {
									previousTaxAmountL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity
										.getTransactionCode().trim()
										.equalsIgnoreCase("EL_INTEREST")) {
									rateOfInterestL = (float) electricitySubLedgerEntity
											.getBalanceAmount();
								}
								if (electricitySubLedgerEntity.getTransactionCode().trim().equalsIgnoreCase("EL_TAX_INTEREST")) {
									interestOnTaxL = (float) electricitySubLedgerEntity.getBalanceAmount();
								}
							}

							logger.info("rateOfInterestL " + rateOfInterestL);
							logger.info("previousTaxAmountL "+ previousTaxAmountL);
							logger.info("interestOnTaxL " + interestOnTaxL);

							for (ELRateMaster elRateMaster : elRateMasterList) {
								if (elRateMaster.getRateName().trim().equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (previouBillEntity.getArrearsAmount() - (rateOfInterestL+ previousTaxAmountL + interestOnTaxL));
									logger.info("arrersAmount " + arrersAmount);
									consolidatedBill = calculationController.interstAmountForMonthWise(elRateMaster, endDate.toDateMidnight().toDate(),startDate.toDateMidnight().toDate(),arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "+ arrersAmount+ " is "+ (Float) map.getValue());
										}
									}
								}
							}

							for (ELRateMaster elRateMaster : elRateMasterList) {
								if (elRateMaster.getRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmountL;
									consolidatedBill = calculationController
											.interstAmountForMonthWise(
													elRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}
						}
						if (previouBillEntity.getBillAmount() > 0) {
							logger.info("billEntity.getBillAmount() "
									+ previouBillEntity.getBillAmount());

							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(
									previouBillEntity.getBillDate());
							Days d = Days.daysBetween(endDate, startDate);
							int days = d.getDays();
							logger.info("============ Arrears Amount interst days ========= "
									+ days);
							for (ElectricityBillLineItemEntity billLineItemEntity : previouBillEntity
									.getBillLineItemEntities()) {
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("EL_TAX")) {
									previousTaxAmount = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim().equalsIgnoreCase("EL_INTEREST")) {
									rateOfInterest = (float) billLineItemEntity
											.getBalanceAmount();
								}
								if (billLineItemEntity.getTransactionCode()
										.trim()
										.equalsIgnoreCase("EL_TAX_INTEREST")) {
									interestOnTax = (float) billLineItemEntity
											.getBalanceAmount();
								}
							}

							for (ELRateMaster elRateMaster : elRateMasterList) {
								if (elRateMaster.getRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float arrersAmount = (float) (previouBillEntity
											.getBillAmount() - (rateOfInterest
											+ previousTaxAmount + interestOnTax));
									consolidatedBill = calculationController
											.interstAmountForMonthWise(
													elRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													arrersAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnArrearsAmount = interestOnArrearsAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest  for Arrears Amount "
													+ arrersAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}

							logger.info("rateOfInterest " + rateOfInterest);
							logger.info("previousTaxAmount "
									+ previousTaxAmount);
							logger.info("interestOnTax " + interestOnTax);

							for (ELRateMaster elRateMaster : elRateMasterList) {
								if (elRateMaster.getRateName().trim()
										.equalsIgnoreCase("Rate of interest")) {
									float taxAmount = previousTaxAmount;
									consolidatedBill = calculationController
											.interstAmountForMonthWise(
													elRateMaster, endDate
															.toDateMidnight()
															.toDate(),
													startDate.toDateMidnight()
															.toDate(),
													taxAmount);
									for (Entry<Object, Object> map : consolidatedBill
											.entrySet()) {
										if (map.getKey().equals("total")) {
											interestOnTaxAmount = interestOnTaxAmount
													+ (Float) map.getValue();
											logger.info(":::::::::::::::: Interest for Tax Amount "
													+ taxAmount
													+ " is "
													+ (Float) map.getValue());
										}
									}

								}
							}
						}
					}
				} else {
					logger.info("------------------ First Month Bill -----------------------");
				}

			}
		}
		intersts.put("interestOnTaxAmount", interestOnTaxAmount);
		intersts.put("interestOnArrearsAmount", interestOnArrearsAmount);
		return intersts;
	}

	/*************************** Common calcualtion methods 
	 * @param rateMasterID ******************************/
	@SuppressWarnings("unused")
	private ElectricityBillEntity saveBill(int accountID, float totalAmount,
			Date previousBillDate, Date currentBillDate, String typeOfService,
			float arrearsAmount, Double avgUnits, Integer avgCount,
			String billType, Double avgAmount, String postType, int rateMasterID) {
		ElectricityBillEntity billEntity = new ElectricityBillEntity();
		billEntity.setAccountId(accountID);
		billEntity.setArrearsAmount(getLastBillArrearsAmount("ARREARS",accountID, typeOfService));
		logger.info("getLastBillArrearsAmount(ARREARS,accountID, typeOfService) == "+getLastBillArrearsAmount("ARREARS",accountID, typeOfService));
		if (totalAmount >= 0) {
			billEntity.setBillAmount((double) totalAmount);
		} else {
			billEntity.setBillAmount(0d);
		}

		if (avgAmount >= 0) {
			billEntity.setAvgAmount(-avgAmount);
		} else {
			billEntity.setAvgAmount(0d);
		}
		Double netAmount = (billEntity.getArrearsAmount() + totalAmount + billEntity.getAvgAmount());
		billEntity.setNetAmount(netAmount);
		/*if (netAmount >= 0) {
			billEntity.setNetAmount(netAmount);
		} else {
			billEntity.setNetAmount(0d);
		}*/
		
		billEntity.setElBillDate(new Timestamp(new java.util.Date().getTime()));
		billEntity.setPostType(postType);
		billEntity.setFromDate(new java.sql.Date(previousBillDate.getTime()));
		billEntity.setBillDate(new java.sql.Date(currentBillDate.getTime()));
		String configName = "Due Days";
		String status = "Active";
		String dueDays = electricityBillsService.getBillingConfigValue(configName,status);
		int days=0;
		if(dueDays!=null)
		{
			days = Integer.parseInt(dueDays);
		}
		
		Date billDueDate = addDays(currentBillDate, days);
		billEntity.setBillDueDate(new java.sql.Date(billDueDate.getTime()));
		Calendar c = Calendar.getInstance();
		c.setTime(currentBillDate);
		c.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date billMonth = new java.sql.Date(c.getTime().getTime());
		logger.info("billMonth ::::::::::::::: " + billMonth);
		billEntity.setBillMonth(billMonth);
		billEntity.setStatus("Generated");
		billEntity.setCreatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		billEntity.setLastUpdatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		billEntity
				.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
		billEntity.setTypeOfService(typeOfService);
		billEntity.setBillNo("BILL" + billParameterService.getSequencyNumber());
		billEntity.setAvgCount(avgCount);
		billEntity.setAvgUnits(avgUnits);
		billEntity.setBillType(billType);
		if(netAmount>0){
			
	double	latePaymentAnount = billController.interestCalculationEL(rateMasterID, netAmount.floatValue(),billEntity);	
	      billEntity.setLatePaymentAmount(latePaymentAnount);
		}else{
		billEntity.setLatePaymentAmount(0.0);
		}
		electricityBillsService.save(billEntity);
		Locale locale = null;
		return billEntity;
	}

	private void saveBillLineItems(ElectricityBillEntity billEntity,
			Set<ElectricityBillLineItemEntity> billLineItemEntities,
			Float roundoffValue, float arrearsAmount) {
		for (ElectricityBillLineItemEntity electricityBillLineItemEntity : billLineItemEntities) {
			electricityBillLineItemEntity.setElectricityBillEntity(billEntity);
			if (electricityBillLineItemEntity.getBalanceAmount() != 0) {
				electricityBillLineItemService
						.update(electricityBillLineItemEntity);
			}
		}
	}

	private void saveBillParameters(ElectricityBillEntity billEntity,
			List<ELRateMaster> elRateMasterList, int accountID,
			String typeOfService, Date previousBillDate, Date currentBillDate,
			Float uomValue, Integer billableDays, String presentMeterStaus,
			String previousMeterStaus, Float meterConstant,
			Float presentUOMReading, Float previousUOMReading,
			ElectricityBillEntity electricityBillEntity, String ecString,
			String fsaString, Float previousDgreading, Float PresentDgReading,
			Float dgmeterconstant, Object dgApplicable, Float dgunit,
			String todString, Object todapplicable, float pfReading,
			float connectedLoad, String meterChangeUnit, String tariffChange) {
		List<BillParameterMasterEntity> billParameterMasterEntitiesList = billingParameterMasterService
				.getBillParameterMasterByServiceType(typeOfService);
		logger.info("billParameterMasterEntitiesList ::::::::::: "
				+ billParameterMasterEntitiesList.size());

		for (BillParameterMasterEntity billParameterMasterEntity : billParameterMasterEntitiesList) {

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Number of days")) {
				ElectricityBillParametersEntity noOfDays = new ElectricityBillParametersEntity(
						"Integer", billableDays.toString(), "Active");
				noOfDays.setBillParameterMasterEntity(billParameterMasterEntity);
				noOfDays.setElectricityBillEntity(billEntity);
				billParameterService.update(noOfDays);
			}
			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Previous status")) {
				ElectricityBillParametersEntity previousStatus = new ElectricityBillParametersEntity(
						"String", previousMeterStaus, "Active");
				previousStatus
						.setBillParameterMasterEntity(billParameterMasterEntity);
				previousStatus.setElectricityBillEntity(billEntity);
				billParameterService.update(previousStatus);
			}

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Present Status")) {
				if (presentMeterStaus.equalsIgnoreCase("Meter Change")) {
					ElectricityBillParametersEntity presentStatus = new ElectricityBillParametersEntity(
							"String", presentMeterStaus, "Active",
							meterChangeUnit);
					presentStatus
							.setBillParameterMasterEntity(billParameterMasterEntity);
					presentStatus.setElectricityBillEntity(billEntity);
					billParameterService.update(presentStatus);
				} else {
					ElectricityBillParametersEntity presentStatus = new ElectricityBillParametersEntity(
							"String", presentMeterStaus, "Active");
					presentStatus
							.setBillParameterMasterEntity(billParameterMasterEntity);
					presentStatus.setElectricityBillEntity(billEntity);
					billParameterService.update(presentStatus);
				}

			}
			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Previous reading")) {
				ElectricityBillParametersEntity reviousReading = new ElectricityBillParametersEntity(
						"Integer", previousUOMReading.toString(), "Active");
				reviousReading
						.setBillParameterMasterEntity(billParameterMasterEntity);
				reviousReading.setElectricityBillEntity(billEntity);
				billParameterService.update(reviousReading);
			}

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Present reading")) {
				ElectricityBillParametersEntity presentReading = new ElectricityBillParametersEntity(
						"Integer", presentUOMReading.toString().toString(),
						"Active");
				presentReading
						.setBillParameterMasterEntity(billParameterMasterEntity);
				presentReading.setElectricityBillEntity(billEntity);
				billParameterService.update(presentReading);
			}

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Meter Constant")) {
				ElectricityBillParametersEntity meterConstantObject = new ElectricityBillParametersEntity(
						"Double", meterConstant.toString(), "Active");
				meterConstantObject
						.setBillParameterMasterEntity(billParameterMasterEntity);
				meterConstantObject.setElectricityBillEntity(billEntity);
				billParameterService.update(meterConstantObject);
			}

			if (billParameterMasterEntity.getBvmName()
					.equalsIgnoreCase("Units")) {
				ElectricityBillParametersEntity units = new ElectricityBillParametersEntity(
						"Double", uomValue.toString(), "Active");
				units.setBillParameterMasterEntity(billParameterMasterEntity);
				units.setElectricityBillEntity(billEntity);
				billParameterService.update(units);
			}

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"EC Slabs")) {
				ecString = ecString.replace("null", " ");
				ecString = ecString.replace("</br>", " ");
				ElectricityBillParametersEntity slabs = new ElectricityBillParametersEntity(
						"String", "NA", "Active");
				slabs.setNotes(ecString);
				slabs.setBillParameterMasterEntity(billParameterMasterEntity);
				slabs.setElectricityBillEntity(billEntity);
				billParameterService.update(slabs);
			}

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"FSA Slabs")) {
				fsaString = fsaString.replace("null", " ");
				fsaString = fsaString.replace("</br>", " ");
				ElectricityBillParametersEntity slabs = new ElectricityBillParametersEntity(
						"String", "NA", "Active");
				slabs.setNotes(fsaString);
				slabs.setBillParameterMasterEntity(billParameterMasterEntity);
				slabs.setElectricityBillEntity(billEntity);
				billParameterService.update(slabs);
			}
			if (tariffChange != null) {
				if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
						"Tariff Change")) {
					tariffChange = tariffChange.replaceAll("null", " ");
					ElectricityBillParametersEntity tariffChante = new ElectricityBillParametersEntity(
							"String", "NA", "Active");
					tariffChante.setNotes(tariffChange);
					tariffChante
							.setBillParameterMasterEntity(billParameterMasterEntity);
					tariffChante.setElectricityBillEntity(billEntity);
					billParameterService.update(tariffChante);
				}
			}

			if (pfReading != 0) {
				Float pfReading1 = pfReading;
				if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
						"PF")) {
					ElectricityBillParametersEntity pfReadingEntity = new ElectricityBillParametersEntity(
							"Integer", pfReading1.toString(), "Active");
					pfReadingEntity
							.setBillParameterMasterEntity(billParameterMasterEntity);
					pfReadingEntity.setElectricityBillEntity(billEntity);
					billParameterService.update(pfReadingEntity);
				}
				if (billParameterMasterEntity.getBvmName().trim()
						.equalsIgnoreCase("Maximum Demand Recorded")) {
					Float penaltyUnits = connectedLoad;
					if (penaltyUnits > 0) {
						ElectricityBillParametersEntity excessLoad = new ElectricityBillParametersEntity(
								"Integer", penaltyUnits.toString(), "Active");
						excessLoad
								.setBillParameterMasterEntity(billParameterMasterEntity);
						excessLoad.setElectricityBillEntity(billEntity);
						billParameterService.update(excessLoad);
					}
				}
			}
			if (dgApplicable != null) {
				if (dgApplicable.equals("Yes")) {
					if (billParameterMasterEntity.getBvmName()
							.equalsIgnoreCase("Previous DG reading")) {
						ElectricityBillParametersEntity previousdgReading = new ElectricityBillParametersEntity(
								"Integer", previousDgreading.toString(),
								"Active");
						previousdgReading
								.setBillParameterMasterEntity(billParameterMasterEntity);
						previousdgReading.setElectricityBillEntity(billEntity);
						billParameterService.update(previousdgReading);
					}
					if (billParameterMasterEntity.getBvmName()
							.equalsIgnoreCase("DG Units")) {
						ElectricityBillParametersEntity dgUnits = new ElectricityBillParametersEntity(
								"Integer", dgunit.toString(), "Active");
						dgUnits.setBillParameterMasterEntity(billParameterMasterEntity);
						dgUnits.setElectricityBillEntity(billEntity);
						billParameterService.update(dgUnits);
					}
					if (billParameterMasterEntity.getBvmName()
							.equalsIgnoreCase("Present  DG reading")) {
						ElectricityBillParametersEntity presentdgReading = new ElectricityBillParametersEntity(
								"Integer", PresentDgReading.toString(),
								"Active");
						presentdgReading
								.setBillParameterMasterEntity(billParameterMasterEntity);
						presentdgReading.setElectricityBillEntity(billEntity);
						billParameterService.update(presentdgReading);
					}
					if (billParameterMasterEntity.getBvmName()
							.equalsIgnoreCase("DG Meter Constant")) {
						ElectricityBillParametersEntity dgMeterConstant = new ElectricityBillParametersEntity(
								"Integer", dgmeterconstant.toString(), "Active");
						dgMeterConstant
								.setBillParameterMasterEntity(billParameterMasterEntity);
						dgMeterConstant.setElectricityBillEntity(billEntity);
						billParameterService.update(dgMeterConstant);
					}
				} else {

				}
			}
			if (todapplicable.equals("Yes")) {
				if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
						"Tod Slabs")) {

					ElectricityBillParametersEntity todslabs = new ElectricityBillParametersEntity(
							"String", "NA", "Active");
					todslabs.setNotes(todString);
					todslabs.setBillParameterMasterEntity(billParameterMasterEntity);
					todslabs.setElectricityBillEntity(billEntity);
					billParameterService.update(todslabs);
				}
			}

		}
	}

	public static float simpleInterest(float principle, float rate, float time) {
		float interest = (principle * rate * time) / 100;
		return interest;
	}

	public static float roundOff(float val, int places) {
		//BigDecimal bigDecima = new BigDecimal(val);
		//bigDecima = bigDecima.setScale(places, BigDecimal.ROUND_UP);
		float bigDecima = (float)Math.round(val);
		return bigDecima;
	}

	public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public Float getMeterConstant(Float meterConstantValue) {
		return (float) ((meterConstantValue - 1) * 100);
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

	@SuppressWarnings({ "rawtypes", "unused" })
	// Excel File Import For Bank_Statements
	@RequestMapping(value = "/btbill/upload", method = RequestMethod.POST)
	public @ResponseBody
	void uploadAssetDocument(@RequestParam MultipartFile files,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException, ParseException {
		logger.info("Inside Bill Upload Method");
		XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator rows = sheet.rowIterator();
		Date billdate = null;
		int i = 0;
		int accountId = 0;
		while (rows.hasNext()) {
			XSSFRow row = ((XSSFRow) rows.next());
			ElectricityBillEntity entity = new ElectricityBillEntity();
			entity.setCreatedBy((String) SessionData.getUserDetails().get(
					"userID"));
			entity.setLastUpdatedBy((String) SessionData.getUserDetails().get(
					"userID"));
			entity.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
			entity.setTypeOfService("Telephone Broadband");
			entity.setStatus("Generated");
			if (row.getRowNum() == 0) {
				continue; // just skip the rows if row number is 0 or 1
			}
			Integer accVal = 0;

			if (!StringUtils.isEmpty(row.getCell(0).getStringCellValue())) {
				String accountno = (row.getCell(0).getStringCellValue()).trim();
				accountId = tariffCalculationService
						.getAccountIdOnAccountNo(accountno);
				logger.info("Account no"+ (row.getCell(0).getStringCellValue()));
				entity.setAccountId(accountId);

			} else {
				logger.info("FAILED");
				i = 1;
			}

			// Account Number
			logger.info("AccountNo >>>>>>>" + row.getCell(0));
			if (!StringUtils.isEmpty(row.getCell(1).getStringCellValue())) {
				// Long accVal = Long.valueOf(row.getCell(1).getCellType());
				logger.info("BillNo" + (row.getCell(1).getStringCellValue()));
				entity.setBillNo((row.getCell(1).getStringCellValue()));
				// br.setAccountNo(accVal);
			} else {
				logger.info("FAILED");
				i = 1;
			}
			// TxtDate
			String dateVal = row.getCell(2).getDateCellValue().toString();
			if (!StringUtils.isEmpty(dateVal)) {
				DateFormat formatter = new SimpleDateFormat(
						"E MMM dd HH:mm:ss Z yyyy");
				Date date1 = (Date) formatter.parse(dateVal);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date1);

				int month1 = cal.get(Calendar.MONTH);
				int month = month1 + 1;
				logger.info("month" + month);
				int year = cal.get(Calendar.YEAR);
				logger.info("year" + year);
				String typeOfService = ("Telephone Broadband").trim();
				int accountid = accVal;
				List<Object> list = tariffCalculationService.getBillOnDate(
						month, year, typeOfService, accountid);
				logger.info("list.size()" + list.size());
				if (list.size() > 0) {
					PrintWriter out = response.getWriter();
					out.write("Bill Is Already Generated");
					break;
				} else {
					java.sql.Date dbdate = new java.sql.Date(date1.getTime());
					logger.info("new java.sql.Date(date.getTime()" + dbdate);
					entity.setBillMonth(dbdate);
				}

			} else {
				logger.info("FAILED");
				i = 1;
			}
			String issuedate = row.getCell(3).getDateCellValue().toString();
			if (!StringUtils.isEmpty(issuedate)) {

				DateFormat formatter = new SimpleDateFormat(
						"E MMM dd HH:mm:ss Z yyyy");
				Date d1 = (Date) formatter.parse(issuedate);

				java.sql.Date dbdate = new java.sql.Date(d1.getTime());

				entity.setBillDate(dbdate);
				logger.info("issuedate-entity.getBillDate()-====="
						+ entity.getBillDate());

			} else {
				logger.info("FAILED");
				i = 1;
			}
			String duedate = row.getCell(4).getDateCellValue().toString();
			if (!StringUtils.isEmpty(duedate)) {

				DateFormat formatter = new SimpleDateFormat(
						"E MMM dd HH:mm:ss Z yyyy");
				Date date1 = (Date) formatter.parse(duedate);

				java.sql.Date dbdate = new java.sql.Date(date1.getTime());
				entity.setBillDueDate(dbdate);

				logger.info("duedate-entity.getBillDate()-====="
						+ entity.getBillDate());

				// br.setTxDate(new Date(d.getTime()));
			} else {
				logger.info("FAILED");
				i = 1;
			}

			// Description
			if (!StringUtils.isEmpty(row.getCell(5).getStringCellValue())) {
				logger.info(row.getCell(5).getStringCellValue());
				// br.setDescription(row.getCell(3).getStringCellValue());
				entity.setPostType(row.getCell(5).getStringCellValue());
			} else {
				logger.info("FAILED");
				i = 1;
			}

			// Credit
			// int credit = (int) row.getCell(4).getNumericCellValue();
			logger.info("CREDIT >>>>>>>" + row.getCell(6));
			if (row.getCell(6) != null) {
				double credit = Double.valueOf(row.getCell(6)
						.getNumericCellValue());

				entity.setBillAmount(credit);
				entity.setArrearsAmount(0.0);
				entity.setAvgAmount(0.0);
				entity.setNetAmount(credit);
				entity.setBillType("Normal");
				// double creditVal = Double.parseDouble(credit);
				// br.setCredit(credit);
			} else {
				logger.info("FAILED");
				i = 1;
			}
			String sbilldate = row.getCell(7).getDateCellValue().toString();
			if (!StringUtils.isEmpty(sbilldate)) {

				DateFormat formatter = new SimpleDateFormat(
						"E MMM dd HH:mm:ss Z yyyy");
				billdate = (Date) formatter.parse(sbilldate);

				java.sql.Timestamp dbdate = new java.sql.Timestamp(
						billdate.getTime());

				entity.setElBillDate(dbdate);

			} else {
				logger.info("FAILED");
				i = 1;
			}

			Date previousbilldate = tariffCalculationService.getBillDate(
					accountId, "Telephone Broadband");
			entity.setFromDate((java.sql.Date) previousbilldate);

			String txcode = row.getCell(8).getStringCellValue();
			if (!StringUtils.isEmpty(row.getCell(8).getStringCellValue())) {
				logger.info(row.getCell(8).getStringCellValue());
				logger.info(txcode);
				String comma = ",";
				String[] trancode = txcode.split(comma);
				logger.info(trancode[0]);
				logger.info(trancode[1]);
				String tracode1 = trancode[0];
				String couln = ":";
				String[] valueofTraCode1 = tracode1.split(couln);

				String telAmountCode = valueofTraCode1[0];

				Integer telAmount = Integer.parseInt(valueofTraCode1[1]);
				logger.info("valuetrcode1" + telAmountCode);
				logger.info("amount1" + telAmount);
				String tracode2 = trancode[1];
				String[] valueofTraCode2 = tracode2.split(couln);
				String telTaxCode = valueofTraCode2[0];
				Integer telTax = Integer.parseInt(valueofTraCode2[1]);
				logger.info("telTaxCode" + telTaxCode);
				logger.info("telTax" + telTax);

				ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();
				ElectricityBillLineItemEntity billLineItemEntity1 = new ElectricityBillLineItemEntity();

				billLineItemEntity.setTransactionCode(telAmountCode);
				billLineItemEntity.setBalanceAmount(telAmount);
				billLineItemEntity1.setTransactionCode(telTaxCode);
				billLineItemEntity1.setBalanceAmount(telTax);
				billLineItemEntity.setCreditAmount(0);
				billLineItemEntity.setDebitAmount(0);
				billLineItemEntity.setStatus("Not Approved");
				billLineItemEntity.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				billLineItemEntity.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				billLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));

				billLineItemEntity1.setCreditAmount(0);
				billLineItemEntity1.setDebitAmount(0);
				billLineItemEntity1.setStatus("Not Approved");
				billLineItemEntity1.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				billLineItemEntity1.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				billLineItemEntity1.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));

				electricityBillsService.save(entity);

				ElectricityBillLineItemEntity billLineItemEntity2 = new ElectricityBillLineItemEntity(
						billLineItemEntity.getTransactionCode(),
						billLineItemEntity.getCreditAmount(),
						billLineItemEntity.getDebitAmount(),
						billLineItemEntity.getBalanceAmount(),
						billLineItemEntity.getStatus());
				ElectricityBillLineItemEntity billLineItemEntity3 = new ElectricityBillLineItemEntity(
						billLineItemEntity1.getTransactionCode(),
						billLineItemEntity1.getCreditAmount(),
						billLineItemEntity1.getDebitAmount(),
						billLineItemEntity1.getBalanceAmount(),
						billLineItemEntity1.getStatus());

				billLineItemEntity2.setElectricityBillEntity(entity);
				billLineItemEntity3.setElectricityBillEntity(entity);
				electricityBillLineItemService.save(billLineItemEntity2);
				electricityBillLineItemService.save(billLineItemEntity3);
			} else {
				logger.info("FAILED");
				i = 1;
			}

			int days = Days.daysBetween(new DateTime(previousbilldate),
					new DateTime(billdate)).getDays();
			logger.info(":::::::Number of Days::::::::" + days);
			String typeOfService = "Telephone_Broadband";
			List<BillParameterMasterEntity> billParameterMasterEntitiesList = billingParameterMasterService
					.getBillParameterMasterByServiceType(typeOfService);
			for (BillParameterMasterEntity billParameterMasterEntity : billParameterMasterEntitiesList) {
				logger.info("Bill parameters servic type :::"
						+ billParameterMasterEntity.getServiceType());

				if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
						"Number of days")) {
					ElectricityBillParametersEntity noOfDays = new ElectricityBillParametersEntity(
							"Integer", String.valueOf(days), "Active");
					noOfDays.setBillParameterMasterEntity(billParameterMasterEntity);
					noOfDays.setElectricityBillEntity(entity);
					billParameterService.save(noOfDays);
				}
			}
			// }
		}

	}

	private double getLastBillArrearsAmount(String postType, int accounId,String typeOfService) {
		ElectricityLedgerEntity lastTransactionLedgerEntity = electricityLedgerService.find(electricityLedgerService.getLastLedgerBillAreears(accounId, typeOfService));
		double arrearsAmount = lastTransactionLedgerEntity.getBalance();
		return arrearsAmount;
	}

	private void storeArrears(String postType, ElectricityBillEntity billEntity) {

		ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();

		int lastTransactionLedgerId = electricityLedgerService
				.getLastLedgerBillAreears(billEntity.getAccountId(),
						billEntity.getTypeOfService());

		ledgerEntity.setTransactionSequence((electricityLedgerService
				.getLedgerSequence(billEntity.getAccountId()).intValue()) + 1);
		ledgerEntity.setAccountId(billEntity.getAccountId());
		String typeOfService = billEntity.getTypeOfService();
		ledgerEntity.setLedgerType(typeOfService + " Ledger");
		ledgerEntity.setPostType("ARREARS");
		if (typeOfService.equals("Electricity")) {
			ledgerEntity.setTransactionCode("EL");
		}
		if (typeOfService.equals("Gas")) {
			ledgerEntity.setTransactionCode("GA");
		}
		if (typeOfService.equals("Solid Waste")) {
			ledgerEntity.setTransactionCode("SW");
		}
		if (typeOfService.equals("Water")) {
			ledgerEntity.setTransactionCode("WT");
		}
		if (typeOfService.equals("Others")) {
			ledgerEntity.setTransactionCode("OT");
		}
		if (typeOfService.equals("CAM")) {
			ledgerEntity.setTransactionCode("CAM");
		}
		if (typeOfService.equals("Telephone Broadband")) {
			ledgerEntity.setTransactionCode("TEL");
		}
		
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		Calendar calLast = Calendar.getInstance();
		int lastYear = calLast.get(Calendar.YEAR)-1;

		ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
		ledgerEntity.setPostReference(billEntity.getBillNo());
		ledgerEntity.setLedgerDate(billEntity.getBillDate());
		ledgerEntity.setAmount(billEntity.getArrearsAmount());
		ledgerEntity.setBalance(billEntity.getArrearsAmount());
		ledgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
		ledgerEntity.setStatus("Approved");

		electricityLedgerService.save(ledgerEntity);

		List<ElectricitySubLedgerEntity> lastTransactionSubLedger = electricitySubLedgerService
				.getLastSubLedger(lastTransactionLedgerId,
						ledgerEntity.getTransactionCode());

		for (ElectricitySubLedgerEntity electricitySubLedgerEntity : lastTransactionSubLedger) {

			ElectricitySubLedgerEntity subLedgerEntity = new ElectricitySubLedgerEntity();

			subLedgerEntity.setTransactionCode(electricitySubLedgerEntity
					.getTransactionCode());
			subLedgerEntity.setAmount(0);
			subLedgerEntity.setBalanceAmount(electricitySubLedgerEntity
					.getBalanceAmount());
			subLedgerEntity.setStatus("Approved");

			subLedgerEntity.setElectricityLedgerEntity(ledgerEntity);
			electricitySubLedgerService.save(subLedgerEntity);
		}
	}

	@RequestMapping(value = "/bill/getPreviousStatus", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<?> getPreviousStatus(HttpServletRequest req) {
		int billId = Integer.parseInt(req.getParameter("billId"));
		int accountId = Integer.parseInt(req.getParameter("accountId"));
		String serviceType = req.getParameter("serviceType");

		logger.info(":::billId:::" + billId);
		logger.info(":::accountId:::" + accountId);
		logger.info(":::serviceType:::" + serviceType);

		return null;
	}

	public float getAvgUnit(Date currentBillDate, String typeOfService,
			int accountId) {

		logger.info("----------------- No histrical data, average units based on sancation load ------------------");
		if (typeOfService.trim().equalsIgnoreCase("Electricity")) {
			float uomValue = 0;
			ServiceMastersEntity mastersEntity1 = serviceMasterService
					.getServiceMasterServicType(accountId, typeOfService);
			List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService
					.findAllByParentId(mastersEntity1.getServiceMasterId());
			float sancationLoad = 0;
			for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1) {

				if (serviceParametersEntity.getServiceParameterMaster()
						.getSpmName().equalsIgnoreCase("Sanctioned KW")
						|| serviceParametersEntity.getServiceParameterMaster()
								.getSpmName()
								.equalsIgnoreCase("Sanctioned KVA")
						|| serviceParametersEntity.getServiceParameterMaster()
								.getSpmName().equalsIgnoreCase("Sanctioned HP")) {
					sancationLoad = Float.parseFloat(serviceParametersEntity
							.getServiceParameterValue());
				}
			}
			logger.info("sancationLoad ---------------- " + sancationLoad);
			int tariffId = serviceMasterService
					.getServiceMasterByAccountNumber(accountId, typeOfService);
			ELRateMaster rateMaster = rateMasterService
					.getRateMasterByRateName(tariffId, "Average units on load");
			if (rateMaster != null) {
				if (rateMaster.getRateType().trim()
						.equalsIgnoreCase("Range Slab")) {
					List<ELRateSlabs> elRateSlabsList = rateSlabService
							.getRateSlabsForRangeSlab(rateMaster.getElrmid(),
									sancationLoad);
					for (ELRateSlabs elRateSlabs : elRateSlabsList) {
						if (elRateSlabs.getSlabRateType().trim()
								.equalsIgnoreCase("Multiplier")) {
							uomValue = elRateSlabs.getRate();
						}
					}
				}
			}
			return uomValue;
		} else {

			float uomValue = 0.0f;
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(currentBillDate);
			cal1.add(Calendar.YEAR, -1);
			Date lastYear = cal1.getTime();
			int bvmId = billingParameterMasterService.getServicMasterId(
					typeOfService, "Units");
			List<ElectricityBillEntity> billIdList = electricityBillsService
					.getBillEntityByAccountId(accountId, typeOfService);
			if (!billIdList.isEmpty()) {
				float avgUnits = 0;
				List<String> listValus = new ArrayList<>();
				for (ElectricityBillEntity electricityBillEntity : billIdList) {
					listValus = billParameterService.getAverageUnits(
							electricityBillEntity.getElBillId(), bvmId,
							lastYear, currentBillDate);
					for (String string : listValus) {
						avgUnits += Float.parseFloat(string);
					}
				}
				uomValue = avgUnits / billIdList.size();
			}
			return uomValue;

		}
	}

	@RequestMapping(value = "/bills/readTowerNames", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readTowerNames() {
		return tariffCalculationService.getTowerNames();
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/bill/generateBulkBill", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<Object, Object> generateBulkBill(HttpServletRequest req,
			HttpServletResponse response, @RequestBody String body)
			throws ParseException, JSONException {
		logger.info("::::::::::::::::::: In Bulk Bill Generation Method :::::::::::::::::::::");

		String blockName = req.getParameter("blockName");
		Float consumptionUnit = Float.parseFloat(req.getParameter("consumptionUnit"));

		String propertyId = req.getParameter("propertyId");
		Date presentdate = new SimpleDateFormat("dd/MM/yyyy").parse(req
				.getParameter("presentdate"));
		String serviceTypeList = req.getParameter("serviceTypeList");
		String billCalcType = req.getParameter("billCalcType");
		int blocId = Integer.parseInt(req.getParameter("blocId"));
		String postType = "Bill";
		logger.info("blockName" + blockName);
		logger.info(":::::::consumptionUnit::::::::::" + consumptionUnit);
		logger.info("propertyId" + propertyId);
		logger.info("presentdate" + presentdate);
		logger.info("serviceTypeList" + serviceTypeList);
		logger.info("blocId" + blocId);
		List<WTRateMaster> waterRateMasterList = new ArrayList<>();
		List<SolidWasteRateMaster> solidWasterRateMasterList = new ArrayList<>();
		PrintWriter out;
		int accountId = 0;
		Date getbilldate = null;
		Integer billableDays = 0;
		String accountNo = null;
		int propId = 0;
		if (blockName.equals("All Blocks")) {
			List<Integer> list = tariffCalculationService.getallBlocks();
			// for (Blocks blocks : list) {
			for (int i = 0; i < list.size(); i++) {

				Integer blockI = list.get(i);
				logger.info("::::::blockId:::::::" + blockI);
				List<Integer> properties = tariffCalculationService
						.getProertyId(blockI);
				for (Integer integer : properties) {
					propId = integer;
					logger.info("::::::::::propId::::::::" + propId);
					List<Integer> personIdList = tariffCalculationService
							.findPersonDetail(propId);
					for (Integer inte : personIdList) {
						accountId = tariffCalculationService.findAccountDetail(
								inte, propId, serviceTypeList);

						logger.info("------------accountId-----------"
								+ accountId);

						if (accountId != 0) {
							accountNo = tariffCalculationService
									.getAccontNoONAccountId(accountId);
							getbilldate = tariffCalculationService.getBillDate(
									accountId, serviceTypeList);
							String currentBillStatus = tariffCalculationService
									.getPreviousBillStatus(accountId,
											serviceTypeList, presentdate);
							if (currentBillStatus.equalsIgnoreCase("Generated")) {
								logger.info(":::::::::Bill For the "
										+ accountNo + "for" + presentdate
										+ " has been already generated:::::");
							} else {
								logger.info("###:::::::::::::::::::::::::"
										+ getbilldate
										+ "::::::::::::##########");
								if (getbilldate != null) {

									String previousBillStatus = tariffCalculationService
											.getPreviousBillStatus(accountId,
													serviceTypeList,
													getbilldate);
									if (previousBillStatus
											.equalsIgnoreCase("Generated")
											|| previousBillStatus
													.equalsIgnoreCase("Approved")) {

										try {
											out = response.getWriter();
											out.write("Bill of "
													+ serviceTypeList
													+ " for Account No "
													+ accountNo
													+ " has not been Posted, Please Post it to Generate Next Bill "
													+ "\n");
											// return null;
										} catch (Exception e) {
										}
									} else {
										LocalDate fromDate = new LocalDate(
												getbilldate);
										LocalDate toDate = new LocalDate(
												presentdate);
										billableDays = Days.daysBetween(
												fromDate, toDate).getDays();
										logger.info("::::::::::::::Billable Days::::::::::"
												+ billableDays);
										logger.info("::::::::::::getbilldate:::::::::::"
												+ getbilldate);
										int previousstatus = serviceStatus(
												presentdate, serviceTypeList,
												accountId);
										logger.info(":::::::::::::previousstatys::::::::::::"
												+ previousstatus);
										String meteredStatus = tariffCalculationService
												.getAccountMetered(accountId,
														serviceTypeList);
										if (meteredStatus
												.equalsIgnoreCase("No")) {
											if (serviceTypeList
													.equalsIgnoreCase("Water")) {
												int waterTariffId = serviceMasterService
														.getWaterTariffId(
																accountId,
																serviceTypeList);
												waterRateMasterList = tariffCalculationService
														.getWaterRateMaster(waterTariffId);
												logger.info(":::::::::::::"
														+ waterRateMasterList);
												Float previousreading = tariffCalculationService
														.getPreviousReading(
																accountId,
																serviceTypeList);
												logger.info("previousraeing"
														+ previousreading);
												if (waterRateMasterList.size() != 0) {
													logger.info("consumption unit at Ist place::::::::::::::::::::"
															+ consumptionUnit);
													logger.info("::::::::propId:::::"
															+ propId);
													logger.info("::::::::billCalcType:::::"
															+ billCalcType);
													if (billCalcType
															.trim()
															.equalsIgnoreCase(
																	"Square Feet")) {
														consumptionUnit = tariffCalculationService
																.getConsumptionUnitBasedOnSqFt(
																		propId,
																		consumptionUnit);
													} else {
														consumptionUnit = consumptionUnit + 0;
													}
													try {
														out = response
																.getWriter();
														out.write("Bill of "
																+ serviceTypeList
																+ " for Account No "
																+ accountNo
																+ " has been Generated "
																+ "\n");

													} catch (Exception e) {
													}
													saveWaterBulkBillParameters(
															waterRateMasterList,
															accountId,
															serviceTypeList,
															getbilldate,
															presentdate,
															consumptionUnit,
															billableDays,
															postType);
												} else {
													logger.info("For this Tariff "
															+ waterTariffId
															+ "  Rate Is  Not There");
												}
											} else if (serviceTypeList
													.equalsIgnoreCase("Solid Waste")) {
												int solidwasteId = serviceMasterService
														.getSolidWasteTariffId(
																accountId,
																serviceTypeList);
												solidWasterRateMasterList = tariffCalculationService
														.getSolidWasteRateMaster(solidwasteId);
												Float previousreading = tariffCalculationService
														.getPreviousReading(
																accountId,
																serviceTypeList);
												logger.info("::::::::::::previousraeing:::::::::::::"
														+ previousreading);
												logger.info("::::::::::::consumptionUnit:::::::::::"
														+ consumptionUnit);
												if (solidWasterRateMasterList
														.size() != 0) {
													logger.info("::::::::::::solidWasterRateMasterList::::::::::::"
															+ solidWasterRateMasterList
																	.get(0));
													saveSolidWasteBulkBillParameters(
															solidWasterRateMasterList,
															accountId,
															serviceTypeList,
															getbilldate,
															presentdate,
															consumptionUnit,
															billableDays,
															postType);
												} else {
													logger.info("No Rate For This Tariff Of SoidWaste");
												}
											}

										} else {
											logger.info("Account is Metered");
										}
									}

								} else {
									logger.info("Service for that Account is not Activated");
								}
							}

						} else {
							logger.info("Account for this customer is not there");
						}
					}
				}

			}

		} else if (propertyId.equals("0")) {
			logger.info("@@@@@@@@@@@@@@@@@In All Proerty method @@@@@@@@@@@@@@@@@@@@@@-"
					+ propertyId);
			List<Integer> properties = tariffCalculationService
					.getProertyId(blocId);
			for (Integer integer : properties) {
				propId = integer;
				logger.info("::::::::::propId::::::::" + propId);
				List<Integer> personIdList = tariffCalculationService
						.findPersonDetail(propId);
				for (Integer inte : personIdList) {
					accountId = tariffCalculationService.findAccountDetail(
							inte, propId, serviceTypeList);
					logger.info("------------accountId-----------" + accountId);
					if (accountId != 0) {
						getbilldate = tariffCalculationService.getBillDate(
								accountId, serviceTypeList);
						logger.info("###:::::::::::::::::::::::::"
								+ getbilldate + "::::::::::::##########");
						if (getbilldate != null) {
							LocalDate fromDate = new LocalDate(getbilldate);
							LocalDate toDate = new LocalDate(presentdate);
							billableDays = Days.daysBetween(fromDate, toDate)
									.getDays();
							logger.info("::::::::::::::Billable Days::::::::::"
									+ billableDays);
							logger.info("::::::::::::getbilldate:::::::::::"
									+ getbilldate);
							int previousstatus = serviceStatus(presentdate,
									serviceTypeList, accountId);
							logger.info(":::::::::::::previousstatys::::::::::::"
									+ previousstatus);
							String meteredStatus = tariffCalculationService
									.getAccountMetered(accountId,
											serviceTypeList);
							if (meteredStatus.equalsIgnoreCase("No")) {
								if (serviceTypeList.equalsIgnoreCase("Water")) {
									int waterTariffId = serviceMasterService
											.getWaterTariffId(accountId,
													serviceTypeList);
									waterRateMasterList = tariffCalculationService
											.getWaterRateMaster(waterTariffId);
									logger.info(":::::::::::::"
											+ waterRateMasterList);
									Float previousreading = tariffCalculationService
											.getPreviousReading(accountId,
													serviceTypeList);
									logger.info("previousraeing"
											+ previousreading);
									if (waterRateMasterList.size() != 0) {
										logger.info("consumption unit at Ist place::::::::::::::::::::"
												+ consumptionUnit);
										logger.info("::::::::propId:::::"
												+ propId);
										logger.info("::::::::billCalcType:::::"
												+ billCalcType);
										if (billCalcType
												.trim()
												.equalsIgnoreCase("Square Feet")) {
											consumptionUnit = tariffCalculationService
													.getConsumptionUnitBasedOnSqFt(
															propId,
															consumptionUnit);
										} else {
											consumptionUnit = consumptionUnit + 0;
										}
										saveWaterBulkBillParameters(
												waterRateMasterList, accountId,
												serviceTypeList, getbilldate,
												presentdate, consumptionUnit,
												billableDays, postType);
									} else {
										logger.info("For this Tariff "
												+ waterTariffId
												+ "  Rate Is  Not There");
									}
								} else if (serviceTypeList
										.equalsIgnoreCase("Solid Waste")) {
									int solidwasteId = serviceMasterService
											.getSolidWasteTariffId(accountId,
													serviceTypeList);
									solidWasterRateMasterList = tariffCalculationService
											.getSolidWasteRateMaster(solidwasteId);
									Float previousreading = tariffCalculationService
											.getPreviousReading(accountId,
													serviceTypeList);
									logger.info("::::::::::::previousraeing:::::::::::::"
											+ previousreading);
									logger.info("::::::::::::consumptionUnit:::::::::::"
											+ consumptionUnit);
									if (solidWasterRateMasterList.size() != 0) {
										logger.info("::::::::::::solidWasterRateMasterList::::::::::::"
												+ solidWasterRateMasterList
														.get(0));
										saveSolidWasteBulkBillParameters(
												solidWasterRateMasterList,
												accountId, serviceTypeList,
												getbilldate, presentdate,
												consumptionUnit, billableDays,
												postType);
									} else {
										logger.info("No Rate For This Tariff Of SoidWaste");
									}
								}

							} else {
								logger.info("Account is Metered");
							}

						} else {
							logger.info("Service for that Account is not Activated");
						}

					} else {
						logger.info("Account for this customer is not there");
					}
				}
			}
		} else {
			String comma = ",";
			String[] trancode = propertyId.split(comma);
			Integer personId = 0;
			for (int i = 0; i < trancode.length; i++) {
				int propid = Integer.parseInt(trancode[i]);
				List<Integer> personIdList = tariffCalculationService
						.findPersonDetail(propid);
				for (Integer integer : personIdList) {
					personId = integer;
					accountId = tariffCalculationService.findAccountDetail(
							integer, propid, serviceTypeList);
					if (accountId != 0) {
						getbilldate = tariffCalculationService.getBillDate(
								accountId, serviceTypeList);
						logger.info("###:::::::::::::::::::::::::"
								+ getbilldate + "::::::::::::##########");
						if (getbilldate != null) {
							LocalDate fromDate = new LocalDate(getbilldate);
							LocalDate toDate = new LocalDate(presentdate);
							billableDays = Days.daysBetween(fromDate, toDate)
									.getDays();
							logger.info("::::::::::::::Billable Days::::::::::"
									+ billableDays);
							logger.info("::::::::::::getbilldate:::::::::::"
									+ getbilldate);
							int previousstatus = serviceStatus(presentdate,
									serviceTypeList, accountId);
							logger.info(":::::::::::::previousstatys::::::::::::"
									+ previousstatus);
							String meteredStatus = tariffCalculationService
									.getAccountMetered(accountId,
											serviceTypeList);
							if (meteredStatus.equalsIgnoreCase("No")) {
								if (serviceTypeList.equalsIgnoreCase("Water")) {
									int waterTariffId = serviceMasterService
											.getWaterTariffId(accountId,
													serviceTypeList);
									waterRateMasterList = tariffCalculationService
											.getWaterRateMaster(waterTariffId);
									logger.info(":::::::::::::"
											+ waterRateMasterList);
									Float previousreading = tariffCalculationService
											.getPreviousReading(accountId,
													serviceTypeList);
									logger.info("previousraeing"
											+ previousreading);
									if (waterRateMasterList.size() != 0) {
										logger.info("consumption unit at Ist place::::::::::::::::::::"
												+ consumptionUnit);
										logger.info("::::::::propId:::::"
												+ propId);
										logger.info("::::::::billCalcType:::::"
												+ billCalcType);
										if (billCalcType
												.trim()
												.equalsIgnoreCase("Square Feet")) {
											consumptionUnit = tariffCalculationService
													.getConsumptionUnitBasedOnSqFt(
															propId,
															consumptionUnit);
										} else {
											consumptionUnit = consumptionUnit + 0;
										}
										saveWaterBulkBillParameters(
												waterRateMasterList, accountId,
												serviceTypeList, getbilldate,
												presentdate, consumptionUnit,
												billableDays, postType);
									} else {
										logger.info("For this Tariff "
												+ waterTariffId
												+ "  Rate Is  Not There");
									}
								} else if (serviceTypeList
										.equalsIgnoreCase("Solid Waste")) {
									int solidwasteId = serviceMasterService
											.getSolidWasteTariffId(accountId,
													serviceTypeList);
									solidWasterRateMasterList = tariffCalculationService
											.getSolidWasteRateMaster(solidwasteId);
									Float previousreading = tariffCalculationService
											.getPreviousReading(accountId,
													serviceTypeList);
									logger.info("::::::::::::previousraeing:::::::::::::"
											+ previousreading);
									logger.info("::::::::::::consumptionUnit:::::::::::"
											+ consumptionUnit);
									if (solidWasterRateMasterList.size() != 0) {
										logger.info("::::::::::::solidWasterRateMasterList::::::::::::"
												+ solidWasterRateMasterList
														.get(0));
										saveSolidWasteBulkBillParameters(
												solidWasterRateMasterList,
												accountId, serviceTypeList,
												getbilldate, presentdate,
												consumptionUnit, billableDays,
												postType);
									} else {
										logger.info("No Rate For This Tariff Of SoidWaste");
									}
								}

							} else {
								logger.info("Account is Metered");
							}

						} else {
							logger.info("Service for that Account is not Activated");
						}

					} else {
						logger.info("Account for this customer is not there");
					}
				}
			}
		}

		return null;
	}

	private void saveSolidWasteBulkBillParameters(
			List<SolidWasteRateMaster> sWasteRateMasterList, int accountID,
			String typeOfService, Date previousBillDate, Date currentBillDate,
			Float uomValue, Integer billableDays, String postType) {
		float ecAmount = 0;
		float taxAmount = 0;
		float totalAmount;
		Float roundoffValue = 0f;
		float rateOfInterest = 0f;
		float arrearsAmount = 0f;
		LocalDate startDate = new LocalDate(previousBillDate);
		LocalDate endDate = new LocalDate(currentBillDate);
		Months months = Months.monthsBetween(startDate, endDate);
		int monthsInYear = months.getMonths();
		if (monthsInYear > 0) {
			uomValue = (uomValue * monthsInYear);
		} else {
			logger.info("::::::::::Bill amount for less than month:::::::::");
			uomValue = uomValue + 0;
		}

		Set<ElectricityBillLineItemEntity> billLineItemEntities = new LinkedHashSet<>();
		ElectricityBillLineItemEntity domesticGeneral = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity swTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity roundOffBillLineItemEntity = new ElectricityBillLineItemEntity();

		for (SolidWasteRateMaster sWasteRateMaster : sWasteRateMasterList) {
			if (sWasteRateMaster.getSolidWasteRateName().equalsIgnoreCase(
					"Residential Collection")) {
				logger.info(":::::::::::: In "
						+ sWasteRateMaster.getSolidWasteRateName()
						+ "::::::::::");
				domesticGeneral.setTransactionCode("SW_RES");

				ecAmount = ecAmount + uomValue;
				logger.info("ecAmount ::::::::: " + ecAmount);
				domesticGeneral.setBalanceAmount(ecAmount);
				domesticGeneral.setCreditAmount(0);
				domesticGeneral.setDebitAmount(0);
				domesticGeneral.setStatus("Not Approved");
				domesticGeneral.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				domesticGeneral.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				domesticGeneral.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(domesticGeneral);
			}
		}

		for (SolidWasteRateMaster sWasteRateMaster : sWasteRateMasterList) {
			if (sWasteRateMaster.getSolidWasteRateName()
					.equalsIgnoreCase("Tax")) {
				logger.info(":::::::::::: In "
						+ sWasteRateMaster.getSolidWasteRateName()
						+ "::::::::::");
				swTax.setTransactionCode("SW_TAX");
				taxAmount = taxAmount
						+ calculationController.solidWasteTariffAmount(
								sWasteRateMaster, previousBillDate,
								currentBillDate, ecAmount);
				logger.info("taxAmount ::::::::: " + taxAmount);
				swTax.setBalanceAmount(taxAmount);
				swTax.setCreditAmount(0);
				swTax.setDebitAmount(0);
				swTax.setStatus("Not Approved");
				swTax.setCreatedBy((String) SessionData.getUserDetails().get(
						"userID"));
				swTax.setLastUpdatedBy((String) SessionData.getUserDetails()
						.get("userID"));
				swTax.setLastUpdatedDT(new java.sql.Timestamp(new Date()
						.getTime()));
				billLineItemEntities.add(swTax);
			}

		}
		totalAmount = roundOff(ecAmount + taxAmount + arrearsAmount
				+ rateOfInterest, 0);
		roundoffValue = roundOff(totalAmount
				- (ecAmount + taxAmount + arrearsAmount + rateOfInterest), 2);
		logger.info("::::::::::::::::::::::::: totalAmount ::::::::::::::: "
				+ totalAmount);

		roundOffBillLineItemEntity.setCreditAmount(0);
		roundOffBillLineItemEntity.setDebitAmount(0);
		roundOffBillLineItemEntity.setStatus("Not Approved");
		roundOffBillLineItemEntity.setCreatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
				new Date().getTime()));
		roundOffBillLineItemEntity.setBalanceAmount(roundoffValue);
		roundOffBillLineItemEntity.setTransactionCode("WT_ROF");
		billLineItemEntities.add(roundOffBillLineItemEntity);

		ElectricityBillEntity billEntity = saveBill(accountID, totalAmount,
				previousBillDate, currentBillDate, typeOfService,
				arrearsAmount, 0.0, 0, "Bulk Bill", 0.0, postType,2);
		saveBillLineItems(billEntity, billLineItemEntities, roundoffValue,
				arrearsAmount);
		saveBillSolidWasteBulkParameters(billEntity, sWasteRateMasterList,
				accountID, typeOfService, previousBillDate, currentBillDate,
				uomValue, billableDays);

	}

	private void saveBillSolidWasteBulkParameters(
			ElectricityBillEntity billEntity1,
			List<SolidWasteRateMaster> solidWasterRateMasterList,
			int accountID, String typeOfService, Date previousBillDate,
			Date currentBillDate, Float uomValue, Integer billableDays) {

		List<BillParameterMasterEntity> billParameterMasterEntitiesList = billingParameterMasterService
				.getBillParameterMasterByServiceType(typeOfService);
		logger.info("billParameterMasterEntitiesList ::::::::::: "
				+ billParameterMasterEntitiesList.size());

		for (BillParameterMasterEntity billParameterMasterEntity : billParameterMasterEntitiesList) {
			logger.info("Bill parameters servic type :::"
					+ billParameterMasterEntity.getServiceType());

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Number of days")) {
				ElectricityBillParametersEntity noOfDays = new ElectricityBillParametersEntity(
						"Integer", billableDays.toString(), "Active");
				noOfDays.setBillParameterMasterEntity(billParameterMasterEntity);
				noOfDays.setElectricityBillEntity(billEntity1);
				billParameterService.save(noOfDays);
			}

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Volume(KG)")) {
				ElectricityBillParametersEntity units = new ElectricityBillParametersEntity(
						"Integer", uomValue.toString(), "Active");
				units.setBillParameterMasterEntity(billParameterMasterEntity);
				units.setElectricityBillEntity(billEntity1);
				billParameterService.save(units);
			}

		}

	}

	private void saveWaterBulkBillParameters(
			List<WTRateMaster> waterRateMasterList, int accountID,
			String typeOfService, Date previousBillDate, Date currentBillDate,
			Float uomValue, Integer billableDays, String postType) {
		ElectricityBillEntity previousBillEntity = electricityBillsService
				.getPreviousBill(accountID, typeOfService, previousBillDate,
						"Bill");
		float wcAmount = 0;
		float scAmount = 0;
		float solAmount = 0;
		float fcAmount = 0;
		float roAmount = 0f;
		float taxAmount = 0;
		float totalAmount;
		Float roundoffValue = 0f;
		float rateOfInterest = 0f;
		float arrearsAmount = 0f;
		String wtSlabString = null;
		HashMap<String, Object> consolidatedBill = new LinkedHashMap<>();
		Double avgAmount = 0d;

		ElectricityBillEntity billEntityForArrears = new ElectricityBillEntity();
		billEntityForArrears.setAccountId(accountID);
		billEntityForArrears.setArrearsAmount(getLastBillArrearsAmount(
				"ARREARS", accountID, typeOfService));
		billEntityForArrears.setElBillDate(new java.sql.Timestamp(
				currentBillDate.getTime()));
		billEntityForArrears.setPostType("Bill");
		billEntityForArrears.setBillDate(new java.sql.Date(currentBillDate
				.getTime()));
		String configName = "Due Days";
		String status = "Active";
		String dueDays = electricityBillsService.getBillingConfigValue(configName,status);
		int days=0;
		if(dueDays!=null)
		{
			days = Integer.parseInt(dueDays);
		}
		
		Date billDueDate = addDays(currentBillDate, days);
		billEntityForArrears.setBillDueDate(new java.sql.Date(billDueDate
				.getTime()));
		Calendar c = Calendar.getInstance();
		c.setTime(currentBillDate);
		c.set(Calendar.DAY_OF_MONTH, 1);

		java.sql.Date billMonth = new java.sql.Date(c.getTime().getTime());
		logger.info("billMonth ::::::::::::::: " + billMonth);
		billEntityForArrears.setBillMonth(billMonth);
		billEntityForArrears.setStatus("Generated");
		billEntityForArrears.setCreatedBy((String) SessionData.getUserDetails()
				.get("userID"));
		billEntityForArrears.setLastUpdatedBy((String) SessionData
				.getUserDetails().get("userID"));
		billEntityForArrears.setLastUpdatedDT(new java.sql.Timestamp(new Date()
				.getTime()));
		billEntityForArrears.setTypeOfService(typeOfService);
		billEntityForArrears.setBillNo("BILL"
				+ billParameterService.getSequencyNumber());
		logger.info("billEntity.getBillDueDate() ::::::::::::::: "
				+ billEntityForArrears.getBillDueDate());
		storeArrears("ARREARS", billEntityForArrears);

		Set<ElectricityBillLineItemEntity> billLineItemEntities = new LinkedHashSet<>();
		ElectricityBillLineItemEntity waterCharges = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity sweageCharges = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity roCharges = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity waterTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity waterFixcedCharages = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity solorCharages = new ElectricityBillLineItemEntity();

		ElectricityBillLineItemEntity roundOffBillLineItemEntity = new ElectricityBillLineItemEntity();
		logger.info("::::::::::uomValue::::::::::" + uomValue);
		for (WTRateMaster waterRateMaster : waterRateMasterList) {

			if (waterRateMaster.getWtRateName().equalsIgnoreCase(
					"Water charges")) {
				if (previousBillEntity != null) {
					if (previousBillEntity.getAvgCount() >= 1) {
						consolidatedBill = calculationController
								.tariffAmountAvgCountWater(waterRateMaster,
										previousBillDate, currentBillDate,
										uomValue,
										previousBillEntity.getAvgCount());

					} else {
						logger.info("previous bill entity is null:::::::averagecountlessthan1"
								+ uomValue);
						consolidatedBill = calculationController
								.waterTariffAmount(waterRateMaster,
										previousBillDate, currentBillDate,
										uomValue);
					}
				} else {
					logger.info("previous bill entity is null:::::::uomvalue"
							+ uomValue);
					consolidatedBill = calculationController.waterTariffAmount(
							waterRateMaster, previousBillDate, currentBillDate,
							uomValue);
				}
				for (Entry<String, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						wcAmount = (Float) map.getValue();
					}
					if (map.getKey().equals("wtSlabString")) {
						wtSlabString = (String) map.getValue();
						logger.info(":::::::::::::::wtSlabString::::::::::::::"
								+ wtSlabString);
					}
				}
				logger.info(":::::::::::: In "
						+ waterRateMaster.getWtRateName() + "::::::::::");
				waterCharges.setTransactionCode("WT_WC");
				logger.info("wcAmount ::::::::: " + wcAmount);
				waterCharges.setBalanceAmount(wcAmount);
				waterCharges.setCreditAmount(0);
				waterCharges.setDebitAmount(0);
				waterCharges.setStatus("Not Approved");
				waterCharges.setCreatedBy((String) SessionData.getUserDetails()
						.get("userID"));
				waterCharges.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				waterCharges.setLastUpdatedDT(new java.sql.Timestamp(new Date()
						.getTime()));
				billLineItemEntities.add(waterCharges);
			}
			if (waterRateMaster.getWtRateName().equalsIgnoreCase(
					"Solar charges")) {
				String solApplicable = null;
				ServiceMastersEntity mastersEntity1 = serviceMasterService
						.getServiceMasterServicType(accountID, typeOfService);
				List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService
						.findAllByParentId(mastersEntity1.getServiceMasterId());
				for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1) {

					if (serviceParametersEntity.getServiceParameterMaster()
							.getSpmName().equalsIgnoreCase("Solar charges")) {
						solApplicable = serviceParametersEntity
								.getServiceParameterValue();
					}
				}
				logger.info("solApplicable --------------- " + solApplicable);
				if (solApplicable != null
						&& solApplicable.equalsIgnoreCase("Yes")) {

					LocalDate fromDate = new LocalDate(previousBillDate);
					LocalDate toDate = new LocalDate(currentBillDate);
					PeriodType monthDay = PeriodType.yearMonthDay()
							.withYearsRemoved();
					Period difference = new Period(fromDate, toDate, monthDay);
					float billableMonths = difference.getMonths();
					int daysAfterMonth = difference.getDays();

					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(currentBillDate);
					float daysToMonths = (float) daysAfterMonth
							/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
					float netMonth = daysToMonths + billableMonths;

					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCountWater(waterRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth,
											previousBillEntity.getAvgCount());

						} else {
							consolidatedBill = calculationController
									.waterTariffAmount(waterRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth);
						}
					} else {
						consolidatedBill = calculationController
								.waterTariffAmount(waterRateMaster,
										previousBillDate, currentBillDate,
										1 * netMonth);
					}

					for (Entry<String, Object> map : consolidatedBill
							.entrySet()) {
						if (map.getKey().equals("total")) {
							solAmount = (Float) map.getValue();
						}
					}
					solorCharages.setTransactionCode("WT_SOL");
					solorCharages.setBalanceAmount(solAmount);
					solorCharages.setCreditAmount(0);
					solorCharages.setDebitAmount(0);
					solorCharages.setStatus("Not Approved");
					solorCharages.setCreatedBy((String) SessionData
							.getUserDetails().get("userID"));
					solorCharages.setLastUpdatedBy((String) SessionData
							.getUserDetails().get("userID"));
					solorCharages.setLastUpdatedDT(new java.sql.Timestamp(
							new Date().getTime()));
					billLineItemEntities.add(solorCharages);
				}
			}

			if (waterRateMaster.getWtRateName().equalsIgnoreCase("RO charges")) {
				String roApplicable = null;
				ServiceMastersEntity mastersEntity1 = serviceMasterService
						.getServiceMasterServicType(accountID, typeOfService);
				List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService
						.findAllByParentId(mastersEntity1.getServiceMasterId());
				for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1) {

					if (serviceParametersEntity.getServiceParameterMaster()
							.getSpmName().equalsIgnoreCase("RO charges")) {
						roApplicable = serviceParametersEntity
								.getServiceParameterValue();
					}
				}
				logger.info("roApplicable --------------- " + roApplicable);
				if (roApplicable != null
						&& roApplicable.equalsIgnoreCase("Yes")) {

					LocalDate fromDate = new LocalDate(previousBillDate);
					LocalDate toDate = new LocalDate(currentBillDate);
					PeriodType monthDay = PeriodType.yearMonthDay()
							.withYearsRemoved();
					Period difference = new Period(fromDate, toDate, monthDay);
					float billableMonths = difference.getMonths();
					int daysAfterMonth = difference.getDays();

					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(currentBillDate);
					float daysToMonths = (float) daysAfterMonth
							/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
					float netMonth = daysToMonths + billableMonths;

					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCountWater(waterRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth,
											previousBillEntity.getAvgCount());

						} else {
							consolidatedBill = calculationController
									.waterTariffAmount(waterRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth);
						}
					} else {
						consolidatedBill = calculationController
								.waterTariffAmount(waterRateMaster,
										previousBillDate, currentBillDate,
										1 * netMonth);
					}

					for (Entry<String, Object> map : consolidatedBill
							.entrySet()) {
						if (map.getKey().equals("total")) {
							roAmount = (Float) map.getValue();
						}
					}
					roCharges.setTransactionCode("WT_RO");
					roCharges.setBalanceAmount(roAmount);
					roCharges.setCreditAmount(0);
					roCharges.setDebitAmount(0);
					roCharges.setStatus("Not Approved");
					roCharges.setCreatedBy((String) SessionData
							.getUserDetails().get("userID"));
					roCharges.setLastUpdatedBy((String) SessionData
							.getUserDetails().get("userID"));
					roCharges.setLastUpdatedDT(new java.sql.Timestamp(
							new Date().getTime()));
					billLineItemEntities.add(roCharges);
				}
			}

			if (waterRateMaster.getWtRateName().equalsIgnoreCase(
					"Sewage charges")) {

				ServiceMastersEntity mastersEntity1 = serviceMasterService
						.getServiceMasterServicType(accountID, typeOfService);
				List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService
						.findAllByParentId(mastersEntity1.getServiceMasterId());
				String sewageApplicable = null;
				for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1) {

					if (serviceParametersEntity.getServiceParameterMaster()
							.getSpmName().equalsIgnoreCase("Sewage charges")) {
						sewageApplicable = serviceParametersEntity
								.getServiceParameterValue();
					}
				}
				logger.info("sewageApplicable --------------- "
						+ sewageApplicable);
				if (sewageApplicable != null
						&& sewageApplicable.equalsIgnoreCase("Yes")) {

					LocalDate fromDate = new LocalDate(previousBillDate);
					LocalDate toDate = new LocalDate(currentBillDate);
					PeriodType monthDay = PeriodType.yearMonthDay()
							.withYearsRemoved();
					Period difference = new Period(fromDate, toDate, monthDay);
					float billableMonths = difference.getMonths();
					int daysAfterMonth = difference.getDays();

					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(currentBillDate);
					float daysToMonths = (float) daysAfterMonth
							/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
					float netMonth = daysToMonths + billableMonths;

					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCountWater(waterRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth,
											previousBillEntity.getAvgCount());

						} else {
							consolidatedBill = calculationController
									.waterTariffAmount(waterRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth);
						}
					} else {
						consolidatedBill = calculationController
								.waterTariffAmount(waterRateMaster,
										previousBillDate, currentBillDate,
										1 * netMonth);
					}

					for (Entry<String, Object> map : consolidatedBill
							.entrySet()) {
						if (map.getKey().equals("total")) {
							scAmount = (Float) map.getValue();
						}
					}
					sweageCharges.setTransactionCode("WT_SC");
					sweageCharges.setBalanceAmount(scAmount);
					sweageCharges.setCreditAmount(0);
					sweageCharges.setDebitAmount(0);
					sweageCharges.setStatus("Not Approved");
					sweageCharges.setCreatedBy((String) SessionData
							.getUserDetails().get("userID"));
					sweageCharges.setLastUpdatedBy((String) SessionData
							.getUserDetails().get("userID"));
					sweageCharges.setLastUpdatedDT(new java.sql.Timestamp(
							new Date().getTime()));
					billLineItemEntities.add(sweageCharges);
				}
			}

			if (waterRateMaster.getWtRateName().equalsIgnoreCase(
					"Fixed charges")) {
				LocalDate fromDate = new LocalDate(previousBillDate);
				LocalDate toDate = new LocalDate(currentBillDate);
				PeriodType monthDay = PeriodType.yearMonthDay()
						.withYearsRemoved();
				Period difference = new Period(fromDate, toDate, monthDay);
				float billableMonths = difference.getMonths();
				int daysAfterMonth = difference.getDays();

				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(currentBillDate);
				float daysToMonths = (float) daysAfterMonth
						/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
				float netMonth = daysToMonths + billableMonths;

				if (previousBillEntity != null) {
					if (previousBillEntity.getAvgCount() >= 1) {
						consolidatedBill = calculationController
								.tariffAmountAvgCountWater(waterRateMaster,
										previousBillDate, currentBillDate,
										0.0f, previousBillEntity.getAvgCount());

					} else {
						consolidatedBill = calculationController
								.waterTariffAmount(waterRateMaster,
										previousBillDate, currentBillDate,
										1 * netMonth);
					}
				} else {
					consolidatedBill = calculationController.waterTariffAmount(
							waterRateMaster, previousBillDate, currentBillDate,
							1 * netMonth);
				}

				for (Entry<String, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						fcAmount = (Float) map.getValue();
					}
				}
				waterFixcedCharages.setTransactionCode("WT_FC");
				waterFixcedCharages.setBalanceAmount(fcAmount);
				waterFixcedCharages.setCreditAmount(0);
				waterFixcedCharages.setDebitAmount(0);
				waterFixcedCharages.setStatus("Not Approved");
				waterFixcedCharages.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				waterFixcedCharages.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				waterFixcedCharages.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(waterFixcedCharages);
			}

		}

		float interestOnArrearsAmount = 0;
		float interestOnTaxAmount = 0;
		ElectricityBillLineItemEntity interestOnTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity interestOnBillAmount = new ElectricityBillLineItemEntity();
		consolidatedBill = interestCalculationWater(accountID, typeOfService,
				previousBillDate, waterRateMasterList, currentBillDate);
		for (Entry<String, Object> map : consolidatedBill.entrySet()) {
			if (map.getKey().equals("interestOnArrearsAmount")) {
				interestOnArrearsAmount = interestOnArrearsAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnArrearsAmount);
				interestOnBillAmount.setTransactionCode("WT_INTEREST");
				interestOnBillAmount.setBalanceAmount(interestOnArrearsAmount);
				interestOnBillAmount.setCreditAmount(0);
				interestOnBillAmount.setDebitAmount(0);
				interestOnBillAmount.setStatus("Not Approved");
				interestOnBillAmount.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnBillAmount);
			}
			if (map.getKey().equals("interestOnTaxAmount")) {
				interestOnTaxAmount = interestOnTaxAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnTaxAmount);
				interestOnTax.setTransactionCode("WT_TAX_INTEREST");
				interestOnTax.setBalanceAmount(interestOnTaxAmount);
				interestOnTax.setCreditAmount(0);
				interestOnTax.setDebitAmount(0);
				interestOnTax.setStatus("Not Approved");
				interestOnTax.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnTax);
			}
		}

		logger.info("wcAmount -------------- " + wcAmount);
		logger.info("scAmount -------------- " + scAmount);
		logger.info("roAmount -------------- " + roAmount);
		logger.info("solAmount -------------- " + solAmount);
		logger.info("fcAmount -------------- " + fcAmount);
		logger.info("rateOfInterest -------------- " + rateOfInterest);
		logger.info("arrearsAmount -------------- " + arrearsAmount);
		logger.info("(float) interestOnTax.getBalanceAmount() -------------- "
				+ (float) interestOnTax.getBalanceAmount());
		logger.info("(float) interestOnBillAmount.getBalanceAmount() -------------- "
				+ (float) interestOnBillAmount.getBalanceAmount());

		float totalExcludeTax = (float) (wcAmount + solAmount + roAmount
				+ scAmount + fcAmount + taxAmount + rateOfInterest
				+ arrearsAmount + (float) interestOnTax.getBalanceAmount()
				+ (float) interestOnBillAmount.getBalanceAmount() + roundoffValue);
		logger.info(":::::::::::::: Total Amount Excludeing tax ::::::::::::: "
				+ totalExcludeTax);

		for (WTRateMaster waterRateMaster : waterRateMasterList) {
			if (waterRateMaster.getWtRateName().equalsIgnoreCase("Tax")) {
				logger.info(":::::::::::: In "
						+ waterRateMaster.getWtRateName() + "::::::::::");
				waterTax.setTransactionCode("WT_TAX");
				consolidatedBill = calculationController.waterTariffAmount(
						waterRateMaster, previousBillDate, currentBillDate,
						totalExcludeTax);

				for (Entry<String, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						taxAmount = (Float) map.getValue();
					}
				}
				logger.info("taxAmount ::::::::: " + taxAmount);
				waterTax.setBalanceAmount(taxAmount);
				waterTax.setCreditAmount(0);
				waterTax.setDebitAmount(0);
				waterTax.setStatus("Not Approved");
				waterTax.setCreatedBy((String) SessionData.getUserDetails()
						.get("userID"));
				waterTax.setLastUpdatedBy((String) SessionData.getUserDetails()
						.get("userID"));
				waterTax.setLastUpdatedDT(new java.sql.Timestamp(new Date()
						.getTime()));
				billLineItemEntities.add(waterTax);
			}

		}
		logger.info("wcAmount " + wcAmount);
		logger.info("scAmount " + scAmount);
		logger.info("solAmount " + solAmount);
		logger.info("fcAmount " + fcAmount);
		logger.info("roAmount " + roAmount);
		logger.info("taxAmount " + taxAmount);
		logger.info("rateOfInterest " + rateOfInterest);
		logger.info("arrearsAmount " + arrearsAmount);
		logger.info("interestOnTaxAmount " + interestOnTax.getBalanceAmount());
		logger.info("interestOnArrears "
				+ interestOnBillAmount.getBalanceAmount());
		logger.info("Total without round off "
				+ (wcAmount + roAmount + solAmount + scAmount + fcAmount
						+ taxAmount + rateOfInterest + arrearsAmount
						+ interestOnTax.getBalanceAmount() + interestOnBillAmount
							.getBalanceAmount()));

		totalAmount = roundOff(wcAmount + solAmount + scAmount + roAmount
				+ taxAmount + fcAmount + rateOfInterest + arrearsAmount
				+ (float) interestOnTax.getBalanceAmount()
				+ (float) interestOnBillAmount.getBalanceAmount(), 0);
		logger.info("totalAmount :::::::::::::: " + totalAmount);
		roundoffValue = totalAmount
				- (wcAmount + scAmount + roAmount + fcAmount + taxAmount
						+ rateOfInterest + arrearsAmount
						+ (float) interestOnTax.getBalanceAmount() + (float) interestOnBillAmount
							.getBalanceAmount());
		logger.info("::::::::::::::::::::::::: Round off value is ::::::::::::::: "
				+ roundoffValue);

		roundOffBillLineItemEntity.setCreditAmount(0);
		roundOffBillLineItemEntity.setDebitAmount(0);
		roundOffBillLineItemEntity.setStatus("Not Approved");
		roundOffBillLineItemEntity.setCreatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
				new Date().getTime()));
		roundOffBillLineItemEntity.setBalanceAmount(roundoffValue);
		roundOffBillLineItemEntity.setTransactionCode("WT_ROF");
		billLineItemEntities.add(roundOffBillLineItemEntity);

		ElectricityBillEntity billEntity = electricityBillsService
				.getPreviousBill(accountID, typeOfService, previousBillDate,
						"Bill");
		List<ElectricityBillEntity> billEntities = new ArrayList<>();
		if (billEntity != null) {
			if (billEntity.getAvgCount() >= 1) {
				List<BigDecimal> billIds = electricityBillsService
						.getLastAvgBills(accountID, typeOfService,
								previousBillDate, "Bill",
								billEntity.getAvgCount());
				for (BigDecimal bigDecimal : billIds) {
					billEntities.add(electricityBillsService.find(bigDecimal
							.intValue()));
				}
			}

			for (ElectricityBillEntity electricityBillEntity : billEntities) {
				avgAmount = avgAmount + electricityBillEntity.getBillAmount();
			}
		}

		/*
		 * ElectricityBillEntity billEntity = saveBill(accountID, totalAmount,
		 * previousBillDate, currentBillDate, typeOfService, arrearsAmount,
		 * avgUnits, avgCount, billType, avgAmount, postType);
		 * saveBillLineItems(billEntity, billLineItemEntities, roundoffValue,
		 * arrearsAmount); saveBillWaterParameters(billEntity,
		 * waterRateMasterList, accountID, typeOfService, previousBillDate,
		 * currentBillDate, uomValue, billableDays, presentMeterStaus,
		 * previousMeterStaus, meterConstant, presentUOMReading,
		 * previousUOMReading,wtSlabString);
		 */
		/*--*/

		ElectricityBillEntity billEntity1 = saveBill(accountID, totalAmount,
				previousBillDate, currentBillDate, typeOfService,
				arrearsAmount, 0.0, 0, "Bulk bill", 0.0, postType,2);
		saveBillLineItems(billEntity1, billLineItemEntities, roundoffValue,
				arrearsAmount);
		saveBulkBillWaterParameters(billEntity1, waterRateMasterList,
				accountID, typeOfService, previousBillDate, currentBillDate,
				uomValue, billableDays, wtSlabString);

	}

	private void saveBulkBillWaterParameters(ElectricityBillEntity billEntity,
			List<WTRateMaster> waterRateMasterList, int accountID,
			String typeOfService, Date previousBillDate, Date currentBillDate,
			Float uomValue, Integer billableDays, String wtSlabString) {
		List<BillParameterMasterEntity> billParameterMasterEntitiesList = billingParameterMasterService
				.getBillParameterMasterByServiceType(typeOfService);
		logger.info("billParameterMasterEntitiesList ::::::::::: "
				+ billParameterMasterEntitiesList.size());

		for (BillParameterMasterEntity billParameterMasterEntity : billParameterMasterEntitiesList) {
			logger.info("Bill parameters servic type :::"
					+ billParameterMasterEntity.getServiceType());

			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"Number of days")) {
				ElectricityBillParametersEntity noOfDays = new ElectricityBillParametersEntity(
						"Integer", billableDays.toString(), "Active");
				noOfDays.setBillParameterMasterEntity(billParameterMasterEntity);
				noOfDays.setElectricityBillEntity(billEntity);
				billParameterService.save(noOfDays);
			}

			/*
			 * if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
			 * "Previous status")) { ElectricityBillParametersEntity
			 * previousStatus = new ElectricityBillParametersEntity("String",
			 * previousMeterStaus, "Active");
			 * previousStatus.setBillParameterMasterEntity
			 * (billParameterMasterEntity);
			 * previousStatus.setElectricityBillEntity(billEntity);
			 * billParameterService.save(previousStatus); } if
			 * (billParameterMasterEntity
			 * .getBvmName().equalsIgnoreCase("Present Status")) {
			 * ElectricityBillParametersEntity presentStatus = new
			 * ElectricityBillParametersEntity("String", presentMeterStaus,
			 * "Active");
			 * presentStatus.setBillParameterMasterEntity(billParameterMasterEntity
			 * ); presentStatus.setElectricityBillEntity(billEntity);
			 * billParameterService.save(presentStatus); } if
			 * (billParameterMasterEntity
			 * .getBvmName().equalsIgnoreCase("Previous reading")) {
			 * ElectricityBillParametersEntity reviousReading = new
			 * ElectricityBillParametersEntity("Integer",
			 * previousUOMReading.toString(), "Active");
			 * reviousReading.setBillParameterMasterEntity
			 * (billParameterMasterEntity);
			 * reviousReading.setElectricityBillEntity(billEntity);
			 * billParameterService.save(reviousReading); }
			 * 
			 * if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
			 * "Present reading")) { ElectricityBillParametersEntity
			 * presentReading = new ElectricityBillParametersEntity("Integer",
			 * presentUOMReading.toString().toString(),"Active");
			 * presentReading.
			 * setBillParameterMasterEntity(billParameterMasterEntity);
			 * presentReading.setElectricityBillEntity(billEntity);
			 * billParameterService.save(presentReading); }
			 * 
			 * if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
			 * "Meter Constant")) { ElectricityBillParametersEntity
			 * meterConstantObject = new ElectricityBillParametersEntity("Date",
			 * meterConstant.toString(), "Active");
			 * meterConstantObject.setBillParameterMasterEntity
			 * (billParameterMasterEntity);
			 * meterConstantObject.setElectricityBillEntity(billEntity);
			 * billParameterService.save(meterConstantObject); }
			 */
			if (billParameterMasterEntity.getBvmName()
					.equalsIgnoreCase("Units")) {
				ElectricityBillParametersEntity units = new ElectricityBillParametersEntity(
						"Integer", uomValue.toString(), "Active");
				units.setBillParameterMasterEntity(billParameterMasterEntity);
				units.setElectricityBillEntity(billEntity);
				billParameterService.save(units);
			}
			if (billParameterMasterEntity.getBvmName().equalsIgnoreCase(
					"WC Slabs")) {
				logger.info("wtSlabString::::::::" + wtSlabString);
				wtSlabString = wtSlabString.replace("null", " ");
				wtSlabString = wtSlabString.replace("</br>", " ");
				ElectricityBillParametersEntity slabs = new ElectricityBillParametersEntity(
						"String", "NA", "Active");
				slabs.setNotes(wtSlabString);
				slabs.setBillParameterMasterEntity(billParameterMasterEntity);
				slabs.setElectricityBillEntity(billEntity);
				billParameterService.save(slabs);
			}

		}
	}

	private int serviceStatus(Date date, String servicetype, int accountId) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int month1 = cal.get(Calendar.MONTH);
		int month = month1 + 1;
		int year = cal.get(Calendar.YEAR);

		List<Object> list1 = tariffCalculationService.getBillOnDate(month,
				year, servicetype, accountId);
		logger.info(":::::::::::::list1::::::::::::" + list1.size());
		int size = list1.size();
		return size;
	}

	@RequestMapping(value = "/bill/billCorrection", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> calculateBillCorrection(
			HttpServletRequest req, HttpServletResponse response,
			@RequestBody String body) throws ParseException, JSONException {
		logger.info("------------------------- In Bill Correction method ------------------------");
		int billId = 0;
		if (req.getParameter("billId") != null) {
			billId = Integer.parseInt(req.getParameter("billId"));
		}

		Object dgApplicable = "NO";
		Object todApplicable = "NO";

		ElectricityBillEntity billEntity = electricityBillsService.find(billId);
		logger.info(" billEntity " + billEntity.getBillMonth());
		Map<String, Object> map = new LinkedHashMap<>();
		if (billEntity != null) {
			/*
			 * if(billEntity.getTypeOfService().equalsIgnoreCase("Electricity")
			 * || billEntity.getTypeOfService().equalsIgnoreCase("Water")
			 * ||billEntity.getTypeOfService().equalsIgnoreCase("Gas")) {
			 */
			map.put("bcAccountNo", billEntity.getAccountObj().getAccountNo());
			map.put("bcServiceName", billEntity.getTypeOfService());
			map.put("bcPreviousDate", new SimpleDateFormat("dd/MM/yyyy")
					.format(billEntity.getFromDate()));
			map.put("bcSWPresentdate", new SimpleDateFormat("dd/MM/yyyy")
					.format(billEntity.getFromDate()));
			map.put("bcPresentdate", new SimpleDateFormat("dd/MM/yyyy")
					.format(billEntity.getBillDate()));

			String paraMeterName = "Previous status";
			String previousStatus = null;
			if (billParameterService.getParameterValue(
					billEntity.getElBillId(), billEntity.getTypeOfService(),
					paraMeterName) != null) {
				previousStatus = billParameterService.getParameterValue(
						billEntity.getElBillId(),
						billEntity.getTypeOfService(), paraMeterName);
				logger.info("previousStatus -------------- " + previousStatus);
			}

			String paraMeterName1 = "Present status";
			String presentStatus = null;
			if (billParameterService.getParameterValue(
					billEntity.getElBillId(), billEntity.getTypeOfService(),
					paraMeterName1) != null) {
				presentStatus = billParameterService.getParameterValue(
						billEntity.getElBillId(),
						billEntity.getTypeOfService(), paraMeterName1);
				logger.info("presentStatus -------------- " + presentStatus);
			}

			String paraMeterName2 = "Present reading";
			String presentReading = null;
			if (billParameterService.getParameterValue(
					billEntity.getElBillId(), billEntity.getTypeOfService(),
					paraMeterName2) != null) {
				presentReading = billParameterService.getParameterValue(
						billEntity.getElBillId(),
						billEntity.getTypeOfService(), paraMeterName2);
				logger.info("presentReading -------------- " + presentReading);
			}

			String paraMeterName3 = "Previous reading";
			String previousReading = null;
			if (billParameterService.getParameterValue(
					billEntity.getElBillId(), billEntity.getTypeOfService(),
					paraMeterName3) != null) {
				previousReading = billParameterService.getParameterValue(
						billEntity.getElBillId(),
						billEntity.getTypeOfService(), paraMeterName3);
				logger.info("previousReading -------------- " + previousReading);
			}

			String paraMeterName4 = "Meter Constant";
			String bcMeterFactor = null;
			if (billParameterService.getParameterValue(
					billEntity.getElBillId(), billEntity.getTypeOfService(),
					paraMeterName4) != null) {
				bcMeterFactor = billParameterService.getParameterValue(
						billEntity.getElBillId(),
						billEntity.getTypeOfService(), paraMeterName4);
				logger.info("bcMeterFactor -------------- " + bcMeterFactor);
			}

			String paraMeterName5 = "Units";
			String bcUnits = null;
			if (billParameterService.getParameterValue(
					billEntity.getElBillId(), billEntity.getTypeOfService(),
					paraMeterName5) != null) {
				bcUnits = billParameterService.getParameterValue(
						billEntity.getElBillId(),
						billEntity.getTypeOfService(), paraMeterName5);
				logger.info("bcUnits -------------- " + bcUnits);
			}

			String paraMeterName6 = "Volume(KG)";
			String volume = null;
			if (billParameterService.getParameterValue(
					billEntity.getElBillId(), billEntity.getTypeOfService(),
					paraMeterName6) != null) {
				volume = billParameterService.getParameterValue(
						billEntity.getElBillId(),
						billEntity.getTypeOfService(), paraMeterName6);
				logger.info("volume -------------- " + volume);
			}

			String paraMeterName7 = "PF";
			String pfReading = null;
			if (billParameterService.getParameterValue(
					billEntity.getElBillId(), billEntity.getTypeOfService(),
					paraMeterName7) != null) {
				pfReading = billParameterService.getParameterValue(
						billEntity.getElBillId(),
						billEntity.getTypeOfService(), paraMeterName7);
				logger.info("pfReading -------------- " + pfReading);
			}

			String paraMeterName8 = "Maximum Demand Recorded";
			String recordedDemand = null;
			if (billParameterService.getParameterValue(
					billEntity.getElBillId(), billEntity.getTypeOfService(),
					paraMeterName8) != null) {
				recordedDemand = billParameterService.getParameterValue(
						billEntity.getElBillId(),
						billEntity.getTypeOfService(), paraMeterName8);
				logger.info("pfReading -------------- " + recordedDemand);
			}
			List<Object> dglist = tariffCalculationService.todApplicable(
					billEntity.getTypeOfService(), billEntity.getAccountId());
			dgApplicable = dglist.get(1);
			todApplicable = dglist.get(0);
			if (dgApplicable != null) {
				if (dgApplicable.equals("Yes")) {

					String paraMeterName9 = "Previous DG reading";
					String previousDgReading = null;
					if (billParameterService.getParameterValue(
							billEntity.getElBillId(),
							billEntity.getTypeOfService(), paraMeterName9) != null) {
						previousDgReading = billParameterService
								.getParameterValue(billEntity.getElBillId(),
										billEntity.getTypeOfService(),
										paraMeterName9);
						logger.info("previousDgReading -------------- "
								+ previousDgReading);
					}

					String paraMeterName10 = "Present  DG reading";
					String presentDGReading = null;
					if (billParameterService.getParameterValue(
							billEntity.getElBillId(),
							billEntity.getTypeOfService(), paraMeterName10) != null) {
						presentDGReading = billParameterService
								.getParameterValue(billEntity.getElBillId(),
										billEntity.getTypeOfService(),
										paraMeterName10);
						logger.info("PresentDGReading -------------- "
								+ presentDGReading);
					}

					String paraMeterName11 = "DG Meter Constant";
					String dGMeterConstant = null;
					if (billParameterService.getParameterValue(
							billEntity.getElBillId(),
							billEntity.getTypeOfService(), paraMeterName11) != null) {
						dGMeterConstant = billParameterService
								.getParameterValue(billEntity.getElBillId(),
										billEntity.getTypeOfService(),
										paraMeterName11);
						logger.info("DGMeterConstant -------------- "
								+ dGMeterConstant);
					}
					String paraMeterName12 = "DG Units";
					String dGUnits = null;
					if (billParameterService.getParameterValue(
							billEntity.getElBillId(),
							billEntity.getTypeOfService(), paraMeterName12) != null) {
						dGUnits = billParameterService.getParameterValue(
								billEntity.getElBillId(),
								billEntity.getTypeOfService(), paraMeterName12);
						logger.info("dGUnits -------------- " + dGUnits);
					}

					map.put("dgApplicable", dgApplicable);
					map.put("previousDgReading", previousDgReading);
					map.put("presentDGReading", presentDGReading);
					map.put("dGMeterConstant", dGMeterConstant);
					map.put("dGUnits", dGUnits);

				} else {
					map.put("dgApplicable", "No");
				}
			}

			map.put("todApplicable", todApplicable);

			map.put("bcPresentReading", presentReading);
			map.put("bcPreviousReading", previousReading);
			map.put("bcPreviousStaus", previousStatus);
			map.put("bcPresentStaus", presentStatus);
			map.put("bcMeterFactor", bcMeterFactor);
			map.put("bcMeterType",
					tariffCalculationService.getMeterType(
							billEntity.getAccountId(),
							billEntity.getTypeOfService()));
			map.put("bcPfReading", pfReading);
			map.put("bcRecordedDemand", recordedDemand);
			if (volume == null) {
				map.put("bcUnits", bcUnits);
			} else {
				map.put("bcPresentReading", volume);
			}

			/* } */

		}
		return (HashMap<String, Object>) map;
	}

	@RequestMapping(value = "/bill/reCalculateBill", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> reCalculateBill(HttpServletRequest req,
			HttpServletResponse response, @RequestBody String body)
			throws ParseException, JSONException {
		logger.info("------------------------- In Bill re-calculation method ------------------------");

		Map<String, Object> map = new LinkedHashMap<>();
		Object dgApplicable = "No";

		Float presentUOMReading = 0f;
		String presentMeterStaus = null;
		String previousMeterStaus = null;
		Float previousUOMReading = 0f;
		Float meterConstant = 0f;
		Date previousBillDate = null;
		Float uomValue;
		String typeOfService = req.getParameter("serviceName");
		String accountNo = req.getParameter("accountNo");
		int accountID = accountService.getAccountIdByNo(accountNo);
		logger.info("accountID ----------------" + accountID);
		int elBillId = Integer.parseInt(req.getParameter("elBillId"));
		Date currentBillDate = new SimpleDateFormat("dd/MM/yyyy").parse(req
				.getParameter("presentbilldate"));
		String billType = "Normal";
		String meterchangeUnit = "Not generated";
		Float previousDgreading = 0f;
		Float presentDgReading = 0f;
		Float dgmeterconstant = 0f;
		Double avgUnits = 0d;
		Integer avgCount = 0;
		Float dgunit = 0f;

		List<Object> dglist = tariffCalculationService.todApplicable(
				typeOfService, accountID);
		dgApplicable = dglist.get(1);

		if (dgApplicable != null) {
			if (dgApplicable.equals("Yes")) {
				if (req.getParameter("previousDgreading").equals("")
						|| req.getParameter("previousDgreading").equals(null)
						|| req.getParameter("previousDgreading").isEmpty()) {
					logger.info("::::::::::::::::::: previousDgreading  reading is null ::::::::::::::::::");
					previousDgreading = 0f;
				} else {
					previousDgreading = Float.parseFloat(req
							.getParameter("previousDgreading"));
					logger.info("::::::::::::::::::: previousDgreading reading is Not null ::::::::::::::::::"
							+ previousDgreading);
				}

				if (req.getParameter("presentDgReading").equals("")
						|| req.getParameter("presentDgReading").equals(null)
						|| req.getParameter("presentDgReading").isEmpty()) {
					logger.info("::::::::::::::::::: presentDgReading  reading is null ::::::::::::::::::");
					presentDgReading = 0f;
				} else {
					presentDgReading = Float.parseFloat(req
							.getParameter("presentDgReading"));
					logger.info("::::::::::::::::::: presentDgReading reading is Not null :::::::::::::::::: "
							+ presentDgReading);
				}
				if (req.getParameter("dgmeterconstant").equals("")
						|| req.getParameter("dgmeterconstant").equals(null)
						|| req.getParameter("dgmeterconstant").isEmpty()) {
					logger.info("::::::::::::::::::: dgmeterconstant is null ::::::::::::::::::");
					dgmeterconstant = 0f;
				} else {
					dgmeterconstant = Float.parseFloat(req
							.getParameter("dgmeterconstant"));
					logger.info("::::::::::::::::::: dgmeterconstant is Not null ::::::::::::::::::"
							+ dgmeterconstant);
				}
				if (req.getParameter("dgUnit").equals("")
						|| req.getParameter("dgUnit").equals(null)
						|| req.getParameter("dgUnit").isEmpty()) {
					logger.info("::::::::::::::::::: dgmeterconstant is null ::::::::::::::::::");
					dgmeterconstant = 0f;
				} else {
					dgunit = Float.parseFloat(req.getParameter("dgUnit"));
					logger.info("dgunit:::::::::" + dgunit);
				}
				/*
				 * if (dgApplicable.equals("Yes")) {
				 * 
				 * dgunit = Float.parseFloat(req.getParameter("dgUnit"));
				 * logger.info("dgunit:::::::::" + dgunit);
				 * 
				 * }
				 */
			}
		} else {
			dgApplicable = "NO";
		}

		if (req.getParameter("presentReading").equals("")
				|| req.getParameter("presentReading").equals(null)
				|| req.getParameter("presentReading").isEmpty()) {
			presentUOMReading = 0f;
		} else {
			presentUOMReading = Float.parseFloat(req
					.getParameter("presentReading"));
		}

		if (req.getParameter("presentReading").equals("")
				|| req.getParameter("presentReading").equals(null)
				|| req.getParameter("presentReading").isEmpty()) {
			presentUOMReading = 0f;
		} else {
			uomValue = Float.parseFloat(req.getParameter("presentReading"));
		}

		if (req.getParameter("presentStaus").equals("")
				|| req.getParameter("presentStaus").equals(null)
				|| req.getParameter("presentStaus").isEmpty()) {
		} else {
			presentMeterStaus = req.getParameter("presentStaus");
		}

		if (req.getParameter("previousStaus").equals("")
				|| req.getParameter("previousStaus").equals(null)
				|| req.getParameter("previousStaus").isEmpty()) {
		} else {
			previousMeterStaus = req.getParameter("previousStaus");
		}

		if (req.getParameter("previousReading").equals("")
				|| req.getParameter("previousReading").equals(null)
				|| req.getParameter("previousReading").isEmpty()) {
		} else {
			previousUOMReading = Float.parseFloat(req
					.getParameter("previousReading"));
		}

		if (req.getParameter("meterFactor").equals("")
				|| req.getParameter("meterFactor").equals(null)
				|| req.getParameter("meterFactor").isEmpty()) {
		} else {
			meterConstant = Float.parseFloat(req.getParameter("meterFactor"));
		}

		if (req.getParameter("previousbillDate").equals("")
				|| req.getParameter("previousbillDate").equals(null)
				|| req.getParameter("previousbillDate").isEmpty()) {
		} else {
			previousBillDate = new SimpleDateFormat("dd/MM/yyyy").parse(req
					.getParameter("previousbillDate"));
		}

		if (req.getParameter("units").equals("")
				|| req.getParameter("units").equals(null)
				|| req.getParameter("units").isEmpty()) {
			uomValue = 0f;
		} else {
			uomValue = Float.parseFloat(req.getParameter("units"));
		}
		float pfReading = 0;
		float connectedLoad = 0;
		if (req.getParameter("pfReading").equals("")
				|| req.getParameter("pfReading").equals(null)
				|| req.getParameter("pfReading").isEmpty()) {
			logger.info("::::::::::::::::::: power factor reading is null ::::::::::::::::::");
		} else {
			logger.info("::::::::::::::::::: Power factor reading is Not null ::::::::::::::::::");
			pfReading = Float.parseFloat(req.getParameter("pfReading"));
		}

		if (req.getParameter("recoredDemand").equals("")
				|| req.getParameter("recoredDemand").equals(null)
				|| req.getParameter("recoredDemand").isEmpty()) {
			logger.info("::::::::::::::::::: Connected Load reading is null ::::::::::::::::::");
		} else {
			logger.info("::::::::::::::::::: Connected Load reading is Not null ::::::::::::::::::");
			connectedLoad = Float.parseFloat(req.getParameter("recoredDemand"));
		}

		logger.info("elBillId ----------------- " + elBillId);
		logger.info("typeOfService ------------ " + typeOfService);
		logger.info("currentBillDate ---------- " + currentBillDate);
		logger.info("previousBillDate --------- " + previousBillDate);
		logger.info("previousUOMReading ------- " + previousUOMReading);
		logger.info("presentUOMReading -------- " + presentUOMReading);
		logger.info("uomValue ----------------- " + uomValue);
		logger.info("meterConstant ------------ " + meterConstant);

		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		Integer billableDays = Days.daysBetween(fromDate, toDate).getDays();
		logger.info("--------- Number of billable days are :" + billableDays);

		electricityBillLineItemService.deleteAllBill(elBillId);
		ElectricityBillEntity billEntity = electricityBillsService
				.find(elBillId);
		logger.info("---------------- Updating the record with of id is ------------ "
				+ billEntity.getElBillId());

		List<ELRateMaster> elRateMasterList = new ArrayList<>();
		List<WTRateMaster> waterRateMasterList = new ArrayList<>();
		List<GasRateMaster> gasRateMasterList = new ArrayList<>();
		List<SolidWasteRateMaster> solidWasterRateMasterList = new ArrayList<>();
		if (typeOfService.equalsIgnoreCase("Electricity")) {
			logger.info("::::::::::::: Electricity service type ::::::::::::: ");
			int tariffId = serviceMasterService
					.getServiceMasterByAccountNumber(accountID, typeOfService);
			logger.info(":::::::::::::" + typeOfService
					+ " Tairff id is ::::::::::::: " + tariffId);
			elRateMasterList = tariffCalculationService
					.getElectricityRateMaster(tariffId);
			List<Object> list = tariffCalculationService.todApplicable(
					typeOfService, accountID);
			Integer todT1 = 0;
			Integer todT2 = 0;
			Integer todT3 = 0;

			Object todApplicable = list.get(0);
			// dgApplicable = list.get(1);
			logger.info("todApplicable:::::::::" + todApplicable);
			logger.info("dgApplicable:::::::::" + dgApplicable);
			if (todApplicable.equals("Yes")) {
				todT1 = Integer.parseInt(req.getParameter("presentTod1"));
				todT2 = Integer.parseInt(req.getParameter("presentTod2"));
				todT3 = Integer.parseInt(req.getParameter("presentTod3"));
				logger.info(" todT1::" + todT1 + " todT2::" + todT2
						+ " todT3::" + todT3);
			}

			else {
				logger.info(":::::::::Tod Is not Applicable::::::::::");
			}

			saveElectricityBillParametersUpdate(billEntity, elRateMasterList,
					accountID, typeOfService, previousBillDate,
					currentBillDate, uomValue, billableDays, presentMeterStaus,
					previousMeterStaus, meterConstant, presentUOMReading,
					previousUOMReading, todT1, todT2, todT3, dgunit,
					todApplicable, dgApplicable, previousDgreading,
					presentDgReading, dgmeterconstant, avgUnits, avgCount,
					billType, pfReading, connectedLoad, meterchangeUnit);
		}
		if (typeOfService.equalsIgnoreCase("Water")) {
			logger.info("::::::::::::: Water service type ::::::::::::: ");
			int waterTariffId = serviceMasterService.getWaterTariffId(
					accountID, typeOfService);
			logger.info(":::::::::::::" + typeOfService
					+ " Tairff id is ::::::::::::: " + waterTariffId);
			waterRateMasterList = tariffCalculationService
					.getWaterRateMaster(waterTariffId);
			saveWaterBillParametersUpdate(billEntity, waterRateMasterList,
					accountID, typeOfService, previousBillDate,
					currentBillDate, uomValue, billableDays, presentMeterStaus,
					previousMeterStaus, meterConstant, presentUOMReading,
					previousUOMReading, avgUnits, avgCount, billType,
					meterchangeUnit);
		}
		if (typeOfService.equalsIgnoreCase("Gas")) {
			logger.info("::::::::::::: Gas service type ::::::::::::: ");
			int gasTariffId = serviceMasterService.getGasTariffId(accountID,
					typeOfService);
			logger.info(":::::::::::::" + typeOfService
					+ " Tairff id is ::::::::::::: " + gasTariffId);
			gasRateMasterList = tariffCalculationService
					.getGasRateMaster(gasTariffId);
			saveGasBillParametersUpdate(billEntity, gasRateMasterList,
					accountID, typeOfService, previousBillDate,
					currentBillDate, uomValue, billableDays, presentMeterStaus,
					previousMeterStaus, meterConstant, presentUOMReading,
					previousUOMReading, avgUnits, avgCount, billType,
					meterchangeUnit);
		}
		if (typeOfService.equalsIgnoreCase("Solid Waste")) {
			logger.info("::::::::::::: Solid Waste service type ::::::::::::: "
					+ accountID + "----- " + typeOfService);
			int solidWasteTariffId = serviceMasterService
					.getSolidWasteTariffId(accountID, typeOfService);
			logger.info(":::::::::::::" + typeOfService
					+ " Tairff id is ::::::::::::: " + solidWasteTariffId);
			solidWasterRateMasterList = tariffCalculationService
					.getSolidWasteRateMaster(solidWasteTariffId);
			saveSolidWasteBillParametersUpdate(billEntity,
					solidWasterRateMasterList, accountID, typeOfService,
					previousBillDate, currentBillDate, presentUOMReading,
					billableDays, presentMeterStaus, previousMeterStaus,
					meterConstant, presentUOMReading, previousUOMReading,
					avgUnits, avgCount, billType);
		}

		if (typeOfService.equalsIgnoreCase("Others")) {
			String postType = "Bill";
			logger.info("::::::::::::: Othres service type Update ::::::::::::: ");
			saveOthersBillParametersUpdate(billEntity, accountID,
					typeOfService, postType, previousBillDate, currentBillDate,
					avgUnits, avgCount);
		}

		return (HashMap<String, Object>) map;
	}

	private void saveOthersBillParametersUpdate(
			ElectricityBillEntity billEntity, int accountID,
			String typeOfService, String postType, Date previousBillDate,
			Date currentBillDate, Double avgUnits, Integer avgCount) {

		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		List<ElectricityBillLineItemEntity> billLineItemEntities = new ArrayList<>();
		int commonServiceId = serviceMasterService.getCommonServicesTariffId(
				accountID, typeOfService);
		logger.info("commonServiceId -------- " + commonServiceId);
		List<CommonServicesRateMaster> commonServicesRateMasters = tariffCalculationService
				.getCommonServicesRateMaster(commonServiceId);
		logger.info("commonServicesRateMasters.size() "
				+ commonServicesRateMasters.size());

		ElectricityBillEntity billEntityForArrears = new ElectricityBillEntity();
		billEntityForArrears.setAccountId(accountID);
		billEntityForArrears.setArrearsAmount(getLastBillArrearsAmount(
				"ARREARS", accountID, typeOfService));
		billEntityForArrears.setElBillDate(new java.sql.Timestamp(
				currentBillDate.getTime()));
		billEntityForArrears.setPostType("Bill");
		billEntityForArrears.setBillDate(new java.sql.Date(currentBillDate
				.getTime()));
		String configName = "Due Days";
		String status = "Active";
		String dueDays = electricityBillsService.getBillingConfigValue(configName,status);
		int days=0;
		if(dueDays!=null)
		{
			days = Integer.parseInt(dueDays);
		}
		
		Date billDueDate = addDays(currentBillDate, days);
		billEntityForArrears.setBillDueDate(new java.sql.Date(billDueDate
				.getTime()));
		Calendar c = Calendar.getInstance();
		c.setTime(currentBillDate);
		c.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date billMonth = new java.sql.Date(c.getTime().getTime());
		logger.info("billMonth ::::::::::::::: " + billMonth);
		billEntityForArrears.setBillMonth(billMonth);
		billEntityForArrears.setStatus("Generated");
		billEntityForArrears.setCreatedBy((String) SessionData.getUserDetails()
				.get("userID"));
		billEntityForArrears.setLastUpdatedBy((String) SessionData
				.getUserDetails().get("userID"));
		billEntityForArrears.setLastUpdatedDT(new java.sql.Timestamp(new Date()
				.getTime()));
		billEntityForArrears.setTypeOfService(typeOfService);
		billEntityForArrears.setBillNo("BILL"
				+ billParameterService.getSequencyNumber());
		logger.info("billEntity.getBillDueDate() ::::::::::::::: "
				+ billEntityForArrears.getBillDueDate());
		storeArrears("ARREARS", billEntityForArrears);

		float interestOnArrearsAmount = 0;
		float interestOnTaxAmount = 0;
		ElectricityBillLineItemEntity interestOnTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity interestOnBillAmount = new ElectricityBillLineItemEntity();
		consolidatedBill = interestCalculationCommonService(accountID,
				typeOfService, previousBillDate, commonServicesRateMasters,
				currentBillDate);
		for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
			if (map.getKey().equals("interestOnArrearsAmount")) {
				interestOnArrearsAmount = interestOnArrearsAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnArrearsAmount);
				interestOnBillAmount.setTransactionCode("OT_INTEREST");
				interestOnBillAmount.setBalanceAmount(interestOnArrearsAmount);
				interestOnBillAmount.setCreditAmount(0);
				interestOnBillAmount.setDebitAmount(0);
				interestOnBillAmount.setStatus("Not Approved");
				interestOnBillAmount.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnBillAmount);
			}
			if (map.getKey().equals("interestOnTaxAmount")) {
				interestOnTaxAmount = interestOnTaxAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnTaxAmount);
				interestOnTax.setTransactionCode("OT_TAX_INTEREST");
				interestOnTax.setBalanceAmount(interestOnTaxAmount);
				interestOnTax.setCreditAmount(0);
				interestOnTax.setDebitAmount(0);
				interestOnTax.setStatus("Not Approved");
				interestOnTax.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnTax);
			}
		}

		// ElectricityBillEntity billEntity = new ElectricityBillEntity();
		billEntity.setAccountId(accountID);
		billEntity.setArrearsAmount(getLastBillArrearsAmount("ARREARS",
				accountID, typeOfService));
		billEntity.setBillAmount(0d);
		billEntity
				.setNetAmount((double) (billEntity.getArrearsAmount() + billEntity
						.getBillAmount()));
		billEntity.setElBillDate(new Timestamp(new java.util.Date().getTime()));
		billEntity.setPostType(postType);
		billEntity.setFromDate(new java.sql.Date(previousBillDate.getTime()));
		billEntity.setBillDate(new java.sql.Date(currentBillDate.getTime()));
		Date billDueDate1 = addDays(currentBillDate, 15);
		billEntity.setBillDueDate(new java.sql.Date(billDueDate1.getTime()));
		Calendar c1 = Calendar.getInstance();
		c1.setTime(currentBillDate);
		c1.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date billMonth1 = new java.sql.Date(c1.getTime().getTime());
		logger.info("billMonth ::::::::::::::: " + billMonth1);
		billEntity.setBillMonth(billMonth1);
		billEntity.setStatus("Generated");
		billEntity.setCreatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		billEntity.setLastUpdatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		billEntity
				.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
		billEntity.setTypeOfService(typeOfService);
		// billEntity.setBillNo("BILL" +
		// billParameterService.getSequencyNumber());
		billEntity.setAvgCount(avgCount);
		billEntity.setAvgUnits(avgUnits);
		logger.info("billEntity.getBillDueDate() ::::::::::::::: "
				+ billEntity.getBillDueDate());
		electricityBillsService.update(billEntity);

	}

	private void saveSolidWasteBillParametersUpdate(
			ElectricityBillEntity electricityBillEntityUpdate,
			List<SolidWasteRateMaster> solidWasterRateMasterList,
			int accountID, String typeOfService, Date previousBillDate,
			Date currentBillDate, Float uomValue, Integer billableDays,
			String presentMeterStaus, String previousMeterStaus,
			Float meterConstant, Float presentUOMReading2,
			Float previousUOMReading, Double avgUnits, Integer avgCount,
			String billType) {

		float ecAmount = 0;
		float fcAmount = 0;
		float taxAmount = 0;
		float totalAmount;
		Float roundoffValue = 0f;
		float rateOfInterest = 0f;
		float arrearsAmount = 0f;
		Double avgAmount = 0d;
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();

		ElectricityBillLineItemEntity residentialCollection = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity solidWasteFixedCharges = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity solidWasteTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity roundOffBillLineItemEntity = new ElectricityBillLineItemEntity();

		Set<ElectricityBillLineItemEntity> billLineItemEntities = new LinkedHashSet<>();
		logger.info("solidWasterRateMasterList ------------------- "
				+ solidWasterRateMasterList.size());
		for (SolidWasteRateMaster solidWasteRateMaster : solidWasterRateMasterList) {
			logger.info("solidWasteRateMaster.getSolidWasteRateName() ---------------------- "
					+ solidWasteRateMaster.getSolidWasteRateName());
			if (solidWasteRateMaster.getSolidWasteRateName().equalsIgnoreCase(
					"Residential Collection")) {
				logger.info(":::::::::::: In "
						+ solidWasteRateMaster.getSolidWasteRateName()
						+ "::::::::::");
				residentialCollection.setTransactionCode("SW_RES");
				ecAmount = ecAmount
						+ calculationController.solidWasteTariffAmount(
								solidWasteRateMaster, previousBillDate,
								currentBillDate, uomValue);
				residentialCollection.setBalanceAmount(ecAmount);

				residentialCollection.setCreditAmount(0);
				residentialCollection.setDebitAmount(0);
				residentialCollection.setStatus("Not Approved");
				residentialCollection.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				residentialCollection.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				residentialCollection.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(residentialCollection);
			}

			if (solidWasteRateMaster.getSolidWasteRateName().equalsIgnoreCase(
					"Fixed charges")) {
				LocalDate fromDate = new LocalDate(previousBillDate);
				LocalDate toDate = new LocalDate(currentBillDate);
				PeriodType monthDay = PeriodType.yearMonthDay()
						.withYearsRemoved();
				Period difference = new Period(fromDate, toDate, monthDay);
				float billableMonths = difference.getMonths();
				int daysAfterMonth = difference.getDays();
				Calendar cal = Calendar.getInstance();
				cal.setTime(previousBillDate);
				float daysToMonths = (float) daysAfterMonth
						/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				float netMonth = daysToMonths + billableMonths;
				netMonth = Math.round(netMonth * 100) / 100;
				logger.info(":::::::::::: In "
						+ solidWasteRateMaster.getSolidWasteRateName()
						+ "::::::::::");

				fcAmount = fcAmount
						+ calculationController.solidWasteTariffAmount(
								solidWasteRateMaster, previousBillDate,
								currentBillDate, 1 * netMonth);

				solidWasteFixedCharges.setTransactionCode("SW_FC");
				solidWasteFixedCharges.setBalanceAmount(fcAmount);
				solidWasteFixedCharges.setCreditAmount(0);
				solidWasteFixedCharges.setDebitAmount(0);
				solidWasteFixedCharges.setStatus("Not Approved");
				solidWasteFixedCharges.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				solidWasteFixedCharges.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				solidWasteFixedCharges.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(solidWasteFixedCharges);
			}
		}

		float interestOnArrearsAmount = 0;
		float interestOnTaxAmount = 0;
		ElectricityBillLineItemEntity interestOnTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity interestOnBillAmount = new ElectricityBillLineItemEntity();
		consolidatedBill = interestCalculationSolidWaste(accountID,
				typeOfService, previousBillDate, solidWasterRateMasterList,
				currentBillDate);
		for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
			if (map.getKey().equals("interestOnArrearsAmount")) {
				interestOnArrearsAmount = interestOnArrearsAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnArrearsAmount);
				interestOnBillAmount.setTransactionCode("SW_INTEREST");
				interestOnBillAmount.setBalanceAmount(interestOnArrearsAmount);
				interestOnBillAmount.setCreditAmount(0);
				interestOnBillAmount.setDebitAmount(0);
				interestOnBillAmount.setStatus("Not Approved");
				interestOnBillAmount.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnBillAmount);
			}
			if (map.getKey().equals("interestOnTaxAmount")) {
				interestOnTaxAmount = interestOnTaxAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnTaxAmount);
				interestOnTax.setTransactionCode("SW_TAX_INTEREST");
				interestOnTax.setBalanceAmount(interestOnTaxAmount);
				interestOnTax.setCreditAmount(0);
				interestOnTax.setDebitAmount(0);
				interestOnTax.setStatus("Not Approved");
				interestOnTax.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnTax);
			}
		}

		for (SolidWasteRateMaster solidWasteRateMaster : solidWasterRateMasterList) {
			if (solidWasteRateMaster.getSolidWasteRateName().equalsIgnoreCase(
					"Tax")) {
				logger.info(":::::::::::: In "
						+ solidWasteRateMaster.getSolidWasteRateName()
						+ "::::::::::");
				solidWasteTax.setTransactionCode("SW_TAX");
				taxAmount = taxAmount
						+ calculationController.solidWasteTariffAmount(
								solidWasteRateMaster, previousBillDate,
								currentBillDate, ecAmount);
				logger.info("taxAmount ::::::::: " + taxAmount);
				solidWasteTax.setBalanceAmount(taxAmount);
				solidWasteTax.setCreditAmount(0);
				solidWasteTax.setDebitAmount(0);
				solidWasteTax.setStatus("Not Approved");
				solidWasteTax.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				solidWasteTax.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				solidWasteTax.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(solidWasteTax);
			}
		}
		totalAmount = roundOff(ecAmount + fcAmount + taxAmount + arrearsAmount
				+ rateOfInterest, 0);
		roundoffValue = roundOff(
				totalAmount
						- (ecAmount + fcAmount + taxAmount + arrearsAmount + rateOfInterest),
				2);
		logger.info("::::::::::::::::::::::::: Round off value is ::::::::::::::: "
				+ roundoffValue);

		ElectricityBillEntity billEntity = new ElectricityBillEntity();
		billEntity.setAccountId(accountID);
		billEntity.setArrearsAmount(getLastBillArrearsAmount("ARREARS",
				accountID, typeOfService));
		billEntity.setBillAmount((double) totalAmount);
		billEntity
				.setNetAmount((double) (billEntity.getArrearsAmount() + totalAmount));
		billEntity.setElBillDate(new Timestamp(new java.util.Date().getTime()));
		billEntity.setPostType("Bill");
		billEntity.setBillDate(new java.sql.Date(currentBillDate.getTime()));
		String configName = "Due Days";
		String status = "Active";
		String dueDays = electricityBillsService.getBillingConfigValue(configName,status);
		int days=0;
		if(dueDays!=null)
		{
			days = Integer.parseInt(dueDays);
		}
		
		Date billDueDate = addDays(currentBillDate, days);
		billEntity.setBillDueDate(new java.sql.Date(billDueDate.getTime()));
		Calendar c = Calendar.getInstance();
		c.setTime(currentBillDate);
		c.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date billMonth = new java.sql.Date(c.getTime().getTime());
		logger.info("billMonth ::::::::::::::: " + billMonth);
		billEntity.setBillMonth(billMonth);
		billEntity.setStatus("Generated");
		billEntity.setCreatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		billEntity.setLastUpdatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		billEntity
				.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
		billEntity.setTypeOfService(typeOfService);
		billEntity.setBillNo("BILL" + billParameterService.getSequencyNumber());
		logger.info("billEntity.getBillDueDate() ::::::::::::::: "
				+ billEntity.getBillDueDate());

		roundOffBillLineItemEntity.setCreditAmount(0);
		roundOffBillLineItemEntity.setDebitAmount(0);
		roundOffBillLineItemEntity.setStatus("Not Approved");
		roundOffBillLineItemEntity.setCreatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
				new Date().getTime()));
		roundOffBillLineItemEntity.setBalanceAmount(roundoffValue);
		roundOffBillLineItemEntity.setTransactionCode("EL_ROF");
		billLineItemEntities.add(roundOffBillLineItemEntity);

		ElectricityBillEntity billEntity1 = saveBillUpdate(
				electricityBillEntityUpdate, accountID, totalAmount,
				previousBillDate, currentBillDate, typeOfService,
				arrearsAmount, avgUnits, avgCount, billType, avgAmount);
		saveBillLineItems(billEntity1, billLineItemEntities, roundoffValue,
				arrearsAmount);
		saveBillSolidWasteParameters(billEntity1, solidWasterRateMasterList,
				accountID, typeOfService, previousBillDate, currentBillDate,
				uomValue, billableDays, presentMeterStaus, previousMeterStaus,
				meterConstant, uomValue, previousUOMReading);
		Locale locale = null;
		billController.getBillDoc(billEntity1.getElBillId(), locale,1);

	}

	private void saveGasBillParametersUpdate(
			ElectricityBillEntity electricityBillEntityUpdate,
			List<GasRateMaster> gasRateMasterList, int accountID,
			String typeOfService, Date previousBillDate, Date currentBillDate,
			Float uomValue, Integer billableDays, String presentMeterStaus,
			String previousMeterStaus, Float meterConstant,
			Float presentUOMReading, Float previousUOMReading, Double avgUnits,
			Integer avgCount, String billType, String meterchangeUnit) {

		float ecAmount = 0;
		float fcAmount = 0;
		float taxAmount = 0;
		float totalAmount;
		Float roundoffValue = 0f;
		float rateOfInterest = 0f;
		float arrearsAmount = 0f;
		Double avgAmount = 0d;
		String gaString = null;
		float lossDistributionUnit = 0.0f;
		float distributionLossAmount = 0f;
		ElectricityBillEntity billEntityForArrears = new ElectricityBillEntity();
		billEntityForArrears.setAccountId(accountID);
		billEntityForArrears.setArrearsAmount(getLastBillArrearsAmount(
				"ARREARS", accountID, typeOfService));
		billEntityForArrears.setElBillDate(new java.sql.Timestamp(
				currentBillDate.getTime()));
		billEntityForArrears.setPostType("Bill");
		billEntityForArrears.setBillDate(new java.sql.Date(currentBillDate
				.getTime()));
		String configName = "Due Days";
		String status = "Active";
		String dueDays = electricityBillsService.getBillingConfigValue(configName,status);
		int days=0;
		if(dueDays!=null)
		{
			days = Integer.parseInt(dueDays);
		}
		
		Date billDueDate = addDays(currentBillDate, days);
		billEntityForArrears.setBillDueDate(new java.sql.Date(billDueDate
				.getTime()));
		Calendar c = Calendar.getInstance();
		c.setTime(currentBillDate);
		c.set(Calendar.DAY_OF_MONTH, 1);

		java.sql.Date billMonth = new java.sql.Date(c.getTime().getTime());
		logger.info("billMonth ::::::::::::::: " + billMonth);
		billEntityForArrears.setBillMonth(billMonth);
		billEntityForArrears.setStatus("Generated");
		billEntityForArrears.setCreatedBy((String) SessionData.getUserDetails()
				.get("userID"));
		billEntityForArrears.setLastUpdatedBy((String) SessionData
				.getUserDetails().get("userID"));
		billEntityForArrears.setLastUpdatedDT(new java.sql.Timestamp(new Date()
				.getTime()));
		billEntityForArrears.setTypeOfService(typeOfService);
		billEntityForArrears.setBillNo("BILL"
				+ billParameterService.getSequencyNumber());
		logger.info("billEntity.getBillDueDate() ::::::::::::::: "
				+ billEntityForArrears.getBillDueDate());
		storeArrears("ARREARS", billEntityForArrears);

		ElectricityBillLineItemEntity gasDomestic = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity distributionLosses = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity gasFixedCharges = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity gasTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity roundOffBillLineItemEntity = new ElectricityBillLineItemEntity();
		Set<ElectricityBillLineItemEntity> billLineItemEntities = new LinkedHashSet<>();
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		ElectricityBillEntity previousBillEntity = electricityBillsService
				.getPreviousBill(accountID, typeOfService, previousBillDate,
						"Bill");

		for (GasRateMaster gasRateMaster : gasRateMasterList) {
			if (gasRateMaster.getGasRateName().equalsIgnoreCase("Domestic")) {
				logger.info(":::::::::::: In " + gasRateMaster.getGasRateName()
						+ "::::::::::");

				lossDistributionUnit = tariffCalculationService
						.getdistributionLossUnit(previousBillDate, accountID);

				if (lossDistributionUnit != 0) {
					logger.info(":::::::::::lossDistributionUnit:::::"
							+ lossDistributionUnit);
					consolidatedBill = calculationController.gasTariffAmount(
							gasRateMaster, previousBillDate, currentBillDate,
							lossDistributionUnit);

					for (Entry<Object, Object> map : consolidatedBill
							.entrySet()) {
						if (map.getKey().equals("total")) {
							distributionLossAmount = (Float) map.getValue();
							logger.info("distributionLossAmount ------------------------------- IN Gas "
									+ distributionLossAmount);
						}
						if (map.getKey().equals("gaString")) {
							gaString = (String) map.getValue();
						}
					}
					distributionLosses.setTransactionCode("GA_LOSS");
					distributionLosses.setBalanceAmount(distributionLossAmount);
					distributionLosses.setCreditAmount(0);
					distributionLosses.setDebitAmount(0);
					distributionLosses.setStatus("Not Approved");
					distributionLosses.setCreatedBy((String) SessionData
							.getUserDetails().get("userID"));
					distributionLosses.setLastUpdatedBy((String) SessionData
							.getUserDetails().get("userID"));
					distributionLosses.setLastUpdatedDT(new java.sql.Timestamp(
							new Date().getTime()));
					billLineItemEntities.add(distributionLosses);
				}

				if (presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus
								.equalsIgnoreCase("Meter Not Reading")
						|| presentMeterStaus
								.equalsIgnoreCase("Direct Connection")
						|| presentMeterStaus.equalsIgnoreCase("Burnt")
						|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
					consolidatedBill = calculationController.gasTariffAmount(
							gasRateMaster, previousBillDate, currentBillDate,
							uomValue);
				} else {
					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCountGas(gasRateMaster,
											previousBillDate, currentBillDate,
											uomValue,
											previousBillEntity.getAvgCount());
							for (Entry<Object, Object> map : consolidatedBill
									.entrySet()) {
								if (map.getKey().equals("total")) {
									ecAmount = ecAmount
											+ (Float) map.getValue();
								}
							}
						} else {
							consolidatedBill = calculationController
									.gasTariffAmount(gasRateMaster,
											previousBillDate, currentBillDate,
											uomValue);
						}
					} else {
						consolidatedBill = calculationController
								.gasTariffAmount(gasRateMaster,
										previousBillDate, currentBillDate,
										uomValue);
					}
				}

				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						ecAmount = (Float) map.getValue();
					}
					if (map.getKey().equals("gaString")) {
						gaString = (String) map.getValue();
					}
				}
				gasDomestic.setTransactionCode("GA_DOM");
				gasDomestic.setBalanceAmount(ecAmount);
				gasDomestic.setCreditAmount(0);
				gasDomestic.setDebitAmount(0);
				gasDomestic.setStatus("Not Approved");
				gasDomestic.setCreatedBy((String) SessionData.getUserDetails()
						.get("userID"));
				gasDomestic.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				gasDomestic.setLastUpdatedDT(new java.sql.Timestamp(new Date()
						.getTime()));
				billLineItemEntities.add(gasDomestic);
			}

			if (gasRateMaster.getGasRateName()
					.equalsIgnoreCase("Fixed charges")) {
				logger.info(":::::::::::: In " + gasRateMaster.getGasRateName()
						+ "::::::::::");

				LocalDate fromDate = new LocalDate(previousBillDate);
				LocalDate toDate = new LocalDate(currentBillDate);
				PeriodType monthDay = PeriodType.yearMonthDay()
						.withYearsRemoved();
				Period difference = new Period(fromDate, toDate, monthDay);
				float billableMonths = difference.getMonths();
				int daysAfterMonth = difference.getDays();
				Calendar cal = Calendar.getInstance();
				cal.setTime(previousBillDate);
				float daysToMonths = (float) daysAfterMonth
						/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				float netMonth = daysToMonths + billableMonths;
				netMonth = Math.round(netMonth * 100) / 100;
				if (presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus
								.equalsIgnoreCase("Meter Not Reading")
						|| presentMeterStaus
								.equalsIgnoreCase("Direct Connection")
						|| presentMeterStaus.equalsIgnoreCase("Burnt")
						|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
					consolidatedBill = calculationController.gasTariffAmount(
							gasRateMaster, previousBillDate, currentBillDate,
							1 * netMonth);
				} else {
					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCountGas(gasRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth,
											previousBillEntity.getAvgCount());

						} else {
							consolidatedBill = calculationController
									.gasTariffAmount(gasRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth);
						}
					} else {
						consolidatedBill = calculationController
								.gasTariffAmount(gasRateMaster,
										previousBillDate, currentBillDate,
										1 * netMonth);
					}
				}
				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						fcAmount = (Float) map.getValue();
					}
				}
				gasFixedCharges.setTransactionCode("GA_FC");
				gasFixedCharges.setBalanceAmount(fcAmount);
				gasFixedCharges.setCreditAmount(0);
				gasFixedCharges.setDebitAmount(0);
				gasFixedCharges.setStatus("Not Approved");
				gasFixedCharges.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				gasFixedCharges.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				gasFixedCharges.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(gasFixedCharges);
			}

		}

		float interestOnArrearsAmount = 0;
		float interestOnTaxAmount = 0;
		ElectricityBillLineItemEntity interestOnTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity interestOnBillAmount = new ElectricityBillLineItemEntity();
		consolidatedBill = interestCalculationGas(accountID, typeOfService,
				previousBillDate, gasRateMasterList, currentBillDate);

		for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
			if (map.getKey().equals("interestOnArrearsAmount")) {
				interestOnArrearsAmount = interestOnArrearsAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnArrearsAmount);
				interestOnBillAmount.setTransactionCode("GA_INTEREST");
				interestOnBillAmount.setBalanceAmount(interestOnArrearsAmount);
				interestOnBillAmount.setCreditAmount(0);
				interestOnBillAmount.setDebitAmount(0);
				interestOnBillAmount.setStatus("Not Approved");
				interestOnBillAmount.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnBillAmount);
			}
			if (map.getKey().equals("interestOnTaxAmount")) {
				interestOnTaxAmount = interestOnTaxAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnTaxAmount);
				interestOnTax.setTransactionCode("GA_TAX_INTEREST");
				interestOnTax.setBalanceAmount(interestOnTaxAmount);
				interestOnTax.setCreditAmount(0);
				interestOnTax.setDebitAmount(0);
				interestOnTax.setStatus("Not Approved");
				interestOnTax.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnTax);
			}
		}

		for (GasRateMaster gasRateMaster : gasRateMasterList) {
			if (gasRateMaster.getGasRateName().equalsIgnoreCase("Tax")) {
				logger.info(":::::::::::: In " + gasRateMaster.getGasRateName()
						+ "::::::::::");
				gasTax.setTransactionCode("GA_TAX");
				consolidatedBill = calculationController.gasTariffAmount(
						gasRateMaster, previousBillDate, currentBillDate,
						(ecAmount));
				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						taxAmount = (Float) map.getValue();
					}
				}
				logger.info("taxAmount ::::::::: " + taxAmount);
				gasTax.setBalanceAmount(taxAmount);
				gasTax.setCreditAmount(0);
				gasTax.setDebitAmount(0);
				gasTax.setStatus("Not Approved");
				gasTax.setCreatedBy((String) SessionData.getUserDetails().get(
						"userID"));
				gasTax.setLastUpdatedBy((String) SessionData.getUserDetails()
						.get("userID"));
				gasTax.setLastUpdatedDT(new java.sql.Timestamp(new Date()
						.getTime()));
				billLineItemEntities.add(gasTax);
			}
		}
		totalAmount = roundOff(ecAmount + distributionLossAmount + fcAmount
				+ taxAmount + arrearsAmount + rateOfInterest
				+ interestOnArrearsAmount + interestOnTaxAmount, 0);
		roundoffValue = roundOff(totalAmount
				- (ecAmount + fcAmount + distributionLossAmount + taxAmount
						+ arrearsAmount + rateOfInterest
						+ interestOnArrearsAmount + interestOnTaxAmount), 2);
		logger.info("::::::::::::::::::::::::: Round off value is ::::::::::::::: "
				+ roundoffValue);

		roundOffBillLineItemEntity.setCreditAmount(0);
		roundOffBillLineItemEntity.setDebitAmount(0);
		roundOffBillLineItemEntity.setStatus("Not Approved");
		roundOffBillLineItemEntity.setCreatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
				new Date().getTime()));
		roundOffBillLineItemEntity.setBalanceAmount(roundoffValue);
		roundOffBillLineItemEntity.setTransactionCode("EL_ROF");
		billLineItemEntities.add(roundOffBillLineItemEntity);

		if (presentMeterStaus.equalsIgnoreCase("Door Lock")
				|| presentMeterStaus.equalsIgnoreCase("Meter Not Reading")
				|| presentMeterStaus.equalsIgnoreCase("Direct Connection")
				|| presentMeterStaus.equalsIgnoreCase("Burnt")
				|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
			logger.info("-------------- Average bill generation for gas -------------------------");
			presentUOMReading = previousUOMReading;
			ElectricityBillEntity billEntity1 = electricityBillsService
					.getPreviousBill(accountID, typeOfService,
							previousBillDate, "Bill");
			logger.info("billEntity1 ----------- " + billEntity1);
			if (billEntity1 != null) {
				avgUnits = billEntity1.getAvgUnits() + uomValue;
				avgCount = billEntity1.getAvgCount() + 1;
				billType = "Average";
			}
		} else {
			ElectricityBillEntity billEntity1 = electricityBillsService
					.getPreviousBill(accountID, typeOfService,
							previousBillDate, "Bill");
			List<ElectricityBillEntity> billEntities = new ArrayList<>();
			if (billEntity1 != null) {
				if (billEntity1.getAvgCount() >= 1) {
					List<BigDecimal> billIds = electricityBillsService
							.getLastAvgBills(accountID, typeOfService,
									previousBillDate, "Bill",
									billEntity1.getAvgCount());
					for (BigDecimal bigDecimal : billIds) {
						billEntities.add(electricityBillsService
								.find(bigDecimal.intValue()));
					}
				}

				for (ElectricityBillEntity electricityBillEntity : billEntities) {
					logger.info("------------- electricityBillEntity.getBillAmount() "
							+ electricityBillEntity.getBillAmount());
					avgAmount = avgAmount
							+ electricityBillEntity.getBillAmount();
					logger.info("--------------- avgAmount " + avgAmount);
				}
			}
		}

		ElectricityBillEntity billEntity = saveBillUpdate(
				electricityBillEntityUpdate, accountID, totalAmount,
				previousBillDate, currentBillDate, typeOfService,
				arrearsAmount, avgUnits, avgCount, billType, avgAmount);
		saveBillLineItems(billEntity, billLineItemEntities, roundoffValue,
				arrearsAmount);
		saveBillGasParameters(billEntity, gasRateMasterList, accountID,
				typeOfService, previousBillDate, currentBillDate, uomValue,
				billableDays, presentMeterStaus, previousMeterStaus,
				meterConstant, presentUOMReading, previousUOMReading, gaString,
				lossDistributionUnit, meterchangeUnit);
		Locale locale = null;
		billController.getBillDoc(billEntity.getElBillId(), locale,1);

	}

	private void saveWaterBillParametersUpdate(
			ElectricityBillEntity electricityBillEntityUpdate,
			List<WTRateMaster> waterRateMasterList, int accountID,
			String typeOfService, Date previousBillDate, Date currentBillDate,
			Float uomValue, Integer billableDays, String presentMeterStaus,
			String previousMeterStaus, Float meterConstant,
			Float presentUOMReading, Float previousUOMReading, Double avgUnits,
			Integer avgCount, String billType, String meterchangeUnit) {

		ElectricityBillEntity previousBillEntity = electricityBillsService
				.getPreviousBill(accountID, typeOfService, previousBillDate,
						"Bill");

		float wcAmount = 0;
		float scAmount = 0;
		float solAmount = 0;
		float fcAmount = 0;
		float roAmount = 0f;
		float taxAmount = 0;
		float totalAmount;
		Float roundoffValue = 0f;
		float rateOfInterest = 0f;
		float arrearsAmount = 0f;
		String wtSlabString = null;
		HashMap<String, Object> consolidatedBill = new LinkedHashMap<>();
		Double avgAmount = 0d;

		ElectricityBillEntity billEntityForArrears = new ElectricityBillEntity();
		billEntityForArrears.setAccountId(accountID);
		billEntityForArrears.setArrearsAmount(getLastBillArrearsAmount(
				"ARREARS", accountID, typeOfService));
		billEntityForArrears.setElBillDate(new java.sql.Timestamp(
				currentBillDate.getTime()));
		billEntityForArrears.setPostType("Bill");
		billEntityForArrears.setBillDate(new java.sql.Date(currentBillDate
				.getTime()));
		String configName = "Due Days";
		String status = "Active";
		String dueDays = electricityBillsService.getBillingConfigValue(configName,status);
		int days=0;
		if(dueDays!=null)
		{
			days = Integer.parseInt(dueDays);
		}
		
		Date billDueDate = addDays(currentBillDate, days);
		billEntityForArrears.setBillDueDate(new java.sql.Date(billDueDate
				.getTime()));
		Calendar c = Calendar.getInstance();
		c.setTime(currentBillDate);
		c.set(Calendar.DAY_OF_MONTH, 1);

		java.sql.Date billMonth = new java.sql.Date(c.getTime().getTime());
		logger.info("billMonth ::::::::::::::: " + billMonth);
		billEntityForArrears.setBillMonth(billMonth);
		billEntityForArrears.setStatus("Generated");
		billEntityForArrears.setCreatedBy((String) SessionData.getUserDetails()
				.get("userID"));
		billEntityForArrears.setLastUpdatedBy((String) SessionData
				.getUserDetails().get("userID"));
		billEntityForArrears.setLastUpdatedDT(new java.sql.Timestamp(new Date()
				.getTime()));
		billEntityForArrears.setTypeOfService(typeOfService);
		billEntityForArrears.setBillNo("BILL"
				+ billParameterService.getSequencyNumber());
		logger.info("billEntity.getBillDueDate() ::::::::::::::: "
				+ billEntityForArrears.getBillDueDate());
		storeArrears("ARREARS", billEntityForArrears);

		Set<ElectricityBillLineItemEntity> billLineItemEntities = new LinkedHashSet<>();
		ElectricityBillLineItemEntity waterCharges = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity sweageCharges = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity roCharges = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity solorCharages = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity waterTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity waterFixcedCharages = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity roundOffBillLineItemEntity = new ElectricityBillLineItemEntity();

		for (WTRateMaster waterRateMaster : waterRateMasterList) {
			if (waterRateMaster.getWtRateName().equalsIgnoreCase(
					"Water charges")) {
				if (presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus
								.equalsIgnoreCase("Meter Not Reading")
						|| presentMeterStaus
								.equalsIgnoreCase("Direct Connection")
						|| presentMeterStaus.equalsIgnoreCase("Burnt")
						|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
					consolidatedBill = calculationController.waterTariffAmount(
							waterRateMaster, previousBillDate, currentBillDate,
							uomValue);
				} else {
					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCountWater(waterRateMaster,
											previousBillDate, currentBillDate,
											uomValue,
											previousBillEntity.getAvgCount());

						} else {
							consolidatedBill = calculationController
									.waterTariffAmount(waterRateMaster,
											previousBillDate, currentBillDate,
											uomValue);
						}
					} else {
						consolidatedBill = calculationController
								.waterTariffAmount(waterRateMaster,
										previousBillDate, currentBillDate,
										uomValue);
					}
				}
				for (Entry<String, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						wcAmount = wcAmount + (Float) map.getValue();
					}
					if (map.getKey().equals("wtSlabString")) {
						wtSlabString = (String) map.getValue();
					}
				}
				logger.info(":::::::::::: In "
						+ waterRateMaster.getWtRateName() + "::::::::::");
				waterCharges.setTransactionCode("WT_WC");
				logger.info("ecAmount ::::::::: " + wcAmount);
				waterCharges.setBalanceAmount(wcAmount);
				waterCharges.setCreditAmount(0);
				waterCharges.setDebitAmount(0);
				waterCharges.setStatus("Not Approved");
				waterCharges.setCreatedBy((String) SessionData.getUserDetails()
						.get("userID"));
				waterCharges.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				waterCharges.setLastUpdatedDT(new java.sql.Timestamp(new Date()
						.getTime()));
				billLineItemEntities.add(waterCharges);
			}

			if (waterRateMaster.getWtRateName().equalsIgnoreCase(
					"Solar charges")) {
				String solApplicable = null;
				ServiceMastersEntity mastersEntity1 = serviceMasterService
						.getServiceMasterServicType(accountID, typeOfService);
				List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService
						.findAllByParentId(mastersEntity1.getServiceMasterId());
				for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1) {

					if (serviceParametersEntity.getServiceParameterMaster()
							.getSpmName().equalsIgnoreCase("Solar charges")) {
						solApplicable = serviceParametersEntity
								.getServiceParameterValue();
					}
				}
				logger.info("solApplicable --------------- " + solApplicable);
				if (solApplicable != null
						&& solApplicable.equalsIgnoreCase("Yes")) {

					LocalDate fromDate = new LocalDate(previousBillDate);
					LocalDate toDate = new LocalDate(currentBillDate);
					PeriodType monthDay = PeriodType.yearMonthDay()
							.withYearsRemoved();
					Period difference = new Period(fromDate, toDate, monthDay);
					float billableMonths = difference.getMonths();
					int daysAfterMonth = difference.getDays();

					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(currentBillDate);
					float daysToMonths = (float) daysAfterMonth
							/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
					float netMonth = daysToMonths + billableMonths;

					if (presentMeterStaus.equalsIgnoreCase("Door Lock")
							|| presentMeterStaus
									.equalsIgnoreCase("Meter Not Reading")
							|| presentMeterStaus
									.equalsIgnoreCase("Direct Connection")
							|| presentMeterStaus.equalsIgnoreCase("Burnt")
							|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
						consolidatedBill = calculationController
								.waterTariffAmount(waterRateMaster,
										previousBillDate, currentBillDate,
										1 * netMonth);
					} else {
						if (previousBillEntity != null) {
							if (previousBillEntity.getAvgCount() >= 1) {
								consolidatedBill = calculationController
										.tariffAmountAvgCountWater(
												waterRateMaster,
												previousBillDate,
												currentBillDate, 1 * netMonth,
												previousBillEntity
														.getAvgCount());

							} else {
								consolidatedBill = calculationController
										.waterTariffAmount(waterRateMaster,
												previousBillDate,
												currentBillDate, 1 * netMonth);
							}
						} else {
							consolidatedBill = calculationController
									.waterTariffAmount(waterRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth);
						}
					}
					for (Entry<String, Object> map : consolidatedBill
							.entrySet()) {
						if (map.getKey().equals("total")) {
							solAmount = (Float) map.getValue();
						}
					}
					solorCharages.setTransactionCode("WT_SOL");
					solorCharages.setBalanceAmount(solAmount);
					solorCharages.setCreditAmount(0);
					solorCharages.setDebitAmount(0);
					solorCharages.setStatus("Not Approved");
					solorCharages.setCreatedBy((String) SessionData
							.getUserDetails().get("userID"));
					solorCharages.setLastUpdatedBy((String) SessionData
							.getUserDetails().get("userID"));
					solorCharages.setLastUpdatedDT(new java.sql.Timestamp(
							new Date().getTime()));
					billLineItemEntities.add(solorCharages);
				}
			}

			if (waterRateMaster.getWtRateName().equalsIgnoreCase("RO charges")) {
				String roApplicable = null;
				ServiceMastersEntity mastersEntity1 = serviceMasterService
						.getServiceMasterServicType(accountID, typeOfService);
				List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService
						.findAllByParentId(mastersEntity1.getServiceMasterId());
				for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1) {

					if (serviceParametersEntity.getServiceParameterMaster()
							.getSpmName().equalsIgnoreCase("RO charges")) {
						roApplicable = serviceParametersEntity
								.getServiceParameterValue();
					}
				}
				logger.info("roApplicable --------------- " + roApplicable);
				if (roApplicable != null
						&& roApplicable.equalsIgnoreCase("Yes")) {

					LocalDate fromDate = new LocalDate(previousBillDate);
					LocalDate toDate = new LocalDate(currentBillDate);
					PeriodType monthDay = PeriodType.yearMonthDay()
							.withYearsRemoved();
					Period difference = new Period(fromDate, toDate, monthDay);
					float billableMonths = difference.getMonths();
					int daysAfterMonth = difference.getDays();

					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(currentBillDate);
					float daysToMonths = (float) daysAfterMonth
							/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
					float netMonth = daysToMonths + billableMonths;

					if (presentMeterStaus.equalsIgnoreCase("Door Lock")
							|| presentMeterStaus
									.equalsIgnoreCase("Meter Not Reading")
							|| presentMeterStaus
									.equalsIgnoreCase("Direct Connection")
							|| presentMeterStaus.equalsIgnoreCase("Burnt")
							|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
						consolidatedBill = calculationController
								.waterTariffAmount(waterRateMaster,
										previousBillDate, currentBillDate,
										1 * netMonth);
					} else {
						if (previousBillEntity != null) {
							if (previousBillEntity.getAvgCount() >= 1) {
								consolidatedBill = calculationController
										.tariffAmountAvgCountWater(
												waterRateMaster,
												previousBillDate,
												currentBillDate, 1 * netMonth,
												previousBillEntity
														.getAvgCount());

							} else {
								consolidatedBill = calculationController
										.waterTariffAmount(waterRateMaster,
												previousBillDate,
												currentBillDate, 1 * netMonth);
							}
						} else {
							consolidatedBill = calculationController
									.waterTariffAmount(waterRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth);
						}
					}
					for (Entry<String, Object> map : consolidatedBill
							.entrySet()) {
						if (map.getKey().equals("total")) {
							roAmount = roAmount + (Float) map.getValue();
						}
					}
					roCharges.setTransactionCode("WT_RO");
					roCharges.setBalanceAmount(roAmount);
					roCharges.setCreditAmount(0);
					roCharges.setDebitAmount(0);
					roCharges.setStatus("Not Approved");
					roCharges.setCreatedBy((String) SessionData
							.getUserDetails().get("userID"));
					roCharges.setLastUpdatedBy((String) SessionData
							.getUserDetails().get("userID"));
					roCharges.setLastUpdatedDT(new java.sql.Timestamp(
							new Date().getTime()));
					billLineItemEntities.add(roCharges);
				}
			}

			if (waterRateMaster.getWtRateName().equalsIgnoreCase(
					"Sewage charges")) {

				LocalDate fromDate = new LocalDate(previousBillDate);
				LocalDate toDate = new LocalDate(currentBillDate);
				PeriodType monthDay = PeriodType.yearMonthDay()
						.withYearsRemoved();
				Period difference = new Period(fromDate, toDate, monthDay);
				float billableMonths = difference.getMonths();
				int daysAfterMonth = difference.getDays();

				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(currentBillDate);
				float daysToMonths = (float) daysAfterMonth
						/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
				float netMonth = daysToMonths + billableMonths;

				if (presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus
								.equalsIgnoreCase("Meter Not Reading")
						|| presentMeterStaus
								.equalsIgnoreCase("Direct Connection")
						|| presentMeterStaus.equalsIgnoreCase("Burnt")
						|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
					consolidatedBill = calculationController.waterTariffAmount(
							waterRateMaster, previousBillDate, currentBillDate,
							1 * netMonth);
				} else {
					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCountWater(waterRateMaster,
											previousBillDate, currentBillDate,
											0.0f,
											previousBillEntity.getAvgCount());

						} else {
							consolidatedBill = calculationController
									.waterTariffAmount(waterRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth);
						}
					} else {
						consolidatedBill = calculationController
								.waterTariffAmount(waterRateMaster,
										previousBillDate, currentBillDate,
										1 * netMonth);
					}
				}
				for (Entry<String, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						scAmount = (Float) map.getValue();
					}
				}
				logger.info(":::::::::::: In "
						+ waterRateMaster.getWtRateName() + "::::::::::");
				sweageCharges.setTransactionCode("WT_SC");
				logger.info("ecAmount ::::::::: " + scAmount);
				sweageCharges.setBalanceAmount(scAmount);
				sweageCharges.setCreditAmount(0);
				sweageCharges.setDebitAmount(0);
				sweageCharges.setStatus("Not Approved");
				sweageCharges.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				sweageCharges.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				sweageCharges.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(sweageCharges);
			}

			if (waterRateMaster.getWtRateName().equalsIgnoreCase(
					"Fixed charges")) {
				LocalDate fromDate = new LocalDate(previousBillDate);
				LocalDate toDate = new LocalDate(currentBillDate);
				PeriodType monthDay = PeriodType.yearMonthDay()
						.withYearsRemoved();
				Period difference = new Period(fromDate, toDate, monthDay);
				float billableMonths = difference.getMonths();
				int daysAfterMonth = difference.getDays();

				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(currentBillDate);
				float daysToMonths = (float) daysAfterMonth
						/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
				float netMonth = daysToMonths + billableMonths;
				if (presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus
								.equalsIgnoreCase("Meter Not Reading")
						|| presentMeterStaus
								.equalsIgnoreCase("Direct Connection")
						|| presentMeterStaus.equalsIgnoreCase("Burnt")
						|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
					consolidatedBill = calculationController.waterTariffAmount(
							waterRateMaster, previousBillDate, currentBillDate,
							1 * netMonth);
				} else {
					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCountWater(waterRateMaster,
											previousBillDate, currentBillDate,
											0.0f,
											previousBillEntity.getAvgCount());

						} else {
							consolidatedBill = calculationController
									.waterTariffAmount(waterRateMaster,
											previousBillDate, currentBillDate,
											1 * netMonth);
						}
					} else {
						consolidatedBill = calculationController
								.waterTariffAmount(waterRateMaster,
										previousBillDate, currentBillDate,
										1 * netMonth);
					}
				}
				for (Entry<String, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						fcAmount = (Float) map.getValue();
					}
				}
				waterFixcedCharages.setTransactionCode("WT_FC");
				waterFixcedCharages.setBalanceAmount(fcAmount);
				waterFixcedCharages.setCreditAmount(0);
				waterFixcedCharages.setDebitAmount(0);
				waterFixcedCharages.setStatus("Not Approved");
				waterFixcedCharages.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				waterFixcedCharages.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				waterFixcedCharages.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(waterFixcedCharages);
			}
		}

		float interestOnArrearsAmount = 0;
		float interestOnTaxAmount = 0;
		ElectricityBillLineItemEntity interestOnTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity interestOnBillAmount = new ElectricityBillLineItemEntity();
		consolidatedBill = interestCalculationWater(accountID, typeOfService,
				previousBillDate, waterRateMasterList, currentBillDate);
		for (Entry<String, Object> map : consolidatedBill.entrySet()) {
			if (map.getKey().equals("interestOnArrearsAmount")) {
				interestOnArrearsAmount = interestOnArrearsAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnArrearsAmount);
				interestOnBillAmount.setTransactionCode("WT_INTEREST");
				interestOnBillAmount.setBalanceAmount(interestOnArrearsAmount);
				interestOnBillAmount.setCreditAmount(0);
				interestOnBillAmount.setDebitAmount(0);
				interestOnBillAmount.setStatus("Not Approved");
				interestOnBillAmount.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnBillAmount);
			}
			if (map.getKey().equals("interestOnTaxAmount")) {
				interestOnTaxAmount = interestOnTaxAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnTaxAmount);
				interestOnTax.setTransactionCode("WT_TAX_INTEREST");
				interestOnTax.setBalanceAmount(interestOnTaxAmount);
				interestOnTax.setCreditAmount(0);
				interestOnTax.setDebitAmount(0);
				interestOnTax.setStatus("Not Approved");
				interestOnTax.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnTax);
			}
		}

		float totalExcludeTax = (float) (wcAmount + roundoffValue);
		logger.info(":::::::::::::: Total Amount Excludeing tax ::::::::::::: "
				+ totalExcludeTax);

		for (WTRateMaster waterRateMaster : waterRateMasterList) {
			if (waterRateMaster.getWtRateName().equalsIgnoreCase("Tax")) {
				logger.info(":::::::::::: In "
						+ waterRateMaster.getWtRateName() + "::::::::::");
				waterTax.setTransactionCode("WT_TAX");
				consolidatedBill = calculationController.waterTariffAmount(
						waterRateMaster, previousBillDate, currentBillDate,
						totalExcludeTax);
				for (Entry<String, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						taxAmount = (Float) map.getValue();
					}
				}

				waterTax.setBalanceAmount(taxAmount);
				waterTax.setCreditAmount(0);
				waterTax.setDebitAmount(0);
				waterTax.setStatus("Not Approved");
				waterTax.setCreatedBy((String) SessionData.getUserDetails()
						.get("userID"));
				waterTax.setLastUpdatedBy((String) SessionData.getUserDetails()
						.get("userID"));
				waterTax.setLastUpdatedDT(new java.sql.Timestamp(new Date()
						.getTime()));
				billLineItemEntities.add(waterTax);
			}

		}
		logger.info("wcAmount " + wcAmount);
		logger.info("scAmount " + scAmount);
		logger.info("solAmount " + solAmount);
		logger.info("fcAmount " + fcAmount);
		logger.info("roAmount " + roAmount);
		logger.info("taxAmount " + taxAmount);
		logger.info("rateOfInterest " + rateOfInterest);
		logger.info("arrearsAmount " + arrearsAmount);
		logger.info("interestOnTaxAmount " + interestOnTax.getBalanceAmount());
		logger.info("interestOnArrears "
				+ interestOnBillAmount.getBalanceAmount());
		logger.info("Total without round off "
				+ (wcAmount + roAmount + solAmount + scAmount + fcAmount
						+ taxAmount + rateOfInterest + arrearsAmount
						+ interestOnTax.getBalanceAmount() + interestOnBillAmount
							.getBalanceAmount()));

		totalAmount = roundOff(wcAmount + solAmount + scAmount + roAmount
				+ fcAmount + taxAmount + rateOfInterest + +arrearsAmount
				+ (float) interestOnTax.getBalanceAmount()
				+ (float) interestOnBillAmount.getBalanceAmount(), 0);
		logger.info("totalAmount :::::::::::::: " + totalAmount);
		roundoffValue = totalAmount
				- (wcAmount + scAmount + solAmount + roAmount + fcAmount
						+ taxAmount + rateOfInterest + arrearsAmount
						+ (float) interestOnTax.getBalanceAmount() + (float) interestOnBillAmount
							.getBalanceAmount());
		logger.info("::::::::::::::::::::::::: Round off value is ::::::::::::::: "
				+ roundoffValue);

		roundOffBillLineItemEntity.setCreditAmount(0);
		roundOffBillLineItemEntity.setDebitAmount(0);
		roundOffBillLineItemEntity.setStatus("Not Approved");
		roundOffBillLineItemEntity.setCreatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
				new Date().getTime()));
		roundOffBillLineItemEntity.setBalanceAmount(roundoffValue);
		roundOffBillLineItemEntity.setTransactionCode("WT_ROF");
		billLineItemEntities.add(roundOffBillLineItemEntity);

		if (presentMeterStaus.equalsIgnoreCase("Door Lock")
				|| presentMeterStaus.equalsIgnoreCase("Meter Not Reading")
				|| presentMeterStaus.equalsIgnoreCase("Direct Connection")
				|| presentMeterStaus.equalsIgnoreCase("Burnt")
				|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
			presentUOMReading = previousUOMReading;
			ElectricityBillEntity billEntity = electricityBillsService
					.getPreviousBill(accountID, typeOfService,
							previousBillDate, "Bill");
			if (billEntity != null) {
				avgUnits = billEntity.getAvgUnits() + uomValue;
				avgCount = billEntity.getAvgCount() + 1;
				billType = "Average";
			}
		} else {
			ElectricityBillEntity billEntity = electricityBillsService
					.getPreviousBill(accountID, typeOfService,
							previousBillDate, "Bill");
			List<ElectricityBillEntity> billEntities = new ArrayList<>();
			if (billEntity != null) {
				if (billEntity.getAvgCount() >= 1) {
					List<BigDecimal> billIds = electricityBillsService
							.getLastAvgBills(accountID, typeOfService,
									previousBillDate, "Bill",
									billEntity.getAvgCount());
					for (BigDecimal bigDecimal : billIds) {
						billEntities.add(electricityBillsService
								.find(bigDecimal.intValue()));
					}
				}

				for (ElectricityBillEntity electricityBillEntity : billEntities) {
					avgAmount = avgAmount
							+ electricityBillEntity.getBillAmount();
				}
			}
		}

		ElectricityBillEntity billEntity = saveBillUpdate(
				electricityBillEntityUpdate, accountID, totalAmount,
				previousBillDate, currentBillDate, typeOfService,
				arrearsAmount, avgUnits, avgCount, billType, avgAmount);
		saveBillLineItems(billEntity, billLineItemEntities, roundoffValue,
				arrearsAmount);
		saveBillWaterParameters(billEntity, waterRateMasterList, accountID,
				typeOfService, previousBillDate, currentBillDate, uomValue,
				billableDays, presentMeterStaus, previousMeterStaus,
				meterConstant, presentUOMReading, previousUOMReading,
				wtSlabString, meterchangeUnit);
		
		Locale locale = null;
		billController.getBillDoc(billEntity.getElBillId(), locale,1);
	}

	@SuppressWarnings("unused")
	private void saveElectricityBillParametersUpdate(
			ElectricityBillEntity electricityBillEntityUpdate,
			List<ELRateMaster> elRateMasterList, int accountID,
			String typeOfService, Date previousBillDate, Date currentBillDate,
			Float uomValue, Integer billableDays, String presentMeterStaus,
			String previousMeterStaus, Float meterConstant,
			Float presentUOMReading, Float previousUOMReading, Integer todT1,
			Integer todT2, Integer todT3, Float dgunit, Object todApplicable,
			Object dgApplicable, Float previousDgreading,
			Float presentDgReading, Float dgmeterconstant, Double avgUnits,
			Integer avgCount, String billType, float pfReading,
			float connectedLoad, String meterchangeUnit) {

		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		ElectricityBillEntity previousBillEntity = electricityBillsService
				.getPreviousBill(accountID, typeOfService, previousBillDate,
						"Bill");

		float ecAmount = 0;
		float mmcAmount = 0;
		float taxAmount = 0;
		float solarRebate = 0;
		float totalAmount = 0;
		Float roundoffValue = 0f;
		float fsaAmount = 0f;
		float rateOfInterest = 0f;
		float arrearsAmount = 0f;
		float fcAmount = 0f;
		float mdiPenaltyAmount = 0;
		float pfRebate = 0;
		float pfPenalty = 0;
		float octroiAmount = 0f;
		float dgAMount = 0f;
		Double avgAmount = 0d;
		String ecString = "";
		String fsaString = "";
		String todString = "";
		float dgFixcedAmount = 0;
		String tariffChange = null;
		/*
		 * set<ElectricityBillLineItemEntity> billLineItemEntities = new
		 * ArrayList<>();
		 */
		Set<ElectricityBillLineItemEntity> billLineItemEntities = new LinkedHashSet<>();
		ElectricityBillLineItemEntity ecBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity mmcBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity taxBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity roundOffBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity fuelBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity solarBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity fcBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity octroiBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity dgBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity dgFixcedBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity pfPenaltyBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity pfRebateBillLineItemEntity = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity mdiPenalty = new ElectricityBillLineItemEntity();

		ElectricityBillEntity billEntityForArrears = new ElectricityBillEntity();
		billEntityForArrears.setAccountId(accountID);
		billEntityForArrears.setArrearsAmount(getLastBillArrearsAmount(
				"ARREARS", accountID, typeOfService));
		billEntityForArrears.setElBillDate(new java.sql.Timestamp(
				currentBillDate.getTime()));
		billEntityForArrears.setPostType("Bill");
		billEntityForArrears.setBillDate(new java.sql.Date(currentBillDate
				.getTime()));
		String configName = "Due Days";
		String status = "Active";
		String dueDays = electricityBillsService.getBillingConfigValue(configName,status);
		int days=0;
		if(dueDays!=null)
		{
			days = Integer.parseInt(dueDays);
		}
		
		Date billDueDate = addDays(currentBillDate, days);
		billEntityForArrears.setBillDueDate(new java.sql.Date(billDueDate
				.getTime()));
		Calendar c = Calendar.getInstance();
		c.setTime(currentBillDate);
		c.set(Calendar.DAY_OF_MONTH, 1);

		java.sql.Date billMonth = new java.sql.Date(c.getTime().getTime());
		logger.info("billMonth ::::::::::::::: " + billMonth);
		billEntityForArrears.setBillMonth(billMonth);
		billEntityForArrears.setStatus("Generated");
		billEntityForArrears.setCreatedBy((String) SessionData.getUserDetails()
				.get("userID"));
		billEntityForArrears.setLastUpdatedBy((String) SessionData
				.getUserDetails().get("userID"));
		billEntityForArrears.setLastUpdatedDT(new java.sql.Timestamp(new Date()
				.getTime()));
		billEntityForArrears.setTypeOfService(typeOfService);
		billEntityForArrears.setBillNo("BILL"
				+ billParameterService.getSequencyNumber());
		logger.info("billEntity.getBillDueDate() ::::::::::::::: "
				+ billEntityForArrears.getBillDueDate());
		storeArrears("ARREARS", billEntityForArrears);
		
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		Float uomValueTest =  netMonth /uomValue;

		for (ELRateMaster elRateMaster : elRateMasterList) {
			if (elRateMaster.getRateName().equalsIgnoreCase("EC")) {
				String hariyanaTairff = elTariffMasterService.getStateName(elRateMaster.getElTariffID());
				logger.info("hariyanaTairff =========== "+hariyanaTairff);
				ELRateMaster elRateMaster1 =null;
				String category = null;
				if(hariyanaTairff!=null){
					if(hariyanaTairff.equalsIgnoreCase("Haryana")){
						logger.info("uomValueTest ============= "+uomValueTest);
						logger.info(" IF 1"+(uomValueTest>0 && uomValueTest<=800));
						if(uomValueTest>=0 && uomValueTest<=800)
						{
							logger.info(" ============ EC category 1 tariff ======================");
							 category = ResourceBundle.getBundle("utils").getString("category1");
							logger.info("category1Tariff ================= "+category);
							if(category!=null)
							{
								 elRateMaster1 = rateMasterService.getRateMasterByRateName(elRateMaster.getElTariffID(), "EC",category);
							}
						}
						else if (uomValueTest>800) {
							 category = ResourceBundle.getBundle("utils").getString("category2");
							logger.info("category1Tariff ================= "+category);
							if(category!=null)
							{
								 elRateMaster1 = rateMasterService.getRateMasterByRateName(elRateMaster.getElTariffID(), "EC",category);
							}	
						}
					}else {
						logger.info("IF not hariyan =============== ");
						elRateMaster1 = elRateMaster;
						}
				}
				
				
				
				ecBillLineItemEntity.setTransactionCode("EL_EC");
				if (todApplicable.equals("Yes")) {
					logger.info("::::::::::::::Tod is applicable::::::::::::");
					consolidatedBill = tariffCalculationService.tariffTod(
							elRateMaster, todT1, todT2, todT3,
							previousBillDate, currentBillDate);
					for (Entry<Object, Object> map : consolidatedBill
							.entrySet()) {
						if (map.getKey().equals("total")) {
							ecAmount = ecAmount + (Float) map.getValue();
						}
						if (map.getKey().equals("todslab")) {
							todString = todString + (String) map.getValue();
							logger.info("::::::::todString:::::" + todString);
						}
						logger.info("::::::::todString:::::" + todString);
					}
					consolidatedBill = calculationController.tariffAmount(
							elRateMaster, previousBillDate, currentBillDate,
							uomValue, 0.0f,category);
					for (Entry<Object, Object> map : consolidatedBill
							.entrySet()) {
						if (map.getKey().equals("slabString")) {
							ecString = ecString + (String) map.getValue();
						}
					}
				} else {
					if (presentMeterStaus.equalsIgnoreCase("Door Lock")
							|| presentMeterStaus
									.equalsIgnoreCase("Meter Not Reading")
							|| presentMeterStaus
									.equalsIgnoreCase("Direct Connection")
							|| presentMeterStaus.equalsIgnoreCase("Burnt")
							|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
						consolidatedBill = calculationController.tariffAmount(
								elRateMaster, previousBillDate,
								currentBillDate, uomValue, 0,category);
					} else {
						if (previousBillEntity != null) {
							if (previousBillEntity.getAvgCount() >= 1) {
								consolidatedBill = calculationController
										.tariffAmountAvgCount(elRateMaster,
												previousBillDate,
												currentBillDate,
												previousBillEntity
														.getAvgCount(),
												uomValue, 0.0f,category);
							} else {
								consolidatedBill = calculationController
										.tariffAmount(elRateMaster,
												previousBillDate,
												currentBillDate, uomValue, 0,category);
							}
						} else {
							consolidatedBill = calculationController
									.tariffAmount(elRateMaster,
											previousBillDate, currentBillDate,
											uomValue, 0,category);
						}
					}
					for (Entry<Object, Object> map : consolidatedBill
							.entrySet()) {

						if (map.getKey().equals("total")) {
							ecAmount = ecAmount + (Float) map.getValue();
						}
						if (map.getKey().equals("slabString")) {
							ecString = ecString + (String) map.getValue();
						}

						if (map.getKey().equals("tariffChange")) {
							tariffChange = (String) map.getValue();
						}
					}
				}
				ecBillLineItemEntity.setBalanceAmount(ecAmount);
				ecBillLineItemEntity.setCreditAmount(0);
				ecBillLineItemEntity.setDebitAmount(0);
				ecBillLineItemEntity.setStatus("Not Approved");
				ecBillLineItemEntity.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				ecBillLineItemEntity.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				ecBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(ecBillLineItemEntity);
			}

			if (elRateMaster.getRateName().equalsIgnoreCase("MMC")) {

				ServiceMastersEntity mastersEntity1 = serviceMasterService
						.getServiceMasterServicType(accountID, typeOfService);
				List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService
						.findAllByParentId(mastersEntity1.getServiceMasterId());
				float fcUomValue = 0;
				for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1) {

					if (serviceParametersEntity.getServiceParameterMaster()
							.getSpmName().equalsIgnoreCase("Sanctioned KW")
							|| serviceParametersEntity
									.getServiceParameterMaster().getSpmName()
									.equalsIgnoreCase("Sanctioned KVA")
							|| serviceParametersEntity
									.getServiceParameterMaster().getSpmName()
									.equalsIgnoreCase("Sanctioned HP")) {
						fcUomValue = Float.parseFloat(serviceParametersEntity
								.getServiceParameterValue());
					}
				}

				if (presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus
								.equalsIgnoreCase("Meter Not Reading")
						|| presentMeterStaus
								.equalsIgnoreCase("Direct Connection")
						|| presentMeterStaus.equalsIgnoreCase("Burnt")
						|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
					consolidatedBill = calculationController.tariffAmount(
							elRateMaster, previousBillDate, currentBillDate,
							fcUomValue * netMonth, 0,elRateMaster.getRateNameCategory());
				} else {
					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCount(elRateMaster,
											previousBillDate, currentBillDate,
											previousBillEntity.getAvgCount(),
											fcUomValue * netMonth, 0.0f,elRateMaster.getRateNameCategory());
						} else {
							consolidatedBill = calculationController
									.tariffAmount(elRateMaster,
											previousBillDate, currentBillDate,
											fcUomValue * netMonth, 0,elRateMaster.getRateNameCategory());
						}
					} else {
						consolidatedBill = calculationController.tariffAmount(
								elRateMaster, previousBillDate,
								currentBillDate, fcUomValue * netMonth, 0,elRateMaster.getRateNameCategory());
					}
				}
				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						mmcAmount = mmcAmount + (Float) map.getValue();
						logger.info("fcAmount :::::::::: " + fcAmount);
					}
				}
				mmcBillLineItemEntity.setTransactionCode("EL_MMC");
				mmcBillLineItemEntity.setBalanceAmount(mmcAmount);
				mmcBillLineItemEntity.setCreditAmount(0);
				mmcBillLineItemEntity.setDebitAmount(0);
				mmcBillLineItemEntity.setStatus("Not Approved");
				mmcBillLineItemEntity.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				mmcBillLineItemEntity.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				mmcBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(mmcBillLineItemEntity);
			}

			if (elRateMaster.getRateName().equalsIgnoreCase("FC")) {
				ServiceMastersEntity mastersEntity1 = serviceMasterService
						.getServiceMasterServicType(accountID, typeOfService);
				List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService
						.findAllByParentId(mastersEntity1.getServiceMasterId());
				float fcUomValue = 0;
				for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1) {

					if (serviceParametersEntity.getServiceParameterMaster()
							.getSpmName().equalsIgnoreCase("Connected KW")) {
						fcUomValue = Float.parseFloat(serviceParametersEntity
								.getServiceParameterValue());
					}
				}

				fcBillLineItemEntity.setTransactionCode("EL_FC");
				if (presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus
								.equalsIgnoreCase("Meter Not Reading")
						|| presentMeterStaus
								.equalsIgnoreCase("Direct Connection")
						|| presentMeterStaus.equalsIgnoreCase("Burnt")
						|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
					consolidatedBill = calculationController.tariffAmount(
							elRateMaster, previousBillDate, currentBillDate,
							fcUomValue, 0,elRateMaster.getRateNameCategory());
				} else {
					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCount(elRateMaster,
											previousBillDate, currentBillDate,
											previousBillEntity.getAvgCount(),
											fcUomValue, 0.0f,elRateMaster.getRateNameCategory());
						} else {
							consolidatedBill = calculationController
									.tariffAmount(elRateMaster,
											previousBillDate, currentBillDate,
											fcUomValue, 0,elRateMaster.getRateNameCategory());
						}
					} else {
						consolidatedBill = calculationController.tariffAmount(
								elRateMaster, previousBillDate,
								currentBillDate, fcUomValue, 0,elRateMaster.getRateNameCategory());
					}
				}
				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						fcAmount = fcAmount + (Float) map.getValue();
						logger.info("fcAmount :::::::::: " + fcAmount);
					}
				}
				fcBillLineItemEntity.setBalanceAmount(fcAmount);
				fcBillLineItemEntity.setCreditAmount(0);
				fcBillLineItemEntity.setDebitAmount(0);
				fcBillLineItemEntity.setStatus("Not Approved");
				fcBillLineItemEntity.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				fcBillLineItemEntity.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				fcBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(fcBillLineItemEntity);
			}
			if (elRateMaster.getRateName().equalsIgnoreCase("Solar Rebate")) {
				ServiceMastersEntity mastersEntity = serviceMasterService
						.getServiceMasterServicType(accountID, typeOfService);
				List<ServiceParametersEntity> serviceParametersEntities = serviceParameterService
						.findAllByParentId(mastersEntity.getServiceMasterId());

				for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities) {

					if (serviceParametersEntity.getServiceParameterMaster()
							.getSpmName().equalsIgnoreCase("Solar Rebate")) {
						solarBillLineItemEntity
								.setTransactionCode("EL_Rebate_EC");
						String solarrebateType = getreabateType(elRateMaster
								.getElrmid());
						logger.info("::::::::::::solarrebateType:::::::::"
								+ solarrebateType);
						if (solarrebateType.trim().equalsIgnoreCase(
								"Not applicable")) {

							List<ELRateSlabs> elRateSlabs = elRateSlabService
									.findRateSlabByID(elRateMaster.getElrmid());

							for (ELRateSlabs elRateSlabs2 : elRateSlabs) {
								logger.info("elRateSlabs.getRate() ------------------ "
										+ elRateSlabs2.getRate());
								solarRebate = elRateSlabs2.getRate();
							}
						} else {
							for (Entry<Object, Object> map : consolidatedBill
									.entrySet()) {
								consolidatedBill = calculationController
										.tariffAmount(elRateMaster,
												previousBillDate,
												currentBillDate, ecAmount, 0,elRateMaster.getRateNameCategory());
								if (map.getKey().equals("total")) {
									solarRebate = solarRebate
											+ (Float) map.getValue();
									logger.info("solarRebate :::::::::: "
											+ solarRebate);
								}
							}
						}
						consolidatedBill = calculationController.tariffAmount(
								elRateMaster, previousBillDate,
								currentBillDate, ecAmount, 0,elRateMaster.getRateNameCategory());

						solarBillLineItemEntity.setBalanceAmount(solarRebate);

						solarBillLineItemEntity.setCreditAmount(0);
						solarBillLineItemEntity.setDebitAmount(0);
						solarBillLineItemEntity.setStatus("Not Approved");
						solarBillLineItemEntity
								.setCreatedBy((String) SessionData
										.getUserDetails().get("userID"));
						solarBillLineItemEntity
								.setLastUpdatedBy((String) SessionData
										.getUserDetails().get("userID"));
						solarBillLineItemEntity
								.setLastUpdatedDT(new java.sql.Timestamp(
										new Date().getTime()));
						billLineItemEntities.add(solarBillLineItemEntity);

					} else {
						logger.info("::::::::::::::Solar Rebate Not Applicable::::::::::::::");
					}
				}
			}

			if (elRateMaster.getRateName().equalsIgnoreCase("Octroi")) {
				octroiBillLineItemEntity.setTransactionCode("EL_OCTROI");
				consolidatedBill = calculationController.tariffAmount(
						elRateMaster, previousBillDate, currentBillDate,
						ecAmount, 0,elRateMaster.getRateNameCategory());
				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						octroiAmount = octroiAmount + (Float) map.getValue();
					}
				}
				octroiBillLineItemEntity.setBalanceAmount(octroiAmount);
				octroiBillLineItemEntity.setBalanceAmount(octroiAmount);
				octroiBillLineItemEntity.setCreditAmount(0);
				octroiBillLineItemEntity.setDebitAmount(0);
				octroiBillLineItemEntity.setStatus("Not Approved");
				octroiBillLineItemEntity.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				octroiBillLineItemEntity.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				octroiBillLineItemEntity
						.setLastUpdatedDT(new java.sql.Timestamp(new Date()
								.getTime()));
				billLineItemEntities.add(octroiBillLineItemEntity);
			}

			if (elRateMaster.getRateName().equalsIgnoreCase("FSA")) {
				
				String hariyanaTairff = elTariffMasterService.getStateName(elRateMaster.getElTariffID());
				logger.info("hariyanaTairff =========== "+hariyanaTairff);
				ELRateMaster elRateMaster1 = null;
				String category = null;
				if(hariyanaTairff!=null){
					if(hariyanaTairff.equalsIgnoreCase("Haryana")){
						if(uomValueTest>=0 && uomValueTest<=100)
						{
							logger.info(" ============ FSA category 1 tariff ======================");
							 category = ResourceBundle.getBundle("utils").getString("category1");
							logger.info("category1Tariff ================= "+category);
							if(category!=null)
							{
								 elRateMaster1 = rateMasterService.getRateMasterByRateName(elRateMaster.getElTariffID(), "FSA",category);
							}
						}
						else if (uomValueTest>100 && uomValueTest<=800) {
							logger.info(" ============ FSA category 2 tariff ======================");
							 category = ResourceBundle.getBundle("utils").getString("category2");
							logger.info("category1Tariff ================= "+category);
							if(category!=null)
							{
								elRateMaster1 = rateMasterService.getRateMasterByRateName(elRateMaster.getElTariffID(), "FSA",category);
							}	
						}
						else if (uomValueTest>800) {
							logger.info(" ============ FSA category 3 tariff ======================");
							 category = ResourceBundle.getBundle("utils").getString("category3");
							logger.info("category1Tariff ================= "+category);
							if(category!=null)
							{
								 elRateMaster1 = rateMasterService.getRateMasterByRateName(elRateMaster.getElTariffID(), "FSA",category);
							}	
						}
					}else {
						 elRateMaster1 = elRateMaster;
					}
				}				
				fuelBillLineItemEntity.setTransactionCode("EL_FSA");
				if (presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus
								.equalsIgnoreCase("Meter Not Reading")
						|| presentMeterStaus
								.equalsIgnoreCase("Direct Connection")
						|| presentMeterStaus.equalsIgnoreCase("Burnt")
						|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
					consolidatedBill = calculationController.tariffAmount(
							elRateMaster, previousBillDate, currentBillDate,
							uomValue, 0,category);
				} else {
					if (previousBillEntity != null) {
						if (previousBillEntity.getAvgCount() >= 1) {
							consolidatedBill = calculationController
									.tariffAmountAvgCount(elRateMaster,
											previousBillDate, currentBillDate,
											previousBillEntity.getAvgCount(),
											uomValue, 0.0f,category);
						} else {
							consolidatedBill = calculationController
									.tariffAmount(elRateMaster,
											previousBillDate, currentBillDate,
											uomValue, 0,category);
						}
					} else {
						consolidatedBill = calculationController.tariffAmount(
								elRateMaster, previousBillDate,
								currentBillDate, uomValue, 0,category);
					}
				}
				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						fsaAmount = fsaAmount + (Float) map.getValue();
					}
					if (map.getKey().equals("slabString")) {
						fsaString = fsaString + (String) map.getValue();
					}
				}
				fuelBillLineItemEntity.setBalanceAmount(fsaAmount);
				fuelBillLineItemEntity.setCreditAmount(0);
				fuelBillLineItemEntity.setDebitAmount(0);
				fuelBillLineItemEntity.setStatus("Not Approved");
				fuelBillLineItemEntity.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				fuelBillLineItemEntity.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				fuelBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(fuelBillLineItemEntity);
			}

			if (dgApplicable.equals("Yes")) {
				if (elRateMaster.getRateName().equalsIgnoreCase("DG")) {
					dgBillLineItemEntity.setTransactionCode("EL_DG");
					consolidatedBill = calculationController.tariffAmount(
							elRateMaster, previousBillDate, currentBillDate,
							dgunit, 0,elRateMaster.getRateNameCategory());
					for (Entry<Object, Object> map : consolidatedBill
							.entrySet()) {
						if (map.getKey().equals("total")) {
							dgAMount = dgAMount + (Float) map.getValue();
							logger.info("::::::::dgAMount::::::::::" + dgAMount);
						}
					}
					dgBillLineItemEntity.setBalanceAmount(dgAMount);
					dgBillLineItemEntity.setCreditAmount(0);
					dgBillLineItemEntity.setDebitAmount(0);
					dgBillLineItemEntity.setStatus("Not Approved");
					dgBillLineItemEntity.setCreatedBy((String) SessionData
							.getUserDetails().get("userID"));
					dgBillLineItemEntity.setLastUpdatedBy((String) SessionData
							.getUserDetails().get("userID"));
					dgBillLineItemEntity
							.setLastUpdatedDT(new java.sql.Timestamp(new Date()
									.getTime()));
					billLineItemEntities.add(dgBillLineItemEntity);
				}
				if (elRateMaster.getRateName().equalsIgnoreCase(
						"DG Fixed Charge")) {
					List<ELRateSlabs> elRateSlabs = elRateSlabService
							.findRateSlabByID(elRateMaster.getElrmid());
					float dgfixecharge = 0;
					consolidatedBill = calculationController.tariffAmount(
							elRateMaster, previousBillDate, currentBillDate,
							1.f, 0,elRateMaster.getRateNameCategory());
					for (Entry<Object, Object> map : consolidatedBill
							.entrySet()) {
						if (map.getKey().equals("total")) {
							dgFixcedAmount = dgFixcedAmount
									+ (Float) map.getValue();
							logger.info("::::::::dgFixcedAmount::::::::::"
									+ dgFixcedAmount);
						}
					}

					dgFixcedBillLineItemEntity.setTransactionCode("EL_DG_FC");
					dgFixcedBillLineItemEntity.setBalanceAmount(dgFixcedAmount);
					dgFixcedBillLineItemEntity.setCreditAmount(0);
					dgFixcedBillLineItemEntity.setDebitAmount(0);
					dgFixcedBillLineItemEntity.setStatus("Not Approved");
					dgFixcedBillLineItemEntity
							.setCreatedBy((String) SessionData.getUserDetails()
									.get("userID"));
					dgFixcedBillLineItemEntity
							.setLastUpdatedBy((String) SessionData
									.getUserDetails().get("userID"));
					dgFixcedBillLineItemEntity
							.setLastUpdatedDT(new java.sql.Timestamp(new Date()
									.getTime()));
					billLineItemEntities.add(dgFixcedBillLineItemEntity);
				}
			}

			String typesofmeter = tariffCalculationService.getMeterType(
					accountID, typeOfService);
			logger.info("::::::::typesofmeter::::::::::" + typesofmeter);
			if (typesofmeter != null) {
				if (typesofmeter.trim().equalsIgnoreCase("Trivector Meter")) {
					if (elRateMaster.getRateName().equalsIgnoreCase(
							"PF Penalty")) {
						logger.info("--------- PF penalty applicable ---------------");

						if (presentMeterStaus.equalsIgnoreCase("Door Lock")
								|| presentMeterStaus
										.equalsIgnoreCase("Meter Not Reading")
								|| presentMeterStaus
										.equalsIgnoreCase("Direct Connection")
								|| presentMeterStaus.equalsIgnoreCase("Burnt")
								|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
							consolidatedBill = calculationController
									.tariffAmount(elRateMaster,
											previousBillDate, currentBillDate,
											uomValue, pfReading,elRateMaster.getRateNameCategory());
						} else {
							if (previousBillEntity != null) {
								if (previousBillEntity.getAvgCount() >= 1) {
									consolidatedBill = calculationController
											.tariffAmountAvgCount(elRateMaster,
													previousBillDate,
													currentBillDate,
													previousBillEntity
															.getAvgCount(),
													uomValue, pfReading,elRateMaster.getRateNameCategory());
								} else {
									consolidatedBill = calculationController
											.tariffAmount(elRateMaster,
													previousBillDate,
													currentBillDate, uomValue,
													pfReading,elRateMaster.getRateNameCategory());
								}
							} else {
								consolidatedBill = calculationController
										.tariffAmount(elRateMaster,
												previousBillDate,
												currentBillDate, uomValue,
												pfReading,elRateMaster.getRateNameCategory());
							}
						}

						for (Entry<Object, Object> map : consolidatedBill
								.entrySet()) {
							if (map.getKey().equals("rebate")) {
								pfRebate = pfRebate + (Float) map.getValue();
							}

							if (map.getKey().equals("penalty")) {
								pfPenalty = pfPenalty + (Float) map.getValue();
							}
						}

						if (pfRebate != 0) {
							pfRebateBillLineItemEntity
									.setTransactionCode("EL_PF_R");
							pfRebateBillLineItemEntity
									.setBalanceAmount(-pfRebate);
							pfRebateBillLineItemEntity.setCreditAmount(0);
							pfRebateBillLineItemEntity.setDebitAmount(0);
							pfRebateBillLineItemEntity
									.setStatus("Not Approved");
							pfRebateBillLineItemEntity
									.setCreatedBy((String) SessionData
											.getUserDetails().get("userID"));
							pfRebateBillLineItemEntity
									.setLastUpdatedBy((String) SessionData
											.getUserDetails().get("userID"));
							pfRebateBillLineItemEntity
									.setLastUpdatedDT(new java.sql.Timestamp(
											new Date().getTime()));
							billLineItemEntities
									.add(pfRebateBillLineItemEntity);
						}

						if (pfPenalty != 0) {
							pfPenaltyBillLineItemEntity
									.setTransactionCode("EL_PF_P");
							pfPenaltyBillLineItemEntity
									.setBalanceAmount(pfPenalty);
							pfPenaltyBillLineItemEntity.setCreditAmount(0);
							pfPenaltyBillLineItemEntity.setDebitAmount(0);
							pfPenaltyBillLineItemEntity
									.setStatus("Not Approved");
							pfPenaltyBillLineItemEntity
									.setCreatedBy((String) SessionData
											.getUserDetails().get("userID"));
							pfPenaltyBillLineItemEntity
									.setLastUpdatedBy((String) SessionData
											.getUserDetails().get("userID"));
							pfPenaltyBillLineItemEntity
									.setLastUpdatedDT(new java.sql.Timestamp(
											new Date().getTime()));
							billLineItemEntities
									.add(pfPenaltyBillLineItemEntity);
						}
					}

					if (elRateMaster.getRateName().equalsIgnoreCase("MDI")) {
						logger.info("-------------- MDI Penalty applicable ----------------- ");
						ServiceMastersEntity mastersEntity1 = serviceMasterService
								.getServiceMasterServicType(accountID,
										typeOfService);
						List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService
								.findAllByParentId(mastersEntity1
										.getServiceMasterId());
						float mdiValue = 0;
						for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1) {

							if (serviceParametersEntity
									.getServiceParameterMaster().getSpmName()
									.equalsIgnoreCase("Sanctioned KW")
									|| serviceParametersEntity
											.getServiceParameterMaster()
											.getSpmName()
											.equalsIgnoreCase("Sanctioned KVA")
									|| serviceParametersEntity
											.getServiceParameterMaster()
											.getSpmName()
											.equalsIgnoreCase("Sanctioned HP")) {
								mdiValue = Float
										.parseFloat(serviceParametersEntity
												.getServiceParameterValue());
							}
						}

						if (presentMeterStaus.equalsIgnoreCase("Door Lock")
								|| presentMeterStaus
										.equalsIgnoreCase("Meter Not Reading")
								|| presentMeterStaus
										.equalsIgnoreCase("Direct Connection")
								|| presentMeterStaus.equalsIgnoreCase("Burnt")
								|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
							consolidatedBill = calculationController
									.tariffAmount(elRateMaster,
											previousBillDate, currentBillDate,
											mdiValue, connectedLoad,elRateMaster.getRateNameCategory());
						} else {
							if (previousBillEntity != null) {
								if (previousBillEntity.getAvgCount() >= 1) {
									consolidatedBill = calculationController
											.tariffAmountAvgCount(elRateMaster,
													previousBillDate,
													currentBillDate,
													previousBillEntity
															.getAvgCount(),
													mdiValue, connectedLoad,elRateMaster.getRateNameCategory());
								} else {
									consolidatedBill = calculationController
											.tariffAmount(elRateMaster,
													previousBillDate,
													currentBillDate, mdiValue,
													connectedLoad,elRateMaster.getRateNameCategory());
								}
							} else {
								consolidatedBill = calculationController
										.tariffAmount(elRateMaster,
												previousBillDate,
												currentBillDate, mdiValue,
												connectedLoad,elRateMaster.getRateNameCategory());
							}
						}
						for (Entry<Object, Object> map : consolidatedBill
								.entrySet()) {
							if (map.getKey().equals("total")) {
								mdiPenaltyAmount = mdiPenaltyAmount
										+ (Float) map.getValue();
								logger.info("mdi penalty ::::::::::--------------------- "
										+ mdiPenaltyAmount);
							}
						}
						mdiPenalty.setBalanceAmount(mdiPenaltyAmount);
						mdiPenalty.setTransactionCode("EL_MDI");
						mdiPenalty.setCreditAmount(0);
						mdiPenalty.setDebitAmount(0);
						mdiPenalty.setStatus("Not Approved");
						mdiPenalty.setCreatedBy((String) SessionData
								.getUserDetails().get("userID"));
						mdiPenalty.setLastUpdatedBy((String) SessionData
								.getUserDetails().get("userID"));
						mdiPenalty.setLastUpdatedDT(new java.sql.Timestamp(
								new Date().getTime()));
						billLineItemEntities.add(mdiPenalty);
					}
				}
			}

		}

		float interestOnArrearsAmount = 0;
		float interestOnTaxAmount = 0;
		ElectricityBillLineItemEntity interestOnTax = new ElectricityBillLineItemEntity();
		ElectricityBillLineItemEntity interestOnBillAmount = new ElectricityBillLineItemEntity();
	//	consolidatedBill = interestCalculation(accountID, typeOfService,previousBillDate, elRateMasterList, currentBillDate);
		for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
			if (map.getKey().equals("interestOnArrearsAmount")) {
				interestOnArrearsAmount = interestOnArrearsAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnArrearsAmount);
				interestOnBillAmount.setTransactionCode("EL_INTEREST");
				interestOnBillAmount.setBalanceAmount(interestOnArrearsAmount);
				interestOnBillAmount.setCreditAmount(0);
				interestOnBillAmount.setDebitAmount(0);
				interestOnBillAmount.setStatus("Not Approved");
				interestOnBillAmount.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnBillAmount.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnBillAmount);
			}
			if (map.getKey().equals("interestOnTaxAmount")) {
				interestOnTaxAmount = interestOnTaxAmount
						+ (Float) map.getValue();
				logger.info("------ " + interestOnTaxAmount);
				interestOnTax.setTransactionCode("EL_TAX_INTEREST");
				interestOnTax.setBalanceAmount(interestOnTaxAmount);
				interestOnTax.setCreditAmount(0);
				interestOnTax.setDebitAmount(0);
				interestOnTax.setStatus("Not Approved");
				interestOnTax.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				interestOnTax.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(interestOnTax);
			}
		}

		float totalExcludeTax = (float) (ecAmount + dgAMount + fsaAmount + roundoffValue);
		logger.info(":::::::::::::: Total Amount Excludeing tax ::::::::::::: "
				+ totalExcludeTax);

		for (ELRateMaster elRateMaster : elRateMasterList) {
			if (elRateMaster.getRateName().equalsIgnoreCase("Tax")) {
				taxBillLineItemEntity.setTransactionCode("EL_TAX");
				consolidatedBill = calculationController.tariffAmount(
						elRateMaster, previousBillDate, currentBillDate,
						totalExcludeTax, 0,elRateMaster.getRateNameCategory());
				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						taxAmount = taxAmount + (Float) map.getValue();
						logger.info("=========== Tax for toatal amount  "
								+ totalExcludeTax + " is " + taxAmount);
					}
				}
				taxBillLineItemEntity.setBalanceAmount(taxAmount);
				taxBillLineItemEntity.setCreditAmount(0);
				taxBillLineItemEntity.setDebitAmount(0);
				taxBillLineItemEntity.setStatus("Not Approved");
				taxBillLineItemEntity.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				taxBillLineItemEntity.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				taxBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				billLineItemEntities.add(taxBillLineItemEntity);
			}
		}

		logger.info("ecAmount " + ecAmount);
		logger.info("mmcAmount " + mmcAmount);
		logger.info("taxAmount " + taxAmount);
		logger.info("rateOfInterest " + rateOfInterest);
		logger.info("fcAmount " + fcAmount);
		logger.info("octroiAmount " + octroiAmount);
		logger.info("arrearsAmount " + arrearsAmount);
		logger.info("dgAMount " + dgAMount);
		logger.info("solarRebate " + solarRebate);
		logger.info("interestOnTaxAmount " + interestOnTax.getBalanceAmount());
		logger.info("fsaAmount " + fsaAmount);
		logger.info("interestOnArrears "
				+ interestOnBillAmount.getBalanceAmount());
		logger.info("pfPenalty " + pfPenalty);
		logger.info("pfRebate " + pfRebate);
		logger.info("mdiPenaltyAmount " + mdiPenaltyAmount);
		logger.info("Total without round off "
				+ (ecAmount + mdiPenaltyAmount + mmcAmount + pfPenalty
						- pfRebate + avgAmount.floatValue() + taxAmount
						+ rateOfInterest + fcAmount + octroiAmount
						+ arrearsAmount + dgAMount - solarRebate
						+ interestOnTax.getBalanceAmount() + fsaAmount + interestOnBillAmount
							.getBalanceAmount()));

		totalAmount = roundOff(
				ecAmount + mmcAmount + taxAmount + mdiPenaltyAmount + pfPenalty
						- pfRebate + rateOfInterest + fcAmount + dgFixcedAmount
						+ octroiAmount + arrearsAmount + dgAMount - solarRebate
						+ (float) interestOnTax.getBalanceAmount()
						+ (float) interestOnBillAmount.getBalanceAmount()
						+ fsaAmount, 0);
		logger.info("totalAmount :::::::::::::: " + totalAmount);
		roundoffValue = totalAmount
				- (ecAmount + pfPenalty + mmcAmount + mdiPenaltyAmount
						- pfRebate + taxAmount + rateOfInterest + fcAmount
						+ fsaAmount + dgFixcedAmount + octroiAmount
						+ arrearsAmount + dgAMount - solarRebate
						+ (float) interestOnTax.getBalanceAmount() + (float) interestOnBillAmount
							.getBalanceAmount());
		logger.info("::::::::::::::::::::::::: Round off value is ::::::::::::::: "
				+ roundoffValue);

		roundOffBillLineItemEntity.setCreditAmount(0);
		roundOffBillLineItemEntity.setDebitAmount(0);
		roundOffBillLineItemEntity.setStatus("Not Approved");
		roundOffBillLineItemEntity.setCreatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedBy((String) SessionData
				.getUserDetails().get("userID"));
		roundOffBillLineItemEntity.setLastUpdatedDT(new java.sql.Timestamp(
				new Date().getTime()));
		roundOffBillLineItemEntity.setBalanceAmount(roundoffValue);
		roundOffBillLineItemEntity.setTransactionCode("EL_ROF");
		billLineItemEntities.add(roundOffBillLineItemEntity);

		if (presentMeterStaus.equalsIgnoreCase("Door Lock")
				|| presentMeterStaus.equalsIgnoreCase("Meter Not Reading")
				|| presentMeterStaus.equalsIgnoreCase("Direct Connection")
				|| presentMeterStaus.equalsIgnoreCase("Burnt")
				|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
			presentUOMReading = previousUOMReading;
			ElectricityBillEntity billEntity = electricityBillsService
					.getPreviousBill(accountID, typeOfService,
							previousBillDate, "Bill");
			if (billEntity != null) {
				avgUnits = billEntity.getAvgUnits() + uomValue;
				avgCount = billEntity.getAvgCount() + 1;
				billType = "Average";
			}
		} else {
			ElectricityBillEntity billEntity = electricityBillsService
					.getPreviousBill(accountID, typeOfService,
							previousBillDate, "Bill");
			List<ElectricityBillEntity> billEntities = new ArrayList<>();
			if (billEntity != null) {
				if (billEntity.getAvgCount() >= 1) {
					List<BigDecimal> billIds = electricityBillsService
							.getLastAvgBills(accountID, typeOfService,
									previousBillDate, "Bill",
									billEntity.getAvgCount());
					for (BigDecimal bigDecimal : billIds) {
						billEntities.add(electricityBillsService
								.find(bigDecimal.intValue()));
					}
				}

				for (ElectricityBillEntity electricityBillEntity : billEntities) {
					logger.info("------------- electricityBillEntity.getBillAmount() "
							+ electricityBillEntity.getBillAmount());
					avgAmount = avgAmount
							+ electricityBillEntity.getBillAmount();
					logger.info("--------------- avgAmount " + avgAmount);
				}
			}
		}

		ElectricityBillEntity billEntity = saveBillUpdate(
				electricityBillEntityUpdate, accountID, totalAmount,
				previousBillDate, currentBillDate, typeOfService,
				arrearsAmount, avgUnits, avgCount, billType, avgAmount);

		saveBillLineItems(billEntity, billLineItemEntities, roundoffValue,
				arrearsAmount);
		saveBillParameters(billEntity, elRateMasterList, accountID,
				typeOfService, previousBillDate, currentBillDate, uomValue,
				billableDays, presentMeterStaus, previousMeterStaus,
				meterConstant, presentUOMReading, previousUOMReading,
				previousBillEntity, ecString, fsaString, previousDgreading,
				presentDgReading, dgmeterconstant, dgApplicable, dgunit,
				todString, todApplicable, pfReading, connectedLoad,
				meterchangeUnit, tariffChange);
		Locale locale = null;
		billController.getBillDoc(billEntity.getElBillId(), locale,1);
	}

	private ElectricityBillEntity saveBillUpdate(
			ElectricityBillEntity billEntity, int accountID, float totalAmount,
			Date previousBillDate, Date currentBillDate, String typeOfService,
			float arrearsAmount, Double avgUnits, Integer avgCount,
			String billType, Double avgAmount) {

		// ElectricityBillEntity billEntity = new ElectricityBillEntity();
		billEntity.setAccountId(accountID);
		billEntity.setArrearsAmount(getLastBillArrearsAmount("ARREARS",
				accountID, typeOfService));
		if (totalAmount >= 0) {
			billEntity.setBillAmount((double) totalAmount);
		} else {
			billEntity.setBillAmount(0d);
		}

		if (avgAmount >= 0) {
			billEntity.setAvgAmount(-avgAmount);
		} else {
			billEntity.setAvgAmount(0d);
		}
		Double netAmount = (billEntity.getArrearsAmount() + totalAmount + billEntity.getAvgAmount());
		billEntity.setNetAmount(netAmount);
		/*if (netAmount >= 0) {
			billEntity.setNetAmount(netAmount);
		} else {
			billEntity.setNetAmount(0d);
		}*/

		billEntity.setElBillDate(new Timestamp(new java.util.Date().getTime()));
		billEntity.setPostType("Bill");
		billEntity.setFromDate(new java.sql.Date(previousBillDate.getTime()));
		billEntity.setBillDate(new java.sql.Date(currentBillDate.getTime()));
		String configName = "Due Days";
		String status = "Active";
		String dueDays = electricityBillsService.getBillingConfigValue(configName,status);
		int days=0;
		if(dueDays!=null)
		{
			days = Integer.parseInt(dueDays);
		}
		
		Date billDueDate = addDays(currentBillDate, days);
		billEntity.setBillDueDate(new java.sql.Date(billDueDate.getTime()));
		Calendar c = Calendar.getInstance();
		c.setTime(currentBillDate);
		c.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date billMonth = new java.sql.Date(c.getTime().getTime());
		logger.info("billMonth ::::::::::::::: " + billMonth);
		billEntity.setBillMonth(billMonth);
		billEntity.setStatus("Generated");
		billEntity.setCreatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		billEntity.setLastUpdatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		billEntity
				.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
		billEntity.setTypeOfService(typeOfService);
		// billEntity.setBillNo("BILL" +
		// billParameterService.getSequencyNumber());
		logger.info("billEntity.getBillDueDate() ::::::::::::::: "
				+ billEntity.getBillDueDate());
		billEntity.setAvgCount(avgCount);
		billEntity.setAvgUnits(avgUnits);
		billEntity.setBillType(billType);
		electricityBillsService.update(billEntity);
		return billEntity;

	}

	public float otherServicesTaxAmount(
			CommonServicesRateMaster commonServicesRateMaster,
			java.sql.Date fromDate, java.sql.Date billDate,
			float totalAmountForTax) {
		return calculationController
				.otherServicesTaxAmount(commonServicesRateMaster, fromDate,
						billDate, totalAmountForTax);
	}

	@SuppressWarnings("unused")
	public float getAverageUnit(Date currentBillDate, String typeOfService,
			int accountId, int serviceID) {

		Float uomValue = 0.0f;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(currentBillDate);
		cal1.add(Calendar.YEAR, -1);
		Date lastYear = cal1.getTime();
		List<String> serviceParameterMasterValueList = serviceMasterService
				.getServiceParameterValueBasedOnMasterId(serviceID);
		if (serviceParameterMasterValueList.isEmpty()) {
			uomValue = getAvgUnit(currentBillDate, typeOfService, accountId);

		} else {
			logger.info("--------------- Average units present in service master -----------------");
			uomValue = Float.parseFloat(serviceParameterMasterValueList.get(0));
		}

		return uomValue;
	}

	@RequestMapping(value = "/bill/getPreviousCAMDAte/{accountId}", method = RequestMethod.POST)
	public @ResponseBody
	Date getPreviousCAMDAte(@PathVariable int accountId) {

		int lastBillId = camConsolidationService.getLastBillObj(accountId,
				"CAM", "Bill");
		java.sql.Date camPreviousDate;
		if (lastBillId != 0) {
			camPreviousDate = electricityBillsService.find(lastBillId)
					.getBillDate();
		} else {
			int serviceMasterId = camConsolidationService.getServiceMasterObj(
					accountId, "CAM");
			camPreviousDate = serviceMasterService.find(serviceMasterId)
					.getServiceStartDate();
		}
		return camPreviousDate;
	}

	@RequestMapping(value = "/bill/postToTally", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String postToTally(HttpServletRequest req,
			HttpServletResponse response, @RequestBody String body)
			throws Exception {
		logger.info("------------------------- Post bill to tally  ------------------------");
		int billId = 0;
		List<Map<String, Object>> mapsList = new ArrayList<>();

		if (req.getParameter("billId") != null) {
			billId = Integer.parseInt(req.getParameter("billId"));
		}
		ElectricityBillEntity billEntity = electricityBillsService.find(billId);
		if (billEntity != null) {
			System.out.println("total amount =========== "+billEntity.getBillAmount());
			System.out.println("Arrears amount =========== "+billEntity.getArrearsAmount());
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("totalAmount", billEntity.getBillAmount());
			map.put("arrearsAmount", billEntity.getArrearsAmount());
			mapsList.add(map);

		}
		Date basicDateOfInvoice = billEntity.getBillDate();
		String billNumber = billEntity.getBillNo();
		String billDate = new SimpleDateFormat("YYYYMMdd").format(billEntity
				.getBillDate());
		String serviceType = billEntity.getTypeOfService();
		String accountNo = billEntity.getAccountObj().getAccountNo();

		String salesLedger = null  ;
		String arrearsLedge = null;
		if (serviceType.equalsIgnoreCase("Electricity")) {
			salesLedger = "Electricity Collection";
			arrearsLedge= "Electricity Arrears";
			
			
		}
	
		
		String propertyNumber=null;
		String firstName=null;
		String lastName=null;
		String personAccountNo=null;
		
		List<Map<String, Object>>  propertyMap=tariffCalculationService.getTallyDetailData(billId);
		List<List<String>>  acccountDetails=this.getAccountDetails(propertyMap);
		for (int i = 0; i < acccountDetails.size(); i++) {
			personAccountNo=acccountDetails.get(i).get(0);
			firstName=acccountDetails.get(i).get(1);
			lastName=acccountDetails.get(i).get(2);
			propertyNumber=acccountDetails.get(i).get(3);
		}
		
		if (serviceType.equalsIgnoreCase("CAM")) {
			salesLedger = "Maintenance Charges (CAM)";
			
		List<Map<String,Object>> lineItemList=electricityBillLineItemService.findAllDetailsForCamPosting(billId);
		System.out.println("===============lineItemList"+lineItemList);
		String str= tallyInvoicePostService.responsePostInvoiceForCAM(mapsList,billNumber,billDate,salesLedger,billId,
				                                                basicDateOfInvoice,serviceType, accountNo,arrearsLedge,lineItemList,propertyNumber);
		System.out.println("str : "+str);
		if (str.equalsIgnoreCase("CAM Successfully Posted To Tally")) {
			transactionMasterService.setTallyStatusUpdate(billId);
		}
		return str;
		
		
	//	return "Error while posting";
     /*   String str=null;
		String str = tallyInvoicePostService.reponsePostInvoiceToTally(
				mapsList, billNumber, billDate, salesLedger, billId,
				basicDateOfInvoice, serviceType, accountNo,arrearsLedge);
		if (str.equalsIgnoreCase("Bill Successfully Posted To Tally")) {
			transactionMasterService.setTallyStatusUpdate(billId);
		}
          try
          {
		return new StringBuilder(propertyNumber).toString()+"("+new StringBuilder(firstName).toString()+" "+new StringBuilder(lastName).toString()+"_"+new StringBuilder(personAccountNo).toString()+") "+str;
	}
          catch(Exception e)
          {
        	  return str;
          }*/
	}
		return "Error while posting";
	
	
	}		
	@RequestMapping(value="/electricityBills/postAllBillToTally",method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public String postAllToTally(HttpServletRequest req,
			HttpServletResponse response, @RequestBody String body)
			throws Exception {
		String serviceType=req.getParameter("serviceType");
		String presentdate=req.getParameter("presentdate");
		logger.info("service type===="+serviceType);
		logger.info("presentdate===="+presentdate);
		Date monthDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH)
		.parse(presentdate);
		HttpSession session=req.getSession(false);
		List<Map<String,Object>> result=tallyAllInvoicePostServiceImpl.post(session,serviceType,monthDate);
				
		int count=0;
   	 for (Map<String, Object> map : result) {
	    	
   	  for (Map.Entry<String, Object> entry : map.entrySet()) {
   	        if(entry.getKey().equalsIgnoreCase("success"))
   	        {
   	        	count++;
   	        Object value = entry.getValue();
   	        System.out.println( "=========value=="+value);
   	        
   	        
   	        }
   	        if(entry.getKey().equalsIgnoreCase("server"))
   	        {
   	        	return "Please start 'TallyERP9' server and load company";
   	        }
   	        
   	      
   	    
   	  }
   	 }
   	
	if(count>0)
	{
		return count+ "Bill Successfully Posted To Tally";
	}
	else
	{
	   return "no! Bill  Posted To Tally";
	}   
		

		
		
	}
	
	public List<List<String>> getAccountDetails(
			List<Map<String, Object>> propertyMap) {
		String accountNo=null;
		String firstName=null;
		String lastName=null;
		String propertyNumber=null;
		String key=null;
		Object value=null;
		List<List<String>> listItems=new ArrayList<List<String>>();
		for (Map<String,Object> accountDetails : propertyMap) {
			List<String> list=new ArrayList<String>();
			for (Map.Entry<String, Object> entry : accountDetails.entrySet()){
				key = entry.getKey();
				value = entry.getValue();
				if(key.equalsIgnoreCase("accountNo")){
					accountNo=(String)value;
					accountNo=StringEscapeUtils.escapeXml(accountNo);
				}
				if(key.equalsIgnoreCase("firstName")){
					firstName=(String)value;
					firstName=StringEscapeUtils.escapeXml(firstName);
				}
				if(key.equalsIgnoreCase("lastName")){
					lastName=(String)value;
					lastName=StringEscapeUtils.escapeXml(lastName);
				}
				if(key.equalsIgnoreCase("propertyNumber")){
					propertyNumber=(String)value;
					propertyNumber=StringEscapeUtils.escapeXml(propertyNumber);
				}
			}
			list.add(accountNo);
			list.add(firstName);
			list.add(lastName);
			list.add(propertyNumber);
			listItems.add(list);
			
		}
		return listItems;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/bill/saveOldBill/{elBillId}", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<Object> saveBillDoc(HttpServletRequest req,
			HttpServletResponse response, @RequestBody String body,@PathVariable int elBillId)
			 {
		
		logger.info("::::::::::::::::"+elBillId);
		
		Locale locale=null;
		//billController.getBillDoc(elBillId, locale);

		PrintWriter out;
		try {
			out = response.getWriter();
			out.write("Bill saved Successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping(value = "/electricityBillTemplate/electricityBillTemplatesDetailsExport/{month}", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileElectricityBill(@PathVariable String month,HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
		Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
       
		List<Object[]> electricityBillTemplateEntities =electricityBillsService.readBillMonthWise(monthToShow);
	
               
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
        header.createCell(2).setCellValue("Property No");
        header.createCell(3).setCellValue("Net Amount");
        header.createCell(4).setCellValue("Service Type");
        header.createCell(5).setCellValue("Bill Month");
        header.createCell(6).setCellValue("Bill Due Date"); 
        header.createCell(7).setCellValue("Bill Status");
        header.createCell(8).setCellValue("Tally Status");      
        header.createCell(9).setCellValue("Bill Number");      
        
    
        for(int j = 0; j<=9; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:I200"));
        }
        
        int count = 1;
        //XSSFCell cell = null;
    	for (Object[] electricityBill :electricityBillTemplateEntities ) {
    		
    		XSSFRow row = sheet.createRow(count);
    		
    		String str="";
    		if((String)electricityBill[12]!=null){
    			str=(String)electricityBill[12];
    		}
    		else{
    			str="";
    		}

    		row.createCell(0).setCellValue(str);
    		String personName = "";
    		if((String)electricityBill[16] != null){
    			personName = personName.concat((String)electricityBill[15]);	
    		}
    		else{
    			personName ="";
    		}
			
			if((String)electricityBill[16] != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)electricityBill[16]);
			}
			else{
				personName ="";
			}
    		row.createCell(1).setCellValue(personName);
    		String queryString = "Select p.property_No from Property p where p.propertyId=(Select a.propertyId from Account a where a.accountId ="+(Integer)electricityBill[3]+")";
    		row.createCell(2).setCellValue((String)entityManager.createQuery(queryString).getSingleResult());
    		
    		
    		row.createCell(3).setCellValue(String.format("%.2f",(Double)electricityBill[14]));
    		if((String)electricityBill[1]!=null){
    			str=(String)electricityBill[1];
    		}
    		else{
    			str="";
    		}
    		row.createCell(4).setCellValue(str);
    		SimpleDateFormat sdf = new SimpleDateFormat("MMM,yyyy");
    		if((Date)electricityBill[8]!=null){
    			str=sdf.format((Date)electricityBill[8]);
    		}
    		else{
    			str="";
    		}
    		row.createCell(5).setCellValue(str);
    		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
    		if((Date)electricityBill[7]!=null){
    			str=sd.format((Date)electricityBill[7]);
    		}
    		else{
    			str="";
    		}
    		row.createCell(6).setCellValue(str);
    		if((String)electricityBill[11]!=null){
    			str=(String)electricityBill[11];
    		}
    		else{
    			str="";
    		}
    		
    		row.createCell(7).setCellValue(str);
    		if((String)electricityBill[22]!=null){
    			str=(String)electricityBill[22];
    		}
    		else{
    			str="";
    		}
    		row.createCell(8).setCellValue(str);
    		
    		if((String)electricityBill[23]!=null){
    			str=(String)electricityBill[23];
    		}
    		else{
    			str="";
    		}
    		row.createCell(9).setCellValue(str);
    		
    		
    		
    		count ++;
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"BillGeneratingTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/electricityBillPdfTemplate/electricityBillPdfTemplatesDetailsExport/{month}", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileElectricityBillPdf(@PathVariable String month,HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
        
       
		Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
	       
		List<Object[]> electricityBillTemplateEntities =electricityBillsService.readBillMonthWise(monthToShow);
	
	
              
      
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
      
       
        PdfPTable table = new PdfPTable(9);
        Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
        
    	
        
        table.addCell(new Paragraph("Account No",font1));
        table.addCell(new Paragraph("Person Name",font1));
        table.addCell(new Paragraph("Property No",font1));
        table.addCell(new Paragraph("Net Amount",font1));
        table.addCell(new Paragraph("Service Type",font1));
        table.addCell(new Paragraph("Bill Month",font1));
        table.addCell(new Paragraph("Bill Due Date",font1));
        table.addCell(new Paragraph("Bill Status",font1));
        table.addCell(new Paragraph("Tally Status",font1));
        //XSSFCell cell = null;
    	for (Object[] electricityBill :electricityBillTemplateEntities) {
    		String str="";
    		if((String)electricityBill[12]!=null){
    			str=(String)electricityBill[12];
    		}
    		else{
    			str="";
    		}

        
        PdfPCell cell1 = new PdfPCell(new Paragraph(str,font3));
        String personName = "";
		if((String)electricityBill[16] != null){
			personName = personName.concat((String)electricityBill[15]);	
		}
		else{
			personName ="";
		}
		
		if((String)electricityBill[16] != null)
		{
			personName = personName.concat(" ");
			personName = personName.concat((String)electricityBill[16]);
		}
		else{
			personName ="";
		}
        PdfPCell cell2 = new PdfPCell(new Paragraph(personName,font3));
    	String queryString = "Select p.property_No from Property p where p.propertyId=(Select a.propertyId from Account a where a.accountId ="+(Integer)electricityBill[3]+")";
    
        PdfPCell cell3 = new PdfPCell(new Paragraph((String)entityManager.createQuery(queryString).getSingleResult(),font3));
       
        PdfPCell cell4 = new PdfPCell(new Paragraph(String.format("%.2f",(Double)electricityBill[14]),font3));
        if((String)electricityBill[1]!=null){
			str=(String)electricityBill[1];
		}
		else{
			str="";
		}
        PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));
        SimpleDateFormat sdf = new SimpleDateFormat("MMM,yyyy");
        if((Date)electricityBill[8]!=null){
			str=sdf.format((Date)electricityBill[8]);
		}
		else{
			str="";
		}
        PdfPCell cell6 = new PdfPCell(new Paragraph(str,font3));
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
        if((Date)electricityBill[7]!=null){
			str=sd.format((Date)electricityBill[7]);
		}
		else{
			str="";
		}
        PdfPCell cell7 = new PdfPCell(new Paragraph(str,font3));
        if((String)electricityBill[11]!=null){
			str=(String)electricityBill[11];
		}
		else{
			str="";
		}
        PdfPCell cell8 = new PdfPCell(new Paragraph(str,font3));
        if((String)electricityBill[22]!=null){
			str=(String)electricityBill[22];
		}
		else{
			str="";
		}
        PdfPCell cell9 = new PdfPCell(new Paragraph(str,font3));
      
       
       

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
        float[] columnWidths = {12f, 15f, 10f, 10f, 13f, 10f, 15f, 10f, 12f};

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
		response.setHeader("Content-Disposition", "inline;filename=\"BillGeneratingTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/billingGeneration/getAccountsBasedOnseletedProperty/{propertyId}", method = RequestMethod.GET)
	public @ResponseBody List<?> accountNumberAutocomplete(@PathVariable int propertyId) {
		return tariffCalculationService.getAllAccuntNumbersBasedOnProperty(propertyId);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/bill/deleteBill", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String deleteBill(HttpServletRequest req, HttpServletResponse response)throws ParseException, JSONException {
		String elbId=req.getParameter("elBillId");
		String billstatus=req.getParameter("bill_status");
		System.err.println("elbId====>"+elbId+"   billstatus====>"+billstatus);
		
		String max="SELECT MAX(ELB_ID) FROM BILL WHERE ACCOUNT_ID IN(SELECT ACCOUNT_ID FROM BILL WHERE ELB_ID='"+elbId+"')";
		BigDecimal maxid = (BigDecimal) entityManager.createNativeQuery(max).getSingleResult();
		
		if(!billstatus.equalsIgnoreCase("Cancelled")){
			if(maxid.intValue() > Integer.parseInt(elbId)){
				return "Cant Delete! Next Month Bill exists for this property";
			}
		}
		
		String query1="Delete FROM SUB_LEDGER WHERE ELL_ID IN(SELECT ELL_ID FROM LEDGER WHERE POST_REF=(SELECT BILL_NO FROM BILL WHERE ELB_ID='"+elbId+"'))";
		String query2="Delete FROM LEDGER WHERE POST_REF=(SELECT BILL_NO FROM BILL WHERE ELB_ID='"+elbId+"')";
		String query3="Delete FROM BILL_PARAMETERS WHERE ELB_ID='"+elbId+"'";
		String query4="Delete FROM BILL_LINEITEM WHERE ELB_ID='"+elbId+"'";
		String query5="Delete FROM BILL WHERE ELB_ID='"+elbId+"'";
		try{
			entityManager.createNativeQuery(query1).executeUpdate();
			entityManager.createNativeQuery(query2).executeUpdate();
			entityManager.createNativeQuery(query3).executeUpdate();
			entityManager.createNativeQuery(query4).executeUpdate();
			entityManager.createNativeQuery(query5).executeUpdate();
			return "Bill Deleted Successfully !";
		}catch(Exception e){
			e.printStackTrace();
			return "Exception Came While Deleting !";
		}

	}
	
}
