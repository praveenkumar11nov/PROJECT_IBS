package com.bcits.bfm.service.advanceCollection;

import java.util.List;

import com.bcits.bfm.model.ClearedPayments;
import com.bcits.bfm.service.GenericService;

public interface ClearedPaymentsService extends GenericService<ClearedPayments>
{

	List<ClearedPayments> findAll(int advCollId);

}
