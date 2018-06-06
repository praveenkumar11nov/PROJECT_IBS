package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.AssetSpares;
import com.bcits.bfm.model.ConciergeServices;
import com.bcits.bfm.model.ConciergeVendorServices;
import com.bcits.bfm.model.CsVendorServiceCharges;
import com.bcits.bfm.service.facilityManagement.CsVendorServiceChargeService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class CsVendorServiceChargeImpl extends GenericServiceImpl<CsVendorServiceCharges> implements CsVendorServiceChargeService {
	
	@SuppressWarnings("serial")
	@Override
	public List<?> getResponse(int vsId) {
		@SuppressWarnings("unchecked")
		List<CsVendorServiceCharges> list =entityManager.createNamedQuery("CsVendorServiceCharges.findAll").setParameter("vsId", vsId).getResultList();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final CsVendorServiceCharges record : list) {
			response.add(new HashMap<String, Object>() {{
				put("vscId", record.getVscId());
				put("vsId", record.getVsId());
				put("collectionMethod", record.getCollectionMethod());
				put("vendorRateType", record.getVendorRateType());
				put("vrtPaymethod", record.getVrtPaymethod());
				put("vendorRateTypeNote", record.getVendorRateTypeNote());
				put("vendorRate", record.getVendorRate());
				put("serviceRateType", record.getServiceRateType());
				put("srtPaymethod", record.getSrtPaymethod());
				put("serviceRateTypeNote", record.getServiceRateTypeNote());
				put("serviceRate", record.getServiceRate());
				put("createdBy", record.getCreatedBy());
				put("lastUpdatedBy", record.getLastUpdatedBy());
			}});
		}
		return response;
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ConciergeVendorServices> getVendorService(int vsId) {
		
		return entityManager.createNamedQuery("ConciergeVendorServices.getVendorService")
				.setParameter("vsId", vsId)
				.getResultList();
		
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public ConciergeServices getVendorServiceBasedOnServiceChargeID(int vscId) {
		
		return (ConciergeServices) entityManager.createNamedQuery("CsVendorServiceCharges.getConciergeServices")
				.setParameter("vscId", vscId)
				.getSingleResult();
		
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> getDistinctServiceId() {
		return entityManager.createNamedQuery("CsVendorServiceCharges.getDistinctServiceId",Integer.class)
				.getResultList();
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CsVendorServiceCharges> findAll() {
		return entityManager.createNamedQuery("serviceCharges.getAll").getResultList();
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public CsVendorServiceCharges getVendorRate(int vsId, String rateType) {
		return entityManager.createNamedQuery("CsVendorServiceCharges.getVendorRate",CsVendorServiceCharges.class)
				.setParameter("vsId", vsId)
				.setParameter("rateType", rateType)
				.getSingleResult();
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public CsVendorServiceCharges getServiceChargeBasedOnId(int vscId) {
		return  entityManager.createNamedQuery("CsVendorServiceCharges.getServiceChargeBasedOnId",CsVendorServiceCharges.class)
				.setParameter("vscId", vscId)
				.getSingleResult();
		
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CsVendorServiceCharges> getChargeObjectExceptId(int vscId) {
		return entityManager.createNamedQuery("CsVendorServiceCharges.getChargeObjectExceptId")
		.setParameter("vscId", vscId)
		.getResultList();
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CsVendorServiceCharges> getServiceChargeBasedOnVendorServiceId(int vsId) {
		return entityManager.createNamedQuery("CsVendorServiceCharges.getServiceChargeBasedOnVendorServiceId")
				.setParameter("vsId", vsId)
				.getResultList();
	}
	@Override
	public int getVedorRateBasedOnVendorRateType(int vscId) {
		List<Integer> list = entityManager.createNamedQuery("CsVendorServiceCharges.getVedorRateBasedOnVendorRateType",
				Integer.class)
				.setParameter("vscId", vscId)
				.getResultList();
				Iterator<Integer> it=list.iterator();
				while(it.hasNext()){
					
					return (int) it.next();
				}
				return 0;
			}
	
}
