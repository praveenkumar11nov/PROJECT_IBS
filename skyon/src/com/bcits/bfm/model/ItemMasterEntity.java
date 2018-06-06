package com.bcits.bfm.model;

import java.sql.Blob;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name = "ITEMS_MASTER")
@NamedQueries({
	@NamedQuery(name = "ItemMasterEntity.findAllData", query = "SELECT s FROM ItemMasterEntity s"),
	@NamedQuery(name = "ItemMasterEntity.findAllDatas", query = "SELECT s FROM ItemMasterEntity s WHERE s.sId=:sid"),
	@NamedQuery(name = "Item.uploadImageOnId", query = "UPDATE ItemMasterEntity p SET p.itemImage= :itemImage, p.lastUpdatedDt= :lastUpdatedDate WHERE p.gid= :gid" ),
	//@NamedQuery(name = "Items.getItemNameByItemId", query= "SELECT u.itemName from ItemMasterEntity u WHERE u.gid = :gid"),
	@NamedQuery(name = "ItemMaster.getImage", query= "SELECT p.itemImage FROM ItemMasterEntity p WHERE p.gid= :gid"),	
	@NamedQuery(name = "Items.uploadItemsOnId",query = "UPDATE ItemMasterEntity a SET a.itemImage= :itemImage , a.docType=:docType  WHERE a.gid= :gid" ),
	@NamedQuery(name = "Itemmaster.updateVisitorImage", query = "Update ItemMasterEntity v SET v.itemImage = :itemImage WHERE v.gid = :vmId"),
})
public class ItemMasterEntity {
	
	
	@Id
	@SequenceGenerator(name = "items_mast_entity_seq", sequenceName = "ITEMS_MAST_ENTITY_SEQ")
	@GeneratedValue(generator = "items_mast_entity_seq")
	@Column(name = "GID", unique = true, nullable = false, precision = 10, scale = 0)
	private int gid;
	
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	@Column(name = "PRICE")
	private Float price;
	
	@Column(name = "UNIT_OF_MEASURE")
	private String unitOfMeasure;
	
	@Column(name = "DISCOUNT")
	private String discount;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Lob
	@Column(name = "ITEM_IMAGE")
	private Blob itemImage;
	
	@Column(name = "STORE_NAME")
	private String storeName;
	
	@Column(name = "ITEM_REVIEW")
	private String itemReview;
	
	
	
	

	@Column(name = "DOCTYPE")
	private String docType;
	
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;

	
	public Timestamp getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ST_ID", insertable = false, updatable = false)
   private StoreMasterEntity storeMasterEntity;

	@Column(name = "ST_ID")
	private int sId;

	public int getGid() {
		return gid;
	}

	public Blob getItemImage() {
		return itemImage;
	}

	public void setItemImage(Blob itemImage) {
		this.itemImage = itemImage;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	



	

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getDiscount() {
		return discount;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}                                                                 

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getCategory() {
		return category;
	}

	
	public void setCategory(String category) {
		this.category = category;
	}


	public String getItemReview() {
		return itemReview;
	}

	public void setItemReview(String itemReview) {
		this.itemReview = itemReview;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public StoreMasterEntity getStoreMasterEntity() {
		return storeMasterEntity;
	}

	public void setStoreMasterEntity(StoreMasterEntity storeMasterEntity) {
		this.storeMasterEntity = storeMasterEntity;
	}

	public int getsId() {
		return sId;
	}

	public void setsId(int sId) {
		this.sId = sId;
	}
	}
