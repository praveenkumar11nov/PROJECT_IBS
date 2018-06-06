package com.bcits.bfm.model;


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
import javax.validation.constraints.Min;


@NamedQueries({ 
	@NamedQuery(name = "PatrolSettings.getAllRoleIds", query = "SELECT ps.rlId FROM PatrolSettings ps ORDER BY ps.psId DESC"),
	@NamedQuery(name = "PatrolSettings.findAll", query = "SELECT ps FROM PatrolSettings ps ORDER BY ps.psId DESC"),
	@NamedQuery(name = "PatrolSettings.UpdateStatus",query="UPDATE PatrolSettings ps SET  ps.status = :psStatus WHERE ps.psId = :psId"),
	@NamedQuery(name = "PatrolSettings.getAllPatrolRoles", query = "SELECT ps.rlId FROM PatrolSettings ps"),
	
})

@Entity
@Table(name = "PATROL_SETTINGS")
public class PatrolSettings {
	
	@Id
	@SequenceGenerator(name = "psseq", sequenceName = "PATROL_SETTINGS_SEQ")
	@GeneratedValue(generator = "psseq")
	@Column(name = "PS_ID")
	private int psId;
	
	@Min(value = 1, message = "'Role' Is Not Selected")
	@Column(name = "RL_ID")
	private int rlId;
	
	@Column(name = "STATUS")
	private String status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RL_ID", nullable = false,insertable=false,updatable=false)
	private Role role;

	public PatrolSettings() {
		super();
	}

	
	public PatrolSettings(int psId, int rlId) {
		super();
		this.psId = psId;
		this.rlId = rlId;
		//this.role = role;
	}


	public int getPsId() {
		return psId;
	}

	public void setPsId(int psId) {
		this.psId = psId;
	}

	public int getRlId() {
		return rlId;
	}

	public void setRlId(int rlId) {
		this.rlId = rlId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}

	

}
