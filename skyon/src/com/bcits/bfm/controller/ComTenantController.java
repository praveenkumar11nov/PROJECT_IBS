package com.bcits.bfm.controller;

import java.sql.SQLException;
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
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
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

import com.bcits.bfm.model.DrGroupId;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.model.Tenant;
import com.bcits.bfm.model.TenantProperty;
import com.bcits.bfm.service.DrGroupIdService;
import com.bcits.bfm.service.customerOccupancyManagement.DocumentRepositoryService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.customerOccupancyManagement.TenantPropertyService;
import com.bcits.bfm.service.customerOccupancyManagement.TenantSevice;
import com.bcits.bfm.service.facilityManagement.MailRoomService;
import com.bcits.bfm.service.userManagement.GroupsService;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

/**
 * Controller which includes all the business logic concerned to this module
 *
 * 
 * @author Pooja.K
 * @version %I%, %G%
 * @since 0.1
 */
@Controller
public class ComTenantController {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private TenantSevice tenantSevice;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private TenantSevice tenantService;	
	
	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private MailRoomService mailService;
	
	@Autowired
	private TenantPropertyService tenantPropertyService;
	
	@Autowired
	private DocumentRepositoryService documentRepoService;
	
	@Autowired
	private DrGroupIdService drGroupIdService;
	
	@Autowired
	private GroupsService groupService;
	
	private Logger logger=Logger.getLogger(ComTenantController.class);
	
	@RequestMapping("/comtenants")
	public String familyIndex(HttpServletRequest request,HttpServletResponse response,ModelMap model, final Locale locale)
	{
		model.addAttribute("ViewName", "Customer Occupancy");
	     breadCrumbService.addNode("nodeID", 0, request);
	     breadCrumbService.addNode("Customer Occupancy", 1, request);
	    breadCrumbService.addNode("Manage Tenant", 2, request);
	   	
		model.addAttribute("personStyle", commonController.populateCategories("personStyle", locale));
		model.addAttribute("title", commonController.populateCategories("title", locale));
		model.addAttribute("maritalStatus", commonController.populateCategories("maritalStatus", locale));
		model.addAttribute("sex", commonController.populateCategories("sex", locale));
		model.addAttribute("nationality", commonController.populateCategories("nationality", locale));
		model.addAttribute("bloodGroup", commonController.populateCategories("bloodGroup", locale));
		model.addAttribute("workNature", commonController.populateCategories("workNature", locale));
		model.addAttribute("status", commonController.populateCategories("values.project.status", locale));
		model.addAttribute("meCategory", commonController.populateCategories("meCategory", locale));
		
	   	return "com/tenant";
	}
	
