package com.bcits.bfm.calender;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@Entity
@Table(name="JOB_CALENDER_TASK")
public class Task {
    private int taskId;
    private Integer ownerId;
    private String title;
    private String description;
    private String recurrenceRule;
    private String recurrenceException;
    private Integer recurrenceId;
    private boolean isAllDay;
    private Date start;
    private Date end;
    
    @Id
 	@SequenceGenerator(name="SEQ_ASSET_LOC" ,sequenceName="ASSET_LOC_SEQ")
 	@GeneratedValue(generator="SEQ_ASSET_LOC")  
    @Column(name="TASK_ID")   
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId ) {
        this.taskId = taskId ;
    }

    @Column(name="PERSON")
    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
    
    @Column(name="START_DATE")
    @JsonSerialize(using=IsoDateJsonSerializer.class)
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }
    
    @Column(name="END_DATE")
    @JsonSerialize(using=IsoDateJsonSerializer.class)
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
    
    @Column(name="TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name="DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="RECURRENCE_RULE")
    public String getRecurrenceRule() {
        return recurrenceRule;
    }

    public void setRecurrenceRule(String recurrenceRule) {
        this.recurrenceRule = recurrenceRule;
    }

    @Column(name="IS_ALL_DAY")
    public boolean getIsAllDay() {
        return isAllDay;
    }

    public void setIsAllDay(boolean isAllDay) {
        this.isAllDay = isAllDay;
    }

    @Column(name="RECURENCE_EXCEPTION")
    public String getRecurrenceException() {
        return recurrenceException;
    }

    public void setRecurrenceException(String recurrenceException) {
        this.recurrenceException = recurrenceException;
    }

    @Column(name="RECURRENCE_ID")
    public Integer getRecurrenceId() {
        return recurrenceId;
    }

    public void setRecurrenceId(Integer recurrenceId) {
        this.recurrenceId = recurrenceId;
    }

}
