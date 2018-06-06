package com.bcits.bfm.service;

import java.sql.Blob;
import java.util.List;

import com.bcits.bfm.model.PhotoGallery;

public interface PhotoGalleryService extends GenericService<PhotoGallery>{
	
	public Blob getImage(int pgId);
	public List<PhotoGallery> getAllPhotoByeventId(int peId);
	public void updateImage(int pgId, Blob blob);

}
