package com.bcits.bfm.serviceImpl.solidWasteTariffManagemntImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.SolidWasteRateMaster;
import com.bcits.bfm.model.SolidWasteRateSlab;
import com.bcits.bfm.service.solidWasteTariffManagment.SolidWasteRateSlabService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class SolidWasteRateSlabServiceImpl extends GenericServiceImpl<SolidWasteRateSlab> implements SolidWasteRateSlabService
{

	@Override
	public List<SolidWasteRateSlab> rateSlabListByParentID(int solidWasteRmId) {
		return entityManager.createNamedQuery("SolidWasteRateSlab.rateSlabListByParentID",SolidWasteRateSlab.class).setParameter("solidWasteRmId", solidWasteRmId).getResultList();
	}

	@Override
	public Object setResponse(List<SolidWasteRateSlab> solidWasteRateSlabsList) 
	{
		  List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		  for (final SolidWasteRateSlab solidWasteRateSlab : solidWasteRateSlabsList) 
		  {
				           result.add(new HashMap<String, Object>()
				            {
								private static final long serialVersionUID = 1L;

							{
							 if((solidWasteRateSlab.getSolidWasteSlabFrom()) == null)
							 {
								 
								 put("solidWasteRmId",solidWasteRateSlab.getSolidWasteRmId());
								 put("solidWasteRsId",solidWasteRateSlab.getSolidWasteRsId());
								 put("solidWasteSlabNo",solidWasteRateSlab.getSolidWasteSlabNo());
					             put("solidWasteSlabType", solidWasteRateSlab.getSolidWasteSlabType());
					             put("solidWasteSlabRateType",solidWasteRateSlab.getSolidWasteSlabRateType());
					             put("solidWasteRate", solidWasteRateSlab.getSolidWasteRate());
					             put("status", solidWasteRateSlab.getStatus());
					             put("createdBy", solidWasteRateSlab.getCreatedBy());
					             put("lastUpdatedBy", solidWasteRateSlab.getLastUpdatedBy());
					             put("lastUpdatedDT", solidWasteRateSlab.getLastUpdatedDT());
								 put("solidWasteDummySlabFrom", "NA");
								 put("solidWasteDummySlabTo", "NA");
							 }
							 else
							{
								 put("solidWasteRmId",solidWasteRateSlab.getSolidWasteRmId());
								 put("solidWasteRsId",solidWasteRateSlab.getSolidWasteRsId());
								 put("solidWasteSlabNo",solidWasteRateSlab.getSolidWasteSlabNo());
					             put("solidWasteSlabType", solidWasteRateSlab.getSolidWasteSlabType());
					             put("solidWasteSlabFrom",solidWasteRateSlab.getSolidWasteSlabFrom());
					             put("solidWasteSlabTo", solidWasteRateSlab.getSolidWasteSlabTo());
					             if(solidWasteRateSlab.getSolidWasteSlabTo() == 999999)
					             {
					            	 put("solidWasteDummySlabTo", "Max");
					            	 put("solidWasteDummySlabFrom", solidWasteRateSlab.getSolidWasteSlabFrom());
					             }
					             else
					             {
					            	 put("solidWasteDummySlabTo", solidWasteRateSlab.getSolidWasteSlabTo());
					            	 put("solidWasteDummySlabFrom", solidWasteRateSlab.getSolidWasteSlabFrom());
					             }
					             put("solidWasteSlabRateType",solidWasteRateSlab.getSolidWasteSlabRateType());
					             put("solidWasteRate", solidWasteRateSlab.getSolidWasteRate());
					             put("status", solidWasteRateSlab.getStatus());
					             put("createdBy", solidWasteRateSlab.getCreatedBy());
					             put("lastUpdatedBy", solidWasteRateSlab.getLastUpdatedBy());
					             put("lastUpdatedDT", solidWasteRateSlab.getLastUpdatedDT());
							}
				            }});
		  }
		return result;
	}

	@Override
	public void updateslabStatus(int solidWasteRsId,HttpServletResponse response) 
	{

		try
		  {
		   PrintWriter out = response.getWriter();
		   List<String> attributesList = new ArrayList<String>();
		   attributesList.add("status");
		   SolidWasteRateSlab solidWasteRateSlab = find(solidWasteRsId);
		   if(solidWasteRateSlab.getStatus().equalsIgnoreCase("Active"))
		   {
		    entityManager.createNamedQuery("SolidWasteRateSlab.updateRateSlabStatusFromInnerGrid").setParameter("status", "Inactive").setParameter("solidWasteRsId", solidWasteRsId).executeUpdate();
		    out.write("Rate Slab In-Active");
		   }
		   else
		   {
		    entityManager.createNamedQuery("SolidWasteRateSlab.updateRateSlabStatusFromInnerGrid").setParameter("status", "Active").setParameter("solidWasteRsId", solidWasteRsId).executeUpdate();
		    out.write("Rate Slab Active");
		   }
		  } 
		  catch (IOException e)
		  {
		   e.printStackTrace();
		  }
	
		
	}

	@Override
	public SolidWasteRateSlab findRateSlabByPrimayID(int solidWasteRsId) {
		return entityManager.createNamedQuery("SolidWasteRateSlab.findRateSlabByPrimayID",SolidWasteRateSlab.class).setParameter("solidWasteRsId", solidWasteRsId).getSingleResult();
	}

	@Override
	public List<SolidWasteRateSlab> getElRateSlabGreaterThanUpdateSlabNoEq(int solidWasteSlabNo, int solidWasteRsId) {
		return entityManager.createNamedQuery("SolidWasteRateSlab.getGasRateSlabGreaterThanUpdateSlabNoEq",SolidWasteRateSlab.class).setParameter("solidWasteSlabNo", solidWasteSlabNo).setParameter("solidWasteRmId", solidWasteRsId).getResultList();
	}

	@Override
	public SolidWasteRateMaster getStatusofSolidWasteRateMaster(int solidWasteRmId) {
		return  entityManager.createNamedQuery("SolidWasteRateMaster.findID",SolidWasteRateMaster.class).setParameter("solidWasteRmId", solidWasteRmId).getSingleResult();
	}

	@Override
	public List<SolidWasteRateSlab> getSolidWasteRateSlabGreaterThanUpdateSlabNo(int solidWasteSlabNo, int solidWasteRmId) {
		return entityManager.createNamedQuery("SolidWasteRateSlab.getGasRateSlabGreaterThanUpdateSlabNo",SolidWasteRateSlab.class).setParameter("solidWasteSlabNo", solidWasteSlabNo).setParameter("solidWasteRmId", solidWasteRmId).getResultList();
	}

}
