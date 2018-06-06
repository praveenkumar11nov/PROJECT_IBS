package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="HELPDESK_FEEDBACK")
@NamedQueries({

})
public class Feedback {
	private int feedbackId;
	private int ticketId;
	private String name;
	private String feedback;
	
	@Id
	@SequenceGenerator(name="helpdesk_feedback_sequence" ,sequenceName="HELPDESK_FEEDBACK_SEQUENCE")
	@GeneratedValue(generator="helpdesk_feedback_sequence")
	@Column(name="FID")
	
	public int getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(int feedbackId) {
		this.feedbackId =feedbackId;
	}
	

	@Column(name="CUST_NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name =name;
	}
	
	@Column(name="FEEDBACK")
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	@Column(name="TICKET_ID")
	public int	getTicketId() {
		return ticketId;
	}
     public void setTicketId(int ticketId) {
		this.ticketId =ticketId;	
	
}
	

}
