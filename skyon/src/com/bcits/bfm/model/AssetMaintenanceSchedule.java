package com.bcits.bfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="ASSET_MAINT_SCHEDULE")
@NamedQueries({
	@NamedQuery(name = "AssetMaintenanceSchedule.findAll", query = "SELECT a FROM AssetMaintenanceSchedule a ORDER BY a.amsId DESC"),
	@NamedQuery(name = "AssetMaintenanceSchedule.setAmsStatus", query = "UPDATE AssetMaintenanceSchedule a SET a.amsStatus = :amsStatus WHERE a.amsId = :amsId"),
	@NamedQuery(name = "AssetMaintenanceSchedule.getUsersListBasedOnDeptId", query = "SELECT obj FROM Users obj WHERE obj.department.dept_Id=:deptId"),
	@NamedQuery(name = "AssetMaintenanceSchedule.findAllList", query = "SELECT a.amsId,a.assetId,ast.assetName,a.taskName,a.taskLocation,a.mtId,mt.maintainanceType,a.taskDescription,a.toolsRequired,a.expectedResult,a.taskBestTime,a.expectedTaskDuration,a.taskFrequency,a.taskStartDate,a.taskEndDate,a.taskLastExecuted,a.taskOverdueByDays,a.createdBy,a.lastUpdatedBy,a.lastUpdatedDate,a.amsStatus,a.countOfTaskExecution,a.jtId,jt.jtType,a.mtDpIt,de.dept_Name,a.timeFrom,a.timeTo FROM AssetMaintenanceSchedule a INNER JOIN a.asset ast INNER JOIN a.maintainanceTypes mt INNER JOIN a.jobTypes jt INNER JOIN a.maintainanceDepartment md INNER JOIN md.department de ORDER BY a.amsId DESC"),

	})
public class AssetMaintenanceSchedule {
	
	private int amsId;
	
	private int assetId;
	private Asset asset;
	
	private String taskName;
	private String taskLocation;
	//private String taskGroup;
	private String taskDescription;
	private String toolsRequired;
	private String expectedResult;
	private String taskBestTime;
	private int expectedTaskDuration;
	private String taskFrequency;
	private Date taskStartDate;
	private Date taskLastExecuted;
	private int taskOverdueByDays;
	private String createdBy;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;
	private Date taskEndDate;
	private String amsStatus;
	private int countOfTaskExecution;
	
	private int mtId;
	private MaintainanceTypes maintainanceTypes;
	
	private int jtId;
	private JobTypes jobTypes;
	
	private int mtDpIt;
	private MaintainanceDepartment maintainanceDepartment;
	
	private String timeFrom;
	private String timeTo;
	
	
	@Id
	@SequenceGenerator(name="SEQ_ASSET_MAINT_SCH" ,sequenceName="ASSET_MAINT_SCH_SEQ")
	@GeneratedValue(generator="SEQ_ASSET_MAINT_SCH")
	@Column(name="AMS_ID")
	public int getAmsId() {
		return amsId;
	}
	public void setAmsId(int amsId) {
		this.amsId = amsId;
	}
	
	@Column(name="AM_ID")
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	
	@Column(name="TASK_NAME")
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	@Column(name="TASK_LOCATION")
	public String getTaskLocation() {
		return taskLocation;
	}
	public void setTaskLocation(String taskLocation) {
		this.taskLocation = taskLocation;
	}
	
	/*@Column(name="TASK_GROUP")
	public String getTaskGroup() {
		return taskGroup;
	}
	public void setTaskGroup(String taskGroup) {
		this.taskGroup = taskGroup;
	}*/
	
	@Column(name="TASK_DESCRIPTION")
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	
	@Column(name="TOOLS_REQUIRED")
	public String getToolsRequired() {
		return toolsRequired;
	}
	public void setToolsRequired(String toolsRequired) {
		this.toolsRequired = toolsRequired;
	}
	
