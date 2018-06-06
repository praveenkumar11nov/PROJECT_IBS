package com.bcits.bfm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
/*@NamedQueries({
	@NamedQuery(name = "AssetCategory.find", query = "SELECT a FROM AssetCategory a WHERE a.assetcatId = :assetcatId"),
	@NamedQuery(name = "AssetCategory.findId", query = "SELECT a FROM AssetCategory a WHERE a.parentId = :parentId AND a.assetcatText =:assetcatText")
})*/
public class AssetCategory {    
   
    
    private int assetcatId;
    private String assetcatText;
    private Integer parentId;

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

    
}