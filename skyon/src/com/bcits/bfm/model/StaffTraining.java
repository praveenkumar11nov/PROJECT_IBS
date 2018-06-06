package com.bcits.bfm.model;



import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bcits.bfm.util.SessionData;


@Entity
@Table(name = "STAFF_TRAININGS")
@NamedQueries({
	@NamedQuery(name = "StaffTraining.findAll", query = "SELECT r FROM StaffTraining r ORDER BY r.stId"),
	@NamedQuery(name = "StaffTraining.findById", query = "SELECT r FROM StaffTraining r  WHERE r.personId = :personId ORDER BY r.ptSlno DESC"),
	@NamedQuery(name = "StaffTraining.uploadCertificateOnId" , query = "UPDATE StaffTraining s SET s.trainingDocument= :trainingDocument, s.trainingDocumentType=:trainingDocumentType WHERE s.stId= :stId" )

})
public class StaffTraining {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="stafftarining_seq_gen")
	@SequenceGenerator(name="stafftarining_seq_gen", sequenceName="STAFF_TRAIN_SEQ")
	@Column(name = "ST_ID")
	private int stId;	
	
	@Min(value = 1, message = "'Person' is not selected")
	@Column(name = "PERSON_ID", precision = 10, scale = 0)
	private int personId;
	
	@OneToOne
	@JoinColumn(name = "PERSON_ID", insertable = false, updatable = false, nullable = false)
	private Person person;
	

	
	
	@Column(name = "PT_SL_NO")
	private int ptSlno;
	
	@Size(min = 1, max = 100, message = "Training Name can have maximum {max} letters")
	@Column(name = "TRAINING_NAME")
	private String trainingName;
	
	@Size(min = 0, max = 100, message = "Recommanded By can have maximum {max} letters")
	@Column(name = "TRAINED_BY")
	private String trainedBy;
	
	@Column(name = "FROM_DATE")
	private Date fromDate;
	
	@NotNull(message="Invalid Date Range")
	@Column(name = "TO_DATE")
	private Date toDate;
	
	@Size(min = 0, max = 300, message = "Description can have maximum {max} letters")
	@Column(name = "TRAINING_DESCRIPTION")
	private String trainingDesc;
	
	@Column(name = "CERTIFICATION_ACHIEVED")
	private String certificationAch;	
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATED_DT")
	private Date lastUpdatedDate;	
	
	
	@Column(name = "TRAINING_DOC")
	private Blob trainingDocument;
	
	@Column(name = "TRAINING_DOC_TYPE")
	private String trainingDocumentType; 
	/*@Column(name="CREATED_AT")
	private Date createdAt = Calendar.getInstance().getTime();*/

	public Blob getTrainingDocument() {
		return trainingDocument;
	}

	public void setTrainingDocument(Blob trainingDocument) {
		this.trainingDocument = trainingDocument;
	}

	public int getStId() {
		return stId;
	}

	public void setStId(int stId) {
		this.stId = stId;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	

	public int getPtSlno() {
		return ptSlno;
	}

	public void setPtSlno(int ptSlno) {
		this.ptSlno = ptSlno;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public String getTrainedBy() {
		return trainedBy;
	}

	public void setTrainedBy(String trainedBy) {
		this.trainedBy = trainedBy;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getTrainingDesc() {
		return trainingDesc;
	}

	public void setTrainingDesc(String trainingDesc) {
		this.trainingDesc = trainingDesc;
	}

	public String getCertificationAch() {
		return certificationAch;
	}

	public void setCertificationAch(String certificationAch) {
		this.certificationAch = certificationAch;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastupdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	

	

	public String getTrainingDocumentType() {
		return trainingDocumentType;
	}

	public void setTrainingDocumentType(String trainingDocumentType) {
		this.trainingDocumentType = trainingDocumentType;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}


	@PrePersist
	 protected void onCreate() {
	    lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	    createdBy = (String) SessionData.getUserDetails().get("userID");
	 }

	 @PreUpdate
	 protected void onUpdate() {
		 lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	 }
			

}
