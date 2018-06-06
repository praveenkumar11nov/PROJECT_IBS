package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MODULES")
@NamedQueries({
		@NamedQuery(name = "Module.checkProductExistence", query = "SELECT m.md_name FROM Module m WHERE m.md_name=:name"),
		@NamedQuery(name = "Module.findAll", query = "SELECT m FROM Module m"),
		@NamedQuery(name = "Module.count", query = "select count(m.md_id) from Module m") })
public class Module {
	@Id
	@SequenceGenerator(name = "MODULE_SEQ", sequenceName = "MODULE_SEQ")
	@GeneratedValue(generator = "MODULE_SEQ")
	@Column(name = "MD_ID", nullable = false, unique = true, precision = 11, scale = 0)
	private int md_id;

	@NotNull(message = "Product Name Cannot Be a Null ")
	@Column(name = "PR_ID", nullable = false, unique = false, precision = 11, scale = 0)
	private int pr_id;

	@NotNull(message = "Module Name Cannot Be a Null ")
	@Column(name = "MD_NAME", nullable = false, unique = false, length = 45)
	private String md_name;

	@Column(name = "MD_DESCRIPTION", nullable = true, unique = false, length = 45)
	private String md_description;

	@OneToOne
	@JoinColumn(name = "PR_ID", insertable = false, updatable = false, nullable = false)
	private Product product;

	public Module() {
		super();
	}

	public Module(int md_id, int pr_id, String md_name, String md_description) {
		super();
		this.md_id = md_id;
		this.pr_id = pr_id;
		this.md_name = md_name;
		this.md_description = md_description;
	}

	@OneToOne
	@JoinColumn(name = "PR_ID", insertable = false, updatable = false, nullable = false)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@javax.persistence.Column(name = "MD_ID")
	public int getMd_id() {
		return md_id;
	}

	public void setMd_id(int md_id) {
		this.md_id = md_id;
	}

	@javax.persistence.Column(name = "PR_ID")
	public int getPr_id() {
		return pr_id;
	}

	public void setPr_id(int pr_id) {
		this.pr_id = pr_id;
	}

	@javax.persistence.Column(name = "MD_NAME")
	public String getMd_name() {
		return md_name;
	}

	public void setMd_name(String md_name) {
		this.md_name = md_name;
	}

	@javax.persistence.Column(name = "MD_DESCRIPTION")
	public String getMd_description() {
		return md_description;
	}

	public void setMd_description(String md_description) {
		this.md_description = md_description;
	}

}
