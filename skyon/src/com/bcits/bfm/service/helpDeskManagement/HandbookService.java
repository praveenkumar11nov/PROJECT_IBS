package com.bcits.bfm.service.helpDeskManagement;

import java.util.List;

import com.bcits.bfm.model.Handbook;
import com.bcits.bfm.service.GenericService;

public interface HandbookService extends GenericService<Handbook> {

	List<Handbook> findAll();

}
