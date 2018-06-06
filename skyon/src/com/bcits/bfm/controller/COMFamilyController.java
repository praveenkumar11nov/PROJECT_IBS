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

import com.bcits.bfm.model.Family;
import com.bcits.bfm.model.FamilyProperty;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.customerOccupancyManagement.FamilyPropertyService;
import com.bcits.bfm.service.customerOccupancyManagement.OwnerService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.customerOccupancyManagement.TenantSevice;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.bcits.bfm.service.customerOccupancyManagement.FamilyService;
import com.bcits.bfm.service.facilityManagement.MailRoomService;
import com.bcits.bfm.service.userManagement.UsersService;

/**
 * Controller which includes all the business logic concerned to this module
 *
 * 
 * @author Pooja.K
 * @version %I%, %G%
 * @since 0.1
 */
@Controller
public class COMFamilyController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CommonController commonController;
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private FamilyService familyService;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private MailRoomService mailService;
	
	@Autowired
	private FamilyPropertyService familyPropertyService;
	
	@Autowired
	private TenantSevice tenantSevice;
	
	  @RequestMapping("/comfamily")
	   	public String familyIndex(HttpServletRequest request,HttpServletResponse response,ModelMap model, final Locale locale)
	   	{
		    model.addAttribute("ViewName", "Customer Occupancy");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Customer Occupancy", 1, request);	   		
			breadCrumbService.addNode("Manage Family", 2, request);
	   		model.addAttribute("personStyle", commonController.populateCategories("personStyle", locale));
			model.addAttribute("title", commonController.populateCategories("title", locale));
			model.addAttribute("sex", commonController.populateCategories("sex", locale));
			model.addAttribute("bloodGroup", commonController.populateCategories("bloodGroup", locale));
			model.addAttribute("status", commonController.populateCategories("values.project.status", locale));
			model.addAttribute("meCategory", commonController.populateCategories("meCategory", locale));
			
	   		return "com/family";
	   	}
	  
	  @RequestMapping(value = "/comfamily/getPersonStyleList", method = RequestMethod.GET)
	    public @ResponseBody Set<?> getPersonStyleList() {
		  Set<String> result = new HashSet<String>();
		    for (String personStyles : familyService.getPersonStyle())
			{
				result.add(personStyles);
			}
	        return result;
	    }
	  
	  @RequestMapping(value = "/comfamily/getPersonTitleList", method = RequestMethod.GET)
	    public @ResponseBody Set<?> getPersonTitleList() {
		  Set<String> result = new HashSet<String>();
		    for (String personTitles : familyService.getPersonTitleList())
			{
				result.add(personTitles);
			}
	        return result;
	    }
	  
	  @RequestMapping(value = "/comfamily/getPersonFirstNameList", method = RequestMethod.GET)
	    public @ResponseBody Set<?> getPersonFirstNameList() {
		  Set<String> result = new HashSet<String>();
		    for (String personFstNames : familyService.getPersonFirstName())
			{
				result.add(personFstNames);
			}
	        return result;
	    }
	  @RequestMapping(value = "/comfamily/getPersonMiddleNameList", method = RequestMethod.GET)
	    public @ResponseBody Set<?> getPersonMiddleNameList() {
		  Set<String> result = new HashSet<String>();
		    for (String personMiddleNames : familyService.getPersonMiddleName())
			{
				result.add(personMiddleNames);
			}
	        return result;
	    }
	  @RequestMapping(value = "/comfamily/getPersonLastNameList", method = RequestMethod.GET)
	    public @ResponseBody Set<?> getPersonLastName() {
		  Set<String> result = new HashSet<String>();
		    for (String personLastNames : familyService.getPersonLastName())
			{
				result.add(personLastNames);
			}
	        return result;
	    }
	  @RequestMapping(value = "/comfamily/getLanguage", method = RequestMethod.GET)
	    public @ResponseBody Set<?> getLanguage() {
		  Set<String> result = new HashSet<String>();
		    for (String personLanguages : familyService.getLanguage())
			{
				result.add(personLanguages);
			}
	        return result;
	    }
	  
	  
	  @RequestMapping(value = "/comfamily/read", method = RequestMethod.POST)
		public @ResponseBody Object readPerson(final Locale locale) 
		{
			
			List<Person> personsList = null;
			
			JsonResponse errorResponse = new JsonResponse();
			
			try
			{
				personsList = familyService.getAllFamilyDetails();
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
	 
	  
	  @RequestMapping("/comfamily/cu")
		public @ResponseBody Object CreateUpdateFamily(@RequestBody Map<String, Object> map,HttpServletRequest request,
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
				person.setPersonId(personService.getPersonIdBasedOnCreatedByAndLastUpdatedDt(person.getCreatedBy(), person.getLastUpdatedDt()));
				
				obj = new Family();
				obj.setPersonId(person.getPersonId());
				obj.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
				obj.setLastUpdatedBy(person.getLastUpdatedBy());
				obj.setLastUpdatedDt(new Timestamp(new Date().getTime()));
				
					familyService.save(obj);
					
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
				int familyId = familyService.getFamilyIdByInstanceOfPersonId(person.getPersonId());
				
				person.setPersonId(personService.getPersonIdBasedOnCreatedByAndLastUpdatedDt(person.getCreatedBy(), person.getLastUpdatedDt()));
				
				obj = new Family();
				obj.setFamilyId(familyId);
				obj.setPersonId(person.getPersonId());
				obj.setCreatedBy(person.getCreatedBy());
				obj.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
				obj.setLastUpdatedDt(new Timestamp(new Date().getTime()));
				familyService.update(obj);
				}
			}
			
			return person;
		}	  

	  @SuppressWarnings("serial")
	@RequestMapping(value = "/comfamily/property/readTowerNames", method = RequestMethod.GET)
		public @ResponseBody List<?> getTowerNames()
		{
			 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
		        for (final Property record : mailService.findPropertyNames()) {
		            result.add(new HashMap<String, Object>() 
		            {{	            	
		            	put("propertyId", record.getPropertyId());
		            	put("towerName", record.getBlocks().getBlockName());
		            	
		            }});
		        }
		        return result;
		}
	  

	  /*******   For Reading Property Id & Property Names********/
		@SuppressWarnings("serial")
		@RequestMapping(value = "/comfamily/property/getPropertyNo", method = { RequestMethod.GET,
				RequestMethod.POST })
		public @ResponseBody Set<?> getPropertyNo()
		{		
			 Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();        
		        for (final Property record : propertyService.getPropertyNo()) {
		            result.add(new HashMap<String, Object>() 
		            {{
		            	put("propertyId", record.getPropertyId());
		            	put("propertyNo", record.getProperty_No());
		            }});
		        }
		        return result;
		        
			/*return designationService.getAll();*/
		}
		/***********  END **********/
		
	  
		@SuppressWarnings({ "unchecked", "serial" })
		@RequestMapping(value = "/comfamily/property/read/{personId}",  method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody
		List<?> readFamilyProperty(@PathVariable int personId) {
		
				List<Map<String, Object>> familyProperty =  new ArrayList<Map<String, Object>>(); 
				
				for (final FamilyProperty record : (List<FamilyProperty>) familyPropertyService.findAllFamilyPropertyBasedOnPersonID(personId) ) 
		        {
					
					
					final Person personRec = personService.getPersonBasedOnId(record.getOwnerId());
					familyProperty.add(new HashMap<String, Object>() {{
						put("familyPropertyId" ,record.getFamilyPropertyId());
						put("propertyNo" ,propertyService.getProprtyNoBasedOnPropertyId(record.getPropertyId()));
						put("fpRelationship" ,record.getFpRelationship() );
						put("startDate",ConvertDate.TimeStampString(record.getStartDate()));
						put("endDate" , ConvertDate.TimeStampString(record.getEndDate()));
						put("propertyId" , record.getPropertyId());
						/*put("personId" , record.getOwnerId());*/
						put("owner" , personRec.getFirstName()+" " + personRec.getLastName()+"     ["+personRec.getPersonType()+"]");
						put("ownerId" ,personRec.getFirstName()+" " + personRec.getLastName()+"     ["+personRec.getPersonType()+"]");
						put("blockId",record.getProperty().getBlockId());
						put("propertyId" ,record.getPropertyId());
						
					}});
		        }
				
				return familyProperty;
		}
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/comfamily/getPropertyNos", method = RequestMethod.GET)
		public @ResponseBody Set<?> getPropertyNos()
		{
			
			Set<Map<String, Object>> result = new HashSet<Map<String, Object>>(); 
			
			List<Property> record = (List<Property>) familyPropertyService.getPropertyNoBasedOnOwners();
			
			Map<String, Object> map = null;
			
			for (Iterator<Property> iterator = record.iterator(); iterator.hasNext();)
			{
				Property property = (Property) iterator.next();
				map = new HashMap<String, Object>();
	 	       	map.put("propertyId", property.getPropertyId());

				map.put("propertyNo", property.getProperty_No());
				map.put("blockId", property.getBlockId());
				
				//map.put("towerName", property.getTowerName());
	 	       	
	 	       	result.add(map);
				
			}
	        return result;
		}
		@SuppressWarnings("serial")
		  @RequestMapping(value = "/familyProperty/getAllOwnersOnPropetyId", method = RequestMethod.GET)
		   public @ResponseBody List<?> getAllOwnersOnPropetyId(HttpServletRequest request,HttpServletResponse response) 
		   {
		     int propertyId = Integer.parseInt(request.getParameter("propertyId"));
		     //for owner
		        List<Person> ownerList = personService.getAllOwnersOnPropetyId(propertyId);
		        
		       
		     List<Map<String, Object>> getOwnerNames = new ArrayList<Map<String, Object>>();
		     getOwnerNames.add(new HashMap<String, Object>() {{
		      put("owner", "Select");
		      put("ownerId","");
		     }});
		      for (final Person record : ownerList ) 
		      {
		         getOwnerNames.add(new HashMap<String, Object>() {{
		         if( record.getLastName() == null ){ 
		           put("owner", record.getFirstName());
		          }
		          else{
		                  put("owner", record.getFirstName()+" "+record.getLastName());
		          }
		                  put("ownerId", record.getPersonId());
		                  put("type", record.getPersonType());
		                  
		                 
		                }}); 
		          }
	/*	
		 @SuppressWarnings("serial")
		@RequestMapping(value = "/familyProperty/getAllOwnersOnPropetyId", method = RequestMethod.GET)
		 public @ResponseBody List<?> getAllOwnersOnPropetyId(HttpServletRequest request,HttpServletResponse response) 
		 {
			  int propertyId = Integer.parseInt(request.getParameter("propertyId"));
			  //for owner
		      List<Person> ownerList = personService.getAllOwnersOnPropetyId(propertyId);
		      
		     
			  List<Map<String, Object>> getOwnerNames = new ArrayList<Map<String, Object>>();
			 
			   for (final Person record : ownerList ) 
			   {
			      getOwnerNames.add(new HashMap<String, Object>() {{
			    	 if( record.getLastName() == null ){ 
			    		  put("owner", record.getFirstName());
			    	  }
			    	  else{
			               put("owner", record.getFirstName()+" "+record.getLastName());
			    	  }
			               put("ownerId", record.getPersonId());
			               put("type", record.getPersonType());
				              
			              
			             }}); 
			       }*/
			   //for tenant
			   List<Person> tenantList = personService.getAllTenantOnPropetyId(propertyId);
			      
				 // List<Map<String, Object>> getOwnerNames = new ArrayList<Map<String, Object>>();
				   for (final Person record : tenantList ) 
				   {
					   
				      getOwnerNames.add(new HashMap<String, Object>() {{
				    	  if( record.getLastName() == null ){ 
				    		  put("owner", record.getFirstName());
				    	  }
				    	  else{
				               put("owner", record.getFirstName()+" "+record.getLastName());
				    	  }
				               put("ownerId", record.getPersonId());
				               put("type", record.getPersonType());
				              
				             }});            
				       }
				   //for domestic
				   List<Person> domesticList = personService.getAllDomesticOnPropetyId(propertyId);
				      
				     
					 // List<Map<String, Object>> getOwnerNames = new ArrayList<Map<String, Object>>();
					   for (final Person record : domesticList ) 
					   {
					      getOwnerNames.add(new HashMap<String, Object>() {{
					    	  if( record.getLastName() == null ){ 
					    		  put("owner", record.getFirstName());
					    	  }
					    	  else{
					               put("owner", record.getFirstName()+" "+record.getLastName());
					    	  }
					               put("ownerId", record.getPersonId());
					               put("type", record.getPersonType());
					              
					             }});            
					       }
			   
			    return getOwnerNames; 
			 }
@SuppressWarnings({ "unchecked", "serial" })
@RequestMapping(value="/comfamily/property/create/{personId}")
public @ResponseBody Object CreateFamilyProperty(@PathVariable int personId,@RequestBody Map<String, Object> map,
		HttpServletRequest request,HttpServletResponse response,ModelMap model,
		@ModelAttribute("familyProperty") FamilyProperty familyProperty,BindingResult bindingResult,SessionStatus sessionStatus, final Locale locale)
{
	
	JsonResponse errorResponse = new JsonResponse();
	
	Integer ownerId = ((Integer) map.get("ownerId"));
	int propertyId = ((Integer)map.get("propertyId"));
	int familyId = familyPropertyService.getFamilyIdBasedOnPersonId(personId);
	int count = 0;
	
	List<FamilyProperty> propertyList = (List<FamilyProperty>) familyPropertyService.findAll();
	for (Iterator<FamilyProperty> iterator = propertyList.iterator(); iterator.hasNext();) {
		FamilyProperty familyProperty2 = (FamilyProperty) iterator.next();
		if( familyProperty2.getFamilyId() == familyId && familyProperty2.getPropertyId() == propertyId && familyProperty2.getOwnerId() == ownerId){
			count = 1;
			break;
			
		}
		
	}
	if( count == 1 ){
		HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
			{
				put("duplicate", messageSource.getMessage(
						"FamilyProperty.unique.record", null, locale));
			}};
			errorResponse.setStatus("DUPLICATE");
			errorResponse.setResult(errorMapResponse);

			return errorResponse;
		
	}
	else{
	familyProperty = familyPropertyService.getFamilyPropertyObject(map, "save", familyProperty,personId);
	
	validator.validate(familyProperty, bindingResult);

			if (bindingResult.hasErrors()) {
				errorResponse.setStatus("FAIL");
				errorResponse.setResult(bindingResult.getAllErrors());
			

				return errorResponse;
			}
			else{
				familyPropertyService.save(familyProperty);
				//return familyProperty;
				return readFamilyProperty(personId);
			}
	}
	
}
	

