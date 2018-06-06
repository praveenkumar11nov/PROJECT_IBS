package com.bcits.bfm.serviceImpl.billingWizardImpl;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.mail.MessagingException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.controller.BillController;
import com.bcits.bfm.controller.TariffCalculationController;
import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.Address;
import com.bcits.bfm.model.BillParameterMasterEntity;
import com.bcits.bfm.model.BillingPaymentsEntity;
import com.bcits.bfm.model.CamReportSettingsEntity;
import com.bcits.bfm.model.CommonTariffMaster;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.ELTariffMaster;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.ElectricityBillLineItemEntity;
import com.bcits.bfm.model.ElectricityBillParametersEntity;
import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.model.ElectricityMetersEntity;
import com.bcits.bfm.model.GasTariffMaster;
import com.bcits.bfm.model.LastSixMonthsBills;
import com.bcits.bfm.model.Notification;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.model.ServiceParameterMaster;
import com.bcits.bfm.model.ServiceParametersEntity;
import com.bcits.bfm.model.SlabDetails;
import com.bcits.bfm.model.SolidWasteTariffMaster;
import com.bcits.bfm.model.TransactionMasterEntity;
import com.bcits.bfm.model.WTTariffMaster;
import com.bcits.bfm.service.NotificationService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.accountsManagement.ElectricityLedgerService;
import com.bcits.bfm.service.advanceCollection.AdvanceCollectionService;
import com.bcits.bfm.service.billingCollectionManagement.PaymentService;
import com.bcits.bfm.service.billingWizard.BillingTemplateService;
import com.bcits.bfm.service.commonAreaMaintenance.CamConsolidationService;
import com.bcits.bfm.service.commonAreaMaintenance.CamLedgerService;
import com.bcits.bfm.service.commonAreaMaintenance.CamReportSettingsService;
import com.bcits.bfm.service.commonServicesTariffManagement.CommonServiceTariffMasterServices;
import com.bcits.bfm.service.commonServicesTariffManagement.CommonServicesRateMasterService;
import com.bcits.bfm.service.customerOccupancyManagement.AddressService;
import com.bcits.bfm.service.customerOccupancyManagement.CityService;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.deposits.DepositsService;
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
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.service.waterTariffManagement.WTRateMasterService;
import com.bcits.bfm.service.waterTariffManagement.WTTariffMasterService;
import com.bcits.bfm.util.EmailCredentialsDetailsBean;
import com.bcits.bfm.util.NumberToWord;
import com.bcits.bfm.util.SendBillInfoThroughSMS;
import com.bcits.bfm.util.SendMailConsolidatedBill;
import com.bcits.bfm.util.SendMailConsolidationBills;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.util.SmsCredentialsDetailsBean;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Repository
public class BillingTemplateServiceImpl implements BillingTemplateService  {

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
	private CamConsolidationService camConsolidationService;

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
	private TransactionMasterService transactionMasterService;
	
	@Autowired
	private DepositsService depositsService;
	
	@Autowired
	private AdvanceCollectionService advanceCollectionService;
	
	@Autowired
	private MeterParameterMasterService meterParameterMasterService;
	
	@Autowired
	private PersonService personService;
	
