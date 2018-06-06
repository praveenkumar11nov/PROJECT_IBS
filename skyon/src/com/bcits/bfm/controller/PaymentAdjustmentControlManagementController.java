package com.bcits.bfm.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.AdjustmentComponentsEntity;
import com.bcits.bfm.model.PaymentAdjustmentControlEntity;
import com.bcits.bfm.model.PaymentAdjustmentEntity;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.MeetingRequestService;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentComponentService;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentControlService;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.sun.corba.se.spi.presentation.rmi.PresentationDefaults;

/**
 * Controller which includes all the business logic concerned to this module's Use case
 * Module: Billing Collections
 * Use Case : Payments
 * 
 * @author Ravi Shankar Reddy
 * @version %I%, %G%
 * @since 0.1
 */

@Controller
public class PaymentAdjustmentControlManagementController {

static Logger logger = LoggerFactory.getLogger(MeterStatusController.class);

	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private JsonResponse jsonErrorResponse;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private AdjustmentControlService adjustmentControlService; 
	
	@Autowired
	private AdjustmentComponentService adjustmentComponentService;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private AdjustmentService adjustmentService;
	
	@Autowired
	private MeetingRequestService meetingRequestService;
	
	@RequestMapping("/paymentAdjustmentControl")
	public String paymentAdjustment(HttpServletRequest request, Model model) {
		logger.info("In payment adjustment control method");
		model.addAttribute("ViewName", "Payment Adjustment Control");	
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Payment Adjustment Control", 1, request);
		return "billingPayments/paymentAdjustmentControl";
	}
	
   	
   	// ****************************** Payment Adjustment Control Read,Create,Delete methods ********************************//
   
