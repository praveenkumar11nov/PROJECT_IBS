package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PRODUCT")
@NamedQueries({
		@NamedQuery(name = "Product.checkProductExistence", query = "SELECT p.name FROM Product p WHERE p.name=:name"),
		@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
		@NamedQuery(name = "Product.count", query = "select count(p.pr_id) from Product p"),
		@NamedQuery(name = "Product.getProductname", query = "SELECT p.name FROM Product p WHERE p.pr_id=:id") })
public class Product {
	@Id
	@SequenceGenerator(name = "PRODUCT_SEQ", sequenceName = "PRODUCT_SEQ")
	@GeneratedValue(generator = "PRODUCT_SEQ")
	@Column(name = "PR_ID", nullable = false, unique = true, precision = 10, scale = 0)
	private int pr_id;

	@Column(name = "DESCRIPTION", nullable = true, unique = false, length = 45)
	private String description;

	@NotNull(message = "Product Name Cannot Be a Null ")
	@Column(name = "NAME", nullable = false, unique = true, length = 45)
	private String name;

	// default constructor
	public Product() {

	}

	// parameterised constructor
	public Product(int pr_id, String description, String name) {
		super();
		this.pr_id = pr_id;
		this.description = description;
		this.name = name;
	}

	// getters and setters

	public String getDescription() {
		return description;
	}

	public int getPr_id() {
		return pr_id;
	}

	public void setPr_id(int pr_id) {
		this.pr_id = pr_id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
