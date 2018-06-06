package com.bcits.bfm.service.commonAreaMaintenance;

import java.util.List;

import com.bcits.bfm.model.CamChargesEntity;
import com.bcits.bfm.service.GenericService;

public interface CamChargesService extends GenericService<CamChargesEntity>{

	List<CamChargesEntity> findAll();

	List<CamChargesEntity> findAllData();

}
