package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.Department;
import com.bcits.bfm.model.JcTeam;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.JcTeamService;
import com.bcits.bfm.service.facilityManagement.JobCardsService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

/**
 * A data access object (DAO) providing persistence and search support for
 * JcTeam entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see .JcTeam
 * @author MyEclipse Persistence Tools
 */
@Repository
public class JcTeamServiceImpl extends GenericServiceImpl<JcTeam> implements JcTeamService {
	private static final Log logger = LogFactory.getLog(JcTeamServiceImpl.class);
	@Autowired
	private JobCardsService jobCardsService;	
	@Autowired
	private PersonService personService;
	@Autowired
	private DepartmentService departmentService;
	
	
	/**
	 * Find all JcTeam entities.
	 * 
	 * @return List<JcTeam> all JcTeam entities
	 */
	@SuppressWarnings("unchecked")
	public List<JcTeam> findAll() {
		logger.info("finding all JcTeam instances");
		try {
			final String queryString = "select model from JcTeam model";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}
	
	@Override
	public List<?> readData(int jcId) {
		List<Map<String, Object>> jcTeam= new ArrayList<Map<String, Object>>();
		for (final JcTeam record :readJobTeam(jcId) ) {
			jcTeam.add(new HashMap<String, Object>() {				
				private static final long serialVersionUID = 1L;
				{				
					put("jctId", record.getJctId());
					//put("jobCards", record.getJobCards().getJcId());
					put("userId", record.getPerson().getPersonId() );					
					put("userName",record.getPerson().getFirstName()+" "+record.getPerson().getLastName());					
					put("departmentName", record.getDepartment().getDept_Name());					
					put("departmentId", record.getDepartment().getDept_Id());					
					put("jctStartDt", ConvertDate.TimeStampString(record.getJctStartDt()));	
					if(record.getJctEndDt()!=null)
						put("jctEndDt", ConvertDate.TimeStampString(record.getJctEndDt()));
					put("jctHours", record.getJctHours());
					put("jctWorktime", record.getJctWorktime());					
					put("createdBy", record.getCreatedBy());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					put("lastUpdatedDate",ConvertDate.TimeStampString(record.getLastUpdatedDt()));
				}
			});
		}
		return jcTeam;	
	}

	public List<JcTeam> readJobTeam(int jcId) {
		@SuppressWarnings("unchecked")
		List<JcTeam> jcTeam = entityManager.createNamedQuery("JobCards.readJobTeam").setParameter("jcId", jobCardsService.find(jcId)).getResultList();
		return jcTeam;
	}

	@Override
	public JcTeam setParameters(int jcId, JcTeam jobTeam, String userName,Map<String, Object> map) {
		jobTeam.setJobCards(jobCardsService.find(jcId));
		if(map.get("userName") instanceof String){
			Person p=personService.find(Integer.parseInt(map.get("userId").toString()));
			jobTeam.setPerson(p);
		}else if(map.get("userName") instanceof java.util.LinkedHashMap) {
			@SuppressWarnings("rawtypes")
			Map m=(Map) map.get("userName");
			@SuppressWarnings("rawtypes")
			Iterator iterator = m.entrySet().iterator();
			while (iterator.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry mapEntry = (Map.Entry) iterator.next();
				if(mapEntry.getKey()=="userId"){
					Person p=personService.find(Integer.parseInt(mapEntry.getValue().toString()));
					jobTeam.setPerson(p);
					break;
				}						
			}
				
		}else{
			Person p=personService.find(Integer.parseInt(map.get("userName").toString()));
			jobTeam.setPerson(p);
		}
		if(map.get("departmentName") instanceof String){
			Department d=departmentService.find(Integer.parseInt(map.get("departmentId").toString()));
			jobTeam.setDepartment(d);
		}else if(map.get("departmentName") instanceof java.util.LinkedHashMap) {
			@SuppressWarnings("rawtypes")
			Map m=(Map) map.get("departmentName");
			@SuppressWarnings("rawtypes")
			Iterator iterator = m.entrySet().iterator();
			while (iterator.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry mapEntry = (Map.Entry) iterator.next();
				if(mapEntry.getKey()=="departmentId"){
					Department d=departmentService.find(Integer.parseInt(mapEntry.getValue().toString()));
					jobTeam.setDepartment(d);
					break;
				}						
			}
		}else{
			Department d=departmentService.find(Integer.parseInt(map.get("departmentName").toString()));
			jobTeam.setDepartment(d);
		}
		jobTeam.setJctStartDt(ConvertDate.formattedDate(map.get("jctStartDt").toString()));
		if(map.get("jctEndDt")!=null)
			jobTeam.setJctEndDt(ConvertDate.formattedDate(map.get("jctEndDt").toString()));
		jobTeam.setJctHours(map.get("jctHours").toString());
		jobTeam.setJctWorktime((String) map.get("jctWorktime"));
		if(map.get("createdBy")==null || map.get("createdBy")=="")
			jobTeam.setCreatedBy(userName);
		jobTeam.setLastUpdatedBy(userName);		
		return jobTeam;
	}

	@Override
	public Object deleteJobTeam(JcTeam jobTeam) {
		logger.info("finding all JcTeam instances");
		try {
			final String queryString = "DELETE JcTeam WHERE jctId="+jobTeam.getJctId();
			Query query = entityManager.createQuery(queryString);
			return query.executeUpdate();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}
	
}