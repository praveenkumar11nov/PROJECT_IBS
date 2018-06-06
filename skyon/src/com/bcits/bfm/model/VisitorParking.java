package com.bcits.bfm.model;


/**
 * VisitorParking entity. @author MyEclipse Persistence Tools
 */
/*@Entity
@Table(name = "VISITOR_PARKING")
@NamedQueries({	
	
@NamedQuery(name="VisitorParkingRecords",query="select vp from VisitorParking vp order by vp.vpId desc"),
@NamedQuery(name="Visitorparking.VehicleNo",query="select distinct(vp.vehicleNo) from VisitorParking vp"),
@NamedQuery(name="visitorparking_vVisit_visitor",query="select vp.vpId,vp.vpExpectedHours,vp.vpStatus,vp.psId,"
		+ "vm.vmId,vm.vmName,vm.vmContactNo from VisitorParking vp join vp.visitor vm where vm.vmId=:vmId" ),
		@NamedQuery(name="updatevisitorparking" ,query="update VisitorParking vp set vp.vmId=:vmId,vp.vmContactNo=:vmContactNo,vp.vpExpectedHours=:vpExpectedHours,"
				+ "vp.vpStatus=:status,vp.psId=:psId,vp.createdBy=:createdBy,vp.lastUpdatedBy=:lastUpdatedBy,vp.lastUpdatedDt=:lastUpdatedDt where vp.vpId=:vpId"),
				@NamedQuery(name="Visitordeatils",query="select vp.vpId,vp.vpExpectedHours,vp.vpStatus,vp.createdBy,vp.lastUpdatedBy,ps.psId,ps.psSlotNo,"
						+ " vm.vmId,vm.vmName,vm.vmContactNo,vm.vmFrom,vm.createdBy,vm.lastUpdatedBy,vm.lastUpdateDt"
						+ " from VisitorParking  vp JOIN  vp.visitor vm  join vp.parkingSlots ps order by vp.vpId desc"),
					@NamedQuery(name="updateVisitorParkingStatus",query="update VisitorParking vp set vp.vpStatus=:vpStatus where vp.vpId=:vpId"),	
						
						@NamedQuery(name="visitorBasedOnId",query="SELECT v.vmId,v.vmName,v.vmContactNo,v.vmFrom,v.createdBy,v.lastUpdatedBy,v.lastUpdateDt,"
								+ "vv.vvId,vv.gender,vv.category,vv.vpurpose,vv.vinDt,vv.voutDt,vv.createdBy,vv.lastUpdatedBy,vv.vvstatus, p.propertyId, p.property_No,b.blockId,b.blockName,"
								+ "vp.vpId,vp.vpExpectedHours,vp.vpStatus,vp.createdBy,vp.lastUpdatedBy,vp.vehicleNo,ps.psId,ps.psSlotNo,ac.acId,ac.acNo"
								+ "  FROM Visitor v, VisitorVisits vv, VisitorParking vp, Property p, ParkingSlots ps,Blocks b,AccessCards ac WHERE v.vmId = vv.vmId AND v.vmId = vp.vmId AND  vv.acId=ac.acId AND p.propertyId = vv.propertyId AND vp.psId=ps.psId AND p.blockId=b.blockId AND vv.vvstatus='IN' order by v.vmId desc"),
								
								
								@NamedQuery(name="visitorAllRecordBasedOnId",query="SELECT v.vmId,v.vmName,v.vmContactNo,v.vmFrom,v.createdBy,v.lastUpdatedBy,v.lastUpdateDt,"
										+ "vv.vvId,vv.gender,vv.category,vv.vpurpose,vv.vinDt,vv.voutDt,vv.createdBy,vv.lastUpdatedBy,vv.vvstatus, p.propertyId, p.property_No,b.blockId,b.blockName,"
										+ "vp.vpId,vp.vpExpectedHours,vp.vpStatus,vp.createdBy,vp.lastUpdatedBy,vp.vehicleNo,ps.psId,ps.psSlotNo,ac.acId,ac.acNo"
										+ "  FROM Visitor v, VisitorVisits vv, VisitorParking vp, Property p, ParkingSlots ps,Blocks b,AccessCards ac  WHERE v.vmId = vv.vmId AND v.vmId = vp.vmId AND vv.acId=ac.acId AND p.propertyId = vv.propertyId AND vp.psId=ps.psId AND p.blockId=b.blockId  order by v.vmId desc  "),
									
										@NamedQuery(name="visitorRecordSearchBasedOnId",query="SELECT v.vmId,v.vmName,v.vmContactNo,v.vmFrom,v.createdBy,v.lastUpdatedBy,v.lastUpdateDt,"
												+ "vv.vvId,vv.gender,vv.category,vv.vpurpose,vv.vinDt,vv.voutDt,vv.createdBy,vv.lastUpdatedBy,vv.vvstatus, p.propertyId, p.property_No,b.blockId,b.blockName,"
												+ "vp.vpId,vp.vpExpectedHours,vp.vpStatus,vp.createdBy,vp.lastUpdatedBy,vp.vehicleNo,ps.psId,ps.psSlotNo,ac.acId,ac.acNo"
												+ "  FROM Visitor v, VisitorVisits vv, VisitorParking vp, Property p, ParkingSlots ps,Blocks b,AccessCards ac  WHERE v.vmId = vv.vmId AND v.vmId = vp.vmId AND vv.acId=ac.acId AND p.propertyId = vv.propertyId AND vp.psId=ps.psId AND p.blockId=b.blockId AND vv.vvId=:vvId "),
												
										
										@NamedQuery(name="VisitorParking.getVisitorParkingSlot",query="select distinct(ps.psId),ps.psSlotNo from ParkingSlots ps,VisitorParking vp where ps.psOwnership=:psOwnership and ps.psId=vp.psId and ps.status=:status and vp.vpStatus=:vpStatus  "),
										@NamedQuery(name="VisitorParking.getVisitorParkingSlot_VparkingisNull",query="select distinct(ps.psId),ps.psSlotNo from ParkingSlots ps  where ps.psOwnership=:psOwnership  and ps.status=:status"),
										
										
										@NamedQuery(name="VisitorParking.getVisitorParkingSlot",query="SELECT ps.psId,ps.psSlotNo FROM ParkingSlots ps WHERE ps.psId NOT IN (SELECT vp.psId  FROM VisitorParking vp WHERE UPPER(vp.vpStatus) LIKE 'OCCUPIED' ) AND ps.psOwnership LIKE 'VISITORS'"),
										//@NamedQuery(name="VisitorParking.getVisitorrecordbasedOnId",query="select vp from visitorParking vp where vp.vmId=:vmId")
										@NamedQuery(name="updateVisitorParkingSearchRecord",query="update VisitorParking vp set vp.vpStatus=:vpStatus , vp.lastUpdatedBy=:lastUpdatedBy,vp.lastUpdatedDt=:lastUpdatedDt where vp.vpId=:vpId and vp.vmId=:vmId"),
											@NamedQuery(name="VisitorParkingSearchBasedOnId",query="select vp from VisitorParking vp where vp.vmId=:vmId"),
											@NamedQuery(name="ParkingSlots.findIdbasedOnSlotName",query="Select ps.psId from ParkingSlots ps where ps.psSlotNo=:psSlotNo"),
											@NamedQuery(name="ParkingSlotsForFilter",query="select ps.psSlotNo,ps.psId from VisitorParking vp,ParkingSlots ps where vp.psId=ps.psId")
										

	
				
})*/
@SuppressWarnings("serial")
public class VisitorParking implements java.io.Serializable {

	
	/*// Fields
	@Id
	@Column(name = "VP_ID", unique = true, nullable = false, precision = 11, scale = 0)
	@SequenceGenerator(name="visitor_parking_seq",sequenceName="VISTIOR_PARKING_SEQ")
	@GeneratedValue(generator="visitor_parking_seq")
	private int vpId;
	
	
	
	@Column(name = "VM_ID", nullable = false, length = 20)
	private int vmId;
	
	
	@NotEmpty( message = "'Contact No/VisitorName' is not selected")
	@Column(name = "VM_CONTACT_NO", nullable = false, length = 20)
	private String vmContactNo;
	
	
	
	@Column(name = "VP_EXPECTED_HOURS", precision = 11, scale = 0)
	private int vpExpectedHours;
	
	@Column(name = "STATUS", length = 20)
	private String vpStatus;
	
	@Column(name = "PS_ID")	
	//@NotEmpty( message = "'Parking Slot' is not selected")
	private int psId;
	

	@Column(name = "CREATED_BY", length = 20)
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY", length = 20)
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT",length=6)
	private Timestamp lastUpdatedDt;


	
	@Column(name="VEHICLE_NO",length=100)
	private String vehicleNo;
	
	
	
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="VV_ID")
	private VisitorVisits visitorvisit;
	
	
	public String getVehicleNo() {
		return vehicleNo;
	}


	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	@OneToOne
    @JoinColumn(name="PS_ID",nullable=false,insertable=false,updatable=false)
	private ParkingSlots parkingSlots;
	
	
	
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="VM_ID",insertable=false,updatable=false)
	private Visitor visitor;
	
	
	
	
	public VisitorParking() {
	}
	
	
	public VisitorParking(int vpId, int vmId, String vmContactNo,
			int vpExpectedHours, String vpStatus, int psId, String createdBy,
			String lastUpdatedBy, Timestamp lastUpdatedDt) {
		super();
		this.vpId = vpId;
		this.vmId = vmId;
		this.vmContactNo = vmContactNo;
		//this.vpExpectedHours = vpExpectedHours;
		//this.vpStatus = vpStatus;
		//this.psId = psId;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
		//this.vehicleNo=vehicleNo;
	}	
	

	public VisitorParking(int vpId, int vmId, String vmContactNo,
			int vpExpectedHours, String status, int psId,
			String createdBy, String lastUpdatedBy, ParkingSlots parkingSlots,
			Visitor visitor) {
		super();
		this.vpId = vpId;
		this.vmId = vmId;
		this.vmContactNo = vmContactNo;
		//this.vpExpectedHours = vpExpectedHours;
		//this.vpStatus = status;
		//this.psId = psId;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		//this.parkingSlots = parkingSlots;
		this.visitor = visitor;
	}

	public int getVmId() {
		return vmId;
	}

	public void setVmId(int vmId) {
		this.vmId = vmId;
	}	
	

	public Timestamp getLastUpdatedDt() {
		return lastUpdatedDt;
	}


	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	public Visitor getVisitor() {
		return visitor;
	}

	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}
	

	public int getVpId() {
		return this.vpId;
	}

	
	public int getPsId() {
		return psId;
	}

	public void setPsId(int psId) {
		this.psId = psId;
	}

	public ParkingSlots getParkingSlots() {
		return parkingSlots;
	}

	public void setParkingSlots(ParkingSlots parkingSlots) {
		this.parkingSlots = parkingSlots;
	}

	public void setVpId(int vpId) {
		this.vpId = vpId;
	}

	
	public int getVpExpectedHours() {
		return this.vpExpectedHours;
	}

	public void setVpExpectedHours(int vpExpectedHours) {
		this.vpExpectedHours = vpExpectedHours;
	}

	
	public String getVpStatus() {
		return this.vpStatus;
	}

	public void setVpStatus(String status) {
		this.vpStatus = status;
	}

	
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}	

	public String getVmContactNo() {
		return vmContactNo;
	}

	public void setVmContactNo(String vmContactNo) {
		this.vmContactNo = vmContactNo;
	}

*/	
}