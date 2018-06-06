package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import com.bcits.bfm.model.MaintainanceTypes;
import com.bcits.bfm.service.facilityManagement.MaintainanceTypesService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

/**
 * A data access object (DAO) providing persistence and search support for
 * MaintainanceTypes entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see .MaintainanceTypes
 * @author MyEclipse Persistence Tools
 */
@Repository
public class MaintainanceTypesServiceImpl extends
		GenericServiceImpl<MaintainanceTypes> implements
		MaintainanceTypesService {

	private static final Log logger = LogFactory.getLog(MaintainanceTypesService.class);	

	/**
	 * Find all MaintainanceTypes entities.
	 * 
	 * @return List<MaintainanceTypes> all MaintainanceTypes entities
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<MaintainanceTypes> findAll() {
		logger.info("finding all MaintainanceTypes instances");
		try {
			final String queryString = "select model from MaintainanceTypes model ORDER BY model.mtId DESC";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public MaintainanceTypes getBeanObject(Map<String, Object> map) {
		logger.error("Maintainance Types Setting Details Started");
		MaintainanceTypes mt = new MaintainanceTypes();
		mt.setMaintainanceType(((String) map.get("maintainanceType")).toUpperCase());
		mt.setDescription((String) map.get("description"));
		mt.setMtDt(ConvertDate.formattedDate(map.get("mtDt").toString()));
		logger.error("Maintainance Types Setting Details Completed");
		return mt;
	}
}