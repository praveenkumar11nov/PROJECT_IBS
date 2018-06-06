package com.bcits.bfm.serviceImpl.gasTariffManagmentImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Repository;
import com.bcits.bfm.model.GasRateMaster;
import com.bcits.bfm.model.GasRateSlab;
import com.bcits.bfm.service.gasTariffManagment.GasRateSlabService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class GasRateSlabServiceImpl extends GenericServiceImpl<GasRateSlab> implements GasRateSlabService
{

	@Override
	public List<GasRateSlab> rateSlabListByParentID(int gasrmid) {
		return entityManager.createNamedQuery("GasRateSlab.rateSlabListByParentID",GasRateSlab.class).setParameter("gasrmId", gasrmid).getResultList();
	}

	@Override
	public Object setResponse(List<GasRateSlab> gasRateSlabsList) {
		  List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		  for (final GasRateSlab gasRateSlab : gasRateSlabsList) 
		  {
				           result.add(new HashMap<String, Object>()
				            {
								private static final long serialVersionUID = 1L;

							{
							 if((gasRateSlab.getGasSlabFrom()) == null)
							 {
								 put("gasrmId",gasRateSlab.getGasrmId());
								 put("gasrsId",gasRateSlab.getGasrsId());
								 put("gasSlabNo",gasRateSlab.getGasSlabNo());
					             put("gasSlabType", gasRateSlab.getGasSlabType());
					             put("gasSlabRateType",gasRateSlab.getGasSlabRateType());
					             put("gasRate", gasRateSlab.getGasRate());
					             put("status", gasRateSlab.getStatus());
					             put("createdBy", gasRateSlab.getCreatedBy());
					             put("lastUpdatedBy", gasRateSlab.getLastUpdatedBy());
					             put("lastUpdatedDT", gasRateSlab.getLastUpdatedDT());
								 put("gasDummySlabFrom", "NA");
								 put("gasDummySlabTo", "NA");
							 }
							 else
							{
								 put("gasrmId",gasRateSlab.getGasrmId());
								 put("gasrsId",gasRateSlab.getGasrsId());
								 put("gasSlabNo",gasRateSlab.getGasSlabNo());
								 put("gasSlabType", gasRateSlab.getGasSlabType());
					             put("gasSlabFrom",gasRateSlab.getGasSlabFrom());
					             put("gasSlabTo", gasRateSlab.getGasSlabTo());
					             if(gasRateSlab.getGasSlabTo() == 999999)
					             {
					            	 put("gasDummySlabTo", "Max");
					            	 put("gasDummySlabFrom", gasRateSlab.getGasSlabFrom());
					             }
					             else
					             {
					            	 put("gasDummySlabTo", gasRateSlab.getGasSlabTo());
					            	 put("gasDummySlabFrom", gasRateSlab.getGasSlabFrom());
					             }
					             put("gasSlabRateType",gasRateSlab.getGasSlabRateType());
					             put("gasRate", gasRateSlab.getGasRate());
					             put("status", gasRateSlab.getStatus());
					             put("createdBy", gasRateSlab.getCreatedBy());
					             put("lastUpdatedBy", gasRateSlab.getLastUpdatedBy());
					             put("lastUpdatedDT", gasRateSlab.getLastUpdatedDT());
							}
				            }});
		  }
		return result;
	}

	@Override
	public void updateslabStatus(int gasrsId, HttpServletResponse response)
	{
		try
		  {
		   PrintWriter out = response.getWriter();
		   List<String> attributesList = new ArrayList<String>();
		   attributesList.add("status");
		   GasRateSlab gasRateSlab = find(gasrsId);
		   if(gasRateSlab.getStatus().equalsIgnoreCase("Active"))
		   {
		    entityManager.createNamedQuery("GasRateSlab.updateRateSlabStatusFromInnerGrid").setParameter("status", "Inactive").setParameter("gasrsId", gasrsId).executeUpdate();
		    out.write("Rate Slab In-Active");
		   }
		   else
		   {
		    entityManager.createNamedQuery("GasRateSlab.updateRateSlabStatusFromInnerGrid").setParameter("status", "Active").setParameter("gasrsId", gasrsId).executeUpdate();
		    out.write("Rate Slab Active");
		   }
		  } 
		  catch (IOException e)
		  {
		   e.printStackTrace();
		  }
	}

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
	}

}
