package com.bcits.bfm.amr.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.amr.service.AMREntityService;
import com.bcits.bfm.model.AMRDataEntity;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;


@Repository
public class AMREntityServiceImpl extends GenericServiceImpl<AMRDataEntity> implements AMREntityService{

	@Override
	public List<?> getAMRAccountEntityDetails() {
		// TODO Auto-generated method stub
		return entityManager.createQuery("select eme.elMeterId,a.accountId,p.personId,(select pp.propertyId from Property pp where pp.propertyId = a.propertyId),(select pp.blocks.blockId from Property pp where pp.propertyId = a.propertyId), (select pp.property_No from Property pp where pp.propertyId = a.propertyId),(select pp.blocks.blockName from Property pp where pp.propertyId = a.propertyId),(select amr.columnName from AMRProperty amr where amr.propertyId = a.propertyId) from ElectricityMetersEntity eme inner join eme.account a inner join a.person p").getResultList();
	}

	@Override
	public List<AMRDataEntity> getAMRAccountDetails(String strDate, String pesentDate) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("AmrDataDetails.getAMRData",AMRDataEntity.class).setParameter("strDate", strDate).setParameter("pesentDate", pesentDate).getResultList();
	}

	@Override
	public List<AMRDataEntity> getAccountDetailsOnPropertyId(int propid,String strDate, String pesentDate) {
		// TODO Auto-generated method stub
		System.out.println("strDate::"+strDate+":::propid::"+propid);
		return entityManager.createNamedQuery("AmrDataDetailsOnMonth.getAMRData",AMRDataEntity.class).setParameter("strDate", strDate).setParameter("pesentDate", pesentDate).setParameter("propid", propid).getResultList();
	}

}
