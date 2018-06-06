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

@Entity
@Table(name = "PROJECT_LOCATION")
@NamedQueries({
	@NamedQuery(name="ProjectLocation.findAll",query="SELECT p FROM ProjectLocation p"),
	@NamedQuery(name="ProjectLocation.getProjectLocationId",query="SELECT p FROM ProjectLocation p WHERE p.lastUpdatedDate = :lastUpdatedDate")
})
public class ProjectLocation 
{
	@Id     
    @SequenceGenerator(name = "projectLocationSeq", sequenceName = "PROJECTLOCATION_SEQ")
	@GeneratedValue(generator = "projectLocationSeq")
    @Column(name="PROJECT_LOCATION_ID")
	private int projectLocationId;
	@Column(name="STATE_ID")
	private int stateId;
	@Column(name="PROJECT_LOCATION_NAME")
	private String projectLocationName;
	
	@Column(name="LASTUPDATED_DATE")
	private Timestamp lastUpdatedDate;
	
	
	public int getProjectLocationId() {
		return projectLocationId;
	}
	public void setProjectLocationId(int projectLocationId) {
		this.projectLocationId = projectLocationId;
	}
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public String getProjectLocationName() {
		return projectLocationName;
	}
	public void setProjectLocationName(String projectLocationName) {
		this.projectLocationName = projectLocationName;
	}
	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	
	
	
}
