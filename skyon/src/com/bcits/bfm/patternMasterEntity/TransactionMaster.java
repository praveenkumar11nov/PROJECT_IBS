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

@Entity
@Table(name = "PETTY_TRANSACTION_MASTER")
@NamedQueries
({
	
	@NamedQuery(name="TransactionManager.readAllPatternNames",query="SELECT t FROM TransactionMaster t "),
	@NamedQuery(name="TransactionMaster.findTransactionNames",query="SELECT t FROM TransactionMaster t WHERE t.processName=:name AND t.transactionStatus='Active'"),
	@NamedQuery(name="TransactionMaster.findTransactionNameUniqueness",query="SELECT t.name FROM TransactionMaster t"),
	@NamedQuery(name="TransactionMaster.findAlls",query="SELECT t.tId,t.name,t.code,t.description,t.level,t.transactionStatus,t.processName FROM TransactionMaster t  ORDER BY t.tId DESC"),
	@NamedQuery(name = "TransactionMaster.UpdateStatus",query="UPDATE TransactionMaster t SET  t.transactionStatus= :status WHERE t.tId= :tId"),
	@NamedQuery(name="TransactionMaster.findLevel",query="select t.tId,t.code,t.level,t.transactionStatus FROM TransactionMaster t WHERE t.tId= :tId"),
	@NamedQuery(name="TransactionMaster.processNames",query="SELECT t.processName FROM TransactionMaster t WHERE t.tId IN(SELECT tm.tId FROM PettyTransactionManager tm)"),
	@NamedQuery(name="TransactionMaster.getLevelofTransaction",query="select t.level from TransactionMaster t WHERE t.tId=:tId"),
	@NamedQuery(name="TransactionMaster.getLevelFromTransDetail",query="select MAX(t.lNum) from TransactionDetail t WHERE t.tId=:tId"),
	
})


public class TransactionMaster  {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "t_seq", sequenceName = "TRANSCTION_SEQ") 
	@GeneratedValue(generator = "t_seq")
	@Column(name = "TID", unique = true, nullable = false, precision = 10, scale = 0)
	private int tId;
	  
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "CODE")	
	private String code;	
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "LEVEL_NO")
	private int level;
	@Column(name="TX_STATUS")
	private String transactionStatus;
	
	@Column(name = "PROCESS_NAME")
	private String processName;
	
	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int gettId() {
		return tId;
	}

	public void settId(int tId) {
		this.tId = tId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}
 
	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
