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
@Table(name = "FORMS")
@NamedQueries({
		@NamedQuery(name = "Form.checkProductExistence", query = "SELECT f.fm_name FROM Form f WHERE f.fm_name=:name"),
		@NamedQuery(name = "Form.findAll", query = "SELECT f FROM Form f"),
		@NamedQuery(name = "Form.count", query = "select count(f.fm_id) from Form f") })
public class Form {
	@Id
	@SequenceGenerator(name = "FORMS_SEQ", sequenceName = "FORMS_SEQ")
	@GeneratedValue(generator = "FORMS_SEQ")
	@Column(name = "FM_ID", nullable = false, unique = true, precision = 11, scale = 0)
	private int fm_id;

	@NotNull(message = "Product Name Cannot Be a Null ")
	@Column(name = "PR_ID", nullable = false, unique = false, precision = 11, scale = 0)
	private int pr_id;

	@NotNull(message = "Module Name Cannot Be a Null ")
	@Column(name = "MD_ID", nullable = false, unique = false, precision = 11, scale = 0)
	private int md_id;

	@NotNull(message = "Form Name Cannot Be a Null ")
	@Column(name = "FM_NAME", nullable = false, unique = false, length = 45)
	private String fm_name;

	@Column(name = "FM_DESCRIPTION", nullable = true, unique = false, length = 45)
	private String fm_description;

	@OneToOne
	@JoinColumn(name = "PR_ID", insertable = false, updatable = false, nullable = false)
	private Product product;

	@OneToOne
	@JoinColumn(name = "MD_ID", insertable = false, updatable = false, nullable = false)
	private Module module;

	@OneToOne
	@JoinColumn(name = "PR_ID", insertable = false, updatable = false, nullable = false)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@OneToOne
	@JoinColumn(name = "MD_ID", insertable = false, updatable = false, nullable = false)
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Form() {
		super();
	}

	public Form(int fm_id, int pr_id, int md_id, String fm_name,
			String fm_description) {
		super();
		this.fm_id = fm_id;
		this.pr_id = pr_id;
		this.md_id = md_id;
		this.fm_name = fm_name;
		this.fm_description = fm_description;
	}

	public int getFm_id() {
		return fm_id;
	}

	public void setFm_id(int fm_id) {
		this.fm_id = fm_id;
	}

	public int getPr_id() {
		return pr_id;
	}

	public void setPr_id(int pr_id) {
		this.pr_id = pr_id;
	}

	public int getMd_id() {
		return md_id;
	}

	public void setMd_id(int md_id) {
		this.md_id = md_id;
	}

	public String getFm_name() {
		return fm_name;
	}

	public void setFm_name(String fm_name) {
		this.fm_name = fm_name;
	}

	public String getFm_description() {
		return fm_description;
	}

	public void setFm_description(String fm_description) {
		this.fm_description = fm_description;
	}

}
