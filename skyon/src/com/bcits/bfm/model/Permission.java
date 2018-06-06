package com.bcits.bfm.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PERMISSION")
@NamedQueries({
		@NamedQuery(name = "Permission.findAll", query = "SELECT p FROM Permission p"),
		@NamedQuery(name = "Permission.findRoleId", query = "select p from Permission p WHERE p.prl_id=:roleId "),
		@NamedQuery(name = "Permission.findCount", query = "select count(p.prl_id) from Permission p WHERE P.ptk_id=:tkId"),
		@NamedQuery(name = "Permission.findAllById", query = "SELECT p FROM Permission p WHERE P.ptk_id=:tkId") })
public class Permission {
	@Id
	@SequenceGenerator(name = "PERMISSION_SEQ", sequenceName = "PERMISSION_SEQ")
	@GeneratedValue(generator = "PERMISSION_SEQ")
	private int prmid;
	private int prl_id;
	private int ptk_id;

	@OneToOne
	@JoinColumn(name = "PRL_ID", insertable = false, updatable = false, nullable = false)
	private Role roles;

	/*
	 * public Permission(int prmid, int rl_id,int tk_id) { super(); this.prmid =
	 * prmid; this.rl_id = rl_id; this.tk_id = tk_id; }
	 */
	public int getPrmid() {
		return prmid;
	}

	/*
	 * public void setPrmid(int prmid) { this.prmid = prmid; }
	 */

	public int getPrl_id() {
		return prl_id;
	}

	public Role getRoles() {
		return roles;
	}

	public void setRoles(Role roles) {
		this.roles = roles;
	}

	public void setPrl_id(int prl_id) {
		this.prl_id = prl_id;
	}

	public int getPtk_id() {
		return ptk_id;
	}

	/*
	 * public void setPtk_id(int ptk_id) { this.ptk_id = ptk_id; }
	 */

	public Permission(int prmid, int prl_id, int ptk_id, Role roles) {
		super();
		this.prmid = prmid;
		this.prl_id = prl_id;
		this.ptk_id = ptk_id;
		this.roles = roles;
	}

	public Permission() {
		super();
	}

}
