package com.bcits.bfm.serviceImpl;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.PhotoGallery;
import com.bcits.bfm.service.PhotoGalleryService;

@Repository
public class PhotoGalleryServiceImpl extends GenericServiceImpl<PhotoGallery> implements PhotoGalleryService {

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Blob getImage(int pgId) {
		
		return  (Blob) entityManager.createNamedQuery("PhotoGallery.getImage")
				.setParameter("pgId", pgId)
				.getSingleResult();
	}

	@Override
	public List<PhotoGallery> getAllPhotoByeventId(int peId) {
		return entityManager.createNamedQuery("PhotoGallery.getAllPhotoByEventId").setParameter("peId", peId).getResultList();
	}

	@Override
	public void updateImage(int pgId, Blob blob) {
		entityManager.createNamedQuery("PhotoGallery.updateImage").setParameter("pgId", pgId).setParameter("image", blob).executeUpdate();
		
	}

	
}
