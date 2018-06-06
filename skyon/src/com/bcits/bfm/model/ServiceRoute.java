package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name="SERVICE_ROUTE")
@NamedQueries({
	
	@NamedQuery(name="ServiceRoute.GetAllServiceRoute",query="SELECT e FROM ServiceRoute e "),
    
     @NamedQuery(name="ServiceRoute.GETStaffName",query="SELECT p.personId,p.firstName,p.middleName,p.lastName FROM Person p Where p.personType='Staff' "),
     @NamedQuery(name="ServiceRoute.GetServiceRouteOnSrId",query="SELECT e FROM ServiceRoute e WHERE e.srId=:srId"),
     @NamedQuery(name="ServiceRoute.GetPersonDetail",query="SELECT p FROM Person p WHERE p.personId=:personId")
})

public class ServiceRoute implements java.io.Serializable{

	@Id
	@Column(name="SR_ID",unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name="SERVICE_ROUTE_ID_SEQ",sequenceName="SERVICE_ROUTE_ID_SEQ")
	@GeneratedValue(generator="SERVICE_ROUTE_ID_SEQ")
	private int srId;
	
	@Column(name="PERSON_ID")
	private Integer personId;
	
	@JsonIgnore
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "PERSON_ID", insertable = false, updatable = false)
	private Person personobj;
	
	@Column(name="ROUTE_NAME")
	private String routeName;
	
	@Column(name="READ_DAY")
	private Date readDay;
	
	@Column(name="ESTIMATION_DAY")
	private Integer estimationDay;
	
	@Column(name="SUB_ROUTE_NAME")
	private String subRouteName;


	public String getRouteName() {
		return routeName;
	}

	public Integer getEstimationDay() {
		return estimationDay;
	}

	public void setEstimationDay(Integer estimationDay) {
		this.estimationDay = estimationDay;
	}

	public Integer getBillFrom() {
		return billFrom;
	}


	public void setBillFrom(Integer billFrom) {
		this.billFrom = billFrom;
	}


	public Integer getBillTo() {
		return billTo;
	}


	public void setBillTo(Integer billTo) {
		this.billTo = billTo;
	}

	@Column(name="BILL_WINDOW_FROM")
	private Integer billFrom;
	
	@Column(name="BILL_WINDOW_TO")
	private Integer billTo;
	
	@Column(name="READ_CYCLE")
	private String readCycle;
	
	@Column(name="ROUTE_DESCRIPTION")
	private String routeDescription;
	
	@Column(name="LAST_READ_DATE")
	private Timestamp LastReadDate;
	
	/*@Column(name="SR_NODE_TYPE")
	private String serviceRouteNodeType;*/
	
	/*public String getServiceRouteNodeType() {
		return serviceRouteNodeType;
	}


	public void setServiceRouteNodeType(String serviceRouteNodeType) {
		this.serviceRouteNodeType = serviceRouteNodeType;
	}*/

	/*
	public void setSpid(Integer spid) {
		this.spid = spid;
	}





	public void setMrId(Integer mrId) {
		this.mrId = mrId;
	}
*/

	public ServiceRoute(){}
	
	
	/*public ServiceRoute(int srId, int propertyId, String routeName, Integer parentId,
			String treehirerachry, Date readDay, String routeSreial,
			String readCycle, String routeDescription, Timestamp lastReadDate,
			String createdBy, String lastupdatedBy, Timestamp lastUpdatedDate) {
		super();
		this.srId = srId;
		this.propertyId = propertyId;
		this.routeName = routeName;
		this.parentId = parentId;
		this.treehirerachry = treehirerachry;
		this.readDay = readDay;
		
		ReadCycle = readCycle;
		routeDescription = routeDescription;
		LastReadDate = lastReadDate;
		this.createdBy = createdBy;
		this.lastupdatedBy = lastupdatedBy;
		this.lastUpdatedDate = lastUpdatedDate;
	}*/
	
	


	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}


	public String getSubRouteName() {
		return subRouteName;
	}


	public void setSubRouteName(String subRouteName) {
		this.subRouteName = subRouteName;
	}

	public Timestamp getLastReadDate() {
		return LastReadDate;
	}


	public void setLastReadDate(Timestamp lastReadDate) {
		LastReadDate = lastReadDate;
	}


	/*public Integer getMrId() {
		return mrId;
	}


	public void setMrId(Integer mrId) {
		this.mrId = mrId;
	}*/


	


	


	public int getSrId() {
		return srId;
	}

	public void setSrId(int srId) {
		this.srId = srId;
	}

	/*public int getSpid() {
		return spid;
	}

	public void setSpid(int spid) {
		this.spid = spid;
	}*/

	

	

	

	public Date getReadDay() {
		return readDay;
	}

	public void setReadDay(Date readDay) {
		this.readDay = readDay;
	}

	/*public String getRouteSreial() {
		return RouteSreial;
	}

	public void setRouteSreial(String routeSreial) {
		RouteSreial = routeSreial;
	}
*/
	

	/*public Timestamp getLastReadDate() {
		return LastReadDate;
	}

	public void setLastReadDate(Timestamp lastReadDate) {
		LastReadDate = lastReadDate;
	}*/

	public String getCreatedBy() {
		return createdBy;
	}

	public Integer getPersonId() {
		return personId;
	}


	public void setPersonId(Integer personId) {
		this.personId = personId;
	}


	public Person getPersonobj() {
		return personobj;
	}


	public void setPersonobj(Person personobj) {
		this.personobj = personobj;
	}


	public String getReadCycle() {
		return readCycle;
	}


	public void setReadCycle(String readCycle) {
		this.readCycle = readCycle;
	}


	public String getRouteDescription() {
		return routeDescription;
	}


	public void setRouteDescription(String routeDescription) {
		this.routeDescription = routeDescription;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastupdatedBy() {
		return lastupdatedBy;
	}

	public void setLastupdatedBy(String lastupdatedBy) {
		this.lastupdatedBy = lastupdatedBy;
	}

	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	/*@Column(name="SP_ID")
	private Integer spId;*/
	
	/*@OneToOne	 
	@JoinColumn(name = "ELM_ID", insertable = false, updatable = false)
	private ElectricityMetersEntity elmMeterentity;*/
	
	

    
	/*public ElectricityMetersEntity getElmMeterentity() {
		return elmMeterentity;
	}


	public void setElmMeterentity(ElectricityMetersEntity elmMeterentity) {
		this.elmMeterentity = elmMeterentity;
	}*/

	/*@OneToOne	 
	@JoinColumn(name = "SP_ID", insertable = false, updatable = false)
	private ServicePointEntity entity;*/
	
	
	/*public Integer getSpId() {
		return spId;
	}


	public void setSpId(Integer spId) {
		this.spId = spId;
	}*/

	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastupdatedBy;
	
	@Column(name="LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;
	
	
	
	
	
	/*@PrePersist
	protected void onCreate() {
		lastupdatedBy = (String) SessionData.getUserDetails().get("userID");
		createdBy= (String) SessionData.getUserDetails().get("userID");
	}

	@PreUpdate
	protected void onUpdate() {
		lastupdatedBy = (String) SessionData.getUserDetails().get("userID");
	}*/
}