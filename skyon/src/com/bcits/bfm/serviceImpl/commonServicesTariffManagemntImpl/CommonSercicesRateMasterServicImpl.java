package com.bcits.bfm.serviceImpl.commonServicesTariffManagemntImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.CommonServicesRateMaster;
import com.bcits.bfm.service.commonServicesTariffManagement.CommonServicesRateMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
@Repository
public class CommonSercicesRateMasterServicImpl extends GenericServiceImpl<CommonServicesRateMaster> implements CommonServicesRateMasterService
{

	@Override
	public List<CommonServicesRateMaster> findActive() {
		String status = "Active";
		return entityManager.createNamedQuery("CommonServicesRateMaster.findActive",CommonServicesRateMaster.class).setParameter("status", status).getResultList();
	}

	@Override
	public List<?> setResponse(List<CommonServicesRateMaster> commonServicesRateMastersList) {


		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();;
		  for (final CommonServicesRateMaster commonServicesRateMaster : commonServicesRateMastersList) 
		  {
			               final String solidWasteTariffName = (String) entityManager.createNamedQuery("CommonServicesRateMaster.getWaterTariffNameByID").setParameter("csTariffId", commonServicesRateMaster.getCsTariffId()).getSingleResult();
			               commonServicesRateMaster.setCsTariffName(solidWasteTariffName);
				           result.add(new HashMap<String, Object>() 
				            {
							private static final long serialVersionUID = 1L;
							{
							 put("csTariffName",commonServicesRateMaster.getCsTariffName());
				             put("csRmId", commonServicesRateMaster.getCsRmId());
				             put("csTariffId",commonServicesRateMaster.getCsTariffId());
				             put("csRateName", commonServicesRateMaster.getCsRateName());
				             put("csRateDescription",commonServicesRateMaster.getCsRateDescription());
				             put("csRateType", commonServicesRateMaster.getCsRateType());
				             put("csValidFrom", commonServicesRateMaster.getCsValidFrom());             
				             put("csValidTo", commonServicesRateMaster.getCsValidTo());
				            // put("wtValidFrom", new SimpleDateFormat("dd/MM/yyyy").format(wtRateMaster.getWtValidFrom()));             
				           //  put("wtValidTo", new SimpleDateFormat("dd/MM/yyyy").format(wtRateMaster.getWtValidTo()));
				             put("csRateUOM", commonServicesRateMaster.getCsRateUOM());
				             put("csMinRate", commonServicesRateMaster.getCsMinRate());
				             put("csMaxRate", commonServicesRateMaster.getCsMaxRate());
				             put("csTodType", commonServicesRateMaster.getCsTodType());
				             put("status", commonServicesRateMaster.getStatus());
				             put("createdBy", commonServicesRateMaster.getCreatedBy());
				             put("lastUpdatedBy", commonServicesRateMaster.getLastUpdatedBy());
				             put("lastUpdatedDT", commonServicesRateMaster.getLastUpdatedDT());
				            }});
		  }
		return result;
		
	}

	@Override
	public List<CommonServicesRateMaster> findALL() {
		return entityManager.createNamedQuery("CommonServicesRateMaster.findAll",CommonServicesRateMaster.class).getResultList();
	}

	@Override
	public void commonServicesRateMasterEndDate(int csRmId,HttpServletResponse response) {


		try
		{
			PrintWriter out = response.getWriter();
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("solidWasteValidTo");
			CommonServicesRateMaster commonServicesRateMaster = find(csRmId);
			if(commonServicesRateMaster.getCsValidTo()== null || commonServicesRateMaster.getCsValidTo().equals(""))
			{
				entityManager.createNamedQuery("CommonServicesRateMaster.serviceEndDateUpdate").setParameter("csValidTo", new java.util.Date()).setParameter("csRmId", csRmId).executeUpdate();
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

	@Override
	public void setCommonServicesRateMasterStatus(int csRmId, String operation,HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("CommonServicesRateMaster.setWaterRateMasterStatus").setParameter("status", "Active").setParameter("csRmId", csRmId).executeUpdate();
				out.write("Rate Master active");
			}
			else
			{
				entityManager.createNamedQuery("CommonServicesRateMaster.setWaterRateMasterStatus").setParameter("status", "Inactive").setParameter("csRmId", csRmId).executeUpdate();
				out.write("Rate Master inactive");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public CommonServicesRateMaster getTaxCommonServiceMaster(String csRateName) {
		return entityManager.createNamedQuery("CommonServicesRateMaster.getTaxCommonServiceMaster",CommonServicesRateMaster.class).setParameter("csRateName", csRateName).getSingleResult();
	}

}
