package com.bcits.bfm.service.facilityManagement;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.StaffNotices;
import com.bcits.bfm.service.GenericService;

public interface StaffNoticesService extends GenericService<StaffNotices> 
{
	public List<StaffNotices> findAll();
	public StaffNotices getStaffNotObject(Map<String, Object> map,
			String string, StaffNotices staffNotice);
	public List<StaffNotices> findById(int personId);
	public List<Object[]> getCountOfNotices(int personId);
	public void uploadNoticeOnId(int snId, Blob noticeDocument, String noticeDocumentType);
	



}



