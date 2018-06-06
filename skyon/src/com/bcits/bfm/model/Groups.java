package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "GROUPS")
@NamedQueries({
		@NamedQuery(name = "Groups.findAllWithoutInvalidDefault", query = "SELECT g FROM Groups g WHERE g.gr_name != 'Invalid' ORDER BY g.gr_id DESC"),	
	
		@NamedQuery(name = "Groups.groupName", query = "SELECT g.gr_name FROM Groups g WHERE g.gr_name=:Gname"),
		@NamedQuery(name = "Groups.findAll", query = "SELECT g FROM Groups g order by g.gr_id desc"),
		@NamedQuery(name="GroupNames",query="select g.gr_name from Groups g"),
		@NamedQuery(name = "Groups.count", query = "select count(g.id) from Groups g"),
		@NamedQuery(name = "Groups.CheckGroup", query = "select g.gr_name from Groups g where g.gr_name=:groupsName"),
		@NamedQuery(name = "Groups.CheckNameExistence", query = "select g from Groups g where g.gr_name=:gName"), 
		@NamedQuery(name = "Groups.UpdateStatus",query="UPDATE Groups g SET  g.gr_status = :gr_status WHERE g.gr_id = :grId"),
		@NamedQuery(name = "Groups.getAllActiveGroups",query="SELECT g from Groups g WHERE g.gr_status = 'Active'"),
		@NamedQuery(name = "Groups.getGroupIdBasedOnGroupName",query="SELECT g.gr_id from Groups g WHERE g.gr_name = :gr_name")
		
		
		
})
public class Groups implements java.io.Serializable {

	@Id
	@SequenceGenerator(name = "seq", sequenceName = "GROUPS_SEQ")
	@GeneratedValue(generator = "seq")
	@Column(name = "GR_ID", nullable = false, unique = true, precision = 10, scale = 0)
	private int gr_id;

	@Size(min=1,max=200,message="Group Description  Cannot be empty")
	@Column(name = "GR_DESCRIPTION", nullable = false, length = 1020)
	private String gr_description;

	@Column(name = "GR_NAME")
	@Size(min = 2)
	@Pattern(regexp = "^[a-zA-Z]+[ ._a-zA-Z0-9._]*[a-zA-Z0-9]$",
    message = "Group name field can not allow special symbols except(_ .) ")
	private String gr_name;

	@Column(name = "GR_STATUS", nullable = false, length = 1020)
	private String gr_status;

	@Column(name = "CREATED_BY", nullable = false, length = 1020)
	private String created_by;

	@Column(name = "LAST_UPDATED_BY", nullable = false, length = 1020)
	private String last_Updated_by;
	

	public Groups() {
		super();
	}

	public Groups(int gr_id, String gr_description, String gr_name,
			String gr_status, String created_by, String last_Updated_by) {
		super();
		this.gr_id = gr_id;
		this.gr_description = gr_description;
		this.gr_name = gr_name;
		this.gr_status = gr_status;
		this.created_by = created_by;
		this.last_Updated_by = last_Updated_by;
	}

	public int getGr_id() {
		return gr_id;
	}

	public void setGr_id(int gr_id) {
		this.gr_id = gr_id;
	}

	public String getGr_description() {
		return gr_description;
	}

	public void setGr_description(String gr_description) {
		this.gr_description = gr_description;
	}

	public String getGr_name() {
		return gr_name;
	}

	public void setGr_name(String gr_name) {
		this.gr_name = gr_name;
	}

	public String getGr_status() {
		return gr_status;
	}

	public void setGr_status(String gr_status) {
		this.gr_status = gr_status;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getLast_Updated_by() {
		return last_Updated_by;
	}

	public void setLast_Updated_by(String last_Updated_by) {
		this.last_Updated_by = last_Updated_by;
	}

	@Override
	public int hashCode(){
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(this.created_by);
	    buffer.append(this.gr_description);
	    buffer.append(this.gr_id);
	    buffer.append(this.gr_name);
	    buffer.append(this.gr_status);
	    buffer.append(this.last_Updated_by);
	    return buffer.toString().hashCode();
	}
	
	@Override
	public boolean equals(Object object){
	    if (object == null) return false;
	    if (object == this) return true;
	    if (this.getClass() != object.getClass())return false;
	    Groups groups = (Groups)object;
	    if(this.hashCode()== groups.hashCode())return true;
	   return false;
	} 
}