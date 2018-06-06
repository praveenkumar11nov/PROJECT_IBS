package com.bcits.bfm.service.serviceRoute;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bcits.bfm.model.ServiceRoute;
import com.bcits.bfm.service.GenericService;
@Service
public interface ServiceRouteService extends GenericService<ServiceRoute>{
public ServiceRoute getNodeDetails(int nodeid);
//public List<ServiceRoute> getNodeDetails(int nodeid);
      @SuppressWarnings("rawtypes")
	List getAllDetailsList(List<ServiceRoute> servicePointList);
	public List<ServiceRoute> getServiceRoute(String serviceRoute);
	
	public List<ServiceRoute> findAllOnParentId();
	
	public List<ServiceRoute> findOnSrId(int srId);
	
	public List<ServiceRoute> getonServiceRouteDetail(int parentid,String routeplan);
	
	
	public ServiceRoute getTariffObject(Map<String, Object>map,String type,ServiceRoute tariffmaster,int srid);

	public List<ServiceRoute > getServiceRouteNameBasedonServiceid(int srid);
	
    public List<Object[]> getPropertyName(int blockId) ;
    
    public List<Object[]> getBlockName() ;
    //public List<Object[]> getBlockName() ;
    public List<Object[]> getMeterName() ;

	public  List<Object[]> getPropertyNameUpdate(Integer nodeid);
	
	public List<Object[]> getStaffName();

}
