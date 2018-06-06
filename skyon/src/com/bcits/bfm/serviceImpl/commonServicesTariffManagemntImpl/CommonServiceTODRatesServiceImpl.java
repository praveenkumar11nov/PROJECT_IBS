package com.bcits.bfm.serviceImpl.commonServicesTariffManagemntImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Repository;
import com.bcits.bfm.model.CommonServicesTodRates;
import com.bcits.bfm.service.commonServicesTariffManagement.CommonServiceTODRatesService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class CommonServiceTODRatesServiceImpl extends GenericServiceImpl<CommonServicesTodRates> implements CommonServiceTODRatesService
{
	@Override
	public List<CommonServicesTodRates> findALL(int csRsId) {
		List<CommonServicesTodRates> list = entityManager.createNamedQuery("CommonServicesTodRates.findAll",CommonServicesTodRates.class).setParameter("csRsId", csRsId).getResultList();
		return selectedList(list);
	}
	private List<CommonServicesTodRates> selectedList(List<CommonServicesTodRates> list) 
	{
		List<CommonServicesTodRates> listNew = new ArrayList<CommonServicesTodRates>();
		for (Iterator<CommonServicesTodRates> iterator = list.iterator(); iterator.hasNext();) {
			CommonServicesTodRates elTodRates = (CommonServicesTodRates) iterator.next();
			elTodRates.setCommonServicesRateSlab(null);
			listNew.add(elTodRates);
		}
		return listNew;
	}
	@Override
	public void updateslabStatus(int cstiId, HttpServletResponse response)
	{
		 try
		  {
		   PrintWriter out = response.getWriter();
		   List<String> attributesList = new ArrayList<String>();
		   attributesList.add("status");
		   CommonServicesTodRates elTodRates = find(cstiId);
		   if(elTodRates.getStatus().equalsIgnoreCase("Active"))
		   {
		    entityManager.createNamedQuery("CommonServicesTodRates.updateTODRatesStatusFromInnerGrid").setParameter("status", "Inactive").setParameter("cstiId", cstiId).executeUpdate();
		    out.write("TOD Rate In-Active");
		   }
		   else
		   {
		    entityManager.createNamedQuery("CommonServicesTodRates.updateTODRatesStatusFromInnerGrid").setParameter("status", "Active").setParameter("cstiId", cstiId).executeUpdate();
		    out.write("TOD Rate Active");
		   }
		  } 
		  catch (IOException e)
		  {
		   e.printStackTrace();
		  }
	}
}
