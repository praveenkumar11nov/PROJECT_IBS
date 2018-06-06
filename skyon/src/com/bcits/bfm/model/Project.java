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
import javax.persistence.UniqueConstraint;

/**
 * Project entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PROJECT", uniqueConstraints = @UniqueConstraint(columnNames = "PROJECT_NAME"))
@NamedQueries({
		/*@NamedQuery(name = "Project.projectname", query = "select p.projectName from Project p where p.projectName=:pName"),*/
	@NamedQuery(name="Project.NO_OF_TOWER",query="select p.no_OF_TOWERS from Project p where p.projectId=:projectID"),	
	@NamedQuery(name = "Project.findAll", query = "select p from Project p"),
	@NamedQuery(name = "Project.findAllProjects", query = "SELECT p.projectId,p.projectName,p.no_OF_TOWERS,p.projectAddress,p.project_PINCODE,p.projectState,p.createdBy,p.lastUpdatedBy,p.projectLocation,s.stateName,c.countryName,p.projectCountry,pl.projectLocationName FROM Project p, State s,Country c,ProjectLocation pl WHERE s.stateId = p.projectState AND p.projectCountry = c.countryId AND pl.projectLocationId = p.projectLocation "),
		@NamedQuery(name = "Project.count", query = "select count(p.projectId) from Project p"),
		@NamedQuery(name="Project.findname",query="select p.projectName from Project p "),
		@NamedQuery(name="Project.getAllUniquePinCodes",query="SELECT DISTINCT(project_PINCODE) FROM Project")
		})
public class Project implements java.io.Serializable 
{

	// Fields
	@Id
	@SequenceGenerator(name="PROJECT_SEQ",sequenceName="PROJECT_SEQ")
	@GeneratedValue(generator="PROJECT_SEQ")
	@Column(name = "PROJECT_ID", unique = true, nullable = false, precision = 11, scale = 0)
	private int projectId;
	
	@Column(name = "PROJECT_NAME", unique = true, length = 100)
	private String projectName;
	
	@Column(name = "no_OF_TOWERS", precision = 3, scale = 0)
	private int no_OF_TOWERS;
	
	/*@Column(name = "NO_OF_PROPERTIES", precision = 4, scale = 0)
	private int no_OF_PROPERTIES;*/
	
	@Column(name = "PROJECT_ADDRESS", length = 100)
	private String projectAddress;
	
	@Column(name = "PROJECT_PINCODE", precision = 11, scale = 0)
	private int project_PINCODE;
	
	@Column(name = "PROJECT_STATE", length = 45)
	private String projectState;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name = "PROJECT_LOCATION")
	private int projectLocation;
	
	@Column(name="PROJECT_COUNTRY")
	private int projectCountry;

	// private Timestamp lastUpdatedDt;

	// Constructors

	/** default constructor */
	public Project() {
	}

	/** minimal constructor */
	public Project(int projectId) {
		this.projectId = projectId;
	}

	/** full constructor */
	public Project(int projectId, String projectName, int no_OF_TOWERS,
			 String projectAddress, int project_PINCODE,
			String projectState, String createdBy, String lastUpdatedBy) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.no_OF_TOWERS = no_OF_TOWERS;
		//this.no_OF_PROPERTIES = NO_OF_PROPERTIES;
		this.projectAddress = projectAddress;
		this.project_PINCODE = project_PINCODE;
		this.projectState = projectState;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;

	}

	// Property accessors
	
	
	public int getProjectId() {
		return this.projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	
	public int getno_OF_TOWERS() {
		return this.no_OF_TOWERS;
	}

	public void setno_OF_TOWERS(int no_OF_TOWERS) {
		this.no_OF_TOWERS = no_OF_TOWERS;
	}

	
	/*public int getNO_OF_PROPERTIES() {
		return this.no_OF_PROPERTIES;
	}

	public void setNO_OF_PROPERTIES(int NO_OF_PROPERTIES) {
		this.no_OF_PROPERTIES = NO_OF_PROPERTIES;
	}
*/
	
	public String getProjectAddress() {
		return this.projectAddress;
	}

	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}

	
	public int getPROJECT_PINCODE() {
		return this.project_PINCODE;
	}

	public void setPROJECT_PINCODE(int PROJECT_PINCODE) {
		this.project_PINCODE = PROJECT_PINCODE;
	}

	
	public String getProjectState() {
		return this.projectState;
	}

	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}

	
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public int getProjectLocation() {
		return projectLocation;
	}

	public void setProjectLocation(int projectLocation) {
		this.projectLocation = projectLocation;
	}

	public int getProjectCountry() {
		return projectCountry;
	}

	public void setProjectCountry(int projectCountry) {
		this.projectCountry = projectCountry;
	}
	
	
	
	

	/*
	 * @Column(name = "LAST_UPDATED_DT", length = 11) public Timestamp
	 * getLastUpdatedDt() { return this.lastUpdatedDt; }
	 * 
	 * public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
	 * this.lastUpdatedDt = lastUpdatedDt; }
	 */

}