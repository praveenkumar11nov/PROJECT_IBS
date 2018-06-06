package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ConciergeVendorServices;
import com.bcits.bfm.model.CsVendorCommentsRating;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.customerOccupancyManagement.OwnerService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.ConciergeVendorsService;
import com.bcits.bfm.service.facilityManagement.CsVendorCommentsRatingService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

@Repository
public class CsVendorCommentsRatingImpl extends GenericServiceImpl<CsVendorCommentsRating> implements CsVendorCommentsRatingService {

	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private ConciergeVendorsService conciergeVendorsService;
	
	@Autowired
	private PersonService personService;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public CsVendorCommentsRating getVendorCommentsObject(Map<String, Object> map, String type,CsVendorCommentsRating csVendorCommentsRating,int personId,HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String userName = (String)session.getAttribute("userId");
		if( type == "save" ){
			final Map<String,Object> ownerMap = (Map<String,Object>)map.get("ownerNames");
			int vendorId = conciergeVendorsService.getCsVendorIdBasedOnPersonId(personId).getCsvId();
			System.out.println(ownerMap.get("ownerId"));
			csVendorCommentsRating.setCsvId(vendorId);
			csVendorCommentsRating.setOwnerId((Integer)ownerMap.get("ownerId"));
			csVendorCommentsRating.setCreatedBy(userName);
			
		}
		if( type == "update" ){
			csVendorCommentsRating.setVcrId((Integer)map.get("vcrId"));
			csVendorCommentsRating.setCsvId((Integer)map.get("csvId"));
			csVendorCommentsRating.setOwnerId((Integer)map.get("ownerId"));
			csVendorCommentsRating.setCreatedBy((String)map.get("createdBy"));
			
		}
		csVendorCommentsRating.setPersonId(personId);
		csVendorCommentsRating.setComments((String)map.get("comments"));
		csVendorCommentsRating.setRatings((Integer)map.get("ratings"));
		csVendorCommentsRating.setLastUpdatedBy(userName);
		return csVendorCommentsRating;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getResponse(int personId) {
		
		@SuppressWarnings("unchecked")
		List<CsVendorCommentsRating> list =entityManager.createNamedQuery("CsVendorCommentsRating.findAll")
		.setParameter("personId", personId)
		.getResultList();
		
		List<Map<String, Object>> csVendorCommentsRating =  new ArrayList<Map<String, Object>>(); 
		for (final CsVendorCommentsRating record : list) {
			final Person personRec = personService.getPersonBasedOnOwnerId(record.getOwnerId());
				csVendorCommentsRating.add(new HashMap<String, Object>() {{
					put("vcrId", record.getVcrId());
					put("csvId", record.getCsvId());
					put("ownerId", record.getOwnerId());
					if( personRec.getLastName() == null ){
						put("ownerNames",personRec.getFirstName()+"["+personRec.getPersonType()+"]");
					}
					else{
						put("ownerNames",personRec.getFirstName()+" "+personRec.getLastName()+"["+personRec.getPersonType()+"]" );
					}
					put("comments", record.getComments());
					put("ratings", record.getRatings());
					put("createdBy", record.getCreatedBy());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					
				}});
			
	}
		return csVendorCommentsRating;	
		
	}
}
