package com.bcits.bfm.model;

import java.io.Serializable;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "FEEDBACK_CHILD")
@NamedQueries({
	@NamedQuery(name = "FeedbackChild.getChildById", query = "select f from FeedbackChild f where f.feedbackChild_id=:feedbackChild_id"),
	@NamedQuery(name = "FeedbackChild.getChildByMasterId", query = "select f from FeedbackChild f where f.fMaster_d=:fMaster_d")
})
public class FeedbackChild implements Serializable
{
	@Id
	@SequenceGenerator(name = "fcseq", sequenceName = "FCHILD_SEQ")
	@GeneratedValue(generator = "fcseq")
	@Column(name="FEEDBACK_CHILD_ID" )
	private int feedbackChild_id;
	
	@Column(name="FEEDBACK_VALUE")
	private String value;
	
	@Column(name="FEEDBACK_RATE")
	private int rate;
	
	@Column(name="FEEDBACK_COMMENT")
	private String comment;
	
	@Column(name="FMASTER_ID", unique=true, nullable=false, precision=11, scale=0)
	private int fMaster_d;
    
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="FMASTER_ID", insertable = false, updatable = false, nullable = false)
	private FeedbackMaster feedbackMaster;



	public int getFeedbackChild_id() {
		return feedbackChild_id;
	}



	public void setFeedbackChild_id(int feedbackChild_id) {
		this.feedbackChild_id = feedbackChild_id;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public int getRate() {
		return rate;
	}



	public void setRate(int rate) {
		this.rate = rate;
	}



	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
	}



	public int getfMaster_d() {
		return fMaster_d;
	}



	public void setfMaster_d(int fMaster_d) {
		this.fMaster_d = fMaster_d;
	}



	public FeedbackMaster getFeedbackMaster() {
		return feedbackMaster;
	}



	public void setFeedbackMaster(FeedbackMaster feedbackMaster) {
		this.feedbackMaster = feedbackMaster;
	}

	

}

