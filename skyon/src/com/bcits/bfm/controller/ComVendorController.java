package com.bcits.bfm.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.Family;
import com.bcits.bfm.model.FamilyProperty;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Vendors;
import com.bcits.bfm.service.VendorsManagement.VendorsService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class ComVendorController {
	
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private VendorsService vendorsService;
	
	/*@RequestMapping("/vendors")
   	public String vendorIndex(HttpServletRequest request,HttpServletResponse response,ModelMap model)
   	{
   		model.addAttribute("ViewName", "Vendors");
   		breadCrumbService.addNode("nodeID", 0, request);
   		breadCrumbService.addNode("Manage Vendors", 1, request);
   		return "vendorManagement/vendors";
   	}*/
	
	
	@RequestMapping(value = "/person/getPersonStyleList", method = RequestMethod.GET)
    public @ResponseBody Set<?> getPersonStyleList() {
	  Set<String> result = new HashSet<String>();
	    for (String personStyles : personService.getPersonStyle())
		{
	    	
			result.add(personStyles);
		}
        return result;
    }
  
  @RequestMapping(value = "/person/getPersonTitleList", method = RequestMethod.GET)
    public @ResponseBody Set<?> getPersonTitleList() {
	  Set<String> result = new HashSet<String>();
	    for (String personTitles : personService.getPersonTitleList())
		{
	    	
			result.add(personTitles);
		}
        return result;
    }
  
  @RequestMapping(value = "/person/getPersonFirstNameList", method = RequestMethod.GET)
    public @ResponseBody Set<?> getPersonFirstNameList() {
	  Set<String> result = new HashSet<String>();
	    for (String personFstNames : personService.getPersonFirstName())
		{
	    	
			result.add(personFstNames);
		}
        return result;
    }
  @RequestMapping(value = "/person/getPersonMiddleNameList", method = RequestMethod.GET)
    public @ResponseBody Set<?> getPersonMiddleNameList() {
	  Set<String> result = new HashSet<String>();
	    for (String personMiddleNames : personService.getPersonMiddleName())
		{
	    	
			result.add(personMiddleNames);
		}
        return result;
    }
  @RequestMapping(value = "/person/getPersonLastNameList", method = RequestMethod.GET)
    public @ResponseBody Set<?> getPersonLastName() {
	  Set<String> result = new HashSet<String>();
	    for (String personLastNames : personService.getPersonLastName())
		{
	    	
			result.add(personLastNames);
		}
        return result;
    }
  @RequestMapping(value = "/person/getLanguage", method = RequestMethod.GET)
    public @ResponseBody Set<?> getLanguage() {
	  Set<String> result = new HashSet<String>();
	    for (String personLanguages : personService.getLanguage())
		{
	    	
			result.add(personLanguages);
		}
        return result;
    }
  
  @RequestMapping("/comvendors/cu")
	public @ResponseBody Object CreateUpdateComVendors(@RequestBody Map<String, Object> map,HttpServletRequest request,
			HttpServletResponse response,ModelMap model,@ModelAttribute("person") Person person, 
			BindingResult bindingResult,final Locale locale)
	{
	  
	 
		String operation = request.getParameter("action");
	
		Family obj = null;
		if(operation.equalsIgnoreCase("create"))
		{
			
			person = personService.getPersonObject(map, "save", person);
			
			validator.validate(person, bindingResult);

			JsonResponse errorResponse = new JsonResponse();
			
		

			if (bindingResult.hasErrors()) {
				errorResponse.setStatus("FAIL");
				errorResponse.setResult(bindingResult.getAllErrors());
				

				return errorResponse;
			}
			else{
				
				personService.save(person);
			}
			
		}
		else if(operation.equalsIgnoreCase("update"))
		{
			person = personService.getPersonObject(map, "update", person);
			
			
			validator.validate(person, bindingResult);

			JsonResponse errorResponse = new JsonResponse();
			
		
			if (bindingResult.hasErrors()) {
				errorResponse.setStatus("FAIL");
				errorResponse.setResult(bindingResult.getAllErrors());
		
				return errorResponse;
			}
			
			else{
				
				person = personService.update(person);
		}
		}
		return person;
	}
  
  @RequestMapping(value = "/comvendors/vendor/read/{personId}", method = { RequestMethod.GET, RequestMethod.POST } )
 	public @ResponseBody List<?> readVendorDetails(@PathVariable int personId) 
 	{
	  List<Map<String, Object>> vendors =  new ArrayList<Map<String, Object>>(); 
		
		for (final Vendors record : (List<Vendors>) vendorsService.findAllVendorBasedOnPersonID(personId) ) 
      {
			
			vendors.add(new HashMap<String, Object>() {{
				put("vendorId" ,record.getVendorId());
				put("personId" ,record.getPersonId());
				/*put("vendorCategoryList" ,record.getVendorCategoryList());*/
				put("cstNo" ,record.getCstNo() );
				put("stateTaxNo",record.getStateTaxNo());
				put("serviceTaxNo" , record.getServiceTaxNo());
				put("status" , record.getStatus());
				
			}});
      }
		
		return vendors;
 	}	
  
  
  @RequestMapping(value="/comvendors/vendor/create/{personId}")
  public @ResponseBody Object CreateVendorDetails(@PathVariable int personId,@RequestBody Map<String, Object> map,
  		HttpServletRequest request,HttpServletResponse response,ModelMap model,
  		@ModelAttribute("vendors") Vendors vendors,SessionStatus sessionStatus)
  {
  	
  	
  	JsonResponse errorResponse = new JsonResponse();
  	
  	vendors = vendorsService.getVendorsObject(map, "save", vendors,personId);
  	
  
  	vendorsService.save(vendors);
  			return vendors;
  		
  }
  

@RequestMapping("/comvendors/vendor/update/{personId}")
public @ResponseBody Object updateFamilyProperty(@PathVariable int personId,@RequestBody Map<String, Object> map,
		HttpServletRequest request,HttpServletResponse response,ModelMap model,
		@ModelAttribute("vendors") Vendors vendors)
{
	vendors = vendorsService.getVendorsObject(map, "update", vendors,personId);
	vendorsService.update(vendors);
	return vendors;
}



@RequestMapping(value = "/users/getVendors", method = RequestMethod.GET)
public @ResponseBody
List<?> getVendors(HttpServletRequest request) {

	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();  
	for (final Vendors vendor : vendorsService.findAll()) {
		result.add(new HashMap<String, Object>() {{
			put("vendorName",vendor.getPerson().getFirstName()+ " " +vendor.getPerson().getLastName());
			put("vendorId",vendor.getVendorId());
		}});
	}
	return result;
}
  	
}
