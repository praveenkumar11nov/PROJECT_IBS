package com.bcits.bfm.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jettison.json.JSONException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import com.bcits.bfm.model.Department;
import com.bcits.bfm.model.JcLabourtasks;
import com.bcits.bfm.model.JcMaterials;
import com.bcits.bfm.model.JcNotes;
import com.bcits.bfm.model.JcObjectives;
import com.bcits.bfm.model.JcTeam;
import com.bcits.bfm.model.JcTools;
import com.bcits.bfm.model.JobCards;
import com.bcits.bfm.model.JobDocuments;
import com.bcits.bfm.model.JobNotification;
import com.bcits.bfm.model.JobTypes;
import com.bcits.bfm.model.MaintainanceDepartment;
import com.bcits.bfm.model.MaintainanceTypes;
import com.bcits.bfm.model.OwnerProperty;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.StoreItemLedger;
import com.bcits.bfm.model.StoreMaster;
import com.bcits.bfm.model.ToolMaster;
import com.bcits.bfm.model.UnitOfMeasurement;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.VendorsManagement.ItemMasterService;
import com.bcits.bfm.service.VendorsManagement.UomService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.facilityManagement.AssetService;
import com.bcits.bfm.service.facilityManagement.JcLabourtasksService;
import com.bcits.bfm.service.facilityManagement.JcMaterialsService;
import com.bcits.bfm.service.facilityManagement.JcNotesService;
import com.bcits.bfm.service.facilityManagement.JcObjectivesService;
import com.bcits.bfm.service.facilityManagement.JcTeamService;
import com.bcits.bfm.service.facilityManagement.JcToolsService;
import com.bcits.bfm.service.facilityManagement.JobCalenderService;
import com.bcits.bfm.service.facilityManagement.JobCardsService;
import com.bcits.bfm.service.facilityManagement.JobDocumentsService;
import com.bcits.bfm.service.facilityManagement.JobNotificationService;
import com.bcits.bfm.service.facilityManagement.JobTypesService;
import com.bcits.bfm.service.facilityManagement.MaintainanceDepartmentService;
import com.bcits.bfm.service.facilityManagement.MaintainanceTypesService;
import com.bcits.bfm.service.facilityManagement.OwnerPropertyService;
import com.bcits.bfm.service.facilityManagement.ToolMasterService;
import com.bcits.bfm.service.inventoryManagement.StoreItemLedgerService;
import com.bcits.bfm.service.inventoryManagement.StoreMasterService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.service.userManagement.GroupsService;
import com.bcits.bfm.service.userManagement.MessagesService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.bcits.bfm.util.FilterUnit;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


@Controller
public class MaintenanceController extends FilterUnit {
	@Autowired
	MessagesService messagesService;
	@Autowired
	private JobTypesService jobTypesService;
	@Autowired
	private MaintainanceTypesService maintainanceTypesService;
	@Autowired
	private AssetService assetService;
	@Autowired
	private Validator validator;
	@Autowired
	private JobCardsService jobCardsService;
	@Autowired
	private JcObjectivesService jcObjectivesService;
	@Autowired
	private JobNotificationService jobNotificationService;
	@Autowired
	private JcNotesService jcNotesService;
	@Autowired
	private JcTeamService jcTeamService;
	@Autowired
	private JcToolsService jcToolsService;
	@Autowired
	private JcMaterialsService jcMaterialsService;
	@Autowired
	private JcLabourtasksService jcLabourtasksService;	
	@Autowired
	private ToolMasterService toolMasterService;	
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private GroupsService groupsService;
	@Autowired
	private PropertyService propertyService;	
	@Autowired
	private OwnerPropertyService ownerPropertyService;
	@Autowired
	private UsersService userService;	
	@Autowired
	private StoreItemLedgerService storeItemLedgerService;
	@Autowired
	private UomService uomService;
	@Autowired
	private ItemMasterService itemMasterService;	
	@Autowired
	private StoreMasterService storeMasterService;
	@Autowired
	private MaintainanceDepartmentService maintainanceDepartmentService;
	@Autowired
	private JobDocumentsService jobDocumentsService;
	@Autowired
	private JobCalenderService jobCalenderService;
	@Autowired
	private PersonService personService;
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();

	private static final Log logger = LogFactory.getLog(MaintenanceController.class);

	@Autowired
	private BreadCrumbTreeService breadCrumbService;

	/*----------------------------------Manage Job Types-----------------------------------*/

