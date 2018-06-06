package com.bcits.bfm.serviceImpl.tariffManagemntImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.bcits.bfm.model.ELTariffMaster;
import com.bcits.bfm.service.tariffManagement.EL_Tariff_MasterServices;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class EL_Tariff_MasterServicesImpl extends GenericServiceImpl<ELTariffMaster> implements EL_Tariff_MasterServices{
	
	static Logger logger = LoggerFactory.getLogger(EL_Tariff_MasterServices.class);
	
	@SuppressWarnings("unchecked")
	@Override
	
	public List<Integer> getTariffNameBasedonTariffid(int EL_Tariff_Id) {
		return entityManager.createNamedQuery("EL_Tariff_Id.GETTariffName").setParameter("EL_Tariff_Id",EL_Tariff_Id).getResultList();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public void remove(int EL_Tariff_Id) {
		entityManager.createNamedQuery("EL_Tariff_Master.DELETE").setParameter("EL_Tariff_Id",EL_Tariff_Id).executeUpdate();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	
	public List<ELTariffMaster> getTariffDetail(int parentId,String Tariff_Code) {
		
		logger.info(parentId+"======================================="+Tariff_Code);
		
            return entityManager.createNamedQuery("abc").setParameter("parentId", parentId).setParameter("tariff_Name", Tariff_Code).getResultList();
           
	}

	@Override
	
	public void updateList(List<ELTariffMaster> taraiffMaster) {
		for(ELTariffMaster eltraiffmasters:taraiffMaster){
			entityManager.merge(eltraiffmasters);
		}
		entityManager.flush();
		
	}

	@Override
	
	public void delete(List<ELTariffMaster> eltraiffmaster) {
for(ELTariffMaster eltraiffmasters:eltraiffmaster){

	entityManager.createNamedQuery("EL_Tariff_Master.DELETE").setParameter("EL_Tariff_Id",eltraiffmasters.getElTariffID()).executeUpdate();	
		}
		
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public List<ELTariffMaster> findAllOnParentId(Integer EL_Tariff_Id,String status) {
		
		logger.info("\n\n\n\t"+EL_Tariff_Id);
		
		List<ELTariffMaster> tarifflist=null;
		if(EL_Tariff_Id == null){
			EL_Tariff_Id=1;
			tarifflist=entityManager.createNamedQuery("TariffMasterTree.getTariffListbyId").setParameter("parentId", EL_Tariff_Id).getResultList();
		logger.info(tarifflist+"+++++++++");
		}else{
			tarifflist=entityManager.createNamedQuery("TariffMasterTree.getTariffListbyId").setParameter("parentId", EL_Tariff_Id).getResultList();
		logger.info(tarifflist.toString()+"==================");
		}
			
			
		
		return tarifflist;
	}

	@Override
	public void saveListEL_Tariff_Master(List<ELTariffMaster> eltariff) {
		 entityManager.merge(eltariff);
		
	}

	@Override
	public ELTariffMaster getNodeDetails(int nodeid) 
	{
		return entityManager.createNamedQuery("TariffMaster.GetNodeDetails",ELTariffMaster.class).setParameter("nodeid", nodeid).getSingleResult();
	}

	
	
	
	@Override
	public ELTariffMaster getTariffObject(Map<String, Object> map,
			String type, ELTariffMaster tariffmaster, int EL_Tariff_Id) {
				
			tariffmaster.setCreatedBy((String)map.get("createdBy"));
			tariffmaster.setLastUpdatedDT(new Timestamp(new Date().getTime()));
					
		return tariffmaster;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ELTariffMaster> findAllOnParentIdwotStatus(
			Integer EL_Tariff_Id) {

		
		List<ELTariffMaster> tarifflist=null;
		if(EL_Tariff_Id == null){
			EL_Tariff_Id=1;
			tarifflist=entityManager.createNamedQuery("Tariffmaster.GetAllTariff").setParameter("parentId", EL_Tariff_Id).getResultList();
		
		}else{
			tarifflist=entityManager.createNamedQuery("Tariffmaster.GetAllTariff").setParameter("parentId", EL_Tariff_Id).getResultList();
		
		}
			
			
		
		return tarifflist;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTariffName(String tariffname) {
		
		
		return entityManager.createNamedQuery("Tariffmaster.GetTariffName").setParameter("tariff_Name",tariffname).getResultList();
	}

}
