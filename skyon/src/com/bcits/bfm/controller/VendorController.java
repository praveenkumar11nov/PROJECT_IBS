package com.bcits.bfm.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.DrGroupId;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Requisition;
import com.bcits.bfm.model.StoreGoodsReceipt;
import com.bcits.bfm.model.VendorContractLineitems;
import com.bcits.bfm.model.VendorContracts;
import com.bcits.bfm.model.VendorIncidents;
import com.bcits.bfm.model.VendorInvoices;
import com.bcits.bfm.model.VendorLineItems;
import com.bcits.bfm.model.VendorPayments;
import com.bcits.bfm.model.VendorRequests;
import com.bcits.bfm.model.Vendors;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.DrGroupIdService;
import com.bcits.bfm.service.VendorsManagement.RequisitionDetailsService;
import com.bcits.bfm.service.VendorsManagement.RequisitionService;
import com.bcits.bfm.service.VendorsManagement.VendorContractsLineItemsService;
import com.bcits.bfm.service.VendorsManagement.VendorContractsService;
import com.bcits.bfm.service.VendorsManagement.VendorIncidentService;
import com.bcits.bfm.service.VendorsManagement.VendorInvoicesService;
import com.bcits.bfm.service.VendorsManagement.VendorLineItemsService;
import com.bcits.bfm.service.VendorsManagement.VendorPaymentsService;
import com.bcits.bfm.service.VendorsManagement.VendorRequestService;
import com.bcits.bfm.service.VendorsManagement.VendorsService;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.inventoryManagement.StoreGoodsReceiptItemsService;
import com.bcits.bfm.service.inventoryManagement.StoreGoodsReceiptService;
import com.bcits.bfm.service.inventoryManagement.StoreMasterService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.EmailCredentialsDetailsBean;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SmsCredentialsDetailsBean;
import com.bcits.bfm.util.VendorContractRenewalMail;
import com.bcits.bfm.util.ValidationUtil;
import com.bcits.bfm.util.VendorIncidentMail;
import com.bcits.bfm.util.VendorRequestEmail;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
public class VendorController 
{
	static Logger logger = LoggerFactory.getLogger(VendorController.class);
		
	@Autowired
	private Validator validator;
	
	@Autowired
	private DrGroupIdService drGroupIdService;
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private VendorContractsService vendorContractsService;
	
	@Autowired
	private VendorContractsLineItemsService vcLineItemService;
	
	@Autowired
	private VendorPaymentsService vendorPaymentsService;
	
	@Autowired
	private VendorInvoicesService vendorInvoiceService;
	
	@Autowired
	private VendorIncidentService vendorIncidentService;
	
	@Autowired
	private StoreMasterService storeMasterService;
	
	@Autowired
	private RequisitionService requisitionService;
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private VendorsService vendorsService;
	
	@Autowired
	private VendorLineItemsService vendorLineItemService;
	
	@Autowired
	private VendorRequestService vendorRequestService; 
	
	@Resource
	private ValidationUtil validationUtil;
	
	@Autowired
	private JsonResponse errorResponse;
	
	@Autowired
	private RequisitionDetailsService requisitionDetailsService;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private StoreGoodsReceiptItemsService goodsReceiptItemsService;
	
	@Autowired
	private StoreGoodsReceiptService goodsReceiptService; 
	
	@Autowired
	private CommonService commonService;
	
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	
/************************************************************** [< FOR VENDOR CONTROLLERS >] *********************************************************************************/
	@RequestMapping("/vendors")
   	public String vendorIndex(HttpServletRequest request,HttpServletResponse response,ModelMap model,final Locale locale)
   	{
		logger.info("REDING INDEX PADE FOR VENDOR FROM VENDOR-CONTROLLER");
   		model.addAttribute("ViewName", "Vendors");
   		
   		model.addAttribute("personStyle", commonController.populateCategories("personStyle", locale));
		model.addAttribute("title", commonController.populateCategories("title", locale));
		model.addAttribute("sex", commonController.populateCategories("sex", locale));
		model.addAttribute("bloodGroup", commonController.populateCategories("bloodGroup", locale));
		model.addAttribute("nationality", commonController.populateCategories("nationality", locale));
		model.addAttribute("workNature", commonController.populateCategories("workNature", locale));		
		model.addAttribute("meCategory", commonController.populateCategories("meCategory", locale));
		
   		breadCrumbService.addNode("nodeID", 0, request);
   		breadCrumbService.addNode("Vendors", 1, request);
   		breadCrumbService.addNode("Manage Vendors", 2, request);
   		return "vendorManagement/vendors";
   	}
	
	//*** FOR READING PERSON STYLES  ***//
	@RequestMapping(value = "/person/getPersonStyleChecks", method = RequestMethod.GET)
    public @ResponseBody Set<?> getPersonStyleList() {
	  Set<String> result = new HashSet<String>();
	    for (String personStyles : personService.getPersonStyle())
		{
			result.add(personStyles);
		}
        return result;
    }
	//***  END FOR READING PERSON STYLES  ****//	
	
	
	@SuppressWarnings("serial")
	//***  FOR READING VENDOR DETAILS CHILD RECORD TO VENDOR PAGE  ***//
	@RequestMapping(value = "/vendorDetail/vendorDetailsReadUrl/{personId}", method = { RequestMethod.GET, RequestMethod.POST } )
 	public @ResponseBody List<?> readVendorDetails(@PathVariable int personId) 
 	{
	    List<Map<String, Object>> vendors =  new ArrayList<Map<String, Object>>(); 		
	    for (final Vendors record : (List<Vendors>) vendorsService.findAllVendorBasedOnPersonID(personId) ) 
        {	
			 vendors.add(new HashMap<String, Object>() 
		     {{
				 put("vendorId" ,record.getVendorId());
				 put("personId" ,record.getPersonId());
				 put("panNo" ,record.getPanNo());
				 put("cstNo" ,record.getCstNo() );
				 put("stateTaxNo",record.getStateTaxNo());
				 put("serviceTaxNo" , record.getServiceTaxNo());
				 put("status" , record.getStatus());
		     }});
        }
		return vendors;
 	}	
	//***   END FOR REDING VENDOR DETAILS CHILD READING  ***//

	//***  FOR UPDATING VENDOR DETAILS ***//
	@RequestMapping(value = "/vendorDetail/vendorDetailsUpdateUrl/update/{personId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> updateVendorDetails(@ModelAttribute("vendors")Vendors vendors, BindingResult result,
			ModelMap model, SessionStatus sessionStatus, final Locale locale, @PathVariable int personId, HttpServletRequest req) throws ParseException
	{
		HttpSession session = req.getSession(false);
		String userName = (String)session.getAttribute("userId");
		Person person = personService.find(personId);
		vendors.setPerson(person);
		vendors.setPanNo(req.getParameter("panNo"));
		vendors.setCstNo(req.getParameter("cstNo"));
		vendors.setStateTaxNo(req.getParameter("stateTaxNo"));
		vendors.setServiceTaxNo(req.getParameter("serviceTaxNo"));
		vendors.setStatus(req.getParameter("status"));
		vendors.setCreatedBy(person.getCreatedBy());
		vendors.setLastUpdatedBy(userName);
		vendors.setLastUpdatedDt(new Timestamp(new Date().getTime()));
		person.setVendors(vendors);
		
		personService.update(person);	
		return vendorsService.getVendorResponse(personId);
	}
	//*** END FOR UPDATING VENDOR DETAILS ***//
	
	//*** FOR UPADATING THE STATUS FOR VENDORS ***//
	/***************** @throws IOException ***********************/		
	@RequestMapping(value = "/vendorDetails/updateVendorStatus/{vendorId}/{statusVal}", method = { RequestMethod.GET, RequestMethod.POST })
	public void updateVendorStatus(@PathVariable int vendorId,@PathVariable String statusVal, HttpServletResponse response) throws IOException
	{
		List<Vendors> vendor=vendorsService.findAll();
		Iterator<Vendors> it=vendor.iterator();
		PrintWriter out = response.getWriter();
		while(it.hasNext())
		{
			Vendors vendorObj=it.next();				
			if(vendorObj.getVendorId()==vendorId)
			{
				vendorsService.updateVendorStatus(vendorId, statusVal);
				logger.info("UPADTED  THE DB");
			}
		}
		out.write("Status Updated Successfully");
	}
	//*** END FOR UPDATING THE STATUS FOR VENDORS  ***//
	
	
/*********************************************************************  END FOR VENDOR CONTROLLERS  *********************************************************************/
	
	 
/**************************************************************** [< FOR VENDOR CONTRACTS CONTROLLER >] *****************************************************************/ 
		//***  FOR READING INDEX PAGE ***//
		@RequestMapping(value = "/vendorContracts")
		public String indexvendorContracts(ModelMap model,HttpServletRequest request) 
		{
			logger.info("VENDOR CONTRACTS Index Page From Controller");
			model.addAttribute("ViewName", "Procurement");
			breadCrumbService.addNode("nodeID", 0,request);
			breadCrumbService.addNode("Procurement", 1,request);
			breadCrumbService.addNode("Manage Vendor &nbsp;Contracts", 2, request);
			model.addAttribute("stores", commonController.populateCategories("storeId", "storeName", "StoreMaster"));
			return "vendorManagement/vendorContracts";
		}
		//***  END FOR READING INDEX PAGE  ***//
		
		//***     FOR READING THE CONTENTS TO JSP ***//
		 @RequestMapping(value = "/vendorContracts/read", method = RequestMethod.POST)
		 public @ResponseBody List<?> readAllVendorContracts()
		 {
			 return vendorContractsService.getAllRequiredVendorContractsRecord();
		 	//return vendorContractsService.setResponse();
		 } 
		 //***   END OF READING CONTENTS ***//
		 
		 //***  FOR CHECKING APPROVED REQUISITIONS AVAILBLE IN VENDOR_CONTRACTS  ***//
		 @RequestMapping(value="/requisition/checkApprovedRequisitionsAvailability",method=RequestMethod.POST)
		 public @ResponseBody int checkForApprovedRequisitions()
		 { 
			List<?> list = vendorContractsService.checkForApprovedRequisitions();
			int sizeOfApprovedRequisition = list.size(); 
			logger.info("Size of Approved Requisition List"+list.size());
			return sizeOfApprovedRequisition;
		 }
		 //***  END FOR CHECKING APPROVED REQUISTIONS  ***//
		 
