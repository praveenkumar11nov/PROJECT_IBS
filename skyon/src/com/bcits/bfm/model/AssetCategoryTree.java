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
@Table(name="ASSET_CAT_NEW")
@NamedQueries({
	@NamedQuery(name = "AssetCategoryTree.getAssetListById", query = "SELECT a FROM AssetCategoryTree a WHERE a.parentId = :parentId ORDER BY a.assetcatText"),
	@NamedQuery(name = "AssetCategoryTree.findAllOnAssetCatId", query = "SELECT a FROM AssetCategoryTree a WHERE a.assetcatId = :assetcatId"),
	@NamedQuery(name = "AssetCategoryTree.findIdByParent", query = "SELECT a FROM AssetCategoryTree a WHERE a.parentId = :parentId AND a.assetcatText =:assetcatText")

	})
public class AssetCategoryTree {    
    @ManyToOne()
    @JoinColumn(name="PARENT_ID", insertable=false, updatable=false)
    @JsonIgnore
    public AssetCategoryTree getParent() {
        return parent;
    }

    @JsonIgnore
    public void setParent(AssetCategoryTree parent) {
        this.parent = parent;
    }
    
    private AssetCategoryTree parent;
 
    @OneToMany(mappedBy="parent", fetch=FetchType.EAGER)
    @JsonIgnore    
    public Set<AssetCategoryTree> getChilds() {
        return childs;
    }
    
    @JsonIgnore
    public void setChilds(Set<AssetCategoryTree> childs) {
        this.childs = childs;
    }
    
    @JsonIgnore
    private Set<AssetCategoryTree> childs = new HashSet<AssetCategoryTree>();

    @Transient
    public Boolean getHasChilds() {
        return !getChilds().isEmpty();
    }  
    
    private int assetcatId;
    private String assetcatText;
    private Integer parentId;
    private String treeHierarchy;

    @Id
	@SequenceGenerator(name="SEQ_ASSET_CAT" ,sequenceName="ASSET_CAT_SEQ")
	@GeneratedValue(generator="SEQ_ASSET_CAT")
    @Column(name="ASSET_CAT_ID")
    public int getAssetcatId() {
        return assetcatId;
    }

    public void setAssetcatId(int assetcatId) {
        this.assetcatId = assetcatId;
    }
    
    @Column(name="ASSET_CAT_TEXT")
    public String getAssetcatText() {
        return assetcatText;
    }

    public void setAssetcatText(String assetcatText) {
        this.assetcatText = assetcatText;
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
    
    
    

    
}