	static Logger logger = LoggerFactory.getLogger(BillController.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ServiceMasterService masterService;

	@Autowired
	private CityService cityService;
	
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
	private CamLedgerService camLedgerService;
	
	@Autowired
	private CamReportSettingsService camReportSettingsService;
	
	@Autowired
	private UsersService usersService;

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private CommonServiceTariffMasterServices commonServiceTariffMasterServices;
	
	@Autowired
	private PropertyService propertyService;
	
	public String consolidatedBillMailTemplate(int accountId,String billMonth,Locale locale){

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

		String address1 = (String) addressDetailsList[0];
		// String city = (String)addressDetailsList[1];

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
		
		String calculationBasis = camLedgerService.getCamSetting().get(0).getCamChargesBasedOn();

		String str= ""
				+"<div id='myTab'>"
				+"<table id='tabs' style='width: 100%; background: white; border: 2px solid black; border-radius: 34px; padding: 21px 23px;'>"
				+"<tr>"
				+"	<td style='padding: 5.5em; border: 1px solid #808080;' width='50%' colspan='2'><img id='eye' src='http://www.ireoprojects.co.in/gallery/ireo-grandarch.jpg' height='100px' width='300px' /></td>"
				+"	<td style='padding: 5.5em; border: 1px solid #808080;vertical-align:middle' width='49%'>Orchid Centre, DLF Golf Course Rd, IILM Institute, Sector 53, Gurgaon, Haryana, 0124 475 4000 </td>"
				+"</tr>"
				+"<tr>"
				+"	<td style='padding: 5.5em; border: 1px solid #808080;background: black; color: white; font-weight: bolder;' colspan='3'>Customer Details</td>"
				+"</tr>"
				+"<tr>"
				+"	<td style='padding: 5.5em; border: 1px solid #808080;padding-left: 25px' width='49%' ><b> <h4 id='name'>"+account.getPerson().getFirstName()+" "+account.getPerson().getLastName()+"</h4> </b> "
				+ "		<span id='addr'>"+address1+"</span>"
				+"		<span id='email'>"+toAddressMobile+"</span>"
				+"		<span id='mobile'>"+toAddressEmail+"</span></td>"
				+"	<td style='padding: 5.5em; border: 1px solid #808080; vertical-align: middle; border-left: 2px solid' colspan='2'>"
				+"		<table style='width: 100%;'>"
				+"			<tr>"
				+"				<td style='padding: 5.5em; border: 1px solid #808080;' align='center'><b>Account Number</b></td>"
				+"				<td style='padding: 5.5em; border: 1px solid #808080;' id='accno'>"+account.getAccountNo()+"</td>"
				+"			</tr>"
				+"			<tr>"
				+"				<td style='padding: 5.5em; border: 1px solid #808080;' align='center'><b>Period From</b></td>"
				+"				<td style='padding: 5.5em; border: 1px solid #808080;' >"+billToDateStr+"</td>"
				+"			</tr>"
				+"			<tr>"
				+"				<td style='padding: 5.5em; border: 1px solid #808080;' align='center'><b>Period To</b></td>"
				+"				<td style='padding: 5.5em; border: 1px solid #808080;' >"+billFromDateStr+"</td>"
				+"			</tr>"
				+"			<tr>"
				+"				<td style='padding: 5.5em; border: 1px solid #808080;' align='center'><b>Due Date</b></td>"
				+"				<td style='padding: 5.5em; border: 1px solid #808080;' >"+dueDateStr+"</td>"
				+"			</tr>"
				+"		</table>"
				+"	</td>"
				+"</tr>"
				+"<tr style='background-color: black'>"
				+"	<td style='padding: 5.5em; border: 1px solid #808080;backgound: black; color: white; font-weight: bolder;' colspan='3'>Charges</td>"
				+"</tr>"
				+"<tr>"
				+"	<td style='padding: 5.5em; border: 1px solid #808080;' colspan='3'>"
				+"		<table style='width: 100%; text-align: center;'>"
				+"			<tr>"
				+"				<th style='font-weight: bold;padding: 5.5em; border: 1px solid #808080;'>Past Dues</th>"
				+"				<th style='font-weight: bold;padding: 5.5em; border: 1px solid #808080;'>Current Charges</th>"
				+"				<th style='font-weight: bold;padding: 5.5em; border: 1px solid #808080;'>Payments</th>"
				+"				<th style='font-weight: bold;padding: 5.5em; border: 1px solid #808080;'>Adjustment</th>"
				+"				<th style='font-weight: bold;padding: 5.5em; border: 1px solid #808080;'>Amount Due</th>"
				+"			</tr>"
				+"			<tr>"
				+"				<td style='padding: 5.5em; border: 1px solid #808080;'>"+arrearsAmount+"</td>"
				+"				<td style='padding: 5.5em; border: 1px solid #808080;'>"+(eleAmt+waterAmt+gasAmt+swAmt+otherAmt+teleAmt)+"</td>"
				+"				<td style='padding: 5.5em; border: 1px solid #808080;'>"+(-(paymentAmount))+"</td>"
				+"				<td style='padding: 5.5em; border: 1px solid #808080;'>"+adjustment+"</td>"
				+"				<td style='padding: 5.5em; border: 1px solid #808080;'>"+((arrearsAmount)+(eleAmt+waterAmt+gasAmt+swAmt+otherAmt+teleAmt+camAmt)+(paymentAmount)-(adjustment))+"</td>"
				+"			</tr>"
				+"		</table>"
				+"	</td>"
				+"</tr>"
				+"<tr style='background-color: black'>"
				+"	<td style='padding: 5.5em; border: 1px solid #808080;backgound: black; color: white; font-weight: bolder;' colspan=3>Bill Details</td>"
				+"</tr>"
				+"<tr>"
				+"	<td colspan='3'>"
				+"		<table style='width: 100%; text-align: center;'>"
				+"			<tr>"
				+"				<td width='30%' width='30%' style='background:#7cb5ec;padding: 5.5em; border: 1px solid #808080;'>"
				+"					<table style='width: 100%; text-align: center;'>"
				+"						<tr>"
				+"							<th style='font-weight: bold;padding: 5.5em; border: 1px solid #808080;' colspan='2'>Electricity</th>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' width='50%'>Tariff</td>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' width='50%' id='eletariff'>"+elTariffName+"</td>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;'>Amount</td>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' id='eleamt'>"+eleAmt+"</td>"
				+"						</tr>"
				+"					</table>"
				+"				</td>"
				+"				<td width='40%' rowspan='2' id='tdcontainer' style='vertical-align: middle;'>"
				+"					<div id='syed' style='min-width: 300px; height: 400px; max-width: 300px; margin: 0 auto; vertical-align: middle;'>"
				+"					</div> "
				+"				</td>"
				+"				<td width='30%' width='30%' style='background:#f7a35c;padding: 5.5em; border: 1px solid #808080;'>"
				+"					<table style='width: 100%; text-align: center;'>"
				+"						<tr>"
				+"							<th style='font-weight: bold;padding: 5.5em; border: 1px solid #808080;' colspan='2'>Gas</th>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' width='50%'>Tariff</td>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' width='50%' id='gastariff'>"+gsTariffName+"</td>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' width='50%'>Amount</td>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' width='50%' id='gasamt'>"+gasAmt+"</td>"
				+"						</tr>"
				+"					</table>"
				+"				</td>"
				+"			</tr>"
				+"			<tr>"
				+"				<td width='30%' width='30%' style='background:#90ee7e;padding: 5.5em; border: 1px solid #808080;'>"
				+"					<table style='width: 100%; text-align: center;'>"
				+"						<tr>"
				+"							<th style='font-weight: bold;padding: 5.5em; border: 1px solid #808080;' colspan='2'>Water</th>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' width='50%'>Tariff</td>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' width='50%' id='watertariff'>"+wtTariffName+"</td>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;'>Amount</td>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' id='wateramt'>"+waterAmt+"</td>"
				+"						</tr>"
				+"					</table>"
				+"				</td>"

				+"				<td width='30%' width='30%' style='background:#7798BF;padding: 5.5em; border: 1px solid #808080;'>"
				+"					<table style='width: 100%; text-align: center;'>"
				+"						<tr>"
				+"							<th style='font-weight: bold;padding: 5.5em; border: 1px solid #808080;' colspan='2'>Solid Waste</th>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' width='50%' >Tariff</td>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' width='50%' id='swtariff'>"+swTariffName+"</td>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;'>Amount</td>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' id='swamt'>"+swAmt+"</td>"
				+"						</tr>"
				+"					</table>"
				+"				</td>"
				+"			</tr>"
				+"			<tr>"
				+"				<td width='30%' width='30%' style='background:#aaeeee;padding: 5.5em; border: 1px solid #808080;'>"
				+"					<table style='width: 100%; text-align: center;'>"
				+"						<tr>"
				+"							<th style='font-weight: bold;padding: 5.5em; border: 1px solid #808080;' colspan='2'>Telecom</th>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' width='50%'>Tariff</td>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' width='50%' id='internettariff'>"+teleTariffName+"</td>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;'>Amount</td>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' id='internetamt'>"+teleAmt+"</td>"
				+"						</tr>"

				+"					</table>"
				+"				</td>"

				+"				<td width='30%' width='30%' style='background: #ff0066;padding: 5.5em; border: 1px solid #808080;'>"
				+"					<table style='width: 100%; text-align: center;'>"
				+"						<tr>"
				+"							<th style='font-weight: bold;padding: 5.5em; border: 1px solid #808080;' colspan='2'>Common</th>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' width='50%'>Tariff</td>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' width='50%' id='commontariff'>"+otTariffName+"</td>"
				+"						</tr>"
				+"						<tr>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;'>Amount</td>"
				+"							<td style='padding: 5.5em; border: 1px solid #808080;' id='commonamt'>"+otherAmt+"</td>"
				+"						</tr>"
				+"					</table>"
				+"				</td>"
				
				
				+"<td width='30%' style='border: 1px solid #808080;background: #FFFF33'>" 
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

	@SuppressWarnings({ "unused" })
	@Async
	@Override
	public void allConsolidatedBillMailTemplate(int elBillId,Locale locale) {

		Map<String, Object> map = new LinkedHashMap<>();
		Address address = null;
		Contact contactMob  = null;
		Contact contactEmail = null;
		String tariffName = "";
		
		String billType = "";
		String slabs= "";
		String bpmStr = "";
		String mpmStr = "";
		String bliStr = "";
		String spmStr= "";
		
		String postType = "";
		String str = "";
		String meterSrNo ="";
		String meterType ="";
		String meterMake ="";
		
		float sanctionLoad=0.0f;
		float sanctionLoadDG=0.0f;
		String arrearString="";
		Float uomValue=0.0f;
		Float numberOfDays=0.0f;
		Float presentReading=0.0f;
		Float previousReading=0.0f;
		Float meterConstant=0.0f;
		int elTariffId = 0;
		Float pfValue=0.0f;
		Float mdiValue=0.0f;
		int solidWasteTariffId =0;
		int wtTariffId =0;
		int gasTariffId=0;
		String dgApplicable="";
		String dgSlabString = "";
		Double arrearsAmount=0.0;
		String meterStatus="";
		Double voltageLevel =0.0;
		String connectionType="";
		Double interestOnTax=0.0;
		Double interestAmount=0.0;
		Double taxAmount=0.0;
		float amountForInteresetCal=0.0f;
	    Float latePaymentAmount=0.0f;
	    Float connectionSecurity=0.0f;
	    Float dgUnits =0.0f;
	    String othersTariffName= "";
	    int othersTariffId = 0;
	    List<SlabDetails> dgSlabDetailsList = new ArrayList<>();
	    List<SlabDetails> slabDetailsList = new ArrayList<>();
	    
		ElectricityBillEntity electricityBillEntity = electricityBillsService.find(elBillId);
		
        if(electricityBillEntity!=null){
			billType = electricityBillEntity.getBillType();
			postType = electricityBillEntity.getPostType();
			
			Object[] addressDetailsList = camConsolidationService.getAddressDetailsForMail(electricityBillEntity.getAccountObj().getPerson().getPersonId());
			List<String> contactDetailsList = camConsolidationService.getContactDetailsForMail(electricityBillEntity.getAccountObj().getPerson().getPersonId());
		
			/*String address1 = (String)addressDetailsList[0];
			String city = (String)addressDetailsList[1];*/
			Property property = propertyService.find(electricityBillEntity.getAccountObj().getPropertyId());
			String address1 = property.getProperty_No()+","+property.getBlocks().getBlockName();
			String city = "Gurgaon";
			
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
			
           if(electricityBillEntity.getTypeOfService().equals("CAM")){
        	   logger.info("cam bill templete -------------------------------");
           }else{
				
				String spmQuery = "select spm from ServiceParameterMaster spm where spm.serviceType='"+electricityBillEntity.getTypeOfService()+"' order by spm.spmSequence";
				String bpmQuery = "select bpm from BillParameterMasterEntity bpm where bpm.serviceType='"+electricityBillEntity.getTypeOfService()+"' order by bpm.bvmSequence";
				String lineItems ="select bli from ElectricityBillLineItemEntity bli where bli.electricityBillEntity.elBillId="+elBillId;
				
				List<ServiceParameterMaster> spmList = serviceParameterMasterService.executeSimpleQuery(spmQuery);

				if(spmList.size()>0){
					for(ServiceParameterMaster entity : spmList){
						
						ServiceParametersEntity serviceParametersEntity = serviceParameterService.getSingleResult("select obj from ServiceParametersEntity obj where obj.spmId="+entity.getSpmId()+" and obj.serviceMastersEntity.accountObj.accountId="+electricityBillEntity.getAccountObj().getAccountId());
						if(serviceParametersEntity!=null)
						{
							if(serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Sanctioned KW") || serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Sanctioned KVA")||serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Sanctioned HP"))
							{
								sanctionLoad = Float.parseFloat(serviceParametersEntity.getServiceParameterValue());
							}
							/*if(serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("DG Applicable"))
							{
								dgApplicable=serviceParametersEntity.getServiceParameterValue();
								if(serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Sanctioned DG"))
								{
									sanctionLoadDG = Float.parseFloat(serviceParametersEntity.getServiceParameterValue());
								}
							}*/
							
							dgApplicable=serviceParametersEntity.getServiceParameterValue();
							if(serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Sanctioned DG"))
							{
								sanctionLoadDG = Float.parseFloat(serviceParametersEntity.getServiceParameterValue());
							}
							
							if(serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Connection Type"))
							{
							 connectionType=serviceParametersEntity.getServiceParameterValue();
							}
							
							if(serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Voltage Level"))
							{
							 voltageLevel = Double.parseDouble(serviceParametersEntity.getServiceParameterValue());
							}
							if(serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Connection Security"))
							{
								connectionSecurity = Float.parseFloat(serviceParametersEntity.getServiceParameterValue());
							}
						}
					}
				}

			    ElectricityMetersEntity electricityMetersEntity = electricityMetersService.getMeter(electricityBillEntity.getAccountId(),electricityBillEntity.getTypeOfService());
			   if(electricityMetersEntity!=null)
			   {
				   meterSrNo = electricityMetersEntity.getMeterSerialNo();
				   meterType = meterParameterMasterService.getMeterParameter(electricityBillEntity.getAccountId(),electricityMetersEntity.getElMeterId(),electricityMetersEntity.getTypeOfServiceForMeters(),"Meter type");
				   meterMake = meterParameterMasterService.getMeterParameter(electricityBillEntity.getAccountId(),electricityMetersEntity.getElMeterId(),electricityMetersEntity.getTypeOfServiceForMeters(),"Meter Make");
				   
				   if(meterParameterMasterService.getMeterParameter(electricityBillEntity.getAccountId(),electricityMetersEntity.getElMeterId(),electricityMetersEntity.getTypeOfServiceForMeters(),"Meter Constant")!=null)
				   {
					   float meterConstant1 = Float.parseFloat(meterParameterMasterService.getMeterParameter(electricityBillEntity.getAccountId(),electricityMetersEntity.getElMeterId(),electricityMetersEntity.getTypeOfServiceForMeters(),"Meter Constant"));
					   if(meterConstant1==1)
					   {
						   meterStatus="Normal";
					   }else if (meterConstant1<1) {
						   meterStatus="Slow";
					    }
					   else if (meterConstant1>1) {
						   meterStatus="Fast";
					    }
				   }
			   }
			    
				List<BillParameterMasterEntity> bpmList = billParameterMasterService.executeSimpleQuery(bpmQuery);

				if(bpmList.size()>0){
					for(BillParameterMasterEntity entity : bpmList){
						if(entity!=null)
						{
							ElectricityBillParametersEntity billingParameterMaster = electricityBillParameterService.getSingleResult("select obj from ElectricityBillParametersEntity obj where obj.billParameterMasterEntity.bvmId="+entity.getBvmId()+" and obj.electricityBillEntity.accountObj.accountId="+electricityBillEntity.getAccountObj().getAccountId()+" and obj.electricityBillEntity.elBillId="+elBillId);
							if(billingParameterMaster!=null){
								if(billingParameterMaster.getNotes()!=null)
									slabs = billingParameterMaster.getNotes(); 
							}
						}
					}
				}
				List<ElectricityBillLineItemEntity> bliList = billLineItemService.executeSimpleQuery(lineItems);
				if(bliList.size()>0){
					for (ElectricityBillLineItemEntity electricityBillLineItemEntity : bliList) {
						List<TransactionMasterEntity> listTM =  transactionMasterService.executeSimpleQuery("select o from TransactionMasterEntity o where o.transactionCode='"+electricityBillLineItemEntity.getTransactionCode()+"'");
						if(!listTM.isEmpty())
						{
							electricityBillLineItemEntity.setTransactionName(listTM.get(0).getTransactionName());
						}
					}
				}
				List<ElectricityBillLineItemEntity> arrearsList = new ArrayList<>();
				ElectricityBillEntity previoisBill;
				if(electricityBillEntity.getArrearsAmount()>0)
				{
					previoisBill = electricityBillsService.getPreviousBillWithOutStatus(electricityBillEntity.getAccountId(), electricityBillEntity.getTypeOfService(), electricityBillEntity.getFromDate(), postType);
					if(previoisBill!=null)
					{
					   arrearsAmount=previoisBill.getNetAmount();
						for (ElectricityBillLineItemEntity previousBillLineItems : previoisBill.getBillLineItemEntities()) {

							List<TransactionMasterEntity> listTM = transactionMasterService.executeSimpleQuery("select o from TransactionMasterEntity o where o.transactionCode='"+previousBillLineItems.getTransactionCode()+"'");
							if(!listTM.isEmpty())
							{
								previousBillLineItems.setTransactionName(listTM.get(0).getTransactionName());
								arrearsList.add(previousBillLineItems);
							}
						}
					}
				}
			
				List<ServiceMastersEntity> serviceMastersList = serviceMasterService.executeSimpleQuery("select obj from ServiceMastersEntity obj where obj.accountObj.accountId="+electricityBillEntity.getAccountObj().getAccountId()+" and obj.typeOfService='"+electricityBillEntity.getTypeOfService()+"'");

				if(!serviceMastersList.isEmpty() && serviceMastersList.size()>0){ 
					for (ServiceMastersEntity serviceMastersEntity : serviceMastersList) {
						
						String paraMeterName5 = "Units";
						String uomValueString = null;
						if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(), electricityBillEntity.getTypeOfService(),paraMeterName5) != null) {
							uomValueString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(), paraMeterName5);
							if(uomValueString!=null)
							uomValue = Float.parseFloat(uomValueString);
						}
						
						String paraMeterName6 = "Number of days";
						String numberOfDaysString = null;
						if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(), electricityBillEntity.getTypeOfService(),paraMeterName6) != null) {
							numberOfDaysString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(), paraMeterName6);
							if(numberOfDaysString!=null)
							numberOfDays = Float.parseFloat(numberOfDaysString);
						}
						
						String paraMeterName7 = "Present reading";
						String presentReadingString = null;
						if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(), electricityBillEntity.getTypeOfService(),paraMeterName7) != null) {
							presentReadingString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(), paraMeterName7);
							if(presentReadingString!=null)
							presentReading = Float.parseFloat(presentReadingString);
						}
						
						String paraMeterName8 = "Previous reading";
						String previousReadingString = null;
						if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(), electricityBillEntity.getTypeOfService(),paraMeterName8) != null) {
							previousReadingString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(), paraMeterName8);
							if(previousReadingString!=null)
							previousReading = Float.parseFloat(previousReadingString);
						}
						
						
						String paraMeterName9 = "Meter Constant";
						String meterConstantString = null;
						if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(), electricityBillEntity.getTypeOfService(),paraMeterName9) != null) {
							meterConstantString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(), paraMeterName9);
							if(meterConstantString!=null)
							meterConstant = Float.parseFloat(meterConstantString);
						}
						
						String paraMeterName10 = "DG Units";
						String dgUnitsString = null;
						if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(), electricityBillEntity.getTypeOfService(),paraMeterName10) != null) {
							dgUnitsString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(), paraMeterName10);
							if(dgUnitsString!=null)
							dgUnits = Float.parseFloat(dgUnitsString);
						}
						
						if(serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Electricity")){
							ELTariffMaster eltariffMaster = elTariffMasterService.getSingleResult("select o from ELTariffMaster o where o.elTariffID="+serviceMastersEntity.getElTariffID());
							if(eltariffMaster!=null)
							{
								 elTariffId = serviceMastersEntity.getElTariffID();
								 tariffName = eltariffMaster.getTariffName();
							}
								
						}else if(serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Gas")){
							GasTariffMaster gasTariffMaster = gasTariffMasterService.getSingleResult("select o from GasTariffMaster o where o.gasTariffId="+serviceMastersEntity.getGaTariffID());
							if(gasTariffMaster!=null)
							{
								tariffName = gasTariffMaster.getGastariffName();
								gasTariffId = gasTariffMaster.getGasTariffId();
							}
						}else if(serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Water")){
							List<WTTariffMaster> wttariffMaster = wtTariffMasterService.executeSimpleQuery("select o from WTTariffMaster o where o.wtTariffId="+serviceMastersEntity.getWtTariffID());
							if(wttariffMaster!=null && wttariffMaster.size()>0){
								tariffName = wttariffMaster.get(0).getTariffName();
								wtTariffId = wttariffMaster.get(0).getWtTariffId();
							}
						}else if(serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Solid Waste")){
							SolidWasteTariffMaster solidWasteTariffMaster = solidWasteTariffMasterService.getSingleResult("select o from SolidWasteTariffMaster o where o.solidWasteTariffId="+serviceMastersEntity.getSwTariffID());
							if(solidWasteTariffMaster!=null)
							{
								tariffName = solidWasteTariffMaster.getSolidWasteTariffName();
								solidWasteTariffId = solidWasteTariffMaster.getSolidWasteTariffId();
							}
						}
						else if(serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Others")){
							CommonTariffMaster otherTariffMaster = commonServiceTariffMasterServices.getSingleResult("select o from CommonTariffMaster o where o.csTariffID="+serviceMastersEntity.getOthersTariffID());
							if(otherTariffMaster!=null)
							{
								othersTariffName = otherTariffMaster.getCsTariffName();
								othersTariffId = otherTariffMaster.getCsTariffID();
							}
						}
					}
				}

				 if(electricityBillEntity.getTypeOfService().equalsIgnoreCase("Electricity"))
				 {
					 if(electricityMetersEntity.getMeterType().equalsIgnoreCase("Trivector Meter"))
					 {
						    logger.info("------------- PF and MDI is applicable ----------------");
					    	String paraMeterName10 = "PF";
							String pfString = null;
							if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(), electricityBillEntity.getTypeOfService(),paraMeterName10) != null) {
								pfString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(), paraMeterName10);
								pfValue = Float.parseFloat(pfString);
							}
							String paraMeterName11 = "Maximum Demand Recorded";
							String mdiString = null;
							if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(), electricityBillEntity.getTypeOfService(),paraMeterName11) != null) {
								mdiString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(), paraMeterName11);
								mdiValue = Float.parseFloat(mdiString);	
							}
					 }
				 }
	 List<LastSixMonthsBills> lastSixMonthsBills = new ArrayList<>();		 
	if(electricityBillEntity.getTypeOfService().equalsIgnoreCase("Electricity"))
	{
		slabDetailsList = billController.getTariffSlabList(elTariffId,electricityBillEntity.getFromDate(),electricityBillEntity.getBillDate(),uomValue,locale);
		for (ElectricityBillLineItemEntity electricityBillLineItemEntity : electricityBillEntity.getBillLineItemEntities()) {
	        
			if(electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("EL_TAX"))
			{
				 taxAmount =electricityBillLineItemEntity.getBalanceAmount();
			}
			if(electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("EL_INTEREST"))
			{
				 interestAmount =electricityBillLineItemEntity.getBalanceAmount();
			}
			if(electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("EL_TAX_INTEREST"))
			{
				 interestOnTax =electricityBillLineItemEntity.getBalanceAmount();
			}
		}
		int rateMasterID =rateMasterService.getRateMasterEC(elTariffId, "Rate of interest");
		amountForInteresetCal = (float) (electricityBillEntity.getNetAmount() -(interestOnTax+interestAmount+taxAmount));
		if(rateMasterID!=0)
		{
		 latePaymentAmount= billController.interestCalculationEL(rateMasterID,amountForInteresetCal,electricityBillEntity);
		}
		            
		if(dgApplicable.equalsIgnoreCase("Yes")){
			dgSlabDetailsList =  billController.getTariffSlabStringDGList(elTariffId,electricityBillEntity.getFromDate(),electricityBillEntity.getBillDate(),dgUnits,locale);
		}
		
		List<?> lastSixMontsBills = electricityBillsService.getLastSixMontsBills(electricityBillEntity.getAccountId(),electricityBillEntity.getTypeOfService(),electricityBillEntity.getBillDate(),"Units");
		for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator.hasNext();)
		{
			LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills();
			final Object[] values = (Object[]) iterator.next();
			map.put(new SimpleDateFormat("MMM").format((Date)values[0]), (Double)values[2]);
			sixMonthsBills.setMonth((Date)values[0]);
			sixMonthsBills.setUnits(Double.parseDouble((String)values[1]));
			sixMonthsBills.setAmount((Double)values[2]);
			lastSixMonthsBills.add(sixMonthsBills);
		}
		
	}else if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Water")) {
		slabDetailsList =  billController.getWaterTariffSlabList(wtTariffId,electricityBillEntity.getFromDate(),electricityBillEntity.getBillDate(),uomValue,locale);
	for (ElectricityBillLineItemEntity electricityBillLineItemEntity : electricityBillEntity.getBillLineItemEntities()) {
	        
			if(electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("WT_TAX"))
			{
				 taxAmount =electricityBillLineItemEntity.getBalanceAmount();
			}
			if(electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("WT_INTEREST"))
			{
				 interestAmount =electricityBillLineItemEntity.getBalanceAmount();
			}
			if(electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("WT_TAX_INTEREST"))
			{
				 interestOnTax =electricityBillLineItemEntity.getBalanceAmount();
			}
		}
		
		int rateMasterID =rateMasterService.getRateMasterDomesticGeneral(wtTariffId, "Rate of interest");
		amountForInteresetCal = (float) (electricityBillEntity.getNetAmount() -(interestOnTax+interestAmount+taxAmount));
		if(rateMasterID!=0)
		{
			latePaymentAmount= billController.interestCalculationWt(rateMasterID,amountForInteresetCal,electricityBillEntity);
		}
		
		List<?> lastSixMontsBills = electricityBillsService.getLastSixMontsBills(electricityBillEntity.getAccountId(),electricityBillEntity.getTypeOfService(),electricityBillEntity.getBillDate(),"Units");
		for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator.hasNext();)
		{
			LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills();
			final Object[] values = (Object[]) iterator.next();
			map.put(new SimpleDateFormat("MMM").format((Date)values[0]), (Double)values[2]);
			sixMonthsBills.setMonth((Date)values[0]);
			sixMonthsBills.setUnits(Double.parseDouble((String)values[1]));
			sixMonthsBills.setAmount((Double)values[2]);
			lastSixMonthsBills.add(sixMonthsBills);
		}
	}
	else if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Gas")) {
		slabDetailsList =  billController.getGasTariffSlabList(gasTariffId,electricityBillEntity.getFromDate(),electricityBillEntity.getBillDate(),uomValue,locale);
	for (ElectricityBillLineItemEntity electricityBillLineItemEntity : electricityBillEntity.getBillLineItemEntities()) {
	        
			if(electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("GA_TAX"))
			{
				 taxAmount =electricityBillLineItemEntity.getBalanceAmount();
			}
			if(electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("GA_INTEREST"))
			{
				 interestAmount =electricityBillLineItemEntity.getBalanceAmount();
			}
			if(electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("GA_TAX_INTEREST"))
			{
				 interestOnTax =electricityBillLineItemEntity.getBalanceAmount();
			}
		}
		int rateMasterID =rateMasterService.getRateMasterGas(gasTariffId, "Rate of interest");
		amountForInteresetCal = (float) (electricityBillEntity.getNetAmount() -(interestOnTax+interestAmount+taxAmount));
		if(rateMasterID!=0)
		{
			latePaymentAmount= billController.interestCalculationGas(rateMasterID,amountForInteresetCal,electricityBillEntity);
		}
		
		List<?> lastSixMontsBills = electricityBillsService.getLastSixMontsBills(electricityBillEntity.getAccountId(),electricityBillEntity.getTypeOfService(),electricityBillEntity.getBillDate(),"Units");
		for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator.hasNext();)
		{
			LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills();
			final Object[] values = (Object[]) iterator.next();
			map.put(new SimpleDateFormat("MMM").format((Date)values[0]), (Double)values[2]);
			sixMonthsBills.setMonth((Date)values[0]);
			sixMonthsBills.setUnits(Double.parseDouble((String)values[1]));
			sixMonthsBills.setAmount((Double)values[2]);
			lastSixMonthsBills.add(sixMonthsBills);
		}
		
	}
	else if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Solid Waste")) {
		String paraMeterName5 = "Volume(KG)";
		String uomValueString = null;
		float solidWasterUnits=0.0f;
		if (electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(), electricityBillEntity.getTypeOfService(),paraMeterName5) != null) {
			uomValueString = electricityBillParameterService.getParameterValue(electricityBillEntity.getElBillId(),electricityBillEntity.getTypeOfService(), paraMeterName5);
			if(uomValueString!=null)
				solidWasterUnits = Float.parseFloat(uomValueString);
		}
		slabDetailsList=  billController.getSolidWasteTariffSlabList(solidWasteTariffId,electricityBillEntity.getFromDate(),electricityBillEntity.getBillDate(),solidWasterUnits,locale);
	for (ElectricityBillLineItemEntity electricityBillLineItemEntity : electricityBillEntity.getBillLineItemEntities()) {
			if(electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("SW_TAX"))
			{
				 taxAmount =electricityBillLineItemEntity.getBalanceAmount();
			}
			if(electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("SW_INTEREST"))
			{
				 interestAmount =electricityBillLineItemEntity.getBalanceAmount();
			}
			if(electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("SW_TAX_INTEREST"))
			{
				 interestOnTax =electricityBillLineItemEntity.getBalanceAmount();
			}
		}

		int rateMasterID =rateMasterService.getRateMasterSW(solidWasteTariffId, "Rate of interest");
		amountForInteresetCal = (float) (electricityBillEntity.getNetAmount() -(interestOnTax+interestAmount+taxAmount));
		if(rateMasterID!=0)
		{
			latePaymentAmount= billController.interestCalculationSW(rateMasterID,amountForInteresetCal,electricityBillEntity);
		}
		
		List<?> lastSixMontsBills = electricityBillsService.getLastSixMontsBills(electricityBillEntity.getAccountId(),electricityBillEntity.getTypeOfService(),electricityBillEntity.getBillDate(),"Volume(KG)");
		for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator.hasNext();)
		{
			LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills();
			final Object[] values = (Object[]) iterator.next();
			map.put(new SimpleDateFormat("MMM").format((Date)values[0]), (Double)values[2]);
			sixMonthsBills.setMonth((Date)values[0]);
			sixMonthsBills.setUnits(Double.parseDouble((String)values[1]));
			sixMonthsBills.setAmount((Double)values[2]);
			lastSixMonthsBills.add(sixMonthsBills);
		}
	}
	else if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Others")) {
		
	int rateMasterID =rateMasterService.getRateMasterOT(othersTariffId, "Rate of interest");
	amountForInteresetCal = (float) (electricityBillEntity.getNetAmount() -(interestOnTax+interestAmount+taxAmount));
	if(rateMasterID!=0)
	{
		latePaymentAmount= billController.interestCalculationOT(rateMasterID,amountForInteresetCal,electricityBillEntity);
	}
	
	for (ElectricityBillLineItemEntity electricityBillLineItemEntity : electricityBillEntity.getBillLineItemEntities()) {
	        
			if(electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("OT_TAX"))
			{
				 taxAmount =electricityBillLineItemEntity.getBalanceAmount();
			}
			if(electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("OT_INTEREST"))
			{
				 interestAmount =electricityBillLineItemEntity.getBalanceAmount();
			}
			if(electricityBillLineItemEntity.getTransactionCode().equalsIgnoreCase("OT_TAX_INTEREST"))
			{
				 interestOnTax =electricityBillLineItemEntity.getBalanceAmount();
			}
		}
	
	List<?> lastSixMontsBills = electricityBillsService.getLastSixMontsBillsOthers(electricityBillEntity.getAccountId(),electricityBillEntity.getTypeOfService(),electricityBillEntity.getBillDate(),"Units");
	for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator.hasNext();)
	{
		LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills();
		final Object[] values = (Object[]) iterator.next();
		map.put(new SimpleDateFormat("MMM").format((Date)values[0]), (Double)values[2]);
		sixMonthsBills.setMonth((Date)values[0]);
		sixMonthsBills.setUnits(Double.parseDouble((String)values[1]));
		sixMonthsBills.setAmount((Double)values[2]);
		lastSixMonthsBills.add(sixMonthsBills);
	}
	}
	else if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Telephone Broadband")) {
	List<?> lastSixMontsBills = electricityBillsService.getLastSixMontsBillsOthers(electricityBillEntity.getAccountId(),electricityBillEntity.getTypeOfService(),electricityBillEntity.getBillDate(),"Units");
	for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator.hasNext();)
	{
		LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills();
		final Object[] values = (Object[]) iterator.next();
		map.put(new SimpleDateFormat("MMM").format((Date)values[0]), (Double)values[2]);
		sixMonthsBills.setMonth((Date)values[0]);
		sixMonthsBills.setUnits(Double.parseDouble((String)values[1]));
		sixMonthsBills.setAmount((Double)values[2]);
		lastSixMonthsBills.add(sixMonthsBills);
	}
	}
	
	else if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Telephone Broadband")) {
	List<?> lastSixMontsBills = electricityBillsService.getLastSixMontsBillsOthers(electricityBillEntity.getAccountId(),electricityBillEntity.getTypeOfService(),electricityBillEntity.getBillDate(),"Units");
	for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator.hasNext();)
	{
		LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills();
		final Object[] values = (Object[]) iterator.next();
		map.put(new SimpleDateFormat("MMM").format((Date)values[0]), (Double)values[2]);
		sixMonthsBills.setMonth((Date)values[0]);
		sixMonthsBills.setUnits(Double.parseDouble((String)values[1]));
		sixMonthsBills.setAmount((Double)values[2]);
		lastSixMonthsBills.add(sixMonthsBills);
	}
	}
	
	HashMap<String, Object> param = new HashMap<String, Object>();
	BillingPaymentsEntity billingPaymentsEntity = paymentService.getPamentDetals(electricityBillEntity.getAccountId(),electricityBillEntity.getFromDate());
	
	if(billingPaymentsEntity!=null)
	{
		param.put("paymentAmount", billingPaymentsEntity.getPaymentAmount());
		param.put("receiptNo", billingPaymentsEntity.getReceiptNo());
		param.put("receiptDate",billingPaymentsEntity.getReceiptDate());
		param.put("modeOfPay", billingPaymentsEntity.getPaymentMode());
	}
	
	JRBeanCollectionDataSource subReportDS = new JRBeanCollectionDataSource(
			bliList);
	JRBeanCollectionDataSource subReport1DS = new JRBeanCollectionDataSource(
			arrearsList);
	JRBeanCollectionDataSource subReport2DS = new JRBeanCollectionDataSource(
			lastSixMonthsBills);
	JRBeanCollectionDataSource subReport3DS = new JRBeanCollectionDataSource(
			slabDetailsList);
	JRBeanCollectionDataSource subReport4DS = new JRBeanCollectionDataSource(
			dgSlabDetailsList);
	JRBeanCollectionDataSource subReport5DS = new JRBeanCollectionDataSource(
			lastSixMonthsBills);

	param.put("subreport_datasource", subReportDS);
	param.put("subreport_datasource1", subReport1DS);
	param.put("subreport_datasource2", subReport2DS);
	param.put("subreport_datasource3", subReport3DS);
	param.put("subreport_datasource4", subReport4DS);
	param.put("subreport_datasource5", subReport5DS);

	param.put(
			"title",
			ResourceBundle.getBundle("utils").getString(
					"report.title"));
	param.put("companyAddress", ResourceBundle.getBundle("utils")
			.getString("report.address"));
	param.put("point1", ResourceBundle.getBundle("utils")
			.getString("report.point1"));
	param.put("point2", ResourceBundle.getBundle("utils")
			.getString("report.point2"));
	param.put("point3", ResourceBundle.getBundle("utils")
			.getString("report.point3"));
	param.put("point4", ResourceBundle.getBundle("utils")
			.getString("report.point4"));
	param.put("point5.1", ResourceBundle.getBundle("utils")
			.getString("report.point5.1"));
	param.put("point5.2", ResourceBundle.getBundle("utils")
			.getString("report.point5.2"));
	param.put("point5.3", ResourceBundle.getBundle("utils")
			.getString("report.point5.3"));
	param.put("point6", ResourceBundle.getBundle("utils")
			.getString("report.point6"));

	param.put("amountPayble", electricityBillEntity.getNetAmount());
	param.put("dueDate", electricityBillEntity.getBillDueDate());
	if(electricityBillEntity.getAccountObj().getPerson().getFirstName()!=null)
	param.put("name", electricityBillEntity.getAccountObj().getPerson().getFirstName() + " " +electricityBillEntity.getAccountObj().getPerson().getLastName());

