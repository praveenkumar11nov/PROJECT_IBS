package com.bcits.bfm.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.service.tariffManagement.TariffCalculationService;

@Controller
public class ApiForPayTmAppController 
{
	/*@Autowired
	TariffCalculationService tariffCalculationService;
	
	private static final Log logger = LogFactory.getLog(ApiForPayTmAppController.class);
	
	@RequestMapping(value="/PayTmApi/validationApiForCustomer",method={RequestMethod.POST,RequestMethod.GET}, produces={"application/json; charset=UTF-8"})
	public @ResponseBody Object validationApiForPayTmApp(HttpServletRequest request)
	{
		    logger.info("*****************inside validationApiForPayTmApp()*******************");
		    int siteId = Integer.parseInt(request.getParameter("site_Id"));
		    String meterNumber = request.getParameter("unique_No");
			return tariffCalculationService.getValidMeterDetails(siteId,meterNumber);	
	}
	
	@RequestMapping(value="/PayTmApi/postThePayment",method={RequestMethod.POST,RequestMethod.GET}, produces={"application/json; charset=UTF-8"})
	public @ResponseBody Object paymentApiForPayTmApp(HttpServletRequest request) throws Exception
	{
		    logger.info("*****************inside paymentApiForPayTmApp()*******************");
		    String uniqueID = request.getParameter("uniqueID");
		    String paymentDate = request.getParameter("payment_Date");
		    String paymentTime = request.getParameter("payment_Time");
		    String siteId = request.getParameter("siteId");
		    String uniqueTxnId = request.getParameter("uniqueTxnId");
		    String amountPayable = request.getParameter("amountPayable");
		    
			return tariffCalculationService.postThePayment(uniqueID,paymentDate,paymentTime,siteId,uniqueTxnId,amountPayable);	
	}
	
	
	@RequestMapping(value="/PayTmApi/postThePayment",method={RequestMethod.POST,RequestMethod.GET}, produces={"application/json; charset=UTF-8"})
	public @ResponseBody Object paymentStatusCheck(HttpServletRequest request) throws Exception
	{
		    logger.info("*****************inside paymentApiForPayTmApp()*******************");
		    String uniqueID = request.getParameter("Unique_id");
		    String txnid = request.getParameter("unique_id");
		    
		    System.err.println("uniqueID "+uniqueID +"txnid "+txnid);
		    
			return tariffCalculationService.paymentStatusCheck(uniqueID,txnid);	
	}
	*/
	
	
	
}