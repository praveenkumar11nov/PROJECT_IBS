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
import com.bcits.bfm.service.waterTariffManagement.WTRateMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class WTRateMasterServicImpl extends GenericServiceImpl<WTRateMaster> implements WTRateMasterService
{

	@Override
	public List<WTRateMaster> findALL()
	{
		return entityManager.createNamedQuery("WTRateMaster.findAll",WTRateMaster.class).getResultList();
	}
	
	@Override
	public List<WTRateMaster> findActive()
	{
		String status = "Active";
		return entityManager.createNamedQuery("WTRateMaster.findActive",WTRateMaster.class).setParameter("wtRateMasterStatus", status).getResultList();
	}

	@Override
	public List<Map<String, Object>> setResponse(List<WTRateMaster> wtRateMastersList)
	{
	     List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();;
		  for (final WTRateMaster wtRateMaster : wtRateMastersList) 
		  {
			               final String wtTariffName = (String) entityManager.createNamedQuery("WTRateMaster.getWaterTariffNameByID").setParameter("wtTariffId", wtRateMaster.getWtTariffId()).getSingleResult();
			               wtRateMaster.setWtTariffName(wtTariffName);
				           result.add(new HashMap<String, Object>() 
				            {
							private static final long serialVersionUID = 1L;
							{
							 put("wtTariffName",wtRateMaster.getWtTariffName());
				             put("wtrmid", wtRateMaster.getWtrmid());
				             put("wtTariffId",wtRateMaster.getWtTariffId());
				             put("wtRateName", wtRateMaster.getWtRateName());
				             put("wtRateDescription",wtRateMaster.getWtRateDescription());
				             put("wtRateType", wtRateMaster.getWtRateType());
				             put("wtValidFrom", wtRateMaster.getWtValidFrom());             
				             put("wtValidTo", wtRateMaster.getWtValidTo());
				            // put("wtValidFrom", new SimpleDateFormat("dd/MM/yyyy").format(wtRateMaster.getWtValidFrom()));             
				           //  put("wtValidTo", new SimpleDateFormat("dd/MM/yyyy").format(wtRateMaster.getWtValidTo()));
				             put("wtRateUOM", wtRateMaster.getWtRateUOM());
				             put("wtMinRate", wtRateMaster.getWtMinRate());
				             put("wtMaxRate", wtRateMaster.getWtMaxRate()); 
				             put("wtRateMasterStatus", wtRateMaster.getWtRateMasterStatus());
				             put("wtCreatedBy", wtRateMaster.getWtCreatedBy());
				             put("wtLastUpdatedBy", wtRateMaster.getWtLastUpdatedBy());
				             put("wtLastUpdatedDT", wtRateMaster.getWtLastUpdatedDT());
				            }});
		  }
		return result;
	}

	@Override
	public void setWaterRateMasterStatus(int wtrmid, String operation, HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("WTRateMaster.setWaterRateMasterStatus").setParameter("wtRateMasterStatus", "Active").setParameter("wtrmid", wtrmid).executeUpdate();
				out.write("Rate Master active");
			}
			else
			{
				entityManager.createNamedQuery("WTRateMaster.setWaterRateMasterStatus").setParameter("wtRateMasterStatus", "Inactive").setParameter("wtrmid", wtrmid).executeUpdate();
				out.write("Rate Master inactive");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override 
	public WTRateMaster getStatusofWTRateMaster(int wtrmid) {
		return  entityManager.createNamedQuery("WTRateMaster.findID",WTRateMaster.class).setParameter("wtrmid", wtrmid).getSingleResult();
	}

}
