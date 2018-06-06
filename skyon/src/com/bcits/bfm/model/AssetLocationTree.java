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
@Table(name="ASSET_LOC_NEW")
@NamedQueries({
	@NamedQuery(name = "AssetLocationTree.findAllOnParentId", query = "SELECT a FROM AssetLocationTree a WHERE a.parentId = :parentId ORDER BY a.assetlocText"),
	@NamedQuery(name = "AssetLocationTree.findAllOnAssetLocId", query = "SELECT a FROM AssetLocationTree a WHERE a.assetlocId = :assetlocId ORDER BY a.assetlocText"),
	@NamedQuery(name = "AssetLocationTree.findIdByParent", query = "SELECT a FROM AssetLocationTree a WHERE a.parentId = :parentId AND a.assetlocText =:assetlocText"),
	@NamedQuery(name = "AssetLocationTree.findAllLocationType", query = "SELECT DISTINCT(a.locationType) FROM AssetLocationTree a")
	
	})
public class AssetLocationTree {    
    @ManyToOne()
    @JoinColumn(name="PARENT_ID", insertable=false, updatable=false)
    @JsonIgnore
    public AssetLocationTree getParent() {
        return parent;
    }

    @JsonIgnore
    public void setParent(AssetLocationTree parent) {
        this.parent = parent;
    }
    
    private AssetLocationTree parent;
 
    @OneToMany(mappedBy="parent", fetch=FetchType.EAGER)
    @JsonIgnore    
    public Set<AssetLocationTree> getChilds() {
        return childs;
    }
    
    @JsonIgnore
    public void setChilds(Set<AssetLocationTree> childs) {
        this.childs = childs;
    }
    
    @JsonIgnore
    private Set<AssetLocationTree> childs = new HashSet<AssetLocationTree>();

    @Transient
    public Boolean getHasChilds() {
        return !getChilds().isEmpty();
    }  
    
    private int assetlocId;
    private String assetlocText;
    private Integer parentId;
    private String treeHierarchy;
    private String locationType;
    private String locationAddress;
    private String contactDetails;
    private String geoCode;
    
    @Id
	@SequenceGenerator(name="SEQ_ASSET_LOC" ,sequenceName="ASSET_LOC_SEQ")
	@GeneratedValue(generator="SEQ_ASSET_LOC")
    @Column(name="ASSET_LOC_ID")
    public int getAssetlocId() {
        return assetlocId;
    }

    public void setAssetlocId(int assetlocId) {
        this.assetlocId = assetlocId;
    }
    
    @Column(name="ASSET_LOC_TEXT")
    public String getAssetlocText() {
        return assetlocText;
    }

    public void setAssetlocText(String assetlocText) {
        this.assetlocText = assetlocText;
    }
    
    @Column(name="PARENT_ID")
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Column(name="TREE_HIERARCHY")
   	public String getTreeHierarchy() {
   		return treeHierarchy;
   	}

   	public void setTreeHierarchy(String treeHierarchy) {
   		this.treeHierarchy = treeHierarchy;
   	}

   	@Column(name="LOCATION_TYPE")
	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	@Column(name="LOCATION_ADDRESS")
	public String getLocationAddress() {
		return locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	@Column(name="CONTACT_DETAILS")
	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	@Column(name="GEO_CODE")
	public String getGeoCode() {
		return geoCode;
	}

	public void setGeoCode(String geoCode) {
		this.geoCode = geoCode;
	}	
}