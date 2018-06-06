package com.bcits.bfm.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.bcits.bfm.controller.BillController;
import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.Address;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.ELTariffMaster;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.model.GasTariffMaster;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.model.SolidWasteTariffMaster;
import com.bcits.bfm.model.WTTariffMaster;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.accountsManagement.ElectricityLedgerService;
import com.bcits.bfm.service.customerOccupancyManagement.AddressService;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillLineItemService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillParameterService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.electricityMetersManagement.ElectricityMeterParametersService;
import com.bcits.bfm.service.electricityMetersManagement.ElectricityMetersService;
import com.bcits.bfm.service.facilityManagement.BillingParameterMasterService;
import com.bcits.bfm.service.facilityManagement.ServiceParameterMasterService;
import com.bcits.bfm.service.gasTariffManagment.GasTariffMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceParameterService;
import com.bcits.bfm.service.solidWasteTariffManagment.SolidWasteTariffMasterService;
import com.bcits.bfm.service.tariffManagement.ELTariffMasterService;
import com.bcits.bfm.service.waterTariffManagement.WTTariffMasterService;

@Component 
public class BillTemplates
{

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
	
	
	public String consolidatedBillMailTemplate(@RequestParam("accountId") int accountId, @RequestParam("billMonthSql") String billMonth){

	
		System.out.println(">>>>>>>>>>>>>>"+accountId+"-----------"+billMonth);
		
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


		List<ElectricityBillEntity> getfromAndToDates = electricityBillsService.executeSimpleQuery("select e from ElectricityBillEntity e where e.accountId="+accountId+" and ROWNUM<=2 order by e.elBillId desc");

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

		double eleAmt = 0.0;
		if(eleBillList.size()>0){
			dueDate = eleBillList.get(0).getBillDueDate();
			for(ElectricityBillEntity billEntity : eleBillList)
				eleAmt += billEntity.getBillAmount();
		}

		double waterAmt = 0.0;
		if(waterBillList.size()>0){
			dueDate = waterBillList.get(0).getBillDueDate();
			for(ElectricityBillEntity billEntity : waterBillList)
				waterAmt += billEntity.getBillAmount();
		}

		double gasAmt = 0.0;
		if(gasBillList.size()>0){
			dueDate = gasBillList.get(0).getBillDueDate();
			for(ElectricityBillEntity billEntity : gasBillList)
				gasAmt += billEntity.getBillAmount();
		}	

		double swAmt = 0.0;
		if(swBillList.size()>0){
			dueDate = swBillList.get(0).getBillDueDate();
			for(ElectricityBillEntity billEntity : swBillList)
				swAmt += billEntity.getBillAmount();
		}

		double teleAmt = 0.0;
		if(teleBillList.size()>0){
			dueDate = teleBillList.get(0).getBillDueDate();
			for(ElectricityBillEntity billEntity : teleBillList)
				teleAmt += billEntity.getBillAmount();
		}

		double otherAmt = 0.0;
		if(othersBillList.size()>0){
			dueDate = othersBillList.get(0).getBillDueDate();
			for(ElectricityBillEntity billEntity : othersBillList)
				otherAmt += billEntity.getBillAmount();
		}

		double camAmt = 0.0;
		if(camBillList.size()>0){
			dueDate = camBillList.get(0).getBillDueDate();
			for(ElectricityBillEntity billEntity : camBillList)
				camAmt += billEntity.getBillAmount();
		}


		//ElectricityBillEntity electricityBillEntity = electricityBillsService.find(elBillId);
		String addrQuery = "select obj from Address obj where obj.personId="+account.getPerson().getPersonId()
				+" and obj.addressPrimary='Yes'";
		Address address = addressService.getSingleResult(addrQuery);

		String mobileQuery = "select obj from Contact obj where obj.personId="+account.getPerson().getPersonId()
				+" and obj.contactPrimary='Yes' and obj.contactType='Mobile'";
		Contact contactMob = contactService.getSingleResult(mobileQuery);
		String emailQuery = "select obj from Contact obj where obj.personId="+account.getPerson().getPersonId()
				+" and obj.contactPrimary='Yes' and obj.contactType='Email'";
		Contact contactEmail = contactService.getSingleResult(emailQuery);


		double balance = 0.0;
		double amount = 0.0;
		List<ElectricityLedgerEntity> ledger = electricityLedgerService.executeSimpleQuery("select obj from ElectricityLedgerEntity obj where obj.accountId="+accountId+" and obj.postType='PAYMENT' and obj.ledgerDate=TO_DATE('"+billFromDate+"','YYYY-MM-DD')");
		if(ledger!=null && ledger.size()>0){
			for(int i=0;i<ledger.size();i++){
				balance+=ledger.get(i).getBalance();
				amount+=ledger.get(i).getAmount();
			}
		}
		double adjustment =0.0; 
		List<ElectricityLedgerEntity> adjustments = electricityLedgerService.executeSimpleQuery("select obj from ElectricityLedgerEntity obj where obj.accountId="+accountId+" and obj.postType='ADJUSTMENT' and obj.ledgerDate=TO_DATE('"+billFromDate+"','YYYY-MM-DD')");
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
		if(dueDate!=null)
			dueDateStr = new SimpleDateFormat("dd, MMMM, YYYY").format(dueDate);

		String str= ""
				+"<div id='myTab'>"
				+"<table id='tabs' style='width: 100%; background: white; border: 2px solid black; border-radius: 34px; padding: 21px 23px;'>"
				+"<tr>"
				+"	<td style='padding: 5.5em; border: 1px solid #808080;' width='50%' colspan='2'><img id='eye' src='http://www.newdelhiprime.com/images/20130322_032827_1352285522447-ireo-logo.gif' height='100px' width='300px' /></td>"
				+"	<td style='padding: 5.5em; border: 1px solid #808080;vertical-align:middle' width='49%'>Orchid Centre, DLF Golf Course Rd, IILM Institute, Sector 53, Gurgaon, Haryana, 0124 475 4000 </td>"
				+"</tr>"
				+"<tr>"
				+"	<td style='padding: 5.5em; border: 1px solid #808080;background: black; color: white; font-weight: bolder;' colspan='3'>Customer Details</td>"
				+"</tr>"
				+"<tr>"
				+"	<td style='padding: 5.5em; border: 1px solid #808080;padding-left: 25px' width='49%' ><b> <h4 id='name'>"+account.getPerson().getFirstName()+" "+account.getPerson().getLastName()+"</h4> </b> "
				+ "		<span id='addr'>"+address.getAddress1() +"</span>"
				+"		<span id='email'>"+contactMob.getContactContent()+"</span>"
				+"		<span id='mobile'>"+contactEmail.getContactContent()+"</span></td>"
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
				+"				<td style='padding: 5.5em; border: 1px solid #808080;'>"+balance+"</td>"
				+"				<td style='padding: 5.5em; border: 1px solid #808080;'>"+(eleAmt+waterAmt+gasAmt+swAmt+otherAmt+teleAmt)+"</td>"
				+"				<td style='padding: 5.5em; border: 1px solid #808080;'>"+(-(amount))+"</td>"
				+"				<td style='padding: 5.5em; border: 1px solid #808080;'>"+adjustment+"</td>"
				+"				<td style='padding: 5.5em; border: 1px solid #808080;'>"+(balance+(eleAmt+waterAmt+gasAmt+swAmt+otherAmt+teleAmt)+amount-adjustment)+"</td>"
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
				+"				<td width='30%' width='30%' style='padding: 5.5em; border: 1px solid #808080;'>"
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
				+"				<td width='40%' rowspan='3' id='tdcontainer' style='vertical-align: middle;'>"
				+"					<div id='syed' style='min-width: 300px; height: 400px; max-width: 300px; margin: 0 auto; vertical-align: middle;'>"
				+"					</div> "
				+"				</td>"
				+"				<td width='30%' width='30%' style='padding: 5.5em; border: 1px solid #808080;'>"
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
				+"				<td width='30%' width='30%' style='padding: 5.5em; border: 1px solid #808080;'>"
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

				+"				<td width='30%' width='30%' style='padding: 5.5em; border: 1px solid #808080;'>"
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
				+"				<td width='30%' width='30%' style='padding: 5.5em; border: 1px solid #808080;'>"
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

				+"				<td width='30%' width='30%' style='padding: 5.5em; border: 1px solid #808080;'>"
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
				+"			</tr>"
				+"		</table>"
				+"	</td>"
				+"</tr>"
				+"</table>"
				+"</div>";

		return str;

	}
}
