/*package com.bcits.bfm.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;


@Controller
public class TransactionDetailsController {
	

	@Autowired
	private DesignationService designationService;

	@Autowired
	private TransactionMasterService transactionMasterService;
	
	@Autowired
	private TransactionDetailService transctionDetailservice;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private GenericDAO genericDAO;
	
	Logger logger=LoggerFactory.getLogger(TransactionDetailsController.class);
	@RequestMapping(value="transactionDetail",method=RequestMethod.GET)
	public String index()
	{
		return "pettyCash/TransactionChild";
	}
	
	@RequestMapping(value="/transaction/readDesignationUrl/{tId}/{id}",method=RequestMethod.GET)
	public @ResponseBody List<?> getAllDesignationNames(@PathVariable int tId,@PathVariable int id,HttpServletRequest request)
	{
		
		logger.info("inside a Transction details............."+tId);
		//int tId= Integer.parseInt(request.getParameter("tId"));
		HttpSession session= request.getSession(false);
		int officeId=(int)session.getAttribute("officeId");
		return transactionMasterService.findAllDesignations(tId,officeId);
	}
	
	@RequestMapping(value="/transction/readALLTransactionCodeUrl",method=RequestMethod.GET)
	public @ResponseBody List<?> getAllTransactionCode(HttpServletRequest request)
	{
		HttpSession session= request.getSession(false);
		int officeId=(int)session.getAttribute("officeId");
		logger.info("inside a Transction details.............");
		return transactionMasterService.findAll(officeId);
	}
	
	@RequestMapping(value="/transactionchild/readTransctinUrl1/{tId}",method=RequestMethod.GET)
	public @ResponseBody List<?> readAllTransctionData(@PathVariable int tId)
	{
		return transctionDetailservice.findAll(tId);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/transactionchild/createTransctinUrl1/{tId}",method=RequestMethod.GET)
	public @ResponseBody Object createAllData(@ModelAttribute("transactionDetail")TransactionDetail transactionDetail,@PathVariable int tId,BindingResult bindingResult,ModelMap model,HttpServletRequest req,HttpServletResponse response, SessionStatus sessionStatus,final Locale locale)throws Exception 
	{
		int desigId=transactionDetail.getDnId();
		
	System.out.println("==========================="+desigId);
	Object[] obj1=genericDAO.executeSingleObjectQuery("select count(*),count(*) from TransactionDetail t where t.tId="+tId+"and t.dnId="+desigId);
		Long count1=(Long)obj1[0];
		System.out.println(count1);
		
		if(count1==0)
		{
	
		int child_level=0;
		//int parent_level=0;
		//transctionDetailservice.findLevel(tId);
		List<?> list=transctionDetailservice.findAll(tId);
		for(Iterator<?> itr=list.iterator();itr.hasNext();)
		{
			HashMap<String, Object> hasmap=(HashMap<String, Object>) itr.next();
			child_level=(int) hasmap.get("lNum");
			logger.info("child level"+child_level);
		}
		if(child_level>=0)
		{
			child_level=child_level+1;
			transactionDetail.setlNum(child_level);
		}
		
		
		List<?> parentlist=transactionMasterService.findLevel(tId);
		for(Iterator<?> itr=list.iterator();itr.hasNext();)
		{
			HashMap<String, Object> phasmap=(HashMap<String, Object>) itr.next();
			parent_level=(int) phasmap.get("level");
			logger.info("parent level "+parent_level);
		}
		    
		  transactionDetail.settId(tId);
		
		transctionDetailservice.save(transactionDetail);
		  return transactionDetail; 
		}
		
		
		
	   
	  
		   
		   
		   
		   
	   
	   
	 
	
	
	@RequestMapping(value="/transactionchild/updateTransctionUrl1/{tId}",method=RequestMethod.GET)
	public @ResponseBody Object updateAllData(@ModelAttribute("transactionDetail")TransactionDetail transactionDetail,@PathVariable int tId,BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale)throws Exception 
	{
		
		transactionDetail.settId(tId);
		
		transctionDetailservice.update(transactionDetail);
		
		return transactionDetail;
	}
	
	@RequestMapping(value="/transactionchild/deleteTransctionUrl1/{tId}",method=RequestMethod.GET)
	public @ResponseBody Object deleteAllData(@ModelAttribute("transactionDetail")TransactionDetail transactionDetail,@PathVariable int tId,BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale)throws Exception 
	{
		
		//transactionDetail.settId(tId);
		
		
		transctionDetailservice.delete(transactionDetail);

		
		return transactionDetail;
	}
	
@RequestMapping(value="/read/designationUniqueNess/{tId}",method=RequestMethod.GET)
public @ResponseBody List<?> getAllDesignationId(@PathVariable int tId)
{
	return transctionDetailservice.getAllDesignationId(tId);
}
	
}
*/