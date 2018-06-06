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
import com.bcits.bfm.service.solidWasteTariffManagment.SolidWasteRateMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class SolidWasteRateMasterServicImpl extends GenericServiceImpl<SolidWasteRateMaster> implements SolidWasteRateMasterService
{

	@Override
	public List<SolidWasteRateMaster> findActive() {
		String status = "Active";
		return entityManager.createNamedQuery("SolidWasteRateMaster.findActive",SolidWasteRateMaster.class).setParameter("status", status).getResultList();
	}

	@Override
	public List<Map<String, Object>> setResponse(List<SolidWasteRateMaster> solidWasteRateMastersList) {

		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();;
		  for (final SolidWasteRateMaster solidWasteRateMaster : solidWasteRateMastersList) 
		  {
			               final String solidWasteTariffName = (String) entityManager.createNamedQuery("SolidWasteRateMaster.getWaterTariffNameByID").setParameter("solidWasteTariffId", solidWasteRateMaster.getSolidWasteTariffId()).getSingleResult();
			               solidWasteRateMaster.setSolidWasteTariffName(solidWasteTariffName);
				           result.add(new HashMap<String, Object>() 
				            {
							private static final long serialVersionUID = 1L;
							{
							 put("solidWasteTariffName",solidWasteRateMaster.getSolidWasteTariffName());
				             put("solidWasteRmId", solidWasteRateMaster.getSolidWasteRmId());
				             put("solidWasteTariffId",solidWasteRateMaster.getSolidWasteTariffId());
				             put("solidWasteRateName", solidWasteRateMaster.getSolidWasteRateName());
				             put("solidWasteRateDescription",solidWasteRateMaster.getSolidWasteRateDescription());
				             put("solidWasteRateType", solidWasteRateMaster.getSolidWasteRateType());
				             put("solidWasteValidFrom", solidWasteRateMaster.getSolidWasteValidFrom());             
				             put("solidWasteValidTo", solidWasteRateMaster.getSolidWasteValidTo());
				            // put("wtValidFrom", new SimpleDateFormat("dd/MM/yyyy").format(wtRateMaster.getWtValidFrom()));             
				           //  put("wtValidTo", new SimpleDateFormat("dd/MM/yyyy").format(wtRateMaster.getWtValidTo()));
				             put("solidWasteRateUOM", solidWasteRateMaster.getSolidWasteRateUOM());
				             put("solidWasteMinRate", solidWasteRateMaster.getSolidWasteMinRate());
				             put("solidWasteMaxRate", solidWasteRateMaster.getSolidWasteMaxRate()); 
				             put("status", solidWasteRateMaster.getStatus());
				             put("createdBy", solidWasteRateMaster.getCreatedBy());
				             put("lastUpdatedBy", solidWasteRateMaster.getLastUpdatedBy());
				             put("lastUpdatedDT", solidWasteRateMaster.getLastUpdatedDT());
				            }});
		  }
		return result;
		}

	@Override
	public List<SolidWasteRateMaster> findALL() {
		return entityManager.createNamedQuery("SolidWasteRateMaster.findAll",SolidWasteRateMaster.class).getResultList();
	}

	@Override
	public void setSolidWasteRateMasterStatus(int solidWastermid,String operation, HttpServletResponse response) {

		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("SolidWasteRateMaster.setWaterRateMasterStatus").setParameter("status", "Active").setParameter("solidWasteRmId", solidWastermid).executeUpdate();
				out.write("Rate Master active");
			}
			else
			{
				entityManager.createNamedQuery("SolidWasteRateMaster.setWaterRateMasterStatus").setParameter("status", "Inactive").setParameter("solidWasteRmId", solidWastermid).executeUpdate();
				out.write("Rate Master inactive");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	
		
	}

	@Override
	public void solidWasteRateMasterEndDate(int solidWastermid,HttpServletResponse response) {

		try
		{
			PrintWriter out = response.getWriter();
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("solidWasteValidTo");
			SolidWasteRateMaster solidWasteRateMaster = find(solidWastermid);
			if(solidWasteRateMaster.getSolidWasteValidTo()== null || solidWasteRateMaster.getSolidWasteValidTo().equals(""))
			{
				entityManager.createNamedQuery("SolidWasteRateMaster.serviceEndDateUpdate").setParameter("solidWasteValidTo", new java.util.Date()).setParameter("solidWasteRmId", solidWastermid).executeUpdate();
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
