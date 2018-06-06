package com.bcits.bfm.service.electricityBillsManagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ElectricityBillLineItemEntity;
import com.bcits.bfm.service.GenericService;

public interface ElectricityBillLineItemService extends  GenericService<ElectricityBillLineItemEntity>{

	List<ElectricityBillLineItemEntity> findALL();
	void setElectricityBillLineItemStatus(int elBillId, String operation, HttpServletResponse response);
	List<ElectricityBillLineItemEntity> findAllById(int elBillId);
	List<ElectricityBillLineItemEntity> findAllLineItemsById(int elBillId);
	List<ElectricityBillLineItemEntity> findLineItemBasedOnTransactionCode(int elBillId, String transactionCode);
	ElectricityBillLineItemEntity findByTransactionCode(int transactionId,String transactionCode);
	void deleteAllBill(int i);
	List<ElectricityBillLineItemEntity> findAllLineItemsByIdUpdate(int elBillId);
	List<?> getTaransactionCodeForOthers(String billServiceType);
	List<Map<String,Object>> findAllDetailsForCamPosting(int billId);
}
