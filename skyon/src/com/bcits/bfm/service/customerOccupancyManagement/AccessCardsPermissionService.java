package com.bcits.bfm.service.customerOccupancyManagement;

import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.AccessCardsPermission;
import com.bcits.bfm.service.GenericService;

public interface AccessCardsPermissionService extends GenericService<AccessCardsPermission> 
{

	List<AccessCardsPermission> findAll();

	List<?> findOnacId(int i);

	AccessCardsPermission getaccessCardsPermisssionObject(
			Map<String, Object> map, String string,
			AccessCardsPermission accessCardsPermisssion);

}
