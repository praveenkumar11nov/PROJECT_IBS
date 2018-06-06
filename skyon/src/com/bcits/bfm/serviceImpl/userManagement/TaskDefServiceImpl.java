package com.bcits.bfm.serviceImpl.userManagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;





import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Permission;
import com.bcits.bfm.model.Role;
import com.bcits.bfm.model.Tasks;
import com.bcits.bfm.service.userManagement.TaskDefService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.BfmLogger;

@Repository
public class TaskDefServiceImpl extends GenericServiceImpl<Tasks> implements
		TaskDefService {

	
	
	 
	@Override
	public Tasks update(Map<String, Object> map, Tasks tasks) {
		tasks = getChildObjects(map, "update", tasks);
		tasks = getBeanObject(map, "update", tasks);
		entityManager.merge(tasks);
		return tasks;

	}

	
	 
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Tasks findById(int tkId) {
		Tasks instance = entityManager.find(Tasks.class, tkId);
		return instance;
	}

	
	
	 
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Tasks> getAll() {
		BfmLogger.logger.info(" INSIDE Tasks method: get All method");
		return entityManager.createNamedQuery("Tasks.findAll").getResultList();
	}

	
	
	 
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Tasks> getTaskId() {
		return entityManager.createNamedQuery("Tasks.findId").getResultList();
	}

	
	
	@SuppressWarnings("rawtypes")
	@Override
	public int count() {
		List al = entityManager.createNamedQuery("Module.count")
				.getResultList();
		int val = Integer.parseInt(al.get(0).toString());
		BfmLogger.logger.info("size " + val);
		return val;
	}

	
	
	 
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Role> getRoleNames(int taskId) {
		BfmLogger.logger
				.info("===================service class==============================");
		return entityManager.createNamedQuery("Tasks.getRoleNames")
				.setParameter("taskId", taskId).getResultList();
	}

	
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Permission> getTaskRolesBasedOntaskId(int taskId) {
		return entityManager
				.createNamedQuery("Tasks.getTaskRolesBasedOntaskId")
				.setParameter("taskId", taskId).getResultList();
	}

	
	 
	@Override
	@SuppressWarnings("unchecked")
	public Tasks getChildObjects(Map<String, Object> map, String type,
			Tasks tasks) {

		Set<Permission> permissions = new HashSet<Permission>();
		List<Role> jsonCombinedRoles = (ArrayList<Role>) map.get("role");

		if (!(jsonCombinedRoles.isEmpty()))
			permissions.addAll(insertNewPermission(jsonCombinedRoles));

		tasks.setPermission(permissions);
		return tasks;
	}

	@SuppressWarnings("unchecked")
	private Collection<? extends Permission> insertNewPermission(
			List<Role> jsonCombinedRoles) {
		Set<Permission> permissions = new HashSet<Permission>();

		for (Iterator<Role> iterator = jsonCombinedRoles.iterator(); iterator
				.hasNext();) {
			Permission permission = new Permission();
			permission.setPrl_id((Integer) ((Map<String, Object>) iterator
					.next()).get("rlId"));
			permissions.add(permission);
		}
		return permissions;
	}

	
	
	@Override
	@SuppressWarnings("unchecked")
	public Tasks getBeanObject(Map<String, Object> map, String type, Tasks tasks) {
		
		/* Users users = new Users(0, (int)model.get("urDnId"),
		 (String)model.get("urLoginName"));
		 */
		final Map<String, Object> productMap = (Map<String, Object>) map
				.get("product");
		final Map<String, Object> moduleMap = (Map<String, Object>) map
				.get("module");
		final Map<String, Object> formMap = (Map<String, Object>) map
				.get("form");
		if (type.equals("update")) {
			tasks.setTk_id((Integer) map.get("tk_id"));
			tasks.setFm_id((Integer) formMap.get("fm_id"));
			tasks.setMd_id((Integer) moduleMap.get("md_id"));
			tasks.setPr_id((Integer) productMap.get("pr_id"));
			tasks.setTk_name((String) map.get("tk_name"));
			tasks.setTk_description((String) map.get("tk_description"));
		} else {
			tasks.setFm_id((Integer) map.get("form"));
			tasks.setMd_id((Integer) map.get("module"));
			tasks.setPr_id((Integer) map.get("product"));
			tasks.setTk_name((String) map.get("tk_name"));
			tasks.setTk_description((String) map.get("tk_description"));
		}

		return tasks;
	}

}
