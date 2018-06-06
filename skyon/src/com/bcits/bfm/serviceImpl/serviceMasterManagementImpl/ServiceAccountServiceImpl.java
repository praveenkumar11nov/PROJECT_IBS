package com.bcits.bfm.serviceImpl.serviceMasterManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ServiceAccountEntity;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.service.serviceMasterManagement.ServiceAccountService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ServiceAccountServiceImpl extends GenericServiceImpl<ServiceAccountEntity> implements ServiceAccountService  {

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ServiceAccountEntity> findALL() {
		return entityManager.createNamedQuery("ServiceAccountEntity.findAll").getResultList();
		//return getAllDetailsList(entityManager.createNamedQuery("ServiceParametersEntity.findAll").getResultList());
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ServiceAccountEntity> findAllById(int serviceMasterId) {
		return selectedList(entityManager.createNamedQuery("ServiceAccountEntity.findAllById",ServiceAccountEntity.class).setParameter("serviceMasterId", serviceMasterId).getResultList());
	}
	
	private List<ServiceAccountEntity> selectedList(List<ServiceAccountEntity> list) 
	{
		List<ServiceAccountEntity> listNew = new ArrayList<ServiceAccountEntity>();
		for (Iterator<ServiceAccountEntity> iterator = list.iterator(); iterator.hasNext();) {
			ServiceAccountEntity serviceAccountEntity = (ServiceAccountEntity) iterator.next();
			serviceAccountEntity.setServiceMastersEntity(null);
			serviceAccountEntity.setAccount(null);
			listNew.add(serviceAccountEntity);
		}
		return listNew;
	}
	
	@Override
	public void updateAccountStatusFromInnerGrid(int serviceAccoutId, HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();
			
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("status");

			ServiceAccountEntity serviceAccountEntity = find(serviceAccoutId);
			
			if(serviceAccountEntity.getStatus().equalsIgnoreCase("Active"))
			{
				entityManager.createNamedQuery("ServiceAccountEntity.updateAccountStatusFromInnerGrid").setParameter("status", "Inactive").setParameter("serviceAccoutId", serviceAccoutId).executeUpdate();
				out.write("Account is activated");
			}
			else
			{
				entityManager.createNamedQuery("ServiceAccountEntity.updateAccountStatusFromInnerGrid").setParameter("status", "Active").setParameter("serviceAccoutId", serviceAccoutId).executeUpdate();
				out.write("Account is de-activated");
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public ServiceMastersEntity getServiceAccount(int serviceMasterId) {
		return entityManager.createNamedQuery("ServiceAccountEntity.getServiceAccount",ServiceMastersEntity.class).setParameter("serviceMasterId", serviceMasterId).getSingleResult();
	}
	
	@Override
	public void ledgerEndDateUpdate(int serviceAccoutId,HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
			
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("ledgerEndDate");

			ServiceAccountEntity serviceAccountEntity = find(serviceAccoutId);
			
			if(serviceAccountEntity.getLedgerEndDate()==null || serviceAccountEntity.getLedgerEndDate().equals(""))
			{
				entityManager.createNamedQuery("ServiceAccountEntity.ledgerEndDateUpdate").setParameter("ledgerEndDate", new java.util.Date()).setParameter("serviceAccoutId", serviceAccoutId).executeUpdate();
				out.write("Ledger Ended");
			}else {
				out.write("Ledger Already Ended");
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
}
