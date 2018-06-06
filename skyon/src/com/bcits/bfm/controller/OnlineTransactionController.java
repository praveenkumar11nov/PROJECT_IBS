package com.bcits.bfm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.OnlinePaymentTransactions;
import com.bcits.bfm.service.OnlinePaymentTransactionsService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class OnlineTransactionController {
	
	static Logger logger = LoggerFactory
			.getLogger(OnlineTransactionController.class);
	@Autowired
	private OnlinePaymentTransactionsService onlinePaymentTransactionsService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@RequestMapping(value = "/onlineTransaction",method={ RequestMethod.GET, RequestMethod.POST })
	public String onlinetransaction(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Online Transaction Payments");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Bills Generation", 1, request);
		breadCrumbService.addNode("Online Transaction Payments", 2, request);
		return "servicetasks/onlineTransaction";
	}


	@SuppressWarnings({ "unchecked", "serial" })
	@RequestMapping(value = "/onlineTransaction/billRead", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody List<?> readOnlineTransaction()
		{
	    	logger.info("In Online Transaction payment");
	    	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	    	Calendar c = Calendar.getInstance();
	    	Date date1=c.getTime();	    	
	    	c.setTime(date1);
	    	c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));	    	
	    	String fromDate=DateFormatUtils.format((Date)c.getTime(),"yyyy/MM/dd");
	    	Date date2=c.getTime();
	    	c.setTime(date2);
	    	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
	    	String toDate=DateFormatUtils.format((Date)c.getTime(),"yyyy/MM/dd");
			List<Object[]> onlinePaymentTransaction=(List<Object[]>) onlinePaymentTransactionsService.getTransactionDetailsByAccountId(fromDate,toDate);
	    	System.out.println("list size--------------------------------"+onlinePaymentTransaction.size());
			for (final Object[] obj : onlinePaymentTransaction) {
			
				result.add(new HashMap<String, Object>() {
					{
						put("serviceType", (String)obj[1]);
					    put("transactionDate",  DateFormatUtils.format((Date) obj[4],
					    		
								"MM/dd/yyyy"));
					    put("paymentStatus", (String)obj[3]);
					    put("transactionAmount", (String)obj[5]);
					    
					    put("payumoneyId",(String)obj[0]);
					   put("TransactionId",(String)obj[2]);
					   put("personName",(String)obj[7]+" "+(String)obj[8]);
					   put("accountNo",(String)obj[9]);
					   put("propertyNo",(String)obj[6]);
					   put("onlineid",(Integer)obj[10]);
					   System.out.println((Integer)obj[10]);
					 
					}
				});

			}
			return result;
		}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/onlineTransaction/byMonth/{from}/{to}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> readOnlineTransactionByMonth(@PathVariable String from ,@PathVariable String to) throws ParseException
	{
    	logger.info("In Online Transaction payment by month ======================");
    	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    	SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
    	Date dateFrom = formatter.parse(from);
    	Date dateTo = formatter.parse(to);
    	String fromDate=DateFormatUtils.format((Date)dateFrom,"yyyy/MM/dd");
    	Calendar c = Calendar.getInstance();
    	c.setTime(dateTo);
    	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
    	String toDate=DateFormatUtils.format((Date)c.getTime(),"yyyy/MM/dd");
		List<Object[]> onlinePaymentTransaction=(List<Object[]>) onlinePaymentTransactionsService.getTransactionDetailsByAccountId(fromDate,toDate);
		for (final Object[] obj : onlinePaymentTransaction) {
			result.add(new HashMap<String, Object>() {
				{
					put("serviceType", (String)obj[1]);
				    put("transactionDate",  DateFormatUtils.format((Date) obj[4],
							"dd/MM/yyyy"));
				    put("paymentStatus", (String)obj[3]);
				    put("transactionAmount", (String)obj[5]);
				    
				    put("payumoneyId",(String)obj[0]);
				   put("TransactionId",(String)obj[2]);
				   put("personName",(String)obj[7]+" "+(String)obj[8]);
				   put("accountNo",(String)obj[9]);
				   put("propertyNo",(String)obj[6]);
				   put("onlineid",(Integer)obj[10]);
				}
			});

		}
		return result;
	}
	
	@RequestMapping(value = "/filterForAccountNumber", method = RequestMethod.GET)
	public @ResponseBody Set<String> commonFilterForAccountNumbersUrl() {
		return onlinePaymentTransactionsService.commonFilterForAccountNumbersUrl();
		
	}

	@RequestMapping(value = "/filterForPropertyNumber", method = RequestMethod.GET)
	public @ResponseBody Set<String> commonFilterForPropertyNumbersUrl() {
		
		return onlinePaymentTransactionsService.commonFilterForPropertyNumbersUrl();
		
	}
	
	@RequestMapping(value = "/filterForPaymentStatus", method = RequestMethod.GET)
	public @ResponseBody Set<String> commonFilterForPaymentStatusUrl() {
		
		return onlinePaymentTransactionsService.commonFilterForPaymentStatusUrl();
		
	}
	

	@RequestMapping(value = "/filterForPayUMoneyId", method = RequestMethod.GET)
	public @ResponseBody Set<String> commonFilterForPayUMoneyIdUrl() {
		
		return onlinePaymentTransactionsService.commonFilterForPayUMoneyIdUrl();
		
	}
	
	@RequestMapping(value = "/filterForTransactionId", method = RequestMethod.GET)
	public @ResponseBody Set<String> commonFilterForTransactionIdUrl() {
		
		return onlinePaymentTransactionsService.commonFilterForTransactionIdUrl();
		
	}
	
	@RequestMapping(value = "/filterForServiceType", method = RequestMethod.GET)
	public @ResponseBody Set<String> commonFilterForServiceTypeUrl() {
		
		return onlinePaymentTransactionsService.commonFilterForServiceTypeUrl();
		
	}
}
