package com.bcits.bfm.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "FILE_REPOSITORY_NEW")
@NamedQueries({ 
	
	@NamedQuery(name="FileRepositoryTree.findGroupName",query="select fpt.frGroupName from FileRepositoryTree fpt where fpt.frGroupName=:frGroupName"),
	@NamedQuery(name = "FileRepositoryTree.find", query = "SELECT fpt FROM FileRepositoryTree fpt WHERE fpt.parentId = :parentId ORDER BY fpt.frGroupName"),
	@NamedQuery(name = "FileRepositoryTree.findAllonfileRepositoryId", query = "SELECT fpt FROM FileRepositoryTree fpt WHERE fpt.frGroupId = :frGroupId"),
	@NamedQuery(name = "FileRepositoryTree.findIdByParent", query = "SELECT fpt FROM FileRepositoryTree fpt WHERE fpt.parentId = :parentId AND fpt.frGroupName =:frGroupName")
})
public class FileRepositoryTree implements java.io.Serializable {

	// Fields
	 @ManyToOne()
	    @JoinColumn(name="PARENT_ID", insertable=false, updatable=false)
	    @JsonIgnore
	public FileRepositoryTree getParent() {
		return parent;
	}

	 @JsonIgnore
	public void setParent(FileRepositoryTree parent) {
		this.parent = parent;
	}
	
	private FileRepositoryTree parent;
	
	@OneToMany(mappedBy="parent", fetch=FetchType.EAGER)
	@JsonIgnore
	public Set<FileRepositoryTree> getChilds() {
		return childs;
	}
	@JsonIgnore
	public void setChilds(Set<FileRepositoryTree> childs) {
		this.childs = childs;
	}
	
	@JsonIgnore
	private Set<FileRepositoryTree> childs=new HashSet<FileRepositoryTree>();
	
	@Transient
	public boolean getHasChilds(){
		return !getChilds().isEmpty();
		
	}
	
	

	private int frGroupId;
	private String frGroupName;
	private Integer parentId;
	private String frGroupDescription;
	private String treeHierarchy;

	// Constructors

	

	@Id
	@SequenceGenerator(name = "SEQ_FILE_REPOSITORY_NEW", sequenceName = "FILE_REPOSITORY_NEW_SEQ")
	@GeneratedValue(generator = "SEQ_FILE_REPOSITORY_NEW")
	@Column(name = "FR_GROUP_ID")
	public int getFrGroupId() {
		return frGroupId;
	}

	public void setFrGroupId(int frGroupId) {
		this.frGroupId = frGroupId;
	}


	@Column(name = "FR_GROUP_NAME")
	public String getFrGroupName() {
		return frGroupName;
	}

	public void setFrGroupName(String frGroupName) {
		this.frGroupName = frGroupName;
	}

	@Column(name = "PARENT_ID")
	public Integer getParentId() {
		return this.parentId;
	}

	
	

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "FR_GROUP_DESCRIPTION")
	public String getFrGroupDescription() {
		return this.frGroupDescription;
	}

	public void setFrGroupDescription(String frGroupDescription) {
		this.frGroupDescription = frGroupDescription;
	}

	@Column(name = "TREE_HIERARCHY")
	public String getTreeHierarchy() {
		return this.treeHierarchy;
	}

	public void setTreeHierarchy(String treeHierarchy) {
		this.treeHierarchy = treeHierarchy;
	}

}