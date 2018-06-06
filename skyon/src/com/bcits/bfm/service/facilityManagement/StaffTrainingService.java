package com.bcits.bfm.service.facilityManagement;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.StaffTraining;
import com.bcits.bfm.service.GenericService;

public interface StaffTrainingService extends GenericService<StaffTraining> 
{
	public List<StaffTraining> findAll();
	/*public abstract MailRoom getBeanObject(Map<String, Object> map, String string,MailRoom mailroom);*/
	public List<StaffTraining> findById(int id);
	public StaffTraining getStaffTrainObject(Map<String, Object> map,
			String string, StaffTraining staffTraining);
	public void uploadCertificateOnId(int stId, Blob trainingDocument,String docType);
	public List<?> getUniqueTrainingName(String className, String attribute,
			String attribute1, int selectedRowId);
}

