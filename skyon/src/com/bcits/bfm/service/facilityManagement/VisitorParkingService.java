/*package com.bcits.bfm.service.facilityManagement;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bcits.bfm.model.ParkingSlots;
import com.bcits.bfm.model.VisitorParking;
import com.bcits.bfm.model.VisitorVisits;
import com.bcits.bfm.service.GenericService;

public interface VisitorParkingService extends GenericService<VisitorParking>{

	
	public List<VisitorParking> findAllparking();
	public VisitorParking getVisitorParkingObject(Map<String, Object> map,
			String type, VisitorParking visitorparking);
	
	
	
	public List getVisitorParkingfields() ;
	public List getVisitorParkingList(List visitorParkinglist) ;
	
	
	public VisitorParking getVisitorParking(Map<String, Object> map,
			String type, VisitorParking visitorParking);
	
	public Integer updateVisitorParking(int vpId,int vmId,
			String vmContactNo,int psId,String status,int vpExpectedHours,String createdBy,String lastUpdatedBy,Timestamp lastUpdatedDt);
	
	public List getParkingSlotForFilter();
	public List<VisitorParking>  getVisitorVehicleNoForFilter();
	
	
	public List getVisitorAllRecordBasedOnId();
	
	public List<VisitorParking> getSlotNos();
	
	
	public int count_VisitorParking();
	public List GETPARKINGDETAILS();	
	
	public List getVisitorBasedOnId();
	
	public Integer updateVisitorParkingStatusByButton(int vpId,String vpStatus);
	
	
	public List getVisitorParkingSlotslist();
	public List getParkingSlot_Vparking_Null();
	public List getVisitorRecordSearchBasedonId(int vvId);
	
	
	public VisitorParking getVisitorBasedOnId(int vmId);
	public Integer updateVsisitorParkingBasaedOnSearch(int vpId,int vmId,String vpStatus,Timestamp lastUpdatedDt,String lastUpdatedBy);
	
	public int findIdBasedOnParkingSlot(String psSlotNo);
	
}*/