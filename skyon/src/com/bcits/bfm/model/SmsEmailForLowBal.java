package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="LOWBALANCE_STATUS")
@NamedQueries({
	@NamedQuery(name="SmsEmailForLowBal.lowBal1",query="SELECT s From SmsEmailForLowBal s Where s.meterNo =:meterNo and s.txn_Id =:txn_Id"),
	@NamedQuery(name="SmsEmailForLowBal.lowBal2",query="SELECT s From SmsEmailForLowBal s Where s.meterNo =:meterNo and s.txn_Id =:txn_Id and s.status!='s1' and s.status ='s2'"),
	@NamedQuery(name="SmsEmailForLowBal.lowBal3",query="SELECT s From SmsEmailForLowBal s Where s.meterNo =:meterNo and s.txn_Id =:txn_Id and s.status NOT IN('s1','s2') and s.status ='s3'"),
	@NamedQuery(name="SmsEmailForLowBal.EstateManager",query="SELECT u.personId FROM Users u INNER JOIN u.designation dn where u.dnId=dn.dn_Id and dn.dn_Name='Estate Manager' and u.status='Active'")
})
public class SmsEmailForLowBal {
	@Id
	@SequenceGenerator(name="LOWBALANCE_STATUS_SEQ", sequenceName="LOWBALANCE_STATUS_SEQ")
	@GeneratedValue(generator="LOWBALANCE_STATUS_SEQ")
	@Column(name="LID")
	private int lId;
	
	@Column(name="METER_NO")
	private String meterNo;
	
	@Column(name="RECHARGED_AMOUNT")
	private String txn_Id;
	
	@Column(name="STATUS")
	private String status;
	
	public int getlId() {
		return lId;
	}
	public void setlId(int lId) {
		this.lId = lId;
	}
	public String getMeterNo() {
		return meterNo;
	}
	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}
	
	public String getTxn_Id() {
		return txn_Id;
	}
	public void setTxn_Id(String txn_Id) {
		this.txn_Id = txn_Id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "SmsEmailForLowBal [lId=" + lId + ", meterNo=" + meterNo + ", txn_Id=" + txn_Id + ", status=" + status
				+ "]";
	}
	
}
