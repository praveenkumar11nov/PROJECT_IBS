package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
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
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.bcits.bfm.model.DrGroupId;
import com.bcits.bfm.model.GraphModelView;
import com.bcits.bfm.model.ItemMaster;
import com.bcits.bfm.model.Requisition;
import com.bcits.bfm.model.RequisitionDetails;
import com.bcits.bfm.model.StoreItemLedger;
import com.bcits.bfm.model.StoreMaster;
import com.bcits.bfm.model.UnitOfMeasurement;
import com.bcits.bfm.model.VendorPriceList;
import com.bcits.bfm.model.Vendors;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.DrGroupIdService;
import com.bcits.bfm.service.VendorsManagement.ItemMasterService;
import com.bcits.bfm.service.VendorsManagement.RequisitionDetailsService;
import com.bcits.bfm.service.VendorsManagement.RequisitionService;
import com.bcits.bfm.service.VendorsManagement.UomService;
import com.bcits.bfm.service.VendorsManagement.VendorContractsService;
import com.bcits.bfm.service.VendorsManagement.VendorPriceListService;
import com.bcits.bfm.service.VendorsManagement.VendorsService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.inventoryManagement.StoreItemLedgerService;
import com.bcits.bfm.service.inventoryManagement.StoreMasterService;
import com.bcits.bfm.service.userManagement.DesignationService;
import com.bcits.bfm.util.BfmLogger;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class ProcurementManagementControlller
{
	static Logger logger = LoggerFactory.getLogger(ProcurementManagementControlller.class);

	@Autowired
	private RequisitionService requisitionService;

	@Autowired
	private RequisitionDetailsService requisitionDetailsService;

	@Autowired
	private ItemMasterService itemMasterService;

	@Autowired
	private Validator validator;

	@Autowired
	private DrGroupIdService drGroupIdService;

	@Autowired
	private BreadCrumbTreeService breadCrumbService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UomService uomService;

	@Autowired
	private VendorsService vendorService;

	@Autowired
	private DesignationService designationService;

	@Autowired
	private PersonService personService;

	@Autowired
	private VendorContractsService vendorContractsService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private VendorPriceListService vendorPricelistService;

	@Autowired
	private VendorsService vendorsService;

	@Autowired
	private StoreMasterService storeMasterService;

	@Autowired
	private StoreItemLedgerService storeItemLedgerService;

	DateTimeCalender dateTimeCalender = new DateTimeCalender();

	/****************************************************<-- FOR REQUISITION USE CASE -->***************************************************/
	@RequestMapping(value = "/requisitions")
	public String index(ModelMap model,HttpServletRequest request) 
	{
		model.addAttribute("ViewName", "Procurement");
		breadCrumbService.addNode("nodeID", 0,request);
		breadCrumbService.addNode("Procurement", 1,request);
		breadCrumbService.addNode("Manage Requsitions", 2, request);
		return "procurement/requisition";
	}

	//For Reading Contenets To Jsp
	@RequestMapping(value = "/procurement/requisition/read", method = RequestMethod.POST)
	public @ResponseBody List<?> read() 
	{	
		return requisitionService.setResponse();
		//return requisitionService.findAllRequisitionRequiredFields();
	}

	//For Reading Contenets To Jsp
	@SuppressWarnings("serial")
	@RequestMapping(value = "/procurement/vendors/readVendors", method = RequestMethod.GET)
	public @ResponseBody List<?> readVendors() 
	{	
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
		for (final Requisition record : requisitionService.findAll()) {
			/*if(record.getVendors().getPerson().getPersonType().equalsIgnoreCase("Vendor"))
        	{*/        	
			result.add(new HashMap<String, Object>() 
					{{
						put("personId", record.getVendors().getPerson().getPersonId());
						put("vendorId", record.getVendors().getVendorId());
						put("fullVendorName", record.getVendors().getPerson().getFirstName()+" "+record.getPerson().getLastName());
						put("personType", record.getVendors().getPerson().getPersonType());

						if(record.getVendors().getPerson().getPersonType().contains("Vendor"))
						{
							put("personType", record.getVendors().getPerson().getPersonType());		            		
						}	         
					}});
			//}
		}
		return result;
	}


	/****  FOR READING THE DAPARTMENT  *****/
	@RequestMapping(value = "/procurement/vendorUsers/readDepartmentForVendors", method = RequestMethod.GET)
	public @ResponseBody List<?> getDepartment()
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<?> deptList = designationService.getDistinctDepartments();		
		Map<String, Object> deptMap = null;
		for (Iterator<?> iterator = deptList.iterator(); iterator.hasNext();)
		{
			final Object[] values = (Object[]) iterator.next();			
			deptMap = new HashMap<String, Object>();
			deptMap.put("dept_Id", (Integer)values[0]);
			deptMap.put("dept_Name", (String)values[1]);
			deptMap.put("dr_Status", (String)values[2]);
			result.add(deptMap);
		}

		return result;
	}

	@RequestMapping(value = "/procurement/readUserStaff/readUserStaffPersons", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getAllPersonNames(HttpServletRequest request) 
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<?> deptList = requisitionService.getDepartments();		
		logger.info("Department size"+deptList.size());
		Map<String, Object> deptMap = null;
		for (Iterator<?> iterator = deptList.iterator(); iterator.hasNext();)
		{
			final Object[] values = (Object[]) iterator.next();			
			deptMap = new HashMap<String, Object>();

			/*if((Integer) values[1]!=1 && (Integer)values[0]!=1)
 	       	{*/
			deptMap.put("dept_Id", (Integer)values[0]); 
			deptMap.put("personId", (Integer) values[1]);		
			deptMap.put("fullName", (String)values[2]+" "+(String)values[3]);
			//	}	       	
			deptMap.put("urLoginName", (String) values[4]);

			result.add(deptMap);
		}
		return result;
	}
	/****  END OFR READING DEPARTMENT  ****/

	@SuppressWarnings("serial")
	@RequestMapping(value = "/procurement/requisition/getVendorNamesForRequisition", method = RequestMethod.GET)
	public @ResponseBody List<?> getVendorNamesAutoUrl() 
	{
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(final Vendors record : vendorsService.findAll())
		{
			final String fname = record.getPerson().getFirstName();
			final String lname = record.getPerson().getLastName();
			final int personid = record.getPerson().getPersonId();
			final String status = record.getPerson().getPersonStatus();
			if(status.equalsIgnoreCase("active"))
			{
				if(personid!=1)
				{
					result.add(new HashMap<String,Object>()
							{{
								if(lname==null)
								{
									put("fullVendorName",fname);
								}
								else
								{
									put("fullVendorName",fname+" "+lname);
								}
								put("personId",record.getPerson().getPersonId());
								put("personType",record.getPerson().getPersonType());
								put("vendorId",record.getVendorId());
							}});
				}
			}
		}
		return result;
	} 


	@RequestMapping(value = "/procurement/requisition/getRequisitionNames", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getAllRequisitionNames() {
		return 	requisitionService.getRequisitionName();
	}

	//For Inserting the values
	@SuppressWarnings("unused")
	@RequestMapping(value = "/procurement/requisition/create", method = RequestMethod.GET)
	public @ResponseBody Object createRequisitionUrl(Map<String, Object> map,@ModelAttribute("requisition") Requisition requisition, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req,HttpSession session) throws ParseException 
	{
		String reqDt = req.getParameter("reqDate");
		String reqByDt = req.getParameter("reqByDate");

		requisition.setReqDate(dateTimeCalender.getDateToStore(reqDt));
		requisition.setReqByDate(dateTimeCalender.getDateToStore(reqByDt));

		String st1 = (String) session.getAttribute("createdBy");
		String st2 = (String)session.getAttribute("lastupdatedBy");

		requisition.setCreatedBy((String)SessionData.getUserDetails().get("userID"));
		requisition.setLastUpdatedBy((String)SessionData.getUserDetails().get("userID"));
		requisition.setLastUpdatedDt(new Timestamp(new Date().getTime()));

		int storeid = requisition.getStoreId();
		int vendorid = requisition.getVendorId();
		
		logger.info("**********Store Id **************"+storeid);
		logger.info("**********Vendor Id **************"+vendorid);

		if(storeid == 0)
		{
			logger.info("Inside If store & Vendor");
			requisition.setStoreId(1);
			if(vendorid == 0){
			requisition.setVendorId(1);
			}
			requisitionService.save(requisition);
		}
		else
		{
			requisition.setVendorId(Integer.parseInt(req.getParameter("vendorId")));
			requisitionService.save(requisition);
		}
		return requisitionService.setResponse();
	}

	@RequestMapping(value = "/procurement/requisition/update", method = RequestMethod.GET)
	public @ResponseBody Object updateRequisitionUrl(Map<String, Object> map,@ModelAttribute("requisition")Requisition requisition, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req,HttpSession session) throws ParseException 
	{
		String reqDt = req.getParameter("reqDate");
		String reqByDt = req.getParameter("reqByDate");

		requisition.setReqDate(dateTimeCalender.getDateToStore(reqDt));
		requisition.setReqByDate(dateTimeCalender.getDateToStore(reqByDt));

		requisition.setCreatedBy((String)SessionData.getUserDetails().get("userID"));
		requisition.setLastUpdatedBy((String)SessionData.getUserDetails().get("userID"));
		requisition.setLastUpdatedDt(new Timestamp(new Date().getTime()));

		int storeid = requisition.getStoreId();
		if(storeid == 0)
		{
			requisition.setStoreId(1);
			requisitionService.update(requisition);
		}
		else
		{
			requisitionService.update(requisition);
		}
		//requisitionService.update(requisition);
		return requisitionService.setResponse();
	}

	/******************** @throws IOException FOR REQUISITION ACTIVATING AND DE-ACTIVATING**************************************************/
	@SuppressWarnings("unused")
	@RequestMapping(value = "/procurement/requisition/requisitionStatus/{reqId}/{operation}", method = RequestMethod.POST)
	public String RoleStatus( @PathVariable int reqId,@PathVariable String operation,ModelMap model, HttpServletRequest request,HttpServletResponse response, final Locale locale) throws IOException
	{		

		String conf = request.getParameter("force");
		PrintWriter out = response.getWriter();
		int id = requisitionService.getReqIdFromChild(reqId);

		if(reqId == id)
		{
			requisitionService.setRequisitionStatus(reqId, operation, response, messageSource, locale);
		}
		else{
			out.write("No Requisition Details Added");
		}
		return null;
	}
	/**************************EENNDDFOR REQUISITION ACTIVATING AND DE-ACTIVATING*************************/

	/********** For Deleting The records ************/	
	@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
	@RequestMapping(value = "/procurement/requisition/destroy", method = RequestMethod.POST)
	public @ResponseBody Object destroy(@RequestBody Map<String, Object> map,@ModelAttribute("requisition") Requisition requisition, 
			BindingResult bindingResult,ModelMap model, 
			SessionStatus sessionStatus, final Locale locale) throws IOException 
			{	
		JsonResponse errorResponse = new JsonResponse();
		int reqid = (int)map.get("reqId");
		requisition = requisitionService.find(reqid);
		String reqStatus = requisition.getStatus();

		if(reqStatus.equalsIgnoreCase("Approved"))
		{
			HashMap<String, String> errorMapResponse = new HashMap<String, String>()
					{
				{
					put("ApprovedRequisitionDestroyError", messageSource.getMessage("Requisition.deleteApprovedRequisitionError", null, locale));
				}
					};
					errorResponse.setStatus("ApprovedRequisitionDestroyError");
					errorResponse.setResult(errorMapResponse);
					return errorResponse;
		}
		if(reqStatus.equalsIgnoreCase("PO PLaced"))
		{
			HashMap<String, String> errorMapResponse = new HashMap<String, String>()
					{
				{
					put("POPlacedRequisitionDestroyError", messageSource.getMessage("Requisition.POPlacedRequisitionDestroyError", null, locale));
				}
					};
					errorResponse.setStatus("POPlacedRequisitionDestroyError");
					errorResponse.setResult(errorMapResponse);
					return errorResponse;
		}		
		if(reqStatus.equalsIgnoreCase("Rejected") || reqStatus.equalsIgnoreCase("Created"))
		{
			logger.info("Requisition Status"+reqStatus);
			int rdPkId = 0;
			List<RequisitionDetails> reqDetails = requisitionDetailsService.findAll();
			List uniqueIdVal = new ArrayList(new HashSet(reqDetails));
			for (Iterator<RequisitionDetails> iterator = uniqueIdVal.iterator(); iterator.hasNext();) 
			{
				RequisitionDetails req2 = (RequisitionDetails) iterator.next();				
				int rdReqId = req2.getRequisition().getReqId();
				rdPkId = 0;
				if(rdReqId==reqid)
				{	
					rdPkId = req2.getRdId();
					if(rdReqId == reqid)
					{
						requisitionDetailsService.delete(rdPkId);
					}
				}
			}
			Requisition findReqId = requisitionService.find(reqid);
			int requisitionId = findReqId.getReqId();
			requisitionService.delete(requisitionId);		
			JsonResponse jsonResponse = new JsonResponse();
			//jsonResponse.setStatus("deleted");
			return jsonResponse;
		}		
		return requisition;
			}	
	/********  End *********/

	/**** FOR FILTERS *****/
	/**
	 * For Reading Requisition Names 
	 * @return Requisition Names
	 */
	@RequestMapping(value = "/procurement/requisition/getReqNamesFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getReqNames()
	{ 
		Set<String> result = new HashSet<String>();
		for (Requisition vc : requisitionService.findAll())
		{	
			result.add(vc.getReqName());		    	
		}
		return result;
	}

	/**
	 * For Reading Requisition Type 
	 * @return Requisition Type
	 */
	@RequestMapping(value = "/procurement/requisition/getReqTypeFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getReqType()
	{ 
		Set<String> result = new HashSet<String>();
		for (Requisition requisition : requisitionService.findAll())
		{	
			result.add(requisition.getReqType());		    	
		}
		return result;
	}

	/**
	 * For Reading Requisition Department 
	 * @return Requisition Department
	 */
	@RequestMapping(value = "/procurement/requisition/getReqDepartmentFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getReqDepartmentFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (Requisition requisition : requisitionService.findAll())
		{	
			result.add(requisition.getDepartment().getDept_Name());		    	
		}
		return result;
	}

	@RequestMapping(value = "/procurement/requisition/getStoreNameFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getStoreNameFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (Requisition requisition : requisitionService.findAll())
		{	
			result.add(requisition.getStoreMaster().getStoreName());		    	
		}
		return result;
	}
	/**
	 * For Reading Requisition Users 
	 * @return Requisition Users
	 */
	@RequestMapping(value = "/procurement/requisition/getReqUsersFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getReqUsersFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (Requisition requisition : requisitionService.findAll())
		{	
			result.add(requisition.getPerson().getFirstName()+" "+requisition.getPerson().getLastName());		    	
		}
		return result;
	}


	/**
	 * For Reading Requisition Vendors 
	 * @return Requisition Vendors
	 */
	@RequestMapping(value = "/procurement/requisition/getVendorsFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getVendorsFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (Requisition requisition : requisitionService.findAll())
		{	
			if(requisition.getVendors().getPerson().getLastName()!=null)
			{
				result.add(requisition.getVendors().getPerson().getFirstName()+" "+requisition.getVendors().getPerson().getLastName());
			}
			else if(requisition.getVendors().getPerson().getLastName() == null)
			{
				result.add(requisition.getVendors().getPerson().getFirstName());
			}
		}
		return result;
	}

	/**
	 * For Reading Requisition Quote 
	 * @return Requisition Quote
	 */
	@RequestMapping(value = "/procurement/requisition/getQuoteFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getQuoteFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (Requisition requisition : requisitionService.findAll())
		{	
			result.add(requisition.getReqVendorQuoteRequisition());		    	
		}
		return result;
	}

	/**
	 * For Reading Requisition Description 
	 * @return Requisition Description
	 */
	@RequestMapping(value = "/procurement/requisition/getDescriptionFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getDescriptionFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (Requisition requisition : requisitionService.findAll())
		{	
			result.add(requisition.getReqDescription());		    	
		}
		return result;
	}

	/**
	 * For Reading Requisition Description 
	 * @return Requisition Description
	 */
	@RequestMapping(value = "/procurement/requisition/getReqStatusFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getReqStatusFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (Requisition requisition : requisitionService.findAll())
		{	
			result.add(requisition.getStatus());		    	
		}
		return result;
	}	
	/************************************************************<--  END FOR REQUISITION USE CASE -->*******************************************************************/


	/********************************************************<-- FOR REQUSITION_DETAILS USE CASE -->********************************************************************/	
	@RequestMapping(value = "/procurement/requisitionDetails/readRequisitionDetails/{id}", method = RequestMethod.GET)
	public @ResponseBody List<?> readUserIdBasedOnRoleId(@PathVariable int id) 
	{
		logger.info("READING ALL REQUISITION DETAILS FROM CONTROLLER");
		return requisitionDetailsService.findAllRequiredRequisitionDetailsFields(id);
	}

	/***  FOR READING UOM  ***/
	@SuppressWarnings("serial")
	@RequestMapping(value = "/procurement/vendors/readUomDetails", method = RequestMethod.GET)
	public @ResponseBody List<?> readUomDetails() 
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
		for (final UnitOfMeasurement record : uomService.findAll()) {
			result.add(new HashMap<String, Object>() 
					{{
						put("imId", record.getItemMaster().getImId());
						put("uom", record.getUom());
						put("uomId",record.getUomId());
						put("uomCode",record.getUom()+" "+record.getCode());            	
					}});
		}
		return result;
	}
	/***  END FOR READING UOM  ****/


	/***  FOR READING UOM  ***/
	@SuppressWarnings("serial")
	@RequestMapping(value = "/procurement/vendor/readOnlyVendorsDetails", method = RequestMethod.GET)
	public @ResponseBody List<?> readVendorsDetails() 
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
		for (final Vendors record : vendorService.findAll())
		{
			if(record.getVendorId()!=0)
			{
				result.add(new HashMap<String, Object>() 
						{{
							put("vendorId", record.getVendorId());
							put("fullVendorName",record.getPerson().getFirstName()+" "+record.getPerson().getLastName());
						}});
			}
		}
		return result;
	}
	/***  END FOR READING UOM  ****/

	@SuppressWarnings("serial")
	@RequestMapping(value = "/procurement/requisitionDetails/getRequisitionId", method = RequestMethod.GET)
	public @ResponseBody List<?> getRequisitionId() 
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
		for (final Requisition record : requisitionService.findAll())
		{
			result.add(new HashMap<String, Object>() 
					{{
						put("reqId", record.getReqId());
					}});
		}
		return result;
	}

	/////  Read Item Master Details //////
	@SuppressWarnings("serial")
	@RequestMapping(value = "/procurement/requisitionDetails/getItemMasterId", method = RequestMethod.GET)
	public @ResponseBody List<?> getItemMasterId() 
	{
		BfmLogger.logger.info("INSIDE ITEM MASTER");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
		for (final ItemMaster record : itemMasterService.findAll())
		{
			result.add(new HashMap<String, Object>() 
					{{
						put("imId", record.getImId());
						put("imName", record.getImName());
						put("imNameType",record.getImName()+" "+record.getImType());
					}});
		}
		return result;
	}

	@RequestMapping(value = "/procurement/requisitionDetails/createRequisitionDetails/{id}", method = RequestMethod.POST)
	public @ResponseBody Object createReqDeatils(@RequestBody Map<String, Object> map, @ModelAttribute RequisitionDetails requisitionDetails, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale,@PathVariable int id) 
	{			
		Timestamp check = new Timestamp(new Date().getTime());
		requisitionDetails.setCreatedBy("sssss");
		drGroupIdService.save(new DrGroupId(requisitionDetails.getCreatedBy(), check));
		requisitionDetails.setDrGroupId(drGroupIdService.getNextVal(requisitionDetails.getCreatedBy(), check));	

		requisitionDetails = requisitionDetailsService.getBeanObject(map,"save",requisitionDetails);
		validator.validate(requisitionDetails, bindingResult);	
		if(requisitionDetails == null)
		{
			throw new NullPointerException("NULL VALUES PRESENT");
		}
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors())
		{
			BfmLogger.logger.info("Error method() in requisition Create Controller");
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			BfmLogger.logger.info("Binding Result:"+bindingResult);
			return errorResponse;
		}
		else
		{	
			requisitionDetails.setRequisitionId(id);		 	
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("requisitionId", id);
			int count = (int) requisitionDetailsService.countAll(params);		 	
			if(count == 0)
			{
				count = 1;
				requisitionDetails.setRdSlno(count);
				requisitionDetailsService.save(requisitionDetails);
			}
			else
			{
				count++;
				requisitionDetails.setRdSlno(count);
				requisitionDetailsService.save(requisitionDetails);
			}
			return requisitionDetails;
		}
	}

	//***********For Updating the new ItemMaster details******************//
	@RequestMapping(value = "/procurement/requisitionDetails/updateRequisitionDetails/{id}", method = RequestMethod.POST)
	public @ResponseBody Object updateMail(@RequestBody Map<String, Object> map, @ModelAttribute RequisitionDetails requisitionDetails, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale,@PathVariable int id)
	{	
		BfmLogger.logger.info("Inside Controller Update ()");		

		requisitionDetails = requisitionDetailsService.getBeanObject(map,"update",requisitionDetails);		
		validator.validate(requisitionDetails, bindingResult);		
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors())
		{
			BfmLogger.logger.info("Error method() in ItemMaster update Controller");
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			BfmLogger.logger.info("Binding Result:"+bindingResult);
			return errorResponse;
		}
		else
		{				
			BfmLogger.logger.info("Inside else part of update method()");
			requisitionDetails.setRequisitionId(id);
			requisitionDetailsService.update(requisitionDetails);			
			return requisitionDetails;
		}
	}	
	/********* END OF UPDATE ************/

	/********** For Deleting The records ************/	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/procurement/requisitionDetails/destroyRequisitionDetails", method = RequestMethod.POST)
	public @ResponseBody Object destroyRequisitionDetails(@RequestBody Map<String, Object> map,@ModelAttribute("requisitionDetails") RequisitionDetails requisitionDetails, 
			BindingResult bindingResult,ModelMap model,SessionStatus sessionStatus, final Locale locale) throws IOException 
			{	
		JsonResponse errorResponse = new JsonResponse();
		int rdid = (Integer) map.get("rdId");			
		RequisitionDetails rd = requisitionDetailsService.find(rdid);
		int reqIdval = rd.getRequisitionId();

		Requisition req = requisitionService.find(reqIdval);
		String reqStatus = req.getStatus();

		if(reqStatus.equalsIgnoreCase("Approved"))
		{
			HashMap<String, String> errorMapResponse = new HashMap<String, String>()
					{
				{
					put("deletingRequisitionDetailsError", messageSource.getMessage("RequisitionDetails.deletingRequisitionDetailsError", null, locale));
				}
					};
					errorResponse.setStatus("deletingRequisitionDetailsError");
					errorResponse.setResult(errorMapResponse);
					return errorResponse;
		}
		if(reqStatus.equalsIgnoreCase("PO Placed"))
		{
			HashMap<String, String> errorMapResponse = new HashMap<String, String>()
					{
				{
					put("deletingPOPLacedRequisitionDetailsError", messageSource.getMessage("RequisitionDetails.deletingPOPLacedRequisitionDetailsError", null, locale));
				}
					};
					errorResponse.setStatus("deletingPOPLacedRequisitionDetailsError");
					errorResponse.setResult(errorMapResponse);
					return errorResponse;
		}
		else
		{
			requisitionDetailsService.delete(rdid);
		}
		return requisitionDetails;		
			}	
	/********  End *********/

	/**********  FOR REQUISITION DETAILS FILTER  ******************/
	//For REqDetails SLNo Filter
	@RequestMapping(value = "/procurement/reqDetails/getReqDetailSlNoFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getReqDetailSlNoFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (RequisitionDetails requisitionDetails : requisitionDetailsService.findAll())
		{	
			int slno = requisitionDetails.getRdSlno();
			String reqSlNo = String.valueOf(slno);
			result.add(reqSlNo);		    	
		}
		return result;
	}

	// FOR ReqDetails ItemName Filter
	@RequestMapping(value = "/procurement/reqDetails/getItemNameFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getItemNameFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (RequisitionDetails requisitionDetails : requisitionDetailsService.findAll())
		{	
			result.add(requisitionDetails.getItemMaster().getImName());		    	
		}
		return result;
	}

	//For ReqDetails UOM Names
	@RequestMapping(value = "/procurement/reqDetails/getReqDetailsUOMNameFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getReqDetailsUOMNameFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (RequisitionDetails requisitionDetails : requisitionDetailsService.findAll())
		{	
			result.add(requisitionDetails.getUnitOfMeasurement().getUom());		    	
		}
		return result;
	}

	//For ReqDetails UOM Names
	@RequestMapping(value = "/procurement/reqDetails/getReqDetailsDescriptionFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getReqDetailsDescriptionFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (RequisitionDetails requisitionDetails : requisitionDetailsService.findAll())
		{	
			result.add(requisitionDetails.getRdDescription());		    	
		}
		return result;
	}

	//For ReqDetails UOM Names
	@RequestMapping(value = "/procurement/reqDetails/getReqDetailsQuantityFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getReqDetailsQuantityFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (RequisitionDetails requisitionDetails : requisitionDetailsService.findAll())
		{	
			int qt = requisitionDetails.getRdQuantity();
			String reqQuantity = String.valueOf(qt);
			result.add(reqQuantity);		    	
		}
		return result;
	}
	/**********  END FOR REQUISITION DETAILS FILTER  ****************/

	/********************************************************<-- END FOR REQUISITION_DETAILS USE CASE -->***************************************************************/


	/*******************************************************<-- FOR ITEM MASTER USE CASE --> **************************************************************************/ 
	@RequestMapping(value = "/itemMasters")
	public String indexForItemMaster(ModelMap model,HttpServletRequest request) 
	{
		model.addAttribute("ViewName", "Inventory ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("inventory", 1, request);
		breadCrumbService.addNode("Manage Item Master", 2, request);
		return "procurement/itemMaster";
	}

	/*****For Reading ItemMasterDetails To Jsp*******/
	@RequestMapping(value = "/procurement/itemMaster/read", method = RequestMethod.GET)
	public @ResponseBody List<?> readItemMaster() 
	{	
		//List<ItemMaster> itemMaster = itemMasterService.findAll();
		return itemMasterService.setResponse();
	}	
	/*******  END  ********/

	/** For Uniqueness Chack **/	
	@RequestMapping(value = "/procurement/itemMaster/getItemNames", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getItemNames() {
		return 	itemMasterService.getItemNames();
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	/*************For Creating/Entering the new ItemMaster details***************/
	@RequestMapping(value = "/procurement/itemMaster/create", method = RequestMethod.GET)
	public @ResponseBody Object create(@ModelAttribute("itemMaster")ItemMaster itemMaster, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale) throws InterruptedException 
	{	
		//Item Master Persist
		String description = itemMaster.getImDescription();
		String desc_trim = description.trim();
		itemMaster.setImDescription(desc_trim);		
		logger.info("Item Master Created Date"+itemMaster.getImCreatedDate());		
		itemMasterService.save(itemMaster);


		//Getting last generated ItemMaster id value		
		List id = itemMasterService.getMaxCount();
		@SuppressWarnings("unchecked")
		Iterator<Integer> id1 = id.iterator();
		int maxId = 0;
		String cno = "";
		while(id1.hasNext())
		{		
			maxId = id1.next();
		}
		int itemId = itemMaster.getImId(); //last generated id value is: itemId

		Integer uomConv = 1;
		double conersionVal = uomConv.doubleValue();

		// Setting the itemId and other details to the child record i.e "UnitOfMeasurement"
		UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
		unitOfMeasurement.setBaseUom("Yes");
		unitOfMeasurement.setUom(itemMaster.getUom());
		unitOfMeasurement.setCode(itemMaster.getCode());
		unitOfMeasurement.setImId(itemId);
		unitOfMeasurement.setStatus("Created");
		unitOfMeasurement.setUomConversion(conersionVal);

		itemMaster.setImPurchaseUom(itemMaster.getUom());
		itemMaster.setImUomIssue(itemMaster.getCode());
		itemMasterService.update(itemMaster);

		uomService.save(unitOfMeasurement); //Persisting data inside UnitOfMeasurement grid with(ItemId,BaseUom,Code) 
		return itemMasterService.setResponse();
	}	
	/****************** End *****************/


	/***********For Updating the new ItemMaster details******************/
	@RequestMapping(value = "/procurement/itemMaster/update", method = RequestMethod.GET)
	public @ResponseBody Object updateMail(@ModelAttribute("itemMaster")ItemMaster itemMaster, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale) throws InterruptedException
	{	
		itemMasterService.update(itemMaster);
		return itemMasterService.setResponse();
	}	
	/********* END OF UPDATE ************/

	/********** For Deleting The records ************/	
	@RequestMapping(value = "/procurement/itemMaster/destroy", method = RequestMethod.POST)
	public @ResponseBody ItemMaster destroy(@RequestBody Map<String, Object> model,ItemMaster itemMaster) 
	{	
		int id = (Integer) model.get("imId");		
		itemMasterService.delete(id);
		return itemMaster;		
	}	
	/********  End *********/

	//Cron Job For Checking Optimal Stock With Ledger Balance
	//@Scheduled(cron = "${scheduling.job.cron.itemMasterCron}")
	 @SuppressWarnings("rawtypes")
	public void runCronJob() throws ParseException
	 { 
	  int imIdInLedger = 0;
	  double ledgerBal = 0;
	  int ledgerBalanceVal = 0;
	  
	  int itemId = 0;
	  int uomId = 0;
	  int optimalStock = 0;
	  int storeIdFromLedger = 0;
	  
	  
	  List<?> ledgerDetails = itemMasterService.getDetailsFromLedgerAndItemMasterForCronScheduler();  
	  for (Iterator<?> iterator = ledgerDetails.iterator(); iterator.hasNext();)
	  {
	   final Object[] values = (Object[]) iterator.next(); 
	   imIdInLedger = (Integer)values[0];
	   ledgerBal = (Double)values[1];
	   ledgerBalanceVal = (int) ledgerBal;
	   itemId = (Integer)values[2];
	   optimalStock = (Integer)values[3];
	   uomId = (Integer)values[5];
	   if(imIdInLedger == itemId)
	   {
	    logger.info("EQUAL "+imIdInLedger+"=="+itemId);
	    if(ledgerBalanceVal < optimalStock)
	    {
	     storeIdFromLedger = (Integer)values[4];
	     logger.info("LESS "+ledgerBalanceVal+" < "+optimalStock);
	     itemMasterService.updateReorderLevelStatus(itemId);
	     
	    logger.info("--------------------------------------\n\n"+storeIdFromLedger);
	     
	     
	     List<?> reqIdVal = itemMasterService.getReqId(storeIdFromLedger);
	     for (Iterator reqIdIt = reqIdVal.iterator(); reqIdIt.hasNext();) 
	     {
	      int reqIdFromRequisition = (int) reqIdIt.next();
	      logger.info("==============================================\n\n"+reqIdFromRequisition);
	      
	      List<?> reqDetails = itemMasterService.getReqDetails(reqIdFromRequisition);
	      for (Iterator<?> reqVal = reqDetails.iterator(); reqVal.hasNext();)
	      {
	       final Object[] reqValues = (Object[]) reqVal.next();   
	              int deptId = (Integer)reqValues[0];
	              int personId = (Integer)reqValues[1];
	              int vendorId = (Integer)reqValues[2];
	              String createdBy = (String)reqValues[3];
	        String lastUpdatedBy = (String)reqValues[4];
	        String qoute = (String)reqValues[5];
	        String reqName = (String)reqValues[6];
	              
	              Requisition req = new Requisition();
	              req.setDept_Id(deptId);
	              req.setPersonId(personId);
	              req.setVendorId(vendorId);
	              req.setStoreId(storeIdFromLedger);
	              req.setReqVendorQuoteRequisition(qoute);
	              req.setCreatedBy(createdBy);
	              req.setLastUpdatedBy(lastUpdatedBy);
	              req.setReqVendorQuoteRequisition(qoute);
	              req.setReqName(reqName);
	              req.setReqDate((Date)reqValues[7]);
	              req.setReqByDate((Date)reqValues[8]); 
	              req.setReqDescription((String)reqValues[9]);
	              req.setReqType("Item Supply");
	              req.setStatus("Automated Requisition");
	              req.setLastUpdatedDt(new Timestamp(new Date().getTime()));
	              
	              requisitionService.save(req);
	              
	              int lastReqIdGenerated = req.getReqId();
	              
	              RequisitionDetails details = new RequisitionDetails();
	              details.setRequisitionId(lastReqIdGenerated);
	              details.setImId(itemId);
	              details.setUomId(uomId);
	              details.setCreatedBy(createdBy);
	              details.setLastUpdatedBy(lastUpdatedBy);
	              details.setDn_Id(1);
	              details.setRdQuantity(ledgerBalanceVal);
	              requisitionDetailsService.save(details);
	      }
	     }
	     
	    }
	   }
	  }
	 }
	/************************************  FOR UOM ****************************************************/	

	/************  For reding values to ITEM PURCHASE UOM  ****************/
	@SuppressWarnings({ "serial", "unchecked" })
	@RequestMapping(value = "/procurement/itemMaster/readItemPurchaseUom/{imId}", method = RequestMethod.GET)
	public @ResponseBody List<?> readItemPurchaseUom(@PathVariable int imId) 
	{	
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("imId", imId);
		for (final UnitOfMeasurement record : (List<UnitOfMeasurement>) commonService.selectQuery("UnitOfMeasurement", null, params)) {
			result.add(new HashMap<String, Object>() 
					{{            	
						put("imPurchaseUom", record.getUom());            	
					}});
		}
		logger.info("From UOM controler");
		return result;	
	}
	/***********   END ********************************/

	/************  For reding values to ITEM UOM ISSUE UOM  ****************/
	@SuppressWarnings({ "serial", "unchecked" })
	@RequestMapping(value = "/procurement/itemMaster/readItemUomIssue/{imId}", method = RequestMethod.GET)
	public @ResponseBody List<?> readItemUomIssue(@PathVariable int imId) 
	{	
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("imId", imId);
		for (final UnitOfMeasurement record : (List<UnitOfMeasurement>) commonService.selectQuery("UnitOfMeasurement", null, params)) {
			result.add(new HashMap<String, Object>() 
					{{            	
						put("imUomIssue", record.getCode());            	
					}});
		}
		logger.info("From UOM controler");
		return result;
	}
	/***********   END ********************************/ 

	//For Reading the data to the graph
	/*@RequestMapping(value = "/itemMaster/readItemCreatedDateForGraph", method = RequestMethod.POST)
	public @ResponseBody List<?> readDetailsForGraph(HttpServletResponse response) throws IOException
	{
		List<GraphModelView> result = new ArrayList<GraphModelView>();  
		for (final Object[] values :  itemMasterService.readStockAndItemCreatedDateForGraph())
		{	

			GraphModelView GraphModelView = new GraphModelView();
			String monthVal = (String) values[2];

			if(monthVal.equalsIgnoreCase("01"))
			{
				logger.info("Month Value Is -- > 01"+"\nOptimal Stock For January == "+(Long)values[1]);
				GraphModelView.setMonthValue("Jan");
				long l = (Long)values[1];
				GraphModelView.setGraphIndex((int)(l));
			}
			//Feb
			if(monthVal.equalsIgnoreCase("02"))
			{
				logger.info("Month Value Is -- > 02"+"\nOptimal Stock For February == "+(Long)values[1]);
				GraphModelView.setMonthValue("Feb");
				long l = (Long)values[1];
				GraphModelView.setGraphIndex((int)(l));
			}				
			//March
			if(monthVal.equalsIgnoreCase("03"))
			{
				logger.info("Month Value Is -- > 03"+"\nOptimal Stock For March == "+(Long)values[1]);
				GraphModelView.setMonthValue("Mar");
				long l = (Long)values[1];
				GraphModelView.setGraphIndex((int)(l));
			}				
			//April
			if(monthVal.equalsIgnoreCase("04"))
			{
				logger.info("Month Value Is -- > 04"+"\nOptimal Stock For April == "+(Long)values[1]);
				GraphModelView.setMonthValue("Apr");
				long l = (Long)values[1];
				GraphModelView.setGraphIndex((int)(l));
			}				
			//May
			if(monthVal.equalsIgnoreCase("05"))
			{
				logger.info("Month Value Is -- > 05"+"\nOptimal Stock For May == "+(Long)values[1]);
				GraphModelView.setMonthValue("May");
				long l = (Long)values[1];
				GraphModelView.setGraphIndex((int)(l));
			}				
			//June
			if(monthVal.equalsIgnoreCase("06"))
			{
				logger.info("Month Value Is -- > 06"+"\nOptimal Stock For June == "+(Long)values[1]);
				GraphModelView.setMonthValue("Jun");
				long l = (Long)values[1];
				GraphModelView.setGraphIndex((int)(l));
			}				
			//July
			if(monthVal.equalsIgnoreCase("07"))
			{
				logger.info("Month Value Is -- > 07"+"\nOptimal Stock For July == "+(Long)values[1]);
				GraphModelView.setMonthValue("Jul");
				long l = (Long)values[1];
				GraphModelView.setGraphIndex((int)(l));
			}				
			//August
			if(monthVal.equalsIgnoreCase("08"))
			{
				logger.info("Month Value Is -- > 08"+"\nOptimal Stock For August == "+(Long)values[1]);
				GraphModelView.setMonthValue("Aug");
				long l = (Long)values[1];
				GraphModelView.setGraphIndex((int)(l));
			}				
			//September
			if(monthVal.equalsIgnoreCase("09"))
			{
				logger.info("Month Value Is -- > 09"+"\nOptimal Stock For September == "+(Long)values[1]);
				GraphModelView.setMonthValue("Sept");
				long l = (Long)values[1];
				GraphModelView.setGraphIndex((int)(l));
			}				
			//October
			if(monthVal.equalsIgnoreCase("10"))
			{
				logger.info("Month Value Is -- > 10"+"\nOptimal Stock For October == "+(Long)values[1]);
				GraphModelView.setMonthValue("Oct");
				long l = (Long)values[1];
				GraphModelView.setGraphIndex((int)(l));
			}				
			//November
			if(monthVal.equalsIgnoreCase("11"))
			{
				logger.info("Month Value Is -- > 11"+"\nOptimal Stock For November == "+(Long)values[1]);
				GraphModelView.setMonthValue("Nov");
				long l = (Long)values[1];
				GraphModelView.setGraphIndex((int)(l));
			}				
			//December
			if(monthVal.equalsIgnoreCase("12"))
			{
				logger.info("Month Value Is -- > 12"+"\nOptimal Stock For December == "+(Long)values[1]);
				GraphModelView.setMonthValue("Dec");
				long l = (Long)values[1];
				GraphModelView.setGraphIndex((int)(l));
			}
			result.add(GraphModelView);
		}
		return result;	
	}*/

	/************  ITEM_MASTER FILTER URLS'S **********/
	@RequestMapping(value = "/procurement/itemMasterFilter/getItemMasterGroupFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getItemMasterGroupFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (ItemMaster itemMaster : itemMasterService.findAll())
		{	
			result.add(itemMaster.getImGroup());		    	
		}
		return result;
	}

	@RequestMapping(value = "/procurement/itemMasterFilter/getItemMasterReqTypeFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getItemMasterReqTypeFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (ItemMaster itemMaster : itemMasterService.findAll())
		{	
			result.add(itemMaster.getImType());		    	
		}
		return result;
	}

	@RequestMapping(value = "/procurement/itemMasterFilter/getItemMasterUOMFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getItemMasterUOMFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (ItemMaster itemMaster : itemMasterService.findAll())
		{	
			result.add(itemMaster.getUomClass());		    	
		}
		return result;
	}

	@RequestMapping(value = "/procurement/itemMasterFilter/getItemMasterNameFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getItemMasterNameFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (ItemMaster itemMaster : itemMasterService.findAll())
		{	
			result.add(itemMaster.getImName());		    	
		}
		return result;
	}

	@RequestMapping(value = "/procurement/itemMasterFilter/getItemMasterDescriptionNameFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getItemMasterDescriptionNameFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (ItemMaster itemMaster : itemMasterService.findAll())
		{	
			result.add(itemMaster.getImDescription());		    	
		}
		return result;
	}

	@RequestMapping(value = "/procurement/itemMasterFilter/getItemMasterOptimalStockFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getItemMasterOptimalStockFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (ItemMaster itemMaster : itemMasterService.findAll())
		{	
			int optStock = itemMaster.getImOptimal_Stock();
			String optStockVal = String.valueOf(optStock);
			result.add(optStockVal);	 	    	
		}
		return result;
	}

	@RequestMapping(value = "/procurement/itemMasterFilter/getItemMasterPurchaseUOMFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getItemMasterPurchaseUOMFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (ItemMaster itemMaster : itemMasterService.findAll())
		{	
			result.add(itemMaster.getImPurchaseUom());		    	
		}
		return result;
	}

	@RequestMapping(value = "/procurement/itemMasterFilter/getItemMasterUomIssueFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getItemMasterUomIssueFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (ItemMaster itemMaster : itemMasterService.findAll())
		{	
			result.add(itemMaster.getImUomIssue());		    	
		}
		return result;
	}	
	/************ END FOR ITEM_MASTER URL'S FILTER  ******/

	/******************************************************<-- END FOR ITEM MASTER USE CASE -->***********************************************************************/


	/*****************************************************<-- FOR UNIT_OF_MEASUREMENT USE CASE -->********************************************************/
	/************  For sending the contents to the jsp  ****************/
	@SuppressWarnings("serial")
	@RequestMapping(value = "/procurementUom/uom/readUomBasedOnItemId/{imid}", method = RequestMethod.GET)
	public @ResponseBody List<?> read(@PathVariable int imid) 
	{	
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
		for (final UnitOfMeasurement record : uomService.findUomBasedOnItemId(imid)) {
			result.add(new HashMap<String, Object>() 
					{{
						put("imId", record.getItemMaster().getImId());            	
						put("imName", record.getItemMaster().getImName());
						put("uomId", record.getUomId());
						put("uom", record.getUom());
						put("code", record.getCode());
						put("baseUom", record.getBaseUom());
						put("uomConversion", record.getUomConversion());
						put("status", record.getStatus());
					}});
		}
		logger.info("From UOM controler");
		return result;	
	}
	/***********   END ********************************/

	@RequestMapping(value = "/procurementUom/uom/createUom/{id}", method = RequestMethod.POST)
	public @ResponseBody Object createUomDetails(@RequestBody Map<String, Object> map, @ModelAttribute UnitOfMeasurement uom, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale,@PathVariable int id) 
	{			
		logger.info("Inside Create()");
		uom = ((UomService) uomService).getBeanObject(map, "save", uom,id);
		validator.validate(uom, bindingResult);
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors())
		{
			BfmLogger.logger.info("Error method() in UOM Create Controller");
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			logger.info("Binding Result:"+bindingResult);
			return errorResponse;
		}
		else
		{				
			BfmLogger.logger.info("Inside Else Part of Save()");
			uomService.save(uom);	
			logger.info("SAVED IN CONTROLLER");
			return uom;
		}
	}

	/************* FOR UPDATING ****************************/
	@RequestMapping(value = "/procurementUom/uom/updateUom/{id}", method = RequestMethod.POST)
	public @ResponseBody Object updateUomDetails(@RequestBody Map<String, Object> map, @ModelAttribute UnitOfMeasurement uom, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale,@PathVariable int id) 
	{			
		logger.info("Inside Update()");
		uom = ((UomService) uomService).getBeanObject(map, "update", uom,id);
		validator.validate(uom, bindingResult);
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors())
		{
			BfmLogger.logger.info("Error in UOM Update Controller");
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			logger.info("Binding Result:"+bindingResult);
			return errorResponse;
		}
		else
		{				
			logger.info("Inside Else Part of Update()");
			uomService.update(uom);	
			logger.info("Updated IN Controller");
			return uom;
		}
	}
	/************ UPDATE END ****************************/

	/**********  Destroy Method **********************/
	@SuppressWarnings("serial")
	@RequestMapping(value = "/procurementUom/uom/destroyUom", method = RequestMethod.POST)
	public @ResponseBody Object destroy(@RequestBody Map<String,Object> map,UnitOfMeasurement uom) throws IOException 
	{	
		final Locale locale = null;
		JsonResponse json = new JsonResponse();
		int id = (Integer) map.get("uomId");
		String baseVal = uomService.getBaseUomBasedOnId(id);
		if(baseVal.equals("Yes"))
		{
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("deleteBaseUomError", messageSource.getMessage(
							"Uom.DeleteError", null, locale));
				}
			};
			json.setStatus("ERROR");
			json.setResult(errorMapResponse);
			return json;
		}
		else
		{
			uomService.delete(id);
			logger.info("deleted from UOM controller");
		}
		return uom;			
	}	
	/********** END FOR DESTROY ***********/

	/***************** @throws IOException ***********************/		
	@RequestMapping(value = "/procurementUom/uomDetails/updateUomStatus/{uomId}/{statusVal}", method = { RequestMethod.GET, RequestMethod.POST })
	public void updateVendorStatus(@PathVariable int uomId,@PathVariable String statusVal, HttpServletResponse response) throws IOException
	{
		List<UnitOfMeasurement> uom=uomService.findAll();
		Iterator<UnitOfMeasurement> it=uom.iterator();
		PrintWriter out = response.getWriter();
		while(it.hasNext())
		{
			UnitOfMeasurement uomObj=it.next();				
			if(uomObj.getUomId()==uomId)
			{
				uomService.updateUomStatus(uomId, statusVal);
				logger.info("UPADTED  THE DB");
			}
		}
		out.write("Status Updated Successfully");
	}
	//*** END FOR UPDATING THE STATUS FOR VENDORS  ***//


	@RequestMapping(value = "/procurementUom/uom/getUom/{itemId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getAllUoms(@PathVariable int itemId) {
		return 	uomService.getUom(itemId);
	}

	@RequestMapping(value = "/procurementUom/uom/getCode/{itemId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getAllCode(@PathVariable int itemId) {
		return 	uomService.getCode(itemId);
	}
	/****************************************************<-- END FOR UNIT_OF_MEASUREMENT USE CASE -->*****************************************************/


	/*****************************************************<--  FOR VENDOR_PRICELIST USE CASE  -->*********************************************************/
	@RequestMapping(value = "/vendorPriceLists")
	public String indexVendorPriceList(ModelMap model,HttpServletRequest request) 
	{
		model.addAttribute("ViewName", "Procurement");
		breadCrumbService.addNode("nodeID", 0,request);
		breadCrumbService.addNode("Procurement", 1,request);
		breadCrumbService.addNode("Manage Vendor PriceList", 2, request);
		return "procurement/vendorPriceList";
	}

	//For Reading Contenets To Jsp
	@RequestMapping(value = "/procurement/vendorPriceList/read", method = RequestMethod.POST)
	public @ResponseBody List<?> readVendorPriceList() 
	{	
		return vendorPricelistService.setResponse();
	}

	/***  For Reading Person Names  ***/
	@SuppressWarnings("serial")
	@RequestMapping(value = "/procurement/vendorPriceList/readPersons", method = RequestMethod.GET)
	public @ResponseBody List<?> readPersonNames() 
	{	
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
		for (final Vendors record : vendorService.findAll())
		{
			result.add(new HashMap<String, Object>() 
					{{         
						put("personId",record.getPersonId());
						put("vendorId",record.getVendorId());
						put("firstName",record.getPerson().getFirstName()+" "+record.getPerson().getLastName());
						put("personType",record.getPerson().getPersonType());
					}});
		}
		return result;
	}
	/***  END  ***/

	/***  For Reading Item Names  ***/
	@SuppressWarnings("serial")
	@RequestMapping(value = "/procurement/vendorPriceList/readItems", method = RequestMethod.GET)
	public @ResponseBody List<?> readItems() 
	{	
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
		for (final ItemMaster record : itemMasterService.findAll())
		{
			result.add(new HashMap<String, Object>() 
					{{  
						put("imId",record.getImId());
						put("imName",record.getImName());
						put("imNameType",record.getImName()+" "+record.getImType());
					}});
		}
		return result;
	}
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/procurement/vendorPriceList/getItemTypesUrl", method = RequestMethod.GET)
	public @ResponseBody Set<?> getItemTypesUrl() 
	{	
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();   
		List<ItemMaster> im=itemMasterService.findItemTypes();
		for (final Iterator<?> i = im.iterator(); i.hasNext();) {
		{
			result.add(new HashMap<String, Object>() 
					{{  
						final Object[] values = (Object[])i.next();

						put("imId",(Integer)values[1]);
						put("itemType",(String)values[0]);
						put("itemGroup",(String)values[2]);
						put("itemName",(String)values[3]);

					}});
		}
	 }
		return result;
		
	}
	
	
	/***  END  ***/

	/*************For Creating/Entering the new VendorPriceList details***************/
	@RequestMapping(value = "/procurement/vendorPriceList/create", method = RequestMethod.GET)
	public @ResponseBody Object createVendorPriceListUrl(Map<String, Object> map,@ModelAttribute("vendorPriceList")VendorPriceList vendorPriceList, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException 
	{
		String validFrom = req.getParameter("validFrom");
		String validTo = req.getParameter("validTo");

		vendorPriceList.setValidFrom(dateTimeCalender.getDateToStore(validFrom));
		vendorPriceList.setValidTo(dateTimeCalender.getDateToStore(validTo));

		String itemId = req.getParameter("imName");
		vendorPriceList.setImId(Integer.parseInt(itemId));
		vendorPricelistService.save(vendorPriceList);
		return vendorPricelistService.setResponse();
	}
	/****************** End *****************/

	/***********For Updating the new ItemMaster details******************/
	@RequestMapping(value = "/procurement/vendorPriceList/update", method = RequestMethod.GET)
	public @ResponseBody Object updateVendorPriceListUrl(Map<String, Object> map,@ModelAttribute("VendorPriceList")VendorPriceList vendorPriceList, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException 
	{
		String validFrom = req.getParameter("validFrom");
		String validTo = req.getParameter("validTo");

		vendorPriceList.setValidFrom(dateTimeCalender.getDateToStore(validFrom));
		vendorPriceList.setValidTo(dateTimeCalender.getDateToStore(validTo));

		vendorPricelistService.update(vendorPriceList);
		return vendorPricelistService.setResponse();
	}
	/********* END OF UPDATE ************/	

	@RequestMapping(value = "/procurement/vendorPriceList/destroy", method = RequestMethod.POST)
	public @ResponseBody VendorPriceList destroy(@RequestBody Map<String, Object> model,VendorPriceList vendorPriceList) 
	{	
		int id = (Integer) model.get("vpId");		
		vendorPricelistService.delete(id);
		BfmLogger.logger.info("DELETED FROM VendorPriceList CONTROLLER");
		return vendorPriceList;		
	}	
	/********  End *********/

	/******************** @throws IOException FOR REQUISITION ACTIVATING AND DE-ACTIVATING**************************************************/
	@SuppressWarnings("unused")
	@RequestMapping(value = "/procurement/vendorPriceList/vendorPriceListStatus/{vpId}/{operation}", method = RequestMethod.POST)
	public String roleStatusVendorPriceList(@PathVariable int vpId,@PathVariable String operation,ModelMap model, HttpServletRequest request,HttpServletResponse response, final Locale locale) throws IOException
	{		
		vendorPricelistService.setVendorPriceListStatus(vpId, operation, response, messageSource, locale);
		VendorPriceList requisition=vendorPricelistService.find(vpId);
		PrintWriter out;
		if(operation.equalsIgnoreCase("activate")){}
		else{}
		return null;
	}
	/**************************EENNDDFOR REQUISITION ACTIVATING AND DE-ACTIVATING*************************/


	/**************************  VENDOR PRICELIST FILTER'S  ****************************/
	@RequestMapping(value = "/procurement/vendorPriceList/getVendorNameForPriceListFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getVendorNameForPriceListFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (VendorPriceList priceList : vendorPricelistService.findAll())
		{	
			result.add(priceList.getVendors().getPerson().getFirstName());		    	
		}
		return result;
	}

	@RequestMapping(value = "/procurement/vendorPriceList/getItemTypeForPriceListFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getItemTypeForPriceListFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (VendorPriceList priceList : vendorPricelistService.findAll())
		{	
			result.add(priceList.getItemType());		    	
		}
		return result;
	}

	@RequestMapping(value = "/procurement/vendorPriceList/getItemNameForPriceListFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getItemNameForPriceListFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (VendorPriceList priceList : vendorPricelistService.findAll())
		{	
			result.add(priceList.getItemMaster().getImName());		    	
		}
		return result;
	}

	@RequestMapping(value = "/procurement/vendorPriceList/getUOMForPriceListFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getUOMForPriceListFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (VendorPriceList priceList : vendorPricelistService.findAll())
		{	
			result.add(priceList.getUnitOfMeasurement().getUom());		    	
		}
		return result;
	}

	@RequestMapping(value = "/procurement/vendorPriceList/getRateForPriceListFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getRateForPriceListFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (VendorPriceList priceList : vendorPricelistService.findAll())
		{	
			long rt = priceList.getRate();
			String rate = String.valueOf(rt);
			result.add(rate);		    	
		}
		return result;
	}

	@RequestMapping(value = "/procurement/vendorPriceList/getDeliveryAtPriceListFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getDeliveryAtPriceListFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (VendorPriceList priceList : vendorPricelistService.findAll())
		{				
			result.add(priceList.getDeliveryAt());		    	
		}
		return result;
	}

	@RequestMapping(value = "/procurement/vendorPriceList/getPaymentTermsPriceListFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getPaymentTermsPriceListFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (VendorPriceList priceList : vendorPricelistService.findAll())
		{				
			result.add(priceList.getPaymentTerms());		    	
		}
		return result;
	}

	@RequestMapping(value = "/procurement/vendorPriceList/getInvoicePayableDaysPriceListFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getInvoicePayableDaysPriceListFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (VendorPriceList priceList : vendorPricelistService.findAll())
		{				
			int days = priceList.getInvoice_Payable_days();
			String invPayDays = String.valueOf(days);
			result.add(invPayDays);		    	
		}
		return result;
	}

	@RequestMapping(value = "/procurement/vendorPriceList/getStatusPriceListFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getStatusPriceListFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (VendorPriceList priceList : vendorPricelistService.findAll())
		{	
			result.add(priceList.getStatus());		    	
		}
		return result;
	}

	@RequestMapping(value="/procurement/reqDetails/readDesignationBasedOnDepartment/{deptId}")
	public @ResponseBody List<?> getDesignation(@PathVariable int deptId,HttpServletResponse response) throws IOException
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<?> id = requisitionDetailsService.getDesignation(deptId);
		Map<String, Object> deptMap = null;
		for (Iterator<?> iterator = id.iterator(); iterator.hasNext();)
		{
			final Object[] values = (Object[]) iterator.next();			
			deptMap = new HashMap<String, Object>();
			deptMap.put("dn_Id", (Integer)values[0]);
			deptMap.put("dn_Name", (String)values[1]);
			result.add(deptMap);
		}
		return result;
	}
	/******************************************************<--  END FOR VENDOR_PRICELIST USE CASE -->********************************************************/

	@SuppressWarnings("serial")
	@RequestMapping(value = "/stores/readStoresDetails", method = RequestMethod.GET)
	public @ResponseBody List<?> getStoreNamesAutoUrl() 
	{
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(final StoreMaster record : storeMasterService.getAllStoresRequiredFields())
		{
			String status = record.getStoreStatus();				
			if(status.equalsIgnoreCase("Active"))
			{
				result.add(new HashMap<String,Object>()
						{{
							put("storeId",record.getStoreId());
							put("storeName",record.getStoreName());
							put("storeLocation",record.getStoreLocation());
						}});
			}
		}
		return result;
	} 

	public void raiseNewRequisition(int storeId,int itemId,int uomId)
	{

	}

	/*@RequestMapping(value = "/itemMaster/readCreditedDataForGraph", method = RequestMethod.POST)
	public @ResponseBody List<?> getType(HttpServletResponse response) throws IOException
	{
		List<GraphModelView> result = new ArrayList<GraphModelView>();
		for (final Object[] values :  itemMasterService.getGraph("Credit"))
		{
				GraphModelView GraphModelView = new GraphModelView();
				String monthVal = (String) values[2];
				if(monthVal.equalsIgnoreCase("01"))
				{
					GraphModelView.setMonthValue("Jan");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For January : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("02"))
				{
					GraphModelView.setMonthValue("Feb");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For February : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("03"))
				{
					GraphModelView.setMonthValue("Mar");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For March : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("04"))
				{
					GraphModelView.setMonthValue("Apr");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For April : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("05"))
				{
					GraphModelView.setMonthValue("May");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For May : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("06"))
				{
					GraphModelView.setMonthValue("Jun");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For June : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("07"))
				{
					GraphModelView.setMonthValue("Jul");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For July : "+quantity);
				}

				if(monthVal.equalsIgnoreCase("08"))
				{
					GraphModelView.setMonthValue("Aug");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For August : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("09"))
				{
					GraphModelView.setMonthValue("Sept");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For September : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("10"))
				{
					GraphModelView.setMonthValue("Oct");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For October : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("11"))
				{
					GraphModelView.setMonthValue("Nov");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(Math.abs(quantity)));
					logger.info("Credit For November : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("12"))
				{
					GraphModelView.setMonthValue("Dec");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For December : "+quantity);
				}	
				result.add(GraphModelView);
		 }
		return result;
    }*/


	/*@RequestMapping(value = "/itemMaster/readDebittedDataForGraph", method = RequestMethod.POST)
	public @ResponseBody List<?> getDebitData(HttpServletResponse response) throws IOException
	{
		List<GraphModelView> result = new ArrayList<GraphModelView>();
		for (final Object[] values :  itemMasterService.getGraph("Debit"))
		{
				GraphModelView GraphModelView = new GraphModelView();
				String monthVal = (String) values[2];
				if(monthVal.equalsIgnoreCase("01"))
				{
					GraphModelView.setMonthValue("Jan");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For January : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("02"))
				{
					GraphModelView.setMonthValue("Feb");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For February : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("03"))
				{
					GraphModelView.setMonthValue("Mar");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For March : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("04"))
				{
					GraphModelView.setMonthValue("Apr");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For April : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("05"))
				{
					GraphModelView.setMonthValue("May");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For May : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("06"))
				{
					GraphModelView.setMonthValue("Jun");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For June : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("07"))
				{
					GraphModelView.setMonthValue("Jul");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For July : "+quantity);
				}

				if(monthVal.equalsIgnoreCase("08"))
				{
					GraphModelView.setMonthValue("Aug");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For August : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("09"))
				{
					GraphModelView.setMonthValue("Sept");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For September : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("10"))
				{
					GraphModelView.setMonthValue("Oct");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For October : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("11"))
				{
					GraphModelView.setMonthValue("Nov");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(Math.abs(quantity)));
					logger.info("Debit For November : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("12"))
				{
					GraphModelView.setMonthValue("Dec");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For December : "+quantity);
				}	
				result.add(GraphModelView);
		 }
		return result;
    }*/

	@RequestMapping(value = "/itemMaster/readCreditedItemsForGraph/{itemId}", method = RequestMethod.POST)
	public @ResponseBody List<?> readCreditedItemsForGraph(@PathVariable int itemId,HttpServletResponse response) throws IOException
	{
		List<GraphModelView> result = null;
		List<StoreItemLedger> storeList =  storeItemLedgerService.getStoreItemLedgerList(itemId);
		
		logger.info("----------------\nSIZE OF LIST"+storeList.size());
		int ledgerMasterId = 0;
		for (StoreItemLedger storeItemLedger : storeList) {
			ledgerMasterId = storeItemLedger.getSilId();
			logger.info("=================\n\n"+ledgerMasterId);

			result = new ArrayList<GraphModelView>();
			for (final Object[] values :  itemMasterService.getGraph1(ledgerMasterId,"Credit"))
			{
				GraphModelView GraphModelView = new GraphModelView();
				String monthVal = (String) values[2];
				logger.info("*************\n\n"+monthVal);
				if(monthVal.equalsIgnoreCase("01"))
				{
					GraphModelView.setMonthValue("Jan");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For January : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("02"))
				{
					GraphModelView.setMonthValue("Feb");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For February : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("03"))
				{
					GraphModelView.setMonthValue("Mar");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For March : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("04"))
				{
					GraphModelView.setMonthValue("Apr");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For April : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("05"))
				{
					GraphModelView.setMonthValue("May");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For May : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("06"))
				{
					GraphModelView.setMonthValue("Jun");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For June : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("07"))
				{
					GraphModelView.setMonthValue("Jul");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For July : "+quantity);
				}

				if(monthVal.equalsIgnoreCase("08"))
				{
					GraphModelView.setMonthValue("Aug");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For August : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("09"))
				{
					GraphModelView.setMonthValue("Sept");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For September : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("10"))
				{
					GraphModelView.setMonthValue("Oct");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For October : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("11"))
				{
					GraphModelView.setMonthValue("Nov");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(Math.abs(quantity)));
					logger.info("Credit For November : "+quantity);
				}
				if(monthVal.equalsIgnoreCase("12"))
				{
					GraphModelView.setMonthValue("Dec");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Credit For December : "+quantity);
				}	
				result.add(GraphModelView);
			}
		}
		return result;		
	}

	//READING DEBITTED DATA FOR GRAPH
	@RequestMapping(value = "/itemMaster/readDebittedItemsForGraph/{itemId}", method = RequestMethod.POST)
	public @ResponseBody List<?> readDebittedItemsForGraph(@PathVariable int itemId,HttpServletResponse response) throws IOException
	{
		List<GraphModelView> result = null;
		String query = "select s from StoreItemLedger s where s.imId = "+itemId+"";
		List<StoreItemLedger> storeList =  storeItemLedgerService.executeSimpleQuery(query);
		logger.info("----------------\nSIZE OF LIST"+storeList.size());
		int ledgerMasterId = 0;
		for (StoreItemLedger storeItemLedger : storeList) {
			ledgerMasterId = storeItemLedger.getSilId();
			logger.info("=================\n\n"+ledgerMasterId);

			result = new ArrayList<GraphModelView>();
			for (final Object[] values :  itemMasterService.getGraph1(ledgerMasterId,"Debit"))
			{
				GraphModelView GraphModelView = new GraphModelView();
				String monthVal = (String) values[2];
				logger.info("Month Val=================\n\n"+monthVal);
				if("01".equalsIgnoreCase(monthVal))
				{
					GraphModelView.setMonthValue("Jan");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For January : "+quantity);
				}
				if("02".equalsIgnoreCase(monthVal))
				{
					GraphModelView.setMonthValue("Feb");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For February : "+quantity);
				}
				if("03".equalsIgnoreCase(monthVal))
				{
					GraphModelView.setMonthValue("Mar");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For March : "+quantity);
				}
				if("04".equalsIgnoreCase(monthVal))
				{
					GraphModelView.setMonthValue("Apr");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For April : "+quantity);
				}
				if("05".equalsIgnoreCase(monthVal))
				{
					GraphModelView.setMonthValue("May");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For May : "+quantity);
				}
				if("06".equalsIgnoreCase(monthVal))
				{
					GraphModelView.setMonthValue("Jun");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For June : "+quantity);
				}
				if("07".equalsIgnoreCase(monthVal))
				{
					GraphModelView.setMonthValue("Jul");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For July : "+quantity);
				}

				if("08".equalsIgnoreCase(monthVal))
				{
					GraphModelView.setMonthValue("Aug");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For August : "+quantity);
				}
				if("09".equalsIgnoreCase(monthVal))
				{
					GraphModelView.setMonthValue("Sept");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For September : "+quantity);
				}
				if("10".equalsIgnoreCase(monthVal))
				{
					GraphModelView.setMonthValue("Oct");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For October : "+quantity);
				}
				if("11".equalsIgnoreCase(monthVal))
				{
					GraphModelView.setMonthValue("Nov");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(Math.abs(quantity)));
					logger.info("Debit For November : "+quantity);
				}
				if("12".equalsIgnoreCase(monthVal))
				{
					GraphModelView.setMonthValue("Dec");
					double quanVal1 = (double) values[1];
					int quantity = (int)quanVal1;
					GraphModelView.setGraphIndex(Math.abs(quantity));
					logger.info("Debit For December : "+quantity);
				}	
				result.add(GraphModelView);
			}
		}
		return result;		
	}
}
