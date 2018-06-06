package com.bcits.bfm.patternMasterEntity;

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
import javax.persistence.Transient;


@SuppressWarnings("serial")
@Entity
@Table(name = "PETTY_TRANSACTION_MANAGERS")
@NamedQueries
({
	
	@NamedQuery(name="Users.getlevel",query="SELECT t.level FROM TransactionMaster t WHERE t.tId=:transId"),
	@NamedQuery(name="pettytransactionmanager.readAll",query="SELECT ptm FROM PettyTransactionManager ptm"),
//	@NamedQuery(name="TransactionManager.findTransaction",query="SELECT ptm.transManageId,ptm.tId,ptm.transactionMaster.name,ptm.transactionMaster.code,ptm.transactionMaster.level,ptm.transactionMaster.description,ptm.transactionMaster.processName FROM PettyTransactionManager ptm WHERE ptm.tId=ptm.transactionMaster.tId "),
@NamedQuery(name="TransactionManager.readAllProcessNames",query="SELECT t.processId,t.processName FROM ProcessMaster t "),
@NamedQuery(name="TransactionManager.getTransactionId",query="SELECT t.tId FROM PettyTransactionManager t WHERE t.tId IN(SELECT tm.tId FROM TransactionMaster tm WHERE tm.processName LIKE :process)"),
@NamedQuery(name="TransactionManager.getTransactionLevel",query="SELECT t.level FROM TransactionMaster t WHERE t.tId=:transId"),
@NamedQuery(name="TransactionManager.findTransaction",query="SELECT ptm.transManageId,ptm.tId,ptm.transactionMaster.name,ptm.transactionMaster.code,ptm.transactionMaster.level,ptm.transactionMaster.description,ptm.transactionMaster.processName FROM PettyTransactionManager ptm WHERE ptm.tId=ptm.transactionMaster.tId"),
@NamedQuery(name="Users.getTransId",query="SELECT t.tId FROM PettyTransactionManager t WHERE t.tId IN(SELECT tm.tId FROM TransactionMaster tm WHERE tm.processName LIKE 'Manpower process')"),
@NamedQuery(name="PettyTransactionManager.findIdOfPettyTransactionManager",query="SELECT t FROM PettyTransactionManager t"),

})
public class PettyTransactionManager {
	@Id
	@SequenceGenerator(name = "req_seq", sequenceName = "TRANS_MANAGER_SEQS") 
	@GeneratedValue(generator = "req_seq")
	@Column(name = "TRANS_MANAGER_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private int transManageId;

	
	
	@Column(name = "TRANS_ID")
	private int tId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TRANS_ID", insertable = false, updatable = false, nullable = false)
	private TransactionMaster transactionMaster;

	
	
	@Column(name="PROCESS_NAME")
	private String processName;
	

	
	public int getTransManageId() {
		return transManageId;
	}

	public void setTransManageId(int transManageId) {
		this.transManageId = transManageId;
	}

	public TransactionMaster getTransactionMaster() {
		return transactionMaster;
	}

	public void setTransactionMaster(TransactionMaster transactionMaster) {
		this.transactionMaster = transactionMaster;
	}

	public int gettId() {
		return tId;
	}

	public void settId(int tId) {
		this.tId = tId;
	}

	
	
}
