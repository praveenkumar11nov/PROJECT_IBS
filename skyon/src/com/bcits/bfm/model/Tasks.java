package com.bcits.bfm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.bcits.bfm.util.BfmLogger;

@Entity
@Table(name = "TASKS")
@NamedQueries({
		@NamedQuery(name = "Tasks.checkProductExistence", query = "SELECT t.tk_name FROM Tasks t WHERE t.tk_name=:name"),
		@NamedQuery(name = "Tasks.findAll", query = "SELECT t FROM Tasks t"),
		@NamedQuery(name = "Users.getRoleNames", query = "select r from Permission p, Role r where p.ptk_id = :taskId and r.rlId = p.prl_id "),
		@NamedQuery(name = "Tasks.count", query = "select count(t.tk_id) from Tasks t"),
		@NamedQuery(name = "Tasks.findId", query = "select max(t.tk_id) from Tasks t"),
		@NamedQuery(name = "Tasks.getTaskRolesBasedOntaskId", query = "select p FROM Permission p WHERE p.ptk_id = :taskId") })
public class Tasks implements Serializable {
	@Id
	@SequenceGenerator(name = "TASK_SEQ", sequenceName = "TASK_SEQ")
	@GeneratedValue(generator = "TASK_SEQ")
	@Column(name = "TK_ID")
	private int tk_id;

	@Column(name = "PR_ID")
	private int pr_id;

	@Column(name = "MD_ID")
	private int md_id;

	@Column(name = "FM_ID")
	private int fm_id;

	@NotNull(message = "Product Name Cannot Be a Null ")
	@Column(name = "TK_NAME", nullable = false, unique = false, length = 45)
	private String tk_name;

	@NotNull(message = "Product Name Cannot Be a Null ")
	@Column(name = "TK_DESCRIPTION", nullable = true, unique = false, length = 45)
	private String tk_description;

	/*
	 * @Transient private String prl_id;
	 */

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "PTK_ID")
	private Set<Permission> permission;

	@OneToOne
	@JoinColumn(name = "PR_ID", insertable = false, updatable = false, nullable = false)
	private Product product;

	@OneToOne
	@JoinColumn(name = "MD_ID", insertable = false, updatable = false, nullable = false)
	private Module module;

	@OneToOne
	@JoinColumn(name = "FM_ID", insertable = false, updatable = false, nullable = false)
	private Form form;

	/*
	 * @OneToOne
	 * 
	 * @JoinColumn(name = "RL_ID",insertable=false , updatable=false ,
	 * nullable=false) private Role role;
	 * 
	 * 
	 * @OneToOne
	 * 
	 * @JoinColumn(name = "RL_ID",insertable=false , updatable=false ,
	 * nullable=false) public Role getRole() { return role; }
	 * 
	 * public void setRole(Role role) { this.role = role; }
	 */

	/*
	 * @ManyToMany(fetch = FetchType.EAGER)
	 * 
	 * @JoinTable(name = "PERMISSION", joinColumns = { @JoinColumn(name =
	 * "TK_ID" , referencedColumnName = "TK_ID") }, inverseJoinColumns = {
	 * @JoinColumn(name = "PRL_ID" ,referencedColumnName = "RL_ID")}) private
	 * Set<Role> role;
	 */

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "PERMISSION", joinColumns = { @JoinColumn(name = "PTK_ID", referencedColumnName = "TK_ID") }, inverseJoinColumns = { @JoinColumn(name = "PRL_ID", referencedColumnName = "RL_ID") })
	private Set<Role> role;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PERMISSION", joinColumns = { @JoinColumn(name = "PTK_ID", referencedColumnName = "TK_ID") }, inverseJoinColumns = { @JoinColumn(name = "PRL_ID", referencedColumnName = "RL_ID") })
	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

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

	@OneToOne
	@JoinColumn(name = "FM_ID", insertable = false, updatable = false, nullable = false)
	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public Tasks() {
		super();
	}

	public Tasks(int tk_id, int pr_id, int md_id, int fm_id, String tk_name,
			String tk_description) {
		super();
		this.tk_id = tk_id;
		this.pr_id = pr_id;
		this.md_id = md_id;
		this.fm_id = fm_id;
		this.tk_name = tk_name;
		this.tk_description = tk_description;
	}

	public int getTk_id() {
		return tk_id;
	}

	public Tasks(int tk_id, int pr_id, int md_id, int fm_id, String tk_name,
			String tk_description, Set<Permission> permission) {
		super();
		this.tk_id = tk_id;
		this.pr_id = pr_id;
		this.md_id = md_id;
		this.fm_id = fm_id;
		this.tk_name = tk_name;
		this.tk_description = tk_description;
		this.permission = permission;
	}

	public void setTk_id(int tk_id) {
		this.tk_id = tk_id;
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

	public int getFm_id() {
		return fm_id;
	}

	public void setFm_id(int fm_id) {
		this.fm_id = fm_id;
	}

	public String getTk_name() {
		return tk_name;
	}

	public void setTk_name(String tk_name) {
		this.tk_name = tk_name;
	}

	public String getTk_description() {
		return tk_description;
	}

	public void setTk_description(String tk_description) {
		this.tk_description = tk_description;
	}

	public Set<Permission> getPermission() {
		BfmLogger.logger.info("get permission");
		return permission;
	}

	public void setPermission(Set<Permission> permission) {
		this.permission = permission;
	}

}
