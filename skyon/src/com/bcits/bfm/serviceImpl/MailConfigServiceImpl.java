package com.bcits.bfm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.MailMaster;
import com.bcits.bfm.service.MailConfigService;

@Repository
public class MailConfigServiceImpl extends GenericServiceImpl<MailMaster> implements MailConfigService{

	

	@Override
	public List<?> findAll() {
		return entityManager.createNamedQuery("MailMaster.getAll").getResultList();
	}

	@Override
	public int getServiceStatus(String value) {
		return ((Number)entityManager.createNamedQuery("MailMaster.getServiceStatus").setParameter("value", value).getSingleResult()).intValue();
	}

	@Override
	public MailMaster getMailMasterByService(String serviceType) {
		return (MailMaster) entityManager.createNamedQuery("MailMaster.getMailMasterByServiceType").setParameter("serviceType", serviceType).getSingleResult();
	}

}
