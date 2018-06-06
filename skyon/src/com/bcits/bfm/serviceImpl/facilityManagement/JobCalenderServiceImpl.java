package com.bcits.bfm.serviceImpl.facilityManagement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.Asset;
import com.bcits.bfm.model.JobCalender;
import com.bcits.bfm.service.facilityManagement.AssetService;
import com.bcits.bfm.service.facilityManagement.JobCalenderService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

/**
 * A data access object (DAO) providing persistence and search support for
 * JobCalender entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see .JobCalender
 * @author MyEclipse Persistence Tools
 */
@Repository
public class JobCalenderServiceImpl extends GenericServiceImpl<JobCalender> implements JobCalenderService {
	private static final Log logger = LogFactory.getLog(JobCalenderServiceImpl.class);
	
	@Autowired
	private AssetService assetService;
	
	// property constants
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static final String IS_ALL_DAY = "isAllDay";
	public static final String RECURRENCE_RULE = "recurrenceRule";
	public static final String RECURRENCE_ID = "recurrenceId";
	public static final String RECURENCE_EXCEPTION = "recurenceException";
	public static final String SCHEDULE_TYPE = "scheduleType";
	public static final String JOB_NUMBER = "jobNumber";
	public static final String JOB_GROUP = "jobGroup";
	public static final String EXPECTED_DAYS = "expectedDays";
	public static final String JOB_PRIORITY = "jobPriority";
	public static final String JOB_ASSETS = "jobAssets";
	


