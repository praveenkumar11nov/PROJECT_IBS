package com.bcits.bfm.service.facilityManagement;

import java.sql.Blob;
import java.util.List;

import com.bcits.bfm.model.PreRegisteredVisitors;
import com.bcits.bfm.model.VisitorVisits;
import com.bcits.bfm.service.GenericService;

public interface PreRegisteredVisitorService extends GenericService<PreRegisteredVisitors> 
{
	public List<PreRegisteredVisitors> findAll();
	public List<PreRegisteredVisitors> getVisitorRequiredDetails();
	public List<Object[]> getVisitorDetails();
	public List<?> searchPreRegisteredVisitorBasedOnContactNo(String visitorContactNo);
	public int getPropertyIdBasedOnPropertyNo(String propertyNo);
	public int getParkinSlotIdBasedOnPsSlotNo(String psSlotNo);
	public Blob getReRegisteredImageForWizardView(String visitorContactNo);
	public Blob getImageForPreRegisteredView(int viId);
}
