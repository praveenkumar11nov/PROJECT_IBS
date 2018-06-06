package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.AssetMaintenanceSchedule;
import com.bcits.bfm.model.JobCalender;
import com.bcits.bfm.model.JobCards;
import com.bcits.bfm.model.JobTypes;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.AssetMaintenanceScheduleService;
import com.bcits.bfm.service.facilityManagement.JobCalenderService;
import com.bcits.bfm.service.facilityManagement.JobCardsService;
import com.bcits.bfm.service.facilityManagement.JobTypesService;
import com.bcits.bfm.service.facilityManagement.MaintainanceDepartmentService;
import com.bcits.bfm.service.facilityManagement.MaintainanceTypesService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;


@Repository
public class AssetMaintenanceScheduleServiceImpl extends GenericServiceImpl<AssetMaintenanceSchedule> implements AssetMaintenanceScheduleService
{
	static Logger logger = LoggerFactory.getLogger(AssetMaintenanceScheduleServiceImpl.class);

	/*@Autowired
	private AssetOwnerShipService assetOwnerShipService;*/

	@Autowired
	private JobCalenderService jobCalenderService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private MaintainanceTypesService maintainanceTypesService;

	@Autowired
	private JobTypesService jobTypesService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private JobCardsService jobCardsService;
	
	@Autowired
	private MaintainanceDepartmentService maintainanceDepartmentService;
	
