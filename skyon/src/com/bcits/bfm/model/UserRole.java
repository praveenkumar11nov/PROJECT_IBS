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

import org.hibernate.annotations.Formula;

/**
 * UserRole Entity
 * @author Shashidhar Bemalgi
 *
 */
@Entity
@Table(name = "USER_ROLE")
@NamedQueries({
	@NamedQuery(name = "rolesDetailsById", query = "SELECT rl.rlName FROM Role rl,UserRole url  where  rl.rlId=url.uro_Rl_Id and url.uro_Ur_Id=:userID"),
		@NamedQuery(name = "UserRole.findAll", query = "SELECT r FROM UserRole r"),
		@NamedQuery(name = "UserRole.Delete", query = "DELETE FROM UserRole WHERE uro_Id= :id"),
		@NamedQuery(name = "UserRole.getUrRlId", query = "SELECT userRole FROM UserRole userRole WHERE userRole.uro_Rl_Id= :roleId"),
		@NamedQuery(name = "Role.CheckUser", query = "SELECT ug FROM UserRole ug WHERE ug.uro_Ur_Id = :id and ug.uro_Rl_Id = :roleId"),
		@NamedQuery(name = "MaintatinanceGroup.CheckUser", query = "SELECT ug FROM MaintainanceDepartment ug WHERE ug.users = :id AND ug.department = :roleId AND ug.jobType LIKE :jobType"),
		@NamedQuery(name = "Role.DeleteUser", query = "DELETE FROM UserRole ug WHERE ug.uro_Rl_Id = :roleId and ug.uro_Ur_Id = :userId"),
		@NamedQuery(name = "SelectRole", query = "SELECT u.urLoginName,u.urId from Users u, UserRole ug where u.urId = ug.uro_Ur_Id and ug.uro_Rl_Id = :gid"),
		@NamedQuery(name = "UserRole.getUserRoleInstanceBasedOnUserIdAndRoleId", query = "SELECT ur FROM UserRole ur WHERE ur.uro_Ur_Id = :uro_Ur_Id AND ur.uro_Rl_Id = :uro_Rl_Id"),
		@NamedQuery(name = "Role.getActiveRoles", query = "SELECT r FROM Role r"),
		/*@NamedQuery(name = "Role.getActiveRoles", query = "SELECT r FROM Role r where r.rlStatus = :Active and r.rlStatus != :Invalid"),*/
		@NamedQuery(name = "UserRole.setAllPrevRolesBasedOnRoleId", query = "UPDATE UserRole ur SET ur.uro_Rl_Id = (SELECT r.rlId FROM Role r WHERE r.rlName = 'Invalid') WHERE  ur.uro_Rl_Id = :roleId"),
		/*@NamedQuery(name = "UserRole.removeAllRedundantUserRoleRecords", query = "DELETE FROM UserRole ur1 WHERE ur1.ROWID NOT IN (SELECT MIN(UserRole.ROWID) FROM UserRole ur2 GROUP BY ur2.uro_Rl_Id, ur2.uro_Ur_Id)")
	*/
		
/*
 * @NamedQuery(name = "SelectGroup",query =
 * "SELECT u from Users u, UserGroup ug where u.urId = ug.UG_UR_ID and ug.UG_GR_ID = :gid"
 * ),
 */
})
public class UserRole {
	@Id
	@SequenceGenerator(name = "USERS_SEQ", sequenceName = "USER_ROLE_SEQ")
	@GeneratedValue(generator = "USERS_SEQ")
	@Column(name = "URO_ID")
	private int uro_Id;
	
	@Column(name = "URO_RL_ID")
	private int uro_Rl_Id;
	@Column(name = "URO_UR_ID")
	private int uro_Ur_Id;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;

	@Formula("(SELECT WM_CONCAT(r.RL_NAME) FROM USER_ROLE ur,Role r WHERE ur.URO_UR_ID=URO_UR_ID AND ur.URO_RL_ID=r.RL_ID)")
	private String aggreRoleName;
	
	
	
	/*public String getAggreRoleName() {
		return aggreRoleName;
	}

	public void setAggreRoleName(String aggreRoleName) {
		this.aggreRoleName = aggreRoleName;
	}*/
	
	public UserRole() {
	}

	public UserRole(int uro_Id, int uro_Rl_Id, int uro_Ur_Id, String createdBy,
			String lastUpdatedBy, Timestamp lastUpdatedDt) {
		super();

		this.uro_Id = uro_Id;
		this.uro_Rl_Id = uro_Rl_Id;
		this.uro_Ur_Id = uro_Ur_Id;

		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}

	public int getUro_Id() {
		return uro_Id;
	}

	public void setUro_Id(int uro_Id) {
		this.uro_Id = uro_Id;
	}

	public int getUro_Rl_Id() {
		return uro_Rl_Id;
	}

	public void setUro_Rl_Id(int uro_Rl_Id) {
		this.uro_Rl_Id = uro_Rl_Id;
	}

	public int getUro_Ur_Id() {
		return uro_Ur_Id;
	}

	public void setUro_Ur_Id(int uro_Ur_Id) {
		this.uro_Ur_Id = uro_Ur_Id;

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

	public Timestamp getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

}
