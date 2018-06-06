package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.DrGroupId;
import com.bcits.bfm.model.Project;
import com.bcits.bfm.model.ProjectLocation;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.DrGroupIdService;
import com.bcits.bfm.service.customerOccupancyManagement.ProjectLocationService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.facilityManagement.BlocksService;
import com.bcits.bfm.serviceImpl.customerOccupancyManagement.ProjectServiceImpl;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.itextpdf.text.log.Logger;


/**
 * Controller which includes all the business logic concerned to this module's Usecase
 * Module: Customer Occupancy 
 * Use Case : Project,Property
 * 
 * @author Mohammed Farooq
 * @version %I%, %G%
 * @since 0.1
 */
/**
 * @author user47
 *
 */

@Controller
public class ProjectController 
{
	
	private static final Log logger = LogFactory.getLog(ProjectController.class);

	@Autowired
	private ProjectServiceImpl projectService;
	
	@Autowired
	private ProjectLocationService projectLocationService;
	
	@Autowired
	private BlocksService blockServie;
	
	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private PropertyService propertyImpl;
	
	@Autowired
	private JsonResponse errorResponse;
	
	@Autowired
	private BlocksService blocksService;
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private JsonResponse jsonErrorResponse;
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private DrGroupIdService drGroupIdService;
	
	
	
