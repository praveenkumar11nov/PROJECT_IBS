package com.bcits.bfm.service.tariffManagement;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ELRateSlabs;
import com.bcits.bfm.model.GasRateSlab;
import com.bcits.bfm.model.SolidWasteRateSlab;
import com.bcits.bfm.model.WTRateSlabs;
import com.bcits.bfm.service.GenericService;

public interface ELRateSlabService extends  GenericService<ELRateSlabs> 
{
	
	List<ELRateSlabs> findALL();
	List<ELRateSlabs> findRateSlabByID(int elrateSlabID);
	float getMaxSlabTo(int elrmId);
	/*List<ELRateSlabs> findAllGreaterThanId(int elrsId);*/
	ELRateSlabs findRateSlabByPrimayID(int parseInt);
	void updateslabStatus(int slabID, HttpServletResponse response);
	int getMaxSlabNumber(int elrmId);
	List<ELRateSlabs> getElRateSlabGreaterThanUpdateSlabNo(int slabsNo,int elrmId);
	List<ELRateSlabs> getElRateSlabGreaterThanUpdateSlabNoEq(int slabsNo,int elrmId);
	ELRateSlabs findIdAndParentId(int elrsId, int elrmid);
	/*List<ELRateSlabs> findAllLessThanId(int elrsId);*/
	List<ELRateSlabs> findAllLessThanId(int slabsNo, int elrmId);
	List<ELRateSlabs> findAllGreaterThanId(int slabsNo, int elrmId);
	List<ELRateSlabs> getRateSlabsForRangeSlab(int elrmid, float sancationLoad);
	List<ELRateSlabs> getRateSlabByRateMasterId(Integer rateMasterId);
	List<WTRateSlabs> getWtRateSlabByRateMasterId(int rateMasterID);
	List<GasRateSlab> getGasRateSlabByRateMasterId(int rateMasterID);
	List<SolidWasteRateSlab> getSWRateSlabByRateMasterId(int rateMasterID);

}
