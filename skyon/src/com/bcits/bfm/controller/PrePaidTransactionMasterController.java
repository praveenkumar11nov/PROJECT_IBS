package com.bcits.bfm.controller;
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

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.bcits.bfm.model.AdvancePaymentEntity;
import com.bcits.bfm.model.PrepaidCalculationBasisEntity;
import com.bcits.bfm.model.PrepaidServiceMaster;
import com.bcits.bfm.model.PrepaidTxnChargesEntity;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.PreCalculationBasisService;
import com.bcits.bfm.service.PrepaidServiceMasterSs;
import com.bcits.bfm.service.PrepaidTransactionService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;

@Controller
public class PrePaidTransactionMasterController {
	
	final static Logger logger=LoggerFactory.getLogger(PrePaidTransactionMasterController.class);
	
	@Autowired
	private PrepaidTransactionService transactionService;
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private PrepaidServiceMasterSs prepaidServiceMasterSs;
	
	@Autowired
	private PreCalculationBasisService pBasisService;
	
	@RequestMapping(value="/prePaidTransactionMaster")
	public String transactionMasterIndex(){
		logger.info("in side transactionMasterIndex methode");
		
		return "prepaid_Transaction_Master";
	}
	
	@RequestMapping(value="/prepaidCharges/createUrl/{serviceId}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object saveCalculation(@PathVariable int serviceId,@ModelAttribute("prepaidTxnChargesEntity") PrepaidTxnChargesEntity prepaidTxnChargesEntity,HttpServletRequest request) throws ParseException{
		logger.info("in side save methode "+serviceId+" "+prepaidTxnChargesEntity.getRate());
		
		prepaidTxnChargesEntity.setSid(serviceId);
		prepaidTxnChargesEntity.setCharge_Type(request.getParameter("charge_Type"));
		prepaidTxnChargesEntity.setcId(transactionService.getcbId(serviceId));
		
		
		  String vfdate=request.getParameter("validFrom");
		  String vtdate=request.getParameter("validTo");
		
		  
		  SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH);
	        
	        Date date1=formatter.parse(vfdate);
	        Date date2=formatter.parse(vtdate);
	        
//		  Date frDate=new SimpleDateFormat("dd/MM/yyyy").parse(vfdate);
//		  Date todate=new SimpleDateFormat("dd/MM/yyyy").parse(vtdate);
		  
