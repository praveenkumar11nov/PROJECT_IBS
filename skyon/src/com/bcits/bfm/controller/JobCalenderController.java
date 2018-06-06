package com.bcits.bfm.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.JobCalender;
import com.bcits.bfm.model.JobCards;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.JobCalenderService;
import com.bcits.bfm.service.facilityManagement.JobCardsService;
import com.bcits.bfm.service.facilityManagement.JobTypesService;
import com.bcits.bfm.service.facilityManagement.MaintainanceTypesService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class JobCalenderController {
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private Validator validator;

    @Autowired 
    private JobCalenderService jobCalenderService;   
    
    @Autowired
    private DepartmentService departmentService;
    
    @Autowired
    private MaintainanceTypesService maintainanceTypesService;
    
    @Autowired
    private JobTypesService jobTypesService;
   
    @Autowired
    private JobCardsService jobCardsService;
    
    @Autowired
    private PersonService personService;
    
    
    @RequestMapping(value = "/jobcalender", method = RequestMethod.GET)
	public String jobcalender(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Maintenance");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Maintenance", 1, request);
		breadCrumbService.addNode("Manage Job Calender", 2, request);
		return "maintenance/jobcalender";
	}
    
    @RequestMapping(value = "/index/read", method = RequestMethod.POST)
    public @ResponseBody List<?> read() {
        return jobCalenderService.readAll();
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/index/create", method = RequestMethod.POST)
    public @ResponseBody Object create(@RequestBody ArrayList<Map<String, Object>> models,@ModelAttribute JobCalender jobCalender,
			BindingResult bindingResult, HttpServletRequest request) throws ParseException {
    	HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("userId");
		JsonResponse errorResponse = new JsonResponse();
    	List<JobCalender> jobCalenderList = new ArrayList<JobCalender>();
        for (Map<String, Object> model : models) { 
        	
        	jobCalender.setDescription((String)model.get("description"));
        	jobCalender.setTitle((String)model.get("title"));
            
            SimpleDateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            iso8601.setTimeZone(TimeZone.getTimeZone("UTC"));
            
            jobCalender.setStart(iso8601.parse((String)model.get("start")));
            jobCalender.setEnd(iso8601.parse((String)model.get("end")));
            jobCalender.setIsAllDay((boolean) model.get("isAllDay"));
            jobCalender.setRecurrenceRule((String)model.get("recurrenceRule"));
            jobCalender.setRecurrenceException((String)model.get("recurrenceException"));
            if(model.get("recurrenceId")!=null)
            	jobCalender.setRecurrenceId((Integer)model.get("recurrenceId"));
            jobCalender.setScheduleType(Integer.parseInt(model.get("scheduleType").toString()));
            
            jobCalender.setJobNumber(RandomStringUtils.randomAlphanumeric(7).toUpperCase());
            jobCalender.setDescription((String) model.get("description"));
            /*jobCalender.setJobGroup((String) model.get("jobGroup"));*/
            jobCalender.setExpectedDays(Integer.parseInt(model.get("expectedDays").toString()));            
            if(model.get("pn_Name") instanceof String){   
            	if(model.get("pn_Name")==""){
            		
            	}else{
            		jobCalender.setPerson(personService.find(Integer.parseInt(model.get("personId").toString())));
            	}
            }else if(model.get("pn_Name") instanceof java.util.LinkedHashMap){
            	 Map<String, Object> ownerMap=(Map<String, Object>) model.get("pn_Name");
                 jobCalender.setPerson(personService.find(Integer.parseInt(ownerMap.get("personId").toString())));               
            }
            else
            	jobCalender.setPerson(personService.find(Integer.parseInt(model.get("pn_Name").toString())));
            
            if(model.get("departmentName") instanceof String)
	            jobCalender.setMaintainanceDepartment(departmentService.find(Integer.parseInt(model.get("departmentId").toString())));
            else if(model.get("departmentName") instanceof java.util.LinkedHashMap){
            	 Map<String, Object> departmentMap=(Map<String, Object>) model.get("departmentName");
                 jobCalender.setMaintainanceDepartment(departmentService.find(Integer.parseInt(departmentMap.get("departmentId").toString())));
             }
            else
            	jobCalender.setMaintainanceDepartment(departmentService.find(Integer.parseInt(model.get("departmentName").toString())));           
             
            if(model.get("jobTypes") instanceof String){
            	 if(model.get("jobTypes")==""){              		
            		 jobCalender.setJobTypes(null);
              	}else{
              		jobCalender.setJobTypes(jobTypesService.find(Integer.parseInt(model.get("jobTypeId").toString())));
	            }
             }else if(model.get("jobTypes") instanceof java.util.LinkedHashMap ){
            	 Map<String, Object> jobtypeMap=(Map<String, Object>) model.get("jobTypes");
                 jobCalender.setJobTypes(jobTypesService.find(Integer.parseInt(jobtypeMap.get("jobTypeId").toString())));             	
             }else
            	 jobCalender.setJobTypes(jobTypesService.find(Integer.parseInt(model.get("jobTypes").toString())));
	                        
        	 if(model.get("maintainanceTypes") instanceof String){  
        		 if(model.get("maintainanceTypes")==""){              		
            		 jobCalender.setMaintainanceTypes(null);
              	}else{
              		jobCalender.setMaintainanceTypes(maintainanceTypesService.find(Integer.parseInt(model.get("jobMtId").toString())));
              	}
         	}else if(model.get("maintainanceTypes") instanceof java.util.LinkedHashMap){
         	   Map<String, Object> maintainanceMap=(Map<String, Object>) model.get("maintainanceTypes");
               jobCalender.setMaintainanceTypes(maintainanceTypesService.find(Integer.parseInt(maintainanceMap.get("jobMtId").toString())));
              
         	}else
              	jobCalender.setMaintainanceTypes(maintainanceTypesService.find(Integer.parseInt(model.get("maintainanceTypes").toString())));      
           
        	 jobCalender.setJobPriority((String) model.get("jobPriority"));
           
        	String ids="";          
            if(model.get("assetName") instanceof String){  
            	jobCalender.setJobAssets(model.get("assetName").toString());
        	}else{
        		 List<Map<String, Object>> listAssets = (ArrayList<Map<String, Object>>) model.get("assetName");// this is what you have already
         		for (Map<String, Object> map1 :listAssets) {
         		    for (Map.Entry<String, Object> entry : map1.entrySet()) {		    	
         		    	if(entry.getKey().equalsIgnoreCase("assetId")){
         		    		ids+=entry.getValue()+",";
         		    	}
         		    }
         		}
         		
         		jobCalender.setJobAssets(ids);
        	}           
    		
    		validator.validate(jobCalender, bindingResult);
			if (bindingResult.hasErrors()) {
				errorResponse.setStatus("FAIL");
				errorResponse.setResult(bindingResult.getAllErrors());
				return errorResponse;
			} else {
				jobCalenderList.add(jobCalender);
			}
          
        }        
        jobCalenderService.saveOrUpdate(jobCalenderList);     
       		
        creatingJobCards(jobCalenderList,userName);       
        return jobCalenderService.readAll();
    }
    
 
	private void creatingJobCards(List<JobCalender> jobCalenderList,String userName) {
		try{
			
			for(JobCalender jc:jobCalenderList){	
				JobCards jcards=new JobCards();				
				jcards.setJobNo(jc.getJobNumber());
				jcards.setJobName(jc.getTitle());
				/*jcards.setJobGroup(jc.getJobGroup());*/
				jcards.setJobDescription(jc.getDescription());
				Timestamp date=new Timestamp(new Date().getTime());
				jcards.setJobDt(date);
				jcards.setJobDepartment(jc.getMaintainanceDepartment());
				jcards.setPerson(jc.getPerson());
				jcards.setJobTypes(jc.getJobTypes());
				jcards.setJobExpectedSla(jc.getExpectedDays());
				jcards.setJobAssets(jc.getJobAssets());
				jcards.setJobPriority(jc.getJobPriority());
				jcards.setMaintainanceTypes(jc.getMaintainanceTypes());
				jcards.setCreatedBy(userName);
				jcards.setLastUpdatedBy(userName);			
				jcards.setJobGeneratedby("USER DEFINED");
				jcards.setStatus("INIT");
				jcards.setSuspendStatus("RESUME");
				jcards.setJobCalender(jc);
				
				jobCardsService.saveRecords(jcards);
				//new Thread(new CreateJobCards(jc,userName)).start();			
			}
		}catch(Exception e){
			
		}
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@RequestMapping(value = "/index/update", method = RequestMethod.POST)
    public @ResponseBody Object update(@RequestBody ArrayList<Map<String, Object>> models,@ModelAttribute JobCalender jobCalender1,
			BindingResult bindingResult, HttpServletRequest request) throws ParseException {
      
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("userId");
		
    	List<JobCalender> list=new ArrayList<JobCalender>();
    	
        for (Map<String, Object> model : models) {         
        	JobCalender jobCalender=new JobCalender();
        	jobCalender.setJobCalenderId((int)model.get("jobCalenderId"));
        	jobCalender.setDescription((String)model.get("description"));
        	jobCalender.setTitle((String)model.get("title"));
            
            SimpleDateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            iso8601.setTimeZone(TimeZone.getTimeZone("UTC"));
            
            jobCalender.setStart(iso8601.parse((String)model.get("start")));
            jobCalender.setEnd(iso8601.parse((String)model.get("end")));
            jobCalender.setIsAllDay((boolean) model.get("isAllDay"));
            jobCalender.setRecurrenceRule((String)model.get("recurrenceRule"));
            jobCalender.setRecurrenceException((String)model.get("recurrenceException"));
            if(model.get("recurrenceId")!=null)
            	jobCalender.setRecurrenceId((Integer)model.get("recurrenceId"));
            jobCalender.setScheduleType(Integer.parseInt(model.get("scheduleType").toString()));
            
            jobCalender.setJobNumber((String) model.get("jobNumber"));
            jobCalender.setDescription((String) model.get("description"));
           /* jobCalender.setJobGroup((String) model.get("jobGroup"));*/
            jobCalender.setExpectedDays(Integer.parseInt(model.get("expectedDays").toString()));
            Map<String, Object> jobtypeMap=(Map<String, Object>) model.get("jobTypes");
            jobCalender.setJobTypes(jobTypesService.find(Integer.parseInt(jobtypeMap.get("jobTypeId").toString())));
        	
            Map<String, Object> departmentMap=(Map<String, Object>) model.get("departmentName");
            jobCalender.setMaintainanceDepartment(departmentService.find(Integer.parseInt(departmentMap.get("departmentId").toString())));
            
            Map<String, Object> maintainanceMap=(Map<String, Object>) model.get("maintainanceTypes");
            jobCalender.setMaintainanceTypes(maintainanceTypesService.find(Integer.parseInt(maintainanceMap.get("jobMtId").toString())));
            
            Map<String, Object> ownerMap=(Map<String, Object>) model.get("pn_Name");
            jobCalender.setPerson(personService.find(Integer.parseInt(ownerMap.get("personId").toString())));
           	
            jobCalender.setJobPriority((String) model.get("jobPriority"));
            
            String ids="";          
			List<Map<String, Object>> listAssets = (ArrayList<Map<String, Object>>) model.get("assetName");// this is what you have already
    		for (Map<String, Object> map1 :listAssets) {
    		    for (Map.Entry<String, Object> entry : map1.entrySet()) {		    	
    		    	if(entry.getKey().equalsIgnoreCase("assetId")){
    		    		ids+=entry.getValue()+",";
    		    	}
    		    }
    		}
    		
    		jobCalender.setJobAssets(ids);
            list.add(jobCalender);
        }  
        String status=updateJobCards(list,userName);
        if(status.equals("success")){
        	jobCalenderService.Update(list);        
            return jobCalenderService.readAll();
        }else if(status.equals("Error")){
        	JsonResponse errorResponse = new JsonResponse();
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", "Error: Contact Administrator");
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
        }
        else{
        	JsonResponse errorResponse = new JsonResponse();
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", "Cannot Update The Schedule,Job Under Process with This Schedule");
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
        }
        
     
    }
    
    private String updateJobCards(List<JobCalender> list,String userName) {
    	try{
    		boolean status=true;
    		for(JobCalender jc:list){
    			List<JobCards> jCardId=jobCardsService.getId(jc);			
    			for(JobCards jca:jCardId){	
    				if(!(jca.getStatus().equalsIgnoreCase("INIT"))){
    					status=false;
    					
    				}else{
    					updatingJobCards(jca,jc,userName);
    				}
    			}
    		}
    		if(status==true){    			
    			return "success";
    		}else{
    			return "failure";
    		}
    	
    	}catch(Exception e){
    		return "Error";
    	}
			
	}
    
    private void updatingJobCards(JobCards jca,JobCalender jc,String userName) {
		try{
			
				
				JobCards jcards=new JobCards();		
				jcards.setJcId(jca.getJcId());
				jcards.setJobNo(jc.getJobNumber());
				jcards.setJobName(jc.getTitle());
				/*jcards.setJobGroup(jc.getJobGroup());*/
				jcards.setJobDescription(jc.getDescription());
				Timestamp date=new Timestamp(new Date().getTime());
				jcards.setJobDt(date);
				jcards.setJobDepartment(jc.getMaintainanceDepartment());
				jcards.setPerson(jc.getPerson());
				jcards.setJobTypes(jc.getJobTypes());
				jcards.setJobExpectedSla(jc.getExpectedDays());
				jcards.setJobAssets(jc.getJobAssets());
				jcards.setJobPriority(jc.getJobPriority());
				jcards.setMaintainanceTypes(jc.getMaintainanceTypes());
				jcards.setCreatedBy(userName);
				jcards.setLastUpdatedBy(userName);			
				jcards.setJobGeneratedby("USER DEFINED");
				jcards.setStatus("INIT");
				jcards.setSuspendStatus("RESUME");
				jcards.setJobCalender(jc);
				
				jobCardsService.update(jcards);
				//new Thread(new CreateJobCards(jc,userName)).start();			
			
		}catch(Exception e){
			
		}
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/jobcalender/index/destroy", method = RequestMethod.POST)
    public @ResponseBody Object destroy(@RequestBody ArrayList<Map<String, Object>> models) {    	  
        
        for (Map<String, Object> model : models) {  
        	JobCalender jc=jobCalenderService.find(Integer.parseInt(model.get("jobCalenderId").toString()));
        	List<JobCards> jCardId=jobCardsService.getId(jc);
        	boolean status=true;
        	for(JobCards jcard:jCardId){
        		if(!(jcard.getStatus().equalsIgnoreCase("INIT"))){
        			status=false;
        		}
        	}
        	
        	if(status==true){
        		jobCalenderService.delete(Integer.parseInt(model.get("jobCalenderId").toString()));
        		 return jobCalenderService.readAll();
        	}else{
        		JsonResponse errorResponse = new JsonResponse();
    			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
    				{
    					put("invalid", "Cannot Delet The Schedule,Job Under Process with This Schedule");
    				}
    			};
    			errorResponse.setStatus("invalid");
    			errorResponse.setResult(errorMapResponse);
    			return errorResponse;
        	}
           
        }  
        return null;
      
    }  
   //@Scheduled(cron="*/20 * * * * ?")
    public void createJobbCardsbyCalender(){
    	
    	List<JobCalender> jobCalenderList=jobCalenderService.findAll();
    
    	for(JobCalender jc:jobCalenderList){   		
    		JobCards jobCards=new JobCards();
    		jobCards.setJobAssets(jc.getJobAssets());    		
    		jobCards.setMaintainanceTypes(jc.getMaintainanceTypes());    		
    		jobCards.setJobName(jc.getTitle());
    		jobCards.setJobDescription(jc.getDescription());
    		jobCards.setJobDepartment(jc.getMaintainanceDepartment());
    		/*jobCards.setJobGroup(jc.getJobGroup());
*/    		jobCards.setJobExpectedSla(jc.getExpectedDays());
    		jobCards.setJobDt(new Timestamp(new Date().getTime()));
    		jobCards.setSuspendStatus("RESUME");
    		jobCards.setPerson(jc.getPerson());
    		jobCards.setJobTypes(jc.getJobTypes());    		
    		jobCards.setJobPriority(jc.getJobPriority());		
    		jobCards.setStatus("INIT");
    		jobCards.setJobNo(jc.getJobNumber());
    		jobCards.setJobGeneratedby("USER DEFINED");	
   			jobCards.setExpectedDate(new Timestamp(jc.getEnd().getTime()));
   			
   			jobCards.setJobCalender(jc);
   			
   			try{   				
   				jobCardsService.save(jobCards);
   			}catch(Exception e){
   				
   			}
    	}
    }
   
}