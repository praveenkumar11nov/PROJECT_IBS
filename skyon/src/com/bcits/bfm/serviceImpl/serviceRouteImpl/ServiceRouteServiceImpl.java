package com.bcits.bfm.serviceImpl.serviceRouteImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.ServiceRoute;
import com.bcits.bfm.service.serviceRoute.ServiceRouteService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;

@Repository
public class ServiceRouteServiceImpl extends GenericServiceImpl<ServiceRoute> implements ServiceRouteService{

	@Override
	public ServiceRoute getNodeDetails(int nodeid) {
		
		return null;
				//entityManager.createNamedQuery("ServiceRoute.GetNodeDetails",ServiceRoute.class).setParameter("nodeid", nodeid).getSingleResult();
	}
	/*@Override
		public  List<ServiceRoute> getNodeDetails(int nodeid) {
		
		Query query=entityManager.createNativeQuery("SELECT PROPERTY.PROPERTY_NO,SERVICE_ROUTE.SR_ID,SERVICE_ROUTE.ROUTE_PLAN,SERVICE_ROUTE.ROUTE_DESCRIPTION FROM PROPERTY ,SERVICE_ROUTE WHERE SR_ID= ? AND SERVICE_ROUTE.PROPERTY_ID=PROPERTY.PROPERTY_ID",ServiceRoute.class);
			
		query.setParameter(1, nodeid);
		List<ServiceRoute> result=query.getResultList();
			
		return entityManager.createNamedQuery("ServiceRoute.GetNodeDetails",ServiceRoute.class).setParameter("nodeid", nodeid).getResultList();
	//return result;
	}*/

	@Override
	public List<ServiceRoute> getServiceRoute(String serviceRoute) {
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceRoute> findAllOnParentId() {
		
		List<ServiceRoute> servicelist=entityManager.createNamedQuery("ServiceRoute.GetAllServiceRoute").getResultList();
		
		
		
		
		
		return getAllDetailsList(servicelist);
	}
	
	@SuppressWarnings("unused")
	private List<ServiceRoute> selectedList(List<ServiceRoute> list) 
	{
		List<ServiceRoute> listNew = new ArrayList<ServiceRoute>();
		for (Iterator<ServiceRoute> iterator = list.iterator(); iterator.hasNext();) {
			ServiceRoute spInstructions = (ServiceRoute) iterator.next();
			spInstructions.setPersonobj(null);
			listNew.add(spInstructions);
		}
		return listNew;
		
		
		
		
		
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public
	List getAllDetailsList(List<ServiceRoute> servicePointList){
		List<Map<String, Object>> servicePointMap =  new ArrayList<Map<String, Object>>();    

		for (final ServiceRoute servicePoint : servicePointList) 
		{
			
			int personId=servicePoint.getPersonId();
			 final List<Person> person=entityManager.createNamedQuery("ServiceRoute.GetPersonDetail").setParameter("personId", personId).getResultList();		 
			servicePointMap.add(new HashMap<String, Object>() {
			
				private static final long serialVersionUID = 1L;

			{  
			put("srId",servicePoint.getSrId());	
			put("personId", servicePoint.getPersonId());	
			//put("staffName",servicePoint.getPersonobj().getFirstName()+" "+servicePoint.getPersonobj().getLastName());
			for ( Person person1 : person) 
			{
				
				put("staffName",person1.getFirstName()+" "+person1.getLastName());
			
			}
			put("routeName",servicePoint.getRouteName());
			put("subRouteName",servicePoint.getSubRouteName());
			put("billFrom",servicePoint.getBillFrom());
			put("billTo", servicePoint.getBillTo());
			put("readCycle", servicePoint.getReadCycle());
			put("routeDescription", servicePoint.getRouteDescription());
			put("estimationDay", servicePoint.getEstimationDay());
			
			put("readDay", servicePoint.getReadDay());
			/*put("servicePointName", servicePoint.getServicePointName());
			put("status", servicePoint.getStatus());
			put("createdBy", servicePoint.getCreatedBy());
			put("lastUpdatedBy", servicePoint.getLastUpdatedBy());*/
			}});
			
		}
		
		/*for (ServiceRoute servicePoint1 : servicePointList) {
			int personId=servicePoint1.getPersonId();
			 List<Person> person=entityManager.createNamedQuery("ServiceRoute.GetPersonDetail").setParameter("personId", personId).getResultList();		 
			 
			 for (final Person person1 : person) 
				{
					
					servicePointMap.add(new HashMap<String, Object>() {
					
						private static final long serialVersionUID = 1L;

					{  
						
					put("staffName",servicePoint.getFirstName()+" "+servicePoint.getLastName());
					
					}});
					
				}
		
		}*/
		return servicePointMap;	
	}

	@Override
	public List<ServiceRoute> getonServiceRouteDetail(int parentid,
			String routeplan) {
		
		return null;
				//entityManager.createNamedQuery("ServiceRouteName.BasedOnParentId").setParameter("parentId", parentid).setParameter("routePlan",routeplan).getResultList();
	}

	@SuppressWarnings("unused")
	@Override
	public ServiceRoute getTariffObject(Map<String, Object> map, String type,
			ServiceRoute serviceroot, int srid) {
		DateTimeCalender dateTimeCalender = new DateTimeCalender(); 
		/*if(type=="update"){
			//tariffmaster.setEL_Tariff_Id((Integer)map.get("EL_Tariff_Id"));
		    // tariffmaster.setCreated_By((String)map.get("Created_By"));
		     tariffmaster.setLast_Updated_By((String)map.get("Last_Updated_By"));
		     tariffmaster.setLast_Updated_DT(new Timestamp(new Date().getTime()));
		}else if(type=="save"){*/
		serviceroot.setCreatedBy((String)map.get("createdBy"));
		serviceroot.setLastUpdatedDate(new Timestamp(new Date().getTime()));
		
		return serviceroot;
	}

	@Override
	public List<ServiceRoute> getServiceRouteNameBasedonServiceid(int srid) {
	
		return null;
				//entityManager.createNamedQuery("ServiceRoute.GETServiceRoutename").setParameter("srId", srid).getResultList();
	}

	@Override
public List<Object[]> getPropertyName(int blockId) {
		
		return null;
				//entityManager.createNamedQuery("ServiceRoute.GETPropertyName").setParameter("blockId", blockId).getResultList();
	}

	@Override
	public List<Object[]> getBlockName() {
		//return entityManager.createNamedQuery("ServiceRoute.GETBlockName").getResultList();
		return null;
	}

	public List<Object[]> getPropertyNameUpdate(Integer nodeid) {
		//return entityManager.createNamedQuery("ServiceRoute.GETPropertyNameUpdate").setParameter("nodeid", nodeid).getResultList();
	return null;
	}

	@Override
	public List<Object[]> getMeterName() {
		//return entityManager.createNamedQuery("ServiceRoute.GETMeterName").getResultList();
	
	return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getStaffName() {
		
		return entityManager.createNamedQuery("ServiceRoute.GETStaffName").getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceRoute> findOnSrId(int srId) {
List<ServiceRoute> servicelist=entityManager.createNamedQuery("ServiceRoute.GetServiceRouteOnSrId").setParameter("srId", srId).getResultList();
		
		
		
		
		
		return getAllDetailsList(servicelist);
	}





}
