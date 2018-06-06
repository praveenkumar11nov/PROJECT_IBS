package com.bcits.bfm.serviceImpl.broadTeleTariffManagemntImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.BroadTeleRateMaster;
import com.bcits.bfm.service.broadTeleTariffManagment.BroadTeleRateMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class BroadTeleRateMasterServicImpl extends GenericServiceImpl<BroadTeleRateMaster> implements BroadTeleRateMasterService
{

	@Override
	public List<BroadTeleRateMaster> findActive() 
	{
		String status = "Active";
		return entityManager.createNamedQuery("BroadTeleRateMaster.findActive",BroadTeleRateMaster.class).setParameter("status", status).getResultList();
	}

	@Override
	public List<Map<String, Object>> setResponse( List<BroadTeleRateMaster> broadTeleRateMastersList) {
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();;
		  for (final BroadTeleRateMaster broadTeleRateMaster : broadTeleRateMastersList) 
		  {
			               final String broadTeleTariffName = (String) entityManager.createNamedQuery("BroadTeleRateMaster.getBroadTeleTariffNameByID").setParameter("broadTeleTariffId", broadTeleRateMaster.getBroadTeleTariffId()).getSingleResult();
			               broadTeleRateMaster.setBroadTeleTariffName(broadTeleTariffName);
				           result.add(new HashMap<String, Object>() 
				            {
							private static final long serialVersionUID = 1L;
							{
							 put("broadTeleTariffName",broadTeleRateMaster.getBroadTeleTariffName());
				             put("broadTeleRmid", broadTeleRateMaster.getBroadTeleRmid());
				             put("broadTeleTariffId",broadTeleRateMaster.getBroadTeleTariffId());
				             put("broadTeleRateName", broadTeleRateMaster.getBroadTeleRateName());
				             put("broadTeleRateDescription",broadTeleRateMaster.getBroadTeleRateDescription());
				             put("broadTeleRateType", broadTeleRateMaster.getBroadTeleRateType());
				             put("broadTeleValidFrom", broadTeleRateMaster.getBroadTeleValidFrom());             
				             put("broadTeleValidTo", broadTeleRateMaster.getBroadTeleValidTo());
				            // put("wtValidFrom", new SimpleDateFormat("dd/MM/yyyy").format(wtRateMaster.getWtValidFrom()));             
				           //  put("wtValidTo", new SimpleDateFormat("dd/MM/yyyy").format(wtRateMaster.getWtValidTo()));
				             put("broadTeleRateUOM", broadTeleRateMaster.getBroadTeleRateUOM());
				             put("broadTeleMinRate", broadTeleRateMaster.getBroadTeleMinRate());
				             put("broadTeleMaxRate", broadTeleRateMaster.getBroadTeleMaxRate()); 
				             put("status", broadTeleRateMaster.getStatus());
				             put("createdBy", broadTeleRateMaster.getCreatedBy());
				             put("lastUpdatedBy", broadTeleRateMaster.getLastUpdatedBy());
				             put("lastUpdatedDT", broadTeleRateMaster.getLastUpdatedDT());
				            }});
		  }
		return result;
	}

	@Override
	public List<BroadTeleRateMaster> findALL() {
		return entityManager.createNamedQuery("BroadTeleRateMaster.findAll",BroadTeleRateMaster.class).getResultList();
	}

	@Override
	public void setBroadTeleRateMasterStatus(int broadTeleRmid, String operation,HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("BroadTeleRateMaster.setBroadTeleRateMasterStatus").setParameter("status", "Active").setParameter("broadTeleRmid", broadTeleRmid).executeUpdate();
				out.write("Rate Master active");
			}
			else
			{
				entityManager.createNamedQuery("BroadTeleRateMaster.setBroadTeleRateMasterStatus").setParameter("status", "Inactive").setParameter("broadTeleRmid", broadTeleRmid).executeUpdate();
				out.write("Rate Master inactive");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void broadTeleRateMasterEndDate(int broadTeleRmid, HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("gasValidTo");
			BroadTeleRateMaster broadTeleRateMaster = find(broadTeleRmid);
			if(broadTeleRateMaster.getBroadTeleValidTo() == null || broadTeleRateMaster.getBroadTeleValidTo().equals(""))
			{
				entityManager.createNamedQuery("GasRateMaster.serviceEndDateUpdate").setParameter("gasValidTo", new java.util.Date()).setParameter("broadTeleRmid", broadTeleRmid).executeUpdate();
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