	 @RequestMapping("/comtenants/cu")
		public @ResponseBody Object CreateUpdateOwners(@RequestBody Map<String, Object> map,HttpServletRequest request,
				HttpServletResponse response,ModelMap model,@ModelAttribute("person") Person person, 
				BindingResult bindingResult,final Locale locale)
		{
		  
			String operation = request.getParameter("action");
			
			Tenant obj = null;
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
				
				obj = new Tenant();
				obj.setPersonId(person.getPersonId());
				obj.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
				obj.setLastUpdatedBy(person.getLastUpdatedBy());
				obj.setLastUpdatedDt(new Timestamp(new Date().getTime()));
				
				
				tenantSevice.save(obj);
					
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
				int tenantId = tenantSevice.getTenantIdByInstanceOfPersonId(person.getPersonId());
				person.setPersonId(personService.getPersonIdBasedOnCreatedByAndLastUpdatedDt(person.getCreatedBy(), person.getLastUpdatedDt()));
				
				obj = new Tenant();
				obj.setTenantId(tenantId);
				obj.setPersonId(person.getPersonId());
				obj.setCreatedBy(person.getCreatedBy());
				obj.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
				obj.setLastUpdatedDt(new Timestamp(new Date().getTime()));
				tenantSevice.update(obj);
				}
			}
			
			return person;
		}
	 
	 @RequestMapping(value = "/comtenants/getPersonStyleList", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getPersonStyleList() 
	 {
		  Set<String> result = new HashSet<String>();
		  for (String personStyles : tenantSevice.getPersonStyle())
			  result.add(personStyles);
	      return result;
	 }
	  
	  @RequestMapping(value = "/comtenants/getPersonTitleList", method = RequestMethod.GET)
	    public @ResponseBody Set<?> getPersonTitleList() {
		  Set<String> result = new HashSet<String>();
		    for (String personTitles : tenantSevice.getPersonTitleList())
			{
				result.add(personTitles);
			}
	        return result;
	    }
	 
	  @RequestMapping(value = "/comtenants/getPersonFirstNameList", method = RequestMethod.GET)
	    public @ResponseBody Set<?> getPersonFirstNameList() {
		  Set<String> result = new HashSet<String>();
		    for (String personFstNames : tenantSevice.getPersonFirstName())
			{
				result.add(personFstNames);
			}
	        return result;
	    }
	  @RequestMapping(value = "/comtenants/getPersonMiddleNameList", method = RequestMethod.GET)
	    public @ResponseBody Set<?> getPersonMiddleNameList() {
		  Set<String> result = new HashSet<String>();
		    for (String personMiddleNames : tenantSevice.getPersonMiddleName())
			{
				result.add(personMiddleNames);
			}
	        return result;
	    }
	  @RequestMapping(value = "/comtenants/getPersonLastNameList", method = RequestMethod.GET)
	    public @ResponseBody Set<?> getPersonLastName() {
		  Set<String> result = new HashSet<String>();
		    for (String personLastNames : tenantSevice.getPersonLastName())
			{
				result.add(personLastNames);
			}
	        return result;
	    }
	  @RequestMapping(value = "/comtenants/getLanguage", method = RequestMethod.GET)
	    public @ResponseBody Set<?> getLanguage() {
		  Set<String> result = new HashSet<String>();
		    for (String personLanguages : tenantSevice.getLanguage())
			{
				result.add(personLanguages);
			}
	        return result;
	    }
	  
	 @SuppressWarnings("serial")
	@RequestMapping(value = "/comtenants/property/readTowerNames", method = RequestMethod.GET)
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
		@RequestMapping(value = "/comtenants/property/getPropertyNo", method = { RequestMethod.GET,
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
		@RequestMapping(value = "/comtenants/property/read/{personId}",  method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody
		List<?> readTenantProperty(@PathVariable int personId) {
			
			System.err.println("@RequestMapping(value=\"/comtenants/property/read/"+personId+"\")\n--------Inside readTenantProperty()----------");
			List<Map<String, Object>> tenantProperty =  new ArrayList<Map<String, Object>>(); 
			
			for (final TenantProperty record : (List<TenantProperty>) tenantPropertyService.findAllTenantPropertyBasedOnPersonID(personId) ) 
	        {
				
				System.out.println("tenantPropertyId==>"+record.getTenantPropertyId());
				System.out.println("propertyNo========>"+propertyService.getProprtyNoBasedOnPropertyId(record.getPropertyId()));
				System.out.println("groupId===========>"+record.getGroupId());
				System.out.println("status============>"+record.getStatus());
				System.out.println("startDate=========>"+ConvertDate.TimeStampString(record.getStartDate()));
				if(record.getEndDate()!=null){
				System.out.println("endDate===========>"+ConvertDate.TimeStampString(record.getEndDate()));
				}
				System.out.println("blockId===========>"+record.getProperty().getBlockId());
				System.out.println("propertyId========>"+record.getPropertyId()+"\n");
				
				tenantProperty.add(new HashMap<String, Object>() {{
					put("tenantPropertyId" ,record.getTenantPropertyId());
					put("propertyNo" ,propertyService.getProprtyNoBasedOnPropertyId(record.getPropertyId()));
					put("groupId" ,record.getGroupId());
					put("status" , record.getStatus());
					put("startDate",ConvertDate.TimeStampString(record.getStartDate()));
					if(record.getEndDate()!=null){	put("endDate" , ConvertDate.TimeStampString(record.getEndDate()));	}
					put("blockId",record.getProperty().getBlockId());
					put("propertyId" ,record.getPropertyId());
					
				}});
	        }
			System.out.println("tenantProperty="+tenantProperty);
			return tenantProperty;
			//return (List<FamilyProperty>) familyPropertyService.findAllFamilyPropertyBasedOnPersonID(personId);
		}
		
		@SuppressWarnings("serial")
		@RequestMapping(value="/comtenants/property/create/{personId}")
		public @ResponseBody Object CreateFamilyProperty(@PathVariable int personId,@RequestBody Map<String, Object> map,
				HttpServletRequest request,HttpServletResponse response,ModelMap model,
				@ModelAttribute("tenantProperty") TenantProperty tenantProperty,BindingResult bindingResult,SessionStatus sessionStatus, final Locale locale)
		{
			
			JsonResponse errorResponse = new JsonResponse();
			//DateTimeCalender dateTimeCalender = new DateTimeCalender();
			int tenantId = tenantPropertyService.getTenantIdBasedOnPersonId(personId);
			int count = 0;
			
			List<TenantProperty> listProperty = tenantPropertyService.findAll();
			for (Iterator<TenantProperty> iterator = listProperty.iterator(); iterator
					.hasNext();) {
				TenantProperty tenantProperty2 = (TenantProperty) iterator
						.next();
				if( tenantProperty2.getTenantId().getTenantId() == tenantId && tenantProperty2.getPropertyId() == (Integer)map.get("propertyId") ){
					count = 1;
					break;
				}
			}
			if( count == 1 ){
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("invalid", messageSource.getMessage(
								"tenantProperty.unique.record", null, locale));
					}
				};
				errorResponse.setStatus("INVALID");
				errorResponse.setResult(errorMapResponse);
				

				return errorResponse;

			}
			else{
			tenantProperty = tenantPropertyService.getTenantPropertyObject(map, request,"save", tenantProperty,personId);
			
			drGroupIdService.save(new DrGroupId(tenantProperty.getCreatedBy(), tenantProperty.getLastUpdatedDt()));
			tenantProperty.setGroupId(drGroupIdService.getNextVal(tenantProperty.getCreatedBy(), tenantProperty.getLastUpdatedDt()));
			
			validator.validate(tenantProperty, bindingResult);

					if (bindingResult.hasErrors())
					{
						errorResponse.setStatus("FAIL");
						errorResponse.setResult(bindingResult.getAllErrors());
						

						return errorResponse;
					}
					else
					{
						tenantPropertyService.save(tenantProperty);
						return readTenantProperty(personId);
					}
			}
		}
		@SuppressWarnings("serial")
		@RequestMapping(value="/comtenants/property/update/{personId}")
		public @ResponseBody Object updateTenantProperty(@PathVariable int personId,@RequestBody Map<String, Object> map,
				HttpServletRequest request,HttpServletResponse response,ModelMap model,
				@ModelAttribute("tenantProperty") TenantProperty tenantProperty,BindingResult bindingResult,SessionStatus sessionStatus, final Locale locale)
		{
			JsonResponse errorResponse = new JsonResponse();
			int tenPropertyId = Integer.parseInt(map.get("tenantPropertyId").toString());
			int propertyId = tenantPropertyService.getProprtyIdBasedOntenPropertyId(tenPropertyId);
			int gridPropertyId = (Integer)map.get("propertyId");
			if( gridPropertyId != propertyId ){
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("noneditable", messageSource.getMessage(
								"tenantProperty.noneditable", null, locale));
					}
				};
				errorResponse.setStatus("NONEDITABLE");
				errorResponse.setResult(errorMapResponse);
				

				return errorResponse;
				
			}
			
			else{
				tenantProperty = tenantPropertyService.getTenantPropertyObject(map, request,"update", tenantProperty,personId);
				validator.validate(tenantProperty, bindingResult);

				if (bindingResult.hasErrors())
				{
					errorResponse.setStatus("FAIL");
					errorResponse.setResult(bindingResult.getAllErrors());
					
					return errorResponse;
				}
				else
				{
			
			tenantPropertyService.update(tenantProperty);
			return readTenantProperty(personId);
				}
			}
		}
			
		    @RequestMapping(value = "/comtenantsproperty/delete/{personId}", method = {RequestMethod.POST,RequestMethod.GET})
			public @ResponseBody Object deleteTenantProperty(@PathVariable("personId") int personId,@RequestBody Map<String, Object> map) 
			{
				logger.info("Inside Tenants Delete Method-----------------------------"+personId+" TenPropId"+map.get("tenantPropertyId"));
				
			  //TenantProperty tenantProperty = new TenantProperty();
				int id = (Integer) map.get("tenantPropertyId");
				
				tenantPropertyService.delete(id);
				return readTenantProperty(personId);
				//return tenantProperty;
			}
		
		  /*@RequestMapping(value = "/comtenants/tenantDocument/read/{personId}",method = RequestMethod.POST)
			public @ResponseBody List<DocumentRepository> readTenantPropertyDocRepo(@PathVariable int personId) throws SQLException
			{
			
				DocumentRepository obj = null;
				List<DocumentRepository> list = new ArrayList<DocumentRepository>();
				
				for(DocumentRepository doc : documentRepoService.getAllOnPersonId(personId))
				{
					
					obj = new DocumentRepository();
					obj.setApproved(doc.getApproved());
					obj.setDocumentNumber(doc.getDocumentNumber());
					obj.setDocumentName(doc.getDocumentName());
					obj.setDrId(doc.getDrId());
					//MultipartFile file = (MultipartFile) doc.getDocumentFile()doc.getDocumentFile().getBinaryStream().;
				
					list.add(obj);
				}
				return list;
			}*/
}
