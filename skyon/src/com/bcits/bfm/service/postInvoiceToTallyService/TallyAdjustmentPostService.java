package com.bcits.bfm.service.postInvoiceToTallyService;

import java.util.Date;


public interface TallyAdjustmentPostService {
	
	
	String reponsePostAdjustmentToTally(int adjustmentId) throws Exception;
	String generateXmlAgustmentPosting(Date monthDate);


}
