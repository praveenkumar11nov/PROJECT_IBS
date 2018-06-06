package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import navigation.Example;
import navigation.Widget;
import net.sf.ehcache.CacheManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bcits.bfm.ldap.ItemDetails;
import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.ldap.ModuleDetails;
import com.bcits.bfm.ldap.ThirdLevelDetails;
import com.bcits.bfm.model.Pinstatus;
import com.bcits.bfm.model.UserLog;
import com.bcits.bfm.service.OnlinePaymentTransactionsService;
import com.bcits.bfm.service.userManagement.FormDefService;
import com.bcits.bfm.service.userManagement.ModuleDefService;
import com.bcits.bfm.service.userManagement.PermissionService;
import com.bcits.bfm.service.userManagement.PinstatusService;
import com.bcits.bfm.service.userManagement.ProductDefService;
import com.bcits.bfm.service.userManagement.RoleService;
import com.bcits.bfm.service.userManagement.TaskDefService;
import com.bcits.bfm.service.userManagement.UserLogSerivce;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.serviceImpl.facilityManagement.ParkingSlotsAllotmentServiceImpl;
import com.bcits.bfm.util.Link;
import com.bcits.bfm.util.MyBootstrapCacheLoaderFactory;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.novell.ldap.LDAPException;

/**
 * @author Manjunath Kotagi And Farooq
 *
 */
@Controller
public class HomeController {
	@Autowired
	private LdapBusinessModel ldapBusinessModel;
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	@Autowired
	private ProductDefService productDefService;
	@Autowired
	private ModuleDefService moduleDefService;
	@Autowired
	private FormDefService formDefService;
	@Autowired
	private TaskDefService taskDefService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UserLogSerivce userlogService;
	@Autowired
	private PinstatusService pinstatusService;
	@Autowired
	CacheManager cacheManager;
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private MyBootstrapCacheLoaderFactory myBootstrapCacheLoaderFactory;
	
	@Autowired
	private OnlinePaymentTransactionsService onlinePaymentTransactionsService;

	private static final Log logger = LogFactory.getLog(ParkingSlotsAllotmentServiceImpl.class);



	/** ------------------- Home ----------------------- */

	/**
	 * Display Home Page
	 * 
	 * @param model : none
	 * @param req : none
	 * @param response : none
	 * @return Home Page
	 * @throws JSONException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws ParseException 
	 */
	@Link(label="Home", family="UserManagementController", parent = "")
	@RequestMapping(value="/home")	
	public String home(Model model, HttpServletRequest req,HttpServletResponse response) throws JSONException, JsonParseException, JsonMappingException, IOException, ParseException {
		model.addAttribute("ViewName", "Dashboard");
		breadCrumbService.addNode("nodeID", 0, req);
		breadCrumbService.addNode("Dashboard", 1, req);
		
		String userId = (String) SessionData.getUserDetails().get("userID");
		userId=ldapBusinessModel.getUserLoginName(userId);	
		List<JSONObject> memberWithDesciption=myBootstrapCacheLoaderFactory.getMemberWithDesciption();	
		List<ModuleDetails> memberWithDesciptionForMenu=myBootstrapCacheLoaderFactory.getMemberWithDesciptionMenu();	
		List<String> roles = ldapBusinessModel.getLoginRoles(userId);
		HttpSession session = req.getSession(true);
		ServletContext servletcontext = session.getServletContext();	
		servletcontext.setAttribute("menuName","Home");
		session.setAttribute("thirdLevelMenu", null);  
		   
		ModelAndView modelAndView = new ModelAndView();	     
		modelAndView.addObject("userName", userId);
		String check=null;
	 	if(roles.size()==0){
			model.addAttribute("msg", "UnAuthorised to Access.. No Roles Found");
			return "login";
		}else {
			
			session.setAttribute("userId", userId);
			
	        servletcontext.setAttribute("memberWithDesciption", memberWithDesciption);       		
	        servletcontext.setAttribute("role", roles);
			
			Cookie[] cookie = null;	
			cookie = req.getCookies();	
			
			try{
				int length=cookie.length;
				 if(length==0){
					Cookie newcookie = new Cookie(userId, userId);
					newcookie.setMaxAge(60*60*24*365);
					response.addCookie(newcookie);
					
				}
				 else if(length==1){
					Cookie newcookie = new Cookie(userId, userId);
					newcookie.setMaxAge(60*60*24*365);
					response.addCookie(newcookie);
					
				}
					
				else{
					cookie[0].setValue(userId);	
					cookie[0].setMaxAge(60*60*24*365);
					response.addCookie(cookie[0]);
				}
			}catch(NullPointerException e){
				return "redirect:/disabledCookie";
			}			
		    check=storeUserDetails(userId,session,req);
			 
		}
	 	
	 	
		//model.addAttribute("userName", userId);		
		String theme = pinstatusService.getUserTheme(userId);
		session.setAttribute("themeName", theme);
		
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());  		
		
