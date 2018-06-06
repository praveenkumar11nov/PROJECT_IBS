package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
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
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.model.AccessCards;
import com.bcits.bfm.model.AccessCardsPermission;
import com.bcits.bfm.model.AccessPoints;
import com.bcits.bfm.model.DocumentRepository;
import com.bcits.bfm.model.OwnerProperty;
import com.bcits.bfm.model.ParkingSlots;
import com.bcits.bfm.model.ParkingSlotsAllotment;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.PersonAccessCards;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.customerOccupancyManagement.AccessCardSevice;
import com.bcits.bfm.service.customerOccupancyManagement.AccessCardsPermissionService;
import com.bcits.bfm.service.customerOccupancyManagement.AccessRepositoryService;
import com.bcits.bfm.service.customerOccupancyManagement.DocumentDefinerService;
import com.bcits.bfm.service.customerOccupancyManagement.DocumentRepositoryService;
import com.bcits.bfm.service.customerOccupancyManagement.OwnerService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonAccessCardService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.facilityManagement.AccessPointsService;
import com.bcits.bfm.service.facilityManagement.MailRoomService;
import com.bcits.bfm.service.facilityManagement.OwnerPropertyService;
import com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService;
import com.bcits.bfm.service.facilityManagement.VehicleService;
import com.bcits.bfm.serviceImpl.facilityManagement.ParkingSlotsAllotmentServiceImpl;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.view.BreadCrumbTreeService;

/**
 * Controller which includes all the business logic concerned to this module's Usecase
 * Module: Customer Occupancy 
 * Use Case : OwnerProperty,Access Cards,Access Card Permission,Documents
 * 
 * @author Mohammed Farooq
 * @version %I%, %G%
 * @since 0.1
 */


@Controller
public class COMOwnerController 
{
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private BreadCrumbTreeService breadCrumbService;

	@Autowired
	private AccessCardsPermissionService accessCardsPermissionsService;

	@Autowired
	private DocumentRepositoryService documentRepoService;

	@Autowired
	private PersonService personService;

	@Autowired
	private MailRoomService mailroomService;

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private DocumentDefinerService documentDefinerService;

	@Autowired
	private OwnerPropertyService ownerPropertyService;

	@Autowired
	private AccessCardSevice accessCardsService;

	@Autowired
	private AccessRepositoryService accessRepoService;
	
	@Autowired
	private JsonResponse jsonErrorResponse;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private DateTimeCalender dateTimeCalender;
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private PersonAccessCardService personAccessCardService;
	
	@Autowired
	private AccessPointsService accessPointsService;
	
	@Autowired
	private ParkingSlotsAllotmentService parkingSlotsAllotmentService;
	
	private static final Log logger = LogFactory
			.getLog(ParkingSlotsAllotmentServiceImpl.class);
	
	@RequestMapping("/comdashboard")
	public String comdashboard(HttpServletRequest request,HttpServletResponse response,ModelMap model, final Locale locale)
	{
		model.addAttribute("ViewName", "Dashboard");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Dashboard", 1, request);
		model.addAttribute("person",new Person());
		model.addAttribute("personStyle", commonController.populateCategories("personStyle", locale));
		model.addAttribute("title", commonController.SpringDropdownList("title",locale));
		model.addAttribute("maritalStatus", commonController.SpringDropdownList("maritalStatus", locale));
		model.addAttribute("sex", commonController.SpringDropdownList("sex", locale));
		model.addAttribute("nationality", commonController.SpringDropdownList("nationality", locale));
		model.addAttribute("bloodGroup", commonController.SpringDropdownList("bloodGroup", locale));
		model.addAttribute("workNature", commonController.SpringDropdownList("workNature", locale));
		model.addAttribute("addressType",commonController.SpringDropdownList("values.address.addressLocation", locale));
		model.addAttribute("meCategory", commonController.SpringDropdownList("meCategory", locale));
		
		model.addAttribute("status", commonController.populateCategories("values.project.status", locale));
		
		return "com/comdashboard";
	}
	
	@RequestMapping("/comowners")
	public String ownersIndex(HttpServletRequest request,HttpServletResponse reponse,ModelMap model, final Locale locale)
	{
		model.addAttribute("ViewName", "Customer Occupancy");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Customer Occupancy", 1, request);
		breadCrumbService.addNode("Manage Owners", 2, request);
		
		model.addAttribute("personStyle", commonController.populateCategories("personStyle", locale));
		model.addAttribute("title", commonController.populateCategories("title", locale));
		model.addAttribute("maritalStatus", commonController.populateCategories("maritalStatus", locale));
		model.addAttribute("sex", commonController.populateCategories("sex", locale));
		model.addAttribute("nationality", commonController.populateCategories("nationality", locale));
		model.addAttribute("bloodGroup", commonController.populateCategories("bloodGroup", locale));
		model.addAttribute("workNature", commonController.populateCategories("workNature", locale));
		model.addAttribute("status", commonController.populateCategories("values.project.status", locale));
		model.addAttribute("meCategory", commonController.populateCategories("meCategory", locale));
		
		return "com/owners";
	}
	
	/**
	 *
	 * Use Case: Owner Property
	 * 
	 */