		 /****  FOR READING REQUISITION TYPE BASED ON REQUISITION_ID  
		 * @return ****/
		 @SuppressWarnings("unused")
		@RequestMapping(value="/requisition/readRequisitionTypeBasedOnReqId/{reqId}",method = RequestMethod.POST)
		 public @ResponseBody List<?> getRequisitionType(@PathVariable int reqId,HttpServletResponse response) throws IOException
		 {
			 int uomBudget = 0;
			 int reqIdFromChild = requisitionService.getReqIdFromChild(reqId);
			 logger.info("IN CHILD REQ_ID IS ----> "+reqIdFromChild);
			 int budget = 0; 
			 if(reqId == reqIdFromChild)
			 {
				logger.info("INSIDE PARENT ID EXISTS IN CHILD -- > \n"+reqId+"=="+reqIdFromChild);
				uomBudget = vendorContractsService.readBudgetUom(reqIdFromChild).intValue();
			 }
			 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			 List<?> reqList = requisitionService.getReqTypeBasedOnReqId(reqId);		
			 Map<String, Object> reqMap = null;
			 for (Iterator<?> iterator = reqList.iterator(); iterator.hasNext();)
			 {
				final Object[] values = (Object[]) iterator.next();			
				reqMap = new HashMap<String, Object>();
				reqMap.put("reqType", (String)values[0]);
				reqMap.put("uomBudget", uomBudget);
				//reqMap.put("reqDate", (java.sql.Date) values[1]);
		 	  	result.add(reqMap);
		 	 }
		     return result;
		 }
		 /**** END FOR READING REQUISITION TYPE BASED ON REQ_ID ****/
		 
		 //***  FOR READING LIST OF REQUISITION DETAILS TO DROPDOWN ***//
		 @SuppressWarnings({ "unchecked", "rawtypes" })
		 @RequestMapping(value = "/requisition/readRequistionDetails", method = RequestMethod.GET)
		 public @ResponseBody List<?> readRequistionDetails() 
		 {
			List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = null;
			
			List<Requisition> requisitionList = requisitionService.getRequisitionDetails();
			List uniqueReqNames = new ArrayList(new HashSet(requisitionList)); 
			for (Iterator<Requisition> iterator = uniqueReqNames.iterator(); iterator.hasNext();) 
			{
				Requisition requisition = (Requisition) iterator.next();				
				map = new HashMap<String,Object>();
				String reqStatus = requisition.getStatus();
				logger.info("Requisition Status"+reqStatus);
				if(reqStatus.equalsIgnoreCase("Approved"))
				{
					map.put("vendorId",requisition.getVendorId());
					map.put("reqId",requisition.getReqId());
					map.put("reqName",requisition.getReqName());
					map.put("reqDescription",requisition.getReqDescription());	
					map.put("reqDate", requisition.getReqDate());
					result.add(map);
				}
				else
				{
					logger.info("Requisition NOT APPROVED");
				}
			}			
			return result;
		  }
		 //*** END FOR READING REQUISITION DETAILS TO DROPDOWN  ***//
		 
		 //****  FOR READING VENDORS FOR VENDORCONTRACTS BASED ON REQUISITION  ***//
		 @SuppressWarnings("serial")
		 @RequestMapping(value = "/vendorContracts/getVendorNamesForVendorcontracts", method = RequestMethod.GET)
		 public @ResponseBody List<?> getVendorNamesForVendorcontracts() 
		 {
			 List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
			 for(final Requisition record : requisitionService.findAll())
			 {
			 	result.add(new HashMap<String,Object>()
			    {{
			    	put("personId",record.getPerson().getPersonId());
			    	put("vendorName",record.getPerson().getFirstName()+" "+record.getPerson().getLastName());
			    	put("personType",record.getPerson().getPersonType());
			    	put("vendorId",record.getVendorId());
			    	put("reqId",record.getReqId());
				}});
			 }
			 return result;
		  } 
		 //**** END FOR READING VENDORS FOR VENDORCONTRACTS BASED ON REQUISITION  ***//
		 
		 
		 //***  FOR READING STORE MASTER DEATILS TO DROPDOWN ***//
		 @SuppressWarnings("serial")
		 @RequestMapping(value = "/storeMaster/readStoreMasterDetails", method = RequestMethod.GET)
		 public @ResponseBody List<?> readStoreMasterDetails() 
		 {
			List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
			for(final Requisition record : requisitionService.findAll())
			{
				//String status = record.getStoreStatus();				
				/*if(status.equalsIgnoreCase("Active"))
				{*/
					result.add(new HashMap<String,Object>()
					{{
						 put("storeId",record.getStoreId());
						 put("storeName",record.getStoreMaster().getStoreName());
						 put("storeLocation",record.getStoreMaster().getStoreLocation());
						 put("reqId",record.getReqId());
					}});
				//}
			}
				return result;
		  }
		 
		 
		 //*** END FOR READING STORE MASTER DETAILS TO DROPDOWN  ***//
		 
	 
		//***  FOR CREATING THE VENDOR CONTRACTS RECORD * @throws InterruptedException *******/	
		@RequestMapping(value = "/vendorContracts/createVendorContracts", method = RequestMethod.GET)
		public @ResponseBody Object createvendorContracts(@ModelAttribute("vendors")VendorContracts vendorContracts, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale) throws InterruptedException, ParseException 
		{	
			//Inserting data into child
			vendorContracts.setVcLineitems(getVcLineItemset(vendorContracts));
			
			//Inserting data into parent
			String vcDesc = vendorContracts.getDescription();
			String vcDesc_trim = vcDesc.trim();
			vendorContracts.setDescription(vcDesc_trim);
			
			int storeId = vendorContracts.getStoreId();
			if(storeId == 0)
			{
				vendorContracts.setStoreId(1);
				vendorContractsService.save(vendorContracts);
			}
			else
			{
				vendorContractsService.save(vendorContracts);
			}			
			return vendorContractsService.setResponse();
		}
		
		////////// METHOD TO SAVE VENDOR CONTRACT LINE ITEMS RECORD//////////////////
		private Set<VendorContractLineitems> getVcLineItemset(VendorContracts vendorContracts) throws InterruptedException
		{				
			Set<VendorContractLineitems> set = new HashSet<VendorContractLineitems>();
			List<String> attributes = new ArrayList<String>();
			attributes.add("imId");
			attributes.add("rdQuantity");
			attributes.add("uomId");
			attributes.add("rdSlno");
			//attributes.add("reqType");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("requisitionId", vendorContracts.getReqId());	
			
			int id = (int) params.get("requisitionId");
			
			String reqType = vcLineItemService.getReqType(id);
			
			List<?> list = vcLineItemService.selectQuery("RequisitionDetails", attributes, params);
			int count = 1;
			for (Iterator<?> i = list.iterator(); i.hasNext();) 
			{
				final Object[] values = (Object[]) i.next();				
				VendorContractLineitems vendorContractLineitems = new VendorContractLineitems();
				vendorContractLineitems.setCreatedBy(vendorContracts.getCreatedBy());
				vendorContractLineitems.setImId((int)values[0]);
				vendorContractLineitems.setQuantity((int)values[1]);
				vendorContractLineitems.setUomId((int)values[2]);
				vendorContractLineitems.setVclSlno((int)values[3]);
				vendorContractLineitems.setVclSlno(count);
				count++;
				vendorContractLineitems.setReqType(reqType);
				vendorContractLineitems.setLastUpdatedBy(vendorContracts.getLastUpdatedBy());
				vendorContractLineitems.setLastUpdatedDt(new Timestamp(new Date().getTime()));
				set.add(vendorContractLineitems);
			}
			return set;
		}
		/////////////  END FOR SAVING VC_LINEITEMS  /////////////////
		
		
		
		
		//***  FOR CREATING THE VENDOR CONTRACTS RECORD  *******/	
		@RequestMapping(value = "/vendorContracts/updateVendorContracts", method = RequestMethod.GET)
		public @ResponseBody Object updatevendorContracts(@ModelAttribute("vendors")VendorContracts vendorContracts, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale) 
		{	
		    logger.info("Inside UPDATE Vendor Contracts Record");
			vendorContractsService.update(vendorContracts);
			return vendorContractsService.setResponse();
		}
		
