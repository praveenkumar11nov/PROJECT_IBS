package com.bcits.bfm.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="HELP_TOPIC_SLA")  
@NamedQueries({
	@NamedQuery(name = "HelpTopicEntity.findAll", query = "SELECT ht.topicId,ht.topicName,ht.topicDesc,ht.normalSLA,ht.level1SLA,ht.level2SLA,ht.level3SLA,ht.level1User,ht.level2User,ht.level3User,p1.firstName,p1.lastName,p2.firstName,p2.lastName,p3.firstName,p3.lastName,ht.createdDate,ht.status,ht.createdBy,ht.lastUpdatedBy,ht.lastUpdatedDT,ht.dept_Id,d.dept_Name,ht.level1NotifiedUsers,ht.level2NotifiedUsers,ht.level3NotifiedUsers FROM HelpTopicEntity ht INNER JOIN ht.usersObj1 uo1 INNER JOIN uo1.person p1 INNER JOIN ht.usersObj2 uo2 INNER JOIN uo2.person p2 INNER JOIN ht.usersObj3 uo3 INNER JOIN uo3.person p3 INNER JOIN ht.deptObj d ORDER BY ht.topicId DESC "),
	@NamedQuery(name="HelpTopicEntity.findUsers",query="SELECT u.urId,u.urLoginName,u.deptId,d.dn_Name,p.personId,p.firstName,p.lastName,dept.dept_Name FROM Users u INNER JOIN u.designation d INNER JOIN d.department dept INNER JOIN u.person p WHERE u.status='Active'"),
	@NamedQuery(name = "HelpTopicEntity.setHelpTopicStatus", query = "UPDATE HelpTopicEntity ht SET ht.status = :status WHERE ht.topicId = :topicId"),
	@NamedQuery(name = "HelpTopicEntity.getDistinctHelpTopics", query = "SELECT DISTINCT ht.topicId,ht.topicName,ht.dept_Id FROM HelpTopicEntity ht WHERE ht.status='Active'")
})
public class HelpTopicEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TOPIC_ID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "helpTopic_seq", sequenceName = "HELP_TOPIC_SLA_SEQ") 
	@GeneratedValue(generator = "helpTopic_seq")
	private int topicId;
	
	@Column(name="TOPIC_NAME")
	@NotEmpty(message="Help topic name is required")
	private String topicName;
	
	@Column(name="TOPIC_DESC")
	@NotEmpty(message="Help topic description is required")
	private String topicDesc;
	
	@Column(name="TOPIC_CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name="NORMAL_SLA")
	@NotNull(message = "Normal sla is required")
	private int normalSLA;
	
	@Column(name="DEPT_ID", unique=true, nullable=false, precision=11, scale=0)
	@NotNull(message = "Department is not selected")
	private int dept_Id;
	
	@OneToOne
	@JoinColumn(name = "DEPT_ID", insertable = false, updatable = false, nullable = false)
    private Department deptObj;
	
	@Column(name="LEVEL1_USER", unique=true, nullable=false, precision=11, scale=0)
	@NotNull(message = "Level one user is not selected")
	private int level1User;
	
	@OneToOne	 
	@JoinColumn(name = "LEVEL1_USER", insertable = false, updatable = false, nullable = false)
    private Users usersObj1;
	
	@Column(name="LEVEL1_SLA")
	@NotNull(message = "Level one sla is required")
	private int level1SLA;
	
	@Column(name="LEVEL2_USER", unique=true, nullable=false, precision=11, scale=0)
	@NotNull(message = "Level two user is not selected")
	private int level2User;
	
	@OneToOne	 
	@JoinColumn(name = "LEVEL2_USER", insertable = false, updatable = false, nullable = false)
    private Users usersObj2;
	
	@Column(name="LEVEL2_SLA")
	@NotNull(message = "Level two sla is required")
	private int level2SLA;
	
	@Column(name="LEVEL3_USER", unique=true, nullable=false, precision=11, scale=0)
	@NotNull(message = "Level three user is not selected")
	private int level3User;
	
	@OneToOne	 
	@JoinColumn(name = "LEVEL3_USER", insertable = false, updatable = false, nullable = false)
    private Users usersObj3;
	
	@Column(name="LEVEL3_SLA")
	@NotNull(message = "Level three sla is required")
	private int level3SLA;
	
	@Column(name="LEVEL1_NOTIFIEDUSERS")
	private String level1NotifiedUsers;
	
	@Column(name="LEVEL2_NOTIFIEDUSERS")
	private String level2NotifiedUsers;
	
	@Column(name="LEVEL3_NOTIFIEDUSERS")
	private String level3NotifiedUsers;
	
	@Column(name="STATUS")
	@NotEmpty(message="Help topic status is required")
	private String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getTopicDesc() {
		return topicDesc;
	}

	public void setTopicDesc(String topicDesc) {
		this.topicDesc = topicDesc;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public int getNormalSLA() {
		return normalSLA;
	}

	public void setNormalSLA(int normalSLA) {
		this.normalSLA = normalSLA;
	}

	public int getLevel1User() {
		return level1User;
	}

	public void setLevel1User(int level1User) {
		this.level1User = level1User;
	}

	public Users getUsersObj1() {
		return usersObj1;
	}

	public void setUsersObj1(Users usersObj1) {
		this.usersObj1 = usersObj1;
	}

	public int getLevel1SLA() {
		return level1SLA;
	}

	public void setLevel1SLA(int level1sla) {
		level1SLA = level1sla;
	}

	public int getLevel2User() {
		return level2User;
	}

	public void setLevel2User(int level2User) {
		this.level2User = level2User;
	}

	public Users getUsersObj2() {
		return usersObj2;
	}

	public void setUsersObj2(Users usersObj2) {
		this.usersObj2 = usersObj2;
	}

	public int getLevel2SLA() {
		return level2SLA;
	}

	public void setLevel2SLA(int level2sla) {
		level2SLA = level2sla;
	}

	public int getLevel3User() {
		return level3User;
	}

	public void setLevel3User(int level3User) {
		this.level3User = level3User;
	}

	public Users getUsersObj3() {
		return usersObj3;
	}

	public void setUsersObj3(Users usersObj3) {
		this.usersObj3 = usersObj3;
	}

	public int getLevel3SLA() {
		return level3SLA;
	}

	public void setLevel3SLA(int level3sla) {
		level3SLA = level3sla;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Timestamp getLastUpdatedDT() {
		return lastUpdatedDT;
	}

	public void setLastUpdatedDT(Timestamp lastUpdatedDT) {
		this.lastUpdatedDT = lastUpdatedDT;
	}
	
	public int getDept_Id() {
		return dept_Id;
	}

	public void setDept_Id(int dept_Id) {
		this.dept_Id = dept_Id;
	}

	public Department getDeptObj() {
		return deptObj;
	}

	public void setDeptObj(Department deptObj) {
		this.deptObj = deptObj;
	}

	@PrePersist
	 protected void onCreate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  createdBy = (String) SessionData.getUserDetails().get("userID");	  
	 }
	 
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	 }

	public String getLevel1NotifiedUsers() {
		return level1NotifiedUsers;
	}

	public void setLevel1NotifiedUsers(String level1NotifiedUsers) {
		this.level1NotifiedUsers = level1NotifiedUsers;
	}

	public String getLevel2NotifiedUsers() {
		return level2NotifiedUsers;
	}

	public void setLevel2NotifiedUsers(String level2NotifiedUsers) {
		this.level2NotifiedUsers = level2NotifiedUsers;
	}

	public String getLevel3NotifiedUsers() {
		return level3NotifiedUsers;
	}

	public void setLevel3NotifiedUsers(String level3NotifiedUsers) {
		this.level3NotifiedUsers = level3NotifiedUsers;
	}
	
}
