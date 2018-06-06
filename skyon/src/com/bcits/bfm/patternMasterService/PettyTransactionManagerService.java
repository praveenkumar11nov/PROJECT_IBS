package com.bcits.bfm.patternMasterService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bcits.bfm.patternMasterEntity.PettyTransactionManager;
import com.bcits.bfm.service.GenericService;

@Service
public interface PettyTransactionManagerService extends GenericService<PettyTransactionManager>{

	public List<?> readAllProcessNames();

	public List<?> getTransactionType();

	public List<?> findall();

	public List<?> readTransactionManager();

}
