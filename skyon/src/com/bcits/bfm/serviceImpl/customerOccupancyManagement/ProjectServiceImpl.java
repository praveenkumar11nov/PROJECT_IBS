package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Project;


@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class ProjectServiceImpl 
{
	private static final Log logger = LogFactory.getLog(ProjectServiceImpl.class);
	
	@PersistenceContext(unitName="bfm")
	private EntityManager entityManager;
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	private EntityManager getEntityManager() {
		return entityManager;
	}

	
	public void save(Project project) {
		logger.info("saving Project instance");
		try {
			getEntityManager().persist(project);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	
	public void delete(int id) {
		logger.info("deleting Project instance");
		try {
			getEntityManager().remove(entityManager.find(Project.class, id));
			// em.flush();
			entityManager.flush();
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	
	public Project update(Project entity) {
		logger.info("updating Project instance");
		try {
			Project result = getEntityManager().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public Project findById(int id) {
		logger.info("finding Project instance with id: " + id);
		try {
			Project instance = getEntityManager().find(Project.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}
	
	public int getTowerNo(int id){		
		return entityManager.createNamedQuery("Project.NO_OF_TOWER").setParameter("projectID", id).getFirstResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Project> findAll() {
		logger.info("finding all Project instances");
		 List<Project> project_list=null;
		try {
			
			project_list=entityManager.createNamedQuery("Project.findAll").getResultList();
			
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
		return project_list;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findAllProjects() 
	{
		logger.info("finding all Project instances");
		 List<Project> project_list=null;
		try 
		{
			project_list=entityManager.createNamedQuery("Project.findAllProjects").getResultList();
		} 
		catch (RuntimeException re) 
		{
			logger.error("find all failed", re);
			throw re;
		}
		return getAllProjectList(project_list);
	}

	@SuppressWarnings("rawtypes")
	private List getAllProjectList(List<Project> projectlist) 
	{
		List<Map<String, Object>> allProjectDetailsList = new ArrayList<Map<String, Object>>();

		Map<String, Object> projectMap = null;

		for (Iterator i = projectlist.iterator(); i.hasNext();) 
		{
			projectMap = new HashMap<String, Object>();

			final Object[] values = (Object[]) i.next();
			projectMap.put("projectId", (Integer)values[0]);
			
			projectMap.put("projectName", (String)values[1]);
			
			projectMap.put("no_OF_TOWERS", (Integer)values[2]);
			
			projectMap.put("projectAddress", (String)values[3]);
			
			projectMap.put("project_PINCODE", (Integer)values[4]);
			projectMap.put("projectState", Integer.parseInt((String) values[5]));
			
			projectMap.put("createdBy", (String)values[6]);
			
			projectMap.put("lastUpdatedBy", (String)values[7]);
			
			projectMap.put("projectLocation", (Integer)values[8]);
			
			projectMap.put("stateName", (String)values[9]);
			
			//projectMap.put("no_OF_PROPERTIES", (Integer)values[10]);
			
			projectMap.put("countryName", (String)values[10]);
			
			projectMap.put("projectCountry", (Integer)values[11]);
			
			projectMap.put("projectLocationName", (String)values[12]);
			
			allProjectDetailsList.add(projectMap);
		}
		return allProjectDetailsList;
	}
	
	
	

	@SuppressWarnings("rawtypes")
	public int count(){
		
		List list=entityManager.createNamedQuery("Project.count").getResultList();
		int listcount=Integer.parseInt(list.get(0).toString());
		return listcount;
	}
	
	@SuppressWarnings("rawtypes")
	public List findProjectNames() 
	{
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("Project.findname").getResultList();
	}

	public List<?> getAllUniquePinCodes() {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("Project.getAllUniquePinCodes").getResultList();
	}
}