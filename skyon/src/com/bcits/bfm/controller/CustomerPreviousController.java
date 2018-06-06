package com.bcits.bfm.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.bcits.bfm.model.CustomerEntity;
import com.bcits.bfm.model.CustomerItemsEntity;
import com.bcits.bfm.model.CustomerPreviousOrder;
import com.bcits.bfm.model.StoreMasterEntity;
import com.bcits.bfm.service.helpDeskManagement.CustomerOrderService;
import com.bcits.bfm.service.helpDeskManagement.CustomerPreviousOrderService;

@Controller
public class CustomerPreviousController {

	@Autowired
	private CustomerPreviousOrderService customerPreviousOrderService;
	
	@Autowired
	private CustomerOrderService customerOrderService;
	
	@RequestMapping("/Customerpreorder")
	public String readIndex(Model model)
	{
		return "/helpDesk/Customerpreorder";
	}	
	

	@RequestMapping(value = "/customerpreorder/read/{personId}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> templateItemsRead(@PathVariable int personId,HttpServletRequest req) {
		List<CustomerEntity> customerEntities = customerPreviousOrderService.findAllData(personId);
			return customerEntities;
	
	}
	
	@RequestMapping(value = "/customerpreorder/destroy", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object salaryComponentDestroy(@ModelAttribute("customerPreviousOrder") CustomerPreviousOrder customerPreviousOrder,BindingResult bindingResult, ModelMap model,HttpServletRequest req, SessionStatus sessionStatus) throws Exception 
	{
	
		try {
		
			customerPreviousOrderService.delete(customerPreviousOrder.getCpid());
		} catch (Exception e) {
			
		}
		sessionStatus.setComplete();
		return customerPreviousOrder;
		}
}
