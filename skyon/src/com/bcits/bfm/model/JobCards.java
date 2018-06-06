package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bcits.bfm.util.SessionData;

/**
 * JobCards entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "JOB_CARDS")
@NamedQueries({
	@NamedQuery(name = "JobCards.UpdateStatus", query = "UPDATE JobCards jobCards SET  jobCards.status = :Status,jobCards.jobStartDt=:startDate WHERE jobCards.jcId = :jcId"),
	@NamedQuery(name = "JobCards.UpdateSupendStatus", query = "UPDATE JobCards jobCards SET  jobCards.suspendStatus = :Status WHERE jobCards.jcId = :jcId"),
	@NamedQuery(name = "JobCards.UpdateStatusOnJob", query = "UPDATE JobCards jobCards SET  jobCards.status = :Status WHERE jobCards.jcId = :jcId"),
	@NamedQuery(name = "JobCards.UpdateStatusOnClose", query = "UPDATE JobCards jobCards SET  jobCards.status = :Status,jobCards.jobEndDt=:endDate,jobCards.jobSla=:sla WHERE jobCards.jcId = :jcId"),
	@NamedQuery(name = "JobCards.getRequiredCardsAndMaterials", query = "SELECT jc.jcId, jc.jobNo, jc.jobName, sm.storeId, sm.storeName, sm.storeLocation, im.imId, im.imName, im.imType,jcm.jcmId,jcm.jcQuantity,jcm.unitOfMeasurement.uom "
			+ "FROM JobCards jc INNER JOIN jc.jcMaterialses jcm INNER JOIN jcm.storeMaster sm INNER JOIN jcm.itemMaster im "
			+ "WHERE jcm.status LIKE :status AND sm.storeId > 1 ORDER BY jcm.jcmId DESC"),
	@NamedQuery(name = "JobCards.findAllJobCardsList",query="SELECT model FROM JobCards model ORDER BY model.jcId DESC"),
	@NamedQuery(name = "JobCalender.findAllJobCalendersList",query="SELECT model.jobCalender FROM JobCards model"),
	@NamedQuery(name = "JobCards.readDataforMobi",query="SELECT model FROM JobCards model WHERE model.status!=:status AND model.person=:person ORDER BY model.jcId DESC"),
	@NamedQuery(name = "JobCards.getJobCardsBasedOnId",query="SELECT model FROM JobCards model WHERE model.jobCalender=:jobCalender"),
	@NamedQuery(name = "JobCards.findByProperty",query="SELECT model FROM JobCards model WHERE model.maintainanceTypesId=:maintainanceTypesId"),
	@NamedQuery(name = "JobCards.findAllList",query="SELECT j.jcId,j.jobGeneratedby,j.jobNo,j.jobName,j.jobDescription,j.jobDt,d.dept_Name,d.dept_Id,j.jobExpectedSla,j.jobSla,mt.mtId,mt.maintainanceType,j.jobStartDt,j.jobEndDt,j.jobAssets,p.firstName,p.lastName,p.personId,j.jobCcId,j.jobBmsId,j.jobAmsId,jt.jtType,jt.jtId,j.jobPriority,j.status,j.suspendStatus,j.createdBy,j.lastUpdatedBy,j.expectedDate,j.lastUpdatedDt,j.jobCalender.jobCalenderId FROM JobCards j INNER JOIN j.jobDepartment d INNER JOIN j.maintainanceTypes mt INNER JOIN j.person p INNER JOIN j.jobTypes jt  ORDER BY j.jcId DESC"),
	@NamedQuery(name = "JobCards.deleteJobCard",query="DELETE FROM JobCards jc WHERE jc.jcId= :jobCardId"),

})
public class JobCards implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	// Fields

	private int jcId;
	private Person person;
	private int personId;
	private int maintainanceTypesId;
	private MaintainanceTypes maintainanceTypes;
	private int jobTypesId;
	private JobTypes jobTypes;
	private String jobGeneratedby;
	private String jobNo;
	private String jobName;
	private String jobGroup;
	private String jobDescription;
	private Timestamp jobDt;
	private int jobDepartmentId;
	private Department jobDepartment;
	private int jobExpectedSla;
	private String jobSla;
	private Timestamp jobStartDt;
	private Timestamp jobEndDt;
	private String jobAssets;
	private int jobCcId;
	private int jobBmsId;
	private int jobAmsId;
	private String jobPriority;
	private String status;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	private String suspendStatus;
	private Timestamp expectedDate;
	private JobCalender jobCalender;
	
	private Set<JobDocuments> jobDocumentses = new HashSet<JobDocuments>(0);
	private Set<JcTools> jcToolses = new HashSet<JcTools>(0);
	private Set<JcMaterials> jcMaterialses = new HashSet<JcMaterials>(0);	
	private Set<JcObjectives> jcObjectiveses = new HashSet<JcObjectives>(0);
	private Set<JcTeam> jcTeams = new HashSet<JcTeam>(0);
	private Set<JcLabourtasks> jcLabourtaskses = new HashSet<JcLabourtasks>(0);
	private Set<JobNotification> jobNotifications = new HashSet<JobNotification>(0);
	private Set<JcNotes> jcNoteses = new HashSet<JcNotes>(0);
	

	// Constructors

	/** default constructor */
	public JobCards() {
	}


	// Property accessors
	@Id
	@Column(name = "JC_ID", unique = true, nullable = false, precision = 11, scale = 0)
	@SequenceGenerator(name = "JOB_CARDS_SEQ", sequenceName = "JOB_CARDS_SEQ")
	@GeneratedValue(generator = "JOB_CARDS_SEQ")	
	public int getJcId() {
		return this.jcId;
	}

	public void setJcId(int jcId) {
		this.jcId = jcId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JOB_OWNER_ID", nullable = false)
	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	@Column(name = "JOB_OWNER_ID", nullable = false,insertable=false,updatable=false)
	public int getPersonId() {
		return personId;
	}


	public void setPersonId(int personId) {
		this.personId = personId;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JOB_MT_ID", nullable = false)
	public MaintainanceTypes getMaintainanceTypes() {
		return this.maintainanceTypes;
	}

	public void setMaintainanceTypes(MaintainanceTypes maintainanceTypes) {
		this.maintainanceTypes = maintainanceTypes;
	}	
	

	@Column(name = "JOB_MT_ID", updatable=false,insertable=false)
	public int getMaintainanceTypesId() {
		return maintainanceTypesId;
	}


	public void setMaintainanceTypesId(int maintainanceTypesId) {
		this.maintainanceTypesId = maintainanceTypesId;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JOB_TYPE", nullable = false)
	public JobTypes getJobTypes() {
		return this.jobTypes;
	}

	public void setJobTypes(JobTypes jobTypes) {
		this.jobTypes = jobTypes;
	}
	
	

	@Column(name = "JOB_TYPE", insertable=false,updatable=false)
	public int getJobTypesId() {
		return jobTypesId;
	}


	public void setJobTypesId(int jobTypesId) {
		this.jobTypesId = jobTypesId;
	}


	@Column(name = "JOB_GENERATEDBY", nullable = false, length = 45)
	public String getJobGeneratedby() {
		return this.jobGeneratedby;
	}

	public void setJobGeneratedby(String jobGeneratedby) {
		this.jobGeneratedby = jobGeneratedby;
	}

	@Column(name = "JOB_NO", nullable = false, length = 45)
	public String getJobNo() {
		return this.jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	@Column(name = "JOB_NAME", nullable = false, length = 50)
	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Column(name = "JOB_GROUP", nullable = true, length = 200)
	public String getJobGroup() {
		return this.jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	@Column(name = "JOB_DESCRIPTION", length = 500)
	public String getJobDescription() {
		return this.jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}	
	
	@Column(name = "JOB_DT", nullable = false)	
	public Timestamp getJobDt() {
		return this.jobDt;
	}

	public void setJobDt(Timestamp jobDt) {
		this.jobDt = jobDt;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JOB_DEPARTMENT", nullable = false)	
	public Department getJobDepartment() {
		return this.jobDepartment;
	}

	public void setJobDepartment(Department jobDepartment) {
		this.jobDepartment = jobDepartment;
	}

	@Column(name = "JOB_DEPARTMENT", insertable=false,updatable=false)	
	public int getJobDepartmentId() {
		return jobDepartmentId;
	}


	public void setJobDepartmentId(int jobDepartmentId) {
		this.jobDepartmentId = jobDepartmentId;
	}


	@Column(name = "JOB_EXPECTED_SLA", nullable = false, precision = 10, scale = 0)
	public int getJobExpectedSla() {
		return this.jobExpectedSla;
	}

	public void setJobExpectedSla(int jobExpectedSla) {
		this.jobExpectedSla = jobExpectedSla;
	}

	@Column(name = "JOB_SLA", length = 25)
	public String getJobSla() {
		return this.jobSla;
	}

	public void setJobSla(String jobSla) {
		this.jobSla = jobSla;
	}

	@Column(name = "JOB_START_DT", length = 11)
	public Timestamp getJobStartDt() {
		return this.jobStartDt;
	}

	public void setJobStartDt(Timestamp jobStartDt) {
		this.jobStartDt = jobStartDt;
	}
	
	@Column(name = "EXPECTED_COMPLETION_DATE", length = 11)
	public Timestamp getExpectedDate() {
		return this.expectedDate;
	}
	
	public void setExpectedDate(Timestamp expectedDate) {
		this.expectedDate = expectedDate;
	}

	@Column(name = "JOB_END_DT", length = 11)
	public Timestamp getJobEndDt() {
		return this.jobEndDt;
	}

	public void setJobEndDt(Timestamp jobEndDt) {
		this.jobEndDt = jobEndDt;
	}

	@Column(name = "JOB_ASSETS", nullable = false, length = 45)
	public String getJobAssets() {
		return this.jobAssets;
	}

	public void setJobAssets(String jobAssets) {
		this.jobAssets = jobAssets;
	}

	@Column(name = "JOB_CC_ID", precision = 11, scale = 0)
	public int getJobCcId() {
		return this.jobCcId;
	}

	public void setJobCcId(int jobCcId) {
		this.jobCcId = jobCcId;
	}

	@Column(name = "JOB_BMS_ID", precision = 22, scale = 0)
	public int getJobBmsId() {
		return this.jobBmsId;
	}

	public void setJobBmsId(int jobBmsId) {
		this.jobBmsId = jobBmsId;
	}

	@Column(name = "JOB_AMS_ID", precision = 22, scale = 0)
	public int getJobAmsId() {
		return this.jobAmsId;
	}

	public void setJobAmsId(int jobAmsId) {
		this.jobAmsId = jobAmsId;
	}

	@Column(name = "JOB_PRIORITY", nullable = false, length = 45)
	public String getJobPriority() {
		return this.jobPriority;
	}

	public void setJobPriority(String jobPriority) {
		this.jobPriority = jobPriority;
	}

	@Column(name = "STATUS", nullable = false, length = 45)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CREATED_BY", length = 45)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "LAST_UPDATED_BY", length = 45)
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "LAST_UPDATED_DT", length = 11)
	public Timestamp getLastUpdatedDt() {
		return this.lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	@Column(name = "SUSPEND_STATUS", nullable = false, length = 45)
	public String getSuspendStatus() {
		return this.suspendStatus;
	}

	public void setSuspendStatus(String suspendStatus) {
		this.suspendStatus = suspendStatus;
	}	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JOB_CALENDER_ID",unique = true)	
	public JobCalender getJobCalender() {
		return jobCalender;
	}

	public void setJobCalender(JobCalender jobCalender) {
		this.jobCalender = jobCalender;
	}
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,orphanRemoval=true,mappedBy = "jobCards")
	public Set<JobDocuments> getJobDocumentses() {
		return this.jobDocumentses;
	}

	public void setJobDocumentses(Set<JobDocuments> jobDocumentses) {
		this.jobDocumentses = jobDocumentses;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,orphanRemoval=true, mappedBy = "jobCards")
	public Set<JcTools> getJcToolses() {
		return this.jcToolses;
	}

	public void setJcToolses(Set<JcTools> jcToolses) {
		this.jcToolses = jcToolses;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,orphanRemoval=true, mappedBy = "jobCards")
	public Set<JcMaterials> getJcMaterialses() {
		return this.jcMaterialses;
	}

	public void setJcMaterialses(Set<JcMaterials> jcMaterialses) {
		this.jcMaterialses = jcMaterialses;
	}	

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,orphanRemoval=true, mappedBy = "jobCards")
	public Set<JcObjectives> getJcObjectiveses() {
		return this.jcObjectiveses;
	}

	public void setJcObjectiveses(Set<JcObjectives> jcObjectiveses) {
		this.jcObjectiveses = jcObjectiveses;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,orphanRemoval=true, mappedBy = "jobCards")
	public Set<JcTeam> getJcTeams() {
		return this.jcTeams;
	}

	public void setJcTeams(Set<JcTeam> jcTeams) {
		this.jcTeams = jcTeams;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,orphanRemoval=true, mappedBy = "jobCards")
	public Set<JcLabourtasks> getJcLabourtaskses() {
		return this.jcLabourtaskses;
	}

	public void setJcLabourtaskses(Set<JcLabourtasks> jcLabourtaskses) {
		this.jcLabourtaskses = jcLabourtaskses;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,orphanRemoval=true, mappedBy = "jobCards")
	public Set<JobNotification> getJobNotifications() {
		return this.jobNotifications;
	}

	public void setJobNotifications(Set<JobNotification> jobNotifications) {
		this.jobNotifications = jobNotifications;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,orphanRemoval=true, mappedBy = "jobCards")
	public Set<JcNotes> getJcNoteses() {
		return this.jcNoteses;
	}

	public void setJcNoteses(Set<JcNotes> jcNoteses) {
		this.jcNoteses = jcNoteses;
	}
	
	@PrePersist
	protected void onCreate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}
	@PreUpdate
	protected void onUpdate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}

	
}