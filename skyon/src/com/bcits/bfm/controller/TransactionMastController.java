package com.bcits.bfm.controller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.bcits.bfm.patternMasterEntity.TransactionDetail;
import com.bcits.bfm.patternMasterEntity.TransactionMaster;
import com.bcits.bfm.patternMasterService.ProcessMasterService;
import com.bcits.bfm.patternMasterService.TransactionDetailService;
import com.bcits.bfm.patternMasterService.TransactionMasterService;
import com.bcits.bfm.service.GenericService;






@Controller
public class TransactionMastController {
	
	private static final TransactionMaster TransactionMaster = null;

	
	
	
		@Autowired
		private TransactionMasterService transactionMasterService;
		
		@Autowired
		private MessageSource messageSource;
		
		@Autowired
		private TransactionDetailService transactionDetailService;
		
		@Autowired
		private ProcessMasterService processMasterService;
		
		//Create Department
		@RequestMapping(value = "/transactionmaster/createTransactionMasterUrl", method = RequestMethod.GET)
		public @ResponseBody Object createTransactionMaster(@ModelAttribute("transactionMaster") TransactionMaster transactionMaster, ModelMap model, final Locale locale,HttpServletRequest req) throws InterruptedException 
		{
			
			return transactionMasterService.save(transactionMaster);
		
		}
		
		@RequestMapping(value = "/transactionmaster/readTransactionMasterUrl", method = RequestMethod.POST)
		public @ResponseBody List<?> readTransactionMasterUrl(Model model, HttpServletRequest request,final Locale locale)
		{
			HttpSession session= request.getSession(false);
			
			return transactionMasterService.findAll();
		}
		@RequestMapping(value = "/transaction/readDesignationUrl/{tId}", method = RequestMethod.GET)
		public @ResponseBody List<?> StoreReadUrl() 
		{
			return transactionMasterService.findAllDesig();
		} 
		
		@RequestMapping(value = "/transaction/readDepartmentUrl/{tId}", method = RequestMethod.GET)
		public @ResponseBody List<?> DeptReadUrl() 
		{
			return transactionMasterService.findAllDept();
		} 
		
		
		@RequestMapping(value = "/transactionmaster/updateTransactionMasterUrl", method = RequestMethod.GET)
		public @ResponseBody Object UpdateUrl(@ModelAttribute("transactionMaster") TransactionMaster transactionMaster) 
		{
			return transactionMasterService.update(transactionMaster);
		} 
		
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/transactionchild/createTransctinUrl1/{tId}",method=RequestMethod.GET)
		public @ResponseBody Object createAllData(@ModelAttribute("transactionDetail")TransactionDetail transactionDetail,@PathVariable int tId,BindingResult bindingResult,ModelMap model,HttpServletRequest req,HttpServletResponse response, SessionStatus sessionStatus,final Locale locale)throws Exception 
		{
			
		
			transactionDetail.settId(tId);
			
			int child_level=0;
			
			List<?> list=transactionDetailService.findAll(tId);
			for(Iterator<?> itr=list.iterator();itr.hasNext();)
			{
				HashMap<String, Object> hasmap=(HashMap<String, Object>) itr.next();
				child_level=(int) hasmap.get("lNum");
			
			}
			if(child_level>=0)
			{
				child_level=child_level+1;
				transactionDetail.setlNum(child_level);

				  transactionDetailService.save(transactionDetail);
			}
			
			
	
			  return transactionDetail; 
			}
		

		@RequestMapping(value="/transactionchild/updateTransctionUrl1/{tId}",method=RequestMethod.GET)
		public @ResponseBody Object updateAllData(@ModelAttribute("transactionDetail")TransactionDetail transactionDetail,@PathVariable int tId,BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale)throws Exception 
		{
			
			transactionDetail.settId(tId);
			
			transactionDetailService.update(transactionDetail);
			
			return transactionDetail;
		}
		@RequestMapping(value = "/transaction/readAllProcessUrl", method = RequestMethod.GET)
		public @ResponseBody List<?> readAllProcessUrl(Model model,HttpServletRequest request,HttpServletResponse response,ModelMap model1) 
		{
			
			return processMasterService.readAllProcessName();
		}
		@RequestMapping(value="/transactionchild/readTransctinUrl1/{tId}",method=RequestMethod.GET)
		public @ResponseBody List<?> readAllTransctionData(@PathVariable int tId)
		{
			return transactionDetailService.findAll(tId);
		}
		@RequestMapping(value="/commonss/getAllTransactionUniqueness",method=RequestMethod.GET)
		public @ResponseBody List<?> getAllTransactionUniqueness(Model model,HttpServletRequest request,HttpServletResponse response,ModelMap model1)
		{
			return transactionDetailService.getAllTransactionUniqueness();
		}
	
		