/*	if(electricityBillEntity.getAccountObj().getPerson().getMiddleName()!=null)
	{
		param.put("middleName", electricityBillEntity.getAccountObj().getPerson().getMiddleName());
	}
	
	if(electricityBillEntity.getAccountObj().getPerson().getLastName()!=null)
	{
		param.put("lastName", param.put("lastName", electricityBillEntity.getAccountObj().getPerson().getLastName()));
	}
	*/
	param.put("address", address1);
	param.put("city", city);
	param.put("mobileNo", toAddressMobile);
	param.put("emailId", toAddressEmail);
	param.put("sanctionedUtility", Math.round(sanctionLoad) + " kW");
	String sanctionedDG = Math.round(sanctionLoadDG) + " kW";
	if (sanctionedDG.equalsIgnoreCase("0 kW")) {
		param.put("sanctionedDG", "NA");
	} else {
		param.put("sanctionedDG", Math.round(sanctionLoadDG)
				+ " kW");
	}
	String voltage = Math.round(voltageLevel) + " V";
	if (voltage.equalsIgnoreCase("0 V")) {
		param.put("voltageLevel", "NA");
	} else {
		param.put("voltageLevel", Math.round(voltageLevel) + " V");
	}

	param.put("meterType", meterType);
	param.put("connectionType", connectionType);
	param.put("connectionSecurity", connectionSecurity);
	param.put("pF", pfValue);
	param.put("surcharge", latePaymentAmount);
	param.put("billNo", electricityBillEntity.getBillNo());
	param.put("amountAfterDueDate",
			electricityBillEntity.getNetAmount()
					+ latePaymentAmount);
	param.put("billDate", electricityBillEntity.getBillDate());
	param.put(
			"billingPeriod",
			DateFormatUtils.format(
					(electricityBillEntity.getFromDate()),
					"dd MMM. yyyy")
					+ " To "
					+ DateFormatUtils.format(
							(electricityBillEntity.getBillDate()),
							"dd MMM. yyyy"));
	param.put("caNo", electricityBillEntity.getAccountObj()
			.getAccountNo());
	param.put("serviceType",
			electricityBillEntity.getTypeOfService());
	param.put("tariffCategory", tariffName);
	param.put("billBasis", billType);
	param.put("meterMake", meterMake);
	param.put("meterSrNo", meterSrNo);
	param.put("meterStatus", meterStatus);
	param.put("mf", meterConstant);
	param.put("energyType",
			electricityBillEntity.getTypeOfService() + "/"
					+ meterSrNo);
	param.put("mdi", mdiValue);
	param.put("presentReading", presentReading);
	param.put("previousReading", previousReading);
	param.put("previousBillDate",
			electricityBillEntity.getFromDate());
	param.put("presentBillDate",
			electricityBillEntity.getBillDate());
	param.put("units", uomValue);
	param.put("days", Math.round(numberOfDays));
	param.put("billAmount", electricityBillEntity.getBillAmount());
	param.put("arrearsAmount",
			electricityBillEntity.getArrearsAmount());
	String amountInWords = NumberToWord
			.convertNumberToWords(electricityBillEntity
					.getBillAmount().intValue());
	param.put("amountInWords", amountInWords + " Only");
	param.put("realPath", "reports/");
	param.put("panNo", "AACAG1252D");
	param.put("sTaxNo", "AACAG1252DSD001");
	JRBeanCollectionDataSource jre = new JRBeanCollectionDataSource(
			bliList);
	JasperPrint jasperPrint;
 			logger.info("---------- JRXL loading -----------");
