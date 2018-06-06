package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="UOM")
@NamedQueries
({
	@NamedQuery(name="Uom.findUomBasedOnItemId",query="SELECT u FROM UnitOfMeasurement u where u.imId = :imid ORDER BY u.baseUom DESC"),
	@NamedQuery(name="UOM.findAll",query="SELECT u FROM UnitOfMeasurement u"),
	@NamedQuery(name = "UOM.getAllUom", query = "SELECT u.uom FROM UnitOfMeasurement u WHERE u.imId = :itemId"),
	@NamedQuery(name = "UOM.getAllCode", query = "SELECT u.code FROM UnitOfMeasurement u WHERE u.imId = :itemId"),
	@NamedQuery(name = "UOM.getBaseUomBasedOnId", query = "SELECT u.baseUom FROM UnitOfMeasurement u WHERE u.uomId=:UomId"),
	@NamedQuery(name="UOM.updateUomStatus",query="UPDATE UnitOfMeasurement uom SET uom.status = :uomStatus WHERE uom.uomId = :uomid"),
	
})
public class UnitOfMeasurement 
{
    @Id 
	@SequenceGenerator(name = "uomseq", sequenceName = "UOM_SEQ")
	@GeneratedValue(generator = "uomseq")
	
    @Column(name="UOM_ID", unique=true, nullable=false, precision=11, scale=0)
	private int uomId;
    
    @Column(name="IM_ID", unique=true, nullable=false, precision=11, scale=0)
	private int imId;
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IM_ID", insertable = false, updatable = false, nullable = false)
    private ItemMaster itemMaster;
    
    @Column(name="UNITS_OF_MEASURE", nullable=false, length=200)
	private String uom;
    
    @Column(name="CODE", nullable=false, length=200)
	private String code;
    
    @Column(name="BASE_UNIT_OF_MEASURE", nullable=false, length=200)
	private String baseUom;
    
    @Column(name = "UOM_CONVERSION")
	private Double uomConversion;  
    
    @Column(name="STATUS", nullable=false, length=200)
	private String status;
    
    /*@Column(name = "IM_PURCHASE_UOM", length = 45)
	private String imPurchaseUom;
    
    @Column(name = "IM_UOM_ISSUE", length = 45)
	private String imUomIssue;*/

	public UnitOfMeasurement(){}
	
	public UnitOfMeasurement(int uomId,int imId,String uom,String code,String baseUom,Double uomConversion)
	{
		this.uomId = uomId;
		this.imId = imId;		
		this.uom = uom;
		this.code = code;
		this.baseUom = baseUom;
		this.uomConversion = uomConversion;
	}	
	public int getUomId()	{
		return uomId;
	}
	public void setUomId(int uomId) {
		this.uomId = uomId;
	}
	public int getImId() {
		return imId;
	}
	public void setImId(int imId) {
		this.imId = imId;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBaseUom() {
		return baseUom;
	}
	public void setBaseUom(String baseUom) {
		this.baseUom = baseUom;
	}	  
    public ItemMaster getItemMaster() {
		return itemMaster;
	}
	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}
	public Double getUomConversion() {
		return uomConversion;
	}
	public void setUomConversion(Double uomConversion) {
		this.uomConversion = uomConversion;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