   	@RequestMapping(value = "/paymentAdjustmentControl/paymentAdjustmentRead", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<PaymentAdjustmentControlEntity> readAdjustments() {
		logger.info("In read adjustments method");
		List<PaymentAdjustmentControlEntity> adjustmentControlEntities = adjustmentControlService.findAll();
		return adjustmentControlEntities;
	}

	@RequestMapping(value = "/paymentAdjustmentControl/paymentAdjustmentCreate", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object saveAdjustmentsDetails(@ModelAttribute("adjustmentControlEntity") PaymentAdjustmentControlEntity adjustmentControlEntity,BindingResult bindingResult,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale) throws Exception {

		logger.info("In save adjustments details method");
		
		adjustmentControlEntity.setAccountId(adjustmentControlEntity.getAccountId());
		adjustmentControlEntity.setStatus("Created");
		adjustmentControlEntity.setPacDate(new java.sql.Date(new Date().getTime()));
		
		validator.validate(adjustmentControlEntity, bindingResult);
		adjustmentControlService.save(adjustmentControlEntity);
		sessionStatus.setComplete();
		return adjustmentControlEntity;
		
	}
	
	@RequestMapping(value = "/paymentAdjustmentControl/paymentAdjustmentUpdate", method = RequestMethod.GET)
	public @ResponseBody Object editAdjustmentDetails(@ModelAttribute("adjustmentControlEntity") PaymentAdjustmentControlEntity adjustmentControlEntity,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

		logger.info("In edit adjustments details method");
		adjustmentControlEntity.setAccountId(adjustmentControlEntity.getAccountId());
		adjustmentControlEntity.setPacDate(new java.sql.Date(new Date().getTime()));
		
		validator.validate(adjustmentControlEntity, bindingResult);
		adjustmentControlService.update(adjustmentControlEntity);
		
		return adjustmentControlEntity;
	}

	@RequestMapping(value = "/paymentAdjustmentControl/paymentAdjustmentDestroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteAdjustmentsDetsails(@ModelAttribute("adjustmentControlEntity") PaymentAdjustmentControlEntity adjustmentControlEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss,HttpServletRequest req) {
		logger.info("In delete adjustment details method");
		JsonResponse errorResponse = new JsonResponse();
		
		try {
			adjustmentControlService.delete(adjustmentControlEntity.getPacId());
		} catch (Exception e) {
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return adjustmentControlEntity;
	}
	
	@RequestMapping(value = "/paymentAdjustmentControl/setAdjustmentControlStatus/{pacId}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
	public void setAdjustmentControlStatus(@PathVariable int pacId,@PathVariable String operation, HttpServletResponse response) {
		
		logger.info("In adjustment status change method");
		if (operation.equalsIgnoreCase("activate")){
			adjustmentControlService.setAdjustmentControlStatus(pacId, operation, response);
		} else{
			adjustmentControlService.setAdjustmentControlStatus(pacId, operation, response);
		}
	}
	
	// ****************************** Adjustments Filters Data methods *******************************

	   @RequestMapping(value = "/paymentAdjustmentControl/filter/{feild}", method = RequestMethod.GET)
		public @ResponseBody Set<?> getAdjustmentsContentsForFilter(@PathVariable String feild) {
			logger.info("In  adjustment use case filters Method");
			List<String> attributeList = new ArrayList<String>();
			attributeList.add(feild);
			HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("PaymentAdjustmentControlEntity",attributeList, null));
					
			return set;
		}
	   
	   // ****************************** Adjustment Components Read,Create,Delete methods ********************************//

	  	@RequestMapping(value = "/paymentAdjustmentControl/adjustmentCalcLineRead/{pacId}", method = {RequestMethod.GET, RequestMethod.POST })
	  	public @ResponseBody List<AdjustmentComponentsEntity> readAdjustmentCalcLine(@PathVariable int pacId) {
	  		logger.info("In read adjustment calc line method");
	  		List<AdjustmentComponentsEntity> componentsEntities = adjustmentComponentService.findAllById(pacId);
	  		return componentsEntities;
	  	}
	  	
	  	@RequestMapping(value = "/paymentAdjustmentControl/adjustmentCalcLineCreate/{pacId}", method = RequestMethod.GET)
	  	public @ResponseBody Object saveAdjustmentCalcLine(@ModelAttribute("adjustmentComponentsEntity") AdjustmentComponentsEntity adjustmentComponentsEntity,BindingResult bindingResult,@PathVariable int pacId, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

	  		logger.info("In save adjustment calc line method");
	  		PaymentAdjustmentControlEntity adjustmentControlEntity = adjustmentControlService.find(pacId);
	  		adjustmentComponentsEntity.setAdjustmentControlEntity(adjustmentControlEntity);
	  		
	  		validator.validate(adjustmentComponentsEntity, bindingResult);
	  		adjustmentComponentService.save(adjustmentComponentsEntity);
	  		
	  		return adjustmentComponentsEntity;
	  	}
	  	
	  	@RequestMapping(value = "/paymentAdjustmentControl/adjustmentCalcLineUpdate/{pacId}", method = RequestMethod.GET)
	  	public @ResponseBody Object editAdjustmentCalcLine(@ModelAttribute("adjustmentComponentsEntity") AdjustmentComponentsEntity adjustmentComponentsEntity,BindingResult bindingResult,@PathVariable int pacId, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

	  		logger.info("In edit adjustment calc line method");
	  		adjustmentComponentsEntity.setAdjustmentControlEntity(adjustmentControlService.find(pacId));  		
	  		validator.validate(adjustmentComponentsEntity, bindingResult);
	  		adjustmentComponentService.update(adjustmentComponentsEntity);
	  		
	  		return adjustmentComponentsEntity;
	  	}

	  	@RequestMapping(value = "/paymentAdjustmentControl/adjustmentCalcLineDestroy", method = RequestMethod.GET)
	  	public @ResponseBody Object deleteAdjustmentCalcLine(@ModelAttribute("adjustmentComponentsEntity") AdjustmentComponentsEntity adjustmentComponentsEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss,HttpServletRequest req) {
	  		logger.info("In delete adjustment calc line method");
	  		JsonResponse errorResponse = new JsonResponse();
	  		
	  		try {
	  			adjustmentComponentService.delete(adjustmentComponentsEntity.getAdjComId());
	  		} catch (Exception e) {
	  			errorResponse.setStatus("CHILD");
	  			return errorResponse;
	  		}
	  		ss.setComplete();
	  		return adjustmentComponentsEntity;
	  	}
	  	
	 // ****************************** Adjustment Calculation Line Filters Data methods ********************************/

	    @RequestMapping(value = "/paymentAdjustmentControl/adjustmentCalcLine/filter/{feild}", method = RequestMethod.GET)
	 	public @ResponseBody Set<?> getAdjustmentCalcLineContentsForFilter(@PathVariable String feild) {
	 		logger.info("In adjustment calc line use case filters Method");
	 		List<String> attributeList = new ArrayList<String>();
	 		attributeList.add(feild);
	 		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("AdjustmentComponentsEntity",attributeList, null));
	 				
	 		return set;
	 	}
	    
	   @RequestMapping(value = "/paymentAdjustmentControl/getTransactionCodes/{typeOfService}", method = RequestMethod.GET)
		public @ResponseBody List<?> getTransactionCodes(@PathVariable String typeOfService) 
		{
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
			List<?> transactionCodeList = adjustmentComponentService.getTransactionCodes(typeOfService.trim());
			Map<String, Object> codeMap = null;
			for (Iterator<?> iterator = transactionCodeList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				codeMap = new HashMap<String, Object>();
				codeMap.put("transactionCode", (String)values[0]);
				codeMap.put("transactionName", (String)values[1]);
	 	       	result.add(codeMap);
	 	     }
	        return result;
		}
	   
	   @RequestMapping(value = "/paymentAdjustmentControl/getAllPaidAccountNumbers", method = RequestMethod.GET)
		public @ResponseBody List<?> getAllPaidAccountNumbers() {
			return adjustmentControlService.getAllPaidAccountNumbers();
		}
	   
	   @RequestMapping(value = "/paymentAdjustmentControl/readServiceTypes", method = RequestMethod.GET)
		public @ResponseBody List<?> readServiceTypes() {		
			return adjustmentControlService.findServiceTypes();
		}	
	   
	   @RequestMapping(value="/paymentAdjustment/adjustmentToAll")
	   public @ResponseBody String AdjustmentToAll(HttpServletRequest request ) throws NumberFormatException, Exception
	   {
		   String radio1=request.getParameter("radio1");
		   String radio2=request.getParameter("radio2");
		   String propertyId=request.getParameter("propertyId");
		   String blockId=request.getParameter("blockId");
		   String adjustmentAmount=request.getParameter("adjustmentAmount");
		   String presnetDate=request.getParameter("presnetDate");
		   System.out.println("current date ++++"+presnetDate);
		   String adjustmentType=request.getParameter("adjustmentType");
		   String ledgerType=request.getParameter("ledgerType");
		   String serviceType="";
		   if(ledgerType.equalsIgnoreCase("Electricity Ledger"))
		   {
			   serviceType="Electricity"; 
		   }
		   else if(ledgerType.equalsIgnoreCase("Gas Ledger"))
		   {
			   serviceType="Gas";
		   }
		   else if(ledgerType.equalsIgnoreCase("CAM Ledger"))
		   {
			   serviceType="CAM";
		   }
		   else if(ledgerType.equalsIgnoreCase("Water Ledger"))
		   {
			   serviceType="Water";
		   }
		   else if(ledgerType.equalsIgnoreCase("Telephone Broadband Ledger"))
		   {
			   serviceType="Telephone Broadband";
		   }
		   else if(ledgerType.equalsIgnoreCase("Solid Waste Ledger"))
		   {
			   serviceType="Solid Waste";
		   }
		   else if(ledgerType.equalsIgnoreCase("Others Ledger"))
		   {
			   serviceType="Others";
		   }
		   System.out.println("property ++++++++++"+propertyId);
		   System.out.println("blockId ++++++++++"+blockId);
		   int count=0;
		   if(radio1.equalsIgnoreCase("Block"))
		   {
			   if(radio2.equalsIgnoreCase("All"))
			   {
				  List<Integer> blocksId=adjustmentService.getAllBlocksId();
				  for (Integer blockId1 : blocksId) {
					  List<Property> properties=adjustmentService.getPropertyByBlockId(blockId1);
					  System.out.println("list of property ==+++"+properties.size());
					   for (Property property : properties) {
						   int accountId=adjustmentService.getAccountIdByPropertyId(property.getPropertyId(),serviceType);
						   if(accountId>=1)
							{
						   count++;
						   adjustmentSave(accountId,adjustmentAmount,presnetDate,adjustmentType,ledgerType);
							}
					}
				}
			   }
			   else if(radio2.equalsIgnoreCase("Select Block"))
			   {
				   String blocksIds[]=blockId.split(",");
				   for (String blockid : blocksIds) {
					   System.out.println("blocks ++"+blockid);
					   List<Property> properties=adjustmentService.getPropertyByBlockId(Integer.parseInt(blockid));
					   System.out.println("list of property ==+++"+properties.size());
					   for (Property property : properties) {
						   int accountId=adjustmentService.getAccountIdByPropertyId(property.getPropertyId(),serviceType);
						   if(accountId>=1)
							{
						   count++;
						   adjustmentSave(accountId,adjustmentAmount,presnetDate,adjustmentType,ledgerType);
							}
					}
					   System.out.println("Count ++++"+count);
				}
			   }
		   }
		   else if(radio1.equalsIgnoreCase("Property"))
		   {
			   String propertyIds[]=propertyId.split(",");
			   for (String propertyid : propertyIds) {
				int accountId=adjustmentService.getAccountIdByPropertyId(Integer.parseInt(propertyid),serviceType);
				if(accountId>=1)
				{
					count++;
				adjustmentSave(accountId,adjustmentAmount,presnetDate,adjustmentType,ledgerType);
				}
				
			}
		   }
		   if(count>0)
		   {
			   return "Successfully added "+count+" adjustments for "+ledgerType;
		   }
		   else
		   {
			   return "Failure";
		   }
		   
	   }
	   public void adjustmentSave(int accountId,String adjustmentAmount,String presentDate,String adjustementType,String ledgerType) throws Exception
	   {
		   PaymentAdjustmentEntity paymentAdjustment= new PaymentAdjustmentEntity();
		   paymentAdjustment.setAccountId(accountId);
		   paymentAdjustment.setAdjustmentAmount(Double.parseDouble(adjustmentAmount));
		   paymentAdjustment.setAdjustmentType(adjustementType);
		   paymentAdjustment.setAdjustmentLedger(ledgerType);	   
		   paymentAdjustment.setAdjustmentNo(genrateAdjustmentNumber());
		   paymentAdjustment.setPostedToGl("No");
		   paymentAdjustment.setStatus("Created");
		   paymentAdjustment.setJvDate(new Timestamp(new Date().getTime()));
		   paymentAdjustment.setAdjustmentDate(dateTimeCalender.getdateFormat(presentDate));
		   paymentAdjustment.setClearedStatus("No");
		   paymentAdjustment.setTallystatus("Not Posted");
		   adjustmentService.save(paymentAdjustment);
		   
	   }
	   
	   public String genrateAdjustmentNumber() throws Exception {  
						
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String year = sdf.format(new Date());
			int nextSeqVal = adjustmentService.autoGeneratedAdjustmentNumber();	 
			
			return "AD"+year+nextSeqVal;		   
		}
}
