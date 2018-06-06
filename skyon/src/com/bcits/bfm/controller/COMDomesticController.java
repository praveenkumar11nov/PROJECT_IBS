package com.bcits.bfm.controller;

import java.sql.Timestamp;
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

import org.apache.log4j.Logger;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.bcits.bfm.model.Domestic;
import com.bcits.bfm.model.DomesticProperty;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.TenantProperty;
import com.bcits.bfm.service.customerOccupancyManagement.DomesticPropertyService;
import com.bcits.bfm.service.customerOccupancyManagement.DomesticService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.customerOccupancyManagement.TenantSevice;

/**
 * Controller which includes all the business logic concerned to this module
 *
 * 
 * @author Pooja.K
 * @version %I%, %G%
 * @since 0.1
 */
@Controller
public class COMDomesticController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private DomesticService domesticService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private TenantSevice tenantSevice;
	
	@Autowired
	private DomesticPropertyService domesticPropertyService;
	
	@Autowired
	private PropertyService propertyService;
	
	 @RequestMapping("/comdomestichelp")
	   	public String familyIndex(HttpServletRequest request,HttpServletResponse response,ModelMap model, final Locale locale)
	   	{
		    model.addAttribute("ViewName", "Customer Occupancy");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Customer Occupancy", 1, request);
	   		breadCrumbService.addNode("Manage Domestic", 2, request);
	   		
	   		model.addAttribute("personStyle", commonController.populateCategories("personStyle", locale));
			model.addAttribute("title", commonController.populateCategories("title", locale));
			model.addAttribute("sex", commonController.populateCategories("sex", locale));
			model.addAttribute("bloodGroup", commonController.populateCategories("bloodGroup", locale));
			model.addAttribute("status", commonController.populateCategories("values.project.status", locale));
			model.addAttribute("meCategory", commonController.populateCategories("meCategory", locale));
	   		
	   		return "com/domestic";
	   	}
	 
	 @RequestMapping(value = "/comdomestichelp/getPersonStyleList", method = RequestMethod.GET)
	    public @ResponseBody Set<?> getPersonStyleList() {
		  Set<String> result = new HashSet<String>();
		    for (String personStyles : domesticService.getPersonStyle())
			{
				result.add(personStyles);
			}
	        return result;
	    }
	  
	  @RequestMapping(value = "/comdomestichelp/getPersonTitleList", method = RequestMethod.GET)
	    public @ResponseBody Set<?> getPersonTitleList() {
		  Set<String> result = new HashSet<String>();
		    for (String personTitles : domesticService.getPersonTitleList())
			{
				result.add(personTitles);
			}
	        return result;
	    }
	  
	  @RequestMapping(value = "/comdomestichelp/getPersonFirstNameList", method = RequestMethod.GET)
	    public @ResponseBody Set<?> getPersonFirstNameList() {
		  Set<String> result = new HashSet<String>();
		    for (String personFstNames : domesticService.getPersonFirstName())
			{
				result.add(personFstNames);
			}
	        return result;
	    }
	  @RequestMapping(value = "/comdomestichelp/getPersonMiddleNameList", method = RequestMethod.GET)
	    public @ResponseBody Set<?> getPersonMiddleNameList() {
		  Set<String> result = new HashSet<String>();
		    for (String personMiddleNames : domesticService.getPersonMiddleName())
			{
				result.add(personMiddleNames);
			}
	        return result;
	    }
	  @RequestMapping(value = "/comdomestichelp/getPersonLastNameList", method = RequestMethod.GET)
	    public @ResponseBody Set<?> getPersonLastName() {
		  Set<String> result = new HashSet<String>();
		    for (String personLastNames : domesticService.getPersonLastName())
			{
				result.add(personLastNames);
			}
	        return result;
	    }
	  @RequestMapping(value = "/comdomestichelp/getLanguage", method = RequestMethod.GET)
	    public @ResponseBody Set<?> getLanguage() {
		  Set<String> result = new HashSet<String>();
		    for (String personType : domesticService.getLanguage())
			{
				result.add(personType);
			}
	        return result;
	    }
	  
	  @RequestMapping(value = "/comdomestichelp/read", method = RequestMethod.POST)
		public @ResponseBody Object readPerson(final Locale locale) 
		{
			
			List<Person> personsList = null;
			
			JsonResponse errorResponse = new JsonResponse();
			
			try
			{
				personsList = domesticService.getAllDomesticDetails();
				return personsList;
			}
			
			catch (Exception e)
			{
				@SuppressWarnings("serial")
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("readException", messageSource.getMessage(
								"exception.project.exception", null, locale));
					}
				};

				errorResponse.setStatus("READ_EXCEPTION");
				errorResponse.setResult(errorMapResponse);

				return errorResponse;
			}
			
		}
	  
	  
	  @RequestMapping("/comdomestichelp/cu")
		public @ResponseBody Object CreateUpdateDomestic(@RequestBody Map<String, Object> map,HttpServletRequest request,
				HttpServletResponse response,ModelMap model,@ModelAttribute("person") Person person, 
				BindingResult bindingResult,final Locale locale)
		{
		  
			String operation = request.getParameter("action");
	
			Domestic obj = null;
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
				person.setPersonId(personService.getPersonIdBasedOnCreatedByAndLastUpdatedDt(person.getCreatedBy(), person.getLastUpdatedDt()));
				
				obj = new Domestic();
				obj.setPersonId(person.getPersonId());
				obj.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
				obj.setLastUpdatedBy(person.getLastUpdatedBy());
				obj.setLastUpdatedDt(new Timestamp(new Date().getTime()));
				
				
				domesticService.save(obj);
					
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
				int domesticId = domesticService.getDomesticIdByInstanceOfPersonId(person.getPersonId());
				person.setPersonId(personService.getPersonIdBasedOnCreatedByAndLastUpdatedDt(person.getCreatedBy(), person.getLastUpdatedDt()));
				
				obj = new Domestic();
				obj.setDomesticId(domesticId);
				obj.setPersonId(person.getPersonId());
				obj.setCreatedBy(person.getCreatedBy());
				obj.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
				obj.setLastUpdatedDt(new Timestamp(new Date().getTime()));
				domesticService.update(obj);
				}
			}
			
			return person;
		}	  
	 
	  
	  @SuppressWarnings({ "unchecked", "serial" })
	@RequestMapping(value = "/comdomestichelp/property/read/{personId}",  method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody
		List<?> readDomesticProperty(@PathVariable int personId) {
		
			List<Map<String, Object>> domesticProperty =  new ArrayList<Map<String, Object>>(); 
			
			for (final DomesticProperty record : (List<DomesticProperty>) domesticPropertyService.findAllDomesticPropertyBasedOnPersonID(personId) ) 
	        {
				
				domesticProperty.add(new HashMap<String, Object>() {{
					put("domasticPropertyId" ,record.getDomasticPropertyId());
					put("propertyNo" ,propertyService.getProprtyNoBasedOnPropertyId(record.getPropertyId()));
					put("workNature" ,record.getWorkNature());
					put("startDate",ConvertDate.TimeStampString(record.getStartDate()));
					put("endDate" , ConvertDate.TimeStampString(record.getEndDate()));
					put("blockId",record.getProperty().getBlockId());
					put("propertyId" ,record.getPropertyId());
				}});
	        }
			
			
			return domesticProperty;
			//return (List<FamilyProperty>) familyPropertyService.findAllFamilyPropertyBasedOnPersonID(personId);
		}
	  
	  @SuppressWarnings("serial")
	@RequestMapping(value="/comdomestichelp/property/create/{personId}")
	  public @ResponseBody Object CreateDomesticProperty(@PathVariable int personId,@RequestBody Map<String, Object> map,
	  		HttpServletRequest request,HttpServletResponse response,ModelMap model,
	  		@ModelAttribute("domesticProperty") DomesticProperty domesticProperty,BindingResult bindingResult,SessionStatus sessionStatus, final Locale locale)
	  {
	  	
	  	JsonResponse errorResponse = new JsonResponse();
		//DateTimeCalender dateTimeCalender = new DateTimeCalender();
		int domesticId = domesticPropertyService.getDomesticIdBasedOnPersonId(personId);
		int count = 0;
		List<DomesticProperty> listProperty = domesticPropertyService.findAll();
		for (Iterator<DomesticProperty> iterator = listProperty.iterator(); iterator
				.hasNext();) {
			DomesticProperty domesticProperty2 = (DomesticProperty) iterator
					.next();
			if( domesticProperty2.getDomasticId() == domesticId && domesticProperty2.getPropertyId() == (Integer)map.get("propertyId") ){
				count = 1;
				break;
			}
		}
		if( count == 1 ){
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("duplicate", messageSource.getMessage(
							"domesticProperty.unique.record", null, locale));
				}
			};
			errorResponse.setStatus("DUPLICATE");
			errorResponse.setResult(errorMapResponse);
			
			return errorResponse;

		}
	  	
	  	else{
	  	domesticProperty = domesticPropertyService.getDomesticPropertyObject(map, "save", domesticProperty,personId);
	  	
	  	validator.validate(domesticProperty, bindingResult);

	  			if (bindingResult.hasErrors()) {
	  				errorResponse.setStatus("FAIL");
	  				errorResponse.setResult(bindingResult.getAllErrors());
	  			

	  				return errorResponse;
	  			}
	  			else{
	  				domesticPropertyService.save(domesticProperty);
	  				return readDomesticProperty(personId);
	  				//return domesticProperty(personId);
	  			}
	  	}
	  }
	  
	  @SuppressWarnings("serial")
	@RequestMapping("/comdomestichelp/property/update/{personId}")
	  public @ResponseBody Object updateDomesticProperty(@PathVariable int personId,@RequestBody Map<String, Object> map,
	  		HttpServletRequest request,HttpServletResponse response,ModelMap model,
	  		@ModelAttribute("domesticProperty") DomesticProperty domesticProperty,BindingResult bindingResult,final Locale locale)
	  {
			JsonResponse errorResponse = new JsonResponse();
			int domesticPropertyId = Integer.parseInt(map.get("domasticPropertyId").toString());
			int propertyId = domesticPropertyService.getProprtyIdBasedOnDomesticPropertyId(domesticPropertyId);
			int gridPropertyId = (Integer)map.get("propertyId");
			if( gridPropertyId != propertyId ){
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("noneditable", messageSource.getMessage(
								"domesticProperty.noneditable", null, locale));
					}
				};
				errorResponse.setStatus("NONEDITABLE");
				errorResponse.setResult(errorMapResponse);
			

				return errorResponse;
				
			}
		  else{
				domesticProperty = domesticPropertyService.getDomesticPropertyObject(map, "update", domesticProperty,personId);
				validator.validate(domesticProperty, bindingResult);

	  			if (bindingResult.hasErrors()) {
	  				errorResponse.setStatus("FAIL");
	  				errorResponse.setResult(bindingResult.getAllErrors());
	  				

	  				return errorResponse;
	  			}
	  			else{
	  
	  	domesticPropertyService.update(domesticProperty);
	  	return readDomesticProperty(personId);
	  			}
		  }
	  }
	  
	    @RequestMapping(value = "/comdomestichelp/property/delete/{personId}", method = {RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object deleteOwnerProperty(@PathVariable("personId") int personId,@RequestBody Map<String, Object> map) 
		{
		  //DomesticProperty domesticProperty = new DomesticProperty();
			int id = (Integer) map.get("domasticPropertyId");
			Logger.getLogger(COMDomesticController.class).info("inside a property Delete Method---------"+personId);
			domesticPropertyService.delete(id);
			//return domesticProperty;
			return readDomesticProperty(personId);
		}
	  
	  
		 @RequestMapping(value = "/comdomestichelp/getWorkNatures", method = RequestMethod.GET)
			public @ResponseBody List<Map<String, String>> getWorkNatures(final Locale locale) 
			{		
				String[] workNatures = messageSource.getMessage("values.workNature", null, locale).split(",");

				List<Map<String, String>> result = new ArrayList<Map<String, String>>();

				Map<String, String> map = null;

				for (int i = 0; i < workNatures.length; i++)
				{
					map = new HashMap<String, String>();
					map.put("value", workNatures[i]);
					map.put("name", workNatures[i]);

					result.add(map);
				}

				return result;

			}
}
