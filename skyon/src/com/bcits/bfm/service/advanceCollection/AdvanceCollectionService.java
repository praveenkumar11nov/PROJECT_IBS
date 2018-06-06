package com.bcits.bfm.service.advanceCollection;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.AdvanceCollectionEntity;
import com.bcits.bfm.service.GenericService;

public interface AdvanceCollectionService extends GenericService<AdvanceCollectionEntity> {

	List<AdvanceCollectionEntity> findAll();

	List<?> getAllAccountNumbers();

	void setAdvanceCollectionStatus(int advCollId, String operation,HttpServletResponse response);

	Set<String> commonFilterForAccountNumbersUrl();

	Set<String> commonFilterForPropertyNoUrl();

	Set<String> commonFilterForTransactionNameUrl();

	List<AdvanceCollectionEntity> testUniqueAccount(int accountId);

	List<?> findPersonForFilters();

}
