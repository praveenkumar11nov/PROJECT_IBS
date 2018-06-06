package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name="TrendLogValue.getMaxTimeStampBasedOnCurrentData",query="SELECT MAX(tlv.logTime) FROM TrendLogValue tlv  WHERE EXTRACT(month FROM tlv.logTime) = :montOne AND EXTRACT(year FROM tlv.logTime) = :year AND EXTRACT(day FROM tlv.logTime) = :day AND tlv.trendLogId = :trendLogId GROUP BY datediff(day,0, tlv.logTime)"),
})
@Entity
@Table(name="TrendLogValue",schema="dbo",catalog="taclogdata")
public class TrendLogValue {
 @Id
 @Column(name="TrendLogId")
 private Integer trendLogId;
 
 @Column(name="LogTime")
 private Timestamp logTime;
 
 @Column(name="ValueType")
 private Integer valueType;
 
 @Column(name="LogValue")
 private Double logValue;
 
 @Column(name="Sequence")
 private Integer sequence;

public Integer getTrendLogId() {
	return trendLogId;
}

public void setTrendLogId(Integer trendLogId) {
	this.trendLogId = trendLogId;
}

public Timestamp getLogTime() {
	return logTime;
}

public void setLogTime(Timestamp logTime) {
	this.logTime = logTime;
}

public Integer getValueType() {
	return valueType;
}

public void setValueType(Integer valueType) {
	this.valueType = valueType;
}

public Double getLogValue() {
	return logValue;
}

public void setLogValue(Double logValue) {
	this.logValue = logValue;
}

public Integer getSequence() {
	return sequence;
}

public void setSequence(Integer sequence) {
	this.sequence = sequence;
}
 
}
