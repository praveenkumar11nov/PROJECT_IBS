package com.bcits.bfm.patternMasterEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name = "PROCESS_MASTER")
@NamedQueries
({
	@NamedQuery(name="ProcessMaster.findAllProcess",query="SELECT d FROM ProcessMaster d ORDER BY d.processId DESC"),
	@NamedQuery(name="ProcessMaster.readAllProcess",query="SELECT d.processId,d.processName FROM ProcessMaster d ORDER BY d.processId DESC"),
	@NamedQuery(name="ProcessMaster.getAllProcessType",query="SELECT d.processType FROM ProcessMaster d"),
})

public class ProcessMaster {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "p_seq", sequenceName = "PROCESSMASTER_SEQ") 
	@GeneratedValue(generator = "p_seq")
	@Column(name = "PROCESS_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private int processId;
	
	@Column(name = "PROCESS_NAME")
	private String processName;
	
	@Column(name = "PROCESS_TYPE")
	private String processType;

	
	@Column(name = "PROCESS_DESCRIPTION")
	private String processDescription;


	public int getProcessId() {
		return processId;
	}


	public void setProcessId(int processId) {
		this.processId = processId;
	}


	public String getProcessName() {
		return processName;
	}


	public void setProcessName(String processName) {
		this.processName = processName;
	}


	public String getProcessType() {
		return processType;
	}


	public void setProcessType(String processType) {
		this.processType = processType;
	}


	public String getProcessDescription() {
		return processDescription;
	}


	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}
	
	
}
