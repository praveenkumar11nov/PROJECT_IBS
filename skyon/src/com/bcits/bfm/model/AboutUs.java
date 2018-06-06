package com.bcits.bfm.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "ABOUT_US")
@NamedQueries({
	@NamedQuery(name="AboutUs.getAll",query="SELECT a.about_id,a.about_name,a.about_designation,a.about_description FROM AboutUs a"),
	@NamedQuery(name = "AboutUs.uploadImageOnId", query = "UPDATE AboutUs a SET a.image= :image WHERE a.about_id= :about_id" ),
	@NamedQuery(name = "AboutUs.getImage", query = "SELECT a.image FROM AboutUs a WHERE a.about_id= :about_id" ),
	@NamedQuery(name="AboutUs.getAboutUsById", query="SELECT a from AboutUs a WHERE a.about_id = :about_id"),
})

public class AboutUs 
{
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="about_seq", sequenceName="ABOUT_US_SEQ")
	@GeneratedValue(generator="about_seq")	
	private int about_id;
	
	@Column(name="NAME")
	private String about_name;
	
	@Column(name="DESIGNATION")
	private String about_designation;
	
	@Column(name="DESCRIPTION")
	private String about_description;
	
	
	@Lob
	@Column(name = "IMAGE")
	private Blob image;

	public int getAbout_id() {
		return about_id;
	}

	public void setAbout_id(int about_id) {
		this.about_id = about_id;
	}

	public String getAbout_name() {
		return about_name;
	}

	public void setAbout_name(String about_name) {
		this.about_name = about_name;
	}

	public String getAbout_designation() {
		return about_designation;
	}

	public void setAbout_designation(String about_designation) {
		this.about_designation = about_designation;
	}

	public String getAbout_description() {
		return about_description;
	}

	public void setAbout_description(String about_description) {
		this.about_description = about_description;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	
	
}
