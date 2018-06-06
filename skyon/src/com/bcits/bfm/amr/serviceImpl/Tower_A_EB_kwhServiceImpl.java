package com.bcits.bfm.amr.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.amr.service.Tower_A_EB_kwhService;
import com.bcits.bfm.model.Tower_A_EB_kwh;

@Repository
public class Tower_A_EB_kwhServiceImpl extends GenericMSSQLServiceImpl<Tower_A_EB_kwh> implements Tower_A_EB_kwhService  {

	@Override
	public List<Tower_A_EB_kwh> read() {
		return entityManager.createNamedQuery("Tower_A_EB_kwh.findALL",Tower_A_EB_kwh.class).getResultList();
	}
}