		@SuppressWarnings("serial")
		@RequestMapping(value = "/vendorContracts/getVendorNamesAutoUrl", method = RequestMethod.GET)
		public @ResponseBody List<?> getVendorNamesAutoUrl() 
		{
			List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
			for(final Vendors record : vendorsService.findAll())
			{
				final int vendorid = record.getVendorId();
				final String status = record.getPerson().getPersonStatus();
				final String lname = record.getPerson().getLastName();		
				
				if(vendorid!=1)
				{
					if(status.equalsIgnoreCase("active"))
					{
						result.add(new HashMap<String,Object>()
					    {{
					    	if(lname==null)
					    	{
					    		put("vendorName",record.getPerson().getFirstName());
					    	}
					    	else
					    	{
					    		put("vendorName",record.getPerson().getFirstName()+" "+record.getPerson().getLastName());
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
		//***  END FOR CREATING THE VENDOR CONTRACTS RECORDS *****/
				
		//***  @throws IOException FOR VendorContracts ACTIVATING AND DE-ACTIVATING Status**************************************************/
		@RequestMapping(value = "/vendorContracts/vendorContractsStatus/{vcId}/{operation}", method = RequestMethod.GET)
		public @ResponseBody String RoleStatus(@PathVariable int vcId,@PathVariable String operation,ModelMap model, HttpServletRequest request,HttpServletResponse response, final Locale locale) throws IOException
		{		
			VendorContracts vendorContracts=vendorContractsService.find(vcId); //for getting REQUISITION ID
			PrintWriter out = response.getWriter();
			int reqId = vendorContracts.getReqId();
			
			Requisition requisitionId= requisitionService.find(reqId);
			String reqStatus = "";
			if(requisitionId.getReqId()==reqId)
			{
				reqStatus = requisitionId.getStatus();
				if(reqStatus.equalsIgnoreCase("Approved"))
				{
					vendorContractsService.setVendorContractStatus(vcId, operation, response, messageSource, locale);
					requisitionId.setStatus("PO Placed");
					requisitionService.update(requisitionId);
					@SuppressWarnings("unused")
					String vendorContractStatus = vendorContracts.getStatus();
					if(operation.equalsIgnoreCase("approved"))
					{
					    out.write("Vendor Contracts Approved");
					}
					else
					{
					    out.write("Vendor Contracts Rejected");
					}
				}
				else if(reqStatus.equalsIgnoreCase("Created"))
				{
					out.write("Requisition Not Approved ! Purchase Order Cannot Be Placed");
				}
				else if(reqStatus.equalsIgnoreCase("Rejected"))
				{
					out.write("Purchase Order Placed ! Cannot Be Rejected");					
				}
				
				else{
					
					out.write("Already PO Placed in Requisition");	
				}
			}
			else
			{
				out.write("Requisition details are mismatched");					

			}
			return null;
		}
		
		//*** FOR VENDOR FILTER IN VENDOR_CONTRACT ***//
		@RequestMapping(value = "/vcContract/getVendorNamesFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getVendorNamesFilter()
		{ 
		    Set<String> result = new HashSet<String>();
			for (VendorContracts vc : vendorContractsService.findAll())
			{	
			    result.add(vc.getVendors().getPerson().getFirstName()+" "+vc.getVendors().getPerson().getLastName());		    	
			}
		    return result;
		}
		//*** END FOR VENDOR FILTER IN VENDOR_CONTRACT ***//
		
		//*** FOR STORE NAME FILTER IN VENDOR_CONTRACT ***//
		@RequestMapping(value = "/vcContract/getStoreNamesFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getStoreNamesFilter()
		{ 
		    Set<String> result = new HashSet<String>();
			for (VendorContracts vc : vendorContractsService.findAll())
			{	
			    result.add(vc.getStoreMaster().getStoreName());		    	
			}
		    return result;
		}
		//*** END FOR STORE NAME FILTER IN VENDOR_CONTRACT ***//
		
		//***  FILTER FOR CAM CATEGORY FILTER  ****//
		@RequestMapping(value = "/vcContract/getCamCategoryFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getVendorNames()
		{ 
		    Set<String> result = new HashSet<String>();
			for (VendorContracts vc : vendorContractsService.findAll())
			{
				int camId = vc.getCamCategoryId();
				String camVal = String.valueOf(camId);
			    result.add(camVal);		    	
			}
		    return result;
		}
		//*** END FOR CAM CATEGORY FILTER ***//
		
		@SuppressWarnings("unused")
		//***  FILTER FOR CONTRACT TYPE  ****//
		@RequestMapping(value = "/vcContract/getContractType", method = RequestMethod.GET)
		public @ResponseBody Set<?> getContractType()
		{ 
		    Set<String> result = new HashSet<String>();
			for (VendorContracts vc : vendorContractsService.findAll())
			{	
			   // result.add(vc.getContractType());		    	
			}
		    return result;
		}
		//*** END FOR CONTRACT TYPE FILTER ***//
		
		//***  FILTER FOR CONTRACT TYPE  ****//
		@RequestMapping(value = "/vcContract/getDescription", method = RequestMethod.GET)
		public @ResponseBody Set<?> getDescription()
		{ 
		    Set<String> result = new HashSet<String>();
			for (VendorContracts vc : vendorContractsService.findAll())
			{	
			    result.add(vc.getDescription());		    	
			}
		    return result;
		}
		//*** END FOR CONTRACT TYPE FILTER ***//
		
		
		//***  FOR CONTRACT NAME FILTER  ***//
		@RequestMapping(value = "/vcContract/getContractNameFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getContractNameFilter()
		{ 
		    Set<String> result = new HashSet<String>();
			for (VendorContracts vc : vendorContractsService.findAll())
			{
			   result.add(vc.getContractName());		    	
			}
		    return result;
		}		
		//***  END FOR CNAME FILTER ***//
		
		//***  FOR CONTRACT NUMBER FILTER  ***//
	    @RequestMapping(value = "/vcContract/getContractNoFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getContractNumberFilter()
		{ 
		    Set<String> result = new HashSet<String>();
			for (VendorContracts vc : vendorContractsService.findAll())
			{
			   result.add(vc.getContractNo());		    	
			}
		    return result;
		}		
		//***  END FOR CNO FILTER ***//
	    
	   //***  FOR INVOICE PAYABLE DAYS FILTER  ***//
	   @RequestMapping(value = "/vcContract/getInvoicePayDays", method = RequestMethod.GET)
	   public @ResponseBody Set<?> getInvoicePayableDays()
	   { 
		   Set<String> result = new HashSet<String>();
		   for (VendorContracts vc : vendorContractsService.findAll())
		   {
			  int invoicePayableDays = vc.getInvoicePayableDays();
			  String InvPayDays = String.valueOf(invoicePayableDays);
			  result.add(InvPayDays);		    	
		   }
		   return result;
	   }		
	   //***  END FOR INVOCIE PAYABALE DAYS FILTER ***//
		
	   //***  FOR VENDOR SLA FILTER  ***//
	   @RequestMapping(value = "/vcContract/getVendorSla", method = RequestMethod.GET)
	   public @ResponseBody Set<?> getVendorSla()
	   { 
		   Set<String> result = new HashSet<String>();
		   for (VendorContracts vc : vendorContractsService.findAll())
		   {
			   int invoicePayableDays = vc.getVendorSla();
			   String sla = String.valueOf(invoicePayableDays);
			   result.add(sla);		    	
		   }
		   return result;
	   }		
	   //***  END FOR VENDOR SLA FILTER ***//
	   
	   
	   //***  FILTER FOR CONTRACT STATUS  ***//	   
	   @RequestMapping(value = "/vcContract/getContractStatusFilter", method = RequestMethod.GET)
	   public @ResponseBody Set<?> getContractStatusFilter()
	   { 
		   Set<String> result = new HashSet<String>();
		   for (VendorContracts vc : vendorContractsService.findAll())
		   {
			  result.add(vc.getStatus());		    	
		   }
		   return result;
	   }
	   
	   
	   //*** END FOR VENDO CONTRACTS FILTER  ***/
	   
		
 /******************************************************************  END FOR VENDOR CONTRACTS CONTROLLER  ******************************************************/
		
				
/************************************************************ [< FOR VENDOR CONTRACTS LINE ITEMS(CHILD FOR CONTRACTS) >] ****************************************************/		

		@SuppressWarnings({ "serial", "unchecked" })
		//***   For sending the contents to the jsp  ****************/
		@RequestMapping(value = "/vendorContractsLineItems/vendorContractsReadLineItems/{vcId}", method = RequestMethod.GET)
	    public @ResponseBody List<?> read(@PathVariable int vcId) 
		{	
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("vcId", vcId);			
			List<VendorContractLineitems> list =  (List<VendorContractLineitems>) vcLineItemService.selectObjectQuery(params);	
			List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
			for (final VendorContractLineitems record : list)
			{				
				response.add(new HashMap<String, Object>() 
				{{
					String type = record.getReqType();
					if(type.equalsIgnoreCase("Item Supply"))
					{
						put("imId", record.getImId());
						put("imName",record.getItemMaster().getImName());
						put("uomId",record.getUomId());
		            	put("uom",record.getUnitOfMeasurement().getUom());
					}
					if(type.equalsIgnoreCase("Manpower"))
					{
						put("imId","");
						put("imName","");
						put("uomId",record.getUomId());
		            	put("uom",record.getUnitOfMeasurement().getUom());
					}
					if(type.equalsIgnoreCase("General Contract"))
					{
						put("imId", "");
						put("imName","");
						put("uomId","");
		            	put("uom","");
					}
					
					put("vclId", record.getVclId());
	            	put("vcId", record.getVcId());
	            	//put("imName",record.getItemMaster().getImName());
	            	put("rate", record.getRate());
	            	put("vclSlno",record.getVclSlno());
	            	put("amount",record.getAmount());
	            	put("quantity", record.getQuantity());	            	
	            	put("reqType", record.getReqType());	            	
	            	/*put("uomId",record.getUomId());
	            	put("uom",record.getUnitOfMeasurement().getUom());*/
				}});
			}
			return response;
	    }
		/***********   END ********************************/	
		
		//***  FOR CREATING THE VENDOR CONTRACTS RECORD @throws InterruptedException *******//	
		@RequestMapping(value = "/vendorContractsLineItems/vendorContractsUpdateLineItems/{vcId}", method = {RequestMethod.GET, RequestMethod.POST})
		public @ResponseBody Object updatevendorContractsLineItems(@ModelAttribute("vendors")VendorContractLineitems vendorContractLineitems, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale) throws InterruptedException 
		{	
		    logger.info("Inside UPDATE VENDOR-CONTRACTS-LINEITEMS Record");
			vendorContractLineitems.setLastUpdatedBy(vendorContractLineitems.getLastUpdatedBy());
			vendorContractLineitems.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			                                                                                                                                                                                           
			int imid = vendorContractLineitems.getImId();
			int uid = vendorContractLineitems.getUomId();
			int rate = vendorContractLineitems.getRate();                                                                
			int qt = vendorContractLineitems.getQuantity();
			logger.info("Item_Id & UOM_Id"+imid+"\n"+uid);
			logger.info("Rate & Quantity"+rate+"\n"+qt); 
			
			vendorContractLineitems.setRate(rate);
			vendorContractLineitems.setQuantity(qt);			
			if(imid==0)
			{
				logger.info("ITEM_IS NULL inside if");
				vendorContractLineitems.setImId(1);
			}
			else
			{
				logger.info("inside else");
				vendorContractLineitems.setImId(vendorContractLineitems.getImId());
			}
			if(uid==0)
			{
				logger.info("UOM_ID NULL INSIDE IF");
				vendorContractLineitems.setUomId(1);
			}
			else
			{
				vendorContractLineitems.setUomId(vendorContractLineitems.getUomId());
			}
		    vcLineItemService.update(vendorContractLineitems);
			return vendorContractsService.setResponse();			
		}
		//***  END FOR CREATING THE VENDOR CONTRACTS RECORDS *****/		
		
		
/*******************************************************  END FOR VENDOR CONTRACTS LINE ITEMS(CHILD FOR CONTRACTS) *****************************************************/
		
						
		
/*******************************************************************  [< FOR VENDOR INVOICES CONTROLLER >]  ***************************************************************/
		//***  FOR REDING VENDOR INVOICES INDEX PAGE ***//
		@RequestMapping(value = "/vendorInvoices")
		public String indexvendorInvoices(ModelMap model,HttpServletRequest request) 
		{
		 	logger.info("VENDOR INVOICES Index Page From Controller");
			model.addAttribute("ViewName", "Vendors");
			breadCrumbService.addNode("nodeID", 0,request);
			breadCrumbService.addNode("Vendors", 1,request);
			breadCrumbService.addNode("Manage Vendor &nbsp;Invoices", 2, request);
			/*model.addAttribute("vendorContracts", commonController.populateCategories("vcId", "contractType", "VendorContracts"));*/
			return "vendorManagement/vendorInvoices";
	    }
		
		//*** FOR READING THE CONTENTS TO JSP    ************/
		@RequestMapping(value = "/vendorInvoices/readVendorInvoiceUrl", method = RequestMethod.POST)
		public @ResponseBody List<?> readAllVendorInvoices()
		{
		   return vendorInvoiceService.setResponse();
		} 
		//***  END OF READING CONTENTS   *******/
		
		//*** FOR READING VENDOR CONTRACTS TEMPALTE TO DROPDOWN  ***//
		@SuppressWarnings("serial")
		@RequestMapping(value = "/vendorInvoice/readVendorContracts", method = RequestMethod.GET)
	    public @ResponseBody List<?> readVendorContracts() 
		{
			List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
			for(final VendorContracts record : vendorContractsService.findAll())
			{
				result.add(new HashMap<String,Object>()
				{{
					 put("vcId",record.getVcId());
					 put("vendorId",record.getVendors().getVendorId());
					 put("vendorInvoiceDetails",record.getContractNo()+" "+record.getContractName());
					 put("contractName",record.getContractNo());
				}});
			}
			return result;
		}
		//****  END FOR READING CONTRACTS  ***//
		
		
		//***  FOR READING VENDOR CONTRACTS THROUGH VCLINEITEMS  ****//
		@SuppressWarnings({ "serial", "rawtypes" })
		@RequestMapping(value = "/vendorInvoice/readAvailableVendorContractsFromVCLineItems", method = RequestMethod.GET)
	    public @ResponseBody List testRead() 
		{
			List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
			for(final StoreGoodsReceipt record : goodsReceiptService.getRequiredStoreGoodsReceipts())
			{
				final String cname = record.getContractName();
				final String cno = record.getContractNo();
				result.add(new HashMap<String,Object>()
				{{
					
					 put("vcId",record.getVcId());
					 put("contractName",cname);
					 put("contractNo",cno);
					 put("vendorInvoiceDetails",cname+cno);
				}});
			}
			return result;			
		}
		//*****  END FOR VENDOR CONTRACTS THROUGH VCLINEITEMS  ****/
		
		//****** FOR GETTING CONTRACT_START_DATE for Vendor Invoices *******//
		@RequestMapping(value="/vendorInvoices/readVendorContractStartDateBasedOnVcId/{vcId}",method=RequestMethod.GET)
		public @ResponseBody java.sql.Date readVendorContractStartDateBasedOnVcId(@PathVariable int vcId)
		{
			java.sql.Date dtVal = vendorInvoiceService.getVendorContractStartDateBasedOnVcId(vcId);
			return dtVal;
		}
		//******  END FOR GETTING CONTRACT_START_DATE ***********//
		
		//***  FOR CREATING THE VENDOR INVOICE RECORD * @throws InterruptedException *******/	
		@RequestMapping(value = "/vendorInvoices/createVendorInvoiceUrl", method = RequestMethod.GET)
		public @ResponseBody Object createVendorInvoiceUrl(Map<String, Object> map,@ModelAttribute("vendors")VendorInvoices vendorInvoices, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale) throws InterruptedException 
		{				
			logger.info("Inside Create Vendor Invoice Record");
			int vendorContractId = vendorInvoices.getVcId();
			
			//for Persisiting data into child grid i.e VI_LINEITEMS
			vendorInvoices.setVendorLineitems(getVendorInvoicesLineItemset(vendorInvoices,vendorContractId));
			
			String invoiceDescription = vendorInvoices.getDescription();
			String invdesc_trim = invoiceDescription.trim();
			vendorInvoices.setDescription(invdesc_trim);
			
			//For Persisisting data into parent
			vendorInvoiceService.save(vendorInvoices);
			return vendorInvoiceService.setResponse();
		}
		//***  END FOR CREATING THE VENDOR INVOICE RECORDS *****/
		
		
		///////////////////////////////////////////////////////////////////////////////////
		@SuppressWarnings("unused")
		private Set<VendorLineItems> getVendorInvoicesLineItemset(VendorInvoices vendorInvoices,int vcid) throws InterruptedException
		{
			int storeId = vendorLineItemService.getStoreIdBasedOnVcId(vcid);
			Double rate = null;
			Double quantity = null;
			int imid = 0;
			int uomid = 0;
			List<?> list1 = vendorLineItemService.getStorGoodreceiptData(storeId);				
			logger.info("Number od store goods receipt data "+list1.size());				
			for (Iterator<?> i = list1.iterator(); i.hasNext();)
			{
				final Object[] values = (Object[]) i.next();
				rate = (Double)values[0];
				quantity = (Double)values[1];
				imid = (int)values[2];
				uomid = (int)values[3];
			}
			Set<VendorLineItems> set = new HashSet<VendorLineItems>();
			List<String> attributes = new ArrayList<String>();
			attributes.add("imId");
			attributes.add("rate");
			attributes.add("itemQuantity");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("stgrId", storeId);			
			List<?> list = vcLineItemService.selectQuery("StoreGoodsReceiptItems", attributes, params);			
			int count = 1;
			for (Iterator<?> i = list.iterator(); i.hasNext();) 
			{
				final Object[] values = (Object[]) i.next();				
				VendorLineItems vendorLineItems = new VendorLineItems();
				vendorLineItems.setCreatedBy(vendorInvoices.getCreatedBy());
				vendorLineItems.setImId((int)values[0]);
				vendorLineItems.setRate((Double)values[1]);
				vendorLineItems.setQuantity((Double)values[2]);
				vendorLineItems.setReqtype("Item Supply");
				
				vendorLineItems.setVilSlno(count);
				count++;
				
				vendorLineItems.setLastUpdatedBy(vendorInvoices.getLastUpdatedBy());
				vendorLineItems.setLastUpdatedDt(new Timestamp(new Date().getTime()));		
				set.add(vendorLineItems);
			}
			return set;
		}		
		///////////////////////////////////////////////////////////////////////////////////
		
		//***  FOR UPDATING THE VENDOR INVOICE RECORD  *******/	
		@RequestMapping(value = "/vendorInvoices/updateVendorInvoiceUrl", method = RequestMethod.GET)
		public @ResponseBody Object updateVendorInvoiceUrl(Map<String, Object> map,@ModelAttribute("vendors")VendorInvoices vendorInvoices, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale) 
		{				
			logger.info("Inside UPDATING Vendor Invoice Record");
			vendorInvoiceService.update(vendorInvoices);
			return vendorInvoiceService.setResponse();
		}
		//***  END FOR UPDATING THE VENDOR INVOICE RECORDS *****/
		
		//***  @throws IOException FOR REQUISITION ACTIVATING AND DE-ACTIVATING  ***//
		@RequestMapping(value = "/vendorInvoice/vendorInvoiceStatus/{viId}/{operation}", method = RequestMethod.POST)
		public String vendorInvoiceStatus(@PathVariable int viId,@PathVariable String operation,ModelMap model, HttpServletRequest request,HttpServletResponse response, final Locale locale) throws IOException
		{		
			logger.info("UPDATE STATUS");
			vendorInvoiceService.setVendorInvoiceStatus(viId, operation, response, messageSource, locale);
			@SuppressWarnings("unused")
			VendorInvoices vendorInvoices=vendorInvoiceService.find(viId);
			@SuppressWarnings("unused")
			PrintWriter out;
			return null;
		}
		//***  END FOR REQUISITION ACTIVATING AND DE-ACTIVATING ***//		
				
		@RequestMapping(value = "/vendorInvoice/getContractNameFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getContractNameFilterForVendorinvoice()
		{ 
		     Set<String> result = new HashSet<String>();
			 for (VendorInvoices vc : vendorInvoiceService.findAll())
			 {
				 result.add(vc.getVendorContracts().getContractName());		    	
			 }
			 return result;
		}
		
		//** FILTER FOR INVOICE NO  ***//
		@RequestMapping(value = "/vendorInvocie/getInvoiceNoFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getInvoiceNoFilter()
		{ 
		     Set<String> result = new HashSet<String>();
			 for (VendorInvoices vc : vendorInvoiceService.findAll())
			 {
				 result.add(vc.getInvoiceNo());		    	
			 }
			 return result;
		}	
		//** END FOR INVOICE NO FILTER  ***//		
		
		//** FILTER FOR INVOICE TITLE  ***//
		@RequestMapping(value = "/vendorInvocie/getInvoiceTitleFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getInvoiceTitleFilter()
		{ 
		     Set<String> result = new HashSet<String>();
			 for(VendorInvoices vc : vendorInvoiceService.findAll())
			 {
				 result.add(vc.getInvoiceTitle());		    	
			 }
			 return result;
		}		
		//** END FOR INVOICE TITLE FILTER  ***//
		
		//** FILTER FOR INVOICE DESCRIPTION  ***//
		@RequestMapping(value = "/vendorInvocie/getInvoiceDescriptionFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getInvoiceDescriptionFilter()
		{ 
		     Set<String> result = new HashSet<String>();
			 for(VendorInvoices vc : vendorInvoiceService.findAll())
			 {
				 result.add(vc.getDescription());		    	
			 }
			 return result;
		}
		//** END FOR INVOICE DESCRIPTION FILTER  ***//
		
		//*** FILTER FOR VENDOR INVOICE STATUS  ***//		
		@RequestMapping(value = "/vendorInvocie/getInvoiceStatusFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getInvoiceStatusFilter()
		{ 
		     Set<String> result = new HashSet<String>();
			 for(VendorInvoices vc : vendorInvoiceService.findAll())
			 {
				 result.add(vc.getStatus());		    	
			 }
			 return result;
		}
		
		//*** END FOR VENDOR INVOICE STATUS FILTER  ***//
		
/***************************************************************** END FOR VENDOR INVOICES CONTROLLER *****************************************************************/
		
		
		
/********************************************************* [< FOR VENDOR PAYEMNTS LINE ITEMS(CHILD FOR VENDOR INVOICES) >]   ****************************************************/		

		@SuppressWarnings("serial")
		@RequestMapping(value = "/vendorPayments/vendorPaymentsReadUrl/{viId}", method = RequestMethod.POST)
		public @ResponseBody List<?> readVendorPayments(@PathVariable int viId) 
		{	
			List<VendorPayments> list =  vendorPaymentsService.findVendorPaymentsBasedOnVendorInvoice(viId);
			List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
			for (final VendorPayments record : list)
			{
				response.add(new HashMap<String, Object>() 
				{{
					put("vipId", record.getVipId());
	            	put("viId", record.getViId());
	            	java.util.Date viDateUtil = record.getVipDt();
					java.sql.Date viDateSql = new java.sql.Date(viDateUtil.getTime());
	            	put("vipDt", viDateSql);
	            	put("vendorInvoiceNoTitle",record.getVendorInvoices().getInvoiceNo()+" "+record.getVendorInvoices().getInvoiceTitle());
	            	put("vipPayType", record.getVipPayType());
	            	put("vipPayamount", record.getVipPayamount());
	            	put("vipPayBy", record.getVipPayBy());
	            	put("vipPaydetails", record.getVipPaydetails());
	            	put("vipNotes",record.getVipNotes());
	            	put("createdBy", record.getCreatedBy());
	            	put("lastUpdatedBy", record.getLastUpdatedBy());
	            	put("lastUpdatedDt", record.getLastUpdatedDt());
				}});
			}
			return response;
			//return vendorPaymentsService.setResponse();	
		}		
		//End for reading the contents to jsp
		
		//***   For reading the vednorInvoiceTemplate  *****/
		@SuppressWarnings("serial")
		@RequestMapping(value = "/vendorInvoice/vendorInvoiceUrl/{viId}", method = RequestMethod.GET)
		public @ResponseBody List<?> getVendorInvoiceUrl(@PathVariable int viId) 
		{
			List<Map<String, Object>> vendorPayment =  new ArrayList<Map<String, Object>>(); 		
		    for (final VendorInvoices record : (List<VendorInvoices>) vendorInvoiceService.findById(viId)) 
	        {	
		    	vendorPayment.add(new HashMap<String, Object>() 
			     {{
					 put("viId" ,record.getViId());
					 put("vendorInvoiceNoTitle" ,record.getInvoiceNo()+" "+record.getInvoiceTitle());
			     }});
	        }
			return vendorPayment;
		}
		//***  End for reading the vendorInvoiceTemplate  *****/
		
		//***   FOR CREATING THE VENDOR PAYMENTS @throws ParseException *****/
		@RequestMapping(value = "/vendorPayments/vendorPaymentsCreateUrl/{viId}", method = {RequestMethod.GET, RequestMethod.POST})
		public @ResponseBody Object createVendorPayments(@ModelAttribute("vendorPayments")VendorPayments vendorPayments, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws InterruptedException, ParseException 
		{	
		    String assetYear = req.getParameter("vipDt");
			vendorPayments.setVipDt(dateTimeCalender.getDateToStore(assetYear));
			
			String paymentDetails = vendorPayments.getVipPaydetails();
			String payDetails_trim = paymentDetails.trim();
			
			
			String paymentNotes = vendorPayments.getVipNotes();
			String payNotes_trim = paymentNotes.trim();
			logger.info("DETAILS -->"+payDetails_trim+"\n\nNOTES-->"+payNotes_trim);
			
			vendorPayments.setVipPaydetails(payDetails_trim);
			vendorPayments.setVipNotes(payNotes_trim);
		    vendorPaymentsService.save(vendorPayments);
			return vendorPaymentsService.setResponse();
		}		
		//***  END FOR CREATING VENDOR PAYMENTS  *****/
		
		//***  FOR UPDATING THE VENDOR PAYMENTS *****/		
		@RequestMapping(value = "/vendorPayments/vendorPaymentsUpdateUrl/{viId}", method = {RequestMethod.GET, RequestMethod.GET})
		public @ResponseBody Object  updateVendorPayments(@ModelAttribute("vendorPayments")VendorPayments vendorPayments, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws InterruptedException, ParseException 
		{	
		    logger.info("Inside UPDATE VENDOR-PAYMENTS RECORD");
		    String assetYear = req.getParameter("vipDt");
			vendorPayments.setVipDt(dateTimeCalender.getDateToStore(assetYear));
		    vendorPaymentsService.update(vendorPayments);
			return vendorPaymentsService.setResponse();
		}	
		//***   END FOR UPDATING VENDOR PAYMENTS  *****/
		
		//***   FOR DELETING THE VENDOR PAYMENT RECORDS  ******/
		@RequestMapping(value = "/vendorPayments/vendorPaymentsDestroyUrl",method = RequestMethod.GET)
		public @ResponseBody VendorPayments deleteVendorPayments(@ModelAttribute("vendorPayments") VendorPayments vendorPayments,BindingResult bindingResult, SessionStatus sessionStatus) {
			vendorPaymentsService.delete(vendorPayments.getVipId());			
			sessionStatus.setComplete();
			return vendorPayments;
		}
		
/************************************************** END FOR VENDOR PAYEMNTS LINE ITEMS(CHILD FOR INVOICES) ******************************************************/
			
		
/*************************************************  [< FOR VENDOR INVOICE LINEITEMS (CHILD GRID FOR VENDOR INVOICES >]  *****************************************/
		/************  For sending the contents to the jsp  ****************/
		@SuppressWarnings("serial")
		@RequestMapping(value = "/vendorInvoiceLineItems/vendorInvoiceLineItemsReadUrl/{viId}", method = RequestMethod.POST)
	    public @ResponseBody List<?> vendorInvoiceLineItemsReadUrl(@PathVariable int viId) 
		{	
			List<VendorLineItems> list =  vendorLineItemService.findVendorLineItemsBasedOnVendorInvoice(viId);
			List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
			for (final VendorLineItems record : list)
			{
				response.add(new HashMap<String, Object>() 
				{{
					put("vilId", record.getVilId());
	            	put("viId", record.getViId());
	            	put("imId", record.getImId());
	            	put("imName",record.getItemMaster().getImName());
	            	put("rate", record.getRate());
	            	put("vilSlno",record.getVilSlno());
	            	put("amount",record.getAmount());
	            	put("quantity", record.getQuantity());
	            	put("reqType", record.getReqtype());
				}});
			}
			return response;
	    }
		/***********   END ********************************/
		
		/***** FOR CREATING VENDOR INVOICE LINEITEMS RECORD *******/	
		@RequestMapping(value = "/vendorInvocieLineItems/vendorInvoiceLineItemsUpdateUrl/{viId}", method = RequestMethod.GET)
		public @ResponseBody Object vendorInvoiceLineItemsUpdateUrl(@ModelAttribute("vendors")VendorLineItems vendorLineItems, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale) 
		{	
		    logger.info("\n\n----------------------Inside UPDATE VENDOR INVOICE LINEITEMS Record");
		    vendorLineItems.setReqtype("Item Supply");
			vendorLineItemService.update(vendorLineItems);
			return vendorLineItems;
			//return vendorLineItemService.setResponse();
		}
		/*****  END FOR CREATING VENDOR INVOICE LINEITEMS  *******/
				
		/******  FOR DELETING THE VENDOR INVOICE LINEITEMS RECORDS  ******/
		@RequestMapping(value = "/vendorInvocieLineItems/vendorInvoiceLineItemsDestroyUrl",method = RequestMethod.GET)
		public @ResponseBody VendorLineItems deleteVcLineItems(@ModelAttribute("lineItems") VendorLineItems lineItems ,BindingResult bindingResult, SessionStatus sessionStatus) {
			vendorLineItemService.delete(lineItems.getVilId());			
			sessionStatus.setComplete();
			return lineItems;
		}
		/*****  END FOR DELETING THE VENDOR INVOICE LINEITEMS RECORD  *****/
		
/************************************************  [<  END FOR VENDOR INVOICE LINEITEMS (CHILD GRID FOR VEDNOR INVOICES) >]  ***********************************/
		
		
		

/******************************************************** [< FOR VENDOR INCIDENTS CONTROLLER >] ************************************************************************/
		@RequestMapping(value = "/vendorIncidents")
		public String indexvendorIncidents(ModelMap model,HttpServletRequest request) 
		{ 
			logger.info("VENDOR INCIDENTS Index Page From Controller");
			model.addAttribute("ViewName", "Vendors");
			breadCrumbService.addNode("nodeID", 0,request);
			breadCrumbService.addNode("Vendors", 1,request);
			breadCrumbService.addNode("Manage Vendor &nbsp;Incidents", 2, request);
			return "vendorManagement/vendorIncidents";
		}

		/**********     FOR READING THE CONTENTS TO JSP    ************/
		@RequestMapping(value = "/vendorIncidents/readvendorIncidentsUrl", method = RequestMethod.POST)
		public @ResponseBody List<?> readVendorIncidentsUrl()
		{
		   return vendorIncidentService.setResponse();
		} 
		 /*******   END OF READING CONTENTS   *******/
		
		@SuppressWarnings("serial")
		@RequestMapping(value="/vendorIncidents/readVendorContractsForVendorIncidents",method=RequestMethod.GET)
		public @ResponseBody List<?> readVendorsContractsForIncidents()
		{
			List<VendorContracts> list =  vendorContractsService.findAll();
			List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
			for (final VendorContracts record : list)
			{
				String status = record.getStatus();
				int vcid = record.getVcId();
				if(vcid!=0)
				{
					if(status.equalsIgnoreCase("Approved"))
					{
						response.add(new HashMap<String, Object>() 
					    {{
							 put("vcId",record.getVcId());
							 put("vendorId",record.getVendors().getVendorId());
							 put("firstName",record.getVendors().getPerson().getFirstName());
							 put("vendorInvoiceDetails",record.getContractNo()+" "+record.getContractName());
							 put("contractName",record.getContractName());    	
						}});
					}
				}
			}
			return response;
		}
		
		/******  FOR READING VENDOR_CONTRACT_SLA FOR SELECTED CONTRACT *********/
		@RequestMapping(value="/vendorIncidents/getSLAForSelectedContract/{vcId}",method = RequestMethod.POST)
		public @ResponseBody Integer getSLAForSelectedContract(@PathVariable int vcId,HttpServletResponse response) throws IOException
		{
			int val = vendorIncidentService.getContractSLA(vcId);
			return val;			
		}
		
		/***** FOR CREATING THE VENDOR INCIDENTS RECORD  
		 * @throws ParseException *******/	
		@RequestMapping(value = "/vendorIncidents/createvendorIncidentsUrl", method = RequestMethod.GET)
		public @ResponseBody Object createVendorIncidentsUrl(Map<String, Object> map,@ModelAttribute("vendors")VendorIncidents vendorIncidents, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException 
		{			
			String incidentDescription = vendorIncidents.getIncidentDescription();
			String incidentDesc_trim = incidentDescription.trim();			
			
			String slacomments = vendorIncidents.getSlaComments(); 
			String slaComm_trim = slacomments.trim();
			
			vendorIncidents.setIncidentDescription(incidentDesc_trim);
			vendorIncidents.setSlaComments(slaComm_trim);
			
			int vendorContractId = vendorIncidents.getVcId();			
			VendorContracts vendorContracts = vendorContractsService.find(vendorContractId);
			int vendorId = vendorContracts.getVendorId();
			String contractNumber = vendorContracts.getContractNo();
			String contractName = vendorContracts.getContractName();
			
			
			int expectedsla = vendorIncidentService.getContractSLA(vendorContractId);
			vendorIncidents.setExpectedSla(expectedsla);
			
			vendorIncidentService.save(vendorIncidents);
			
			int incidentpersonId = 0;
			String incidentsalutaion = "";
			String incidentfname = "";
			String incidentlname = "";
			List<Person> vendorList = vendorContractsService.readPersonIdBasedOnVendorId(vendorId);
			for (final Person personRecord : vendorList) 
			{	
				incidentpersonId = personRecord.getPersonId();
				incidentsalutaion = personRecord.getTitle();
				incidentfname = personRecord.getFirstName();
				incidentlname = personRecord.getLastName();
			}
			
			String fullname = null;
		    if(incidentlname == null)
		    {
		    	fullname = incidentfname;
		    }
		    else if(incidentlname!=null)
		    {
		    	fullname = incidentfname+"\t"+incidentlname;
		    }
		    logger.info("\n\n"+incidentpersonId+"\n\n"+incidentsalutaion+"\n\n"+fullname);
			List<Contact> c = contactService.findAll(); //FOR GETTING CONTACT DETAILS OF TENANT & OWNER
			Iterator<Contact> itc = c.iterator();
			while(itc.hasNext())
			{
				//******  CHECKING CONTACT DETAILS FOR TENANT BASED ON PERSONID ***********//*
				Contact contact = itc.next();
				if(contact.getPersonId() == incidentpersonId)
				{
				    logger.info("PersonId In Contact table:"+contact.getPersonId() +" == " +incidentpersonId);
				 	String incidentcontactType = contact.getContactType();
					String incidentcontactContent = contact.getContactContent();
					logger.info("Contact-Type Is-- "+incidentcontactType+"\n\n"+"Contact-Content is --"+incidentcontactContent);
					if(incidentcontactType.equalsIgnoreCase("email"))
					{
						new Thread(new VendorIncidentMail(incidentcontactContent, fullname,contractName, contractNumber)).start();						
					}
					else if(incidentcontactType.equalsIgnoreCase("Mobile"))
					{
						
					}
				}
			}
			return vendorIncidentService.setResponse();
		}
		/***** END FOR CREATING THE VENDOR INCIDENTS RECORDS *****/
		
		/***** FOR UPDATING THE VENDOR INVOICE RECORD  *******/	
		@RequestMapping(value = "/vendorIncidents/updatevendorIncidentsUrl", method = RequestMethod.GET)
		public @ResponseBody Object updatevendorIncidentsUrl(Map<String, Object> map,@ModelAttribute("vendors")VendorIncidents vendorIncidents, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale) 
		{				
			logger.info("Inside UPDATING Vendor Incident Record");
			vendorIncidentService.update(vendorIncidents);
			return vendorIncidentService.setResponse();
		}
		/***** END FOR UPDATING THE VENDOR INVOICE RECORDS *****/
		
		@RequestMapping(value = "/vendorIncidents/updateVendorIncidentStatus/{id}/{status}", method = RequestMethod.POST)
		public void updateVendorIncidentsStatus(@PathVariable int id,@PathVariable String status,HttpServletResponse response) throws IOException
		{
			List<VendorIncidents> vinc=vendorIncidentService.findAll();
			Iterator<VendorIncidents> it=vinc.iterator();
			PrintWriter out = response.getWriter();
			while(it.hasNext())
			{
				VendorIncidents vendorIncidents=it.next();				
				if(vendorIncidents.getVcSlaId()==id)
				{
					logger.info("UPADTED  THE DB");
					vendorIncidentService.updateVendorIncidentsStatus(id, status);
				}
			}
			out.write("Status Updated Successfully");
		}
		
		
		//***  FILTER FOR VENDOR INCIDENT VENDOR_CONTRACTS  ***//		
		@RequestMapping(value = "/vendorIncidents/getVendorContractsFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getVendorContractsFilter()
		{ 
		    Set<String> result = new HashSet<String>();
			for (VendorIncidents vi : vendorIncidentService .findAll())
			{
			    result.add(vi.getVendorContracts().getContractName());		    	
			}
		    return result;
		}
		//***  END FOR READINF VENDOR_CONTRACTS ***//
		
		//***  FILTER FOR VENDOR INCIDENT DESCRIPTION  ***//
		@RequestMapping(value = "/vendorIncidents/getVendorIncidentDescriptionFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getVendorIncidentDescriptionFilter()
		{ 
		    Set<String> result = new HashSet<String>();
			for (VendorIncidents vi : vendorIncidentService .findAll())
			{
			    result.add(vi.getIncidentDescription());		    	
			}
		    return result;
		}
		//*** END FOR VENDOR INCIDET DESCRIPTION ***//
		
		
		//***  FILTER FOR VENDOR INCIDENT DESCRIPTION  ***//
		@RequestMapping(value = "/vendorIncidents/getVendorIncidentExpectedSlaFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getVendorIncidentExpectedSlaFilter()
		{ 
		    Set<String> result = new HashSet<String>();
			for (VendorIncidents vi : vendorIncidentService .findAll())
			{
				int expSla = vi.getExpectedSla();
				String expectedSla = String.valueOf(expSla);
			    result.add(expectedSla);		    	
			}
		    return result;
		}
		//*** END FOR VENDOR INCIDET DESCRIPTION ***//
		
		//***  FILTER FOR VENDOR INCIDENT DESCRIPTION  ***//
		@RequestMapping(value = "/vendorIncidents/getVendorIncidentSlaReachedFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getVendorIncidentSlaReachedFilter()
		{ 
		    Set<String> result = new HashSet<String>();
			for (VendorIncidents vi : vendorIncidentService .findAll())
			{
				int slaReach = vi.getSlaReached();
				String slareached = String.valueOf(slaReach);
			    result.add(slareached);		    	
			}
		    return result;
		}
	    //*** END FOR VENDOR INCIDET DESCRIPTION ***//
		
		//***  FILTER FOR VENDOR INCIDENT DESCRIPTION  ***//
		@RequestMapping(value = "/vendorIncidents/getVendorIncidentSlaCommentsFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getVendorIncidentSlaCommentsFilter()
		{ 
		    Set<String> result = new HashSet<String>();
			for (VendorIncidents vi : vendorIncidentService .findAll())
			{
			    result.add(vi.getSlaComments());		    	
			}
		    return result;
		}
	    //*** END FOR VENDOR INCIDET DESCRIPTION ***//
		
		
		//***  FILTER FOR VENDOR INCIDENT DESCRIPTION  ***//
		@RequestMapping(value = "/vendorIncidents/getVendorIncidentSlaStatusFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getVendorIncidentSlaStatusFilter()
		{ 
		    Set<String> result = new HashSet<String>();
			for (VendorIncidents vi : vendorIncidentService .findAll())
			{
			    result.add(vi.getSlaStatus());		    	
			}
		    return result;
		}
		//*** END FOR VENDOR INCIDET DESCRIPTION ***//
		
/*************************************************************** END FOR VENDOR INCIDENTS CONTROLLER  ********************************************************************/
		
		
/***********************************************  FOR VENDOR REQUEST CONTROLLER  ********************************************************/		
		
		//**  FOR REDING VENDOR REQUETS INDEX PAGE **//
		@RequestMapping(value = "/vendorRequests")
		public String indexvendorRequests(ModelMap model,HttpServletRequest request) 
		{
			logger.info("VENDOR REQUESTS Index Page From Controller");
			model.addAttribute("ViewName", "Vendors");
			breadCrumbService.addNode("nodeID", 0,request);
			breadCrumbService.addNode("Vendors", 1,request);
			breadCrumbService.addNode("Manage Vendor &nbsp;Requests", 2, request);
			return "vendorManagement/vendorRequests";
		}
		// END FOR READING VENDOR REQUETS INDEX PAGE // 
		
		
		//**  FOR SENDING THE CONTENTS TO VENDOR REQUESTS JSP  **//
		@RequestMapping(value = "/vendorRequestsUrl/readVendorRequestsUrl", method = RequestMethod.POST)
		public @ResponseBody List<?> readVendorRequestsUrl()
		{
		   return vendorRequestService.setResponse();
		} 
		//  END FOR SENDING THE CONTENTS TO VEDNOR REQUESTS JSP // 
		
		@SuppressWarnings("serial")
		@RequestMapping(value = "/vendorRequest/getVendorNamesForvendorRequests", method = RequestMethod.GET)
		public @ResponseBody List<?> getVendorNamesForVendorRequests() 
		{
			List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
			for(final Vendors record : vendorsService.findAll())
			{
				final String lname = record.getPerson().getLastName();				
				result.add(new HashMap<String,Object>()
			    {{
			    	if(lname==null)
			    	{
			    		put("vendorName",record.getPerson().getFirstName());
			    	}
			    	else
			    	{
			    		put("vendorName",record.getPerson().getFirstName()+" "+record.getPerson().getLastName());
			    	}
			    	put("personId",record.getPerson().getPersonId());
			    	put("personType",record.getPerson().getPersonType());
			    	put("vendorId",record.getVendorId());
				}});
			}
			return result;
		} 
		
		//**  FOR CREATING VENDOR REQUESTS RECORD  **//
		@SuppressWarnings("unused")
		@RequestMapping(value = "/vendorRequestsUrl/createVendorRequestsUrl", method = RequestMethod.GET)
		public @ResponseBody Object createVendorRequestsUrl(Map<String, Object> map,@ModelAttribute("vendorRequests")VendorRequests vendorRequests, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) 
		{
			String requestNote = vendorRequests.getRequestNote(); 
			String reqNote_trim = requestNote.trim();
			
			String replynote = vendorRequests.getReplyNote();
			String replyNote_trim = replynote.trim();
			
			vendorRequests.setRequestNote(reqNote_trim);
			vendorRequests.setReplyNote("None");
			
			int vcid = vendorRequests.getVcId();
			logger.info("VendorContract_Id in Vendor_Request\n\n"+vcid);
			
			if(vcid == 0)
			{
				vendorRequests.setVcId(1);
				vendorRequestService.save(vendorRequests);
			}
			else
			{
				vendorRequestService.save(vendorRequests);
			}
			
			int vendorId = vendorRequests.getVendorId();
			
			List<Vendors> vendor = vendorsService.findAll();
			Iterator<Vendors> vendorIter = vendor.iterator();
			int personId = 0;
			String firstName = "";
			String lastName = "";
			String personSalutation = ""; 
			String contactType = "";
			String contactContent = "";
			while(vendorIter.hasNext())
			{
				Vendors vendorObj = vendorIter.next();
				if(vendorObj.getVendorId() == vendorId)
				{
					personId = vendorObj.getPersonId();
					personSalutation = vendorObj.getPerson().getTitle();
					firstName = vendorObj.getPerson().getFirstName();
					lastName = vendorObj.getPerson().getLastName();
				}
			}
		 	 List<Contact> c = contactService.findAll(); //FOR GETTING CONTACT DETAILS OF TENANT & OWNER
			 Iterator<Contact> itc = c.iterator();
			 while(itc.hasNext())
			 {
			 	 /******  CHECKING CONTACT DETAILS FOR VENDORS BASED ON PERSONID ***********/
				 Contact c1 = itc.next();
				 if(c1.getPersonId() == personId)
				 { 
					 contactType = c1.getContactType();
					 contactContent = c1.getContactContent();
				  	 if(contactType.equalsIgnoreCase("email"))
					 {
				  		new Thread(new VendorRequestEmail(contactContent, personSalutation+"."+firstName+"\t"+lastName)).start();
					 }
				 }
			 }
			return vendorRequestService.setResponse();
		}
		// END FOR CREATING VENDOR REQUESTS RECORD //		
		
		//**  FOR UPDATING VENDOR REQUESTS RECORD  **//		
		@RequestMapping(value = "/vendorRequestsUrl/updateVendorRequestsUrl", method = RequestMethod.GET)
		public @ResponseBody Object updateVendorRequestsUrl(Map<String, Object> map,@ModelAttribute("vendors")VendorRequests vendorRequests, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale) 
		{				
			logger.info("Inside UPDATING Vendor REQUESTS Record");
			vendorRequestService.update(vendorRequests);
			return vendorRequestService.setResponse();
		}		
		// END FOR UPDATING VENDOR REQUETS RECORD //		
		
		//**  FOR UPDATING VENDOR REQUESTS  STATUS  **//
		@RequestMapping(value = "/vendorRequestsUrl/statusUpdateForVendorRequests/{id}/{status}/{replyNoteVal}", method = RequestMethod.POST)
		public void statusUpdateForVendorRequests(@PathVariable int id,@PathVariable String status,@PathVariable String replyNoteVal,HttpServletResponse response) throws IOException
		{
			List<VendorRequests> vreq=vendorRequestService.findAll();
			Iterator<VendorRequests> it=vreq.iterator();
			PrintWriter out = response.getWriter();
			logger.info("\n"+id+"\n"+status+"\n"+replyNoteVal);
			while(it.hasNext())
			{
				VendorRequests vendorRequests=it.next();				
				if(vendorRequests.getVrId()==id)
				{
					logger.info("UPADTED  THE DB");
				}
				String replyVal_trim = replyNoteVal.trim();
				vendorRequestService.updateVendorRequestStatus(id, status,replyVal_trim);
			}
			out.write("Status Updated Successfully");
		}
		// END FOR UPDATING VENDOR REQUESTS STATUS  //
		
		//*** FOR FILTER URLS'S ***//
		
		@RequestMapping(value="/vendorRequestsUrl/getVendorNamesFilter",method=RequestMethod.GET)
		public @ResponseBody Set<?> getVendorNamesForFilter()
		{
			Set<String> result = new HashSet<String>();
			for(VendorRequests requests : vendorRequestService.findAll())
			{
				result.add(requests.getVendors().getPerson().getFirstName()+" "+requests.getVendors().getPerson().getLastName());
			}
			return result;
		}
		
		@RequestMapping(value="/vendorRequestsUrl/getVendorContractFilter",method=RequestMethod.GET)
		public @ResponseBody Set<?> getVendorContractFilter()
		{
			Set<String> result = new HashSet<String>();
			for(VendorRequests requests : vendorRequestService.findAll())
			{
				result.add(requests.getVendorContracts().getContractName()+" "+requests.getVendorContracts().getContractNo());
			}
			return result;
		}
		
		@RequestMapping(value = "/vendorRequestsUrl/getReqTypeFilterFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getReqTypeFilterFilter()
		{ 
		    Set<String> result = new HashSet<String>();
			for (VendorRequests requests : vendorRequestService.findAll())
			{
			    result.add(requests.getRequestType());		    	
			}
		    return result;
		}
		
		@RequestMapping(value = "/vendorRequestsUrl/getReqNoteFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getReqNoteFilter()
		{ 
		    Set<String> result = new HashSet<String>();
			for (VendorRequests requests : vendorRequestService.findAll())
			{
			    result.add(requests.getRequestNote());		    	
			}
		    return result;
		}
		
		@RequestMapping(value = "/vendorRequestsUrl/getReplyNoteFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getReplyNoteFilter()
		{ 
		    Set<String> result = new HashSet<String>();
			for (VendorRequests requests : vendorRequestService.findAll())
			{
			    result.add(requests.getReplyNote());		    	
			}
		    return result;
		}
		
		@RequestMapping(value = "/vendorRequestsUrl/getStatusFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getStatusFilter()
		{ 
		    Set<String> result = new HashSet<String>();
			for (VendorRequests requests : vendorRequestService.findAll())
			{
			    result.add(requests.getStatus());		    	
			}
		    return result;
		}
		
		//*** END FOR FILTER URL'S ***//
		
/**********************************************  END FOR VENDOR REQUEST CONTROLLER *****************************************************/	
		
		
		
		@SuppressWarnings("unused")
		/* ==================================  Concierge Vendors================================= */
		@RequestMapping(value = "/vendorPerson/createVendors/{operation}/{personType}", method = {RequestMethod.POST ,RequestMethod.GET})
		public @ResponseBody Object createConciergerVendorPerson(@PathVariable String operation, @PathVariable String personType, @ModelAttribute("person") Person person, 
				BindingResult bindingResult, ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest request)
		{
		
			HttpSession session = request.getSession(false);
			String userName = (String)session.getAttribute("userId");
			if( request.getParameter("existedPersonId") != "" ){
				int existedPersonId = Integer.parseInt(request.getParameter("existedPersonId"));
			if ( existedPersonId != 0 )
			{	
				person = personService.find(existedPersonId);
				personService.updatePersonType(person, personType);
				person = personService.find(existedPersonId);
				
				Vendors vendor = new Vendors();
				vendor.setPerson(person);
				vendor.setPanNo(request.getParameter("panNo"));
				vendor.setCstNo(request.getParameter("cstNo"));
				vendor.setStateTaxNo(request.getParameter("stateTaxNo"));
				vendor.setServiceTaxNo(request.getParameter("serviceTaxNo"));
				vendor.setStatus("Created");
				vendor.setVendorPersonStatus("Inactive");
				vendor.setCreatedBy(person.getLastUpdatedBy());
				vendor.setLastUpdatedBy(person.getLastUpdatedBy());
				vendor.setLastUpdatedDt(new Timestamp(new Date().getTime()));
				vendor.setPerson(person);
				person.setVendors(vendor);
				
				personService.update(person);	
				person.setPersonImages(null);
				sessionStatus.setComplete();
				return personService.getPersonFullDetails(person);				
			}
			}
			else{
				try
				{
					//person.setPersonType(personType);
					person = personService.getPersonNewObject(person, operation);

					if(!((person.getPersonStyle()).equalsIgnoreCase("Individual")))
					{
						if((validationUtil.customValidateModelObject(person, "NonIndividual", locale)) != null)
							return validationUtil.customValidateModelObject(person, "NonIndividual", locale);
					}
					else
					{
						if((validationUtil.customValidateModelObject(person, "Individual", locale)) != null)
							return validationUtil.customValidateModelObject(person, "Individual", locale);
					}

					if(operation.equalsIgnoreCase("create"))
					{
						drGroupIdService.save(new DrGroupId(person.getCreatedBy(), person.getLastUpdatedDt()));
						person.setDrGroupId(drGroupIdService.getNextVal(person.getCreatedBy(), person.getLastUpdatedDt()));
						
						person = setChildSaveDetails(person, person.getPersonType(), request);
						personService.save(person);	

					}
					
					person.setPersonImages(null);
					sessionStatus.setComplete();
					return personService.getPersonFullDetails(person);
				}

				catch (Exception e)
				{
					e.printStackTrace();
					return errorResponse.throwException(locale);
				}
			}
			logger.info("personId "+person.getPersonId() +"existedPersonId   "+ request.getParameter("existedPersonId"));
			return null;			
		}
		private Person setChildSaveDetails(Person person, String personType, HttpServletRequest request)
		{
			Vendors vendor = new Vendors();
			vendor.setPerson(person);
			vendor.setPanNo(request.getParameter("panNo"));
			vendor.setCstNo(request.getParameter("cstNo"));
			vendor.setStateTaxNo(request.getParameter("stateTaxNo"));
			vendor.setServiceTaxNo(request.getParameter("serviceTaxNo"));
			vendor.setStatus("Created");
			vendor.setVendorPersonStatus("Inactive");
			vendor.setCreatedBy(person.getLastUpdatedBy());
			vendor.setLastUpdatedBy(person.getLastUpdatedBy());
			vendor.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			vendor.setPerson(person);
			person.setVendors(vendor);	
			return person;
		}
		
		@SuppressWarnings("unused")
		@Scheduled(cron = "${scheduling.job.cron.vendorContractCron}")
		public void runCronJob() throws Exception
		{	
			//Getting Current date
			Date today = new Date();			
			java.sql.Date todayDt = new java.sql.Date(today.getTime());
			java.sql.Date minusDate = null;
			java.sql.Date currentDate = new java.sql.Date(new Date().getTime());
			Calendar cal = Calendar.getInstance();
			
			int contractId = 0;
			Date contractStartDate = null;
			Date contractEndDate = null;
			int personId = 0;
			int vendorId = 0;
			String salutation = "";
			String fullName = "";
			String fname = "";
			String lname = "";
			String renewalRequired = "";
			String contractName = "";
			String contactType = "";
			String contactContent = "";
						
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			List<?> vendorContracts = vendorContractsService.readDataForCronScheduler();
			Map<String, Object> vcDetails = null;
			for (Iterator<?> iterator = vendorContracts.iterator(); iterator.hasNext();)
			{
				//Reading Values From DB
				final Object[] values = (Object[]) iterator.next();					
				contractId = (Integer)values[0];
				contractStartDate = (Date)values[1];
				contractEndDate = (Date)values[2];
						
				//Subtracting contractEndDate with 7 days 
				cal.setTime(contractEndDate);
				cal.add(Calendar.DATE, -7);
				Date minus7Days = cal.getTime();
				minusDate = new java.sql.Date(minus7Days.getTime());
				
				//Getting VendorId Based On ContractId
				VendorContracts contracts = vendorContractsService.find(contractId);
				vendorId = contracts.getVendorId();
				renewalRequired = contracts.getRenewalRequired();
				logger.info("VENDOR_ID"+vendorId);
				//Finding Person Details Based On VendorId
				List<Person> vendorList = vendorContractsService.readPersonIdBasedOnVendorId(vendorId);
				for (final Person personRecord : vendorList) 
				{
					personId = personRecord.getPersonId();
					salutation = personRecord.getTitle();
					lname = personRecord.getLastName();
					if(lname == "")
					{
						fullName = personRecord.getFirstName();
					}
					else
					{
						fullName = personRecord.getFirstName()+"\t"+personRecord.getLastName();
					}
				}
				logger.info(personId+"\n"+salutation+"\n"+fullName+"\n"+contractEndDate+"\n"+renewalRequired);
				
				//Getting Contact Details Based On PersonId
				List<Contact> c = contactService.findAll();
				Iterator<Contact> itc = c.iterator();
				while(itc.hasNext())
				{
				   /******  CHECKING CONTACT DETAILS FOR TENANT BASED ON PERSONID ***********/
				   Contact c1 = itc.next();
				   if(c1.getPersonId() == personId)
				   {
					  logger.info("PersonId In Contact table:"+c1.getPersonId() +" == " +personId);
					  contactType = c1.getContactType();
					  contactContent = c1.getContactContent();
					  logger.info("Contact-Type Is ---> "+contactType+"\n\n"+"Contact-Content is ---> "+contactContent);
				   }
				}
				//Converting CurrentDate & SubtractedDate to String & comparing
				String presentdate = currentDate.toString();
				String dateBefore7Days = minusDate.toString();
				if(renewalRequired.equalsIgnoreCase("yes"))
				{
				   if(presentdate.equalsIgnoreCase(dateBefore7Days))
				   {
					  if(contactType.equalsIgnoreCase("email"))
					  {
						  
						  EmailCredentialsDetailsBean emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
						  SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
					      String messageContent ="<html>"
										+ "<style type=\"text/css\">"
										+ "table.hovertable {"
										+ "font-family: verdana,arial,sans-serif;"
										+ "font-size:11px;"
										+ "color:#333333;"
										+ "border-width: 1px;"
										+ "border-color: #999999;"
										+ "border-collapse: collapse;"
										+ "}"
										+ "table.hovertable th {"
										+ "background-color:#c3dde0;"
										+ "border-width: 1px;"
										+ "padding: 8px;"
										+ "border-style: solid;"
										+ "border-color: #394c2e;"
										+ "}"
										+ "table.hovertable tr {"
										+ "background-color:#88ab74;"
										+ "}"
										+ "table.hovertable td {"
										+ "border-width: 1px;"
										+ "padding: 8px;"
										+ "border-style: solid;"
										+ "border-color: #394c2e;"
										+ "}"
										+ "</style>"
										+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon RWA MAILROOM SERVICES.</h2> <br /> Dear "+fullName+", <br/> <br/> "
										+"This email is to inform you that your contract for the  <b>"+contractName+"</b><br/>"
										+ "is about to expire</b><br/>"
										+"Expiry Date:<b>"+contractEndDate+"</b><br/><br/>"								
										+"Thank you very much and have a nice day.<br/><br/>"
										+ "<br/>Sincerely,<br/><br/>"
										+ "Manager,<br/>"
										+"MailRoom Services,<br/>"
										+"Skyon RWA<br/><br/> This is an auto generated Email.Please dont revert back"
										+"</body></html>";
					      
					        emailCredentialsDetailsBean.setToAddressEmail(contactContent);
							emailCredentialsDetailsBean.setMessageContent(messageContent);
						  
						    new Thread(new VendorContractRenewalMail(emailCredentialsDetailsBean)).start();
						 logger.info("EMAIL SENT SUCCEEFULLY TO :"+contactContent);
					  }
					  else if(contactType.equalsIgnoreCase("mobile"))
					  {
						 logger.info("INSIDE MOBILE");
						 
					  }
				   }
				}
	 	    }			
		}

		@RequestMapping(value = "/vendorPerson/commonFilter/{feild}", method = RequestMethod.GET)
		public @ResponseBody Set<?> getAllContentsForFilter(@PathVariable String feild) {
			List<String> attributeList = new ArrayList<String>();
			attributeList.add(feild);
			HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("Vendors",attributeList, null));
			
		return set;
		}
		

		@RequestMapping(value = "/vendorInvoiceTemplate/vendorInvoiceTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
		public String exportFileFamily(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
			
			
	 
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
	        
	       
			List<Object[]> invoiceTemplateEntities =vendorInvoiceService.findAllInvoices();
		
	               
	        String sheetName = "Templates";//name of sheet

	    	XSSFWorkbook wb = new XSSFWorkbook();
	    	XSSFSheet sheet = wb.createSheet(sheetName) ;
	    	
	    	XSSFRow header = sheet.createRow(0);    	
	    	
	    	XSSFCellStyle style = wb.createCellStyle();
	    	style.setWrapText(true);
	    	XSSFFont font = wb.createFont();
	    	font.setFontName(HSSFFont.FONT_ARIAL);
	    	font.setFontHeightInPoints((short)10);
	    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    	style.setFont(font);
	    	
	    	header.createCell(0).setCellValue("Vendor Contracts");
	        header.createCell(1).setCellValue("Invoice Number");
	        header.createCell(2).setCellValue("Invoice Date");
	        header.createCell(3).setCellValue("Invoice Title");
	        header.createCell(4).setCellValue("Description");
	        header.createCell(5).setCellValue("Status");        
	       
	    
	        for(int j = 0; j<=5; j++){
	    		header.getCell(j).setCellStyle(style);
	            sheet.setColumnWidth(j, 8000); 
	            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:F200"));
	        }
	        
	        int count = 1;
	        //XSSFCell cell = null;
	    	for (Object[] values :invoiceTemplateEntities ) {
	    		
	    		XSSFRow row = sheet.createRow(count);
	    		
	    		//int value = count+1;

	    		row.createCell(0).setCellValue((String)values[2]);
	    		
	    		row.createCell(1).setCellValue((String)values[4]);
	    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    		java.util.Date invoiceDtUtil =  (java.util.Date)values[5];
				java.sql.Date invoiceDtSql = new java.sql.Date(invoiceDtUtil.getTime());
	    		
	    	    row.createCell(2).setCellValue(sdf.format(invoiceDtSql));
	    		
	    		row.createCell(3).setCellValue((String)values[6]);
	    		row.createCell(4).setCellValue((String)values[7]);
	    		row.createCell(5).setCellValue((String)values[8]);
	    		
	    		count ++;
			}
	    	
	    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
	    	wb.write(fileOut);
	    	fileOut.flush();
	    	fileOut.close();
	        
	        ServletOutputStream servletOutputStream;

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"VendorInvoicesTemplates.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();
			
			return null;
		}
		
		@RequestMapping(value = "/vendorInvoicePdfTemplate/vendorInvoicePdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
		public String exportPdfVendorInvoice(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
			
			
	 
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
	        
	       
			List<Object[]> invoiceTemplateEntities =vendorInvoiceService.findAllInvoices();
		
	              
	      
	        Document document = new Document();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfWriter.getInstance(document, baos);
	        document.open();
	        
	       
	        
	        PdfPTable table = new PdfPTable(6);
	        
	        Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
	        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
	        
	        
	       
	        table.addCell(new Paragraph("Vendor Contracts",font1));
	        table.addCell(new Paragraph("Invoice Number",font1));
	        table.addCell(new Paragraph("Invoice Date",font1));
	        table.addCell(new Paragraph("Invoice Title",font1));
	        table.addCell(new Paragraph("Description",font1));
	        table.addCell(new Paragraph("Status",font1));
	       
	      
	    	for (Object[] values :invoiceTemplateEntities) {
	    		
	    		
	             PdfPCell cell1 = new PdfPCell(new Paragraph((String)values[2],font3));
	        
	   
	        PdfPCell cell2 = new PdfPCell(new Paragraph((String)values[4],font3));
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    		java.util.Date invoiceDtUtil =  (java.util.Date)values[5];
			java.sql.Date invoiceDtSql = new java.sql.Date(invoiceDtUtil.getTime());
    		
	    
	        PdfPCell cell3 = new PdfPCell(new Paragraph(sdf.format(invoiceDtSql),font3));
	       
	        PdfPCell cell4 = new PdfPCell(new Paragraph((String)values[6],font3));
	   
	        PdfPCell cell5 = new PdfPCell(new Paragraph((String)values[7],font3));
	        
	      
	        PdfPCell cell6 = new PdfPCell(new Paragraph((String)values[8],font3));
	       

	        table.addCell(cell1);
	        table.addCell(cell2);
	        table.addCell(cell3);
	        table.addCell(cell4);
	        table.addCell(cell5);
	        table.addCell(cell6);
	        
	        table.setWidthPercentage(100);
	        float[] columnWidths = {10f, 10f, 10f, 10f, 10f, 10f};

	        table.setWidths(columnWidths);
	    		
			}
	        
	         document.add(table);
	        document.close();

	    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
	    	baos.writeTo(fileOut);
	    	fileOut.flush();
	    	fileOut.close();
	        
	        ServletOutputStream servletOutputStream;

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline;filename=\"VendorInvoicesTemplates.pdf"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();
			
			return null;
		}
		
		

}














