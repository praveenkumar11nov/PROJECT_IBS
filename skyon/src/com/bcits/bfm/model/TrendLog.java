package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name="TrendLog.findAll",query="Select t from TrendLog t"),
	
})
@Entity
@Table(name="TrendLog",schema="dbo",catalog="taclogdata")
public class TrendLog {

	@Id
	@Column(name = "TrendLogId")
	private Integer trendLogId;
	
	@Column(name = "TrendLogGuid")
	private String trendLogGuid;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "MaxSequence")
	private Integer maxSequence;
	
	@Column(name = "Capacity")
	private Long capacity;
	
	@Column(name = "IntervalSeconds")
	private Long intervalSeconds;
	
	@Column(name = "IntervalMonths")
	private Long intervalMonths;
	
	@Column(name = "IntervalYears")
	private Long intervalYears;
	
	public Integer getTrendLogId() {
		return trendLogId;
	}
	public void setTrendLogId(Integer trendLogId) {
		this.trendLogId = trendLogId;
	}
	public String getTrendLogGuid() {
		return trendLogGuid;
	}
	public void setTrendLogGuid(String trendLogGuid) {
		this.trendLogGuid = trendLogGuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMaxSequence() {
		return maxSequence;
	}
	public void setMaxSequence(Integer maxSequence) {
		this.maxSequence = maxSequence;
	}
	public Long getCapacity() {
		return capacity;
	}
	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}
	public Long getIntervalSeconds() {
		return intervalSeconds;
	}
	public void setIntervalSeconds(Long intervalSeconds) {
		this.intervalSeconds = intervalSeconds;
	}
	public Long getIntervalMonths() {
		return intervalMonths;
	}
	public void setIntervalMonths(Long intervalMonths) {
		this.intervalMonths = intervalMonths;
	}
	public Long getIntervalYears() {
		return intervalYears;
	}
	public void setIntervalYears(Long intervalYears) {
		this.intervalYears = intervalYears;
	}
	
	
}
