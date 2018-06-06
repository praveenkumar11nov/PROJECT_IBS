package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * ROLE entity
 * @author Shashidhar Bemalgi
 *
 */
@Entity
@Table(name = "ROLE")
@NamedQueries({
		@NamedQuery(name = "Role.findAllWithoutInvalidDefault", query = "SELECT r FROM Role r WHERE r.rlName != 'Invalid' ORDER BY r.rlId DESC"),	
		@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r ORDER BY r.rlId DESC"),
		@NamedQuery(name = "Role.checkUserExistence", query = "SELECT r FROM Role r WHERE r.rlName= :name"),
		@NamedQuery(name = "Role.RoleNameExistence", query = "SELECT r.rlName FROM Role r WHERE r.rlName= :RoleName"),
		@NamedQuery(name = "Role.UpdateStatus",query="UPDATE Role r SET  r.rlStatus = :rlStatus WHERE r.rlId = :rlId"),
		@NamedQuery(name = "Role.getAllActiveRoles",query="SELECT r from Role r WHERE r.rlStatus = 'Active'"),
		@NamedQuery(name = "Role.getRoleIdBasedOnRoleName",query="SELECT r.rlId from Role r WHERE r.rlName = :rlName"),
		/*@NamedQuery(name = "UserRole.getUserIdBasedOnRoleId",query="SELECT uro_Ur_Id FROM UserRole WHERE uro_Rl_Id = :id")*/
		/*@NamedQuery(name = "UserRole.getUserIdBasedOnRoleId",query="SELECT u.urLoginName,u.urId FROM UserRole ur JOIN Users u WHERE ur.uro_Rl_Id=:id"),*/
		@NamedQuery(name = "UserRole.getUserIdBasedOnRoleId",query="SELECT u.urLoginName,u.urId FROM Users u JOIN u.userRoles ur WHERE ur.uro_Rl_Id=:id and u.status='Active' "),
		@NamedQuery(name = "Role.getRoleNameBasedOnId",query="SELECT r.rlName from Role r WHERE r.rlId = :id"),
		@NamedQuery(name = "Role.getUserNamesBasedOnUserId",query="SELECT u.urLoginName from Users u,UserRole ur WHERE u.urId = ur.uro_Ur_Id and ur.uro_Rl_Id = :id"),
		@NamedQuery(name = "Role.findAllRole", query = "SELECT r FROM UserRole ur, Role r,Users u WHERE ur.uro_Rl_Id = r.rlId and ur.uro_Ur_Id = u.urId and ur.uro_Ur_Id=:uid")
})
public class Role {
	@Id
	@SequenceGenerator(name = "ROLE_SEQ", sequenceName = "ROLE_SEQ")
	@GeneratedValue(generator = "ROLE_SEQ")
	@Column(name = "RL_ID")
	@NotNull
	private int rlId;
	
	@Column(name = "RL_NAME")
	@NotNull
	@Pattern(regexp = "^[a-zA-Z]+[ ._a-zA-Z0-9._]*[a-zA-Z0-9]$",
    message = "Role Name field can not allow special symbols except(_ .) ")
	private String rlName;
	
	@Size(min=1,max=200,message="Role Description  Cannot be empty")
	@Column(name = "RL_DESCRIPTION")
	/*@Pattern(regexp = "^[a-zA-Z]+[._a-zA-Z0-9._]*[a-zA-Z0-9]$",message = "Role Description field can not allow special symbols except(_ .) ")*/
	private String rlDescription;
	@Column(name = "RL_STATUS")
	@NotNull
	private String rlStatus;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDate;

	

	public Role() {
	}

	public Role(int rlId, String rlName, String rlDescription, String rlStatus,
			String createdBy, String lastUpdatedBy, Timestamp lastUpdatedDate) {
		super();
		this.rlId = rlId;
		this.rlName = rlName;
		this.rlDescription = rlDescription;
		this.rlStatus = rlStatus;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public int getRlId() {
		return rlId;
	}

	public String getRlName() {
		return rlName;
	}

	public String getRlDescription() {
		return rlDescription;
	}

	public String getRlStatus() {
		return rlStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setRlId(int rlId) {
		this.rlId = rlId;
	}

	public void setRlName(String rlName) {
		this.rlName = rlName;
	}

	public void setRlDescription(String rlDescription) {
		this.rlDescription = rlDescription;
	}

	public void setRlStatus(String rlStatus) {
		this.rlStatus = rlStatus;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	@Override
	public int hashCode(){
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(this.createdBy);
	    buffer.append(this.lastUpdatedBy);
	    buffer.append(this.rlDescription);
	    buffer.append(this.rlId);
	    buffer.append(this.rlName);
	    buffer.append(this.rlStatus);
	    return buffer.toString().hashCode();
	}
	
	@Override
	public boolean equals(Object object){
	    if (object == null) return false;
	    if (object == this) return true;
	    if (this.getClass() != object.getClass())return false;
	    Role role = (Role)object;
	    if(this.hashCode()== role.hashCode())return true;
	   return false;
	}

}
