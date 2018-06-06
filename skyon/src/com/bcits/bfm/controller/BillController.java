package com.bcits.bfm.controller;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Hibernate;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.Address;
import com.bcits.bfm.model.BillDocument;
import com.bcits.bfm.model.BillParameterMasterEntity;
import com.bcits.bfm.model.BillingPaymentsEntity;
import com.bcits.bfm.model.CommonServicesRateMaster;
import com.bcits.bfm.model.CommonTariffMaster;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.ELRateMaster;
import com.bcits.bfm.model.ELRateSlabs;
import com.bcits.bfm.model.ELTariffMaster;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.ElectricityBillLineItemEntity;
import com.bcits.bfm.model.ElectricityBillParametersEntity;
import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.model.ElectricityMetersEntity;
import com.bcits.bfm.model.GasRateMaster;
import com.bcits.bfm.model.GasRateSlab;
import com.bcits.bfm.model.GasTariffMaster;
import com.bcits.bfm.model.LastSixMonthsBills;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.model.ServiceParameterMaster;
import com.bcits.bfm.model.ServiceParametersEntity;
import com.bcits.bfm.model.SlabDetails;
import com.bcits.bfm.model.SolidWasteRateMaster;
import com.bcits.bfm.model.SolidWasteRateSlab;
import com.bcits.bfm.model.SolidWasteTariffMaster;
import com.bcits.bfm.model.TransactionMasterEntity;
import com.bcits.bfm.model.WTRateMaster;
import com.bcits.bfm.model.WTRateSlabs;
import com.bcits.bfm.model.WTTariffMaster;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.accountsManagement.ElectricityLedgerService;
import com.bcits.bfm.service.advanceCollection.AdvanceCollectionService;
import com.bcits.bfm.service.billingCollectionManagement.PaymentService;
import com.bcits.bfm.service.commonAreaMaintenance.CamConsolidationService;
import com.bcits.bfm.service.commonAreaMaintenance.CamLedgerService;
import com.bcits.bfm.service.commonServicesTariffManagement.CommonServiceTariffMasterServices;
import com.bcits.bfm.service.commonServicesTariffManagement.CommonServicesRateMasterService;
import com.bcits.bfm.service.customerOccupancyManagement.AddressService;
import com.bcits.bfm.service.customerOccupancyManagement.CityService;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.deposits.DepositsService;
import com.bcits.bfm.service.electricityBillsManagement.ELectricityBillDocumentService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillLineItemService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillParameterService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.electricityMetersManagement.ElectricityMeterParametersService;
import com.bcits.bfm.service.electricityMetersManagement.ElectricityMetersService;
import com.bcits.bfm.service.facilityManagement.BillingParameterMasterService;
import com.bcits.bfm.service.facilityManagement.MeterParameterMasterService;
import com.bcits.bfm.service.facilityManagement.ServiceParameterMasterService;
import com.bcits.bfm.service.gasTariffManagment.GasRateMasterService;
import com.bcits.bfm.service.gasTariffManagment.GasTariffMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceParameterService;
import com.bcits.bfm.service.solidWasteTariffManagment.SolidWasteRateMasterService;
import com.bcits.bfm.service.solidWasteTariffManagment.SolidWasteTariffMasterService;
import com.bcits.bfm.service.tariffManagement.ELRateMasterService;
import com.bcits.bfm.service.tariffManagement.ELRateSlabService;
import com.bcits.bfm.service.tariffManagement.ELTariffMasterService;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.bcits.bfm.service.transactionMaster.InterestSettingService;
import com.bcits.bfm.service.transactionMaster.TransactionMasterService;
import com.bcits.bfm.service.waterTariffManagement.WTRateMasterService;
import com.bcits.bfm.service.waterTariffManagement.WTTariffMasterService;
import com.bcits.bfm.util.NumberToWord;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker; // deprecated
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.itextpdf.text.pdf.PdfWriter;

@SuppressWarnings("deprecation")
@Controller
public class BillController {
	
	@PersistenceContext(unitName="bfm")
	protected EntityManager entityManager;

	@Autowired
	private PersonService personService;

	static Logger logger = LoggerFactory.getLogger(BillController.class);

	@Autowired
	private ContactService contactService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private ElectricityBillsService electricityBillsService;

	@Autowired
	private ElectricityBillLineItemService billLineItemService;

	@Autowired
	private ServiceMasterService masterService;

	@Autowired
	private ServiceParameterMasterService serviceParameterMasterService;

	@Autowired
	private ServiceParameterService serviceParameterService;

	@Autowired
	private CamLedgerService camLedgerService;

	@Autowired
	private BillingParameterMasterService billParameterMasterService;

	@Autowired
	private ElectricityBillParameterService electricityBillParameterService;

	@Autowired
	private ElectricityMeterParametersService electricityMeterParametersService;

	@Autowired
	private ElectricityMetersService electricityMetersService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private MeterParameterMasterService meterParameterMasterService;

	@Autowired
	private ElectricityLedgerService electricityLedgerService;

	@Autowired
	private TransactionMasterService transactionMasterService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private ELTariffMasterService elTariffMasterService;

	@Autowired
	private WTTariffMasterService wtTariffMasterService;

	@Autowired
	private GasTariffMasterService gasTariffMasterService;

	@Autowired
	private SolidWasteTariffMasterService solidWasteTariffMasterService;

	@Autowired
	private CommonServiceTariffMasterServices commonServiceTariffMasterServices;

	@Autowired
	private DepositsService depositsService;

	@Autowired
	private AdvanceCollectionService advanceCollectionService;

	@Autowired
	private CityService cityService;

	@Autowired
	private CamConsolidationService camConsolidationService;

	@Autowired
	private ELRateMasterService rateMasterService;

	@Autowired
	private ELRateSlabService elRateSlabService;

	@Autowired
	private TariffCalculationService tariffCalculationService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private InterestSettingService interestSettingService;

	@Autowired
	TariffCalculationController calculationController;

	@Autowired
	WTRateMasterService wtRateMasterService;

	@Autowired
	GasRateMasterService gasRateMasterService;

	@Autowired
	SolidWasteRateMasterService solidWasteRateMasterService;

	@Autowired
	CommonServicesRateMasterService commonServicesRateMasterService;

	@Autowired
	ELectricityBillDocumentService billDocumentService;
	
	@Autowired
	CAMBillsController camBillsController;