		@RequestMapping(value = "/transaction/dropdownForProcessName/{attribute}", method = RequestMethod.GET)
		public @ResponseBody List<Map<String, String>> getAllChecks(@PathVariable String attribute, final Locale locale) 
		{
			String[] str = messageSource.getMessage(attribute, null, locale).split(",");
			List<String> list = new ArrayList<String>();
			Collections.addAll(list, str);
			
			List<Map<String, String>> result = new ArrayList<Map<String, String>>();
			Map<String, String> map = null;
			for (Iterator<String> iterator = list.iterator(); iterator.hasNext();)
			{
				String string = (String) iterator.next();
				map = new HashMap<String, String>();
				map.put("value", string);
				map.put("text", string);
				result.add(map);
			}
			return result;
		 }
		 @RequestMapping(value = "/common/getAllProcessNames/{attribute}", method = RequestMethod.GET)
		 public @ResponseBody List<Map<String, String>> getAllChec(@PathVariable String attribute, final Locale locale) 
		 {
		  String[] str = messageSource.getMessage(attribute, null, locale).split(",");
		  List<String> list = new ArrayList<String>();
		  Collections.addAll(list, str);
		 
		  List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		  Map<String, String> map = null;
		  for (Iterator<String> iterator = list.iterator(); iterator.hasNext();)
		  {
		   String string = (String) iterator.next();
		   map = new HashMap<String, String>();
		   map.put("value", string);
		   map.put("text", string);
		   result.add(map);
		  }
		  return result;
		 }
	

		
		
		
		@RequestMapping(value = "/commonss/getAllChecks/{attribute}", method = RequestMethod.GET)
		public @ResponseBody List<Map<String, String>> readAccessCardTypes(@PathVariable String attribute, final Locale locale) 
		{
			String[] str = messageSource.getMessage(attribute, null, locale).split(",");
			List<String> list = new ArrayList<String>();
			Collections.addAll(list, str);
			
			List<Map<String, String>> result = new ArrayList<Map<String, String>>();
			Map<String, String> map = null;
			for (Iterator<String> iterator = list.iterator(); iterator.hasNext();)
			{
				String string = (String) iterator.next();
				map = new HashMap<String, String>();
				map.put("value", string);
				map.put("text", string);
				result.add(map);
			}
			return result;
		}
		@RequestMapping(value ="/commonss/getAllTransactionNames/{processName}",  method = {RequestMethod.GET, RequestMethod.POST})
		public @ResponseBody List<?> readAllTransactionUrl(Model model,@PathVariable String processName,HttpServletRequest request,HttpServletResponse response,ModelMap model1) 
		{
			String name="Manpower process";
			List<TransactionMaster> transactionMasters=transactionMasterService.findall(name);
		
			return transactionMasters;
		}
		@RequestMapping(value = "/patternMaster/readProcessNameForUniqueness", method = RequestMethod.GET)
		  public @ResponseBody List<?> readProcessNameForUniqueness() 
		  {  
		   return transactionMasterService.readProcessNameForUniqueness();
		  }
		
		@RequestMapping(value = "/transaction/transactionStatus/{tId}/{operation}",method = {RequestMethod.POST,RequestMethod.GET})
		public String updateItemStatus(@PathVariable int tId,@PathVariable String operation,ModelMap model, HttpServletRequest request,HttpServletResponse response, final Locale locale)
		{
			transactionMasterService.setItemStatus(tId, operation, response, messageSource, locale);
			return null;
		}
		
}
