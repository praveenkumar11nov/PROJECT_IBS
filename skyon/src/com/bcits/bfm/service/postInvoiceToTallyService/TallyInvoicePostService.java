package com.bcits.bfm.service.postInvoiceToTallyService;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.ElectricityBillLineItemEntity;

public interface TallyInvoicePostService {

	/*String reponsePostInvoiceToTally(List<Map<String, Object>> mapsList,
			String billNumber, String billDate,String ledgerName,int billId,Date basicDateOfInvoice,String serviceType,String accountNo,String arrearsLedge)
			throws Exception;*/

	String reponsePostReceiptToTally(List<Map<String, Object>> mapsList,
			Date receiptDate, String receiptNumber, String bankAcNumber, String bankName, String paymentMode, int paymentCollectionId, double checkAmount,Date cashReceiptDate) throws Exception;

	String responsePostInvoiceForCAM(List<Map<String, Object>> mapsList,
			String billNumber, String billDate, String salesLedger, int billId,
			Date basicDateOfInvoice, String serviceType, String accountNo,
			String arrearsLedge,
			List<Map<String, Object>> lineItemList,String propertyNumber);

}
