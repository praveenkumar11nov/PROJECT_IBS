package com.bcits.bfm.model;


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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="JOB_CALENDER")
@NamedQueries({
	@NamedQuery(name="JobCalender.findRequiredFieldOnAmsId", query="SELECT obj.jobCalenderId, obj.title, obj.jobNumber, obj.maintainanceTypes.maintainanceType, obj.start, obj.end  FROM JobCalender obj WHERE obj.assetMaintenanceSchedule.amsId =:amsId order by obj.start"),
	@NamedQuery(name = "JobCalender.findAllJobCalenderList",query="SELECT model FROM JobCalender model ORDER BY model.jobCalenderId DESC"),
	@NamedQuery(name = "JobCalender.deleteJobCalender",query="DELETE FROM JobCalender jc WHERE jc.jobCalenderId=:jobCalender"),

})
public class JobCalender implements java.io.Serializable  {
    
	
	private static final long serialVersionUID = 1L;
	
	private int jobCalenderId;
    private int scheduleType;
    private String title;
    private String description;
    private String recurrenceRule;
    private String recurrenceException;
    private int recurrenceId;
    private boolean isAllDay;
    private Date start;
    private Date end;
    
    private String jobNumber;
	private String jobGroup;
	private int expectedDays;
	private String jobPriority;
	private String jobAssets;
	private MaintainanceTypes maintainanceTypes;
	private Department departmentName;
	private AssetMaintenanceSchedule assetMaintenanceSchedule;
	private JobTypes jobTypes;
	private Person person;
	
	private Set<JobCards> jobCardses = new HashSet<JobCards>(0);
    
    @Id
    @Column(name="JOB_CALENDER_ID")
    @SequenceGenerator(name = "JOB_CALENDER_SEQUENCE", sequenceName = "JOB_CALENDER_SEQUENCE")
	@GeneratedValue(generator = "JOB_CALENDER_SEQUENCE")	
    public int getJobCalenderId() {
        return jobCalenderId;
    }

    public void setJobCalenderId(int jobCalenderId ) {
        this.jobCalenderId = jobCalenderId ;
    }

    @Column(name="SCHEDULE_TYPE")
    public int getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(int scheduleType) {
        this.scheduleType = scheduleType;
    }
    
    @Column(name="START_DATE") 	
    @NotNull(message="Start Date & Time is Required")
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }
    
    @Column(name="END_DATE")   
    @NotNull(message="End Date & Time is Required")
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
    
    @Column(name="Title")
    @NotEmpty(message="Title is Required")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name="DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="RECURRENCE_RULE")
    public String getRecurrenceRule() {
        return recurrenceRule;
    }

    public void setRecurrenceRule(String recurrenceRule) {
        this.recurrenceRule = recurrenceRule;
    }

    @Column(name="IS_ALL_DAY")
    public boolean getIsAllDay() {
        return isAllDay;
    }

    public void setIsAllDay(boolean isAllDay) {
        this.isAllDay = isAllDay;
    }

    @Column(name="RECURENCE_EXCEPTION")
    public String getRecurrenceException() {
        return recurrenceException;
    }

    public void setRecurrenceException(String recurrenceException) {
        this.recurrenceException = recurrenceException;
    }

    @Column(name="RECURRENCE_ID")
    public int getRecurrenceId() {
        return recurrenceId;
    }

    public void setRecurrenceId(int recurrenceId) {
        this.recurrenceId = recurrenceId;
    }
    
    
    /*Custom Columns*/
    @NotEmpty(message="Job Number is Required")
	@Column(name = "JOB_NUMBER", nullable = false, length = 45)
	public String getJobNumber() {
		return this.jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	/*@NotEmpty(message="Job Group is Required")*/
	@Column(name = "JOB_GROUP", nullable = true, length = 45)
	public String getJobGroup() {
		return this.jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	

	@Column(name = "EXPECTED_DAYS", nullable = false, precision = 11, scale = 0)
	public int getExpectedDays() {
		return this.expectedDays;
	}

	public void setExpectedDays(int expectedDays) {
		this.expectedDays = expectedDays;
	}

	@NotEmpty(message="Job Priority is Required")
	@Column(name = "JOB_PRIORITY", nullable = false, length = 45)
	public String getJobPriority() {
		return this.jobPriority;
	}

	public void setJobPriority(String jobPriority) {
		this.jobPriority = jobPriority;
	}

	@NotEmpty(message="Job Assets is Required")
	@Column(name = "JOB_ASSETS", nullable = false, length = 4000)
	public String getJobAssets() {
		return this.jobAssets;
	}

	public void setJobAssets(String jobAssets) {
		this.jobAssets = jobAssets;
	}	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JOB_DEPT_ID",nullable = false)
	@NotNull(message="Job Department is Required")
	public Department getMaintainanceDepartment() {
		return this.departmentName;
	}

	public void setMaintainanceDepartment(Department departmentName) {
		this.departmentName = departmentName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JOB_TYPE_ID",referencedColumnName="JT_ID",nullable = false)
	@NotNull(message="Job Type is Required")
	public JobTypes getJobTypes() {
		return this.jobTypes;
	}

	public void setJobTypes(JobTypes jobTypes) {
		this.jobTypes = jobTypes;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MAINTAINANCE_TYPE_ID",nullable = false)
	@NotNull(message="Maintainance Type is Required")
	public MaintainanceTypes getMaintainanceTypes() {
		return this.maintainanceTypes;
	}

	public void setMaintainanceTypes(MaintainanceTypes maintainanceTypes) {
		this.maintainanceTypes = maintainanceTypes;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AMS_ID", nullable = true)
	public AssetMaintenanceSchedule getAssetMaintenanceSchedule() {
		return assetMaintenanceSchedule;
	}

	public void setAssetMaintenanceSchedule(
			AssetMaintenanceSchedule assetMaintenanceSchedule) {
		this.assetMaintenanceSchedule = assetMaintenanceSchedule;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERSON", nullable = true)		
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,orphanRemoval=true,mappedBy = "jobCalender")
	public Set<JobCards> getJobCardses() {
		return this.jobCardses;
	}

	public void setJobCardses(Set<JobCards> jobCardses) {
		this.jobCardses = jobCardses;
	}	

}