	@Autowired
	private PersonService personService;
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	

	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() {
		List<AssetMaintenanceSchedule> assetMaintenanceSchedule =  entityManager.createNamedQuery("AssetMaintenanceSchedule.findAllList").getResultList();		
		List<Map<String, Object>> selectedFieldResponse = new ArrayList<Map<String, Object>>();    
		for (final Iterator<?> i = assetMaintenanceSchedule.iterator(); i.hasNext();) {
			
			selectedFieldResponse.add(new HashMap<String, Object>() {{
				final Object[] values = (Object[])i.next();

				put("amsId", (Integer)values[0]);
				put("assetId", (Integer)values[1]);
				put("assetName", (String)values[2]);
				put("taskName", (String)values[3]);
				if((String)values[4]!=null){
				put("taskLocation", (String)values[4]);}
				else{
				put("taskLocation", "N/A");}
				put("mtId", (Integer)values[5]);
				put("maintainanceType", (String)values[6]);
				if((String)values[7]!=null){
				put("taskDescription", (String)values[7]);}
				else{
				put("taskDescription", "N/A");}
				put("toolsRequired", (String)values[8]);
				if((String)values[9]!=null){
				put("expectedResult", (String)values[9]);}
				else{
				put("expectedResult", "N/A");}
				put("taskBestTime", (String)values[10]);
				put("expectedTaskDuration", (Integer)values[11]);
				put("taskFrequency", (String)values[12]);
				
				if((Date)values[13]!=null){
					java.util.Date apsmDateUtil = (Date)values[13];
					java.sql.Date apsmDateSql = new java.sql.Date(apsmDateUtil.getTime());
					put("taskStartDate", apsmDateSql);
					}else{
						put("taskStartDate", "");
					}
				if((Date)values[14]!=null){
					java.util.Date apsmDateUtil = (Date)values[14];
					java.sql.Date apsmDateSql = new java.sql.Date(apsmDateUtil.getTime());
					put("taskEndDate", apsmDateSql);
					}else{
						put("taskEndDate", "");
					}
				if((Date)values[15]!=null){
					java.util.Date apsmDateUtil = (Date)values[15];
					java.sql.Date apsmDateSql = new java.sql.Date(apsmDateUtil.getTime());
					put("taskLastExecuted", apsmDateSql);
					}else{
						put("taskLastExecuted", "");
					}
				
				
				
				put("taskOverdueByDays", (Integer)values[16]);
				
				put("createdBy", (String)values[17]);
				put("lastUpdatedBy", (String)values[18]);
		
				
				java.util.Date lastUpdatedDtUtil = (Date)values[19];
				java.sql.Date lastUpdatedDtSql = new java.sql.Date(lastUpdatedDtUtil.getTime());
				put("lastUpdatedDate", lastUpdatedDtSql);
				
				put("amsStatus", (String)values[20]);
				put("countOfTaskExecution", (Integer)values[21]);
				
				
				put("jtId", (Integer)values[22]);
				put("jtType", (String)values[23]);
				
				put("mtDpIt", (Integer)values[24]);
				put("mtDpName", (String)values[25]);
				put("timeFrom",(String)values[26]);
				put("timeTo",(String)values[27]);
			}});
		}
		return selectedFieldResponse;
	}
	
	
	@SuppressWarnings("unchecked")
	public void populateJobCalender(AssetMaintenanceSchedule assetMaintenanceSchedule,List<DateTime> dates,int i)
	{
		try{
		logger.info("\n\n\nPopulate Jobs");
		String splitDate[] = dates.get(i).toString().split("T");
		Date formTime;
	    Date toTime;
		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
	    SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
	    formTime = parseFormat.parse(assetMaintenanceSchedule.getTimeFrom());
		toTime = parseFormat.parse(assetMaintenanceSchedule.getTimeTo());  	
		
		String jcQuery = "SELECT jc FROM JobCalender jc WHERE jc.start=TO_TIMESTAMP('"+splitDate[0]+" "+displayFormat.format(formTime)+":00','YYYY-MM-DD HH24:MI:SS') AND jc.assetMaintenanceSchedule.amsId="+assetMaintenanceSchedule.getAmsId();
		
		List<JobCalender> jcList = jobCalenderService.executeSimpleQuery(jcQuery);
		if(jcList.size() == 0){
			logger.info("Indise if statements");
			JobCalender jobCalender =  new JobCalender();
			jobCalender.setAssetMaintenanceSchedule(assetMaintenanceSchedule);
			jobCalender.setDescription(assetMaintenanceSchedule.getAsset().getAssetLocationTree().getTreeHierarchy());							
			jobCalender.setStart(dateTimeCalender.getTimestampFromDateAndTime(dates.get((i)).toDateMidnight().toDate(), displayFormat.format(formTime)));
			if(dates.size() != (i+1)){
				/*if(StringUtils.equals(assetMaintenanceSchedule.getTaskFrequency(), "Fortnightly"))
					jobCalender.setEnd(dateTimeCalender.getTimestampFromDateAndTime(dates.get((i+2)).toDateMidnight().toDate(), displayFormat.format(toTime)));
				else*/
					jobCalender.setEnd(dateTimeCalender.getTimestampFromDateAndTime(dates.get((i)).toDateMidnight().toDate(), displayFormat.format(toTime)));
			}
			else
				jobCalender.setEnd(dateTimeCalender.getTimestampFromDateAndTime(dates.get((i)).toDateMidnight().toDate(), displayFormat.format(toTime)));
			
				
			//jobCalender.setExpectedDays(assetMaintenanceSchedule.getExpectedTaskDuration());
			jobCalender.setJobAssets(assetMaintenanceSchedule.getAsset().getAssetId()+"");
			//jobCalender.setJobGroup(assetMaintenanceSchedule.getTaskGroup());
				jobCalender.setIsAllDay(false);
			jobCalender.setScheduleType(1);
			jobCalender.setTitle(assetMaintenanceSchedule.getAsset().getAssetName()+" - "+assetMaintenanceSchedule.getMaintainanceTypes().getMaintainanceType()+" Maintainence");
			jobCalender.setJobNumber(assetMaintenanceSchedule.getAmsId()+""+i);
			jobCalender.setJobPriority("NORMAL");
			JobTypes jt=jobTypesService.find(assetMaintenanceSchedule.getJobTypes().getJtId());
			jobCalender.setJobTypes(jt);
			jobCalender.setExpectedDays(jt.getJtSla());
			/*if(StringUtils.equals(assetMaintenanceSchedule.getTaskFrequency(), "weekly") || StringUtils.equals(assetMaintenanceSchedule.getTaskFrequency(), "Monthly"))
				jobCalender.setRecurrenceRule("FREQ="+assetMaintenanceSchedule.getTaskFrequency().toUpperCase()+";BYDAY=");
			else
				jobCalender.setRecurrenceRule("FREQ="+assetMaintenanceSchedule.getTaskFrequency().toUpperCase());*/
			
			jobCalender.setMaintainanceDepartment(departmentService.find(assetMaintenanceSchedule.getMaintainanceDepartment().getDepartment().getDept_Id()));
			
			List<Users> list = entityManager.createNamedQuery("AssetMaintenanceSchedule.getUsersListBasedOnDeptId").setParameter("deptId", assetMaintenanceSchedule.getMaintainanceDepartment().getDepartment().getDept_Id()).getResultList();
			
			logger.info("\n\nUSERS LIST >>>>>>>>>>>>> "+list.size());
			
			if(list.size()>0)
				jobCalender.setPerson(personService.find(assetMaintenanceSchedule.getMaintainanceDepartment().getUsers().getPerson().getPersonId()));
				jobCalender.setMaintainanceTypes(maintainanceTypesService.find(assetMaintenanceSchedule.getMtId()));
				
			    jobCalenderService.save(jobCalender);
			
			JobCards jcards=new JobCards();				
			jcards.setJobNo(jobCalender.getJobNumber());
			jcards.setJobName(jobCalender.getTitle());
			/*jcards.setJobGroup(jc.getJobGroup());*/
			jcards.setJobDescription(jobCalender.getDescription());
			Timestamp date=new Timestamp(new Date().getTime());
			jcards.setJobDt(date);
			jcards.setJobDepartment(jobCalender.getMaintainanceDepartment());
			jcards.setPerson(jobCalender.getPerson());
			jcards.setJobTypes(jobCalender.getJobTypes());
			jcards.setJobExpectedSla(jobCalender.getExpectedDays());
			jcards.setJobAssets(jobCalender.getJobAssets());
			jcards.setJobPriority(jobCalender.getJobPriority());
			jcards.setMaintainanceTypes(jobCalender.getMaintainanceTypes());
			jcards.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
			jcards.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));			
			jcards.setJobGeneratedby("USER DEFINED");
			jcards.setStatus("INIT");
			jcards.setSuspendStatus("RESUME");
			jcards.setJobCalender(jobCalender);
			
			jobCardsService.saveRecords(jcards);
		}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	@Override
	public void setAmsStatus(int amsId, String operation,
			HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
			if(StringUtils.containsIgnoreCase(operation, "Approved")){
				entityManager.createNamedQuery("AssetMaintenanceSchedule.setAmsStatus").setParameter("amsStatus", operation).setParameter("amsId", amsId).executeUpdate();
			out.write("Maintainance Schedule is Approved");
			}
			else{
				entityManager.createNamedQuery("AssetMaintenanceSchedule.setAmsStatus").setParameter("amsStatus", operation).setParameter("amsId", amsId).executeUpdate();
			out.write("Maintainance Schedule is Rejected");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

}