/*   		  InputStream in = this.getClass().getClassLoader().getResourceAsStream("Bill.jrxml");
 			if(in != null){*/
 				logger.info("---------- JRXL compling -----------");
	 			JasperReport report;
				try {
				//	report = JasperCompileManager.compileReport(in);
					logger.info("---------- filling the report -----------");
		 			//jasperPrint=JasperFillManager.fillReport(report, param,jre);
					//this.getClass().getClassLoader().getResourceAsStream("reports/CAMBILL.jrxml");
					if(electricityBillEntity.getTypeOfService().equalsIgnoreCase("Electricity"))
					{
						param.put("energyType", electricityBillEntity.getTypeOfService() +"/" +meterSrNo );
						jasperPrint=JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/Bill.jasper"), param,jre);	
					}else if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Water") || electricityBillEntity.getTypeOfService().equalsIgnoreCase("Gas")) {
						param.put("energyType", electricityBillEntity.getTypeOfService() +"/" +meterSrNo );
						jasperPrint=JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/WGBill.jasper"), param,jre);
					}
					else if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Solid Waste")) {
						param.put("energyType", electricityBillEntity.getTypeOfService());
						jasperPrint=JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/SWBill.jasper"), param,jre);
					}
					else if (electricityBillEntity.getTypeOfService().equalsIgnoreCase("Others")) {
						param.put("tariffCategory", "Others");
						param.put("energyType", electricityBillEntity.getTypeOfService());
						jasperPrint=JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/OTBill.jasper"), param,jre);
					}
					else 
					{
						param.put("tariffCategory", "Telephone Broadband");
						param.put("energyType", electricityBillEntity.getTypeOfService());
						jasperPrint=JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/OTBill.jasper"), param,jre);
					}
					
					removeBlankPage(jasperPrint.getPages());
		 			byte[] pdfByteArray = JasperExportManager.exportReportToPdf(jasperPrint);
		 				 				
		 			Person person = electricityBillEntity.getAccountObj().getPerson();
		 			String personName = person.getFirstName();
		 			logger.info("toAddressEmail ------------- "+toAddressEmail);				 				
		 			/*try {
						mailCode(toAddressEmail,toAddressMobile,electricityBillEntity,pdfByteArray,personName);
					} catch (MessagingException e) {
						e.printStackTrace();
					}*/

	 				Notification notification = new Notification();
	 				 if(person.getUsers()!=null){
	 					String userName = (String) SessionData.getUserDetails().get("userID");
		 				int userId = usersService.getUserInstanceByLoginName(userName).getUrId();
		 				if(person.getUsers()!=null)
		 				{
		 					notification.setUser_id(person.getUsers().getUrId()+"");
			 				notification.setFromUser(userId+"");
			 				notification.setSubject("Your bills are generated");
			 				notification.setMessage("This notification is to inform you that your bill generated");
			 				notification.setRead_status(0);
			 				notification.setMsg_status("INBOX");
			 				notificationService.save(notification);
		 				}
	 				 }
	 				
				} catch (JRException e) {
					logger.info("----------------- JRException");
					e.printStackTrace();
				} 
           }
      }
}

	private void removeBlankPage(List<JRPrintPage> pages) {
	    for (Iterator<JRPrintPage> i = pages.iterator(); i.hasNext(); ) {
	        JRPrintPage page = i.next();
	        if (page.getElements().size() == 4)
	            i.remove();
	    }
	}	
	
	@SuppressWarnings("unused")
	@Override
	@Async
	public String allConsolidatedCAMBillMailTemplate(int elBillId, Locale locale){
		Map<String, Object> map = new LinkedHashMap<>();
		Address address = null;
		Contact contactMob  = null;
		Contact contactEmail = null;
		String str = "";
		ElectricityBillEntity electricityBillEntity = electricityBillsService.find(elBillId);
		if(electricityBillEntity!=null){
			Object[] addressDetailsList = camConsolidationService.getAddressDetailsForMail(electricityBillEntity.getAccountObj().getPerson().getPersonId());
			List<String> contactDetailsList = camConsolidationService.getContactDetailsForMail(electricityBillEntity.getAccountObj().getPerson().getPersonId());
			Property property = propertyService.find(electricityBillEntity.getAccountObj().getPropertyId());
			String address1 = property.getProperty_No()+","+property.getBlocks().getBlockName();
			String city = "Gurgaon";
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
						
			if(electricityBillEntity.getTypeOfService().equals("CAM")){					
				HashMap<String, Object> param = new HashMap<String, Object>();
				List<LastSixMonthsBills> lastSixMonthsBills = new ArrayList<>();
				String calculationBasis = camConsolidationService.getParameterValueBasedOnParameterName("Bill Basis",elBillId);
				List<?> lastSixMontsBills = electricityBillsService.getLastSixMonthsCAMBills(electricityBillEntity.getAccountId(),electricityBillEntity.getTypeOfService(),electricityBillEntity.getBillDate());
				for (Iterator<?> iterator = lastSixMontsBills.iterator(); iterator.hasNext();)
				{
					final Object[] values = (Object[]) iterator.next();
					LastSixMonthsBills sixMonthsBills = new LastSixMonthsBills();
					sixMonthsBills.setMonth((Date)values[0]);
					sixMonthsBills.setBillBases(calculationBasis);
					sixMonthsBills.setAmount((Double)values[1]);
					lastSixMonthsBills.add(sixMonthsBills);
				}
				
				BillingPaymentsEntity billingPaymentsEntity = paymentService.getPamentDetals(electricityBillEntity.getAccountId(),electricityBillEntity.getFromDate());
				if(billingPaymentsEntity!=null)
				{
					param.put("paymentAmount", billingPaymentsEntity.getPaymentAmount());
					param.put("receiptNo", billingPaymentsEntity.getReceiptNo());
					param.put("receiptDate",billingPaymentsEntity.getReceiptDate());
					param.put("modeOfPay", billingPaymentsEntity.getPaymentMode());
				}
				
				double parkingSlotsAmount = electricityBillsService.getLineItemAmountBasedOnTransactionCode(electricityBillEntity.getElBillId(),"CAM_PARKING_SLOT");
				double perSlotAmount = camConsolidationService.getParkingPerSlotAmount("CAM_PARKING_SLOT");
				double serviceTaxAmount = camConsolidationService.getParkingPerSlotAmount("CAM_SERVICE_TAX");
				double eCessTaxAmount = camConsolidationService.getParkingPerSlotAmount("CAM_ECESS");
				double sheCessTaxAmount = camConsolidationService.getParkingPerSlotAmount("CAM_SHECESS");
				double arrearsAmountCam = electricityBillEntity.getArrearsAmount();
				double serviceTax = Math.round(electricityBillsService.getLineItemAmountBasedOnTransactionCode(electricityBillEntity.getElBillId(),"CAM_SERVICE_TAX"));
				double educationAmount = electricityBillsService.getLineItemAmountBasedOnTransactionCode(electricityBillEntity.getElBillId(),"CAM_ECESS");
				double sEducationAmount = electricityBillsService.getLineItemAmountBasedOnTransactionCode(electricityBillEntity.getElBillId(),"CAM_SHECESS");
				double billAmount = electricityBillEntity.getBillAmount()-serviceTax-educationAmount-sEducationAmount-parkingSlotsAmount;
				double totalAmount = Math.round(electricityBillEntity.getBillAmount()+arrearsAmountCam);
				double totalCamRates = camConsolidationService.getTotalCamRates();
				
				   JRBeanCollectionDataSource subReportDS = new JRBeanCollectionDataSource(lastSixMonthsBills);
				   JRBeanCollectionDataSource subReport1DS = new JRBeanCollectionDataSource(lastSixMonthsBills);
				 
				    param.put("subreport_datasource", subReportDS);
				    param.put("subreport_datasource1", subReport1DS);
				    
					param.put("camPoint1", ResourceBundle.getBundle("utils").getString("report.CAM.point1"));
					param.put("camPoint2", ResourceBundle.getBundle("utils").getString("report.CAM.point2"));
					param.put("camPoint3", ResourceBundle.getBundle("utils").getString("report.CAM.point3")+" ("+calculationBasis+")");
					param.put("camPoint4", ResourceBundle.getBundle("utils").getString("report.CAM.point2")+" ("+perSlotAmount+"/- per slot)");
					param.put("camPoint5", ResourceBundle.getBundle("utils").getString("report.CAM.point5"));
					param.put("camPoint6", ResourceBundle.getBundle("utils").getString("report.CAM.point6")+" @ "+serviceTaxAmount+"%");
					param.put("camPoint7", ResourceBundle.getBundle("utils").getString("report.CAM.point7")+" @ "+eCessTaxAmount+"%");
					param.put("camPoint8", ResourceBundle.getBundle("utils").getString("report.CAM.point8")+" @ "+sheCessTaxAmount+"%");
					param.put("camPoint9", ResourceBundle.getBundle("utils").getString("report.CAM.point9"));
					param.put("camPoint10",ResourceBundle.getBundle("utils").getString("report.CAM.point10"));
					param.put("camPoint11",ResourceBundle.getBundle("utils").getString("report.CAM.point11"));
					param.put("camPoint12",ResourceBundle.getBundle("utils").getString("report.CAM.point12"));
					param.put("camPoint13",ResourceBundle.getBundle("utils").getString("report.CAM.point12"));
					
					Float latePaymentAmount=0.0f;
					double vat = 0;
					  
					param.put("camPointValue1", camConsolidationService.getAreaOfProperty(electricityBillEntity.getAccountObj().getPropertyId()));
					param.put("camPointValue2", calculationBasis);
					param.put("camPointValue3", totalCamRates);
					param.put("camPointValue4", parkingSlotsAmount);
					param.put("camPointValue5", Math.round(billAmount));
					param.put("camPointValue6", serviceTax);
					param.put("camPointValue7", educationAmount);
					param.put("camPointValue8", sEducationAmount);
					param.put("camPointValue9", vat);
					param.put("camPointValue10", arrearsAmountCam);
					param.put("camPointValue11", totalAmount);
					param.put("camPointValue12", NumberToWord.convertNumberToWords((int)totalAmount));
					
					param.put("title",ResourceBundle.getBundle("utils").getString("report.title"));
					param.put("companyAddress",ResourceBundle.getBundle("utils").getString("report.address"));
					param.put("point1",ResourceBundle.getBundle("utils").getString("report.point1"));
					param.put("point2",ResourceBundle.getBundle("utils").getString("report.point2"));
					param.put("point3",ResourceBundle.getBundle("utils").getString("report.point3"));
					param.put("point4",ResourceBundle.getBundle("utils").getString("report.point4"));
					param.put("point5.1",ResourceBundle.getBundle("utils").getString("report.point5.1"));
					param.put("point5.2",ResourceBundle.getBundle("utils").getString("report.point5.2"));
					param.put("point5.3",ResourceBundle.getBundle("utils").getString("report.point5.3"));
					param.put("amountPayble", electricityBillEntity.getNetAmount());
					param.put("dueDate", electricityBillEntity.getBillDueDate());
					param.put("name", electricityBillEntity.getAccountObj().getPerson().getFirstName());
					param.put("address", address1);
					param.put("city", city);
					param.put("mobileNo", toAddressMobile);
					param.put("emailId",toAddressEmail);
					param.put("surcharge", latePaymentAmount);
					param.put("billNo", electricityBillEntity.getBillNo());
					param.put("amountAfterDueDate", electricityBillEntity.getNetAmount()+latePaymentAmount);
					param.put("billDate", electricityBillEntity.getBillDate());
					param.put("billingPeriod", DateFormatUtils.format((electricityBillEntity.getFromDate()), "MMM-yyyy")+" To "+DateFormatUtils.format((electricityBillEntity.getBillDate()), "MMM-yyyy"));
					param.put("caNo", electricityBillEntity.getAccountObj().getAccountNo());
					param.put("serviceType",electricityBillEntity.getTypeOfService());
					param.put("billBasis",calculationBasis);
					
					List<CamReportSettingsEntity> camReportSetting = camReportSettingsService.findAllData();
					String configName = "Show Particulars";
					String status = "Active";
					String showParticulars = electricityBillsService.getBillingConfigValue(configName,status);
					if(showParticulars.equalsIgnoreCase("Yes"))
					{
						param.put("heading1", ResourceBundle.getBundle("utils").getString("report.CAM.heading1"));
						param.put("heading2", ResourceBundle.getBundle("utils").getString("report.CAM.heading2"));
						param.put("heading3", ResourceBundle.getBundle("utils").getString("report.CAM.heading3"));
						param.put("heading4", ResourceBundle.getBundle("utils").getString("report.CAM.heading4"));
						
						param.put("report.CAM.mcPoint1", ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint1"));
						param.put("report.CAM.mcPoint2", ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint2"));
						param.put("report.CAM.mcPoint3", ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint3"));
						param.put("report.CAM.mcPoint4", ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint4"));
						param.put("report.CAM.mcPoint5", ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint5"));
						param.put("report.CAM.mcPoint6", ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint6"));
						param.put("report.CAM.mcPoint7", ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint7"));
						param.put("report.CAM.mcPoint8", ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint8"));
						param.put("report.CAM.mcPoint9", ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint9"));
						param.put("report.CAM.mcPoint10",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint10"));
						param.put("report.CAM.mcPoint11",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint11"));
						param.put("report.CAM.mcPoint12",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint12"));
						param.put("report.CAM.mcPoint13",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint13"));
						param.put("report.CAM.mcPoint14",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint14"));
						param.put("report.CAM.mcPoint15",ResourceBundle.getBundle("utils").getString("report.CAM.mcPoint15"));

						param.put("report.CAM.ucPoint1", ResourceBundle.getBundle("utils").getString("report.CAM.ucPoint1"));
						param.put("report.CAM.ucPoint2", ResourceBundle.getBundle("utils").getString("report.CAM.ucPoint2"));
						param.put("report.CAM.ucPoint3", ResourceBundle.getBundle("utils").getString("report.CAM.ucPoint3"));
						param.put("report.CAM.ucPoint4", ResourceBundle.getBundle("utils").getString("report.CAM.ucPoint4"));
						
						param.put("report.CAM.ocPoint1", ResourceBundle.getBundle("utils").getString("report.CAM.ocPoint1"));
						param.put("report.CAM.ocPoint2", ResourceBundle.getBundle("utils").getString("report.CAM.ocPoint2"));
						param.put("report.CAM.ocPoint3", ResourceBundle.getBundle("utils").getString("report.CAM.ocPoint3"));
						param.put("report.CAM.ocPoint4", ResourceBundle.getBundle("utils").getString("report.CAM.ocPoint4"));
						param.put("report.CAM.ocPoint5", ResourceBundle.getBundle("utils").getString("report.CAM.ocPoint5"));
						
					}
					
					List<ElectricityBillEntity> electricityBillEntities = new ArrayList<>();
					electricityBillEntities.add(electricityBillEntity);
				
			 		  JRBeanCollectionDataSource jre = new JRBeanCollectionDataSource(electricityBillEntities);
			 		  JasperPrint jasperPrint;
			 		  logger.info("---------- JRXL compling ----------");
				 	  JasperReport report;
					  try {
							logger.info("---------- filling the report -----------");
							jasperPrint=JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("reports/CAMBILL.jasper"), param,jre);
							removeBlankPage(jasperPrint.getPages());
					 		byte[] pdfByteArray = JasperExportManager.exportReportToPdf(jasperPrint);
					 			
				 			Person person = electricityBillEntity.getAccountObj().getPerson();
				 			String personName = person.getFirstName();
				 							 				
				 			/*try {
								mailCode(toAddressEmail,toAddressMobile,electricityBillEntity,pdfByteArray,personName);
							} catch (MessagingException e) {
								e.printStackTrace();
							}
*/
				 			Notification notification = new Notification();

				 			String userName = (String) SessionData.getUserDetails().get("userID");
				 			int userId = usersService.getUserInstanceByLoginName(userName).getUrId();
                            if(person.getUsers()!=null){
                                notification.setUser_id(person.getUsers().getUrId()+"");
    				 			notification.setFromUser(userId+"");
    				 			notification.setSubject("Your bills are generated");
    				 			notification.setMessage("This notification is to inform you that your bill generated");
    				 			notification.setRead_status(0);
    				 			notification.setMsg_status("INBOX");
    				 			notificationService.save(notification);
                              }
					 			
							} catch (JRException e) {
								logger.info("----------------- JRException");
								e.printStackTrace();
							} 
			 	}			
			
		}
		map.put("string", str);
		 return str;
	}
	
/*	public void mailCode(String toAddressEmail,String toAddressMobile,ElectricityBillEntity electricityBillEntity,byte[] pdfByteArray,String personName) throws MessagingException{
		
		SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
		EmailCredentialsDetailsBean emailCredentialsDetailsBean = null;
		try {
			emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Property property = propertyService.find(electricityBillEntity.getAccountObj().getPropertyId());
		String fileName ="";
		if(property!=null)
		{
		 fileName = electricityBillEntity.getTypeOfService()+ "_" + property.getProperty_No()+".pdf";
		}
		emailCredentialsDetailsBean.setMessageContent("Please find the attached bill, Thank You");
		emailCredentialsDetailsBean.setToAddressEmail(toAddressEmail);
		
		new Thread(new SendMailConsolidationBills(emailCredentialsDetailsBean,pdfByteArray,fileName)).start();
		
		
		String messagePerson = "Grand Arch RWA bill desk.Your bill's calculated and send to your registered email,check once."
				+ "If you have any queries,contact to our bill desk service";

		smsCredentialsDetailsBean.setNumber(toAddressMobile);
		smsCredentialsDetailsBean.setUserName(personName);
		smsCredentialsDetailsBean.setMessage(messagePerson);

		new Thread(new SendBillInfoThroughSMS(smsCredentialsDetailsBean)).start();
		
	}
*/
	@Override
	@Async
	public void consolidateSingleBillSendMail(Account account,String billMonth,Locale locale) {
		EmailCredentialsDetailsBean emailCredentialsDetailsBean = null;
		try {
			emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
		
		Person person = account.getPerson();
		String personName = person.getFirstName();

		List<String> contactDetailsList = camConsolidationService.getContactDetailsForMail(person.getPersonId());

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

		//String personId=request.getParameter("personId");	
		//simplePSVM.getAll();	
		String consoldatedBill = consolidatedBillMailTemplate(account.getAccountId(),billMonth,locale);	

		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("D://Bills/html/consolidatedBill.html"));
			bw.write(consoldatedBill);
			bw.close();
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D://Bills/pdf/consolidatedBill.pdf"));
			document.open();
			XMLWorkerHelper.getInstance().parseXHtml(writer, document,new FileInputStream("D://Bills/html/consolidatedBill.html"));
			document.close();
			emailCredentialsDetailsBean.setToAddressEmail(toAddressEmail);
			emailCredentialsDetailsBean.setMessageContent("Please find the attached bill, Thank You");

			new Thread(new SendMailConsolidatedBill(emailCredentialsDetailsBean)).start();
		}catch(Exception e){
			
		}
		
		String messagePerson = "Grand Arch RWA bill desk.Your bill's calculated and send to your registered email,check once."
				+"If you have any queries,contact to our bill desk service";
		
		smsCredentialsDetailsBean.setNumber(toAddressMobile);
		smsCredentialsDetailsBean.setUserName(personName);
		smsCredentialsDetailsBean.setMessage(messagePerson);

		new Thread(new SendBillInfoThroughSMS(smsCredentialsDetailsBean)).start();

		Notification notification = new Notification();

		String userName = (String) SessionData.getUserDetails().get("userID");
		int userId = usersService.getUserInstanceByLoginName(userName).getUrId();

		notification.setUser_id(person.getPersonId()+"");
		notification.setFromUser(userId+"");
		notification.setSubject("Your bills are generated");
		notification.setMessage("This notification is to inform you that your bill generated");
		notification.setRead_status(0);
		notification.setMsg_status("INBOX");

		notificationService.save(notification);
	}
	
}
