package com.bcits.bfm.service.facilityManagement;

import java.sql.Timestamp;
import java.util.List;

import com.bcits.bfm.model.VisitorVisits;
import com.bcits.bfm.service.GenericService;

public interface VisitorVisitsService extends GenericService<VisitorVisits>  {

	
	/*public VisitorVisits getVisitorVisitsObject(Map<String, Object> map,
			String type, VisitorVisits visitorVisits);*/
	
	
	public List<VisitorVisits> findVisitorIn();
	@SuppressWarnings("rawtypes")
	public List getPropertyNoForFilter();
	public List<Integer>  getVisitorVvIdForFilter(int vmId);
	@SuppressWarnings("rawtypes")
	public List  getVisitorVisitsStatusBasedvInDt(int vmId);
	public List<VisitorVisits> findVisitorVisitsDetailsBasedOnId(int vmId);
	public Integer updateVsisitorVisitStatusByButton(int vvId,String vvstatus,String vpStatus,Timestamp vvLastUpdatedDt,Timestamp voutDt,String reason);
	public int count_visitorVisits();
	public VisitorVisits getVisitorVisitRecordBasedOnId(int vmId);
	public VisitorVisits searchVisitorBasedOnName(String vmName);
	public VisitorVisits searchVisitorBasedOnPropertyNo(String propertyNo);
	public Integer updateVsisitorVisitBasaedOnSearch(int vvId,int vmId,String vvstatus,Timestamp voutDt,Timestamp lastupdatedDt,String lastUpdatedBy);
	@SuppressWarnings("rawtypes")
	public List getParkingSlot_Vparking_Null();
	@SuppressWarnings("rawtypes")
	public List getVisitorParkingSlotslist();
	public int findIdBasedOnParkingSlot(String psSlotNo);
	public List<VisitorVisits>  getVisitorVehicleNoForFilter();
	public List<VisitorVisits> findAll();
	@SuppressWarnings("rawtypes")
	public List getfindAccessCardVisitorNotNull();
	@SuppressWarnings("rawtypes")
	public List getfindAccessCardVisitor();
	//public List<?> findVistors();
	
	
}