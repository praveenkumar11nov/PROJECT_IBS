package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Lostandfound;
import com.bcits.bfm.service.helpDeskManagement.LostandfoundService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

/**
 * A data access object (DAO) providing persistence and search support for
 * Lostandfound entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see .Lostandfound
 * @author MyEclipse Persistence Tools
 */
@Repository
public class LostandfoundServiceImpl extends GenericServiceImpl<Lostandfound> implements LostandfoundService {
	
	/**
	 * Find all Lostandfound entities.
	 * 
	 * @return List<Lostandfound> all Lostandfound entities
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Lostandfound> findAll() {
		try {
			final String queryString = "select model from Lostandfound model order by model.id desc";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public Lostandfound setParameters(Map<String, Object> model,Lostandfound lostandfound,String userName) {
		
		
	
		
		
		if(model.get("lostandfoundtype").toString().equalsIgnoreCase("Lost")){
			if(model.get("block") instanceof String){
				lostandfound.setBlockId(Integer.parseInt(model.get("blockId").toString()));
			}else{
				lostandfound.setBlockId(Integer.parseInt(model.get("block").toString()));
			}
			if(model.get("propertyNo") instanceof String){
				lostandfound.setPropertyId(Integer.parseInt(model.get("propertyId").toString()));
			}else{
				lostandfound.setPropertyId(Integer.parseInt(model.get("propertyNo").toString()));
			}
			if(model.get("personName") instanceof String){
				lostandfound.setPersonId(Integer.parseInt(model.get("personId").toString()));
			}else{
				lostandfound.setPersonId(Integer.parseInt(model.get("personName").toString()));
			}
		}
		
		lostandfound.setCreatedBy(userName);
		lostandfound.setDate(ConvertDate.formattedDate(model.get("datelost").toString()));
		lostandfound.setDescription((String) model.get("lostandfoundcontent"));
		lostandfound.setSubject((String) model.get("lostandfoundsubject"));
		lostandfound.setType((String) model.get("lostandfoundtype"));		
		lostandfound.setStatus((String) model.get("status"));		
		lostandfound.setUpdatedBy(userName);
		return lostandfound;
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Blob getImage(int lostandfoundId) {
		return (Blob) entityManager.createNamedQuery("lostandfound.getImage", Blob.class)
				.setParameter("lostandfoundId", lostandfoundId)
				.getSingleResult();
	}

	@Override
	public void uploadImageOnId(int lostandfoundId, Blob blob) {
		entityManager.createNamedQuery("lostandfound.uploadImageOnId")
		.setParameter("lostandfoundId", lostandfoundId)
		.setParameter("blob", blob)		
		.executeUpdate();
		
	}
	

}