package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.model.UserLog;
import com.bcits.bfm.service.userManagement.UserLogSerivce;
import com.bcits.bfm.serviceImpl.facilityManagement.ParkingSlotsAllotmentServiceImpl;
import com.bcits.bfm.util.EmailCredentialsDetailsBean;
import com.bcits.bfm.util.RandomPasswordGenerator;
import com.bcits.bfm.util.SendMailForUserDetails;
import com.bcits.bfm.util.SendPasswordRecovery;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.novell.ldap.LDAPException;

/**
 * @author Manjunath Kotagi
 *
 */
@Controller
public class LoginController {
	@Autowired
	private LdapBusinessModel ldapBusinessModel;
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UserLogSerivce userlogService;

	@Value("${Unique.conf.serverIp}")
	private String hostIp;
	@Value("${Unique.conf.Serverport}")
	private String portNo;
	@Value("${Unique.conf.AppVersion}")
	private String version;
	
	@Value("${Unique.conf.DeployVersion}")
	private String DeployVersion;
	
	@Value("${Unique.server.link.bfm}")
	private String bfmUrl;

	private static final Log logger = LogFactory.getLog(ParkingSlotsAllotmentServiceImpl.class);

	/* ------------------- Manage Login -----------------------*/	 
	
	/**
	 * Displaying Login Page 
	 * 
	 * @param model : none
	 * @param request : none
	 * @param response : none
	 * @return Login Page
	 * @throws IOException
	 */
	@RequestMapping(value = { "/", "/login" }, method ={ RequestMethod.POST,RequestMethod.GET})
	public String loginpage(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		model.addAttribute("DeployVersion", DeployVersion);
		
		String referrer = request.getHeader("Referer");
	    if(referrer!=null){
	    	if(!referrer.contains("login")){
	    		request.getSession().setAttribute("url_prior_login", referrer);
	    	}
	    }
		
		/*String userId = (String) SessionData.getUserDetails().get("userID");	
		if (userId.equals("anonymousUser")) {
			logger.info("Accessing Login Page");
			return "login";
		} else {			
			logger.info("Session Already Exists,Redirecting to Home Page");
			
			return "redirect:/home";
		}*/
	    
	    return "login";		
	}



	/**
	 * Displaying Login Page incase of Session not exists
	 * 
	 * @param model : none
	 * @param request : none
	 * @param response : none
	 * @return Login Page
	 * @throws IOException
	 */
	@RequestMapping(value = "/logininvalid")
	public String loginInvalid(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		model.addAttribute("msg","Session Already Exists,Please Logout Previous User");
		return "sessiontimeout";
	}
	
	
	@RequestMapping(value = "/loginExpire")
	public String loginExpire(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		model.addAttribute("msg","Session Timeout Please Login Again");
		HttpSession session = request.getSession(true);	
		session.removeAttribute("url_prior_login");
		return "errorpage404";
	}

	/**
	 * Display User Image
	 * 
	 * @param response : none
	 * @param request : none
	 */
	@RequestMapping("/cookie/getuserimage/")
	public void getImageFromCookie(HttpServletResponse response,
			HttpServletRequest request) {
		Cookie[] cookie = null;
		cookie = request.getCookies();
		
		response.setContentType("image/jpeg");
		byte bt[] = null;
		OutputStream ot = null;
		try {
			ot = response.getOutputStream();
			cookie[0].setMaxAge(60 * 60 * 24 * 365);
			bt = ldapBusinessModel.getImage(cookie[0].getValue());
			logger.info("Accessing Image From Cookie");
		} catch (Exception e) {
			try {
				bt = ldapBusinessModel.getImage("bcitsadmin");
				logger.info("Accessing Image From Cookie");
			} catch (Exception e1) {
				logger.info("Unable To Fetch Image From LDAP");
			}
		} finally {
			if (ot != null) {
				try {
					ot.write(bt);
					ot.close();
				} catch (IOException e) {
					e.getMessage();
				}
			}
		}
	}

