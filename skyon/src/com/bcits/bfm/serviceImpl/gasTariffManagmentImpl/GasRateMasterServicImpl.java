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
import com.bcits.bfm.service.gasTariffManagment.GasRateMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class GasRateMasterServicImpl extends GenericServiceImpl<GasRateMaster> implements GasRateMasterService
{

	@Override
	public List<GasRateMaster> findActive() 
	{
		String status = "Active";
		return entityManager.createNamedQuery("GasRateMaster.findActive",GasRateMaster.class).setParameter("status", status).getResultList();
	}

	@Override
	public List<Map<String, Object>> setResponse( List<GasRateMaster> gasRateMastersList) {
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();;
		  for (final GasRateMaster gasRateMaster : gasRateMastersList) 
		  {
			               final String gasTariffName = (String) entityManager.createNamedQuery("GasRateMaster.getWaterTariffNameByID").setParameter("gasTariffId", gasRateMaster.getGasTariffId()).getSingleResult();
			               gasRateMaster.setGasTariffName(gasTariffName);
				           result.add(new HashMap<String, Object>() 
				            {
							private static final long serialVersionUID = 1L;
							{
							 put("gasTariffName",gasRateMaster.getGasTariffName());
				             put("gasrmid", gasRateMaster.getGasrmid());
				             put("gasTariffId",gasRateMaster.getGasTariffId());
				             put("gasRateName", gasRateMaster.getGasRateName());
				             put("gasRateDescription",gasRateMaster.getGasRateDescription());
				             put("gasRateType", gasRateMaster.getGasRateType());
				             put("gasValidFrom", gasRateMaster.getGasValidFrom());             
				             put("gasValidTo", gasRateMaster.getGasValidTo());
				            // put("wtValidFrom", new SimpleDateFormat("dd/MM/yyyy").format(wtRateMaster.getWtValidFrom()));             
				           //  put("wtValidTo", new SimpleDateFormat("dd/MM/yyyy").format(wtRateMaster.getWtValidTo()));
				             put("gasRateUOM", gasRateMaster.getGasRateUOM());
				             put("gasMinRate", gasRateMaster.getGasMinRate());
				             put("gasMaxRate", gasRateMaster.getGasMaxRate()); 
				             put("status", gasRateMaster.getStatus());
				             put("createdBy", gasRateMaster.getCreatedBy());
				             put("lastUpdatedBy", gasRateMaster.getLastUpdatedBy());
				             put("lastUpdatedDT", gasRateMaster.getLastUpdatedDT());
				            }});
		  }
		return result;
	}

	@Override
	public List<GasRateMaster> findALL() {
		return entityManager.createNamedQuery("GasRateMaster.findAll",GasRateMaster.class).getResultList();
	}

	@Override
	public void setGasRateMasterStatus(int gasrmid, String operation,HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("GasRateMaster.setWaterRateMasterStatus").setParameter("status", "Active").setParameter("gasrmid", gasrmid).executeUpdate();
				out.write("Rate Master active");
			}
			else
			{
				entityManager.createNamedQuery("GasRateMaster.setWaterRateMasterStatus").setParameter("status", "Inactive").setParameter("gasrmid", gasrmid).executeUpdate();
				out.write("Rate Master inactive");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void gasRateMasterEndDate(int gasrmid, HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("gasValidTo");
			GasRateMaster gasRateMaster = find(gasrmid);
			if(gasRateMaster.getGasValidTo()== null || gasRateMaster.getGasValidTo().equals(""))
			{
				entityManager.createNamedQuery("GasRateMaster.serviceEndDateUpdate").setParameter("gasValidTo", new java.util.Date()).setParameter("gasrmid", gasrmid).executeUpdate();
				out.write("Rate Master Ended");
			}
			else
			{
				out.write("Rate Master Already Ended");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
}