		  prepaidTxnChargesEntity.setValidFrom(date1);
		  prepaidTxnChargesEntity.setValidTo(date2);  
		  
		  
		return transactionService.save(prepaidTxnChargesEntity);
	}
	@RequestMapping(value="/prepaidCharges/updateUrl/{serviceId}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object updatePrePaidCharges(@ModelAttribute("prepaidTxnChargesEntity") PrepaidTxnChargesEntity prepaidTxnChargesEntity,@PathVariable int serviceId){
		prepaidTxnChargesEntity.setSid(serviceId);
		prepaidTxnChargesEntity.setcId(transactionService.getcbId(serviceId));
		return transactionService.update(prepaidTxnChargesEntity);
	}
	
	@RequestMapping(value="/prepaidCharges/destroyUrl",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object deletePrePaidCharges(HttpServletRequest request,@ModelAttribute("prTxnChargesEntity") PrepaidTxnChargesEntity prTxnChargesEntity,BindingResult bindingResult,SessionStatus ss){
		
		int id=Integer.parseInt(request.getParameter("transactionId"));
		System.err.println("id "+id);
		try{
		transactionService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
		}
		ss.setComplete();
	 return	prTxnChargesEntity;
	}
	
	@RequestMapping(value="/prepaidCharges/readUrl/{serviceId}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<PrepaidTxnChargesEntity> readPrePaidCharges(@PathVariable int serviceId,HttpServletRequest request){
		logger.info("in sdie read");
		List<PrepaidTxnChargesEntity> prepaidTxnChargesEntities=transactionService.readAllData(serviceId);
		System.out.println(prepaidTxnChargesEntities);
		return prepaidTxnChargesEntities;
	}
	
	@RequestMapping(value = "/prepaidCharges/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody Set<?> filterForPrepaidCharges(@PathVariable String feild) {
		logger.info("In  transaction master use case filters Method");
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("PrepaidTxnChargesEntity",attributeList, null));
		
		return set;
	}
	
	/*@RequestMapping(value="/services")
	public String serviceIndex(){
		return "services";
	}*/
	
	@RequestMapping(value="/prepaidservices/createUrl",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object saveServices(@ModelAttribute("prepaidServiceMaster") PrepaidServiceMaster prepaidServiceMaster){
		
		return prepaidServiceMasterSs.save(prepaidServiceMaster);
	}
	
	@RequestMapping(value="/prepaidservices/readUrl",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object readServices(){
		
		return prepaidServiceMasterSs.getAllServiceNames();
	}
	
	@RequestMapping(value="/prepaidservices/serviedestroyUrl",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object upadteService(@ModelAttribute("prepaidServiceMaster") PrepaidServiceMaster prepaidServiceMaster, BindingResult bindingResult,Model model,SessionStatus ss){
		JsonResponse errorResponse=new JsonResponse();
		if(prepaidServiceMaster.getStatus().equalsIgnoreCase("Active")){
			errorResponse.setStatus("ActiveStatuserr");
			errorResponse.setResult("Active service  details cannot be deleted");
			return errorResponse;
		}else{
			try{
				prepaidServiceMasterSs.delete(prepaidServiceMaster.getServiceId());
			}catch(Exception e){
				errorResponse.setStatus("CHILD");
				return errorResponse;
			}
			ss.setComplete();
			return prepaidServiceMaster;
		}
		
	}
	
	@RequestMapping(value = "/prepaidservices/serviceStatus/{serviceId}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
	public void serviceMasterStatus(@PathVariable int serviceId,@PathVariable String operation, HttpServletResponse response) {
		logger.info("In serviceMaster Status Change Method");
		if (operation.equalsIgnoreCase("activate"))
			prepaidServiceMasterSs.setServiceStatus(serviceId,operation, response);
		else
			prepaidServiceMasterSs.setServiceStatus(serviceId,operation, response);
	}
	
	@RequestMapping(value="/prepaidservices/calculationCreateUrl/{serviceId}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object saveCalculation(@PathVariable int serviceId,@ModelAttribute("prBasisEntity") PrepaidCalculationBasisEntity prBasisEntity,HttpServletRequest request){
		logger.info("in side save methode "+serviceId);
	  List<?> data=pBasisService.getData(serviceId);
	  if(data.isEmpty()){
		prBasisEntity.setCbName(prBasisEntity.getCbName().toUpperCase());
		prBasisEntity.setsId(serviceId);
		return pBasisService.save(prBasisEntity);
	  }else{
		  JsonResponse erResponse=new JsonResponse();
		  erResponse.setStatus("ONEENTRY");
		  return erResponse;
	  }
	}
	
	@RequestMapping(value="/prepaidservices/calculationReadUrl/{serviceId}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object readCalculation(@PathVariable int serviceId){
		logger.info("in side read "+serviceId);
		return pBasisService.readData(serviceId);
	}
	
	@RequestMapping(value="/serviceChargeCalcu/getServiceNamesUrl",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> readAllService(){
		
		List<Map<String, Object>> resultList=new ArrayList<>();
		 Map<String, Object> mapList=null;
		 List<?> sList=prepaidServiceMasterSs.readAllServices();
		 for(Iterator<?> iterator=sList.iterator();iterator.hasNext();){
			 final Object[] value=(Object[]) iterator.next();
			 mapList=new HashMap<>();
			 mapList.put("serviceId", value[0]);
			 mapList.put("service_Name", value[1]);
			 resultList.add(mapList);
		 }
		 return resultList;
	}
	
	@RequestMapping(value="/prepaidservices/calculationupdateUrl/{serviceId}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object editAdvancePayment(@ModelAttribute("prepaidCalculationBasisEntity") PrepaidCalculationBasisEntity prepaidCalculationBasisEntity,BindingResult bindingResult,@PathVariable int serviceId, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {
		System.out.println(serviceId+"==========================");
		System.out.println(prepaidCalculationBasisEntity.getCid()+"==========================CID");
		System.out.println(prepaidCalculationBasisEntity.getCbName()+"==========================");
		logger.info("in side update methode ");
		prepaidCalculationBasisEntity.setCbName(prepaidCalculationBasisEntity.getCbName().toUpperCase());
		System.out.println(" ****** "+prepaidCalculationBasisEntity.getCid()+prepaidCalculationBasisEntity.getCbName()+prepaidCalculationBasisEntity.getsId());
		prepaidCalculationBasisEntity.setsId(serviceId);
		return pBasisService.update(prepaidCalculationBasisEntity);
	}
}
