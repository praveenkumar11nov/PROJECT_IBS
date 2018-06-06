package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.HelpDeskSettingsEntity;
import com.bcits.bfm.service.helpDeskManagement.HelpDeskAccessSettingsService;
import com.bcits.bfm.service.userManagement.RoleService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class HelpDeskAccessSettingsServiceImpl extends GenericServiceImpl<HelpDeskSettingsEntity> implements HelpDeskAccessSettingsService  {
	
	@Autowired
	RoleService roleService;
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<HelpDeskSettingsEntity> findAll() {
		return getAllDetailsList(entityManager.createNamedQuery("HelpDeskSettingsEntity.findAll").getResultList());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> getAllRoleIds() {		
		return entityManager.createNamedQuery("HelpDeskSettingsEntity.getAllRoleIds").getResultList();
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetailsList(List<?> settingEntities) {		
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> settingMap = null;
		 for (Iterator<?> iterator = settingEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				settingMap = new HashMap<String, Object>();
				
				settingMap.put("settingId", (Integer)values[0]);
				settingMap.put("rlId", (Integer)values[1]);
				settingMap.put("rlName", roleService.find((Integer)values[1]).getRlName());
			
			result.add(settingMap);
	     }
       return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getAllActiveRoles() {
		List<?> rolesList = entityManager.createNamedQuery("HelpDeskSettingsEntity.getAllActiveRoles").getResultList();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> roleMap = null;
		 for (Iterator<?> iterator = rolesList.iterator(); iterator.hasNext();) {
				final Object[] values = (Object[]) iterator.next();
				roleMap = new HashMap<String, Object>();
				
				roleMap.put("rlId", (Integer)values[0]);
				roleMap.put("rlName", (String)values[1]);
				
				result.add(roleMap);
		 }
		 return result;
	}
}
