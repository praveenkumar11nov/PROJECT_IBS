package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@NamedQueries({
	@NamedQuery(name="AccessEvent.findLogInOut",query="SELECT ac FROM AccessEvent ac  WHERE ac.personIdLo = :personIdLo"),
	@NamedQuery(name="AccessEvent.findMinAndMaxTime",query="SELECT MIN(ac.loginLogOut),MAX(ac.loginLogOut) FROM AccessEvent ac"),
	@NamedQuery(name ="AccessEvent.readAttendanceMonthWise", query = "SELECT DISTINCT(ac.personIdLo) FROM AccessEvent ac  WHERE EXTRACT(month FROM ac.loginLogOut) =:month and EXTRACT(year FROM ac.loginLogOut) =:year AND ac.personIdLo != 0"),
	@NamedQuery(name="AccessEvent.findByTimeStamp",query="SELECT ac.personIdLo FROM AccessEvent ac  WHERE ac.loginLogOut = :loginLogOut AND ac.personIdLo != 0"),
})

@Entity
@Table(name = "AccessEvent", schema = "dbo", catalog = "ContinuumDB")
public class AccessEvent implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer objectIdHi;
    private long objectIdLo;
	private Timestamp loginLogOut;
	private String description;
	private int eventClass;
	private int cardType;
	private String cardNumber;
	private int personIdHi;
	private int personIdLo;
	private int areaIdHi;
	private int areaIdLo;
	private int doorIdHi;
	private int doorIdLo;
	private int departmentPointHi;
	private int departmentPointLo;
	private int controllerIdHi;
	private int controllerIdLo;
	private int siteCode;
	private String message;
	private int zoneCode;
	private int loggingWkstaIdHi;
	private int loggingWkstaIdLo;
	private Timestamp timeOfLog;
	private int nonABACardNumber;
	private int fipsAgencyCode;
	private int fipsOrgId;
	private int fipsHmac;
	private int fipsSystemCode;
	private int fipsCredentialNumber;
	private String fipsPersonId;
	private int fipsCredentialSeries;
	private int fipsCredentialIssue;
	private int fipsOrgCategory;
	private int fipsPersonOrgAssociation;
	private Timestamp fipsExpirationDate;
	
	@Id
	@Column(name = "ObjectIdHi")
	public Integer getObjectIdHi() {
		return objectIdHi;
	}
	public void setObjectIdHi(Integer objectIdHi) {
		this.objectIdHi = objectIdHi;
	}
	@Id
	@Column(name = "ObjectIdLo")
	public long getObjectIdLo() {
		return objectIdLo;
	}
	public void setObjectIdLo(long objectIdLo) {
		this.objectIdLo = objectIdLo;
	}
	
	@Column(name = "TimeStamp")
	public Timestamp getLoginLogOut() {
		return loginLogOut;
	}
	public void setLoginLogOut(Timestamp loginLogOut) {
		this.loginLogOut = loginLogOut;
	}
	
	@Column(name = "Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "EventClass")
	public int getEventClass() {
		return eventClass;
	}
	public void setEventClass(int eventClass) {
		this.eventClass = eventClass;
	}
	
	@Column(name = "CardType")
	public int getCardType() {
		return cardType;
	}
	public void setCardType(int cardType) {
		this.cardType = cardType;
	}
	@Column(name = "CardNumber")
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	@Column(name = "PersonIdHi")
	public int getPersonIdHi() {
		return personIdHi;
	}
	public void setPersonIdHi(int personIdHi) {
		this.personIdHi = personIdHi;
	}
	
	@Column(name = "PersonIdLo")
	public int getPersonIdLo() {
		return personIdLo;
	}
	public void setPersonIdLo(int personIdLo) {
		this.personIdLo = personIdLo;
	}
	
	@Column(name = "AreaIdHi")
	public int getAreaIdHi() {
		return areaIdHi;
	}
	public void setAreaIdHi(int areaIdHi) {
		this.areaIdHi = areaIdHi;
	}
	
	@Column(name = "AreaIdLo")
	public int getAreaIdLo() {
		return areaIdLo;
	}
	public void setAreaIdLo(int areaIdLo) {
		this.areaIdLo = areaIdLo;
	}
	
	@Column(name = "DoorIdHi")
	public int getDoorIdHi() {
		return doorIdHi;
	}
	public void setDoorIdHi(int doorIdHi) {
		this.doorIdHi = doorIdHi;
	}
	
	@Column(name = "DoorIdLo")
	public int getDoorIdLo() {
		return doorIdLo;
	}
	public void setDoorIdLo(int doorIdLo) {
		this.doorIdLo = doorIdLo;
	}
	
	@Column(name = "DepartmentPointHi")
	public int getDepartmentPointHi() {
		return departmentPointHi;
	}
	public void setDepartmentPointHi(int departmentPointHi) {
		this.departmentPointHi = departmentPointHi;
	}
	
	@Column(name = "DepartmentPointLo")
	public int getDepartmentPointLo() {
		return departmentPointLo;
	}
	
	public void setDepartmentPointLo(int departmentPointLo) {
		this.departmentPointLo = departmentPointLo;
	}
	
	@Column(name = "ControllerIdHi")
	public int getControllerIdHi() {
		return controllerIdHi;
	}
	public void setControllerIdHi(int controllerIdHi) {
		this.controllerIdHi = controllerIdHi;
	}
	
	@Column(name = "ControllerIdLo")
	public int getControllerIdLo() {
		return controllerIdLo;
	}
	public void setControllerIdLo(int controllerIdLo) {
		this.controllerIdLo = controllerIdLo;
	}
	
	@Column(name = "SiteCode")
	public int getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(int siteCode) {
		this.siteCode = siteCode;
	}
	
	@Column(name = "Message")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Column(name = "ZoneCode")
	public int getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(int zoneCode) {
		this.zoneCode = zoneCode;
	}
	
	@Column(name = "LoggingWkstaIdHi")
	public int getLoggingWkstaIdHi() {
		return loggingWkstaIdHi;
	}
	public void setLoggingWkstaIdHi(int loggingWkstaIdHi) {
		this.loggingWkstaIdHi = loggingWkstaIdHi;
	}
	
	@Column(name = "LoggingWkstaIdLo")
	public int getLoggingWkstaIdLo() {
		return loggingWkstaIdLo;
	}
	public void setLoggingWkstaIdLo(int loggingWkstaIdLo) {
		this.loggingWkstaIdLo = loggingWkstaIdLo;
	}
	
	@Column(name = "TimeOfLog")
	public Timestamp getTimeOfLog() {
		return timeOfLog;
	}
	public void setTimeOfLog(Timestamp timeOfLog) {
		this.timeOfLog = timeOfLog;
	}
	@Column(name = "NonABACardNumber")
	public int getNonABACardNumber() {
		return nonABACardNumber;
	}
	public void setNonABACardNumber(int nonABACardNumber) {
		this.nonABACardNumber = nonABACardNumber;
	}
	
	@Column(name = "FipsAgencyCode")
	public int getFipsAgencyCode() {
		return fipsAgencyCode;
	}
	public void setFipsAgencyCode(int fipsAgencyCode) {
		this.fipsAgencyCode = fipsAgencyCode;
	}
	
	@Column(name = "FipsOrgId")
	public int getFipsOrgId() {
		return fipsOrgId;
	}
	public void setFipsOrgId(int fipsOrgId) {
		this.fipsOrgId = fipsOrgId;
	}
	
	@Column(name = "FipsHmac")
	public int getFipsHmac() {
		return fipsHmac;
	}
	public void setFipsHmac(int fipsHmac) {
		this.fipsHmac = fipsHmac;
	}
	
	@Column(name = "FipsSystemCode")
	public int getFipsSystemCode() {
		return fipsSystemCode;
	}
	public void setFipsSystemCode(int fipsSystemCode) {
		this.fipsSystemCode = fipsSystemCode;
	}
	
	@Column(name = "FipsCredentialNumber")
	public int getFipsCredentialNumber() {
		return fipsCredentialNumber;
	}
	public void setFipsCredentialNumber(int fipsCredentialNumber) {
		this.fipsCredentialNumber = fipsCredentialNumber;
	}
	
	@Column(name = "FipsPersonId")
	public String getFipsPersonId() {
		return fipsPersonId;
	}
	public void setFipsPersonId(String fipsPersonId) {
		this.fipsPersonId = fipsPersonId;
	}
	
	@Column(name = "FipsCredentialSeries")
	public int getFipsCredentialSeries() {
		return fipsCredentialSeries;
	}
	public void setFipsCredentialSeries(int fipsCredentialSeries) {
		this.fipsCredentialSeries = fipsCredentialSeries;
	}
	@Column(name = "FipsCredentialIssue")
	public int getFipsCredentialIssue() {
		return fipsCredentialIssue;
	}
	public void setFipsCredentialIssue(int fipsCredentialIssue) {
		this.fipsCredentialIssue = fipsCredentialIssue;
	}
	@Column(name = "FipsOrgCategory")
	public int getFipsOrgCategory() {
		return fipsOrgCategory;
	}
	public void setFipsOrgCategory(int fipsOrgCategory) {
		this.fipsOrgCategory = fipsOrgCategory;
	}
	@Column(name = "FipsPersonOrgAssociation")
	public int getFipsPersonOrgAssociation() {
		return fipsPersonOrgAssociation;
	}
	public void setFipsPersonOrgAssociation(int fipsPersonOrgAssociation) {
		this.fipsPersonOrgAssociation = fipsPersonOrgAssociation;
	}
	@Column(name = "FipsExpirationDate")
	public Timestamp getFipsExpirationDate() {
		return fipsExpirationDate;
	}
	public void setFipsExpirationDate(Timestamp fipsExpirationDate) {
		this.fipsExpirationDate = fipsExpirationDate;
	}

	
	
	
	
}
