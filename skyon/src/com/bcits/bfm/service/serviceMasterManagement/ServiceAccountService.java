package com.bcits.bfm.service.serviceMasterManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ServiceAccountEntity;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.service.GenericService;

public interface ServiceAccountService extends GenericService<ServiceAccountEntity> {
	
	List<ServiceAccountEntity> findALL();
	List<ServiceAccountEntity> findAllById(int serviceMasterId);
	void updateAccountStatusFromInnerGrid(int serviceAccoutId,HttpServletResponse response);
	ServiceMastersEntity getServiceAccount(int serviceMasterId);
	void ledgerEndDateUpdate(int serviceAccoutId, HttpServletResponse response);

}