	@RequestMapping(value = "/bill", method = RequestMethod.GET)
	public String bill(Model model) {
		return "bill/bill";
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/detailedbill", method = RequestMethod.GET)
	public String detailedBill(@RequestParam("accid") int accountId,
			@RequestParam("typeOfService") String typeOfService,
			HttpServletRequest request, Model model) {

		// int personId = 2220;
		if (accountId > 0) {

			Account account = accountService
					.getSingleResult("select obj from Account obj where obj.accountId="
							+ accountId + " and obj.accountStatus='Active'");
			model.addAttribute("account", account);

			// model.addAttribute("account",accountService.executeSimpleQuery("select obj from Account obj where obj.accountId="+accountId+" and obj.accountStatus='Active'").get(0));

			model.addAttribute("person",
					personService.find(account.getPersonId()));
			model.addAttribute(
					"address",
					addressService.executeSimpleQuery(
							"select obj from Address obj where obj.personId="
									+ account.getPersonId()
									+ " and obj.addressPrimary='Yes'").get(0));
			model.addAttribute(
					"mobile",
					contactService
							.executeSimpleQuery(
									"select obj from Contact obj where obj.personId="
											+ account.getPersonId()
											+ " and obj.contactType='Mobile' and obj.contactPrimary='Yes'")
							.get(0));
			model.addAttribute(
					"email",
					contactService
							.executeSimpleQuery(
									"select obj from Contact obj where obj.personId="
											+ account.getPersonId()
											+ " and obj.contactType='Email' and obj.contactPrimary='Yes'")
							.get(0));
		}

		List<ElectricityBillEntity> billEntities = electricityBillsService
				.executeSimpleQuery("select obj from ElectricityBillEntity obj where obj.accountId="
						+ request.getParameter("accid")
						+ " and obj.typeOfService='" + typeOfService + "'");
		if (billEntities.size() > 0) {
			model.addAttribute("bill", billEntities.get(0));
			List<ElectricityBillLineItemEntity> billLineItemEntities = billLineItemService
					.executeSimpleQuery("select obj from ElectricityBillLineItemEntity obj where obj.electricityBillEntity.elBillId="
							+ billEntities.get(0).getElBillId());
			if (billLineItemEntities.size() > 0)
				model.addAttribute("lineitems", billLineItemEntities);
			else
				model.addAttribute("lineitems", "");
		} else {
			model.addAttribute("bill", null);
		}

		List<ServiceMastersEntity> serviceMastersEntities = masterService
				.executeSimpleQuery("select obj from ServiceMastersEntity obj where obj.accountObj.accountId="
						+ accountId
						+ " and obj.typeOfService='"
						+ typeOfService + "'");
		if (serviceMastersEntities.size() > 0) {
			List<Map<String, Object>> serviceparameter = new ArrayList<Map<String, Object>>();
			for (final Object[] service : serviceParameterService
					.getNameandValue(serviceMastersEntities.get(0)
							.getServiceMasterId())) {
				serviceparameter.add(new HashMap<String, Object>() {
					{
						put("name", (String) service[0]);
						put("value", (String) service[1]);
					}
				});
			}
			model.addAttribute("serviceparameter", serviceparameter);
		} else {
			model.addAttribute("serviceparameter", "");
		}

		List<ElectricityBillEntity> electricityBillEntities = electricityBillsService
				.executeSimpleQuery("select obj from ElectricityBillEntity obj where obj.accountId="
						+ accountId
						+ " and obj.typeOfService='"
						+ typeOfService + "'");
		if (electricityBillEntities.size() > 0) {
			List<Map<String, Object>> billparameter = new ArrayList<Map<String, Object>>();
			for (final Object[] service : billParameterMasterService
					.getNameandValue(electricityBillEntities.get(0)
							.getElBillId())) {
				billparameter.add(new HashMap<String, Object>() {
					{
						put("name", (String) service[0]);
						put("value", (String) service[1]);
					}
				});
			}
			model.addAttribute("billparameter", billparameter);
		} else {
			model.addAttribute("billparameter", "");
		}

		List<ElectricityMetersEntity> electricityMetersEntities = electricityMetersService
				.executeSimpleQuery("select obj from ElectricityMetersEntity obj where obj.account="
						+ accountId
						+ " and obj.typeOfService='"
						+ typeOfService + "'");
		if (electricityMetersEntities.size() > 0) {
			List<Map<String, Object>> meterparameter = new ArrayList<Map<String, Object>>();
			for (final Object[] service : meterParameterMasterService
					.getNameandValue(electricityMetersEntities.get(0)
							.getElMeterId())) {
				meterparameter.add(new HashMap<String, Object>() {
					{
						put("name", (String) service[0]);
						put("value", (String) service[1]);
					}
				});
			}
			model.addAttribute("meterparameter", meterparameter);
		} else {
			model.addAttribute("meterparameter", "");
		}
		return "bill/detailedbill";
	}

	@RequestMapping(value = "/asset/getpersondetail/{personId}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	Person getPersonDetail(@PathVariable int personId) {

		List<Person> plisat = personService
				.executeSimpleQuery("select obj from Person obj where obj.personId= "
						+ personId);
		List<Contact> mlist = contactService
				.executeSimpleQuery("select obj from Contact obj where obj.personId= "
						+ personId
						+ " and obj.contactType='Mobile' and obj.contactPrimary='Yes'");
		List<Contact> elist = contactService
				.executeSimpleQuery("select obj from Contact obj where obj.personId= "
						+ personId
						+ " and obj.contactType='Email' and obj.contactPrimary='Yes'");
		List<Address> alist = addressService
				.executeSimpleQuery("select obj from Address obj where obj.personId= "
						+ personId + " and obj.addressPrimary='Yes'");

		Person person = new Person();
		person.setFirstName(plisat.get(0).getFirstName());
		person.setLastName(plisat.get(0).getLastName());

		if (mlist.size() > 0)
			person.setFatherName(mlist.get(0).getContactContent());
		if (elist.size() > 0)
			person.setSpouseName(elist.get(0).getContactContent());
		if (alist.size() > 0)
			person.setBloodGroup(alist.get(0).getAddress1());

		return person;
	}

	@RequestMapping(value = "/asset/getbilldetails/{accountId}/{typeOfService}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	ElectricityBillEntity getElectricityDetails(@PathVariable int accountId,
			@PathVariable String typeOfService) {

		ElectricityBillEntity electricityBillEntity = electricityBillsService
				.getSingleResult("select obj from ElectricityBillEntity obj where obj.elBillId = (select max(e.elBillId) from ElectricityBillEntity e where e.accountId="
						+ accountId
						+ " and e.typeOfService = '"
						+ typeOfService + "' )");
		if (electricityBillEntity != null) {
			ElectricityBillEntity billEntity = new ElectricityBillEntity();
			billEntity.setBillAmount(electricityBillEntity.getBillAmount());
			billEntity.setBillDueDate(electricityBillEntity.getBillDueDate());
			billEntity.setBillMonth(electricityBillEntity.getBillMonth());
			billEntity.setElBillDate(electricityBillEntity.getElBillDate());
			billEntity.setTypeOfService(electricityBillEntity
					.getTypeOfService());
			return billEntity;
		}
		return null;
	}

	@RequestMapping(value = "/asset/getbilllineitems", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	List<ElectricityBillLineItemEntity> getElectricityLineItems() {

		List<ElectricityBillLineItemEntity> list = billLineItemService
				.executeSimpleQuery("select obj from ElectricityBillLineItemEntity obj ");

		List<ElectricityBillLineItemEntity> list2 = new ArrayList<ElectricityBillLineItemEntity>();
		for (ElectricityBillLineItemEntity billLineItemEntity : list) {
			ElectricityBillLineItemEntity billLineItemEntity2 = new ElectricityBillLineItemEntity();
			billLineItemEntity2.setBalanceAmount(billLineItemEntity
					.getBalanceAmount());
			billLineItemEntity2.setTransactionCode(billLineItemEntity
					.getTransactionCode());
			list2.add(billLineItemEntity2);
		}

		return list2;
	}

	@RequestMapping(value = "/bill/getAjaxDetailedBill", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	Map<String, Object> getDetailedBillTable(
			@RequestParam("accountId") int accountId,
			@RequestParam("typeOfService") String typeOfService, Locale locale,
			HttpServletResponse res) {

		ElectricityBillEntity electricityBillEntity = electricityBillsService
				.getSingleResult("select obj from ElectricityBillEntity obj where obj.elBillId = (select max(e.elBillId) from ElectricityBillEntity e where e.accountId="
						+ accountId
						+ " and e.typeOfService = '"
						+ typeOfService + "' )");

		Map<String, Object> detailedHtmlTable = detailedBillPopup(
				electricityBillEntity.getElBillId(), res, locale);
		return detailedHtmlTable;
	}

	@RequestMapping(value = "/bill/getbilltablePDF/{elBillId}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public void viewBillPDF(@PathVariable int elBillId,
			HttpServletResponse res, Locale locale) {
		ElectricityBillEntity electricityBillEntity = electricityBillsService.find(elBillId);
		//getBillDoc(electricityBillEntity.getElBillId(), locale);
		Blob blob = electricityBillsService.getBlog(elBillId);
		Property property = propertyService.find(electricityBillEntity
				.getAccountObj().getPropertyId());
		try {
			if (blob != null) {
				res.setHeader("Content-Disposition", "inline; filename="
						+ electricityBillEntity.getTypeOfService() + "_"
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

	@RequestMapping(value = "/bill/downloadAllBills/{serviceType}/{presentdate}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public void downloadAllBills(HttpServletResponse res,
			HttpServletRequest req, Locale locale,
			@PathVariable String serviceType, @PathVariable String presentdate)
			throws ParseException, IOException {
		Date monthDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH)
				.parse(presentdate);
		res.setContentType("application/zip");
		res.setHeader("Content-disposition", "inline; filename="
				+ DateFormatUtils.format((monthDate), "MMMyyyy") + ".zip");
		OutputStream out = res.getOutputStream();
		final ZipOutputStream zipfile = new ZipOutputStream(res.getOutputStream());
		List<ElectricityBillEntity> billEntities = electricityBillsService
				.downloadAllBills(serviceType, monthDate);
		for (ElectricityBillEntity electricityBillEntity : billEntities) {
			//getBillDoc(electricityBillEntity.getElBillId(), locale);
			Property property = propertyService.find(electricityBillEntity
					.getAccountObj().getPropertyId());
			Blob blob = electricityBillsService.getBlog(electricityBillEntity
					.getElBillId());
			ZipEntry ze = new ZipEntry(electricityBillEntity.getTypeOfService()
					+ "_" + property.getProperty_No() + ".pdf");
			zipfile.putNextEntry(ze);
			int length;
			try {
				length = (int) blob.length();
				byte[] data1 = blob.getBytes(1, length);
				zipfile.write(data1, 0, data1.length);
			} catch (SQLException e) {
				logger.error("SQLException");
			}
		}
		zipfile.closeEntry();
		zipfile.close();
		out.flush();
		out.close();
	}

	// curl
	// 'http://192.168.2.200:8888/bfm_acq/BillGeneration/GenerateBills/viewBillButton'
	// -X POST -H 'Host: 192.168.2.200:8888' -H 'User-Agent: Mozilla/5.0
	// (Windows NT 6.3; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0' -H 'Accept:
	// */*' -H 'Accept-Language: en-US,en;q=0.5' -H 'Accept-Encoding: gzip,
	// deflate' -H 'X-Requested-With: XMLHttpRequest' -H 'Referer:
	// http://192.168.2.200:8888/bfm_acq/electrictyBills' -H 'Cookie:
	// bcitsadmin=bcitsadmin; moduleName=AMR Data;
	// JSESSIONID=9E74AFA0147515DD198D4FD327386651'
	/*
	 * @SuppressWarnings("unused")
	 * 
	 * @RequestMapping(value = "/bill/getbilltablePDF/{elBillId}", method =
	 * {RequestMethod.POST, RequestMethod.GET }) public void
	 * viewBillPDF(@PathVariable int elBillId,Locale locale) { Map<String,
	 * Object> map = new LinkedHashMap<>(); Address address = null; Contact
	 * contactMob = null; Contact contactEmail = null; String tariffName = "";
	 * 
	 * String billType = ""; String slabs = ""; String bpmStr = ""; String
	 * mpmStr = ""; String bliStr = ""; String spmStr = "";
	 * 
	 * String postType = ""; String str = ""; String meterSrNo = ""; String
	 * meterType = ""; String meterMake = "";
	 * 
	 * float sanctionLoad = 0.0f; float sanctionLoadDG = 0.0f; String
	 * arrearString = ""; Float uomValue = 0.0f; Float numberOfDays = 0.0f;
	 * Float presentReading = 0.0f; Float previousReading = 0.0f; Float
	 * meterConstant = 0.0f; int elTariffId = 0; Float pfValue = 0.0f; Float
	 * mdiValue = 0.0f; int solidWasteTariffId = 0; int wtTariffId = 0; int
	 * gasTariffId = 0; String dgApplicable = ""; String dgSlabString = "";
	 * Double arrearsAmount = 0.0; String meterStatus = ""; Double voltageLevel
	 * = 0.0; String connectionType = ""; Double interestOnTax = 0.0; Double
	 * interestAmount = 0.0; Double taxAmount = 0.0; float amountForInteresetCal
	 * = 0.0f; Float latePaymentAmount = 0.0f; Float connectionSecurity = 0.0f;
	 * Float dgUnits = 0.0f; String othersTariffName = ""; int othersTariffId =
	 * 0; List<SlabDetails> dgSlabDetailsList = new ArrayList<>();
	 * List<SlabDetails> slabDetailsList = new ArrayList<>();
	 * 
	 * ElectricityBillEntity electricityBillEntity =
	 * electricityBillsService.find(elBillId);
	 * 
	 * if (electricityBillEntity != null) { billType =
	 * electricityBillEntity.getBillType(); postType =
	 * electricityBillEntity.getPostType();
	 * 
	 * Object[] addressDetailsList = camConsolidationService
	 * .getAddressDetailsForMail(electricityBillEntity
	 * .getAccountObj().getPerson().getPersonId()); List<String>
	 * contactDetailsList = camConsolidationService
	 * .getContactDetailsForMail(electricityBillEntity
	 * .getAccountObj().getPerson().getPersonId());
	 * 
	 * Property property =
	 * propertyService.find(electricityBillEntity.getAccountObj
	 * ().getPropertyId());
	 * 
	 * String address1 =
	 * property.getProperty_No()+","+property.getBlocks().getBlockName(); String
	 * city = "Gurgaon";
	 * 
	 * String toAddressEmail = ""; String toAddressMobile = "";
	 * 
	 * for (Iterator<?> iterator = contactDetailsList.iterator(); iterator
	 * .hasNext();) { final Object[] values = (Object[]) iterator.next(); if
	 * (((String) values[0]).equals("Email")) { toAddressEmail = (String)
	 * values[1]; } else { toAddressMobile = (String) values[1]; } }
	 * 
	 * if (electricityBillEntity.getTypeOfService().equals("CAM")) {
	 * logger.info("cam bill templete -------------------------------");
	 * 
	 * HashMap<String, Object> param = new HashMap<String, Object>();
	 * List<LastSixMonthsBills> lastSixMonthsBills = new ArrayList<>();
	 * 
	 * List<?> lastSixMontsBills = electricityBillsService
	 * .getLastSixMonthsCAMBills( electricityBillEntity.getAccountId(),
	 * electricityBillEntity.getTypeOfService(),
	 * electricityBillEntity.getBillDate()); String calculationBasis =
	 * camConsolidationService
	 * .getParameterValueBasedOnParameterName("Bill Basis"); String
	 * noOfParkingSlots = camConsolidationService
	 * .getParameterValueBasedOnParameterName("No of parking slots"); for
	 * (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator
	 * .hasNext();) { final Object[] values = (Object[]) iterator.next();
	 * LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills();
	 * sixMonthsBills.setMonth((Date) values[0]);
	 * sixMonthsBills.setBillBases(calculationBasis);
	 * sixMonthsBills.setAmount((Double) values[1]);
	 * lastSixMonthsBills.add(sixMonthsBills); }
	 * 
	 * BillingPaymentsEntity billingPaymentsEntity = paymentService
	 * .getPamentDetals(electricityBillEntity.getAccountId(),
	 * electricityBillEntity.getFromDate()); if (billingPaymentsEntity != null)
	 * { param.put("paymentAmount", billingPaymentsEntity.getPaymentAmount());
	 * param.put("receiptNo", billingPaymentsEntity.getReceiptNo());
	 * param.put("receiptDate", billingPaymentsEntity.getReceiptDate());
	 * param.put("modeOfPay", billingPaymentsEntity.getPaymentMode()); }
	 * 
	 * double parkingSlotsAmount = electricityBillsService
	 * .getLineItemAmountBasedOnTransactionCode(
	 * electricityBillEntity.getElBillId(), "CAM_PARKING_SLOT"); double
	 * perSlotAmount = camConsolidationService
	 * .getParkingPerSlotAmount("CAM_PARKING_SLOT"); double serviceTaxAmount =
	 * camConsolidationService .getParkingPerSlotAmount("CAM_SERVICE_TAX");
	 * double eCessTaxAmount = camConsolidationService
	 * .getParkingPerSlotAmount("CAM_ECESS"); double sheCessTaxAmount =
	 * camConsolidationService .getParkingPerSlotAmount("CAM_SHECESS"); double
	 * arrearsAmountCam = electricityBillEntity .getArrearsAmount(); double
	 * serviceTax = Math.round(electricityBillsService
	 * .getLineItemAmountBasedOnTransactionCode(
	 * electricityBillEntity.getElBillId(), "CAM_SERVICE_TAX")); double
	 * educationAmount = electricityBillsService
	 * .getLineItemAmountBasedOnTransactionCode(
	 * electricityBillEntity.getElBillId(), "CAM_ECESS"); double
	 * sEducationAmount = electricityBillsService
	 * .getLineItemAmountBasedOnTransactionCode(
	 * electricityBillEntity.getElBillId(), "CAM_SHECESS"); double billAmount =
	 * electricityBillEntity.getBillAmount() - serviceTax - educationAmount -
	 * sEducationAmount - parkingSlotsAmount; double totalAmount =
	 * Math.round(electricityBillEntity .getBillAmount() + arrearsAmountCam);
	 * double totalCamRates = camConsolidationService .getTotalCamRates();
	 * 
	 * JRBeanCollectionDataSource subReportDS = new JRBeanCollectionDataSource(
	 * lastSixMonthsBills); JRBeanCollectionDataSource subReport1DS = new
	 * JRBeanCollectionDataSource( lastSixMonthsBills);
	 * 
	 * param.put("subreport_datasource", subReportDS);
	 * param.put("subreport_datasource1", subReport1DS);
	 * 
	 * param.put("camPoint1", ResourceBundle.getBundle("utils")
	 * .getString("report.CAM.point1")); param.put("camPoint2",
	 * ResourceBundle.getBundle("utils") .getString("report.CAM.point2"));
	 * param.put("camPoint3", ResourceBundle.getBundle("utils")
	 * .getString("report.CAM.point3") + " (" + calculationBasis + ")");
	 * param.put("camPoint4", ResourceBundle.getBundle("utils")
	 * .getString("report.CAM.point4") + " (" + perSlotAmount + "/- per slot)");
	 * param.put("camPoint5", ResourceBundle.getBundle("utils")
	 * .getString("report.CAM.point5")); param.put("camPoint6",
	 * ResourceBundle.getBundle("utils") .getString("report.CAM.point6") +
	 * " (Amount + Parking Slot Amount)"); param.put("camPoint7",
	 * ResourceBundle.getBundle("utils") .getString("report.CAM.point7") + " @ "
	 * + serviceTaxAmount + "%"); param.put("camPoint8",
	 * ResourceBundle.getBundle("utils") .getString("report.CAM.point8") + " @ "
	 * + eCessTaxAmount + "%"); param.put("camPoint9",
	 * ResourceBundle.getBundle("utils") .getString("report.CAM.point9") + " @ "
	 * + sheCessTaxAmount + "%"); param.put("camPoint10",
	 * ResourceBundle.getBundle("utils") .getString("report.CAM.point10"));
	 * param.put("camPoint11", ResourceBundle.getBundle("utils")
	 * .getString("report.CAM.point11")); param.put("camPoint12",
	 * ResourceBundle.getBundle("utils") .getString("report.CAM.point12"));
	 * param.put("camPoint13", ResourceBundle.getBundle("utils")
	 * .getString("report.CAM.point13"));
	 * 
	 * double vat = 0; String month = "";
	 * 
	 * if (Integer.parseInt(noOfParkingSlots) == 1) { month = "month"; } else {
	 * month = "months"; }
	 * 
	 * param.put("camPointValue1", camConsolidationService
	 * .getAreaOfProperty(electricityBillEntity
	 * .getAccountObj().getPropertyId())); param.put("camPointValue2",
	 * calculationBasis); param.put("camPointValue3", totalCamRates);
	 * param.put("camPointValue4", parkingSlotsAmount + " (" + perSlotAmount +
	 * " x " + noOfParkingSlots + " Slots x " +
	 * getNoMonthsDiff(electricityBillEntity) + " " + month + ")");
	 * param.put("camPointValue5", Math.round(billAmount));
	 * param.put("camPointValue6", (parkingSlotsAmount +
	 * Math.round(billAmount))); param.put("camPointValue7", serviceTax);
	 * param.put("camPointValue8", educationAmount); param.put("camPointValue9",
	 * sEducationAmount); param.put("camPointValue10", vat);
	 * param.put("camPointValue11", arrearsAmountCam);
	 * param.put("camPointValue12", totalAmount); param.put("camPointValue13",
	 * NumberToWord.convertNumberToWords((int) totalAmount));
	 * 
	 * param.put( "title", ResourceBundle.getBundle("utils").getString(
	 * "report.title")); param.put("companyAddress",
	 * ResourceBundle.getBundle("utils") .getString("report.address"));
	 * param.put("point1", ResourceBundle.getBundle("utils")
	 * .getString("report.point1")); param.put("point2",
	 * ResourceBundle.getBundle("utils") .getString("report.point2"));
	 * param.put("point3", ResourceBundle.getBundle("utils")
	 * .getString("report.point3")); param.put("point4",
	 * ResourceBundle.getBundle("utils") .getString("report.point4"));
	 * param.put("point5.1", ResourceBundle.getBundle("utils")
	 * .getString("report.point5.1")); param.put("point5.2",
	 * ResourceBundle.getBundle("utils") .getString("report.point5.2"));
	 * param.put("point5.3", ResourceBundle.getBundle("utils")
	 * .getString("report.point5.3")); param.put("point6",
	 * ResourceBundle.getBundle("utils") .getString("report.point6"));
	 * param.put("amountPayble", electricityBillEntity.getNetAmount());
	 * param.put("dueDate", electricityBillEntity.getBillDueDate());
	 * param.put("name", electricityBillEntity.getAccountObj()
	 * .getPerson().getFirstName()); param.put("address", address1);
	 * param.put("city", city); param.put("mobileNo", toAddressMobile);
	 * param.put("emailId", toAddressEmail); param.put("surcharge",
	 * latePaymentAmount); param.put("billNo",
	 * electricityBillEntity.getBillNo()); param.put("amountAfterDueDate",
	 * electricityBillEntity.getNetAmount() + latePaymentAmount);
	 * param.put("billDate", electricityBillEntity.getBillDate()); param.put(
	 * "billingPeriod", DateFormatUtils.format(
	 * (electricityBillEntity.getFromDate()), "dd MMM. yyyy") + " To " +
	 * DateFormatUtils.format( (electricityBillEntity.getBillDate()),
	 * "dd MMM. yyyy")); param.put("caNo", electricityBillEntity.getAccountObj()
	 * .getAccountNo()); param.put("serviceType",
	 * electricityBillEntity.getTypeOfService()); param.put("billBasis",
	 * calculationBasis); param.put("realPath", "reports/"); //
	 * "src/reports/build.jpg" String showParticulars = "Yes"; if
	 * (showParticulars.equalsIgnoreCase("Yes")) { param.put("heading1",
	 * ResourceBundle.getBundle("utils") .getString("report.CAM.heading1"));
	 * param.put("heading2", ResourceBundle.getBundle("utils")
	 * .getString("report.CAM.heading2")); param.put("heading3",
	 * ResourceBundle.getBundle("utils") .getString("report.CAM.heading3"));
	 * param.put("heading4", ResourceBundle.getBundle("utils")
	 * .getString("report.CAM.heading4"));
	 * 
	 * param.put( "report.CAM.mcPoint1",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.mcPoint1"));
	 * param.put( "report.CAM.mcPoint2",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.mcPoint2"));
	 * param.put( "report.CAM.mcPoint3",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.mcPoint3"));
	 * param.put( "report.CAM.mcPoint4",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.mcPoint4"));
	 * param.put( "report.CAM.mcPoint5",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.mcPoint5"));
	 * param.put( "report.CAM.mcPoint6",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.mcPoint6"));
	 * param.put( "report.CAM.mcPoint7",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.mcPoint7"));
	 * param.put( "report.CAM.mcPoint8",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.mcPoint8"));
	 * param.put( "report.CAM.mcPoint9",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.mcPoint9"));
	 * param.put( "report.CAM.mcPoint10",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.mcPoint10"));
	 * param.put( "report.CAM.mcPoint11",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.mcPoint11"));
	 * param.put( "report.CAM.mcPoint12",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.mcPoint12"));
	 * param.put( "report.CAM.mcPoint13",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.mcPoint13"));
	 * param.put( "report.CAM.mcPoint14",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.mcPoint14"));
	 * param.put( "report.CAM.mcPoint15",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.mcPoint15"));
	 * 
	 * param.put( "report.CAM.ucPoint1",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.ucPoint1"));
	 * param.put( "report.CAM.ucPoint2",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.ucPoint2"));
	 * param.put( "report.CAM.ucPoint3",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.ucPoint3"));
	 * param.put( "report.CAM.ucPoint4",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.ucPoint4"));
	 * 
	 * param.put( "report.CAM.ocPoint1",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.ocPoint1"));
	 * param.put( "report.CAM.ocPoint2",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.ocPoint2"));
	 * param.put( "report.CAM.ocPoint3",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.ocPoint3"));
	 * param.put( "report.CAM.ocPoint4",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.ocPoint4"));
	 * param.put( "report.CAM.ocPoint5",
	 * ResourceBundle.getBundle("utils").getString( "report.CAM.ocPoint5"));
	 * 
	 * }
	 * 
	 * List<ElectricityBillEntity> electricityBillEntities = new ArrayList<>();
	 * electricityBillEntities.add(electricityBillEntity);
	 * 
	 * JRBeanCollectionDataSource jre = new
	 * JRBeanCollectionDataSource(electricityBillEntities); JasperPrint
	 * jasperPrint; logger.info("---------- JRXL compling ----------");
	 * JasperReport report; try {
	 * logger.info("---------- filling the report -----------");
	 * 
	 * jasperPrint =
	 * JasperFillManager.fillReport(this.getClass().getClassLoader(
	 * ).getResourceAsStream("reports/CAMBILL.jasper"), param,jre); //
	 * jasperPrint
	 * =JasperFillManager.fillReport(this.getClass().getClassLoader().
	 * getResourceAsStream("D:\\Jrxml\\CAM\\CAMBILL.jasper"), // param,jre);
	 * removeBlankPage(jasperPrint.getPages()); ServletOutputStream
	 * servletOutputStream;
	 * 
	 * servletOutputStream = res.getOutputStream();
	 * res.setContentType("application/pdf");
	 * res.setHeader("Content-Disposition", "inline; filename="+ "CAM" +
	 * electricityBillEntity.getBillNo()+ ".pdf");
	 * JasperExportManager.exportReportToPdfStream
	 * (jasperPrint,servletOutputStream); servletOutputStream.flush();
	 * servletOutputStream.close();
	 * 
	 * } catch (JRException e) {
	 * logger.info("----------------- JRException || IOException");
	 * e.printStackTrace(); }
	 * 
	 * } else {
	 * 
	 * String spmQuery =
	 * "select spm from ServiceParameterMaster spm where spm.serviceType='"+
	 * electricityBillEntity.getTypeOfService()+ "' order by spm.spmSequence";
	 * String bpmQuery =
	 * "select bpm from BillParameterMasterEntity bpm where bpm.serviceType='"+
	 * electricityBillEntity.getTypeOfService()+ "' order by bpm.bvmSequence";
	 * String lineItems =
	 * "select bli from ElectricityBillLineItemEntity bli where bli.electricityBillEntity.elBillId="
	 * + elBillId+"ORDER BY bli.elBillLineId ASC";
	 * 
	 * List<ServiceParameterMaster> spmList =
	 * serviceParameterMasterService.executeSimpleQuery(spmQuery);
	 * 
	 * if (spmList.size() > 0) { for (ServiceParameterMaster entity : spmList) {
	 * 
	 * ServiceParametersEntity serviceParametersEntity =
	 * serviceParameterService.getSingleResult(
	 * "select obj from ServiceParametersEntity obj where obj.spmId="+
	 * entity.getSpmId()+ " and obj.serviceMastersEntity.accountObj.accountId="
	 * + electricityBillEntity.getAccountObj() .getAccountId()); if
	 * (serviceParametersEntity != null) { if (serviceParametersEntity
	 * .getServiceParameterMaster().getSpmName()
	 * .equalsIgnoreCase("Sanctioned KW") || serviceParametersEntity
	 * .getServiceParameterMaster() .getSpmName()
	 * .equalsIgnoreCase("Sanctioned KVA") || serviceParametersEntity
	 * .getServiceParameterMaster() .getSpmName()
	 * .equalsIgnoreCase("Sanctioned HP")) { sanctionLoad = Float
	 * .parseFloat(serviceParametersEntity .getServiceParameterValue()); } if
	 * (serviceParametersEntity
	 * .getServiceParameterMaster().getSpmName().equalsIgnoreCase
	 * ("DG Applicable")) { dgApplicable =
	 * serviceParametersEntity.getServiceParameterValue(); logger.info(
	 * "serviceParametersEntity.getServiceParameterMaster().getSpmName() == "
	 * +serviceParametersEntity.getServiceParameterMaster().getSpmName()); if
	 * (serviceParametersEntity
	 * .getServiceParameterMaster().getSpmName().equalsIgnoreCase
	 * ("Sanctioned DG")) { sanctionLoadDG =
	 * Float.parseFloat(serviceParametersEntity.getServiceParameterValue());
	 * logger.info("sanctionLoadDG ------------------- "+sanctionLoadDG); } }
	 * 
	 * if (serviceParametersEntity.getServiceParameterMaster().getSpmName().
	 * equalsIgnoreCase("Sanctioned DG")) { sanctionLoadDG =
	 * Float.parseFloat(serviceParametersEntity.getServiceParameterValue()); }
	 * 
	 * if (serviceParametersEntity .getServiceParameterMaster().getSpmName()
	 * .equalsIgnoreCase("Connection Type")) { connectionType =
	 * serviceParametersEntity .getServiceParameterValue(); }
	 * 
	 * if (serviceParametersEntity .getServiceParameterMaster().getSpmName()
	 * .equalsIgnoreCase("Voltage Level")) { voltageLevel = Double
	 * .parseDouble(serviceParametersEntity .getServiceParameterValue()); } if
	 * (serviceParametersEntity .getServiceParameterMaster().getSpmName()
	 * .equalsIgnoreCase("Connection Security")) { connectionSecurity = Float
	 * .parseFloat(serviceParametersEntity .getServiceParameterValue()); } } } }
	 * 
	 * ElectricityMetersEntity electricityMetersEntity =
	 * electricityMetersService .getMeter(electricityBillEntity.getAccountId(),
	 * electricityBillEntity.getTypeOfService()); if (electricityMetersEntity !=
	 * null) { meterSrNo = electricityMetersEntity.getMeterSerialNo(); meterType
	 * = meterParameterMasterService .getMeterParameter(electricityBillEntity
	 * .getAccountId(), electricityMetersEntity .getElMeterId(),
	 * electricityMetersEntity .getTypeOfServiceForMeters(), "Meter type");
	 * meterMake = meterParameterMasterService
	 * .getMeterParameter(electricityBillEntity .getAccountId(),
	 * electricityMetersEntity .getElMeterId(), electricityMetersEntity
	 * .getTypeOfServiceForMeters(), "Meter Make");
	 * 
	 * if (meterParameterMasterService .getMeterParameter(electricityBillEntity
	 * .getAccountId(), electricityMetersEntity .getElMeterId(),
	 * electricityMetersEntity .getTypeOfServiceForMeters(), "Meter Constant")
	 * != null) { float meterConstant1 = Float
	 * .parseFloat(meterParameterMasterService.getMeterParameter(
	 * electricityBillEntity.getAccountId(),
	 * electricityMetersEntity.getElMeterId(), electricityMetersEntity
	 * .getTypeOfServiceForMeters(), "Meter Constant")); if (meterConstant1 ==
	 * 1) { meterStatus = "Normal"; } else if (meterConstant1 < 1) { meterStatus
	 * = "Slow"; } else if (meterConstant1 > 1) { meterStatus = "Fast"; } } }
	 * 
	 * List<BillParameterMasterEntity> bpmList = billParameterMasterService
	 * .executeSimpleQuery(bpmQuery);
	 * 
	 * if (bpmList.size() > 0) { for (BillParameterMasterEntity entity :
	 * bpmList) { if (entity != null) { ElectricityBillParametersEntity
	 * billingParameterMaster = electricityBillParameterService
	 * .getSingleResult(
	 * "select obj from ElectricityBillParametersEntity obj where obj.billParameterMasterEntity.bvmId="
	 * + entity.getBvmId() +
	 * " and obj.electricityBillEntity.accountObj.accountId=" +
	 * electricityBillEntity .getAccountObj() .getAccountId() +
	 * " and obj.electricityBillEntity.elBillId=" + elBillId); if
	 * (billingParameterMaster != null) { if (billingParameterMaster.getNotes()
	 * != null) slabs = billingParameterMaster.getNotes(); } } } }
	 * List<ElectricityBillLineItemEntity> bliList =
	 * billLineItemService.executeSimpleQuery(lineItems); if (bliList.size() >
	 * 0) { for (ElectricityBillLineItemEntity electricityBillLineItemEntity :
	 * bliList) { List<TransactionMasterEntity> listTM =
	 * transactionMasterService.executeSimpleQuery(
	 * "select o from TransactionMasterEntity o where o.transactionCode='" +
	 * electricityBillLineItemEntity .getTransactionCode() + "'"); if
	 * (!listTM.isEmpty()) { electricityBillLineItemEntity
	 * .setTransactionName(listTM.get(0) .getTransactionName()); } } }
	 * List<ElectricityBillLineItemEntity> arrearsList = new ArrayList<>();
	 * ElectricityBillEntity previoisBill; if
	 * (electricityBillEntity.getArrearsAmount() > 0) { previoisBill =
	 * electricityBillsService .getPreviousBillWithOutStatus(
	 * electricityBillEntity.getAccountId(),
	 * electricityBillEntity.getTypeOfService(),
	 * electricityBillEntity.getFromDate(), postType); if (previoisBill != null)
	 * { arrearsAmount = previoisBill.getNetAmount(); for
	 * (ElectricityBillLineItemEntity previousBillLineItems : previoisBill
	 * .getBillLineItemEntities()) {
	 * 
	 * List<TransactionMasterEntity> listTM =
	 * transactionMasterService.executeSimpleQuery
	 * ("select o from TransactionMasterEntity o where o.transactionCode='"+
	 * previousBillLineItems .getTransactionCode() + "'"); if
	 * (!listTM.isEmpty()) { previousBillLineItems.setTransactionName(listTM
	 * .get(0).getTransactionName()); arrearsList.add(previousBillLineItems); }
	 * } } }
	 * 
	 * List<ServiceMastersEntity> serviceMastersList = serviceMasterService
	 * .executeSimpleQuery
	 * ("select obj from ServiceMastersEntity obj where obj.accountObj.accountId="
	 * + electricityBillEntity.getAccountObj() .getAccountId() +
	 * " and obj.typeOfService='" + electricityBillEntity.getTypeOfService() +
	 * "'");
	 * 
	 * if (!serviceMastersList.isEmpty() && serviceMastersList.size() > 0) { for
	 * (ServiceMastersEntity serviceMastersEntity : serviceMastersList) {
	 * 
	 * String paraMeterName5 = "Units"; String uomValueString = null; if
	 * (electricityBillParameterService.getParameterValue(
	 * electricityBillEntity.getElBillId(),
	 * electricityBillEntity.getTypeOfService(), paraMeterName5) != null) {
	 * uomValueString = electricityBillParameterService
	 * .getParameterValue(electricityBillEntity .getElBillId(),
	 * electricityBillEntity .getTypeOfService(), paraMeterName5); if
	 * (uomValueString != null) uomValue = Float.parseFloat(uomValueString); }
	 * 
	 * String paraMeterName6 = "Number of days"; String numberOfDaysString =
	 * null; if (electricityBillParameterService.getParameterValue(
	 * electricityBillEntity.getElBillId(),
	 * electricityBillEntity.getTypeOfService(), paraMeterName6) != null) {
	 * numberOfDaysString = electricityBillParameterService
	 * .getParameterValue(electricityBillEntity .getElBillId(),
	 * electricityBillEntity .getTypeOfService(), paraMeterName6); if
	 * (numberOfDaysString != null) numberOfDays = Float
	 * .parseFloat(numberOfDaysString); }
	 * 
	 * String paraMeterName7 = "Present reading"; String presentReadingString =
	 * null; if (electricityBillParameterService.getParameterValue(
	 * electricityBillEntity.getElBillId(),
	 * electricityBillEntity.getTypeOfService(), paraMeterName7) != null) {
	 * presentReadingString = electricityBillParameterService
	 * .getParameterValue(electricityBillEntity .getElBillId(),
	 * electricityBillEntity .getTypeOfService(), paraMeterName7); if
	 * (presentReadingString != null) presentReading = Float
	 * .parseFloat(presentReadingString); }
	 * 
	 * String paraMeterName8 = "Previous reading"; String previousReadingString
	 * = null; if (electricityBillParameterService.getParameterValue(
	 * electricityBillEntity.getElBillId(),
	 * electricityBillEntity.getTypeOfService(), paraMeterName8) != null) {
	 * previousReadingString = electricityBillParameterService
	 * .getParameterValue(electricityBillEntity .getElBillId(),
	 * electricityBillEntity .getTypeOfService(), paraMeterName8); if
	 * (previousReadingString != null) previousReading = Float
	 * .parseFloat(previousReadingString); }
	 * 
	 * String paraMeterName9 = "Meter Constant"; String meterConstantString =
	 * null; if (electricityBillParameterService.getParameterValue(
	 * electricityBillEntity.getElBillId(),
	 * electricityBillEntity.getTypeOfService(), paraMeterName9) != null) {
	 * meterConstantString = electricityBillParameterService
	 * .getParameterValue(electricityBillEntity .getElBillId(),
	 * electricityBillEntity .getTypeOfService(), paraMeterName9); if
	 * (meterConstantString != null) meterConstant = Float
	 * .parseFloat(meterConstantString); }
	 * 
	 * String paraMeterName10 = "DG Units"; String dgUnitsString = null; if
	 * (electricityBillParameterService.getParameterValue(
	 * electricityBillEntity.getElBillId(),
	 * electricityBillEntity.getTypeOfService(), paraMeterName10) != null) {
	 * dgUnitsString = electricityBillParameterService
	 * .getParameterValue(electricityBillEntity .getElBillId(),
	 * electricityBillEntity .getTypeOfService(), paraMeterName10); if
	 * (dgUnitsString != null) dgUnits = Float.parseFloat(dgUnitsString); }
	 * 
	 * if (serviceMastersEntity.getTypeOfService()
	 * .equalsIgnoreCase("Electricity")) { ELTariffMaster eltariffMaster =
	 * elTariffMasterService
	 * .getSingleResult("select o from ELTariffMaster o where o.elTariffID=" +
	 * serviceMastersEntity .getElTariffID()); if (eltariffMaster != null) {
	 * elTariffId = serviceMastersEntity .getElTariffID(); tariffName =
	 * eltariffMaster.getTariffName(); }
	 * 
	 * } else if (serviceMastersEntity.getTypeOfService()
	 * .equalsIgnoreCase("Gas")) { GasTariffMaster gasTariffMaster =
	 * gasTariffMasterService
	 * .getSingleResult("select o from GasTariffMaster o where o.gasTariffId=" +
	 * serviceMastersEntity .getGaTariffID()); if (gasTariffMaster != null) {
	 * tariffName = gasTariffMaster.getGastariffName(); gasTariffId =
	 * gasTariffMaster.getGasTariffId(); } } else if
	 * (serviceMastersEntity.getTypeOfService() .equalsIgnoreCase("Water")) {
	 * List<WTTariffMaster> wttariffMaster = wtTariffMasterService
	 * .executeSimpleQuery("select o from WTTariffMaster o where o.wtTariffId="
	 * + serviceMastersEntity .getWtTariffID()); if (wttariffMaster != null &&
	 * wttariffMaster.size() > 0) { tariffName = wttariffMaster.get(0)
	 * .getTariffName(); wtTariffId = wttariffMaster.get(0) .getWtTariffId(); }
	 * } else if (serviceMastersEntity.getTypeOfService()
	 * .equalsIgnoreCase("Solid Waste")) { SolidWasteTariffMaster
	 * solidWasteTariffMaster = solidWasteTariffMasterService .getSingleResult(
	 * "select o from SolidWasteTariffMaster o where o.solidWasteTariffId=" +
	 * serviceMastersEntity .getSwTariffID()); if (solidWasteTariffMaster !=
	 * null) { tariffName = solidWasteTariffMaster .getSolidWasteTariffName();
	 * solidWasteTariffId = solidWasteTariffMaster .getSolidWasteTariffId(); } }
	 * else if (serviceMastersEntity.getTypeOfService()
	 * .equalsIgnoreCase("Others")) { CommonTariffMaster otherTariffMaster =
	 * commonServiceTariffMasterServices
	 * .getSingleResult("select o from CommonTariffMaster o where o.csTariffID="
	 * + serviceMastersEntity .getOthersTariffID()); if (otherTariffMaster !=
	 * null) { othersTariffName = otherTariffMaster .getCsTariffName();
	 * othersTariffId = otherTariffMaster .getCsTariffID(); } } } }
	 * 
	 * if (electricityBillEntity.getTypeOfService().equalsIgnoreCase(
	 * "Electricity")) { if (electricityMetersEntity.getMeterType()
	 * .equalsIgnoreCase("Trivector Meter")) {
	 * logger.info("------------- PF and MDI is applicable ----------------");
	 * String paraMeterName10 = "PF"; String pfString = null; if
	 * (electricityBillParameterService.getParameterValue(
	 * electricityBillEntity.getElBillId(),
	 * electricityBillEntity.getTypeOfService(), paraMeterName10) != null) {
	 * pfString = electricityBillParameterService
	 * .getParameterValue(electricityBillEntity .getElBillId(),
	 * electricityBillEntity .getTypeOfService(), paraMeterName10); pfValue =
	 * Float.parseFloat(pfString); } String paraMeterName11 =
	 * "Maximum Demand Recorded"; String mdiString = null; if
	 * (electricityBillParameterService.getParameterValue(
	 * electricityBillEntity.getElBillId(),
	 * electricityBillEntity.getTypeOfService(), paraMeterName11) != null) {
	 * mdiString = electricityBillParameterService
	 * .getParameterValue(electricityBillEntity .getElBillId(),
	 * electricityBillEntity .getTypeOfService(), paraMeterName11); mdiValue =
	 * Float.parseFloat(mdiString); } } } List<LastSixMonthsBills>
	 * lastSixMonthsBills = new ArrayList<>(); if
	 * (electricityBillEntity.getTypeOfService().equalsIgnoreCase(
	 * "Electricity")) { slabDetailsList = getTariffSlabList(elTariffId,
	 * electricityBillEntity.getFromDate(), electricityBillEntity.getBillDate(),
	 * uomValue, locale); int rateMasterID = rateMasterService.getRateMasterEC(
	 * elTariffId, "Rate of interest");
	 * 
	 * for (ElectricityBillLineItemEntity electricityBillLineItemEntity :
	 * electricityBillEntity .getBillLineItemEntities()) {
	 * 
	 * if (electricityBillLineItemEntity.getTransactionCode()
	 * .equalsIgnoreCase("EL_TAX")) { taxAmount = electricityBillLineItemEntity
	 * .getBalanceAmount(); } if
	 * (electricityBillLineItemEntity.getTransactionCode()
	 * .equalsIgnoreCase("EL_INTEREST")) { interestAmount =
	 * electricityBillLineItemEntity .getBalanceAmount(); } if
	 * (electricityBillLineItemEntity.getTransactionCode()
	 * .equalsIgnoreCase("EL_TAX_INTEREST")) { interestOnTax =
	 * electricityBillLineItemEntity .getBalanceAmount(); } }
	 * amountForInteresetCal = (float) (electricityBillEntity .getNetAmount() -
	 * (interestOnTax + interestAmount + taxAmount)); if (rateMasterID != 0) {
	 * latePaymentAmount =
	 * interestCalculationEL(rateMasterID,amountForInteresetCal,
	 * electricityBillEntity); }
	 * 
	 * if (dgApplicable.equalsIgnoreCase("Yes")) { dgSlabDetailsList =
	 * getTariffSlabStringDGList
	 * (elTariffId,electricityBillEntity.getFromDate(),electricityBillEntity
	 * .getBillDate(), dgUnits,locale); }
	 * 
	 * } else if (electricityBillEntity.getTypeOfService()
	 * .equalsIgnoreCase("Water")) { slabDetailsList =
	 * getWaterTariffSlabList(wtTariffId, electricityBillEntity.getFromDate(),
	 * electricityBillEntity.getBillDate(), uomValue, locale); for
	 * (ElectricityBillLineItemEntity electricityBillLineItemEntity :
	 * electricityBillEntity .getBillLineItemEntities()) {
	 * 
	 * if (electricityBillLineItemEntity.getTransactionCode()
	 * .equalsIgnoreCase("WT_TAX")) { taxAmount = electricityBillLineItemEntity
	 * .getBalanceAmount(); } if
	 * (electricityBillLineItemEntity.getTransactionCode()
	 * .equalsIgnoreCase("WT_INTEREST")) { interestAmount =
	 * electricityBillLineItemEntity .getBalanceAmount(); } if
	 * (electricityBillLineItemEntity.getTransactionCode()
	 * .equalsIgnoreCase("WT_TAX_INTEREST")) { interestOnTax =
	 * electricityBillLineItemEntity .getBalanceAmount(); } } int rateMasterID =
	 * rateMasterService .getRateMasterDomesticGeneral(wtTariffId,
	 * "Rate of interest"); amountForInteresetCal = (float)
	 * (electricityBillEntity .getNetAmount() - (interestOnTax + interestAmount
	 * + taxAmount)); if (rateMasterID != 0) { latePaymentAmount =
	 * interestCalculationWt(rateMasterID, amountForInteresetCal,
	 * electricityBillEntity); }
	 * 
	 * List<?> lastSixMontsBills = electricityBillsService
	 * .getLastSixMontsBills( electricityBillEntity.getAccountId(),
	 * electricityBillEntity.getTypeOfService(),
	 * electricityBillEntity.getBillDate(), "Units"); for (Iterator<?> iterator
	 * = lastSixMontsBills.iterator(); iterator .hasNext();) {
	 * LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills(); final
	 * Object[] values = (Object[]) iterator.next(); map.put(new
	 * SimpleDateFormat("MMM") .format((Date) values[0]), (Double) values[2]);
	 * sixMonthsBills.setMonth((Date) values[0]); sixMonthsBills.setUnits(Double
	 * .parseDouble((String) values[1])); sixMonthsBills.setAmount((Double)
	 * values[2]); lastSixMonthsBills.add(sixMonthsBills); } } else if
	 * (electricityBillEntity.getTypeOfService() .equalsIgnoreCase("Gas")) {
	 * slabDetailsList = getGasTariffSlabList(gasTariffId,
	 * electricityBillEntity.getFromDate(), electricityBillEntity.getBillDate(),
	 * uomValue, locale); for (ElectricityBillLineItemEntity
	 * electricityBillLineItemEntity : electricityBillEntity
	 * .getBillLineItemEntities()) {
	 * 
	 * if (electricityBillLineItemEntity.getTransactionCode()
	 * .equalsIgnoreCase("GA_TAX")) { taxAmount = electricityBillLineItemEntity
	 * .getBalanceAmount(); } if
	 * (electricityBillLineItemEntity.getTransactionCode()
	 * .equalsIgnoreCase("GA_INTEREST")) { interestAmount =
	 * electricityBillLineItemEntity .getBalanceAmount(); } if
	 * (electricityBillLineItemEntity.getTransactionCode()
	 * .equalsIgnoreCase("GA_TAX_INTEREST")) { interestOnTax =
	 * electricityBillLineItemEntity .getBalanceAmount(); } } int rateMasterID =
	 * rateMasterService.getRateMasterGas( gasTariffId, "Rate of interest");
	 * amountForInteresetCal = (float) (electricityBillEntity .getNetAmount() -
	 * (interestOnTax + interestAmount + taxAmount)); if (rateMasterID != 0) {
	 * latePaymentAmount = interestCalculationGas( rateMasterID,
	 * amountForInteresetCal, electricityBillEntity); }
	 * 
	 * List<?> lastSixMontsBills = electricityBillsService
	 * .getLastSixMontsBills( electricityBillEntity.getAccountId(),
	 * electricityBillEntity.getTypeOfService(),
	 * electricityBillEntity.getBillDate(), "Units"); for (Iterator<?> iterator
	 * = lastSixMontsBills.iterator(); iterator .hasNext();) {
	 * LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills(); final
	 * Object[] values = (Object[]) iterator.next(); map.put(new
	 * SimpleDateFormat("MMM") .format((Date) values[0]), (Double) values[2]);
	 * sixMonthsBills.setMonth((Date) values[0]); sixMonthsBills.setUnits(Double
	 * .parseDouble((String) values[1])); sixMonthsBills.setAmount((Double)
	 * values[2]); lastSixMonthsBills.add(sixMonthsBills); }
	 * 
	 * } else if (electricityBillEntity.getTypeOfService()
	 * .equalsIgnoreCase("Solid Waste")) { String paraMeterName5 = "Volume(KG)";
	 * String uomValueString = null; float solidWasterUnits = 0.0f; if
	 * (electricityBillParameterService.getParameterValue(
	 * electricityBillEntity.getElBillId(),
	 * electricityBillEntity.getTypeOfService(), paraMeterName5) != null) {
	 * uomValueString = electricityBillParameterService
	 * .getParameterValue(electricityBillEntity .getElBillId(),
	 * electricityBillEntity .getTypeOfService(), paraMeterName5); if
	 * (uomValueString != null) solidWasterUnits =
	 * Float.parseFloat(uomValueString); } slabDetailsList =
	 * getSolidWasteTariffSlabList( solidWasteTariffId,
	 * electricityBillEntity.getFromDate(), electricityBillEntity.getBillDate(),
	 * solidWasterUnits, locale); for (ElectricityBillLineItemEntity
	 * electricityBillLineItemEntity : electricityBillEntity
	 * .getBillLineItemEntities()) { if
	 * (electricityBillLineItemEntity.getTransactionCode()
	 * .equalsIgnoreCase("SW_TAX")) { taxAmount = electricityBillLineItemEntity
	 * .getBalanceAmount(); } if
	 * (electricityBillLineItemEntity.getTransactionCode()
	 * .equalsIgnoreCase("SW_INTEREST")) { interestAmount =
	 * electricityBillLineItemEntity .getBalanceAmount(); } if
	 * (electricityBillLineItemEntity.getTransactionCode()
	 * .equalsIgnoreCase("SW_TAX_INTEREST")) { interestOnTax =
	 * electricityBillLineItemEntity .getBalanceAmount(); } }
	 * 
	 * int rateMasterID = rateMasterService.getRateMasterSW( solidWasteTariffId,
	 * "Rate of interest"); amountForInteresetCal = (float)
	 * (electricityBillEntity .getNetAmount() - (interestOnTax + interestAmount
	 * + taxAmount)); if (rateMasterID != 0) { latePaymentAmount =
	 * interestCalculationSW(rateMasterID, amountForInteresetCal,
	 * electricityBillEntity); }
	 * 
	 * List<?> lastSixMontsBills = electricityBillsService
	 * .getLastSixMontsBills( electricityBillEntity.getAccountId(),
	 * electricityBillEntity.getTypeOfService(),
	 * electricityBillEntity.getBillDate(), "Volume(KG)"); for (Iterator<?>
	 * iterator = lastSixMontsBills.iterator(); iterator .hasNext();) {
	 * LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills(); final
	 * Object[] values = (Object[]) iterator.next(); map.put(new
	 * SimpleDateFormat("MMM") .format((Date) values[0]), (Double) values[2]);
	 * sixMonthsBills.setMonth((Date) values[0]); sixMonthsBills.setUnits(Double
	 * .parseDouble((String) values[1])); sixMonthsBills.setAmount((Double)
	 * values[2]); lastSixMonthsBills.add(sixMonthsBills); } } else if
	 * (electricityBillEntity.getTypeOfService() .equalsIgnoreCase("Others")) {
	 * for (ElectricityBillLineItemEntity electricityBillLineItemEntity :
	 * electricityBillEntity .getBillLineItemEntities()) {
	 * 
	 * if (electricityBillLineItemEntity.getTransactionCode()
	 * .equalsIgnoreCase("OT_TAX")) { taxAmount = electricityBillLineItemEntity
	 * .getBalanceAmount(); } if
	 * (electricityBillLineItemEntity.getTransactionCode()
	 * .equalsIgnoreCase("OT_INTEREST")) { interestAmount =
	 * electricityBillLineItemEntity .getBalanceAmount(); } if
	 * (electricityBillLineItemEntity.getTransactionCode()
	 * .equalsIgnoreCase("OT_TAX_INTEREST")) { interestOnTax =
	 * electricityBillLineItemEntity .getBalanceAmount(); } }
	 * 
	 * int rateMasterID = rateMasterService.getRateMasterOT( othersTariffId,
	 * "Rate of interest"); amountForInteresetCal = (float)
	 * (electricityBillEntity .getNetAmount() - (interestOnTax + interestAmount
	 * + taxAmount)); if (rateMasterID != 0) { latePaymentAmount =
	 * interestCalculationOT(rateMasterID, amountForInteresetCal,
	 * electricityBillEntity); }
	 * 
	 * List<?> lastSixMontsBills = electricityBillsService
	 * .getLastSixMontsBillsOthers( electricityBillEntity.getAccountId(),
	 * electricityBillEntity.getTypeOfService(),
	 * electricityBillEntity.getBillDate(), "Units"); for (Iterator<?> iterator
	 * = lastSixMontsBills.iterator(); iterator .hasNext();) {
	 * LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills(); final
	 * Object[] values = (Object[]) iterator.next(); map.put(new
	 * SimpleDateFormat("MMM") .format((Date) values[0]), (Double) values[2]);
	 * sixMonthsBills.setMonth((Date) values[0]); sixMonthsBills.setUnits(Double
	 * .parseDouble((String) values[1])); sixMonthsBills.setAmount((Double)
	 * values[2]); lastSixMonthsBills.add(sixMonthsBills); } }
	 * 
	 * else if (electricityBillEntity.getTypeOfService()
	 * .equalsIgnoreCase("Telephone Broadband")) { List<?> lastSixMontsBills =
	 * electricityBillsService .getLastSixMontsBillsOthers(
	 * electricityBillEntity.getAccountId(),
	 * electricityBillEntity.getTypeOfService(),
	 * electricityBillEntity.getBillDate(), "Units"); for (Iterator<?> iterator
	 * = lastSixMontsBills.iterator(); iterator .hasNext();) {
	 * LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills(); final
	 * Object[] values = (Object[]) iterator.next(); map.put(new
	 * SimpleDateFormat("MMM") .format((Date) values[0]), (Double) values[2]);
	 * sixMonthsBills.setMonth((Date) values[0]); sixMonthsBills.setUnits(Double
	 * .parseDouble((String) values[1])); sixMonthsBills.setAmount((Double)
	 * values[2]); lastSixMonthsBills.add(sixMonthsBills); } }
	 * 
	 * HashMap<String, Object> param = new HashMap<String, Object>();
	 * BillingPaymentsEntity billingPaymentsEntity = paymentService
	 * .getPamentDetals(electricityBillEntity.getAccountId(),
	 * electricityBillEntity.getFromDate());
	 * 
	 * if (billingPaymentsEntity != null) { param.put("paymentAmount",
	 * billingPaymentsEntity.getPaymentAmount()); param.put("receiptNo",
	 * billingPaymentsEntity.getReceiptNo()); param.put("receiptDate",
	 * billingPaymentsEntity.getReceiptDate()); param.put("modeOfPay",
	 * billingPaymentsEntity.getPaymentMode()); }
	 * 
	 * JRBeanCollectionDataSource subReportDS = new JRBeanCollectionDataSource(
	 * bliList); JRBeanCollectionDataSource subReport1DS = new
	 * JRBeanCollectionDataSource( arrearsList); JRBeanCollectionDataSource
	 * subReport2DS = new JRBeanCollectionDataSource( lastSixMonthsBills);
	 * JRBeanCollectionDataSource subReport3DS = new JRBeanCollectionDataSource(
	 * slabDetailsList); JRBeanCollectionDataSource subReport4DS = new
	 * JRBeanCollectionDataSource( dgSlabDetailsList);
	 * JRBeanCollectionDataSource subReport5DS = new JRBeanCollectionDataSource(
	 * lastSixMonthsBills);
	 * 
	 * param.put("subreport_datasource", subReportDS);
	 * param.put("subreport_datasource1", subReport1DS);
	 * param.put("subreport_datasource2", subReport2DS);
	 * param.put("subreport_datasource3", subReport3DS);
	 * param.put("subreport_datasource4", subReport4DS);
	 * param.put("subreport_datasource5", subReport5DS);
	 * 
	 * param.put( "title", ResourceBundle.getBundle("utils").getString(
	 * "report.title")); param.put("companyAddress",
	 * ResourceBundle.getBundle("utils") .getString("report.address"));
	 * param.put("point1", ResourceBundle.getBundle("utils")
	 * .getString("report.point1")); param.put("point2",
	 * ResourceBundle.getBundle("utils") .getString("report.point2"));
	 * param.put("point3", ResourceBundle.getBundle("utils")
	 * .getString("report.point3")); param.put("point4",
	 * ResourceBundle.getBundle("utils") .getString("report.point4"));
	 * param.put("point5.1", ResourceBundle.getBundle("utils")
	 * .getString("report.point5.1")); param.put("point5.2",
	 * ResourceBundle.getBundle("utils") .getString("report.point5.2"));
	 * param.put("point5.3", ResourceBundle.getBundle("utils")
	 * .getString("report.point5.3")); param.put("point6",
	 * ResourceBundle.getBundle("utils") .getString("report.point6"));
	 * 
	 * param.put("amountPayble", electricityBillEntity.getNetAmount());
	 * param.put("dueDate", electricityBillEntity.getBillDueDate());
	 * if(electricityBillEntity
	 * .getAccountObj().getPerson().getFirstName()!=null) param.put("name",
	 * electricityBillEntity
	 * .getAccountObj().getPerson().getFirstName()+" "+electricityBillEntity
	 * .getAccountObj().getPerson().getLastName()); param.put("address",
	 * address1); param.put("city", city); param.put("mobileNo",
	 * toAddressMobile); param.put("emailId", toAddressEmail);
	 * param.put("sanctionedUtility", Math.round(sanctionLoad) + " kW"); String
	 * sanctionedDG = Math.round(sanctionLoadDG) + " kW"; if
	 * (sanctionedDG.equalsIgnoreCase("0 kW")) { param.put("sanctionedDG",
	 * "NA"); } else { param.put("sanctionedDG", Math.round(sanctionLoadDG) +
	 * " kW"); } String voltage = Math.round(voltageLevel) + " V"; if
	 * (voltage.equalsIgnoreCase("0 V")) { param.put("voltageLevel", "NA"); }
	 * else { param.put("voltageLevel", Math.round(voltageLevel) + " V"); }
	 * 
	 * param.put("meterType", meterType); param.put("connectionType",
	 * connectionType); param.put("connectionSecurity", connectionSecurity);
	 * param.put("pF", pfValue); param.put("surcharge", latePaymentAmount);
	 * param.put("billNo", electricityBillEntity.getBillNo());
	 * param.put("amountAfterDueDate", electricityBillEntity.getNetAmount() +
	 * latePaymentAmount); param.put("billDate",
	 * electricityBillEntity.getBillDate()); param.put( "billingPeriod",
	 * DateFormatUtils.format( (electricityBillEntity.getFromDate()),
	 * "dd MMM. yyyy") + " To " + DateFormatUtils.format(
	 * (electricityBillEntity.getBillDate()), "dd MMM. yyyy"));
	 * param.put("caNo", electricityBillEntity.getAccountObj() .getAccountNo());
	 * param.put("serviceType", electricityBillEntity.getTypeOfService());
	 * param.put("tariffCategory", tariffName); param.put("billBasis",
	 * billType); param.put("meterMake", meterMake); param.put("meterSrNo",
	 * meterSrNo); param.put("meterStatus", meterStatus); param.put("mf",
	 * meterConstant); param.put("energyType",
	 * electricityBillEntity.getTypeOfService() + "/" + meterSrNo);
	 * param.put("mdi", mdiValue); param.put("presentReading", presentReading);
	 * param.put("previousReading", previousReading);
	 * param.put("previousBillDate", electricityBillEntity.getFromDate());
	 * param.put("presentBillDate", electricityBillEntity.getBillDate());
	 * param.put("units", uomValue); param.put("days",
	 * Math.round(numberOfDays)); param.put("billAmount",
	 * electricityBillEntity.getBillAmount()); param.put("arrearsAmount",
	 * electricityBillEntity.getArrearsAmount()); String amountInWords =
	 * NumberToWord .convertNumberToWords(electricityBillEntity
	 * .getBillAmount().intValue()); param.put("amountInWords", amountInWords +
	 * " Only"); param.put("realPath", "reports/"); param.put("panNo",
	 * "AACAG1252D"); param.put("sTaxNo", "AACAG1252DSD001");
	 * JRBeanCollectionDataSource jre = new JRBeanCollectionDataSource(
	 * bliList); JasperPrint jasperPrint;
	 * logger.info("---------- JRXL loading -----------");
	 * 
	 * InputStream in = this.getClass().getClassLoader().getResourceAsStream
	 * ("Bill.jrxml"); if(in != null){
	 * 
	 * logger.info("---------- JRXL compling -----------"); JasperReport report;
	 * try { // report = JasperCompileManager.compileReport(in);
	 * logger.info("---------- filling the report -----------"); //
	 * jasperPrint=JasperFillManager.fillReport(report, // param,jre); //
	 * this.getClass
	 * ().getClassLoader().getResourceAsStream("reports/CAMBILL.jrxml"); if
	 * (electricityBillEntity.getTypeOfService()
	 * .equalsIgnoreCase("Electricity")) { param.put("energyType",
	 * electricityBillEntity.getTypeOfService() + "/" + meterSrNo); jasperPrint
	 * = JasperFillManager.fillReport(this .getClass().getClassLoader()
	 * .getResourceAsStream("reports/Bill.jasper"), param, jre); } else if
	 * (electricityBillEntity.getTypeOfService() .equalsIgnoreCase("Water") ||
	 * electricityBillEntity.getTypeOfService() .equalsIgnoreCase("Gas")) {
	 * param.put("energyType", electricityBillEntity.getTypeOfService() + "/" +
	 * meterSrNo); jasperPrint = JasperFillManager.fillReport(this
	 * .getClass().getClassLoader()
	 * .getResourceAsStream("reports/WGBill.jasper"), param, jre); } else if
	 * (electricityBillEntity.getTypeOfService()
	 * .equalsIgnoreCase("Solid Waste")) { param.put("energyType",
	 * electricityBillEntity.getTypeOfService()); jasperPrint =
	 * JasperFillManager.fillReport(this .getClass().getClassLoader()
	 * .getResourceAsStream("reports/SWBill.jasper"), param, jre); } else if
	 * (electricityBillEntity.getTypeOfService() .equalsIgnoreCase("Others")) {
	 * param.put("tariffCategory", othersTariffName); param.put("energyType",
	 * electricityBillEntity.getTypeOfService()); jasperPrint =
	 * JasperFillManager.fillReport(this .getClass().getClassLoader()
	 * .getResourceAsStream("reports/OTBill.jasper"), param, jre); } else {
	 * param.put("tariffCategory", "Telephone Broadband");
	 * param.put("energyType",electricityBillEntity.getTypeOfService());
	 * jasperPrint =
	 * JasperFillManager.fillReport(this.getClass().getClassLoader(
	 * ).getResourceAsStream("reports/OTBill.jasper"),param, jre); }
	 * removeBlankPage(jasperPrint.getPages()); // ServletOutputStream
	 * servletOutputStream = null;
	 * 
	 * //servletOutputStream = res.getOutputStream();
	 * //res.setContentType("application/pdf");
	 * //res.setHeader("Content-Disposition", "inline; filename="+
	 * electricityBillEntity.getTypeOfService()+"_"+property.getProperty_No()+
	 * ".pdf"); //
	 * JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream
	 * ); byte[] bytes =JasperExportManager.exportReportToPdf(jasperPrint); Blob
	 * blob = Hibernate.createBlob(bytes);
	 * logger.info("blob ======================= "+blob); String path =
	 * "D:\\Bills\\"
	 * +DateFormatUtils.format((electricityBillEntity.getBillDate())
	 * ,"MMM yyyy"); boolean result = false;
	 * 
	 * File directory = new File(path);
	 * 
	 * if (directory.exists()) { System.out.println("Folder already exists");
	 * 
	 * JasperExportManager.exportReportToPdfFile(jasperPrint,path+"\\"+
	 * electricityBillEntity
	 * .getTypeOfService()+electricityBillEntity.getBillNo() + ".pdf"); } else {
	 * result = directory.mkdirs();
	 * 
	 * logger.info(
	 * "====================== directory created successfully. =========================== "
	 * ); JasperExportManager.exportReportToPdfFile(jasperPrint,path+"\\"+
	 * electricityBillEntity.getTypeOfService()+
	 * electricityBillEntity.getBillNo() + ".pdf"); }
	 * 
	 * //servletOutputStream.flush(); // servletOutputStream.close();
	 * 
	 * } catch (JRException e) { logger.info("----------------- JRException");
	 * e.printStackTrace(); } } } }
	 */

	public Float interestCalculationOT(int rateMasterID,
			float amountForInteresetCal,
			ElectricityBillEntity electricityBillEntity) {

		/*
		 * List<InterestSettingsEntity> interestSettingsEntities =
		 * interestSettingService.findAllData(); String interestType = null; for
		 * (InterestSettingsEntity interestSettingsEntity :
		 * interestSettingsEntities) { interestType =
		 * interestSettingsEntity.getInterestBasedOn();
		 * logger.info(" Interest Calculation base on -->" + interestType); }
		 */

		String configName = "Interest Calculation";
		String status = "Active";
		String interestType = electricityBillsService.getBillingConfigValue(
				configName, status);
		logger.info("interestType ==================== " + interestType);

		Map<Object, Object> consolidatedBill = new HashMap<>();
		Float interestAmountAfterDueDate = 0.0f;
		CommonServicesRateMaster elRateMaster = commonServicesRateMasterService
				.find(rateMasterID);
		if (interestType != null) {
			if (interestType.trim().equalsIgnoreCase("Daywise")) {
				logger.info("--------------------- Interest calculation based on ----"
						+ interestType);
				Calendar cal = Calendar.getInstance();
				cal.setTime(electricityBillEntity.getBillDueDate());
				cal.add(Calendar.DATE, 15);
				Date nextBillDate = cal.getTime();
				logger.info("nextBillDate ------------------ " + nextBillDate);
				consolidatedBill = calculationController
						.interstAmountCommonService(elRateMaster,
								electricityBillEntity.getBillDueDate(),
								nextBillDate, amountForInteresetCal);
				for (Entry<Object, Object> map1 : consolidatedBill.entrySet()) {
					if (map1.getKey().equals("total")) {
						interestAmountAfterDueDate = interestAmountAfterDueDate
								+ (Float) map1.getValue();
					}
				}
			} else {
				logger.info("--------------------- Interest calculation based on ----"
						+ interestType);

				Calendar cal = Calendar.getInstance();
				cal.setTime(electricityBillEntity.getBillDueDate());
				cal.add(Calendar.DATE, 30);
				Date nextBillDate = cal.getTime();
				logger.info("nextBillDate ------------------ " + nextBillDate);
				consolidatedBill = calculationController
						.interstAmountCommonService(elRateMaster,
								electricityBillEntity.getBillDueDate(),
								nextBillDate, amountForInteresetCal);
				for (Entry<Object, Object> map1 : consolidatedBill.entrySet()) {
					if (map1.getKey().equals("total")) {
						interestAmountAfterDueDate = interestAmountAfterDueDate
								+ (Float) map1.getValue();
					}
				}
			}
		}
		return interestAmountAfterDueDate;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/bill/getbilltable", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	Map<String, Object> detailedBillPopup(
			@RequestParam("elBillId") int elBillId, HttpServletResponse res,
			Locale locale) {
		Map<String, Object> map = new LinkedHashMap<>();
		Address address = null;
		Contact contactMob = null;
		Contact contactEmail = null;
		String tariffName = "";
		String slabs = "";
		String bpmStr = "";
		String mpmStr = "";
		String bliStr = "";
		String spmStr = "";
		String postType = "";
		String str = "";
		String meterSrNo = "";
		String meterType = "";
		String meterMake = "";
		String slabString = "";
		double sanctionLoad = 0.0f;
		double sanctionLoadDG = 0.0f;
		String arrearString = "";
		Float uomValue = 0.0f;
		Float numberOfDays = 0.0f;
		Float presentReading = 0.0f;
		Float previousReading = 0.0f;
		Float meterConstant = 0.0f;
		int elTariffId = 0;
		Double pfValue = 0.0;
		Float mdiValue = 0.0f;
		int solidWasteTariffId = 0;
		int wtTariffId = 0;
		int gasTariffId = 0;
		String dgApplicable = "";
		String dgSlabString = "";
		Double arrearsAmount = 0.0;
		String meterStatus = "";
		float voltageLevel = 0.0f;
		String connectionType = "";
		Double interestOnTax = 0.0;
		Double interestAmount = 0.0;
		Double taxAmount = 0.0;
		float amountForInteresetCal = 0.0f;
		Float latePaymentAmount = 0.0f;
		Double connectionSecurity = 0.0;
		ElectricityBillEntity electricityBillEntity = electricityBillsService
				.find(elBillId);

		if (electricityBillEntity != null) {

			postType = electricityBillEntity.getPostType();

			int personId = electricityBillEntity.getAccountObj().getPerson()
					.getPersonId();
			Object[] addressDetailsList = camConsolidationService
					.getAddressDetailsForMail(personId);
			List<String> contactDetailsList = camConsolidationService
					.getContactDetailsForMail(personId);

			String address1 = (String) addressDetailsList[0];
			String city = (String) addressDetailsList[1];

			String toAddressEmail = "";
			String toAddressMobile = "";

			for (Iterator<?> iterator = contactDetailsList.iterator(); iterator
					.hasNext();) {
				final Object[] values = (Object[]) iterator.next();
				if (((String) values[0]).equals("Email")) {
					toAddressEmail = (String) values[1];
				} else {
					toAddressMobile = (String) values[1];
				}
			}

			if (electricityBillEntity.getTypeOfService().equals("CAM")) {

				String lastSixBills = "";
				List<?> lastSixMontsBills = electricityBillsService
						.getLastSixMonthsCAMBills(
								electricityBillEntity.getAccountId(),
								electricityBillEntity.getTypeOfService(),
								electricityBillEntity.getBillDate());
				for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator
						.hasNext();) {
					final Object[] values = (Object[]) iterator.next();
					map.put(new SimpleDateFormat("MMM")
							.format((Date) values[0]), (Double) values[1]);
					lastSixBills += "<tr><td style='' ><p>"
							+ DateFormatUtils.format((Date) values[0],
									"MMM-yyyy")
							+ "</p></td><td style='' ><p>PSF</p></td><td style='' ><p>"
							+ (Double) values[1] + " </p></td></tr>";
				}

				BillingPaymentsEntity billingPaymentsEntity = paymentService
						.getPamentDetals(electricityBillEntity.getAccountId(),
								electricityBillEntity.getFromDate());
				String paymentString = "";
				if (billingPaymentsEntity != null) {
					paymentString += "<table style='width: 100%;'>"
							+ "<tr>"
							+ "<td style='' ><p>Amount</p></td> <td style='padding:  0.2em;'>"
							+ billingPaymentsEntity.getPaymentAmount()
							+ "</td> "
							+ "</tr>"
							+ "<tr>"
							+ "<td style=''><p>Receipt No.</p></td> <td style='padding:  0.2em;'>"
							+ billingPaymentsEntity.getReceiptNo()
							+ "</td> "
							+ "</tr>"
							+ "<tr>"
							+ "<td style=''><p>Receipt Date.</p></td> <td style='padding:  0.2em;'>"
							+ DateFormatUtils.format(
									billingPaymentsEntity.getReceiptDate(),
									"dd-MMM-yyyy")
							+ "</td> "
							+ "</tr>"
							+ "<tr>"
							+ "	<td style=''><p>Mode of payment </p></td> <td style='padding:  0.2em;'>"
							+ billingPaymentsEntity.getPaymentMode() + "</td>"
							+ "</tr>" + "</table>";
				}

				double parkingSlotsAmount = electricityBillsService
						.getLineItemAmountBasedOnTransactionCode(
								electricityBillEntity.getElBillId(),
								"CAM_PARKING_SLOT");
				double perSlotAmount = camConsolidationService
						.getParkingPerSlotAmount("CAM_PARKING_SLOT");
				double serviceTaxAmount = camConsolidationService
						.getParkingPerSlotAmount("CAM_SERVICE_TAX");
				double eCessTaxAmount = camConsolidationService
						.getParkingPerSlotAmount("CAM_ECESS");
				double sheCessTaxAmount = camConsolidationService
						.getParkingPerSlotAmount("CAM_SHECESS");
				double arrearsAmountCam = electricityBillEntity
						.getArrearsAmount();
				double serviceTax = Math.round(electricityBillsService
						.getLineItemAmountBasedOnTransactionCode(
								electricityBillEntity.getElBillId(),
								"CAM_SERVICE_TAX"));
				double educationAmount = electricityBillsService
						.getLineItemAmountBasedOnTransactionCode(
								electricityBillEntity.getElBillId(),
								"CAM_ECESS");
				double sEducationAmount = electricityBillsService
						.getLineItemAmountBasedOnTransactionCode(
								electricityBillEntity.getElBillId(),
								"CAM_SHECESS");
				double billAmount = electricityBillEntity.getBillAmount()
						- serviceTax - educationAmount - sEducationAmount
						- parkingSlotsAmount;
				double totalAmount = Math.round(electricityBillEntity
						.getBillAmount() + arrearsAmountCam);
				double totalCamRates = camConsolidationService
						.getTotalCamRates();
				String configName = "CAM Charges";
				String status = "Active";
				String camSetting = electricityBillsService
						.getBillingConfigValue(configName, status);
				logger.info("camSetting ==================== " + camSetting);
				String calculationBasis = camSetting;

				str = "<div id='myTab'>"
						+ "<table id='tabs' style='width: 100%;margin-top: -40px; background: white; padding: 21px 23px; border: 2px solid black; font-size: 12px;border-collapse: collapse;'>"
						+ "<tr style='border-bottom:1px solid black;'><td style='padding: 0.77em;'>&nbsp;</td><td style='padding: 0.77em;'><b><u>COMMON&nbsp;AREA&nbsp;MAINTENANCE&nbsp;BILL</u></b></td></tr>"
						+ "<tr style='border-bottom:1px solid black;'><td style='width:80px;height:100px; border-bottom:1px solid black;vertical-align:'> <img src='/bfm_acq/resources/images/build.jpg' style='width:190px;height:100px'></td>"

						+ "<td style='padding: 0.77em; border-bottom:1px solid black;border-right:1px solid black;vertical-align: top;border-top:1px solid black;border-right:1px solid black;top;width: 33%;'><b style='color:#FF9900;font-size:16px;'>Grand&nbsp;Arch&nbsp;Resident&nbsp;Welfare&nbsp;Association</b><br><b style='color:#90EE90;font-size:13px;'>Sector-58,&nbsp;Gram-Behrampur&nbsp;Gurgaon&nbsp;-&nbsp;122002</b><br></td>"
						+ "<td style='padding: 0.77em; border-bottom:1px solid black;vertical-align: top;border-top:1px solid black;'><b>Amount Payable -"
						+ totalAmount
						+ "</b><br><b>Due Date -"
						+ DateFormatUtils.format(
								electricityBillEntity.getBillDueDate(),
								"dd MMM. yyyy")
						+ "</b></td></tr>"
						+ "<tr>"
						+ "<td style='width:400px;vertical-align: top;border-right: 1pt solid black;'>"
						+ "<b style='font-size:15px;margin-left: 10px;'><u>Customer Details</u></b>"
						+ "<table style='width: 100%;border-collapse: collapse;margin-left: 2px;'>"
						+ "<tr>"
						+ "<td style='' ><p>Name </p></td><td style='padding:  0.2em;text-align: left'>"
						+ electricityBillEntity.getAccountObj().getPerson()
								.getFirstName()
						+ "</td>"

						+ "</tr>"
						+ "<tr>	"

						+ "<td style=''><p>Address </p></td> <td style='padding:  0.2em;'>"
						+ address1
						+ "</td> "

						+ "</tr>"
						+ "<tr>"

						+ "<td style=''><p>City</p></td> <td style='padding:  0.2em;'>"
						+ city
						+ "</td>"

						+ "</tr>"
						+ "<tr>	"

						+ "<td style=''><p>Mobile No </p></td> <td style='padding:  0.2em;'>"
						+ toAddressMobile
						+ "</td>"

						+ "</tr>"
						+ "<tr>"

						+ "<td style=' '><p>E-Mail </p></td> <td style='padding:  0.2em;'>"
						+ toAddressEmail
						+ "</td>"

						+ "</tr>"
						+ "</table>"
						+ "</td>"

						+ "<td style='width:400px;vertical-align: top; border-right: 1pt solid black;'>"
						+ "<table style='width: 100%;border-collapse: collapse;margin-left: 2px; >"
						+ "<tr><td style='padding: 0.77em;'><b style='font-size:15px;margin-left: 10px;'><u>Account Details</u></b></td></tr>"

						+ "<tr>	"
						+ "<td style=''><p>CA No </p></td> <td style='padding:  0.2em;'>"
						+ electricityBillEntity.getAccountObj().getAccountNo()
						+ "</td>"
						+ "</tr>"
						+ "<tr>	"
						+ "<td style=''><p>Service Type</p></td> <td style='padding:  0.2em;'>"
						+ electricityBillEntity.getTypeOfService()
						+ "</td>"
						+ "</tr>"
						+ "<tr>	"
						+ "<td style=''><p>Bill Basis</p></td> <td style='padding:  0.2em;'>"
						+ calculationBasis
						+ "</td> "
						+ "</tr>"
						+ "</table>"
						+ "</td>"

						+ "<td style='width:400px;vertical-align: top;'>"
						+ "<table style='width: 100%;border-collapse: collapse;margin-left: 2px;'>"
						+ "<tr>"
						+ "<td style=' '><p>Surcharge </p></td><td style='padding:  0.2em;'>"
						+ Math.round((electricityBillEntity.getNetAmount() * 0.02))
						+ "</td> "
						+ "</tr>"
						+ "<tr>	"
						+ "<td style=''><p>Payable after <br/> due date - </p></td> <td style='padding:  0.2em;'>"
						+ (electricityBillEntity.getNetAmount() + Math
								.round((electricityBillEntity.getNetAmount() * 0.02)))
						+ "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style=''><p>Bill&nbsp;No</p></td> <td style='padding:  0.2em;'>"
						+ electricityBillEntity.getBillNo()
						+ "</td>"
						+ "</tr>"
						+ "<tr>	"
						+ "<td style=''><p>Bill&nbsp;Date</p></td> <td style='padding:  0.2em;'>"
						+ DateFormatUtils.format(
								electricityBillEntity.getBillDate(),
								"dd MMM. yyyy")
						+ "</td>"
						+ "</tr>"
						+ "<tr>	"
						+ "<td style=''><p>Billing&nbsp;Period&nbsp;</p></td> <td style='padding:  0.2em;'>"
						+ DateFormatUtils.format(
								electricityBillEntity.getFromDate(),
								"dd MMM. yyyy")
						+ "&nbsp;&nbsp;&nbsp;to&nbsp;"
						+ DateFormatUtils.format(
								electricityBillEntity.getBillDate(),
								"dd MMM. yyyy")
						+ "</td>"
						+ "</tr>"

						+ "</table>"
						+ "</td>"
						+ "</tr>"
						+ "<tr style='background-color: black'>"
						+ "<td style='backgound: black; color: white; font-weight: bolder;' colspan=3>Bill&nbsp;Details</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td colspan='3'>"

						+ "<table style='width: 86%;'>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;width: 70%;' ><p>1 <b>Apartment Size (Sq Ft)</b> </p></td><td style='padding:  0.2em;'>:</td><td style='padding:  0.2em;'>"
						+ camConsolidationService
								.getAreaOfProperty(electricityBillEntity
										.getAccountObj().getPropertyId())
						+ "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;width: 70%;'><p>2 <b>Bill Basis</b> </p></td><td style='padding:  0.2em;'>:</td> <td style='padding:  0.2em;'>"
						+ calculationBasis
						+ "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;width: 70%;'><p>3 <b>Rates ("
						+ calculationBasis
						+ ")</b></p></td><td style='padding:  0.2em;'>:</td> <td style='padding:  0.2em;'>"
						+ totalCamRates
						+ "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;width: 70%;'><p>4 <b>Parking Slot Charges ("
						+ perSlotAmount
						+ "/- per slot)</b> </p></td><td style='padding:  0.2em;'>:</td> <td style='padding:  0.2em;'>"
						+ parkingSlotsAmount
						+ "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;width: 70%;'><p>5 <b>Amount (Rs.)</b> </p></td><td style='padding:  0.2em;'>:</td> <td style='padding:  0.2em;'>"
						+ Math.round(billAmount)
						+ "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;width: 70%;'><p>6 <b>Service Tax @ "
						+ serviceTaxAmount
						+ "%</b></p></td><td style='padding:  0.2em;'>:</td> <td style='padding:  0.2em;'>"
						+ serviceTax
						+ "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;width: 70%;'><p>7 <b>E.Cess on Service Tax @ "
						+ eCessTaxAmount
						+ "%</b></p></td><td style='padding:  0.2em;'>:</td> <td style='padding:  0.2em;'>"
						+ educationAmount
						+ "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;width: 70%;'><p>8 <b>S.H.E Cess on Service Tax @ "
						+ sheCessTaxAmount
						+ "%</b></p></td><td style='padding:  0.2em;'>:</td> <td style='padding:  0.2em;'>"
						+ sEducationAmount
						+ "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;width: 70%;'><p>9 <b>VAT</b></p></td><td style='padding:  0.2em;width: 50%;'>:</td><td style='padding:  0.2em;'>"
						+ 0
						+ "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;width: 70%;'><p>10 <b>Previous Balance</b></p></td><td style='padding:  0.2em;'>:</td> <td style='padding:  0.2em;'>"
						+ arrearsAmountCam
						+ "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;width: 70%;'><p>11 <b>Total Amount</b></p></td><td style='padding:  0.2em;'>:</td> <td style='padding:  0.2em;'>"
						+ totalAmount
						+ "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;width: 70%;'><p>12 <b>Amount in words : "
						+ NumberToWord.convertNumberToWords((int) totalAmount)
						+ "</b></p></td><td style='padding:  0.2em;'></td> <td style='padding:  0.2em;'></td>"
						+ "</tr>"
						+ "</table>"
						+ "</td></tr>"

						+ "<tr style='background-color: black'>"
						+ "<td style='padding: 0.2em;backgound: black; color: white; font-weight: bolder;' colspan='4'>Particulars</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td colspan='2' style='width:400px;vertical-align: top;'>"
						+ "<table style='width: 100%;'colspan='2'>"
						+ "<tr><td style='height: 37px;'><b><u>Maintainance Charges</u></b></td></tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;' ><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.mcPoint1")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.mcPoint2")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.mcPoint3")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.mcPoint4")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em; '><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.mcPoint5")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.mcPoint6")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.mcPoint7")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.mcPoint8")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.mcPoint9")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.mcPoint10")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.mcPoint11")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.mcPoint12")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.mcPoint13")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.mcPoint14")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.mcPoint15")
						+ "</p></td>"
						+ "</tr>"
						+ "</table>"
						+ "</td>"