	/**
	 * Method to request for Project View Page
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return - Project View page
	 */
	@RequestMapping("/comproject")
	public String projectIndex(HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{ 
		model.addAttribute("ViewName", "Customer Occupancy");
	   breadCrumbService.addNode("nodeID", 0, request);
	   breadCrumbService.addNode("Customer Occupancy", 1, request);
		breadCrumbService.addNode("Manage Project", 2, request);
		return "com/project";
	}

	/**
	 * Method to request the Property View Page
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param locale
	 * @return Property View page
	 */
	@RequestMapping("/comproperty")
	public String propertyIndex(HttpServletRequest request,HttpServletResponse response,ModelMap model, final Locale locale)
	{
		 model.addAttribute("ViewName", "Customer Occupancy");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Customer Occupancy", 1, request);
		breadCrumbService.addNode("Manage Property", 2, request);
		model.addAttribute("statusYesNo",commonController.populateCategories("values.address.addressPrimary", locale));
		model.addAttribute("propertyType" , commonController.populateCategories("property.PropertyType", locale));
		model.addAttribute("propertyMeasurement",commonController.populateCategories("property.MeasuermentType", locale));
		model.addAttribute("propertyStatus",commonController.populateCategories("property.Status", locale));
		model.addAttribute("propertyBillable",commonController.populateCategories("property.Billable", locale));
		model.addAttribute("sex", commonController.populateCategories("sex", locale));
		model.addAttribute("status", commonController.populateCategories("values.project.status", locale));
		model.addAttribute("bloodGroup", commonController.populateCategories("bloodGroup", locale));
		return "com/property";
	}
	
	/**
	 * Reading all the projects
	 * 
	 * @return List of projects
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/project/read_projectData",method=RequestMethod.GET)
	public @ResponseBody List<Project> read_project()
	{
		return projectService.findAllProjects();
	}
	
	/**
	 * Reading all project names for filter
	 * @return list of project names
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/project/readallprojectnames",method = RequestMethod.GET)
	public @ResponseBody List getAllProjectNames()
	{
		return projectService.findProjectNames();
	}
	
	/**
	 * Reading all property id for filter
	 * @return list of property id
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/project/readallpropertyId",method = RequestMethod.GET)
	public @ResponseBody Set<?> getAllProjectNamesID()
	{
		Set<Integer> data=new HashSet<Integer>();
		
		for(Property p:propertyService.findAll())
		{
			data.add(p.getPropertyId());
			
		}
		return data;
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/project/readallpropertyIdcreate",method = RequestMethod.GET)
	public @ResponseBody Set<?> getAllProjectNamesID1()
	{
		Set<String> data=new HashSet<String>();
		
		for(Property p:propertyService.findAll())
		{
			data.add(p.getCreatedBy());
			
		}
		return data;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/project/readallpropertyIdUpdate",method = RequestMethod.GET)
	public @ResponseBody Set<?> getAllProjectNamesID2()
	{
		Set<String> data=new HashSet<String>();
		
		for(Property p:propertyService.findAll())
		{
			data.add(p.getLastUpdatedBy());
			
		}
		return data;
	}
	
	
	/**
	 * Reading all property numbers for dropdowns,filters
	 * @return list of property numbers
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/property/readallpropertyNumber",method = RequestMethod.GET)
	public @ResponseBody List getAllPropertyNumbers()
	{
		return propertyService.getPropertyNameForFilter();
	}
	
	/**
	 * Creating the Project
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/project/create",method=RequestMethod.POST)
	public @ResponseBody Object create(@RequestBody Map<String, Object> map)
	{
		Project project=new Project();
		project.setProjectName((String)map.get("projectName"));
		project.setno_OF_TOWERS((Integer)map.get("no_OF_TOWERS"));
		project.setProjectAddress((String)map.get("projectAddress"));
		project.setPROJECT_PINCODE(Integer.parseInt((String) map.get("project_PINCODE")));
		project.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
		project.setLastUpdatedBy((String)map.get("lastUpdatedBy"));
		project.setProjectCountry((Integer)map.get("projectCountry"));
		project.setProjectState((String)map.get("projectState")+"");
		if(map.get("projectLocation") instanceof String)
		{
			ProjectLocation plocation = new ProjectLocation();
			plocation.setStateId(Integer.parseInt((String)map.get("projectState")));
			plocation.setProjectLocationName((String)map.get("projectLocation"));
			plocation.setLastUpdatedDate(new Timestamp(new Date().getTime()));
			projectLocationService.save(plocation);
			plocation = projectLocationService.findOnLastUpdatedDate(plocation.getLastUpdatedDate());
			project.setProjectLocation(plocation.getProjectLocationId());
		}
		else
		{
			project.setProjectLocation((Integer)map.get("projectLocation"));
		}
		
		List<Project> ps = projectService.findAll();
		Iterator<Project> it = ps.iterator();
		String msg = "";
		while (it.hasNext()) 
		{
			Project pslot = it.next();
			if (pslot.getProjectName().equalsIgnoreCase(project.getProjectName())) {
				msg = "Exists";
			}
		}
		if (!(msg.equals(""))) 
		{
			final String cardNumber = project.getProjectName();
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("projectNameAlreadyExists", "Project Name : "+cardNumber+" Already Exists");
				}
			};

			jsonErrorResponse.setStatus("PROJECT_NAME_EXISTS");
			jsonErrorResponse.setResult(errorMapResponse);

			return jsonErrorResponse;
		} 
		else 
		{
			projectService.save(project);
			return project;
		}
	}


	/**
	 * Update the project details
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/project/update_project",method=RequestMethod.POST)
	public @ResponseBody Object update_project(@RequestBody Map<String, Object> map)
	{
		Project project=new Project();
		project.setProjectId((Integer)map.get("projectId"));
		project.setProjectName((String)map.get("projectName"));
		project.setno_OF_TOWERS((Integer)map.get("no_OF_TOWERS"));
		project.setProjectAddress((String)map.get("projectAddress"));
		project.setPROJECT_PINCODE(Integer.parseInt((String) map.get("project_PINCODE")));
		project.setCreatedBy((String)map.get("createdBy"));
		project.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		project.setProjectCountry((Integer)map.get("projectCountry"));
		project.setProjectState((String)map.get("projectState")+"");
		if(map.get("projectLocation") instanceof String)
		{
			ProjectLocation plocation = new ProjectLocation();
			plocation.setStateId(Integer.parseInt((String)map.get("projectState")));
			plocation.setProjectLocationName((String)map.get("projectLocation"));
			plocation.setLastUpdatedDate(new Timestamp(new Date().getTime()));
			projectLocationService.save(plocation);
			plocation = projectLocationService.findOnLastUpdatedDate(plocation.getLastUpdatedDate());
			project.setProjectLocation(plocation.getProjectLocationId());
		}
		else
		{
			project.setProjectLocation((Integer)map.get("projectLocation"));
		}
		List<Project> ps = projectService.findAll();
		Iterator<Project> it = ps.iterator();
		String msg = "";
		while (it.hasNext()) 
		{
			Project pslot = it.next();
			if (pslot.getProjectId() != project.getProjectId()) 
			{
				if (pslot.getProjectName().equalsIgnoreCase(project.getProjectName())) 
				{
					msg = "Exists";
				}
			}
		}
		if (!(msg.equals(""))) 
		{
			final String cardNumber = project.getProjectName();
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("projectNameAlreadyExists", "Project Name : "+cardNumber+" Already Exists");
				}
			};

			jsonErrorResponse.setStatus("PROJECT_NAME_EXISTS");
			jsonErrorResponse.setResult(errorMapResponse);

			return jsonErrorResponse;
		} 
		else 
		{
			projectService.update(project);
			return project;
		}
	}

	/**
	 * Delete Project
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/project/delete_project",method=RequestMethod.POST)
	public  @ResponseBody Object delete(@RequestBody Map<String, Object> map)
	{
		try
		{
			Project project=new Project();
			int id=(Integer)map.get("projectId");
			projectService.delete(id);
			return project;
		}
		catch(Exception e)
		{
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("childFoundException", "Project have blocks, cannot be deleted");
				}
			};
			errorResponse.setStatus("CHILD_FOUND_EXCEPTION");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		}
	}
	
	/**
	 * Read all blocks under project
	 * @param projectId
	 * @return list of blocks under project
	 */
	@RequestMapping(value="/project/readblock/{projectId}",method=RequestMethod.GET)
	public @ResponseBody List<Blocks> readBlocks(@PathVariable int projectId)
	{
		return blockServie.findBlockOnProjectId(projectId);
	}
	
	
	/**
	 * Create Block under selected project
	 * @param projectId
	 * @param map
	 * @param request
	 * @param response
	 * @param block
	 * @return block Object
	 */
	@RequestMapping(value="/project/createblock/{projectId}",method=RequestMethod.POST)
	public @ResponseBody Blocks createblock(@PathVariable int projectId,@RequestBody Map<String,Object> map,HttpServletRequest request,HttpServletResponse response,@ModelAttribute("block") Blocks block)
	{
		block.setProjectId(projectId);
		block = blockServie.getBeanObject(block,"save",map,request);
		blockServie.save(block);
		return block;
	}
	
	
	/**
	 * Update block details under selected project
	 * @param projectId
	 * @param map
	 * @param request
	 * @param response
	 * @param block
	 * @return block Object
	 */
	@RequestMapping(value="/project/updateblock/{projectId}",method=RequestMethod.POST)
	public @ResponseBody Blocks updateblock(@PathVariable int projectId,@RequestBody Map<String,Object> map,HttpServletRequest request,HttpServletResponse response,@ModelAttribute("block") Blocks block)
	{
		block.setProjectId(projectId);
		block = blockServie.getBeanObject(block,"update",map,request);
		blockServie.update(block);
		return block;
	}
	
	/**
	 * Delete the block under selected project
	 * 
	 * If block does not have Property then Delete success
	 * 
	 * Else Child found exception thrown
	 * 
	 * @param map
	 * @param block
	 * @return
	 */
	@RequestMapping(value="/project/deleteblock",method=RequestMethod.POST)
	public  @ResponseBody Object deleteblock(@RequestBody Map<String, Object> map,@ModelAttribute("block") Blocks block)
	{
		try
		{
			int id=(Integer)map.get("blockId");
			blockServie.delete(id);
			return block;
		}
		catch(Exception e)
		{
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("childFoundException", "Block is assigned with prorperties,Cannot be deleted");
				}
			};
			errorResponse.setStatus("CHILD_FOUND_EXCEPTION");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		}
	}
	
	/**
	 * Reading Block Names for Dropdownlist, Filters
	 * 
	 * @return list of block names
	 */
	@RequestMapping(value="/property/getBlockNames",method= RequestMethod.GET)
	public @ResponseBody List<Blocks> getBlockNames()
	{
		List<Blocks> blockslist = new ArrayList<Blocks>();
		Blocks project = null;
		for (final Blocks record : blockServie.findAll()) 
		{
			project =  new Blocks();
			project.setProjectId(record.getProjectId());
			project.setBlockName(record.getBlockName());
			project.setBlockId(record.getBlockId());
			project.setNumOfProperties(record.getNumOfProperties());
			blockslist.add(project);
		}
		return blockslist;
	}
	
	/**
	 * Read all pincodes for filter
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/project/readAllPinCodes",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<String> getUniquePincodes()
	{
		List list = projectService.getAllUniquePinCodes();
		List<String> tempList = new ArrayList<String>();
		Iterator it = list.iterator();
		while(it.hasNext())
		{
			tempList.add(it.next().toString());
		}
		return tempList;
	}
	
	/**
	 * Use Case: ----------------------------------------- Property
	 * --------------------------------------------
	 */
	
	
	/**
	 * Read all property
	 * @return list of property objects
	 */
	@RequestMapping(value = "/property/read_property", method = RequestMethod.GET)
	public @ResponseBody List<Property> read_property() 
	{
		List<Property> list = propertyImpl.findAll();
		return propertyImpl.getAllPropetyList(list);
	}

	
	/**
	 * Create Property
	 * @param map
	 * @param property
	 * @return property Object
	 */
	@RequestMapping(value = "/property/create_property", method = RequestMethod.POST)
	public @ResponseBody Object create_property(@RequestBody Map<String, Object> map,@ModelAttribute("property") Property property) 
	{
		property.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
		property.setLastUpdatedDt(new Timestamp(new Date().getTime()));
		drGroupIdService.save(new DrGroupId(property.getCreatedBy(), property.getLastUpdatedDt()));
		property.setDrGroupId(drGroupIdService.getNextVal(property.getCreatedBy(), property.getLastUpdatedDt()));
		
		property.setBlocks(blocksService.find(Integer.parseInt(map.get("blocks").toString())));
		property.setBlockId(property.getBlocks().getBlockId());
		property  = propertyService.getBeanObject(property,"save",map);
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("projectId", property.getProjectId());
		queryMap.put("blockId", property.getBlockId());
		queryMap.put("property_No", property.getProperty_No());
		List<Property> propertyList = propertyService.getByNamedQuery("Property.findAllOnIds", queryMap);
		try
		{
			Iterator<Property> it = propertyList.iterator();
			String msg = "";
			while (it.hasNext()) 
			{
				Property p = it.next();
				if (p.getProperty_No().equalsIgnoreCase(property.getProperty_No())) 
				{
					msg = "Exists";
				}
			}
			if (!(msg.equals(""))) 
			{
				final String propertyNumber = property.getProperty_No();
				@SuppressWarnings("serial")
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("propertyNumberAlreadyExists", "Property "+propertyNumber+" Already Exists in selected block");
					}
				};

				errorResponse.setStatus("PROPERTY_NUM_EXISTS");
				errorResponse.setResult(errorMapResponse);

				return errorResponse;
			}
			else
			{
				propertyImpl.save(property);
				return property;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return property;
		}
		//return property;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	//Excel File Import For Bank_Statements
	@RequestMapping(value = "/properties/upload", method = RequestMethod.POST)
	public @ResponseBody Object  uploadPropertyDocument(@RequestParam MultipartFile files,HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException, ParseException 
	{	
		
		JsonResponse errorResponse = new JsonResponse();
		logger.info("INSDIE PROPERTY FILE_UPLOAD");
		XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator rows = sheet.rowIterator();			
		int i=0;	
		int j=0;
		int k=0;
		List<Property> properties = new ArrayList<Property>();		
		
		while(rows.hasNext())
		{
			
			System.out.println("inside property upload");
			XSSFRow row = ((XSSFRow) rows.next());
			logger.info("\n\nROW COUNT"+row.getRowNum());
            Iterator cells = row.cellIterator();
            
            DrGroupId drGroupId=null;
            Property ps = new Property();
           if(row.getRowNum()==0){
				   continue; //just skip the rows if row number is 0 or 1
				  }
           
           
           
           if(!StringUtils.isEmpty(row.getCell(0).getStringCellValue())){
               
               String value=row.getCell(0).getStringCellValue();
          
               
               System.out.println("inside property number"+value);
               
               Integer blockid=blockServie.getBlockIdByName(value);
               int id=blockid;
               
               ps.setBlockId(id);
                  }
           
              
                else{
                	logger.info("FAILED");
                	i=1;
                }
           
           
           
           if(!StringUtils.isEmpty(row.getCell(1).getStringCellValue())){
           	ps.setPropertyType(row.getCell(1).getStringCellValue());
           }
           else{
           	logger.info("FAILED");
           	i=1;
           }
           
           if(!StringUtils.isEmpty(row.getCell(2).getStringCellValue())){
           	ps.setAreaType(row.getCell(2).getStringCellValue());
           }
           else{
           	logger.info("FAILED");
           	i=1;
           }
           
           if(row.getCell(3)!=null){
           	BigDecimal b1;
           	b1 = new BigDecimal(row.getCell(3).getNumericCellValue());
           	int area = b1.intValue();
           	ps.setArea(area);
           }
           else{
           	logger.info("FAILED");
           	i=1;
           }
           
           if(!StringUtils.isEmpty(row.getCell(4).getStringCellValue())){
               
               String value=row.getCell(4).getStringCellValue();
            /*  
               if(value.matches("[a-zA-Z0-9]\\w-_*"))
               {*/
     
               	List<Property> proList = propertyService.executeSimpleQuery("SELECT p FROM Property p WHERE p.property_No = '"+value+"'");
               	
               	if(proList.isEmpty()){
               		logger.info("------------------------------\n\nempty List Insert Your Data");
               	 ps.setProperty_No(value);
               	}
               	else{
               		logger.info("==============================\n\nData Already Exists");
               		
               		HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", " Property Number Already Exists");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
               		
               		j=1;
               		break;
               	}
               
               	
                }
               else
               {
            	   
            		logger.info("FAILED");
            		i=1;
               		
            	 /*  HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
    					{
    						put("cannotImport", " Property Number does not allow special characters");
    					}
    				};
    				errorResponse.setStatus("cannotImport");
    				errorResponse.setResult(errorMapResponse);
              		
              		j=1;
              		break;*/
               }
               
         //  }
           
         
           
           if(row.getCell(5)!=null){
           	BigDecimal b1;
           	b1 = new BigDecimal(row.getCell(5).getNumericCellValue());
           	int property_Floor = b1.intValue();
           	ps.setProperty_Floor(property_Floor);
           }
           else{
           	logger.info("FAILED");
           	i=1;
           }

           if(!StringUtils.isEmpty(row.getCell(6).getStringCellValue())){
           	ps.setStatus(row.getCell(6).getStringCellValue());
           }
           else{
           	logger.info("FAILED");
           	i=1;
           }
           
           if(!StringUtils.isEmpty(row.getCell(7).getStringCellValue())){
           	ps.setPropertyBillable(row.getCell(7).getStringCellValue());
           }
           else{
           	logger.info("FAILED");
           	i=1;
           }
           
           if(row.getCell(8)!=null){
           	BigDecimal b1;
           	b1 = new BigDecimal(row.getCell(8).getNumericCellValue());
           	int no_of_ParkingSlots = b1.intValue();
           	ps.setNo_of_ParkingSlots(no_of_ParkingSlots);
           }
           else{
           	logger.info("FAILED");
           	i=1;
           }
           
           
           String value2=row.getCell(0).getStringCellValue();
          Integer projectid=blockServie.getProjectIdBasedOnBlockName(value2);
           ps.setProjectId(projectid);
           
           drGroupId=new DrGroupId((String)SessionData.getUserDetails().get("userID"), new Timestamp(new Date().getTime()));
           drGroupIdService.save(drGroupId);
           ps.setDrGroupId(drGroupId.getId());
           ps.setCreatedBy((String)SessionData.getUserDetails().get("userID"));
           ps.setLastUpdatedDt(new Timestamp(new Date().getTime()));
           
           properties.add(ps);
          
        
		
		logger.info("\n\n\t\ti>>"+i);
		}
		String responseVal = null;
		if(j==1){
			return errorResponse;
		}
		if(i==0)
		{	
			logger.info("Empty File");
			if(properties.size()==0){
				logger.info("Empty File Checking");
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("cannotImport", "File is Empty,Please Upload Valid File");
					}
				};
				errorResponse.setStatus("cannotImport");
				errorResponse.setResult(errorMapResponse);
			    k=1;
			}
			for (Property property : properties) {
				
				propertyService.update(property);
				//responseVal = "Success";	
				
				
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("cannotImport", "File Imported Successfully");
					}
				};
				errorResponse.setStatus("cannotImport");
				errorResponse.setResult(errorMapResponse);
				
			}
		
		}
		
		else if(i==1){
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("cannotImport", "File Importing Failed:Empty Cells Found or Duplicate Records Found");
				}
			};
			errorResponse.setStatus("cannotImport");
			errorResponse.setResult(errorMapResponse);
			
		}
		if(k==1){
			return errorResponse;
		}
		return errorResponse;
		
	}

	
	
	
	/**
	 * Update Property details
	 * @param map
	 * @param property
	 * @return property object
	 */
	@RequestMapping(value = "/property/update_property", method = RequestMethod.POST)
	public @ResponseBody
	Object update_property(@RequestBody Map<String, Object> map,@ModelAttribute("property") Property property) 
	{
		property.setPropertyId((Integer) map.get("propertyId"));
		property.setBlocks(blocksService.find(Integer.parseInt(map.get("blocks").toString())));
		property.setBlockId(property.getBlocks().getBlockId());
		property  = propertyService.getBeanObject(property,"update",map);
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("projectId", property.getProjectId());
		queryMap.put("blockId", property.getBlockId());
		queryMap.put("property_No", property.getProperty_No());
		List<Property> propertyList = propertyService.getByNamedQuery("Property.findAllOnIds", queryMap);
		try
		{
			Iterator<Property> it = propertyList.iterator();
			String msg = "";
			while (it.hasNext()) 
			{
				Property p = it.next();
				if(p.getPropertyId() != property.getPropertyId())
				{
					if (p.getProperty_No().equalsIgnoreCase(property.getProperty_No())) 
					{
						msg = "Exists";
					}
				}
			}
			if (!(msg.equals(""))) 
			{
				final String propertyNumber = property.getProperty_No();
				@SuppressWarnings("serial")
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("propertyNumberAlreadyExists", "Property "+propertyNumber+" Already Exists in selected block");
					}
				};

				errorResponse.setStatus("PROPERTY_NUM_EXISTS");
				errorResponse.setResult(errorMapResponse);

				return errorResponse;
			}
			else
			{
				propertyImpl.update(property);
				return property;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return property;
		}
	}

	/**
	 * Delete Property
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/property/delete_property", method = RequestMethod.POST)
	public @ResponseBody
	Object delete_property(@RequestBody Map<String, Object> map) 
	{
		try
		{
			Property property = new Property();
			int id = (Integer) map.get("propertyId");
			propertyImpl.delete(id);
			return property;
		}
		catch(Exception e)
		{
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("childFoundException", "Property is assigned cannot be deleted");
				}
			};

			errorResponse.setStatus("CHILD_FOUND_EXCEPTION");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		}

	}
	
	
	/**
	 * Getting all the family memebers based on personId
	 * 
	 * UI :  Property>Owner/Tenant > View Family
	 * 
	 * @param personId
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/property/getFamilyMembersBasedOnPersonId", method = RequestMethod.POST)
	public @ResponseBody List<Contact> getFamilyMembers(@RequestParam("personId") int personId,HttpServletRequest request, HttpServletResponse response) 
	{
		List list = propertyImpl.getFamilyMembersBasedOnPersonId(personId);
		return list;
	}
	
	/**
	 * Getting the Property Count under the selected Block, In order to restrict the maximum property under a block
	 * @param blockId
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/property/getPropertyCountInSelectedBlock/{blockId}")
	public @ResponseBody void getPropertyCountInBlock(@PathVariable int blockId,HttpServletRequest req,HttpServletResponse resp) throws IOException
	{
		List count = propertyImpl.executeSimpleQuery("SELECT COUNT(*) FROM Property p WHERE p.blockId = "+blockId);
		PrintWriter  out = resp.getWriter();
		out.write(count.get(0).toString());
		
	}
	@SuppressWarnings({ "rawtypes", "unused" })
	//Excel File Import For Bank_Statements
	@RequestMapping(value = "/blocks/upload/{projectId}", method = RequestMethod.POST)
	public @ResponseBody Object uploadAssetDocument(@PathVariable int projectId,@RequestParam MultipartFile files,HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException, ParseException 
	{	
	//	int projectId1=(Integer.parseInt(request.getParameter("projectId")));
		JsonResponse errorResponse = new JsonResponse();
		logger.info("INSDIE BANK_STATEMENT FILE_UPLOAD"+projectId);
		XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator rows = sheet.rowIterator();			
		int i=0;		
		int j=0;
		int k=0;
		List<Blocks> blocks = new ArrayList<Blocks>();	
		
		while(rows.hasNext())
		{
			XSSFRow row = ((XSSFRow) rows.next());
			logger.info("*********ROW COUNT*********"+row.getRowNum());
            Iterator cells = row.cellIterator();
            
            Blocks bs = new Blocks();
            if(row.getRowNum()==0){
				   continue; //just skip the rows if row number is 0 or 1
				  }
            
            Integer noOfBlocks=blockServie.getNoOfBlocks(projectId);
            System.out.println("noOfBlocks===============================================noOfBlocks"+noOfBlocks);
         Long sumOfBlocks=blockServie.getSumOfBlocks(projectId);
            //List<Blocks> sumOfBlocks=blockServie.executeSimpleQuery("SELECT COUNT(b.blockId) FROM Blocks b WHERE b.projectId='"+projectId+"'");
           System.out.println("sumOfBlocks===========================sumOfBlocks"+sumOfBlocks);
           if(sumOfBlocks>noOfBlocks)
           {
        	   
        	   
        	   HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("cannotImport", "Cannot Add More Blocks than No of Towers in Project");
					}
				};
				errorResponse.setStatus("cannotImport");
				errorResponse.setResult(errorMapResponse);
        	   j=1;
        	   break;
        	   
           }
           
         //  int blocks=sumOfBlocks;
            System.out.println("parking slots =================================="+projectId);
            
            
            
            if(!StringUtils.isEmpty(row.getCell(0).getStringCellValue())){
            	
            	String value=row.getCell(0).getStringCellValue();

            	/*if(value==""||value==null)
            	{
            		return;
            	}
             */if(value.matches("[a-zA-Z& ]{3,45}$"))
         	{

        		logger.info("**********Block Name*************"+value);

            	List<Blocks> blocks2 = blocksService.executeSimpleQuery("SELECT b FROM Blocks b WHERE b.blockName = '"+value+"'");
            	
            	if(blocks2.isEmpty()){
            		bs.setBlockName(value);
            		logger.info("*********Empty List Insert Your Data***********");
            		
            		
            		
            		bs.setBlockName(value);
            	}
            	else{
            		logger.info("**********Data Already Exists*************");
            		//i=1;
            		
            		HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Block Name Already Exists");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
				    j=1;
					break;
            	}
            	
           
            }
             else
             {
            	 HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Block Name can contain alphabets,spaces and max 45 characters");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
				    j=1;
					break;
             }
            	 
	                	/*logger.info("FAILED");
	                	i=1;*/
		            

	                
            
            }
          
            else{
            	logger.info("Failed");
            	i=1;
            	break;
            }
         
            
            
            
        
            if(row.getCell(1)!=null){
            	
            	BigDecimal b1;
            	b1 = new BigDecimal(row.getCell(1).getNumericCellValue());
            	Integer prop_no = b1.intValue(); 
            	bs.setNumOfProperties(prop_no);
            }
    
            if(row.getCell(2)!=null){
            	
            	BigDecimal b2;
            	b2 = new BigDecimal(row.getCell(2).getNumericCellValue());
            	Integer park_no = b2.intValue();
            	bs.setNumOfParkingSlots(park_no);
            	
            	}
            	
            	
            	
        
		int pid=projectId;
		
			bs.setProjectId(pid);
		
        	/*if(blocks.contains(bs)){
        		i=1;
        	}else
        		blocks.add(bs);
		}*/
			blocks.add(bs);
		logger.info("\n\n\t\ti>>"+i);
		}

		logger.info("*********Value of i********* "+i);

		String responseVal = null;
		
		if(j==1){
			return errorResponse;
		}
		if(i==0)
		{	
			logger.info("Empty File");
			if(blocks.size()==0){
				logger.info("Empty File Checking");
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("cannotImport", "File is Empty,Please Upload Valid File");
					}
				};
				errorResponse.setStatus("cannotImport");
				errorResponse.setResult(errorMapResponse);
			    k=1;
			}
			
			logger.info("Inside if Condition");
			for (Blocks blocks2 : blocks) {
				
				blockServie.save(blocks2);
				//responseVal = "Success";	
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("cannotImport", "File Imported Successfully");
				}
			};
			errorResponse.setStatus("cannotImport");
			errorResponse.setResult(errorMapResponse);
				
			}
		}
		else if(i==1)
		{

			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("cannotImport", "File Importing Failed:Empty Cells Found or Duplicate Records Found");
				}
			};
			errorResponse.setStatus("cannotImport");
			errorResponse.setResult(errorMapResponse);

		}
		
		if(k==1){
			return errorResponse;
		}
	
		return errorResponse;		
}
	
	
}
