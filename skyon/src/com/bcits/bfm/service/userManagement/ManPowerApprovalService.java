package com.bcits.bfm.service.userManagement;

import java.util.List;

import com.bcits.bfm.model.ManPowerApproval;
import com.bcits.bfm.patternMasterEntity.TransactionMaster;
import com.bcits.bfm.service.GenericService;

public interface ManPowerApprovalService extends GenericService<ManPowerApproval>{

	List<?> readAllUserDetails(int urId);

	int getpersonId(int urId);

	int getreqInLevel(int personid);

	int getLevel(int personid);

	void updateReqInLevel(int personid,int reqInLevel);

	void updateUserStatus(int urId,String status);

	void deleteApprovalData(int urId);

	int getTransactionId();

	void updatePersonTransId(int urId,int transId);

	//List<TransactionMaster> manpowerNotificationUnread();

	/*List<?> readAllUserDetails(int urId);*/

}
