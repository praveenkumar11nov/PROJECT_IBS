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
@Table(name="ASSET_LOC_NEW")
@NamedQueries({
	@NamedQuery(name = "AssetLocation.find", query = "SELECT a FROM AssetLocation a WHERE a.assetlocId = :assetlocId ORDER BY a.assetlocText"),
	@NamedQuery(name = "AssetLocation.findId", query = "SELECT a FROM AssetLocation a WHERE a.parentId = :parentId AND a.assetlocText =:assetlocText")
})
public class AssetLocation {    
   
    
    private int assetlocId;
    private String assetlocText;
    private Integer parentId;

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

    
}