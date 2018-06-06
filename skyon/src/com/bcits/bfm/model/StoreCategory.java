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

@Entity
@NamedQueries({
	@NamedQuery(name = "StoreCategory.findAllForTreeByStoreCategoryId", query = "SELECT s FROM StoreCategory s WHERE s.parentId = :parentId ORDER BY s.storeCategoryName"),
	@NamedQuery(name = "StoreCategory.deleteTreeDate", query = "DELETE StoreCategory s WHERE s.parentId LIKE '%-%'")
})
@Table(name = "STORE_CATEGORY")
public class StoreCategory
{
	private StoreCategory parent;
	@JsonIgnore
    private Set<StoreCategory> childs = new HashSet<StoreCategory>();
    private String storeCategoryId;
    private String storeCategoryName;
    private String parentId;
	
	@ManyToOne()
    @JoinColumn(name="PARENT_ID", insertable=false, updatable=false)
    @JsonIgnore
    public StoreCategory getParent()
	{
		return parent;
	}

    @JsonIgnore
    public void setParent(StoreCategory parent)
	{
		this.parent = parent;
	}
    
    @OneToMany(mappedBy="parent", fetch=FetchType.EAGER)
    @JsonIgnore    
    public Set<StoreCategory> getChilds()
	{
		return childs;
	}
    
    @JsonIgnore
    public void setChilds(Set<StoreCategory> childs)
	{
		this.childs = childs;
	}
    
    @Transient
    public Boolean getHasChilds() {
        return !getChilds().isEmpty();
    }  
    
    @Id
/*	@SequenceGenerator(name="STORE_CATEGORY_SEQ" ,sequenceName="STORE_CATEGORY_SEQ")
	@GeneratedValue(generator="STORE_CATEGORY_SEQ")*/
    @Column(name="STORE_CATEGORY_ID")
    public String getStoreCategoryId()
	{
		return storeCategoryId;
	}

    public void setStoreCategoryId(String storeCategoryId)
	{
		this.storeCategoryId = storeCategoryId;
	}
    
    @Column(name="STORE_CATEGORY_NAME")
    public String getStoreCategoryName()
	{
		return storeCategoryName;
	}

    public void setStoreCategoryName(String storeCategoryName)
	{
		this.storeCategoryName = storeCategoryName;
	}
    
    @Column(name="PARENT_ID")
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
}
