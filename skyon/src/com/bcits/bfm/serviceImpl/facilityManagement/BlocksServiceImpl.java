package com.bcits.bfm.serviceImpl.facilityManagement;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.ejb.criteria.expression.function.UpperFunction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.service.facilityManagement.BlocksService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.SessionData;


@Repository
@Transactional(propagation=Propagation.REQUIRED)
public class BlocksServiceImpl extends GenericServiceImpl<Blocks> implements BlocksService 
{
		
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Blocks> findAll() {
		try {
			final String queryString = "select model from Blocks model";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public Blocks getBeanObject(Blocks block, String type,Map<String, Object> map,
			HttpServletRequest request) 
	{
		// TODO Auto-generated method stub
		if(type.equalsIgnoreCase("save"))
		{
			block.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
			block.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));	
		}
		else
		{
			block.setBlockId((Integer) map.get("blockId"));
			block.setCreatedBy((String)map.get("createdBy"));
			block.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		}
		block.setBlockName((String)map.get("blockName"));
		block.setNumOfProperties((Integer)map.get("numOfProperties"));
		block.setNumOfParkingSlots((Integer)map.get("numOfParkingSlots"));
		block.setLastUpdatedDt(new Timestamp(new Date().getTime()));
		return block;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Blocks> findBlockOnProjectId(int projectId) 
	{
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("Blocks.findBlockOnProjectId").setParameter("projectId", projectId).getResultList();
	}

	@Override
	public Blocks findbyName(String stringCellValue) {
		try {
			final String queryString = "select model from Blocks model where model.blockName=:blockName";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("blockName", stringCellValue);
			return (Blocks) query.getResultList().get(0);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public Blocks getBlockNameByBlockId(int blockId) {
		return  entityManager.createNamedQuery("Blocks.getBlockNameByBlockId",
				Blocks.class)
				.setParameter("blockId", blockId )
				.getSingleResult();

	}

	@Override
	public Integer getBlockIdByName(String value) {
		try{
		return  entityManager.createNamedQuery("Blocks.getBlockName",Integer.class)
				.setParameter("value",  value.toUpperCase() )
				.getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
return null;
	}

	@Override
	public Integer getProjectIdBasedOnBlockName(String value2) {
		return  entityManager.createNamedQuery("Blocks.getProjectId",Integer.class)
				.setParameter("value2", value2.toUpperCase() )
				.getSingleResult();
	}

	@Override
	public Integer getNoOfBlocks(int projectId) {
		return  entityManager.createNamedQuery("Blocks.getNoOfBlocks",Integer.class)
				.setParameter("projectId", projectId )
				.getSingleResult();
	}

	@Override
	public Long getSumOfBlocks(int projectId) {
		return  entityManager.createNamedQuery("Blocks.sumOfBlocks",Long.class)
				.setParameter("projectId", projectId )
				.getSingleResult();
	}
}