	public JobCalender findById(int id) {
		logger.info("finding JobCalender instance with id: " + id);
		try {
			JobCalender instance = entityManager.find(JobCalender.class,
					id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all JobCalender entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the JobCalender property to query
	 * @param value
	 *            the property value to match
	 * @return List<JobCalender> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<JobCalender> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding JobCalender instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from JobCalender model where model."
					+ propertyName + "= :propertyValue";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("propertyValue", value);
			return query.getResultList();
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	public List<JobCalender> findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List<JobCalender> findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List<JobCalender> findByIsAllDay(Object isAllDay) {
		return findByProperty(IS_ALL_DAY, isAllDay);
	}

	public List<JobCalender> findByRecurrenceRule(Object recurrenceRule) {
		return findByProperty(RECURRENCE_RULE, recurrenceRule);
	}

	public List<JobCalender> findByRecurrenceId(Object recurrenceId) {
		return findByProperty(RECURRENCE_ID, recurrenceId);
	}

	public List<JobCalender> findByRecurenceException(Object recurenceException) {
		return findByProperty(RECURENCE_EXCEPTION, recurenceException);
	}

	public List<JobCalender> findByJobNumber(Object jobNumber) {
		return findByProperty(JOB_NUMBER, jobNumber);
	}

	public List<JobCalender> findByJobGroup(Object jobGroup) {
		return findByProperty(JOB_GROUP, jobGroup);
	}

	public List<JobCalender> findByExpectedDays(Object expectedDays) {
		return findByProperty(EXPECTED_DAYS, expectedDays);
	}

	public List<JobCalender> findByScheduleType(Object scheduleType) {
		return findByProperty(SCHEDULE_TYPE, scheduleType);
	}

	public List<JobCalender> findByJobPriority(Object jobPriority) {
		return findByProperty(JOB_PRIORITY, jobPriority);
	}

	public List<JobCalender> findByJobAssets(Object jobAssets) {
		return findByProperty(JOB_ASSETS, jobAssets);
	}

	/**
	 * Find all JobCalender entities.
	 * 
	 * @return List<JobCalender> all JobCalender entities
	 */
	@SuppressWarnings("unchecked")
	public List<JobCalender> findAll() {
		logger.info("finding all JobCalender instances");
	    return entityManager.createNamedQuery("JobCalender.findAllJobCalenderList").getResultList();
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> readAll() {
		List<Map<String, Object>> jobList = new ArrayList<Map<String, Object>>();
		for (final JobCalender record : findAll()) {
			jobList.add(new HashMap<String, Object>() {
				{
					put("jobCalenderId", record.getJobCalenderId());
					put("scheduleType", record.getScheduleType());
					put("title", record.getTitle());
					put("description", record.getDescription());
					put("isAllDay", record.getIsAllDay());
					put("recurrenceRule", record.getRecurrenceRule());					
					put("recurrenceId", record.getRecurrenceId());
					put("recurenceException", record.getRecurrenceException());
					put("start",ConvertDate.TimeStampString(new Timestamp(record.getStart().getTime())));
					put("end", ConvertDate.TimeStampString(new Timestamp(record.getEnd().getTime())));
					
					put("jobNumber", record.getJobNumber());
					put("jobGroup", record.getJobGroup());
					put("expectedDays", record.getExpectedDays());
					put("jobPriority", record.getJobPriority());				
					
					Map<String,Object> mapMaintainance=new HashMap<String,Object>();
					mapMaintainance.put("jobMt", record.getMaintainanceTypes().getMaintainanceType());
					mapMaintainance.put("jobMtId", record.getMaintainanceTypes().getMtId());
					put("maintainanceTypes", mapMaintainance);					
					
					Map<String,Object> mapDepartment=new HashMap<String,Object>();
					mapDepartment.put("departmentName", record.getMaintainanceDepartment().getDept_Name());
					mapDepartment.put("departmentId", record.getMaintainanceDepartment().getDept_Id());
					put("departmentName", mapDepartment);					
					
					Map<String,Object> mapJobtype=new HashMap<String,Object>();
					mapJobtype.put("jobType", record.getJobTypes().getJtType());
					mapJobtype.put("jobTypeId", record.getJobTypes().getJtId());
					put("jobTypes", mapJobtype);
					
					put("expectedDays", record.getExpectedDays());
					
					Map<String,Object> mapjobOwner=new HashMap<String,Object>();
					
					String lastName="";
					try{
						if(record.getPerson().getLastName()!=null){
							lastName=record.getPerson().getLastName();
						}
						mapjobOwner.put("pn_Name", record.getPerson().getFirstName()+" "+lastName);
						mapjobOwner.put("personId", record.getPerson().getPersonId());
						put("pn_Name", mapjobOwner);
						
					}catch(Exception e){
					}
					
					
					List<Map<String,Object>> assetList = new ArrayList<Map<String,Object>>();					
					if(record.getJobAssets()!=null){
						String assets[]=record.getJobAssets().split(",");
						Map<String,Object> mapobject = null;
						for (String value : assets) {
							mapobject = new HashMap<String,Object>();	
							logger.info("Asset Id:---------------------"+value);
							Asset asset= assetService.find(Integer.parseInt(value));
							if(asset!=null){
							logger.info("Asset Name:---------------------"+asset.getAssetName());
							mapobject.put("assetName", asset.getAssetName());
							mapobject.put("assetId", asset.getAssetId());
						    }
							assetList.add(mapobject);
						}						
					}					
					put("assetName",assetList);	
					put("jobAssetsDummy",assetList);	
					
				}
			});
		}		
		return jobList;
	}

	@Override
	public void saveOrUpdate(List<JobCalender> jobCalenderList) {
		 for (JobCalender task : jobCalenderList) {
	            save(task);
	        }
	}
	@Override
	public void Update(List<JobCalender> jobCalenderList) {
		for (JobCalender task : jobCalenderList) {
			update(task);
		}
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findRequiredFieldOnAmsId(int amsId) {
		return entityManager.createNamedQuery("JobCalender.findRequiredFieldOnAmsId").setParameter("amsId",amsId).getResultList();
	}
	
	@Override
	public void deleteJobCalender(int jobCalender) {
		logger.info("finding all JobCalender instances");
		
		entityManager.createNamedQuery("JobCalender.deleteJobCalender").setParameter("jobCalender",jobCalender).executeUpdate();
	
	}	
	
}