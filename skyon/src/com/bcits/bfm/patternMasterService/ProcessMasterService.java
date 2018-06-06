package com.bcits.bfm.patternMasterService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bcits.bfm.patternMasterEntity.ProcessMaster;
import com.bcits.bfm.service.GenericService;

@Service
public interface ProcessMasterService extends GenericService<ProcessMaster>{

	List<?> readAllProcessName();

}
