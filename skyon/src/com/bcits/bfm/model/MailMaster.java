package com.bcits.bfm.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="MAIL_MASTER")
@NamedQueries({
	@NamedQuery(name="MailMaster.getAll",query="SELECT mail From MailMaster mail"),
	@NamedQuery(name="MailMaster.getServiceStatus",query="SELECT COUNT(m.mailMasterId) From MailMaster m WHERE m.mailServiceType=:value",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	@NamedQuery(name="MailMaster.getMailMasterByServiceType",query="SELECT m From MailMaster m where m.mailServiceType=:serviceType"),
	
})
@Cacheable(true)
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL,region="MailMaster")
public class MailMaster {
	
	@Id
	@SequenceGenerator(name="mail_master_seq",sequenceName="MAIL_MASTER_SEQ")
	@GeneratedValue(generator="mail_master_seq")
	@Column(name="MAIL_MASTER_ID")
	private int mailMasterId;
	
	@Column(name="MAIL_SUBJECT")
	private String mailSubject;
	
	@Column(name="MAIL_MESSAGE")
	private String mailMessage;
	
	@Column(name="MAIL_SERVICE_TYPE")
	private String mailServiceType;

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailMessage() {
		return mailMessage;
	}

	public void setMailMessage(String mailMessage) {
		this.mailMessage = mailMessage;
	}

	public String getMailServiceType() {
		return mailServiceType;
	}

	public void setMailServiceType(String mailServiceType) {
		this.mailServiceType = mailServiceType;
	}

	public int getMailMasterId() {
		return mailMasterId;
	}

	public void setMailMasterid(int mailMasterId) {
		this.mailMasterId = mailMasterId;
	}
	
	

}