	@Column(name="EXPECTED_RESULTS")
	public String getExpectedResult() {
		return expectedResult;
	}
	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}
	
	@Column(name="TASK_BESTTIME")
	public String getTaskBestTime() {
		return taskBestTime;
	}
	public void setTaskBestTime(String taskBestTime) {
		this.taskBestTime = taskBestTime;
	}
	
	@Column(name="EXPECTED_TASK_DURATION")
	public int getExpectedTaskDuration() {
		return expectedTaskDuration;
	}
	public void setExpectedTaskDuration(int expectedTaskDuration) {
		this.expectedTaskDuration = expectedTaskDuration;
	}
	
	@Column(name="TASK_FREQUENCY")
	public String getTaskFrequency() {
		return taskFrequency;
	}
	public void setTaskFrequency(String taskFrequency) {
		this.taskFrequency = taskFrequency;
	}
	
	@Column(name="TASK_START_DATE")
	public Date getTaskStartDate() {
		return taskStartDate;
	}
	public void setTaskStartDate(Date taskStartDate) {
		this.taskStartDate = taskStartDate;
	}
	
	@Column(name="TASK_LASTEXECUTED")
	public Date getTaskLastExecuted() {
		return taskLastExecuted;
	}
	public void setTaskLastExecuted(Date taskLastExecuted) {
		this.taskLastExecuted = taskLastExecuted;
	}
	
	@Column(name="TASK_OVERDUE_BYDAYS")
	public int getTaskOverdueByDays() {
		return taskOverdueByDays;
	}
	public void setTaskOverdueByDays(int taskOverdueByDays) {
		this.taskOverdueByDays = taskOverdueByDays;
	}
	
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="LAST_UPDATED_BY")
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	@Column(name="LAST_UPDATED_DT")
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	
	@Column(name="TASK_END_DATE")
	public Date getTaskEndDate() {
		return taskEndDate;
	}
	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}
	
	@Column(name="AMS_STATUS")
	public String getAmsStatus() {
		return amsStatus;
	}
	public void setAmsStatus(String amsStatus) {
		this.amsStatus = amsStatus;
	}
	
	@Column(name="COUNT_OF_TASK_EXECUTON")
	public int getCountOfTaskExecution() {
		return countOfTaskExecution;
	}
	public void setCountOfTaskExecution(int countOfTaskExecution) {
		this.countOfTaskExecution = countOfTaskExecution;
	}


	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="AM_ID",insertable=false, updatable=false)
	public Asset getAsset() {
		return asset;
	}
	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	

	
	@Column(name="MT_ID")
	public int getMtId() {
		return mtId;
	}
	public void setMtId(int mtId) {
		this.mtId = mtId;
	}

	
	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="MT_ID",insertable=false, updatable=false)
	public MaintainanceTypes getMaintainanceTypes() {
		return maintainanceTypes;
	}
	public void setMaintainanceTypes(MaintainanceTypes maintainanceTypes) {
		this.maintainanceTypes = maintainanceTypes;
	}
	
	
	

	@Column(name="JT_ID")
	public int getJtId() {
		return jtId;
	}
	public void setJtId(int jtId) {
		this.jtId = jtId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="JT_ID",insertable=false, updatable=false)
	public JobTypes getJobTypes() {
		return jobTypes;
	}
	public void setJobTypes(JobTypes jobTypes) {
		this.jobTypes = jobTypes;
	}
	
	
	@Column(name="MT_DP_IT")
	public int getMtDpIt() {
		return mtDpIt;
	}
	public void setMtDpIt(int mtDpIt) {
		this.mtDpIt = mtDpIt;
	}
	
	
	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="MT_DP_IT",insertable=false, updatable=false)
	public MaintainanceDepartment getMaintainanceDepartment() {
		return maintainanceDepartment;
	}
	public void setMaintainanceDepartment(
			MaintainanceDepartment maintainanceDepartment) {
		this.maintainanceDepartment = maintainanceDepartment;
	}	
	
	@Column(name="TIME_FROM")
	public String getTimeFrom() {
		return timeFrom;
	}
	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}
	
	
	@Column(name="TIME_TO")
	public String getTimeTo() {
		return timeTo;
	}
	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}
	@PrePersist
	protected void onCreate() {
	    lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	    createdBy = (String) SessionData.getUserDetails().get("userID");
	    amsStatus = "Created";
	}

	@PreUpdate
	protected void onUpdate() {
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	}

}