	/**
	 * Reading the Properties Assigned to the Owner based on Owner/PersonId
	 * 
	 * @param model [ personId - based on personId fetching the property assigned to this personId]
	 * 
	 * @param request
	 *            Input from the view
	 *            
	 * @return Returns the list of property assgined
	 * @since 0.1
	 */
	
	

	@RequestMapping(value = "/comowner/property/read/{personId}",  method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<?> readOwnerProperty(@PathVariable int personId) 
	{
		return ownerPropertyService.findAllOwnerPropertyBasedOnPersonID(personId);
	}


	/**
	 *  Creating/Assigning the property to the person/Owner based on personId.  
	 *
	 * @param - personId [based on personId assigning the property to owner]
	 * @return          Returns current person Object .
	 * @throws ParseException 
	 * @since           1.0
	 */

	@RequestMapping("/comowner/property/create/{personId}")
	public @ResponseBody Object CreateOwnersProperty(@PathVariable int personId,@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,ModelMap model,
			@ModelAttribute("ownerProperty") OwnerProperty ownerProperty ,BindingResult bindingResult,SessionStatus sessionStatus) throws ParseException
	{
		HttpSession session = request.getSession(true);
		session.getAttribute("userId");
		ownerProperty.setCreatedBy((String)session.getAttribute("userId"));
		ownerProperty = ownerPropertyService.getOwnerPropertyObject(map, "save", ownerProperty,personId);
		
		validator.validate(ownerProperty, bindingResult);
		if (bindingResult.hasErrors()) {
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());
			logger.error(bindingResult);

			return jsonErrorResponse;
		}
		else {
			int proptertyNumber = ownerProperty.getPropertyId();
			
			int status = ownerPropertyService.checkPropertyAssigned(proptertyNumber,ownerProperty.getOwnerId());
			if(status > 0)
			{
				jsonErrorResponse.setStatus("AlreadyAssigned");
				jsonErrorResponse.setResult("Already Assigned This Property");
				return jsonErrorResponse;
			}
			else
			{
				ownerPropertyService.save(ownerProperty);
				return ownerProperty;
			}	
		}
		
	}


	/** 
	 *  Updating the property assigned to the person/Owner based on personId.  
	 *
	 * @param - personId [based on personId assigning the property to owner]
	 * @return          Returns current person Object .
	 * @throws ParseException 
	 * @since           1.0
	 */

