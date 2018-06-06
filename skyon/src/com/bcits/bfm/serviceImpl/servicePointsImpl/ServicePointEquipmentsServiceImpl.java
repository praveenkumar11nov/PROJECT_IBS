package com.bcits.bfm.serviceImpl.servicePointsImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ServicePointEquipmentsEntity;
import com.bcits.bfm.service.servicePoints.ServicePointEquipmentsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ServicePointEquipmentsServiceImpl extends GenericServiceImpl<ServicePointEquipmentsEntity> implements ServicePointEquipmentsService  {

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ServicePointEquipmentsEntity> findALL() {
		return entityManager.createNamedQuery("ServicePointEquipmentsEntity.findAll").getResultList();
	}

	@Override
	public void setServicePointStatus(int servicePointId, String operation,
			HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("ServicePointEquipmentsEntity.setServicePointStatus").setParameter("status", "Active").setParameter("servicePointId", servicePointId).executeUpdate();
				out.write("Service point is activated");
			}
			else
			{
				entityManager.createNamedQuery("ServicePointEquipmentsEntity.setServicePointStatus").setParameter("status", "Inactive").setParameter("servicePointId", servicePointId).executeUpdate();
				out.write("Service point is de-activated");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ServicePointEquipmentsEntity> findAllById(int servicePointId) {
		return selectedList(entityManager.createNamedQuery("ServicePointEquipmentsEntity.findAllById",ServicePointEquipmentsEntity.class).setParameter("servicePointId", servicePointId).getResultList());
	}
	
	private List<ServicePointEquipmentsEntity> selectedList(List<ServicePointEquipmentsEntity> list) 
	{
		List<ServicePointEquipmentsEntity> listNew = new ArrayList<ServicePointEquipmentsEntity>();
		for (Iterator<ServicePointEquipmentsEntity> iterator = list.iterator(); iterator.hasNext();) {
			ServicePointEquipmentsEntity spEquipments = (ServicePointEquipmentsEntity) iterator.next();
			spEquipments.setServicePointEntity(null);
			listNew.add(spEquipments);
		}
		return listNew;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<String> getAllEquipmentTypes() {
		return entityManager.createNamedQuery("ServicePointEquipmentsEntity.getAllEquipmentTypes").getResultList();
	}
	
	@Override
	public void updateEquipmentStatusFromInnerGrid(int spEquipmentId, HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();
			
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("status");

			ServicePointEquipmentsEntity equipmentsEntity = find(spEquipmentId);
			
			if(equipmentsEntity.getStatus().equalsIgnoreCase("Active"))
			{
				entityManager.createNamedQuery("ServicePointEquipmentsEntity.updateEquipmentStatusFromInnerGrid").setParameter("status", "Inactive").setParameter("spEquipmentId", spEquipmentId).executeUpdate();
				out.write("Equipment is de-activated");
			}
			else
			{
				entityManager.createNamedQuery("ServicePointEquipmentsEntity.updateEquipmentStatusFromInnerGrid").setParameter("status", "Active").setParameter("spEquipmentId", spEquipmentId).executeUpdate();
				out.write("Equipment is activated");
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
