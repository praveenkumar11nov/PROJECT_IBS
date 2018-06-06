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

/**
 * Entity which includes attributes and their getters and setters mapping with 'DR_GROUP_ID' table.
 * 
 * @author Manjunath Krishnappa
 * @version %I%, %G%
 * @since 0.1
 */
@Entity
@Table(name = "DR_GROUP_ID")
@NamedQueries({ 		
	@NamedQuery(name = "DrGroupId.getNextVal", query = "SELECT d.id FROM DrGroupId d WHERE d.createdBy = :createdBy AND d.lastUpdatedDt = :lastUpdatedDt")
	})
public class DrGroupId
{
	@Id
	@SequenceGenerator(name = "drGroupIdSeq", sequenceName = "DR_GROUP_ID_SEQ")
	@GeneratedValue(generator = "drGroupIdSeq")
	@Column(name = "ID")
	private int id;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;

	public DrGroupId(String createdBy, Timestamp lastUpdatedDt)
	{
		this.createdBy = createdBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}

	/**
	 * Gets the Id of this group.
	 * 
	 * @return This arms Id.
	 * @since 0.1
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * Changes the Id of this group.
	 * 
	 * @param id
	 *            This group's new Id.
	 * @since 0.1
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Gets this group created by user name.
	 * 
	 * @return Created by user name of this group
	 * @since 0.1
	 */
	public String getCreatedBy()
	{
		return createdBy;
	}

	/**
	 * Changes the user name who created this group.
	 * 
	 * @param createdBy
	 *            New user name who created this group.
	 * @since 0.1
	 */
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	/**
	 * Gets this group last updated date time.
	 * 
	 * @return Last updated date time of this group.
	 * @since 0.1
	 */
	public Timestamp getLastUpdatedDt()
	{
		return lastUpdatedDt;
	}

	/**
	 * Changes the last updated date time this group.
	 * 
	 * @param lastUpdatedDt
	 *            New last updated date time of this group.
	 * @since 0.1
	 */
	public void setLastUpdatedDt(Timestamp lastUpdatedDt)
	{
		this.lastUpdatedDt = lastUpdatedDt;
	}

}