	@RequestMapping("/comowner/property/update/{personId}")
	public @ResponseBody Object UpdateOwnersProperty(@PathVariable int personId,@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,ModelMap model,
			@ModelAttribute("ownerProperty") OwnerProperty ownerProperty,BindingResult bindingResult,SessionStatus sessionStatus) throws ParseException
	{
		HttpSession session = request.getSession(true);
		session.getAttribute("userId");
		ownerProperty.setLastUpdatedBy((String)session.getAttribute("userId"));
		ownerProperty = ownerPropertyService.getOwnerPropertyObject(map, "update", ownerProperty,personId);
		
		
		validator.validate(ownerProperty, bindingResult);
		if (bindingResult.hasErrors()) {
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());
			logger.error(bindingResult);

			return jsonErrorResponse;
		}
		else 
		{
			int proptertyNumber = ownerProperty.getPropertyId();
			Map<String,Object> queryMap = new HashMap<String,Object>();
			queryMap.put("ownerId", ownerProperty.getOwnerId());
			queryMap.put("propertyId", proptertyNumber);
			
			List<OwnerProperty> listOwnerProperty = ownerPropertyService.getByNamedQuery("OwnerProperty.AssignedPropertyToOwner",queryMap);
				Iterator<OwnerProperty> it = listOwnerProperty.iterator();
				String msg = "";
				while (it.hasNext()) 
				{
					OwnerProperty p = it.next();
					if(p.getOwnerPropertyId() != ownerProperty.getOwnerPropertyId())
					{
						if (p.getPropertyId()==proptertyNumber) 
						{
							msg = "Exists";
						}
					}
				}
				if (msg != "") 
				{
					jsonErrorResponse.setStatus("AlreadyAssigned");
					jsonErrorResponse.setResult("Already Assigned This Property");
					return jsonErrorResponse;
				}
				else
				{
					ownerPropertyService.update(ownerProperty);
					return ownerProperty;
				}	
		}
	}
	
	/**
	 *  Deleting the property that is assigned to the person/Owner.  
	 *
	 * @param   - None
	 * @return  - Returns current person Object .
	 * @since   - 1.0
	 */
	
	@RequestMapping(value = "/comowner/property/delete/{personId}", method = RequestMethod.POST)
	public @ResponseBody Object deleteOwnerProperty(@PathVariable int personId,@RequestBody Map<String, Object> map) 
	{
		
		int id = (Integer) map.get("ownerPropertyId");
		logger.info("inside a Owner property Del Method------id="+id+" property Id--------"+personId);
		ownerPropertyService.delete(id);
		return readOwnerProperty(personId);
	}
	
	
	/**
	 *  Reading the properties assigned to the person/Owner based on personId.  
	 *
	 * @param  - personId [based on personId assigning the property to owner]
	 * @return - Returns List of Properties
	 * @since  - 1.0
	 */

	@SuppressWarnings({ "rawtypes", "serial" })
	@RequestMapping(value = "/ownerProperty/readProperties/{personId}", method = RequestMethod.GET)
	public @ResponseBody List<?> readPropertyNumber(@PathVariable int personId)
	{		
		List<OwnerProperty> ownerPropertyList =(List<OwnerProperty>) ownerPropertyService.findAll();
		int[] propertyIds = new int[ownerPropertyList.size()];
		int count = 0;
		for (Iterator iterator = ownerPropertyList.iterator(); iterator
				.hasNext();) 
		{
			OwnerProperty ownerProperty = (OwnerProperty) iterator.next();
			propertyIds[count] = ownerProperty.getPropertyId();
			count++;
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
		for (final Property record : mailroomService.findPropertyNames()) 
		{

			result.add(new HashMap<String, Object>() 
					{{
						put("propertyId", record.getPropertyId());
						put("property_No", record.getProperty_No());
						put("propertyName",record.getPropertyName());
					}});
		}
		return result;
	}
	
	/**
	 *  Vistor Request Required (Yes/No) Setting value to dropdown.  
	 *
	 * @param  - None
	 * @return - Returns List of Options (Yes/No)
	 * @since  - 1.0
	 */
	 
	@RequestMapping(value = "/ownerProperty/visitorRegReqChecks", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> getContactPrimary(final Locale locale) 
	{

		String[] visitorRegReq = messageSource.getMessage("values.ownerProperty.visitorRegReq", null, locale).split(",");

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		Map<String, String> map = null;

		for (int i = 0; i < visitorRegReq.length; i++)
		{
			map = new HashMap<String, String>();
			map.put("value", visitorRegReq[i]);
			map.put("name", visitorRegReq[i]);

			result.add(map);
		}

		return result;

	}

	/**
	 *  Uploading the document of person/Owner based on personId.  
	 *
	 * @param  - personId [based on personId assigning the property to owner]
	 * @return - Null
	 * @since  - 1.0
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/kycComplaints/upload/async/save", method = RequestMethod.POST)
	public @ResponseBody void save(@RequestParam List<MultipartFile> files,HttpServletRequest request) {
		// Save the files
		int drId = Integer.parseInt(request.getParameter("drId"));
		DocumentRepository drObject = null;
		for (MultipartFile file : files) 
		{
			drObject =  new DocumentRepository();
			drObject = documentRepoService.find(drId);
			Blob blob;
			try 
			{
				blob = Hibernate.createBlob(file.getInputStream());
				documentRepoService.uploadImageOnId(drId,blob);
			} 
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				logger.error(e);
				e.printStackTrace();
			}


		}
		//Code to save file to any Directory
		
		//InputStream inputStream = null;  
		//OutputStream outputStream = null;  
		// for (MultipartFile file : files) 
		{  
			//String fileName = file.getOriginalFilename();  

			/* if (result.hasErrors()) {  
        	   return new ModelAndView("uploadForm");  
        	  }  */

			/*try {  
        	   inputStream = file.getInputStream();  

        	   File newFile = new File("D:/UploadedFiles/" + fileName);  
        	   if (!newFile.exists()) {  
        	    newFile.createNewFile();  
        	   }  
        	   outputStream = new FileOutputStream(newFile);  
        	   int read = 0;  
        	   byte[] bytes = new byte[1024];  

        	   while ((read = inputStream.read(bytes)) != -1) {  
        	    outputStream.write(bytes, 0, read);  
        	   }  
        	  } catch (IOException e) {  
        	   // TODO Auto-generated catch block  
        	   e.printStackTrace();  
        	  }  */

		}

		// Return an empty string to signify success
	}
	 
	/**
	 *  Reading the documents of the person/Owner based on personId.  
	 *
	 * @param  - personId [based on personId assigning the property to owner]
	 * @return - Returns List of Documents
	 * @since  - 1.0
	 */

	@RequestMapping(value = "/ownerDocument/read/{personId}/{personType}",method = RequestMethod.POST)
	public @ResponseBody List<DocumentRepository> readOwnerPropertyDocRepo(@PathVariable int personId,@PathVariable String personType) throws SQLException
	{
		DocumentRepository obj = null;
		List<DocumentRepository> list = new ArrayList<DocumentRepository>();
		for(DocumentRepository doc : documentRepoService.getAllOnPersonId(personId,personType))
		{

			obj = new DocumentRepository();
			obj.setApproved(doc.getApproved());
			obj.setDocumentNumber(doc.getDocumentNumber());
			obj.setDocumentName(doc.getDocumentName());
			obj.setDrId(doc.getDrId());
			obj.setDocumentType(doc.getDocumentType());
			obj.setDocumentFormat(doc.getDocumentFormat());
			obj.setCreatedBy(doc.getCreatedBy());
			obj.setDocumentDescription(doc.getDocumentDescription());
			list.add(obj);
		}
		return list;
	}
	
	/**
	 *  Adding the document details of person/Owner based on personId.  
	 *
	 * @param  - personId [based on personId assigning the property to owner]
	 * @return - None
	 * @since  - 1.0
	 */
	
	@RequestMapping("/ownerDocument/create/{personId}/{personType}")
	public @ResponseBody Object DocumentCreate(@PathVariable int personId,@PathVariable String personType,@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,ModelMap model,
			@ModelAttribute("documentRepository") DocumentRepository documentRepository,BindingResult bindingResult,SessionStatus sessionStatus)
	{
		documentRepository.setDrGroupId(personId);
		documentRepository.setDocumentType(personType);
		documentRepository = documentRepoService.getDocumentRepositoryObject(map,"save",documentRepository);
		documentRepoService.save(documentRepository);
		return documentRepository;
	}
	
	
	/**
	 * Updating the Document details of person/Owner based on personId.  
	 *
	 * @param  - personId [based on personId assigning the property to owner]
	 * @return - None
	 * @since  - 1.0
	 */
	
	@RequestMapping("/ownerDocument/update/{personId}/{personType}")
	public @ResponseBody Object UpdateDocument(@PathVariable int personId,@PathVariable String personType,@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,ModelMap model,
			@ModelAttribute("documentRepository") DocumentRepository documentRepository,BindingResult bindingResult,SessionStatus sessionStatus)
	{
		documentRepository.setDrGroupId(personId);
		documentRepository.setDocumentType(personType);
		documentRepository = documentRepoService.getDocumentRepositoryObject(map,"update",documentRepository);
		documentRepoService.update(documentRepository);
		return documentRepository;
	}
	
	/**
	 *  Deleting the Document record from Document Repository  
	 *
	 * @param  - None
	 * @return - None
	 * @since  - 1.0
	 */
	@RequestMapping(value = "/conciergeVendor/documentsDeleteUrl", method = RequestMethod.POST)
	public @ResponseBody Object deleteUploadedDocument(@RequestBody Map<String, Object> map,@ModelAttribute("documentRepository") DocumentRepository documentRepository,SessionStatus sessionStatus) throws Exception{
     logger.info("In destroy DocumentRepository details method");
	    
	    JsonResponse errorResponse = new JsonResponse();
		if("Yes".equalsIgnoreCase(map.get("approved").toString())) {
			errorResponse.setStatus("AciveItemMasterDestroyError");
			return errorResponse;				
		}else{
		try {
			int id = (Integer) map.get("drId");
			documentRepoService.delete(id);		
			
		} catch (Exception e) {
			
		}
		sessionStatus.setComplete();
		return documentRepository;
		}
	}
	
	
	
	@RequestMapping(value = {"/conciergeVendor/ownerDocument/delete","/manpower/ownerDocument/delete","/owner/ownerDocument/delete","/tenant/ownerDocument/delete","/dom/ownerDocument/delete","/family/ownerDocument/delete","/vendors/ownerDocument/delete","/property/propertyDocument/delete"}, method = RequestMethod.POST)
	public @ResponseBody Object deleteUploadedDocument(@RequestBody Map<String, Object> map) 
	{
		logger.info("In Pets DELETE()-----------------------"+map.get("drId"));
		DocumentRepository dr = new DocumentRepository();
		int id = (Integer) map.get("drId");
		documentRepoService.delete(id);
		return dr;
	}

	/**
	 *  Download/View the document based on document Id 
	 *
	 * @param  - documentId
	 * @return - File
	 * @since  - 1.0
	 */
	
	@RequestMapping("/download/{documentId}")
	public String download(@PathVariable("documentId")
	Integer documentId, HttpServletResponse response) throws Exception 
	{
	
			DocumentRepository d = documentRepoService.find(2162);
			
			DocumentRepository docRep=documentRepoService.getDocumentbyId(documentId);
			
		
			 try {
		            
		            if(docRep.getDocumentFile() != null)
		            {
		            	response.setHeader("Content-Disposition", "inline;filename=\"" +docRep.getDocumentName()+ "\"");
			            OutputStream out = response.getOutputStream();
		            	response.setContentType(docRep.getDocumentType());
		            	IOUtils.copy(docRep.getDocumentFile().getBinaryStream(), out);
		            	out.flush();
		            	out.close();
		            }	
		            else
		            {
			            OutputStream out = response.getOutputStream();
		            	IOUtils.write("File Not Found", out);
		            	out.flush();
		            	out.close();
		            	//return "filenotfound";
		            }
		            /*FileInputStream inputStream = new FileInputStream(docRep.getDocumentFile());
		            response.setContentLength((int) docRep.getDocumentFile().length());
		            
		            // set headers for the response
		            String headerKey = "Content-Disposition";
		            String headerValue = String.format("attachment; filename=\"%s\"",
		            		docRep.getDocumentName());
		            response.setHeader(headerKey, headerValue);
		     
		            // get output stream of the response
		            OutputStream outStream = response.getOutputStream();
		     
		            byte[] buffer = new byte[BUFFER_SIZE];
		            int bytesRead = -1;
		     
		            // write bytes read from the input stream into the output stream
		            while ((bytesRead = inputStream.read(buffer)) != -1) {
		                outStream.write(buffer, 0, bytesRead);
		            }
		     
		            inputStream.close();
		            outStream.close();*/
		        }
			 	catch (IOException e) 
		        {
		            e.printStackTrace();
		        } catch (SQLException e) 
		        {
		            e.printStackTrace();
		        }
		return null;
	}


	/**
	 *  Reading the access cards assigned to the Owner/Person based on personId.  
	 *
	 * @param personId         based on personId fetching the access cards assgined
	 * @return          Returns list of access cards assigned .
	 * @since           1.0
	 */

	@RequestMapping("/comowner/accesscards/read/{personId}")
	public @ResponseBody	List<?> accesCardsRead(@PathVariable int personId) 
	{
		return accessCardsService.findOnPersonId(personId);
	}
	
	/**
	 *  Reading the access cards assigned to the Owner/Person based on personId.  
	 *
	 * @param personId         based on personId fetching the access cards assgined
	 * @return          Returns list of access cards assigned .
	 * @since           1.0
	 */
	
	@RequestMapping("/comowner/accesscard/read/{personId}")
	public @ResponseBody	List<?> personAccessCardRead(@PathVariable int personId) 
	{
		return  personAccessCardService.findOnPersonId(personId);
	}
	
	/**
	 *  Reading the access cards assigned to the Owner/Person based on personId.  
	 *
	 * @param personId         based on personId fetching the access cards assgined
	 * @return          Returns list of access cards assigned .
	 * @since           1.0
	 */
	@RequestMapping("/comowner/accesscardsforperson/read/{personId}")
	public @ResponseBody	List<?> accesscardsforpersonSystemSecurity(@PathVariable int personId) 
	{
		return accessCardsService.findOnPersonIdForSystemSecurity(personId);
	}

	/** 
	 *  Creating/Assigning the access Card to the Owner/Person based on personId.  
	 *
	 * @param personId  Assign card based on personId 
	 * @return          Returns current accessCard Object .
	 * @since           1.0
	 */

	@RequestMapping("/comowner/accesscards/create/{personId}")
	public @ResponseBody Object AssignCardsToOwner(@PathVariable int personId,@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,ModelMap model,
			@ModelAttribute("accessCards") AccessCards accessCards,BindingResult bindingResult,SessionStatus sessionStatus)
	{
		
		accessCards = accessCardsService.getAccessCardsObject(map, "save", accessCards);
		
		Date d1=dateTimeCalender.getDate1((String) map.get("acStartDate"));
		Date d2=dateTimeCalender.getDate1((String) map.get("acEndDate"));
		
		if(d1.compareTo(d2)<=0){
			//Do nothing
		}
		else{
			bindingResult.reject("acStartDate", "Invalid Date Range : Start Date should not be greater that End Date"); ;
		}
		
		validator.validate(accessCards, bindingResult);
		if (bindingResult.hasErrors()) {
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());
			logger.error(bindingResult);

			return jsonErrorResponse;
		}
		else {
		accessCardsService.save(accessCards);
		return accessCards;
		}
	}
	
	/** 
	 *  Creating/Assigning the access Card to the Owner/Person based on personId.  
	 *
	 * @param personId  Assign card based on personId 
	 * @return          Returns current accessCard Object .
	 * @since           1.0
	 */
	@RequestMapping("/comowner/accesscard/create/{personId}")
	public @ResponseBody Object AssignCardsToOwnerNew(@PathVariable int personId,@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,ModelMap model,
			@ModelAttribute("accessCards") PersonAccessCards personAccessCards,BindingResult bindingResult,SessionStatus sessionStatus)
	{
		personAccessCards.setPersonId(personId);
		personAccessCards = personAccessCardService.getAccessCardsObject(map, "save", personAccessCards);
		
		 
		
		validator.validate(personAccessCards, bindingResult);
		if (bindingResult.hasErrors()) {
			logger.info("Error method() in /comowner/accesscard/create");
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());
			logger.error(bindingResult);
			return jsonErrorResponse;
		}
		else {
			personAccessCardService.save(personAccessCards);
		return personAccessCards;
		}
	}
	
	/** 
	 *  Updating the access Card to the Owner/Person based on personId.  
	 *
	 * @param personId  Assign card based on personId 
	 * @return          Returns current accessCard Object .
	 * @since           1.0
	 */
	@RequestMapping("/comowner/accesscard/update/{personId}")
	public @ResponseBody Object UpdateCardsAssignedToOwnerNew(@PathVariable int personId,@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,ModelMap model,
			@ModelAttribute("accessCards") PersonAccessCards personAccessCards,BindingResult bindingResult,SessionStatus sessionStatus)
	{
		personAccessCards.setPersonId(personId);
		personAccessCards = personAccessCardService.getAccessCardsObject(map, "update", personAccessCards);
		validator.validate(personAccessCards, bindingResult);
		if (bindingResult.hasErrors()) {
			logger.info("Error method() in /comowner/accesscard/create");
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());
			logger.error(bindingResult);

			return jsonErrorResponse;
		}
		else {
			personAccessCardService.update(personAccessCards);
		return personAccessCards;
		}
	}

	/** 
	 *  Updating the access cards assigned to the person/Owner based on personId.  
	 *
	 * @param personId         based on personId assigning the property to owner
	 * @return          Returns current person Object .
	 * @since           1.0
	 */
	@RequestMapping("/comowner/accesscards/update/{personId}")
	public @ResponseBody Object UpdateCardsAssignedToOwner(@PathVariable int personId,@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,ModelMap model,
			@ModelAttribute("accessCards") AccessCards accessCards,BindingResult bindingResult,SessionStatus sessionStatus)
	{
		//accessCards.setPersonId(personId);
		accessCards = accessCardsService.getAccessCardsObject(map, "update", accessCards);
		Date d1=dateTimeCalender.getDate1((String) map.get("acStartDate"));
		Date d2=dateTimeCalender.getDate1((String) map.get("acEndDate"));
		
		if(d1.compareTo(d2)<=0){
			//Do nothing
		}
		else{
			bindingResult.reject("acStartDate", "Invalid Date Range : Start Date should not be greater that End Date"); ;
		}
		validator.validate(accessCards, bindingResult);
		if (bindingResult.hasErrors()) {
			logger.info("Error method() in /comowner/accesscards/update");
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());
			logger.error(bindingResult);

			return jsonErrorResponse;
		}
		else {
		accessCardsService.update(accessCards);
		return accessCards;
		}
	}
	
	/** 
	 *  Un-assign the access card from the Person/Owner   
	 *
	 * @param   - None 
	 * @return  - None
	 * @since   - 1.0
	 */
	@RequestMapping(value = {"/manpower/accesscard/delete","/owner/accesscard/delete","/tenant/accesscard/delete","/dom/accesscard/delete","/family/accesscard/delete"}, method = RequestMethod.POST)
	public @ResponseBody Object deleteAccessCardNew(@RequestBody Map<String, Object> map) 
	{
		PersonAccessCards ac = new PersonAccessCards();
		int id = (Integer) map.get("personacId");
		personAccessCardService.delete(id);
		return ac;
	}



	 
	
	/** 
	 *  Reading the Accesss Card Permissions Based on accessCardID  ( acId) - From Master AccessCard
	 *
	 * @param acId     Read card permissions on acId 
	 * @return          List of Access Points/Permissions
	 * @since           1.0
	 */
	@RequestMapping("/comowner/accesscardspermisions/read/{acId}")
	public @ResponseBody	List<?> accesCardsPermissionRead(@PathVariable int acId,@RequestBody String personId,HttpServletRequest request) 
	{
		if(personId.equalsIgnoreCase("{}"))
		{
			return accessCardsPermissionsService.findOnacId(acId);
		}
		else
		{
			return accessCardsPermissionsService.findOnacId(acId);

		}

	}
	
	/** 
	 *  Reading Access Cards Permissions based on Assigned card selection for person - From COM Module
	 *
	 * @param acId     Read card permissions on acId 
	 * @return          List of Access Points/Permissions
	 * @since           1.0
	 */
	@RequestMapping("/comowner/accesscardspermisions/read")
	public @ResponseBody	List<?> accesCardsPermissionReadOnPersonId(@RequestParam("personId") String personId,HttpServletRequest request) 
	{
	
		if(personId.equalsIgnoreCase("{}"))
		{
		
			return null;
		}
		else
		{
			
			return accessCardsPermissionsService.findOnacId(Integer.parseInt(personId));
		}

	}
	
	
	/** 
	 *  Adding the access points/Permissions to the access card
	 *
	 * @param   - None 
	 * @return  - Access Point Object
	 * @since           1.0
	 */
	@RequestMapping(value="/comowner/accesscardspermisions/create",method=RequestMethod.GET)
	public @ResponseBody Object createAccessCardsPermissions(@ModelAttribute("person") AccessCardsPermission accessCardsPermisssion,BindingResult bindingResult,SessionStatus sessionStatus) 
	{
		Map<String,Object> checkExistanceMap = new HashMap<String,Object>();
		checkExistanceMap.put("arId", accessCardsPermisssion.getArId());
		checkExistanceMap.put("acId", accessCardsPermisssion.getAcId());
		long count = accessCardsPermissionsService.countAll(checkExistanceMap);
		validator.validate(accessCardsPermisssion, bindingResult);
		if (bindingResult.hasErrors()) 
		{
			logger.info("Error method() in /comowner/accesscardspermisions/create");
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());
			logger.info(bindingResult);
			return jsonErrorResponse;
		}
		else if(count == 0)
		{
			accessCardsPermissionsService.save(accessCardsPermisssion);
			return accessCardsPermisssion;
		}
		else
		{
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("accessPointAlreadyAssigned", "Access Point  Already Assigned");
				}
			};
			jsonErrorResponse.setStatus("ACP_ALREADY_ASSIGNED");
			jsonErrorResponse.setResult(errorMapResponse);
			return jsonErrorResponse;
		}
	}
	
	
	/** 
	 *  Updating the access points/Permissions details
	 *
	 * @param   - None 
	 * @return  - Access Point Object
	 * @since   - 1.0
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="/comowner/accesscardspermisions/update",method=RequestMethod.GET)
	public @ResponseBody Object updateAccessCardsPermissions(@ModelAttribute("person") AccessCardsPermission accessCardsPermisssion,BindingResult bindingResult) 
	{
		validator.validate(accessCardsPermisssion, bindingResult);
		List<AccessCardsPermission> listAccesscardPermission = accessCardsPermissionsService.findAll();
		Iterator<AccessCardsPermission> it = listAccesscardPermission.iterator();
		String msg = "";
		int count = 0;
		if (bindingResult.hasErrors()) 
		{
			logger.info("Error method() in /comowner/accesscardspermisions/create");
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());
			return jsonErrorResponse;
		}
		else if(msg != "")
		{
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("accessPointAlreadyAssigned", "Access Point  Already Assigned");
				}
			};

			jsonErrorResponse.setStatus("ACP_ALREADY_ASSIGNED");
			jsonErrorResponse.setResult(errorMapResponse);
			return jsonErrorResponse;
		}
		else 
		{
			accessCardsPermissionsService.update(accessCardsPermisssion);
			return accessCardsPermisssion;
		}
	}
	
	/** 
	 *  Deleting the Access Point/Permission, assigned to the Access Card
	 *
	 * @param   - None 
	 * @return  - Access Point Object
	 * @since   - 1.0
	 */
	@RequestMapping(value = {"/manpower/accesscardspermisions/delete","/owner/accesscardspermisions/delete","/tenant/accesscardspermisions/delete","/dom/accesscardspermisions/delete","/family/accesscardspermisions/delete"}, method = RequestMethod.GET)
	public @ResponseBody Object deleteAccessCardPermission(@ModelAttribute("person") AccessCardsPermission accessCardsPermisssion,BindingResult bindingResult) 
	{
		 
		
		//AccessCardsPermission ac = new AccessCardsPermission();
		//int id = (Integer) map.get("acpId");
		accessCardsPermissionsService.delete(accessCardsPermisssion.getAcpId());;
		return accessCardsPermisssion;
		/*
		
		accessCardsPermissionsService.delete(accessCardsPermisssion.getAcpId());
		accessCardsPermisssion.setAcpEndDate(null);
		accessCardsPermisssion.setAcpStartDate(null);
		return accessCardsPermisssion;*/
	}

	/** 
	 *  Getting All Access Card Permission based on personId when the dropdown is changed
	 *
	 * @param   - personId 
	 * @return  - List of acces points
	 * @since   - 1.0
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/comowner/getAccessCardsBasedOnPersonId/{personId}")
	public @ResponseBody	List<?> getAccessCardsBasedOnPersonId(@PathVariable int personId,HttpServletRequest request) 
	{
		List<Map<String,Object>> cardsList = new ArrayList<Map<String,Object>>();
		return accessCardsService.findOnPersonIdForPermissions(personId);
	}

	/** 
	 *  Reading all access points to populate in dropdown list
	 *
	 * @param   - None 
	 * @return  - List of access points [name,value]
	 * @since   - 1.0
	 */
	@RequestMapping("/comowner/accessrepositoryread/read")
	public @ResponseBody	List<?> getAccessCardsRepository(HttpServletRequest request) 
	{
		List<Map<String,Object>> cardsList = new ArrayList<Map<String,Object>>();
		Map<String,Object> cardMap = null;
		for (AccessPoints record : accessPointsService.findAll()) 
		{
			cardMap = new HashMap<String,Object>();
			cardMap.put("name", record.getApName());
			cardMap.put("value", record.getApId());
			cardsList.add(cardMap);
		}
		return cardsList;
	}
	
	/** 
	 *  Reading all Towers/Blocks to populate in dropdown list
	 *
	 * @param   - None 
	 * @return  - List of Towers/Blocks [name,value]
	 * @since   - 1.0
	 */
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/comowner/property/readTowerNames", method = RequestMethod.GET)
	public @ResponseBody List<?> getTowerNames()
	{
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
	        for (final Property record : propertyService.findAll()) {
	            result.add(new HashMap<String, Object>() 
	            {{	            	
	            	put("propertyId", record.getPropertyId());
	            	put("towerName", record.getBlocks().getBlockName());
	            	
	            }});
	        }
	        return result;
	}
	
	/** 
	 *  Reading Status to populate in dropdown list
	 *
	 * @param   - None 
	 * @return  - List of Status [Yes, No]
	 * @since   - 1.0
	 */
	
	@SuppressWarnings("serial")
	@RequestMapping(value="/comowner/status/readstatus",method=RequestMethod.GET)
	public @ResponseBody List<?> readStatus()
	{
		String[] accessCardType = {"Active","Inactive"};
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
	        for (int i = 0;i<accessCardType.length;i++) {
	        	final String temp = accessCardType[i];
	            result.add(new HashMap<String, Object>() 
	            {{	            	
	            	put("name",temp);
	            	put("value", temp);
	            	
	            }});
	        }
	        return result;
	}
	
	/** 
	 *  Reading all Owners Under the Property
	 *
	 * @param   - PersonType, PropertyId 
	 * @return  - List of Persons
	 * @since   - 1.0
	 */
	@RequestMapping(value="/comowner/getAllOwner/{personType}/{propertyId}",method=RequestMethod.POST)
	public @ResponseBody List<?> getAllOwnersOnProperytId(@PathVariable String personType,@PathVariable int propertyId)
	{
		return personService.getAllOwnersUnderProperty(personType,propertyId);
	}
	
	/** 
	 *  Reading all Family Members Under the Property
	 *
	 * @param   - PersonType, PropertyId 
	 * @return  - List of Persons
	 * @since   - 1.0
	 */

	@RequestMapping(value="/comowner/getAllFamily/{personType}/{propertyId}",method=RequestMethod.POST)
	public @ResponseBody List<?> getAllFamilyOnProperytId(@PathVariable String personType,@PathVariable int propertyId)
	{
		return personService.getAllFamilyUnderProperty(personType,propertyId);
	}
	
	/** 
	 *  Reading all Tenants Under the Property
	 *
	 * @param   - PersonType, PropertyId 
	 * @return  - List of Persons
	 * @since   - 1.0
	 */
	@RequestMapping(value="/comowner/getAllTenant/{personType}/{propertyId}",method=RequestMethod.POST)
	public @ResponseBody List<?> getAllTenantOnProperytId(@PathVariable String personType,@PathVariable int propertyId)
	{
		return personService.getAllTenantUnderProperty(personType,propertyId);
	}
	
	/** 
	 *  Reading all Owners Under the Property
	 *
	 * @param   - PersonType, PropertyId 
	 * @return  - List of Persons
	 * @since   - 1.0
	 */
	@RequestMapping(value="/comowner/getAllDocumentHelp/{personType}/{propertyId}",method=RequestMethod.POST)
	public @ResponseBody List<?> getAllDomesticHelpOnProperytId(@PathVariable String personType,@PathVariable int propertyId)
	{
		return personService.getAllDomesticHelpUnderProperty(personType,propertyId);
	}
	
	/** 
	 *  Reading all Pets Under the Property
	 *
	 * @param   - PetType, PropertyId 
	 * @return  - List of Pets
	 * @since   - 1.0
	 */
	@RequestMapping(value="/comowner/getAllPets/{petType}/{propertyId}",method=RequestMethod.POST)
	public @ResponseBody List<?> getAllPetsOnProperytId(@PathVariable String petType,@PathVariable int propertyId)
	{
		return personService.getAllPetsOnProperytId(petType,propertyId);
	}
	
	/** 
	 *  Checking Count to know the Property OwnerShip.
	 *  
	 *  If count > 0 , Then the next owner will be Co-Owner
	 *  
	 *  Else, Primary Owner
	 *
	 * @param   - PropertyId 
	 * @return  - List of Persons
	 * @since   - 1.0
	 */
	@RequestMapping(value="/getPropertyOwnerShipStatus/{propertyId}",method=RequestMethod.POST)
	public @ResponseBody int getPropertyOwnerShipStatus(@PathVariable int propertyId)
	{
		 int count = ownerPropertyService.getPropertyOwnerShipStatus(propertyId);
		 return count;
	}
	
	
	/** 
	 * Getting all Owners on Property Id 
	 *
	 * @param   - PersonType, PropertyId 
	 * @return  - List of Persons
	 * @since   - 1.0
	 */
	 @SuppressWarnings("serial")
	 @RequestMapping(value = "/property/getAllOwnersOnPropetyId", method = RequestMethod.GET)
	 public @ResponseBody List<?> getOwnerNamesvehicledetails(HttpServletRequest request,HttpServletResponse response) 
	 {
		  int propertyId = Integer.parseInt(request.getParameter("propertyId"));
	      List<OwnerProperty> ownerProperty=ownerPropertyService.findAll();
	      List<OwnerProperty> ownerPropertyList=new ArrayList<OwnerProperty>();   
	  
	      Iterator<OwnerProperty> it=ownerProperty.iterator();
	      while(it.hasNext())
	      {
	    	  	OwnerProperty o=it.next();
	    		  if(propertyId==o.getPropertyId())
	    		  {
	    			  ownerPropertyList.add(o);
	    		  }
	    }
	   
	   
	  List<Map<String, Object>> getOwnerNames = new ArrayList<Map<String, Object>>();
	  getOwnerNames.add(new HashMap<String, Object>() {{
		  put("owner", "Select");
		  put("ownerId","");
	  }});
	   for (final OwnerProperty record :ownerPropertyList ) 
	   {
		   
	      getOwnerNames.add(new HashMap<String, Object>() {{
	               put("owner", record.getOwner().getPerson().getFirstName()+" "+record.getOwner().getPerson().getLastName());
	               put("ownerId", record.getOwnerId());
	             }});            
	       }
	    return getOwnerNames; 
	 }
	
 	/** 
	 *  Getting all the Vehicles on propertyId
	 *
	 * @param   - PropertyId 
	 * @return  - List of Vehicles
	 * @since   - 1.0
	 */
	@RequestMapping(value="/comowner/getAllVehicles/{propertyId}",method=RequestMethod.POST)
	public @ResponseBody List<?> getAllVehiclesBasedOnPropertyId(@PathVariable int propertyId)
	{
		return vehicleService.getallVehicles(propertyId);
		  
	}
	
	
	@SuppressWarnings("serial")
	@RequestMapping(value="/properties/getAllParkingSlots/{propertyId}",method=RequestMethod.POST)
	public @ResponseBody List<?> getAllParkingSlots(@PathVariable int propertyId)
	{
		logger.info("Parking Slots Master Reading Records");
		List<Map<String, Object>> parkingSlots = new ArrayList<Map<String, Object>>();
		for (final ParkingSlotsAllotment record : parkingSlotsAllotmentService.getAllParkingSlots(propertyId)) {
			parkingSlots.add(new HashMap<String, Object>() {
				{
					put("slotNumber", record.getParkingSlots().getPsSlotNo());
					put("ownerShipType", record.getParkingSlots().getPsOwnership());
					put("parkingMethod", record.getParkingSlots().getPsParkingMethod());
					put("psActiveDate", ConvertDate.TimeStampString(record.getParkingSlots().getPsActiveDate()));
			    }
			});
		}
		return parkingSlots;
 
	}
	
	@RequestMapping("/upload")
	public String upload(HttpServletRequest request,HttpServletResponse reponse,ModelMap model, final Locale locale)
	{
		model.addAttribute("ViewName", "Photo Gallery");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Customer Care", 1, request);
		breadCrumbService.addNode("Photo Gallery", 2, request);
		return "com/upload";
	} 
	
}