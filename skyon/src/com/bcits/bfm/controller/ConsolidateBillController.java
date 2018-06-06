package com.bcits.bfm.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.Address;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.ELTariffMaster;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.ElectricityBillLineItemEntity;
import com.bcits.bfm.model.ElectricityBillParametersEntity;
import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.model.ElectricityMeterParametersEntity;
import com.bcits.bfm.model.GasTariffMaster;
import com.bcits.bfm.model.MailMaster;
import com.bcits.bfm.model.Notification;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.model.ServiceParametersEntity;
import com.bcits.bfm.model.SolidWasteTariffMaster;
import com.bcits.bfm.model.WTTariffMaster;
import com.bcits.bfm.service.MailConfigService;
import com.bcits.bfm.service.NotificationService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.accountsManagement.ElectricityLedgerService;
import com.bcits.bfm.service.billingWizard.BillingTemplateService;
import com.bcits.bfm.service.commonAreaMaintenance.CamConsolidationService;
import com.bcits.bfm.service.commonAreaMaintenance.CamLedgerService;
import com.bcits.bfm.service.customerOccupancyManagement.AddressService;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.customerOccupancyManagement.TenantSevice;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillLineItemService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillParameterService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.electricityMetersManagement.ElectricityMeterParametersService;
import com.bcits.bfm.service.electricityMetersManagement.ElectricityMetersService;
import com.bcits.bfm.service.facilityManagement.BillingParameterMasterService;
import com.bcits.bfm.service.facilityManagement.ServiceParameterMasterService;
import com.bcits.bfm.service.gasTariffManagment.GasTariffMasterService;
import com.bcits.bfm.service.helpDeskManagement.OpenNewTicketService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceParameterService;
import com.bcits.bfm.service.solidWasteTariffManagment.SolidWasteTariffMasterService;
import com.bcits.bfm.service.tariffManagement.ELTariffMasterService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.service.waterTariffManagement.WTTariffMasterService;
import com.bcits.bfm.util.EmailCredentialsDetailsBean;
import com.bcits.bfm.util.SendBillInfoThroughSMS;
import com.bcits.bfm.util.SendMailConsolidationBills;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.util.SmsCredentialsDetailsBean;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.itextpdf.text.DocumentException;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.commons.lang.time.DateFormatUtils;
import java.util.ResourceBundle;
import org.apache.poi.ss.util.CellRangeAddress;
import javax.servlet.ServletOutputStream;
import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;


import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;

@Controller
public class ConsolidateBillController{

	private static final Log logger = LogFactory.getLog(ConsolidateBillController.class);

	@Autowired
	private ElectricityBillsService electricityBillsService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ContactService contactService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private BillController billController;

	@Autowired
	private ServiceParameterMasterService serviceParameterMasterService;

	@Autowired
	private ServiceParameterService serviceParameterService;

	@Autowired
	private BillingParameterMasterService  billParameterMasterService;

	@Autowired
	private ElectricityBillParameterService electricityBillParameterService;

	@Autowired
	private ElectricityMeterParametersService electricityMeterParametersService;

	@Autowired
	private ElectricityMetersService electricityMetersService;

	@Autowired
	private ElectricityBillLineItemService billLineItemService;

	@Autowired
	private ElectricityLedgerService electricityLedgerService;

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
	private BillingTemplateService billingTemplateService;
	
	@Autowired
	private CamLedgerService camLedgerService;

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private PropertyService propertyService;

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private CamConsolidationService camConsolidationService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private OpenNewTicketService openNewTicketService;
	
	@Autowired
	private MailConfigService mailConfigService;
	
	@Autowired
	private TenantSevice tenantService;

	/*@RequestMapping(value = "/consolidatedbill", method = RequestMethod.GET)
	public String index(ModelMap model,HttpServletRequest request) {

		return "bill/consolidatedbill";
	}*/

	@RequestMapping(value = "/consolidatedbill")
	public String index(ModelMap model,HttpServletRequest request) 
	{
		model.addAttribute("ViewName", "Bills Generation");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Bills Generation", 1, request);
		breadCrumbService.addNode("Manage View Bills ", 2, request);
		return "bill/consolidatedbill";
	}


	@RequestMapping(value = "/consolidate/consolidateBills",method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody
	List<?> consolidateBills(@RequestParam("month") String month) throws ParseException {
		String[] str = month.split("/");
		if(str.length>=2){
			String billQuery = "select o from ElectricityBillEntity o where o.status IN ('Posted','Paid') AND EXTRACT(month FROM o.billMonth)='"+str[0]+"' and  EXTRACT(year FROM o.billMonth)='"+str[1]+"'";
			List<ElectricityBillEntity> billList = electricityBillsService.executeSimpleQuery(billQuery);

			for (ElectricityBillEntity electricityBillEntity : billList) {
				String cbId = electricityBillEntity.getAccountId()+""+str[0]+""+str[1]; 
				electricityBillsService.executeDeleteQuery("update ElectricityBillEntity e set e.cbId='"+cbId+"' where e.elBillId="+electricityBillEntity.getElBillId());
			}
		}
		return null;
	}


	@SuppressWarnings("serial")
	@RequestMapping(value = "/consolidatedbill/read",method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody
	List<?> getElectricityDetails() {

		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
		for (final Object[] record : electricityBillsService.distinctCbId()) 
		{	
			response.add(new HashMap<String, Object>() {{
				put("cbId", (String)record[0]);
				put("accountId", (Integer)record[1]);
				put("accountNo", (String)record[2]);
				put("billMonth", new SimpleDateFormat("MMMM, yyyy").format((Date)record[3]));
				put("billMonthSql", (Date)record[3]);
				put("billAmount", (Double)record[7]);
				put("serviceType",(String)record[4]);
				put("mailSent_Status",(String)record[8]);
				//put("billAmount", electricityBillsService.getConsolidatedBill((String)record[0],(Date)record[3]));	
				String personName = "";
				personName = personName.concat((String)record[5]);
				if((String)record[6] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)record[6]);
				}
				put("personName", personName);
			}
			});
		}
		return response;
	}
	
