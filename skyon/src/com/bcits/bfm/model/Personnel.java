package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@NamedQueries({
	@NamedQuery(name="Personnel.findmsSql",query="SELECT p.objectIdLo,p.firstName,p.lastName,p.department,p.nonAbacardNumber,p.timeEntered FROM Personnel p"),
	@NamedQuery(name="Personnel.findByObjectId",query="SELECT p FROM Personnel p WHERE p.objectIdLo=:objectIdLo AND p.department LIKE 'Facility staff'"),
	@NamedQuery(name="Personnel.getAccessCards",query="SELECT p.objectIdLo,p.nonAbacardNumber,p.firstName,p.lastName FROM Personnel p WHERE p.objectIdLo NOT IN(:listAcid)"),
	@NamedQuery(name="Personnel.getAccessCardNoUniqe",query="SELECT p.objectIdLo FROM Personnel p WHERE p.objectIdLo=:objectIdLo"),
	@NamedQuery(name="Personnel.findByObjectIdLo",query="SELECT p FROM Personnel p WHERE p.objectIdLo=:objectIdLo"),

})

@Entity
@Table(name = "Personnel", schema = "dbo", catalog = "ContinuumDB")
public class Personnel implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
		private Integer objectIdHi;
		private Integer objectIdLo;
		private String uiName;
		private Integer ownerIdHi;
		private Integer ownerIdLo;
		private Integer deviceIdHi;
		private Integer deviceIdLo;
		private Boolean templateFlag;
		private Integer templateIdHi;
		private Integer templateIdLo;
		private String controllerName;
		private Integer alarmGraphicPage;
		private Timestamp activationDate;
		private Timestamp savedActivationDate;
		private Boolean ada;
		private String address;
		private Boolean allowEntEntEgr;
		private String blood;
		private Integer cardType;
		private Integer cardType2;
		private Integer siteCode;
		private Integer siteCode2;
		private String cardNumber;
		private String cardNumber2;
		private Integer savedCardType;
		private String city;
		private String country;
		private String customControl1;
		private String customControl2;
		private String customControl3;
		private Timestamp dateOfBirth;
		private Boolean deletePending;
		private String department;
		private Float departmentCode;
		private Boolean distFailed;
		private Boolean duress;
		private String emergencyContact;
		private String emergencyPhone;
		private String empNumber;
		private Boolean entryEgress;
		private Timestamp expirationDate;
		private Timestamp savedExpirationDate;
		private String eyeColor;
		private String firstName;
		private String hairColor;
		private String height;
		private String homePhone;
		private Integer inactiveDisableDays;
		private String info1;
		private String info2;
		private String info3;
		private String info4;
		private String info5;
		private String info6;
		private String jobTitle;
		private String lastName;
		private String licenseNumber;
		private Boolean lostCard;
		private String middleName;
		private String officeLocation;
		private String parkingSticker;
		private String photoFile;
		private Integer pin;
		private Integer savedPin;
		private String savedCardNumber;
		private Integer savedSiteCode;
		private Integer sex;
		private String signature;
		private String socSecNo;
		private Timestamp startDate;
		private Integer state;
		private Integer savedState;
		private String stateOfResidence;
		private String supervisor;
		private Timestamp timeEntered;
		private Integer valueHi;
		private Integer valueLo;
		private String vehicalInfo;
		private Boolean visitor;
		private Integer weight;
		private String workPhone;
		private String zip;
		private Integer zone;
		private Integer zonePointHi;
		private Integer zonePointLo;
		private Integer nonAbacardNumber;
		private Integer nonAbacardNumber2;
		private String blobTemplate;
		private Boolean executivePrivilege;
		private Integer defaultClearanceLevel;
		private Integer fipsAgencyCode;
		private Integer fipsOrgId;
		private Integer fipsHmac;
		private Integer fipsSystemCode;
		private Integer fipsCredentialNumber;
		private String fipsPersonId;
		private Integer fipsCredentialSeries;
		private Integer fipsCredentialIssue;
		private Integer fipsOrgCategory;
		private Integer fipsPersonOrgAssociation;
		private Timestamp fipsExpirationDate;
		private Boolean fipsPivControlled;
		private Integer fipsPivState;
		private Integer savedCardType2;
		private String savedCardNumber2;
		private Integer savedSiteCode2;

		
		
		@Id
		@Column(name = "ObjectIdHi")
		public Integer getObjectIdHi() {
			return objectIdHi;
		}

		public void setObjectIdHi(Integer objectIdHi) {
			this.objectIdHi = objectIdHi;
		}

		@Column(name = "uiName", length = 128)
		public String getUiName() {
			return this.uiName;
		}

		public void setUiName(String uiName) {
			this.uiName = uiName;
		}
		
        
		@Column(name = "OwnerIdHi")
		public Integer getOwnerIdHi() {
			return this.ownerIdHi;
		}

		public void setOwnerIdHi(Integer ownerIdHi) {
			this.ownerIdHi = ownerIdHi;
		}

		@Column(name = "OwnerIdLo")
		public Integer getOwnerIdLo() {
			return this.ownerIdLo;
		}

		public void setOwnerIdLo(Integer ownerIdLo) {
			this.ownerIdLo = ownerIdLo;
		}

		@Column(name = "DeviceIdHi")
		public Integer getDeviceIdHi() {
			return this.deviceIdHi;
		}

		public void setDeviceIdHi(Integer deviceIdHi) {
			this.deviceIdHi = deviceIdHi;
		}

		@Column(name = "DeviceIdLo")
		public Integer getDeviceIdLo() {
			return this.deviceIdLo;
		}

		public void setDeviceIdLo(Integer deviceIdLo) {
			this.deviceIdLo = deviceIdLo;
		}

		@Column(name = "TemplateFlag")
		public Boolean getTemplateFlag() {
			return this.templateFlag;
		}

		public void setTemplateFlag(Boolean templateFlag) {
			this.templateFlag = templateFlag;
		}

		@Column(name = "TemplateIdHi")
		public Integer getTemplateIdHi() {
			return this.templateIdHi;
		}

		public void setTemplateIdHi(Integer templateIdHi) {
			this.templateIdHi = templateIdHi;
		}

		@Column(name = "TemplateIdLo")
		public Integer getTemplateIdLo() {
			return this.templateIdLo;
		}

		public void setTemplateIdLo(Integer templateIdLo) {
			this.templateIdLo = templateIdLo;
		}

		@Column(name = "ControllerName", length = 16)
		public String getControllerName() {
			return this.controllerName;
		}

		public void setControllerName(String controllerName) {
			this.controllerName = controllerName;
		}

		@Column(name = "AlarmGraphicPage")
		public Integer getAlarmGraphicPage() {
			return this.alarmGraphicPage;
		}

		public void setAlarmGraphicPage(Integer alarmGraphicPage) {
			this.alarmGraphicPage = alarmGraphicPage;
		}

		@Column(name = "ActivationDate", length = 23)
		public Timestamp getActivationDate() {
			return this.activationDate;
		}

		public void setActivationDate(Timestamp activationDate) {
			this.activationDate = activationDate;
		}

		@Column(name = "SavedActivationDate", length = 23)
		public Timestamp getSavedActivationDate() {
			return this.savedActivationDate;
		}

		public void setSavedActivationDate(Timestamp savedActivationDate) {
			this.savedActivationDate = savedActivationDate;
		}

		@Column(name = "ADA")
		public Boolean getAda() {
			return this.ada;
		}

		public void setAda(Boolean ada) {
			this.ada = ada;
		}

		@Column(name = "Address", length = 48)
		public String getAddress() {
			return this.address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		@Column(name = "AllowEntEntEgr")
		public Boolean getAllowEntEntEgr() {
			return this.allowEntEntEgr;
		}

		public void setAllowEntEntEgr(Boolean allowEntEntEgr) {
			this.allowEntEntEgr = allowEntEntEgr;
		}

		@Column(name = "Blood", length = 3)
		public String getBlood() {
			return this.blood;
		}

		public void setBlood(String blood) {
			this.blood = blood;
		}

		@Column(name = "CardType")
		public Integer getCardType() {
			return this.cardType;
		}

		public void setCardType(Integer cardType) {
			this.cardType = cardType;
		}

		@Column(name = "CardType2")
		public Integer getCardType2() {
			return this.cardType2;
		}

		public void setCardType2(Integer cardType2) {
			this.cardType2 = cardType2;
		}

		@Column(name = "SiteCode")
		public Integer getSiteCode() {
			return this.siteCode;
		}

		public void setSiteCode(Integer siteCode) {
			this.siteCode = siteCode;
		}

		@Column(name = "SiteCode2")
		public Integer getSiteCode2() {
			return this.siteCode2;
		}

		public void setSiteCode2(Integer siteCode2) {
			this.siteCode2 = siteCode2;
		}

		@Column(name = "CardNumber")
		public String getCardNumber() {
			return this.cardNumber;
		}

		public void setCardNumber(String cardNumber) {
			this.cardNumber = cardNumber;
		}

		@Column(name = "CardNumber2")
		public String getCardNumber2() {
			return this.cardNumber2;
		}

		public void setCardNumber2(String cardNumber2) {
			this.cardNumber2 = cardNumber2;
		}

		@Column(name = "SavedCardType")
		public Integer getSavedCardType() {
			return this.savedCardType;
		}

		public void setSavedCardType(Integer savedCardType) {
			this.savedCardType = savedCardType;
		}

		@Column(name = "City", length = 48)
		public String getCity() {
			return this.city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		@Column(name = "Country", length = 48)
		public String getCountry() {
			return this.country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		@Column(name = "CustomControl1", length = 80)
		public String getCustomControl1() {
			return this.customControl1;
		}

		public void setCustomControl1(String customControl1) {
			this.customControl1 = customControl1;
		}

		@Column(name = "CustomControl2", length = 80)
		public String getCustomControl2() {
			return this.customControl2;
		}

		public void setCustomControl2(String customControl2) {
			this.customControl2 = customControl2;
		}

		@Column(name = "CustomControl3", length = 80)
		public String getCustomControl3() {
			return this.customControl3;
		}

		public void setCustomControl3(String customControl3) {
			this.customControl3 = customControl3;
		}

		@Column(name = "DateOfBirth", length = 23)
		public Timestamp getDateOfBirth() {
			return this.dateOfBirth;
		}

		public void setDateOfBirth(Timestamp dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}

		@Column(name = "DeletePending")
		public Boolean getDeletePending() {
			return this.deletePending;
		}

		public void setDeletePending(Boolean deletePending) {
			this.deletePending = deletePending;
		}

		@Column(name = "Department", length = 32)
		public String getDepartment() {
			return this.department;
		}

		public void setDepartment(String department) {
			this.department = department;
		}

		@Column(name = "DepartmentCode", precision = 24, scale = 0)
		public Float getDepartmentCode() {
			return this.departmentCode;
		}

		public void setDepartmentCode(Float departmentCode) {
			this.departmentCode = departmentCode;
		}

		@Column(name = "DistFailed")
		public Boolean getDistFailed() {
			return this.distFailed;
		}

		public void setDistFailed(Boolean distFailed) {
			this.distFailed = distFailed;
		}

		@Column(name = "Duress")
		public Boolean getDuress() {
			return this.duress;
		}

		public void setDuress(Boolean duress) {
			this.duress = duress;
		}

		@Column(name = "EmergencyContact", length = 80)
		public String getEmergencyContact() {
			return this.emergencyContact;
		}

		public void setEmergencyContact(String emergencyContact) {
			this.emergencyContact = emergencyContact;
		}

		@Column(name = "EmergencyPhone", length = 40)
		public String getEmergencyPhone() {
			return this.emergencyPhone;
		}

		public void setEmergencyPhone(String emergencyPhone) {
			this.emergencyPhone = emergencyPhone;
		}

		@Column(name = "EmpNumber", length = 16)
		public String getEmpNumber() {
			return this.empNumber;
		}

		public void setEmpNumber(String empNumber) {
			this.empNumber = empNumber;
		}

		@Column(name = "EntryEgress")
		public Boolean getEntryEgress() {
			return this.entryEgress;
		}

		public void setEntryEgress(Boolean entryEgress) {
			this.entryEgress = entryEgress;
		}

		@Column(name = "ExpirationDate", length = 23)
		public Timestamp getExpirationDate() {
			return this.expirationDate;
		}

		public void setExpirationDate(Timestamp expirationDate) {
			this.expirationDate = expirationDate;
		}

		@Column(name = "SavedExpirationDate", length = 23)
		public Timestamp getSavedExpirationDate() {
			return this.savedExpirationDate;
		}

		public void setSavedExpirationDate(Timestamp savedExpirationDate) {
			this.savedExpirationDate = savedExpirationDate;
		}

		@Column(name = "EyeColor", length = 32)
		public String getEyeColor() {
			return this.eyeColor;
		}

		public void setEyeColor(String eyeColor) {
			this.eyeColor = eyeColor;
		}

		@Column(name = "FirstName", length = 32)
		public String getFirstName() {
			return this.firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		@Column(name = "HairColor", length = 12)
		public String getHairColor() {
			return this.hairColor;
		}

		public void setHairColor(String hairColor) {
			this.hairColor = hairColor;
		}

		@Column(name = "Height", length = 16)
		public String getHeight() {
			return this.height;
		}

		public void setHeight(String height) {
			this.height = height;
		}

		@Column(name = "HomePhone", length = 40)
		public String getHomePhone() {
			return this.homePhone;
		}

		public void setHomePhone(String homePhone) {
			this.homePhone = homePhone;
		}

		@Column(name = "InactiveDisableDays")
		public Integer getInactiveDisableDays() {
			return this.inactiveDisableDays;
		}

		public void setInactiveDisableDays(Integer inactiveDisableDays) {
			this.inactiveDisableDays = inactiveDisableDays;
		}

		@Column(name = "Info1", length = 40)
		public String getInfo1() {
			return this.info1;
		}

		public void setInfo1(String info1) {
			this.info1 = info1;
		}

		@Column(name = "Info2", length = 40)
		public String getInfo2() {
			return this.info2;
		}

		public void setInfo2(String info2) {
			this.info2 = info2;
		}

		@Column(name = "Info3", length = 40)
		public String getInfo3() {
			return this.info3;
		}

		public void setInfo3(String info3) {
			this.info3 = info3;
		}

		@Column(name = "Info4", length = 40)
		public String getInfo4() {
			return this.info4;
		}

		public void setInfo4(String info4) {
			this.info4 = info4;
		}

		@Column(name = "Info5", length = 40)
		public String getInfo5() {
			return this.info5;
		}

		public void setInfo5(String info5) {
			this.info5 = info5;
		}

		@Column(name = "Info6", length = 40)
		public String getInfo6() {
			return this.info6;
		}

		public void setInfo6(String info6) {
			this.info6 = info6;
		}

		@Column(name = "JobTitle", length = 32)
		public String getJobTitle() {
			return this.jobTitle;
		}

		public void setJobTitle(String jobTitle) {
			this.jobTitle = jobTitle;
		}

		@Column(name = "LastName", length = 32)
		public String getLastName() {
			return this.lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		@Column(name = "LicenseNumber", length = 12)
		public String getLicenseNumber() {
			return this.licenseNumber;
		}

		public void setLicenseNumber(String licenseNumber) {
			this.licenseNumber = licenseNumber;
		}

		@Column(name = "LostCard")
		public Boolean getLostCard() {
			return this.lostCard;
		}

		public void setLostCard(Boolean lostCard) {
			this.lostCard = lostCard;
		}

		@Column(name = "MiddleName", length = 40)
		public String getMiddleName() {
			return this.middleName;
		}

		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}

		@Column(name = "OfficeLocation", length = 16)
		public String getOfficeLocation() {
			return this.officeLocation;
		}

		public void setOfficeLocation(String officeLocation) {
			this.officeLocation = officeLocation;
		}

		@Column(name = "ParkingSticker", length = 8)
		public String getParkingSticker() {
			return this.parkingSticker;
		}

		public void setParkingSticker(String parkingSticker) {
			this.parkingSticker = parkingSticker;
		}

		@Column(name = "PhotoFile")
		public String getPhotoFile() {
			return this.photoFile;
		}

		public void setPhotoFile(String photoFile) {
			this.photoFile = photoFile;
		}

		@Column(name = "PIN")
		public Integer getPin() {
			return this.pin;
		}

		public void setPin(Integer pin) {
			this.pin = pin;
		}

		@Column(name = "SavedPIN")
		public Integer getSavedPin() {
			return this.savedPin;
		}

		public void setSavedPin(Integer savedPin) {
			this.savedPin = savedPin;
		}

		@Column(name = "SavedCardNumber")
		public String getSavedCardNumber() {
			return this.savedCardNumber;
		}

		public void setSavedCardNumber(String savedCardNumber) {
			this.savedCardNumber = savedCardNumber;
		}

		@Column(name = "SavedSiteCode")
		public Integer getSavedSiteCode() {
			return this.savedSiteCode;
		}

		public void setSavedSiteCode(Integer savedSiteCode) {
			this.savedSiteCode = savedSiteCode;
		}

		@Column(name = "Sex")
		public Integer getSex() {
			return this.sex;
		}

		public void setSex(Integer sex) {
			this.sex = sex;
		}

		@Column(name = "Signature")
		public String getSignature() {
			return this.signature;
		}

		public void setSignature(String signature) {
			this.signature = signature;
		}

		@Column(name = "SocSecNo", length = 11)
		public String getSocSecNo() {
			return this.socSecNo;
		}

		public void setSocSecNo(String socSecNo) {
			this.socSecNo = socSecNo;
		}

		@Column(name = "StartDate", length = 23)
		public Timestamp getStartDate() {
			return this.startDate;
		}

		public void setStartDate(Timestamp startDate) {
			this.startDate = startDate;
		}

		@Column(name = "State")
		public Integer getState() {
			return this.state;
		}

		public void setState(Integer state) {
			this.state = state;
		}

		@Column(name = "SavedState")
		public Integer getSavedState() {
			return this.savedState;
		}

		public void setSavedState(Integer savedState) {
			this.savedState = savedState;
		}

		@Column(name = "StateOfResidence", length = 2)
		public String getStateOfResidence() {
			return this.stateOfResidence;
		}

		public void setStateOfResidence(String stateOfResidence) {
			this.stateOfResidence = stateOfResidence;
		}

		@Column(name = "Supervisor", length = 40)
		public String getSupervisor() {
			return this.supervisor;
		}

		public void setSupervisor(String supervisor) {
			this.supervisor = supervisor;
		}

		@Column(name = "TimeEntered", length = 23)
		public Timestamp getTimeEntered() {
			return this.timeEntered;
		}

		public void setTimeEntered(Timestamp timeEntered) {
			this.timeEntered = timeEntered;
		}

		@Column(name = "ValueHi")
		public Integer getValueHi() {
			return this.valueHi;
		}

		public void setValueHi(Integer valueHi) {
			this.valueHi = valueHi;
		}

		@Column(name = "ValueLo")
		public Integer getValueLo() {
			return this.valueLo;
		}

		public void setValueLo(Integer valueLo) {
			this.valueLo = valueLo;
		}

		@Column(name = "VehicalInfo", length = 40)
		public String getVehicalInfo() {
			return this.vehicalInfo;
		}

		public void setVehicalInfo(String vehicalInfo) {
			this.vehicalInfo = vehicalInfo;
		}

		@Column(name = "Visitor")
		public Boolean getVisitor() {
			return this.visitor;
		}

		public void setVisitor(Boolean visitor) {
			this.visitor = visitor;
		}

		@Column(name = "Weight")
		public Integer getWeight() {
			return this.weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}

		@Column(name = "WorkPhone", length = 40)
		public String getWorkPhone() {
			return this.workPhone;
		}

		public void setWorkPhone(String workPhone) {
			this.workPhone = workPhone;
		}

		@Column(name = "Zip", length = 10)
		public String getZip() {
			return this.zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		@Column(name = "Zone")
		public Integer getZone() {
			return this.zone;
		}

		public void setZone(Integer zone) {
			this.zone = zone;
		}

		@Column(name = "ZonePointHi")
		public Integer getZonePointHi() {
			return this.zonePointHi;
		}

		public void setZonePointHi(Integer zonePointHi) {
			this.zonePointHi = zonePointHi;
		}

		@Column(name = "ZonePointLo")
		public Integer getZonePointLo() {
			return this.zonePointLo;
		}

		public void setZonePointLo(Integer zonePointLo) {
			this.zonePointLo = zonePointLo;
		}

		@Column(name = "NonABACardNumber")
		public Integer getNonAbacardNumber() {
			return this.nonAbacardNumber;
		}

		public void setNonAbacardNumber(Integer nonAbacardNumber) {
			this.nonAbacardNumber = nonAbacardNumber;
		}

		@Column(name = "NonABACardNumber2")
		public Integer getNonAbacardNumber2() {
			return this.nonAbacardNumber2;
		}

		public void setNonAbacardNumber2(Integer nonAbacardNumber2) {
			this.nonAbacardNumber2 = nonAbacardNumber2;
		}

		@Column(name = "BLOB_Template")
		public String getBlobTemplate() {
			return this.blobTemplate;
		}

		public void setBlobTemplate(String blobTemplate) {
			this.blobTemplate = blobTemplate;
		}

		@Column(name = "ExecutivePrivilege")
		public Boolean getExecutivePrivilege() {
			return this.executivePrivilege;
		}

		public void setExecutivePrivilege(Boolean executivePrivilege) {
			this.executivePrivilege = executivePrivilege;
		}

		@Column(name = "DefaultClearanceLevel")
		public Integer getDefaultClearanceLevel() {
			return this.defaultClearanceLevel;
		}

		public void setDefaultClearanceLevel(Integer defaultClearanceLevel) {
			this.defaultClearanceLevel = defaultClearanceLevel;
		}

		@Column(name = "FipsAgencyCode")
		public Integer getFipsAgencyCode() {
			return this.fipsAgencyCode;
		}

		public void setFipsAgencyCode(Integer fipsAgencyCode) {
			this.fipsAgencyCode = fipsAgencyCode;
		}

		@Column(name = "FipsOrgId")
		public Integer getFipsOrgId() {
			return this.fipsOrgId;
		}

		public void setFipsOrgId(Integer fipsOrgId) {
			this.fipsOrgId = fipsOrgId;
		}

		@Column(name = "FipsHmac")
		public Integer getFipsHmac() {
			return this.fipsHmac;
		}

		public void setFipsHmac(Integer fipsHmac) {
			this.fipsHmac = fipsHmac;
		}

		@Column(name = "FipsSystemCode")
		public Integer getFipsSystemCode() {
			return this.fipsSystemCode;
		}

		public void setFipsSystemCode(Integer fipsSystemCode) {
			this.fipsSystemCode = fipsSystemCode;
		}

		@Column(name = "FipsCredentialNumber")
		public Integer getFipsCredentialNumber() {
			return this.fipsCredentialNumber;
		}

		public void setFipsCredentialNumber(Integer fipsCredentialNumber) {
			this.fipsCredentialNumber = fipsCredentialNumber;
		}

		@Column(name = "FipsPersonId")
		public String getFipsPersonId() {
			return this.fipsPersonId;
		}

		public void setFipsPersonId(String fipsPersonId) {
			this.fipsPersonId = fipsPersonId;
		}

		@Column(name = "FipsCredentialSeries")
		public Integer getFipsCredentialSeries() {
			return this.fipsCredentialSeries;
		}

		public void setFipsCredentialSeries(Integer fipsCredentialSeries) {
			this.fipsCredentialSeries = fipsCredentialSeries;
		}

		@Column(name = "FipsCredentialIssue")
		public Integer getFipsCredentialIssue() {
			return this.fipsCredentialIssue;
		}

		public void setFipsCredentialIssue(Integer fipsCredentialIssue) {
			this.fipsCredentialIssue = fipsCredentialIssue;
		}

		@Column(name = "FipsOrgCategory")
		public Integer getFipsOrgCategory() {
			return this.fipsOrgCategory;
		}

		public void setFipsOrgCategory(Integer fipsOrgCategory) {
			this.fipsOrgCategory = fipsOrgCategory;
		}

		@Column(name = "FipsPersonOrgAssociation")
		public Integer getFipsPersonOrgAssociation() {
			return this.fipsPersonOrgAssociation;
		}

		public void setFipsPersonOrgAssociation(Integer fipsPersonOrgAssociation) {
			this.fipsPersonOrgAssociation = fipsPersonOrgAssociation;
		}

		@Column(name = "FipsExpirationDate", length = 23)
		public Timestamp getFipsExpirationDate() {
			return this.fipsExpirationDate;
		}

		public void setFipsExpirationDate(Timestamp fipsExpirationDate) {
			this.fipsExpirationDate = fipsExpirationDate;
		}

		@Column(name = "FipsPivControlled")
		public Boolean getFipsPivControlled() {
			return this.fipsPivControlled;
		}

		public void setFipsPivControlled(Boolean fipsPivControlled) {
			this.fipsPivControlled = fipsPivControlled;
		}

		@Column(name = "FipsPivState")
		public Integer getFipsPivState() {
			return this.fipsPivState;
		}

		public void setFipsPivState(Integer fipsPivState) {
			this.fipsPivState = fipsPivState;
		}

		@Column(name = "SavedCardType2")
		public Integer getSavedCardType2() {
			return this.savedCardType2;
		}

		public void setSavedCardType2(Integer savedCardType2) {
			this.savedCardType2 = savedCardType2;
		}

		@Column(name = "SavedCardNumber2")
		public String getSavedCardNumber2() {
			return this.savedCardNumber2;
		}

		public void setSavedCardNumber2(String savedCardNumber2) {
			this.savedCardNumber2 = savedCardNumber2;
		}

		@Column(name = "SavedSiteCode2")
		public Integer getSavedSiteCode2() {
			return this.savedSiteCode2;
		}

		public void setSavedSiteCode2(Integer savedSiteCode2) {
			this.savedSiteCode2 = savedSiteCode2;
		}

		@Id
		@Column(name = "ObjectIdLo")
		public Integer getObjectIdLo() {
			return objectIdLo;
		}

		public void setObjectIdLo(Integer objectIdLo) {
			this.objectIdLo = objectIdLo;
		}

		

		

		

}
