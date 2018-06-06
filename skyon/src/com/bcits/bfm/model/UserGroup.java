package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * UserGroup entity
 * @author Shashidhar Bemalgi
 *
 */
@Entity
@Table(name = "USER_GROUP")
@NamedQueries({
	
	@NamedQuery(name = "allDetailsById", query = "SELECT g.gr_name FROM Groups g,UserGroup ug  where  g.gr_id=ug.UG_GR_ID and ug.UG_UR_ID=:userID"),
		/*@NamedQuery(name = "ManageUserGroups.findAll", query = "SELECT ug FROM UserGroup ug"),*/
	@NamedQuery(name = "Users.getUserIdName", query = "SELECT u.urLoginName,u.urId FROM Users u WHERE u.urId!=1"),
		@NamedQuery(name = "ManageUserGroups.findById", query = "SELECT ug FROM UserGroup ug WHERE ug.UG_GR_ID = :UG_GR_ID "),
		@NamedQuery(name = "CheckUser", query = "SELECT ug FROM UserGroup ug WHERE ug.UG_UR_ID = :id and ug.UG_GR_ID = :groupid"),
		@NamedQuery(name = "SelectGroup", query = "SELECT u.urLoginName,u.urId from Users u, UserGroup ug where u.urId = ug.UG_UR_ID and ug.UG_GR_ID = :gid"),
		@NamedQuery(name = "DeleteUser", query = "DELETE FROM UserGroup ug WHERE ug.UG_GR_ID = :groupId and ug.UG_UR_ID = :userId"),
		@NamedQuery(name = "UserGroup.getUserGroupInstanceBasedOnUserIdAndGroupId", query = "SELECT ug FROM UserGroup ug WHERE ug.UG_UR_ID = :UG_UR_ID AND ug.UG_GR_ID = :UG_GR_ID"),
		@NamedQuery(name = "Group.getActiveGroups", query = "SELECT g FROM Groups g"),
	
	@NamedQuery(name = "UserGroup.setAllPrevGroupsBasedOnGroupId", query = "UPDATE UserGroup ug SET ug.UG_GR_ID = (SELECT g.gr_id FROM Groups g WHERE g.gr_name = 'Invalid') WHERE  ug.UG_GR_ID = :groupId"),
	/*@NamedQuery(name = "UserRole.removeAllRedundantUserRoleRecords", query = "DELETE FROM UserRole ur1 WHERE ur1.ROWID NOT IN (SELECT MIN(UserRole.ROWID) FROM UserRole ur2 GROUP BY ur2.uro_Rl_Id, ur2.uro_Ur_Id)")
*/
	
	})
public class UserGroup {
	@Id
	@SequenceGenerator(name = "ugseq", sequenceName = "USER_GROUP_SEQ")
	@GeneratedValue(generator = "ugseq")
	private int UG_ID;
	private int UG_GR_ID;
	private int UG_UR_ID;
	private String CREATED_BY;
	private String LAST_UPDATED_BY;
	private Timestamp LAST_UPDATED_DT;
	
	/*@Formula("(SELECT WM_CONCAT(g.GR_NAME) FROM USER_GROUP ug, GROUPS g WHERE ug.UG_UR_ID=6 AND ug.UG_GR_ID=g.GR_ID)")
	private String aggreGroupName;
	*/
	public UserGroup() {
	}

	public UserGroup(int uG_ID, int uG_GR_ID, int uG_UR_ID, String cREATED_BY,
			String lAST_UPDATED_BY, Timestamp lAST_UPDATED_DT) {
		super();
		UG_ID = uG_ID;
		UG_GR_ID = uG_GR_ID;
		UG_UR_ID = uG_UR_ID;
		CREATED_BY = cREATED_BY;
		LAST_UPDATED_BY = lAST_UPDATED_BY;
		LAST_UPDATED_DT = lAST_UPDATED_DT;
	}

	public int getUG_ID() {
		return UG_ID;
	}

	public void setUG_ID(int uG_ID) {
		UG_ID = uG_ID;
	}

	public int getUG_GR_ID() {
		return UG_GR_ID;
	}

	public void setUG_GR_ID(int uG_GR_ID) {
		UG_GR_ID = uG_GR_ID;
	}

	public int getUG_UR_ID() {
		return UG_UR_ID;
	}

	public void setUG_UR_ID(int uG_UR_ID) {
		UG_UR_ID = uG_UR_ID;
	}

	public String getCREATED_BY() {
		return CREATED_BY;
	}

	public void setCREATED_BY(String cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}

	public String getLAST_UPDATED_BY() {
		return LAST_UPDATED_BY;
	}

	public void setLAST_UPDATED_BY(String lAST_UPDATED_BY) {
		LAST_UPDATED_BY = lAST_UPDATED_BY;
	}

	public Timestamp getLAST_UPDATED_DT() {
		return LAST_UPDATED_DT;
	}

	public void setLAST_UPDATED_DT(Timestamp lAST_UPDATED_DT) {
		LAST_UPDATED_DT = lAST_UPDATED_DT;
	}

	
}
