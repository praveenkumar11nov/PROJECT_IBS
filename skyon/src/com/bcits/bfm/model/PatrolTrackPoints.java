package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "PATROL_TRACK_POINTS")

@NamedQueries({ 
	@NamedQuery(name = "PatrolTrackPoints.UpdateStatus",query="UPDATE PatrolTrackPoints pp SET  pp.status = :ptpStatus WHERE pp.ptpId = :ptpId"),
	//@NamedQuery(name = "PatrolTrackPoints.findAll", query = "SELECT pp FROM PatrolTrackPoints pp ORDER BY pp.ptpId DESC"),
	@NamedQuery(name = "PatrolTrackPoints.findPatrolPointExceptId", query = "SELECT pp FROM PatrolTrackPoints pp WHERE pp.ptpId NOT IN (:ptpId) ORDER BY pp.ptpId DESC"),
	@NamedQuery(name = "PatrolTrackPoints.PatrolTrackPointInstanceById", query = "SELECT pp from PatrolTrackPoints pp where pp.ptpId=:ptpId"),
	@NamedQuery(name = "PatrolTrackPoints.findAllBasedOnPtId", query = "SELECT pp FROM PatrolTrackPoints pp WHERE pp.ptId=:ptId"),
	@NamedQuery(name = "PatrolTrackPoints.getStatus", query = "SELECT pp.status FROM PatrolTrackPoints pp ORDER BY pp.ptpId DESC"),
	@NamedQuery(name = "PatrolTrackPoints.getTimeIntervals", query = "SELECT pp.ptpVisitInterval FROM PatrolTrackPoints pp ORDER BY pp.ptpId DESC"),
	@NamedQuery(name = "PatrolTrackPoints.getSequences", query = "SELECT pp.ptpSequence FROM PatrolTrackPoints pp ORDER BY pp.ptpId DESC"),
	@NamedQuery(name = "PatrolTrackPoints.ptIds", query = "SELECT pp.ptId FROM PatrolTrackPoints pp WHERE pp.ptId=:ptId "),
	@NamedQuery(name = "PatrolTrackPoints.getStatusBasedOnId", query = "SELECT pp.status FROM PatrolTrackPoints pp WHERE pp.ptpId=:ptpId "),
	@NamedQuery(name = "PatrolTrackPoints.findAll", query = "SELECT pp.ptpId,pt.ptName,ac.apName,ac.apId,pp.ptpSequence,pp.ptpVisitInterval,pp.status,pp.lastUpdatedBy,pp.lastUpdatedDt,pp.createdBy,pt.ptId FROM PatrolTrackPoints pp INNER JOIN pp.patrolTracks pt INNER JOIN pp.accessPoints ac ORDER BY pp.ptpId DESC"),
})

public class PatrolTrackPoints {
	
	@Id
	@SequenceGenerator(name = "PATROLTRACKPOINTS_SEQ", sequenceName = "PATROLTRACKPOINTS_SEQ")
	@GeneratedValue(generator = "PATROLTRACKPOINTS_SEQ")
	@Column(name = "PTP_ID")
	private int ptpId;
	
	
	@Min(value = 1, message = "'PatrolTrack' should not contain empty and invalid name")
	@Column(name = "PT_ID", precision = 11, scale = 0)
	private int ptId;
	
	@Min(value = 1, message = "'Access point Name' should not contain empty and invalid name.")
	@Column(name = "ACCESS_POINT_ID", precision = 11, scale = 0)
	private int arId;
	
	@Column(name = "PTP_SEQUENCE")
	private int ptpSequence;
	
	@NotEmpty(message = "'Time Interval' should not contain empty and invalid characters")
	@Column(name = "PTP_VISIT_INTERVAL")
	private String ptpVisitInterval;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DT")
	private String lastUpdatedDt;
	
	/*@OneToOne
	@JoinColumn(name = "AR_ID", insertable = false, updatable = false, nullable = false)
	private AccessRepository accessRepository;*/
	
	@OneToOne
	@JoinColumn(name = "PT_ID", insertable = false, updatable = false, nullable = false)
	private PatrolTracks patrolTracks;

	@OneToOne
	@JoinColumn(name = "ACCESS_POINT_ID", insertable = false, updatable = false, nullable = false)
	private AccessPoints accessPoints;
	

	public PatrolTrackPoints(int ptpId, int ptId, int arId,
			int ptpSequence, String ptpVisitInterval, String status,
			String createdBy, String lastUpdatedBy, String lastUpdatedDt, PatrolTracks patrolTracks) {
		super();
		this.ptpId = ptpId;
		this.ptId = ptId;
		this.arId = arId;
		this.ptpSequence = ptpSequence;
		this.ptpVisitInterval = ptpVisitInterval;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
		//this.accessRepository = accessRepository;
		this.patrolTracks = patrolTracks;
	}
	
	

	public AccessPoints getAccessPoints() {
		return accessPoints;
	}



	public void setAccessPoints(AccessPoints accessPoints) {
		this.accessPoints = accessPoints;
	}



	public PatrolTrackPoints() {
		super();
	}



	public int getPtpId() {
		return ptpId;
	}

	public void setPtpId(int ptpId) {
		this.ptpId = ptpId;
	}

	public int getPtId() {
		return ptId;
	}

	public void setPtId(int ptId) {
		this.ptId = ptId;
	}

	public int getArId() {
		return arId;
	}

	public void setArId(int arId) {
		this.arId = arId;
	}

	public int getPtpSequence() {
		return ptpSequence;
	}

	public void setPtpSequence(int ptpSequence) {
		this.ptpSequence = ptpSequence;
	}

	public String getPtpVisitInterval() {
		return ptpVisitInterval;
	}

	public void setPtpVisitInterval(String ptpVisitInterval) {
		this.ptpVisitInterval = ptpVisitInterval;
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

	public String getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(String lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}
	
	/*public AccessRepository getAccessRepository() {
		return accessRepository;
	}

	public void setAccessRepository(AccessRepository accessRepository) {
		this.accessRepository = accessRepository;
	}*/
	
	public PatrolTracks getPatrolTracks() {
		return patrolTracks;
	}

	public void setPatrolTracks(PatrolTracks patrolTracks) {
		this.patrolTracks = patrolTracks;
	}
	
	
	
	

}