		List<JSONObject> modules=new ArrayList<JSONObject>();
		List<String> mod=new ArrayList<String>();
		
		for(ModuleDetails mdetails:memberWithDesciptionForMenu){
			for(String rol:roles){
//				logger.info(mdetails.getRole()+"  :: "+rol);				
				if(mdetails.getRole().contains(rol)){
					JSONObject module=new JSONObject();
					
					module.put("text", mdetails.getDescription());					
					module.put("role", mdetails.getRole());
					List<JSONObject> items=new ArrayList<JSONObject>();
					List<String> im=new ArrayList<String>();					
					for(ItemDetails idetails:mdetails.getItems()){					
						if(idetails.getRole().contains(rol)){
							JSONObject item=new JSONObject();
							item.put("text", idetails.getText());						
							item.put("url", idetails.getUrl());
							item.put("role", idetails.getRole());						
							if(!(im.contains(idetails.getText()))){
								
								items.add(item);
							}
							im.add(idetails.getText());
						}
						
					}
					
					module.put("items", items);					
					if(!(mod.contains(mdetails.getDescription()))){
						modules.add(module);
					}
					mod.add(mdetails.getDescription());
				}
			}		
			
		}		 
     
		JSONObject jo = new JSONObject();	
		jo.put("Navigation", modules);   
        
        
        HashMap<String, Widget[]> navigation = mapper.readValue(jo.toString(), new TypeReference<HashMap<String,Widget[]>>() {});    
        
        for (Map.Entry<String, Widget[]> entry : navigation.entrySet()) 
        {
            for (Widget widget : entry.getValue()) 
            {
            	List<Example> examples = new ArrayList<Example>();   
	                for (Example example : widget.getItems()) 
	                {	
            				examples.add(example);
	                }
            	 
            	widget.setItems(examples.toArray(new Example[examples.size()]));
            }
        }     
         
        servletcontext.setAttribute("navigation", navigation);
        cacheManager.removalAll();
        model.addAttribute("User",usersService.getUserDetails());
       // model.addAttribute("Customer",usersService.getCustomerDetails());
        model.addAttribute("Staff",usersService.getUserDetails());
        model.addAttribute("Owner",usersService.getOwnerDetails());
        model.addAttribute("Vendor",usersService.getVendorDetails());
        model.addAttribute("Parking",usersService.getParkingDetails());
        model.addAttribute("Visitor",usersService.getVisitorDetails());
        model.addAttribute("adminFiles", usersService.getFileDrawingDetails());
        model.addAttribute("assets", usersService.getAssetsInventaryDetails());
        model.addAttribute("helpDesk", usersService.getHelpDeskDetails());
        
        model.addAttribute("transaction", onlinePaymentTransactionsService.getTransactionDetails());
        
        if(check!=null){
        	model.addAttribute("countValue", "first"); 
        	return "changePassword";
		 }
      
        String prevUrl = "";
        if (session.getAttribute("url_prior_login")!= null) {
        	prevUrl = (String)session.getAttribute("url_prior_login");
        	session.removeAttribute("url_prior_login");
        	if(prevUrl!=null && !prevUrl.contains("loginExpire") && !prevUrl.contains("logout")){
        		response.sendRedirect(prevUrl);
        	}
        }

