package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * VisitorVisits entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "VISITOR_VISITS")
@NamedQueries({
		@NamedQuery(name = "ParkingSlot_No_AllSlots", query = "select distinct(ps) from ParkingSlots ps where ps.psOwnership =:psOwnership AND ps.status=:status"),

		@NamedQuery(name = "ParkingSlots.filterparkingSlotNo", query = "select distinct(ps.psSlotNo) from ParkingSlots ps,VisitorVisits vv where vv.psId=ps.psId "),

		@NamedQuery(name = "ParkingSlotId", query = "select ps.psId from ParkingSlots ps where ps.psSlotNo=:psSlotNo"),

		@NamedQuery(name = "visitorvisit.vvId", query = "select distinct(vv.vvId) from VisitorVisits vv where vv.vmId=:vmId"),

		@NamedQuery(name = "VisitorVistisDetails", query = "select vv from VisitorVisits vv where vv.vvstatus=:vvstatus order by vv.vvId desc"),
		
		//@NamedQuery(name = "VisitorVistisDetails", query = "select vv.vvId,vv.category,vv.vvstatus,vv.vpurpose,vv.vehicleNo,vv.reason,vv.vinDt,vv.voutDt,vv.vpExpectedHours,vv.vpStatus,vv.vvCreatedBy,vv.vvLastUpdatedBy,vv.vvLastUpdatedDt,ac.acId,ac.acNo,p.blocks.blockId,p.blocks.blockName,p.property_No,p.propertyId,ps.psId,ps.psSlotNo,v.vmId,v.vmContactNo,v.vmFrom,v.vmName,v.gender,v.vmCreatedBy,v.documentNameType,v.vmLastUpdatedBy,v.vmLastUpdatedDt from VisitorVisits vv INNER JOIN vv.accessCards ac  INNER JOIN vv.property p INNER JOIN vv.parkingSlots ps INNER JOIN vv.visitor v where vv.vvstatus=:vvstatus order by vv.vvId desc"),

		@NamedQuery(name = "VisitorVisits.findAll", query = "select vv from VisitorVisits vv  order by vv.vvId desc"),

		@NamedQuery(name = "visitorvisit.update", query = "UPDATE VisitorVisits vv SET  vv.vvstatus = :vvStatus ,vv.voutDt=:voutDt WHERE vv.vvId = :vvId"),

		@NamedQuery(name = "VisitorVisits.getVisitorStatusBasedOnLastUpdatedDt", query = "select vv.vvstatus from VisitorVisits vv where  vv.vmId=:vmId"),

		@NamedQuery(name = "VisitorVistisDetailsbasedOnId", query = "select vv from VisitorVisits vv where vv.vmId=:vmId order by vv.vvId desc"),

		@NamedQuery(name = "updateVisitorVisitsStatusButton", query = "update VisitorVisits vv set vv.vvstatus=:vvstatus,vv.vpStatus=:vpStatus, vv.voutDt=:voutDt,vv.vvLastUpdatedDt=:vvLastUpdatedDt,vv.reason = :reason where vv.vvId=:vvId"),

		@NamedQuery(name = "updateVisitorVisitSearchRecord", query = "update VisitorVisits vv set vv.vvstatus=:vvstatus , vv.voutDt=:voutDt, vv.vvLastUpdatedBy=:vvLastUpdatedBy,vv.vvLastUpdatedDt=:vvLastUpdatedDt  where vv.vvId=:vvId and vv.vmId=:vmId"),

		@NamedQuery(name = "FindPropertyNoForFilter", query = "select distinct(p.property_No) from Property p,VisitorVisits vv where vv.propertyId=p.propertyId"),

		@NamedQuery(name = "VisitorVisits.getVisitorParkingSlot_VparkingisNull", query = "select distinct(ps.psId),ps.psSlotNo from ParkingSlots ps  where ps.psOwnership=:psOwnership  and ps.status=:status"),

		@NamedQuery(name = "VisitorVisits.getVisitorParkingSlot", query = "SELECT ps.psId,ps.psSlotNo FROM ParkingSlots ps WHERE ps.psId NOT IN (SELECT vv.psId  FROM VisitorVisits vv WHERE UPPER(vv.vpStatus) LIKE 'OCCUPIED' ) AND ps.status LIKE 'true' AND  ps.psOwnership LIKE 'VISITORS'"),

		@NamedQuery(name = "ParkingSlots.findIdbasedOnSlotName", query = "Select ps.psId from ParkingSlots ps where ps.psSlotNo=:psSlotNo"),

		@NamedQuery(name = "VisitorVisits.VehicleNo", query = "select distinct(vv.vehicleNo) from VisitorVisits vv"),

		@NamedQuery(name = "VisitorVisitrecordSearchBasedOnId", query = "select vv from VisitorVisits vv where vv.vmId=:vmId order by vv.vvId desc"),
		@NamedQuery(name = "SearchVisitorBasedOnVisitorName", query = "select vv from VisitorVisits vv INNER JOIN vv.visitor v where v.vmName = :vmName"),
		@NamedQuery(name = "SearchVisitorBasedOnPropertyNo", query = "select vv from VisitorVisits vv INNER JOIN vv.property p where p.property_No = :propertyNo"),

		@NamedQuery(name = "AccessCards.findAccessCard", query = "select distinct(ac.acId), ac.acNo from AccessCards ac where UPPER(ac.status) LIKE 'ACTIVE' AND ac.acType='Visitor'"),

		@NamedQuery(name = "AccessCards.findAccessCard_visitorVisitNotNull", query = "select distinct(ac.acId),ac.acNo from AccessCards ac where ac.acId NOT IN (select vv.acId from VisitorVisits vv WHERE UPPER(vv.vvstatus) LIKE 'IN' AND vv.acId IS NOT NULL) AND ac.status LIKE 'Active' AND ac.acType='Visitor'"),

//		@NamedQuery(name = "VisitorVistisDetails.readData", query = "SELECT vv.vvId,vv.category,vv.vvstatus,vv.vpurpose,vv.vehicleNo,vv.reason,vv.vinDt,vv.voutDt,vv.vpExpectedHours,vv.vpStatus,vv.vvCreatedBy,vv.vvLastUpdatedBy,vv.vvLastUpdatedDt,ac.acId,ac.acNo,b.blockId,b.blockName,p.property_No,p.propertyId,ps.psId,ps.psSlotNo,vi.vmId,vi.vmContactNo,vi.vmFrom,vi.vmName,vi.gender,vi.vmCreatedBy,vi.documentNameType,vi.vmLastUpdatedBy,vi.vmLastUpdatedDt FROM VisitorVisits vv INNER JOIN vv.accessCards ac INNER JOIN vv.property p INNER JOIN p.blocks b INNER JOIN vv.parkingSlots ps INNER JOIN vv.visitor vi where vv.vvstatus=:vvstatus ORDER BY vv.vvId DESC")
})
public class VisitorVisits implements java.io.Serializable {

	

