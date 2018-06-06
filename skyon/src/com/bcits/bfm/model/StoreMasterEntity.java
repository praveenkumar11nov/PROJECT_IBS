package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "STORE_MASTERS")
@NamedQueries({
	@NamedQuery(name = "StoreMasterEntity.findAllData", query = "SELECT s FROM StoreMasterEntity s"),
	@NamedQuery(name = "Storemaster.UpdateStatus", query = "UPDATE StoreMasterEntity p SET  p.storeStatus = :StStatus WHERE p.sId = :stId"),
	//@NamedQuery(name = "StoreMasterEntity.findAll", query = "SELECT s.sId,s.storeName FROM StoreMasterEntity s"),
	@NamedQuery(name = "StoreMasterEntity.findAllStoreNames", query = "SELECT s FROM StoreMasterEntity s where s.storeStatus='Active' "),
	@NamedQuery(name = "Store.getLoginDetails", query = "SELECT s FROM StoreMasterEntity s where s.username=:user And s.password=:pass AND s.storeStatus='Active'"),
	@NamedQuery(name = "StoreMasterEntity.findAllStoreMobile", query = "SELECT s FROM StoreMasterEntity s where s.sId=:sId "),
	
})
public class StoreMasterEntity {
	
	@Id
	@SequenceGenerator(name = "stores_masters_seq", sequenceName = "STORES_MASTERS_SEQ")
	@GeneratedValue(generator = "stores_masters_seq")
	@Column(name = "ST_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private int sId;
	
	@Column(name = "ST_NAME")
	private String storeName;
	
	@Column(name = "STATUS")
	private String storeStatus;
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "STORE_NUM")
	private String storeNum;
	
	@Column(name = "USER_NAME")
	private String username;
	
	@Column(name = "USER_PASSS")
	private String password;
	
	public String getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(String storeNum) {
		this.storeNum = storeNum;
	}

	public String getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(String storeStatus) {
		this.storeStatus = storeStatus;
	}

	public int getsId() {
		return sId;
	}

	public void setsId(int sId) {
		this.sId = sId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreDesc() {
		return storeDesc;
	}

	public void setStoreDesc(String storeDesc) {
		this.storeDesc = storeDesc;
	}

	@Column(name = "ST_DESC")
	private String storeDesc;

}
