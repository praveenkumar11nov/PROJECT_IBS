package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;




@Entity
@Table(name="AMR_DATA")
@NamedQueries({
	@NamedQuery(name = "AMRDataEntity.getAMRAccountDetails", query = "select eme.elMeterId,a.accountId,p.personId,(select pp.propertyId from Property pp where pp.propertyId = a.propertyId),(select pp.blocks.blockId from Property pp where pp.propertyId = a.propertyId), (select pp.property_No from Property pp where pp.propertyId = a.propertyId),(select pp.blocks.blockName from Property pp where pp.propertyId = a.propertyId),(select amr.columnName from AMRProperty amr where amr.propertyId = a.propertyId) from ElectricityMetersEntity eme inner join eme.account a inner join a.person p"),	
    @NamedQuery(name="AmrDataDetails.getAMRData",query="SELECT ad from AMRDataEntity ad WHERE  ad.readingDate >= TO_DATE(:strDate, 'YYYY-MM-DD') and ad.readingDate<= TO_DATE(:pesentDate, 'YYYY-MM-DD')"), 
	 @NamedQuery(name="AmrDataDetailsOnMonth.getAMRData",query="SELECT ad from AMRDataEntity ad WHERE  ad.readingDate >= TO_DATE(:strDate, 'YYYY-MM-DD') and ad.readingDate<= TO_DATE(:pesentDate, 'YYYY-MM-DD') and ad.propertyId=:propid") 
})
public class AMRDataEntity {

	@Id
	@SequenceGenerator(name="SEQ_AMR_DATA" ,sequenceName="AMR_DATA_SEQ")
	@GeneratedValue(generator="SEQ_AMR_DATA")
	@Column(name="AMR_ID") 
	private int amrDataId;
	
	@Column(name="READING_DATE")
	private Timestamp readingDate;
	


	@Column(name="PROPERTY_ID")
	private int propertyId;
	
	@Column(name="ACCOUNT_ID")
	private int AccountId;
	
	@Column(name="BLOCK_ID")
	private int blockId;
	
	@Column(name="ELM_ID")
	private int meterId;
	
	@Column(name="EL_READING")
	private double elReading;
	
	@Column(name="DG_READING")
	private double dgReading;
	
	@Column(name="PERSON_ID")
	private int personId;
	
	
	@OneToOne	 
	@JoinColumn(name = "ACCOUNT_ID", insertable = false, updatable = false, nullable = false)
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private Account accountobj;
	
	
	@OneToOne	 
	@JoinColumn(name = "PERSON_ID", insertable = false, updatable = false, nullable = false)
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private Person personobj;
	
	
	@OneToOne	 
	@JoinColumn(name = "ELM_ID", insertable = false, updatable = false, nullable = false)
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private ElectricityMetersEntity elEntity;
	
	@OneToOne	 
	@JoinColumn(name = "PROPERTY_ID", insertable = false, updatable = false, nullable = false)
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private Property propobj;
	
	@OneToOne	 
	@JoinColumn(name = "BLOCK_ID", insertable = false, updatable = false, nullable = false)
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private Blocks blockObj;
	
	
	public int getAmrDataId() {
		return amrDataId;
	}

	public void setAmrDataId(int amrDataId) {
		this.amrDataId = amrDataId;
	}

	public Timestamp getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(Timestamp readingDate) {
		this.readingDate = readingDate;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public int getAccountId() {
		return AccountId;
	}

	public void setAccountId(int accountId) {
		AccountId = accountId;
	}

	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	public int getMeterId() {
		return meterId;
	}

	public void setMeterId(int meterId) {
		this.meterId = meterId;
	}

	public double getElReading() {
		return elReading;
	}

	public void setElReading(double elReading) {
		this.elReading = elReading;
	}

	public double getDgReading() {
		return dgReading;
	}

	public void setDgReading(double dgReading) {
		this.dgReading = dgReading;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public Account getAccountobj() {
		return accountobj;
	}

	public void setAccountobj(Account accountobj) {
		this.accountobj = accountobj;
	}

	public Person getPersonobj() {
		return personobj;
	}

	public void setPersonobj(Person personobj) {
		this.personobj = personobj;
	}

	public ElectricityMetersEntity getElEntity() {
		return elEntity;
	}

	public void setElEntity(ElectricityMetersEntity elEntity) {
		this.elEntity = elEntity;
	}

	public Property getPropobj() {
		return propobj;
	}

	public void setPropobj(Property propobj) {
		this.propobj = propobj;
	}

	public Blocks getBlockObj() {
		return blockObj;
	}

	public void setBlockObj(Blocks blockObj) {
		this.blockObj = blockObj;
	}
	
}
