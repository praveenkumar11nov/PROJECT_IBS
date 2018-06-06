package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.AboutUs;
import com.bcits.bfm.model.Lostandfound;
import com.bcits.bfm.service.helpDeskManagement.AboutUsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository

public class AboutUsServiceImpl extends GenericServiceImpl<AboutUs> implements AboutUsService {


	@Override
	public List<com.bcits.bfm.model.AboutUs> getAll() {
		try {
			/*final String queryString = "select about from AboutUs about order by model.id desc";*/
			Query query = entityManager.createNamedQuery("AboutUs.getAll");
			return query.getResultList();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void uploadImageOnId(int personId, Blob files) {
		// TODO Auto-generated method stub.
		entityManager.createNamedQuery("AboutUs.uploadImageOnId")
		.setParameter("about_id", personId)
		.setParameter("image", files)		
		.executeUpdate();
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Blob getImage(int personId) {
		// TODO Auto-generated method stub
		System.out.println("person id ============="+personId);
		return (Blob) entityManager.createNamedQuery("AboutUs.getImage", Blob.class)
				.setParameter("about_id", personId)
				.getSingleResult();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findbyAboutId(int personId) {
		return entityManager.createNamedQuery("AboutUs.getAboutUsById")
				.setParameter("about_id", personId).getResultList();
	}

}
