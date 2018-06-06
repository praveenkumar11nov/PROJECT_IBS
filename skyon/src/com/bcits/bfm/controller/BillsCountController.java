package com.bcits.bfm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.OnlinePaymentTransactions;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.OnlinePaymentTransactionsService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.facilityManagement.ServiceParameterMasterService;
import com.bcits.bfm.util.FilterUnit;
import com.bcits.bfm.util.ValidationUtil;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class BillsCountController extends FilterUnit {
	static Logger logger = LoggerFactory
			.getLogger(BillsCountController.class);
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	ServiceParameterMasterService spmService;
	
	@Autowired
	private ElectricityBillsService electricityBillsService;
	
	@Resource
	private ValidationUtil validationUtil;
		
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PersonService personService;

	@RequestMapping(value = "/numberofbilled")
	public String service(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Bills Generation");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Bills Generation", 1, request);
		breadCrumbService.addNode("Manage Consolidate Bills", 2, request);
		return "servicetasks/numberofbilled";
	}
	
	@SuppressWarnings({ "unchecked", "serial" })
	@RequestMapping(value = "/numberofbilled/billRead", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody List<?> readElBillData()
		{
	    	logger.info("In Consolidated Electricity Bill Read Method");
	    	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			List<Object[]> billEntities = (List<Object[]>) electricityBillsService.find();
			for (final Object[] obj : billEntities) {
			
				result.add(new HashMap<String, Object>() {
					{
						put("typeOfService", (String) obj[0]);
					    put("noCustomer",  (Long) obj[1]);
					    put("numberBilled", (Long) obj[2]);
					    put("consolidated", (Long) obj[3]);
					    
					    long object2=(Long) obj[1];
					    long object3=(Long) obj[2];
					    long object5=(Long) obj[3];
					    long object4=object2-object3;
					    long object6=object2-object5;
					    
					    put("numberPending",object4);
					    put("consolidatePending",object6);
					
					}
				});

			}
			return result;
		}
	    
	  @SuppressWarnings({ "unchecked", "serial" })
	   @RequestMapping(value="/numberofbilled/getTypeOfService", method=RequestMethod.GET)
	   public @ResponseBody List<?> typeService(){
		   logger.info("##########################");
		   List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			List<Object[]> billEntities = (List<Object[]>) electricityBillsService.find();
			for (final Object[] obj : billEntities) {
				result.add(new HashMap<String, Object>() {
					{
						put("typeOfService", (String) obj[0]);
					}
				});

			}
			return result;
		
	   }

}
