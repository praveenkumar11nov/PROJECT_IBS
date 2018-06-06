package com.bcits.bfm.controller;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;





import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.mail.MessagingException;
import javax.servlet.ServletOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.bcits.bfm.model.PrePaidMeters;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.model.ServiceParametersEntity;
import com.bcits.bfm.model.SolidWasteTariffMaster;
import com.bcits.bfm.model.WTTariffMaster;
import com.bcits.bfm.service.MailConfigService;
import com.bcits.bfm.service.NotificationService;
import com.bcits.bfm.service.PrePaidMeterService;
import com.bcits.bfm.service.PrepaidBillService;
import com.bcits.bfm.service.SendMailBillService;
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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



@Controller

public class SendMailPrepaidBillController {
	
	Logger logger=LoggerFactory.getLogger(SendMailPrepaidBillController.class);
	
	
	@Autowired
	private SendMailBillService sendMailBillService;
	
	@Autowired
	private NotificationService notificationService;
	
	
	@Autowired
	private PrePaidMeterService prePaidMeterService;

	@Autowired
    private PrepaidBillService prepaidBillService;
	
	@Autowired
	private MailConfigService mailConfigService;
	
	@RequestMapping(value = "/prepaidBillGeneration/sendAllBillsMonthWise", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String sendAllBillsMonthWiseAndServiceWise(HttpServletRequest request,HttpServletResponse response) throws ParseException, java.text.ParseException
	{
		System.out.println("entered into controller");
	   java.util.Date month = new SimpleDateFormat("MMM yyyy").parse(request.getParameter("selectedMonth"));
	   Calendar cal = Calendar.getInstance();
	   cal.setTime(month);
	   int month1 = cal.get(Calendar.MONTH);
	   int actualmonth = month1 + 1;
       int year = cal.get(Calendar.YEAR);
      int blockId=Integer.parseInt(request.getParameter("blockSendId"));
      String propertyId=request.getParameter("propertyId");
      logger.info("Block ID "+blockId +" propId "+propertyId +" month "+month);
       //String serviceType = request.getParameter("serviceType");
       try{
    	   int count;
    	   synchronized (this) {
    		   count =fetchBillsData(actualmonth,year,propertyId);
    	   }
    	   logger.info(count+ "Mails send Successfully");
    	   return "success";
       }catch(Exception e){
    	   e.printStackTrace();
    	   return "fail";
       }
	}
	
	
	
	@Async
	private int fetchBillsData(int actualmonth,int year, String propertyId2){
		int count=0;
		String cama=",";
		String[] tranId=propertyId2.split(cama);
		for(int i=0;i<tranId.length;i++){
			int propId=Integer.parseInt(tranId[i]);
		List<?> billsDataList = sendMailBillService.fetchBillsDataBasedOnMonthAndServiceType(actualmonth,year,propId);
		System.out.println("Size of list++++++++++++"+billsDataList.size());
		for (Iterator<?> billIterator = billsDataList.iterator(); billIterator.hasNext();) {
			String billNo = (String) billIterator.next();
			
			Blob blob = sendMailBillService.getBlog(billNo);
			if(blob != null){
				
				//int propertyId = Integer.parseInt(values[0]+"");	
				int personId=prePaidMeterService.getPersonId(propId); 
				List<String> contactDetailsList = sendMailBillService.getContactDetailsForMail(personId);
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
				String tenantStatus= sendMailBillService.getTenantStatusByPropertyId1(propId);
				if(tenantStatus!= null && tenantStatus.equalsIgnoreCase("Active"))
				{
					tenantContactDetailsList= sendMailBillService.getContactDetailsByPersonId1(propId);
					tenantName=sendMailBillService.getTenantFirstNameByPropertyId1(propId);
				for (Iterator<?> iterator = tenantContactDetailsList.iterator(); iterator.hasNext();) {
					final Object[] values1 = (Object[]) iterator.next();
					if(((String)values1[1]).equals("Email")){
						tenantAddressEmail = (String)values1[2];
					}else{
						tenantAddressMobile = (String)values1[2];
					}				
					}
				
				
				}
				Object[] personNameData = sendMailBillService.getPersonNameBasedOnPersonId1(personId);
				String personName = "";
				if(personNameData[0]!=null){
					personName = personName.concat((String)personNameData[0]);
				}
				 
				 if(personNameData[1]!= null){
				 	personName = personName.concat(" ");
					personName = personName.concat((String)personNameData[1]);
				 }
				try {
					int c1;			
					String status ="";
					c1=mailCode(toAddressEmail,toAddressMobile,propId,blob,personName);
					count=count+c1;
					status="Yes";
					if(tenantStatus!= null && tenantStatus.equalsIgnoreCase("Active"))
					{
						c1=mailCode(tenantAddressEmail,tenantAddressMobile,propId,blob,tenantName);
						count=count+c1;
						status =status +", T";
					}
					if(count!=0){						
					prepaidBillService.updateMailSent_Status(billNo,status);
					}
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				try{
				Notification notification = new Notification();

				String userName = (String) SessionData.getUserDetails().get("userID");
				int userId = sendMailBillService.getUserInstanceByLoginName1(userName).getUrId();
				int toUsersId = sendMailBillService.getUserIdBasedOnPersonId1(personId);
			    if(toUsersId!=0){
			        notification.setUser_id(toUsersId+"");
					notification.setFromUser(userId+"");
					notification.setSubject("Your bills are generated");
					notification.setMessage("This notification is to inform you that your bill generated");
					notification.setRead_status(0);
					notification.setMsg_status("INBOX");
					notificationService.save(notification);
			      }
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				System.out.println("No bill for sending to mail and sms");
			}
		}
	}
		return count;
	}

	
	
	
	
	

	@Async
	public int mailCode(String toAddressEmail,String toAddressMobile,int propertyId,Blob pdfByteArray,String personName) throws MessagingException{
		
		SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
		EmailCredentialsDetailsBean emailCredentialsDetailsBean = null;
		try {
			emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object propertyData[] = sendMailBillService.getPropertyDataBasedOnPropertyId1(propertyId);
		String fileName ="";
		if(propertyData[0]!=null){
			fileName = (String)propertyData[0]+".pdf";
		}
		
		
		String messagecontent =null;
		
		String serviceType="Prepaid Bill";
		MailMaster mailMaster=mailConfigService.getMailMasterByService(serviceType);
		String mailMessage = mailMaster.getMailMessage();	
		emailCredentialsDetailsBean.setMailSubject(mailMaster.getMailSubject());
		int count=0;
		
		if(serviceType.equalsIgnoreCase("Prepaid Bill")){
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
			
		
		emailCredentialsDetailsBean.setMessageContent(messagecontent);		
		emailCredentialsDetailsBean.setToAddressEmail(toAddressEmail);
		new Thread(new SendMailConsolidationBills(emailCredentialsDetailsBean,pdfByteArray,fileName)).start();
		count++;
		}
		/*String messagePerson = "Skyon Resident Welfare Association bill desk.Your bill's calculated and send to your registered email,check once.If you have any queries,contact to our bill desk service";
		//String messagePerson = "Please find enclosed herewith latest electricity bill for your apartment. We apologies for the delay in generation of your electricity bill as we have recently upgraded our billing software for enhanced functionality and for smoother operations of our portal. Thank you for your cooperation ";
		smsCredentialsDetailsBean.setNumber(toAddressMobile);
		smsCredentialsDetailsBean.setUserName(personName);
		smsCredentialsDetailsBean.setMessage(messagePerson);
		new Thread(new SendBillInfoThroughSMS(smsCredentialsDetailsBean)).start();*/
	return count;
	}
	
}