	@RequestMapping(value = "/conslidated/getMaster", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody
	String masterBill(HttpServletRequest req){

		int accountId = Integer.parseInt(req.getParameter("accountId"));
		String billMonth = req.getParameter("billMonthSql");
		Account account = accountService.find(accountId);		
		List<ElectricityBillEntity> eleBillList = electricityBillsService.executeSimpleQuery("select obj from ElectricityBillEntity obj where obj.accountId="+accountId+" and obj.typeOfService = 'Electricity' and obj.billMonth=TO_DATE('"+billMonth+"','YYYY-MM-DD')");
		List<ElectricityBillEntity> waterBillList = electricityBillsService.executeSimpleQuery("select obj from ElectricityBillEntity obj where obj.accountId="+accountId+" and obj.typeOfService = 'Water' and obj.billMonth=TO_DATE('"+billMonth+"','YYYY-MM-DD')");
		List<ElectricityBillEntity> gasBillList = electricityBillsService.executeSimpleQuery("select obj from ElectricityBillEntity obj where obj.accountId="+accountId+" and obj.typeOfService = 'Gas' and obj.billMonth=TO_DATE('"+billMonth+"','YYYY-MM-DD')");
		List<ElectricityBillEntity> swBillList = electricityBillsService.executeSimpleQuery("select obj from ElectricityBillEntity obj where obj.accountId="+accountId+" and obj.typeOfService = 'Solid Waste' and obj.billMonth=TO_DATE('"+billMonth+"','YYYY-MM-DD')");
		List<ElectricityBillEntity> teleBillList = electricityBillsService.executeSimpleQuery("select obj from ElectricityBillEntity obj where obj.accountId="+accountId+" and obj.typeOfService = 'Telephone Broadband' and obj.billMonth=TO_DATE('"+billMonth+"','YYYY-MM-DD')");
		List<ElectricityBillEntity> othersBillList = electricityBillsService.executeSimpleQuery("select obj from ElectricityBillEntity obj where obj.accountId="+accountId+" and obj.typeOfService = 'Others' and obj.billMonth=TO_DATE('"+billMonth+"','YYYY-MM-DD')");
		List<ElectricityBillEntity> camBillList = electricityBillsService.executeSimpleQuery("select obj from ElectricityBillEntity obj where obj.accountId="+accountId+" and obj.typeOfService = 'CAM' and obj.billMonth=TO_DATE('"+billMonth+"','YYYY-MM-DD')");
		java.util.Date dueDate = new java.util.Date();

		Date billFromDate = null;
		Date billToDate = null;
		String billFromDateStr = " ";
		String billToDateStr = "  ";
		String elTariffName ="Electricity Tariff";
		String gsTariffName ="Gas Tariff";
		String wtTariffName ="Water Tariff";
		String swTariffName ="Solid Waste Tariff";
		String teleTariffName ="Telecom Tariff";
		String otTariffName ="Other Tariff";


		//List<ElectricityBillEntity> getfromAndToDates = electricityBillsService.executeSimpleQuery("select e from ElectricityBillEntity e where e.accountId="+accountId+" and ROWNUM<=2 order by e.elBillId desc");

		/*if(!getfromAndToDates.isEmpty()){
			if(!getfromAndToDates.isEmpty() && getfromAndToDates.size()>=2){

				billFromDate = getfromAndToDates.get(0).getBillDate();
				if(billFromDate!=null)
					billFromDateStr = new SimpleDateFormat("dd, MMMM, YYYY").format(billFromDate);
				billToDate = getfromAndToDates.get(1).getBillDate();
				if(billToDate!=null)
					billToDateStr = new SimpleDateFormat("dd, MMMM, YYYY").format(billToDate);
			}else{
				billFromDate = getfromAndToDates.get(0).getBillDate();
				if(billFromDate!=null)
					billFromDateStr = new SimpleDateFormat("dd, MMMM, YYYY").format(billFromDate);
			}
		}*/
		double eleAmt = 0.0;
		if(eleBillList.size()>0){
			billToDate = eleBillList.get(0).getBillDate();
			billFromDate = eleBillList.get(0).getFromDate();
			dueDate = eleBillList.get(0).getBillDueDate();
			for(ElectricityBillEntity billEntity : eleBillList)
				eleAmt += billEntity.getBillAmount();
		}

		double waterAmt = 0.0;
		if(waterBillList.size()>0){
			billToDate = waterBillList.get(0).getBillDate();
			billFromDate = waterBillList.get(0).getFromDate();
			dueDate = waterBillList.get(0).getBillDueDate();
			for(ElectricityBillEntity billEntity : waterBillList)
				waterAmt += billEntity.getBillAmount();
		}

		double gasAmt = 0.0;
		if(gasBillList.size()>0){
			billToDate = gasBillList.get(0).getBillDate();
			billFromDate = gasBillList.get(0).getFromDate();
			dueDate = gasBillList.get(0).getBillDueDate();
			for(ElectricityBillEntity billEntity : gasBillList)
				gasAmt += billEntity.getBillAmount();
		}	

		double swAmt = 0.0;
		if(swBillList.size()>0){
			billToDate = swBillList.get(0).getBillDate();
			billFromDate = swBillList.get(0).getFromDate();
			dueDate = swBillList.get(0).getBillDueDate();
			for(ElectricityBillEntity billEntity : swBillList)
				swAmt += billEntity.getBillAmount();
		}

		double teleAmt = 0.0;
		if(teleBillList.size()>0){
			billToDate = teleBillList.get(0).getBillDate();
			billFromDate = teleBillList.get(0).getFromDate();
			dueDate = teleBillList.get(0).getBillDueDate();
			for(ElectricityBillEntity billEntity : teleBillList)
				teleAmt += billEntity.getBillAmount();
		}

		double otherAmt = 0.0;
		if(othersBillList.size()>0){
			billToDate = othersBillList.get(0).getBillDate();
			billFromDate = othersBillList.get(0).getFromDate();
			dueDate = othersBillList.get(0).getBillDueDate();
			for(ElectricityBillEntity billEntity : othersBillList)
				otherAmt += billEntity.getBillAmount();
		}

		double camAmt = 0.0;
		if(camBillList.size()>0){
			billToDate = camBillList.get(0).getBillDate();
			billFromDate = camBillList.get(0).getFromDate();
			dueDate = camBillList.get(0).getBillDueDate();
			for(ElectricityBillEntity billEntity : camBillList)
				camAmt += billEntity.getBillAmount();
		}

		//ElectricityBillEntity electricityBillEntity = electricityBillsService.find(elBillId);
		/*String addrQuery = "select obj from Address obj where obj.personId="+account.getPerson().getPersonId()
				+" and obj.addressPrimary='Yes'";
		Address address = addressService.getSingleResult(addrQuery);

		String mobileQuery = "select obj from Contact obj where obj.personId="+account.getPerson().getPersonId()
				+" and obj.contactPrimary='Yes' and obj.contactType='Mobile'";
		Contact contactMob = contactService.getSingleResult(mobileQuery);
		String emailQuery = "select obj from Contact obj where obj.personId="+account.getPerson().getPersonId()
				+" and obj.contactPrimary='Yes' and obj.contactType='Email'";
		Contact contactEmail = contactService.getSingleResult(emailQuery);*/
		
		Object[] addressDetailsList = camConsolidationService.getAddressDetailsForMail(account.getPerson().getPersonId());
		List<String> contactDetailsList = camConsolidationService.getContactDetailsForMail(account.getPerson().getPersonId());
	
		String address1 = (String)addressDetailsList[0];
		//String city = (String)addressDetailsList[1];
		
		String toAddressEmail = "";
		String toAddressMobile = "";
		
		for (Iterator<?> iterator = contactDetailsList.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			if(((String)values[0]).equals("Email")){
				toAddressEmail = (String)values[1];
			}else{
				toAddressMobile = (String)values[1];
			}				
		}


		double arrearsAmount = 0.0;
		double paymentAmount = 0.0;
		List<ElectricityLedgerEntity> arrearsLedger = electricityLedgerService.executeSimpleQuery("select obj from ElectricityLedgerEntity obj where obj.accountId="+accountId+" and obj.postType='ARREARS' and obj.ledgerDate=TO_DATE('"+billMonth+"','YYYY-MM-DD')");
		if(arrearsLedger!=null && arrearsLedger.size()>0){
			for(int i=0;i<arrearsLedger.size();i++){
				arrearsAmount+=arrearsLedger.get(i).getAmount();
			}
		}
		
		List<ElectricityLedgerEntity> ledger = electricityLedgerService.executeSimpleQuery("select obj from ElectricityLedgerEntity obj where obj.accountId="+accountId+" and obj.postType='PAYMENT' and obj.ledgerDate=TO_DATE('"+billMonth+"','YYYY-MM-DD')");
		if(ledger!=null && ledger.size()>0){
			for(int i=0;i<ledger.size();i++){
				paymentAmount+=ledger.get(i).getAmount();
			}
		}
		double adjustment =0.0; 
		List<ElectricityLedgerEntity> adjustments = electricityLedgerService.executeSimpleQuery("select obj from ElectricityLedgerEntity obj where obj.accountId="+accountId+" and obj.postType='ADJUSTMENT' and obj.ledgerDate=TO_DATE('"+billMonth+"','YYYY-MM-DD')");
		if(adjustments!=null && adjustments.size()>0){
			for(int i=0;i<adjustments.size();i++){
				adjustment+=adjustments.get(i).getAmount();

			}
		}

		List<ServiceMastersEntity> serviceMastersList = serviceMasterService.executeSimpleQuery("select obj from ServiceMastersEntity obj where obj.accountObj.accountId="+accountId);

		if(!serviceMastersList.isEmpty() && serviceMastersList.size()>0){ 
			for (ServiceMastersEntity serviceMastersEntity : serviceMastersList) {
				if(serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Electricity")){
					ELTariffMaster eltariffMaster = elTariffMasterService.getSingleResult("select o from ELTariffMaster o where o.elTariffID="+serviceMastersEntity.getElTariffID());
					if(eltariffMaster!=null)
						elTariffName = eltariffMaster.getTariffName(); 
				}else if(serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Gas")){
					GasTariffMaster gasTariffMaster = gasTariffMasterService.getSingleResult("select o from GasTariffMaster o where o.gasTariffId="+serviceMastersEntity.getGaTariffID());
					if(gasTariffMaster!=null)
						gsTariffName = gasTariffMaster.getGastariffName();
				}else if(serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Water")){
					List<WTTariffMaster> wttariffMaster = wtTariffMasterService.executeSimpleQuery("select o from WTTariffMaster o where o.wtTariffId="+serviceMastersEntity.getWtTariffID());
					if(wttariffMaster!=null && wttariffMaster.size()>0){
						wtTariffName = wttariffMaster.get(0).getTariffName();
					}
				}else if(serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Solid Waste")){
					SolidWasteTariffMaster solidWasteTariffMaster = solidWasteTariffMasterService.getSingleResult("select o from SolidWasteTariffMaster o where o.solidWasteTariffId="+serviceMastersEntity.getSwTariffID());
					if(solidWasteTariffMaster!=null)
						swTariffName = solidWasteTariffMaster.getSolidWasteTariffName();
				}
			}
		}

		String dueDateStr = "";
		if(dueDate!=null){
			dueDateStr = new SimpleDateFormat("dd, MMMM, YYYY").format(dueDate);
		}
		
		if(billFromDate!=null){
			billFromDateStr = new SimpleDateFormat("dd, MMMM, YYYY").format(billFromDate);
		}
		
		if(billToDate!=null){
			billToDateStr = new SimpleDateFormat("dd, MMMM, YYYY").format(billToDate);
		}
		String configName = "CAM Charges";
		String status = "Active";
		String camSetting = electricityBillsService.getBillingConfigValue(configName,status);
		logger.info("camSetting ==================== "+camSetting);
		String calculationBasis = camSetting;

		String str= ""
				+"<div id='myTab'>"
				+"<table id='tabs' style='width: 100%; background: white; border: 2px solid black; border-radius: 34px; padding: 21px 23px;'>"
				+"<tr>"																							    
				+"	<td style='padding: 0.5em; border: 1px solid #808080;' width='50%' colspan='2'><img id='eye' src='/bfm_acq/resources/images/build.jpg' height='100px' width='300px' /></td>"
				+"	<td style='padding: 0.5em; border: 1px solid #808080;vertical-align:middle' width='49%'>SKYON&nbsp;CONDOMINIUM&nbsp;OWNERS&nbsp;WELFARE&nbsp;ASSOCIATION,<br>Sector 58,&nbsp;Gram-Behrampur,<br>Gurgaon&nbsp;-&nbsp;122002</td>"
				+"</tr>"
				+"<tr>"
				+"	<td style='padding: 0.5em; border: 1px solid #808080;background: black; color: white; font-weight: bolder;' colspan='3'>Customer Details</td>"
				+"</tr>"
				+"<tr>"
				+"	<td style='padding: 0.5em; border: 1px solid #808080;padding-left: 25px' width='49%' ><b> <h4 id='name'>"+account.getPerson().getFirstName()+" "+account.getPerson().getLastName()+"</h4> </b> "
				+ "		<span id='addr'>"+address1+"</span> <br>"
				+"		<span id='email'>"+toAddressMobile+"</span><br>"
				+"		<span id='mobile'>"+toAddressEmail+"</span><br></td>"
				+"	<td style='padding: 0.5em; border: 1px solid #808080; vertical-align: middle; border-left: 2px solid' colspan='2'>"
				+"		<table style='width: 100%;'>"
				+"			<tr>"
				+"				<td style='padding: 0.5em; border: 1px solid #808080;' align='center'><b>Account Number</b></td>"
				+"				<td style='padding: 0.5em; border: 1px solid #808080;' id='accno'>"+account.getAccountNo()+"</td>"
				+"			</tr>"
				+"			<tr>"
				+"				<td style='padding: 0.5em; border: 1px solid #808080;' align='center'><b>Period From</b></td>"
				+"				<td style='padding: 0.5em; border: 1px solid #808080;' >"+billToDateStr+"</td>"
				+"			</tr>"
				+"			<tr>"
				+"				<td style='padding: 0.5em; border: 1px solid #808080;' align='center'><b>Period To</b></td>"
				+"				<td style='padding: 0.5em; border: 1px solid #808080;' >"+billFromDateStr+"</td>"
				+"			</tr>"
				+"			<tr>"
				+"				<td style='padding: 0.5em; border: 1px solid #808080;' align='center'><b>Due Date</b></td>"
				+"				<td style='padding: 0.5em; border: 1px solid #808080;' >"+dueDateStr+"</td>"
				+"			</tr>"
				+"		</table>"
				+"	</td>"
				+"</tr>"
				+"<tr style='background-color: black'>"
				+"	<td style='padding: 0.5em; border: 1px solid #808080;backgound: black; color: white; font-weight: bolder;' colspan='3'>Charges</td>"
				+"</tr>"
				+"<tr>"
				+"	<td style='padding: 0.5em; border: 1px solid #808080;' colspan='3'>"
				+"		<table style='width: 100%; text-align: center;'>"
				+"			<tr>"
				+"				<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>Past Dues</th>"
				+"				<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>Current Charges</th>"
				+"				<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>Payments</th>"
				+"				<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>Adjustment</th>"
				+"				<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>Amount Due</th>"
				+"			</tr>"
				+"			<tr>"
				+"				<td style='padding: 0.5em; border: 1px solid #808080;'>"+arrearsAmount+"</td>"
				+"				<td style='padding: 0.5em; border: 1px solid #808080;'>"+(eleAmt+waterAmt+gasAmt+swAmt+otherAmt+teleAmt+camAmt)+"</td>"
				+"				<td style='padding: 0.5em; border: 1px solid #808080;'>"+(-(paymentAmount))+"</td>"
				+"				<td style='padding: 0.5em; border: 1px solid #808080;'>"+adjustment+"</td>"
				+"				<td style='padding: 0.5em; border: 1px solid #808080;'>"+((arrearsAmount)+(eleAmt+waterAmt+gasAmt+swAmt+otherAmt+teleAmt+camAmt)+(paymentAmount)-(adjustment))+"</td>"
				+"			</tr>"
				+"		</table>"
				+"	</td>"
				+"</tr>"
				+"<tr style='background-color: black'>"
				+"	<td style='padding: 0.5em; border: 1px solid #808080;backgound: black; color: white; font-weight: bolder;' colspan=3>Bill Details</td>"
				+"</tr>"
				+"<tr>"
				+"	<td colspan='3'>"
				+"		<table style='width: 100%; text-align: center;'>"
				+"			<tr>"
				+"				<td width='30%' style='background: #7cb5ec'>"
				+"					<table style='width: 100%;'>"
				+"						<tr>"
				+"							<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;' colspan='2'><a href='#' onclick='detailedBill(this.text)'>Electricity</a></th>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' width='50%'>Tariff</td>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' width='50%' id='eletariff'>"+elTariffName+"</td>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;'>Amount</td>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' id='eleamt'>"+eleAmt+"</td>"
				+"						</tr>"
				+"					</table>"
				+"				</td>"
				+"				<td width='40%' rowspan='2' id='tdcontainer' style='vertical-align: middle;'>"
				+"					<div id='syed' style='min-width: 300px; height: 400px; max-width: 300px; margin: 0 auto; vertical-align: middle;'>"
				+"					</div> "
				+"				</td>"
				+"				<td width='30%' style='background: #7798BF'>"
				+"					<table style='width: 100%;'>"
				+"						<tr>"
				+"							<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;' colspan='2'><a href='#' onclick='detailedBill(this.text)'>Gas</a></th>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' width='50%'>Tariff</td>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' width='50%' id='gastariff'>"+gsTariffName+"</td>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' width='50%'>Amount</td>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' width='50%' id='gasamt'>"+gasAmt+"</td>"
				+"						</tr>"
				+"					</table>"
				+"				</td>"
				+"			</tr>"
				+"			<tr>"
				+"				<td width='30%' style='background: #f7a35c; color: white;'>"
				+"					<table style='width: 100%;'>"
				+"						<tr>"
				+"							<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;' colspan='2'><a href='#' onclick='detailedBill(this.text)'>Water</a></th>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' width='50%'>Tariff</td>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' width='50%' id='watertariff'>"+wtTariffName+"</td>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;'>Amount</td>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' id='wateramt'>"+waterAmt+"</td>"
				+"						</tr>"
				+"					</table>"
				+"				</td>"

				+"				<td width='30%' style='background: #90ed7d'>"
				+"					<table style='width: 100%;'>"
				+"						<tr>"
				+"							<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;' colspan='2'><a href='#' onclick='detailedBill(this.text)'>Solid Waste</a></th>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' width='50%' >Tariff</td>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' width='50%' id='swtariff'>"+swTariffName+"</td>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;'>Amount</td>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' id='swamt'>"+swAmt+"</td>"
				+"						</tr>"
				+"					</table>"
				+"				</td>"
				+"			</tr>"
				+"			<tr>"
				+"				<td width='30%' style='background: #aaeeee'>"
				+"					<table style='width: 100%;'>"
				+"						<tr>"
				+"							<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;' colspan='2'>Telecom</th>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' width='50%'>Tariff</td>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' width='50%' id='internettariff'>"+teleTariffName+"</td>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;'>Amount</td>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' id='internetamt'>"+teleAmt+"</td>"
				+"						</tr>"

				+"					</table>"
				+"				</td>"

				+"				<td width='30%' style='background: #f15c80'>"
				+"					<table style='width: 100%;'>"
				+"						<tr>"
				+"							<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;' colspan='2'>Others</th>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' width='50%'>Tariff</td>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' width='50%' id='commontariff'>"+otTariffName+"</td>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;'>Amount</td>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' id='commonamt'>"+otherAmt+"</td>"
				+"						</tr>"
				+"					</table>"
				+"				</td>"

				+"<td width='30%' style='background: #FFFF33'>" 
				+"					<table style='width: 100%;'>"
				+"						<tr>"
				+"							<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;' colspan='2'>CAM</th>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' width='50%'>Bill Basis</td>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' width='50%' id='camtariff'>"+calculationBasis+"</td>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;'>Amount</td>"
				+"							<td style='padding: 0.5em; border: 1px solid #808080;' id='camamt'>"+camAmt+"</td>"
				+"						</tr>"

				+"					</table>"
				+"				</td>"

				+"			</tr>"



				+"		</table>"
				+"	</td>"
				+"</tr>"
				+"</table>"
				+"</div>";

		return str;

	}

	@RequestMapping(value = "/conslidated/getChildCount", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody
	List<?> childCountBill(@RequestParam("accountId") int accountId,@RequestParam("billMonthSql") String billMonth,@RequestParam("cbId") String cbId){

		List<ElectricityBillEntity> elist =electricityBillsService.executeSimpleQuery("select obj from ElectricityBillEntity obj where obj.accountId ="+accountId+" and obj.billMonth=TO_DATE('"+billMonth+"','YYYY-MM-DD') and obj.cbId="+cbId);		
		List<ElectricityBillEntity> list = new ArrayList<ElectricityBillEntity>();

		for(ElectricityBillEntity electricityBillEntity : elist){
			ElectricityBillEntity entity= new ElectricityBillEntity();
			entity.setTypeOfService(electricityBillEntity.getTypeOfService());
			entity.setBillAmount(electricityBillEntity.getBillAmount());
			entity.setBillDate(electricityBillEntity.getBillDate());
			entity.setBillDueDate(electricityBillEntity.getBillDueDate());
			entity.setBillMonth(electricityBillEntity.getBillMonth());
			entity.setAccountId(electricityBillEntity.getAccountId());
			entity.setElBillId(electricityBillEntity.getElBillId());
			list.add(entity);
		}
		return list;
	}

	@RequestMapping(value = "/conslidated/getAllChild", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody
	String getAllChildTables(@RequestParam("accountId") int accountId,@RequestParam("billMonthSql") String billMonth,@RequestParam("cbId") int cbId){

		List<ElectricityBillEntity> elist =electricityBillsService.executeSimpleQuery("select obj from ElectricityBillEntity obj where obj.accountId ="+accountId+" and obj.billMonth=TO_DATE('"+billMonth+"','YYYY-MM-DD') and obj.cbId="+cbId);		

		String allChilds = "";

		for (int i = 0; i < elist.size(); i++) {
			//allChilds+=billController.detailedBillPopup(elist.get(i).getElBillId());
			int elBillId = elist.get(i).getElBillId();

			ElectricityBillEntity electricityBillEntity = electricityBillsService.find(elBillId);
			String addrQuery = "select obj from Address obj where obj.personId="+electricityBillEntity.getAccountObj().getPerson().getPersonId()
					+" and obj.addressPrimary='Yes'";
			Address address = addressService.getSingleResult(addrQuery);

			String mobileQuery = "select obj from Contact obj where obj.personId="+electricityBillEntity.getAccountObj().getPerson().getPersonId()
					+" and obj.contactPrimary='Yes' and obj.contactType='Mobile'";
			Contact contactMob = contactService.getSingleResult(mobileQuery);
			String emailQuery = "select obj from Contact obj where obj.personId="+electricityBillEntity.getAccountObj().getPerson().getPersonId()
					+" and obj.contactPrimary='Yes' and obj.contactType='Email'";
			Contact contactEmail = contactService.getSingleResult(emailQuery);


			String spQuery = "select sp from ServiceParametersEntity sp where sp.serviceMastersEntity.accountObj.accountId="+electricityBillEntity.getAccountObj().getAccountId()+" and sp.serviceMastersEntity.typeOfService='"+electricityBillEntity.getTypeOfService()+"' order by sp.serviceParameterSequence";

			String mpQuery = "select mp from ElectricityMeterParametersEntity mp where mp.electricityMetersEntity.account.accountId="+electricityBillEntity.getAccountObj().getAccountId()+" and mp.electricityMetersEntity.typeOfService='"+electricityBillEntity.getTypeOfService()+"' order by mp.elMeterParameterSequence";

			String bpQuery = "select bp from ElectricityBillParametersEntity bp where bp.electricityBillEntity.accountObj.accountId="+electricityBillEntity.getAccountObj().getAccountId()+" and bp.electricityBillEntity.typeOfService='"+electricityBillEntity.getTypeOfService()+"' and bp.electricityBillEntity.elBillId="+elBillId;

			String lineItems ="select bli from ElectricityBillLineItemEntity bli where bli.electricityBillEntity.elBillId="+elBillId;



			List<ServiceParametersEntity> spList = serviceParameterService.executeSimpleQuery(spQuery);
			String spStr= "";
			if(spList.size()>0){
				for(ServiceParametersEntity entity : spList){
					if(entity.getServiceParameterMaster()!=null)
						spStr+="<tr><td style='padding: 0.5em; border: 1px solid #808080;'>"+entity.getServiceParameterMaster().getSpmName()+"</td><td style='padding: 0.5em; border: 1px solid #808080;'>"+entity.getServiceParameterValue()+"</td></tr>";
				}
			}


			List<ElectricityMeterParametersEntity> mpList = electricityMeterParametersService.executeSimpleQuery(mpQuery);
			String mpStr = "";
			if(mpList.size()>0){
				for(ElectricityMeterParametersEntity entity : mpList){
					if(entity.getParameterMasterObj()!=null)
						mpStr+="<tr><td style='padding: 0.5em; border: 1px solid #808080;'>"+entity.getParameterMasterObj().getMpmName()+"</td><td style='padding: 0.5em; border: 1px solid #808080;'>"+entity.getElMeterParameterValue()+"</td></tr>";
				}
			}


			String slabs = "";
			List<ElectricityBillParametersEntity> bpList = electricityBillParameterService.executeSimpleQuery(bpQuery);
			String bpStr = "";
			if(bpList.size()>0){
				for(ElectricityBillParametersEntity entity : bpList){

					if(entity.getNotes()!=null)
						slabs=entity.getNotes();

					if(entity.getBillParameterMasterEntity()!=null)
						bpStr+="<tr><td style='padding: 0.5em; border: 1px solid #808080;'>"+entity.getBillParameterMasterEntity().getBvmName()+"</td><td style='padding: 0.5em; border: 1px solid #808080;'>"+entity.getElBillParameterValue()+"</td></tr>";
				}
			}

			String bliStr = "";
			List<ElectricityBillLineItemEntity> bliList = billLineItemService.executeSimpleQuery(lineItems);
			if(bliList.size()>0){
				for (ElectricityBillLineItemEntity electricityBillLineItemEntity : bliList) {
					bliStr+="<tr><td style='padding: 0.5em; border: 1px solid #808080;'>"+electricityBillLineItemEntity.getTransactionCode()+"</td><td style='padding: 0.5em; border: 1px solid #808080;' align='right'>"+electricityBillLineItemEntity.getBalanceAmount()+"</td></tr>";
				}
			}

			allChilds+= ""
					+"<div id='myTab'>"
					+"<table id='tabs"+i+"' style='width: 100%; background: white; border: 2px solid black; border-radius: 34px; padding: 21px 23px;'>"
					+"<tr>"
					+"	<td style='padding: 0.5em; border: 1px solid #808080;' width='50%' colspan='2'><img id='eye' src='http://www.ireoprojects.co.in/gallery/ireo-grandarch.jpg' height='100px' width='300px' /></td>"
					+"	<td style='padding: 0.5em; border: 1px solid #808080;vertical-align:middle' width='49%'>Orchid Centre, DLF Golf Course Rd,<br> IILM Institute, Sector 53, <br>Gurgaon, Haryana<br> 0124 475 4000 </td>"
					+"</tr>"
					+"<tr>"
					+"	<td style='padding: 0.5em; border: 1px solid #808080;background: black; color: white; font-weight: bolder;' colspan='3'>Customer Details</td>"
					+"</tr>"
					+"<tr>"
					+"	<td style='padding: 0.5em; border: 1px solid #808080;padding-left: 25px' width='49%' ><b> <h4 id='name'>"+electricityBillEntity.getAccountObj().getPerson().getFirstName()+" "+ electricityBillEntity.getAccountObj().getPerson().getLastName()+"</h4> </b> "
					+ "		<span id='addr'>"+address.getAddress1() +"</span> <br>"
					+"		<span id='email'>"+contactMob.getContactContent()+"</span><br>"
					+"		<span id='mobile'>"+contactEmail.getContactContent()+"</span><br></td>"
					+"	<td style='padding: 0.5em; border: 1px solid #808080; vertical-align: middle; border-left: 2px solid' colspan='2'>"
					+"		<table style='width: 100%;'>"
					+"			<tr>"
					+"				<td style='padding: 0.5em; border: 1px solid #808080;' align='center'><b>Account Number</b></td>"
					+"				<td style='padding: 0.5em; border: 1px solid #808080;' id='accno'>"+electricityBillEntity.getAccountObj().getAccountNo()+"</td>"
					+"			</tr>"
					+"			<tr>"
					+"				<td style='padding: 0.5em; border: 1px solid #808080;' align='center'><b>Service Type</b></td>"
					+"				<td style='padding: 0.5em; border: 1px solid #808080;' >"+electricityBillEntity.getTypeOfService()+"</td>"
					+"			</tr>"
					+"		</table>"
					+"	</td>"
					+"</tr>"
					+"<tr style='background-color: black'>"
					+"	<td style='padding: 0.5em; border: 1px solid #808080;backgound: black; color: white; font-weight: bolder;' colspan='3'>Parameters</td>"
					+"</tr>"
					+"<tr>"
					+"	<td style='padding: 0.5em; border: 1px solid #808080;' colspan='3'>"
					+"		<table style='width: 100%; text-align: center;'>"
					+"			<tr>"
					+"				<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>Service Parameters</th>"
					+"				<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>Meter Parameters</th>"
					+"				<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>Bill Parameters</th>"
					+"			</tr>"
					+"			<tr>"
					+"				<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>"
					+"					<table style='width: 100%;'>"
					+"						<tr>"
					+"							<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>Name</th>"
					+"							<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>Value</th>"
					+"						</tr>"
					+						spStr
					+"					</table>"
					+"				</th>"
					+"				<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>"
					+"					<table style='width: 100%;'>"
					+"						<tr>"
					+"							<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>Name</th>"
					+"							<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>Value</th>"
					+"						</tr>"
					+ 						mpStr
					+"					</table>"
					+"				</th>"
					+"				<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>"
					+"					<table style='width: 100%;'>"
					+"						<tr>"
					+"							<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>Name</th>"
					+"							<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>Value</th>"
					+"						</tr>"
					+						bpStr
					+"					</table>"
					+"				</th>"
					+"			</tr>"
					+"		</table>"
					+"	</td>"
					+"</tr>"
					+"<tr style='background-color: black'>"
					+"	<td style='padding: 0.5em; border: 1px solid #808080;backgound: black; color: white; font-weight: bolder;' colspan=3>Bill Segments</td>"
					+"</tr>"
					+"<tr>"
					+"	<td style='padding: 0.5em; border: 1px solid #808080;' colspan=3>"
					+"		<table style='width: 100%;'>"
					+"			<tr>"
					+"				<td style='padding: 0.5em; border: 1px solid #808080;' width='40%'>"
					+"					<div style='height: 200px; max-height: 200px; width: 350px; max-width: 350px'>Slabs <br><br> "+slabs+"</div>"
					+"				</td>"
					+"				<td rowspan=2 style='padding: 0.5em; border: 1px solid #808080;' width='60%'>"
					+"					<table style='width: 100%;'>"
					+"						<tr>"
					+"							<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;' align='center'>Line Segments</th>"
					+"							<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;' align='center'>Amount</th>"
					+"						</tr>"
					+						bliStr
					+"						<tr>"
					+"							<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;'>TOTAL</th>"
					+"							<th style='font-weight: bold;padding: 0.5em; border: 1px solid #808080;' align='right'>"+electricityBillEntity.getBillAmount()+"</th>"
					+"						</tr>"
					+"					</table>"
					+"				</td>"
					+"			</tr>"
					+"			<tr>"
					+"				<td style='padding: 0.5em; border: 1px solid #808080;' width='40%'>"
					+"					<div id='container' style='height: 200px; max-height: 200px; width: 350px; max-width: 350px'>"
					+ "					<img style='min-height:200px;max-height:200px;width:350px;max-width:350px' src='./resources/graph.png'/></div>"
					+"				</td>"
					+"			</tr>"
					+"		</table>"
					+"	</td>"
					+"</tr>"
					+"<tr>"
					+"	<td style='padding: 0.5em; border: 1px solid #808080; height: 100px' colspan='3'>"
					+"		<b>Message</b>"
					+"	</td>"
					+"</tr>"
					+"</table>"
					+"</div>";




		}

		return allChilds+"---"+elist.size(); 

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/consolidate/sendConsolidatedBill", method = {RequestMethod.POST,RequestMethod.GET})
	public 
	String sendConsolidatedBill(HttpServletRequest request, HttpServletResponse response,Locale locale) throws IOException, MessagingException, DocumentException{

		String action = request.getParameter("action");
		String billMonth = request.getParameter("billMonthSql");
		
		if(StringUtils.equalsIgnoreCase(action, "single")){

			String accountId=request.getParameter("accountId");	
			System.out.println("*******************accountId="+accountId+"********************");
			Account account = accountService.find(Integer.parseInt(accountId));
			String cbId="";
			//billingTemplateService.consolidateSingleBillSendMail(account,billMonth,locale);
			//sendBill(billMonth,cbId,locale,accountId);
		}else{
			String cbIds=request.getParameter("cbIds");
			System.out.println("cbIds:::::::::::"+cbIds);
			String accountIds = request.getParameter("accountIds");
			String acIds[] = accountIds.split(",");
			logger.info("::::::::acIds.length:::::::::"+acIds.length);
			for (int i = 0; i < acIds.length; i++) {
                     logger.info(":::::::::::::::::"+acIds[i]);
				sendBill(billMonth,locale,acIds[i]);
				//Account account = accountService.find(Integer.parseInt(acIds[i]));
				//billingTemplateService.consolidateSingleBillSendMail(account,billMonth,locale);
				//String cBillIds[]=cbIds.split(",");
				/*for (int j = 0; j < cBillIds.length; j++) {
					System.out.println(":::::::::cbid"+cBillIds[j]+"::::::::::acids"+acIds[i]);
					
				}*/
		    }
	   }
		return null;
	}

	@Async
	public void mailCode(String toAddressEmail,String toAddressMobile,String serviceType,int propertyId,Blob pdfByteArray,String personName) throws MessagingException{
		
		SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
		EmailCredentialsDetailsBean emailCredentialsDetailsBean = null;
		try {
			emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object propertyData[] = openNewTicketService.getPropertyDataBasedOnPropertyId(propertyId);
		String fileName ="";
		if(propertyData[0]!=null){
			fileName = serviceType+ "_" +(String)propertyData[0]+".pdf";
		}
		
		
		String messagecontent =null;
		MailMaster mailMaster=mailConfigService.getMailMasterByService(serviceType);
		String mailMessage=mailMaster.getMailMessage();
		emailCredentialsDetailsBean.setMailSubject(mailMaster.getMailSubject());
		
		if(serviceType.equalsIgnoreCase("Electricity")){
			messagecontent=	
						"<html>"
						+"<body>"		
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
						+ "Dear "+ personName+","
						+ "<br/><br/>"
						/*+ "&nbsp;&nbsp;&nbsp;lease find enclosed here with latest electricity bill for your apartment. "*/
						+ "&nbsp;&nbsp;&nbsp;"+mailMessage
						+ "<br/><br/>"
						+ "<br/>Warm Regards,<br/>"
						+ "Resident Services <br/> <br/>"
						+"</body></html>";
			
			}else if(serviceType.equalsIgnoreCase("CAM")){
				System.err.println("CAM MAIL SERVICE *****************");
				messagecontent=	
						"<html>"
						+"<body>"		
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
						+ "Dear "+ personName+","
						+ "<br/><br/>          "
						/*+ "&nbsp;&nbsp;&nbsp;Please find enclosed here with latest CAM (COMMON AREA MAINTENANCE) bill for your apartment. "*/
						+ "&nbsp;&nbsp;&nbsp;"+mailMessage
						+ "<br/><br/>"
						+ "<br/>Warm Regards,<br/>"
						+ "Resident Services <br/> <br/>"
						+"</body></html>";
			}else{
				messagecontent=	
						"<html>"
						+"<body>"		
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
						+ "Dear "+ personName+","
						+ "<br/><br/>          "
						/*+ "&nbsp;&nbsp;&nbsp;Please find enclosed here with latest " +serviceType+ " bill for your apartment. "*/
						+ "&nbsp;&nbsp;&nbsp;"+mailMessage
						+ "<br/><br/>"
						+ "<br/>Warm Regards,<br/>"
						+ "Resident Services <br/> <br/>"
						+"</body></html>";	
			}	
		
		emailCredentialsDetailsBean.setMessageContent(messagecontent);		
		emailCredentialsDetailsBean.setToAddressEmail(toAddressEmail);
		new Thread(new SendMailConsolidationBills(emailCredentialsDetailsBean,pdfByteArray,fileName)).start();
		
		String messagePerson = "Skyon RWA  bill desk.Your bill's calculated and send to your registered email,check once.If you have any queries,contact to our bill desk service";
		//String messagePerson = "Please find enclosed herewith latest electricity bill for your apartment. We apologise for the delay in generation of your electricity bill as we have recently upgraded our billing software for enhanced functionality and for smoother operations of our portal. Thank you for your cooperation ";
		smsCredentialsDetailsBean.setNumber(toAddressMobile);
		smsCredentialsDetailsBean.setUserName(personName);
		smsCredentialsDetailsBean.setMessage(messagePerson);
		new Thread(new SendBillInfoThroughSMS(smsCredentialsDetailsBean)).start();
	}

	@RequestMapping(value = "/consolidate/sendAllConsolidatedBill", method = {RequestMethod.POST,RequestMethod.GET})
	public String sendAllConsolidatedBill(@RequestParam("action") String action, @RequestParam("billMonthSql") String billMonth,@RequestParam("cbId") String cbId,@RequestParam("serviceType") String serviceType,HttpServletRequest request, HttpServletResponse response,Locale locale) throws IOException, MessagingException, DocumentException{
		String accountId = request.getParameter("accountId");
		String query = "SELECT o.elBillId,p.personId,a.propertyId,o.typeOfService from ElectricityBillEntity o INNER JOIN o.accountObj a INNER JOIN a.person p WHERE o.accountId="+accountId+" and o.billMonth=TO_DATE('"+billMonth+"','YYYY-MM-DD') and o.cbId='"+cbId+"' AND o.typeOfService='"+serviceType+"'";
		List<?> electricityBillList = electricityBillsService.executeSimpleQuery(query);
		for (Iterator<?> billIterator = electricityBillList.iterator(); billIterator.hasNext();) {
			
			final Object[] billDataValues = (Object[]) billIterator.next();
			System.out.println("elbill id +++++++"+(Integer)billDataValues[0]);
			Blob blob = electricityBillsService.getBlog((Integer)billDataValues[0]);
			if(blob != null){
				
				int propertyId = (Integer)billDataValues[2];
				List<String> contactDetailsList = camConsolidationService.getContactDetailsForMail((Integer)billDataValues[1]);
				String toAddressEmail = "";
				String toAddressMobile = "";
				
				for (Iterator<?> iterator = contactDetailsList.iterator(); iterator.hasNext();) {
					final Object[] values = (Object[]) iterator.next();
					if(((String)values[0]).equals("Email")){
						toAddressEmail = (String)values[1];
					}else{
						toAddressMobile = (String)values[1];
					}				
				}
				List<String> tenantContactDetailsList=null;
				String tenantAddressEmail = "";
				String tenantAddressMobile = "";
				String tenantName="";
				String tenantStatus= tenantService.getTenantStatusByPropertyId(propertyId);
				if(tenantStatus!= null && tenantStatus.equalsIgnoreCase("Active"))
				{
					tenantContactDetailsList= tenantService.getContactDetailsByPersonId(propertyId);
					tenantName=tenantService.getTenantFirstNameByPropertyId(propertyId);
				for (Iterator<?> iterator = tenantContactDetailsList.iterator(); iterator.hasNext();) {
					final Object[] values = (Object[]) iterator.next();
					if(((String)values[1]).equals("Email")){
						tenantAddressEmail = (String)values[2];
					}else{
						tenantAddressMobile = (String)values[2];
					}				
					}
				
				
				}
					
				Object[] personNameData = openNewTicketService.getPersonNameBasedOnPersonId((Integer)billDataValues[1]);
				String personName = "";
				 personName = personName.concat((String)personNameData[0]);
				 if(personNameData[1]!= null){
				 	personName = personName.concat(" ");
					personName = personName.concat((String)personNameData[1]);
				 }
								 				
				try {
					String status ="";
					mailCode(toAddressEmail,toAddressMobile,(String)billDataValues[3],propertyId,blob,personName);
					status="Yes";
					if(tenantStatus!= null && tenantStatus.equalsIgnoreCase("Active"))
					{
						mailCode(tenantAddressEmail,tenantAddressMobile,(String)billDataValues[3],propertyId,blob,tenantName);
						status =status +", T";
					}
											
					electricityBillsService.updateMailSent_Status((Integer)billDataValues[0],(String)billDataValues[3],status);
				} catch (MessagingException e) {
					e.printStackTrace();
				}

				Notification notification = new Notification();
/*
				String userName = (String) SessionData.getUserDetails().get("userID");
				int userId = usersService.getUserInstanceByLoginName(userName).getUrId();
				int toUsersId = openNewTicketService.getUserIdBasedOnPersonId((Integer)billDataValues[1]);
			    if(toUsersId!=0){
			        notification.setUser_id(toUsersId+"");
					notification.setFromUser(userId+"");
					notification.setSubject("Your bills are generated");
					notification.setMessage("This notification is to inform you that your bill generated");
					notification.setRead_status(0);
					notification.setMsg_status("INBOX");
					notificationService.save(notification);
			      }*/
			}else{
				logger.info("::::::::::::: else block");
			} 
		}
		return null;
	}


	@RequestMapping(value = "/consolidatedbill/getPersonListForFileter", method = RequestMethod.GET)
	public @ResponseBody List<?> readPersonNamesInLedger() {
		logger.info("In person data filter method");
		List<?> accountPersonList = electricityBillsService.findPersonForFilters();
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


	@RequestMapping(value = "/bill/storeImage", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody void storeImage(HttpServletRequest request,Locale locale) throws FileNotFoundException, IOException{
		String strFile = request.getParameter("bin_data");
		strFile = strFile.replace(' ', '+');
		System.out.println("***"+strFile);
		byte[] decoded = Base64.decodeBase64(strFile);
		//byte[] decoded = DatatypeConverter.parseBase64Binary(strFile);
		try (OutputStream stream = new FileOutputStream("D:/Bills/graphs/abc.png")) {
			stream.write(decoded);
		}
	}
	
	
	
	public String sendBill(  String billMonth, Locale locale,String accountId) throws IOException, MessagingException, DocumentException{
		//String accountId = request.getParameter("accountId");
		String query = "select o.elBillId,p.personId,a.propertyId,o.typeOfService from ElectricityBillEntity o INNER JOIN o.accountObj a INNER JOIN a.person p WHERE o.accountId="+accountId+" and o.billMonth=TO_DATE('"+billMonth+"','YYYY-MM-DD') and o.status IN('Posted','Paid') ";
		List<?> electricityBillList = electricityBillsService.executeSimpleQuery(query);
		for (Iterator<?> billIterator = electricityBillList.iterator(); billIterator.hasNext();) {
			
			final Object[] billDataValues = (Object[]) billIterator.next();			
			Blob blob = electricityBillsService.getBlog((Integer)billDataValues[0]);
			if(blob != null){
				
				int propertyId = (Integer)billDataValues[2];
				List<String> contactDetailsList = camConsolidationService.getContactDetailsForMail((Integer)billDataValues[1]);
				String toAddressEmail = "";
				String toAddressMobile = "";
				
				for (Iterator<?> iterator = contactDetailsList.iterator(); iterator.hasNext();) {
					final Object[] values = (Object[]) iterator.next();
					if(((String)values[0]).equals("Email")){
						toAddressEmail = (String)values[1];
					}else{
						toAddressMobile = (String)values[1];
					}				
				}
					
				Object[] personNameData = openNewTicketService.getPersonNameBasedOnPersonId((Integer)billDataValues[1]);
				String personName = "";
				 personName = personName.concat((String)personNameData[0]);
				 if(personNameData[1]!= null){
				 	personName = personName.concat(" ");
					personName = personName.concat((String)personNameData[1]);
				 }
								 				
				try {
					mailCode(toAddressEmail,toAddressMobile,(String)billDataValues[3],propertyId,blob,personName);
				} catch (MessagingException e) {
					e.printStackTrace();
				}

				Notification notification = new Notification();

				String userName = (String) SessionData.getUserDetails().get("userID");
				int userId = usersService.getUserInstanceByLoginName(userName).getUrId();
				int toUsersId = openNewTicketService.getUserIdBasedOnPersonId((Integer)billDataValues[1]);
			    if(toUsersId!=0){
			        notification.setUser_id(toUsersId+"");
					notification.setFromUser(userId+"");
					notification.setSubject("Your bills are generated");
					notification.setMessage("This notification is to inform you that your bill generated");
					notification.setRead_status(0);
					notification.setMsg_status("INBOX");
					notificationService.save(notification);
			      }
			}else{
				logger.info("::::::::::::: else block");
			} 
		}
		return null;
	}
	
	@RequestMapping(value = "/consolidate/sendAllBillsMonthWiseAndServiceWise", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String sendAllBillsMonthWiseAndServiceWise(HttpServletRequest request,HttpServletResponse response,Locale locale) throws ParseException{
		
	   java.util.Date month = new SimpleDateFormat("MMMM yyyy").parse(request.getParameter("selectedMonth"));
	   Calendar cal = Calendar.getInstance();
	   cal.setTime(month);
	   int month1 = cal.get(Calendar.MONTH);
	   int actualmonth = month1 + 1;
       int year = cal.get(Calendar.YEAR);
       
       String serviceType = request.getParameter("serviceType");
       try{
    	   synchronized (this) {
    		   fetchBillsData(actualmonth,year,serviceType);
    	   }
    	   return "success";
       }catch(Exception e){
    	   e.printStackTrace();
    	   return "fail";
       }
	}
	
	@Async
	private void fetchBillsData(int actualmonth,int year,String serviceType){
		
		List<?> billsDataList = electricityBillsService.fetchBillsDataBasedOnMonthAndServiceType(actualmonth,year,serviceType);
		System.out.println("Size of list++++++++++++"+billsDataList.size());
		for (Iterator<?> billIterator = billsDataList.iterator(); billIterator.hasNext();) {
			final Object[] values = (Object[]) billIterator.next();
			
			Blob blob = electricityBillsService.getBlog((Integer)values[0]);
			if(blob != null){
				
				int propertyId = (Integer)values[2];				
				List<String> contactDetailsList = camConsolidationService.getContactDetailsForMail((Integer)values[1]);
				String toAddressEmail = "";
				String toAddressMobile = "";
				
				for (Iterator<?> iterator = contactDetailsList.iterator(); iterator.hasNext();) {
					final Object[] contactValues = (Object[]) iterator.next();
					if(((String)contactValues[0]).equals("Email")){
						toAddressEmail = (String)contactValues[1];
					}else{
						toAddressMobile = (String)contactValues[1];
					}				
				}
					
				List<String> tenantContactDetailsList=null;
				String tenantAddressEmail = "";
				String tenantAddressMobile = "";
				String tenantName="";
				String tenantStatus= tenantService.getTenantStatusByPropertyId(propertyId);
				if(tenantStatus!= null && tenantStatus.equalsIgnoreCase("Active"))
				{
					tenantContactDetailsList= tenantService.getContactDetailsByPersonId(propertyId);
					tenantName=tenantService.getTenantFirstNameByPropertyId(propertyId);
				for (Iterator<?> iterator = tenantContactDetailsList.iterator(); iterator.hasNext();) {
					final Object[] values1 = (Object[]) iterator.next();
					if(((String)values1[1]).equals("Email")){
						tenantAddressEmail = (String)values1[2];
					}else{
						tenantAddressMobile = (String)values1[2];
					}				
					}
				
				
				}
				Object[] personNameData = openNewTicketService.getPersonNameBasedOnPersonId((Integer)values[1]);
				String personName = "";
				 personName = personName.concat((String)personNameData[0]);
				 if(personNameData[1]!= null){
				 	personName = personName.concat(" ");
					personName = personName.concat((String)personNameData[1]);
				 }
				try {
									
					String status ="";
					mailCode(toAddressEmail,toAddressMobile,serviceType,propertyId,blob,personName);
					status="Yes";
					if(tenantStatus!= null && tenantStatus.equalsIgnoreCase("Active"))
					{
						mailCode(tenantAddressEmail,tenantAddressMobile,serviceType,propertyId,blob,tenantName);
						status =status +", T";
					}
											
					electricityBillsService.updateMailSent_Status((Integer)values[0],serviceType,status);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
 
				Notification notification = new Notification();

				String userName = (String) SessionData.getUserDetails().get("userID");
				System.err.println("userName "+userName);
				int userId=0;
				if(usersService.getUserInstanceByLoginName(userName)==null){
				
				}else{
					userId = usersService.getUserInstanceByLoginName(userName).getUrId();
				}
				int toUsersId = openNewTicketService.getUserIdBasedOnPersonId((Integer)values[1]);
				System.err.println("toUsersId "+toUsersId);
			    if(toUsersId!=0){
			        notification.setUser_id(toUsersId+"");
					notification.setFromUser(userId+"");
					notification.setSubject("Your bills are generated");
					notification.setMessage("This notification is to inform you that your bill generated");
					notification.setRead_status(0);
					notification.setMsg_status("INBOX");
					notificationService.save(notification);
			      }
			}else{
				logger.info("No bill for sending to mail and sms");
			}
		}
	}
	@RequestMapping(value = "/notGeneratedBill")
	public String notGeneratedBill(ModelMap model,HttpServletRequest request) 
	{
		model.addAttribute("ViewName", "Bills Generation");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Bills Generation", 1, request);
		breadCrumbService.addNode("Not Generated Bills ", 2, request);
		return "electricityBills/notGeneratedBill";
	}
	
	@RequestMapping(value = "/noGeneratedBill/searchBillsMonthWiseAndServiceWise", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> searchNotGeneratedBillsMonthWiseAndServiceWise(HttpServletRequest request,HttpServletResponse response,Locale locale) throws ParseException{
		
	   final java.util.Date month = new SimpleDateFormat("MMMM yyyy").parse(request.getParameter("presentdatePost"));
	   String service=request.getParameter("serviceTypePost");
	   System.err.println("service++"+service);
	   Calendar cal = Calendar.getInstance();
	   cal.setTime(month);
		int month1 = cal.get(Calendar.MONTH);
		int montOne =month1 +1;
		int year = cal.get(Calendar.YEAR);
		
		System.err.println("month ++"+montOne);
		System.err.println("yeasr+++"+year);
	   List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (final Object[] record : electricityBillsService.searchNotGeneratedBillMonth(service,montOne,year)) 
		{	
			list.add(new HashMap<String, Object>() {{
				
				put("accountNo", (String)record[1]);
				put("serviceType",(String)record[2]);
				put("propertyNo",(String)record[5]);
				put("billMonth", new SimpleDateFormat("MMMM, yyyy").format(month));
				/*put("billMonth", new SimpleDateFormat("MMMM, yyyy").format((Date)record[3]));
				put("billMonthSql", (Date)record[3]);
				put("billAmount", (Double)record[7]);*/
				
				String personName = "";
				personName = personName.concat((String)record[3]);
				if((String)record[4] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)record[4]);
				}
				put("personName", personName);
			}
			});
		}
		return list;
	}

/*-------------------------------------------- Export To Excel for Not Generated Bill --------------------------------------------------------------*/	
	
	
	@RequestMapping(value = "/electricityBillTemplate/notGeneratedBillTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileNotGeneratedBill(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new java.util.Date(), "MMM yyyy")+".xlsx";
		/*final java.util.Date month = new SimpleDateFormat("MMMM yyyy").parse(request.getParameter("presentdatePost"));*/
		java.util.Date month = new SimpleDateFormat("MMMM yyyy").parse(request.getParameter("month"));
		
		String service=request.getParameter("service");
		   
       
		   Calendar cal = Calendar.getInstance();
		   cal.setTime(month);
			int month2 = cal.get(Calendar.MONTH);
			int montOne =month2 +1;
			int year = cal.get(Calendar.YEAR);
			System.err.println(service+" "+montOne+" "+year );
		List<Object[]> electricityBillTemplateEntities =electricityBillsService.searchNotGeneratedBillMonth(service,montOne,year);
	
               System.out.println("electricityBillTemplateEntities++"+electricityBillTemplateEntities.size());
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
        header.createCell(3).setCellValue("Service Type");
        header.createCell(4).setCellValue("Bill Month");
        
    
        for(int j = 0; j<=4; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:E200"));
        }
        
        int count = 1;
        //XSSFCell cell = null;
    	for (Object[] electricityBill :electricityBillTemplateEntities ) {
    		
    		XSSFRow row = sheet.createRow(count);
    		
    		String str="";
    		if((String)electricityBill[1]!=null){
    			str=(String)electricityBill[1];
    		}
    		else{
    			str="";
    		}
    		row.createCell(0).setCellValue(str);
    		
    		String personName = "";
    		if((String)electricityBill[3] != null){
    			personName = personName.concat((String)electricityBill[3]);	
    		}
    		else{
    			personName ="";
    		}
			if((String)electricityBill[4] != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)electricityBill[4]);
			}
			else{
				personName ="";
			}
    		row.createCell(1).setCellValue(personName);
    		
    		if((String)electricityBill[5]!=null){
    			str=(String)electricityBill[5];
    		}
    		else{
    			str="";
    		}
    		row.createCell(2).setCellValue(str);
    		
    		if((String)electricityBill[2]!=null){
    			str=(String)electricityBill[2];
    		}
    		else{
    			str="";
    		}
    		row.createCell(3).setCellValue(str);
    		
    		SimpleDateFormat sdf = new SimpleDateFormat("MMM,yyyy");
    		if(month!=null){
    			str=sdf.format(month);
    		}
    		else{
    			str="";
    		}
    		row.createCell(4).setCellValue(str);
    		
    		
    		
    		
    		count ++;
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        ServletOutputStream servletOutputStream;
		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"NotGeneratedBillsTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	
	
/*	----------------------------------------Code for Export To PDF for Not generated Bill Module----------------------------------------------------------
*/	
	
	
	@RequestMapping(value = "/electricityBillPdfTemplate/notGeneratedBillPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileElectricityBillPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new java.util.Date(), "MMM yyyy")+".xlsx";
		/*final java.util.Date month = new SimpleDateFormat("MMMM yyyy").parse(request.getParameter("presentdatePost"));*/
		java.util.Date month = new SimpleDateFormat("MMMM yyyy").parse(request.getParameter("month"));
		
		String service=request.getParameter("service");
		   
       
		   Calendar cal = Calendar.getInstance();
		   cal.setTime(month);
			int month2 = cal.get(Calendar.MONTH);
			int montOne =month2 +1;
			int year = cal.get(Calendar.YEAR);
			System.err.println(service+" "+montOne+" "+year );
		List<Object[]> electricityBillTemplateEntities =electricityBillsService.searchNotGeneratedBillMonth(service,montOne,year);
	
               System.out.println("electricityBillTemplateEntities++"+electricityBillTemplateEntities.size());
	
	
              
      
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
      
        																																																																																																																																		
        PdfPTable table = new PdfPTable(5);
        Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
        
    	
        
        table.addCell(new Paragraph("Account No",font1));
        table.addCell(new Paragraph("Person Name",font1));
        table.addCell(new Paragraph("Property No",font1));
        table.addCell(new Paragraph("Service Type",font1));
        table.addCell(new Paragraph("Bill Month",font1));
        for (Object[] electricityBill :electricityBillTemplateEntities) {
    		
        	String str="";
    		if((String)electricityBill[1]!=null){
    			str=(String)electricityBill[1];
    		}
    		else{
    			str="";
    		}

        
        PdfPCell cell1 = new PdfPCell(new Paragraph(str,font3));
        
        String personName = "";
		if((String)electricityBill[3] != null){
			personName = personName.concat((String)electricityBill[3]);	
		}
		else{
			personName ="";
		}
		
		if((String)electricityBill[4] != null)
		{
			personName = personName.concat(" ");
			personName = personName.concat((String)electricityBill[4]);
		}
		else{
			personName ="";
		}
        PdfPCell cell2 = new PdfPCell(new Paragraph(personName,font3));
    	
       
		if((String)electricityBill[5]!=null){
			str=(String)electricityBill[5];
		}
		else{
			str="";
		}
    
        PdfPCell cell3 = new PdfPCell(new Paragraph(str,font3));
       
        if((String)electricityBill[2]!=null){
			str=(String)electricityBill[2];
		}
		else{
			str="";
		}
        
        PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
        
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
        if(month!=null){
			str=sd.format(month);
		}
		else{
			str="";
		}
        
        PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));
        
        
      
       
       

			        table.addCell(cell1);
			        table.addCell(cell2);
			        table.addCell(cell3);
			        table.addCell(cell4);
			        table.addCell(cell5);
       

        table.setWidthPercentage(100);
        float[] columnWidths = {15f, 15f, 15f, 15f, 15f};

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
		response.setHeader("Content-Disposition", "inline;filename=\"NotGeneratedTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	
	/*=========================================Send Cam Bill manually=========================================================*/
	@RequestMapping(value = "/consolidate/sendALLBillatOneTime", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String sendALLBillatOneTime(HttpServletRequest request,HttpServletResponse response,Locale locale) throws ParseException{
		System.err.println("Selected Month========>"+request.getParameter("selectedMonth"));
		System.err.println("Service Type==========>"+request.getParameter("serviceType"));
		
	   java.util.Date month = new SimpleDateFormat("MMMM yyyy").parse(request.getParameter("selectedMonth"));
	   Calendar cal = Calendar.getInstance();
	   cal.setTime(month);
	   int month1 = cal.get(Calendar.MONTH);
	   int actualmonth = month1 + 1;
       int year = cal.get(Calendar.YEAR);
       
       String serviceType = request.getParameter("serviceType");
       try{
    	   synchronized (this) {
    		   fetchALLBillsData(actualmonth,year,serviceType);
    	   }
    	   return "success";
       }catch(Exception e){
    	   return "fail";
       }
	}
	@Async
	private void fetchALLBillsData(int actualmonth,int year,String serviceType){
		
		List<?> billsDataList = electricityBillsService.fetch200BillsData(actualmonth,year,serviceType);
		System.out.println("Size of list++++++++++++"+billsDataList.size());
		for (Iterator<?> billIterator = billsDataList.iterator(); billIterator.hasNext();) {
			final Object[] values = (Object[]) billIterator.next();
			
			Blob blob = electricityBillsService.getBlog((Integer)values[0]);
			if(blob != null){
				
				int propertyId = (Integer)values[2];				
				List<String> contactDetailsList = camConsolidationService.getContactDetailsForMail((Integer)values[1]);
				String toAddressEmail = "";
				String toAddressMobile = "";
				
				for (Iterator<?> iterator = contactDetailsList.iterator(); iterator.hasNext();) {
					final Object[] contactValues = (Object[]) iterator.next();
					if(((String)contactValues[0]).equals("Email")){
						toAddressEmail = (String)contactValues[1];
					}else{
						toAddressMobile = (String)contactValues[1];
					}				
				}
					
				List<String> tenantContactDetailsList=null;
				String tenantAddressEmail = "";
				String tenantAddressMobile = "";
				String tenantName="";
				String tenantStatus= tenantService.getTenantStatusByPropertyId(propertyId);
				if(tenantStatus!= null && tenantStatus.equalsIgnoreCase("Active"))
				{
					tenantContactDetailsList= tenantService.getContactDetailsByPersonId(propertyId);
					tenantName=tenantService.getTenantFirstNameByPropertyId(propertyId);
				for (Iterator<?> iterator = tenantContactDetailsList.iterator(); iterator.hasNext();) {
					final Object[] values1 = (Object[]) iterator.next();
					if(((String)values1[1]).equals("Email")){
						tenantAddressEmail = (String)values1[2];
					}else{
						tenantAddressMobile = (String)values1[2];
					}				
					}
				
				
				}
				Object[] personNameData = openNewTicketService.getPersonNameBasedOnPersonId((Integer)values[1]);
				String personName = "";
				 personName = personName.concat((String)personNameData[0]);
				 if(personNameData[1]!= null){
				 	personName = personName.concat(" ");
					personName = personName.concat((String)personNameData[1]);
				 }
				try {				
					String status ="";
					mailCode(toAddressEmail,toAddressMobile,serviceType,propertyId,blob,personName);
					status="Yes";
					if(tenantStatus!= null && tenantStatus.equalsIgnoreCase("Active"))
					{
						mailCode(tenantAddressEmail,tenantAddressMobile,serviceType,propertyId,blob,tenantName);
						status =status +", T";
					}
											
					electricityBillsService.updateMailSent_Status((Integer)values[0],serviceType,status);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				
				try {
					Notification notification = new Notification();

					String userName = (String) SessionData.getUserDetails().get("userID");
					int userId = usersService.getUserInstanceByLoginName(userName).getUrId();
					int toUsersId = openNewTicketService.getUserIdBasedOnPersonId((Integer)values[1]);
				    if(toUsersId!=0){
				        notification.setUser_id(toUsersId+"");
						notification.setFromUser(userId+"");
						notification.setSubject("Your bills are generated");
						notification.setMessage("This notification is to inform you that your bill generated");
						notification.setRead_status(0);
						notification.setMsg_status("INBOX");
						notificationService.save(notification);
				      }
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else{
				logger.info("No bill for sending to mail and sms");
			}
		}
	}

}
