package com.bcits.bfm.serviceImpl.broadTeleTariffManagemntImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Repository;
import com.bcits.bfm.model.BroadTeleRateSlab;
import com.bcits.bfm.service.broadTeleTariffManagment.BroadTeleRateSlabService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class BroadTeleRateSlabServiceImpl extends GenericServiceImpl<BroadTeleRateSlab> implements BroadTeleRateSlabService
{

	@Override
	public List<BroadTeleRateSlab> rateSlabListByParentID(int broadTeleRmid) {
		return entityManager.createNamedQuery("BroadTeleRateSlab.rateSlabListByParentID",BroadTeleRateSlab.class).setParameter("broadTelrmId", broadTeleRmid).getResultList();
	}

	@Override
	public Object setResponse(List<BroadTeleRateSlab> broadTeleRateSlabsList) {
		  List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		  for (final BroadTeleRateSlab broadTeleRateSlab : broadTeleRateSlabsList) 
		  {
				           result.add(new HashMap<String, Object>()
				            {
								private static final long serialVersionUID = 1L;

							{
							 if((broadTeleRateSlab.getBroadTelSlabFrom()) == null)
							 {
								 put("broadTelrmId",broadTeleRateSlab.getBroadTelrmId());
								 put("broadTelersId",broadTeleRateSlab.getBroadTelersId());
								 put("broadTelSlabNo",broadTeleRateSlab.getBroadTelSlabNo());
					             put("broadTelSlabType", broadTeleRateSlab.getBroadTelSlabType());
					             put("broadTelSlabRateType",broadTeleRateSlab.getBroadTelSlabRateType());
					             put("broadTelRate", broadTeleRateSlab.getBroadTelRate());
					             put("status", broadTeleRateSlab.getStatus());
					             put("createdBy", broadTeleRateSlab.getCreatedBy());
					             put("lastUpdatedBy", broadTeleRateSlab.getLastUpdatedBy());
					             put("lastUpdatedDT", broadTeleRateSlab.getLastUpdatedDT());
								 put("broadTelDummySlabFrom", "NA");
								 put("broadTelDummySlabTo", "NA");
							 }
							 else
							{
								 put("broadTelrmId",broadTeleRateSlab.getBroadTelrmId());
								 put("broadTelersId",broadTeleRateSlab.getBroadTelersId());
								 put("broadTelSlabNo",broadTeleRateSlab.getBroadTelSlabNo());
					             put("broadTelSlabType", broadTeleRateSlab.getBroadTelSlabType());
					             put("broadTelSlabFrom",broadTeleRateSlab.getBroadTelSlabFrom());
					             put("broadTelSlabTo", broadTeleRateSlab.getBroadTelSlabTo());
					             if(broadTeleRateSlab.getBroadTelSlabTo() == 999999)
					             {
					            	 put("broadTelDummySlabTo", "Max");
					            	 put("broadTelDummySlabFrom", broadTeleRateSlab.getBroadTelSlabFrom());
					             }
					             else
					             {
					            	 put("broadTelDummySlabTo", broadTeleRateSlab.getBroadTelSlabTo());
					            	 put("broadTelDummySlabFrom", broadTeleRateSlab.getBroadTelSlabFrom());
					             }
					             put("broadTelSlabRateType",broadTeleRateSlab.getBroadTelSlabRateType());
					             put("broadTelRate", broadTeleRateSlab.getBroadTelRate());
					             put("status", broadTeleRateSlab.getStatus());
					             put("createdBy", broadTeleRateSlab.getCreatedBy());
					             put("lastUpdatedBy", broadTeleRateSlab.getLastUpdatedBy());
					             put("lastUpdatedDT", broadTeleRateSlab.getLastUpdatedDT());
							}
				            }});
		  }
		return result;
	}

	@Override
	public void updateslabStatus(int broadTelersId, HttpServletResponse response)
	{
		try
		  {
		   PrintWriter out = response.getWriter();
		   List<String> attributesList = new ArrayList<String>();
		   attributesList.add("status");
		   BroadTeleRateSlab broadTeleRateSlab = find(broadTelersId);
		   if(broadTeleRateSlab.getStatus().equalsIgnoreCase("Active"))
		   {
		    entityManager.createNamedQuery("BroadTeleRateSlab.updateRateSlabStatusFromInnerGrid").setParameter("status", "Inactive").setParameter("broadTelersId", broadTelersId).executeUpdate();
		    out.write("Rate Slab In-Active");
		   }
		   else
		   {
		    entityManager.createNamedQuery("BroadTeleRateSlab.updateRateSlabStatusFromInnerGrid").setParameter("status", "Active").setParameter("broadTelersId", broadTelersId).executeUpdate();
		    out.write("Rate Slab Active");
		   }
		  } 
		  catch (IOException e)
		  {
		   e.printStackTrace();
		  }
	}
	/*
	@Override
	public GasRateSlab findRateSlabByPrimayID(int gasrsId) {
		return entityManager.createNamedQuery("GasRateSlab.findRateSlabByPrimayID",GasRateSlab.class).setParameter("gasrsId", gasrsId).getSingleResult();
	}

	@Override
	public List<GasRateSlab> getElRateSlabGreaterThanUpdateSlabNoEq(int gasSlabNo, int gasrmId) {
		return entityManager.createNamedQuery("GasRateSlab.getGasRateSlabGreaterThanUpdateSlabNoEq",GasRateSlab.class).setParameter("gasSlabNo", gasSlabNo).setParameter("gasrmId", gasrmId).getResultList();
	}

	@Override
	public GasRateMaster getStatusofGasRateMaster(int gasrmId) 
	{
		return  entityManager.createNamedQuery("GasRateMaster.findID",GasRateMaster.class).setParameter("gasrmid", gasrmId).getSingleResult();
	}

	@Override
	public List<GasRateSlab> getGasRateSlabGreaterThanUpdateSlabNo(int gasSlabNo, int gasrmId) {
		return entityManager.createNamedQuery("GasRateSlab.getGasRateSlabGreaterThanUpdateSlabNo",GasRateSlab.class).setParameter("gasSlabNo", gasSlabNo).setParameter("gasrmId", gasrmId).getResultList();
	}*/
}
