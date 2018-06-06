package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.AccessPoints;
import com.bcits.bfm.service.facilityManagement.AccessPointsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;


@Repository
public class AccessPointsServiceImpl extends GenericServiceImpl<AccessPoints> implements AccessPointsService
{

	
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(AccessPointsServiceImpl.class);

	
	/** Read all access points
	 * @return List of access points
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AccessPoints> findAll() {
		return entityManager.createNamedQuery("AccessPoint.findAll").getResultList();
		
	}

	/** Read all Access Point Codes
	 * @return List od Access point codes
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> findAllApcode() 
	{ 
		return entityManager.createNamedQuery("AccessPoint.getAllApCodes").getResultList();
	}
	
	/** Get Access point Id on Name
	 * @param apName 		Access Point Name
	 * @return Access Point Id
	 */
	@Override
	@SuppressWarnings({ "rawtypes" })
	@Transactional(propagation = Propagation.SUPPORTS)
	public int getAccesspointIdBasedOnName(String apName) {
		List<Integer> accessRepositoryId =  entityManager.createNamedQuery("AccessPoints.getAccessPointIdBasedOnName",
					Integer.class)
					.setParameter("apName", apName)
					.getResultList();
		
		Iterator it=accessRepositoryId.iterator();
		while(it.hasNext()){
			
			return (int) it.next();
		}
		return 0;
		 
	}
	
	/** Get Access Point Name on Id
	 * @param apId	Access Point Id
	 * @return Access POint Name
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AccessPoints> getPointName(int apId){
		
				return entityManager.createNamedQuery("AccessPoints.getPointName")
						.setParameter("apId", apId)
						.getResultList();
	}
}
