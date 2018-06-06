package com.bcits.bfm.model;

import java.sql.Blob;

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
@NamedQueries
({
	@NamedQuery(name="PhotoGallery.getImage", query="select photo.image from PhotoGallery photo where photo.pgId =:pgId"),
	@NamedQuery(name="PhotoGallery.getAllPhotoByEventId" , query="select photo from PhotoGallery photo where photo.peId=:peId order by photo.pgId DESC"),
	@NamedQuery(name="PhotoGallery.updateImage",query="UPDATE PhotoGallery p SET p.image=:image WHERE p.pgId=:pgId")
})

@Entity
@Table(name="PHOTO_GALLERY")
public class PhotoGallery 
{
	@Id
	@SequenceGenerator(name = "pgSeq", sequenceName = "PHOTO_GALLERY_SEQ")
	@GeneratedValue(generator = "pgSeq")
	@Column(name="PG_ID")
	private int pgId;
	
	@Column(name="IMAGE")
	private Blob image;	
	
	@Column(name="PE_ID")
	private int peId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName="PE_ID",name = "PE_ID",insertable = false, updatable = false, nullable = false)
	private PhotoEvent photoEvent;
	
	
	public int getPgId() {
		return pgId;
	}

	public void setPgId(int pgId) {
		this.pgId = pgId;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public int getPeId() {
		return peId;
	}

	public void setPeId(int peId) {
		this.peId = peId;
	}

	
	
	
}