	@RequestMapping(value = "/jobtypes", method = RequestMethod.GET)
	public String indexjobtypes(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Maintainance");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Maintainance", 1, request);
		breadCrumbService.addNode("Manage Job Types", 2, request);
		return "maintenance/jobtypes";
	}
	@RequestMapping(value = "/maintainancedepartment", method = RequestMethod.GET)
	public String indexmaintainancedepartment(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Maintainance");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Maintainance", 1, request);
		breadCrumbService.addNode("Manage Maintainance Users", 2, request);
		return "maintenance/maintainancedepartment";
	}
	
	@RequestMapping(value = "/toolmaster", method = RequestMethod.GET)
	public String indextoolmaster(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Maintainance");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Maintainance", 1, request);
		breadCrumbService.addNode("Manage Tool Master", 2, request);
		return "maintenance/toolmaster";
	}
	

	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobtypedetails/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readjobtypedetails() {
		logger.info("Job Type Reading Records");
		List<Map<String, Object>> jobtypes = new ArrayList<Map<String, Object>>();
		for (final JobTypes record : jobTypesService.findAll()) {
			jobtypes.add(new HashMap<String, Object>() {
				{
					put("jtId", record.getJtId());
					put("jtType", record.getJtType());
					put("jtDescription", record.getJtDescription());
					put("jtSla", record.getJtSla());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					put("lastUpdatedDate",ConvertDate.TimeStampString(record.getLastUpdatedDt()));
					put("createdBy", record.getCreatedBy());
				}
			});
		}
		return jobtypes;
	}

	@RequestMapping(value = "/jobtypedetails/create", method = RequestMethod.POST)
	public @ResponseBody
	Object createjobTypes(@RequestBody Map<String, Object> map,
			@ModelAttribute JobTypes jobTypes, BindingResult bindingResult,
			HttpServletRequest request) {
		logger.info("Job Type Master Adding Records");
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("userId");
		String msg = "";
		JsonResponse errorResponse = new JsonResponse();
		List<JobTypes> ps = jobTypesService.findAll();
		Iterator<JobTypes> it = ps.iterator();
		while (it.hasNext()) {
			JobTypes pslot = it.next();
			if (pslot.getJtType().equalsIgnoreCase(map.get("jtType").toString())) {
				msg = "Exists";
			}
		}
		if (!(msg.equals(""))) {
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", "Job Type Already Exists");
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		} else {
			jobTypes = jobTypesService.getBeanObject(map);
			validator.validate(jobTypes, bindingResult);
			if (bindingResult.hasErrors()) {
				errorResponse.setStatus("FAIL");
				errorResponse.setResult(bindingResult.getAllErrors());
				return errorResponse;
			} else {
				jobTypes.setCreatedBy(userName);
				jobTypes.setLastUpdatedBy(userName);
				jobTypes = jobTypesService.save(jobTypes);
				logger.info("Job Types Adding Records Successful");
				return jobTypes;
			}
		}

	}

	@RequestMapping(value = "/jobtypedetails/update", method = RequestMethod.POST)
	public @ResponseBody
	Object updatejobTypes(@RequestBody Map<String, Object> map,
			@ModelAttribute JobTypes jobTypes, BindingResult bindingResult,
			HttpServletRequest request) {
		logger.info("Job Type Master Adding Records");
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("userId");
		String msg = "";
		JsonResponse errorResponse = new JsonResponse();
		List<JobTypes> ps = jobTypesService.findAll();
		Iterator<JobTypes> it = ps.iterator();
		while (it.hasNext()) {
			JobTypes jtype = it.next();
			if (jtype.getJtId() != (Integer.parseInt(map.get("jtId").toString()))) {
				if (jtype.getJtType().equalsIgnoreCase(map.get("jtType").toString())) {
					msg = "Exists";
				}
			}
		}
		if (!(msg.equals(""))) {
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", "Job Type Already Exists");
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		} else {
			jobTypes = jobTypesService.getBeanObject(map);
			validator.validate(jobTypes, bindingResult);
			if (bindingResult.hasErrors()) {
				errorResponse.setStatus("FAIL");
				errorResponse.setResult(bindingResult.getAllErrors());
				return errorResponse;
			} else {
				jobTypes.setJtId(Integer.parseInt(map.get("jtId").toString()));
				jobTypes.setCreatedBy((String) map.get("createdBy"));
				jobTypes.setLastUpdatedBy(userName);							
				jobTypes = jobTypesService.update(jobTypes);
				logger.info("Job Types Updating Records Successful");
				return jobTypes;
			}
		}

	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobtypedetails/destroy", method = RequestMethod.POST)
	public @ResponseBody Object destroy(@RequestBody Map<String, Object> model) {
		logger.info("Job Types Deleting Record");
		List<JobCards> jcards=jobCardsService.findByProperty("jobTypesId",Integer.parseInt(model.get("jtId").toString()));	
		
		boolean status=true;
		
		for(JobCards jobCard:jcards){
			if(!(jobCard.getStatus().equalsIgnoreCase("INIT"))){				
				status=false;
			}
		}
		
		if(status==true){
			logger.info("Job Types Deleting Record Successful");
			jobTypesService.delete(Integer.parseInt(model.get("jtId").toString()));
			return readjobtypedetails();
		}else{
			JsonResponse errorResponse = new JsonResponse();
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", "Cannot Delete Job Type,Job Under Process with This Job Type");
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		}
		
	}
	
	@RequestMapping(value = "/jobtypedetails/getJobTypeForFilter")
	public @ResponseBody Set<?> getJobTypeForFilter() {		
		Iterator<JobTypes> it=jobTypesService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getJtType());
		}
		Set<String> jobTypes=new HashSet<String>(list);
		return jobTypes;
	}
	
	@RequestMapping(value = "/jobtypedetails/getSlaForFilter")
	public @ResponseBody Set<?> getSlaForFilter() {		
		Iterator<JobTypes> it=jobTypesService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getJtSla()+"");
		}
		Set<String> jobTypes=new HashSet<String>(list);
		return jobTypes;
	}
	@RequestMapping(value = "/jobtypedetails/getCreatedByForFilter")
	public @ResponseBody Set<?> getCreatedByForFilterForJobType() {		
		Iterator<JobTypes> it=jobTypesService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getCreatedBy());
		}
		Set<String> jobTypes=new HashSet<String>(list);
		return jobTypes;
	}
	@RequestMapping(value = "/jobtypedetails/getUpdatedByForFilter")
	public @ResponseBody Set<?> getUpdatedByForFilterForJobType() {		
		Iterator<JobTypes> it=jobTypesService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getLastUpdatedBy());
		}
		Set<String> jobTypes=new HashSet<String>(list);
		return jobTypes;
	}
	
	

	/*----------------------------------Manage Maintenance Types-----------------------------------*/

	@RequestMapping(value = "/maintenancetypes", method = RequestMethod.GET)
	public String indexmaintenancetypes(ModelMap model,
			HttpServletRequest request) {
		model.addAttribute("ViewName", "Maintainance");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Maintainance", 1, request);
		breadCrumbService.addNode("Manage Maintainance Types", 2, request);
		return "maintenance/maintenancetypes";
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/maintenancetypedetails/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readmaintenancetypedetails(Model model) {
		logger.info("Maintenance Type Reading Records");
		List<Map<String, Object>> maintainanceTypes = new ArrayList<Map<String, Object>>();		
		for (final MaintainanceTypes record : maintainanceTypesService.findAll()) {
			maintainanceTypes.add(new HashMap<String, Object>() {
				{
					put("mtId", record.getMtId());
					put("maintainanceType", record.getMaintainanceType());
					put("description", record.getDescription());
					put("mtDt", ConvertDate.TimeStampString(record.getMtDt()));
					put("createdBy", record.getCreatedBy());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					if(record.getLastUpdatedDt()==null){
						put("lastUpdatedDate",ConvertDate.TimeStampString(new Timestamp(new Date().getTime())));
					}
					else{
						put("lastUpdatedDate",ConvertDate.TimeStampString(record.getLastUpdatedDt()));
					}
				}
			});
		}
		return maintainanceTypes;
	}

	@RequestMapping(value = "/maintenancetypedetails/create", method = RequestMethod.POST)
	public @ResponseBody
	Object createMaintainanceTypes(@RequestBody Map<String, Object> map,
			@ModelAttribute MaintainanceTypes MaintainanceTypes,
			BindingResult bindingResult, HttpServletRequest request) {
		logger.info("Maitenance Types Adding Records");
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("userId");
		String msg = "";
		JsonResponse errorResponse = new JsonResponse();
		List<MaintainanceTypes> mt = maintainanceTypesService.findAll();
		Iterator<MaintainanceTypes> it = mt.iterator();
		while (it.hasNext()) {
			MaintainanceTypes mtype = it.next();
			if (mtype.getMaintainanceType().equalsIgnoreCase(
					map.get("maintainanceType").toString())) {
				msg = "Exists";
			}
		}
		if (!(msg.equals(""))) {
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", "Maintenance Type Already Exists");
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		} else {
			MaintainanceTypes = maintainanceTypesService.getBeanObject(map);
			validator.validate(MaintainanceTypes, bindingResult);
			if (bindingResult.hasErrors()) {
				errorResponse.setStatus("FAIL");
				errorResponse.setResult(bindingResult.getAllErrors());
				return errorResponse;
			} else {
				MaintainanceTypes.setCreatedBy(userName);
				MaintainanceTypes.setLastUpdatedBy(userName);
				MaintainanceTypes = maintainanceTypesService
						.save(MaintainanceTypes);
				logger.info("Maitenance Types Adding Records Successful");
				return MaintainanceTypes;
			}
		}

	}

	@RequestMapping(value = "/maintenancetypedetails/update", method = RequestMethod.POST)
	public @ResponseBody
	Object updateMaintainanceTypes(@RequestBody Map<String, Object> model,
			@ModelAttribute MaintainanceTypes maintainanceTypes,
			BindingResult bindingResult, SessionStatus ss,
			HttpServletRequest request) {
		logger.info("Maitenance Types Updating Records");
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("userId");
		JsonResponse errorResponse = new JsonResponse();
		String msg = "";
		List<MaintainanceTypes> mt = maintainanceTypesService.findAll();
		Iterator<MaintainanceTypes> it = mt.iterator();
		while (it.hasNext()) {
			MaintainanceTypes mtype = it.next();
			if (mtype.getMtId() != (Integer.parseInt(model.get("mtId").toString()))) {
				if (mtype.getMaintainanceType().equals(model.get("maintainanceType"))) {
					msg = "Exists";
				}
			}
		}
		if (!(msg.equals(""))) {
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", "Maintenance Type Already Exists");
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		} else {

			maintainanceTypes = maintainanceTypesService.getBeanObject(model);
			validator.validate(maintainanceTypes, bindingResult);

			if (bindingResult.hasErrors()) {
				errorResponse.setStatus("FAIL");
				errorResponse.setResult(bindingResult.getAllErrors());
				return errorResponse;
			} else {
				maintainanceTypes.setMtId(Integer.parseInt(model.get("mtId").toString()));
				maintainanceTypes.setCreatedBy((String) model.get("createdBy"));
				maintainanceTypes.setLastUpdatedBy(userName);
				maintainanceTypes = maintainanceTypesService.update(maintainanceTypes);
				logger.info("Maitenance Types Updating Records Successful");
				return maintainanceTypes;
			}
		}

	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/maintenancetypedetails/destroy", method = RequestMethod.POST)
	public @ResponseBody
	Object destroyMaintenance(@RequestBody Map<String, Object> model) {
		logger.info("Maintenance Types Deleting Record");	
		
		List<JobCards> jcards=jobCardsService.findByProperty("maintainanceTypesId",Integer.parseInt(model.get("mtId").toString()));	
		
		boolean status=true;
		
		for(JobCards jobCard:jcards){
			if(!(jobCard.getStatus().equalsIgnoreCase("INIT"))){				
				status=false;
			}
		}
		
		if(status==true){
			maintainanceTypesService.delete(Integer.parseInt(model.get("mtId").toString()));
			logger.info("Maintenance Types Deleting Record Successful");
			return readjobtypedetails();
		}else{
			JsonResponse errorResponse = new JsonResponse();
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", "Cannot Delete Maintenance Type,Job Under Process with This Maintenance Type");
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		}
	}
	
	@RequestMapping(value = "/maintenancetypedetails/getmaintenanceTypeForFilter")
	public @ResponseBody Set<?> getmaintenanceTypeForFilter() {		
		Iterator<MaintainanceTypes> it=maintainanceTypesService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getMaintainanceType());
		}
		Set<String> maintainanceType=new HashSet<String>(list);
		return maintainanceType;
	}
	
	@RequestMapping(value = "/maintenancetypedetails/getCreatedByForFilter")
	public @ResponseBody Set<?> getmaintenanceTypeCreatedByForFilter() {		
		Iterator<MaintainanceTypes> it=maintainanceTypesService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getCreatedBy());
		}
		Set<String> maintainanceType=new HashSet<String>(list);
		return maintainanceType;
	}
	@RequestMapping(value = "/maintenancetypedetails/getUpdatedByForFilter")
	public @ResponseBody Set<?> getmaintenanceTypeUpdatedByForFilter() {		
		Iterator<MaintainanceTypes> it=maintainanceTypesService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getLastUpdatedBy());
		}
		Set<String> maintainanceType=new HashSet<String>(list);
		return maintainanceType;
	}
	
	/*----------------------------------Manage Job Cards-----------------------------------*/
	@RequestMapping(value = "/jobcards", method = RequestMethod.GET)
	public String indexjobcards(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Maintenance");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Maintenance", 1, request);
		breadCrumbService.addNode("Manage Job Cards", 2, request);
		
		return "maintenance/jobcards";
	}
	
	@RequestMapping(value = "/jobcarddetails/openjobcards", method ={ RequestMethod.GET,RequestMethod.POST})
	public String openjobcards(HttpServletRequest request) {		
		messagesService.updateReadStatus(Integer.parseInt(request.getParameter("id").toString()), 1 );
		return "redirect:/jobcards";
	}
	
	@RequestMapping(value = "/jobcardsdetails/read", method = RequestMethod.POST)
	public @ResponseBody List<?> readDataForJobCards() {
		return jobCardsService.readData();
	}
	@RequestMapping(value = "/jobcardsdetails/mobile/read/{username}", method = RequestMethod.POST)
	public @ResponseBody List<?> readDataFormobileJobCards(HttpServletRequest req,@PathVariable String username) {
		logger.info("UserName:---------"+username);
		return jobCardsService.readDataforMobile(username);
	}
	
	@RequestMapping(value = "/jobcardsdetails/create", method = RequestMethod.POST)
	public @ResponseBody Object createJobCards(@RequestBody Map<String, Object> map,@ModelAttribute("jobCards") JobCards jobCards,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) throws ParseException {
			
		HttpSession session = request.getSession(false);
		Object mobstatus;
		String userName="";
		mobstatus=map.get("mobstatus");
		if(mobstatus != null){
			
			userName=map.get("usermob").toString();
		}else{
			 userName = (String) session.getAttribute("userId");

		}
		JsonResponse errorResponse = new JsonResponse();
		
		jobCards=jobCardsService.setParameters(jobCards,map);
		validator.validate(jobCardsService, bindingResult);	
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {
			jobCards.setCreatedBy(userName);
			jobCards.setLastUpdatedBy(userName);
			jobCardsService.save(jobCards);		
			return readDataForJobCards() ;
		}		
	}
	
	@RequestMapping(value = "/jobcardsdetails/update", method = RequestMethod.POST)
	public @ResponseBody Object updateJobCards(@RequestBody Map<String, Object> map,@ModelAttribute("jobCards") JobCards jobCards,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) throws ParseException {
	
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("userId");
		JsonResponse errorResponse = new JsonResponse();
		
		jobCards=jobCardsService.setParameters(jobCards,map);
		validator.validate(jobCardsService, bindingResult);	
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {
			jobCards.setJcId(Integer.parseInt(map.get("jcId").toString()));
			jobCards.setCreatedBy(userName);
			jobCards.setLastUpdatedBy(userName);
			if(map.get("jobCalender")!=null){
				jobCards.setJobCalender(jobCalenderService.find(Integer.parseInt(map.get("jobCalender").toString())));
			
			}
			jobCardsService.update(jobCards);		
			return readDataForJobCards() ;
		}		
	}	
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobcardsdetails/deleteJobCard", method = RequestMethod.POST)
	public @ResponseBody
	Object destroyJobCards(@RequestBody Map<String, Object> map,final Locale locale) {
		logger.info("*********Job Card Id********************"+Integer.parseInt(map.get("jcId").toString()));

		JobCards jobCards=jobCardsService.find(Integer.parseInt(map.get("jcId").toString()));
		
		logger.info("*********Job Card Status********************"+jobCards.getStatus());

		JsonResponse errorResponse = new JsonResponse();

		if (!(jobCards.getStatus().equalsIgnoreCase("CLOSED") || jobCards.getStatus().equalsIgnoreCase("INIT"))) {

			
			final String st=jobCards.getStatus();
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid","Job Card Under "+st+" Status,You Cannot Delete");
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		} else {	

			logger.info("*********Job Card Status In else********************"+jobCards.getStatus());

			jobCardsService.deleteJobCard(Integer.parseInt(map.get("jcId").toString()));		
			return readDataForJobCards() ;
		}
		
	}
	
	@RequestMapping(value = "/jobcardsdetails/jobcardStatus/{jcId}/{operation}", method = RequestMethod.POST)
	public void jobcardStatus(@PathVariable int jcId,@PathVariable String operation, ModelMap model,HttpServletRequest request, HttpServletResponse response,final Locale locale) throws Exception {
		jobCardsService.setStatus(jcId,operation,response,locale);
	}	
	
	@RequestMapping(value = "/jobcardsdetails/jobcardsuspendStatus/{jcId}/{operation}", method = RequestMethod.POST)
	public void jobcardsuspendStatus(@PathVariable int jcId,@PathVariable String operation, ModelMap model,HttpServletRequest request, HttpServletResponse response,final Locale locale) throws IOException {
		jobCardsService.setsupendStatus(jcId,operation,response);
	}	
	@RequestMapping(value = "/jobcards/getAllDatailsOfJob/{jcId}", method = RequestMethod.POST)
	public void getAllDatailsOfJob(@PathVariable int jcId,HttpServletResponse response) throws IOException, JSONException {
		jobCardsService.getAllDetails(jcId,response);
		
	}	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobcardsdetails/getdepartmentname", method = RequestMethod.GET)
	public @ResponseBody List<?> getdepartmentname() {
		List<Map<String, Object>> department = new ArrayList<Map<String, Object>>();
		List<MaintainanceDepartment> record = maintainanceDepartmentService.findAll();
		for (final Iterator<?> i = record.iterator(); i.hasNext();) {
			  
			department.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();

					put("departmentName", (String)values[1]);
					put("departmentId", (Integer)values[3]);
				}
			});
		}
		return department;
	}		
	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobcardsdetails/getMaintainance", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getMaintainance() {
		List<Map<String, Object>> maintainanceTypes = new ArrayList<Map<String, Object>>();
		for (final MaintainanceTypes record : maintainanceTypesService.findAll()) {
			maintainanceTypes.add(new HashMap<String, Object>() {
				{
					put("jobMt", record.getMaintainanceType());
					put("jobMtId", record.getMtId());
				}
			});
		}
		return maintainanceTypes;
	}		
	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobcardsdetails/getPriority", method = RequestMethod.GET)
	public @ResponseBody List<?> getPriority() {
		List<String> str =new ArrayList<String>();
		str.add("LOW");
		str.add("MEDIUM");
		str.add("HIGH");
		str.add("STATUTORY");		
		List<Map<String, Object>> jobpriorities = new ArrayList<Map<String, Object>>();
		for(final String s:str){
			jobpriorities.add(new HashMap<String, Object>() {
				{
					put("priority",s);										
				}
			});
		}		
		
		return jobpriorities;
	}		
	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobcardsdetails/getJobTypes", method = RequestMethod.GET)
	public @ResponseBody List<?> getJobTypes() {
		List<Map<String, Object>> getJobTypes = new ArrayList<Map<String, Object>>();
		for (final JobTypes record : jobTypesService.findAll()) {
			getJobTypes.add(new HashMap<String, Object>() {
				{
					put("jobType", record.getJtType());
					put("jobTypeId", record.getJtId());
					put("jobSLA", record.getJtSla());
				}
			});
		}
		return getJobTypes;
	}	
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobcardsdetails/getMPerson", method = RequestMethod.GET)
	public @ResponseBody Set<?> getMPerson() {
		Set<Map<String, Object>> getmaintainaceOwner = new HashSet<Map<String, Object>>();
		List<MaintainanceDepartment> record=maintainanceDepartmentService.getMPerson();
			for (final Iterator<?> i = record.iterator(); i.hasNext();) {
				  
				getmaintainaceOwner.add(new HashMap<String, Object>() {
					{
						
					final Object[] values = (Object[])i.next();
					String str="";
					if((String)values[1]!=null){
						str=(String)values[1];
					}
					put("pn_Name", (String)values[0]+" "+str);
					put("personId", (Integer)values[2]);					
					put("personType", (String)values[3]);					
					put("departmentId", (Integer)values[4]);		
				}
			});
		}
		return getmaintainaceOwner;
	}		
	
	@RequestMapping(value = "/jobcardsdetails/getjobcardsForFilter")
	public @ResponseBody Set<?> getjobcardsForFilter() {		
		Iterator<JobCards> it=jobCardsService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getJobGeneratedby());
		}
		Set<String> jobCards=new HashSet<String>(list);
		return jobCards;
	}
	@RequestMapping(value = "/jobcardsdetails/jobNameFilterUrl")
	public @ResponseBody Set<?> jobNameFilterUrl() {		
		Iterator<JobCards> it=jobCardsService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getJobName());
		}
		Set<String> jobCards=new HashSet<String>(list);
		return jobCards;
	}
	
	@RequestMapping(value = "/jobcardsdetails/jobNoFilterUrl")
	public @ResponseBody Set<?> jobNoFilterUrl() {		
		Iterator<JobCards> it=jobCardsService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getJobNo());
		}
		Set<String> jobCards=new HashSet<String>(list);
		return jobCards;
	}
	@RequestMapping(value = "/jobcardsdetails/getCreatedByForJobCaardsFilter")
	public @ResponseBody Set<?> getCreatedByForJobCaardsFilter() {		
		Iterator<JobCards> it=jobCardsService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getCreatedBy());
		}
		Set<String> jobCards=new HashSet<String>(list);
		return jobCards;
	}
	@RequestMapping(value = "/jobcardsdetails/getUpdatedByJobCaardsForFilter")
	public @ResponseBody Set<?> getUpdatedByJobCaardsForFilter() {		
		Iterator<JobCards> it=jobCardsService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getLastUpdatedBy());
		}
		Set<String> jobCards=new HashSet<String>(list);
		return jobCards;
	}
	
	
	@RequestMapping(value = "/jobcardsdetails/jobTypeFilterUrl")
	public @ResponseBody Set<?> jobTypeFilterUrl() {		
		Iterator<JobCards> it=jobCardsService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getJobTypes().getJtType());
		}
		Set<String> jobCards=new HashSet<String>(list);
		return jobCards;
	}
	@RequestMapping(value = "/jobcardsdetails/jobMaintainanceTypeFilterUrl")
	public @ResponseBody Set<?> jobMaintainanceTypeFilterUrl() {		
		Iterator<JobCards> it=jobCardsService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getMaintainanceTypes().getMaintainanceType());
		}
		Set<String> jobCards=new HashSet<String>(list);
		return jobCards;
	}
	
	@RequestMapping(value = "/jobcardsdetails/jobSLAFilterUrl")
	public @ResponseBody Set<?> jobSLAFilterUrl() {		
		Iterator<JobCards> it=jobCardsService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getJobSla()+"");
		}
		Set<String> jobCards=new HashSet<String>(list);
		return jobCards;
	}
	@RequestMapping(value = "/jobcardsdetails/jobStatusFilterUrl")
	public @ResponseBody Set<?> jobStatusFilterUrl() {		
		Iterator<JobCards> it=jobCardsService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getStatus());
		}
		Set<String> jobCards=new HashSet<String>(list);
		return jobCards;
	}
	@RequestMapping(value = "/jobcardsdetails/jobDepartmentFilterUrl")
	public @ResponseBody Set<?> jobDepartmentFilterUrl() {		
		Iterator<JobCards> it=jobCardsService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getJobDepartment().getDept_Name());
		}
		Set<String> jobCards=new HashSet<String>(list);
		return jobCards;
	}
	@RequestMapping(value = "/jobcardsdetails/jobGroupFilterUrl")
	public @ResponseBody Set<?> jobGroupFilterUrl() {		
		Iterator<JobCards> it=jobCardsService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			list.add(it.next().getJobGroup());
		}
		Set<String> jobCards=new HashSet<String>(list);
		return jobCards;
	}
	@RequestMapping(value = "/jobcardsdetails/jobOwnersFilterUrl")
	public @ResponseBody Set<?> jobOwnersFilterUrl() {		
		Iterator<JobCards> it=jobCardsService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){
			Person p=it.next().getPerson();			

			list.add(p.getFirstName()+" "+p.getLastName());
		}
		Set<String> jobCards=new HashSet<String>(list);
		return jobCards;
	}
	@RequestMapping(value = "/jobcardsdetails/jobPriorityFilterUrl")
	public @ResponseBody Set<?> jobPriorityFilterUrl() {		
		Iterator<JobCards> it=jobCardsService.findAll().iterator();
		List<String> list=new ArrayList<String>();
		while(it.hasNext()){			
			list.add(it.next().getJobPriority());
		}
		Set<String> jobCards=new HashSet<String>(list);
		return jobCards;
	}
	
	/*----------------------------------Manage Job Objective-----------------------------------*/
	
	@RequestMapping(value = "/jobobjectivedetails/read/{jcId}", method = RequestMethod.POST)
	public @ResponseBody List<?> readDataForJobObjective(@PathVariable int jcId) {
		return jcObjectivesService.readData(jcId);
	}
	
	@RequestMapping(value = "/jobobjectivedetails/create/{jcId}", method = RequestMethod.POST)
	public @ResponseBody Object createDataForJobObjective(@RequestBody Map<String, Object> map,@PathVariable int jcId,@ModelAttribute("jobObjective") JcObjectives jobObjective,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		jobObjective=jcObjectivesService.setParameters(jcId,jobObjective,userName,map);			
		validator.validate(jobObjective, bindingResult);	
		
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {			
			jcObjectivesService.save(jobObjective);
			return jcObjectivesService.readData(jcId);
		}
		
	}
	
	@RequestMapping(value = "/jobobjectivedetails/update/{jcId}", method = RequestMethod.POST)
	public @ResponseBody Object updateDataForJobObjective(@RequestBody Map<String, Object> map,@PathVariable int jcId,@ModelAttribute("jobObjective") JcObjectives jobObjective,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		jobObjective=jcObjectivesService.setParameters(jcId,jobObjective,userName,map);
		jobObjective.setJcoId(Integer.parseInt(map.get("jcoId").toString()));
		validator.validate(jobObjective, bindingResult);	
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {
			jcObjectivesService.update(jobObjective);
			return jcObjectivesService.readData(jcId);
		}
		
	}
	
	@RequestMapping(value = "/jobobjectivedetails/jobObjectiveStatus/{jcoId}/{operation}", method = RequestMethod.POST)
	public void jobObjectiveStatus(@PathVariable int jcoId,@PathVariable String operation, ModelMap model,HttpServletRequest request, HttpServletResponse response,final Locale locale) throws IOException {
		jcObjectivesService.setStatus(jcoId,operation,response);
	}	
	
	@RequestMapping(value = "/jobNotificationdetails/jobNotificationStatus/{jnId}/{operation}", method = RequestMethod.POST)
	public void jobNotificationStatus(@PathVariable Integer jnId,@PathVariable String operation, ModelMap model,HttpServletRequest request, HttpServletResponse response,final Locale locale) throws IOException {
		jobNotificationService.setStatus(jnId,operation,response,locale);
	}	
	
	@RequestMapping(value = "/jobobjectivedetails/destroy/{jcId}", method = RequestMethod.POST)
	public @ResponseBody Object destroyJobObjective(@RequestBody Map<String, Object> model,@PathVariable int jcId) {		
		JsonResponse errorResponse = new JsonResponse();
		
		JobCards jobCards=jobCardsService.find(jcId);
		if("OPEN".equalsIgnoreCase(jobCards.getStatus()))
		{
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("cannotDelete","Job is already Opened,You can't delete Job Objective");
				}
			};
			errorResponse.setStatus("cannotDelete");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		}
		JcObjectives jcObjectives=jcObjectivesService.find(Integer.parseInt(model.get("jcoId").toString()));
		
		if (jcObjectives.getJcObjectiveAch()=="NO") {			
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid","Job Objective Is Not Achived,You Cannot Delete");
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		} else {			
			return jcObjectivesService.deleteJobObjective(jcObjectives);		
		}
		
	}
	
	/*--------------------------------Job Notification------------------------------------------*/
	
	@RequestMapping(value = "/jobobnotificationdetails/read/{jcId}", method = RequestMethod.POST)
	public @ResponseBody List<?> readDataForJobNotification(@PathVariable int jcId) {
		return jobNotificationService.readData(jcId);
	}
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobobnotificationdetails/getFlats", method = RequestMethod.GET)
	public @ResponseBody List<?> getFlats() {
		List<Map<String, Object>> flats = new ArrayList<Map<String, Object>>();
		Set<String> ids=new HashSet<>();
		for (final OwnerProperty record : ownerPropertyService.findAll()) {
			if(!(ids.contains(record.getProperty().getProperty_No()))){
				flats.add(new HashMap<String, Object>() {
					{
						put("propertyNo", record.getProperty().getProperty_No());
						put("propertyId", record.getProperty().getPropertyId());
						put("blockId", record.getProperty().getBlocks().getBlockId());					
					}
				});
				ids.add(record.getProperty().getProperty_No());
			}
			
		}
		return flats;
	}
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobobnotificationdetails/getNotificationMembers", method = RequestMethod.GET)
	public @ResponseBody List<?> getNotificationMembers() {
		List<Map<String, Object>> members = new ArrayList<Map<String, Object>>();
		Set<String> ids=new HashSet<>();
		for (final OwnerProperty record :ownerPropertyService.findAll()) {
			if(!(ids.contains(record.getOwner().getPerson().getPersonId()+""))){
				members.add(new HashMap<String, Object>() {
					{
						put("personName", record.getOwner().getPerson().getFirstName()+" "+record.getOwner().getPerson().getLastName());
						put("personId", record.getOwner().getPerson().getPersonId());
						put("propertyId", record.getProperty().getPropertyId());
					}
				});
				ids.add(record.getOwner().getPerson().getPersonId()+"");
			}
			
		}
		return members;
	}	
	
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobobnotificationdetails/getNotificationMembersGroups/{jcId}", method = RequestMethod.GET)
	public @ResponseBody List<?> getNotificationMembersGroups(@PathVariable int jcId) {
		List<Map<String, Object>> members = new ArrayList<Map<String, Object>>();
		List<JobCards> cards=new ArrayList<JobCards>(); 
		cards.add((JobCards) jobNotificationService.getAllMembersGroups(jcId));		
		for (final JobCards record :cards) {
			members.add(new HashMap<String, Object>() {
				{
					put("ownerName", record.getJobGroup());
					put("ownerId", record.getPerson().getPersonId());
				}
			});
		}
		return members;
	}
	
	@RequestMapping(value = "/jobobnotificationdetails/create/{jcId}", method = RequestMethod.POST)
	public @ResponseBody Object createDataForJobNotification(@RequestBody Map<String, Object> map,@PathVariable int jcId,@ModelAttribute("jobNotification") JobNotification jobNotification,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		jobNotification=jobNotificationService.setParameters(jcId,jobNotification,userName,map);			
		validator.validate(jobNotification, bindingResult);	
		
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {			
			jobNotificationService.save(jobNotification);
			return jobNotificationService.readData(jcId);
		}
		
	}
	
	@RequestMapping(value = "/jobobnotificationdetails/update/{jcId}", method = RequestMethod.POST)
	public @ResponseBody Object updateDataForJobNotification(@RequestBody Map<String, Object> map,@PathVariable int jcId,@ModelAttribute("jobNotification") JobNotification jobNotification,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		jobNotification=jobNotificationService.setParameters(jcId,jobNotification,userName,map);
		jobNotification.setJnId(Integer.parseInt(map.get("jnId").toString()));
		validator.validate(jobNotification, bindingResult);	
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {
			jobNotificationService.update(jobNotification);
			return jobNotificationService.readData(jcId);
		}
		
	}	
	
	@RequestMapping(value = "/jobobnotificationdetails/destroy", method = RequestMethod.POST)
	public @ResponseBody Object destroyJobNotification(@RequestBody Map<String, Object> model) {		
		
		JobNotification jobNotification=jobNotificationService.find(Integer.parseInt(model.get("jnId").toString()));
		return jobNotificationService.deleteJobObjective(jobNotification);		
		
	}
	/*--------------------------------Job Notes------------------------------------------*/
	
	@RequestMapping(value = "/jobobnotesdetails/read/{jcId}", method = RequestMethod.POST)
	public @ResponseBody List<?> readDataForJobNotes(@PathVariable int jcId) {
		return jcNotesService.readData(jcId);
	}
	
	
	@RequestMapping(value = "/jobobnotesdetails/create/{jcId}", method = RequestMethod.POST)
	public @ResponseBody Object createDataForJobNotes(@RequestBody Map<String, Object> map,@PathVariable int jcId,@ModelAttribute("jobNotes") JcNotes jobNotes,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		jobNotes=jcNotesService.setParameters(jcId,jobNotes,userName,map);			
		validator.validate(jobNotes, bindingResult);	
		
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {			
			jcNotesService.save(jobNotes);
			return jcNotesService.readData(jcId);
		}
		
	}
	
	@RequestMapping(value = "/jobobnotesdetails/update/{jcId}", method = RequestMethod.POST)
	public @ResponseBody Object updateDataForJobNotes(@RequestBody Map<String, Object> map,@PathVariable int jcId,@ModelAttribute("jobNotes") JcNotes jobNotes,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		jobNotes=jcNotesService.setParameters(jcId,jobNotes,userName,map);
		jobNotes.setJcnId(Integer.parseInt(map.get("jcnId").toString()));
		validator.validate(jobNotes, bindingResult);	
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {
			jcNotesService.update(jobNotes);
			return jcNotesService.readData(jcId);
		}
		
	}
	
	@RequestMapping(value = "/jobobnotesdetails/destroy", method = RequestMethod.POST)
	public @ResponseBody Object destroyJobNotes(@RequestBody Map<String, Object> model) {		
		JcNotes jobNotes=jcNotesService.find(Integer.parseInt(model.get("jcnId").toString()));
		return jcNotesService.delteJobNotes(jobNotes);			
		 
	}
	
	/*--------------------------------Job Teams------------------------------------------*/
	
	
	@RequestMapping(value = "/jobobteamdetails/read/{jcId}", method = RequestMethod.POST)
	public @ResponseBody List<?> readDataForJobTeam(@PathVariable int jcId) {
		return jcTeamService.readData(jcId);
	}
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobobteamdetails/getdepartmentname", method = RequestMethod.GET)
	public @ResponseBody List<?> getdepartmentnameforJob() {
		List<Map<String, Object>> department = new ArrayList<Map<String, Object>>();
		for (final Department record : departmentService.getAllActiveDepartments()) {
			department.add(new HashMap<String, Object>() {
				{
					put("departmentName", record.getDept_Name());
					put("departmentId", record.getDept_Id());
				}
			});
		}
		return department;
	}
	
	@SuppressWarnings({ "serial", "unchecked" })
	@RequestMapping(value = "/jobobteamdetails/jobTeamMembers", method = RequestMethod.GET)
	public @ResponseBody List<?> jobTeamMembers() {
		List<Map<String, Object>> persons = new ArrayList<Map<String, Object>>();
		List<Users> users=((List<Users>) userService.findAllActiveUsers());
		for (final Users record :users ) {			
			persons.add(new HashMap<String, Object>() {
				{
					put("userName", record.getPerson().getFirstName()+" "+record.getPerson().getLastName());
					put("userId", record.getPerson().getPersonId());
					put("departmentId", record.getDepartment().getDept_Id());
				}
			});
		}
		return persons;
	}	
	
	@RequestMapping(value = "/jobobteamdetails/create/{jcId}", method = RequestMethod.POST)
	public @ResponseBody Object createDataForJobTeam(@RequestBody Map<String, Object> map,@PathVariable int jcId,@ModelAttribute("jobTeam") JcTeam jobTeam,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		jobTeam=jcTeamService.setParameters(jcId,jobTeam,userName,map);			
		validator.validate(jobTeam, bindingResult);	

		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {			
			jcTeamService.save(jobTeam);
			return jcTeamService.readData(jcId);
		}
		
	}
	
	@RequestMapping(value = "/jobobteamdetails/update/{jcId}", method = RequestMethod.POST)
	public @ResponseBody Object updateDataForJobTeam(@RequestBody Map<String, Object> map,@PathVariable int jcId,@ModelAttribute("jobTeam") JcTeam jobTeam,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		jobTeam=jcTeamService.setParameters(jcId,jobTeam,userName,map);
		jobTeam.setJctId(Integer.parseInt(map.get("jctId").toString()));
		validator.validate(jobTeam, bindingResult);	
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {
			jcTeamService.update(jobTeam);
			return jcTeamService.readData(jcId);
		}
		
	}	
	
	@RequestMapping(value = "/jobobteamdetails/destroy", method = RequestMethod.POST)
	public @ResponseBody Object destroyJobTeam(@RequestBody Map<String, Object> model) {		
		JcTeam jobTeam=jcTeamService.find(Integer.parseInt(model.get("jctId").toString()));
		return jcTeamService.deleteJobTeam(jobTeam);	
		 
	}
	
	/*--------------------------------Job Tools------------------------------------------*/
	
	@RequestMapping(value = "/jobobtoolsdetails/read/{jcId}", method = RequestMethod.POST)
	public @ResponseBody List<?> readDataForJobTools(@PathVariable int jcId) {
		return jcToolsService.readData(jcId);
	}
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobobtoolsdetails/getTools", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getTools() {
		List<Map<String, Object>> toolMaster = new ArrayList<Map<String, Object>>();
		for (final ToolMaster record : toolMasterService.findAll()) {
			toolMaster.add(new HashMap<String, Object>() {
				{
					put("toolMaster", record.getTmName());
					put("toolMasterId", record.getTmId());
					put("quantity", record.getTmQuantity());
				}
			});
		}
		return toolMaster;
	}	
	
	@RequestMapping(value = "/toolMaster/getQuantityBasedOnName/{toolname}", method = RequestMethod.GET)
	public @ResponseBody Object getQuantityBasedOnName(@PathVariable String toolname) {
		return toolMasterService.getQuantityBasedOnName(toolname);
		
	}	
	
	@RequestMapping(value = "/jobobtoolsdetails/getToolsQuantity/{toolId}", method = RequestMethod.GET)
	public @ResponseBody Object getToolsQuantity(@PathVariable int toolId) {
		ToolMaster tm=toolMasterService.find(toolId);
		return tm.getTmQuantity();
	}	
	
	@RequestMapping(value = "/jobobtoolsdetails/create/{jcId}", method = RequestMethod.POST)
	public @ResponseBody Object createDataForJcTools(@RequestBody Map<String, Object> map,@PathVariable int jcId,@ModelAttribute("jcTools") JcTools jcTools,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		jcTools=jcToolsService.setParameters(jcId,jcTools,userName,map);			
		validator.validate(jcTools, bindingResult);	
	
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {			
			jcToolsService.save(jcTools);
			return jcToolsService.readData(jcId);
		}
		
	}
	
	@RequestMapping(value = "/jobobtoolsdetails/update/{jcId}", method = RequestMethod.POST)
	public @ResponseBody Object updateDataForJcTools(@RequestBody Map<String, Object> map,@PathVariable int jcId,@ModelAttribute("jcTools") JcTools jcTools,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		jcTools=jcToolsService.setParameters(jcId,jcTools,userName,map);
		jcTools.setJctoolId(Integer.parseInt(map.get("jctoolId").toString()));
		validator.validate(jcTools, bindingResult);	
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {
			jcToolsService.update(jcTools);
			return jcToolsService.readData(jcId);
		}		
	}	
	
	@RequestMapping(value = "/jobobtoolsdetails/destroy", method = RequestMethod.POST)
	public @ResponseBody Object destroyJcTools(@RequestBody Map<String, Object> model) {			
		JcTools jcTools=jcToolsService.find(Integer.parseInt(model.get("jctoolId").toString()));			
		return jcToolsService.deleteJcTools(jcTools);	
		 
	}
	
	/*--------------------------------Job Material------------------------------------------*/
	
	@RequestMapping(value = "/jobobmaterialdetails/read/{jcId}", method = RequestMethod.POST)
	public @ResponseBody List<?> readDataForJobMaterial(@PathVariable int jcId) {		
		return jcMaterialsService.readData(jcId);
	}	
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobcardsdetails/getMaterialType", method = RequestMethod.GET)
	public @ResponseBody List<?> getMaterialType() {
		List<Map<String, Object>> StoreName = new ArrayList<Map<String, Object>>();
		for (final StoreItemLedger record : storeItemLedgerService.findAllStoreItemLedgers()) {
			StoreName.add(new HashMap<String, Object>() {
				{
					put("jcmType", record.getImType());
					put("storeLedgerId", record.getSilId());					
				}
			});
		}
		return StoreName;
	}
	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobobmaterialdetails/getStoreName", method = RequestMethod.GET)
	public @ResponseBody Set<?> getStoreName() {
		Set<Map<String, Object>> StoreName = new HashSet<Map<String, Object>>();
		for (final StoreItemLedger record : storeItemLedgerService.findAllStoreItemLedgers()) {
			StoreName.add(new HashMap<String, Object>() {
				{
					put("storeMaster", record.getStoreName());
					put("storeMasterId", record.getStoreId());					
				}
			});
		}
		return StoreName;
	}
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobobmaterialdetails/getMaterialName", method = RequestMethod.GET)
	public @ResponseBody List<?> getMaterialName() {
		List<Map<String, Object>> storeItemLedger = new ArrayList<Map<String, Object>>();
		for (final StoreItemLedger record : storeItemLedgerService.findAllStoreItemLedgers()) {
			storeItemLedger.add(new HashMap<String, Object>() {
				{
					put("jcmitemName", record.getImName());
					put("jcmitemId", record.getImId());						
					put("storeMasterId", record.getStoreId());
					StoreMaster sm= storeMasterService.find(record.getStoreId());
					put("StoreName",sm.getStoreName());
					put("imType", record.getImType());
					
						
					
				}
			});
		}
		return storeItemLedger;
	}
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobobmaterialdetails/getMaterialUOM", method = RequestMethod.GET)
	public @ResponseBody List<?> getMaterialUOM() {
		List<Map<String, Object>> uom = new ArrayList<Map<String, Object>>();
		for (final UnitOfMeasurement record : uomService.findAll()) {
			uom.add(new HashMap<String, Object>() {
				{
					put("jcmimUom", record.getUom());
					put("jcmimUomId", record.getUomId());				
					put("jcmitemId", record.getItemMaster().getImId());																			
				}
			});
		}
		return uom;
	}
	
	@RequestMapping(value = "/jobobmaterialdetails/getQuantityforMaterials/{jcmitemId}/{jcmimUomId}/{storeId}", method = RequestMethod.GET)
	public @ResponseBody Object getQuantityforMaterials(@PathVariable int jcmitemId,@PathVariable int jcmimUomId,@PathVariable int storeId) {
		List<StoreItemLedger> sil=storeItemLedgerService.findByUOM(itemMasterService.find(jcmitemId),storeId);
		Iterator<StoreItemLedger> it=sil.iterator();
		String str="";
		while(it.hasNext()){
			StoreItemLedger si=it.next();
			if(si.getUnitOfMeasurement().getUomId()==jcmimUomId){
				str=si.getImBalance()+"";
			}else{
				UnitOfMeasurement uom=uomService.find(jcmimUomId);
				double uomBal;
				if(uom.getImId()==si.getImId()){
					uomBal=si.getImBalance();
					str=uom.getUomConversion()*uomBal+"";
				}
			}
			
		}
		return str;
		
	}	
	
	@RequestMapping(value = "/jobobmaterialdetails/create/{jcId}", method = RequestMethod.POST)
	public @ResponseBody Object createDataForJcMaterial(@RequestBody Map<String, Object> map,@PathVariable int jcId,@ModelAttribute("jcMaterials") JcMaterials jcMaterials,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		jcMaterials=jcMaterialsService.setParameters(jcId,jcMaterials,userName,map);			
		validator.validate(jcMaterials, bindingResult);	
	
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {			
			jcMaterialsService.save(jcMaterials);
			return jcMaterialsService.readData(jcId);
		}
		
	}
	
	@RequestMapping(value = "/jobobmaterialdetails/update/{jcId}", method = RequestMethod.POST)
	public @ResponseBody Object updateDataForJcMaterial(@RequestBody Map<String, Object> map,@PathVariable int jcId,@ModelAttribute("jcMaterials") JcMaterials jcMaterials,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {

		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		jcMaterials=jcMaterialsService.setParameters(jcId,jcMaterials,userName,map);
		jcMaterials.setJcmId(Integer.parseInt(map.get("jcmId").toString()));
		if(map.get("newStatus")!=null){
			jcMaterials.setStatus("Return");
			jcMaterials.setRerunedquantity((String) map.get("returnedQuantinty"));
		}else{
			jcMaterials.setStatus((String) map.get("status"));
		}
		validator.validate(jcMaterials, bindingResult);	
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {
			jcMaterialsService.update(jcMaterials);
			return jcMaterialsService.readData(jcId);
		}		
	}	
	
	@RequestMapping(value = "/jobobmaterialdetails/destroy", method = RequestMethod.POST)
	public @ResponseBody Object destroyJcMaterial(@RequestBody Map<String, Object> model) {			
		JcMaterials jcMaterials=jcMaterialsService.find(Integer.parseInt(model.get("jcmId").toString()));			
		return jcMaterialsService.deleteJcMaterial(jcMaterials);		
		 
	}
	
	
	/*--------------------------------Job Labour Task------------------------------------------*/
	
	@RequestMapping(value = "/joboblabourtaskdetails/read/{jcId}", method = RequestMethod.POST)
	public @ResponseBody List<?> readDataForJobLabourTask(@PathVariable int jcId) {		
		return jcLabourtasksService.readData(jcId);
	}	
	
	@RequestMapping(value = "/joboblabourtaskdetails/create/{jcId}", method = RequestMethod.POST)
	public @ResponseBody Object createDataForJcLabourtasks(@RequestBody Map<String, Object> map,@PathVariable int jcId,@ModelAttribute("jcLabourtasks") JcLabourtasks jcLabourtasks,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		jcLabourtasks=jcLabourtasksService.setParameters(jcId,jcLabourtasks,userName,map);			
		validator.validate(jcLabourtasks, bindingResult);	
	
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {			
			jcLabourtasksService.save(jcLabourtasks);
			return jcLabourtasksService.readData(jcId);
		}
		
	}
	
	@RequestMapping(value = "/joboblabourtaskdetails/update/{jcId}", method = RequestMethod.POST)
	public @ResponseBody Object updateDataForJcLabourtasks(@RequestBody Map<String, Object> map,@PathVariable int jcId,@ModelAttribute("jcLabourtasks") JcLabourtasks jcLabourtasks,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		jcLabourtasks=jcLabourtasksService.setParameters(jcId,jcLabourtasks,userName,map);
		jcLabourtasks.setJclId(Integer.parseInt(map.get("jclId").toString()));
		validator.validate(jcLabourtasks, bindingResult);	
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {
			jcLabourtasksService.update(jcLabourtasks);
			return jcLabourtasksService.readData(jcId);
		}		
	}	
	
	@RequestMapping(value = "/joboblabourtaskdetails/destroy", method = RequestMethod.POST)
	public @ResponseBody Object destroyJcLabourtasks(@RequestBody Map<String, Object> model) {			
		JcLabourtasks jcLabourtasks=jcLabourtasksService.find(Integer.parseInt(model.get("jclId").toString()));			
		return jcLabourtasksService.deleteJcLabourtasks(jcLabourtasks);		
		 
	}
	
	/*--------------------------------Tool Master------------------------------------------*/
	
	@RequestMapping(value = "/toolmasterdetails/read", method = RequestMethod.POST)
	public @ResponseBody List<?> readDataForToolMaster() {		
		return toolMasterService.readData();
	}
	
	@RequestMapping(value = "/toolmasterdetails/create", method = RequestMethod.POST)
	public @ResponseBody Object createDataForToolMaster(@RequestBody Map<String, Object> map,@ModelAttribute("toolMaster") ToolMaster toolMaster,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		toolMaster=toolMasterService.setParameters(toolMaster,userName,map);			
		validator.validate(toolMaster, bindingResult);	
	
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {			
			toolMasterService.save(toolMaster);
			return toolMasterService.readData();
		}
		
	}
	
	@RequestMapping(value = "/toolmasterdetails/update", method = RequestMethod.POST)
	public @ResponseBody Object updateDataForToolMaster(@RequestBody Map<String, Object> map,@ModelAttribute("toolMaster") ToolMaster toolMaster,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		toolMaster=toolMasterService.setParameters(toolMaster,userName,map);
		toolMaster.setTmId(Integer.parseInt(map.get("tmId").toString()));
		toolMaster.setCreatedBy((String) map.get("createdBy"));
		validator.validate(toolMaster, bindingResult);	
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {
			toolMasterService.update(toolMaster);
			return toolMasterService.readData();
		}		
	}	
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/toolmasterdetails/destroy", method = RequestMethod.POST)
	public @ResponseBody Object destroyToolMaster(@RequestBody Map<String, Object> model) {					
		
		List<JobCards> jcards=jobCardsService.findByProperty("maintainanceTypesId",Integer.parseInt(model.get("tmId").toString()));	
		
		boolean status=true;
		
		for(JobCards jobCard:jcards){
			if(!(jobCard.getStatus().equalsIgnoreCase("INIT"))){				
				status=false;
			}
		}
		
		if(status==true){
			toolMasterService.delete(Integer.parseInt(model.get("tmId").toString()));		
			return toolMasterService.readData();			
		}else{
			JsonResponse errorResponse = new JsonResponse();
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", "Cannot Delete Tools,Job Under Process with This Tool");
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		}
	}
	@RequestMapping(value = "/toolmasterdetails/getToolNameForFilter", method = RequestMethod.GET)
	public @ResponseBody Set<String> getToolNameForFilter() {			
		List<ToolMaster> toolName=toolMasterService.findAll();
		Set<String> toolNames=new HashSet<String>();
		Iterator<ToolMaster> it=toolName.iterator();
		while(it.hasNext()){
			toolNames.add(it.next().getTmName());
		}	
		return toolNames;
	}
	@RequestMapping(value = "/toolmasterdetails/getToolQuantityForFilter", method = RequestMethod.GET)
	public @ResponseBody Set<String> getToolQuantityForFilter() {			
		List<ToolMaster> toolName=toolMasterService.findAll();
		Set<String> toolNames=new HashSet<String>();
		Iterator<ToolMaster> it=toolName.iterator();
		while(it.hasNext()){
			toolNames.add(it.next().getTmQuantity()+"");
		}	
		return toolNames;
	}
	@RequestMapping(value = "/toolmasterdetails/getCreatedByForFilter", method = RequestMethod.GET)
	public @ResponseBody Set<String> getCreatedByForFilter() {			
		List<ToolMaster> toolName=toolMasterService.findAll();
		Set<String> toolNames=new HashSet<String>();
		Iterator<ToolMaster> it=toolName.iterator();
		while(it.hasNext()){
			toolNames.add(it.next().getCreatedBy());
		}	
		return toolNames;
	}
	@RequestMapping(value = "/toolmasterdetails/getUpdatedByForFilter", method = RequestMethod.GET)
	public @ResponseBody Set<String> getUpdatedByForFilter() {			
		List<ToolMaster> toolName=toolMasterService.findAll();
		Set<String> toolNames=new HashSet<String>();
		Iterator<ToolMaster> it=toolName.iterator();
		while(it.hasNext()){
			toolNames.add(it.next().getLastUpdatedBy());
		}	
		return toolNames;
	}
	
/*--------------------------------------------------	Maintainance Department----------------------------------*/
	
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/getmaintenanceusers", method = RequestMethod.POST)
	public @ResponseBody List<?> getmaintenanceusers() {
		List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
		List<MaintainanceDepartment> maintainanceDepartment=maintainanceDepartmentService.findAll();
		
		for (	final Iterator<?> i = maintainanceDepartment.iterator(); i.hasNext();) {
			users.add(new HashMap<String, Object>() {			
				{		
					final Object[] values = (Object[])i.next();
					put("urLoginName", (String)values[0]);				
					put("department", (String)values[1]);				
					put("jobtype", (String)values[2]);				
									
				}
			});
		}
		return users;
	}
	
	@RequestMapping(value = "/department/list", method = RequestMethod.GET)
	public @ResponseBody List<?> categoriesDepartment() {
		@SuppressWarnings("rawtypes")
		List dept = departmentService.findAllWithoutInvalidDefault();
		List<Department> departments=new ArrayList<Department>();
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = dept.iterator(); iterator.hasNext();) {
			Department d = (Department) iterator.next();
			if(d.getDept_Status()!="" && d.getDept_Status()!=null){
				departments.add(d);
			}			
		}		
		return departments;
	}
	
	@RequestMapping(value = "/users/listusers/{deptId}", method = RequestMethod.GET)
	public @ResponseBody List<?> listusers(@PathVariable int deptId) {	
		List<Users> usr = new ArrayList<Users>();	
		Users user = null;		
		@SuppressWarnings("rawtypes")
		List users= maintainanceDepartmentService.getAllUsers(deptId);
		for (@SuppressWarnings("rawtypes")
		Iterator i = users.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();			
			user = new Users();
			user.setUrLoginName((String)values[0]);
			user.setUrId((Integer)values[1]);
			usr.add(user);
		}	
		return usr;
		
	}
	
	@RequestMapping(value = "/maintainance/getUsers/{deptId}", method = RequestMethod.POST)
	public @ResponseBody List<Users> getUserDepartmentId(@PathVariable int deptId,HttpServletResponse response)
	{		
		List<Users> usr = new ArrayList<Users>();
		Users users = null;		
		@SuppressWarnings("rawtypes")
		List md = maintainanceDepartmentService.getExistingUsersByDepartment(deptId);	
		for (@SuppressWarnings("rawtypes")
		Iterator i = md.iterator(); i.hasNext();) 
		{
			 final Object[] values = (Object[]) i.next();			
			 users = new Users();
			 users.setUrLoginName((String)values[0]);
			 users.setUrId((Integer)values[1]);
			 usr.add(users);
		}	
		return usr;	
	}
	@RequestMapping(value = "/maintainance/updateDepartment/{urid}/{deptId}/{jobType}", method = RequestMethod.POST)
	public void updateDepartment(@PathVariable int urid,@PathVariable int deptId,@PathVariable String jobType, HttpServletResponse response) throws IOException
	{		
		
		Department dept=departmentService.find(deptId);
		Users user=userService.find(urid);		
		String str=maintainanceDepartmentService.checkUser(dept,user,jobType);
		PrintWriter out=response.getWriter();
		if(str.equalsIgnoreCase("NotExists")){
			MaintainanceDepartment md=new MaintainanceDepartment();
			md.setDepartment(dept);
			md.setUsers(user);
			md.setStatus("Active");	
			md.setJobType(jobType);
			try{
				maintainanceDepartmentService.update(md);
				out.write("success");			
			}catch(Exception e){
				out.write("Fail");
			}
		}else{
			out.write("exists");	
		}
		
		
	}
	@RequestMapping(value = "/maintainance/removeDepartment/{urid}/{deptId}", method = RequestMethod.POST)
	public void removeDepartment(@PathVariable int urid,@PathVariable int deptId,HttpServletResponse response) throws IOException
	{		
		Department dept=departmentService.find(deptId);
		Users user=userService.find(urid);			
		PrintWriter out=response.getWriter();
		try{
			MaintainanceDepartment md=maintainanceDepartmentService.findByUsersDepartment(user,dept);		
			maintainanceDepartmentService.delete(md.getMtDpIt());
			out.write("success");			
		}catch(Exception e){
			e.printStackTrace();
			e.getMessage();
			out.write("Fail");
		}
		
	}
	
	@RequestMapping(value = "/jobdocumentsdetails/read/{jcId}", method = RequestMethod.POST)
	public @ResponseBody List<?> readDataForJobDocuments(@PathVariable int jcId) {
		return jobDocumentsService.readData(jcId);
	}
	
	@RequestMapping(value = "/jobdocumentsdetails/create/{jcId}", method = RequestMethod.POST)
	public @ResponseBody Object createDataForDocuments(@PathVariable int jcId,@RequestBody Map<String, Object> map,@ModelAttribute("jobDocuments") JobDocuments jobDocuments,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		jobDocuments=jobDocumentsService.setParameters(jcId,jobDocuments,userName,map);			
		validator.validate(jobDocuments, bindingResult);	
		
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {			
			jobDocumentsService.save(jobDocuments);
			return jobDocumentsService.readData(jcId);
		}
		
	}
	@RequestMapping(value = "/jobdocumentsdetails/update/{jcId}", method = RequestMethod.POST)
	public @ResponseBody Object updateDataForDocuments(@PathVariable int jcId,@RequestBody Map<String, Object> map,@ModelAttribute("jobDocuments") JobDocuments jobDocuments,BindingResult bindingResult, SessionStatus status, HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userName=(String) session.getAttribute("userId");
		jobDocuments=jobDocumentsService.setParameters(jcId,jobDocuments,userName,map);
		jobDocuments.setJobDocId(Integer.parseInt(map.get("jobDocId").toString()));
		validator.validate(jobDocuments, bindingResult);	
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {
			jobDocumentsService.update(jobDocuments);
			return jobDocumentsService.readData(jcId);
		}		
	}
	
	@SuppressWarnings({ "deprecation", "unused" })
	@RequestMapping(value = "/jobdocuments/upload/async/save", method ={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody void save(@RequestParam List<MultipartFile> files,HttpServletRequest request) {
		// Save the files
		int jobDocId = Integer.parseInt(request.getParameter("jobDocId"));
	
		JobDocuments jdObject = null;
	     for (MultipartFile file : files){
			jdObject =  new JobDocuments();
			jdObject = jobDocumentsService.find(jobDocId);
			Blob blob;
			try{				
				blob = Hibernate.createBlob(file.getInputStream());
				jobDocumentsService.uploadImageOnId(jobDocId,blob);
			} 
			catch (IOException e){
				e.printStackTrace();
			}		
	    }		

	}
	
	@RequestMapping("/downloadJobDocument/{jobDocId}")
	public String downloadJobDocument(@PathVariable("jobDocId")	int jobDocId, HttpServletResponse response) throws Exception{
			JobDocuments jobDoc = jobDocumentsService.find(jobDocId);
			 try {
		            
		            if(jobDoc.getJobDocument() != null){
		            	response.setHeader("Content-Disposition", "inline;filename=\"" +jobDoc.getDocumentName()+ "\"");
			            OutputStream out = response.getOutputStream();
		            	response.setContentType(jobDoc.getDocumentType());
		            	IOUtils.copy(jobDoc.getJobDocument().getBinaryStream(), out);
		            	out.flush();
		            	out.close();
		            }	
		            else{
		            	return "filenotfound";
		            }		           
		        }
			 	catch (IOException e){
		            e.printStackTrace();
		        } catch (SQLException e){
		            e.printStackTrace();
		        }
			 return null;
	}

	@RequestMapping(value = "/jobdocumentsdetails/destroy", method = RequestMethod.POST)
	public @ResponseBody Object destroyjobdocuments(@RequestBody Map<String, Object> model) {			
		JobDocuments jobDocuments=jobDocumentsService.find(Integer.parseInt(model.get("jobDocId").toString()));			
		return jobDocumentsService.deleteJobDocuments(jobDocuments);		
		 
	}	
	
	@SuppressWarnings("unused")
	@Scheduled(cron="${scheduling.job.jobcards}")
	public void doSomething() {
	    List<JobCards> jobcards=jobCardsService.findAll();
	    for(JobCards jcard:jobcards){
	    	
	    	if((!jcard.getStatus().equalsIgnoreCase("CLOSED"))&&(!(jcard.getSuspendStatus().equalsIgnoreCase("SUSPEND"))) &&(!(jcard.getStatus().equalsIgnoreCase("INIT")))){
	    		int days=jcard.getJobExpectedSla();
		    	Date date=jcard.getJobStartDt();
		    	if(date!=null){
		    		Date current = new Date();  
		    		Calendar cal = Calendar.getInstance();  
		    		cal.setTime(date);  
		    		cal.set(Calendar.DAY_OF_MONTH, (cal.get(Calendar.DAY_OF_MONTH)+days));  
		    		current = cal.getTime(); 
		    		logger.info("Current time:========>"+current);
		    		long diffDays = (current.getTime()-new Date().getTime()) / (1000 * 60 * 60 * 24);
		    		logger.info(days+" Expected to Complete ");
		    		logger.info("Started Date "+date);
		    		logger.info("Completetion Date "+current);
		    		logger.info("Days Remaining "+diffDays);
		    		if(days!=1){
		    			if(diffDays<=1){
		    				Locale locale=new Locale("en", "IN");
			    			logger.info("remaining Days "+diffDays+" for the job "+jcard.getJobNo());
			    		   //jobCardsService.sendAlert(jcard,locale);
			    		}
		    		}
		    	}
	    	}
	    }
	}
	
	@RequestMapping(value = "/jobCardsTemplate/jobCardsTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileJobCards(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
       
		List<Object[]> jobCardsEntities = jobCardsService.readAllJobCards();
	
               
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
    	
    	header.createCell(0).setCellValue("Job Number");
        header.createCell(1).setCellValue("Job Name");
        header.createCell(2).setCellValue("Job Date");
        header.createCell(3).setCellValue("Job Department");
        header.createCell(4).setCellValue("Job Type");
        header.createCell(5).setCellValue("Job Owners");
        header.createCell(6).setCellValue("Maintenance");
        header.createCell(7).setCellValue("Job Priority");
        header.createCell(8).setCellValue("Job Start Date");
        header.createCell(9).setCellValue("Job End Date");
        header.createCell(10).setCellValue("Expected To Complete");
        header.createCell(11).setCellValue("Job Status");
        
        for(int j = 0; j<=11; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("K1:L200"));
        }
        
        int count = 1;
       
    	for (Object[] jobCards :jobCardsEntities ) {
    		
    		XSSFRow row = sheet.createRow(count);

    		String str="";
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
    		if((String)jobCards[2]!=null){
				str=(String)jobCards[2];
			}
            else{
            	str="";
            }
            row.createCell(0).setCellValue(str);
            if((String)jobCards[3]!=null){
				str=(String)jobCards[3];
			}
            else{
            	str="";
            }
		    row.createCell(1).setCellValue(str);
    		if((Timestamp)jobCards[5]!=null){
    			str=sdf.format((Timestamp)jobCards[5]);
			}
            else{
            	str="";
            }
    	    row.createCell(2).setCellValue(str);
    	    if((String)jobCards[6]!=null){
				str=(String)jobCards[6];
			}
            else{
            	str="";
            }
    		row.createCell(3).setCellValue(str);
    		if((String)jobCards[21]!=null){
				str=(String)jobCards[21];
			}
            else{
            	str="";
            }
    	    row.createCell(4).setCellValue(str);
    		String personName = "";
			personName = personName.concat((String)jobCards[15]);
			if((String)jobCards[16] != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)jobCards[16]);
			}
    		
    		row.createCell(5).setCellValue(personName);
    		
    		if((String)jobCards[11]!=null){
				str=(String)jobCards[11];
			}
            else{
            	str="";
            }
    	    row.createCell(6).setCellValue(str);
    	    if((String)jobCards[23]!=null){
				str=(String)jobCards[23];
			}
            else{
            	str="";
            }
    		row.createCell(7).setCellValue(str);
    		
    		if((Timestamp)jobCards[12]!=null){
				str=sdf.format((Timestamp)jobCards[12]);
			}
            else{
            	str="";
            }

            row.createCell(8).setCellValue(str);
            if((Timestamp)jobCards[13]!=null){
				str=sdf.format((Timestamp)jobCards[13]);
			}
            else{
            	str="";
            }

            row.createCell(9).setCellValue(str);
            if((Timestamp)jobCards[28]!=null){
				str=sdf.format((Timestamp)jobCards[28]);
			}
            else{
            	str="N/A";
            }

            row.createCell(10).setCellValue(str);
            if((String)jobCards[24]!=null){
				str=(String)jobCards[24];
			}
            else{
            	str="";
            }
    		row.createCell(11).setCellValue(str);
    		
    		
    		
    		
    		count ++;
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"JobCardsTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/jobCardsPdfTemplate/jobCardsPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportPdfJobCards(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
        
       
		List<Object[]> jobCardsEntities = jobCardsService.readAllJobCards();
	
              
      
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        
       
        
        PdfPTable table = new PdfPTable(12);
        
        Font font1 = new Font(Font.FontFamily.HELVETICA  , 7, Font.BOLD);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 7);
        
       
        table.addCell(new Paragraph("Job Number",font1));
        table.addCell(new Paragraph("Job Name",font1));
        table.addCell(new Paragraph("Job Date",font1));
        table.addCell(new Paragraph("Job Department",font1));
        table.addCell(new Paragraph("Job Type",font1));
        table.addCell(new Paragraph("Job Owners",font1));
        table.addCell(new Paragraph("Maintenance",font1));
        table.addCell(new Paragraph("Job Priority",font1));
        table.addCell(new Paragraph("Job Start Date",font1));
        table.addCell(new Paragraph("Job End Date",font1));
        table.addCell(new Paragraph("Expected To Complete",font1));
        table.addCell(new Paragraph("Job Status",font1));
        
    	for (Object[] jobCards :jobCardsEntities) {
    		
    		String str="";
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
    		if((String)jobCards[2]!=null){
				str=(String)jobCards[2];
			}
            else{
            	str="";
            }
    		PdfPCell cell1 = new PdfPCell(new Paragraph(str,font3));
            if((String)jobCards[3]!=null){
				str=(String)jobCards[3];
			}
            else{
            	str="";
            }
            PdfPCell cell2 = new PdfPCell(new Paragraph(str,font3));
    		if((Timestamp)jobCards[5]!=null){
    			str=sdf.format((Timestamp)jobCards[5]);
			}
            else{
            	str="";
            }
    		PdfPCell cell3 = new PdfPCell(new Paragraph(str,font3));
    	    if((String)jobCards[6]!=null){
				str=(String)jobCards[6];
			}
            else{
            	str="";
            }
    	    PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
    		if((String)jobCards[21]!=null){
				str=(String)jobCards[21];
			}
            else{
            	str="";
            }
    		PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));
    		String personName = "";
			personName = personName.concat((String)jobCards[15]);
			if((String)jobCards[16] != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)jobCards[16]);
			}
    		
			PdfPCell cell6 = new PdfPCell(new Paragraph(personName,font3));
    		
    		if((String)jobCards[11]!=null){
				str=(String)jobCards[11];
			}
            else{
            	str="";
            }
    		PdfPCell cell7 = new PdfPCell(new Paragraph(str,font3));
    	    if((String)jobCards[23]!=null){
				str=(String)jobCards[23];
			}
            else{
            	str="";
            }
    	    PdfPCell cell8 = new PdfPCell(new Paragraph(str,font3));
    		
    		if((Timestamp)jobCards[12]!=null){
				str=sdf.format((Timestamp)jobCards[12]);
			}
            else{
            	str="";
            }

    		PdfPCell cell9 = new PdfPCell(new Paragraph(str,font3));
            if((Timestamp)jobCards[13]!=null){
				str=sdf.format((Timestamp)jobCards[13]);
			}
            else{
            	str="";
            }

            PdfPCell cell10 = new PdfPCell(new Paragraph(str,font3));
            if((Timestamp)jobCards[28]!=null){
				str=sdf.format((Timestamp)jobCards[28]);
			}
            else{
            	str="N/A";
            }

            PdfPCell cell11 = new PdfPCell(new Paragraph(str,font3));
            if((String)jobCards[24]!=null){
				str=(String)jobCards[24];
			}
            else{
            	str="";
            }
            PdfPCell cell12 = new PdfPCell(new Paragraph(str,font3));
    		
    		
    		
    		
      
       

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        table.addCell(cell9);
        table.addCell(cell10);
        table.addCell(cell11);
        table.addCell(cell12);
        
        table.setWidthPercentage(100);
      /*  float[] columnWidths = {8f, 10f, 8f, 10f, 10f, 10f, 14f, 10f, 8f};

        table.setWidths(columnWidths);*/
    		
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
		response.setHeader("Content-Disposition", "inline;filename=\"JobCardsTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	
	/*-------------------------------------------------------------------------------------------------------*/	
}

