package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.ParkingSlots;
import com.bcits.bfm.model.ParkingSlotsAllotment;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.facilityManagement.BlocksService;
import com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService;
import com.bcits.bfm.service.facilityManagement.ParkingSlotsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

@Repository
public class ParkingSlotsAllotmentServiceImpl extends
		GenericServiceImpl<ParkingSlotsAllotment> implements
		ParkingSlotsAllotmentService {

	@Autowired
	private PropertyService propertyService;
	@Autowired
	private ParkingSlotsService parkingSlotsService;
	@Autowired
	private BlocksService blocksService;

	private static final Log logger = LogFactory
			.getLog(ParkingSlotsAllotmentServiceImpl.class);

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService#findAll()
	 * 
	 * Finding All instance opf parking slot allotement
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ParkingSlotsAllotment> findAll() {
		logger.info("Finding Parking Slots Allotment Details");
		try {
			/*final String queryString = "select model from ParkingSlotsAllotment model ORDER BY model.psaId DESC";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();*/
			return entityManager.createNamedQuery("ParkingSlotsAllotment.findAll").getResultList();
		} catch (RuntimeException re) {
			logger.error("Finding Parking Slots Allotment Details Failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService#addParkingSlotsAllocation(com.bcits.bfm.model.ParkingSlotsAllotment)
	 * 
	 * parking slot allotement Create Record
	 */
	@Override
	public ParkingSlotsAllotment addParkingSlotsAllocation(
			ParkingSlotsAllotment pakingSlotAllocation) {
		entityManager.persist(pakingSlotAllocation);
		logger.info("Parking Slots Allotment Details Saved");
		return pakingSlotAllocation;
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService#getBeanObject(java.util.Map)
	 * 
	 * Crete parking slot allotement Object
	 */
	@Override
	public ParkingSlotsAllotment getBeanObject(Map<String, Object> map) {
		logger.info("Parking Slots Allotment Setting Started");
		ParkingSlotsAllotment psa = new ParkingSlotsAllotment();
		if (!(map.get("allotmentDateTo").equals("")))
			psa.setAllotmentDateTo(ConvertDate.formattedDate((String) map
					.get("allotmentDateTo")));
		psa.setAllotmentDateFrom(ConvertDate.formattedDate((String) map
				.get("allotmentDateFrom")));
		psa.setProperty(propertyService.find(Integer.parseInt(map.get(
				"property").toString())));
		psa.setParkingSlots(parkingSlotsService.find(Integer.parseInt(map.get(
				"parkingSlotsNo").toString())));
		if (!(map.get("psRent").equals("")))
			psa.setPsRent(Integer.parseInt(map.get("psRent").toString()));
		if (!(map.get("psRentLastRevised").equals("")))
			psa.setPsRentLastRevised(ConvertDate.formattedDate((String) map
					.get("psRentLastRevised")));
		psa.setStatus(map.get("status").toString());
		if (map.get("block") instanceof String)
			psa.setBlocks(blocksService.find(Integer.parseInt(map
					.get("blockId").toString())));
		else
			psa.setBlocks(blocksService.find(Integer.parseInt(map.get("block")
					.toString())));

		logger.info("Parking Slots Allotment Setting Completed");
		return psa;
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService#freeAllocation(com.bcits.bfm.model.ParkingSlotsAllotment)
	 * 
	 * Deleteing parking slot allotement
	 */
	@Override
	public void freeAllocation(ParkingSlotsAllotment psa) {
		/*entityManager.remove(entityManager.contains(psa) ? psa : entityManager
				.merge(psa));*/
		entityManager.createNamedQuery("ParkingSlotAllocation.deleteParkingSlotsAllotment").setParameter("psaId",psa.getPsaId()).executeUpdate();
		logger.info("Parking Slots Allotment Deletion Completed");
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService#getStatus(int)
	 * 
	 * find status of parking slot allotement
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getStatus(int psId) {
		List<String> slotNumbers = entityManager
				.createNamedQuery("ParkingSlotAllocation.getSlotStatus")
				.setParameter("psID", parkingSlotsService.find(psId))
				.getResultList();
		logger.info("Parking Slots Staus Based On PS_ID Completed");
		return slotNumbers.toString();
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService#setParkingSlotAllocationStatus(int, java.lang.String, javax.servlet.http.HttpServletResponse)
	 * 
	 * find status of parking slot
	 */
	@Override
	public void setParkingSlotAllocationStatus(int psaId, String operation,
			HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			if (operation.equalsIgnoreCase("allocate")) {
				ParkingSlotsAllotment psa = find(psaId);
				Date allotementDate = psa.getAllotmentDateTo();
				if (allotementDate != null) {
					Date presentDate = new Date();
					if (presentDate.getTime() > allotementDate.getTime()) {
						out.write("Parking Slot Allocation Date is Expired");
						logger.info("Parking Slot Allocation Date is Expired");
						out.close();
					} else {
						entityManager
								.createNamedQuery(
										"ParkingSlotAllocation.UpdateStatus")
								.setParameter("psaStatus", "true")
								.setParameter("psaId", psaId).executeUpdate();
						logger.info("Parking Slot Allocation is Activated");
						out.write("Parking Slot Is Allocated");
					}
				} else {
					entityManager
							.createNamedQuery(
									"ParkingSlotAllocation.UpdateStatus")
							.setParameter("psaStatus", "true")
							.setParameter("psaId", psaId).executeUpdate();
					logger.info("Parking Slot Allocation is Activated");
					out.write("Parking Slot Is Allocated");
				}
			} else {
				entityManager
						.createNamedQuery("ParkingSlotAllocation.UpdateStatus")
						.setParameter("psaStatus", "false")
						.setParameter("psaId", psaId).executeUpdate();
				logger.info("Parking Slot Allocation is De-Activated");
				out.write("Parking Slot Is De-Allocated");
			}
		} catch (IOException e) {
			logger.info("Parking Slot Allocation Status Failed");
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService#setAllocationStatusInParkingSlot(int, java.lang.String)
	 * 
	 * Updating find status of parking slot allotement status
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setAllocationStatusInParkingSlot(int psaId, String status) {
		List<ParkingSlots> result = entityManager.createNamedQuery("ParkingSlotsAllotment.setAllocationStatusInParkingSlot").setParameter("psaId",psaId).getResultList();
		if (!(result.size() == 0)) {
			int psId = result.get(0).getPsId();
			entityManager
					.createNamedQuery(
							"ParkingSlotAllocation.setAllocationSlotStatus")
					.setParameter("psId", psId).setParameter("status", status)
					.executeUpdate();
			logger.info("Parking Slot ALLOCATION_STATUS Updated");
		}

	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService#statusAfterTimeout(int, java.lang.String)
	 * 
	 * update status of find status of parking slot allotement after expiry
	 */
	@Override
	public void statusAfterTimeout(int psaId, String string) {
		entityManager.createNamedQuery("ParkingSlotAllocation.UpdateStatus")
				.setParameter("psaStatus", string).setParameter("psaId", psaId)
				.executeUpdate();
		logger.info("Parking Slot Allocation PSA_ALLOCATION_STATUS Updated After Expire Date");
		setAllocationStatusInParkingSlot(psaId, string);
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService#getStatusFromParkingSlot(int)
	 * 
	 * finding status of find status of parking slot allotement
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getStatusFromParkingSlot(int psId) {
		logger.info("Getting Status Of Parking Slot");
		List<String> al = entityManager
				.createNamedQuery("ParkingSlotAllocation.getStatusFromParkingSlot")
				.setParameter("psId",psId)
				.getResultList();
		String b = "";
		if (al.size() > 0) {
			b = al.get(0);			
		}
		return b;
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService#findBySlotId(int, java.util.Date)
	 * 
	 * finding Existing slot
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String findBySlotId(int i, Date d) {
		logger.info("Finding Slot Number Of Parking Slot Based on PS_ID");
		List<ParkingSlotsAllotment> psa = entityManager
				.createNamedQuery("ParkingSlotAllocation.getExistingSlot")
				.setParameter("psId", parkingSlotsService.find(i))
				.getResultList();
		String msg = "";
		if (psa.size() != 0) {
			if (psa.get(0).getParkingSlots().getPsParkingMethod()
					.equalsIgnoreCase("FIXED")) {
				msg = "Parking Slot Type is Fixed";
			}
			if (d.getTime() <= psa.get(0).getAllotmentDateTo().getTime()) {
				msg = "FromDate Should Be Greater than Previous Allocation's ToDate";
			}
		}
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService#checkTimeout()
	 * 
	 * finding expiry date
	 */
	@Override
	public void checkTimeout() {
		logger.info("Finding Parking Slot Allocation Expire Date");
		List<ParkingSlotsAllotment> psa = findAll();
		Iterator<ParkingSlotsAllotment> it = psa.iterator();
		while (it.hasNext()) {
			ParkingSlotsAllotment newObj = it.next();
			if (newObj.getAllotmentDateTo() != null) {
				long allotementDate = newObj.getAllotmentDateTo().getTime();
				int psaId = newObj.getPsaId();
				Date presentDate = new Date();
				if (presentDate.getTime() > allotementDate) {
					statusAfterTimeout(psaId, "false");
				}
			}
		}
		logger.info("Finding Parking Slot Allocation Expire Date Completed");
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService#getPropertyId()
	 * 
	 * finding proprty
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Property> getPropertyId() {
		List<Property> al = entityManager
				.createNamedQuery("ParkingSlotAllotment.getPropertyIds")
				.setParameter("status", "true").getResultList();
		return al;
	}

	@Override
	public List<?> findAllParkingSlot() {
		logger.info("Finding Parking Slots Allotment Details");
		try {
			return entityManager.createNamedQuery("ParkingSlotsAllotment.findAllParkingSlot").getResultList();
		} catch (RuntimeException re) {
			logger.error("Finding Parking Slots Allotment Details Failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParkingSlotsAllotment> getAllParkingSlots(int propertyId) {
		 return entityManager.createNamedQuery("ParkingSlotsAllotment.findParkingSlotsAllotment").setParameter("propertyId", propertyId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParkingSlotsAllotment> findAllParkingAllocationSlot() {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("ParkingSlotsAllotment.findAllParkingAllocationSlot").getResultList();
	}

}