		return "dashboard";
	         
	}
	@RequestMapping("/dashBoard/ChartDetails")
	public @ResponseBody Object chartDetails(HttpServletRequest request) throws ParseException{
		return usersService.getChartData();
	}

	/**
	 * Store User Log
	 * 
	 * @param userId : user Id
	 * @param session : session
	 * @param req : Http Request
	 * @return 
	 */
	private String storeUserDetails(String userId, HttpSession session, HttpServletRequest req) {
		java.util.Date date= new java.util.Date();			 
		String sessionId = session.getId();
		String loginStatus = "Login SuccessFull";
		ServletContext servletcontext = session.getServletContext();	
		String lastLoginrole = servletcontext.getAttribute("role").toString();
		UserLog userlog = new UserLog();
		userlog.setUrLoginName(userId);
		userlog.setUlSessionStart(new Timestamp(date.getTime()));		
		userlog.setUlSessionId(sessionId);
		userlog.setPreviousLoginRole(lastLoginrole);
		userlog.setStatus(loginStatus);	
		req.getHeader("VIA");
		String ipAddress = req.getHeader("X-FORWARDED-FOR");
	    if (ipAddress == null) {
	            ipAddress = req.getRemoteAddr();
	    }
		userlog.setSystemIp(ipAddress);
		userlogService.save(userlog);		
		
		Long userLogCount=userlogService.getCount(userId);
		logger.info("**********userLogCount********"+userLogCount);
		if(userLogCount==1){
			return "changePassword";
		}
		else{
		
		List<UserLog> userData=userlogService.findAllUserLogDetails(userlog.getUrLoginName());
		Iterator<UserLog> userDataIterator=userData.iterator();
		while(userDataIterator.hasNext()){
			UserLog ul=userDataIterator.next();				
			if(sessionId!=ul.getUlSessionId()){
				if(ul.getUlSessionEnd()==null || ul.getUlSessionEnd().toString().trim().equals("")){
					UserLog userlog1 =userlogService.find(ul.getUlId());
					userlog1.setLogoutMethod("Time Out");
					userlog1.setUlSessionEnd(new Timestamp(date.getTime()));
					Period p = new Period(userlog1.getUlSessionStart().getTime(), userlog1.getUlSessionEnd().getTime());
					long hours = p.getHours();
					long minutes = p.getMinutes();
					long seconds=p.getSeconds();
					String duration=hours+":"+minutes+":"+seconds;
					userlog1.setDuration(duration);
					userlogService.update(userlog1);				
				}
			}	
				
		}
		UserLog userlog2 =userlogService.find(userlog.getUlId());
		userlog2.setLogoutMethod("");
		userlog2.setUlSessionEnd(null);
		userlog2.setDuration("");
		userlogService.update(userlog2);
		return null;
		}
	}	
	

	/**
	 * 
	 * Displaying User Image
	 * 
	 * @param userName : User Login Name
	 * @param request : none
	 * @param response : none
	 * @throws IOException
	 */
	@RequestMapping("/ldap/getuserimage/")
	public void getImage(Model userName, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
		String userId = (String) SessionData.getUserDetails().get("userID");
		byte bt[] = null;
		OutputStream ot = response.getOutputStream();
		try {
			logger.info("Accessing User Image From LDAP");
			bt = ldapBusinessModel.getImage(userId);
			ot.write(bt);
			ot.close();
		} catch (Exception e) {
			try {
				logger.info("Accessing User Image From LDAP");
				bt = ldapBusinessModel.getImage("bcitsadmin");
			} catch (Exception e1) {
			}
			ot.write(bt);
		}
	}

	/** ------------------- Dash board ----------------------- */

	@RequestMapping("/dashboard")
	public String dashboard(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Dashboard");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Dashboard", 1, request);
		return "dashboard";
	}

	/** ------------------- End ----------------------- */

	@RequestMapping(value = "/userpinstatus")
	public @ResponseBody
	String pinstatus(HttpServletRequest request, HttpServletResponse response) {
		int pinstatus = Integer.parseInt(request.getParameter("pinStatus"));
		String userid = (String) SessionData.getUserDetails().get("userID");
		String status = pinstatusService.checkUserExists(userid, pinstatus);
		if (status.equalsIgnoreCase("Exists")) {
			Pinstatus obj = new Pinstatus();
			obj.setUserid(userid);
			obj.setPinstatus(pinstatus);
			pinstatusService.update(obj);
		} else {
			Pinstatus obj = new Pinstatus();
			obj.setUserid(userid);
			obj.setPinstatus(pinstatus);
			pinstatusService.save(obj);
		}
		return null;
	}

	@RequestMapping("/readPinStatus")
	public @ResponseBody
	String ReadPinStatus(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String userid = (String) SessionData.getUserDetails().get("userID");
		String status = pinstatusService.checkUser(userid, 1);
		PrintWriter out = response.getWriter();
		out.write(status);
		return null;
	}
	
	 /*------------------- End -----------------------*/
	
	@RequestMapping("/changeGridTheme")
	public String changeGridTheme(HttpServletRequest request,HttpServletResponse response)
	{
		String theme = request.getParameter("theme");
		String userid = (String) SessionData.getUserDetails().get("userID");
		pinstatusService.executeDeleteQuery("Update Pinstatus p SET p.theme = '"+theme+"' WHERE p.userid='"+userid+"'");
		HttpSession session = request.getSession();
		session.setAttribute("themeName", theme);
		return "redirect:/myprofile";
		
	}
	
	@RequestMapping(value = "/thirdlevelMenu/{id}", method = RequestMethod.POST)
	public void getAllDatailsOfJob(@PathVariable String id,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		HttpSession session = request.getSession();
		session.setAttribute("menuId", id);
		session.setAttribute("moduleName", request.getParameter("moduleName"));
		session.setAttribute("thirdLevelMenu", null); 
		PrintWriter out=response.getWriter();
		out.print("Success");
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getMenu")
	public String getMenu(HttpServletRequest request,HttpServletResponse response,Model model) throws LDAPException, Exception
	{
		model.addAttribute("ViewName", request.getParameter("menuName"));
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode(request.getParameter("menuName"), 1, request);
		
		List<ThirdLevelDetails> thirdLevelDetails=ldapBusinessModel.thirdlevelMenu(request.getParameter("moduleName"),request.getParameter("menuName"));
		HttpSession session=request.getSession(true);
		ServletContext servletcontext = session.getServletContext();	
		List<String> roles=(List<String>) servletcontext.getAttribute("role");
		
		List<JSONObject> modules=new ArrayList<JSONObject>();
		List<String> mod=new ArrayList<String>();
		
		for(ThirdLevelDetails mdetails:thirdLevelDetails){
			for(String rol:roles){
				if(mdetails.getRole().contains(rol)){
					JSONObject module=new JSONObject();
					module.put("text", mdetails.getText());				
					module.put("role", mdetails.getRole());					
					module.put("url", mdetails.getUrl());					
					if(!(mod.contains(mdetails.getText()))){
						modules.add(module);
					}
					mod.add(mdetails.getText());
				}
			}		
			
		}	
		
		List<JSONObject> jList=new ArrayList<JSONObject>();
		JSONObject jod = new JSONObject();
		jod.put("text", request.getParameter("menuName"));
		jod.put("url", "");
		jod.put("items", modules);
		jList.add(jod);
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());  
		
		JSONObject jo = new JSONObject();	
		jo.put("Navigation", jList);    
    	
		 HashMap<String, Widget[]> thirdlevelNavigation = mapper.readValue(jo.toString(), new TypeReference<HashMap<String,Widget[]>>() {});    
	        
	        for (Map.Entry<String, Widget[]> entry : thirdlevelNavigation.entrySet()) 
	        {
	            for (Widget widget : entry.getValue()) 
	            {
	            	List<Example> examples = new ArrayList<Example>();   
		                for (Example example : widget.getItems()) 
		                {	
	            				examples.add(example);
		                }
	            	 
	            	widget.setItems(examples.toArray(new Example[examples.size()]));
	            }
	        }
		
	     session.setAttribute("thirdLevelMenu", thirdlevelNavigation);  	    
	    
	     session.getServletContext().setAttribute("menuName", request.getParameter("menuName").replaceAll("\\s",""));
		return "blankpage";
	}	 
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/leftthirdlevelMenu")
	public void leftthirdlevelMenu(HttpServletRequest request,HttpServletResponse response,Model model) throws LDAPException, Exception
	{
		
		List<ThirdLevelDetails> thirdLevelDetails=ldapBusinessModel.thirdlevelMenu(request.getParameter("moduleName"),request.getParameter("usecaseName"));
		HttpSession session=request.getSession(true);
		ServletContext servletcontext = session.getServletContext();	
		List<String> roles=(List<String>) servletcontext.getAttribute("role");
		
		List<JSONObject> modules=new ArrayList<JSONObject>();
		List<String> mod=new ArrayList<String>();
		
		for(ThirdLevelDetails mdetails:thirdLevelDetails){
			for(String rol:roles){
				if(mdetails.getRole().contains(rol)){
					JSONObject module=new JSONObject();
					module.put("text", mdetails.getText());										
					module.put("url", mdetails.getUrl());					
					if(!(mod.contains(mdetails.getText()))){
						modules.add(module);
					}
					mod.add(mdetails.getText());
				}
			}		
			
		}	
		
		List<JSONObject> jList=new ArrayList<JSONObject>();
		JSONObject jod = new JSONObject();
		jod.put("text", request.getParameter("usecaseName"));
		jod.put("url", "");
		jod.put("items", modules);
		jList.add(jod);
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());  
		
		JSONObject jo = new JSONObject();	
		jo.put("Navigation", jList);    
    	
		 HashMap<String, Widget[]> thirdlevelNavigation = mapper.readValue(jo.toString(), new TypeReference<HashMap<String,Widget[]>>() {});    
	        
	        for (Map.Entry<String, Widget[]> entry : thirdlevelNavigation.entrySet()) 
	        {
	            for (Widget widget : entry.getValue()) 
	            {
	            	List<Example> examples = new ArrayList<Example>();   
		                for (Example example : widget.getItems()) 
		                {	
	            				examples.add(example);
		                }
	            	 
	            	widget.setItems(examples.toArray(new Example[examples.size()]));
	            }
	        }
		
	     session.setAttribute("thirdLevelMenu", thirdlevelNavigation);  
	    
	     model.addAttribute("ViewName",request.getParameter("moduleName"));
	     session.getServletContext().setAttribute("menuName", request.getParameter("usecaseName").replaceAll("\\s",""));
	     
		response.setContentType("application/json");
		PrintWriter out=response.getWriter();
		out.print(modules);
		
	}	 
	
	@RequestMapping(value="/recoverMenu")	
	public void recoverMenu(Model model, HttpServletRequest req,HttpServletResponse response) throws JSONException, JsonParseException, JsonMappingException, IOException {
		
		HttpSession session = req.getSession(true);
		ServletContext servletcontext = session.getServletContext();	
		
		String userId = (String) SessionData.getUserDetails().get("userID");
		
		userId=ldapBusinessModel.getUserLoginName(userId);	
		
		List<JSONObject> memberWithDesciption=myBootstrapCacheLoaderFactory.getMemberWithDesciption();	
		List<ModuleDetails> memberWithDesciptionForMenu=myBootstrapCacheLoaderFactory.getMemberWithDesciptionMenu();	
		
		List<String> roles = ldapBusinessModel.getLoginRoles(userId);
		
		session.setAttribute("userId", userId);		
        servletcontext.setAttribute("memberWithDesciption", memberWithDesciption);       		
        servletcontext.setAttribute("role", roles);
        
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());  		
		
		List<JSONObject> modules=new ArrayList<JSONObject>();
		List<String> mod=new ArrayList<String>();
		
		for(ModuleDetails mdetails:memberWithDesciptionForMenu){
			for(String rol:roles){
				if(mdetails.getRole().contains(rol)){
					JSONObject module=new JSONObject();
					module.put("text", mdetails.getDescription());					
					module.put("role", mdetails.getRole());
					List<JSONObject> items=new ArrayList<JSONObject>();
					List<String> im=new ArrayList<String>();					
					for(ItemDetails idetails:mdetails.getItems()){					
						if(idetails.getRole().contains(rol)){
							JSONObject item=new JSONObject();
							item.put("text", idetails.getText());						
							item.put("url", idetails.getUrl());
							item.put("role", idetails.getRole());						
							if(!(im.contains(idetails.getText()))){
								
								items.add(item);
							}
							im.add(idetails.getText());
						}
						
					}
					
					module.put("items", items);					
					if(!(mod.contains(mdetails.getDescription()))){
						modules.add(module);
					}
					mod.add(mdetails.getDescription());
				}
			}		
			
		}		 
     
		JSONObject jo = new JSONObject();	
		jo.put("Navigation", modules);   
        
        
        HashMap<String, Widget[]> navigation = mapper.readValue(jo.toString(), new TypeReference<HashMap<String,Widget[]>>() {});    
        
        for (Map.Entry<String, Widget[]> entry : navigation.entrySet()) 
        {
            for (Widget widget : entry.getValue()) 
            {
            	List<Example> examples = new ArrayList<Example>();   
	                for (Example example : widget.getItems()) 
	                {	
            				examples.add(example);
	                }
            	 
            	widget.setItems(examples.toArray(new Example[examples.size()]));
            }
        }     
         
        servletcontext.setAttribute("navigation", navigation);      
        cacheManager.removalAll();
	}
	

}