	/**
	 * Finding User From Cookie
	 * 
	 * @param request : none
	 * @param response : none
	 */
	@RequestMapping(value = "/getUserId")
	public void getUserId(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Cookie[] cookie = null;
			cookie = request.getCookies();
			out.write(cookie[0].getValue());
			logger.info("Accessing UserId From Cookie");
		} catch (IOException e) {
			logger.info("Unable To Fetch UserId From Cookie");
		}
	}

	/**
	 * Users Log Out
	 * 
	 * @param model : none
	 * @param request : none
	 * @param response : none
	 * @return Logot Page
	 * @throws IOException
	 */
	@RequestMapping(value = "/logout")
	public String logout(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("User Logging Out");
		
		java.util.Date date = new java.util.Date();
		String logoutMethod = "By User";
		HttpSession session = request.getSession(true);	
		session.removeAttribute("url_prior_login");
		try{
			UserLog ul=userlogService.findbySessionId(session.getId());
			Period p = new Period(ul.getUlSessionStart().getTime(), date.getTime());
			long hours = p.getHours();
			long minutes = p.getMinutes();
			long seconds=p.getSeconds();
			
			String duration=hours+":"+minutes+":"+seconds;			
			userlogService.updateUserDetails(logoutMethod, session.getId(),new Timestamp(date.getTime()),duration);
		}catch(NullPointerException e){
			
			logger.info("i am in logout catch");
			
		}		
		
		logger.info("i am in logout catch out");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		/*if (auth != null) {
			logger.info("i am in logout if");
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}*/
		SecurityContextHolder.getContext().setAuthentication(null);
		
		logger.info("i am in logout after setAuth");
		
		return "login";
	}

	/**
	 * Forgot Password
	 * 
	 * @param map : none
	 * @param model : none 
	 * @param locale : System Local
	 * @return Users Password successful Message
	 * @throws LDAPException 
	 */
	@RequestMapping(value = "/recoverPassword")
	public String recoverPassword(@RequestParam Map<String, String> map,
		Model model, final Locale locale) throws LDAPException {
		
		Map<String, String> data = ldapBusinessModel.getPassword(map.get("urLoginName"), map.get("emailId"),map.get("mobileNo"));
		String userId = data.get("UserId");
		String password = data.get("Password");
		String errorMessage = data.get("Error");
		String telephoneNumber = data.get("telephoneNumber");

		if (errorMessage == null || errorMessage.equals("")) {

			String message = messageSource.getMessage("Mail.users.subject",
					null, locale);
			logger.info("Forgot Password:Sending Mail To User");
			
			String personPassword = RandomPasswordGenerator.generateRandomPassword();


			logger.info("BFM LINK_----------------"+bfmUrl);
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
						+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">SKYON ADMINISTRATION DEPARTMENT</h2> <br /> Dear SKYON, Welcome to IREO <br/> <br/> "
						+ "Please use following UserName and Password to login. <br/> <br/>"
						+ "<table class=\"hovertable\" >" + "<tr>"
						+ "<td colspan='2'>Your Account Details are</td>"
						+ "</tr>" + "<tr>" + "<td> User Name :</td>"
						+ "<td>" + userId + "</td>"
						+ "</tr>" + "<tr>" + "<td> Password:</td>" + "<td>"
						+ password + "</td>" + "</tr>" + "</table>"
						+ "<br/><br/>"
						+ "<a href=http://"+ bfmUrl
						+ ">click to login</a></body></html>"
						+ "<br/><br/>"
						+ "<br/>Thanks,<br/>"
						+ "Skyon Administration Department <br/> <br/>"
						+"</body></html>";	

			
			    emailCredentialsDetailsBean.setToAddressEmail(map.get("emailId"));
				emailCredentialsDetailsBean.setMessageContent(messageContent);
			
				new Thread(new SendMailForUserDetails(emailCredentialsDetailsBean)).start();
			
			
			
			
			String gateWayUserName = messageSource.getMessage(
					"SMS.users.username", null, locale);
			String gateWayPassword = messageSource.getMessage(
					"SMS.users.password", null, locale);
			logger.info("Forgot Password:Sending SMS To User");
			new Thread(new SendPasswordRecovery("User", telephoneNumber,
					userId, password, gateWayUserName, gateWayPassword))
					.start();
			model.addAttribute("msg",
					"Your Credentials are Sent to Mail :" + map.get("emailId")
							+ " and Mobile Number " + telephoneNumber);

		} else {
			logger.info("Forgot Password:Printing Error Message");
			model.addAttribute("msg", "\'" + errorMessage + "'");
		}		
		return "login";

	}

	/** ------------------- End ----------------------- */

	/** ------------------- Manage Change Password ----------------------- */

	/**
	 * Change Password
	 * 
	 * @param request : none
	 * @param model : none 
	 * @return User password Change status
	 */
	@RequestMapping(value = "/changePassword")
	public String changePassword(HttpServletRequest request, ModelMap model) {
		logger.info("Accessing Change Password");
		model.addAttribute("ViewName", "Change Password");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Change Password", 1, request);
		return "changePassword";
	}

	/**
	 * Updating User Password
	 * 
	 * @param request : none
	 * @param response : none
	 */
	@RequestMapping(value = "/updatePassword", method ={ RequestMethod.POST})
	public String updatePassword(HttpServletRequest request,HttpServletResponse response,ModelMap model) {
		String userid = (String) SessionData.getUserDetails().get("userID");
		
		try {
			String status=null;
			logger.info("OLD PWD_---------------"+request.getParameter("oldPassword"));
			
			logger.info("New PWD_---------------"+request.getParameter("newPassword"));
			
			logger.info("Confirm PWD_---------------"+request.getParameter("confirmPassword"));
			
			String newPwd=(String)request.getParameter("newPassword");
			
			String oldPwd=(String)request.getParameter("confirmPassword");
			
			if(!newPwd.equals(oldPwd))
			{
			 status="New Password and Confirm Password Should Match!";
			}
			else
			{
			
				 status = ldapBusinessModel.changePassword(userid,request.getParameter("oldPassword"),request.getParameter("newPassword"));
				logger.info("Change Password:Printing Successful Message");

				
			}
			
			
			

			if (status.equalsIgnoreCase("The provided user password does not match any password in the user's entry")) {
				status = "Entered Old Password is Wrong";
			}
			model.addAttribute("status",status);
		} catch (Exception e) {
			logger.info("Change Password:Printing Error Message");
			model.addAttribute("status",e.getMessage());
		}
		HttpSession session=request.getSession(true);
		session.getServletContext().setAttribute("menuName","");
				
		return "changePassword";
	}

	/**
	 * Access Denied Page
	 * 
	 * @param request : none
	 * @param model :none
	 * @return
	 */
	@RequestMapping(value = "/accessDenied")
	public String accessDenied(HttpServletRequest request, ModelMap model) {
		return "accessDenied";
	}

	/** ------------------- End ----------------------- */
	
	
	
	
