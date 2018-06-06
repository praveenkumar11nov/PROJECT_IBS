/*package com.bcits.bfm.controller;

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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.bms.service.TrendLogService;
import com.bcits.bfm.bms.service.TrendLogValueService;
import com.bcits.bfm.model.BMSNotificationEntity;
import com.bcits.bfm.model.BMSSettingsEntity;
import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Designation;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.BMSNotificationService;
import com.bcits.bfm.service.BMSSettingsService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.BlocksService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.service.userManagement.DesignationService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.util.EmailCredentialsDetailsBean;
import com.bcits.bfm.util.SendMailForStaff;
import com.bcits.bfm.util.SendSMSForStatus;
import com.bcits.bfm.util.SmsCredentialsDetailsBean;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class BMSController {

	static Logger logger = LoggerFactory.getLogger(BMSController.class);

	
	@Resource
	private BreadCrumbTreeService breadCrumbService;

	@Autowired
	private CommonController commonController;
	
	
	
	@Autowired
	private BMSSettingsService bmsSettingsService;
	
	@Autowired
	private DesignationService designationService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private BlocksService blockService;
	
	@Autowired
	private TrendLogService trendLogService;
	
	@Autowired
	BMSNotificationService bmsNotificationService;

	@PersistenceContext(unitName="BMS")
	protected EntityManager entityManager;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private TrendLogValueService trendLogValueService;
	
	@Autowired
	private PersonService personService;
	
	static String months=null;
	
	
	
	String treatedWaterTankLevel = ResourceBundle.getBundle("application").getString("treatedWaterTankLevel");
	String softWaterTankLevel = ResourceBundle.getBundle("application").getString("softWaterTankLevel");
	String softWaterPHMonitoring = ResourceBundle.getBundle("application").getString("softWaterPHMonitoring");
	String softWaterChlorineMonitoring = ResourceBundle.getBundle("application").getString("softWaterChlorineMonitoring");
	String hydrantPumpStatus = ResourceBundle.getBundle("application").getString("hydrantPumpStatus");
	String sprinklerPumpStatus = ResourceBundle.getBundle("application").getString("sprinklerPumpStatus");
	String jockeyPumpStatus = ResourceBundle.getBundle("application").getString("jockeyPumpStatus");
	String dieselPumpStatus = ResourceBundle.getBundle("application").getString("dieselPumpStatus");
	String fanStatus = ResourceBundle.getBundle("application").getString("fanStatus");
	String liftElevatorStatus = ResourceBundle.getBundle("application").getString("liftElevatorStatus");
	String liftElevatorFaultAlarm = ResourceBundle.getBundle("application").getString("liftElevatorFaultAlarm");
	String dgSetStatus = ResourceBundle.getBundle("application").getString("dgSetStatus");
	String dgSetTripAlarm = ResourceBundle.getBundle("application").getString("dgSetTripAlarm");
	String dgBatteryStatus = ResourceBundle.getBundle("application").getString("dgBatteryStatus");
	String hydroPneumaticPump = ResourceBundle.getBundle("application").getString("hydroPneumaticPump");
	
	//Home Page of BMS DashBoard
	@RequestMapping(value = "/bmsDashboard", method = RequestMethod.GET)
	public String bmsDashboard(ModelMap model, HttpServletRequest request, final Locale locale) {
		model.addAttribute("ViewName", "Energy Dashboard ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Energy Dashboard", 1, request);
		breadCrumbService.addNode("Manage BMS Dashboard", 2, request);
		
			
			Date date=new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int month1 = cal.get(Calendar.MONTH);
			int montOne = month1 + 1;
			int year = cal.get(Calendar.YEAR);
			int day = cal.get(Calendar.DAY_OF_MONTH);
		
			List<?> trendLogs=bmsSettingsService.findTrendLogIds();
			
			if(!(trendLogs.isEmpty())){
			for (final Iterator<?> i = trendLogs.iterator(); i.hasNext();) {
				final Object[] values = (Object[])i.next();
				int trendLogId=(Integer)values[0];
				String trendLogName=(String)values[1];
				logger.info("trendLogId"+trendLogId);
				
				List<?> checkData=trendLogValueService.getMaxTimeStampBasedOnCurrentData(montOne,year,day,trendLogId);
				if(!(checkData.isEmpty())){
				Double logValue=(Double)entityManager.createQuery("SELECT tlv.logValue from TrendLogValue tlv WHERE tlv.trendLogId="+trendLogId+" AND tlv.logTime='"+(Timestamp)checkData.get(0)+"'").getSingleResult();
				if(treatedWaterTankLevel.equalsIgnoreCase(trendLogName)){
					if(logValue!=null){
						if ((double)logValue> 0) {
							model.addAttribute("treatWaterTankLevel","HIGH");
						} else {
							model.addAttribute("treatWaterTankLevel","LOW");
						}
						
					}
				}
				if(softWaterTankLevel.equalsIgnoreCase(trendLogName)){
					if(logValue!=null){
						if ((double)logValue> 0) {
							model.addAttribute("softWaterTankLevel","HIGH");
						} else {
							model.addAttribute("softWaterTankLevel","LOW");
						}
						
					}
				}
				if(softWaterPHMonitoring.equalsIgnoreCase(trendLogName)){
					if(logValue!=null){
						if ((double)logValue> 0) {
							model.addAttribute("softWaterPHMonitoring","HIGH");
						} else {
							model.addAttribute("softWaterTankLevel","LOW");
						}
						
					}
				}
				if(softWaterChlorineMonitoring.equalsIgnoreCase(trendLogName)){
					if(logValue!=null){
						if ((double)logValue> 0) {
							model.addAttribute("softWaterChlorineMonitoring","HIGH");
						} else {
							model.addAttribute("softWaterChlorineMonitoring","LOW");
						}
						
					}
				}
				if(hydrantPumpStatus.equalsIgnoreCase(trendLogName)){
					if(logValue!=null){
						if ((double)logValue> 0) {
							model.addAttribute("hydrantPumpStatus","ON");
						} else {
							model.addAttribute("hydrantPumpStatus","OFF");
						}
						
					}
				}
				if(sprinklerPumpStatus.equalsIgnoreCase(trendLogName)){
					if(logValue!=null){
						if ((double)logValue> 0) {
							model.addAttribute("sprinklerPumpStatus","ON");
						} else {
							model.addAttribute("sprinklerPumpStatus","OFF");
						}
						
					}
				}
				if(jockeyPumpStatus.equalsIgnoreCase(trendLogName)){
					if(logValue!=null){
						if ((double)logValue> 0) {
							model.addAttribute("jockeyPumpStatus","ON");
						} else {
							model.addAttribute("jockeyPumpStatus","OFF");
						}
						
					}
				}
				if(dieselPumpStatus.equalsIgnoreCase(trendLogName)){
					if(logValue!=null){
						if ((double)logValue> 0) {
							model.addAttribute("dieselPumpStatus","ON");
						} else {
							model.addAttribute("dieselPumpStatus","OFF");
						}
						
					}
				}
				if(fanStatus.equalsIgnoreCase(trendLogName)){
					if(logValue!=null){
						if ((double)logValue> 0) {
							model.addAttribute("fanStatus","ON");
						} else {
							model.addAttribute("fanStatus","OFF");
						}
						
					}
				}
				if(liftElevatorStatus.equalsIgnoreCase(trendLogName)){
					if(logValue!=null){
						if ((double)logValue> 0) {
							model.addAttribute("liftElevatorStatus","RUNNING");
						} else {
							model.addAttribute("liftElevatorStatus","STAND BY");
						}
						
					}
				}
				if(liftElevatorFaultAlarm.equalsIgnoreCase(trendLogName)){
					if(logValue!=null){
						if ((double)logValue> 0) {
							model.addAttribute("liftElevatorFaultAlarm","ON");
						} else {
							model.addAttribute("liftElevatorFaultAlarm","OFF");
						}
						
					}
				}
				if(dgSetTripAlarm.equalsIgnoreCase(trendLogName)){
					if(logValue!=null){
						if ((double)logValue> 0) {
							model.addAttribute("DGSetTripAlarm","ON");
						} else {
							model.addAttribute("DGSetTripAlarm","OFF");
						}
						
					}
				}
				if(dgBatteryStatus.equalsIgnoreCase(trendLogName)){
					if(logValue!=null){
						if ((double)logValue> 0) {
							model.addAttribute("DGBatteryStatus","ON");
						} else {
							model.addAttribute("DGBatteryStatus","OFF");
						}
						
					}
				}
				if(dgSetStatus.equalsIgnoreCase(trendLogName)){
					if(logValue!=null){
						if ((double)logValue> 0) {
							model.addAttribute("DGSetStatus","ON");
						} else {
							model.addAttribute("DGSetStatus","OFF");
						}
						
					}
				}
				if(hydroPneumaticPump.equalsIgnoreCase(trendLogName)){
					if(logValue!=null){
						if ((double)logValue> 0) {
							model.addAttribute("hydroPneumaticPumpStatus","ON");
						} else {
							model.addAttribute("hydroPneumaticPumpStatus","OFF");
						}
						
					}
				}
				
				
				}

			}
		}
		
		return "bms/bmsDashboard";
	}
	
	
	//Home Page for BMS settings
	@RequestMapping(value = "/bmsSettings", method = RequestMethod.GET)
	public String bmsSettings(ModelMap model, HttpServletRequest request, final Locale locale) {
		model.addAttribute("ViewName", "BMS Settings ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Energy Dashboard", 1, request);
		breadCrumbService.addNode("Manage BMS Settings", 2, request);
	
		return "bms/bmsSettings";
	}

	//get Department
	@RequestMapping(value = "/bmsSettings/getDepartment", method = RequestMethod.GET)
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
 	       	result.add(deptMap);
 	     }
        return result;
	}
	
	//Get Designation
	@SuppressWarnings("serial")
	@RequestMapping(value = "/bmsSettings/getDesignation", method = RequestMethod.GET)
	public @ResponseBody List<?> getDesignation() 
	{
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
	        for (final Designation record : designationService.getAllActiveDesignations()) {
	            result.add(new HashMap<String, Object>() {{
            	put("dn_Name", record.getDn_Name());
	            put("dept_Id", record.getDept_Id());
	            put("dept_Name", record.getDepartment().getDept_Name());
	            put("dn_Id", record.getDn_Id());
	            }});
	        }
	        return result;
	}
	
	//Read BMS settings
	@RequestMapping(value = "/bmsSettings/readUrl", method = RequestMethod.POST)
	public @ResponseBody List<?> readBMSSettingsData(final Locale locale) {
		return bmsSettingsService.readBMSSettingsData();			 
	}

	//Create BMS Settings
	@RequestMapping(value = "/bmsSettings/modifyBMSSettingsUrl/{operation}", method = {RequestMethod.POST ,RequestMethod.GET})
	public @ResponseBody Object modifyBMSSettingsUrl(@RequestBody Map<String, Object> map,
			HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("bmsSettingsEntity") BMSSettingsEntity bmsSettingsEntity,
			BindingResult bindingResult, ModelMap model,
			SessionStatus sessionStatus, final Locale locale,@PathVariable String operation){
		
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("userId");
		if(operation.equalsIgnoreCase("create"))
		{
			
			bmsSettingsEntity.setCreatedBy(userName);
			bmsSettingsEntity.setLastUpdatedBy(userName);
			bmsSettingsEntity.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));

			
			bmsSettingsEntity.setDeptId(Integer.parseInt(map.get("dept_Name").toString()));
			bmsSettingsEntity.setDnId(Integer.parseInt(map.get("dn_Name").toString()));
			bmsSettingsEntity.setBmsElements((String) map.get("bmsElements"));
			bmsSettingsEntity.setParamValue(Integer.parseInt(map.get("paramValue").toString()));
			bmsSettingsEntity.setStatus("Inactive");
		
			bmsSettingsService.save(bmsSettingsEntity);
			return bmsSettingsEntity;
		}
		else{
			bmsSettingsEntity.setBmsSettingsId(Integer.parseInt(map.get("bmsSettingsId").toString()));
			bmsSettingsEntity.setCreatedBy(userName);
			bmsSettingsEntity.setLastUpdatedBy(userName);
			bmsSettingsEntity.setBmsElements((String) map.get("bmsElements"));
			bmsSettingsEntity.setParamValue(Integer.parseInt(map.get("paramValue").toString()));
			bmsSettingsEntity.setStatus((String) map.get("status"));
			bmsSettingsEntity.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));

			
			if(map.get("dept_Name") instanceof String){
				bmsSettingsEntity.setDeptId(Integer.parseInt(map.get("dept_Id").toString()));
			}else{
				bmsSettingsEntity.setDeptId(Integer.parseInt(map.get("dept_Name").toString()));
			}
			
			if(map.get("dn_Name") instanceof String){
				bmsSettingsEntity.setDnId(Integer.parseInt(map.get("dn_Id").toString()));
			}else{
				bmsSettingsEntity.setDnId(Integer.parseInt(map.get("dn_Name").toString()));
			}
			
			bmsSettingsService.update(bmsSettingsEntity);
			return bmsSettingsEntity;
		}	
	}
	
	//Activation of BMS Settings
	@RequestMapping(value = "/bmsSettings/bmsSettingsStatus/{bmsSettingsId}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
	public void bmsSettingsStatus(@PathVariable int bmsSettingsId, @PathVariable String operation, HttpServletResponse response)
	{		
		logger.info("BMS Settings Activate and Deactivate method");
		logger.info("The operateion is :::::::::::::::: "+operation+" With Id ::::::::::: "+bmsSettingsId);
		
		if(operation.equalsIgnoreCase("activate"))
			bmsSettingsService.setBMSSettingsStatus(bmsSettingsId, operation, response);

		else
			bmsSettingsService.setBMSSettingsStatus(bmsSettingsId, operation, response);
	}
	
	//Filter for Department
	@RequestMapping(value = "/bmsSettings/departmentFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<String> departmentFilterUrl() {	
		List<?> bmsSettingsList = bmsSettingsService.findAllFilter();
		Set<String> result = new HashSet<String>();		
		for (Iterator<?> i = bmsSettingsList.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			result.add((String)values[2]);		
		}
		return result;
	}
	//Filter for Designation
	@RequestMapping(value = "/bmsSettings/designationFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<String> designationFilterUrl() {	
		List<?> bmsSettingsList = bmsSettingsService.findAllFilter();
		Set<String> result = new HashSet<String>();		
		for (Iterator<?> i = bmsSettingsList.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			result.add((String)values[4]);		
		}
		return result;		
		
	}
	////Filter for String Fields
	@RequestMapping(value = "/bmsSettings/commonFilterUrl/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getContentsForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set=new HashSet<Object>(bmsSettingsService.selectQuery("BMSSettingsEntity", attributeList, null));
		
		return set;
	}
	
	//Uniqeness for Trend Log Names
	@RequestMapping(value = "/bmsSettings/readTrendLogNamesUniqe", method = RequestMethod.GET)
	public @ResponseBody List<?> readTrendLogNamesUniqe() {	
		
	return bmsSettingsService.readTrendLogNamesUniqe();
	}
	
	//Uniqeness for Trend Log Ids
		@RequestMapping(value = "/bmsSettings/readTrendLogIdsUniqe", method = RequestMethod.GET)
		public @ResponseBody List<?> readTrendLogIdsUniqe() {	
			
		return bmsSettingsService.readTrendLogIdsUniqe();
		}
	
	//Home Page for BMS Notification Master	
	@RequestMapping(value = "/bmsNotificationMaster", method = RequestMethod.GET)
	public String bmsNotificationMaster(ModelMap model, HttpServletRequest request, final Locale locale) {
		model.addAttribute("ViewName", "BMS Notification");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Energy Dashboard", 1, request);
		breadCrumbService.addNode("Manage BMS Notification", 2, request);
	
		return "bms/bmsNotificationMaster";
	}
	
	//Read BMS Notifications
	@RequestMapping(value = "/bmsNotificationMaster/readUrl", method = RequestMethod.POST)
	public @ResponseBody List<?> readBMSNotificationData(final Locale locale) {
		
		return bmsNotificationService.readBMSNotificationData();
	}
	
	@RequestMapping("/bmsDashboard/ventillationContainer")
	public @ResponseBody Object ventillationContainerData(HttpServletRequest request){		
		List<Object> res=new ArrayList<>();
		Date date=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month1 = cal.get(Calendar.MONTH);
		int montOne = month1 + 1;
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String currDay=cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
		List<BMSSettingsEntity>  ventillation=bmsSettingsService.getVentillationId();
		if(!(ventillation.isEmpty())){
		List<?> ventiData1=entityManager.createQuery("SELECT MAX(tlv.logValue) FROM TrendLogValue tlv  WHERE EXTRACT(month FROM tlv.logTime) ="+ montOne+" AND EXTRACT(year FROM tlv.logTime) ="+ year+" AND EXTRACT(day FROM tlv.logTime) ="+ day+" AND tlv.trendLogId = "+ventillation.get(0).getParamValue()+" GROUP BY tlv.logValue").getResultList();
		if(!(ventiData1.isEmpty())){
		res.add(currDay);
		res.add(ventiData1.get(0));
		}
		else{
		res.add(currDay);
		res.add(0.0);
		}
		for (int i=0;i<=5;i++){
		    cal.add(Calendar.DAY_OF_MONTH,-1);
		    int months = cal.get(Calendar.MONTH);
			int montOnes = months + 1;
			int years = cal.get(Calendar.YEAR);
			int days = cal.get(Calendar.DAY_OF_MONTH);
			String currenDay = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
			List<?> ventiData=entityManager.createQuery("SELECT MAX(tlv.logValue) FROM TrendLogValue tlv  WHERE EXTRACT(month FROM tlv.logTime) ="+ montOnes+" AND EXTRACT(year FROM tlv.logTime) ="+ years+" AND EXTRACT(day FROM tlv.logTime) ="+ days+" AND tlv.trendLogId = "+ventillation.get(0).getParamValue()+" GROUP BY tlv.logValue").getResultList();
			if(!(ventiData.isEmpty())){
			res.add(currenDay);
			res.add(ventiData.get(0));
			}
			else{
			res.add(currenDay);
			res.add(0.0);
			}
		}
	}
		return res;
	}
	
	@RequestMapping("/bmsDashboard/waterDistribution")
	public @ResponseBody Object getMasterDetails(HttpServletRequest request){
	
		List<Object> res=new ArrayList<>();
		Date date=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month1 = cal.get(Calendar.MONTH);
		int montOne = month1 + 1;
		int year = cal.get(Calendar.YEAR);
	    int day = cal.get(Calendar.DAY_OF_MONTH);
		String currDay=cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
		List<BMSSettingsEntity> hydroPneumaticPump=bmsSettingsService.getHydroPneumaticPumpId();
		if(!(hydroPneumaticPump.isEmpty())){
		List<?> waterDistData1=entityManager.createQuery("SELECT MAX(tlv.logValue) FROM TrendLogValue tlv  WHERE EXTRACT(month FROM tlv.logTime) ="+ montOne+" AND EXTRACT(year FROM tlv.logTime) ="+ year+" AND EXTRACT(day FROM tlv.logTime) ="+ day+" AND tlv.trendLogId = "+hydroPneumaticPump.get(0).getParamValue()+" GROUP BY tlv.logValue").getResultList();
		if(!(waterDistData1.isEmpty())){
		res.add(currDay);
		res.add(waterDistData1.get(0));
		}
		else{
		res.add(currDay);
		res.add(0.0);
		}
		
		for (int i=0;i<=5;i++){
		    cal.add(Calendar.DAY_OF_MONTH,-1);
		    int months = cal.get(Calendar.MONTH);
			int montOnes = months + 1;
			int years = cal.get(Calendar.YEAR);
			int days = cal.get(Calendar.DAY_OF_MONTH);
			String currenDay = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
			List<?> waterDistData=entityManager.createQuery("SELECT MAX(tlv.logValue) FROM TrendLogValue tlv  WHERE EXTRACT(month FROM tlv.logTime) ="+ montOnes+" AND EXTRACT(year FROM tlv.logTime) ="+ years+" AND EXTRACT(day FROM tlv.logTime) ="+ days+" AND tlv.trendLogId = "+hydroPneumaticPump.get(0).getParamValue()+" GROUP BY tlv.logValue").getResultList();
			if(!(waterDistData.isEmpty())){
			res.add(currenDay);
			res.add(waterDistData.get(0));
			}
			else{
			res.add(currenDay);
			res.add(0.0);
			}
		}
	   }
		return res;
	}
	
	
	
	@RequestMapping("/bmsDashboard/sewerageTreatedWaterPlant")
	public @ResponseBody Object sewerageTreatedWaterPlant(HttpServletRequest request){
	
		Date date=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month1 = cal.get(Calendar.MONTH);
		int montOne = month1 + 1;
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		List<?> seweragePlant=bmsSettingsService.getSeweragePlantIds();
		List<Map<String,Object>> result=new ArrayList<>();
		if(!(seweragePlant.isEmpty())){
		for (final Iterator<?> i = seweragePlant.iterator(); i.hasNext();) {
			final Object[] values = (Object[])i.next();
		
		List<?> sewerageData =entityManager.createQuery("SELECT MAX(tlv.logValue) FROM TrendLogValue tlv  WHERE EXTRACT(month FROM tlv.logTime) ="+ montOne+" AND EXTRACT(year FROM tlv.logTime) ="+ year+" AND EXTRACT(day FROM tlv.logTime) ="+ day+" AND tlv.trendLogId = "+(Integer)values[0]+" GROUP BY tlv.logValue").getResultList();
		Map<String, Object> obj = new HashMap<>();
				if (!(sewerageData.isEmpty())) {
					obj.put("name", (String) values[1]);
					obj.put("y", sewerageData.get(0));
					result.add(obj);
				} else {
					obj.put("name", (String) values[1]);
					obj.put("y", 0);
					result.add(obj);
				}
			}

		}
		return result;
	}
	
	@RequestMapping("/bmsDashboard/fightFightingSystem")
	public @ResponseBody Object fightFightingSystem(HttpServletRequest request){
	
		Date date=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month1 = cal.get(Calendar.MONTH);
		int montOne = month1 + 1;
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		List<?> fightFightingIds=bmsSettingsService.getFightFightingIds();
		List<Map<String,Object>> result=new ArrayList<>();
		if(!(fightFightingIds.isEmpty())){
			for (final Iterator<?> i = fightFightingIds.iterator(); i.hasNext();) {
				final Object[] values = (Object[])i.next();
			
			List<?> fightFightingData =entityManager.createQuery("SELECT MAX(tlv.logValue) FROM TrendLogValue tlv  WHERE EXTRACT(month FROM tlv.logTime) ="+ montOne+" AND EXTRACT(year FROM tlv.logTime) ="+ year+" AND EXTRACT(day FROM tlv.logTime) ="+ day+" AND tlv.trendLogId = "+(Integer)values[0]+" GROUP BY tlv.logValue").getResultList();
			
				Map<String, Object> obj = new HashMap<>();
				if (!(fightFightingData.isEmpty())) {
					obj.put("name", (String) values[1]);
					obj.put("y", fightFightingData.get(0));
					result.add(obj);
				} else {
					obj.put("name", (String) values[1]);
					obj.put("y", 0);
					result.add(obj);
				}
			}

		}
		return result;
	}
	
	@RequestMapping("/bmsDashboard/liftElevatorSystem")
	public @ResponseBody Object liftElevatorSystem(HttpServletRequest request){
	
		Date date=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month1 = cal.get(Calendar.MONTH);
		int montOne = month1 + 1;
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		List<?> liftElevatorIds=bmsSettingsService.getLiftElevatorIds();
		List<Map<String,Object>> result=new ArrayList<>();
		if(!(liftElevatorIds.isEmpty())){
			for (final Iterator<?> i = liftElevatorIds.iterator(); i.hasNext();) {
				final Object[] values = (Object[])i.next();
			
			List<?> liftElevatorData =entityManager.createQuery("SELECT MAX(tlv.logValue) FROM TrendLogValue tlv  WHERE EXTRACT(month FROM tlv.logTime) ="+ montOne+" AND EXTRACT(year FROM tlv.logTime) ="+ year+" AND EXTRACT(day FROM tlv.logTime) ="+ day+" AND tlv.trendLogId = "+(Integer)values[0]+" GROUP BY tlv.logValue").getResultList();
			Map<String, Object> obj = new HashMap<>();
				if (!(liftElevatorData.isEmpty())) {
					obj.put("name", (String) values[1]);
					obj.put("y", liftElevatorData.get(0));
					result.add(obj);
				} else {
					obj.put("name", (String) values[1]);
					obj.put("y", 0);
					result.add(obj);
				}
			}

		}
		return result;
	}
	
	
	@RequestMapping("/bmsDashboard/dgSet")
	public @ResponseBody Object dgSet(HttpServletRequest request){
	
		Date date=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month1 = cal.get(Calendar.MONTH);
		int montOne = month1 + 1;
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		List<?> dgSetIds=bmsSettingsService.getDGSetIds();
		List<Map<String,Object>> result=new ArrayList<>();
		if(!(dgSetIds.isEmpty())){
			for (final Iterator<?> i = dgSetIds.iterator(); i.hasNext();) {
				final Object[] values = (Object[])i.next();
				
			   List<?> dgSetData =entityManager.createQuery("SELECT MAX(tlv.logValue) FROM TrendLogValue tlv  WHERE EXTRACT(month FROM tlv.logTime) ="+ montOne+" AND EXTRACT(year FROM tlv.logTime) ="+ year+" AND EXTRACT(day FROM tlv.logTime) ="+ day+" AND tlv.trendLogId = "+(Integer)values[0]+" GROUP BY tlv.logValue").getResultList();
			
			   Map<String,Object> obj=new HashMap<>();
				if (!(dgSetData.isEmpty())) {
					obj.put("name", (String) values[1]);
					obj.put("y", dgSetData.get(0));
					result.add(obj);
				} else {
					obj.put("name", (String) values[1]);
					obj.put("y", 0);
					result.add(obj);
				}

			}

		}
		return result;
	}

	
	@RequestMapping(value = "/energyDashBoard", method = RequestMethod.GET)
	public String energyDashBoard(ModelMap model, HttpServletRequest request, final Locale locale) throws ParseException {
		model.addAttribute("ViewName", "Energy Dashboard ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Energy Dashboard", 1, request);
		
		List<?> result= bmsSettingsService.getYearWisedetails();
		List<Map<String,Object>> year=new ArrayList<Map<String,Object>>();
		int i=1;
		for(Iterator<?> itr=result.iterator();itr.hasNext();){
			Object[] obj =(Object[]) itr.next();
			
			if(obj[1]!=null){
			Map<String, Object> record=new HashMap<String,Object>();
			record.put("srNo", i);
			i++;
			record.put("year",(Integer)obj[1]);
			record.put("consumption",Long.parseLong(String.valueOf((Math.round(Double.parseDouble((String)obj[0]))))));
			year.add(record);
			}
		}
		List<?> result1= bmsSettingsService.monthWisedetails();
		List<Map<String,Object>> month=new ArrayList<Map<String,Object>>();
		int j=1;
		for(Iterator<?> itr=result1.iterator();itr.hasNext();){
			Object[] obj =(Object[]) itr.next();
			
			if(obj[1]!=null){
			Map<String, Object> record=new HashMap<String,Object>();
			record.put("srNo", j);
			j++;
			Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("01/"+(Integer)obj[1]+"/"+(Integer)obj[2]);
			
			record.put("month",new SimpleDateFormat("MMMM, yyyy").format(date1));
			record.put("consumption",Long.parseLong(String.valueOf((Math.round(Double.parseDouble((String)obj[0]))))));
			record.put("year",(Integer)obj[2]);
			month.add(record);
			}
		}
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		return "bms/EnergyDashBoard";
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/energyDashboard/yearWisedetails")
	public @ResponseBody Object getsuperAdminDetails(HttpServletRequest request){
		List<List> masterList = new ArrayList<>();
		List<?> result= bmsSettingsService.getYearWisedetails();
		
		for(Iterator<?> iterator=result.iterator();iterator.hasNext();){
			
			Object[] record= (Object[])iterator.next();
			if(record[1]!=null){
				List year=new ArrayList<>();
				year.add(0,""+record[1]+"");
				year.add(1,Long.parseLong(String.valueOf((Math.round(Double.parseDouble((String)record[0]))))));
				masterList.add(year);
			}
			
		}
		
		return masterList;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/energyDashboard/monthWisedetails")
	public @ResponseBody Object monthWisedetails(HttpServletRequest request) throws ParseException{
		List<List> masterList = new ArrayList<>();
		List<?> result= bmsSettingsService.monthWisedetails();
		
		for(Iterator<?> iterator=result.iterator();iterator.hasNext();){
			
			Object[] record= (Object[])iterator.next();
			if(record[1]!=null){
				List month=new ArrayList<>();
				Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("01/"+(Integer)record[1]+"/"+(Integer)record[2]);
				month.add(0,""+new SimpleDateFormat("MMMM, yyyy").format(date1)+"");
				month.add(1,Long.parseLong(String.valueOf((Math.round(Double.parseDouble((String)record[0]))))));
				masterList.add(month);
			}
			
		}
		
		return masterList;
	}
	@RequestMapping("/energyDashboard/towerWisedetails")
	public @ResponseBody Object towerWisedetails(HttpServletRequest request) throws ParseException{
		
		String[] formateMonth=months.split(",");
		String str=formateMonth[0]+" 01, "+formateMonth[1];
		SimpleDateFormat input = new SimpleDateFormat("MMMM dd, yyyy");    
		Date date = input.parse(str);
		List<Map<String,Object>> result=new ArrayList<>();
		List<Blocks> block=blockService.findAll();
				for(Blocks blocks: block){
					List<?> bio=bmsSettingsService.getAccountId(blocks.getBlockId(),date);
					if(!( bio==null || bio.isEmpty())){
						
						if(bio.get(0)== null){
						}else{
							Map<String,Object> obj=new HashMap<>();
							obj.put("name", blocks.getBlockName());
							obj.put("y",Long.parseLong(String.valueOf((Math.round(Double.parseDouble((String) bio.get(0)))))));
							result.add(obj);
						}
					}
					
					
					
				}
		
		
		return result;
	}
	@RequestMapping(value = "getTowerWiseDetails", method ={RequestMethod.GET, RequestMethod.POST})
	public String getTowerWiseDetails(@RequestParam("id") String month,ModelMap model, HttpServletRequest request, final Locale locale) throws ParseException {
		model.addAttribute("ViewName", "Energy Dashboard ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Energy Dashboard", 1, request);
		months=month;
		
		
		String[] formateMonth=months.split(",");
		String str=formateMonth[0]+" 01, "+formateMonth[1];
		SimpleDateFormat input = new SimpleDateFormat("MMMM dd, yyyy");    
		Date date = input.parse(str);
		List<Map<String,Object>> result=new ArrayList<>();
		int j=1;
		List<Blocks> block=blockService.findAll();
				for(Blocks blocks: block){
					List<?> bio=bmsSettingsService.getAccountId(blocks.getBlockId(),date);
					if(!( bio==null || bio.isEmpty())){
						
						if(bio.get(0)== null){
							//obj.put("y",0);
						}else{
							Map<String, Object> record=new HashMap<String,Object>();
							record.put("srNo", j);
							j++;
							record.put("tower", blocks.getBlockName());
							record.put("consumption", Long.parseLong(String.valueOf((Math.round(Double.parseDouble((String) bio.get(0)))))));
							
							result.add(record);
						}
					}
					
					
					
				}
		
		model.addAttribute("tower",result);
		return "bms/TowerWiseDetails";
	}
	
	//cron job for BMS Notifications
	//@Scheduled(cron = "0/30 * * * * ?")
	public  void createCronJobForBMSNotification() {
			
		
		
			Date date=new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int month1 = cal.get(Calendar.MONTH);
			int montOne = month1 + 1;
			int year = cal.get(Calendar.YEAR);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			
			List<?> bmsParamValueList=bmsSettingsService.readTrendLogIdWithDeptDesig();
			
			for (final Iterator<?> i = bmsParamValueList.iterator(); i.hasNext();) {
				final Object[] values = (Object[])i.next();
			
				int paramValue=(Integer)values[0];
				logger.info("paramValue----------"+paramValue);
				List<?> checkData=trendLogValueService.getMaxTimeStampBasedOnCurrentData(montOne,year,day,paramValue);
				//List<?> checkData=trendLogValueService.getMaxTimeStampBasedOnCurrentData(7,2015,8,paramValue);
				if(!(checkData.isEmpty())){
				Double value=(Double)entityManager.createQuery("SELECT tlv.logValue from TrendLogValue tlv WHERE tlv.trendLogId="+paramValue+" AND tlv.logTime='"+(Timestamp)checkData.get(0)+"'").getSingleResult();
				logger.info("trendLog Value----------"+value);
				if(treatedWaterTankLevel.equalsIgnoreCase((String)values[3])){
					logger.info("Inside Treated Water Tank Level");
					if(value!=null){
						
						if(value>0){
							String notifyStatus="";
							sendMailAndSMSAlerts((Integer)values[1],(Integer)values[2],notifyStatus,(String)values[3]);
						}
						
					}
				}
				if(softWaterTankLevel.equalsIgnoreCase((String)values[3])){
					logger.info("Inside Soft Water Tank Level");
					if(value!=null){
						
						if(value>0){
							String notifyStatus="";
							sendMailAndSMSAlerts((Integer)values[1],(Integer)values[2],notifyStatus,(String)values[3]);
						}
						
					}
				}
				if(softWaterPHMonitoring.equalsIgnoreCase((String)values[3])){
					logger.info("Inside Soft Water PH Monitoring");
					if(value!=null){
						
						if(value>0){
							String notifyStatus="";
							sendMailAndSMSAlerts((Integer)values[1],(Integer)values[2],notifyStatus,(String)values[3]);
						}
						
					}
				}
				if(softWaterChlorineMonitoring.equalsIgnoreCase((String)values[3])){
					logger.info("Inside Soft Water Chlorine Monitoring");
					if(value!=null){
						
						if(value>0){
							String notifyStatus="";
							sendMailAndSMSAlerts((Integer)values[1],(Integer)values[2],notifyStatus,(String)values[3]);
						}
						
					}	
				}
				if(hydrantPumpStatus.equalsIgnoreCase((String)values[3])){
					logger.info("Inside Hydrant Pump Status");
					if(value!=null){
						
						if(value>0){
							String notifyStatus="";
							sendMailAndSMSAlerts((Integer)values[1],(Integer)values[2],notifyStatus,(String)values[3]);
						}
						
					}	
				}
				if(sprinklerPumpStatus.equalsIgnoreCase((String)values[3])){
					logger.info("Inside Sprinkler Pump Status");
					if(value!=null){
						
						if(value>0){
							String notifyStatus="";
							sendMailAndSMSAlerts((Integer)values[1],(Integer)values[2],notifyStatus,(String)values[3]);
						}
						
					}	
				}
				if(jockeyPumpStatus.equalsIgnoreCase((String)values[3])){
					logger.info("Inside Jockey Pump Status");
					if(value!=null){
						
						if(value>0){
							String notifyStatus="";
							sendMailAndSMSAlerts((Integer)values[1],(Integer)values[2],notifyStatus,(String)values[3]);
						}
						
					}	
				}
				if(dieselPumpStatus.equalsIgnoreCase((String)values[3])){
					
					if(value!=null){
						logger.info("Inside Diesel Pump Status");
						if(value>0){
							String notifyStatus="";
							sendMailAndSMSAlerts((Integer)values[1],(Integer)values[2],notifyStatus,(String)values[3]);
						}
						
					}	
				}
				if(fanStatus.equalsIgnoreCase((String)values[3])){
					logger.info("Inside Fan Status");
					if(value!=null){
						
						if(value>0){
							String notifyStatus="";
							sendMailAndSMSAlerts((Integer)values[1],(Integer)values[2],notifyStatus,(String)values[3]);
						}

					}
				}
				if(liftElevatorStatus.equalsIgnoreCase((String)values[3])){
					logger.info("Inside Lift Elevator Status");
					if(value!=null){
						
						if(value>0){
							String notifyStatus="";
							sendMailAndSMSAlerts((Integer)values[1],(Integer)values[2],notifyStatus,(String)values[3]);
						}
						
					}	
				}
				if(liftElevatorFaultAlarm.equalsIgnoreCase((String)values[3])){
					logger.info("Inside Lift Elevator Fault Alarm");
					if(value!=null){
						
						if(value>0){
							String notifyStatus="";
							sendMailAndSMSAlerts((Integer)values[1],(Integer)values[2],notifyStatus,(String)values[3]);
						}
						
					}	
				}
				if(dgSetStatus.equalsIgnoreCase((String)values[3])){
					logger.info("Inside DG Set Status");
					if(value!=null){
						
						if(value>0){
							String notifyStatus="";
							sendMailAndSMSAlerts((Integer)values[1],(Integer)values[2],notifyStatus,(String)values[3]);
						}
						
					}
				}
				if(dgSetTripAlarm.equalsIgnoreCase((String)values[3])){
					logger.info("Inside DG Set Trip Alarm");
					if(value!=null){
						
						if(value>0){
							String notifyStatus="";
							sendMailAndSMSAlerts((Integer)values[1],(Integer)values[2],notifyStatus,(String)values[3]);
						}
						
					}	
				}
				if(dgBatteryStatus.equalsIgnoreCase((String)values[3])){
					logger.info("Inside DG Battery Status");
					if(value!=null){
						
						if(value>0){
							String notifyStatus="";
							sendMailAndSMSAlerts((Integer)values[1],(Integer)values[2],notifyStatus,(String)values[3]);
						}
						
					}	
				}
				if(hydroPneumaticPump.equalsIgnoreCase((String)values[3])){
					logger.info("Inside Hydro Pneumatic Pump");
					if(value!=null){
						
						if(value>0){
							String notifyStatus="";
							sendMailAndSMSAlerts((Integer)values[1],(Integer)values[2],notifyStatus,(String)values[3]);
						}

					}
				}

			}

		}
	}
				
				
	@SuppressWarnings("rawtypes")
	private void sendMailAndSMSAlerts(Integer deptId, Integer desigId,String notifyStatus,String trendLogName) {
		
		String emailId="";
		String mobileNo="";
		String SMSStatus="No";
		String MailStatus="No";
		String sentMailPersonIds ="";
		BMSNotificationEntity bmsNoty=new BMSNotificationEntity();
		bmsNoty.setDeptId(deptId);
		bmsNoty.setDnId(desigId);
		bmsNoty.setBmsElements(trendLogName);
		bmsNoty.setNotifyStatus("Low");
		bmsNoty.setNotifyDate(new Timestamp(new Date().getTime()));
		
		logger.info("new Timestamp(new Date().getTime())---------------"+new Timestamp(new Date().getTime()));
		
		List<Users> personList=usersService.getPersonListBasedOnDeptDesigId(deptId,desigId);
		
		if(!(personList.isEmpty())){
			
			for (final Users record : personList) {
			
				Person users = personService.getPersonBasedOnId(record.getPersonId());
				if("Active".equalsIgnoreCase(users.getPersonStatus())){
				
				sentMailPersonIds = sentMailPersonIds +String.valueOf(record.getPersonId()) +",";
				Set<Contact> contact=users.getContacts();
				
				for (Iterator iterator = contact.iterator(); iterator.hasNext();) {
					Contact contact2 = (Contact) iterator.next();
					if ((contact2.getContactType().equalsIgnoreCase("Email")) && (contact2.getContactPrimary().equalsIgnoreCase("Yes"))) {
						emailId = contact2.getContactContent();
						MailStatus="Yes";
						EmailCredentialsDetailsBean emailCredentialsDetailsBean = null;
						try {
							emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
						} catch (Exception e) {
							e.printStackTrace();
						}

						String messageContent = "<html><body>"
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
								+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">GRAND ARCH BMS Facility Services</h2> <br /> Dear "
								+ users.getFirstName()
								+ ",<br/> <br/>The"
								+ trendLogName
								+ "value is"
								+notifyStatus
								+"Please Check it Immediately"
								+ "<br/>Thanks,<br/>"
								+ "GRAND ARCH BMS Facility Services<br/> <br/>"
								+ "</body></html>";

						emailCredentialsDetailsBean.setToAddressEmail(emailId);
						emailCredentialsDetailsBean.setMessageContent(messageContent);

						new Thread(new SendMailForStaff(
								emailCredentialsDetailsBean)).start();
					}
					if ((contact2.getContactType().equalsIgnoreCase("Mobile")) && (contact2.getContactPrimary().equalsIgnoreCase("Yes"))) {

						mobileNo = contact2.getContactContent();
						SMSStatus="Yes";
						String userMessage = "Dear "
								+ users.getFirstName()
								+ ",The"
								+ trendLogName
								+ "value is"
								+notifyStatus
								+"Please Check it Immediately"
								+ "From"
								+ "GRAND ARCH BMS Facility Services.";
								
						SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();

						smsCredentialsDetailsBean.setNumber(mobileNo);
						smsCredentialsDetailsBean.setUserName(users.getFirstName());
						smsCredentialsDetailsBean.setMessage(userMessage);
						new Thread(new SendSMSForStatus(
						smsCredentialsDetailsBean)).start();
					}
				}
			}
				logger.info("sentMailPersonIds-------------"+sentMailPersonIds);
				if(!sentMailPersonIds.equalsIgnoreCase(" ") && sentMailPersonIds!=null){
				bmsNoty.setSmsStatus(SMSStatus);
				bmsNoty.setMailStatus(MailStatus);
				bmsNoty.setPersonList(sentMailPersonIds);
				bmsNotificationService.save(bmsNoty);
				}
			}

		}

	}
				
	
	
	
}
*/