package com.bcits.bfm.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bcits.bfm.model.OnlinePaymentTransactions;



@Controller
public class PaymentFailureController {
	final static Logger logger = LoggerFactory.getLogger(PrePaidPaymentController.class);
	
	@PersistenceContext(unitName="bfm")
	protected EntityManager entityManager;

 @RequestMapping(value="/onlinePaymentFailure")
public String paymentIndex(Model model)
{
	model.addAttribute("onlinePayment", "OnlinePaymentTransaction");
	return "onlinePaymentFailure";
}


/******************************************** Online Payment Read Method ***************************************************************/

@RequestMapping(value="/onlinePayment/onlinePaymentFailureReadUrl",method={RequestMethod.GET,RequestMethod.POST})
public @ResponseBody List<?> onlinepaymentReadMethod(HttpServletRequest request)throws ParseException
{

	String fromDate=request.getParameter("fromDate");
	System.out.println("From date from jsp page----------------"+fromDate);
	

	
	List<Map<String,Object>> resultList= new ArrayList<>();
	Map<String,Object> mapList=null;
	

	String query="select * from ONLINE_PAYMENT_TRANSACTIONS "
			+ " where  TRUNC(CREATED_DATE)=to_date('"+fromDate+"','dd/MM/yyyy') and  UPPER(PAYMENT_STATUS)  like '%FA%'";

	List<?> paymentList=entityManager.createNativeQuery(query).getResultList();
	System.out.println("List of values from query......---------........"+paymentList.size());
	
	for (Iterator<?> iterator = paymentList.iterator(); iterator.hasNext();)
	{
		final Object[] values = (Object[]) iterator.next();
	
	    mapList = new HashMap<String, Object>();
		mapList.put("merchantId",	         values[1]);	
		mapList.put("payumoney_id",			 values[2]);	
		mapList.put("transaction_id",		 values[3]);		
	    mapList.put("tx_amount",			 values[4]);		
		mapList.put("service_type", 		 values[6]);	
		mapList.put("cbid", 			     values[7]);	
		mapList.put("created_date",	String.valueOf(values[8]));	
	    mapList.put("payment_status",        values[9]);	
	  
	    resultList.add(mapList);
	}
	
	return resultList;
}

/***********************************************************************************************************/

}
