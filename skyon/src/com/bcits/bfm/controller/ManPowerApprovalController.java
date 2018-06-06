package com.bcits.bfm.controller;


import java.sql.Blob;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Groups;
import com.bcits.bfm.model.ManPowerApproval;
import com.bcits.bfm.model.Role;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.userManagement.ManPowerApprovalService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.util.EmailCredentialsDetailsBean;
import com.bcits.bfm.util.RandomPasswordGenerator;
import com.bcits.bfm.util.SendMailForStaff;
import com.bcits.bfm.util.SendSMSForStatus;
import com.bcits.bfm.util.SmsCredentialsDetailsBean;


@Controller
public class ManPowerApprovalController {

	
	@Value("${Unique.conf.serverIp}")
	private String hostIp;
	
	@Value("${Unique.conf.Serverport}")
	private String portNo;
	
	@Value("${Unique.conf.AppVersion}")
	private String version;
	
	
	@Value("${Unique.conf.DeployVersion}")
	private String DeployVersion;
	
	@Autowired
	private UsersService usersService;
	@Autowired
	private ManPowerApprovalService manPowerApprovalService;
	
	@Autowired
	private LdapBusinessModel ldapBusinessModel;
	

	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "/manpower/createapprovalDetails/{urId}", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody Object createDepartment(@ModelAttribute("manPowerApproval") ManPowerApproval manPowerApproval,BindingResult bindingResult,@PathVariable int urId,String roles,String vendorName,String groups, ModelMap model,HttpServletRequest request, SessionStatus sessionStatus,final Locale locale) throws InterruptedException, ParseException 
	{
		int userid=urId;

		HttpSession session=request.getSession(false);
		String userId=(String) session.getAttribute("userId");
		
		manPowerApproval.setApprovedBy(userId);
		

		// DateFormat dateFormat = new SimpleDateFormat("yyyy/ MM/dd");
	


	       Date date = new Date();

		manPowerApproval.setApproveddate(date);
		manPowerApproval.setUserId1(urId);
		manPowerApproval.setStatus(manPowerApproval.getStatus());
		
		manPowerApprovalService.save(manPowerApproval);
		int personid=manPowerApprovalService.getpersonId(urId);
		int reqInLevel=manPowerApprovalService.getreqInLevel(personid);
		int level=manPowerApprovalService.getLevel(personid);
		if(manPowerApproval.getStatus().equalsIgnoreCase("Approve")){
		if(reqInLevel==level){
			
			manPowerApprovalService.updateUserStatus(urId,"Active");

			Users user = usersService.find(urId);
			String message = "";
			
				String emailId="";
				String mobileNo="";
				String gateWayUserName=ResourceBundle.getBundle("application").getString("SMS.users.username");
				String gateWayPassword=ResourceBundle.getBundle("application").getString("SMS.users.password");
				

				Users users = usersService.getUserInstanceBasedOnPersonId(user.getPersonId());
				Set<Contact> contact=users.getPerson().getContacts();
				Blob image=users.getPerson().getPersonImages();		
				
				String personPassword = RandomPasswordGenerator.generateRandomPassword();

				
				for (Iterator iterator = contact.iterator(); iterator.hasNext();) {
					Contact contact2 = (Contact) iterator.next();
					if((contact2.getContactType().equalsIgnoreCase("Email")) && (contact2.getContactPrimary().equalsIgnoreCase("Yes"))){
						emailId=contact2.getContactContent();
						

						EmailCredentialsDetailsBean emailCredentialsDetailsBean=null;
						  try {
						  emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
						} catch (Exception e) {
							e.printStackTrace();
						}
						  
						 String messageContent="<html>"
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
									+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">SKYON RWA ADMINISTRATION DEPARTMENT.</h2> <br /> Dear "+ user.getPerson().getFirstName() + ", Welcome to Skyon RWA <br/> <br/> "
									+ "Please use following UserName and Password to login. <br/> <br/>"
									+ "<table class=\"hovertable\" >" + "<tr>"
									+ "<td colspan='2'>Your Account Details are</td>"
									+ "</tr>" + "<tr>" + "<td> User Name :</td>"
									+ "<td>" + emailId + "</td>"
									+ "</tr>" + "<tr>" + "<td> Password:</td>" + "<td>"
									+ personPassword + "</td>" + "</tr>" + "</table>"
									+ "<br/><br/>"
									+ "<a href=http://www.icommunity.co.in/bfm"
									+ ">click to login</a></body></html>"
									+ "<br/><br/>"
									+ "<br/>Thanks,<br/>"
									+ "Skyon RWA Administration Services. <br/> <br/>"
									+"</body></html>";	

						     emailCredentialsDetailsBean.setToAddressEmail(emailId);
							 emailCredentialsDetailsBean.setMessageContent(messageContent);
						
							new Thread(new SendMailForStaff(emailCredentialsDetailsBean)).start();
						
						
					}
					if((contact2.getContactType().equalsIgnoreCase("Mobile")) && (contact2.getContactPrimary().equalsIgnoreCase("Yes"))){

						mobileNo=contact2.getContactContent();
						
						  String userMessage = "Dear "+user.getPerson().getFirstName()+", Your account has been "+"Activated"+". Your account details are username : "+emailId+" & password : "+personPassword+" - Skyon RWA  Administration services.";	

							
						  SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
						   
						    smsCredentialsDetailsBean.setNumber(mobileNo);
							smsCredentialsDetailsBean.setUserName(user.getPerson().getFirstName());
							smsCredentialsDetailsBean.setMessage(userMessage);
							
							new Thread(new SendSMSForStatus(smsCredentialsDetailsBean)).start();
						
						
					}
				}
				Set<String> rolesList = new HashSet<String>();
				Set<String> groupsList = new HashSet<String>();
				
				Set<Role> rolesSet = user.getRoles();
				
				for (Role role : rolesSet) {
					rolesList.add(role.getRlName());
				}
				
				Set<Groups> groupSet = user.getGroups();
				for (Groups group : groupSet) {
					groupsList.add(group.getGr_name());
				}
			ldapBusinessModel.addUsers(user.getUrLoginName(), personPassword, emailId, mobileNo,rolesList, groupsList);
		}else{
			reqInLevel=reqInLevel+1;
			manPowerApprovalService.updateReqInLevel(personid,reqInLevel);
			
		}
		}else{
			manPowerApprovalService.updateUserStatus(urId,"Rejected");
			manPowerApprovalService.updateReqInLevel(personid,1);
			manPowerApprovalService.deleteApprovalData(urId);
		}
		sessionStatus.setComplete();
		return manPowerApproval;   	
	}
	@RequestMapping(value = "/users/getApprovalDetails/{urId}", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody List<?> templateItemsRead(@PathVariable int urId) {

		return manPowerApprovalService.readAllUserDetails(urId);
	}
	
}
