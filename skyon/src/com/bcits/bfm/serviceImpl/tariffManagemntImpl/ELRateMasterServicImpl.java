package com.bcits.bfm.serviceImpl.tariffManagemntImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;





import com.bcits.bfm.model.ELRateMaster;
import com.bcits.bfm.service.tariffManagement.ELRateMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ELRateMasterServicImpl extends GenericServiceImpl<ELRateMaster> implements ELRateMasterService
{
	
	static Logger logger = LoggerFactory.getLogger(ELRateMasterServicImpl.class);
	
	@Override
	public List<?> findALL() {
		String status = "Active";
		return entityManager.createNamedQuery("ELRateMaster.findAll").setParameter("status", status).getResultList();
	}
	
	public List<?> findALL1() {
		return entityManager.createNamedQuery("ELRateMaster.findAll1").getResultList();
	}

	private List<ELRateMaster> selectedList(List<ELRateMaster> list) 
	{
		List<ELRateMaster> listNew = new ArrayList<ELRateMaster>();
		for (Iterator<ELRateMaster> iterator = list.iterator(); iterator.hasNext();) {
			ELRateMaster elRateMaster = (ELRateMaster) iterator.next();
			elRateMaster.setElTariffMaster(null);
			listNew.add(elRateMaster);
		}
		return listNew;
	}

	@Override
	public void setRateMasterStatus(int elrmid, String operation,
			HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("ELRateMaster.setELRateMasterStatus").setParameter("status", "Active").setParameter("elrmid", elrmid).executeUpdate();
				out.write("Rate Master active");
			}
			else
			{
				entityManager.createNamedQuery("ELRateMaster.setELRateMasterStatus").setParameter("status", "Inactive").setParameter("elrmid", elrmid).executeUpdate();
				out.write("Rate Master inactive");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<String> getRateDescriptionList() {
		Set<String> set=new HashSet<String>(entityManager.createNamedQuery("ELRateMaster.getRateDescriptionList").getResultList());
		return set;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getRateTypeList() {
		return entityManager.createNamedQuery("ELRateMaster.getRateTypeList").getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<String> getRateRateUOMList() {
		Set<String> set=new HashSet<String>(entityManager.createNamedQuery("ELRateMaster.getRateUOMList").getResultList());
		return set;
	}

	@Override
	public ELRateMaster getStatusofELRateMaster(int elrmid) {
		return (ELRateMaster) entityManager.createNamedQuery("ELRateMaster.findID").setParameter("elrmid", elrmid).getSingleResult();
	}

	@Override
	public java.util.Date getMaxDate(String stateName,String rateName, int elTariffID,String category)
	{
		return (java.util.Date) entityManager.createNamedQuery("ELRateMaster.findMaxDateByRateNameTariffID").setParameter("rateName", rateName).setParameter("elTariffID", elTariffID).setParameter("stateName", stateName).setParameter("category", category).getSingleResult();
	}

	@Override
	public void elRateMasterEndDateUpdate(int rateMasterId,HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();
			
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("rateMasterEndDate");

			ELRateMaster elRateMaster = find(rateMasterId);
			if(elRateMaster.getValidTo()==null || elRateMaster.equals(""))
			{
				entityManager.createNamedQuery("ELRateMaster.rateMasterEndDateUpdate").setParameter("validTo", new java.util.Date()).setParameter("elrmid", rateMasterId).executeUpdate();
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
	public String getTariffMasterByTariffId(int elTariffID) {
		return (String) entityManager.createNamedQuery("getTariffMasterByTariffId").setParameter("elTariffID", elTariffID).getSingleResult();
	}

	@Override
	public void UpdateDgUnit(Integer operationalCost, Integer depriciationCost,
			Integer fuleCost, Integer maintainanceCost, /*Date validTo,Date validfrom,*/ Integer unitConsumed) {
		float totalnoFlat=(float)(long) entityManager.createQuery("SELECT SUM(b.numOfProperties)FROM Blocks b").getSingleResult();
		
		float dgfixedCharge=Math.round((depriciationCost+operationalCost)/totalnoFlat);
		logger.info("::::::dgfixedCharge:::::::::"+dgfixedCharge);
		float dgUnit=((maintainanceCost+fuleCost)/unitConsumed);
		logger.info("::::::dgUnit:::::::::"+dgUnit);		
		entityManager.createQuery("UPDATE ELRateSlabs e SET e.rate='"+dgUnit+"'WHERE e.elrmId IN(SELECT MAX(el.elrmid) From ELRateMaster el WHERE el.rateName='DG')").executeUpdate();
		entityManager.createQuery("UPDATE ELRateSlabs e SET e.rate='"+dgfixedCharge+"'WHERE e.elrmId IN(SELECT MAX(el.elrmid) From ELRateMaster el WHERE el.rateName='DG Fixed Charge')").executeUpdate();
		//rateDescription
		entityManager.createQuery("UPDATE ELRateMaster e SET e.rateDescription='DG Fixed Charge for "+totalnoFlat+" Flat'WHERE  e.elrmid IN(SELECT MAX(el.elrmid) From ELRateMaster el WHERE el.rateName='DG Fixed Charge') ").executeUpdate();
		
	}

	@Override
	public ELRateMaster getRateMasterByRateName(int elTariffID, String rateName)
	{
		try 
		{
		 return	entityManager.createNamedQuery("ELRateMaster.getRateMasterByRateName",ELRateMaster.class).setParameter("elTariffID", elTariffID).setParameter("rateName", rateName).getSingleResult();
			
		} catch (Exception e)
		{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ELRateMaster> findDGUnits() {
		String status = "Active";
		List<ELRateMaster> list = entityManager.createQuery("SELECT e FROM ELRateMaster e WHERE e.status='"+status+"' AND e.rateName='DG' OR e.rateName='DG Fixed Charge' ").getResultList();
		return selectedList(list);
	}

	@Override
	public Integer getRateMasterEC(int elTariffID, String rateName) {
		try 
		{
		 return	entityManager.createNamedQuery("ELRateMaster.getRateMasterEC",Integer.class).setParameter("elTariffID", elTariffID).setParameter("rateName", rateName).setMaxResults(1).getSingleResult();
			
		} catch (NoResultException e)
		{
			return 0;
		}
	}

	@Override
	public int getRateMasterDomesticGeneral(int wtTariffId, String string) {
		try 
		{
		 return	entityManager.createNamedQuery("ELRateMaster.getRateMasterDomesticGeneral",Integer.class).setParameter("wtTariffId", wtTariffId).setParameter("rateName", string).setMaxResults(1).getSingleResult();
			
		} catch (Exception e)
		{
			return 0;
		}
	}

	@Override
	public int getRateMasterGas(int gasTariffId, String string) {
		try 
		{
		 return	entityManager.createNamedQuery("ELRateMaster.getRateMasterGas",Integer.class).setParameter("gasTariffId", gasTariffId).setParameter("rateName", string).setMaxResults(1).getSingleResult();
			
		} catch (Exception e)
		{
			return 0;
		}
	}
	@Override
	public int getRateMasterSW(int swTariffId, String string) {
		try 
		{
		 return	entityManager.createNamedQuery("ELRateMaster.getRateMasterSW",Integer.class).setParameter("swTariffId", swTariffId).setParameter("rateName", string).setMaxResults(1).getSingleResult();
		} catch (Exception e)
		{
			return 0;
		}
	}

	@Override
	public int getRateMasterOT(int othersTariffId, String string) {
		try 
		{
		 return	entityManager.createNamedQuery("ELRateMaster.getRateMasterOT",Integer.class).setParameter("othersTariffId", othersTariffId).setParameter("rateName", string).setMaxResults(1).getSingleResult();
		} catch (Exception e)
		{
			return 0;
		}
	}

	@Override
	public ELRateMaster getRateMasterByRateName(int elTariffID, String rateName,String category1Tariff) {
		try 
		{
		 return	entityManager.createNamedQuery("ELRateMaster.getRateMasterByRateNameHariyan",ELRateMaster.class).setParameter("elTariffID", elTariffID).setParameter("rateName", rateName).setParameter("rateNameCategory", category1Tariff).setMaxResults(1).getSingleResult();
		} catch (Exception e)
		{
			return null;
		}
	}
}