/******************** LOGIN FOR MOBILE DEVICE *******************/
	

	@RequestMapping(value = "/mobileLoginCustomers" , method = RequestMethod.POST)
	
	 public @ResponseBody String mobileLoginBoth( HttpServletRequest request,
	   HttpServletResponse response) throws IOException {
	 String status = null;
		
		 String username=request.getParameter("userMailId").toString();
		 if( username.contains("@")){
			 
			 
			 LdapBusinessModel ldp = new LdapBusinessModel();
 			
 				   status=ldp.loginCheckForCustomers(request.getParameter("userMailId")	, request.getParameter("password")); 
 				 if(status == null){
 					status="invalid";
 				   }
 				  else{
 					   if(status == ""){
 							status="invalid";
 						    }
 						   else{
 							   
 							status="success";
 							
 						  }


 				  }
			 
			 
		 }
		return status;
	
	}
		    
		    	
	
		    	
	@RequestMapping(value = "/mobileLogin" , method = RequestMethod.POST)
	
	 public @ResponseBody String mobileLogin( HttpServletRequest request,
	   HttpServletResponse response) throws IOException {
	
	 
	  
	  LdapBusinessModel businessModel = new LdapBusinessModel();
	
	  String status=businessModel.loginCheck( request.getParameter("userMailId")	, request.getParameter("password"));
	
	 
   if(status == null){
	status="invalid";
   }
  else{
	   if(status == ""){
			status="invalid";
		    }
		   else{
			   
			status="success";
		
		  }
	
 }
	  
	  return status;
	 }
	
	
	
}
