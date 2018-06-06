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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name="MANPOWER_APPROVAL")
@NamedQueries({
@NamedQuery(name="Users.getAllUsersDetails",query="SELECT ur FROM ManPowerApproval ur WHERE ur.userId1=:urId"),
@NamedQuery(name="Users.personId",query="SELECT ur.personId FROM Users ur WHERE ur.urId=:urId"),
@NamedQuery(name="Users.getreqInLevel",query="SELECT ur.reqInLevel FROM Person ur WHERE ur.personId=:personid"),

@NamedQuery(name="Users.deleteApprovalData",query="DELETE FROM ManPowerApproval u WHERE u.userId1=:urId"),
@NamedQuery(name="Users.getLevel",query="SELECT ur.level FROM TransactionMaster ur WHERE ur.tId=(SELECT ur.transId FROM Person ur WHERE ur.personId=:personid)"),
@NamedQuery(name="Users.updateUserStatus",query="UPDATE Users u SET u.status = :status WHERE u.urId=:urId"),
@NamedQuery(name="Users.updateReqInLevel",query="UPDATE Person u SET u.reqInLevel = :reqInLevel WHERE u.personId=:personid"),
})
public class ManPowerApproval {
	
	
	@Id
	@SequenceGenerator(name = "manApp_seq", sequenceName = "MANPOWER_APPROVAL_SEQ") 
	@GeneratedValue(generator = "manApp_seq")
	@Column(name = "APP_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private int app_id;
	

	
	@Column(name="USER_ID1")
	private int userId1;
	

	@Column(name="STATUS")
	private String status;
	
	@Column(name="APPROVED_BY")
	private String approvedBy;
	
	@Column(name = "APPROVED_DATE")
	private Date approveddate;

	
	


	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}





	
	
	
	public int getApp_id() {
		return app_id;
	}

	

	public void setApp_id(int app_id) {
		this.app_id = app_id;
	}



	public int getUserId1() {
		return userId1;
	}



	public void setUserId1(int userId1) {
		this.userId1 = userId1;
	}



	public String getApprovedBy() {
		return approvedBy;
	}



	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}



	public Date getApproveddate() {
		return approveddate;
	}



	public void setApproveddate(Date approveddate) {
		this.approveddate = approveddate;
	}



	public void setApproveddate(long time) {
		// TODO Auto-generated method stub
		
	}

}