	@Id
	@Column(name = "VV_ID", unique = true, nullable = false, precision = 11, scale = 0)
	@SequenceGenerator(name = "visitor_seq", sequenceName = "VISITOR_VISITS_SEQ")
	@GeneratedValue(generator = "visitor_seq")
	private int vvId;

	@Column(name = "VM_ID")
	private int vmId;

	@Column(name = "AC_ID", nullable = false, precision = 11, scale = 0)
	private Integer acId;

	//@Min(value = 1, message = "'Property' is not selected")
	@Column(name = "PROPERTY_ID", nullable = false, precision = 11, scale = 0)
	private int propertyId;

	@Column(name = "V_PURPOSE", length = 100)
	private String vpurpose;

	@Column(name = "V_IN_DT", length = 11)
	private Timestamp vinDt;

	@Column(name = "V_OUT_DT", length = 11)
	private Timestamp voutDt;

	@Column(name = "VV_CREATED_BY", length = 20)
	private String vvCreatedBy;

	@Column(name = "VV_LAST_UPDATED_BY", length = 20)
	private String vvLastUpdatedBy;

	@Column(name = "VV_STATUS", length = 20)
	private String vvstatus;

	@Column(name = "VV_LAST_UPDATED_DT", length = 6)
	private Timestamp vvLastUpdatedDt;

	
	@Column(name = "CATEGORY", length = 45)
	private String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Timestamp getVvLastUpdatedDt() {
		return vvLastUpdatedDt;
	}

	public void setVvLastUpdatedDt(Timestamp lastupdatedDt) {
		this.vvLastUpdatedDt = lastupdatedDt;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "VM_ID", nullable = false, insertable = false, updatable = false)
	private Visitor visitor;

