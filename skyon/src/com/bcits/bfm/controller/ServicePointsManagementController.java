package com.bcits.bfm.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.ServicePointEntity;
import com.bcits.bfm.model.ServicePointEquipmentsEntity;
import com.bcits.bfm.model.ServicePointInstructionsEntity;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillLineItemService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillParameterService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.servicePoints.ServicePointEquipmentsService;
import com.bcits.bfm.service.servicePoints.ServicePointInstructionService;
import com.bcits.bfm.service.servicePoints.ServicePointService;
import com.bcits.bfm.service.serviceRoute.ServiceRouteService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class ServicePointsManagementController {
	
	static Logger logger = LoggerFactory.getLogger(ServicePointsManagementController.class);
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();

	@Autowired
	private BreadCrumbTreeService breadCrumbService;

	@Autowired
	private ElectricityBillsService electricityBillsService;

	@Autowired
	private ElectricityBillLineItemService electricityBillLineItemService;

	@Autowired
	private ElectricityBillParameterService electricityBillParameterService;

	@Autowired
	private ServicePointService servicePointService;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ServicePointInstructionService servicePointInstructionService;

	@Autowired
	private ServicePointEquipmentsService servicePointEquipmentsService;
	
	@Autowired
	private ServiceRouteService srservice;

	@Autowired
	private Validator validator;
	
	@Autowired
	private CommonService commonService;

	@RequestMapping(value = "/servicePoints", method = RequestMethod.GET)
	public String servicePoints(ModelMap model, HttpServletRequest request) {
		logger.info("In servicePoints  Method");
		model.addAttribute("ViewName", "Service Points");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Service Points", 1, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "servicePoints/servicePoints";
	}

	@RequestMapping("/servicePoint")
	public String servicePoint(HttpServletRequest request, Model model) {
		logger.info("In service point Method");
		model.addAttribute("ViewName", "Service Point");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Service Point", 1, request);
		return "servicePoints/servicePoint";
	}

	// ****************************** ServicePoints Read,Create,Update,Delete methods ********************************//

	@RequestMapping(value = "/servicePoints/ServicePointRead/{srId}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> readServicePointData(@PathVariable int srId) 
	{
		
		logger.info("In readServicePointData Method");
		logger.info("srId:::::::::::: "+srId);
		
		List<ServicePointEntity> pointEntities = servicePointService.findALL(srId);
		
		 
		
		
		return pointEntities;
	}

	@RequestMapping(value = "/servicePoints/servicePointStatus/{servicePointId}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
	public void servicePointStatus(@PathVariable int servicePointId,@PathVariable String operation, HttpServletResponse response) {
		
		logger.info("In ServicePoint Status Change Method");
		if (operation.equalsIgnoreCase("activate"))
			servicePointService.setServicePointStatus(servicePointId,operation, response);
		else
			servicePointService.setServicePointStatus(servicePointId,operation, response);
	}

	@RequestMapping(value = "/servicePoints/ServicePointCreate/{srId}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object saveServicePoint(@ModelAttribute("pointEntity") ServicePointEntity pointEntity,BindingResult bindingResult,@PathVariable int srId, ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale) throws ParseException {

		logger.info("In ServicePoint Create Method");
		pointEntity.setStatus("Inactive");
		pointEntity.setSrId(srId);
		pointEntity.setCommissionDate(dateTimeCalender.getDateToStore(req.getParameter("commissionDate")));
		pointEntity.setDeCommissionDate(dateTimeCalender.getDateToStore(req.getParameter("deCommissionDate")));

		servicePointService.save(pointEntity);
		pointEntity.getSrId();
		return pointEntity;
	}

	@RequestMapping(value = "/servicePoints/ServicePointUpdate", method = RequestMethod.GET)
	public @ResponseBody Object editServicePointss(@ModelAttribute("servicePointEntity") ServicePointEntity servicePointEntity,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

		logger.info("In ServicePoint Update Method");
		servicePointEntity.setCommissionDate(dateTimeCalender.getDateToStore(req.getParameter("commissionDate")));
		servicePointEntity.setDeCommissionDate(dateTimeCalender.getDateToStore(req.getParameter("deCommissionDate")));
		servicePointService.update(servicePointEntity);
		return servicePointEntity;
	}

	@RequestMapping(value = "/servicePoints/ServicePointDestroy", method = RequestMethod.GET)
	public @ResponseBody
	Object deleteServicePoints(@ModelAttribute("servicePointEntity") ServicePointEntity servicePointEntity,	BindingResult bindingResult, ModelMap model, SessionStatus ss) {

		logger.info("In ServicePoint Destroy Method");
		JsonResponse errorResponse = new JsonResponse();
		final Locale locale = null;
		if(servicePointEntity.getStatus().equalsIgnoreCase("Active") )
		{
			HashMap<String, String> errorMapResponse = new HashMap<String, String>()
			{
			  /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
				put("AciveServicePointDestroyError", messageSource.getMessage("ServicePointEntity.AciveServicePointDestroyError", null, locale));
			  }
			};
			errorResponse.setStatus("AciveServicePointDestroyError");
			errorResponse.setResult(errorMapResponse);
			
			return errorResponse;				
		}else{
		try {
			servicePointService.delete(servicePointEntity.getServicePointId());
		} catch (Exception e) {
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return servicePointEntity;
		}
	}

	// ****************************** ServicePoint Filters Data methods ********************************/

	@RequestMapping(value = "/servicePoints/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody Set<?> getServicePointContentsForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("ServicePointEntity",attributeList, null));
		
		return set;
	}

	@RequestMapping(value = "/servicePoints/getPropertyNumList", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getPropertyNumber() {
		return servicePointService.getAllPropertyNumbers();
	}

	@RequestMapping(value = "/servicePoints/getBlockNamesList", method = RequestMethod.GET)
	public @ResponseBody List<String> getBlockNames() {
		return servicePointService.getAllBlockNames();
	}

	// ****************************** ServicePointInstructions Read,Create,Update,Delete methods ********************************//

	@RequestMapping(value = "/servicePoints/instructionRead/{srId}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<ServicePointInstructionsEntity> readServicePointInstructions(@PathVariable int srId) {
		logger.info("In Service Point Instruction read Method");
		List<ServicePointInstructionsEntity> servicePointInstructionsEntities = servicePointInstructionService.findAllById(srId);
		
		return servicePointInstructionsEntities;
	}

	@RequestMapping(value = "/servicePoints/instructionCreate/{srId}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object saveServicePointInstructions(@ModelAttribute("pointInstructionsEntity") ServicePointInstructionsEntity pointInstructionsEntity,BindingResult result, @PathVariable int srId,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale)throws ParseException {

		logger.info("In Service Point Instruction Create Method");
		pointInstructionsEntity.setStatus("Inactive");
		//pointInstructionsEntity.setInstructionDate(dateTimeCalender.getDateToStore(req.getParameter("instructionDate")));
		//Timestamp instructionDate = new Timestamp(new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(req.getParameter("instructionDate")).getTime());
		pointInstructionsEntity.setInstructionDate(dateTimeCalender.getDateToStore(req.getParameter("instructionDate")));
		pointInstructionsEntity.setSrId(srId);
		
		servicePointInstructionService.save(pointInstructionsEntity);
		return pointInstructionsEntity;
	}

	@RequestMapping(value = "/servicePoints/instructionUpdate/{srId}", method = RequestMethod.GET)
	public @ResponseBody Object editInstructions(@ModelAttribute("pointInstructionsEntity") ServicePointInstructionsEntity pointInstructionsEntity,BindingResult bindingResult, @PathVariable int srId, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

		logger.info("In Service Point Instruction Update Method");
		//pointInstructionsEntity.setInstructionDate(dateTimeCalender.getDateToStore(req.getParameter("instructionDate")));
		//Timestamp instructionDate = new Timestamp(new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(req.getParameter("instructionDate")).getTime());
		pointInstructionsEntity.setInstructionDate(dateTimeCalender.getDateToStore(req.getParameter("instructionDate")));
		
		/*pointInstructionsEntity.setSrId(srId);*/
		
		servicePointInstructionService.update(pointInstructionsEntity);
		return pointInstructionsEntity;
	}

	@RequestMapping(value = "/servicePoints/instructionDestroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteRateSlab(@ModelAttribute("pointInstructionsEntity") ServicePointInstructionsEntity pointInstructionsEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss) {

		logger.info("In Service Point Instruction Destroy Method");
		JsonResponse errorResponse = new JsonResponse();
		final Locale locale = null;
		if(pointInstructionsEntity.getStatus().equalsIgnoreCase("Active") )
		{
			HashMap<String, String> errorMapResponse = new HashMap<String, String>()
			{
			  /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
				put("AciveInstructionDestroyError", messageSource.getMessage("ServicePointInstructionsEntity.AciveInstructionDestroyError", null, locale));
			  }
			};
			errorResponse.setStatus("AciveInstructionDestroyError");
			errorResponse.setResult(errorMapResponse);
			
			return errorResponse;				
		}else{
		try {
			servicePointInstructionService.delete(pointInstructionsEntity.getSpInstructionId());
		} catch (Exception e) {
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return pointInstructionsEntity;
		}
	}
	
	@RequestMapping(value = "/servicePoints/instructionUpdateFromInnerGrid/{spInstructionId}", method = { RequestMethod.GET, RequestMethod.POST })
	public void instructionStatusUpdateFromInnerGrid(@PathVariable int spInstructionId, HttpServletResponse response)
	{		
		servicePointInstructionService.updateInstructionStatusFromInnerGrid(spInstructionId, response);
	}

	// ****************************** Instruction Filter Data methods ********************************/

	@RequestMapping(value = "/instructions/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody Set<?> getInstructionContentsForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("ServicePointInstructionsEntity", attributeList, null));

		return set;
	}

	// ****************************** ServicePointEquipments Read,Create,Update,Delete methods ********************************//

	@RequestMapping(value = "/servicePoints/equipmentRead/{servicePointId}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<ServicePointEquipmentsEntity> readServicePointEquipmentss(	@PathVariable int servicePointId) {
		logger.info("In Service Point Equipment Read Method");
		List<ServicePointEquipmentsEntity> pointEquipmentsEntities = servicePointEquipmentsService.findAllById(servicePointId);		
		return pointEquipmentsEntities;
	}

	@RequestMapping(value = "/servicePoints/equipmentCreate/{servicePointId}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object saveServicePointEquipments(@ModelAttribute("servicePointEquipmentsEntity") ServicePointEquipmentsEntity servicePointEquipmentsEntity,BindingResult bindingResult, @PathVariable int servicePointId,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale) throws ParseException {

		logger.info("In Service Point Equipment Create Method");
		servicePointEquipmentsEntity.setStatus("Inactive");
		//servicePointEquipmentsEntity.setServicePointId(servicePointId);
		//servicePointEquipmentsEntity.setInstallDate(dateTimeCalender.getDateToStore(req.getParameter("installDate")));
		//Timestamp installDate = new Timestamp(new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(req.getParameter("installDate")).getTime());
		servicePointEquipmentsEntity.setInstallDate(dateTimeCalender.getDateToStore(req.getParameter("installDate")));
		
		//servicePointEquipmentsEntity.setRemovalDate(dateTimeCalender.getDateToStore(req.getParameter("removalDate")));
		if(req.getParameter("removalDate").equals("")){
			servicePointEquipmentsEntity.setRemovalDate(null);
		}else{
		//Timestamp removalDate = new Timestamp(new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(req.getParameter("removalDate")).getTime());
		servicePointEquipmentsEntity.setRemovalDate(dateTimeCalender.getDateToStore(req.getParameter("removalDate")));
		}
		
		servicePointEquipmentsEntity.setServicePointEntity(servicePointService.find(servicePointId));

		servicePointEquipmentsService.save(servicePointEquipmentsEntity);
		return servicePointEquipmentsEntity;
	}

	@RequestMapping(value = "/servicePoints/equipmentUpdate/{servicePointId}", method = RequestMethod.GET)
	public @ResponseBody Object editEquipments(@ModelAttribute("pointInstructionsEntity") ServicePointEquipmentsEntity pointEquipmentsEntity,BindingResult bindingResult, @PathVariable int servicePointId, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

		logger.info("In Service Point Equipment Update Method");
		//pointEquipmentsEntity.setInstallDate(dateTimeCalender.getDateToStore(req.getParameter("installDate")));
		//Timestamp installDate = new Timestamp(new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(req.getParameter("installDate")).getTime());
		pointEquipmentsEntity.setInstallDate(dateTimeCalender.getDateToStore(req.getParameter("installDate")));
		
		//pointEquipmentsEntity.setRemovalDate(dateTimeCalender.getDateToStore(req.getParameter("removalDate")));
		//Timestamp removalDate = new Timestamp(new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(req.getParameter("removalDate")).getTime());
		pointEquipmentsEntity.setRemovalDate(dateTimeCalender.getDateToStore(req.getParameter("removalDate")));
		
		pointEquipmentsEntity.setServicePointEntity(servicePointService.find(servicePointId));
		
		servicePointEquipmentsService.update(pointEquipmentsEntity);
		
		return pointEquipmentsEntity;
	}

	@RequestMapping(value = "/servicePoints/equipmentDestroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteRateSlab(@ModelAttribute("pointEquipmentsEntity") ServicePointEquipmentsEntity pointEquipmentsEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss) {

		logger.info("In Service Point Equipment Destroy Method");
		JsonResponse errorResponse = new JsonResponse();
		final Locale locale = null;
		if(pointEquipmentsEntity.getStatus().equalsIgnoreCase("Active") )
		{
			HashMap<String, String> errorMapResponse = new HashMap<String, String>()
			{
			  /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
				put("AciveEquipmentDestroyError", messageSource.getMessage("ServicePointEquipmentsEntity.AciveEquipmentDestroyError", null, locale));
			  }
			};
			errorResponse.setStatus("AciveEquipmentDestroyError");
			errorResponse.setResult(errorMapResponse);
			
			return errorResponse;				
		}else{
		try {
			servicePointEquipmentsService.delete(pointEquipmentsEntity.getSpEquipmentId());
		} catch (Exception e) {
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return pointEquipmentsEntity;
		}
	}
	
	@RequestMapping(value = "/servicePoints/equipmentUpdateFromInnerGrid/{spEquipmentId}", method = { RequestMethod.GET, RequestMethod.POST })
	public void equipmentStatusUpdateFromInnerGrid(@PathVariable int spEquipmentId, HttpServletResponse response)
	{		
		servicePointEquipmentsService.updateEquipmentStatusFromInnerGrid(spEquipmentId, response);
	}

	// ****************************** Equipment Filter Data methods ********************************/

	@RequestMapping(value = "/equipments/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody Set<?> getEquipmentContentsForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(
				commonService.selectQuery("ServicePointEquipmentsEntity", attributeList, null));

		return set;
	}

	@RequestMapping(value = "/equipments/getEquipmentTypeList", method = RequestMethod.GET)
	public @ResponseBody List<?> getPersonIndividualsList() {

		List<String> personNamesList = servicePointEquipmentsService.getAllEquipmentTypes();
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (Iterator<String> i = personNamesList.iterator(); i.hasNext();) {
			final String values = i.next();
			String equipmentType = "";
			equipmentType = equipmentType.concat((String) values);
			map = new HashMap<String, String>();
			map.put("name", equipmentType);
			map.put("value", equipmentType);
			result.add(map);
		}
		return result;
	}
}
