package com.bcits.bfm.serviceImpl.electricityBillsManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.bcits.bfm.model.BillingConfiguration;
import com.bcits.bfm.service.electricityBillsManagement.BillingConfigurationService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Service
public class BillingConfigurationServiceImpl extends GenericServiceImpl<BillingConfiguration>  implements BillingConfigurationService {

	@Override
	public List<?> findALL() {
		return entityManager.createNamedQuery("BillingConfiguration.findALL").getResultList();
	}

	@Override
	public void billingSettingsStatus(final int id,final String operation,final HttpServletResponse response) {
		try
		{
			final PrintWriter out = response.getWriter();
			if("activate".equalsIgnoreCase(operation))
			{
				entityManager.createNamedQuery("BillingConfiguration.billingSettingsStatus").setParameter("status", "Active").setParameter("id", id).executeUpdate();
				out.write("Setting is actived");
			}
			else{
				entityManager.createNamedQuery("BillingConfiguration.billingSettingsStatus").setParameter("status", "Inactive").setParameter("id", id).executeUpdate();
				out.write("Setting is inactived");
			}
		} 
		catch (IOException e){
			//LOGGER.info("========== Error while creating print write object ===============");
		}
	}

	@Override
	public String checkForDuplicate(String configName) {
		try
		{
		  return entityManager.createNamedQuery("BillingConfiguration.checkForDuplicate",String.class).setParameter("configName", configName).getSingleResult();
		} catch (Exception e){
			return null;
		}
	}

}
