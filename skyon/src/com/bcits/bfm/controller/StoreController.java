package com.bcits.bfm.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.StoreMasterEntity;
import com.bcits.bfm.service.helpDeskManagement.StoreMasterService;
import com.bcits.bfm.util.EmailCredentialsDetailsBean;
import com.bcits.bfm.util.FilterUnit;
import com.bcits.bfm.util.GrocerySendSMS;
import com.bcits.bfm.util.HelpDeskMailSender;
import com.bcits.bfm.util.SmsCredentialsDetailsBean;
import com.bcits.bfm.view.BreadCrumbTreeService;





@Controller
public class StoreController extends FilterUnit {
	@Autowired
	private StoreMasterService storeMasterService;

	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	@Autowired
	private MessageSource messageSource;
	
	
	@RequestMapping("/storemaster")
	public String readIndex(Model model,HttpServletRequest request)
	{
		model.addAttribute("ViewName", "Customer Care");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Customer Care", 1, request);
		breadCrumbService.addNode("Manage Store Master ", 2, request);
		return "/helpDesk/storemaster";
	}	
	
	@RequestMapping(value = "/storemaster/Create", method = RequestMethod.GET)
	public @ResponseBody Object createDepartment(@ModelAttribute("storeMasterEntity") StoreMasterEntity storeMasterEntity, ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws Exception
	{
		storeMasterEntity.setStoreName((WordUtils.capitalizeFully((String) storeMasterEntity.getStoreName())));
		return storeMasterService.save(storeMasterEntity);
		
		

	}

	@RequestMapping(value = "/storemaster/Read", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<StoreMasterEntity> escalatedTicketRead() {
		
		
		List<StoreMasterEntity> storeMasterEntities = storeMasterService.findAllData();
		return storeMasterEntities;
	}
	
	@RequestMapping(value="/storemaster/Update",method = RequestMethod.GET)	
	public @ResponseBody Object updateDepartment(@ModelAttribute("storeMasterEntity") StoreMasterEntity storeMasterEntity) throws InterruptedException
	{
		return storeMasterService.update(storeMasterEntity);
	
	}

	@RequestMapping(value = "/storemaster/Destroy", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object salaryComponentDestroy(@ModelAttribute("storeMasterEntity") StoreMasterEntity storeMasterEntity,BindingResult bindingResult, ModelMap model,HttpServletRequest req, SessionStatus sessionStatus) throws Exception 
	{
	
		try {
		
			storeMasterService.delete(storeMasterEntity.getsId());
		} catch (Exception e) {
			
		}
		sessionStatus.setComplete();
		return storeMasterEntity;
		}
	
	@RequestMapping(value = "/store/Status/{sId}/{operation}", method = RequestMethod.POST)
	public String updateStatus(@ModelAttribute("storeMasterEntity") StoreMasterEntity storeMasterEntity,@PathVariable int sId,@PathVariable String operation,ModelMap model, HttpServletRequest request,HttpServletResponse response, final Locale locale) throws Exception
	{
		
		
		List<StoreMasterEntity> store=storeMasterService.findStoreMobile(sId);
		String mobile=store.get(0).getStoreNum();
		String username=store.get(0).getUsername();
		String password=store.get(0).getPassword();
	if(operation.equals("activate")){
	
		 SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
		 String messagePerson = " NEW AGE BFM services.This sms is to inform you that your store Account is registered  successfully with username"+username+" and password"+password+"created on "+ new Timestamp(new java.util.Date().getTime()).toLocaleString()
				    +"Thank you and Have a nice day"; 
		
		    smsCredentialsDetailsBean.setNumber(mobile);
			smsCredentialsDetailsBean.setUserName("customer");
			smsCredentialsDetailsBean.setMessage(messagePerson);
			new Thread(new GrocerySendSMS(smsCredentialsDetailsBean)).start();	
			
			storeMasterService.setstoreStatus(sId, operation, response, messageSource, locale);
	}else{
		storeMasterService.setstoreStatus(sId, operation, response, messageSource, locale);
	}
		return null;
	}
	
	@RequestMapping(value = "/storemaster/Filter", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getBlocksForFilter() {
	
		List<StoreMasterEntity> blocks = storeMasterService.findAllStoreNames();
		Set<String> al = new HashSet<String>();
		Iterator<StoreMasterEntity> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getStoreName());
		}
		return al;
	}


	
	@RequestMapping(value = "/itemmaster/storenamesDropDown", method = RequestMethod.GET)
	public @ResponseBody List<?> StoreReadUrl() 
	{
		return storeMasterService.findAll();
	} 
	
	@RequestMapping(value = "storeLogin", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<StoreMasterEntity> LodinDetails(@ModelAttribute("storeMasterEntity")StoreMasterEntity storeMasterEntity,HttpServletRequest req) {
		String user=req.getParameter("userMailId");
		String pass=req.getParameter("password");
		
		 
	List<StoreMasterEntity> customerEntities = storeMasterService.findAllLoginDetails(user,pass);
	
	return customerEntities;	
}
	
}
