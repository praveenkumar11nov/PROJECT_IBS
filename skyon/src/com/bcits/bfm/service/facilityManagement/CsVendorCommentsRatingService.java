package com.bcits.bfm.service.facilityManagement;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bcits.bfm.model.ConciergeVendorServices;
import com.bcits.bfm.model.CsVendorCommentsRating;
import com.bcits.bfm.service.GenericService;

public interface CsVendorCommentsRatingService extends GenericService<CsVendorCommentsRating> {
	
	
	public CsVendorCommentsRating getVendorCommentsObject(Map<String, Object> map, String type,CsVendorCommentsRating csVendorCommentsRating,int personId,HttpServletRequest request);

	List<?> getResponse(int personId);

	
}
