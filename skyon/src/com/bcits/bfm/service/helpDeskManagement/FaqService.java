package com.bcits.bfm.service.helpDeskManagement;

import java.sql.Blob;
import java.util.List;

import com.bcits.bfm.model.Faq;
import com.bcits.bfm.service.GenericService;

public interface FaqService extends GenericService<Faq> {

	List<Faq> findAll();

	void updateDocument(int faqId, Blob blob);

}