@SuppressWarnings("serial")
@RequestMapping("/comfamily/property/update/{personId}")
public @ResponseBody Object updateFamilyProperty(@PathVariable int personId,@RequestBody Map<String, Object> map,
		HttpServletRequest request,HttpServletResponse response,ModelMap model,
		@ModelAttribute("familyProperty") FamilyProperty familyProperty,BindingResult bindingResult,final Locale locale)
{
	JsonResponse errorResponse = new JsonResponse();
	int famPropertyId = Integer.parseInt(map.get("familyPropertyId").toString());
	int propertyId = familyPropertyService.getProprtyIdBasedOnFamPropertyId(famPropertyId);
	int gridPropertyId = (Integer)map.get("propertyId");
	if( gridPropertyId != propertyId ){
		HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
			{
				put("noneditable", messageSource.getMessage(
						"famProperty.noneditable", null, locale));
			}
		};
		errorResponse.setStatus("NONEDITABLE");
		errorResponse.setResult(errorMapResponse);
		

		return errorResponse;
		
	}
	
	else{
		
	familyProperty = familyPropertyService.getFamilyPropertyObject(map, "update", familyProperty,personId);
	familyPropertyService.update(familyProperty);
	//return familyProperty;
	return readFamilyProperty(personId);
	}
	
}
@RequestMapping(value = "/comfamily/property/delete/{personId}",  method = { RequestMethod.GET, RequestMethod.POST })
public @ResponseBody Object deleteDomesticProperty(@PathVariable int personId,@RequestBody Map<String, Object> map) 
{
 // FamilyProperty familyProperty = new FamilyProperty();
	int id = (Integer) map.get("familyPropertyId");

	familyPropertyService.delete(id);
	return readFamilyProperty(personId);
}

}