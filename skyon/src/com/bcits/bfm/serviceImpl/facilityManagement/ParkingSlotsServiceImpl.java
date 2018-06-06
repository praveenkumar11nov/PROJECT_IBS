package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.ParkingSlots;
import com.bcits.bfm.service.facilityManagement.BlocksService;
import com.bcits.bfm.service.facilityManagement.ParkingSlotsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

/**
 * @author Manjunath Kotagi
 * 
 * Parking Slot Master Service Implementation
 *
 */
@Repository
public class ParkingSlotsServiceImpl extends GenericServiceImpl<ParkingSlots>
		implements ParkingSlotsService {

	@Autowired
	private BlocksService blocksService;

	private static final Log logger = LogFactory.getLog(ParkingSlotsServiceImpl.class);

	
	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsService#findAll()
	 * 
	 * Finding All Instances of Parking Slot Master
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ParkingSlots> findAll() {
		logger.info("Finding Parking Slots Master Details");
		try {
			/*final String queryString = "select model from ParkingSlots model ORDER BY model.psId DESC";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();*/		
			return entityManager.createNamedQuery("ParkingSlots.readAll").getResultList();
		} catch (RuntimeException re) {
			logger.error("Parking Slots Master Details Finding Failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsService#addParkingSlots(com.bcits.bfm.model.ParkingSlots)
	 * 
	 * Creating Records of Parking Slot Master
	 */
	@Override
	public ParkingSlots addParkingSlots(ParkingSlots ps) {
		entityManager.persist(ps);
		logger.error("Parking Slots Master Details Saved");
		return ps;
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsService#getBeanObject(java.util.Map)
	 * 
	 * Creating Bean Object to Add Record and Update Record
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ParkingSlots getBeanObject(Map<String, Object> map) {
		logger.error("Parking Slots Master Setting Details Started");
		ParkingSlots ps = new ParkingSlots();

		if (map.get("block") instanceof String)
			ps.setBlockId(blocksService.find(Integer.parseInt(map
					.get("blockId").toString())));
		else
			ps.setBlockId(blocksService.find(Integer.parseInt(map.get("block")
					.toString())));
		ps.setPsSlotNo(((String) map.get("psSlotNo")).toUpperCase());
		ps.setPsOwnership((String) map.get("psOwnership"));
		String parkingmethod = "";
		if ((map.get("psOwnership").toString()).equalsIgnoreCase("VISITORS"))
			parkingmethod = "FLOATING";
		else
			parkingmethod = (String) map.get("psParkingMethod");
		ps.setPsParkingMethod(parkingmethod);
		ps.setPsActiveDate(ConvertDate.formattedDate(map.get("psActiveDate")
				.toString()));
		ps.setStatus(map.get("status").toString());
		ps.setAllocation_status("false");
		logger.error("Parking Slots Master Setting Details Completed");
		return ps;
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsService#setParkingSlotStatus(int, java.lang.String, javax.servlet.http.HttpServletResponse)
	 * 
	 * Updating Parking Slot Status
	 */
	@Override
	public void setParkingSlotStatus(int psId, String operation,HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			if (operation.equalsIgnoreCase("activate")) {
				entityManager.createNamedQuery("ParkingSlot.UpdateStatus")
						.setParameter("psStatus", "true")
						.setParameter("psId", psId).executeUpdate();
				logger.error("Parking Slots Master Status(Activate) Updated");
				out.write("Parking Slot Activated");
			} else {
				entityManager.createNamedQuery("ParkingSlot.UpdateStatus")
						.setParameter("psStatus", "false")
						.setParameter("psId", psId).executeUpdate();
				logger.error("Parking Slots Master Status(De-Activate) Updated");
				out.write("Parking Slot Deactivated");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsService#getAll()
	 * 
	 * Finding Parking Slot Master Own and Rented Details
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ParkingSlots> getAll() {
		List<ParkingSlots> ps = entityManager.createNamedQuery("ParkingSlot.getAll").getResultList();
		logger.error("Parking Slots Of OWN,RENTED(Acitvated) Details Completed");
		return ps;
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsService#readNestedReadUrl(int)
	 * 
	 * Finding Slot Allotement Details Based on parking slot Id	 * 
	 * 
	 */
	@Override
	public List<?> readNestedReadUrl(int paramId) {
		List<?> ps = entityManager.createNamedQuery("ParkingSlot.readNestedReadUrl").setParameter("psId", find(paramId)).getResultList();
		logger.error("Parking Slots Allocation Details Completed");
		return ps;
	}
	

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsService#getallowedSlots(int, javax.servlet.http.HttpServletResponse)
	 * 
	 * Finding All Allowed Slot For specified Block
	 */
	@Override
	public void getallowedSlots(int blockId, HttpServletResponse response) throws IOException {
		logger.error("Parking Slots Details Based on block id");
		final String queryString = "select count(model.psId) from ParkingSlots model where model.blockId=:blockId";
		Query query = entityManager.createQuery(queryString);
		Blocks b=blocksService.find(blockId);
		query.setParameter("blockId", b);
		PrintWriter out = response.getWriter();
		long count = (long)query.getSingleResult();
		out.write(count+"");
		
	}
}