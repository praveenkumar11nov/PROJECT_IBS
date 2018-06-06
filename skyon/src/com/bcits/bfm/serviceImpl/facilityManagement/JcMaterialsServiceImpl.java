package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.ItemMaster;
import com.bcits.bfm.model.JcMaterials;
import com.bcits.bfm.model.UnitOfMeasurement;
import com.bcits.bfm.service.VendorsManagement.ItemMasterService;
import com.bcits.bfm.service.VendorsManagement.UomService;
import com.bcits.bfm.service.facilityManagement.JcMaterialsService;
import com.bcits.bfm.service.facilityManagement.JobCardsService;
import com.bcits.bfm.service.inventoryManagement.StoreMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

/**
 * A data access object (DAO) providing persistence and search support for
 * JcMaterials entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see .JcMaterials
 * @author MyEclipse Persistence Tools
 */
@Repository
public class JcMaterialsServiceImpl extends GenericServiceImpl<JcMaterials> implements JcMaterialsService {
	
	private static final Log logger = LogFactory.getLog(JcMaterialsService.class);
	@Autowired
	private JobCardsService jobCardsService;
	@Autowired
	private ItemMasterService itemMasterService;
	@Autowired
	private UomService uomService;
	@Autowired
	private StoreMasterService storeMasterService;
	
	/**
	 * Find all JcMaterials entities.
	 * 
	 * @return List<JcMaterials> all JcMaterials entities
	 */
	@SuppressWarnings("unchecked")
	public List<JcMaterials> findAll() {
		logger.info("finding all JcMaterials instances");
		try {
			final String queryString = "select model from JcMaterials model";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public List<?> readData(int jcId) {
		List<Map<String, Object>> jcMaterials = new ArrayList<Map<String, Object>>();
		for (final JcMaterials record :readJobMaterials(jcId)) {
			jcMaterials.add(new HashMap<String, Object>() {				
				private static final long serialVersionUID = 1L;
				{				
					put("jcmId", record.getJcmId());
					put("jobCards", record.getJobCards().getJcId());
					put("jcmType", record.getJcmType());					
					put("storeMaster", record.getstoreMaster().getStoreName());					
					put("storeMasterId", record.getstoreMaster().getStoreId());					
					put("jcmitemName", record.getItemMaster().getImName());											
					put("jcmitemId", record.getItemMaster().getImId());											
					put("jcmimUom", record.getUnitOfMeasurement().getUom());											
					put("jcmimUomId", record.getUnitOfMeasurement().getUomId());											
					put("imQuantity", record.getImQuantity());											
					put("jcmMaterial", record.getJcmMaterial());											
					put("jcmPartno", record.getJcmPartno());											
					put("jcmQuantinty", record.getJcQuantity());											
					put("returnedQuantinty", record.getRerunedquantity());											
					put("jcmrate", record.getRate());											
					put("createdBy", record.getCreatedBy());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					put("lastUpdatedDate",ConvertDate.TimeStampString(record.getLastUpdatedDt()));
					put("status",record.getStatus());
				}
			});
		}
		return jcMaterials;	
	}

	public List<JcMaterials> readJobMaterials(int jcId) {
		@SuppressWarnings("unchecked")
		List<JcMaterials> jcMaterials = entityManager.createNamedQuery("JobCards.readJobMaterials").setParameter("jcId", jobCardsService.find(jcId)).getResultList();
		return jcMaterials;
	}

	@Override
	public JcMaterials setParameters(int jcId, JcMaterials jcMaterials,String userName, Map<String, Object> map) {
		if(map.get("createdBy")==null||map.get("createdBy")=="")
			jcMaterials.setCreatedBy(userName);
		jcMaterials.setImQuantity((String) map.get("imQuantity"));
		jcMaterials.setJobCards(jobCardsService.find(jcId));
		jcMaterials.setLastUpdatedBy(userName);
		ItemMaster im=null;
		if(map.get("storeMaster") instanceof String){
			jcMaterials.setstoreMaster(storeMasterService.find(Integer.parseInt(map.get("storeMasterId").toString())));
		}else{
			jcMaterials.setstoreMaster(storeMasterService.find(Integer.parseInt(map.get("storeMaster").toString())));
		}
		if(map.get("jcmitemName") instanceof String){
			im=itemMasterService.find(Integer.parseInt(map.get("jcmitemId").toString()));
		}
		else{
			im=itemMasterService.find(Integer.parseInt(map.get("jcmitemName").toString()));
		}
		jcMaterials.setItemMaster(im);
		jcMaterials.setJcmType((String) map.get("jcmType"));
		jcMaterials.setJcmMaterial((String) map.get("jcmMaterial"));
		if(map.get("jcmimUom") instanceof String)
			jcMaterials.setUnitOfMeasurement(uomService.find(Integer.parseInt(map.get("jcmimUomId").toString())));
		else
			jcMaterials.setUnitOfMeasurement(uomService.find(Integer.parseInt(map.get("jcmimUom").toString())));
		jcMaterials.setJcmPartno((String) map.get("jcmPartno"));		
		jcMaterials.setJcQuantity((String) map.get("jcmQuantinty"));
		jcMaterials.setRate(Integer.parseInt(map.get("jcmrate").toString()));
		jcMaterials.setStatus((String) map.get("status"));
		return jcMaterials;
	}

	@Override
	public Object deleteJcMaterial(JcMaterials jcMaterials) {
		logger.info("finding all JcMaterials instances");
		try {
			final String queryString = "DELETE JcMaterials WHERE jcmId="+jcMaterials.getJcmId();
			Query query = entityManager.createQuery(queryString);
			return query.executeUpdate();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public void updateIssue(int jcId, int imId, int storeId, Double imQuantity,int uomId,String status) {
		logger.info(jcId+" "+imId+" "+storeId+" "+imQuantity+" "+uomId);
		try {
			final String queryString = "UPDATE JcMaterials model SET model.status=:status,model.unitOfMeasurement=:uomId,model.itemMaster=:imId,model.storeMaster=:storeId,model.jcQuantity=:imQuantity WHERE model.jcmId=:jcId";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("uomId", uomService.find(uomId));
			query.setParameter("jcId", jcId);
			query.setParameter("status", status);
			query.setParameter("imId", itemMasterService.find(imId));
			query.setParameter("storeId", storeMasterService.find(storeId));
			query.setParameter("imQuantity", imQuantity+"");
			query.executeUpdate();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public List<String> readJobMaterialsStatus(int jcId) {
		try {
			final String queryString = "select model.status from JcMaterials model where model.jobCards=:jcId";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("jcId", jobCardsService.find(jcId));
			return query.getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}	
}