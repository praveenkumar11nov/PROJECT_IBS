package com.bcits.bfm.serviceImpl.tariffManagemntImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Repository;
import com.bcits.bfm.model.ELTodRates;
import com.bcits.bfm.service.tariffManagement.ELTODRatesService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ELTODRatesServiceImpl extends GenericServiceImpl<ELTodRates> implements ELTODRatesService
{

	@Override
	public List<ELTodRates> findALL(int elrsId)
	{
		List<ELTodRates> list = entityManager.createNamedQuery("ELTodRates.findAll",ELTodRates.class).setParameter("elrsId", elrsId).getResultList();
		return selectedList(list);
   }
		private List<ELTodRates> selectedList(List<ELTodRates> list) 
		{
			List<ELTodRates> listNew = new ArrayList<ELTodRates>();
			for (Iterator<ELTodRates> iterator = list.iterator(); iterator.hasNext();) {
				ELTodRates elTodRates = (ELTodRates) iterator.next();
				elTodRates.setElRateSlabs(null);;
				listNew.add(elTodRates);
			}
			return listNew;
		}

		@Override
		public void updateslabStatus(int eltiId, HttpServletResponse response) {
			
			 try
			  {
			   PrintWriter out = response.getWriter();
			   List<String> attributesList = new ArrayList<String>();
			   attributesList.add("status");
			   ELTodRates elTodRates = find(eltiId);
			   if(elTodRates.getStatus().equalsIgnoreCase("Active"))
			   {
			    entityManager.createNamedQuery("ELTodRates.updateTODRatesStatusFromInnerGrid").setParameter("status", "Inactive").setParameter("eltiId", eltiId).executeUpdate();
			    out.write("TOD Rate In-Active");
			   }
			   else
			   {
			    entityManager.createNamedQuery("ELTodRates.updateTODRatesStatusFromInnerGrid").setParameter("status", "Active").setParameter("eltiId", eltiId).executeUpdate();
			    out.write("TOD Rate Active");
			   }
			  } 
			  catch (IOException e)
			  {
			   e.printStackTrace();
			  }
		}

		@Override
		public Date getRateSlabs(int elrsId) {
			return  (Date) entityManager.createNamedQuery("ELTodRates.getRateSlab").setParameter("elrsId", elrsId).getSingleResult();
		}

		@Override
		public Timestamp getmaxTimeStamp(int elrsId) {
			return  (Timestamp) entityManager.createNamedQuery("ELTodRates.getmaxTimeStamp").setParameter("elrsId", elrsId).getSingleResult();
		}

		@Override
		public int getMaxIncrementalRate(int elrsId) 
		{
			try
			{
				return  (int) entityManager.createNamedQuery("ELTodRates.getMaxIncrementalRate").setParameter("elrsId", elrsId).getSingleResult();
			}
			catch(Exception exception)
			{
				return 0;
			}
			
		}

	}

