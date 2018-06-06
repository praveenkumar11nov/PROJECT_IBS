package com.bcits.bfm.serviceImpl.commonServicesTariffManagemntImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.CommonServicesRateSlab;
import com.bcits.bfm.service.commonServicesTariffManagement.CommonServiceRateSlabService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class CommonServiceRateSlabServiceImpl extends GenericServiceImpl<CommonServicesRateSlab> implements CommonServiceRateSlabService
{

	@Override
	public List<CommonServicesRateSlab> rateSlabListByParentID(int csRmId) {
		return entityManager.createNamedQuery("CommonServicesRateSlab.rateSlabListByParentID",CommonServicesRateSlab.class).setParameter("csRmId", csRmId).getResultList();
	}

	@Override
	public Object setResponse(List<CommonServicesRateSlab> commonServiceRateSlabsList) {

		  List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		  for (final CommonServicesRateSlab commonServicesRateSlab : commonServiceRateSlabsList) 
		  {
				           result.add(new HashMap<String, Object>()
				            {
								private static final long serialVersionUID = 1L;

							{
							 if((commonServicesRateSlab.getCsSlabFrom()) == null)
							 {
								 put("csRmId",commonServicesRateSlab.getCsRmId());
								 put("csRsId",commonServicesRateSlab.getCsRsId());
								 put("csSlabNo",commonServicesRateSlab.getCsSlabNo());
					             put("csSlabType", commonServicesRateSlab.getCsSlabType());
					             put("csSlabRateType",commonServicesRateSlab.getCsSlabRateType());
					             put("csRate", commonServicesRateSlab.getCsRate());
					             put("status", commonServicesRateSlab.getStatus());
					             put("createdBy", commonServicesRateSlab.getCreatedBy());
					             put("lastUpdatedBy", commonServicesRateSlab.getLastUpdatedBy());
					             put("lastUpdatedDT", commonServicesRateSlab.getLastUpdatedDT());
								 put("csDummySlabFrom", "NA");
								 put("csDummySlabTo", "NA");
							 }
							 else
							{
								 put("csRmId",commonServicesRateSlab.getCsRmId());
								 put("csRsId",commonServicesRateSlab.getCsRsId());
								 put("csSlabNo",commonServicesRateSlab.getCsSlabNo());
					             put("csSlabType", commonServicesRateSlab.getCsSlabType());
					             put("csSlabFrom",commonServicesRateSlab.getCsSlabFrom());
					             put("csSlabTo", commonServicesRateSlab.getCsSlabTo());
					             if(commonServicesRateSlab.getCsSlabTo() == 999999)
					             {
					            	 put("csDummySlabTo", "Max");
					            	 put("csDummySlabFrom", commonServicesRateSlab.getCsSlabFrom());
					             }
					             else
					             {
					            	 put("csDummySlabTo", commonServicesRateSlab.getCsSlabTo());
					            	 put("csDummySlabFrom", commonServicesRateSlab.getCsSlabFrom());
					             }
					             put("csSlabRateType",commonServicesRateSlab.getCsSlabRateType());
					             put("csRate", commonServicesRateSlab.getCsRate());
					             put("status", commonServicesRateSlab.getStatus());
					             put("createdBy", commonServicesRateSlab.getCreatedBy());
					             put("lastUpdatedBy", commonServicesRateSlab.getLastUpdatedBy());
					             put("lastUpdatedDT", commonServicesRateSlab.getLastUpdatedDT());
							}
				            }});
		  }
		return result;
	}

	@Override
	public void updateslabStatus(int csRsId, HttpServletResponse response) 
	{
		try
		  {
		   PrintWriter out = response.getWriter();
		   List<String> attributesList = new ArrayList<String>();
		   attributesList.add("status");
		   CommonServicesRateSlab commonServicesRateSlab = find(csRsId);
		   if(commonServicesRateSlab.getStatus().equalsIgnoreCase("Active"))
		   {
		    entityManager.createNamedQuery("CommonServicesRateSlab.updateRateSlabStatusFromInnerGrid").setParameter("status", "Inactive").setParameter("csRsId", csRsId).executeUpdate();
		    out.write("Rate Slab In-Active");
		   }
		   else
		   {
		    entityManager.createNamedQuery("CommonServicesRateSlab.updateRateSlabStatusFromInnerGrid").setParameter("status", "Active").setParameter("csRsId", csRsId).executeUpdate();
		    out.write("Rate Slab Active");
		   }
		  } 
		  catch (IOException e)
		  {
		   e.printStackTrace();
		  }
	}

	@Override
	public List<CommonServicesRateSlab> findRateSlabByID(int csRmId) {
		List<CommonServicesRateSlab> list = entityManager.createNamedQuery("CommonServicesRateSlab.findRateSlabByID",CommonServicesRateSlab.class).setParameter("csRmId", csRmId).getResultList();
		return selectedList(list);
	}
	private List<CommonServicesRateSlab> selectedList(List<CommonServicesRateSlab> list) 
	{
		List<CommonServicesRateSlab> listNew = new ArrayList<CommonServicesRateSlab>();
		for (Iterator<CommonServicesRateSlab> iterator = list.iterator(); iterator.hasNext();) {
			CommonServicesRateSlab elRateSlabs = (CommonServicesRateSlab) iterator.next();
			elRateSlabs.setCommonServicesRateMaster(null);
			listNew.add(elRateSlabs);
		}
		return listNew;
	}

	@Override
	public CommonServicesRateSlab findRateSlabByPrimayID(int csRsId) {
		return entityManager.createNamedQuery("CommonServicesRateSlab.findRateSlabByPrimayID",CommonServicesRateSlab.class).setParameter("csRsId", csRsId).getSingleResult();
	}

}
