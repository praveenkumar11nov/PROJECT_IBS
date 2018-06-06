/*package com.bcits.bfm.patternMasterEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "PETTY_REQUESTAPPROVAL_MASTER")
@NamedQueries
({
	@NamedQuery(name="RequestApprovalMaster.findAll",query="SELECT t.approvalId,t.approvalDate,t.approvalAmount,t.approvalAction,t.approvalRemark,t.reqId,t.requestMaster.amount,t.createdBy,t.createdDate,t.officeId FROM RequestApprovalMaster t where t.reqId=:reqId ORDER BY t.approvalId DESC"),
	@NamedQuery(name="RequestMaster.updateStatus",query="UPDATE RequestMaster d SET d.pettyStatus = :approvalAction WHERE d.reqId=:reqId "),
	@NamedQuery(name="RequestApprovalMaster.updateSanctionAmount",query="UPDATE RequestApprovalMaster d SET d.approvalAmount = :approvalAmount WHERE d.reqId=:reqId "),
	@NamedQuery(name="RequestMaster.findAllApprovalRequest",query="SELECT r.reqId,r.requestedBy,r.requestedFor,r.amount,r.requestDate,r.remark,"+
	"r.pettyStatus,emp.empId,emp.empName FROM RequestMaster r INNER JOIN r.employeeMaster emp WHERE"+
			" r.pettyStatus=:pettyStatus OR r.pettyStatus=:pettyStatus1  ORDER BY r.reqId DESC"), OR r.approvalAction=:approvalAction
	@NamedQuery(name="RequestApprovalMaster.findAllApprovalRequest",query="SELECT r.requestMaster.reqId,r.requestMaster.requestedBy,r.requestMaster.requestedFor,r.requestMaster.amount,r.requestMaster.requestDate,r.requestMaster.remark,r.requestMaster.pettyStatus,r.requestMaster.employeeMaster.empId,r.requestMaster.employeeMaster.empName FROM RequestApprovalMaster r ORDER BY r.approvalId DESC"),
	@NamedQuery(name="RequestApprovalMaster.updateBalance",query="UPDATE RequestApprovalMaster d SET d.amountSpent=:amountSpent,d.amountReturned=:amountReturned,d.amountBalance=:amountBalance WHERE d.reqId=:reqId AND d.amountBalance IS NOT NULL"),
})
public class RequestApprovalMaster  {
	
	@Id
	@SequenceGenerator(name = "reqApp_seq", sequenceName = "REQUESTAPPROVAL_MASTER_SEQ") 
	@GeneratedValue(generator = "reqApp_seq")
	@Column(name = "REQ_APPROVAL_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private int approvalId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "APPROVAL_DATE")
	private Date approvalDate;
	
	@Column(name = "SANCTIONED_AMOUNT")
	private long approvalAmount;
	
	@Column(name = "ACTION")
	private String approvalAction;
	
	@Column(name = "REMARK")
	private String approvalRemark;
	
	@Column(name = "AMOUNT_SPENT")
	private Long amountSpent;
	
	@Column(name = "AMOUNT_RETURNED")
	private Long amountReturned;
	
	@Column(name = "AMOUNT_BALANCE")
	private Long amountBalance;
	
	@Column(name = "REQ_ID")
	private int reqId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REQ_ID", insertable = false, updatable = false, nullable = false)
	private RequestMaster requestMaster;
	
	@Column(name = "OFFICE_ID")
	private int officeId;

	public int getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(int approvalId) {
		this.approvalId = approvalId;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public long getApprovalAmount() {
		return approvalAmount;
	}

	public void setApprovalAmount(long approvalAmount) {
		this.approvalAmount = approvalAmount;
	}

	public String getApprovalAction() {
		return approvalAction;
	}

	public void setApprovalAction(String approvalAction) {
		this.approvalAction = approvalAction;
	}

	public String getApprovalRemark() {
		return approvalRemark;
	}

	public void setApprovalRemark(String approvalRemark) {
		this.approvalRemark = approvalRemark;
	}

	public RequestMaster getRequestMaster() {
		return requestMaster;
	}

	public void setRequestMaster(RequestMaster requestMaster) {
		this.requestMaster = requestMaster;
	}
	
	public int getReqId() {
		return reqId;
	}

	public void setReqId(int reqId) {
		this.reqId = reqId;
	}

	public Long getAmountSpent() {
		return amountSpent;
	}

	public void setAmountSpent(Long amountSpent) {
		this.amountSpent = amountSpent;
	}

	public Long getAmountReturned() {
		return amountReturned;
	}

	public void setAmountReturned(Long amountReturned) {
		this.amountReturned = amountReturned;
	}

	public Long getAmountBalance() {
		return amountBalance;
	}

	public void setAmountBalance(Long amountBalance) {
		this.amountBalance = amountBalance;
	}

	public int getOfficeId() {
		return officeId;
	}

	public void setOfficeId(int officeId) {
		this.officeId = officeId;
	}
}
*/