	public AccessCards getAccessCards() {
		return accessCards;
	}

	public void setAccessCards(AccessCards accessCards) {
		this.accessCards = accessCards;
	}

	@OneToOne
	@JoinColumn(name = "PROPERTY_ID", nullable = false, insertable = false, updatable = false)
	private Property property;

	@OneToOne
	@JoinColumn(name = "AC_ID", nullable = false, insertable = false, updatable = false)
	private AccessCards accessCards;

	@Column(name = "VP_EXPECTED_HOURS", precision = 11, scale = 0)
	private int vpExpectedHours;

	@Column(name = "STATUS", length = 20)
	private String vpStatus;

	@Column(name = "PS_ID")
	private Integer psId;
	 
	@Column(name = "VEHICLE_NO", length = 100)
	//@NotEmpty(message = "'Vehicle Number' can not be Empty")
	//@Min(value = 1, message = "'Department' is not selected")
	private String vehicleNo;
	
	@Column(name = "REASON", length = 20)
	private String reason;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@OneToOne
	@JoinColumn(name = "PS_ID", nullable = false, insertable = false, updatable = false)
	private ParkingSlots parkingSlots;

	public Visitor getVisitor() {
		return visitor;
	}

	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getVvstatus() {
		return vvstatus;
	}

	public void setVvstatus(String vvstatus) {
		this.vvstatus = vvstatus;
	}

	/** default constructor */
	public VisitorVisits() {
	}

	/** minimal constructor */
	public VisitorVisits(int vvId, int vmId, int acId, int propertyId) {
		this.vvId = vvId;
		this.vmId = vmId;
		this.acId = acId;
		this.propertyId = propertyId;
	}

	public int getVmId() {
		return vmId;
	}

	public VisitorVisits(int vvId, int vmId, Integer acId, int propertyId,
			String vpurpose, Timestamp vinDt, Timestamp voutDt,
			String createdBy, String lastUpdatedBy, String vvstatus,
			Timestamp lastupdatedDt, String category, int vpExpectedHours,
			String vpStatus, Integer psId, String vehicleNo) {
		super();
		this.vvId = vvId;
		this.vmId = vmId;
		this.acId = acId;
		this.propertyId = propertyId;
		this.vpurpose = vpurpose;
		this.vinDt = vinDt;
		this.voutDt = voutDt;
		this.vvCreatedBy = createdBy;
		this.vvLastUpdatedBy = lastUpdatedBy;
		this.vvstatus = vvstatus;
		this.vvLastUpdatedDt = lastupdatedDt;
		this.category = category;
		this.vpExpectedHours = vpExpectedHours;
		this.vpStatus = vpStatus;
		this.psId = psId;
		this.vehicleNo = vehicleNo;
	}

	public void setVmId(int vmId) {
		this.vmId = vmId;
	}

	public int getVvId() {
		return this.vvId;
	}

	public void setVvId(int vvId) {
		this.vvId = vvId;
	}

	public Integer getAcId() {
		return this.acId;
	}

	public void setAcId(Integer acId) {
		this.acId = acId;
	}

	public int getPropertyId() {
		return this.propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getVPurpose() {
		return this.vpurpose;
	}

	public void setVPurpose(String VPurpose) {
		this.vpurpose = VPurpose;
	}

	public Timestamp getVInDt() {
		return this.vinDt;
	}

	public void setVInDt(Timestamp vinDt) {
		this.vinDt = vinDt;
	}

	public Timestamp getVOutDt() {
		return this.voutDt;
	}

	public void setVOutDt(Timestamp voutDt) {
		this.voutDt = voutDt;
	}

	public String getVvCreatedBy() {
		return this.vvCreatedBy;
	}

	public void setVvCreatedBy(String createdBy) {
		this.vvCreatedBy = createdBy;
	}

	public String getVvLastUpdatedBy() {
		return this.vvLastUpdatedBy;
	}

	public void setVvLastUpdatedBy(String lastUpdatedBy) {
		this.vvLastUpdatedBy = lastUpdatedBy;
	}

	public Integer getPsId() {
		return psId;
	}

	public void setPsId(Integer psId) {
		this.psId = psId;
	}

	public ParkingSlots getParkingSlots() {
		return parkingSlots;
	}

	public void setParkingSlots(ParkingSlots parkingSlots) {
		this.parkingSlots = parkingSlots;
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

}