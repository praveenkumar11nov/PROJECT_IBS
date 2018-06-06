package com.bcits.bfm.serviceImpl.waterTariffManagemntImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Repository;
import com.bcits.bfm.model.WTRateMaster;
import com.bcits.bfm.model.WTRateSlabs;
import com.bcits.bfm.service.waterTariffManagement.WTRateSlabService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class WTRateSlabServiceImpl extends GenericServiceImpl<WTRateSlabs> implements WTRateSlabService
{

	@Override
	public List<WTRateSlabs> rateSlabListByParentID(int wtrmid) 
	{
		return entityManager.createNamedQuery("WTRateSlabs.rateSlabListByParentID",WTRateSlabs.class).setParameter("wtrmid", wtrmid).getResultList();
	}

	@Override
	public List<Map<String, Object>> setResponse(List<WTRateSlabs> wtRateSlabsList) 
	{
		  List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		  for (final WTRateSlabs wtRateSlabs : wtRateSlabsList) 
		  {
			            result.add(new HashMap<String, Object>() 
				            {
								private static final long serialVersionUID = 1L;
							{
							 if((wtRateSlabs.getWtSlabFrom()) == null)
							 {
								 put("wtrmid",wtRateSlabs.getWtrmid());
								 put("wtrsId",wtRateSlabs.getWtrsId());
								 put("wtSlabNo",wtRateSlabs.getWtSlabNo());
					             put("wtSlabType", wtRateSlabs.getWtSlabType());
					             put("wtSlabRateType",wtRateSlabs.getWtSlabRateType());
					             put("wtRate", wtRateSlabs.getWtRate());
					             put("wtRateSlabStatus", wtRateSlabs.getWtRateSlabStatus());
					             put("wtRateSlabCreatedBy", wtRateSlabs.getWtRateSlabCreatedBy());
					             put("wtRateSlabLastUpdatedBy", wtRateSlabs.getWtRateSlabLastUpdatedBy());
					             put("wtRateSlabLastUpdatedDT", wtRateSlabs.getWtRateSlabLastUpdatedDT());
								 put("wtDummySlabFrom", "NA");
								 put("wtDummySlabTo", "NA");
							 }
							 
							 else
							{
								 put("wtrmid",wtRateSlabs.getWtrmid());
								 put("wtrsId",wtRateSlabs.getWtrsId());
								 put("wtSlabNo",wtRateSlabs.getWtSlabNo());
								 put("wtSlabType", wtRateSlabs.getWtSlabType());
					             put("wtSlabFrom",wtRateSlabs.getWtSlabFrom());
					             put("wtSlabTo", wtRateSlabs.getWtSlabTo());
					             if(wtRateSlabs.getWtSlabTo() == 999999)
					             {
					            	 put("wtDummySlabTo", "Max");
					            	 put("wtDummySlabFrom", wtRateSlabs.getWtSlabFrom());
					             }
					             else
					             {
					            	 put("wtDummySlabTo", wtRateSlabs.getWtSlabTo());
					            	 put("wtDummySlabFrom", wtRateSlabs.getWtSlabFrom());
					             }
					             put("wtSlabRateType",wtRateSlabs.getWtSlabRateType());
					             put("wtRate", wtRateSlabs.getWtRate());
					             put("wtRateSlabStatus", wtRateSlabs.getWtRateSlabStatus());
					             put("wtRateSlabCreatedBy", wtRateSlabs.getWtRateSlabCreatedBy());
					             put("wtRateSlabLastUpdatedBy", wtRateSlabs.getWtRateSlabLastUpdatedBy());
					             put("wtRateSlabLastUpdatedDT", wtRateSlabs.getWtRateSlabLastUpdatedDT());
							}
				            }});
		  }
		return result;
	}

	@Override
	public WTRateMaster getWTRateMaster(int wtrmid) 
	{
		return  entityManager.createNamedQuery("WTRateMaster.getWTRateMaster",WTRateMaster.class).setParameter("elrmid", wtrmid).getSingleResult();
	}

	@Override
	public void updateslabStatus(int wtrsId, HttpServletResponse response)
	{
		try
		  {
		   PrintWriter out = response.getWriter();
		   List<String> attributesList = new ArrayList<String>();
		   attributesList.add("status");
		   WTRateSlabs wtRateSlabs = find(wtrsId);
		   if(wtRateSlabs.getWtRateSlabStatus().equalsIgnoreCase("Active"))
		   {
		    entityManager.createNamedQuery("WTRateSlabs.updateRateSlabStatusFromInnerGrid").setParameter("wtRateSlabStatus", "Inactive").setParameter("wtrsId", wtrsId).executeUpdate();
		    out.write("Rate Slab In-Active");
		   }
		   else
		   {
		    entityManager.createNamedQuery("WTRateSlabs.updateRateSlabStatusFromInnerGrid").setParameter("wtRateSlabStatus", "Active").setParameter("wtrsId", wtrsId).executeUpdate();
		    out.write("Rate Slab Active");
		   }
		  } 
		  catch (IOException e)
		  {
		   e.printStackTrace();
		  }
		
	}

	@Override
	public WTRateSlabs findRateSlabByPrimayID(int wtrsId) {
		return entityManager.createNamedQuery("WTRateSlabs.findRateSlabByPrimayID",WTRateSlabs.class).setParameter("wtrsId", wtrsId).getSingleResult();
	}

	@Override
	public List<WTRateSlabs> getElRateSlabGreaterThanUpdateSlabNoEq(int wtSlabNo, int wtrmid) {
		return entityManager.createNamedQuery("WTRateSlabs.getWTRateSlabGreaterThanUpdateSlabNoEq",WTRateSlabs.class).setParameter("wtSlabNo", wtSlabNo).setParameter("wtrsId", wtrmid).getResultList();
	}

	@Override
	public List<WTRateSlabs> getWTRateSlabGreaterThanUpdateSlabNo(int wtSlabNo,int wtrmid) 
	{
		return entityManager.createNamedQuery("WTRateSlabs.getWTRateSlabGreaterThanUpdateSlabNo",WTRateSlabs.class).setParameter("wtSlabNo", wtSlabNo).setParameter("wtrmid", wtrmid).getResultList();
	}

}
