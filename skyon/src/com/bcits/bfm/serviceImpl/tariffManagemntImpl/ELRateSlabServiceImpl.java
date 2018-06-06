package com.bcits.bfm.serviceImpl.tariffManagemntImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.ELRateSlabs;
import com.bcits.bfm.model.GasRateSlab;
import com.bcits.bfm.model.SolidWasteRateSlab;
import com.bcits.bfm.model.WTRateSlabs;
import com.bcits.bfm.service.tariffManagement.ELRateSlabService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ELRateSlabServiceImpl extends GenericServiceImpl<ELRateSlabs> implements ELRateSlabService
{

	@Override
	@SuppressWarnings("unchecked")
	public List<ELRateSlabs> findALL() {
		return entityManager.createNamedQuery("ELRateSlabs.findAll").getResultList();
	}

	@Override
	public List<ELRateSlabs> findRateSlabByID(int elrmId) {
		List<ELRateSlabs> list = entityManager.createNamedQuery("ELRateSlabs.findRateSlabByID",ELRateSlabs.class).setParameter("elrmId", elrmId).getResultList();
		return selectedList(list);
	}
	
	private List<ELRateSlabs> selectedList(List<ELRateSlabs> list) 
	{
		List<ELRateSlabs> listNew = new ArrayList<ELRateSlabs>();
		for (Iterator<ELRateSlabs> iterator = list.iterator(); iterator.hasNext();) {
			ELRateSlabs elRateSlabs = (ELRateSlabs) iterator.next();
			elRateSlabs.setElRateMaster(null);
			listNew.add(elRateSlabs);
		}
		return listNew;
	}

	@Override
	public float getMaxSlabTo(int elrmId)
	{
		try{
			return (float) entityManager.createNamedQuery("ELRateSlabs.getMaxSlabTo").setParameter("elrmId", elrmId).getSingleResult();
		}
		catch(NullPointerException e)
		{
			return 0;
		}
		
		catch(Exception e)
		{
			return 0;
		}
	}

	/*@Override
	public List<ELRateSlabs> findAllGreaterThanId(int elrsId) {
		return entityManager.createNamedQuery("ELRateSlabs.findAllGreaterThanId",ELRateSlabs.class).setParameter("elrsId", elrsId).getResultList();
	}*/

	@Override
	public ELRateSlabs findRateSlabByPrimayID(int elrsId)
	{
		System.out.println("@@@@@@@@elrsId:::::::::::::"+elrsId);
		return (ELRateSlabs) entityManager.createNamedQuery("ELRateSlabs.findRateSlabByPrimayID").setParameter("elrsId", elrsId).getSingleResult();
	}

	@Override
	public void updateslabStatus(int elrsId, HttpServletResponse response) 
	{
		  try
		  {
		   PrintWriter out = response.getWriter();
		   List<String> attributesList = new ArrayList<String>();
		   attributesList.add("status");
		   ELRateSlabs elRateSlabs = find(elrsId);
		   if(elRateSlabs.getStatus().equalsIgnoreCase("Active"))
		   {
		    entityManager.createNamedQuery("ELRateSlabs.updateRateSlabStatusFromInnerGrid").setParameter("status", "Inactive").setParameter("elrsId", elrsId).executeUpdate();
		    out.write("Rate Slab In-Active");
		   }
		   else
		   {
		    entityManager.createNamedQuery("ELRateSlabs.updateRateSlabStatusFromInnerGrid").setParameter("status", "Active").setParameter("elrsId", elrsId).executeUpdate();
		    out.write("Rate Slab Active");
		   }
		  } 
		  catch (IOException e)
		  {
		   e.printStackTrace();
		  }
	}

	@Override
	public int getMaxSlabNumber(int elrmId) 
	{
		try
		{
		return (int) entityManager.createNamedQuery("ELRateSlabs.getMaxSlabNumber").setParameter("elrmId", elrmId).getSingleResult();
		}
		catch(NullPointerException exception)
		{
			return 0;
		}
		catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List<ELRateSlabs> getElRateSlabGreaterThanUpdateSlabNo(int slabsNo, int elrmId) {
		return entityManager.createNamedQuery("ELRateSlabs.getElRateSlabGreaterThanUpdateSlabNo",ELRateSlabs.class).setParameter("slabsNo", slabsNo).setParameter("elrmId", elrmId).getResultList();
	}

	@Override
	public List<ELRateSlabs> getElRateSlabGreaterThanUpdateSlabNoEq(int slabsNo, int elrmId) {
		return entityManager.createNamedQuery("ELRateSlabs.getElRateSlabGreaterThanUpdateSlabNoEq",ELRateSlabs.class).setParameter("slabsNo", slabsNo).setParameter("elrmId", elrmId).getResultList();
	}

	@Override
	public ELRateSlabs findIdAndParentId(int slabsNo, int elrmid) {
		try {
			return entityManager.createNamedQuery("ELRateSlabs.findIdAndParentId",ELRateSlabs.class).setParameter("slabsNo", slabsNo).setParameter("elrmid", elrmid).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}

	@Override
	public List<ELRateSlabs> findAllLessThanId(int slabsNo, int elrmId) {
		return entityManager.createNamedQuery("ELRateSlabs.findAllLessThanId",ELRateSlabs.class).setParameter("slabsNo", slabsNo).setParameter("elrmId", elrmId).getResultList();
	}

	@Override
	public List<ELRateSlabs> findAllGreaterThanId(int slabsNo, int elrmId) {
		return entityManager.createNamedQuery("ELRateSlabs.findAllGreaterThanId",ELRateSlabs.class).setParameter("slabsNo", slabsNo).setParameter("elrmId", elrmId).getResultList();
	}

	@Override
	public List<ELRateSlabs> getRateSlabsForRangeSlab(int elrmid,float sancationLoad) {
	return entityManager.createNamedQuery("ELRateSlabs.getELRateSlabBetween",ELRateSlabs.class).setParameter("elrmId", elrmid).setParameter("demandCharges", sancationLoad).getResultList();

	}

	@Override
	public List<ELRateSlabs> getRateSlabByRateMasterId(Integer rateMasterId) {
		return entityManager.createNamedQuery("ELRateSlabs.getRateSlabByRateMasterId",ELRateSlabs.class).setParameter("elrmId", rateMasterId).getResultList();
	}

	@Override
	public List<WTRateSlabs> getWtRateSlabByRateMasterId(int rateMasterID) {
		 return entityManager.createNamedQuery("ELRateSlabs.getWtRateSlabByRateMasterId",WTRateSlabs.class).setParameter("wtrmId", rateMasterID).getResultList();
	}

	@Override
	public List<GasRateSlab> getGasRateSlabByRateMasterId(int rateMasterID) {
		return entityManager.createNamedQuery("ELRateSlabs.getGasRateSlabByRateMasterId",GasRateSlab.class).setParameter("gasrmId", rateMasterID).getResultList();
	}

	@Override
	public List<SolidWasteRateSlab> getSWRateSlabByRateMasterId(int rateMasterID) {
		return entityManager.createNamedQuery("ELRateSlabs.getSWRateSlabByRateMasterId",SolidWasteRateSlab.class).setParameter("swrmId", rateMasterID).getResultList();
	}
	
}