						+ "<td style='width:400px;vertical-align: top;'>"
						+ "<table style='width: 100%;'colspan='3'>"
						+ "<tr><td style='height: 37px;'><b><u>Utility Charges</u></b></td></tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;' ><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.ucPoint1")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.ucPoint2")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.ucPoint3")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.ucPoint4")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='width:400px;vertical-align: top;'>"
						+ "<table style='width: 100%;'colspan='2'>"
						+ "<tr><td style='height: 37px;'><b><u>Other Charges</u></b></td></tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em; '><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.ocPoint1")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.ocPoint2")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.ocPoint3")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.ocPoint4")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='padding: 0.2em;'><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.CAM.ocPoint5")
						+ "</p></td>"
						+ "</tr>"
						+ "</table>"
						+ "</td>"
						+ "</tr>"
						+ "</table>"
						+ "</td>"
						+ "</tr>"

						+ "<tr><td colspan='2' style='background-color: #808000; color: black; font-weight: bolder; text-align: center;'>CAM&nbsp;Charges&nbsp;Graph</td><td  style='background-color: #808000; color: black; font-weight: bolder; text-align: center;'>Previous&nbsp;CAM&nbsp;Charges&nbsp;Pattern</td></tr>"
						+ "<tr>"
						+ "<td colspan='2' style='width:400px; vertical-align: top;'>"
						+ "<div id='container' style='height: 200px; max-height: 200px; width: 350px; max-width: 350px'>"
						+ "</div>"
						+ "</td>"

						+ "<td  style='width:400px;vertical-align: top;'>"
						+ "<table style='width: 100%;margin-left: 2px;'>"
						+ "<tr>"
						+ "<td style='' ><p>Bill Month</p></td>"
						+ "<td style='' ><p>Bill Basis</p></td>"
						+ "<td style='' ><p>Amount</p></td> "
						+ "</tr>"
						+ lastSixBills
						+ "<tr style='background-color: #808000; color: black; font-weight: bolder; text-align: center;width: 100%;'><td colspan='3'>Last Payment Status</td></tr>"
						+ paymentString
						+ "</table>"
						+ "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "</tr><br><br><br>"
						+ "<tr style='background-color: #808000'>"
						+ "<td colspan='3' style='backgound: black; color: black; font-weight: bolder;'><u>Notes : Skyon  Resident Welfare Association</u></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td colspan='3' style='width:400px; vertical-align: top;'>"
						+ "<table style='width: 100%;margin-left: 10px;'>"
						+ "<tr>"
						+ "<td style='' ><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.point1")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='' ><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.point2")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='' ><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.point3")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='' ><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.point4")
						+ "</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='' ><p>"
						+ ResourceBundle.getBundle("utils").getString(
								"report.point5")
						+ "</p></td> "
						+ "</tr>"
						+ "</table>" + "</td>"

						+ "</tr>" + "</table>" + "</div>";

			} else {
				String spmQuery = "select spm from ServiceParameterMaster spm where spm.serviceType='"
						+ electricityBillEntity.getTypeOfService()
						+ "' order by spm.spmSequence";

				String bpmQuery = "select bpm from BillParameterMasterEntity bpm where bpm.serviceType='"
						+ electricityBillEntity.getTypeOfService()
						+ "' order by bpm.bvmSequence";

				String lineItems = "select bli from ElectricityBillLineItemEntity bli where bli.electricityBillEntity.elBillId="
						+ elBillId;

				List<ServiceParameterMaster> spmList = serviceParameterMasterService
						.executeSimpleQuery(spmQuery);

				if (spmList.size() > 0) {
					for (ServiceParameterMaster entity : spmList) {

						ServiceParametersEntity serviceParametersEntity = serviceParameterService
								.getSingleResult("select obj from ServiceParametersEntity obj where obj.spmId="
										+ entity.getSpmId()
										+ " and obj.serviceMastersEntity.accountObj.accountId="
										+ electricityBillEntity.getAccountObj()
												.getAccountId());
						if (serviceParametersEntity != null) {
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
								sanctionLoad = Float
										.parseFloat(serviceParametersEntity
												.getServiceParameterValue());
							}
							if (serviceParametersEntity
									.getServiceParameterMaster().getSpmName()
									.equalsIgnoreCase("DG Applicable")) {
								dgApplicable = serviceParametersEntity
										.getServiceParameterValue();
								if (serviceParametersEntity
										.getServiceParameterMaster()
										.getSpmName()
										.equalsIgnoreCase("Sanctioned KW (DG)")) {
									sanctionLoadDG = Float
											.parseFloat(serviceParametersEntity
													.getServiceParameterValue());
								}
							}

							if (serviceParametersEntity
									.getServiceParameterMaster().getSpmName()
									.equalsIgnoreCase("Connection Type")) {
								connectionType = serviceParametersEntity
										.getServiceParameterValue();
							}

							if (serviceParametersEntity
									.getServiceParameterMaster().getSpmName()
									.equalsIgnoreCase("Voltage Level")) {
								voltageLevel = Float
										.parseFloat(serviceParametersEntity
												.getServiceParameterValue());
							}
							if (serviceParametersEntity
									.getServiceParameterMaster().getSpmName()
									.equalsIgnoreCase("Connection Security")) {
								connectionSecurity = Double
										.parseDouble(serviceParametersEntity
												.getServiceParameterValue());
							}
						}
					}
				}

				ElectricityMetersEntity electricityMetersEntity = electricityMetersService
						.getMeter(electricityBillEntity.getAccountId(),
								electricityBillEntity.getTypeOfService());
				if (electricityMetersEntity != null) {
					meterSrNo = electricityMetersEntity.getMeterSerialNo();
					meterType = meterParameterMasterService
							.getMeterParameter(electricityBillEntity
									.getAccountId(), electricityMetersEntity
									.getElMeterId(), electricityMetersEntity
									.getTypeOfServiceForMeters(), "Meter type");
					meterMake = meterParameterMasterService
							.getMeterParameter(electricityBillEntity
									.getAccountId(), electricityMetersEntity
									.getElMeterId(), electricityMetersEntity
									.getTypeOfServiceForMeters(), "Meter Make");

					if (meterParameterMasterService
							.getMeterParameter(electricityBillEntity
									.getAccountId(), electricityMetersEntity
									.getElMeterId(), electricityMetersEntity
									.getTypeOfServiceForMeters(),
									"Meter Constant") != null) {
						float meterConstant1 = Float
								.parseFloat(meterParameterMasterService.getMeterParameter(
										electricityBillEntity.getAccountId(),
										electricityMetersEntity.getElMeterId(),
										electricityMetersEntity
												.getTypeOfServiceForMeters(),
										"Meter Constant"));
						if (meterConstant1 == 1) {
							meterStatus = "Normal";
						} else if (meterConstant1 < 1) {
							meterStatus = "Slow";
						} else if (meterConstant1 > 1) {
							meterStatus = "Fast";
						}
					}
				}

				List<BillParameterMasterEntity> bpmList = billParameterMasterService
						.executeSimpleQuery(bpmQuery);

				if (bpmList.size() > 0) {
					for (BillParameterMasterEntity entity : bpmList) {
						if (entity != null)
							bpmStr += "<tr><td style=' border: 1px solid #808080;'>"
									+ entity.getBvmName() + "</td>";
						ElectricityBillParametersEntity billingParameterMaster = electricityBillParameterService
								.getSingleResult("select obj from ElectricityBillParametersEntity obj where obj.billParameterMasterEntity.bvmId="
										+ entity.getBvmId()
										+ " and obj.electricityBillEntity.accountObj.accountId="
										+ electricityBillEntity.getAccountObj()
												.getAccountId()
										+ " and obj.electricityBillEntity.elBillId="
										+ elBillId);
						if (billingParameterMaster != null) {
							if (billingParameterMaster.getNotes() != null)
								slabs = billingParameterMaster.getNotes();
							bpmStr += "<td style=' border: 1px solid #808080;'>"
									+ billingParameterMaster
											.getElBillParameterValue()
									+ "</td></tr>";
						} else
							bpmStr += "<td style=' border: 1px solid #808080;'>0</td></tr>";
					}
				}
				List<ElectricityBillLineItemEntity> bliList = billLineItemService
						.executeSimpleQuery(lineItems);
				if (bliList.size() > 0) {
					for (ElectricityBillLineItemEntity electricityBillLineItemEntity : bliList) {
						List<TransactionMasterEntity> listTM = transactionMasterService
								.executeSimpleQuery("select o from TransactionMasterEntity o where o.transactionCode='"
										+ electricityBillLineItemEntity
												.getTransactionCode() + "'");
						if (!listTM.isEmpty()) {
							bliStr += "<tr><td style='' ><p>"
									+ listTM.get(0).getTransactionName()
									+ " </p></td> <td style='padding:  0.2em;'>"
									+ electricityBillLineItemEntity
											.getBalanceAmount() + "</td></tr>";
						}
					}
				}
				ElectricityBillEntity previoisBill;
				if (electricityBillEntity.getArrearsAmount() > 0) {
					previoisBill = electricityBillsService
							.getPreviousBillWithOutStatus(
									electricityBillEntity.getAccountId(),
									electricityBillEntity.getTypeOfService(),
									electricityBillEntity.getFromDate(),
									postType);
					if (previoisBill != null) {
						arrearsAmount = previoisBill.getNetAmount();
						for (ElectricityBillLineItemEntity previousBillLineItems : previoisBill
								.getBillLineItemEntities()) {

							List<TransactionMasterEntity> listTM = transactionMasterService
									.executeSimpleQuery("select o from TransactionMasterEntity o where o.transactionCode='"
											+ previousBillLineItems
													.getTransactionCode() + "'");
							if (!listTM.isEmpty()) {
								arrearString += "<tr><td style='' ><p>"
										+ listTM.get(0).getTransactionName()
										+ " </p></td> <td style='padding:  0.2em;'>"
										+ previousBillLineItems
												.getBalanceAmount()
										+ "</td></tr>";
							}
						}
						arrearString += "<tr>"
								+ "<td style=''><b>Total</b></td> <td style='padding:  0.2em;'>"
								+ arrearsAmount + "</td> " + "</tr>";
					}
				}

				List<ServiceMastersEntity> serviceMastersList = serviceMasterService
						.executeSimpleQuery("select obj from ServiceMastersEntity obj where obj.accountObj.accountId="
								+ electricityBillEntity.getAccountObj()
										.getAccountId()
								+ " and obj.typeOfService='"
								+ electricityBillEntity.getTypeOfService()
								+ "'");

				if (!serviceMastersList.isEmpty()
						&& serviceMastersList.size() > 0) {
					for (ServiceMastersEntity serviceMastersEntity : serviceMastersList) {

						String paraMeterName5 = "Units";
						String uomValueString = null;
						if (electricityBillParameterService.getParameterValue(
								electricityBillEntity.getElBillId(),
								electricityBillEntity.getTypeOfService(),
								paraMeterName5) != null) {
							uomValueString = electricityBillParameterService
									.getParameterValue(electricityBillEntity
											.getElBillId(),
											electricityBillEntity
													.getTypeOfService(),
											paraMeterName5);
							if (uomValueString != null)
								uomValue = Float.parseFloat(uomValueString);
						}

						String paraMeterName6 = "Number of days";
						String numberOfDaysString = null;
						if (electricityBillParameterService.getParameterValue(
								electricityBillEntity.getElBillId(),
								electricityBillEntity.getTypeOfService(),
								paraMeterName6) != null) {
							numberOfDaysString = electricityBillParameterService
									.getParameterValue(electricityBillEntity
											.getElBillId(),
											electricityBillEntity
													.getTypeOfService(),
											paraMeterName6);
							if (numberOfDaysString != null)
								numberOfDays = Float
										.parseFloat(numberOfDaysString);
						}

						String paraMeterName7 = "Present reading";
						String presentReadingString = null;
						if (electricityBillParameterService.getParameterValue(
								electricityBillEntity.getElBillId(),
								electricityBillEntity.getTypeOfService(),
								paraMeterName7) != null) {
							presentReadingString = electricityBillParameterService
									.getParameterValue(electricityBillEntity
											.getElBillId(),
											electricityBillEntity
													.getTypeOfService(),
											paraMeterName7);
							if (presentReadingString != null)
								presentReading = Float
										.parseFloat(presentReadingString);
						}

						String paraMeterName8 = "Previous reading";
						String previousReadingString = null;
						if (electricityBillParameterService.getParameterValue(
								electricityBillEntity.getElBillId(),
								electricityBillEntity.getTypeOfService(),
								paraMeterName8) != null) {
							previousReadingString = electricityBillParameterService
									.getParameterValue(electricityBillEntity
											.getElBillId(),
											electricityBillEntity
													.getTypeOfService(),
											paraMeterName8);
							if (previousReadingString != null)
								previousReading = Float
										.parseFloat(previousReadingString);
						}

						String paraMeterName9 = "Meter Constant";
						String meterConstantString = null;
						if (electricityBillParameterService.getParameterValue(
								electricityBillEntity.getElBillId(),
								electricityBillEntity.getTypeOfService(),
								paraMeterName9) != null) {
							meterConstantString = electricityBillParameterService
									.getParameterValue(electricityBillEntity
											.getElBillId(),
											electricityBillEntity
													.getTypeOfService(),
											paraMeterName9);
							if (meterConstantString != null)
								meterConstant = Float
										.parseFloat(meterConstantString);
						}

						if (serviceMastersEntity.getTypeOfService()
								.equalsIgnoreCase("Electricity")) {
							ELTariffMaster eltariffMaster = elTariffMasterService
									.getSingleResult("select o from ELTariffMaster o where o.elTariffID="
											+ serviceMastersEntity
													.getElTariffID());
							if (eltariffMaster != null) {
								elTariffId = serviceMastersEntity
										.getElTariffID();
								tariffName = eltariffMaster.getTariffName();
							}

						} else if (serviceMastersEntity.getTypeOfService()
								.equalsIgnoreCase("Gas")) {
							GasTariffMaster gasTariffMaster = gasTariffMasterService
									.getSingleResult("select o from GasTariffMaster o where o.gasTariffId="
											+ serviceMastersEntity
													.getGaTariffID());
							if (gasTariffMaster != null) {
								tariffName = gasTariffMaster.getGastariffName();
								gasTariffId = gasTariffMaster.getGasTariffId();
							}
						} else if (serviceMastersEntity.getTypeOfService()
								.equalsIgnoreCase("Water")) {
							List<WTTariffMaster> wttariffMaster = wtTariffMasterService
									.executeSimpleQuery("select o from WTTariffMaster o where o.wtTariffId="
											+ serviceMastersEntity
													.getWtTariffID());
							if (wttariffMaster != null
									&& wttariffMaster.size() > 0) {
								tariffName = wttariffMaster.get(0)
										.getTariffName();
								wtTariffId = wttariffMaster.get(0)
										.getWtTariffId();
							}
						} else if (serviceMastersEntity.getTypeOfService()
								.equalsIgnoreCase("Solid Waste")) {
							SolidWasteTariffMaster solidWasteTariffMaster = solidWasteTariffMasterService
									.getSingleResult("select o from SolidWasteTariffMaster o where o.solidWasteTariffId="
											+ serviceMastersEntity
													.getSwTariffID());
							if (solidWasteTariffMaster != null) {
								tariffName = solidWasteTariffMaster
										.getSolidWasteTariffName();
								solidWasteTariffId = solidWasteTariffMaster
										.getSolidWasteTariffId();
							}

						}
					}
				}

				if (electricityBillEntity.getTypeOfService().equalsIgnoreCase(
						"Electricity")) {
					if (electricityMetersEntity.getMeterType()
							.equalsIgnoreCase("Trivector Meter")) {
						logger.info("------------- PF and MDI is applicable ----------------");
						String paraMeterName10 = "PF";
						String pfString = null;
						if (electricityBillParameterService.getParameterValue(
								electricityBillEntity.getElBillId(),
								electricityBillEntity.getTypeOfService(),
								paraMeterName10) != null) {
							pfString = electricityBillParameterService
									.getParameterValue(electricityBillEntity
											.getElBillId(),
											electricityBillEntity
													.getTypeOfService(),
											paraMeterName10);
							pfValue = Double.parseDouble(pfString);
						}
						String paraMeterName11 = "Maximum Demand Recorded";
						String mdiString = null;
						if (electricityBillParameterService.getParameterValue(
								electricityBillEntity.getElBillId(),
								electricityBillEntity.getTypeOfService(),
								paraMeterName11) != null) {
							mdiString = electricityBillParameterService
									.getParameterValue(electricityBillEntity
											.getElBillId(),
											electricityBillEntity
													.getTypeOfService(),
											paraMeterName11);
							mdiValue = Float.parseFloat(mdiString);
						}
					}
				}
				String lastSixBills = "";
				if (electricityBillEntity.getTypeOfService().equalsIgnoreCase(
						"Electricity")) {
					slabString = getTariffSlabString(elTariffId,
							electricityBillEntity.getFromDate(),
							electricityBillEntity.getBillDate(), uomValue,
							locale);
					amountForInteresetCal = (float) (electricityBillEntity
							.getNetAmount() - (interestOnTax + interestAmount + taxAmount));
					int rateMasterID = rateMasterService.getRateMasterEC(
							elTariffId, "Rate of interest");

					for (ElectricityBillLineItemEntity electricityBillLineItemEntity : electricityBillEntity
							.getBillLineItemEntities()) {

						if (electricityBillLineItemEntity.getTransactionCode()
								.equalsIgnoreCase("OT_TAX")) {
							taxAmount = electricityBillLineItemEntity
									.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode()
								.equalsIgnoreCase("OT_INTEREST")) {
							interestAmount = electricityBillLineItemEntity
									.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode()
								.equalsIgnoreCase("OT_TAX_INTEREST")) {
							interestOnTax = electricityBillLineItemEntity
									.getBalanceAmount();
						}
					}

					if (rateMasterID != 0) {
						latePaymentAmount = interestCalculationEL(rateMasterID,
								amountForInteresetCal, electricityBillEntity);
					}

					if (dgApplicable.equalsIgnoreCase("Yes")) {
						dgSlabString = getTariffSlabStringDGString(elTariffId,
								electricityBillEntity.getFromDate(),
								electricityBillEntity.getBillDate(), uomValue,
								locale);
					}

					List<?> lastSixMontsBills = electricityBillsService
							.getLastSixMontsBills(
									electricityBillEntity.getAccountId(),
									electricityBillEntity.getTypeOfService(),
									electricityBillEntity.getBillDate(),
									"Units");
					for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator
							.hasNext();) {
						final Object[] values = (Object[]) iterator.next();
						map.put(new SimpleDateFormat("MMM")
								.format((Date) values[0]), (Double) values[2]);
						lastSixBills += "<tr><td style='' ><p>"
								+ DateFormatUtils.format((Date) values[0],
										"dd MMM. yyyy")
								+ "</p></td><td style='' ><p>"
								+ (String) values[1]
								+ "</p></td><td style='' ><p>"
								+ (Double) values[2] + " </p></td></tr>";
					}
				} else if (electricityBillEntity.getTypeOfService()
						.equalsIgnoreCase("Water")) {
					slabString = getWaterTariffSlabString(wtTariffId,
							electricityBillEntity.getFromDate(),
							electricityBillEntity.getBillDate(), uomValue,
							locale);
					for (ElectricityBillLineItemEntity electricityBillLineItemEntity : electricityBillEntity
							.getBillLineItemEntities()) {

						if (electricityBillLineItemEntity.getTransactionCode()
								.equalsIgnoreCase("WT_TAX")) {
							taxAmount = electricityBillLineItemEntity
									.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode()
								.equalsIgnoreCase("WT_INTEREST")) {
							interestAmount = electricityBillLineItemEntity
									.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode()
								.equalsIgnoreCase("WT_TAX_INTEREST")) {
							interestOnTax = electricityBillLineItemEntity
									.getBalanceAmount();
						}
					}

					int rateMasterID = rateMasterService
							.getRateMasterDomesticGeneral(wtTariffId,
									"Rate of interest");
					if (rateMasterID != 0) {
						latePaymentAmount = interestCalculationWt(rateMasterID,
								amountForInteresetCal, electricityBillEntity);
					}
					List<?> lastSixMontsBills = electricityBillsService
							.getLastSixMontsBills(
									electricityBillEntity.getAccountId(),
									electricityBillEntity.getTypeOfService(),
									electricityBillEntity.getBillDate(),
									"Units");
					for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator
							.hasNext();) {
						final Object[] values = (Object[]) iterator.next();
						map.put(new SimpleDateFormat("MMM")
								.format((Date) values[0]), (Double) values[2]);
						lastSixBills += "<tr><td style='' ><p>"
								+ DateFormatUtils.format((Date) values[0],
										"dd MMM. yyyy")
								+ "</p></td><td style='' ><p>"
								+ (String) values[1]
								+ "</p></td><td style='' ><p>"
								+ (Double) values[2] + " </p></td></tr>";
					}
				} else if (electricityBillEntity.getTypeOfService()
						.equalsIgnoreCase("Gas")) {
					slabString = getGasTariffSlabString(gasTariffId,
							electricityBillEntity.getFromDate(),
							electricityBillEntity.getBillDate(), uomValue,
							locale);
					for (ElectricityBillLineItemEntity electricityBillLineItemEntity : electricityBillEntity
							.getBillLineItemEntities()) {

						if (electricityBillLineItemEntity.getTransactionCode()
								.equalsIgnoreCase("GA_TAX")) {
							taxAmount = electricityBillLineItemEntity
									.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode()
								.equalsIgnoreCase("GA_INTEREST")) {
							interestAmount = electricityBillLineItemEntity
									.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode()
								.equalsIgnoreCase("GA_TAX_INTEREST")) {
							interestOnTax = electricityBillLineItemEntity
									.getBalanceAmount();
						}
					}
					int rateMasterID = rateMasterService.getRateMasterGas(
							gasTariffId, "Rate of interest");
					if (rateMasterID != 0) {
						latePaymentAmount = interestCalculationGas(
								rateMasterID, amountForInteresetCal,
								electricityBillEntity);
					}
					List<?> lastSixMontsBills = electricityBillsService
							.getLastSixMontsBills(
									electricityBillEntity.getAccountId(),
									electricityBillEntity.getTypeOfService(),
									electricityBillEntity.getBillDate(),
									"Units");
					for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator
							.hasNext();) {
						final Object[] values = (Object[]) iterator.next();
						map.put(new SimpleDateFormat("MMM")
								.format((Date) values[0]), (Double) values[2]);
						lastSixBills += "<tr><td style='' ><p>"
								+ DateFormatUtils.format((Date) values[0],
										"dd MMM. yyyy")
								+ "</p></td><td style='' ><p>"
								+ (String) values[1]
								+ "</p></td><td style='' ><p>"
								+ (Double) values[2] + " </p></td></tr>";
					}
				} else if (electricityBillEntity.getTypeOfService()
						.equalsIgnoreCase("Solid Waste")) {
					String paraMeterName5 = "Volume(KG)";
					String uomValueString = null;
					float solidWasterUnits = 0.0f;
					if (electricityBillParameterService.getParameterValue(
							electricityBillEntity.getElBillId(),
							electricityBillEntity.getTypeOfService(),
							paraMeterName5) != null) {
						uomValueString = electricityBillParameterService
								.getParameterValue(electricityBillEntity
										.getElBillId(), electricityBillEntity
										.getTypeOfService(), paraMeterName5);
						if (uomValueString != null)
							solidWasterUnits = Float.parseFloat(uomValueString);
					}
					slabString = getSolidWasteTariffSlabString(
							solidWasteTariffId,
							electricityBillEntity.getFromDate(),
							electricityBillEntity.getBillDate(),
							solidWasterUnits, locale);
					for (ElectricityBillLineItemEntity electricityBillLineItemEntity : electricityBillEntity
							.getBillLineItemEntities()) {
						if (electricityBillLineItemEntity.getTransactionCode()
								.equalsIgnoreCase("SW_TAX")) {
							taxAmount = electricityBillLineItemEntity
									.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode()
								.equalsIgnoreCase("SW_INTEREST")) {
							interestAmount = electricityBillLineItemEntity
									.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode()
								.equalsIgnoreCase("SW_TAX_INTEREST")) {
							interestOnTax = electricityBillLineItemEntity
									.getBalanceAmount();
						}
					}

					int rateMasterID = rateMasterService.getRateMasterSW(
							gasTariffId, "Rate of interest");
					if (rateMasterID != 0) {
						latePaymentAmount = interestCalculationSW(rateMasterID,
								amountForInteresetCal, electricityBillEntity);
					}

					List<?> lastSixMontsBills = electricityBillsService
							.getLastSixMontsBills(
									electricityBillEntity.getAccountId(),
									electricityBillEntity.getTypeOfService(),
									electricityBillEntity.getBillDate(),
									"Volume(KG)");
					for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator
							.hasNext();) {
						final Object[] values = (Object[]) iterator.next();
						map.put(new SimpleDateFormat("MMM")
								.format((Date) values[0]), (Double) values[2]);
						lastSixBills += "<tr><td style='' ><p>"
								+ DateFormatUtils.format((Date) values[0],
										"dd MMM. yyyy")
								+ "</p></td><td style='' ><p>"
								+ (String) values[1]
								+ "</p></td><td style='' ><p>"
								+ (Double) values[2] + " </p></td></tr>";
					}
				} else if (electricityBillEntity.getTypeOfService()
						.equalsIgnoreCase("Others")) {
					slabString = getSolidWasteTariffSlabString(
							solidWasteTariffId,
							electricityBillEntity.getFromDate(),
							electricityBillEntity.getBillDate(), uomValue,
							locale);
					for (ElectricityBillLineItemEntity electricityBillLineItemEntity : electricityBillEntity
							.getBillLineItemEntities()) {

						if (electricityBillLineItemEntity.getTransactionCode()
								.equalsIgnoreCase("OT_TAX")) {
							taxAmount = electricityBillLineItemEntity
									.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode()
								.equalsIgnoreCase("OT_INTEREST")) {
							interestAmount = electricityBillLineItemEntity
									.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode()
								.equalsIgnoreCase("OT_TAX_INTEREST")) {
							interestOnTax = electricityBillLineItemEntity
									.getBalanceAmount();
						}
					}

					List<?> lastSixMontsBills = electricityBillsService
							.getLastSixMontsBillsOthers(
									electricityBillEntity.getAccountId(),
									electricityBillEntity.getTypeOfService(),
									electricityBillEntity.getBillDate(),
									"Volume(KG)");
					for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator
							.hasNext();) {
						final Object[] values = (Object[]) iterator.next();
						map.put(new SimpleDateFormat("MMM")
								.format((Date) values[0]), (Double) values[2]);
						lastSixBills += "<tr><td style='' ><p>"
								+ DateFormatUtils.format((Date) values[0],
										"dd MMM. yyyy")
								+ "</p></td><td style='' ><p>"
								+ (String) values[1]
								+ "</p></td><td style='' ><p>"
								+ (Double) values[2] + " </p></td></tr>";
					}
				}

				BillingPaymentsEntity billingPaymentsEntity = paymentService
						.getPamentDetals(electricityBillEntity.getAccountId(),
								electricityBillEntity.getFromDate());
				String paymentString = "";
				if (billingPaymentsEntity != null) {
					paymentString += "<table style='width: 100%;'>"
							+ "<tr>"
							+ "<td style='' ><p>Amount</p></td> <td style='padding:  0.2em;'>"
							+ billingPaymentsEntity.getPaymentAmount()
							+ "</td> "
							+ "</tr>"
							+ "<tr>"
							+ "<td style=''><p>Receipt No.</p></td> <td style='padding:  0.2em;'>"
							+ billingPaymentsEntity.getReceiptNo()
							+ "</td> "
							+ "</tr>"
							+ "<tr>"
							+ "<td style=''><p>Receipt Date.</p></td> <td style='padding:  0.2em;'>"
							+ DateFormatUtils.format(
									billingPaymentsEntity.getReceiptDate(),
									"dd MMM. yyyy")
							+ "</td> "
							+ "</tr>"
							+ "<tr>"
							+ "	<td style=''><p>Mode of payment </p></td> <td style='padding:  0.2em;'>"
							+ billingPaymentsEntity.getPaymentMode() + "</td>"
							+ "</tr>" + "</table>";
				}

				str = ""
						+ "<div id='myTab'>"
						+ "<table id='tabs' style='width: 100%; background: white; padding: 21px 23px; border: 2px solid black; font-size: 12px;border-collapse: collapse;'>"
						+ "<tr style='border-bottom:1px solid black;'><td style='padding: 0.77em;'>&nbsp;</td><td style='padding: 0.77em;'><b><u>ELECTRICITY BILL</u></b></td></tr>"
						+ "<tr style='border-bottom:1px solid black;'><td style='width:80px;height:100px; border-bottom:1px solid black;vertical-align:'> <img src='http://www.ireoprojects.co.in/gallery/ireo-grandarch.jpg' style='width:160px;height:100px'></td>"

						+ "<td style='padding: 0.77em; border-bottom:1px solid black;border-right:1px solid black;vertical-align: top;border-top:1px solid black;border-right:1px solid black;top;width: 33%;'><b style='color:#FF9900;font-size:15px;'>Skyon Resident Welfare Association</b><br><b style='color:#90EE90;font-size:13px;'>Sector-58, Gram-Behrampur Gurgaon - 122002</b><br></td>"
						+ "<td style='padding: 0.77em; border-bottom:1px solid black;vertical-align: top;border-top:1px solid black;'><b>Amount Payable -"
						+ electricityBillEntity.getNetAmount()
						+ "</b><br><b>Due Date -"
						+ DateFormatUtils.format(
								electricityBillEntity.getBillDueDate(),
								"dd MMM. yyyy")
						+ "</b></td></tr>"
						+ "<tr>"
						+ "<td style='width:400px;vertical-align: top;border-right: 1pt solid black;'>"
						+ "<b style='font-size:15px;margin-left: 10px;'><u>Customer Details</u></b>"
						+ "<table style='width: 100%;border-collapse: collapse;margin-left: 2px;'>"
						+ "<tr>"
						+ "<td style='' ><p>Name </p></td><td style='padding:  0.2em;text-align: left'>"
						+ electricityBillEntity.getAccountObj().getPerson()
								.getFirstName()
						+ "</td>"

						+ "</tr>"
						+ "<tr>	"

						+ "<td style=''><p>Address </p></td> <td style='padding:  0.2em;'>"
						+ address1
						+ "</td> "

						+ "</tr>"
						+ "<tr>"

						+ "<td style=''><p>City</p></td> <td style='padding:  0.2em;'>"
						+ city
						+ "</td>"

						+ "</tr>"
						+ "<tr>	"

						+ "<td style=''><p>Mobile No </p></td> <td style='padding:  0.2em;'>"
						+ toAddressMobile
						+ "</td>"

						+ "</tr>"
						+ "<tr>"

						+ "<td style=' '><p>E-Mail </p></td> <td style='padding:  0.2em;'>"
						+ toAddressEmail
						+ "</td>"

						+ "</tr>"
						+ "<tr><td style='padding: 0.77em;'><b style='font-size:15px;'><u>Account Details</u></b></td></tr>"

						+ "<tr>	"
						+ "<td style=''><p>CA No </p></td> <td style='padding:  0.2em;'>"
						+ electricityBillEntity.getAccountObj().getAccountNo()
						+ "</td>"

						+ "</tr>"
						+ "<tr>	"
						+ "<td style=''><p>Service Type</p></td> <td style='padding:  0.2em;'>"
						+ electricityBillEntity.getTypeOfService()
						+ "</td>"

						+ "</tr>"
						+ "<tr>	"
						+ "<td style=''><p>Tariff Category</p></td> <td style='padding:  0.2em;'>"
						+ tariffName
						+ "</td>"

						+ "</tr>"
						+ "<tr>	"
						+ "<td style=''><p>Bill Basis</p></td> <td style='padding:  0.2em;'>"
						+ electricityBillEntity.getBillType()
						+ "</td> "

						+ "</tr>"
						+ "</table>"
						+ "</td>"

						+ "<td style='width:400px;vertical-align: top; border-right: 1pt solid black;'>"
						+ "<table style='width: 100%;border-collapse: collapse;margin-left: 2px;' >"
						+ "<tr><td style='padding: 0.77em;'><b style='font-size:15px;margin-left: 10px;'><u>Connection Details</u></b></td></tr>"

						+ "<tr>"
						+ "<td style=''><p>Sanctioned (Utility) </p></td> <td style='padding:  0.2em;'>"
						+ sanctionLoad
						+ "</td> "

						+ "</tr>"
						+ "<tr>	"
						+ "<td style=''><p>Sanctioned (DG)</p></td> <td style='padding:  0.2em;'>"
						+ sanctionLoadDG
						+ "</td> "

						+ "</tr>"
						+ "<tr>"
						+ "<td style=''><p>Voltage Level</p></td> <td style='padding:  0.2em;'>"
						+ voltageLevel
						+ "</td> "

						+ "</tr>"
						+ "<tr>"
						+ "<td style=''><p>Meter Type</p></td> <td style='padding:  0.2em;'>"
						+ meterType
						+ "</td>"

						+ "</tr>"
						+ "<tr>"
						+ "<td style=''><p>Connection Type</p></td> <td style='padding:  0.2em;'>"
						+ connectionType
						+ "</td>"

						+ "</tr>"
						+ "<tr>"
						+ "<td style=''><p>Connection Security</p></td> <td style='padding:  0.2em;'>"
						+ connectionSecurity
						+ "</td> "

						+ "</tr>"
						+ "<tr>"
						+ "<td style=''><p>PF</p></td> <td style='padding:  0.2em;'>"
						+ pfValue
						+ "</td>"

						+ "</tr>"
						+ "</table>"
						+ "</td>"

						+ "<td style='width:400px;vertical-align: top;'>"
						+ "<table style='width: 100%;border-collapse: collapse;margin-left: 2px;'>"
						+ "<tr>"
						+ "<td style=''><p>&nbsp;</p></td> <td style='padding:  0.2em;'>&nbsp;</td>"

						+ "</tr>"

						+ "<tr>"
						+ "<td style=' '><p>Surcharge </p></td><td style='padding:  0.2em;'>"
						+ new BigDecimal((latePaymentAmount)).setScale(2,
								RoundingMode.HALF_UP)
						+ "</td> "

						+ "</tr>"
						+ "<tr>	"

						+ "<td style=''><p>Payable after Due Date - </p></td> <td style='padding:  0.2em;'>"
						+ new BigDecimal(
								(latePaymentAmount + electricityBillEntity
										.getNetAmount())).setScale(2,
								RoundingMode.HALF_UP)
						+ "</td>"

						+ "</tr>"
						+ "<tr>"

						+ "<td style=''><p>Bill No</p></td> <td style='padding:  0.2em;'>"
						+ electricityBillEntity.getBillNo()
						+ "</td>"

						+ "</tr>"
						+ "<tr>	"

						+ "<td style=''><p>Bill Date</p> </td> <td style='padding:  0.2em;'>"
						+ DateFormatUtils.format(
								electricityBillEntity.getBillDate(),
								"dd MMM. yyyy")
						+ "</td>"

						+ "</tr>"
						+ "<tr>	"

						+ "<td style=''><p>Billing Period </p> </td> <td style='padding:  0.2em;'>"
						+ DateFormatUtils.format(
								electricityBillEntity.getFromDate(),
								"dd MMM. yyyy")
						+ " To "
						+ DateFormatUtils.format(
								electricityBillEntity.getBillDate(),
								"dd MMM. yyyy")
						+ "</td>"

						+ "</tr>"
						+ "<tr><td style='padding: 0.77em;'><b style='font-size:15px;'><u>Meter Details</u></b></td></tr>"

						+ "<tr>"
						+ "<td style=''><p>Meter Make </p></td> <td style='padding:  0.2em;'>"
						+ meterMake
						+ "</td> "

						+ "</tr>"
						+ "<tr>"
						+ "<td style=''><p>Meter Sr No</p></td> <td style='padding:  0.2em;'>"
						+ meterSrNo
						+ "</td>"

						+ "</tr>"
						+ "<tr>"
						+ "<td style=''><p>Meter Status</p></td> <td style='padding:  0.2em;'>"
						+ meterStatus
						+ "</td> "

						+ "</tr>"
						+ "<tr>	"
						+ "<td style=''><p>MF</p></td> <td style='padding:  0.2em;'>"
						+ meterConstant
						+ "</td> "
						+ "</tr>"
						+ "</table>"
						+ "</td>"

						+ "</tr>"

						+ "<tr style='background-color: black'>"
						+ "<td style='backgound: black; color: white; font-weight: bolder;' colspan=3>Particulars</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td colspan='3'>"
						+ "<table style='width: 100%; text-align: center;'>"
						+ "<tr>"
						+ "<td width='10%' style=' border: 1px solid #808080;'>"
						+ "<table style='width: 100%; text-align: center;vertical-align: top;'>"
						+ "<tr>"
						+ "<th style='font-weight: bold; border: 1px solid #808080;'>Energy Type / Meter Sr No</th></tr>"

						+ "<tr><td style=' border: 1px solid #808080;'>"
						+ electricityBillEntity.getTypeOfService()
						+ "/"
						+ meterSrNo
						+ "</td></tr>"

						+ "</table>"
						+ "</td>"

						+ "<td width='10%' style=' border: 1px solid #808080;'>"
						+ "<table style='width: 100%; text-align: center;vertical-align: top;'>"
						+ "<tr>"
						+ "<th style='font-weight: bold; border: 1px solid #808080;'>MDI</th></tr>"
						+ "<tr><td style=' border: 1px solid #808080;'>"
						+ mdiValue
						+ "</td></tr>"

						+ "</table>"
						+ "</td>"

						+ "<td width='30%' style=' border: 1px solid #808080;vertical-align: top;'>"
						+ "<table style='width: 100%; text-align: center;'>"
						+ "<tr>"

						+ "<tr>"
						+ "<th style='font-weight: bold; border: 1px solid #808080;' colspan='2'>Meter Reading date</th>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style=' border: 1px solid #808080;' width='50%'>New</td>"

						+ "<td style=' border: 1px solid #808080;' width='50%' >Old</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style=' border: 1px solid #808080;' width='50%'>"
						+ electricityBillEntity.getBillDate()
						+ "</td>"

						+ "<td style=' border: 1px solid #808080;' width='50%' >"
						+ electricityBillEntity.getFromDate()
						+ "</td>"
						+ "</tr>"

						+ "</table>"
						+ "</td>"

						+ "<td width='30%' style=' border: 1px solid #808080;vertical-align: top;'>"
						+ "<table style='width: 100%; text-align: center;'>"
						+ "<tr>"

						+ "<tr>"
						+ "<th style='font-weight: bold; border: 1px solid #808080;' colspan='2'>Meter Reading (kWh)</th>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style=' border: 1px solid #808080;' width='50%'>New</td>"
						+ "<td style=' border: 1px solid #808080;' width='50%' >Old</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style=' border: 1px solid #808080;' width='50%'>"
						+ presentReading
						+ "</td>"

						+ "<td style=' border: 1px solid #808080;' width='50%' >"
						+ previousReading
						+ "</td>"
						+ "</tr>"

						+ "</table>"
						+ "</td>"

						+ "<td width='10%' style=' border: 1px solid #808080;vertical-align: top;'>"
						+ "<table style='width: 100%; text-align: center;'>"
						+ "<tr>"
						+ "<th style='font-weight: bold; border: 1px solid #808080;'>MF</th></tr>"
						+ "<tr><td style=' border: 1px solid #808080;'>"
						+ meterConstant
						+ "</td></tr>"

						+ "</table>"
						+ "</td>"

						+ "<td width='30%' style=' border: 1px solid #808080;vertical-align: top;'>"
						+ "<table style='width: 100%; text-align: center;'>"
						+ "<tr>"
						+ "<th style='font-weight: bold; border: 1px solid #808080;' colspan='2'>Current Consumption</th>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style=' border: 1px solid #808080;' width='50%'>Billed Units</td>"
						+ "<td style=' border: 1px solid #808080;' width='50%' >Days</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style=' border: 1px solid #808080;' width='50%'>"
						+ uomValue
						+ "</td>"

						+ "<td style=' border: 1px solid #808080;' width='50%' >"
						+ numberOfDays
						+ "</td>"
						+ "</tr>"

						+ "</table>"
						+ "</td>"
						+ "</tr>"
						+ "</table>"
						+ "</td></tr>"

						+ " <tr><td style='background-color: black; color: white; font-weight: bolder; text-align: center;'>Bill Details</td><td style='background-color: #808000; color: white; font-weight: bolder; text-align: center;'>Slab Details</td><td style='background-color: #87CEEB; color: white; font-weight: bolder; text-align: center;'>Arrear Details</td></tr>"
						+ "<tr>"

						+ "<td style='width:400px;vertical-align: top;'>"
						+ "<table style='width: 100%;margin-left: 2px;'>"
						+ bliStr
						+ ""
						+ "<tr>"
						+ "<td style=''><b>Total Charges Payable by Due Date</b></td> <td style='padding:  0.2em;'>"
						+ electricityBillEntity.getBillAmount()
						+ "</td> "
						+ "</tr>"
						+ "</table>"
						+ "</td>"

						+ "<td style='width:400px; vertical-align: top;'>"
						+ "<table style='width: 100%;margin-left: 2px;'>"
						+ "<tr>"
						+ "<td style='' ><p>Units</p></td> "
						+ "<td style='' ><p>Rates </p></td> "
						+ "<td style='' ><p>Amount (Rs.) </p></td> "
						+ "</tr>"
						+ slabString
						+ "<tr style='background-color: #808000; color: white; font-weight: bolder; text-align: center;width: 100%;'><td colspan='3'>DG Details</td></tr>"
						+ "<tr>"
						+ "<td style='' ><p>Units</p></td>"
						+ "<td style='' ><p>Rates </p></td>"
						+ "<td style='' ><p>Amount (Rs.) </p></td>"
						+ "</tr>"
						+ "<tr>"
						+ dgSlabString
						+ "</tr>"
						+ "</table>"
						+ "</td>"
						+ "<td style='width:400px; vertical-align: top;'>"
						+ "<table style='width: 100%;margin-left: 2px;'>"
						+ arrearString
						+ "	</table>"
						+ "</td>"
						+ "</tr>"
						+ "<tr><td colspan='2' style='background-color: #808000; color: black; font-weight: bolder; text-align: center;'>Consumption Graph</td><td  style='background-color: #808000; color: black; font-weight: bolder; text-align: center;'>Previous Consumption Pattern</td></tr>"
						+ "<td colspan='2' style='width:400px; vertical-align: top;'>"
						+ "<div id='container' style='height: 200px; max-height: 200px; width: 350px; max-width: 350px'>"
						+ "</div>"
						/*
						 * +"<img id='chart' style='width: 600px;' />"
						 * +"<canvas id='canvas'></canvas>"
						 */
						+ "</td>"
						+ "<td  style='width:400px;vertical-align: top;'>"
						+ "<table style='width: 100%;margin-left: 2px;'>"
						+ "<tr>"
						+ "<td style='' ><p>Bill Month</p></td>"
						+ "<td style='' ><p>Units</p></td>"
						+ "<td style='' ><p>Amount</p></td> "
						+ "</tr>"
						+ lastSixBills
						+ "<tr style='background-color: #808000; color: black; font-weight: bolder; text-align: center;width: 100%;'><td colspan='3'>Last Payment Status</td></tr>"
						+ paymentString
						+ "</table>"
						+ "</td>"
						+ "</tr>"
						+ "<tr style='background-color: #808000'>"
						+ "<td colspan='3' style='backgound: black; color: black; font-weight: bolder;'><u>Notes : Skyon Resident Welfare Association</u></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td colspan='3' style='width:400px; vertical-align: top;'>"
						+ "<table style='width: 100%;margin-left: 10px;'>"
						+ "<tr>"
						+ "<td style='' ><p>1 Payment Can be made by crossed cheque/DD in favour of Skyon Resident Welfare Association (A/C No...xxxxxx)payable at Gurgaon</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='' ><p>2 Resident can pay through RTGS/NEFT as per details :- Skyon Resident Welfare Association. A/C No xxxxxx, kotak bank Golf course road, Sector-53 Gurgaon </p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='' ><p>3 Add Rs 50/- for outstation cheques and bank charge of 150/- shall be levied on dishonour cheques </p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='' ><p>4 Cheque should not be post dated and write your Name, Flat no, Contact no on the reverse of the cheque.</p></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='' ><p>5 For more details kindly login on <b style='font-size:13px;color: blue;'><u>www.icommunity.in</u></b> and follow the given instructions </p></td> "
						+ "</tr>" + "</table>" + "</td>" + "</tr>"

						+ "</table>" + "</div>";
			}
		}
		map.put("string", str);
		return map;

	}

	public Float interestCalculationSW(int rateMasterID,
			float amountForInteresetCal,
			ElectricityBillEntity electricityBillEntity) {

		/*
		 * List<InterestSettingsEntity> interestSettingsEntities =
		 * interestSettingService.findAllData(); String interestType = null; for
		 * (InterestSettingsEntity interestSettingsEntity :
		 * interestSettingsEntities) { interestType =
		 * interestSettingsEntity.getInterestBasedOn();
		 * logger.info(" Interest Calculation base on -->" + interestType); }
		 */
		String configName = "Interest Calculation";
		String status = "Active";
		String interestType = electricityBillsService.getBillingConfigValue(
				configName, status);
		logger.info("interestType ==================== " + interestType);
		Map<Object, Object> consolidatedBill = new HashMap<>();
		Float interestAmountAfterDueDate = 0.0f;
		SolidWasteRateMaster elRateMaster = solidWasteRateMasterService
				.find(rateMasterID);
		if (interestType != null) {
			if (interestType.trim().equalsIgnoreCase("Daywise")) {
				logger.info("--------------------- Interest calculation based on ----"
						+ interestType);
				Calendar cal = Calendar.getInstance();
				cal.setTime(electricityBillEntity.getBillDueDate());
				cal.add(Calendar.DATE, 15);
				Date nextBillDate = cal.getTime();
				logger.info("nextBillDate ------------------ " + nextBillDate);

				consolidatedBill = calculationController
						.interstAmountSolidWaste(elRateMaster,
								electricityBillEntity.getBillDueDate(),
								nextBillDate, amountForInteresetCal);
				for (Entry<Object, Object> map1 : consolidatedBill.entrySet()) {
					if (map1.getKey().equals("total")) {
						interestAmountAfterDueDate = interestAmountAfterDueDate
								+ (Float) map1.getValue();
					}
				}
			} else {
				logger.info("--------------------- Interest calculation based on ----"
						+ interestType);
				Calendar cal = Calendar.getInstance();
				cal.setTime(electricityBillEntity.getBillDueDate());
				cal.add(Calendar.DATE, 30);
				Date nextBillDate = cal.getTime();
				logger.info("nextBillDate ------------------ " + nextBillDate);
				consolidatedBill = calculationController
						.interstAmountSolidWaste(elRateMaster,
								electricityBillEntity.getBillDueDate(),
								nextBillDate, amountForInteresetCal);
				for (Entry<Object, Object> map1 : consolidatedBill.entrySet()) {
					if (map1.getKey().equals("total")) {
						interestAmountAfterDueDate = interestAmountAfterDueDate
								+ (Float) map1.getValue();
					}
				}
			}
		}
		return interestAmountAfterDueDate;

	}

	public Float interestCalculationGas(int rateMasterID,
			float amountForInteresetCal,
			ElectricityBillEntity electricityBillEntity) {

		/*
		 * List<InterestSettingsEntity> interestSettingsEntities =
		 * interestSettingService.findAllData(); String interestType = null; for
		 * (InterestSettingsEntity interestSettingsEntity :
		 * interestSettingsEntities) { interestType =
		 * interestSettingsEntity.getInterestBasedOn();
		 * logger.info(" Interest Calculation base on -->" + interestType); }
		 */
		String configName = "Interest Calculation";
		String status = "Active";
		String interestType = electricityBillsService.getBillingConfigValue(
				configName, status);
		logger.info("interestType ==================== " + interestType);
		Map<Object, Object> consolidatedBill = new HashMap<>();
		Float interestAmountAfterDueDate = 0.0f;
		GasRateMaster elRateMaster = gasRateMasterService.find(rateMasterID);
		if (interestType != null) {
			if (interestType.trim().equalsIgnoreCase("Daywise")) {
				logger.info("--------------------- Interest calculation based on ----"
						+ interestType);
				Calendar cal = Calendar.getInstance();
				cal.setTime(electricityBillEntity.getBillDueDate());
				cal.add(Calendar.DATE, 15);
				Date nextBillDate = cal.getTime();
				logger.info("nextBillDate ------------------ " + nextBillDate);
				consolidatedBill = calculationController.interstAmountGas(
						elRateMaster, electricityBillEntity.getBillDueDate(),
						nextBillDate, amountForInteresetCal);
				for (Entry<Object, Object> map1 : consolidatedBill.entrySet()) {
					if (map1.getKey().equals("total")) {
						interestAmountAfterDueDate = interestAmountAfterDueDate
								+ (Float) map1.getValue();
					}
				}
			} else {
				logger.info("--------------------- Interest calculation based on ----"
						+ interestType);
				Calendar cal = Calendar.getInstance();
				cal.setTime(electricityBillEntity.getBillDueDate());
				cal.add(Calendar.DATE, 30);
				Date nextBillDate = cal.getTime();
				logger.info("nextBillDate ------------------ " + nextBillDate);
				consolidatedBill = calculationController.interstAmountGas(
						elRateMaster, electricityBillEntity.getBillDueDate(),
						nextBillDate, amountForInteresetCal);
				for (Entry<Object, Object> map1 : consolidatedBill.entrySet()) {
					if (map1.getKey().equals("total")) {
						interestAmountAfterDueDate = interestAmountAfterDueDate
								+ (Float) map1.getValue();
					}
				}
			}
		}
		return interestAmountAfterDueDate;

	}

	public Float interestCalculationWt(int rateMasterID,
			float amountForInteresetCal,
			ElectricityBillEntity electricityBillEntity) {
		/*
		 * List<InterestSettingsEntity> interestSettingsEntities =
		 * interestSettingService.findAllData(); String interestType = null; for
		 * (InterestSettingsEntity interestSettingsEntity :
		 * interestSettingsEntities) { interestType =
		 * interestSettingsEntity.getInterestBasedOn();
		 * logger.info(" Interest Calculation base on -->" + interestType); }
		 */
		String configName = "Interest Calculation";
		String status = "Active";
		String interestType = electricityBillsService.getBillingConfigValue(
				configName, status);
		logger.info("interestType ==================== " + interestType);
		Map<Object, Object> consolidatedBill = new HashMap<>();
		Float interestAmountAfterDueDate = 0.0f;
		WTRateMaster elRateMaster = wtRateMasterService.find(rateMasterID);
		if (interestType != null) {
			logger.info("--------------------- Interest calculation based on ----"
					+ interestType);
			if (interestType.trim().equalsIgnoreCase("Daywise")) {

				Calendar cal = Calendar.getInstance();
				cal.setTime(electricityBillEntity.getBillDueDate());
				cal.add(Calendar.DATE, 15);
				Date nextBillDate = cal.getTime();
				consolidatedBill = calculationController.interstAmountWater(
						elRateMaster, electricityBillEntity.getBillDueDate(),
						nextBillDate, amountForInteresetCal);
				for (Entry<Object, Object> map1 : consolidatedBill.entrySet()) {
					if (map1.getKey().equals("total")) {
						interestAmountAfterDueDate = interestAmountAfterDueDate
								+ (Float) map1.getValue();
					}
				}
			} else {
				Calendar cal = Calendar.getInstance();
				cal.setTime(electricityBillEntity.getBillDueDate());
				cal.add(Calendar.DATE, 30);
				Date nextBillDate = cal.getTime();
				consolidatedBill = calculationController.interstAmountWater(
						elRateMaster, electricityBillEntity.getBillDueDate(),
						nextBillDate, amountForInteresetCal);
				for (Entry<Object, Object> map1 : consolidatedBill.entrySet()) {
					if (map1.getKey().equals("total")) {
						logger.info("map1.getValue() ------------ "
								+ map1.getValue());
						interestAmountAfterDueDate = interestAmountAfterDueDate
								+ (Float) map1.getValue();
					}
				}
			}
		}
		return interestAmountAfterDueDate;
	}

	public Float interestCalculationEL(int rateMasterID,float amountForInteresetCal,ElectricityBillEntity electricityBillEntity) {

		/*
		 * List<InterestSettingsEntity> interestSettingsEntities =
		 * interestSettingService.findAllData(); String interestType = null; for
		 * (InterestSettingsEntity interestSettingsEntity :
		 * interestSettingsEntities) { interestType =
		 * interestSettingsEntity.getInterestBasedOn();
		 * logger.info(" Interest Calculation base on -->" + interestType); }
		 */

		String configName = "Interest Calculation";
		String status = "Active";
		String interestType = electricityBillsService.getBillingConfigValue(configName, status);
		logger.info("interestType ==================== " + interestType);
		Map<Object, Object> consolidatedBill = new HashMap<>();
		Float interestAmountAfterDueDate = 0.0f;
		if (interestType != null) {
			if (interestType.trim().equalsIgnoreCase("Daywise")) {
				logger.info("--------------------- Interest calculation based on ----"+ interestType);

				ELRateMaster elRateMaster = rateMasterService.find(rateMasterID);
				Calendar cal = Calendar.getInstance();
				cal.setTime(electricityBillEntity.getBillDueDate());
				cal.add(Calendar.DATE, 15);
				Date nextBillDate = cal.getTime();
				logger.info("nextBillDate ------------------ " + nextBillDate);
				consolidatedBill = calculationController.interstAmount(
						elRateMaster, electricityBillEntity.getBillDueDate(),
						nextBillDate, amountForInteresetCal);
				for (Entry<Object, Object> map1 : consolidatedBill.entrySet()) {
					if (map1.getKey().equals("total")) {
						interestAmountAfterDueDate = interestAmountAfterDueDate	+ (Float) map1.getValue();
					}
				}
			} else {
				logger.info("--------------------- Interest calculation based on ----"+ interestType);

				ELRateMaster elRateMaster = rateMasterService.find(rateMasterID);
				Calendar cal = Calendar.getInstance();
				cal.setTime(electricityBillEntity.getBillDueDate());
				logger.info("::::::::::"+electricityBillEntity.getBillDueDate());
				cal.add(Calendar.MONTH, 1);//cal.add(Calendar.DATE, 30);
				Date nextBillDate = cal.getTime();
				logger.info("nextBillDate ------------------ " + nextBillDate);
				consolidatedBill = calculationController.interstAmount(elRateMaster, electricityBillEntity.getBillDueDate(),nextBillDate, amountForInteresetCal);
				for (Entry<Object, Object> map1 : consolidatedBill.entrySet()) {
					if (map1.getKey().equals("total")) {
						interestAmountAfterDueDate = interestAmountAfterDueDate+ (Float) map1.getValue();
					}
				}
			}
		}
		return interestAmountAfterDueDate;
	}

	public String getTariffSlabStringDGString(int elTariffId,
			java.sql.Date previousBillDate, java.sql.Date currentBillDate,
			Float uomValue, Locale locale) {
		float slabDifference = 0.0f;
		float lastSlabTo = 0.0f;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		String slabString = "";
		List<SlabDetails> slabDetailsList = new ArrayList<>();

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth
				/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		netMonth = Math.round(netMonth * 100) / 100;
		if (netMonth == 0) {
			netMonth = daysToMonths;
			logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "
					+ netMonth);
		}

		int rateMasterID = rateMasterService.getRateMasterEC(elTariffId, "DG");
		List<ELRateSlabs> elRateSlabsList = elRateSlabService
				.getRateSlabByRateMasterId(rateMasterID);
		for (ELRateSlabs elRateSlabs : elRateSlabsList) {
			SlabDetails slabDetails = new SlabDetails();
			if (lastSlabTo == 0) {
				slabDifference = (elRateSlabs.getSlabTo() * netMonth - elRateSlabs
						.getSlabFrom() * netMonth);
				lastSlabTo = elRateSlabs.getSlabTo() * netMonth;
			} else {
				slabDifference = (elRateSlabs.getSlabTo() * netMonth - lastSlabTo
						* netMonth);
				lastSlabTo = elRateSlabs.getSlabTo() * netMonth;
			}

			if (elRateSlabs
					.getSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Rupees",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getRate())
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs.getRate()))
							+ "</p></td> </tr>";
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate(elRateSlabs.getRate());
					slabDetails.setAmount(slabDifference
							* (elRateSlabs.getRate()));
					slabDetailsList.add(slabDetails);
				} else {
					if (uomValue > 0) {

						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getRate())
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getRate()))
								+ "</p></td> </tr>";
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getRate());
						slabDetails.setAmount(uomValue
								* (elRateSlabs.getRate()));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if (elRateSlabs
					.getSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Paise",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getRate() / 100)
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs.getRate() / 100))
							+ "</p></td> </tr>";
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate(elRateSlabs.getRate());
					slabDetails.setAmount(slabDifference
							* (elRateSlabs.getRate() / 100));
					slabDetailsList.add(slabDetails);
				} else {
					if (uomValue > 0) {
						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getRate() / 100)
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getRate() / 100))
								+ "</p></td> </tr>";
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getRate());
						slabDetails.setAmount(uomValue
								* (elRateSlabs.getRate() / 100));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if (elRateSlabs
					.getSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Percentage",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getRate() / 100)
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs.getRate() / 100))
							+ "</p></td> </tr>";
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate(elRateSlabs.getRate());
					slabDetails.setAmount(slabDifference
							* (elRateSlabs.getRate() / 100));
					slabDetailsList.add(slabDetails);
				} else {
					if (uomValue > 0) {
						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getRate() / 100)
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getRate() / 100))
								+ "</p></td> </tr>";
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getRate());
						slabDetails.setAmount(uomValue
								* (elRateSlabs.getRate() / 100));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}

			if (elRateSlabs
					.getSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Multiplier",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getRate())
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs.getRate()))
							+ "</p></td> </tr>";
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate(elRateSlabs.getRate());
					slabDetails.setAmount(slabDifference
							* (elRateSlabs.getRate()));
					slabDetailsList.add(slabDetails);
				} else {
					if (uomValue > 0) {
						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getRate())
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getRate()))
								+ "</p></td> </tr>";
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getRate());
						slabDetails.setAmount(uomValue
								* (elRateSlabs.getRate()));
						uomValue = uomValue - slabDifference;
					}
				}
			}
		}
		return slabString;
	}

	@SuppressWarnings("unused")
	public List<SlabDetails> getTariffSlabStringDGList(int elTariffId,
			java.sql.Date previousBillDate, java.sql.Date currentBillDate,
			Float uomValue, Locale locale) {
		float slabDifference = 0.0f;
		float lastSlabTo = 0.0f;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		String slabString = "";
		List<SlabDetails> slabDetailsList = new ArrayList<>();

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth
				/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		netMonth = Math.round(netMonth * 100) / 100;
		if (netMonth == 0) {
			netMonth = daysToMonths;
			logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "
					+ netMonth);
		}

		int rateMasterID = rateMasterService.getRateMasterEC(elTariffId, "DG");
		List<ELRateSlabs> elRateSlabsList = elRateSlabService
				.getRateSlabByRateMasterId(rateMasterID);
		for (ELRateSlabs elRateSlabs : elRateSlabsList) {
			SlabDetails slabDetails = new SlabDetails();
			if (lastSlabTo == 0) {
				slabDifference = ((elRateSlabs.getSlabTo() * netMonth) - (elRateSlabs
						.getSlabFrom() * netMonth));
				lastSlabTo = elRateSlabs.getSlabTo() * netMonth;
			} else {
				slabDifference = ((elRateSlabs.getSlabTo() * netMonth) - lastSlabTo);
				lastSlabTo = elRateSlabs.getSlabTo() * netMonth;
			}

			if (elRateSlabs
					.getSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Rupees",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate(elRateSlabs.getRate());
					slabDetails.setAmount(slabDifference
							* (elRateSlabs.getRate()));
					slabDetailsList.add(slabDetails);
				} else {
					if (uomValue > 0) {
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getRate());
						slabDetails.setAmount(uomValue
								* (elRateSlabs.getRate()));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if (elRateSlabs
					.getSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Paise",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate(elRateSlabs.getRate());
					slabDetails.setAmount(slabDifference
							* (elRateSlabs.getRate() / 100));
					slabDetailsList.add(slabDetails);
				} else {
					if (uomValue > 0) {
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getRate());
						slabDetails.setAmount(uomValue
								* (elRateSlabs.getRate() / 100));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if (elRateSlabs
					.getSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Percentage",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate(elRateSlabs.getRate());
					slabDetails.setAmount(slabDifference
							* (elRateSlabs.getRate() / 100));
					slabDetailsList.add(slabDetails);
				} else {
					if (uomValue > 0) {
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getRate());
						slabDetails.setAmount(uomValue
								* (elRateSlabs.getRate() / 100));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}

			if (elRateSlabs
					.getSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Multiplier",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate(elRateSlabs.getRate());
					slabDetails.setAmount(slabDifference
							* (elRateSlabs.getRate()));
					slabDetailsList.add(slabDetails);
				} else {
					if (uomValue > 0) {
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getRate());
						slabDetails.setAmount(uomValue
								* (elRateSlabs.getRate()));
						uomValue = uomValue - slabDifference;
					}
				}
			}
		}
		return slabDetailsList;
	}

	@SuppressWarnings("unused")
	public List<SlabDetails> getSolidWasteTariffSlabList(
			int solidWasteTariffId, java.sql.Date previousBillDate,
			java.sql.Date currentBillDate, Float uomValue, Locale locale) {

		float slabDifference = 0.0f;
		float lastSlabTo = 0.0f;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		String slabString = "";
		List<SlabDetails> slabDetails = new ArrayList<>();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth
				/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		netMonth = Math.round(netMonth * 100) / 100;
		if (netMonth == 0) {
			netMonth = daysToMonths;
			logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "
					+ netMonth);
		}

		int rateMasterID = rateMasterService.getRateMasterSW(
				solidWasteTariffId, "Residential Collection");
		List<SolidWasteRateSlab> swRateSlabsList = elRateSlabService
				.getSWRateSlabByRateMasterId(rateMasterID);
		for (SolidWasteRateSlab elRateSlabs : swRateSlabsList) {
			SlabDetails details = new SlabDetails();
			if (lastSlabTo == 0) {
				slabDifference = ((elRateSlabs.getSolidWasteSlabTo() * netMonth) - (elRateSlabs
						.getSolidWasteSlabFrom() * netMonth));
				lastSlabTo = elRateSlabs.getSolidWasteSlabTo() * netMonth;
			} else {
				slabDifference = ((elRateSlabs.getSolidWasteSlabTo() * netMonth) - (lastSlabTo));
				lastSlabTo = elRateSlabs.getSolidWasteSlabTo() * netMonth;
			}

			if (elRateSlabs
					.getSolidWasteSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Rupees",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					details.setUnits(slabDifference);
					details.setRate(elRateSlabs.getSolidWasteRate());
					details.setAmount(slabDifference
							* (elRateSlabs.getSolidWasteRate()));
					slabDetails.add(details);
				} else {
					if (uomValue > 0) {
						details.setUnits(uomValue);
						details.setRate(elRateSlabs.getSolidWasteRate());
						details.setAmount(uomValue
								* (elRateSlabs.getSolidWasteRate()));
						slabDetails.add(details);
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if (elRateSlabs
					.getSolidWasteSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Paise",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					details.setUnits(slabDifference);
					details.setRate(elRateSlabs.getSolidWasteRate() / 100);
					details.setAmount(slabDifference
							* (elRateSlabs.getSolidWasteRate() / 100));
					slabDetails.add(details);
				} else {
					if (uomValue > 0) {
						details.setUnits(uomValue);
						details.setRate(elRateSlabs.getSolidWasteRate() / 100);
						details.setAmount(uomValue
								* (elRateSlabs.getSolidWasteRate() / 100));
						slabDetails.add(details);
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if (elRateSlabs
					.getSolidWasteSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Percentage",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					details.setUnits(slabDifference);
					details.setRate(elRateSlabs.getSolidWasteRate() / 100);
					details.setAmount(slabDifference
							* (elRateSlabs.getSolidWasteRate() / 100));
					slabDetails.add(details);
				} else {
					if (uomValue > 0) {
						details.setUnits(uomValue);
						details.setRate(elRateSlabs.getSolidWasteRate() / 100);
						details.setAmount(uomValue
								* (elRateSlabs.getSolidWasteRate() / 100));
						slabDetails.add(details);
						uomValue = uomValue - slabDifference;
					}
				}
			}

			if (elRateSlabs
					.getSolidWasteSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Multiplier",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					details.setUnits(slabDifference);
					details.setRate(elRateSlabs.getSolidWasteRate());
					details.setAmount(slabDifference
							* (elRateSlabs.getSolidWasteRate()));
					slabDetails.add(details);
				} else {
					if (uomValue > 0) {
						details.setUnits(uomValue);
						details.setRate(elRateSlabs.getSolidWasteRate());
						details.setAmount(uomValue
								* (elRateSlabs.getSolidWasteRate()));
						slabDetails.add(details);
						uomValue = uomValue - slabDifference;
					}
				}
			}
		}
		return slabDetails;
	}

	public String getSolidWasteTariffSlabString(int solidWasteTariffId,
			java.sql.Date previousBillDate, java.sql.Date currentBillDate,
			Float uomValue, Locale locale) {

		float slabDifference = 0.0f;
		float lastSlabTo = 0.0f;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		String slabString = "";

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth
				/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		netMonth = Math.round(netMonth * 100) / 100;
		if (netMonth == 0) {
			netMonth = daysToMonths;
			logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "
					+ netMonth);
		}

		int rateMasterID = rateMasterService.getRateMasterSW(
				solidWasteTariffId, "Residential Collection");
		List<SolidWasteRateSlab> swRateSlabsList = elRateSlabService
				.getSWRateSlabByRateMasterId(rateMasterID);
		for (SolidWasteRateSlab elRateSlabs : swRateSlabsList) {
			if (lastSlabTo == 0) {
				slabDifference = (elRateSlabs.getSolidWasteSlabTo() * netMonth - elRateSlabs
						.getSolidWasteSlabFrom() * netMonth);
				lastSlabTo = elRateSlabs.getSolidWasteSlabTo();
			} else {
				slabDifference = (elRateSlabs.getSolidWasteSlabTo() * netMonth - lastSlabTo
						* netMonth);
				lastSlabTo = elRateSlabs.getSolidWasteSlabTo() * netMonth;
			}

			if (elRateSlabs
					.getSolidWasteSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Rupees",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getSolidWasteRate())
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs
									.getSolidWasteRate())) + "</p></td> </tr>";
				} else {
					if (uomValue > 0) {
						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getSolidWasteRate())
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getSolidWasteRate()))
								+ "</p></td> </tr>";
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if (elRateSlabs
					.getSolidWasteSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Paise",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getSolidWasteRate() / 100)
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs
									.getSolidWasteRate() / 100))
							+ "</p></td> </tr>";
				} else {
					if (uomValue > 0) {
						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getSolidWasteRate() / 100)
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getSolidWasteRate() / 100))
								+ "</p></td> </tr>";
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if (elRateSlabs
					.getSolidWasteSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Percentage",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getSolidWasteRate() / 100)
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs
									.getSolidWasteRate() / 100))
							+ "</p></td> </tr>";
				} else {
					if (uomValue > 0) {
						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getSolidWasteRate() / 100)
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getSolidWasteRate() / 100))
								+ "</p></td> </tr>";
						uomValue = uomValue - slabDifference;
					}
				}
			}

			if (elRateSlabs
					.getSolidWasteSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Multiplier",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getSolidWasteRate())
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs
									.getSolidWasteRate())) + "</p></td> </tr>";
				} else {
					if (uomValue > 0) {
						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getSolidWasteRate())
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getSolidWasteRate()))
								+ "</p></td> </tr>";
						uomValue = uomValue - slabDifference;
					}
				}
			}
		}
		return slabString;
	}

	@SuppressWarnings("unused")
	public List<SlabDetails> getGasTariffSlabList(int gasTariffId,
			java.sql.Date previousBillDate, java.sql.Date currentBillDate,
			Float uomValue, Locale locale) {

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		String slabString = null;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		String tariffChange = null;
		float daysToMonths = (float) daysAfterMonth
				/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float totalAmount = 0;
		int rateMasterID = rateMasterService.getRateMasterGas(gasTariffId,
				"Domestic");
		// int rateMasterID = rateMasterService.getRateMasterEC(elTariffId,
		// "EC");
		GasRateMaster gasRateMaster = gasRateMasterService.find(rateMasterID);
		List<Map<String, Object>> dateList = tariffCalculationService
				.getGasMinMaxDate(gasRateMaster.getGasTariffId(),
						gasRateMaster.getGasRateName());
		List<SlabDetails> slabDetails = new ArrayList<>();
		for (Map<String, Object> date : dateList) {
			for (Entry<String, Object> map : date.entrySet()) {
				if (map.getKey().equalsIgnoreCase("validFrom")) {
					minDate = (Date) map.getValue();
				}
				if (map.getKey().equalsIgnoreCase("validTo")) {
					maxDate = (Date) map.getValue();
				}
			}
		}

		if (!(minDate.compareTo(previousBillDate) <= 0)) {
			logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
		} else if (currentBillDate.compareTo(maxDate) > 0) {
			logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
		} else {
			List<GasRateMaster> gasRateMasterList = tariffCalculationService
					.getGasRateMasterByIdName(gasRateMaster.getGasTariffId(),
							gasRateMaster.getGasRateName());
			if (billableMonths == 0) {
				logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
				for (GasRateMaster wtRateMaster1 : gasRateMasterList) {
					if ((previousBillDate.compareTo(wtRateMaster1
							.getGasValidFrom()) >= 0)
							&& (currentBillDate.compareTo(wtRateMaster1
									.getGasValidTo()) <= 0)) {
						if (wtRateMaster1
								.getGasRateType()
								.trim()
								.equalsIgnoreCase(
										messageSource.getMessage(
												"rateType.multiSlab", null,
												locale).trim())
								&& (!wtRateMaster1
										.getGasRateName()
										.trim()
										.equalsIgnoreCase(
												messageSource.getMessage(
														"rateNameType.DR",
														null, locale).trim())
										&& !wtRateMaster1
												.getGasRateName()
												.trim()
												.equalsIgnoreCase(
														messageSource
																.getMessage(
																		"rateNameType.DC",
																		null,
																		locale)
																.trim()) && !wtRateMaster1
										.getGasRateName()
										.trim()
										.equalsIgnoreCase(
												messageSource
														.getMessage(
																"rateNameType.pfPenalty",
																null, locale)
														.trim()))) {
							logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
							slabDetails = tariffCalculationService
									.tariffCalculationMultiSlabDetailsGas(
											wtRateMaster1, previousBillDate,
											currentBillDate, uomValue);
						}
					} else {
						if (previousBillDate.compareTo(wtRateMaster1
								.getGasValidTo()) <= 0) {
							if ((currentBillDate.compareTo(wtRateMaster1
									.getGasValidFrom()) >= 0)
									&& (wtRateMaster1.getGasValidTo()
											.compareTo(currentBillDate) <= 0)) {

							} else {
								if (currentBillDate.compareTo(wtRateMaster1
										.getGasValidFrom()) >= 0
										&& currentBillDate
												.compareTo(wtRateMaster1
														.getGasValidTo()) <= 0) {

								}
							}
						}
					}
				}
			} else {
				logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
				// uomValue = uomValue/ netMonth;
				for (GasRateMaster wtRateMaster1 : gasRateMasterList) {
					if (wtRateMaster1
							.getGasRateType()
							.trim()
							.equalsIgnoreCase(
									messageSource.getMessage(
											"rateType.fixedSlab", null, locale)
											.trim())) {
						uomValue = uomValue * netMonth;
					} else {
						logger.info("netMonth ---------------------- "
								+ netMonth);
					}

					if ((previousBillDate.compareTo(wtRateMaster1
							.getGasValidFrom()) >= 0)
							&& (currentBillDate.compareTo(wtRateMaster1
									.getGasValidTo()) <= 0)) {

						if (wtRateMaster1
								.getGasRateType()
								.trim()
								.equalsIgnoreCase(
										messageSource.getMessage(
												"rateType.multiSlab", null,
												locale).trim())
								&& (!wtRateMaster1
										.getGasRateName()
										.trim()
										.equalsIgnoreCase(
												messageSource.getMessage(
														"rateNameType.DR",
														null, locale).trim())
										&& !wtRateMaster1
												.getGasRateName()
												.trim()
												.equalsIgnoreCase(
														messageSource
																.getMessage(
																		"rateNameType.DC",
																		null,
																		locale)
																.trim()) && !wtRateMaster1
										.getGasRateName()
										.trim()
										.equalsIgnoreCase(
												messageSource
														.getMessage(
																"rateNameType.pfPenalty",
																null, locale)
														.trim()))) {
							logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
							slabDetails = tariffCalculationService
									.tariffCalculationMultiSlabDetailsGas(
											wtRateMaster1, previousBillDate,
											currentBillDate, uomValue);
						}
					} else {
						if (previousBillDate.compareTo(wtRateMaster1
								.getGasValidTo()) <= 0) {
							if ((currentBillDate.compareTo(wtRateMaster1
									.getGasValidFrom()) >= 0)
									&& (wtRateMaster1.getGasValidTo()
											.compareTo(currentBillDate) <= 0)) {
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");

								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue + 0;

								if (loopCount == 0) {
									startDate = new LocalDate(previousBillDate);
									endDate = new LocalDate(
											wtRateMaster1.getGasValidTo());
									endDate = endDate.plusDays(1);
									lastUpdatedDate = wtRateMaster1
											.getGasValidTo();
									loopCount = loopCount + 1;
								} else {
									startDate = new LocalDate(lastUpdatedDate);
									endDate = new LocalDate(
											wtRateMaster1.getGasValidTo());
									lastUpdatedDate = wtRateMaster1
											.getGasValidTo();
									loopCount = loopCount + 1;
								}

								if (wtRateMaster1
										.getGasRateType()
										.trim()
										.equalsIgnoreCase(
												messageSource.getMessage(
														"rateType.multiSlab",
														null, locale).trim())
										&& (!wtRateMaster1
												.getGasRateName()
												.trim()
												.equalsIgnoreCase(
														messageSource
																.getMessage(
																		"rateNameType.DR",
																		null,
																		locale)
																.trim())
												&& !wtRateMaster1
														.getGasRateName()
														.trim()
														.equalsIgnoreCase(
																messageSource
																		.getMessage(
																				"rateNameType.DC",
																				null,
																				locale)
																		.trim()) && !wtRateMaster1
												.getGasRateName()
												.trim()
												.equalsIgnoreCase(
														messageSource
																.getMessage(
																		"rateNameType.pfPenalty",
																		null,
																		locale)
																.trim()))) {
									logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
									slabDetails = tariffCalculationService
											.tariffCalculationMultiSlabDetailsGas(
													wtRateMaster1,
													startDate
															.toDateTimeAtStartOfDay()
															.toDate(),
													endDate.toDateTimeAtStartOfDay()
															.toDate(), uomValue);
								}
							} else {
								if (currentBillDate.compareTo(wtRateMaster1
										.getGasValidFrom()) >= 0
										&& currentBillDate
												.compareTo(wtRateMaster1
														.getGasValidTo()) <= 0) {
									logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
									LocalDate startDate = new LocalDate(
											wtRateMaster1.getGasValidFrom());
									LocalDate endDate = new LocalDate(
											currentBillDate);

									if (wtRateMaster1
											.getGasRateType()
											.trim()
											.equalsIgnoreCase(
													messageSource
															.getMessage(
																	"rateType.multiSlab",
																	null,
																	locale)
															.trim())
											&& (!wtRateMaster1
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															messageSource
																	.getMessage(
																			"rateNameType.DR",
																			null,
																			locale)
																	.trim())
													&& !wtRateMaster1
															.getGasRateName()
															.trim()
															.equalsIgnoreCase(
																	messageSource
																			.getMessage(
																					"rateNameType.DC",
																					null,
																					locale)
																			.trim()) && !wtRateMaster1
													.getGasRateName()
													.trim()
													.equalsIgnoreCase(
															messageSource
																	.getMessage(
																			"rateNameType.pfPenalty",
																			null,
																			locale)
																	.trim()))) {
										logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
										slabDetails = tariffCalculationService
												.tariffCalculationMultiSlabDetailsGas(
														wtRateMaster1,
														startDate
																.toDateTimeAtStartOfDay()
																.toDate(),
														endDate.toDateTimeAtStartOfDay()
																.toDate(),
														uomValue);
									}
								}
							}
						}
					}
				}
			}
		}
		return slabDetails;
	}

	public String getGasTariffSlabString(int gasTariffId,
			java.sql.Date previousBillDate, java.sql.Date currentBillDate,
			Float uomValue, Locale locale) {

		float slabDifference = 0.0f;
		float lastSlabTo = 0.0f;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		String slabString = "";

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth
				/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		netMonth = Math.round(netMonth * 100) / 100;
		if (netMonth == 0) {
			netMonth = daysToMonths;
			logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "
					+ netMonth);
		}

		int rateMasterID = rateMasterService.getRateMasterGas(gasTariffId,
				"Domestic");
		List<GasRateSlab> gasRateSlabsList = elRateSlabService
				.getGasRateSlabByRateMasterId(rateMasterID);
		for (GasRateSlab elRateSlabs : gasRateSlabsList) {

			if (lastSlabTo == 0) {
				slabDifference = (elRateSlabs.getGasSlabTo() * netMonth - elRateSlabs
						.getGasSlabFrom() * netMonth);
				lastSlabTo = elRateSlabs.getGasSlabTo() * netMonth;

			} else {
				slabDifference = (elRateSlabs.getGasSlabTo() * netMonth - lastSlabTo
						* netMonth);
				lastSlabTo = elRateSlabs.getGasSlabTo() * netMonth;

			}

			if (elRateSlabs
					.getGasSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Rupees",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getGasRate())
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs.getGasRate()))
							+ "</p></td> </tr>";
				} else {
					if (uomValue > 0) {

						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getGasRate())
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getGasRate()))
								+ "</p></td> </tr>";
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if (elRateSlabs
					.getGasSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Paise",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getGasRate() / 100)
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs.getGasRate() / 100))
							+ "</p></td> </tr>";
				} else {
					if (uomValue > 0) {
						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getGasRate() / 100)
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getGasRate() / 100))
								+ "</p></td> </tr>";
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if (elRateSlabs
					.getGasSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Percentage",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getGasRate() / 100)
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs.getGasRate() / 100))
							+ "</p></td> </tr>";
				} else {
					if (uomValue > 0) {
						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getGasRate() / 100)
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getGasRate() / 100))
								+ "</p></td> </tr>";
						uomValue = uomValue - slabDifference;
					}
				}
			}

			if (elRateSlabs
					.getGasSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Multiplier",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getGasRate())
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs.getGasRate()))
							+ "</p></td> </tr>";
				} else {
					if (uomValue > 0) {
						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getGasRate())
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getGasRate()))
								+ "</p></td> </tr>";
						uomValue = uomValue - slabDifference;
					}
				}
			}
		}
		return slabString;
	}

	@SuppressWarnings("unused")
	public List<SlabDetails> getWaterTariffSlabList(int wtTariffId,
			java.sql.Date previousBillDate, java.sql.Date currentBillDate,
			Float uomValue, Locale locale) {

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		String slabString = null;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		String tariffChange = null;
		float daysToMonths = (float) daysAfterMonth
				/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float totalAmount = 0;
		int rateMasterID = rateMasterService.getRateMasterDomesticGeneral(
				wtTariffId, "Water charges");
		// int rateMasterID = rateMasterService.getRateMasterEC(elTariffId,
		// "EC");
		WTRateMaster wtRateMaster = wtRateMasterService.find(rateMasterID);
		List<Map<String, Object>> dateList = tariffCalculationService
				.getWaterMinMaxDate(wtRateMaster.getWtTariffId(),
						wtRateMaster.getWtRateName());
		List<SlabDetails> slabDetails = new ArrayList<>();
		for (Map<String, Object> date : dateList) {
			for (Entry<String, Object> map : date.entrySet()) {
				if (map.getKey().equalsIgnoreCase("validFrom")) {
					minDate = (Date) map.getValue();
				}
				if (map.getKey().equalsIgnoreCase("validTo")) {
					maxDate = (Date) map.getValue();
				}
			}
		}

		if (!(minDate.compareTo(previousBillDate) <= 0)) {
			logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
		} else if (currentBillDate.compareTo(maxDate) > 0) {
			logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
		} else {
			List<WTRateMaster> wtRateMasterList = tariffCalculationService
					.getWaterRateMasterByIdName(wtRateMaster.getWtTariffId(),
							wtRateMaster.getWtRateName());
			if (billableMonths == 0) {
				logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
				for (WTRateMaster wtRateMaster1 : wtRateMasterList) {
					if ((previousBillDate.compareTo(wtRateMaster1
							.getWtValidFrom()) >= 0)
							&& (currentBillDate.compareTo(wtRateMaster1
									.getWtValidTo()) <= 0)) {
						if (wtRateMaster1
								.getWtRateType()
								.trim()
								.equalsIgnoreCase(
										messageSource.getMessage(
												"rateType.multiSlab", null,
												locale).trim())
								&& (!wtRateMaster1
										.getWtRateName()
										.trim()
										.equalsIgnoreCase(
												messageSource.getMessage(
														"rateNameType.DR",
														null, locale).trim())
										&& !wtRateMaster1
												.getWtRateName()
												.trim()
												.equalsIgnoreCase(
														messageSource
																.getMessage(
																		"rateNameType.DC",
																		null,
																		locale)
																.trim()) && !wtRateMaster1
										.getWtRateName()
										.trim()
										.equalsIgnoreCase(
												messageSource
														.getMessage(
																"rateNameType.pfPenalty",
																null, locale)
														.trim()))) {
							logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
							slabDetails = tariffCalculationService
									.tariffCalculationMultiSlabDetailsWater(
											wtRateMaster1, previousBillDate,
											currentBillDate, uomValue);
						}
					} else {
						if (previousBillDate.compareTo(wtRateMaster1
								.getWtValidTo()) <= 0) {
							if ((currentBillDate.compareTo(wtRateMaster1
									.getWtValidFrom()) >= 0)
									&& (wtRateMaster1.getWtValidTo().compareTo(
											currentBillDate) <= 0)) {

							} else {
								if (currentBillDate.compareTo(wtRateMaster1
										.getWtValidFrom()) >= 0
										&& currentBillDate
												.compareTo(wtRateMaster1
														.getWtValidTo()) <= 0) {

								}
							}
						}
					}
				}
			} else {
				logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
				// uomValue = uomValue/ netMonth;
				for (WTRateMaster wtRateMaster1 : wtRateMasterList) {
					if (wtRateMaster1
							.getWtRateType()
							.trim()
							.equalsIgnoreCase(
									messageSource.getMessage(
											"rateType.fixedSlab", null, locale)
											.trim())) {
						uomValue = uomValue * netMonth;
					} else {
						logger.info("netMonth ---------------------- "
								+ netMonth);
					}

					if ((previousBillDate.compareTo(wtRateMaster1
							.getWtValidFrom()) >= 0)
							&& (currentBillDate.compareTo(wtRateMaster1
									.getWtValidTo()) <= 0)) {

						if (wtRateMaster1
								.getWtRateType()
								.trim()
								.equalsIgnoreCase(
										messageSource.getMessage(
												"rateType.multiSlab", null,
												locale).trim())
								&& (!wtRateMaster1
										.getWtRateName()
										.trim()
										.equalsIgnoreCase(
												messageSource.getMessage(
														"rateNameType.DR",
														null, locale).trim())
										&& !wtRateMaster1
												.getWtRateName()
												.trim()
												.equalsIgnoreCase(
														messageSource
																.getMessage(
																		"rateNameType.DC",
																		null,
																		locale)
																.trim()) && !wtRateMaster1
										.getWtRateName()
										.trim()
										.equalsIgnoreCase(
												messageSource
														.getMessage(
																"rateNameType.pfPenalty",
																null, locale)
														.trim()))) {
							logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
							slabDetails = tariffCalculationService
									.tariffCalculationMultiSlabDetailsWater(
											wtRateMaster1, previousBillDate,
											currentBillDate, uomValue);
						}
					} else {
						if (previousBillDate.compareTo(wtRateMaster1
								.getWtValidTo()) <= 0) {
							if ((currentBillDate.compareTo(wtRateMaster1
									.getWtValidFrom()) >= 0)
									&& (wtRateMaster1.getWtValidTo().compareTo(
											currentBillDate) <= 0)) {
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");

								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue + 0;

								if (loopCount == 0) {
									startDate = new LocalDate(previousBillDate);
									endDate = new LocalDate(
											wtRateMaster1.getWtValidTo());
									endDate = endDate.plusDays(1);
									lastUpdatedDate = wtRateMaster1
											.getWtValidTo();
									loopCount = loopCount + 1;
								} else {
									startDate = new LocalDate(lastUpdatedDate);
									endDate = new LocalDate(
											wtRateMaster1.getWtValidTo());
									lastUpdatedDate = wtRateMaster1
											.getWtValidTo();
									loopCount = loopCount + 1;
								}

								if (wtRateMaster1
										.getWtRateType()
										.trim()
										.equalsIgnoreCase(
												messageSource.getMessage(
														"rateType.multiSlab",
														null, locale).trim())
										&& (!wtRateMaster1
												.getWtRateName()
												.trim()
												.equalsIgnoreCase(
														messageSource
																.getMessage(
																		"rateNameType.DR",
																		null,
																		locale)
																.trim())
												&& !wtRateMaster1
														.getWtRateName()
														.trim()
														.equalsIgnoreCase(
																messageSource
																		.getMessage(
																				"rateNameType.DC",
																				null,
																				locale)
																		.trim()) && !wtRateMaster1
												.getWtRateName()
												.trim()
												.equalsIgnoreCase(
														messageSource
																.getMessage(
																		"rateNameType.pfPenalty",
																		null,
																		locale)
																.trim()))) {
									logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
									slabDetails = tariffCalculationService
											.tariffCalculationMultiSlabDetailsWater(
													wtRateMaster1,
													startDate
															.toDateTimeAtStartOfDay()
															.toDate(),
													endDate.toDateTimeAtStartOfDay()
															.toDate(), uomValue);
								}
							} else {
								if (currentBillDate.compareTo(wtRateMaster1
										.getWtValidFrom()) >= 0
										&& currentBillDate
												.compareTo(wtRateMaster1
														.getWtValidTo()) <= 0) {
									logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
									LocalDate startDate = new LocalDate(
											wtRateMaster1.getWtValidFrom());
									LocalDate endDate = new LocalDate(
											currentBillDate);

									if (wtRateMaster1
											.getWtRateType()
											.trim()
											.equalsIgnoreCase(
													messageSource
															.getMessage(
																	"rateType.multiSlab",
																	null,
																	locale)
															.trim())
											&& (!wtRateMaster1
													.getWtRateName()
													.trim()
													.equalsIgnoreCase(
															messageSource
																	.getMessage(
																			"rateNameType.DR",
																			null,
																			locale)
																	.trim())
													&& !wtRateMaster1
															.getWtRateName()
															.trim()
															.equalsIgnoreCase(
																	messageSource
																			.getMessage(
																					"rateNameType.DC",
																					null,
																					locale)
																			.trim()) && !wtRateMaster1
													.getWtRateName()
													.trim()
													.equalsIgnoreCase(
															messageSource
																	.getMessage(
																			"rateNameType.pfPenalty",
																			null,
																			locale)
																	.trim()))) {
										logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
										slabDetails = tariffCalculationService
												.tariffCalculationMultiSlabDetailsWater(
														wtRateMaster1,
														startDate
																.toDateTimeAtStartOfDay()
																.toDate(),
														endDate.toDateTimeAtStartOfDay()
																.toDate(),
														uomValue);
									}
								}
							}
						}
					}
				}
			}
		}
		return slabDetails;
	}

	public String getWaterTariffSlabString(int wtTariffId,
			java.sql.Date previousBillDate, java.sql.Date currentBillDate,
			Float uomValue, Locale locale) {

		float slabDifference = 0.0f;
		float lastSlabTo = 0.0f;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		String slabString = "";

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth
				/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		netMonth = Math.round(netMonth * 100) / 100;
		if (netMonth == 0) {
			netMonth = daysToMonths;
			logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "
					+ netMonth);
		}

		int rateMasterID = rateMasterService.getRateMasterDomesticGeneral(
				wtTariffId, "Water charges");
		List<WTRateSlabs> wtRateSlabsList = elRateSlabService
				.getWtRateSlabByRateMasterId(rateMasterID);
		for (WTRateSlabs elRateSlabs : wtRateSlabsList) {

			if (lastSlabTo == 0) {
				slabDifference = (elRateSlabs.getWtSlabTo() * netMonth - elRateSlabs
						.getWtSlabFrom() * netMonth);
				lastSlabTo = elRateSlabs.getWtSlabTo();
			} else {
				slabDifference = (elRateSlabs.getWtSlabTo() * netMonth - lastSlabTo
						* netMonth);
				lastSlabTo = elRateSlabs.getWtSlabTo() * netMonth;
			}

			if (elRateSlabs
					.getWtSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Rupees",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getWtRate())
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs.getWtRate()))
							+ "</p></td> </tr>";
				} else {
					if (uomValue > 0) {
						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getWtRate())
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getWtRate()))
								+ "</p></td> </tr>";
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if (elRateSlabs
					.getWtSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Paise",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getWtRate() / 100)
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs.getWtRate() / 100))
							+ "</p></td> </tr>";
				} else {
					if (uomValue > 0) {
						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getWtRate() / 100)
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getWtRate() / 100))
								+ "</p></td> </tr>";
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if (elRateSlabs
					.getWtSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Percentage",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getWtRate() / 100)
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs.getWtRate() / 100))
							+ "</p></td> </tr>";
				} else {
					if (uomValue > 0) {
						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getWtRate() / 100)
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getWtRate() / 100))
								+ "</p></td> </tr>";
						uomValue = uomValue - slabDifference;
					}
				}
			}

			if (elRateSlabs
					.getWtSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Multiplier",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getWtRate())
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs.getWtRate()))
							+ "</p></td> </tr>";
				} else {
					if (uomValue > 0) {
						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getWtRate())
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getWtRate()))
								+ "</p></td> </tr>";
						uomValue = uomValue - slabDifference;
					}
				}
			}
		}
		return slabString;

	}

	/*
	 * @SuppressWarnings("unused") public List<SlabDetails>
	 * getTariffSlabList(int elTariffId, java.sql.Date
	 * previousBillDate,java.sql.Date currentBillDate, Float uomValue,Locale
	 * locale ) { float slabDifference =0.0f; float lastSlabTo = 0.0f; Date
	 * minDate = null; Date maxDate = null; LocalDate fromDate = new
	 * LocalDate(previousBillDate); LocalDate toDate = new
	 * LocalDate(currentBillDate); PeriodType monthDay =
	 * PeriodType.yearMonthDay().withYearsRemoved(); Period difference = new
	 * Period(fromDate, toDate, monthDay); float billableMonths =
	 * difference.getMonths(); int daysAfterMonth = difference.getDays(); String
	 * slabString=""; List<SlabDetails> slabDetailsList = new ArrayList<>();
	 * Calendar cal1 = Calendar.getInstance(); cal1.setTime(currentBillDate);
	 * float daysToMonths = (float)daysAfterMonth /
	 * cal1.getActualMaximum(Calendar.DAY_OF_MONTH); float netMonth =
	 * daysToMonths + billableMonths; if(netMonth == 0) { netMonth =
	 * daysToMonths; logger.info(
	 * "::::::::::::::: Net months if months less than one month ::::::::::: "
	 * +netMonth); }
	 * 
	 * int rateMasterID =rateMasterService.getRateMasterEC(elTariffId, "EC");
	 * //ELRateMaster elRateMaster = rateMasterService.find(rateMasterID);
	 * List<ELRateSlabs> elRateSlabsList =
	 * elRateSlabService.getRateSlabByRateMasterId(rateMasterID); for
	 * (ELRateSlabs elRateSlabs : elRateSlabsList) { SlabDetails slabDetails =
	 * new SlabDetails(); if(lastSlabTo == 0) { slabDifference =
	 * ((elRateSlabs.getSlabTo() * netMonth) - (elRateSlabs.getSlabFrom()*
	 * netMonth)); lastSlabTo = elRateSlabs.getSlabTo()* netMonth;
	 * 
	 * } else { slabDifference = ((elRateSlabs.getSlabTo()* netMonth) -
	 * (lastSlabTo)); lastSlabTo = elRateSlabs.getSlabTo()* netMonth; }
	 * 
	 * if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.
	 * getMessage("rateSlabType.Rupees",null, locale))) { if(uomValue >
	 * slabDifference) { uomValue = uomValue - slabDifference;
	 * slabDetails.setUnits(slabDifference);
	 * slabDetails.setRate(elRateSlabs.getRate());
	 * slabDetails.setAmount(slabDifference * (elRateSlabs.getRate()));
	 * slabDetailsList.add(slabDetails); } else { if(uomValue > 0) {
	 * 
	 * slabDetails.setUnits(uomValue);
	 * slabDetails.setRate(elRateSlabs.getRate());
	 * slabDetails.setAmount(uomValue * (elRateSlabs.getRate()));
	 * slabDetailsList.add(slabDetails); uomValue = uomValue - slabDifference; }
	 * } }
	 * if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource
	 * .getMessage("rateSlabType.Paise",null, locale))) { if(uomValue >
	 * slabDifference) { uomValue = uomValue - slabDifference;
	 * slabDetails.setUnits(slabDifference);
	 * slabDetails.setRate(elRateSlabs.getRate()/100);
	 * slabDetails.setAmount(slabDifference * (elRateSlabs.getRate()/100));
	 * slabDetailsList.add(slabDetails); } else { if(uomValue > 0) {
	 * slabDetails.setUnits(uomValue);
	 * slabDetails.setRate(elRateSlabs.getRate()/100);
	 * slabDetails.setAmount(uomValue * (elRateSlabs.getRate()/100));
	 * slabDetailsList.add(slabDetails); uomValue = uomValue - slabDifference; }
	 * } }
	 * if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource
	 * .getMessage("rateSlabType.Percentage",null, locale))) { if(uomValue >
	 * slabDifference) { uomValue = uomValue - slabDifference;
	 * slabDetails.setUnits(slabDifference);
	 * slabDetails.setRate(elRateSlabs.getRate()/100);
	 * slabDetails.setAmount(slabDifference * (elRateSlabs.getRate()/100));
	 * slabDetailsList.add(slabDetails); } else { if(uomValue > 0) {
	 * slabDetails.setUnits(uomValue);
	 * slabDetails.setRate(elRateSlabs.getRate()/100);
	 * slabDetails.setAmount(uomValue * (elRateSlabs.getRate()/100));
	 * slabDetailsList.add(slabDetails); uomValue = uomValue - slabDifference; }
	 * } }
	 * 
	 * if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.
	 * getMessage("rateSlabType.Multiplier",null, locale))) { if(uomValue >
	 * slabDifference) { uomValue = uomValue - slabDifference;
	 * slabDetails.setUnits(slabDifference);
	 * slabDetails.setRate(elRateSlabs.getRate());
	 * slabDetails.setAmount(slabDifference * (elRateSlabs.getRate()));
	 * slabDetailsList.add(slabDetails); } else { if(uomValue > 0) {
	 * slabDetails.setUnits(uomValue);
	 * slabDetails.setRate(elRateSlabs.getRate());
	 * slabDetails.setAmount(uomValue * (elRateSlabs.getRate()));
	 * slabDetailsList.add(slabDetails); uomValue = uomValue - slabDifference; }
	 * } } } return slabDetailsList; }
	 */

	@SuppressWarnings("unused")
	public List<SlabDetails> getTariffSlabList(int elTariffId,
			java.sql.Date previousBillDate, java.sql.Date currentBillDate,
			Float uomValue, Locale locale) {

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		String slabString = null;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		String tariffChange = null;
		float daysToMonths = (float) daysAfterMonth
				/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		netMonth = Math.round(netMonth * 100) / 100;
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float totalAmount = 0;

		logger.info("netMonth ============== " + netMonth);
		logger.info("uomValue ============== " + uomValue);
		Float uomValueTest = 0f;
		if (uomValue == 0) {
			uomValueTest = 0f;
		} else {
			uomValueTest = netMonth / uomValue;
		}

		logger.info("uomValueTest ========== " + uomValueTest);
		String hariyanaTairff = elTariffMasterService.getStateName(elTariffId);
		logger.info("hariyanaTairff =========== " + hariyanaTairff);
		ELRateMaster elRateMasterCategory = null;

		int rateMasterID = rateMasterService.getRateMasterEC(elTariffId, "EC");
		ELRateMaster elRateMaster = rateMasterService.find(rateMasterID);

		String category = "None";
		if (hariyanaTairff != null) {
			if (hariyanaTairff.equalsIgnoreCase("Haryana")) {

				if (elRateMaster.getRateNameCategory().equalsIgnoreCase("None")) {
					elRateMasterCategory = rateMasterService
							.getRateMasterByRateName(
									elRateMaster.getElTariffID(), "EC",
									category);
				} else {
					if (uomValueTest >= 0 && uomValueTest <= 800) {
						logger.info(" ============ EC category 1 tariff ======================");
						category = ResourceBundle.getBundle("utils").getString(
								"category1");
						logger.info("category1Tariff ================= "
								+ category);
						if (category != null) {
							elRateMasterCategory = rateMasterService
									.getRateMasterByRateName(elTariffId, "EC",
											category);
						}
					} else if (uomValueTest > 800) {
						category = ResourceBundle.getBundle("utils").getString(
								"category2");
						logger.info("category1Tariff ================= "
								+ category);
						if (category != null) {
							elRateMasterCategory = rateMasterService
									.getRateMasterByRateName(elTariffId, "EC",
											category);
						}
					}
				}
			} else {
				logger.info("IF not hariyan =============== ");
				elRateMaster = elRateMaster;
			}
		}
		List<Map<String, Object>> dateList = tariffCalculationService
				.getMinMaxDate1(elRateMaster.getElTariffID(),
						elRateMaster.getRateName(), category);
		List<SlabDetails> slabDetails = new ArrayList<>();
		for (Map<String, Object> date : dateList) {
			for (Entry<String, Object> map : date.entrySet()) {
				if (map.getKey().equalsIgnoreCase("validFrom")) {
					minDate = (Date) map.getValue();
				}
				if (map.getKey().equalsIgnoreCase("validTo")) {
					maxDate = (Date) map.getValue();
				}
			}
		}

		if (!(minDate.compareTo(previousBillDate) <= 0)) {
			logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
		} else if (currentBillDate.compareTo(maxDate) > 0) {
			logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
		} else {
			List<ELRateMaster> elRateMasterList = tariffCalculationService
					.getRateMasterByIdName1(elRateMaster.getElTariffID(),
							elRateMaster.getRateName(), category);
			logger.info("elRateMasterList == " + elRateMasterList.size());
			if (billableMonths == 0) {
				logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
				for (ELRateMaster elRateMaster1 : elRateMasterList) {
					if ((previousBillDate.compareTo(elRateMaster1
							.getValidFrom()) >= 0)
							&& (currentBillDate.compareTo(elRateMaster1
									.getValidTo()) <= 0)) {
						if (elRateMaster1
								.getRateType()
								.trim()
								.equalsIgnoreCase(
										messageSource.getMessage(
												"rateType.multiSlab", null,
												locale).trim())
								&& (!elRateMaster1
										.getRateName()
										.trim()
										.equalsIgnoreCase(
												messageSource.getMessage(
														"rateNameType.DR",
														null, locale).trim())
										&& !elRateMaster1
												.getRateName()
												.trim()
												.equalsIgnoreCase(
														messageSource
																.getMessage(
																		"rateNameType.DC",
																		null,
																		locale)
																.trim()) && !elRateMaster1
										.getRateName()
										.trim()
										.equalsIgnoreCase(
												messageSource
														.getMessage(
																"rateNameType.pfPenalty",
																null, locale)
														.trim()))) {
							logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
							slabDetails = tariffCalculationService
									.tariffCalculationMultiSlabDetails(
											elRateMaster1, previousBillDate,
											currentBillDate, uomValue);
						}
					} else {
						if (previousBillDate.compareTo(elRateMaster1
								.getValidTo()) <= 0) {
							if ((currentBillDate.compareTo(elRateMaster1
									.getValidFrom()) >= 0)
									&& (elRateMaster1.getValidTo().compareTo(
											currentBillDate) <= 0)) {

							} else {
								if (currentBillDate.compareTo(elRateMaster1
										.getValidFrom()) >= 0
										&& currentBillDate
												.compareTo(elRateMaster1
														.getValidTo()) <= 0) {

								}
							}
						}
					}
				}
			} else {
				logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
				// uomValue = uomValue/ netMonth;
				for (ELRateMaster elRateMaster1 : elRateMasterList) {
					logger.info("elRateMaster1.getRateNameCategory() ============ "
							+ elRateMaster1.getRateNameCategory());
					if (elRateMaster1
							.getRateType()
							.trim()
							.equalsIgnoreCase(
									messageSource.getMessage(
											"rateType.fixedSlab", null, locale)
											.trim())) {
						uomValue = uomValue * netMonth;
					} else {
						logger.info("netMonth ---------------------- "
								+ netMonth);
					}

					if ((previousBillDate.compareTo(elRateMaster1
							.getValidFrom()) >= 0)
							&& (currentBillDate.compareTo(elRateMaster1
									.getValidTo()) <= 0)) {

						if (elRateMaster1
								.getRateType()
								.trim()
								.equalsIgnoreCase(
										messageSource.getMessage(
												"rateType.multiSlab", null,
												locale).trim())
								&& (!elRateMaster1
										.getRateName()
										.trim()
										.equalsIgnoreCase(
												messageSource.getMessage(
														"rateNameType.DR",
														null, locale).trim())
										&& !elRateMaster1
												.getRateName()
												.trim()
												.equalsIgnoreCase(
														messageSource
																.getMessage(
																		"rateNameType.DC",
																		null,
																		locale)
																.trim()) && !elRateMaster1
										.getRateName()
										.trim()
										.equalsIgnoreCase(
												messageSource
														.getMessage(
																"rateNameType.pfPenalty",
																null, locale)
														.trim()))) {
							logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
							slabDetails = tariffCalculationService
									.tariffCalculationMultiSlabDetails(
											elRateMaster1, previousBillDate,
											currentBillDate, uomValue);
						}
					} else {
						tariffChange = tariffChange + "  Valid From "
								+ elRateMaster1.getValidFrom() + " To "
								+ elRateMaster1.getValidTo() + '\n';
						logger.info("tariffChange -------------- "
								+ tariffChange);
						if (previousBillDate.compareTo(elRateMaster1
								.getValidTo()) <= 0) {
							if ((currentBillDate.compareTo(elRateMaster1
									.getValidFrom()) >= 0)
									&& (elRateMaster1.getValidTo().compareTo(
											currentBillDate) <= 0)) {
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");

								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue + 0;

								if (loopCount == 0) {
									startDate = new LocalDate(previousBillDate);
									endDate = new LocalDate(
											elRateMaster1.getValidTo());
									endDate = endDate.plusDays(1);
									lastUpdatedDate = elRateMaster1
											.getValidTo();
									loopCount = loopCount + 1;
								} else {
									startDate = new LocalDate(lastUpdatedDate);
									endDate = new LocalDate(
											elRateMaster1.getValidTo());
									lastUpdatedDate = elRateMaster1
											.getValidTo();
									loopCount = loopCount + 1;
								}

								if (elRateMaster1
										.getRateType()
										.trim()
										.equalsIgnoreCase(
												messageSource.getMessage(
														"rateType.multiSlab",
														null, locale).trim())
										&& (!elRateMaster1
												.getRateName()
												.trim()
												.equalsIgnoreCase(
														messageSource
																.getMessage(
																		"rateNameType.DR",
																		null,
																		locale)
																.trim())
												&& !elRateMaster1
														.getRateName()
														.trim()
														.equalsIgnoreCase(
																messageSource
																		.getMessage(
																				"rateNameType.DC",
																				null,
																				locale)
																		.trim()) && !elRateMaster1
												.getRateName()
												.trim()
												.equalsIgnoreCase(
														messageSource
																.getMessage(
																		"rateNameType.pfPenalty",
																		null,
																		locale)
																.trim()))) {
									logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
									slabDetails = tariffCalculationService
											.tariffCalculationMultiSlabDetails(
													elRateMaster1,
													startDate
															.toDateTimeAtStartOfDay()
															.toDate(),
													endDate.toDateTimeAtStartOfDay()
															.toDate(), uomValue);
								}
							} else {
								if (currentBillDate.compareTo(elRateMaster1
										.getValidFrom()) >= 0
										&& currentBillDate
												.compareTo(elRateMaster1
														.getValidTo()) <= 0) {
									logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
									LocalDate startDate = new LocalDate(
											elRateMaster1.getValidFrom());
									LocalDate endDate = new LocalDate(
											currentBillDate);

									if (elRateMaster1
											.getRateType()
											.trim()
											.equalsIgnoreCase(
													messageSource
															.getMessage(
																	"rateType.multiSlab",
																	null,
																	locale)
															.trim())
											&& (!elRateMaster1
													.getRateName()
													.trim()
													.equalsIgnoreCase(
															messageSource
																	.getMessage(
																			"rateNameType.DR",
																			null,
																			locale)
																	.trim())
													&& !elRateMaster1
															.getRateName()
															.trim()
															.equalsIgnoreCase(
																	messageSource
																			.getMessage(
																					"rateNameType.DC",
																					null,
																					locale)
																			.trim()) && !elRateMaster1
													.getRateName()
													.trim()
													.equalsIgnoreCase(
															messageSource
																	.getMessage(
																			"rateNameType.pfPenalty",
																			null,
																			locale)
																	.trim()))) {
										logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
										slabDetails = tariffCalculationService
												.tariffCalculationMultiSlabDetails(
														elRateMaster1,
														startDate
																.toDateTimeAtStartOfDay()
																.toDate(),
														endDate.toDateTimeAtStartOfDay()
																.toDate(),
														uomValue);
									}
								}
							}
						}
					}
				}
			}
		}
		return slabDetails;
	}

	public String getTariffSlabString(int elTariffId,
			java.sql.Date previousBillDate, java.sql.Date currentBillDate,
			Float uomValue, Locale locale) {
		float slabDifference = 0.0f;
		float lastSlabTo = 0.0f;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		String slabString = "";

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth
				/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		netMonth = Math.round(netMonth * 100) / 100;
		if (netMonth == 0) {
			netMonth = daysToMonths;
			logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "
					+ netMonth);
		}

		int rateMasterID = rateMasterService.getRateMasterEC(elTariffId, "EC");
		List<ELRateSlabs> elRateSlabsList = elRateSlabService
				.getRateSlabByRateMasterId(rateMasterID);

		for (ELRateSlabs elRateSlabs : elRateSlabsList) {
			if (lastSlabTo == 0) {
				slabDifference = (elRateSlabs.getSlabTo() * netMonth - elRateSlabs
						.getSlabFrom() * netMonth);
				lastSlabTo = elRateSlabs.getSlabTo() * netMonth;

			} else {
				slabDifference = (elRateSlabs.getSlabTo() * netMonth - lastSlabTo
						* netMonth);
				lastSlabTo = elRateSlabs.getSlabTo() * netMonth;
			}

			if (elRateSlabs
					.getSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Rupees",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getRate())
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs.getRate()))
							+ "</p></td> </tr>";
				} else {
					if (uomValue > 0) {

						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getRate())
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getRate()))
								+ "</p></td> </tr>";
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if (elRateSlabs
					.getSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Paise",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getRate() / 100)
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs.getRate() / 100))
							+ "</p></td> </tr>";
				} else {
					if (uomValue > 0) {
						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getRate() / 100)
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getRate() / 100))
								+ "</p></td> </tr>";
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if (elRateSlabs
					.getSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Percentage",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getRate() / 100)
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs.getRate() / 100))
							+ "</p></td> </tr>";
				} else {
					if (uomValue > 0) {
						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getRate() / 100)
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getRate() / 100))
								+ "</p></td> </tr>";
						uomValue = uomValue - slabDifference;
					}
				}
			}

			if (elRateSlabs
					.getSlabRateType()
					.trim()
					.equalsIgnoreCase(
							messageSource.getMessage("rateSlabType.Multiplier",
									null, locale))) {
				if (uomValue > slabDifference) {
					uomValue = uomValue - slabDifference;
					slabString += "<tr><td style='padding: 0.2em;'><p>"
							+ slabDifference
							+ "</p></td><td style='padding: 0.2em;' ><p>"
							+ (elRateSlabs.getRate())
							+ "</p></td> <td style='padding: 0.2em;' ><p>"
							+ (slabDifference * (elRateSlabs.getRate()))
							+ "</p></td> </tr>";
				} else {
					if (uomValue > 0) {
						slabString += "<tr><td style='padding: 0.2em;'><p>"
								+ uomValue
								+ "</p></td><td style='padding: 0.2em;' ><p>"
								+ (elRateSlabs.getRate())
								+ "</p></td> <td style='padding: 0.2em;' ><p>"
								+ (uomValue * (elRateSlabs.getRate()))
								+ "</p></td> </tr>";
						uomValue = uomValue - slabDifference;
					}
				}
			}
		}
		return slabString;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/bill/ajaxtable", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody
	String billTemplate(@RequestParam("accountId") int accountId) {

		Account account = accountService.find(accountId);
		// Person person = personService.find(personId);

		Date dueDate = null;

		Date billFromDate = null;
		Date billToDate = null;
		String billFromDateStr = " ";
		String billToDateStr = "  ";
		List<ElectricityBillEntity> getfromAndToDates = electricityBillsService
				.executeSimpleQuery("select e from ElectricityBillEntity e where e.accountId="
						+ accountId + " and ROWNUM<=2 order by e.elBillId desc");

		if (!getfromAndToDates.isEmpty() && getfromAndToDates.size() >= 2) {

			billFromDate = getfromAndToDates.get(0).getBillDate();
			if (billFromDate != null)
				billFromDateStr = new SimpleDateFormat("dd, MMMM, YYYY")
						.format(billFromDate);
			billToDate = getfromAndToDates.get(1).getBillDate();
			if (billToDate != null)
				billToDateStr = new SimpleDateFormat("dd, MMMM, YYYY")
						.format(billToDate);
		} else {
			billFromDate = getfromAndToDates.get(0).getBillDate();
			if (billFromDate != null)
				billFromDateStr = new SimpleDateFormat("dd, MMMM, YYYY")
						.format(billFromDate);
		}

		ElectricityBillEntity electricityBillEntity = electricityBillsService
				.getSingleResult("select obj from ElectricityBillEntity obj where obj.elBillId = (select max(e.elBillId) from ElectricityBillEntity e where e.accountId="
						+ accountId + " and e.typeOfService = 'Electricity' )");
		double eleAmt = 0.0;
		if (electricityBillEntity != null) {
			dueDate = electricityBillEntity.getBillDueDate();
			eleAmt = electricityBillEntity.getBillAmount();
		}

		ElectricityBillEntity waterBillEntity = electricityBillsService
				.getSingleResult("select obj from ElectricityBillEntity obj where obj.elBillId = (select max(e.elBillId) from ElectricityBillEntity e where e.accountId="
						+ accountId + " and e.typeOfService = 'Water' )");

		double waterAmt = 0.0;
		if (waterBillEntity != null) {
			dueDate = waterBillEntity.getBillDueDate();
			waterAmt = waterBillEntity.getBillAmount();
		}

		ElectricityBillEntity gasBillEntity = electricityBillsService
				.getSingleResult("select obj from ElectricityBillEntity obj where obj.elBillId = (select max(e.elBillId) from ElectricityBillEntity e where e.accountId="
						+ accountId + " and e.typeOfService = 'Gas' )");

		double gasAmt = 0.0;
		if (gasBillEntity != null) {
			dueDate = gasBillEntity.getBillDueDate();
			gasAmt = gasBillEntity.getBillAmount();
		}

		ElectricityBillEntity solidwasteBillEntity = electricityBillsService
				.getSingleResult("select obj from ElectricityBillEntity obj where obj.elBillId = (select max(e.elBillId) from ElectricityBillEntity e where e.accountId="
						+ accountId + " and e.typeOfService = 'Solid Waste' )");

		double swAmt = 0.0;
		if (solidwasteBillEntity != null) {
			dueDate = solidwasteBillEntity.getBillDueDate();
			swAmt = solidwasteBillEntity.getBillAmount();
		}

		ElectricityBillEntity telecomBillEntity = electricityBillsService
				.getSingleResult("select obj from ElectricityBillEntity obj where obj.elBillId = (select max(e.elBillId) from ElectricityBillEntity e where e.accountId="
						+ accountId
						+ " and e.typeOfService = 'Telephone Broadband' )");

		double teleAmt = 0.0;
		if (telecomBillEntity != null) {
			dueDate = telecomBillEntity.getBillDueDate();
			teleAmt = telecomBillEntity.getBillAmount();
		}

		ElectricityBillEntity othersBillEntity = electricityBillsService
				.getSingleResult("select obj from ElectricityBillEntity obj where obj.elBillId = (select max(e.elBillId) from ElectricityBillEntity e where e.accountId="
						+ accountId + " and e.typeOfService = 'Others' )");

		double othersAmt = 0.0;
		if (othersBillEntity != null) {
			dueDate = othersBillEntity.getBillDueDate();
			othersAmt = othersBillEntity.getBillAmount();
		}

		String dueDateStr = "";
		if (dueDate != null)
			dueDateStr = new SimpleDateFormat("dd, MMMM, YYYY").format(dueDate);

		List<ServiceMastersEntity> serviceMastersList = serviceMasterService
				.executeSimpleQuery("select obj from ServiceMastersEntity obj where obj.accountObj.accountId="
						+ accountId);

		String elTariffName = "Electricity Tariff";
		String gsTariffName = "Gas Tariff";
		String wtTariffName = "Water Tariff";
		String swTariffName = "Solid Waste Tariff";
		String teleTariffName = "Telecom Tariff";
		String otTariffName = "Other Tariff";

		if (!serviceMastersList.isEmpty() && serviceMastersList.size() > 0) {
			for (ServiceMastersEntity serviceMastersEntity : serviceMastersList) {

				if (serviceMastersEntity.getTypeOfService().equalsIgnoreCase(
						"Electricity")) {
					ELTariffMaster eltariffMaster = elTariffMasterService
							.getSingleResult("select o from ELTariffMaster o where o.elTariffID="
									+ serviceMastersEntity.getElTariffID());

					if (eltariffMaster != null)
						elTariffName = eltariffMaster.getTariffName();
				} else if (serviceMastersEntity.getTypeOfService()
						.equalsIgnoreCase("Gas")) {
					ELTariffMaster eltariffMaster = elTariffMasterService
							.getSingleResult("select o from ELTariffMaster o where o.elTariffID="
									+ serviceMastersEntity.getGaTariffID());
					if (eltariffMaster != null)
						gsTariffName = eltariffMaster.getTariffName();
				} else if (serviceMastersEntity.getTypeOfService()
						.equalsIgnoreCase("Water")) {
					WTTariffMaster wttariffMaster = wtTariffMasterService
							.getSingleResult("select o from WTRateMaster o where o.wtTariffID="
									+ serviceMastersEntity.getWtTariffID());
					if (wttariffMaster != null)
						wtTariffName = wttariffMaster.getTariffName();
				} else if (serviceMastersEntity.getTypeOfService()
						.equalsIgnoreCase("Solid Waste")) {
					ELTariffMaster eltariffMaster = elTariffMasterService
							.getSingleResult("select o from ELTariffMaster o where o.elTariffID="
									+ serviceMastersEntity.getSwTariffID());
					if (eltariffMaster != null)
						swTariffName = eltariffMaster.getTariffName();
				}
			}
		}

		// ElectricityBillEntity electricityBillEntity =
		// electricityBillsService.find(elBillId);
		String addrQuery = "select obj from Address obj where obj.personId="
				+ account.getPersonId() + " and obj.addressPrimary='Yes'";
		Address address = addressService.getSingleResult(addrQuery);

		String mobileQuery = "select obj from Contact obj where obj.personId="
				+ account.getPersonId()
				+ " and obj.contactPrimary='Yes' and obj.contactType='Mobile'";
		Contact contactMob = contactService.getSingleResult(mobileQuery);
		String emailQuery = "select obj from Contact obj where obj.personId="
				+ account.getPersonId()
				+ " and obj.contactPrimary='Yes' and obj.contactType='Email'";
		Contact contactEmail = contactService.getSingleResult(emailQuery);

		double balance = 0.0;
		double amount = 0.0;
		List<ElectricityLedgerEntity> ledger = electricityLedgerService
				.executeSimpleQuery("select obj from ElectricityLedgerEntity obj where obj.accountId="
						+ accountId + " and obj.postType='PAYMENT'");
		if (ledger != null) {
			for (int i = 0; i < ledger.size(); i++) {
				balance += ledger.get(i).getBalance();
				amount += ledger.get(i).getAmount();
			}
		}

		String str = ""
				+ "<div id='myTab'>"
				+ "<table id='tabs' style='width: 100%; background: white; border: 2px solid black; border-radius: 34px; padding: 21px 23px;'>"
				+ "<tr>"
				+ "	<td style='padding: 0.2em; border: 1px solid #808080;' width='50%' colspan='2'><img id='eye'src='http://www.ireoprojects.co.in/gallery/ireo-grandarch.jpg' height='100px' width='300px' /></td>"
				+ "	<td style='padding: 0.2em; border: 1px solid #808080;vertical-align:middle' width='49%'>Orchid Centre, DLF Golf Course Rd,<br> IILM Institute, Sector 53, <br>Gurgaon, Haryana<br> 0124 475 4000 </td>"
				+ "</tr>"
				+ "<tr>"
				+ "	<td style='padding: 0.2em; border: 1px solid #808080;background: black; color: white; font-weight: bolder;' colspan='3'>Customer Details</td>"
				+ "</tr>"
				+ "<tr>"
				+ "	<td style='padding: 0.2em; border: 1px solid #808080;padding-left: 25px' width='49%' ><b> <h4 id='name'>"
				+ account.getPerson().getFirstName()
				+ " "
				+ account.getPerson().getLastName()
				+ "</h4> </b> "
				+ "		<span id='addr'>"
				+ address.getAddress1()
				+ "</span> <br>"
				+ "		<span id='email'>"
				+ contactMob.getContactContent()
				+ "</span>,  <span id='mobile'>"
				+ contactEmail.getContactContent()
				+ "</span><br></td>"
				+ "	<td style='padding: 0.2em; border: 1px solid #808080; vertical-align: middle; border-left: 2px solid' colspan='2'>"
				+ "		<table style='width: 100%;'>"
				+ "			<tr>"
				+ "				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Account Number</b></td>"
				+ "				<td style='padding: 0.2em; border: 1px solid #808080;' id='accno'>"
				+ account.getAccountNo()
				+ "</td>"
				+ "			</tr>"
				+ "			<tr>"
				+ "				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Period From</b></td>"
				+ "				<td style='padding: 0.2em; border: 1px solid #808080;' >"
				+ billToDateStr
				+ "</td>"
				+ "			</tr>"
				+ "			<tr>"
				+ "				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Period To</b></td>"
				+ "				<td style='padding: 0.2em; border: 1px solid #808080;' >"
				+ billFromDateStr
				+ "</td>"
				+ "			</tr>"
				+ "			<tr>"
				+ "				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Due Date</b></td>"
				+ "				<td style='padding: 0.2em; border: 1px solid #808080;' >"
				+ dueDateStr
				+ "</td>"
				+ "			</tr>"
				+ "		</table>"
				+ "	</td>"
				+ "</tr>"
				+ "<tr style='background-color: black'>"
				+ "	<td style='padding: 0.2em; border: 1px solid #808080;backgound: black; color: white; font-weight: bolder;' colspan='3'>Charges</td>"
				+ "</tr>"
				+ "<tr>"
				+ "	<td style='padding: 0.2em; border: 1px solid #808080;' colspan='3'>"
				+ "		<table style='width: 100%; text-align: center;'>"
				+ "			<tr>"
				+ "				<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>Past Dues</th>"
				+ "				<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>Current Charges</th>"
				+ "				<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>Payments</th>"
				+ "				<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>Adjustment</th>"
				+ "				<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>Amount Due</th>"
				+ "			</tr>"
				+ "			<tr>"
				+ "				<td style='padding: 0.2em; border: 1px solid #808080;'>"
				+ balance
				+ "</td>"
				+ "				<td style='padding: 0.2em; border: 1px solid #808080;'>"
				+ (eleAmt + waterAmt + gasAmt + swAmt + teleAmt + othersAmt)
				+ "</td>"
				+ "				<td style='padding: 0.2em; border: 1px solid #808080;'>"
				+ (-(amount))
				+ "</td>"
				+ "				<td style='padding: 0.2em; border: 1px solid #808080;'>0.0</td>"
				+ "				<td style='padding: 0.2em; border: 1px solid #808080;'>"
				+ (balance
						+ (eleAmt + waterAmt + gasAmt + swAmt + teleAmt + othersAmt) + amount)
				+ "</td>"
				+ "			</tr>"
				+ "		</table>"
				+ "	</td>"
				+ "</tr>"
				+ "<tr style='background-color: black'>"
				+ "	<td style='padding: 0.2em; border: 1px solid #808080;backgound: black; color: white; font-weight: bolder;' colspan=3>Bill Details</td>"
				+ "</tr>"
				+ "<tr>"
				+ "	<td colspan='3'>"
				+ "		<table style='width: 100%; text-align: center;'>"
				+ "			<tr>"
				+ "				<td width='30%' style='padding: 0.2em; border: 1px solid #808080;background: #7cb5ec'>"
				+ "					<table style='width: 100%; text-align: center;'>"
				+ "						<tr>"
				+ "							<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;' colspan='2'>Electricity</th>"
				+ "						</tr>"
				+ "						<tr>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' width='50%'>Tariff</td>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' width='50%' id='eletariff'>"
				+ elTariffName
				+ "</td>"
				+ "						</tr>"
				+ "						<tr>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;'>Amount</td>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' id='eleamt'>"
				+ eleAmt
				+ "</td>"
				+ "						</tr>"
				+ "					</table>"
				+ "				</td>"
				+ "				<td width='40%' rowspan='3' id='tdcontainer' style='vertical-align: middle;'>"
				+ "					<div id='syed' style='min-width: 300px; height: 400px; max-width: 300px; margin: 0 auto; vertical-align: middle;'>"
				+ "					</div> "
				+ "				</td>"
				+ "				<td width='30%' style='padding: 0.2em; border: 1px solid #808080;background: #7798BF'>"
				+ "					<table style='width: 100%; text-align: center;'>"
				+ "						<tr>"
				+ "							<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;' colspan='2'>Gas</th>"
				+ "						</tr>"
				+ "						<tr>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' width='50%'>Tariff</td>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' width='50%' id='gastariff'>Non Subsidy</td>"
				+ "						</tr>"
				+ "						<tr>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' width='50%'>Amount</td>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' width='50%' id='gasamt'>"
				+ gasAmt
				+ "</td>"
				+ "						</tr>"
				+ "					</table>"
				+ "				</td>"
				+ "			</tr>"
				+ "			<tr>"
				+ "				<td width='30%' style='padding: 0.2em; border: 1px solid #808080;background: #f7a35c;'>"
				+ "					<table style='width: 100%; text-align: center;'>"
				+ "						<tr>"
				+ "							<th  style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;' colspan='2'>Water</th>"
				+ "						</tr>"
				+ "						<tr>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' width='50%'>Tariff</td>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' width='50%' id='watertariff'>Domestic Supply General</td>"
				+ "						</tr>"
				+ "						<tr>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;'>Amount</td>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' id='wateramt'>"
				+ waterAmt
				+ "</td>"
				+ "						</tr>"
				+ "					</table>"
				+ "				</td>"

				+ "				<td width='30%' style='padding: 0.2em; border: 1px solid #808080;background: #90ed7d'>"
				+ "					<table style='width: 100%; text-align: center;'>"
				+ "						<tr>"
				+ "							<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;' colspan='2'>Solid Waste</th>"
				+ "						</tr>"
				+ "						<tr>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' width='50%' >Tariff</td>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' width='50%' id='swtariff'>Domestic Collection</td>"
				+ "						</tr>"
				+ "						<tr>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;'>Amount</td>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' id='swamt'>"
				+ swAmt
				+ "</td>"
				+ "						</tr>"
				+ "					</table>"
				+ "				</td>"
				+ "			</tr>"
				+ "			<tr>"
				+ "				<td width='30%' style='padding: 0.2em; border: 1px solid #808080;background: #aaeeee'>"
				+ "					<table style='width: 100%; text-align: center;'>"
				+ "						<tr>"
				+ "							<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;' colspan='2'>Telephone</th>"
				+ "						</tr>"
				+ "						<tr>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' width='50%'>Tariff</td>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' width='50%' id='internettariff'>Telephone</td>"
				+ "						</tr>"
				+ "						<tr>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' >Amount</td>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' id='internetamt'>"
				+ teleAmt
				+ "</td>"
				+ "						</tr>"

				+ "					</table>"
				+ "				</td>"

				+ "				<td width='30%' style='padding: 0.2em; border: 1px solid #808080;background: #f15c80'>"
				+ "					<table style='width: 100%; text-align: center;'>"
				+ "						<tr>"
				+ "							<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;' colspan='2'>Common</th>"
				+ "						</tr>"
				+ "						<tr>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' width='50%'>Tariff</td>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' style='padding: 0.2em; border: 1px solid #808080;' width='50%' id='commontariff'>"
				+ otTariffName
				+ "</td>"
				+ "						</tr>"
				+ "						<tr>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;'>Amount</td>"
				+ "							<td style='padding: 0.2em; border: 1px solid #808080;' id='commonamt'>"
				+ othersAmt
				+ "</td>"
				+ "						</tr>"

				+ "					</table>"
				+ "				</td>"
				+ "			</tr>"
				+ "		</table>"
				+ "	</td>" + "</tr>" + "</table>" + "</div>";

		return str;

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/bill/exportData", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody
	String exportData(@RequestParam("action") String action,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, MessagingException, DocumentException {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"operatorireo@gmail.com", "ireo_123");
					}
				});

		String detailedBillPopup = "";
		if (StringUtils.equalsIgnoreCase(action, "single")) {

			String accountId = request.getParameter("accountId");
			Account account = accountService.find(Integer.parseInt(accountId));

			String toAddressEmail = "";
			String toAddressMobile = "";
			for (Contact contact : account.getPerson().getContacts()) {
				if (contact.getContactType().equals("Email")
						&& contact.getContactPrimary().equals("Yes")) {
					toAddressEmail = contact.getContactContent();
				}
				if (contact.getContactType().equals("Mobile")
						&& contact.getContactPrimary().equals("Yes")) {
					toAddressMobile = contact.getContactContent();
				}
			}

			// String personId=request.getParameter("personId");
			detailedBillPopup = billTemplate(Integer.parseInt(accountId));
			BufferedWriter bw = new BufferedWriter(
					new FileWriter("D://ht.html"));
			bw.write(detailedBillPopup);
			bw.close();

			Document document = new Document(PageSize.LETTER);
			PdfWriter.getInstance(document, new FileOutputStream(
					"d://htmlto.pdf"));
			document.open();
			document.addAuthor("Real Gagnon");
			document.addCreator("Real's HowTo");
			document.addSubject("Thanks for your support");
			document.addCreationDate();
			document.addTitle("Please read this");
			HTMLWorker htmlWorker = new HTMLWorker(document);
			htmlWorker.parse(new StringReader(detailedBillPopup));

			document.close();

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("admin@ireo.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toAddressEmail));
			message.setSubject("iREO : Bill");
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("Please find the attached bill, Thank You");
			// message.setContent("<h1>BILL</h1>", "text/html; charset=utf-8");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			String filename = "D://ht.html";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("Multi Utility Bill ");
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			Transport.send(message);

		} else {
			String accountIds = request.getParameter("accountIds");

			String acIds[] = accountIds.split(",");
			for (int i = 0; i < acIds.length; i++) {
				detailedBillPopup = billTemplate(Integer.parseInt(acIds[i]));
				BufferedWriter bw = new BufferedWriter(new FileWriter(
						"D://ht.html"));
				bw.write(detailedBillPopup);
				bw.close();
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("admin@ireo.com"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse("aizazsyed1@gmail.com"));
				message.setSubject("iREO : Bill");
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart
						.setText("Please find the attached bill, Thank You");
				// message.setContent("<h1>BILL</h1>",
				// "text/html; charset=utf-8");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				messageBodyPart = new MimeBodyPart();
				String filename = "D://ht.html";
				DataSource source = new FileDataSource(filename);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName("Multi Utility Bill.html");
				multipart.addBodyPart(messageBodyPart);
				message.setContent(multipart);
				Transport.send(message);

			}
		}
		return null;
	}

	@RequestMapping(value = "/bill/getPrevBill", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody
	List<?> getPrevBills(@RequestParam("accountId") int accountId) {

		List<ElectricityBillEntity> eList = new ArrayList<ElectricityBillEntity>();
		List<ElectricityBillEntity> billList = electricityBillsService
				.executeSimpleQuery("select o from ElectricityBillEntity o where o.accountId="
						+ accountId);
		if (billList.size() > 0) {
			for (ElectricityBillEntity electricityBillEntity : billList) {
				ElectricityBillEntity entity = new ElectricityBillEntity();
				entity.setTypeOfService(electricityBillEntity
						.getTypeOfService());
				entity.setElBillId(electricityBillEntity.getElBillId());
				entity.setAccountId(electricityBillEntity.getAccountId());
				entity.setBillAmount(electricityBillEntity.getBillAmount());
				entity.setBillDate(electricityBillEntity.getBillDate());
				entity.setBillMonth(electricityBillEntity.getBillMonth());
				entity.setCbId(electricityBillEntity.getCbId());
				entity.setStatus(electricityBillEntity.getStatus());
				entity.setPostType(electricityBillEntity.getPostType());
				eList.add(entity);
			}
		}
		return eList;
	}

	@RequestMapping(value = "/bill/cpgetAllBills", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	List<?> getAllBills(@RequestParam("accountId") int accountId) {

		List<ElectricityBillEntity> eList = new ArrayList<ElectricityBillEntity>();
		List<ElectricityBillEntity> billList = electricityBillsService
				.executeSimpleQuery("select o from ElectricityBillEntity o where o.accountId="
						+ accountId
						+ " and o.billMonth BETWEEN ADD_MONTHS(SYSDATE, 0) AND ADD_MONTHS(SYSDATE, 1)");
		if (billList.size() > 0) {
			for (ElectricityBillEntity electricityBillEntity : billList) {
				ElectricityBillEntity entity = new ElectricityBillEntity();
				entity.setTypeOfService(electricityBillEntity
						.getTypeOfService());
				entity.setElBillId(electricityBillEntity.getElBillId());
				entity.setAccountId(electricityBillEntity.getAccountId());
				entity.setBillAmount(electricityBillEntity.getBillAmount());
				entity.setBillDate(electricityBillEntity.getBillDate());
				entity.setBillMonth(electricityBillEntity.getBillMonth());
				entity.setCbId(electricityBillEntity.getCbId());
				entity.setStatus(electricityBillEntity.getStatus());
				entity.setPostType(electricityBillEntity.getPostType());
				eList.add(entity);
			}
		}
		return eList;
	}

	private void removeBlankPage(List<JRPrintPage> pages) {
		for (Iterator<JRPrintPage> i = pages.iterator(); i.hasNext();) {
			JRPrintPage page = i.next();
			if (page.getElements().size() == 4)
				i.remove();
		}
	}

	public int getNoMonthsDiff(ElectricityBillEntity billEntity) {
		Date currentBillDate = (Date) billEntity.getBillDate();
		Date lastBillDate = (Date) billEntity.getFromDate();

		Calendar c1 = Calendar.getInstance();
		c1.setTime(lastBillDate);
		c1.add(Calendar.DATE, 1);
		lastBillDate = c1.getTime();

		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(currentBillDate);
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(lastBillDate);

		int diffYear = startCalendar.get(Calendar.YEAR)
				- endCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + startCalendar.get(Calendar.MONTH)
				- endCalendar.get(Calendar.MONTH);

		return diffMonth + 1;
	}

	public void getBillDoc(int elBillId, Locale locale,int monthly) {
		

		Map<String, Object> map = new LinkedHashMap<>();
		String tariffName = "";
		String billType = "";
		String postType = "";
		String meterSrNo = "";
		String meterType = "";
		String meterMake = "";
		float sanctionLoad = 0.0f;
		Float uomValue = 0.0f;
		Float numberOfDays = 0.0f;
		Float presentReading = 0.0f;
		Float previousReading = 0.0f;
		Float meterConstant = 0.0f;
		int elTariffId = 0;
		Float pfValue = 0.0f;
		Float mdiValue = 0.0f;
		int solidWasteTariffId = 0;
		int wtTariffId = 0;
		int gasTariffId = 0;
		Double arrearsAmount = 0.0;
		String meterStatus = "";
		Double voltageLevel = 0.0;
		String connectionType = "";
		Double interestOnTax = 0.0;
		Double interestAmount = 0.0;
		Double taxAmount = 0.0;
		float amountForInteresetCal = 0.0f;
		Float latePaymentAmount = 0.0f;
		Float connectionSecurity = 0.0f;
		String othersTariffName = "";
		int othersTariffId = 0;
		
		String dgApplicable = "";
		Float dgUnits = 0.0f;
		float sanctionLoadDG = 0.0f;
		float dgMeterConstant = 0.0f;
		float dgPresentReading = 0.0f;
		float dgPreviousReading = 0.0f;
		String co_ownerFirstName="";
	    String co_ownerMiddleName="";
	    String co_ownerLastName="";
	    List<Object[]> co_ownerName=null;
		List<SlabDetails> dgSlabDetailsList = new ArrayList<>();
		List<SlabDetails> slabDetailsList = new ArrayList<>();

		ElectricityBillEntity electricityBillEntity = electricityBillsService.find(elBillId);
		
		if (electricityBillEntity != null) {
			billType = electricityBillEntity.getBillType();
			postType = electricityBillEntity.getPostType();

			List<String> contactDetailsList = camConsolidationService.getContactDetailsForMail(electricityBillEntity.getAccountObj().getPerson().getPersonId());

			Property property = propertyService.find(electricityBillEntity.getAccountObj().getPropertyId());

			String address1 = property.getProperty_No() + ","+ property.getBlocks().getBlockName();
			String city = "Gurgaon";

			String toAddressEmail = "";
			String toAddressMobile = "";

			for (Iterator<?> iterator = contactDetailsList.iterator(); iterator.hasNext();) {
				final Object[] values = (Object[]) iterator.next();
				if (((String) values[0]).equals("Email")) {
					toAddressEmail = (String) values[1];
				} else {
					toAddressMobile = (String) values[1];
				}
			}
			co_ownerName=electricityBillsService.getCo_OwnerDetails(electricityBillEntity.getAccountObj().getPropertyId());
		    for (Object[] string : co_ownerName) {
		    	co_ownerFirstName=(String)string[0];
		    	co_ownerMiddleName=(String)string[1];
		    	co_ownerLastName=(String) string[2];
				
			}

			if (electricityBillEntity.getTypeOfService().equals("CAM")) {

				logger.info("cam bill templete -------------------------------");

				HashMap<String, Object> param = new HashMap<String, Object>();
				List<LastSixMonthsBills> lastSixMonthsBills = new ArrayList<>();

				List<?> lastSixMontsBills = electricityBillsService.getLastSixMonthsCAMBills(electricityBillEntity.getAccountId(),electricityBillEntity.getTypeOfService(),electricityBillEntity.getBillDate());
				String calculationBasis = camConsolidationService.getParameterValueBasedOnParameterName("Bill Basis",elBillId);
				logger.info("calculationBasis ==================== "+ calculationBasis);
				String noOfParkingSlots = camConsolidationService.getParameterValueBasedOnParameterName("No of parking slots", elBillId);
				for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator.hasNext();) {
					final Object[] values = (Object[]) iterator.next();
					LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills();
					sixMonthsBills.setMonth((Date) values[0]);
					sixMonthsBills.setBillBases(calculationBasis);
					sixMonthsBills.setAmount((Double) values[1]);
					lastSixMonthsBills.add(sixMonthsBills);
				}

				/*BillingPaymentsEntity billingPaymentsEntity = paymentService.getPamentDetals(electricityBillEntity.getAccountId(),electricityBillEntity.getFromDate());
				if (billingPaymentsEntity != null) {
					param.put("paymentAmount",billingPaymentsEntity.getPaymentAmount());
					param.put("receiptNo", billingPaymentsEntity.getReceiptNo());
					param.put("receiptDate",billingPaymentsEntity.getReceiptDate());
					param.put("modeOfPay",billingPaymentsEntity.getPaymentMode());
				}*/
				
				//double billAmount = electricityBillEntity.getBillAmount()-sgst-cgst-parkingSlotsAmount-rounoff-previouslatePaymentAmount-checkBouncePenalty-((previouslatePaymentAmount*18)/100) ;
				double billAmount = electricityBillsService.getLineItemAmountBasedOnTransactionCode(electricityBillEntity.getElBillId(),"CAM_RATE");
				double sgst = (electricityBillsService.getLineItemAmountBasedOnTransactionCode(electricityBillEntity.getElBillId(),"CAM_SGST_TAX"));
				double cgst = (electricityBillsService.getLineItemAmountBasedOnTransactionCode(electricityBillEntity.getElBillId(),"CAM_CGST_TAX"));
				
				double parkingSlotsAmount = electricityBillsService.getLineItemAmountBasedOnTransactionCode(electricityBillEntity.getElBillId(),"CAM_PARKING_SLOT");
				double perSlotAmount      = camConsolidationService.getParkingPerSlotAmount("CAM_PARKING_SLOT");
				double arrearsAmountCam   = electricityBillEntity.getArrearsAmount();
				double checkBouncePenalty = electricityBillsService.getLineItemAmountBasedOnTransactionCode(electricityBillEntity.getElBillId(),"EL_CP");
				double previouslatePaymentAmount = electricityBillsService.getLineItemAmountBasedOnTransactionCode(electricityBillEntity.getElBillId(),"CAM_INTEREST");
				double rounoff = electricityBillsService.getLineItemAmountBasedOnTransactionCode(electricityBillEntity.getElBillId(),"CAM_ROF");
				
				
				
				
				
				
				System.err.println("=======================================>billAmount = "+billAmount);
				System.err.println("============>electricityBillEntity.getBillAmount() = "+electricityBillEntity.getBillAmount());
				System.err.println("=============================================>sgst = "+sgst);
				System.err.println("=============================================>cgst = "+cgst);
				System.err.println("===============================>parkingSlotsAmount = "+parkingSlotsAmount);
				System.err.println("==========================================>rounoff = "+rounoff);
				System.err.println("========================>previouslatePaymentAmount = "+previouslatePaymentAmount);
				System.err.println("===============================>checkBouncePenalty = "+checkBouncePenalty);
				System.err.println("=============================>GST Late Paymet @18% = "+(previouslatePaymentAmount*18)/100);
				
				double totalAmount = Math.round(electricityBillEntity.getBillAmount() + arrearsAmountCam);
				double totalCamRates = camConsolidationService.getTotalCamRates();
				
				double camAmount = ((totalCamRates*getNoMonthsDiff(electricityBillEntity))/camBillsController.getTotalNoDaysGivenMonths(electricityBillEntity))*camBillsController.getNoDaysDiff(electricityBillEntity);
				
				//double perdaycalc=(totalCamRates*getNoMonthsDiff(electricityBillEntity))/camBillsController.getTotalNoDaysGivenMonths(electricityBillEntity);
				double perdaycalc=(totalCamRates/365)*12;
				double monthdiff=0;
				if(monthly==0){
					perdaycalc=(totalCamRates/365)*12;
					monthdiff=Math.round(camBillsController.getNoDaysDiff(electricityBillEntity)*10000)/10000;
				}else{
					perdaycalc=totalCamRates;
					monthdiff=camBillsController.getNoOfMonthsDiff(electricityBillEntity);
				}
				
				DecimalFormat df = new DecimalFormat("#.##");
				df.format(0.912385);
				
				JRBeanCollectionDataSource subReportDS = new JRBeanCollectionDataSource(lastSixMonthsBills);
				JRBeanCollectionDataSource subReport1DS = new JRBeanCollectionDataSource(lastSixMonthsBills);

				param.put("subreport_datasource", subReportDS);
				param.put("subreport_datasource1", subReport1DS);
				param.put("camPoint1", ResourceBundle.getBundle("utils").getString("report.CAM.point1"));
				param.put("camPoint2", ResourceBundle.getBundle("utils").getString("report.CAM.point2"));
				param.put("camPoint3", ResourceBundle.getBundle("utils").getString("report.CAM.point3")+ " ("+ calculationBasis + ")");
				param.put("camPoint4", ResourceBundle.getBundle("utils").getString("report.CAM.point4")+ " ("+ perSlotAmount+ "/- per slot)");
				param.put("camPoint5", ResourceBundle.getBundle("utils").getString("report.CAM.point5"));
				param.put("camPoint6", ResourceBundle.getBundle("utils").getString("report.CAM.point6")+ " (Amount + Parking Slot Amount)");
				//param.put("camPoint7", ResourceBundle.getBundle("utils").getString("report.CAM.point7")+ " @ "+ serviceTaxAmount + "%");
				param.put("camPoint7", ResourceBundle.getBundle("utils").getString("report.CAM.point7"));
				//param.put("camPoint8", ResourceBundle.getBundle("utils").getString("report.CAM.point8")+ " @ "+ eCessTaxAmount + "%");
				param.put("camPoint8", ResourceBundle.getBundle("utils").getString("report.CAM.point8"));
				param.put("camPoint9A", ResourceBundle.getBundle("utils").getString("report.CAM.point9A"));
				param.put("camPoint9B", ResourceBundle.getBundle("utils").getString("report.CAM.point9B"));
				param.put("camPoint9C", ResourceBundle.getBundle("utils").getString("report.CAM.point9C"));
				param.put("camPointM", ResourceBundle.getBundle("utils").getString("report.CAM.pointMisc"));
				
				param.put("camPoint9", ResourceBundle.getBundle("utils").getString("report.CAM.point9"));
				//param.put("camPoint10", ResourceBundle.getBundle("utils").getString("report.CAM.point10"));
				param.put("camPoint11", ResourceBundle.getBundle("utils").getString("report.CAM.point11"));
				param.put("camPoint12", ResourceBundle.getBundle("utils").getString("report.CAM.point12"));
				param.put("camPoint13", ResourceBundle.getBundle("utils").getString("report.CAM.point13"));

				double vat = 0;
				String month = "";

				if (Integer.parseInt(noOfParkingSlots) == 1) {
					month = "month";
				} else {
					month = "months";
				}

				param.put("camPointValue1", camConsolidationService.getAreaOfProperty(electricityBillEntity.getAccountObj().getPropertyId()));
				param.put("camPointValue2", calculationBasis);
				param.put("camPointValue3", totalCamRates + " ( Rate per  day = "+ df.format(perdaycalc) + ")");				
				param.put("camPointValue4", parkingSlotsAmount + " ");			   
				double roundOffBillAmount = Math.round(billAmount * 100.0) / 100.0;				
				param.put("camPointValue5", roundOffBillAmount + " ( "+ df.format(perdaycalc) +" x " + camConsolidationService.getAreaOfProperty(electricityBillEntity.getAccountObj().getPropertyId())+" x "+monthdiff + ")");	
				param.put("camPointValue6",(parkingSlotsAmount +roundOffBillAmount));
			  //param.put("camPointValue7", serviceTax);
				param.put("camPointValue7", cgst);
			  //param.put("camPointValue8", sEducationAmount);
				param.put("camPointValue8", sgst);
				param.put("camPointValue9A", previouslatePaymentAmount);
				
//////////////////////////////////////////////changed after tax applicable amount changed to 7500//////////////////////////////////////////			
				double totalLineItemAmount = camConsolidationService.getTotalBillLineItemAmount(electricityBillEntity.getElBillId());
				String serviceTaxExeAmount = electricityBillsService.getBillingConfigValue("CAM Service Tax Amount","Active"); 
				double serviceTaxExe=Double.valueOf(serviceTaxExeAmount);
				if (totalLineItemAmount > serviceTaxExe) {
					param.put("camPointValue9B", (previouslatePaymentAmount*18)/100);
				}else{
					param.put("camPointValue9B", 0.0);
				}
/*.........................................................................................................................................*/			
				double miscCharges=tariffCalculationService.getMisChargesDetail(electricityBillEntity.getAccountId(),electricityBillEntity.getBillMonth());	
				
				
				param.put("camPointValue9C", checkBouncePenalty);
				param.put("camPointValueM", miscCharges+"");
				param.put("camPointValue9", rounoff);
				//param.put("camPointValue10", vat);
				param.put("camPointValue11", arrearsAmountCam);
				param.put("camPointValue12", totalAmount);
				param.put("camPointValue13",NumberToWord.convertNumberToWords((int) totalAmount)+" Only");

				param.put("title",ResourceBundle.getBundle("utils").getString("report.title"));
				param.put("companyAddress", ResourceBundle.getBundle("utils").getString("report.address"));
				param.put("point1", ResourceBundle.getBundle("utils").getString("report.point1C"));
				param.put("point2", ResourceBundle.getBundle("utils").getString("report.point2C"));
				param.put("point3", ResourceBundle.getBundle("utils").getString("report.point3C"));
				//param.put("point3.1", ResourceBundle.getBundle("utils").getString("report.point3C.1C"));
				param.put("point4", ResourceBundle.getBundle("utils").getString("report.point4C"));
				param.put("point5.1", ResourceBundle.getBundle("utils").getString("report.point5.1C"));
				param.put("point5.2", ResourceBundle.getBundle("utils").getString("report.point5.2"));
				param.put("point5.3", ResourceBundle.getBundle("utils").getString("report.point5.3"));
				param.put("point6", ResourceBundle.getBundle("utils").getString("report.point6C"));
				param.put("point7", ResourceBundle.getBundle("utils").getString("report.point6D"));
				
				
				
				param.put("amountPayble", electricityBillEntity.getNetAmount());
				param.put("dueDate", electricityBillEntity.getBillDueDate());
				String lastName="";
			    String middleName=""; 
				if (electricityBillEntity.getAccountObj().getPerson().getMiddleName() != null)
				{
					middleName = electricityBillEntity.getAccountObj().getPerson().getMiddleName();
				}
				if (electricityBillEntity.getAccountObj().getPerson().getLastName() != null)
				{
					lastName = electricityBillEntity.getAccountObj().getPerson().getLastName();
				}
			   // param.put("name", electricityBillEntity.getAccountObj().getPerson().getFirstName()+ " "+middleName +" "+lastName+" "+co_ownerFirstName+" "+co_ownerMiddleName+" "+co_ownerLastName);	
				if(co_ownerFirstName!=null && co_ownerFirstName!="")
				{
					if(co_ownerMiddleName!=null)
					{
						param.put("name", electricityBillEntity.getAccountObj().getPerson().getFirstName()+ " "+middleName +" "+lastName+", "+co_ownerFirstName+" "+co_ownerMiddleName+" "+co_ownerLastName);
					}
					else
					{
						param.put("name", electricityBillEntity.getAccountObj().getPerson().getFirstName()+ " "+middleName +" "+lastName+", "+co_ownerFirstName+" "+co_ownerLastName);
					}
					
				}
				else
				{
					param.put("name", electricityBillEntity.getAccountObj().getPerson().getFirstName()+ " "+middleName +" "+lastName);
				}
				param.put("address", address1);
				param.put("city", city);
				param.put("propartyNumber", property.getProperty_No());
				param.put("parkingSlots", noOfParkingSlots+"/"+perSlotAmount);
				param.put("areaInSqft", camConsolidationService.getAreaOfProperty(electricityBillEntity.getAccountObj().getPropertyId())+"/"+totalCamRates);
				param.put("totalAmount", totalAmount);
				param.put("amountInWords",NumberToWord.convertNumberToWords((int) totalAmount));
				param.put("mobileNo", toAddressMobile);
				param.put("emailId", toAddressEmail);
				latePaymentAmount=electricityBillEntity.getLatePaymentAmount().floatValue();
				param.put("surcharge", latePaymentAmount);
				param.put("billNo", electricityBillEntity.getBillNo());
				param.put("amountAfterDueDate",electricityBillEntity.getNetAmount()+ latePaymentAmount);
				if(electricityBillEntity.getPostType().equalsIgnoreCase("Pre Bill")){
					param.put("billDate", electricityBillEntity.getFromDate());
				}else{
					param.put("billDate", electricityBillEntity.getBillDate());
				}
				
				param.put("billingPeriod",DateFormatUtils.format((camBillsController.fromDateForCAM),"dd MMM. yyyy")+ " To "+ DateFormatUtils.format((camBillsController.ToForCAM),"dd MMM. yyyy"));
				param.put("caNo", electricityBillEntity.getAccountObj().getAccountNo());
				param.put("serviceType","CAM");
				param.put("billBasis", calculationBasis);
				param.put("realPath", "reports/");
				param.put("panNo", "AAPAS0918P");//  AACAG1252D
				//param.put("taxName", "  GSTIN No.");
				
				/*====================================================================================================================*/
				  try {
					   String gst="SELECT GSTN_NUMBER FROM GSTN_DETAILS WHERE ACCOUNT_ID='"+electricityBillEntity.getAccountId()+"'";
					   String gstno = entityManager.createNativeQuery(gst).getSingleResult().toString();
					   param.put("gstinCust",gstno);
						
				  }catch (Exception e) {
					  param.put("gstinCust","N/A");
				  }
				/*====================================================================================================================*/
						
				param.put("sTaxNo", "06AAPAS0918P1ZV");// AACAG1252DSD001
				param.put("previousBalance", (Double) paymentService.getExcessAmount(electricityBillEntity.getAccountId(),electricityBillEntity.getFromDate()));
				List<String> parkingSlots = camConsolidationService.getParkingNos(property.getPropertyId());
				String parkingNos="";
				for (String string : parkingSlots) {
					parkingNos = parkingNos +string+"/";
				}
				param.put("parkingNos", parkingNos);
				param.put("tinRegnNo", "06031829310");
				String configName = "Show Particulars";
				String status = "Active";
				String showParticulars = electricityBillsService.getBillingConfigValue(configName, status);
				logger.info("showParticulars ================= "+ showParticulars);
				if (showParticulars.equalsIgnoreCase("Yes")) {
					param.put("heading1", ResourceBundle.getBundle("utils").getString("report.CAM.heading1"));
					param.put("heading2", ResourceBundle.getBundle("utils").getString("report.CAM.heading2"));
					param.put("heading3", ResourceBundle.getBundle("utils").getString("report.CAM.heading3"));
					param.put("heading4", ResourceBundle.getBundle("utils").getString("report.CAM.heading4"));

					param.put("report.CAM.mcPoint1",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint1"));
					param.put("report.CAM.mcPoint2",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint2"));
					param.put("report.CAM.mcPoint3",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint3"));
					param.put("report.CAM.mcPoint4",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint4"));
					param.put("report.CAM.mcPoint5",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint5"));
					param.put("report.CAM.mcPoint6",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint6"));
					param.put("report.CAM.mcPoint7",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint7"));
					param.put("report.CAM.mcPoint8",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint8"));
					param.put("report.CAM.mcPoint9",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint9"));
					param.put("report.CAM.mcPoint10",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint10"));
					param.put("report.CAM.mcPoint11",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint11"));
					param.put("report.CAM.mcPoint12",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint12"));
					param.put("report.CAM.mcPoint13",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint13"));
					param.put("report.CAM.mcPoint14",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint14"));
					param.put("report.CAM.mcPoint15",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint15"));
					param.put("report.CAM.ucPoint1",ResourceBundle.getBundle("utils").getString("report.CAM.ucPoint1"));
					param.put("report.CAM.ucPoint2",ResourceBundle.getBundle("utils").getString("report.CAM.ucPoint2"));
					param.put("report.CAM.ucPoint3",ResourceBundle.getBundle("utils").getString("report.CAM.ucPoint3"));
					param.put("report.CAM.ucPoint4",ResourceBundle.getBundle("utils").getString("report.CAM.ucPoint4"));
					param.put("report.CAM.ocPoint1",ResourceBundle.getBundle("utils").getString("report.CAM.ocPoint1"));
					param.put("report.CAM.ocPoint2",ResourceBundle.getBundle("utils").getString("report.CAM.ocPoint2"));
					param.put("report.CAM.ocPoint3",ResourceBundle.getBundle("utils").getString("report.CAM.ocPoint3"));
					param.put("report.CAM.ocPoint4",ResourceBundle.getBundle("utils").getString("report.CAM.ocPoint4"));
					param.put("report.CAM.ocPoint5",ResourceBundle.getBundle("utils").getString("report.CAM.ocPoint5"));

				}

				List<ElectricityBillEntity> electricityBillEntities = new ArrayList<>();
				electricityBillEntities.add(electricityBillEntity);

				JRBeanCollectionDataSource jre = new JRBeanCollectionDataSource(
						electricityBillEntities);
				JasperPrint jasperPrint;
				logger.info("---------- JRXL compling ----------");
				JasperReport report;
				try {
					logger.info("---------- filling the report -----------");
					jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/CAMBILL.jasper"), param,jre);
					removeBlankPage(jasperPrint.getPages());
					byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
					InputStream myInputStream = new ByteArrayInputStream(bytes);
					Blob blob = Hibernate.createBlob(myInputStream);
					BillDocument billDocument = new BillDocument(electricityBillEntity, blob);
					billDocumentService.save(billDocument);
					logger.info("---------- Bill saved successfully  -----------");
				} catch (JRException e) {
					e.printStackTrace();
					logger.info("----------------- JRException");
				} catch (IOException e) {
					logger.info("----------------- IOException");
				}

			
			}
			else {

				String spmQuery = "select spm from ServiceParameterMaster spm where spm.serviceType='"+ electricityBillEntity.getTypeOfService()+ "' order by spm.spmSequence";
				String lineItems = "select bli from ElectricityBillLineItemEntity bli where bli.electricityBillEntity.elBillId="+ elBillId + "ORDER BY bli.elBillLineId ASC";
				List<ServiceParameterMaster> spmList = serviceParameterMasterService.executeSimpleQuery(spmQuery);

				if (spmList.size() > 0) {
					for (ServiceParameterMaster entity : spmList) {
						ServiceParametersEntity serviceParametersEntity = serviceParameterService.getSingleResult("select obj from ServiceParametersEntity obj where obj.spmId="+ entity.getSpmId()+ " and obj.serviceMastersEntity.accountObj.accountId="+ electricityBillEntity.getAccountObj().getAccountId());
						if (serviceParametersEntity != null) {
							if (serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Sanctioned KW")|| serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Sanctioned KVA")|| serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Sanctioned HP")) {
								sanctionLoad = Float.parseFloat(serviceParametersEntity.getServiceParameterValue());
							}
							if (serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Sanctioned DG")) {
								sanctionLoadDG = Float.parseFloat(serviceParametersEntity.getServiceParameterValue());
							}
							if (serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Connection Type")) {
								connectionType = serviceParametersEntity.getServiceParameterValue();
							}
							if (serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Voltage Level")) {
								voltageLevel = Double.parseDouble(serviceParametersEntity.getServiceParameterValue());
							}
							if (serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Connection Security")) {
								connectionSecurity = Float.parseFloat(serviceParametersEntity.getServiceParameterValue());
							}
							
							if (serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("DG Applicable")) {
								dgApplicable = serviceParametersEntity.getServiceParameterValue();
							}
						}
					}
				}

				ElectricityMetersEntity electricityMetersEntity = electricityMetersService.getMeter(electricityBillEntity.getAccountId(),electricityBillEntity.getTypeOfService());
				if (electricityMetersEntity != null) {
					meterSrNo = electricityMetersEntity.getMeterSerialNo();
					meterType = meterParameterMasterService.getMeterParameter(electricityBillEntity.getAccountId(), electricityMetersEntity.getElMeterId(), electricityMetersEntity.getTypeOfServiceForMeters(), "Meter type");
					meterMake = meterParameterMasterService.getMeterParameter(electricityBillEntity.getAccountId(), electricityMetersEntity.getElMeterId(), electricityMetersEntity.getTypeOfServiceForMeters(), "Meter Make");
					if (meterParameterMasterService.getMeterParameter(electricityBillEntity.getAccountId(), electricityMetersEntity.getElMeterId(), electricityMetersEntity.getTypeOfServiceForMeters(),"Meter Constant") != null) {
						float meterConstant1 = Float.parseFloat(meterParameterMasterService.getMeterParameter(electricityBillEntity.getAccountId(),electricityMetersEntity.getElMeterId(),electricityMetersEntity.getTypeOfServiceForMeters(),"Meter Constant"));
						if (meterConstant1 == 1) {
							meterStatus = "Normal";
						} else if (meterConstant1 < 1) {
							meterStatus = "Slow";
						} else if (meterConstant1 > 1) {
							meterStatus = "Fast";
						}
					}
				}

				List<ElectricityBillLineItemEntity> bliList = billLineItemService.executeSimpleQuery(lineItems);
				if (bliList.size() > 0) {
					for (ElectricityBillLineItemEntity electricityBillLineItemEntity : bliList) {
						List<TransactionMasterEntity> listTM = transactionMasterService.executeSimpleQuery("select o from TransactionMasterEntity o where o.transactionCode='"+ electricityBillLineItemEntity.getTransactionCode() + "'");
						if (!listTM.isEmpty()) {
							electricityBillLineItemEntity.setTransactionName(listTM.get(0).getTransactionName());
						}
					}
				}
				List<ElectricityBillLineItemEntity> arrearsList = new ArrayList<>();
				ElectricityBillEntity previoisBill;
				if (electricityBillEntity.getArrearsAmount() > 0) {
					previoisBill = electricityBillsService.getPreviousBillWithOutStatus(electricityBillEntity.getAccountId(),electricityBillEntity.getTypeOfService(),electricityBillEntity.getFromDate(),postType);
					if (previoisBill != null) {
						arrearsAmount = previoisBill.getNetAmount();
						for (ElectricityBillLineItemEntity previousBillLineItems : previoisBill.getBillLineItemEntities()) {
							List<TransactionMasterEntity> listTM = transactionMasterService.executeSimpleQuery("select o from TransactionMasterEntity o where o.transactionCode='"+ previousBillLineItems.getTransactionCode() + "'");
							if (!listTM.isEmpty()) {
								previousBillLineItems.setTransactionName(listTM.get(0).getTransactionName());
								arrearsList.add(previousBillLineItems);
							}
						}
					}
				}

				List<ServiceMastersEntity> serviceMastersList = serviceMasterService.executeSimpleQuery("select obj from ServiceMastersEntity obj where obj.accountObj.accountId="+ electricityBillEntity.getAccountObj().getAccountId()+ " and obj.typeOfService='"+ electricityBillEntity.getTypeOfService()+ "'");
				if (!serviceMastersList.isEmpty()&& serviceMastersList.size() > 0) {
					for (ServiceMastersEntity serviceMastersEntity : serviceMastersList) {

						if (serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Electricity")) {
							ELTariffMaster eltariffMaster = elTariffMasterService.getSingleResult("select o from ELTariffMaster o where o.elTariffID="+ serviceMastersEntity.getElTariffID());
							if (eltariffMaster != null) {
								elTariffId = serviceMastersEntity.getElTariffID();
								tariffName = eltariffMaster.getTariffName();
							}

						} else if (serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Gas")) {
							GasTariffMaster gasTariffMaster = gasTariffMasterService.getSingleResult("select o from GasTariffMaster o where o.gasTariffId="	+ serviceMastersEntity.getGaTariffID());
							if (gasTariffMaster != null) {
								tariffName = gasTariffMaster.getGastariffName();
								gasTariffId = gasTariffMaster.getGasTariffId();
							}
						} else if (serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Water")) {
							List<WTTariffMaster> wttariffMaster = wtTariffMasterService.executeSimpleQuery("select o from WTTariffMaster o where o.wtTariffId="+ serviceMastersEntity.getWtTariffID());
							if (wttariffMaster != null&& wttariffMaster.size() > 0) {
								tariffName = wttariffMaster.get(0).getTariffName();
								wtTariffId = wttariffMaster.get(0).getWtTariffId();
							}
						} else if (serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Solid Waste")) {
							SolidWasteTariffMaster solidWasteTariffMaster = solidWasteTariffMasterService.getSingleResult("select o from SolidWasteTariffMaster o where o.solidWasteTariffId="+ serviceMastersEntity.getSwTariffID());
							if (solidWasteTariffMaster != null) {
								tariffName = solidWasteTariffMaster.getSolidWasteTariffName();
								solidWasteTariffId = solidWasteTariffMaster.getSolidWasteTariffId();
							}
						} else if (serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Others")) {
							CommonTariffMaster otherTariffMaster = commonServiceTariffMasterServices.getSingleResult("select o from CommonTariffMaster o where o.csTariffID="+ serviceMastersEntity.getOthersTariffID());
							if (otherTariffMaster != null) {
								othersTariffName = otherTariffMaster.getCsTariffName();
								othersTariffId = otherTariffMaster.getCsTariffID();
							}
						}
					}
				}

				String paraMeterName5 = "Units";
				String uomValueString = null;
				if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName5) != null) {
					uomValueString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName5);
					if (uomValueString != null)
						uomValue = Float.parseFloat(uomValueString);
				}

				String paraMeterName6 = "Number of days";
				String numberOfDaysString = null;
				if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName6) != null) {
					numberOfDaysString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName6);
					if (numberOfDaysString != null)
						numberOfDays = Float.parseFloat(numberOfDaysString);
				}

				String paraMeterName7 = "Present reading";
				String presentReadingString = null;
				if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName7) != null) {
					presentReadingString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName7);
					if (presentReadingString != null)
						presentReading = Float.parseFloat(presentReadingString);
				}

				String paraMeterName8 = "Previous reading";
				String previousReadingString = null;
				if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName8) != null) {
					previousReadingString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName8);
					if (previousReadingString != null)
						previousReading = Float.parseFloat(previousReadingString);
				}

				String paraMeterName9 = "Meter Constant";
				String meterConstantString = null;
				if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName9) != null) {
					meterConstantString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName9);
					if (meterConstantString != null)
						meterConstant = Float.parseFloat(meterConstantString);
				}

				if("Yes".equalsIgnoreCase(dgApplicable))
				{
					String paraMeterName10 = "DG Units";
					String dgUnitsString = null;
					if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName10) != null) {
						dgUnitsString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName10);
						if (dgUnitsString != null)
							dgUnits = Float.parseFloat(dgUnitsString);
					}
					
					String paraMeterName13 = "DG Meter Constant";
					String dgMeterConstantString = null;
					if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName13) != null) {
						dgMeterConstantString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName13);
						if (dgMeterConstantString != null)
							dgMeterConstant = Float.parseFloat(dgMeterConstantString);
					}
					
					String paraMeterName14 = "Present  DG reading";
					String dgPresentReadingString = null;
					if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName14) != null) {
						dgPresentReadingString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName14);
						if (dgPresentReadingString != null)
							dgPresentReading = Float.parseFloat(dgPresentReadingString);
					}
					
					String paraMeterName15 = "Previous DG reading";
					String dgPreviousReadingString = null;
					if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName15) != null) {
						dgPreviousReadingString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName15);
						if (dgPreviousReadingString != null)
							dgPreviousReading = Float.parseFloat(dgPreviousReadingString);
					}
				}
				
				if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Electricity")) {
					if (electricityMetersEntity.getMeterType().equalsIgnoreCase("Trivector Meter")) {
						logger.info("------------- PF and MDI is applicable ----------------");
						String paraMeterName11 = "PF";
						String pfString = null;
						if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName11) != null) {
							pfString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName11);
							pfValue = Float.parseFloat(pfString);
						}
						String paraMeterName12 = "Maximum Demand Recorded";
						String mdiString = null;
						if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName12) != null) {
							mdiString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName12);
							mdiValue = Float.parseFloat(mdiString);
						}
					}
				}
				List<LastSixMonthsBills> lastSixMonthsBills = new ArrayList<>();
				if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Electricity")) {
					slabDetailsList = getTariffSlabList(elTariffId,electricityBillEntity.getFromDate(),electricityBillEntity.getBillDate(), uomValue,locale);
					int rateMasterID = rateMasterService.getRateMasterEC(elTariffId, "Rate of interest");

					for (ElectricityBillLineItemEntity electricityBillLineItemEntity : electricityBillEntity.getBillLineItemEntities()) {

						if (electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("EL_TAX")) {
							taxAmount = electricityBillLineItemEntity.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("EL_INTEREST")) {
							interestAmount = electricityBillLineItemEntity.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("EL_TAX_INTEREST")) {
							interestOnTax = electricityBillLineItemEntity.getBalanceAmount();
						}
					}
					amountForInteresetCal = (float) (electricityBillEntity.getNetAmount() - (interestOnTax + interestAmount + taxAmount));
					if (rateMasterID != 0) {
						//latePaymentAmount = interestCalculationEL(rateMasterID,amountForInteresetCal, electricityBillEntity);
						latePaymentAmount = electricityBillEntity.getLatePaymentAmount().floatValue();
						
					}
					if (dgApplicable.equalsIgnoreCase("Yes")) {
						dgSlabDetailsList = getTariffSlabStringDGList(elTariffId,electricityBillEntity.getFromDate(),electricityBillEntity.getBillDate(), dgUnits,locale);
					}

				} else if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Water")) {
					slabDetailsList = getWaterTariffSlabList(wtTariffId,electricityBillEntity.getFromDate(),electricityBillEntity.getBillDate(), uomValue,locale);
					for (ElectricityBillLineItemEntity electricityBillLineItemEntity : electricityBillEntity.getBillLineItemEntities()) {

						if (electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("WT_TAX")) {
							taxAmount = electricityBillLineItemEntity.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("WT_INTEREST")) {
							interestAmount = electricityBillLineItemEntity.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("WT_TAX_INTEREST")) {
							interestOnTax = electricityBillLineItemEntity.getBalanceAmount();
						}
					}
					int rateMasterID = rateMasterService.getRateMasterDomesticGeneral(wtTariffId,"Rate of interest");
					amountForInteresetCal = (float) (electricityBillEntity.getNetAmount() - (interestOnTax + interestAmount + taxAmount));
					if (rateMasterID != 0) {
						latePaymentAmount = interestCalculationWt(rateMasterID,amountForInteresetCal, electricityBillEntity);
					}

					List<?> lastSixMontsBills = electricityBillsService.getLastSixMontsBills(electricityBillEntity.getAccountId(),electricityBillEntity.getTypeOfService(),electricityBillEntity.getBillDate(),"Units");
					for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator.hasNext();) {
						LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills();
						final Object[] values = (Object[]) iterator.next();
						map.put(new SimpleDateFormat("MMM").format((Date) values[0]), (Double) values[2]);
						sixMonthsBills.setMonth((Date) values[0]);
						sixMonthsBills.setUnits(Double.parseDouble((String) values[1]));
						sixMonthsBills.setAmount((Double) values[2]);
						lastSixMonthsBills.add(sixMonthsBills);
					}
				} else if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Gas")) {
					slabDetailsList = getGasTariffSlabList(gasTariffId,electricityBillEntity.getFromDate(),electricityBillEntity.getBillDate(), uomValue,locale);
					for (ElectricityBillLineItemEntity electricityBillLineItemEntity : electricityBillEntity.getBillLineItemEntities()) {

						if (electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("GA_TAX")) {
							taxAmount = electricityBillLineItemEntity.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("GA_INTEREST")) {
							interestAmount = electricityBillLineItemEntity.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("GA_TAX_INTEREST")) {
							interestOnTax = electricityBillLineItemEntity.getBalanceAmount();
						}
					}
					int rateMasterID = rateMasterService.getRateMasterGas(gasTariffId, "Rate of interest");
					amountForInteresetCal = (float) (electricityBillEntity.getNetAmount() - (interestOnTax + interestAmount + taxAmount));
					if (rateMasterID != 0) {
						latePaymentAmount = interestCalculationGas(rateMasterID, amountForInteresetCal,electricityBillEntity);
					}

					List<?> lastSixMontsBills = electricityBillsService.getLastSixMontsBills(electricityBillEntity.getAccountId(),electricityBillEntity.getTypeOfService(),electricityBillEntity.getBillDate(),"Units");
					for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator.hasNext();) {
						LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills();
						final Object[] values = (Object[]) iterator.next();
						map.put(new SimpleDateFormat("MMM").format((Date) values[0]), (Double) values[2]);
						sixMonthsBills.setMonth((Date) values[0]);
						sixMonthsBills.setUnits(Double.parseDouble((String) values[1]));
						sixMonthsBills.setAmount((Double) values[2]);
						lastSixMonthsBills.add(sixMonthsBills);
					}

				} else if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Solid Waste")) {
					String paraMeterName13 = "Volume(KG)";
					String solidWasteuomValueString = null;
					float solidWasterUnits = 0.0f;
					if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(),paraMeterName13) != null) {
						solidWasteuomValueString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(), electricityBillEntity.getTypeOfService(), paraMeterName13);
						if (solidWasteuomValueString != null)
							solidWasterUnits = Float.parseFloat(solidWasteuomValueString);
					}
					slabDetailsList = getSolidWasteTariffSlabList(solidWasteTariffId,	electricityBillEntity.getFromDate(),electricityBillEntity.getBillDate(),solidWasterUnits, locale);
					for (ElectricityBillLineItemEntity electricityBillLineItemEntity : electricityBillEntity.getBillLineItemEntities()) {
						if (electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("SW_TAX")) {
							taxAmount = electricityBillLineItemEntity.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("SW_INTEREST")) {
							interestAmount = electricityBillLineItemEntity.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("SW_TAX_INTEREST")) {
							interestOnTax = electricityBillLineItemEntity.getBalanceAmount();
						}
					}

					int rateMasterID = rateMasterService.getRateMasterSW(solidWasteTariffId, "Rate of interest");
					amountForInteresetCal = (float) (electricityBillEntity.getNetAmount() - (interestOnTax + interestAmount + taxAmount));
					if (rateMasterID != 0) {
						latePaymentAmount = interestCalculationSW(rateMasterID,amountForInteresetCal, electricityBillEntity);
					}

					List<?> lastSixMontsBills = electricityBillsService.getLastSixMontsBills(electricityBillEntity.getAccountId(),electricityBillEntity.getTypeOfService(),electricityBillEntity.getBillDate(),"Volume(KG)");
					for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator.hasNext();) {
						LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills();
						final Object[] values = (Object[]) iterator.next();
						map.put(new SimpleDateFormat("MMM").format((Date) values[0]), (Double) values[2]);
						sixMonthsBills.setMonth((Date) values[0]);
						sixMonthsBills.setUnits(Double.parseDouble((String) values[1]));
						sixMonthsBills.setAmount((Double) values[2]);
						lastSixMonthsBills.add(sixMonthsBills);
					}
				} else if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Others")) {
					for (ElectricityBillLineItemEntity electricityBillLineItemEntity : electricityBillEntity.getBillLineItemEntities()) {

						if (electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("OT_TAX")) {
							taxAmount = electricityBillLineItemEntity.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("OT_INTEREST")) {
							interestAmount = electricityBillLineItemEntity.getBalanceAmount();
						}
						if (electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("OT_TAX_INTEREST")) {
							interestOnTax = electricityBillLineItemEntity.getBalanceAmount();
						}
					}

					int rateMasterID = rateMasterService.getRateMasterOT(othersTariffId, "Rate of interest");
					amountForInteresetCal = (float) (electricityBillEntity.getNetAmount() - (interestOnTax + interestAmount + taxAmount));
					if (rateMasterID != 0) {
						latePaymentAmount = interestCalculationOT(rateMasterID,amountForInteresetCal, electricityBillEntity);
					}

					List<?> lastSixMontsBills = electricityBillsService.getLastSixMontsBillsOthers(electricityBillEntity.getAccountId(),electricityBillEntity.getTypeOfService(),electricityBillEntity.getBillDate(),"Units");
					for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator.hasNext();) {
						LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills();
						final Object[] values = (Object[]) iterator.next();
						map.put(new SimpleDateFormat("MMM").format((Date) values[0]), (Double) values[2]);
						sixMonthsBills.setMonth((Date) values[0]);
						sixMonthsBills.setUnits(Double.parseDouble((String) values[1]));
						sixMonthsBills.setAmount((Double) values[2]);
						lastSixMonthsBills.add(sixMonthsBills);
					}
				}

				else if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Telephone Broadband")) {
					List<?> lastSixMontsBills = electricityBillsService.getLastSixMontsBillsOthers(electricityBillEntity.getAccountId(),electricityBillEntity.getTypeOfService(),electricityBillEntity.getBillDate(),"Units");
					for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator.hasNext();) {
						LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills();
						final Object[] values = (Object[]) iterator.next();
						map.put(new SimpleDateFormat("MMM").format((Date) values[0]), (Double) values[2]);
						sixMonthsBills.setMonth((Date) values[0]);
						sixMonthsBills.setUnits(Double.parseDouble((String) values[1]));
						sixMonthsBills.setAmount((Double) values[2]);
						lastSixMonthsBills.add(sixMonthsBills);
					}
				}

				HashMap<String, Object> param = new HashMap<String, Object>();
				BillingPaymentsEntity billingPaymentsEntity = paymentService.getPamentDetals(electricityBillEntity.getAccountId(),electricityBillEntity.getFromDate());

				if (billingPaymentsEntity != null) {
					param.put("paymentAmount",billingPaymentsEntity.getPaymentAmount());
					param.put("receiptNo", billingPaymentsEntity.getReceiptNo());
					param.put("receiptDate",billingPaymentsEntity.getReceiptDate());
					param.put("modeOfPay",billingPaymentsEntity.getPaymentMode());
				}

				/*			param.put("title",ResourceBundle.getBundle("utils").getString("report.title"));
				param.put("companyAddress", ResourceBundle.getBundle("utils").getString("report.address"));
			param.put("point1", ResourceBundle.getBundle("utils").getString("report.point1"));
				param.put("point2", ResourceBundle.getBundle("utils").getString("report.point2"));
				param.put("point3", ResourceBundle.getBundle("utils").getString("report.point3"));
				param.put("point4", ResourceBundle.getBundle("utils").getString("report.point4"));
				param.put("point5", ResourceBundle.getBundle("utils").getString("report.point5"));
				param.put("point6", ResourceBundle.getBundle("utils").getString("report.point6"));
				param.put("point7", ResourceBundle.getBundle("utils").getString("report.point7"));*/
				
				param.put( "title",ResourceBundle.getBundle("utils").getString("report.title")); 
				param.put("companyAddress",ResourceBundle.getBundle("utils").getString("report.address"));
		   	    param.put("point1",ResourceBundle.getBundle("utils").getString("report.point1"));
		   	    param.put("point2",ResourceBundle.getBundle("utils").getString("report.point2"));
				param.put("point3",ResourceBundle.getBundle("utils").getString("report.point3"));
				param.put("point4",ResourceBundle.getBundle("utils").getString("report.point4"));
				param.put("point5.1", ResourceBundle.getBundle("utils").getString("report.point5.1"));
				param.put("point5.2",ResourceBundle.getBundle("utils").getString("report.point5.2"));
				param.put("point5.3", ResourceBundle.getBundle("utils").getString("report.point5.3"));
				param.put("point6",ResourceBundle.getBundle("utils") .getString("report.point6"));
				param.put("note",ResourceBundle.getBundle("utils").getString("report.note")); 

				param.put("amountPayble", electricityBillEntity.getNetAmount());
				param.put("dueDate", electricityBillEntity.getBillDueDate());
				String lastName="";
			    String middleName="";
				if (electricityBillEntity.getAccountObj().getPerson().getMiddleName() != null)
				{
					middleName = electricityBillEntity.getAccountObj().getPerson().getMiddleName();
				}
				if (electricityBillEntity.getAccountObj().getPerson().getLastName() != null)
				{
					lastName = electricityBillEntity.getAccountObj().getPerson().getLastName();
				}
				if(co_ownerFirstName!=null && co_ownerFirstName!="")
				{
					if(co_ownerMiddleName!=null)
					{
						param.put("name", electricityBillEntity.getAccountObj().getPerson().getFirstName()+ " "+middleName +" "+lastName+", "+co_ownerFirstName+" "+co_ownerMiddleName+" "+co_ownerLastName);
					}
					else
					{
						param.put("name", electricityBillEntity.getAccountObj().getPerson().getFirstName()+ " "+middleName +" "+lastName+", "+co_ownerFirstName+" "+co_ownerLastName);
					}
					
				}
				else
				{
					param.put("name", electricityBillEntity.getAccountObj().getPerson().getFirstName()+ " "+middleName +" "+lastName);
				}
			    
				param.put("address", address1);
				param.put("city", city);
				param.put("mobileNo", toAddressMobile);
				param.put("emailId", toAddressEmail);
				param.put("sanctionedUtility", Math.round(sanctionLoad) + " kW");
				String sanctionedDG = Math.round(sanctionLoadDG) + " kW";
				if (sanctionedDG.equalsIgnoreCase("0 kW")) {
					param.put("sanctionedDG", "NA");
				} else {
					param.put("sanctionedDG", Math.round(sanctionLoadDG)+ " kW");
				}
				String voltage = Math.round(voltageLevel) + " V";
				if (voltage.equalsIgnoreCase("0 V")) {
					param.put("voltageLevel", "NA");
				} else {
					param.put("voltageLevel", Math.round(voltageLevel) + " V");
				}
			
				JRBeanCollectionDataSource subReportDS = new JRBeanCollectionDataSource(bliList);
				JRBeanCollectionDataSource subReport1DS = new JRBeanCollectionDataSource(arrearsList);
				JRBeanCollectionDataSource subReport2DS = new JRBeanCollectionDataSource(lastSixMonthsBills);
				JRBeanCollectionDataSource subReport3DS = new JRBeanCollectionDataSource(slabDetailsList);
				JRBeanCollectionDataSource subReport4DS = new JRBeanCollectionDataSource(dgSlabDetailsList);
				JRBeanCollectionDataSource subReport5DS = new JRBeanCollectionDataSource(lastSixMonthsBills);

				param.put("subreport_datasource", subReportDS);
				param.put("subreport_datasource1", subReport1DS);
				param.put("subreport_datasource2", subReport2DS);
				param.put("subreport_datasource3", subReport3DS);
				param.put("subreport_datasource4", subReport4DS);
				param.put("subreport_datasource5", subReport5DS);
				param.put("meterType", meterType);
				param.put("connectionType", connectionType);
				param.put("connectionSecurity", connectionSecurity);
				param.put("pF", pfValue);
				param.put("surcharge", latePaymentAmount);
				param.put("billNo", electricityBillEntity.getBillNo());
				param.put("amountAfterDueDate",electricityBillEntity.getNetAmount()+ latePaymentAmount);
				param.put("billDate", electricityBillEntity.getBillDate());
				param.put("billingPeriod",DateFormatUtils.format((electricityBillEntity.getFromDate()),"dd MMM. yyyy")	+ " To "+ DateFormatUtils.format((electricityBillEntity.getBillDate()),"dd MMM. yyyy"));
				param.put("caNo", electricityBillEntity.getAccountObj().getAccountNo());
				param.put("serviceType",electricityBillEntity.getTypeOfService());
				param.put("tariffCategory", tariffName);
				param.put("billBasis", billType);
				param.put("meterMake", meterMake);
				param.put("meterSrNo", meterSrNo);
				param.put("meterStatus", meterStatus);
				param.put("mf", meterConstant);
				param.put("energyType",electricityBillEntity.getTypeOfService() + "/"+ meterSrNo);
				param.put("mdi", mdiValue);
				param.put("presentReading", presentReading);
				param.put("previousReading", previousReading);
				param.put("previousBillDate",electricityBillEntity.getFromDate());
				param.put("presentBillDate",electricityBillEntity.getBillDate());
				param.put("units", uomValue);
				param.put("days", Math.round(numberOfDays));
				param.put("billAmount", electricityBillEntity.getNetAmount());
				param.put("arrearsAmount",electricityBillEntity.getArrearsAmount());
				String amountInWords = NumberToWord.convertNumberToWords(electricityBillEntity.getNetAmount().intValue());
				param.put("amountInWords", amountInWords + " Only");
				param.put("realPath", "reports/");
				param.put("panNo", "AACAG1252D");
				param.put("sTaxNo", "AACAG1252DSD001");
				param.put("propartyNumber", property.getProperty_No());
				param.put("previousBalance", (Double) paymentService.getExcessAmount(electricityBillEntity.getAccountId(),electricityBillEntity.getFromDate()));
				param.put("totalAmount", electricityBillEntity.getNetAmount());
				param.put("dgUnits", dgUnits);
				param.put("dgMeterConstant", dgMeterConstant);
				param.put("dgPresentReading", dgPresentReading);
				param.put("dgPreviousReading", dgPreviousReading);
				param.put("dgEnergyType","DG " + "/"+ meterSrNo);
				param.put("dgmdi", 0.0f);
				param.put("clearedAmount", electricityBillEntity.getAdvanceClearedAmount());
				
				param.put("secondaryAddress", "Grand Arch ,Sector-58");
			/*	JRBeanCollectionDataSource jre = new JRBeanCollectionDataSource(bliList);*/
				JREmptyDataSource jre = new JREmptyDataSource();
				JasperPrint jasperPrint;
				JasperReport report;
				try {
					logger.info("---------- filling the report -----------");
					if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Electricity")) {
						param.put("energyType",electricityBillEntity.getTypeOfService() + "/"+ meterSrNo);
						jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/Bill.jasper"),param, jre);
					} else if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Water")|| electricityBillEntity.getTypeOfService().equalsIgnoreCase("Gas")) {
						param.put("energyType",electricityBillEntity.getTypeOfService() + "/"+ meterSrNo);
						jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/WGBill.jasper"),param, jre);
					} else if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Solid Waste")) {
						param.put("energyType",electricityBillEntity.getTypeOfService());
						jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/SWBill.jasper"),param, jre);
					} else if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Others")) {
						param.put("tariffCategory", othersTariffName);
						param.put("energyType",electricityBillEntity.getTypeOfService());
						jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/OTBill.jasper"),param, jre);
					} else {
						param.put("tariffCategory", "Telephone Broadband");
						param.put("energyType",electricityBillEntity.getTypeOfService());
						jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/OTBill.jasper"),param, jre);
					}
					removeBlankPage(jasperPrint.getPages());
					byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
					InputStream myInputStream = new ByteArrayInputStream(bytes);
					Blob blob = Hibernate.createBlob(myInputStream);
					BillDocument billDocument = new BillDocument(electricityBillEntity, blob);
					billDocumentService.save(billDocument);
				} catch (JRException e) {
					logger.error("----------------- JRException");
				} catch (IOException e) {
					logger.error("----------------- IOException");
				}
			}
		}
		
	}
	/*@RequestMapping(value = "/bill/downloadAllBills/{serviceType}/{presentdate}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public void downloadAllBills(HttpServletResponse res,
			HttpServletRequest req, Locale locale,
			@PathVariable String serviceType, @PathVariable String presentdate)
			throws ParseException, IOException {
		Date monthDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH)
				.parse(presentdate);*/
	
	
	 @RequestMapping(value="bill/printAccontWiseBill/{serviceType}/{accountNo}/{presentdate}/{fromMonth}", method = {
				RequestMethod.POST, RequestMethod.GET })
		    public void printAccontWiseBill(HttpServletRequest request,HttpServletResponse res,@PathVariable String serviceType, @PathVariable String presentdate,@PathVariable String accountNo,@PathVariable String fromMonth) throws ParseException, SQLException, DocumentException, IOException, PrintException{
		        System.out.println("----------- In Test Method ---------------");
		        Date monthDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH).parse(presentdate);
		        Date fromDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH).parse(fromMonth);
	
		        
		        int accNo=Integer.parseInt(accountNo);
		        List<ElectricityBillEntity> billEntities = electricityBillsService.downloadAllBillsOnAccountNo(serviceType, monthDate,fromDate,accNo);
		         List<InputStream> list = new ArrayList<InputStream>();
		       
		        for (ElectricityBillEntity electricityBillEntity : billEntities) {
		        	
		        	logger.info("electricityBillEntity.getElBillId()::::::::::"+electricityBillEntity.getElBillId());
		            Blob blob = electricityBillsService.getBlog(electricityBillEntity.getElBillId());
		            list.add(blob.getBinaryStream());
		            }
		        OutputStream out = res.getOutputStream();
				res.setContentType("application/pdf");
			    out = doMerge(list, out);
			    
			  /********************************   Print Code ************************************************/  
			    DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
		        PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
		        patts.add(Sides.DUPLEX);
		        PrintService[] ps = PrintServiceLookup.lookupPrintServices(flavor, patts);
		        if (ps.length == 0) {
		            throw new IllegalStateException("No Printer found");
		        }
		        System.out.println("Available printers: " + Arrays.asList(ps));

		        PrintService myService = null;
		        for (PrintService printService : ps) {
		            if (printService.getName().equals("Your printer name")) {
		                myService = printService;
		                break;
		            }
		        }

		        if (myService == null) {
		            throw new IllegalStateException("Printer not found");
		        }
		  /* optional 1 */   //FileInputStream fis = new FileInputStream("D:/22303286_acknowledgement.pdf");
		        
		  /* optional  2*/
		        FileInputStream fis = null;
		        IOUtils.copyLarge(fis, out);
		  /* optional  2*/
		        Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
		        DocPrintJob printJob = myService.createPrintJob();
		        printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
		        fis.close(); 
		        /********************************   Print Code ************************************************/ 
			    
				out.flush();
				out.close();
		        System.out.println("Merge success");
		    }
		   

	 /* @RequestMapping(value="bill/printAllBill", method = {RequestMethod.POST, RequestMethod.GET })
	    public void method1(HttpServletRequest request,HttpServletResponse res) throws ParseException, SQLException, DocumentException, IOException, PrintException{
	        System.out.println("----------- In Test Method ---------------");
	       String serviceType=request.getParameter("serviceType");
	       
	       String presentdate=request.getParameter("presentdate");*/
	
	   @RequestMapping(value="bill/printAllBill/{serviceType}/{presentdate}", method = {RequestMethod.POST, RequestMethod.GET })
	    public void method1(HttpServletRequest request,HttpServletResponse res,@PathVariable String serviceType, @PathVariable String presentdate) throws ParseException, SQLException, DocumentException, IOException, PrintException{
	        System.out.println("----------- In Test Method ---------------");
	        Date monthDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH).parse(presentdate);
	        List<ElectricityBillEntity> billEntities = electricityBillsService.downloadAllBills(serviceType, monthDate);
	         List<InputStream> list = new ArrayList<InputStream>();
	       
	        for (ElectricityBillEntity electricityBillEntity : billEntities) {
	            Blob blob = electricityBillsService.getBlog(electricityBillEntity.getElBillId());
	            list.add(blob.getBinaryStream());
	            }
	        OutputStream out = res.getOutputStream();
			res.setContentType("application/pdf");
		    out = doMerge(list, out);
		    
		  /********************************   Print Code ************************************************/  
		    DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
	        PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
	        patts.add(Sides.DUPLEX);
	        PrintService[] ps = PrintServiceLookup.lookupPrintServices(flavor, patts);
	        if (ps.length == 0) {
	            throw new IllegalStateException("No Printer found");
	        }
	        System.out.println("Available printers: " + Arrays.asList(ps));

	        PrintService myService = null;
	        for (PrintService printService : ps) {
	            if (printService.getName().equals("Your printer name")) {
	                myService = printService;
	                break;
	            }
	        }

	        if (myService == null) {
	            throw new IllegalStateException("Printer not found");
	        }
	  /* optional 1 */   //FileInputStream fis = new FileInputStream("D:/22303286_acknowledgement.pdf");
	        
	  /* optional  2*/
	        FileInputStream fis = null;
	        IOUtils.copyLarge(fis, out);
	  /* optional  2*/
	        Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
	        DocPrintJob printJob = myService.createPrintJob();
	        printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
	        fis.close(); 
	        /********************************   Print Code ************************************************/ 
		    
			out.flush();
			out.close();
	        System.out.println("Merge success");
	    }
	    public  OutputStream doMerge(List<InputStream> list, OutputStream outputStream) throws DocumentException, IOException {
	        Document document = new Document();
	        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
	        document.open();
	        PdfContentByte cb = writer.getDirectContent();

	        for (InputStream in : list) {
	            PdfReader reader = new PdfReader(in);
	            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
	                document.newPage();
	                //import the page from source pdf
	                PdfImportedPage page = writer.getImportedPage(reader, i);
	                //add the page to the destination pdf
	                cb.addTemplate(page, 0, 0);
	            }
	        }

	        outputStream.flush();
	        document.close();
	        outputStream.close();
	        return outputStream;
	    } 
	   
		@RequestMapping(value = "/paymentStatmentReport", method = RequestMethod.GET)
		public String paymentReportAccountWise(HttpServletRequest request, Model model) {
 
			logger.info("Payment Statment Report");
			
			return "electricityBills/paymentStamentReort";
		}
		
		@RequestMapping(value="/bill/generateAllLedgerData",method={RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody List<?> readInterstTrackerData(HttpServletRequest request,HttpServletResponse response) throws ParseException
		{
			logger.info("**************** Inside Account Payment Report Read**************");
			int accountId=Integer.parseInt(request.getParameter("accountNo"));
			
			Date month = new SimpleDateFormat("MMM yyyy").parse(request.getParameter("presentdate"));
			Date fromMonth = new SimpleDateFormat("MMM yyyy").parse(request.getParameter("fromMonth"));
			
			logger.info("accountId:::::"+accountId+"month::::::::"+month+"fromMonth"+fromMonth);
			
			java.util.Calendar c = java.util.Calendar.getInstance();
			c.setTime(month);
			c.add(java.util.Calendar.DATE, 1);  // number of days to add
			//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    Date month1=(c.getTime());		

			
			String strDate = new SimpleDateFormat("YYYY-MM-dd").format(fromMonth);
			String pesentDate = new SimpleDateFormat("YYYY-MM-dd").format(month);
		
			
			List<Map<String, Object>> allComData=tariffCalculationService.getLedgerDataOnAccountWise(accountId,strDate,pesentDate); 
			
			return allComData;
		}
		@RequestMapping(value="bill/exportAccontWisePayment/{accountNo}/{presentdate}/{fromMonth}", method = {
				RequestMethod.POST, RequestMethod.GET })
		    public void exportAccontWiseBill(HttpServletRequest request,HttpServletResponse res,@PathVariable String accountNo,@PathVariable String fromMonth, @PathVariable String presentdate) throws ParseException, SQLException, DocumentException, IOException, PrintException{
		        System.out.println("----------- In Test Method ---------------"+presentdate+"::::::"+fromMonth);
		        Date monthDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH).parse(presentdate);
		        Date fromDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH).parse(fromMonth);
			logger.info("monthDate::::"+monthDate+":::::::"+fromDate);
			ServletOutputStream servletOutputStream=null;
			
			
				
				logger.info("*********Income Tracker Report Two**********");
				
				String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
							
							
							
					        logger.info("*****person Size in Vehicle Owner Contact Type***********");
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
					        header.createCell(1).setCellValue("Property No");
					        header.createCell(2).setCellValue("Person Name");
					        header.createCell(3).setCellValue("Account No");
					        header.createCell(4).setCellValue("Service Type"); 
					        header.createCell(5).setCellValue("Post Type"); 
					        header.createCell(6).setCellValue("Month");
					        header.createCell(7).setCellValue("Amount");
					        header.createCell(8).setCellValue("Balance");				        
					        
					        for(int j = 0; j<=8; j++){
					    		header.getCell(j).setCellStyle(style);
					            sheet.setColumnWidth(j, 8000); 
					            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
					        }
				
					        int accountId=Integer.parseInt(accountNo);
							
							//Date month = new SimpleDateFormat("MMM yyyy").parse(request.getParameter("presentdate"));
							//Date fromMonth1 = new SimpleDateFormat("MMM yyyy").parse(request.getParameter("fromMonth"));
							
							//logger.info("accountId:::::"+accountId+"month::::::::"+month+"fromMonth"+fromMonth);
							
									

							
							String strDate = new SimpleDateFormat("YYYY-MM-dd").format(monthDate);
							String pesentDat = new SimpleDateFormat("YYYY-MM-dd").format(fromDate);
						
							logger.info("strDate::::::::"+strDate+"pesentDat:::::"+pesentDat);
							
							List<Map<String, Object>> allComData=tariffCalculationService.getLedgerDataOnAccountWise(accountId,pesentDat,strDate); 
					        
					        
					        
					        
					        
					       // List<Map<String, Object>> result=(List<Map<String, Object>>) paymentService.getAllPaymentDetail();
					        int count = 1;
					        for (Map<String, Object> map : allComData) {
					        	//System.out.println(":::::::::::::::::::000"+map.get(property_No);
					        	System.out.println(map.get("accountNo"));
					        	XSSFRow row = sheet.createRow(count);
								row.createCell(0).setCellValue(String.valueOf(count));
					        	
					        	
									row.createCell(1).setCellValue((String) map.get("property_No"));						
									
									row.createCell(2).setCellValue((String) map.get("personName"));				
									
									row.createCell(3).setCellValue((String) map.get("accountNo"));
										
									if(map.containsKey("ledgerType")){
										row.createCell(4).setCellValue((String) map.get("ledgerType"));
										}
									if(map.containsKey("postType")){
										row.createCell(5).setCellValue((String) map.get("postType"));
										}
									if(map.containsKey("postedBillDate")){
										row.createCell(6).setCellValue((Date) map.get("postedBillDate"));
										}
									
									if(map.containsKey("amount")){
										row.createCell(7).setCellValue((Double) map.get("amount"));
										}else{
											row.createCell(7).setCellValue(0);	
										}
									if(map.containsKey("balance")){
										row.createCell(8).setCellValue((Double) map.get("balance"));
										}else{
											row.createCell(8).setCellValue(0);	
										}
								
							count++;
					        }
					
					        
					    	
					    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
					    	wb.write(fileOut);
					    	fileOut.flush();
					    	fileOut.close();
					        
					       

							servletOutputStream = res.getOutputStream();
							res.setContentType("application/vnd.ms-excel");
							res.setHeader("Content-Disposition", "inline;filename=\"Payment Gateway NEFT Txn.xlsx"+"\"");
							FileInputStream input = new FileInputStream(fileName);
							IOUtils.copy(input, servletOutputStream);
							//servletOutputStream.w
							servletOutputStream.flush();
							servletOutputStream.close();
			

		
					
					        
					    	
	
		
			//paymentService.getAllPaymentDetailforOtherServices();
			
		}
		
		@RequestMapping(value="payment/paymentReport/{accountNo}/{presentdate}/{fromMonth}", method = {	RequestMethod.POST, RequestMethod.GET })
	    public void exportFileElectricityBillPdf(HttpServletRequest request,HttpServletResponse res,@PathVariable String accountNo,@PathVariable String fromMonth, @PathVariable String presentdate) throws ParseException, SQLException, DocumentException, IOException, PrintException{
		
		System.out.println("===========inside pdf===================");

		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";	
		System.out.println("===========inside pdf===================");
		Date monthDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH).parse(presentdate);
        Date fromDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH).parse(fromMonth);
        int accountId=Integer.parseInt(accountNo);
        
        String strDate = new SimpleDateFormat("YYYY-MM-dd").format(monthDate);
		String pesentDat = new SimpleDateFormat("YYYY-MM-dd").format(fromDate);
        		
        		  Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
      
       
        PdfPTable table = new PdfPTable(8);
        Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
        
    	

       // table.addCell(new Paragraph("Sl NO",font1));
        table.addCell(new Paragraph("Property No",font1));
        table.addCell(new Paragraph("Person Name",font1));
        table.addCell(new Paragraph("Account No",font1));
        table.addCell(new Paragraph("Service Type",font1));
        table.addCell(new Paragraph("Post Type",font1));
        table.addCell(new Paragraph("Month",font1));
        table.addCell(new Paragraph("Amount",font1));
        table.addCell(new Paragraph("Balance",font1));
        
        List<Map<String, Object>> allComData=tariffCalculationService.getLedgerDataOnAccountWise(accountId,pesentDat,strDate); 
        
        int count = 1;
        String propertyNo="";
        String accountNum="";
        String personName="";
        for (Map<String, Object> map : allComData) {
        	
        	propertyNo=(String) map.get("property_No");
        	PdfPCell cell1 = new PdfPCell(new Paragraph(propertyNo,font3));
        	
        	personName=(String) map.get("personName");
        	PdfPCell cell2 = new PdfPCell(new Paragraph(personName,font3));
        	
        	accountNum=(String) map.get("accountNo");
        	PdfPCell cell3 = new PdfPCell(new Paragraph(accountNum,font3));
        	
        	String ledgerType=(String) map.get("ledgerType");
        	PdfPCell cell4 = new PdfPCell(new Paragraph(ledgerType,font3));
        	
        	String postType=(String) map.get("postType");
        	PdfPCell cell5 = new PdfPCell(new Paragraph(postType,font3));
        	
        	Date date=(Date) map.get("postedBillDate");
        	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        	String datee=df.format(date);
        	PdfPCell cell6 = new PdfPCell(new Paragraph(datee,font3));
        	
        	Double amount=(Double) map.get("amount");
        	PdfPCell cell7 = new PdfPCell(new Paragraph(Double.toString(amount),font3));
        	
        	Double balance=(Double) map.get("balance");
        	PdfPCell cell8 = new PdfPCell(new Paragraph(Double.toString(balance),font3));
        	
        	 table.addCell(cell1);
		        table.addCell(cell2);
		        table.addCell(cell3);
		        table.addCell(cell4);
		        table.addCell(cell5);
		        table.addCell(cell6);
		        table.addCell(cell7);
		        table.addCell(cell8);
		        
		        table.setWidthPercentage(100);
		        float[] columnWidths = {12f, 15f, 10f, 10f, 13f, 10f, 15f, 10f};

		        table.setWidths(columnWidths);
        	
        	
        
        	count++;
        }
        document.add(new Paragraph("Customer Name : "+personName+"                                                                                                     Account No : "+accountNum,font1));
        document.add(new Paragraph("Report Duration : "+pesentDat+" To "+strDate+"                                                                                       Property No : "+propertyNo,font1));
        document.add(new Paragraph("Name Of Report : Payment Statment Report Account Wise" ,font1));
        
        document.add(new Paragraph("           "));
        document.add(table);
        document.close();

    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	baos.writeTo(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = res.getOutputStream();
		res.setContentType("application/pdf");
		res.setHeader("Content-Disposition", "inline;filename=\"BillGeneratingTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
	
	//return null;
	}

		public Float interestCalculationCam(double amountForInteresetCal,ElectricityBillEntity electricityBillEntity,double camRate) {

			/*
			 * List<InterestSettingsEntity> interestSettingsEntities =
			 * interestSettingService.findAllData(); String interestType = null; for
			 * (InterestSettingsEntity interestSettingsEntity :
			 * interestSettingsEntities) { interestType =
			 * interestSettingsEntity.getInterestBasedOn();
			 * logger.info(" Interest Calculation base on -->" + interestType); }
			 */
	         Float totalAmount=0f;
			String configName = "Interest Calculation";
			String status = "Active";
			String interestType = electricityBillsService.getBillingConfigValue(configName, status);
			logger.info("interestType ==================== " + interestType);
			Map<Object, Object> consolidatedBill = new HashMap<>();
			Float interestAmountAfterDueDate = 0.0f;
			if (interestType != null) {
				if (interestType.trim().equalsIgnoreCase("Daywise")) {
					logger.info("--------------------- Interest calculation based on ----"+ interestType);

					ELRateMaster elRateMaster = rateMasterService.find(1);
					Calendar cal = Calendar.getInstance();
					cal.setTime(electricityBillEntity.getBillDueDate());
					cal.add(Calendar.DATE, 15);
					Date nextBillDate = cal.getTime();
					logger.info("nextBillDate ------------------ " + nextBillDate);
					/*consolidatedBill = calculationController.interstAmount(
							elRateMaster, electricityBillEntity.getBillDueDate(),
							nextBillDate, amountForInteresetCal);*/
					for (Entry<Object, Object> map1 : consolidatedBill.entrySet()) {
						if (map1.getKey().equals("total")) {
							interestAmountAfterDueDate = interestAmountAfterDueDate	+ (Float) map1.getValue();
						}
					}
				} else {
					logger.info("--------------------- Interest calculation based on ----"+ interestType);

					Calendar cal = Calendar.getInstance();
					cal.setTime(electricityBillEntity.getBillDueDate());
					logger.info("::::::::::"+electricityBillEntity.getBillDueDate());
					cal.add(Calendar.MONTH, 1);//cal.add(Calendar.DATE, 30);
					Date nextBillDate = cal.getTime();
					logger.info("nextBillDate ------------------ " + nextBillDate);

					//logger.info("billEntity.getArrearsAmount() "+ billEntity.getArrearsAmount());
					LocalDate fromDate = new LocalDate(electricityBillEntity.getBillDueDate());
					LocalDate toDate = new LocalDate(nextBillDate);
				    Days d = Days.daysBetween(fromDate,toDate);
				    int days = d.getDays();
				    int year = toDate.getYear();
				    LocalDate ld = new LocalDate(year,1,1);
				    int daysInYear = Days.daysBetween(ld,ld.plusYears(1)).getDays();
				    float rateForInterest = (float)12/daysInYear;
				    logger.info("----------- Number of interest days are ------------ "+days);
				    
				    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
					Period difference = new Period(fromDate, toDate, monthDay);
					float billableMonths = difference.getMonths();
					int daysAfterMonth = difference.getDays();
					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(electricityBillEntity.getBillDueDate());
					float daysToMonths = (float) daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
					float netMonth = daysToMonths + billableMonths;
		
					totalAmount =  (float) (totalAmount +((camRate/100) *amountForInteresetCal*netMonth));
					

				
			}
			}
			return totalAmount;
		}
		
		
		public double latepaymentAmountCam(double amountForInteresetCal,ElectricityBillEntity electricityBillEntity,double camRate){
			
			float totalAmount=0;
			
			if (amountForInteresetCal>0 ) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(electricityBillEntity.getBillDueDate());
				logger.info("::::::::::"
						+ electricityBillEntity.getBillDueDate());
				cal.add(Calendar.MONTH, 1);//cal.add(Calendar.DATE, 30);
				Date nextBillDate = cal.getTime();
				logger.info("nextBillDate ------------------ " + nextBillDate);
				//logger.info("billEntity.getArrearsAmount() "+ billEntity.getArrearsAmount());
				LocalDate fromDate = new LocalDate(
						electricityBillEntity.getBillDueDate());
				LocalDate toDate = new LocalDate(nextBillDate);
				Days d = Days.daysBetween(fromDate, toDate);
				int days = d.getDays();
				int year = toDate.getYear();
				LocalDate ld = new LocalDate(year, 1, 1);
				int daysInYear = Days.daysBetween(ld, ld.plusYears(1))
						.getDays();
				float rateForInterest = (float) 12 / daysInYear;
				logger.info("----------- Number of interest days are ------------ "
						+ days);
				PeriodType monthDay = PeriodType.yearMonthDay()
						.withYearsRemoved();
				Period difference = new Period(fromDate, toDate, monthDay);
				float billableMonths = difference.getMonths();
				int daysAfterMonth = difference.getDays();
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(electricityBillEntity.getBillDueDate());
				float daysToMonths = (float) daysAfterMonth
						/ cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
				float netMonth = daysToMonths + billableMonths;
				totalAmount = (float) (totalAmount + ((camRate / 100)
						* amountForInteresetCal * netMonth));
			}
			return totalAmount;
		}
		
		
		@SuppressWarnings("unused")
		@RequestMapping("/users/electricitybill")
		public String bills(HttpServletRequest request,Model model){
		/*
			double eleAmt = 0.0;
			double waterAmt = 0.0;
			double gasAmt = 0.0;
			double swAmt =0.0;
			double broadBandAmt = 0.0;
			double othersAmt = 0.0;
			double camAmt = 0.0;
			Date dueDate =null;
			Date periodFrom = null;
			Date periodTo = null;
			double payments = 0.0;
			double pastDues = 0.0;
			double adjustments = 0.0;
			String elTariffName ="Electricity Tariff";
			String gsTariffName ="Gas Tariff";
			String wtTariffName ="Water Tariff";
			String swTariffName ="Solid Waste Tariff";
			String teleTariffName ="Telecom Tariff";
			String otTariffName ="Other Tariff";

			HttpSession session = request.getSession(); 
			String username= (String) session.getAttribute("userName");
			Person person = generalNotificationService.getPersonBasedOnMailId(username);
			 
			

			Address address=null;
			String name=null;
			String phone=null;
			String email=null;
			Contact contactNew;
			Person person2=null;
			int propertyId = (Integer)session.getAttribute("propertyId");
			List<Account> account=accountService.getAccountBasedonPerson(person.getPersonId(),propertyId);
			
			
			System.out.println("acount iddddd    "+account.get(0).getAccountId());
			model.addAttribute("accountId", account.get(0).getAccountId());
			List<ElectricityBillEntity> elect=electricityBillsService.executeSimpleQuery("select o from ElectricityBillEntity o where o.accountId="+account.get(0).getAccountId()+" and o.elBillId in(select max(o.elBillId) from ElectricityBillEntity o WHERE o.typeOfService='Electricity' and o.cbId is not null and (o.status='Posted' or o.status='Paid') and o.accountId="+account.get(0).getAccountId()+")");
			List<ElectricityBillEntity> water=electricityBillsService.executeSimpleQuery("select o from ElectricityBillEntity o where o.accountId="+account.get(0).getAccountId()+" and o.elBillId in(select max(o.elBillId) from ElectricityBillEntity o WHERE o.typeOfService='Water' and o.cbId is not null and (o.status='Posted' or o.status='Paid') and o.accountId="+account.get(0).getAccountId()+")");
			List<ElectricityBillEntity> cam=electricityBillsService.executeSimpleQuery("select o from ElectricityBillEntity o where o.accountId="+account.get(0).getAccountId()+" and o.elBillId in(select max(o.elBillId) from ElectricityBillEntity o WHERE o.typeOfService='CAM' and o.cbId is not null and (o.status='Posted' or o.status='Paid') and o.accountId="+account.get(0).getAccountId()+")");
			List<ElectricityBillEntity> other=electricityBillsService.executeSimpleQuery("select o from ElectricityBillEntity o where o.accountId="+account.get(0).getAccountId()+" and o.elBillId in(select max(o.elBillId) from ElectricityBillEntity o WHERE o.typeOfService='Others' and o.cbId is not null and (o.status='Posted' or o.status='Paid') and o.accountId="+account.get(0).getAccountId()+")");
			List<ElectricityBillEntity> broadb=electricityBillsService.executeSimpleQuery("select o from ElectricityBillEntity o where o.accountId="+account.get(0).getAccountId()+" and o.elBillId in(select max(o.elBillId) from ElectricityBillEntity o WHERE o.typeOfService='Telephone Broadband' and o.cbId is not null and (o.status='Posted' or o.status='Paid') and o.accountId="+account.get(0).getAccountId()+")");
			List<ElectricityBillEntity> solid=electricityBillsService.executeSimpleQuery("select o from ElectricityBillEntity o where o.accountId="+account.get(0).getAccountId()+" and o.elBillId in(select max(o.elBillId) from ElectricityBillEntity o WHERE o.typeOfService='Solid Waste' and o.cbId is not null and (o.status='Posted' or o.status='Paid') and o.accountId="+account.get(0).getAccountId()+")");
			List<ElectricityBillEntity> gas=electricityBillsService.executeSimpleQuery("select o from ElectricityBillEntity o where o.accountId="+account.get(0).getAccountId()+" and o.elBillId in(select max(o.elBillId) from ElectricityBillEntity o WHERE o.typeOfService='Gas' and o.cbId is not null and (o.status='Posted' or o.status='Paid') and o.accountId="+account.get(0).getAccountId()+")");
		
			System.out.println("Size             "+elect.size());
			List<ElectricityBillEntity> billList=new ArrayList<ElectricityBillEntity>();
			if(elect.size()>0)
			{
				System.out.println("amount             "+elect.get(0));
				billList.add(elect.get(0));
			}
			if(water.size()>0)
			{
				billList.add(water.get(0));
			}
			if(cam.size()>0)
			{
				billList.add(cam.get(0));
			}
			if(other.size()>0)
			{
				billList.add(other.get(0));
			}
			if(broadb.size()>0)
			{
				billList.add(broadb.get(0));
			}
			if(solid.size()>0)
			{
				billList.add(solid.get(0));
			}
			if(gas.size()>0)
			{
				billList.add(gas.get(0));
			}
			
			
			if(!account.isEmpty()&&account.size()>0&&account!=null)
			{
				
				Account account2 = accountService.getSingleResult("select o from Account o WHERE  o.accountId="+account.get(0).getAccountId());
				
				String nameQuery="select p from Person p where p.personId="+account2.getPersonId();
	 			person2=personService.getSingleResult(nameQuery);
	               
	 			name=person2.getFirstName()+" "+person2.getLastName();
	 			model.addAttribute("ownerName", name);
	 			
				
				
				String addquery="SELECT a from Address a WHERE a.personId="+account2.getPersonId()+" and a.addressPrimary='Yes'";
				
				address=addressService.getSingleResult(addquery);
				
				model.addAttribute("address", address.getAddress1());
				
				String emailQuery="select c from Contact c where c.personId="+account2.getPersonId()+" and c.contactPrimary='Yes' and c.contactType='Email'";
				contactNew=contactService.getSingleResult(emailQuery);
				email=contactNew.getContactContent();
				model.addAttribute("email", email);
				
				String mobileQuery="select c from Contact c where c.personId="+account2.getPersonId()+" and c.contactPrimary='Yes' and c.contactType='Mobile'";
				contactNew=contactService.getSingleResult(mobileQuery);
				phone=contactNew.getContactContent();
				model.addAttribute("phone", phone);
			}
			
			
			
			 
			if(!account.isEmpty()&&account !=null&&account.size()>0){
//				List<ElectricityBillEntity> billList = electricityBillsService.executeSimpleQuery("select o from ElectricityBillEntity o where o.accountId="+account.get(0).getAccountId()+" and  o.billMonth BETWEEN ADD_MONTHS(SYSDATE, -1) AND ADD_MONTHS(SYSDATE, 0) and o.cbId is not null and (o.status='Posted' or o.status='Paid') and o.postType='Bill'");

				
				if(billList.size()>0){
					for (ElectricityBillEntity electricityBillEntity : billList) {
						if(electricityBillEntity.getTypeOfService().equalsIgnoreCase("Electricity")){
							eleAmt+=electricityBillEntity.getBillAmount();
						} else if(electricityBillEntity.getTypeOfService().equalsIgnoreCase("Water")){
							waterAmt+=electricityBillEntity.getBillAmount();
						}else if(electricityBillEntity.getTypeOfService().equalsIgnoreCase("Gas")){
							gasAmt+=electricityBillEntity.getBillAmount();
						}else if(electricityBillEntity.getTypeOfService().equalsIgnoreCase("Solid Waste")){
							swAmt+=electricityBillEntity.getBillAmount();
						}else if(electricityBillEntity.getTypeOfService().equalsIgnoreCase("CAM")){
							camAmt+=electricityBillEntity.getBillAmount();
						}else if(electricityBillEntity.getTypeOfService().equalsIgnoreCase("Telephone Broadband")){
							broadBandAmt+=electricityBillEntity.getBillAmount();
						
						}else if(electricityBillEntity.getTypeOfService().equalsIgnoreCase("Others")){
							othersAmt+=electricityBillEntity.getBillAmount();
						}
						dueDate = electricityBillEntity.getBillDueDate();
						periodFrom = electricityBillEntity.getFromDate();
						periodTo =electricityBillEntity.getBillDate();
					}

					List<ElectricityLedgerEntity> eledgerPDList = electricityLedgerService.executeSimpleQuery("select o from ElectricityLedgerEntity o where o.accountId='"+account.get(0).getAccountId()+"' and o.postType='ARREARS' and o.ledgerDate<=TO_DATE('"+periodFrom+"', 'YYYY-MM-DD') ORDER BY o.elLedgerid DESC");
					if(eledgerPDList.size()>0)
						pastDues = eledgerPDList.get(0).getBalance();

					List<ElectricityLedgerEntity> eledgerPaymentList = electricityLedgerService.executeSimpleQuery("select o from ElectricityLedgerEntity o where o.accountId='"+account.get(0).getAccountId()+"' and o.postType='PAYMENT' and o.ledgerDate<=TO_DATE('"+periodTo+"', 'YYYY-MM-DD') ORDER BY o.elLedgerid DESC");
					if(eledgerPaymentList.size()>0)
						payments = eledgerPaymentList.get(0).getAmount();
					System.out.println("payments ======================="+eledgerPaymentList.get(0).getAmount());

					List<ElectricityLedgerEntity> eledgerAdjList = electricityLedgerService.executeSimpleQuery("select o from ElectricityLedgerEntity o where o.accountId='"+account.get(0).getAccountId()+"' and o.postType='ADJUSTMENT' and o.ledgerDate<TO_DATE('"+periodFrom+"', 'YYYY-MM-DD') ORDER BY o.elLedgerid DESC");
					if(eledgerAdjList.size()>0)
						adjustments = eledgerAdjList.get(0).getBalance();
					if(periodFrom!=null)
						model.addAttribute("periodFrom",new SimpleDateFormat("dd, MMMM, YYYY").format(periodFrom));
					model.addAttribute("account",billList.get(0).getAccountObj().getAccountNo());
					if(periodTo!=null)
						model.addAttribute("periodTo",new SimpleDateFormat("dd, MMMM, YYYY").format(periodTo));
					if(dueDate!=null)
						model.addAttribute("dueDate",new SimpleDateFormat("dd, MMMM, YYYY").format(dueDate));

					List<ServiceMastersEntity> serviceMastersList = serviceMasterService.executeSimpleQuery("select obj from ServiceMastersEntity obj where obj.accountObj.accountId="+billList.get(0).getAccountId());

					if(!serviceMastersList.isEmpty() && serviceMastersList.size()>0){ 
						for (ServiceMastersEntity serviceMastersEntity : serviceMastersList) {
							if(serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Electricity")){
								ELTariffMaster eltariffMaster = elTariffMasterService.getSingleResult("select o from ELTariffMaster o where o.elTariffID="+serviceMastersEntity.getElTariffID());
								if(eltariffMaster!=null)
									elTariffName = eltariffMaster.getTariffName(); 
							}else if(serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Gas")){
								ELTariffMaster eltariffMaster = elTariffMasterService.getSingleResult("select o from ELTariffMaster o where o.elTariffID="+serviceMastersEntity.getGaTariffID());
								if(eltariffMaster!=null)
									gsTariffName = eltariffMaster.getTariffName();
							}else if(serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Water")){
								WTTariffMaster wttariffMaster = wtTariffMasterService.getSingleResult("select o from WTRateMaster o where o.wtTariffID="+serviceMastersEntity.getWtTariffID());
								if(wttariffMaster!=null)
									wtTariffName = wttariffMaster.getTariffName();
							}else if(serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Solid Waste")){
								ELTariffMaster eltariffMaster = elTariffMasterService.getSingleResult("select o from ELTariffMaster o where o.elTariffID="+serviceMastersEntity.getSwTariffID());
								if(eltariffMaster!=null)
									swTariffName = eltariffMaster.getTariffName();
							}
						}
					}
				}
			}
			if(payments<0.0)
				model.addAttribute("payments",-(payments));
			else
				model.addAttribute("payments",payments);
			model.addAttribute("pastDues",pastDues);

			double currAmt = eleAmt+waterAmt+gasAmt+camAmt+othersAmt+swAmt+broadBandAmt;
			double totalAmt = 0.0;

			if(pastDues == 0.0){
				totalAmt = currAmt+pastDues;
			}else{
				totalAmt = currAmt+pastDues+payments+adjustments;
			}
			System.out.println("electricity amount++++++++++++++++"+eleAmt);
			model.addAttribute("eleAmt",eleAmt);
			model.addAttribute("waterAmt",waterAmt);
			model.addAttribute("gasAmt",gasAmt);
			model.addAttribute("swAmt",swAmt);
			model.addAttribute("camAmt",camAmt);
			model.addAttribute("broadBandAmt",broadBandAmt);
			model.addAttribute("othersAmt",othersAmt);
			model.addAttribute("totalAmt",totalAmt);
			model.addAttribute("currAmt",currAmt);
			model.addAttribute("adjustments",adjustments);
			model.addAttribute("elTariff",elTariffName);
			model.addAttribute("gsTariff",gsTariffName);
			model.addAttribute("swTariff",swTariffName);
			model.addAttribute("wtTariff",wtTariffName);
			model.addAttribute("viewName","My Bills");
			model.addAttribute("subtitle","My Bills");
*/
			return "backend/billing/electricity";
		}
		
		
}
