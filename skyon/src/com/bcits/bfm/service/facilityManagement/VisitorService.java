package com.bcits.bfm.service.facilityManagement;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.ParkingSlots;
import com.bcits.bfm.model.Visitor;
import com.bcits.bfm.service.GenericService;

public interface VisitorService extends GenericService<Visitor>   {

	public abstract List<Visitor> getAllVisitorRecord();
	public Visitor getVisitorObject(Map<String, Object> map,String type,Visitor visitor );
	public List<Object> getAllRecord_join_list();
	

	public List<Integer> getVisitorId(String visitorname,String vmContactNo);
	
	
	public int updateVistor(byte[] doc,int vmId);
	
	
	public Integer  VisitorId(String visitorname,String vmContactNo);
	
	
	public String getvisitorRecord(int vmId);
	public Integer getVisitorIdBasedOnLastUpdateDt(
			Timestamp lastUpdateDt, String username);
	public void updateVisitorDocument(int vmId,Blob blob,String documentNameType);
	public  Blob getImage(int vmId);
	public  Blob getImageBasedOnVisitorName(String vmName);

	public List<ParkingSlots> getSlotNo();
	public List<ParkingSlots> getparkingSlotNoForFilter();
	
	public List<Integer> getSlotId(String psSlotNo);
	public List<ParkingSlots> getpsSlotNoAll();
	public List<Visitor>  getVisitorAddressForFilter();
	public List<Visitor>  getVisitorContactNoForFilter();
	public List<Visitor>  getVisitorNameForFilter();
	
	public int count_VisitorMaster_count();
	public void updateVisitorImage(int vmId,Blob blob);
	public void updateAddress(int vmId,String vmFrom);
	
}