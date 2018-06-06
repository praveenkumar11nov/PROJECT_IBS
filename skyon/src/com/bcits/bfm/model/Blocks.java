package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.bcits.bfm.util.SessionData;

/**
 * BLOCK entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BLOCK")
@NamedQueries({
	@NamedQuery(name="BLocks.getAllBlocks",query="SELECT b FROM Blocks b"),
	@NamedQuery(name="Blocks.findBlockOnProjectId",query="SELECT b FROM Blocks b WHERE b.projectId = :projectId"),
	@NamedQuery(name="Blocks.getBlockNameByBlockId",query="SELECT b FROM Blocks b WHERE b.blockId=:blockId"),
	@NamedQuery(name="Project.getBlockNames",query="SELECT b.blockName FROM Blocks b WHERE b.projectId=:projectId"),
	@NamedQuery(name="Blocks.getBlockName",query="SELECT b.blockId FROM Blocks b WHERE UPPER(b.blockName)=:value"),
	@NamedQuery(name="Blocks.getProjectId",query="SELECT b.projectId FROM Blocks b WHERE UPPER(b.blockName)=:value2"),
	@NamedQuery(name="Blocks.getNoOfBlocks",query="SELECT p.no_OF_TOWERS FROM Project p WHERE p.projectId=:projectId"),
	@NamedQuery(name="Blocks.sumOfBlocks",query="SELECT COUNT(b.blockId) FROM Blocks b WHERE b.projectId=:projectId"),
	@NamedQuery(name="Blocks.getAll",query="SELECT b FROM Blocks b"),
	@NamedQuery(name="Blocks.getAllBlocksId",query="SELECT b.blockId FROM Blocks b "),
	
})
public class Blocks implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	// Fields

	private int blockId;
	private String blockName;
	private int numOfProperties;
	private int numOfParkingSlots;
	private int projectId;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;


	/** default constructor */
	public Blocks() {
	}	

	/** full constructor */
	public Blocks(int blockId, String blockName) {
		this.blockId = blockId;
		this.blockName = blockName;		
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "BLOCKS_SEQ", sequenceName = "BLOCK_SEQ")
	@GeneratedValue(generator = "BLOCKS_SEQ")
	@Column(name = "BLOCK_ID", unique = true, nullable = false, precision = 5, scale = 0)
	public int getBlockId() {
		return this.blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	@Column(name = "BLOCK_NAME",unique=true)
	@NotEmpty(message="Block Name Sholud Not Be Empty")
	public String getBlockName() {
		return this.blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	
	@Column(name = "NO_OF_PROPERTY")
	public int getNumOfProperties() {
		return numOfProperties;
	}

	public void setNumOfProperties(int numOfProperties) {
		this.numOfProperties = numOfProperties;
	}

	@Column(name = "NO_OF_PARKINGSLOTS")
	public int getNumOfParkingSlots() {
		return numOfParkingSlots;
	}

	public void setNumOfParkingSlots(int numOfParkingSlots) {
		this.numOfParkingSlots = numOfParkingSlots;
	}
	
	@Column(name = "PROJECT_ID")
	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "LAST_UPDATED_BY")	
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	@Column(name = "LAST_UPDATED_DT")
	public Timestamp getLastUpdatedDt() {
		return lastUpdatedDt;
	}
	
	
	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}
	
	@PrePersist
	protected void onCreate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}
	@PreUpdate
	protected void onUpdate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}
	
	
	
}