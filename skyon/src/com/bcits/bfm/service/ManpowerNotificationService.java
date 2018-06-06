package com.bcits.bfm.service;

import java.util.List;

import com.bcits.bfm.model.ManPowerNotification;
import com.bcits.bfm.patternMasterEntity.TransactionDetail;


public interface ManpowerNotificationService extends GenericService<ManPowerNotification>{

	List<ManPowerNotification> manpowerNotificationUnread(int designationId);

	void updateManPowerNotificationStatus(int manPowerId, int desigId);

	List<TransactionDetail> getDesignations(int transId);
	int getDesignationId(String userName);

}
