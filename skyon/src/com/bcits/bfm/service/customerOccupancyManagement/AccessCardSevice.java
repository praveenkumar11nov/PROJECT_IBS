package com.bcits.bfm.service.customerOccupancyManagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.AccessCards;
import com.bcits.bfm.service.GenericService;

public interface AccessCardSevice extends GenericService<AccessCards>{
	
	public List<AccessCards> findAll();
	
	public int getAccesCardIdBasedOnNo(String acNo);
	
	public String getaccessCradNoBasedOnId(int acId);

	public AccessCards getAccessCardsObject(Map<String, Object> map,
			String string, AccessCards accessCards);

	public List<?> findOnPersonId(int personId);
	
	public List<String> getAcName( String acNo , int personId);

	List<?> findOnPersonIdForPermissions(int personId);

	public List<AccessCards> findAllAccessCards();

	public List<?> findOnPersonIdForSystemSecurity(int personId);

	public void updateAccessCardStatus(int acId, String operation,
			HttpServletResponse response);

	List<?> findAllAccessCardNumbers();
	
	public List<AccessCards> getAcNosBasedOnpersonId( int personId );

	public List<?> getCardAssignedStatusOnId(int acId);

	public List<?> readAccessCardData();

	
	

	
}
