package com.bcits.bfm.serviceImpl.tariffManagemntImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.controller.TariffCalculationController;
import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.CommonServicesRateMaster;
import com.bcits.bfm.model.CommonServicesRateSlab;
import com.bcits.bfm.model.ELRateMaster;
import com.bcits.bfm.model.ELRateSlabs;
import com.bcits.bfm.model.ELTariffMaster;
import com.bcits.bfm.model.ELTodRates;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.ElectricityMeterLocationEntity;
import com.bcits.bfm.model.GasRateMaster;
import com.bcits.bfm.model.GasRateSlab;
import com.bcits.bfm.model.GenericBillGeneration;
import com.bcits.bfm.model.GenericClassForTodCalculation;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.model.ServiceParametersEntity;
import com.bcits.bfm.model.SlabDetails;
import com.bcits.bfm.model.SolidWasteRateMaster;
import com.bcits.bfm.model.SolidWasteRateSlab;
import com.bcits.bfm.model.WTRateMaster;
import com.bcits.bfm.model.WTRateSlabs;
import com.bcits.bfm.service.OnlinePaymentTransactionsService;
import com.bcits.bfm.service.PrepaidRechargeService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.accountsManagement.ElectricitySubLedgerService;
import com.bcits.bfm.service.billingCollectionManagement.PaymentService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillParameterService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceParameterService;
import com.bcits.bfm.service.tariffManagement.ELTariffMasterService;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.bcits.bfm.model.OnlinePaymentTransactions;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.PrepaidRechargeEntity;
import com.google.gson.Gson;

@Repository
public class TariffCalculationServiceImpl implements TariffCalculationService
{
	@PersistenceContext(unitName="bfm")
	protected EntityManager entityManager;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ElectricitySubLedgerService electricitySubLedgerService;
	
	@Autowired
	TariffCalculationController calculationController;
	
	@Autowired
	public ELTariffMasterService service;
	
	@Autowired
	ServiceMasterService serviceMasterService;
	
	@Autowired
	ServiceParameterService serviceParameterService;
	
	@Autowired
	ElectricityBillsService electricityBillsService;
	
	static Logger logger = LoggerFactory.getLogger(TariffCalculationServiceImpl.class);
	
	Locale locale;
	
	@Autowired
	ElectricityBillParameterService billParameterService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	PrepaidRechargeService prepaidRechargeService;
	
	@Autowired
	OnlinePaymentTransactionsService onlinePaymentTransactionsService;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getTariffNameCal() {
		
		return entityManager.createNamedQuery("TariffCalc.GETTariffName").getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getBillOnDate(int date, int year, String serviceType,int accountId) {
		return entityManager.createNamedQuery("TariffCalc.GetBillOnDate").setParameter("date", date).setParameter("year", year).setParameter("serviceType", serviceType).setParameter("accountId", accountId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getServiceStatus(int accountId,String serviceType,int serviceId) {
		
		Integer elBillId=(Integer) entityManager.createNamedQuery("TariffCalc.GetBillId").setParameter("accountId", accountId).setParameter("serviceType", serviceType).getSingleResult();
		List<String> list=entityManager.createNamedQuery("TariffCalc.GetBillStatus").setParameter("elBillId", elBillId).getResultList();
		List<?> list1=entityManager.createNamedQuery("TariffCalc.GetBillDate").setParameter("elBillId", elBillId).getResultList();
		List<java.sql.Date> initialBillDate = entityManager.createNamedQuery("TariffCalc.GetBillInitialDate").setParameter("serviceId", serviceId).getResultList();
		Integer meterId=(Integer) entityManager.createNamedQuery("TariffCalc.GetMeterId").setParameter("accountId", accountId).setParameter("serviceType", serviceType).getSingleResult();
		List<?> initialReading=entityManager.createNamedQuery("TariffCalc.GetBillInitialReading").setParameter("meterId", meterId).getResultList();
		//List<ElectricityMeterParametersEntity> initialReading=entityManager.createNamedQuery("TariffCalc.GetMeterId").setParameter("accountId", accountId).setParameter("serviceType", serviceType).getResultList();
		
		/*Integer dgmeterId=(Integer) entityManager.createNamedQuery("TariffCalc.GetDGMeterId").setParameter("accountId", accountId).setParameter("serviceType", serviceType).getSingleResult();
		Integer dginitialReading=(Integer) entityManager.createNamedQuery("TariffCalc.GetDgInitialReading").setParameter("meterId", meterId).getSingleResult();
		Integer dgpreviousReading=(Integer) entityManager.createNamedQuery("TariffCalc.GetPreviousDgReading").setParameter("elBillId", elBillId).getSingleResult();
		logger.info("dginitialReading---------------------"+dginitialReading);
		logger.info("dgpreviousReading:::::::::::::::::::::::::"+dgpreviousReading);*/
		
		List<Map<String, Object>> servicePointMap =  new LinkedList<Map<String, Object>>();  
		List<Map<String, Object>> servicemasterMap =  new LinkedList<Map<String, Object>>();
		List<Map<String, Object>> servicUnmetered =  new LinkedList<Map<String, Object>>();
		List<Map<String, Object>> dgmetered =  new LinkedList<Map<String, Object>>();
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			
			final Object[] values = (Object[]) iterator.next();	
			servicePointMap.add(new LinkedHashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{  
				if(((String)values[0]).equalsIgnoreCase("Present status")){
				put("previousStatus",(String)values[1]);
				}
				
				if(((String)values[0]).equalsIgnoreCase("Present reading")){
				put("previousreading",(String)values[1]);	
				}
				
				if(((String)values[0]).equalsIgnoreCase("Meter Constant")){
				put("meterConstant",(String)values[1]);	
				}
			}});
		}
		for (Iterator<?> iterator = list1.iterator(); iterator.hasNext();) {
			
			final Object[] values = (Object[]) iterator.next();
			servicePointMap.add(new LinkedHashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{  
			put("billdate",(java.sql.Date)values[0]);	
			}});
			
		}
		
		@SuppressWarnings("rawtypes")
		List finallist=new LinkedList<>();
		if(elBillId==null && (meterId!=null)){
			/*finallist.add("Normal");
			finallist.add("0");
			finallist.add("1");
			finallist.add("19/8/2014");*/
			for(Iterator<?> iterator = initialReading.iterator(); iterator.hasNext();){
				
				final Object[] values = (Object[]) iterator.next();
				servicemasterMap.add(new LinkedHashMap<String, Object>() {					
				private static final long serialVersionUID = 1L;
				{  
				    // put("Status","Normal");
					if(((String)values[0]).equalsIgnoreCase("Initial Reading")){
						put("Initial Reading",(String)values[1]);
					}
					if(((String)values[0]).equalsIgnoreCase("Meter Constant")){
						put("Meter Constant",(String)values[1]);
					}
				
				
				}});
					
			}
			for(final java.sql.Date startDate : initialBillDate){
				servicemasterMap.add(new LinkedHashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{  
				put("billdate",startDate);	
				}});	
			}
			finallist.add("Normal");
			for(Map<String, Object> l:servicemasterMap){
				Set<Entry<String, Object>> s=l.entrySet();
				Iterator<Entry<String, Object>> i=s.iterator();
				if(i.hasNext()){
					@SuppressWarnings("rawtypes")
					Map.Entry mentry2 = (Map.Entry)i.next();
					finallist.add(mentry2.getValue());
				}
			}
			
		}else if (elBillId==null && (meterId==null)) {
			finallist.add("Direct Connection");
			finallist.add("0");
			finallist.add("0");
			for(final java.sql.Date startDate : initialBillDate){
				servicUnmetered.add(new LinkedHashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
				{  
				put("billdate",startDate);	
				}});	
			}
			for(Map<String, Object> l:servicUnmetered){
				Set<Entry<String, Object>> s=l.entrySet();
				Iterator<Entry<String, Object>> i=s.iterator();
				if(i.hasNext()){
					@SuppressWarnings("rawtypes")
					Map.Entry mentry2 = (Map.Entry)i.next();
					finallist.add(mentry2.getValue());
				}
			}
		}
		
		else{
		for(Map<String, Object> l:servicePointMap){
			Set<Entry<String, Object>> s=l.entrySet();
			Iterator<Entry<String, Object>> i=s.iterator();
			if(i.hasNext()){
				@SuppressWarnings("rawtypes")
				Map.Entry mentry2 = (Map.Entry)i.next();
				finallist.add(mentry2.getValue());
			}
		}
		}
		if(elBillId!=null){
			 for(Iterator<?> iterator = list1.iterator(); iterator.hasNext();){
				 
				 final Object[] values = (Object[]) iterator.next();				 
				 String billstatus = (String)values[1];
				 finallist.add(billstatus);
			 
		 }}else{
			 finallist.add("Not Generated"); 
		 }
		/*-------------------------------------------------------------------------------------------------------------------------*/
		 List<Object> dgapplicable=todApplicable(serviceType, accountId);
		 logger.info("dgapplicable size ------------ "+dgapplicable.size());
		
		/* if(dgapplicable.get(1).equals("Yes")){*/
		 if(dgapplicable.get(1)!=null)
		 {
			 if(dgapplicable.get(1).equals("Yes")){
				 logger.info(":::::::::::Only DG Applicable::::::::::::: ");
				
				 if(elBillId!=null && (meterId!=null)){
					 logger.info("::::::::::::::Bill all ready Generated:::::::::::::");
					 for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
						 final Object[] values = (Object[]) iterator.next();
						 dgmetered.add(new LinkedHashMap<String, Object>() {
							private static final long serialVersionUID = 1L;
							{  
							logger.info("::::::::::::::Adding Data:::::::::::::");	
							if(((String)values[0]).equalsIgnoreCase("Present  DG reading")){
								put("previousreading",(String)values[1]);
							}
							
							if(((String)values[0]).equalsIgnoreCase("DG Meter Constant")){
								put("meterConstant",(String)values[1]);
							}
							}});
							
						}
				 }else{
			
			for(Iterator<?> iterator = initialReading.iterator(); iterator.hasNext();){
				
				final Object[] values = (Object[]) iterator.next();	
				dgmetered.add(new LinkedHashMap<String, Object>() {					
					private static final long serialVersionUID = 1L;
				{  
				  
				if(((String)values[0]).equalsIgnoreCase("DG Initial Reading")){
					put("DG Initial Reading",(String)values[1]);
				}
				if(((String)values[0]).equalsIgnoreCase("DG Meter Constant")){
					put("DG Meter Constant",(String)values[1]);
				}
				}});
					
			}
			
			 }
				 for(Map<String, Object> l:dgmetered){
						Set<Entry<String, Object>> s=l.entrySet();
						Iterator<Entry<String, Object>> i=s.iterator();
						if(i.hasNext()){
							@SuppressWarnings("rawtypes")
							Map.Entry mentry2 = (Map.Entry)i.next();
							logger.info(mentry2.getKey()+"::::::::::::::"+mentry2.getValue());
							finallist.add(mentry2.getValue());
						}}
				 }
			 
			 else{
				 logger.info(":::::::::::::::::DG Not Applicable:::::::::::::::::::::::::");
			 }
		 }
		 

		return finallist;	
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getServiceStatusCheck(int accountId,String serviceType,int serviceId){

		Integer elBillId = (Integer)entityManager.createNamedQuery("TariffCalc.GetBillId").setParameter("accountId", accountId).setParameter("serviceType", serviceType).getSingleResult();
		List<String> list = entityManager.createNamedQuery("TariffCalc.GetBillStatus").setParameter("elBillId", elBillId).getResultList();
		List<?> list1 = entityManager.createNamedQuery("TariffCalc.GetBillDate").setParameter("elBillId", elBillId).getResultList();
		List<java.sql.Date> initialBillDate = entityManager.createNamedQuery("TariffCalc.GetBillInitialDate").setParameter("serviceId", serviceId).getResultList();
		Integer meterId = (Integer) entityManager.createNamedQuery("TariffCalc.GetMeterId").setParameter("accountId", accountId).setParameter("serviceType", serviceType).getSingleResult();
		List<?> initialReading = entityManager.createNamedQuery("TariffCalc.GetBillInitialReading").setParameter("meterId", meterId).getResultList();
		Date elbilldate=null;
		List<Map<String, Object>> servicePointMap =  new LinkedList<Map<String, Object>>();  
		List<Map<String, Object>> servicemasterMap =  new LinkedList<Map<String, Object>>();
		List<Map<String, Object>> servicUnmetered =  new LinkedList<Map<String, Object>>();
		List<Map<String, Object>> dgmetered =  new LinkedList<Map<String, Object>>();
		int year=0;
		int month=0;
		if(list1.size()>0){
			for (Iterator<?> iterator = list1.iterator(); iterator.hasNext();) {
				
				final Object[] values = (Object[]) iterator.next();	
				elbilldate = (java.sql.Date)values[0];
				Calendar cal = Calendar.getInstance();
				cal.setTime(elbilldate);
				int month1 = cal.get(Calendar.MONTH);
				month = month1 + 1;
				year = cal.get(Calendar.YEAR);
			}
		}
		logger.info(":::::::::month:::::::::"+month);
		logger.info(":::::::::year:::::::::"+year);
		double dgfrchangereading=0.0d;
		double utilityfrchangereading=0.0d;
		final List<Double> finalreading=entityManager.createQuery("SELECT f.finalReading,f.finalReadingDg FROM FRChange f WHERE f.accountId='"+accountId+"' AND f.typeOfService='"+serviceType+"' AND EXTRACT(month FROM f.billDate)='"+month+"' AND EXTRACT(year FROM f.billDate)='"+year+"' ").getResultList();
		System.out.println(":::::::::::"+finalreading);
		for (Iterator iterator = finalreading.iterator(); iterator.hasNext();) {
			Object[] double1 = (Object[]) iterator.next();
			dgfrchangereading=(Double)double1[1];
			utilityfrchangereading=(Double)double1[0];
			logger.info("@@@@@@@@@"+dgfrchangereading);
			
		}
		
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {//FRChange
			final double utilityfrreading=utilityfrchangereading;
			final Object[] values = (Object[]) iterator.next();		
			servicePointMap.add(new LinkedHashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{  
				if(((String)values[0]).equalsIgnoreCase("Present status")){
				put("previousStatus",(String)values[1]);
				}
				
				if(((String)values[0]).equalsIgnoreCase("Present reading")){
				if(finalreading.size()>0){
					put("InitialReading",utilityfrreading);
				}else{
					put("InitialReading",(String)values[1]);	
				}					
				}
				
				if(((String)values[0]).equalsIgnoreCase("Meter Constant")){
				put("MeterConstant",(String)values[1]);	
				}
				
			}});
		}
		for (Iterator<?> iterator = list1.iterator(); iterator.hasNext();) {
			
			final Object[] values = (Object[]) iterator.next();
			servicePointMap.add(new LinkedHashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{  
			put("billdate",(java.sql.Date)values[0]);	
			
			}});
			
		}
		/**/
		
		@SuppressWarnings("rawtypes")
		List finallist=new LinkedList<>();
		Map<Object,Object> map=new LinkedHashMap<>();
		if(elBillId==null && (meterId!=null)){
			for(Iterator<?> iterator = initialReading.iterator(); iterator.hasNext();){
				
				final Object[] values = (Object[]) iterator.next();
				servicemasterMap.add(new LinkedHashMap<String, Object>() {					
				private static final long serialVersionUID = 1L;
				{  
					if(((String)values[0]).equalsIgnoreCase("Initial Reading")){
						put("InitialReading",(String)values[1]);
					}
					if(((String)values[0]).equalsIgnoreCase("Meter Constant")){
						put("MeterConstant",(String)values[1]);
					}
				}});
					
			}
			for(final java.sql.Date startDate : initialBillDate){
			
				servicemasterMap.add(new LinkedHashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{  
				put("billdate",startDate);	
				}});	
			}
			map.put("previousStatus", "Normal");
			for(Map<String, Object> l:servicemasterMap){
				Set<Entry<String, Object>> s=l.entrySet();
				Iterator<Entry<String, Object>> i=s.iterator();
				if(i.hasNext()){
					@SuppressWarnings("rawtypes")
					Map.Entry mentry2 = (Map.Entry)i.next();
					
					map.put("previousStatus", "Normal");
					finallist.add(mentry2.getValue());
					map.put(mentry2.getKey(), mentry2.getValue());
				}
			}
			
		}else if (elBillId==null && (meterId==null)) {
			finallist.add("Direct Connection");
			finallist.add("0");
			finallist.add("0");
			for(final java.sql.Date startDate : initialBillDate){
				servicUnmetered.add(new LinkedHashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{  
				put("billdate",startDate);	
				}});	
			}
			for(Map<String, Object> l:servicUnmetered){
				Set<Entry<String, Object>> s=l.entrySet();
				Iterator<Entry<String, Object>> i=s.iterator();
				if(i.hasNext()){
					@SuppressWarnings("rawtypes")
					Map.Entry mentry2 = (Map.Entry)i.next();
					finallist.add(mentry2.getValue());
					map.put(mentry2.getKey(), mentry2.getValue());
				}
			}
		}
		
		else{
		for(Map<String, Object> l:servicePointMap){
			Set<Entry<String, Object>> s=l.entrySet();
			Iterator<Entry<String, Object>> i=s.iterator();
			if(i.hasNext()){
				@SuppressWarnings("rawtypes")
				Map.Entry mentry2 = (Map.Entry)i.next();
				map.put(mentry2.getKey(), mentry2.getValue());
				finallist.add(mentry2.getValue());
			}
		}
		}
		if (elBillId != null) {
			for (Iterator<?> iterator = list1.iterator(); iterator.hasNext();) {
				final Object[] values = (Object[]) iterator.next();
				String billstatus = (String) values[1];
				map.put("billgenerationstatus", (String) values[1]);
				finallist.add(billstatus);

			}
		} else {
			finallist.add("Not Generated");
			map.put("billgenerationstatus", "Not Generated");
		}
		/*-------------------------------------------------------------------------------------------------------------------------*/
		 List<Object> dgapplicable=todApplicable(serviceType, accountId);
		String dgAddeded="NotAdded";
		 if(dgapplicable.get(1)!=null)
		 {
			 if(dgapplicable.get(1).equals("Yes")){
				 logger.info(":::::::::::Only DG Applicable::::::::::::: ");
				 final boolean dgintreding=false;
				 List<Object> initialReadingName=entityManager.createNamedQuery("TariffCalc.GetBillInitialReadingName").setParameter("elBillId", elBillId).getResultList();
				 for (Object electricityMeterParametersEntity : initialReadingName) {
					 logger.info("electricityMeterParametersEntity"+electricityMeterParametersEntity.toString());
					boolean eString=electricityMeterParametersEntity.equals("Present  DG reading");
					logger.info("::::::::::eString::::::::"+eString);
					if(eString){
						dgAddeded="Added";
						break;
					}
				}
				 if(elBillId!=null && (meterId!=null)){
					 logger.info("::::::::::::::Bill all ready Generated:::::::::::::");
					 final double dgfrread=dgfrchangereading;
					 for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
						 
						 final Object[] values = (Object[]) iterator.next();
						 dgmetered.add(new LinkedHashMap<String, Object>() {
							private static final long serialVersionUID = 1L;
							{
								final boolean dgintreding=((String)values[0]).equalsIgnoreCase("Present  DG reading");
								logger.info("::::::::::::dgintreding:::::::"+dgintreding);
								
								if (((String) values[0]).equalsIgnoreCase("Present  DG reading")) {
									if(finalreading.size()>0){
										put("DGInitialReading",dgfrread);
									}else{
										put("DGInitialReading", (String) values[1]);	
									}	
									
									
								}
								if (((String) values[0]).equalsIgnoreCase("DG Meter Constant")) {
									put("DGMeterConstant", (String) values[1]);
								
							}
								 
							}});
					
						}
				 }else{
			
			for(Iterator<?> iterator = initialReading.iterator(); iterator.hasNext();){
				
				final Object[] values = (Object[]) iterator.next();	
				dgmetered.add(new LinkedHashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{  
				if(((String)values[0]).equalsIgnoreCase("DG Initial Reading")){
					put("DGInitialReading",(String)values[1]);
				}
				if(((String)values[0]).equalsIgnoreCase("DG Meter Constant")){
					put("DGMeterConstant",(String)values[1]);
				}
				}});
			}
			
			 }
				logger.info("dgmetered.size"+dgmetered.size()+"::::::::::::"+dgmetered.contains("DGInitialReading"));
				if(dgAddeded.equalsIgnoreCase("Added")){
					logger.info("in if block");
				}else{
					logger.info(":::::::::::::else block");
                     for(Iterator<?> iterator = initialReading.iterator(); iterator.hasNext();){
						
						final Object[] values = (Object[]) iterator.next();	
						dgmetered.add(new LinkedHashMap<String, Object>() {
						private static final long serialVersionUID = 1L;
						{  
						if(((String)values[0]).equalsIgnoreCase("DG Initial Reading")){
							put("DGInitialReading",(String)values[1]);
						}
						if(((String)values[0]).equalsIgnoreCase("DG Meter Constant")){
							put("DGMeterConstant",(String)values[1]);
						}
						}});
					}	
				}
				 
				 for(Map<String, Object> l:dgmetered){
						Set<Entry<String, Object>> s=l.entrySet();
						Iterator<Entry<String, Object>> i=s.iterator();
						logger.info("dgmetered::::::::::"+dgmetered);
						if(i.hasNext()){
							@SuppressWarnings("rawtypes")
							Map.Entry mentry2 = (Map.Entry)i.next();
							map.put(mentry2.getKey(), mentry2.getValue());
							logger.info("dgmetered::::::"+mentry2.getKey()+"mentry2.getKey()::::::"+mentry2.getValue());
							finallist.add(mentry2.getValue());
						}}
				 }
			 
			 else{
				 logger.info(":::::::::::::::::DG Not Applicable:::::::::::::::::::::::::");
			 }
		 }
		 

		return map;	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getTariffNameCalTod(){
	
		return entityManager.createNamedQuery("TariffCalc.GETTariffNameTOd").getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getServiceName(int accountd) {
		//List<Object[]> service=entityManager.createQuery("SELECT b.serviceMasterId,b.typeOfService FROM ServiceMastersEntity b WHERE b.accountObj.accountId='"+accountd+"' AND b.status='Active' AND b.serviceMasterId IN (SELECT bl.serviceMastersEntity.serviceMasterId FROM BillingWizardEntity b1 WHERE b1.serviceMetered='Yes' AND b1.accountObj.accountId='"+accountd+"')").getResultList();
		
		 return 
				 entityManager.createNamedQuery("TariffCalc.GetServiceName").setParameter("accountId", accountd).getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getRateNameCal(int eltariffId) {
		
		return entityManager.createNamedQuery("TariffCalc.GETRateName").setParameter("eltariffId", eltariffId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ELRateMaster> getRateUomCal(String ratename,int elTariffID) {
		
		return entityManager.createNamedQuery("TariffCalc.GETRateUom").setParameter("ratename", ratename).setParameter("elTariffID", elTariffID).getResultList();
	}

	private float ecAmount(ELRateMaster elRateMaster, Date startDate,Date endDate, HttpServletResponse response,float consumptionPerMonth1)
	{
		
		float totalAmount = 0.0f;
		float slabDifference =0.0f;
		float lastSlabTo = 0.0f; 
		LocalDate fromDate =  new LocalDate(startDate);
	    LocalDate toDate = new LocalDate(endDate);
	    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
	    Period difference = new Period(fromDate, toDate, monthDay);
	    float billableMonths = difference.getMonths();
	    int daysAfterMonth = difference.getDays();
	    
	     Calendar cal1 = Calendar.getInstance();
		 cal1.setTime(endDate);
		 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
		 float netMonth = daysToMonths + billableMonths;
		 netMonth = Math.round(netMonth*100)/100;
		 List<ELRateSlabs> elRateSlabsList = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
		 
		 if(elRateMaster.getRateType().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale)))
		 {
			     logger.info("I am in single slab");
				 logger.info("EC calculation");
				 
				 logger.info("Tariff effective from :"+elRateMaster.getValidFrom()+"</br>");
				 logger.info("Months :"+billableMonths +"\tDays :"+daysAfterMonth+"</br>");
				 logger.info("Net Months :"+netMonth+"</br>");
				 for (ELRateSlabs elRateSlabs : elRateSlabsList)
					 {
				    	totalAmount = totalAmount + (consumptionPerMonth1 * (elRateSlabs.getRate()/100))* netMonth;
				    	logger.info("("+consumptionPerMonth1+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((consumptionPerMonth1 * (elRateSlabs.getRate()/100)))*netMonth +"</br>");
					  }
				 totalAmount = (totalAmount > elRateMaster.getMinRate()/100) ? totalAmount : elRateMaster.getMinRate()/100; 
			 
		 }
		 else if (elRateMaster.getRateType().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale)))
		 {
			 logger.info("I am in Multi Slab");
			 logger.info("EC calculation");
			 
			 logger.info("Tariff effective from :"+elRateMaster.getValidFrom()+"</br>");
			 logger.info("Months :"+billableMonths +"\tDays :"+daysAfterMonth+"</br>");
			 logger.info("Net Months :"+netMonth+"</br>");
				 
		    	 for (ELRateSlabs elRateSlabs : elRateSlabsList)
				  {
					    if(lastSlabTo == 0)
					    {
					     slabDifference = (elRateSlabs.getSlabTo() - elRateSlabs.getSlabFrom());
					     lastSlabTo = elRateSlabs.getSlabTo();
					    }
					    else
					    {
					    	slabDifference = (elRateSlabs.getSlabTo() - lastSlabTo);
					    	lastSlabTo = elRateSlabs.getSlabTo();
					    }
						if(consumptionPerMonth1 > slabDifference)
						{
							consumptionPerMonth1 = consumptionPerMonth1 - slabDifference;
							totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()/100)) *netMonth;
							logger.info("("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((slabDifference * (elRateSlabs.getRate()/100)))*netMonth +"</br>");
						}
						else
						{
							if(consumptionPerMonth1 > 0)
							{
								totalAmount = totalAmount + (consumptionPerMonth1 * (elRateSlabs.getRate()/100))* netMonth;
								logger.info("("+consumptionPerMonth1+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((consumptionPerMonth1 * (elRateSlabs.getRate()/100)))*netMonth +"</br>");
								consumptionPerMonth1 = consumptionPerMonth1 - slabDifference;
							}
						}
				 }
		 }
		else if (elRateMaster.getRateType().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale)))
		{
			logger.info("I am in Multi Slab Telescopic ");
		}
		 else
		 {
			 logger.info("I am in Range Slab");
		 }
		return totalAmount;
	}

/******************************************* Tariff Calculation (Chandra code ends)********************************************************************/
	
	
	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.tariffManagement.TariffCalculationService#tariffTodCalculation(com.bcits.bfm.model.GenericClassForTodCalculation, javax.servlet.http.HttpServletResponse)
	 *  
	 *  For Tod Calculation
	 */
	
	@SuppressWarnings("unused")
	public HashMap<Object, Object> tariffTodCalculation(GenericClassForTodCalculation<String, String, Integer, Integer,Integer,Date, Date> calucaltion,HttpServletResponse response){
		logger.info("calucaltion.getTariffName():::::::::::::::::::"+calucaltion.getTariffName());
	     ELTariffMaster elTariffMaster = entityManager.createNamedQuery("ELTariffMaster.getTariffMasterByName",ELTariffMaster.class).setParameter("tariffName", calucaltion.getTariffName()).getSingleResult();
	     logger.info(" elTariffMaster.getElTariffID():::::::::::::::::::::::::"+ elTariffMaster.getElTariffID()+"::::::::::calucaltion.getRateName()::::::::::::::"+calucaltion.getRateName());
		 List<ELRateMaster> elRateMaster1 = entityManager.createNamedQuery("ELRateMaster.getRateId",ELRateMaster.class).setParameter("tariffMasterId", elTariffMaster.getElTariffID()).setParameter("rateName", calucaltion.getRateName()).getResultList();
     
		HashMap<Object, Object> consilatedBill=new LinkedHashMap<>();
		
		 LocalDate totalStartDate = new LocalDate(calucaltion.getStartDate());
		 LocalDate totalendDate = new LocalDate(calucaltion.getEndDate());
		 PeriodType totalmonthDay = PeriodType.yearMonthDay().withYearsRemoved();
		 Period totaldifference = new Period(totalStartDate, totalendDate, totalmonthDay);
		 int totalbillableMonths = totaldifference.getMonths();
		 int totaldaysAfterMonth = totaldifference.getDays();
		 
		 Calendar caltotal = Calendar.getInstance();
		 caltotal.setTime(calucaltion.getEndDate());
		 float daysToMonthstotal = (float)totaldaysAfterMonth / caltotal.getActualMaximum(Calendar.DAY_OF_MONTH);
		 BigDecimal bdtotal = new BigDecimal(daysToMonthstotal + totalbillableMonths);
		 bdtotal = bdtotal.setScale(2, BigDecimal.ROUND_HALF_UP);
		 float totalmonth = bdtotal.floatValue();
		 logger.info("totalmonth"+totalmonth);
		 
		 ArrayList<Date> minStartdate = new ArrayList<>();
			ArrayList<Date> maxStartdate = new ArrayList<>();	
       
		 Integer unit1=calucaltion.getConsumptionsUnitsT1();
		 Integer unit2=calucaltion.getConsumptionsUnitsT2();
		 Integer unit3=calucaltion.getConsumptionsUnitsT3();
		
		 
		 int consumptionUints=0;
		 consumptionUints=unit1+unit2+unit3;
		 float EC1=0;
		 float EC2=0;
		 float EC3=0;
		 float Totod1=0;
		 float  Tottod2=0;
		 float  Tottod3=0;
	      float totalAmount = 0;
	      float Tottod4=0;
	    
	       float tod1 = 0;
		   float tod2=0;
		   float tod3=0;
		   float temp;
	       int count1=0;
	       int count2=0;
	       int count3=0;
	       String slab1="";
	       String slab2="";
	       String slab3="";
		 logger.info("unit1>:::::::::::::::::::::"+unit1);
		 logger.info("unit2>:::::::::::::::::::::::::"+unit2);
		 logger.info("unit3>:::::::::::::::::::::::::"+unit3);
		for (ELRateMaster elRateMaster :elRateMaster1) {
			
		
		 if(elRateMaster.getTodType().equalsIgnoreCase("COMMON")){
		logger.info("::::::::::::::::::common::::::::::::");
			   ELRateSlabs rateslabs=entityManager.createNamedQuery("ELRateslab.getElrsId", ELRateSlabs.class).setParameter("elrmId",elRateMaster .getElrmid()).getSingleResult();
		 		 List<ELTodRates> todrates=entityManager.createNamedQuery("ELTodrates.getdetail",ELTodRates.class).setParameter("elrsId",rateslabs.getElrsId()).getResultList();
		 		 logger.info("size"+todrates.size());
		 		 
		for (ELTodRates elTodRates : todrates) {
			
		Timestamp fromTimem=	elTodRates.getFromTime();
		Timestamp totmem=    elTodRates.getToTime();
		logger.info("::::::::::fromTimem::::::::"+fromTimem);
		logger.info("::::::::::totmem::::::::"+totmem);
			String ftimem=getDate3( fromTimem);
		String totimem=getDate3(totmem);
	
		
		
		if(calucaltion.getStartDate().after(elTodRates.getTodValidFrom())&& calucaltion.getEndDate().before(elTodRates.getTodValidTo())){			
			
		if(ftimem.equals(messageSource.getMessage("fromTime1",null, locale))&&(totimem.equals(messageSource.getMessage("toTime1",null, locale)))){
		
			 float i=elTodRates.getIncrementalRate();
			    tod1=unit1*(i/100);
			    logger.info("in property file method");
			     logger.info("tod1>"+tod1);
			      logger.info("rate"+i+"paise");
			      float j=i/100;
			      logger.info("Rate for 1st slot:"+i+"Paise="+j+"Rs"+"<br>");
			      logger.info("tod1="+unit1+"*"+j+"="+tod1+"<br>");
			      
			      EC1=ecAmount( elRateMaster, calucaltion.getStartDate(), calucaltion.getEndDate(), response, unit1);
			      consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","("+j+"*"+unit1+")"+"="+ (tod1));
			     
		}
		if(ftimem.equals(messageSource.getMessage("fromTime2",null, locale))&&(totimem.equals(messageSource.getMessage("toTime2",null, locale)))){
			
			float i=elTodRates.getIncrementalRate();
			tod2=unit2*i;
			 logger.info("tod2>"+tod2);
		      logger.info("rate"+i+"paise");
		      float j=i/100;
		      logger.info("Rate for 2nd slot:"+i+"Paise="+j+"Rs"+"<br>");
		      logger.info("tod2="+unit2+"*"+j+"="+tod2+"<br>");
		      EC2=ecAmount( elRateMaster, calucaltion.getStartDate(), calucaltion.getEndDate(), response, unit2);
		      consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","("+j+"*"+unit2+")"+"="+ (tod2));
		     
		}
		if(ftimem.equals(messageSource.getMessage("fromTime3",null, locale))&&(totimem.equals(messageSource.getMessage("toTime3",null, locale)))){
			
	       float i=elTodRates.getIncrementalRate();			
			tod3=unit3*(i/100);
			 logger.info("tod3>"+tod3);
		      logger.info("rate"+i+"paise");
		      float j=i/100;
		      logger.info("Rate for 3rd slot:"+i+"Paise="+j+"Rs"+"<br>");
		      logger.info("tod3="+unit3+"*"+j+"="+tod3+"<br>");
		    
		      EC3=ecAmount( elRateMaster, calucaltion.getStartDate(), calucaltion.getEndDate(), response, unit3);
		      consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","=("+j+"*"+unit3+")"+"="+ (tod3));
		}
		if(tod1!=0 & tod3!=0){
		Totod1=tod1+tod2+tod3;
		logger.info("TOD FOR 1ST Tariff: "+Totod1+"<br>");
		}
		}
		}
	
		for (ELTodRates elTodRates : todrates) {
			Timestamp fromTimem=	elTodRates.getFromTime();
			Timestamp totmem=    elTodRates.getToTime();
				String ftimem=getDate3( fromTimem);
			String totimem=getDate3(totmem);
			
			
			
			if((calucaltion.getEndDate().after(elTodRates.getTodValidFrom())) && (elTodRates.getTodValidTo().before(calucaltion.getEndDate())))	{
			if(( elTodRates.getTodValidTo().after(calucaltion.getStartDate()))){
		
			logger.info("main block"+"elTodRates.getValidFrom()>"+elTodRates.getTodValidFrom()+"elTodRates.getValidTo()>"+elTodRates.getTodValidTo());
			 LocalDate startDate = new LocalDate(calucaltion.getStartDate());
				 LocalDate endDate = new LocalDate(elTodRates.getTodValidTo());
				 PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
				 Period difference = new Period(startDate, endDate, monthDay);
				 int billableMonths = difference.getMonths();
				 int daysAfterMonth = difference.getDays();
				 
				 Calendar cal = Calendar.getInstance();
				 cal.setTime(elTodRates.getTodValidTo());
				 float daysToMonths = (float)daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				 float netMonth = daysToMonths + billableMonths;
				 netMonth = Math.round(netMonth*100)/100;
				
				if((calucaltion.getStartDate().after(elTodRates.getTodValidFrom())) && (elTodRates.getTodValidTo().before(calucaltion.getEndDate()))){
					
					 logger.info("-----------------------------"+"<br>");
					 logger.info("Net month From "+startDate+" to"+ endDate+"is : "+netMonth+"<br>");
					 Date d=calucaltion.getStartDate();
					 
					if(ftimem.equals(messageSource.getMessage("fromTime1",null, locale))&&(totimem.equals(messageSource.getMessage("toTime1",null, locale)))){
						 		
						float percentage=((netMonth*100)/totalmonth);
						
						 float i=elTodRates.getIncrementalRate();
						  float j=i/100;   
						    tod1=((unit1*(netMonth*100/totalmonth))/100)*(i/100);
						    logger.info("in property file method");
						     logger.info("tod1>"+tod1);
						      logger.info("rate"+i+"paise");
						      logger.info("Rate for 1st slot:"+i+"Paise="+j+"Rs"+"<br>");
						      logger.info("Tod for 1st slot:"+"(("+unit1+"*"+percentage+"))/100)*("+j+")="+tod1+"<br>");
						      unit1=(int) ((netMonth*unit1)/totalmonth);
						      EC1=ecAmount( elRateMaster, calucaltion.getStartDate(), calucaltion.getEndDate(), response, unit1);
						      consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","(Tod Rate*Unit)=("+j+"*"+unit1+")"+"="+ (tod1));
					}
					if(ftimem.equals(messageSource.getMessage("fromTime2",null, locale))&&(totimem.equals(messageSource.getMessage("toTime2",null, locale)))){
						
						float percentage=((netMonth*100)/totalmonth);
						float i=elTodRates.getIncrementalRate();
						 float j=i/100;
						tod2=((unit2*(netMonth*100/totalmonth))/100)*i;
						 logger.info("tod2>"+tod2);
					      logger.info("rate"+i+"paise");
					      logger.info("Rate for 2nd slot:"+i+"Paise="+j+"Rs"+"<br>");
					      logger.info("Tod for 2nd slot:"+"(("+unit2+"*"+percentage+"))/100)*("+j+")="+tod2+"<br>");
					      unit2=(int) ((netMonth*unit2)/totalmonth);
					      EC2=ecAmount( elRateMaster, calucaltion.getStartDate(), calucaltion.getEndDate(), response, unit2);
					      consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","("+j+"*"+unit2+")"+"="+ (tod2));
					}
					if(ftimem.equals(messageSource.getMessage("fromTime3",null, locale))&&(totimem.equals(messageSource.getMessage("toTime3",null, locale)))){
						float percentage=((netMonth*100)/totalmonth);
				       float i=elTodRates.getIncrementalRate();	
				       float j=i/100;
						tod3=((unit3*(netMonth*100/totalmonth))/100)*(i/100);
						 logger.info("tod3>"+tod3);
					      logger.info("rate"+i+"paise");
					      logger.info("Rate for 3rd slot:"+i+"Paise="+j+"Rs"+"<br>");
					      logger.info("Tod for 3rd slot:"+"(("+unit3+"*"+percentage+"))/100)*("+j+")="+tod3+"<br>");
					      unit3=(int) ((netMonth*unit3)/totalmonth);
					      EC3=ecAmount( elRateMaster, calucaltion.getStartDate(), calucaltion.getEndDate(), response, unit3);
					      consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","(Tod Rate*Unit)=("+j+"*"+unit3+")"+"="+ (tod3));
					}
				
				if(tod1!=0 & tod3!=0){
				Totod1=tod1+tod2+tod3;
				logger.info("<html><span style='color:#8B0000;'>TOD  Effective From Date "+d+" is: "+Totod1+"<br></span></html>");
				
				tod1=0;
				tod2=0;
				tod3=0;
				}
				}
			
			}
			
			}
		
		}
		
		  float tod11 = 0;
		  float tod22=0;
		  float tod33=0;
		for (ELTodRates elTodRates : todrates) {
			Timestamp fromTimem=	elTodRates.getFromTime();
			Timestamp totmem=    elTodRates.getToTime();
				String ftimem=getDate3( fromTimem);
			String totimem=getDate3(totmem);
			
			if(elTodRates.getTodValidFrom().after(calucaltion.getStartDate()) && (elTodRates.getTodValidTo().before(calucaltion.getEndDate()))){
			
				java.sql.Date validdate=elTodRates.getTodValidFrom();
				logger.info("validdate"+validdate);
				Date x=elTodRates.getTodValidFrom();;
				while(x==validdate){
				
				logger.info("in last method of class##########"+elTodRates.getTodValidFrom()+"<validto>"+elTodRates.getTodValidTo());
				 LocalDate startDate = new LocalDate(elTodRates.getTodValidFrom());
				 LocalDate endDate = new LocalDate(elTodRates.getTodValidTo());
				 PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
				 Period difference = new Period(startDate, endDate, monthDay);
				 int billableMonths = difference.getMonths();
				 int daysAfterMonth = difference.getDays();
				 
				 Calendar cal = Calendar.getInstance();
				 cal.setTime(elTodRates.getTodValidTo());
				 float daysToMonths = (float)daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				 float netMonth = daysToMonths + billableMonths;
				 netMonth = Math.round(netMonth*100)/100;			 
				 logger.info("netMonth>>"+netMonth);				
				 logger.info("Net month From "+startDate+" to"+ endDate+"is : "+netMonth+"<br>");
				
				if(ftimem.equals(messageSource.getMessage("fromTime1",null, locale))&&(totimem.equals(messageSource.getMessage("toTime1",null, locale)))){
					
					
					 float i=		elTodRates.getIncrementalRate();
					
					    tod11=((unit1*(netMonth*100/totalmonth))/100)*(i/100);
					    float j=i/100;
					    float percentage=((netMonth*100)/totalmonth); 
					    logger.info("in property file method");
					     logger.info("tod1>"+tod1);
					      logger.info("rate"+i+"paise");
					      logger.info("Rate for 1st slot:"+i+"Paise="+j+"Rs"+"<br>");
					      logger.info("Tod for 1st slot:"+"(("+unit2+"*"+percentage+"))/100)*("+j+")="+tod11+"<br>");
					      unit1=(int) ((netMonth*unit1)/totalmonth);	
					      EC1=ecAmount( elRateMaster, calucaltion.getStartDate(), calucaltion.getEndDate(), response, unit1);
					      consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","(Tod Rate*Unit)=("+j+"*"+unit1+")"+"="+ (tod11));
				}
				if(ftimem.equals(messageSource.getMessage("fromTime2",null, locale))&&(totimem.equals(messageSource.getMessage("toTime2",null, locale)))){
					
					float i=elTodRates.getIncrementalRate();
					
					tod22=((unit2*(netMonth*100/totalmonth))/100)*i;
					 float j=i/100;
					    float percentage=((netMonth*100)/totalmonth); 
					 logger.info("tod2>"+tod22);
				      logger.info("rate"+i+"paise");
				      logger.info("Rate for 3rd slot:"+i+"Paise="+j+"Rs"+"<br>");
				      logger.info("Tod for 3rd slot:"+"(("+unit2+"*"+percentage+"))/100)*("+j+")="+tod22+"<br>");
				      unit2=(int) ((netMonth*unit2)/totalmonth);	
				      EC2=ecAmount( elRateMaster, calucaltion.getStartDate(), calucaltion.getEndDate(), response, unit2);
				      consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","("+j+"*"+unit2+")"+"="+ (tod22));
				}
				if(ftimem.equals(messageSource.getMessage("fromTime3",null, locale))&&(totimem.equals(messageSource.getMessage("toTime3",null, locale)))){
					
			       float i=elTodRates.getIncrementalRate();			
					
					tod33=((unit3*(netMonth*100/totalmonth))/100)*(i/100);
					 logger.info("tod3>"+tod3);
				      logger.info("rate"+i+"paise");
				      float j=i/100;
					    float percentage=((netMonth*100)/totalmonth); 
					     logger.info("Rate for 3rd  slot:"+i+"Paise="+j+"Rs"+"<br>");
					      logger.info("Tod for 3rd slot:"+"(("+unit3+"*"+percentage+"))/100)*("+j+")="+tod33+"<br>");
					      unit3=(int) ((netMonth*unit3)/totalmonth);
					      EC3=ecAmount( elRateMaster, calucaltion.getStartDate(), calucaltion.getEndDate(), response, unit3);
					      consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","("+j+"*"+unit3+")"+"="+ (tod33));
				}
				break;
				}
				logger.info(tod11+"tod11");
				logger.info(tod33+"tod22");
				if(tod11!=0 & tod33>0){
				temp=Tottod4;  
				Tottod3=tod11+tod22+tod33;
				Tottod4=Tottod3+temp;
				
				logger.info("temp>>>>>>>>"+temp);
				logger.info("Tottod3"+Tottod3);
				logger.info("Tottod4"+Tottod4);
				
				logger.info("<html><span style='color:#FF0000;'>TOD  Effective From Date "+x+" is: "+Tottod3+"<br></span></html>");
			
				tod11=0;tod33=0;tod22=0;
				
			}
				
			}
			}
				
		
		for (ELTodRates elTodRates : todrates) {
			Timestamp fromTimem=	elTodRates.getFromTime();
			Timestamp totmem=    elTodRates.getToTime();
				String ftimem=getDate3( fromTimem);
			String totimem=getDate3(totmem);
		
			if((elTodRates.getTodValidFrom().after(calucaltion.getStartDate()))&&(calucaltion.getEndDate().before(elTodRates.getTodValidTo())) ){
			
				logger.info("in 2nd if"+"elTodRates.getValidFrom()>"+elTodRates.getTodValidFrom()+"elTodRates.getValidTo()>"+elTodRates.getTodValidTo());
				 LocalDate startDate = new LocalDate(elTodRates.getTodValidFrom());
				 LocalDate endDate = new LocalDate(calucaltion.getEndDate());
				 PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
				 Period difference = new Period(startDate, endDate, monthDay);
				 int billableMonths = difference.getMonths();
				 int daysAfterMonth = difference.getDays();
				 
				 Calendar cal = Calendar.getInstance();
				 cal.setTime(elTodRates.getTodValidTo());
				 float daysToMonths = (float)daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				 float netMonth = daysToMonths + billableMonths;
				 netMonth = Math.round(netMonth*100)/100;
				 logger.info("netMonth>>"+netMonth);
				 
				logger.info("in 3rd method");
				if(calucaltion.getEndDate().after(elTodRates.getTodValidFrom())){
					
					Date y=elTodRates.getTodValidFrom();
					logger.info("-----------------------------"+"<br>");
					 logger.info("Net month From"+startDate+"to"+ endDate+"is :"+netMonth+"<br>");
				if(ftimem.equals(messageSource.getMessage("fromTime1",null, locale))&&(totimem.equals(messageSource.getMessage("toTime1",null, locale)))){
				
					 float i=		elTodRates.getIncrementalRate();
					    tod1=((unit1*(netMonth*100/totalmonth))/100)*(i/100);
					    float j=i/100;
					    float percentage=((netMonth*100)/totalmonth); 
					    logger.info("in property file method");
					     logger.info("tod1>"+tod1);
					      logger.info("rate"+i+"paise");
					      logger.info("Rate for 1st slot:"+i+"Paise="+j+"Rs"+"<br>");
					      logger.info("Tod for 1st slot:"+"(("+unit2+"*"+percentage+"))/100)*("+j+")="+tod1+"<br>");
					      unit1=(int) ((netMonth*unit1)/totalmonth);	
					      EC1=ecAmount( elRateMaster, calucaltion.getStartDate(), calucaltion.getEndDate(), response, unit1);
					      consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","("+j+"*"+unit1+")"+"="+ (tod1));
					      
				}
				if(ftimem.equals(messageSource.getMessage("fromTime2",null, locale))&&(totimem.equals(messageSource.getMessage("toTime2",null, locale)))){
					
					 float i=elTodRates.getIncrementalRate();
					tod2=((unit2*(netMonth*100/totalmonth))/100)*i;
					  float j=i/100;
					    float percentage=((netMonth*100)/totalmonth); 
					 logger.info("tod2>"+tod2);
				      logger.info("rate"+i+"paise");
				      logger.info("Rate for 3rd slot:"+i+"Paise="+j+"Rs"+"<br>");
				      logger.info("Tod for 3rd slot:"+"(("+unit2+"*"+percentage+"))/100)*("+j+")="+tod2+"<br>");
				      unit2=(int) ((netMonth*unit2)/totalmonth);	
				      EC2=ecAmount( elRateMaster, calucaltion.getStartDate(), calucaltion.getEndDate(), response, unit2);
				      consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","(Tod Rate*Unit)=("+j+"*"+unit2+")"+"="+ (tod2));
				}
				if(ftimem.equals(messageSource.getMessage("fromTime3",null, locale))&&(totimem.equals(messageSource.getMessage("toTime3",null, locale)))){
					
			         float i=elTodRates.getIncrementalRate();			
					  tod3=((unit3*(netMonth*100/totalmonth))/100)*(i/100);
					  logger.info("tod3>"+tod3);
				      logger.info("rate"+i+"paise");
				       float j=i/100;
					   float percentage=((netMonth*100)/totalmonth); 
					     logger.info("Rate for 3rd  slot:"+i+"Paise="+j+"Rs"+"<br>");
					      logger.info("Tod for 3rd slot:"+"(("+unit3+"*"+percentage+"))/100)*("+j+")="+tod3+"<br>");
					      unit3=(int) ((netMonth*unit3)/totalmonth);
					      EC3=ecAmount( elRateMaster, calucaltion.getStartDate(), calucaltion.getEndDate(), response, unit3);
					      consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","("+j+"*"+unit3+")"+"="+ (tod3));
				}
			if(tod1!=0 & tod3!=0){
				   Tottod2=tod1+tod2+tod3; 
					
				
					logger.info("<html><span style='color:#8B0000;'>TOD  Effective From Date "+y+" is: "+Tottod2+"<br></span></html>");
					tod1=0;
					tod2=0;
					tod3=0;
			}
			}
		
			}
		
		    }
		//totalAmount=EC1+EC2+EC3;
		float uomvalus=(unit1+unit2+unit3);
		Float ActulEC=ecAmount( elRateMaster, calucaltion.getStartDate(), calucaltion.getEndDate(), response, unit1+unit2+unit3);
		logger.info("::::::::::ACtual Ec:::::::::::"+ActulEC);
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		consolidatedBill = calculationController
				.tariffAmount(elRateMaster,
						calucaltion.getStartDate(), calucaltion.getEndDate(),
						uomvalus, 0,elRateMaster.getRateNameCategory());
		
		for (Entry<Object, Object> map : consolidatedBill.entrySet()) {

			if (map.getKey().equals("totalAmount")) {
				totalAmount = (Float) map.getValue();
			}
		    } 
		logger.info(":::::::::::::::totalamount::::::::::"+totalAmount);
		 }
		 if(elRateMaster.getTodType().equalsIgnoreCase("SLAB WISE")){
			 logger.info(":::::::::::::::SLAB WISE::::::::::::::::::");		
		      
			 ELRateSlabs rateslabs=entityManager.createNamedQuery("ELRateslab.getElrsId", ELRateSlabs.class).setParameter("elrmId",elRateMaster .getElrmid()).getSingleResult();
			 	logger.info("elRateMaster .getElrmid()>"+elRateMaster .getElrmid());
			 	int elrsiD = rateslabs.getElrsId();
			 	logger.info("elrsiD>"+elrsiD);
			 
		 		List<ELTodRates> todrates=entityManager.createNamedQuery("ELTodrates.getdetail",ELTodRates.class).setParameter("elrsId", elrsiD).getResultList();	
		 	
		 		 for (ELTodRates elTodRates : todrates) 
					{
						minStartdate.add(elTodRates.getTodValidFrom());
						maxStartdate.add(elTodRates .getTodValidTo());
					}
			    
				  Collections.sort(minStartdate);
				  Collections.sort(maxStartdate);
				  
				  
				  if (!(minStartdate.get(0).compareTo(calucaltion.getStartDate()) <=0 ))
				  {
					  logger.info("minStartdate.get(0)"+minStartdate.get(0));
					  logger.info("calucaltion.getStartDate()"+calucaltion.getStartDate());
					  logger.info("calucaltion.End()"+calucaltion.getEndDate());
					  logger.info("Start Date is not inside the tariff dates"+"</br>");
			      }
				  else if (calucaltion.getEndDate().compareTo(maxStartdate.get(maxStartdate.size()-1)) > 0)
				  {
					  logger.info("End Date is not inside the tariff dates"+"</br>");
			      }else{
		 		
		 		
		 		for (ELTodRates elTodRates : todrates) {
		 			Timestamp fromTimem=	elTodRates.getFromTime();
					Timestamp totmem=    elTodRates.getToTime();
						String ftimem=getDate3( fromTimem);
					String totimem=getDate3(totmem);
					if((calucaltion.getStartDate().after(elTodRates.getTodValidFrom())||(calucaltion.getStartDate().equals(elTodRates.getTodValidFrom()))) && (calucaltion.getEndDate().before(elTodRates.getTodValidTo()))){
						logger.info("-----------------------------"+"<br>");
					
						if(ftimem.equals(messageSource.getMessage("fromTime1",null, locale))&&(totimem.equals(messageSource.getMessage("toTime1",null, locale)))){
			 			
							EC1=ecAmount( elRateMaster, calucaltion.getStartDate(), calucaltion.getEndDate(), response, unit1);
			 				logger.info("elTodRates1>"+elTodRates.getIncrementalRate());
			 				logger.info("EC1 For Unit:"+unit1+":"+EC1+"<br>");
			 				logger.info("elTodRates1>"+elTodRates.getIncrementalRate()+"<br>");
				 			float ratepercentage=elTodRates.getIncrementalRate();
				 			tod1=((ratepercentage*EC1)/100);
				 			logger.info("TOD From"+ftimem+" to "+totimem+":"+tod1+"<br>");
				 			consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","(Tod Rate*EC)/100=("+elTodRates.getIncrementalRate()+"*"+EC1+")/100"+"="+ ((ratepercentage*EC1)/100));
				 			slab1="Tod Rate b/w"+ftimem+"to"+totimem+":(Tod Rate*EC)/100="+ ((ratepercentage*EC1)/100);
				 			logger.info("Tod1>"+tod1);
				 			count1++;
					}
					if(ftimem.equals(messageSource.getMessage("fromTime2",null, locale))&&(totimem.equals(messageSource.getMessage("toTime2",null, locale)))){
					
						EC2=ecAmount( elRateMaster, calucaltion.getStartDate(), calucaltion.getEndDate(), response, unit2);
		 				logger.info("elTodRates3>"+elTodRates.getIncrementalRate());
		 				logger.info("EC2 For Unit:"+unit2+":"+EC2+"<br>");
			 			logger.info("elTodRates2>"+elTodRates.getIncrementalRate()+"<br>");
			 			float ratepercentage=elTodRates.getIncrementalRate();
			 			tod2=((ratepercentage*EC2)/100);
			 			consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","(Tod Rate*EC)/100=("+elTodRates.getIncrementalRate()+"*"+EC2+")/100"+"="+ ((ratepercentage*EC2)/100));
			 			logger.info("TOD From"+ftimem+" to "+totimem+":"+tod2+"<br>");
			 			slab2="Tod Rate b/w"+ftimem+"to"+totimem+":(Tod Rate*EC)/100="+ ((ratepercentage*EC2)/100);
			 			logger.info("Tod2>"+tod2);
			 			count2++;
					
					}
					if(ftimem.equals(messageSource.getMessage("fromTime3",null, locale))&&(totimem.equals(messageSource.getMessage("toTime3",null, locale)))){
					
						EC3=ecAmount( elRateMaster, calucaltion.getStartDate(), calucaltion.getEndDate(), response, unit3);
		 				logger.info("elTodRates2>"+elTodRates.getIncrementalRate());
		 				logger.info("EC3 For Unit:"+unit3+":"+EC3+"<br>");
		 				logger.info("elTodRates2>"+elTodRates.getIncrementalRate()+"<br>");
			 			float ratepercentage=elTodRates.getIncrementalRate();
			 			tod3=((ratepercentage*EC3)/100);
			 			logger.info("TOD From"+ftimem+" to "+totimem+":"+tod3+"<br>");
			 			consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","(Tod Rate*EC)/100=("+elTodRates.getIncrementalRate()+"*"+EC3+")/100"+"="+ ((ratepercentage*EC3)/100));
			 			slab3="Tod Rate b/w"+ftimem+"to"+totimem+":(Tod Rate*EC)/100="+ ((ratepercentage*EC3)/100);
			 			logger.info("Tod3>"+tod3);
						
						count3++;
						
					}
					if(count1!=0 & count2!=0 & count3!=0){
					Totod1=tod1+tod2+tod3;
					logger.info("TOD FOR 1ST Tariff: "+Totod1+"<br>");
					count1=0;
					count2=0;
					count3=0;
					tod1=0;
					tod2=0;
					tod3=0;
					}
					}
					}
				
					for (ELTodRates elTodRates : todrates) {
						Timestamp fromTimem=	elTodRates.getFromTime();
						Timestamp totmem=    elTodRates.getToTime();
							String ftimem=getDate3( fromTimem);
						String totimem=getDate3(totmem);
					
						if((calucaltion.getEndDate().after(elTodRates.getTodValidFrom())) && (elTodRates.getTodValidTo().before(calucaltion.getEndDate())))	{
						if(( elTodRates.getTodValidTo().after(calucaltion.getStartDate()))){
					
						logger.info("main block"+"elTodRates.getValidFrom()>"+elTodRates.getTodValidFrom()+"elTodRates.getValidTo()>"+elTodRates.getTodValidTo());
						 LocalDate startDate = new LocalDate(calucaltion.getStartDate());
							 LocalDate endDate = new LocalDate(elTodRates.getTodValidTo());
							 PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
							 Period difference = new Period(startDate, endDate, monthDay);
							 int billableMonths = difference.getMonths();
							 int daysAfterMonth = difference.getDays();
							 
							 Calendar cal = Calendar.getInstance();
							 cal.setTime(elTodRates.getTodValidTo());
							 float daysToMonths = (float)daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
							 float netMonth = daysToMonths + billableMonths;
							 netMonth = Math.round(netMonth*100)/100;
						
							if((calucaltion.getStartDate().after(elTodRates.getTodValidFrom())  )&& (elTodRates.getTodValidTo().before(calucaltion.getEndDate()))){
								
								 logger.info("-----------------------------"+"<br>");
								 logger.info("Net month From "+startDate+" to"+ endDate+"is : "+netMonth+"<br>");
								 Date d=calucaltion.getStartDate();
								 Date y=elTodRates.getTodValidTo();
								 
								 if(ftimem.equals(messageSource.getMessage("fromTime1",null, locale))&&(totimem.equals(messageSource.getMessage("toTime1",null, locale)))){
						 			
									   unit1=(int) ((netMonth*unit1)/totalmonth);
									   logger.info("unit1"+unit1+"<br>");
									 
									    EC1=ecAmount( elRateMaster, d, y, response, unit1);
						 				logger.info("elTodRates1>"+elTodRates.getIncrementalRate());
						 				logger.info("EC1 For Unit:"+unit1+":"+EC1+"<br>");
						 				logger.info("elTodRates1>"+elTodRates.getIncrementalRate()+"<br>");
							 			float ratepercentage=elTodRates.getIncrementalRate();
							 			tod1=((ratepercentage*EC1)/100);
							 			logger.info("TOD From"+ftimem+" to "+totimem+":"+tod1+"<br>");
							 			logger.info("Tod1>"+tod1);
							 			consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","(Tod Rate*EC)/100=("+elTodRates.getIncrementalRate()+"*"+EC1+")/100"+"="+ ((ratepercentage*EC1)/100));
							 			count1++;
								}
								if(ftimem.equals(messageSource.getMessage("fromTime2",null, locale))&&(totimem.equals(messageSource.getMessage("toTime2",null, locale)))){
									 unit2=(int) ((netMonth*unit2)/totalmonth);
									EC2=ecAmount( elRateMaster, d, y, response, unit2);
					 				logger.info("elTodRates3>"+elTodRates.getIncrementalRate());
					 				logger.info("EC2 For Unit:"+unit2+":"+EC2+"<br>");
						 			logger.info("elTodRates3>"+elTodRates.getIncrementalRate()+"<br>");
						 			float ratepercentage=elTodRates.getIncrementalRate();
						 			tod2=((ratepercentage*EC2)/100);
						 			logger.info("TOD From"+ftimem+" to "+totimem+":"+tod2+"<br>");
						 			logger.info("Tod2>"+tod2);
						 			consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","(Tod Rate*EC)/100=("+elTodRates.getIncrementalRate()+"*"+EC2+")/100"+"="+ ((ratepercentage*EC2)/100));
						 			count2++;
								
								}
								if(ftimem.equals(messageSource.getMessage("fromTime3",null, locale))&&(totimem.equals(messageSource.getMessage("toTime3",null, locale)))){
								
									 unit3=(int) ((netMonth*unit3)/totalmonth);
									EC3=ecAmount( elRateMaster, d, y, response, unit3);
					 				logger.info("elTodRates2>"+elTodRates.getIncrementalRate());
					 				logger.info("EC3 For Unit:"+unit3+":"+EC3+"<br>");
					 				logger.info("elTodRates2>"+elTodRates.getIncrementalRate()+"<br>");
						 			float ratepercentage=elTodRates.getIncrementalRate();
						 			tod3=((ratepercentage*EC3)/100);
						 			logger.info("TOD From"+ftimem+" to "+totimem+":"+tod3+"<br>");
						 			logger.info("Tod3>"+tod3);
						 			consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","(Tod Rate*EC)/100=("+elTodRates.getIncrementalRate()+"*"+EC3+")/100"+"="+ ((ratepercentage*EC3)/100));
						 			count3++;
								}
							if( count1!=0 & count2!=0 & count3!=0){

							Totod1=tod1+tod2+tod3;
							logger.info("<html><span style='color:#8B0000;'>TOD  Effective From Date "+d+" is: "+Totod1+"<br></span></html>");
							
							count1=0;	
							count2=0;
							count3=0;
							tod1=0;
							tod2=0;
							tod3=0;
							}
							}
						
						}
						
						}
					
					}
					
					  float tod11 = 0;
					  float tod22=0;
					  float tod33=0;
					for (ELTodRates elTodRates : todrates) {
						Timestamp fromTimem=	elTodRates.getFromTime();
						Timestamp totmem=    elTodRates.getToTime();
							String ftimem=getDate3( fromTimem);
						String totimem=getDate3(totmem);
						if(elTodRates.getTodValidFrom().after(calucaltion.getStartDate()) && (elTodRates.getTodValidTo().before(calucaltion.getEndDate()))){
						
							java.sql.Date validdate=elTodRates.getTodValidFrom();
							logger.info("validdate"+validdate);
							Date x=elTodRates.getTodValidFrom();;
							if(x==validdate){
							
							logger.info("in last method of class##########"+elTodRates.getTodValidFrom()+"<validto>"+elTodRates.getTodValidTo());
							 LocalDate startDate = new LocalDate(elTodRates.getTodValidFrom());
							 LocalDate endDate = new LocalDate(elTodRates.getTodValidTo());
							 PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
							 Period difference = new Period(startDate, endDate, monthDay);
							 int billableMonths = difference.getMonths();
							 int daysAfterMonth = difference.getDays();
							 
							 Calendar cal = Calendar.getInstance();
							 cal.setTime(elTodRates.getTodValidTo());
							 float daysToMonths = (float)daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
							 float netMonth = daysToMonths + billableMonths;
							 netMonth = Math.round(netMonth*100)/100;
						      Date y=elTodRates.getTodValidTo();
							
							if(ftimem.equals(messageSource.getMessage("fromTime1",null, locale))&&(totimem.equals(messageSource.getMessage("toTime1",null, locale)))){
								
								
								if(ftimem.equals(messageSource.getMessage("fromTime1",null, locale))&&(totimem.equals(messageSource.getMessage("toTime1",null, locale)))){
					 				
									unit1=(int) ((netMonth*unit1)/totalmonth);

									EC1=ecAmount( elRateMaster, x, y, response, unit1);
					 				logger.info("elTodRates1>"+elTodRates.getIncrementalRate());
					 				logger.info("EC1 For Unit:"+unit1+":"+EC1+"<br>");
					 				logger.info("elTodRates1>"+elTodRates.getIncrementalRate()+"<br>");
						 			float ratepercentage=elTodRates.getIncrementalRate();
						 			tod11=((ratepercentage*EC1)/100);
						 			logger.info("TOD From"+ftimem+" to "+totimem+":"+tod1+"<br>");
						 			logger.info("Tod1>"+tod1);
						 			consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","(Tod Rate*EC)/100=("+elTodRates.getIncrementalRate()+"*"+EC1+")/100"+"="+ ((ratepercentage*EC1)/100));
						 			count1++;
							}
							if(ftimem.equals(messageSource.getMessage("fromTime2",null, locale))&&(totimem.equals(messageSource.getMessage("toTime2",null, locale)))){
								
								 unit2=(int) ((netMonth*unit2)/totalmonth);
								EC2=ecAmount( elRateMaster, x, y, response, unit2);
				 				logger.info("elTodRates3>"+elTodRates.getIncrementalRate());
				 				logger.info("EC2 For Unit:"+unit2+":"+EC2+"<br>");
					 			logger.info("elTodRates3>"+elTodRates.getIncrementalRate()+"<br>");
					 			float ratepercentage=elTodRates.getIncrementalRate();
					 			tod22=((ratepercentage*EC2)/100);
					 			logger.info("TOD From"+ftimem+" to "+totimem+":"+tod2+"<br>");
					 			logger.info("Tod1>"+tod2);
					 			consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","(Tod Rate*EC)/100=("+elTodRates.getIncrementalRate()+"*"+EC2+")/100"+""+ ((ratepercentage*EC2)/100));
					 			count2++;
							
							}
							if(ftimem.equals(messageSource.getMessage("fromTime3",null, locale))&&(totimem.equals(messageSource.getMessage("toTime3",null, locale)))){
							
								 unit3=(int) ((netMonth*unit3)/totalmonth);
					 		
								EC3=ecAmount( elRateMaster, x, y, response, unit3);
				 				logger.info("elTodRates2>"+elTodRates.getIncrementalRate());
				 				logger.info("EC3 For Unit:"+unit3+":"+EC3+"<br>");
				 				logger.info("elTodRates2>"+elTodRates.getIncrementalRate()+"<br>");
					 			float ratepercentage=elTodRates.getIncrementalRate();
					 			tod33=((ratepercentage*EC3)/100);
					 			logger.info("TOD From"+ftimem+" to "+totimem+":"+"<br>");
					 			logger.info("Tod3>"+tod3);
					 			consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","(Tod Rate*EC)/100=("+elTodRates.getIncrementalRate()+"*"+EC3+")/100"+""+ ((ratepercentage*EC3)/100));
					 			count3++;
							}
							break;
							}
							logger.info(tod11+"tod11");
							logger.info(tod33+"tod22");
							if(count1!=0 & count2!=0 & count3!=0){
							temp=Tottod4;  
							Tottod3=tod11+tod22+tod33;
							Tottod4=Tottod3+temp;
							
							logger.info("temp>>>>>>>>"+temp);
							logger.info("Tottod3"+Tottod3);
							logger.info("Tottod4"+Tottod4);
							
							logger.info("<html><span style='color:#FF0000;'>TOD  Effective From Date "+x+" is: "+Tottod3+"<br></span></html>");
						
							tod11=0;tod33=0;tod22=0;
							count1=0;	
							count2=0;
							count3=0;
							
							
							}
							
						}
						
						}}				
						
					for (ELTodRates elTodRates : todrates) {
						Timestamp fromTimem=	elTodRates.getFromTime();
						Timestamp totmem=    elTodRates.getToTime();
							String ftimem=getDate3( fromTimem);
						String totimem=getDate3(totmem);
					
						
						
						if((elTodRates.getTodValidFrom().after(calucaltion.getStartDate()))&&(calucaltion.getEndDate().before(elTodRates.getTodValidTo())) ){
						
							
							if(calucaltion.getEndDate().after(elTodRates.getTodValidFrom())){
								
								logger.info("in 2nd if"+"elTodRates.getValidFrom()>"+elTodRates.getTodValidFrom()+"elTodRates.getValidTo()>"+elTodRates.getTodValidTo());
								 LocalDate startDate = new LocalDate(elTodRates.getTodValidFrom());
								 LocalDate endDate = new LocalDate(calucaltion.getEndDate());
								 PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
								 Period difference = new Period(startDate, endDate, monthDay);
								 int billableMonths = difference.getMonths();
								 int daysAfterMonth = difference.getDays();
								 
								 Calendar cal = Calendar.getInstance();
								 cal.setTime(elTodRates.getTodValidTo());
								 float daysToMonths = (float)daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
								 float netMonth = daysToMonths + billableMonths;
								 netMonth = Math.round(netMonth*100)/100;
								Date y=elTodRates.getTodValidFrom();
								Date z=calucaltion.getEndDate();
								logger.info("-----------------------------"+"<br>");
								 logger.info("Net month From"+startDate+"to"+ endDate+"is :"+netMonth+"<br>");
								 if(ftimem.equals(messageSource.getMessage("fromTime1",null, locale))&&(totimem.equals(messageSource.getMessage("toTime1",null, locale)))){
						 				
									 unit1=(int) ((netMonth*unit1)/totalmonth);	
									 EC1=ecAmount( elRateMaster, y, z, response, unit1);
						 				logger.info("elTodRates1>"+elTodRates.getIncrementalRate());
						 				logger.info("EC1 For Unit:"+unit1+":"+EC1+"<br>");
						 				logger.info("elTodRates1>"+elTodRates.getIncrementalRate()+"<br>");
							 			float ratepercentage=elTodRates.getIncrementalRate();
							 			tod1=((ratepercentage*EC1)/100);
							 			logger.info("TOD From"+ftimem+" to "+totimem+":"+tod1+"<br>");
							 			logger.info("Tod1>"+tod1);
							 			consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","(Tod Rate*EC)/100=("+elTodRates.getIncrementalRate()+"*"+EC1+")/100"+"="+ ((ratepercentage*EC1)/100));
							 			count1++;
								}
								if(ftimem.equals(messageSource.getMessage("fromTime2",null, locale))&&(totimem.equals(messageSource.getMessage("toTime2",null, locale)))){
									
									unit2=(int) ((netMonth*unit2)/totalmonth);
									
									EC2=ecAmount( elRateMaster, y, z, response, unit2);
					 				logger.info("elTodRates3>"+elTodRates.getIncrementalRate());
					 				logger.info("EC2 For Unit:"+unit2+":"+EC2+"<br>");
						 			logger.info("elTodRates3>"+elTodRates.getIncrementalRate()+"<br>");
						 			float ratepercentage=elTodRates.getIncrementalRate();
						 			tod2=((ratepercentage*EC2)/100);
						 			logger.info("TOD From"+ftimem+" to "+totimem+":"+tod2+"<br>");
						 			logger.info("Tod1>"+tod2);
						 			consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","(Tod Rate*EC)/100=("+elTodRates.getIncrementalRate()+"*"+EC2+")/100"+"="+ ((ratepercentage*EC2)/100));
						 			count2++;
								
								}
								if(ftimem.equals(messageSource.getMessage("fromTime3",null, locale))&&(totimem.equals(messageSource.getMessage("toTime3",null, locale)))){
								
									 unit3=(int) ((netMonth*unit3)/totalmonth);
						 			
									EC3=ecAmount( elRateMaster, y, z, response, unit3);
					 				logger.info("elTodRates2>"+elTodRates.getIncrementalRate());
					 				logger.info("EC3 For Unit:"+unit3+":"+EC3+"<br>");
					 				logger.info("elTodRates2>"+elTodRates.getIncrementalRate()+"<br>");
						 			float ratepercentage=elTodRates.getIncrementalRate();
						 			tod3=((ratepercentage*EC3)/100);
						 			logger.info("TOD From"+ftimem+" to "+totimem+":"+tod3+"<br>");
						 			logger.info("Tod3>"+tod3);
						 			consilatedBill.put("Tod Rate b/w"+ftimem+"to"+totimem+":","(Tod Rate*EC)/100=("+elTodRates.getIncrementalRate()+"*"+EC3+")/100"+"="+ ((ratepercentage*EC3)/100));
						 			count3++;
								}
						if(count1!=0 & count2!=0 & count3!=0){
							   Tottod2=tod1+tod2+tod3; 
								
							
								logger.info("<html><span style='color:#8B0000;'>TOD  Effective From Date "+y+" is: "+Tottod2+"<br></span></html>");
								tod1=0;
								tod2=0;
								tod3=0;
								count1=0;	
								count2=0;
								count3=0;
						}
						}
						}
		 	           }
					totalAmount=EC1+EC2+EC3;
		                 }
		                 }
	
		}
		  double  Totaleccharge= Math.round(totalAmount);
		  float	totaltod=Totod1+Tottod2+Tottod4;
		  float totalbill= Math.round((Totaleccharge) +totaltod); 
			
		 logger.info("Total Unit>"+consumptionUints);
		 logger.info("Tod Tod amount in Rs:"+totaltod);
		 logger.info("Total EC Charge>"+Totaleccharge);
		 logger.info("Total Charge>"+totalbill);

		    logger.info("---------------------------------------------------------------------------------"+"<br>");
			logger.info("Total EC Charge:"+Totaleccharge+"<br>");
			logger.info("Tod Tod amount in Rs:"+totaltod+"<br>");
			logger.info("Total EC After TOD:>"+totalbill+"<br>");
			consilatedBill.put("totalBill",totalbill);
			consilatedBill.put("Total Tod amount :",totaltod);
			consilatedBill.put("Total Ec Amount :",totalAmount);
	        return consilatedBill;
		}
	
		public String getDate3(Date date)
		{
			 SimpleDateFormat formatDateJava = new SimpleDateFormat("hh:mm:ss a");
			 String date_to_string = formatDateJava.format(date);
			 return date_to_string;
		}
		
		public static float roundOff(float val, int places)
		{
			 BigDecimal bigDecima = new BigDecimal(val);
	         bigDecima = bigDecima.setScale(places, BigDecimal.ROUND_HALF_UP);
	         return  bigDecima.floatValue();
		}
		@Override
		public ELTariffMaster getTariffMasterByName(String tariffName) {
			return entityManager.createNamedQuery("ELTariffMaster.getTariffMasterByName",ELTariffMaster.class).setParameter("tariffName", tariffName).getSingleResult();
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<Map<String, Object>> getMinMaxDate(int elTariffID,String rateName) {
			return getMinMaxDate(entityManager.createNamedQuery("ELRateMaster.getMinMaxDate").setParameter("tariffMasterId", elTariffID).setParameter("rateName", rateName).getResultList());
		}
		
		@SuppressWarnings("rawtypes")
		private List getMinMaxDate(List<?> datesList)
		{
			 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			 Map<String, Object> minMaxDates = null;
			 for (Iterator<?> iterator = datesList.iterator(); iterator.hasNext();)
				{
					final Object[] values = (Object[]) iterator.next();
					minMaxDates = new HashMap<String, Object>();
					minMaxDates.put("validFrom",(Date)values[0]);
					minMaxDates.put("validTo",(Date)values[1]);
				    result.add(minMaxDates);
		     }
	      return result;
		}
		@Override
		public List<ELRateMaster> getRateMasterByIdName(int elTariffID,String rateName) {
			return entityManager.createNamedQuery("ELRateMaster.getRateMasterByIdName",ELRateMaster.class).setParameter("elTariffID", elTariffID).setParameter("rateName",rateName).getResultList();
		}
		
		@Override
		public HashMap<Object, Object> tariffCalculationMultiSlab(ELRateMaster elRateMaster, Date startDate, Date endDate,Float uomValue)
		{
			float totalAmount = 0.0f;
			float slabDifference =0.0f;
			float lastSlabTo = 0.0f; 
			LocalDate fromDate =  new LocalDate(startDate);
		    LocalDate toDate = new LocalDate(endDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    String slabString="";
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(startDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 if(netMonth == 0)
			 {
				 netMonth = daysToMonths;
				 logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "+netMonth);
			 }
			 logger.info("uomValue -------------- uomValue"+uomValue);
			 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
			List<ELRateSlabs> elRateSlabsList = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
			logger.info("::::::::::::::: Number of rate slab are ::::::::::::::: "+elRateSlabsList.size());
			
			for (ELRateSlabs elRateSlabs : elRateSlabsList)
			  {
			    if(lastSlabTo == 0)
			    {
			     slabDifference = Math.round((elRateSlabs.getSlabTo() * netMonth - elRateSlabs.getSlabFrom()* netMonth));
			     lastSlabTo = Math.round(elRateSlabs.getSlabTo()* netMonth)+1;
			    }
			    else
			    {
			    	slabDifference = Math.round((elRateSlabs.getSlabTo()* netMonth - lastSlabTo));
			    	lastSlabTo = Math.round(elRateSlabs.getSlabTo()* netMonth)+1;
			    }
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()));
						logger.info("("+slabDifference+")"+" X"+(elRateSlabs.getRate())+" = "+((slabDifference * (elRateSlabs.getRate()))) +"</br>");
						slabString = slabString+ ("("+slabDifference+")"+" X"+(elRateSlabs.getRate())+" = "+Math.round(((slabDifference * (elRateSlabs.getRate()))) *100f)/100f+"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()));
							logger.info("("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((uomValue * (elRateSlabs.getRate()))) +"</br>");
							slabString = slabString+ ("("+uomValue+")"+" X"+(elRateSlabs.getRate())+" = "+Math.round(((uomValue * (elRateSlabs.getRate())))*100f)/100f +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()/100));
						logger.info("("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((slabDifference * (elRateSlabs.getRate()/100))) +"</br>");
						slabString = slabString+ ("("+slabDifference+")"+" X"+(elRateSlabs.getRate()/100)+" = "+Math.round(((slabDifference * (elRateSlabs.getRate()/100)))*100f)/100f  +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()/100));
							logger.info("("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((uomValue * (elRateSlabs.getRate()/100))) +"</br>");
							slabString = slabString+ ("("+uomValue+")"+" X"+(elRateSlabs.getRate()/100)+" = "+Math.round(((uomValue * (elRateSlabs.getRate()/100)))*100f)/100f +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()/100));
						
						logger.info("("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((slabDifference * (elRateSlabs.getRate()/100))) +"</br>");
						slabString = slabString+ ("("+slabDifference+")"+" X"+(elRateSlabs.getRate()/100)+" = "+Math.round(((slabDifference * (elRateSlabs.getRate()/100))) *100f)/100f +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()/100));
							logger.info("("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((uomValue * (elRateSlabs.getRate()/100))) +"</br>");
							slabString = slabString+ ("("+uomValue+")"+" X"+(elRateSlabs.getRate()/100)+" = "+Math.round(((uomValue * (elRateSlabs.getRate()/100))) *100f)/100f +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate())) ;
						logger.info("("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((slabDifference * (elRateSlabs.getRate()))) +"</br>");
						slabString = slabString+ ("("+slabDifference+")"+" X"+(elRateSlabs.getRate())+" = "+Math.round(((slabDifference * (elRateSlabs.getRate()))) *100f)/100f+"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()))* netMonth;
							logger.info("("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((uomValue * (elRateSlabs.getRate()))) +"</br>");
							slabString = slabString+ ("("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+Math.round(((uomValue * (elRateSlabs.getRate()/100))) *100f)/100f +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
			  }
			if(elRateMaster.getMaxRate()!=0)
			{
				  totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
				  consolidateBill.put("total", totalAmount);
			}
			else
			{
				  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
				  consolidateBill.put("total", totalAmount);
			}
			consolidateBill.put("slabString", slabString);
			return consolidateBill;
		}
		@Override
		public HashMap<Object, Object> tariffCalculationRangeSlab(ELRateMaster elRateMaster, Date startDate, Date endDate,Float uomValue)
		{
			float totalAmount = 0.0f;
			LocalDate fromDate =  new LocalDate(startDate);
		    LocalDate toDate = new LocalDate(endDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(startDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			// netMonth = Math.round(netMonth*100)/100;
			 if(netMonth == 0)
			 {
				 netMonth = daysToMonths;
				 logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "+netMonth);
			 }
			 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
			 List<ELRateSlabs> elRateSlabsList = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabBetween",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).setParameter("demandCharges", uomValue).getResultList();
			 logger.info("uomValue ------------ in ranage -----"+uomValue+"month ----------- "+netMonth);
			 if(elRateSlabsList.isEmpty())
			    {
			    	  logger.info("Invalid Phase"+"</br>");
			    }
				for (ELRateSlabs elRateSlabs : elRateSlabsList)
				  {
					
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + elRateSlabs.getRate() * netMonth * uomValue;
						consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
						consolidateBill.put("rate", elRateSlabs.getRate());

					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + elRateSlabs.getRate()/100 * netMonth*uomValue;
						consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
						consolidateBill.put("rate", elRateSlabs.getRate());
					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + elRateSlabs.getRate()/100 * netMonth*uomValue;
						consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
						consolidateBill.put("rate", elRateSlabs.getRate());
					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + elRateSlabs.getRate() * netMonth*uomValue;
						consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
						consolidateBill.put("rate", elRateSlabs.getRate());
					}
					consolidateBill.put("months", billableMonths);
					consolidateBill.put("days", daysAfterMonth);
					consolidateBill.put("netMonth", netMonth);
				  }
				if(elRateMaster.getMaxRate()!=0)
				{
					 totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
					 consolidateBill.put("total", totalAmount);
				}
				else
				{
					  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
					  consolidateBill.put("total", totalAmount);
				}
			 
			return consolidateBill;
		}
		@Override
		public HashMap<Object, Object> drAmount(ELRateMaster elRateMaster, Date startDate,Date endDate, float uomValue) 
		{

			float totalAmount = 0.0f;
			LocalDate fromDate =  new LocalDate(startDate);
		    LocalDate toDate = new LocalDate(endDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(startDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 netMonth = Math.round(netMonth*100)/100;
			 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
			 
			 List<ELRateSlabs> elRateSlabsList = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabBetween",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).setParameter("demandCharges", uomValue).getResultList();
			 if(elRateSlabsList.isEmpty())
			    {
			    	  logger.info("Invalid Phase"+"</br>");
			    }
				for (ELRateSlabs elRateSlabs : elRateSlabsList)
				  {
					
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + elRateSlabs.getRate() * netMonth;
						consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
						consolidateBill.put("rate", elRateSlabs.getRate());
					}
					consolidateBill.put("months", billableMonths);
					consolidateBill.put("days", daysAfterMonth);
					consolidateBill.put("netMonth", netMonth);
				  }
				if(elRateMaster.getMaxRate()!=0)
				{
					 totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
					 consolidateBill.put("total", totalAmount);
				}
				else
				{
					  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
					  consolidateBill.put("total", totalAmount);
				}
			 
			return consolidateBill;
		}
		@Override
		public HashMap<Object, Object> dcAmount(ELRateMaster elRateMaster, Date startDate,Date endDate, Float uomValue, float contractDemand) 
		{
			float totalAmount = 0.0f;
			float slabDifference =0.0f;
			float lastSlabTo = 0.0f; 
			float penalityFactor =0.0f;
			float billableUnits =0.0f;
			float dcRate=0.0f;
			float contractDemandAmount = 0;
			float excessLoadUnitsAmount = 0;
			float billableAmount = 0;
			LocalDate fromDate =  new LocalDate(startDate);
		    LocalDate toDate = new LocalDate(endDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(startDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 netMonth = Math.round(netMonth*100)/100;
			 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
			 
			 if(elRateMaster.getRateType().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale)))
			 {
				  logger.info("Tariff effective from :"+elRateMaster.getValidFrom()+"</br>");
				  logger.info("Months :"+billableMonths +"\tDays :"+daysAfterMonth+"</br>");
				  logger.info("Net Months :"+netMonth+"</br>");
				  logger.info("</br>");
				  
				List<ELRateSlabs> elRateSlabsList = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
				for (ELRateSlabs elRateSlabs : elRateSlabsList)
				{
					//dcRate= elRateSlabs.getRate();
				//	consolidateBill.put("dcRate", dcRate);
					 if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
						{
							logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
							dcRate= elRateSlabs.getRate();
							consolidateBill.put("dcRate", elRateSlabs.getRate());

						}
						if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
						{
							logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
							dcRate= elRateSlabs.getRate()/100;
							consolidateBill.put("dcRate", elRateSlabs.getRate()/100);
						}
						if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
						{
							logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
							dcRate= elRateSlabs.getRate()/100;
							consolidateBill.put("dcRate", elRateSlabs.getRate()/100);
						}
						if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
						{
							logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
							dcRate= elRateSlabs.getRate();
							consolidateBill.put("dcRate", elRateSlabs.getRate());
						}
					
					List<ELRateMaster> elTariffMastersListERP = entityManager.createNamedQuery("ELRateMaster.elTariffMastersListERP",ELRateMaster.class).setParameter("elTariffID", elRateMaster.getElTariffID()).setParameter("rateName", messageSource.getMessage("rateNameType.ELP",null, locale).trim()).setParameter("validFrom", elRateMaster.getValidFrom()).setParameter("validTo", elRateMaster.getValidTo()).getResultList();
					logger.info("elTariffMastersListERP sizeeeeeeeeee "+elTariffMastersListERP.size());
					
					if(elTariffMastersListERP.isEmpty())
					 {
						 List<ELRateSlabs> elRateSlabsList1 = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
						 for (ELRateSlabs elRateSlabs1 : elRateSlabsList1)
						 {
							/*billableAmount = billableAmount +( uomValue * (elRateSlabs1.getRate()/100)) * netMonth;
							logger.info("( "+uomValue +" X "+elRateSlabs1.getRate()/100+" ) "+" X "+netMonth+" = "+billableAmount+"</br>");*/
							 if(elRateSlabs1.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
								{
									logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
									billableAmount = billableAmount +( uomValue * (elRateSlabs1.getRate())) * netMonth;
									consolidateBill.put("rateType", elRateSlabs1.getSlabRateType());
									consolidateBill.put("rate", elRateSlabs1.getRate());
									consolidateBill.put("billableAmount", billableAmount);

								}
								if(elRateSlabs1.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
								{
									logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
									billableAmount = billableAmount +( uomValue * (elRateSlabs1.getRate()/100)) * netMonth;
									consolidateBill.put("rateType", elRateSlabs1.getSlabRateType());
									consolidateBill.put("rate", elRateSlabs1.getRate());
									consolidateBill.put("billableAmount", billableAmount);
								}
								if(elRateSlabs1.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
								{
									logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
									billableAmount = billableAmount +( uomValue * (elRateSlabs1.getRate()/100)) * netMonth;
									consolidateBill.put("rateType", elRateSlabs1.getSlabRateType());
									consolidateBill.put("rate", elRateSlabs1.getRate());
									consolidateBill.put("billableAmount", billableAmount);
								}
								if(elRateSlabs1.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
								{
									logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
									billableAmount = billableAmount +( uomValue * (elRateSlabs1.getRate())) * netMonth;
									consolidateBill.put("rateType", elRateSlabs1.getSlabRateType());
									consolidateBill.put("rate", elRateSlabs1.getRate());
									consolidateBill.put("billableAmount", billableAmount);
								}
						 }
					 }
					else
					{
						for (ELRateMaster elRateMaster2 : elTariffMastersListERP)
						{
							List<ELRateSlabs> elRateSlabsListERPSLAB = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster2.getElrmid()).getResultList();
							for (ELRateSlabs elRateSlabs2 : elRateSlabsListERPSLAB)
							{
								 penalityFactor = (uomValue/contractDemand) * 100;
								 if(lastSlabTo == 0)
								    {
								     slabDifference = (elRateSlabs2.getSlabTo() - elRateSlabs2.getSlabFrom());
								     lastSlabTo = elRateSlabs2.getSlabTo();
								    }
								    else
								    {
								    	slabDifference = (elRateSlabs2.getSlabTo() - lastSlabTo);
								    	lastSlabTo = elRateSlabs2.getSlabTo();
								    }
								 
									if(penalityFactor > slabDifference)
									{
										penalityFactor = penalityFactor - slabDifference;
										if(elRateSlabs2.getSlabsNo() == 1)
										{
											billableUnits =  (elRateSlabs2.getSlabTo() /100) *contractDemand;
										}
										
										//contractDemandAmount = contractDemandAmount +((dcRate/100) * billableUnits * elRateSlabs2.getRate()) * netMonth;
										
										if(elRateSlabs2.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
										{
											logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs2.getSlabRateType());
											contractDemandAmount = contractDemandAmount +((dcRate) * billableUnits * elRateSlabs2.getRate()) * netMonth;
											consolidateBill.put("rateType", elRateSlabs2.getSlabRateType());
											consolidateBill.put("rate1", elRateSlabs2.getRate());
											consolidateBill.put("contractDemandAmount", contractDemandAmount);
											consolidateBill.put("billableUnits", billableUnits);
										}
										uomValue = uomValue - billableUnits;
									}
									else
									{
										if(penalityFactor > 0)
										{
											logger.info("Excess Load Penalty Amount :"+"</br>");
											logger.info("elRateSlabs.getSlabRateType() ::"+elRateSlabs.getSlabRateType());
											logger.info("messageSource.getMessage()) "+messageSource.getMessage("rateSlabType.Multiplier",null, locale));
								 			//excessLoadUnitsAmount = excessLoadUnitsAmount +((dcRate/100) * uomValue * elRateSlabs2.getRate()) * netMonth;

											if(elRateSlabs2.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
											{
												logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
												excessLoadUnitsAmount = excessLoadUnitsAmount +((dcRate) * uomValue * elRateSlabs2.getRate()) * netMonth;
												consolidateBill.put("rateType", elRateSlabs2.getSlabRateType());
												consolidateBill.put("rate2", elRateSlabs2.getRate());
												consolidateBill.put("excessLoadUnitsAmount", excessLoadUnitsAmount);
												consolidateBill.put("uomValue", uomValue);
											}
											penalityFactor = penalityFactor - slabDifference;
											break;
										}
									}
							}
						}
					}
				}
			 }
			 else if (elRateMaster.getRateType().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale)))
			 {
			 }
			 else if (elRateMaster.getRateType().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale)))
			 {
					logger.info("I am in Multi Slab Telescopic ");
			 }
			else
			 {
					  logger.info("I am in Range Slab");
					  logger.info("Tariff effective from :"+elRateMaster.getValidFrom()+"</br>");
					  logger.info("Months :"+billableMonths +"\tDays :"+daysAfterMonth+"</br>");
					  logger.info("Net Months :"+netMonth+"</br>");
					  logger.info("</br>");
					  
					  ELRateSlabs elRateSlabs = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabBetween",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).setParameter("demandCharges", uomValue).getSingleResult();
					  if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
						{
							logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
							dcRate= elRateSlabs.getRate();
							consolidateBill.put("dcRate", elRateSlabs.getRate());

						}
						if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
						{
							logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
							dcRate= elRateSlabs.getRate()/100;
							consolidateBill.put("dcRate", elRateSlabs.getRate()/100);
						}
						if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
						{
							logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
							dcRate= elRateSlabs.getRate()/100;
							consolidateBill.put("dcRate", elRateSlabs.getRate()/100);
						}
						if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
						{
							logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
							dcRate= elRateSlabs.getRate();
							consolidateBill.put("dcRate", elRateSlabs.getRate());
						}
					
					  
					    List<ELRateMaster> elTariffMastersListERP = entityManager.createNamedQuery("ELRateMaster.elTariffMastersListERP",ELRateMaster.class).setParameter("elTariffID", elRateMaster.getElTariffID()).setParameter("rateName", messageSource.getMessage("rateNameType.ELP",null, locale).trim()).setParameter("validFrom", elRateMaster.getValidFrom()).setParameter("validTo", elRateMaster.getValidTo()).getResultList();
						for (ELRateMaster elRateMaster2 : elTariffMastersListERP)
						{
							List<ELRateSlabs> elRateSlabsListERPSLAB = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster2.getElrmid()).getResultList();
							for (ELRateSlabs elRateSlabs2 : elRateSlabsListERPSLAB)
							{
							    penalityFactor = (uomValue/contractDemand) * 100;
								 if(lastSlabTo == 0)
								    {
								     slabDifference = (elRateSlabs2.getSlabTo() - elRateSlabs2.getSlabFrom());
								     lastSlabTo = elRateSlabs2.getSlabTo();
								    }
								    else
								    {
								    	slabDifference = (elRateSlabs2.getSlabTo() - lastSlabTo);
								    	lastSlabTo = elRateSlabs2.getSlabTo();
								    }
								 
									if(penalityFactor > slabDifference)
									{
										if(elRateSlabs2.getSlabsNo() == 1)
										{
											billableUnits =  (elRateSlabs2.getSlabTo() /100) *contractDemand;
										}
										//contractDemandAmount = contractDemandAmount +((dcRate/100) * billableUnits * elRateSlabs2.getRate()) * netMonth;
										if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
										{
											logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
											contractDemandAmount = contractDemandAmount +((dcRate) * billableUnits * elRateSlabs2.getRate()) * netMonth;
											consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
											consolidateBill.put("rate", elRateSlabs.getRate());
											consolidateBill.put("contractDemandAmount", contractDemandAmount);
											consolidateBill.put("billableUnits", contractDemandAmount);

										}
										if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
										{
											logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
											contractDemandAmount = contractDemandAmount +((dcRate) * billableUnits * elRateSlabs2.getRate()/100) * netMonth;
											consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
											consolidateBill.put("rate", elRateSlabs.getRate());
											consolidateBill.put("contractDemandAmount", contractDemandAmount);
											consolidateBill.put("billableUnits", contractDemandAmount);
										}
										if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
										{
											logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
											contractDemandAmount = contractDemandAmount +((dcRate) * billableUnits * elRateSlabs2.getRate()/100) * netMonth;
											consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
											consolidateBill.put("rate", elRateSlabs.getRate());
											consolidateBill.put("contractDemandAmount", contractDemandAmount);
											consolidateBill.put("billableUnits", contractDemandAmount);
										}
										if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
										{
											logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
											contractDemandAmount = contractDemandAmount +((dcRate) * billableUnits * elRateSlabs2.getRate()) * netMonth;
											consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
											consolidateBill.put("rate", elRateSlabs.getRate());
											consolidateBill.put("contractDemandAmount", contractDemandAmount);
											consolidateBill.put("billableUnits", contractDemandAmount);
										}
										logger.info("Contract Amount :"+"</br>");
										logger.info("( "+billableUnits +"  X"+(dcRate/100)+" X "+elRateSlabs2.getRate()+" ) "+" X "+netMonth+" = "+contractDemandAmount+"</br>");
										logger.info("</br>");
										uomValue = uomValue - billableUnits;
									}
									else
									{
										if(penalityFactor > 0)
										{
											logger.info("Excess Load Penalty Amount :"+"</br>");
										//	excessLoadUnitsAmount = excessLoadUnitsAmount +((dcRate/100) * uomValue * elRateSlabs2.getRate()) * netMonth;
											if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
											{
												logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
												excessLoadUnitsAmount = excessLoadUnitsAmount +((dcRate) * uomValue * elRateSlabs2.getRate()) * netMonth;
												consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
												consolidateBill.put("rate", elRateSlabs.getRate());
												consolidateBill.put("excessLoadUnitsAmount", excessLoadUnitsAmount);
												consolidateBill.put("uomValue", uomValue);

											}
											if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
											{
												logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
												excessLoadUnitsAmount = excessLoadUnitsAmount +((dcRate) * uomValue * elRateSlabs2.getRate()/100) * netMonth;
												consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
												consolidateBill.put("rate", elRateSlabs.getRate());
												consolidateBill.put("excessLoadUnitsAmount", excessLoadUnitsAmount);
												consolidateBill.put("uomValue", uomValue);
											}
											if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
											{
												logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
												excessLoadUnitsAmount = excessLoadUnitsAmount +((dcRate) * uomValue * elRateSlabs2.getRate()/100) * netMonth;
												consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
												consolidateBill.put("rate", elRateSlabs.getRate());
												consolidateBill.put("excessLoadUnitsAmount", excessLoadUnitsAmount);
												consolidateBill.put("uomValue", uomValue);
											}
											if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
											{
												logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
												excessLoadUnitsAmount = excessLoadUnitsAmount +((dcRate) * uomValue * elRateSlabs2.getRate()) * netMonth;
												consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
												consolidateBill.put("rate", elRateSlabs.getRate());
												consolidateBill.put("excessLoadUnitsAmount", excessLoadUnitsAmount);
												consolidateBill.put("uomValue", uomValue);
											}
											logger.info("( "+uomValue +"  X"+(dcRate/100)+" X "+elRateSlabs2.getRate()+" ) "+" X "+netMonth+" = "+excessLoadUnitsAmount+"</br>");
											logger.info("</br>");
											penalityFactor = penalityFactor - slabDifference;
											break;
										}
									}
							}
						}
			 }
			 consolidateBill.put("months", billableMonths);
			 consolidateBill.put("days", daysAfterMonth);
			 consolidateBill.put("netMonth", netMonth);
			totalAmount = totalAmount + excessLoadUnitsAmount + contractDemandAmount + billableAmount;
			if(elRateMaster.getMaxRate()!=0)
			{
				 totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
				 consolidateBill.put("total", totalAmount);
			}
			else
			{
				  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
				  consolidateBill.put("total", totalAmount);
			}
			return consolidateBill;
		}
		@Override
		public HashMap<Object, Object> powerFactorPenalty(ELRateMaster elRateMaster,Date startDate, Date endDate, Float uomValue,float consumptionUnits)
		{
			float totalAmount = 0.0f;
			LocalDate fromDate =  new LocalDate(startDate);
		    LocalDate toDate = new LocalDate(endDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(startDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 netMonth = Math.round(netMonth*100)/100;
			 
			 float consumptionUnitsFlotValue = consumptionUnits/netMonth;
			 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
	         float roundedPF = roundOff(uomValue, 2);
			 try
		        {
				 ELRateSlabs elRateSlabs = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListBetween",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).setParameter("pfFactor", uomValue).getSingleResult();
				 
				 BigDecimal bigDecimal21 = new BigDecimal(elRateSlabs.getSlabTo() - roundedPF);
	             bigDecimal21 = bigDecimal21.setScale(2, RoundingMode.HALF_UP);
	             float powerFactorLagging =  bigDecimal21.floatValue();
	             logger.info("roundedPF --------------------- "+roundedPF);
	             if(roundedPF<=0.90)
	             {
	            	 logger.info("---------------- PF penalty applicable ----------------");
	            	 totalAmount = totalAmount +(powerFactorLagging * 100 *(elRateSlabs.getRate()/100))* consumptionUnitsFlotValue * netMonth ;
	            	 if(elRateMaster.getMaxRate()!=0)
	 				{
	 					 totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
	 					 consolidateBill.put("penalty", totalAmount);
	 				}
	 				else
	 				{
	 					  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
	 					  consolidateBill.put("penalty", totalAmount);
	 				}
	             }
	             if(roundedPF>=0.95 && roundedPF<=1)
	             {
	            	 logger.info("---------------- PF rebate applicable ----------------");
	            	 logger.info("powerFactorLagging --------- "+powerFactorLagging);
	            	 logger.info("elRateSlabs.getRate()------- "+elRateSlabs.getRate());
	            	 logger.info("consumptionUnitsFlotValue -- "+consumptionUnitsFlotValue);
	            	 totalAmount = totalAmount +(powerFactorLagging * 100 *(elRateSlabs.getRate()/100))* consumptionUnitsFlotValue * netMonth ;
	            	 if(elRateMaster.getMaxRate()!=0)
	 				{
	 					 totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
	 					 consolidateBill.put("rebate", totalAmount);
	 				}
	 				else
	 				{
	 					  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
	 					  consolidateBill.put("rebate", totalAmount);
	 				}
	             }
	             
	             
		        }
		        catch(Exception exception)
		         {
		        	logger.info("------------- Error while calculating PF ");
		         }
			return consolidateBill;
		}
		@Override
		public List<ELRateSlabs> getELRateSlabListByRateMasterId(int elrmid) {
			return entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId",elrmid).getResultList();
		}
		
		@Override
		public List<ELRateMaster> getElectricityRateMaster(int tariffId) {
			return entityManager.createNamedQuery("ELRateMaster.getRateMaster",ELRateMaster.class).setParameter("elTariffID",tariffId).getResultList();
		}
		

		@Override
		public List<?> getAllAccuntNumbers() {
			return getAccountNumbersData(entityManager.createNamedQuery("TariffCalculation.getAllAccuntNumbers").getResultList());
		}
		
		@SuppressWarnings("rawtypes")
		public List getAccountNumbersData(List<?> accountNumbers){
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			 Map<String, Object> accountNumberMap = null;
			 for (Iterator<?> iterator = accountNumbers.iterator(); iterator.hasNext();)
				{
					final Object[] values = (Object[]) iterator.next();
					accountNumberMap = new HashMap<String, Object>();
					
					accountNumberMap.put("accountId", (Integer)values[0]);
					accountNumberMap.put("accountNumber", (String)values[1]);
					
					String personName = "";
					personName = personName.concat((String)values[3]);
					if(((String)values[4]) != null)
					{
						personName = personName.concat(" ");
						personName = personName.concat((String)values[4]);
					}
					
					accountNumberMap.put("personId", (Integer)values[2]);
					accountNumberMap.put("personName", personName);
					accountNumberMap.put("personType", (String)values[5]);
					accountNumberMap.put("personStyle", (String)values[6]);
				
				result.add(accountNumberMap);
		     }
	     return result;
		}	
		@Override
		public List<WTRateMaster> getWaterRateMaster(int waterTariffId)
		{
			return entityManager.createNamedQuery("WTRateMaster.getWaterRateMaster",WTRateMaster.class).setParameter("wtTariffId",waterTariffId).getResultList();
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<Map<String, Object>> getWaterMinMaxDate(int wtTariffId,String wtRateName) {
			return getMinMaxDate(entityManager.createNamedQuery("WTRateMaster.getMinMaxDate").setParameter("wtTariffId", wtTariffId).setParameter("wtRateName", wtRateName).getResultList());
		}
		@Override
		public List<WTRateMaster> getWaterRateMasterByIdName(int wtTariffId,String wtRateName) {
			return entityManager.createNamedQuery("WTRateMaster.getWaterRateMasterByIdName",WTRateMaster.class).setParameter("wtTariffId", wtTariffId).setParameter("wtRateName",wtRateName).getResultList();
		}
		
		@Override
		public HashMap<String, Object> waterTariffCalculationMultiSlab(WTRateMaster waterRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue) {

			float totalAmount = 0.0f;
			float slabDifference =0.0f;
			float lastSlabTo = 0.0f; 
			String wtSlabString=null;
			LocalDate fromDate =  new LocalDate(previousBillDate);
		    LocalDate toDate = new LocalDate(currentBillDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    HashMap<String, Object> consolidateBill = new LinkedHashMap<>();
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(previousBillDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 if(netMonth == 0)
			 {
				 netMonth = daysToMonths;
				 logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "+netMonth);
			 }
			List<WTRateSlabs> elRateSlabsList = entityManager.createNamedQuery("WTRateSlabs.getWTRateSlabListByRateMasterId",WTRateSlabs.class).setParameter("wtrmid", waterRateMaster1.getWtrmid()).getResultList();
			for (WTRateSlabs waterRateSlabs : elRateSlabsList)
			  {
			   /* if(lastSlabTo == 0)
			    {
			     slabDifference = (waterRateSlabs.getWtSlabTo() * netMonth - waterRateSlabs.getWtSlabFrom() * netMonth);
			     lastSlabTo =  Math.round(waterRateSlabs.getWtSlabTo() * netMonth)+1;
			    }
			    else
			    {
			    	slabDifference = Math.round((elRateSlabs.getSlabTo()* netMonth - lastSlabTo));(waterRateSlabs.getWtSlabTo() - lastSlabTo);
			    	lastSlabTo = Math.round(waterRateSlabs.getWtSlabTo()* netMonth)+1;
			    }*/
			    
			   if(lastSlabTo == 0)
			    {
			     slabDifference = Math.round((waterRateSlabs.getWtSlabTo() * netMonth - waterRateSlabs.getWtSlabFrom()* netMonth));
			     lastSlabTo = Math.round(waterRateSlabs.getWtSlabTo()* netMonth)+1;
			    }
			    else
			    {
			    	slabDifference = Math.round((waterRateSlabs.getWtSlabTo()* netMonth - lastSlabTo));
			    	lastSlabTo = Math.round(waterRateSlabs.getWtSlabTo()* netMonth)+1;
			    }
			    
				if(waterRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (waterRateSlabs.getWtRate()));
						wtSlabString =wtSlabString +("("+slabDifference+")"+" X"+(waterRateSlabs.getWtRate())+" = "+Math.round(((slabDifference * (waterRateSlabs.getWtRate()))) *100f)/100f +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (waterRateSlabs.getWtRate()));
							wtSlabString =wtSlabString +("("+uomValue+")"+" X"+(waterRateSlabs.getWtRate())+" = "+Math.round(((uomValue * (waterRateSlabs.getWtRate()))) *100f)/100f +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				if(waterRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (waterRateSlabs.getWtRate()/100));
						wtSlabString =wtSlabString +("("+slabDifference+")"+" X"+(waterRateSlabs.getWtRate()/100)+" = "+Math.round(((slabDifference * (waterRateSlabs.getWtRate()/100))) *100f)/100f +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (waterRateSlabs.getWtRate()/100));
							wtSlabString =wtSlabString +("("+uomValue+")"+" X"+(waterRateSlabs.getWtRate()/100)+" = "+Math.round(((uomValue * (waterRateSlabs.getWtRate()/100))) *100f)/100f +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
			  }
			if(waterRateMaster1.getWtMaxRate()!=0)
			{
				  totalAmount = (totalAmount > waterRateMaster1.getWtMaxRate()) ? waterRateMaster1.getWtMaxRate() : totalAmount;
			}
			else
			{
				  totalAmount = (totalAmount > waterRateMaster1.getWtMinRate()) ? totalAmount : waterRateMaster1.getWtMinRate();
			}
			consolidateBill.put("totalAmount", totalAmount);
			consolidateBill.put("wtSlabString", wtSlabString);
			return consolidateBill;
		}
		
		@Override
		public List<GasRateMaster> getGasRateMaster(int gasTariffId) {
			return entityManager.createNamedQuery("GasRateMaster.getGasRateMaster",GasRateMaster.class).setParameter("gasTariffId",gasTariffId).getResultList();
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<Map<String, Object>> getGasMinMaxDate(int gasTariffId,String gasRateName) {
			return getMinMaxDate(entityManager.createNamedQuery("GasRateMaster.getGasMinMaxDate").setParameter("gasTariffId", gasTariffId).setParameter("gasRateName", gasRateName).getResultList());
		}
		@Override
		public List<GasRateMaster> getGasRateMasterByIdName(int gasTariffId,String gasRateName) {
			return entityManager.createNamedQuery("GasRateMaster.getGasRateMasterByIdName",GasRateMaster.class).setParameter("gasTariffId", gasTariffId).setParameter("gasRateName",gasRateName).getResultList();
		}
		@Override
		public HashMap<Object, Object> gasTariffCalculationMultiSlab(GasRateMaster gasRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue)
		{
			float totalAmount = 0.0f;
			float slabDifference =0.0f;
			float lastSlabTo = 0.0f; 
			String gaString=null;
			LocalDate fromDate =  new LocalDate(previousBillDate);
		    LocalDate toDate = new LocalDate(currentBillDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(previousBillDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 if(netMonth == 0)
			 {
				 netMonth = daysToMonths;
				 logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "+netMonth);
			 }
			 
			List<GasRateSlab> gasRateSlabsList = entityManager.createNamedQuery("GasRateSlab.getGasRateSlabListByRateMasterId",GasRateSlab.class).setParameter("gasrmId", gasRateMaster1.getGasrmid()).getResultList();
			for (GasRateSlab gasRateSlabs : gasRateSlabsList)
			  {
			   /* if(lastSlabTo == 0)
			    {
			     slabDifference = (gasRateSlabs.getGasSlabTo() - gasRateSlabs.getGasSlabFrom());
			     lastSlabTo = gasRateSlabs.getGasSlabTo();
			    }
			    else
			    {
			    	slabDifference = (gasRateSlabs.getGasSlabTo() - lastSlabTo);
			    	lastSlabTo = gasRateSlabs.getGasSlabTo();
			    }*/
				

				  if(lastSlabTo == 0)
							    {
							     slabDifference = Math.round((gasRateSlabs.getGasSlabTo() * netMonth - gasRateSlabs.getGasSlabFrom()* netMonth));
							     lastSlabTo = Math.round(gasRateSlabs.getGasSlabTo()* netMonth)+1;
							    }
							    else
							    {
							    	slabDifference = Math.round((gasRateSlabs.getGasSlabTo()* netMonth - lastSlabTo));
							    	lastSlabTo = Math.round(gasRateSlabs.getGasSlabTo()* netMonth)+1;
							    }
				  
				if(gasRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (gasRateSlabs.getGasRate()));
						gaString = gaString+("("+slabDifference+")"+" X"+(gasRateSlabs.getGasRate())+" = "+Math.round(((slabDifference * (gasRateSlabs.getGasRate()))) *100f)/100f +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (gasRateSlabs.getGasRate()));
							gaString = gaString+("("+uomValue+")"+" X"+(gasRateSlabs.getGasRate())+" = "+Math.round(((uomValue * (gasRateSlabs.getGasRate()))) *100f)/100f +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				if(gasRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (gasRateSlabs.getGasRate()/100));
						gaString = gaString+("("+slabDifference+")"+" X"+(gasRateSlabs.getGasRate()/100)+" = "+Math.round(((slabDifference * (gasRateSlabs.getGasRate()/100))) *100f)/100f +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (gasRateSlabs.getGasRate()/100));
							gaString = gaString+("("+uomValue+")"+" X"+(gasRateSlabs.getGasRate()/100)+" = "+Math.round(((uomValue * (gasRateSlabs.getGasRate()/100))) *100f)/100f+"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
			  }
			if(gasRateMaster1.getGasMaxRate()!=0)
			{
				  totalAmount = (totalAmount > gasRateMaster1.getGasMaxRate()) ? gasRateMaster1.getGasMaxRate() : totalAmount;
			}
			else
			{
				  totalAmount = (totalAmount > gasRateMaster1.getGasMinRate()) ? totalAmount : gasRateMaster1.getGasMinRate();
			}
			HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
			consolidateBill.put("gaString", gaString);
			consolidateBill.put("total", totalAmount);
			return consolidateBill;
		}
		@Override
		public List<SolidWasteRateMaster> getSolidWasteRateMaster(int solidWasteTariffId) {
			return entityManager.createNamedQuery("SolidWasteRateMaster.getSolidWasteRateMaster",SolidWasteRateMaster.class).setParameter("solidWasteTariffId",solidWasteTariffId).getResultList();
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<Map<String, Object>> getSolidWasteMinMaxDate(int solidWasteTariffId, String solidWasteRateName)
		{
			return getMinMaxDate(entityManager.createNamedQuery("SolidWasteRateMaster.getSolidWasteMinMaxDate").setParameter("solidWasteTariffId", solidWasteTariffId).setParameter("solidWasteRateName", solidWasteRateName).getResultList());
		}
		@Override
		public List<SolidWasteRateMaster> getSolidWasteRateMasterByIdName(int solidWasteTariffId, String solidWasteRateName) {
			return entityManager.createNamedQuery("SolidWasteRateMaster.getSolidWasteRateMasterByIdName",SolidWasteRateMaster.class).setParameter("solidWasteTariffId", solidWasteTariffId).setParameter("solidWasteRateName",solidWasteRateName).getResultList();
		}
		@Override
		public float solidWasteTariffCalculationMultiSlab(SolidWasteRateMaster solidWasteRateMaster1,Date previousBillDate, Date currentBillDate, Float uomValue)
		{
			float totalAmount = 0.0f;
			float slabDifference =0.0f;
			float lastSlabTo = 0.0f; 
			LocalDate fromDate =  new LocalDate(previousBillDate);
		    LocalDate toDate = new LocalDate(currentBillDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(previousBillDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 netMonth = Math.round(netMonth*100)/100;
			 if(netMonth == 0)
			 {
				 netMonth = daysToMonths;
				 logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "+netMonth);
			 }
			 
			List<SolidWasteRateSlab> solidWasteRateSlabsList = entityManager.createNamedQuery("SolidWasteRateSlab.getSolidWasteRateSlabListByRateMasterId",SolidWasteRateSlab.class).setParameter("solidWasteRmId", solidWasteRateMaster1.getSolidWasteRmId()).getResultList();
			for (SolidWasteRateSlab solidWasteRateSlabs : solidWasteRateSlabsList)
			  {
			    /*if(lastSlabTo == 0)
			    {
			     slabDifference = (solidWasteRateSlabs.getSolidWasteSlabTo() - solidWasteRateSlabs.getSolidWasteSlabFrom());
			     lastSlabTo = solidWasteRateSlabs.getSolidWasteSlabTo();
			    }
			    else
			    {
			    	slabDifference = (solidWasteRateSlabs.getSolidWasteSlabTo() - lastSlabTo);
			    	lastSlabTo = solidWasteRateSlabs.getSolidWasteSlabTo();
			    }*/

				  if(lastSlabTo == 0)
							    {
							     slabDifference = Math.round((solidWasteRateSlabs.getSolidWasteSlabTo() * netMonth - solidWasteRateSlabs.getSolidWasteSlabFrom()* netMonth));
							     lastSlabTo = Math.round(solidWasteRateSlabs.getSolidWasteSlabTo()* netMonth)+1;
							    }
							    else
							    {
							    	slabDifference = Math.round((solidWasteRateSlabs.getSolidWasteSlabTo()* netMonth - lastSlabTo));
							    	lastSlabTo = Math.round(solidWasteRateSlabs.getSolidWasteSlabTo()* netMonth)+1;
							    }

				if(solidWasteRateSlabs.getSolidWasteSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (solidWasteRateSlabs.getSolidWasteRate()));
						logger.info("("+slabDifference+")"+" X"+(solidWasteRateSlabs.getSolidWasteRate())+" = "+Math.round(((slabDifference * (solidWasteRateSlabs.getSolidWasteRate())))*100f)/100f +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (solidWasteRateSlabs.getSolidWasteRate()));
							logger.info("("+uomValue+")"+" X"+(solidWasteRateSlabs.getSolidWasteRate())+" = "+Math.round(((uomValue * (solidWasteRateSlabs.getSolidWasteRate()))) *100f)/100f +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				if(solidWasteRateSlabs.getSolidWasteSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (solidWasteRateSlabs.getSolidWasteRate()/100));
						logger.info("("+slabDifference+")"+" X"+(solidWasteRateSlabs.getSolidWasteRate()/100)+" = "+Math.round(((slabDifference * (solidWasteRateSlabs.getSolidWasteRate()/100)))*100f)/100f+"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (solidWasteRateSlabs.getSolidWasteRate()/100));
							logger.info("("+uomValue+")"+" X"+(solidWasteRateSlabs.getSolidWasteRate()/100)+" = "+Math.round(((uomValue * (solidWasteRateSlabs.getSolidWasteRate()/100)))*100f)/100f +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
			  }
			if(solidWasteRateMaster1.getSolidWasteMaxRate()!=0)
			{
				  totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMaxRate()) ? solidWasteRateMaster1.getSolidWasteMaxRate() : totalAmount;
			}
			else
			{
				  totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMinRate()) ? totalAmount : solidWasteRateMaster1.getSolidWasteMinRate();
			}
			return totalAmount;
		}
		@Override
		public HashMap<String, Object> waterTariffCalculationSingleSlab(WTRateMaster waterRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue) 
		{

			float totalAmount = 0.0f;
			LocalDate fromDate =  new LocalDate(previousBillDate);
		    LocalDate toDate = new LocalDate(currentBillDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    HashMap<String, Object> consolidateBill = new LinkedHashMap<>();
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(previousBillDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 netMonth = Math.round(netMonth*100)/100;
			 if(netMonth == 0)
			 {
				 netMonth = daysToMonths;
				 logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "+netMonth);
			 }
			 
			 List<WTRateSlabs> elRateSlabsList = entityManager.createNamedQuery("WTRateSlabs.getWTRateSlabListByRateMasterId",WTRateSlabs.class).setParameter("wtrmid", waterRateMaster1.getWtrmid()).getResultList();
			for (WTRateSlabs elRateSlabs : elRateSlabsList)
			  {
				if(elRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getWtSlabRateType());
					totalAmount = totalAmount + (uomValue * (elRateSlabs.getWtRate()))* netMonth;
				}
				if(elRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getWtSlabRateType());
					totalAmount = totalAmount + (uomValue * (elRateSlabs.getWtRate()/100))* netMonth;
				}
				if(elRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getWtSlabRateType());
					totalAmount = totalAmount + (uomValue * (elRateSlabs.getWtRate()/100))* netMonth;
				}
				if(elRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getWtSlabRateType());
					totalAmount = totalAmount + (uomValue * (elRateSlabs.getWtRate()))* netMonth;
				}
			  }
			
			if(waterRateMaster1.getWtMaxRate()!=0)
			{
				 totalAmount = (totalAmount > waterRateMaster1.getWtMaxRate()) ? waterRateMaster1.getWtMaxRate() : totalAmount;
			}
			else
			{
				  totalAmount = (totalAmount > waterRateMaster1.getWtMinRate()) ? totalAmount : waterRateMaster1.getWtMinRate();
			}
			
			consolidateBill.put("totalAmount", totalAmount);
			return consolidateBill;
		}
		@Override
		public HashMap<Object, Object> gasTariffCalculationSingleSlab(GasRateMaster gasRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue)
		{
			float totalAmount = 0.0f;
			LocalDate fromDate =  new LocalDate(previousBillDate);
		    LocalDate toDate = new LocalDate(currentBillDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(previousBillDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 netMonth = Math.round(netMonth*100)/100;
			 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
			List<GasRateSlab> gasRateSlabsList = entityManager.createNamedQuery("GasRateSlab.getGasRateSlabListByRateMasterId",GasRateSlab.class).setParameter("gasrmId", gasRateMaster1.getGasrmid()).getResultList();
			for (GasRateSlab gasRateSlabs : gasRateSlabsList)
			  {
				if(gasRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+gasRateSlabs.getGasSlabRateType());
					totalAmount = totalAmount + (uomValue * (gasRateSlabs.getGasRate()))* netMonth;
				}
				if(gasRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+gasRateSlabs.getGasSlabRateType());
					totalAmount = totalAmount + (uomValue * (gasRateSlabs.getGasRate()/100))* netMonth;
				}
				if(gasRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+gasRateSlabs.getGasSlabRateType());
					totalAmount = totalAmount + (uomValue * (gasRateSlabs.getGasRate()/100))* netMonth;
				}
				if(gasRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+gasRateSlabs.getGasSlabRateType());
					totalAmount = totalAmount + (uomValue * (gasRateSlabs.getGasRate()))* netMonth;
				}
			  }
			
			if(gasRateMaster1.getGasMaxRate()!=0)
			{
				 totalAmount = (totalAmount > gasRateMaster1.getGasMaxRate()) ? gasRateMaster1.getGasMaxRate() : totalAmount;
			}
			else
			{
				  totalAmount = (totalAmount > gasRateMaster1.getGasMinRate()) ? totalAmount : gasRateMaster1.getGasMinRate();
			}
			consolidateBill.put("total", totalAmount);
			return consolidateBill;
		
		}
		@Override
		public float solidWasteTariffCalculationSingleSlab(SolidWasteRateMaster solidWasteRateMaster1,Date previousBillDate, Date currentBillDate, Float uomValue)
		{
			float totalAmount = 0.0f;
			LocalDate fromDate =  new LocalDate(previousBillDate);
		    LocalDate toDate = new LocalDate(currentBillDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(previousBillDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 netMonth = Math.round(netMonth*100)/100;
			 if(netMonth == 0)
			 {
				 netMonth = daysToMonths;
				 logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "+netMonth);
			 }
			 List<SolidWasteRateSlab> solidWasteRateSlabsList = entityManager.createNamedQuery("SolidWasteRateSlab.getSolidWasteRateSlabListByRateMasterId",SolidWasteRateSlab.class).setParameter("solidWasteRmId", solidWasteRateMaster1.getSolidWasteRmId()).getResultList();
			for (SolidWasteRateSlab solidWasteRateSlabs : solidWasteRateSlabsList)
			  {
				if(solidWasteRateSlabs.getSolidWasteSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+solidWasteRateSlabs.getSolidWasteSlabRateType());
					totalAmount = totalAmount + (uomValue * (solidWasteRateSlabs.getSolidWasteRate()))* netMonth;
				}
				if(solidWasteRateSlabs.getSolidWasteSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+solidWasteRateSlabs.getSolidWasteSlabRateType());
					totalAmount = totalAmount + (uomValue * (solidWasteRateSlabs.getSolidWasteRate()/100))* netMonth;
				}
				if(solidWasteRateSlabs.getSolidWasteSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+solidWasteRateSlabs.getSolidWasteSlabRateType());
					totalAmount = totalAmount + (uomValue * (solidWasteRateSlabs.getSolidWasteRate()/100))* netMonth;
				}
				if(solidWasteRateSlabs.getSolidWasteSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+solidWasteRateSlabs.getSolidWasteSlabRateType());
					totalAmount = totalAmount + (uomValue * (solidWasteRateSlabs.getSolidWasteRate()))* netMonth;
				}
			  }
			
			if(solidWasteRateMaster1.getSolidWasteMaxRate()!=0)
			{
				 totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMaxRate()) ? solidWasteRateMaster1.getSolidWasteMaxRate() : totalAmount;
			}
			else
			{
				  totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMinRate()) ? totalAmount : solidWasteRateMaster1.getSolidWasteMinRate();
			}
		    return totalAmount;
		}
		@Override
		public List<CommonServicesRateMaster> getCommonServicesRateMaster(int csTariffId) {
			return entityManager.createNamedQuery("CommonServicesRateMaster.getCommonServicesRateMaster",CommonServicesRateMaster.class).setParameter("csTariffId",csTariffId).getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<Map<String, Object>> getCommonServiceMinMaxDate(int csTariffId, String csRateName) {
			return getMinMaxDate(entityManager.createNamedQuery("CommonServicesRateMaster.getCommonServiceMinMaxDate").setParameter("csTariffId", csTariffId).setParameter("csRateName", csRateName).getResultList());
		}
		@Override
		public List<CommonServicesRateMaster> getCommonServiceMasterByIdName(int csTariffId, String csRateName) {
			return entityManager.createNamedQuery("CommonServicesRateMaster.getCommonServiceMasterByIdName",CommonServicesRateMaster.class).setParameter("csTariffId", csTariffId).setParameter("csRateName",csRateName).getResultList();
		}
		@Override
		public float commonServicesTariffCalculationMultiSlab(CommonServicesRateMaster commonServiceRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue) {

			
			float totalAmount = 0.0f;
			float slabDifference =0.0f;
			float lastSlabTo = 0.0f; 
			LocalDate fromDate =  new LocalDate(previousBillDate);
		    LocalDate toDate = new LocalDate(currentBillDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(previousBillDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 netMonth = Math.round(netMonth*100)/100;
			List<CommonServicesRateSlab> commonServiceRateSlabsList = entityManager.createNamedQuery("CommonServicesRateSlab.getcommonServiceRateSlabListByRateMasterId",CommonServicesRateSlab.class).setParameter("csRmId", commonServiceRateMaster1.getCsRmId()).getResultList();
			for (CommonServicesRateSlab  commonServiceRateSlabs : commonServiceRateSlabsList)
			  {
			    if(lastSlabTo == 0)
			    {
			     slabDifference = (commonServiceRateSlabs.getCsSlabTo() - commonServiceRateSlabs.getCsSlabFrom());
			     lastSlabTo = commonServiceRateSlabs.getCsSlabTo();
			    }
			    else
			    {
			    	slabDifference = (commonServiceRateSlabs.getCsSlabTo() - lastSlabTo);
			    	lastSlabTo = commonServiceRateSlabs.getCsSlabTo();
			    }
				if(commonServiceRateSlabs.getCsSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (commonServiceRateSlabs.getCsRate())) *netMonth;
						logger.info("("+slabDifference+"X"+netMonth+")"+" X"+(commonServiceRateSlabs.getCsRate())+" = "+((slabDifference * (commonServiceRateSlabs.getCsRate())))*netMonth +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (commonServiceRateSlabs.getCsRate()))* netMonth;
							logger.info("("+uomValue+"X"+netMonth+")"+" X"+(commonServiceRateSlabs.getCsRate())+" = "+((uomValue * (commonServiceRateSlabs.getCsRate())))*netMonth +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				if(commonServiceRateSlabs.getCsSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (commonServiceRateSlabs.getCsRate()/100)) *netMonth;
						logger.info("("+slabDifference+"X"+netMonth+")"+" X"+(commonServiceRateSlabs.getCsRate()/100)+" = "+((slabDifference * (commonServiceRateSlabs.getCsRate()/100)))*netMonth +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (commonServiceRateSlabs.getCsRate()/100))* netMonth;
							logger.info("("+uomValue+"X"+netMonth+")"+" X"+(commonServiceRateSlabs.getCsRate()/100)+" = "+((uomValue * (commonServiceRateSlabs.getCsRate()/100)))*netMonth +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
			  }
			if(commonServiceRateMaster1.getCsMaxRate()!=0)
			{
				  totalAmount = (totalAmount > commonServiceRateMaster1.getCsMaxRate()) ? commonServiceRateMaster1.getCsMaxRate() : totalAmount;
			}
			else
			{
				  totalAmount = (totalAmount > commonServiceRateMaster1.getCsMinRate()) ? totalAmount : commonServiceRateMaster1.getCsMinRate();
			}
			return totalAmount;
		
		}
		@Override
		public float commonServicesTariffCalculationSingleSlab(CommonServicesRateMaster commonServiceRateMaster1,Date previousBillDate, Date currentBillDate, Float uomValue)
		{
			float totalAmount = 0.0f;
			LocalDate fromDate =  new LocalDate(previousBillDate);
		    LocalDate toDate = new LocalDate(currentBillDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(previousBillDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 netMonth = Math.round(netMonth*100)/100;
			 List<CommonServicesRateSlab> commonServiceRateSlabsList = entityManager.createNamedQuery("CommonServicesRateSlab.getcommonServiceRateSlabListByRateMasterId",CommonServicesRateSlab.class).setParameter("csRmId", commonServiceRateMaster1.getCsRmId()).getResultList();
			for (CommonServicesRateSlab commonServiceRateSlabs : commonServiceRateSlabsList)
			  {
				if(commonServiceRateSlabs.getCsSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+commonServiceRateSlabs.getCsSlabRateType());
					totalAmount = totalAmount + ((uomValue *netMonth )* (commonServiceRateSlabs.getCsRate()))* netMonth;
				}
				if(commonServiceRateSlabs.getCsSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+commonServiceRateSlabs.getCsSlabRateType());
					totalAmount = totalAmount + ((uomValue *netMonth ) * (commonServiceRateSlabs.getCsRate()/100))* netMonth;
				}
				if(commonServiceRateSlabs.getCsSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+commonServiceRateSlabs.getCsSlabRateType());
					
					totalAmount = totalAmount + ((uomValue *netMonth ) * (commonServiceRateSlabs.getCsRate()/100))* netMonth;
					logger.info(" totalAmount :::::::::::::::::::: "+totalAmount);
				}
				if(commonServiceRateSlabs.getCsSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+commonServiceRateSlabs.getCsSlabRateType());
					totalAmount = totalAmount + ((uomValue *netMonth ) * (commonServiceRateSlabs.getCsRate()))* netMonth;
				}
			  }
			
			if(commonServiceRateMaster1.getCsMaxRate()!=0)
			{
				 totalAmount = (totalAmount > commonServiceRateMaster1.getCsMaxRate()) ? commonServiceRateMaster1.getCsMaxRate() : totalAmount;
			}
			else
			{
				  totalAmount = (totalAmount > commonServiceRateMaster1.getCsMinRate()) ? totalAmount : commonServiceRateMaster1.getCsMinRate();
			}
		    return totalAmount;
		}
		@Override
		public float solidWasteTariffCalculationRangeSlab(SolidWasteRateMaster solidWasteRateMaster1,Date previousBillDate, Date currentBillDate, Float uomValue) {

			float totalAmount = 0.0f;
			LocalDate fromDate =  new LocalDate(previousBillDate);
		    LocalDate toDate = new LocalDate(currentBillDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(previousBillDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 netMonth = Math.round(netMonth*100)/100;
			 List<SolidWasteRateSlab> solidWasteRateSlabsList = entityManager.createNamedQuery("SolidWasteRateSlab.getSolidWasterRateSlabBetween",SolidWasteRateSlab.class).setParameter("solidWasteRmId", solidWasteRateMaster1.getSolidWasteRmId()).setParameter("uomValue", uomValue).getResultList();
			 logger.info("::::::::::: solidWasteRateSlabsList ::::::::::: "+solidWasteRateSlabsList.size());
			 if(solidWasteRateSlabsList.isEmpty())
			    {
			    	  logger.info("Invalid Phase"+"</br>");
			    }
				for (SolidWasteRateSlab solidWasteRateSlabs : solidWasteRateSlabsList)
				  {
					
					if(solidWasteRateSlabs.getSolidWasteSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+solidWasteRateSlabs.getSolidWasteSlabRateType());
						totalAmount = totalAmount + solidWasteRateSlabs.getSolidWasteRate() * netMonth;
					}
					if(solidWasteRateSlabs.getSolidWasteSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+solidWasteRateSlabs.getSolidWasteSlabRateType());
						totalAmount = totalAmount + solidWasteRateSlabs.getSolidWasteRate()/100 * netMonth;
					}
					if(solidWasteRateSlabs.getSolidWasteSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+solidWasteRateSlabs.getSolidWasteSlabRateType());
						totalAmount = totalAmount + solidWasteRateSlabs.getSolidWasteRate()/100 * netMonth;
					}
					if(solidWasteRateSlabs.getSolidWasteSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+solidWasteRateSlabs.getSolidWasteSlabRateType());
						totalAmount = totalAmount + solidWasteRateSlabs.getSolidWasteRate() * netMonth;
					}
				  }
				if(solidWasteRateMaster1.getSolidWasteMaxRate()!=0)
				{
					 totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMaxRate()) ? solidWasteRateMaster1.getSolidWasteMaxRate() : totalAmount;
				}
				else
				{
					  totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMinRate()) ? totalAmount : solidWasteRateMaster1.getSolidWasteMinRate();
				}
			return totalAmount;
		
		}
		@Override
		public String tariffCalculationMultiSlabString(ELRateMaster elRateMaster, Date previousBillDate,Date currentBillDate, Float uomValue)
		{

			float totalAmount = 0.0f;
			float slabDifference =0.0f;
			float lastSlabTo = 0.0f; 
			LocalDate fromDate =  new LocalDate(previousBillDate);
		    LocalDate toDate = new LocalDate(currentBillDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(previousBillDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 netMonth = Math.round(netMonth*100)/100;
			 String slabString = null;
			 if(netMonth == 0)
			 {
				 netMonth = daysToMonths;
				 logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "+netMonth);
			 }
			 
			List<ELRateSlabs> elRateSlabsList = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
			logger.info("::::::::::::::: Number of rate slab are ::::::::::::::: "+elRateSlabsList.size());
			for (ELRateSlabs elRateSlabs : elRateSlabsList)
			  {
			    if(lastSlabTo == 0)
			    {
			     slabDifference = (elRateSlabs.getSlabTo() - elRateSlabs.getSlabFrom());
			     lastSlabTo = elRateSlabs.getSlabTo();
			    }
			    else
			    {
			    	slabDifference = (elRateSlabs.getSlabTo() - lastSlabTo);
			    	lastSlabTo = elRateSlabs.getSlabTo();
			    }
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate())) *netMonth;
						slabString = slabString+ ("("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((slabDifference * (elRateSlabs.getRate())))*netMonth +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()))* netMonth;
							slabString = slabString+("("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((uomValue * (elRateSlabs.getRate())))*netMonth +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()/100)) *netMonth;
						slabString = slabString+("("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((slabDifference * (elRateSlabs.getRate()/100)))*netMonth +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()/100))* netMonth;
							slabString = slabString+("("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((uomValue * (elRateSlabs.getRate()/100)))*netMonth +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()/100)) *netMonth;
						slabString = slabString+("("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((slabDifference * (elRateSlabs.getRate()/100)))*netMonth +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()/100))* netMonth;
							slabString = slabString+("("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((uomValue * (elRateSlabs.getRate()/100)))*netMonth +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate())) *netMonth;
						slabString = slabString+("("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((slabDifference * (elRateSlabs.getRate())))*netMonth +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()))* netMonth;
							slabString = slabString+("("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((uomValue * (elRateSlabs.getRate())))*netMonth +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
			  }
			if(elRateMaster.getMaxRate()!=0)
			{
				  totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
			}
			else
			{
				  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
			}
			return slabString;
		}
		
		@Override
		public HashMap<Object, Object> tariffTod(ELRateMaster elRateMaster,
				Integer tod1, Integer tod2, Integer tod3, Date startDate,
				Date endDate) {
			HttpServletResponse response = null;
			logger.info("Tod1"+tod1);
			logger.info("Tod1"+tod2);
			logger.info("Tod1"+tod3);
			logger.info("startDate"+startDate);
			logger.info("endDate"+endDate);
			logger.info("elRateMaster"+service.find(elRateMaster.getElTariffID()).getTariffName());
			logger.info("Tod1"+elRateMaster.getRateName());
			GenericClassForTodCalculation<String, String, Integer, Integer,Integer,Date, Date> calucaltion=new GenericClassForTodCalculation<>();
			
			calucaltion.setConsumptionsUnitsT1(tod3);
			calucaltion.setConsumptionsUnitsT2(tod1);//t2
			calucaltion.setConsumptionsUnitsT3(tod2);
	        calucaltion.setRateName(elRateMaster.getRateName());
	        calucaltion.setTariffName(service.find(elRateMaster.getElTariffID()).getTariffName());
	        calucaltion.setStartDate(startDate);
	        calucaltion.setEndDate(endDate);
		    //tariffCalculationService.tariffTodCalculation(calucaltion,response);
			HashMap<Object, Object> tarifftod=tariffTodCalculation(	calucaltion,response);
			HashMap<Object, Object> totalTod=new HashMap<>();
			Set<Entry<Object, Object>> s=tarifftod.entrySet();
			Iterator<Entry<Object, Object>> i=s.iterator();
				float total=0.0f;
				String slab="";
			while(i.hasNext()){
				@SuppressWarnings("rawtypes")
				Map.Entry mentry2 = (Map.Entry)i.next();		
				if(mentry2.getKey().equals("totalBill")){
				total=(Float) mentry2.getValue();
				}else{
					logger.info(mentry2.getKey()+"--"+mentry2.getValue());
					if(slab.equals("")){
					slab=slab.concat(mentry2.getKey()+""+mentry2.getValue());
					}else{
						slab=slab.concat(","+mentry2.getKey()+""+mentry2.getValue());
						
					}
				}
			}
			totalTod.put("total",total);
			totalTod.put("todslab",slab);
		logger.info(":::::::::slab::::::::::"+slab);

			return totalTod;
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<Object> todApplicable(String serviceType, Integer accountId) {
			List<ServiceMastersEntity> list=entityManager.createNamedQuery("ServiceMasterEntity.getTodApplicable").setParameter("typeOfService", serviceType).setParameter("accountId", accountId).getResultList();
			@SuppressWarnings("rawtypes")
			List todlist=new LinkedList<>();
			String tod=null;
			String dgApplicable=null;
			for (ServiceMastersEntity serviceMastersEntity : list) {
				logger.info(":::::::::::::::Todapplicable::::::::::::::::"+serviceMastersEntity.getTodApplicable());
				tod=serviceMastersEntity.getTodApplicable();
			}
			 ServiceMastersEntity mastersEntity  = serviceMasterService.getServiceMasterServicType(accountId, serviceType);
			 
			 List<ServiceParametersEntity>  serviceParametersEntities = serviceParameterService.findAllByParentId(mastersEntity.getServiceMasterId());
			 for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities)
			   {
				   if(serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("DG Applicable"))
				   {
					   logger.info(":::::::::::DG Applicable::::::::::::"+serviceParametersEntity.getServiceParameterValue());
					   dgApplicable = serviceParametersEntity.getServiceParameterValue();
				   }else{
					   //dgApplicable="No";  
				   }
			}
			 todlist.add(tod);
			 if(dgApplicable!= null){
				 todlist.add(dgApplicable); 
			 }else{
				 todlist.add("No") ;
			 }
			 
			 logger.info(":::::::::::"+todlist.get(0)+"::::::::::"+todlist.get(1));
			return todlist;
		}
		@Override
		public void generateBill(GenericBillGeneration<Integer, String, Float, Date, Date, String, String, Float, Float, Float> billGeneration,
				ServiceMastersEntity mastersEntity,
				List<ServiceParametersEntity> serviceParametersEntities,
				ELRateMaster elRateMaster)
		{
			LocalDate fromDate = new LocalDate(billGeneration.getPreviousDate());
			LocalDate toDate = new LocalDate(billGeneration.getPresentDate());
			PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
			Period difference = new Period(fromDate, toDate, monthDay);
			float billableMonths = difference.getMonths();
			int daysAfterMonth = difference.getDays();
			Calendar cal = Calendar.getInstance();
			cal.setTime(billGeneration.getPresentDate());
			float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			float netMonth = daysToMonths + billableMonths;
			netMonth = Math.round(netMonth*100)/100;
			HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
			
				 if(billableMonths == 0)
					{
							  logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
							  if((billGeneration.getPreviousDate().compareTo(elRateMaster.getValidFrom()) >=0) && (billGeneration.getPresentDate().compareTo(elRateMaster.getValidTo())<=0))
							  {
							   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
							   		 {
							   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
							   		 }
							   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
							   		 {
							   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
							   			consolidateBill = tariffCalculationMultiSlabSlab(elRateMaster,billGeneration,serviceParametersEntities);
							   			
							   			for (Entry<Object, Object> map : consolidateBill.entrySet())
							   		    {
							   				if(map.getKey().equals("total"))
							   				{
							   					
							   				}
							   		    }
							   			
							   			for (Entry<Object, Object> map : consolidateBill.entrySet())
							   		    {
							   				if(map.getKey().equals("slabString"))
							   				{
							   					
							   				}
							   		    }
							   		 }
							   		
							   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
							   		 {
							   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
							   		 }
							   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
							   		{
							   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
							   		}
							   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
							   		{
							   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
							   			
							   		}
							  }
							  else
								{
									if(billGeneration.getPreviousDate().compareTo(elRateMaster.getValidTo())<=0)
									{
										if((billGeneration.getPresentDate().compareTo(elRateMaster.getValidFrom())>= 0) && (elRateMaster.getValidTo().compareTo(billGeneration.getPresentDate())<=0))
										{
											logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
									   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
									   		 {
									   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
									   		 }
									   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
									   		 {
									   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
									   		 }
									   		
									   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
									   		 {
									   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
									   		 }
									   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
									   		{
									   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
									   		}
									   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
									   		{
									   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
									   			
									   		}
										}
										else
				  						{
				 							if(billGeneration.getPresentDate().compareTo(elRateMaster.getValidFrom())>=0 && billGeneration.getPresentDate().compareTo(elRateMaster.getValidTo())<=0)
				  							{
				 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");	
				 						   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				 						   		 {
				 						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
				 						   		 }
				 						   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				 						   		 {
				 						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
				 						   		 }
				 						   		
				 						   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				 						   		 {
				 						   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
				 						   		 }
				 						   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				 						   		{
				 						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
				 						   		}
				 						   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
				 						   		{
				 						   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
				 						   			
				 						   		}
				  							}
				  						}	
									}
								}	
					   }
				    else
					{
						  logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
						  billGeneration.setUomValue(billGeneration.getUomValue()/netMonth);
						  if((billGeneration.getPreviousDate().compareTo(elRateMaster.getValidFrom()) >=0) && (billGeneration.getPresentDate().compareTo(elRateMaster.getValidTo())<=0))
							{
					   		   logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
					   		 
						   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			 
						   		 }
						   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   		 }
						   		
						   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
						   		 }
						   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		{
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
						   		}
						   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
						   		{
						   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
						   			
						   		}
							}
						  else
							{
								if(billGeneration.getPreviousDate().compareTo(elRateMaster.getValidTo())<=0)
								{
									if((billGeneration.getPresentDate().compareTo(elRateMaster.getValidFrom())>= 0) && (elRateMaster.getValidTo().compareTo(billGeneration.getPresentDate())<=0))
									{
										logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
								   		 {
								   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
								   		 }
								   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
								   		 {
								   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
								   		 }
								   		
								   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
								   		 {
								   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
								   		 }
								   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
								   		{
								   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
								   		}
								   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
								   		{
								   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
								   			
								   		}
									}
									else
			  						{
			 							if(billGeneration.getPresentDate().compareTo(elRateMaster.getValidFrom())>=0 && billGeneration.getPresentDate().compareTo(elRateMaster.getValidTo())<=0)
			  							{
			 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");	
			 								 
			 						   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			 						   		 {
			 						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
			 						   		 }
			 						   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			 						   		 {
			 						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			 						   		 }
			 						   		
			 						   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			 						   		 {
			 						   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
			 						   		 }
			 						   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			 						   		{
			 						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
			 						   		}
			 						   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
			 						   		{
			 						   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
			 						   			
			 						   		}
			  							}
			  						}	
								}
							}	
					}	   	
		}
		@SuppressWarnings("unused")
		private HashMap<Object, Object> tariffCalculationMultiSlabSlab(ELRateMaster elRateMaster,GenericBillGeneration<Integer, String, Float, Date, Date, String, String, Float, Float, Float> billGeneration,
				List<ServiceParametersEntity> serviceParametersEntities) 
		{

			float totalAmount = 0.0f;
			float slabDifference =0.0f;
			float lastSlabTo = 0.0f; 
			LocalDate fromDate =  new LocalDate(billGeneration.getPreviousDate());
		    LocalDate toDate = new LocalDate(billGeneration.getPresentDate());
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(billGeneration.getPreviousDate());
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 netMonth = Math.round(netMonth*100)/100;
			 float uomValue = billGeneration.getUomValue();
			 
		     String slabString = "";
			 
			 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
			 
			 for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities) 
			 {
				 List<ELRateSlabs> elRateSlabsList = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
					logger.info("::::::::::::::: Number of rate slab are ::::::::::::::: "+elRateSlabsList.size());
					for (ELRateSlabs elRateSlabs : elRateSlabsList)
					  {
					    if(lastSlabTo == 0)
					    {
					     slabDifference = (elRateSlabs.getSlabTo() - elRateSlabs.getSlabFrom());
					     lastSlabTo = elRateSlabs.getSlabTo();
					    }
					    else
					    {
					    	slabDifference = (elRateSlabs.getSlabTo() - lastSlabTo);
					    	lastSlabTo = elRateSlabs.getSlabTo();
					    }
						if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
						{
							if(billGeneration.getUomValue() > slabDifference)
							{
								uomValue = uomValue - slabDifference;
								totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate())) *netMonth;
								slabString = slabString+("</br>"+"("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((slabDifference * (elRateSlabs.getRate())))*netMonth +"</br>");
							}
							else
							{
								if(uomValue > 0)
								{
									totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()))* netMonth;
									slabString = slabString+("</br>"+"("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((uomValue * (elRateSlabs.getRate())))*netMonth +"</br>");
									uomValue = uomValue - slabDifference;
								}
							}
						}
						if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
						{
							if(uomValue > slabDifference)
							{
								uomValue = uomValue - slabDifference;
								totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()/100)) *netMonth;
								slabString = slabString+("</br>"+"("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((slabDifference * (elRateSlabs.getRate()/100)))*netMonth +"</br>");
							}
							else
							{
								if(uomValue > 0)
								{
									totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()/100))* netMonth;
									slabString = slabString+("</br>"+"("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((uomValue * (elRateSlabs.getRate()/100)))*netMonth +"</br>");
									uomValue = uomValue - slabDifference;
								}
							}
						}
						if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
						{
							if(uomValue > slabDifference)
							{
								uomValue = uomValue - slabDifference;
								totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()/100)) *netMonth;
								slabString = slabString+("</br>"+"("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((slabDifference * (elRateSlabs.getRate()/100)))*netMonth +"</br>");
							}
							else
							{
								if(uomValue > 0)
								{
									totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()/100))* netMonth;
									slabString = slabString+("</br>"+"("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((uomValue * (elRateSlabs.getRate()/100)))*netMonth +"</br>");
									uomValue = uomValue - slabDifference;
								}
							}
						}
						
						if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
						{
							if(uomValue > slabDifference)
							{
								uomValue = uomValue - slabDifference;
								totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate())) *netMonth;
								slabString = slabString+("</br>"+"("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((slabDifference * (elRateSlabs.getRate())))*netMonth +"</br>");
							}
							else
							{
								if(uomValue > 0)
								{
									totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()))* netMonth;
									slabString = slabString+("</br>"+"("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((uomValue * (elRateSlabs.getRate())))*netMonth +"</br>");
									uomValue = uomValue - slabDifference;
								}
							}
						}
				 }
			 }
			if(elRateMaster.getMaxRate()!=0)
			{
				  totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
				  consolidateBill.put("total", totalAmount);
			}
			else
			{
				  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
				  consolidateBill.put("total", totalAmount);
			}
			consolidateBill.put("slabString", slabString);
			return consolidateBill;
		}
		@SuppressWarnings("unused")
		private HashMap<Object, Object> tariffCalculationSingleSlab(ELRateMaster elRateMaster,GenericBillGeneration<Integer, String, Float, Date, Date, String, String, Float, Float, Float> billGeneration,
				List<ServiceParametersEntity> serviceParametersEntities)
		{
		float totalAmount = 0.0f;
		LocalDate fromDate =  new LocalDate(billGeneration.getPresentDate());
	    LocalDate toDate = new LocalDate(billGeneration.getPresentDate());
	    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
	    Period difference = new Period(fromDate, toDate, monthDay);
	    float billableMonths = difference.getMonths();
	    int daysAfterMonth = difference.getDays();
	    
	     Calendar cal1 = Calendar.getInstance();
		 cal1.setTime(billGeneration.getPreviousDate());
		 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
		 float netMonth = daysToMonths + billableMonths;
		 netMonth = Math.round(netMonth*100)/100;
		 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
		 
		 for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities) 
		 {
			 List<ELRateSlabs> elRateSlabsList = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
				for (ELRateSlabs elRateSlabs : elRateSlabsList)
				  {
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + (billGeneration.getUomValue() * (elRateSlabs.getRate()))* netMonth;
					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + (billGeneration.getUomValue() * (elRateSlabs.getRate()/100))* netMonth;
					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + (billGeneration.getUomValue() * (elRateSlabs.getRate()/100))* netMonth;
						
					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + (billGeneration.getUomValue() * (elRateSlabs.getRate()))* netMonth;
					}
				  }
		 }
			
			if(elRateMaster.getMaxRate()!=0)
			{
				 totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
			      consolidateBill.put("total", totalAmount);
			}
			else
			{
				  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
			      consolidateBill.put("total", totalAmount);
			}
		    return consolidateBill;
		}
		
		@Override
		public HashMap<Object, Object> tariffCalculationSingleSlab(ELRateMaster elRateMaster, Date startDate, Date endDate,float uomValue) 
		{
			
			if(elRateMaster.getRateName().equalsIgnoreCase("Tax"))
			{
				float totalAmount = 0.0f;
				LocalDate fromDate =  new LocalDate(startDate);
			    LocalDate toDate = new LocalDate(endDate);
			    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
			    Period difference = new Period(fromDate, toDate, monthDay);
			    float billableMonths = difference.getMonths();
			    int daysAfterMonth = difference.getDays();
			    String singleSlab=null;
			     Calendar cal1 = Calendar.getInstance();
				 cal1.setTime(startDate);
				 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
				 logger.info("daysAfterMonth =========================="+daysAfterMonth);
				 logger.info("cal1.getActualMaximum(Calendar.DAY_OF_MONTH) "+cal1.getActualMaximum(Calendar.DAY_OF_MONTH));
				 float netMonth = daysToMonths + billableMonths;
				 if(netMonth == 0)
				 {
					 netMonth = daysToMonths;
					 logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "+netMonth);
				 }
				 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
				 
				List<ELRateSlabs> elRateSlabsList = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
				for (ELRateSlabs elRateSlabs : elRateSlabsList)
				  {
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + (uomValue  * (elRateSlabs.getRate()));
						singleSlab = singleSlab+("</br>"+"("+uomValue+"X"+(elRateSlabs.getRate())+")"+"= "+((uomValue * (elRateSlabs.getRate()))) +"</br>");
					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()/100));
						singleSlab = singleSlab+("</br>"+"("+uomValue+"X"+(elRateSlabs.getRate()/100)+")"+" = "+((uomValue * (elRateSlabs.getRate()/100))) +"</br>");
					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType()+"elRateSlabs.getRate() "+elRateSlabs.getRate());
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()/100));
						singleSlab = singleSlab+("</br>"+"("+uomValue+"X"+(elRateSlabs.getRate()/100)+")"+" X"+" = "+((uomValue * (elRateSlabs.getRate()/100))) +"</br>");
					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()));
						singleSlab = singleSlab+("</br>"+"("+uomValue+"X"+(elRateSlabs.getRate())+")"+" X"+" = "+((uomValue * (elRateSlabs.getRate()))) +"</br>");
					}
				  }
				
				if(elRateMaster.getMaxRate()!=0)
				{
					 totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
				      consolidateBill.put("total", totalAmount);
				}
				else
				{
					  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
				      consolidateBill.put("total", totalAmount);
				}
				consolidateBill.put("slabString", singleSlab);
			    return consolidateBill;
			}else {
				float totalAmount = 0.0f;
				LocalDate fromDate =  new LocalDate(startDate);
			    LocalDate toDate = new LocalDate(endDate);
			    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
			    Period difference = new Period(fromDate, toDate, monthDay);
			    float billableMonths = difference.getMonths();
			    int daysAfterMonth = difference.getDays();
			    String singleSlab=null;
			     Calendar cal1 = Calendar.getInstance();
				 cal1.setTime(startDate);
				 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
				 logger.info("daysAfterMonth =========================="+daysAfterMonth);
				 logger.info("cal1.getActualMaximum(Calendar.DAY_OF_MONTH) "+cal1.getActualMaximum(Calendar.DAY_OF_MONTH));
				 float netMonth = daysToMonths + billableMonths;
				 if(netMonth == 0)
				 {
					 netMonth = daysToMonths;
					 logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "+netMonth);
				 }

				// netMonth = Math.round(netMonth*100)/100;
				 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
				 
				List<ELRateSlabs> elRateSlabsList = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
				for (ELRateSlabs elRateSlabs : elRateSlabsList)
				  {
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + (uomValue  * (elRateSlabs.getRate()))* netMonth;
						singleSlab = singleSlab+("</br>"+"("+uomValue+"X"+(elRateSlabs.getRate())+")"+" X"+netMonth+" = "+((uomValue * (elRateSlabs.getRate())))*netMonth +"</br>");
					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()/100))* netMonth;
						singleSlab = singleSlab+("</br>"+"("+uomValue+"X"+(elRateSlabs.getRate()/100)+")"+" X"+netMonth+" = "+((uomValue * (elRateSlabs.getRate()/100)))*netMonth +"</br>");
					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType()+"elRateSlabs.getRate() "+elRateSlabs.getRate());
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()/100))* netMonth;
						singleSlab = singleSlab+("</br>"+"("+uomValue+"X"+(elRateSlabs.getRate()/100)+")"+" X"+netMonth+" = "+((uomValue * (elRateSlabs.getRate()/100)))*netMonth +"</br>");
					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()))* netMonth;
						singleSlab = singleSlab+("</br>"+"("+uomValue+"X"+(elRateSlabs.getRate())+")"+" X"+netMonth+" = "+((uomValue * (elRateSlabs.getRate())))*netMonth +"</br>");
					}
				  }
				
				if(elRateMaster.getMaxRate()!=0)
				{
					 totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
				      consolidateBill.put("total", totalAmount);
				}
				else
				{
					  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
				      consolidateBill.put("total", totalAmount);
				}
				consolidateBill.put("slabString", singleSlab);
			    return consolidateBill;
			}
		}
		@SuppressWarnings("unused")
		@Override
		public int findNoOfPersonOfBlock(String propertyId) {
			 String comma = ",";
			 String[] trancode = propertyId.split(comma);
			 Integer personId=0;
			 int personcount=0;
			 for (int i = 0; i < trancode.length; i++) {
				
				 int propid=Integer.parseInt(trancode[i]);
				 List<Integer> personIdList=findPersonDetail(propid);
				 /*for (Integer integer : personIdList) {
					 personId=integer;
					int accountId=findAccountDetail(integer,propid);
					if(accountId!=0){
						personcount++;
						
					}
				}*/
			}
			 logger.info("Total No Of Person:-"+personcount);
			return personcount;
		}
		
		@Override
		public List<?> findPropertyNo(int blockId) {
			List<?> propertyNumbersList = entityManager.createNamedQuery("TariffCalc.readPropertyNames").setParameter("blockId", blockId).getResultList();
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			Map<String, Object> propertyNumberMap = null;
            propertyNumberMap = new HashMap<String, Object>();	
			propertyNumberMap.put("blockId",  blockId);	
			propertyNumberMap.put("propertyId",0);
			propertyNumberMap.put("property_No", "All Property");	
			propertyNumberMap.put("blockName","Blocks");
			result.add(propertyNumberMap);
			
			for (Iterator<?> iterator = propertyNumbersList.iterator(); iterator.hasNext();)
				{
					final Object[] values = (Object[]) iterator.next();
					propertyNumberMap = new HashMap<String, Object>();				
									
					propertyNumberMap.put("blockId", (Integer)values[0]);	
					propertyNumberMap.put("propertyId",(Integer)values[1]);
					propertyNumberMap.put("property_No", (String)values[2]);	
					propertyNumberMap.put("blockName",(String)values[3]);				
				
				result.add(propertyNumberMap);
		     }
	     return result;
		}
		

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
	  public List<Integer> findPersonDetail( int propertyId){
		
		  //List<OwnerProperty> ownerProperties=entityManager.createNamedQuery("TariffCalc.findAllPropertyPersonOwnerList").setParameter("propertyId",propertyId).getResultList();
		  //List<TenantProperty> tenentProperties=entityManager.createNamedQuery("TariffCalc.findAllPropertyPersonOwnerList").setParameter("propertyId",propertyId).getResultList();
			List<Integer> ownerPropertiesId= entityManager.createNamedQuery("TariffCalc.findAllPropertyPersonOwnerList").setParameter("propertyId",propertyId).getResultList();    
			List<Integer> tenentPropertiesId= entityManager.createNamedQuery("TariffCalc.findAllPropertyPersonTenantList").setParameter("propertyId",propertyId).getResultList();    
		    
			List personId= new ArrayList<>();
		  if(ownerPropertiesId.size()!=0){
			 // OwnerProperty opProperty=new OwnerProperty();
			 
			  
			  for (Integer opProperty : ownerPropertiesId) {
				 personId.add(opProperty);
			}
		  }else{
			  logger.info("Not Owner Property");
		  }
          if(tenentPropertiesId.size()!=0){
			 
        	 for (Integer tenantProperty : tenentPropertiesId) {
        		 personId.add(tenantProperty);
			}
        	  //personId=tenentPropertiesId;
		  }else{
			  logger.info("Not Tenent Property");
		  }
          logger.info("personid"+personId);
		  
		  return personId;
	  }
		@SuppressWarnings("unchecked")
		@Override
		public int findAccountDetail(int personId,int propertyId,String serviceTypeList) {
			//List<Integer> accountId= entityManager.createNamedQuery("TariffCalc.findAccountID").setParameter("personId",personId).setParameter("propertyId",propertyId).getResultList();    
			//SELECT  a.accountId  FROM Account a WHERE a.personId =:personId AND a.propertyId=:propertyId
			List<Integer> accountId= entityManager.createQuery("select sme.accountObj.accountId from ServiceMastersEntity sme where sme.accountObj.personId ='"+personId+"' and sme.accountObj.propertyId='"+propertyId+"' and sme.typeOfService='"+serviceTypeList+"'and sme.accountObj.accountId IN(SELECT b.accountObj.accountId from  BillingWizardEntity b WHERE b.serviceMetered='No' and b.serviceMastersEntity.typeOfService='"+serviceTypeList+"') ").getResultList();
			int	accId=0;
			logger.info("accountId"+accountId);
			for (Integer integer : accountId)
			{
				accId=integer;
			}
			return accId;
		}
		@SuppressWarnings("unchecked")
		@Override
		public Date getBillDate(int accountId, String serviceTypeList) {
			
			Date servicedate=null;
			Date serviceStardate=null;
			try{
			List<Date> servicdate= entityManager.createNamedQuery("ServiceMasterEntity.getServiceStartDate").setParameter("accountId",accountId).setParameter("serviceTypeList",serviceTypeList).getResultList();
			for (Date date : servicdate) {
				serviceStardate=date;
			}
				 
			Date billdate=(Date)entityManager.createNamedQuery("BillEntity.getPreviousBillDate").setParameter("accountId",accountId).setParameter("serviceTypeList",serviceTypeList).getSingleResult();
			
			logger.info("billdate::::::::::::::::"+billdate);
			logger.info("serviceStardate::::::::::::::::"+serviceStardate);
			
			if(billdate==null){
				servicedate=serviceStardate;
			}else{
				servicedate=billdate;	
			}
			}catch(Exception e){
				e.printStackTrace();
				
			}
			logger.info("::::::::::::servicedate::::::::::::"+servicedate);
			return servicedate;
		}
		
		@Override
		public Float getPreviousReading(int accountId,String serviceTypeList){
			float previousreading=0.0f;
		String previousreadingl=(String)entityManager.createNamedQuery("BillLineItem.getPreviousreading").setParameter("accountId",accountId).setParameter("typeOfService",serviceTypeList).getSingleResult();
			logger.info("::::::::::::::::::::::::previousreadingl:::::::::::::::::::::::::::::"+previousreadingl);
		    if(previousreadingl==null){
		    	previousreading=0.0f;
		    }else{
		    	previousreading=Float.parseFloat(previousreadingl);	
		    }
			
			return  previousreading;
		}
		@Override
		public Date getPreviousddDate(int accountId,String serviceTypeList) {

			Date serviceStardate=(Date)entityManager.createNamedQuery("ServiceMasterEntity.getServiceStartDate").setParameter("accountId",accountId).setParameter("serviceTypeList",serviceTypeList).getSingleResult();
			
			Date billdate=(Date)entityManager.createNamedQuery("BillEntity.getPreviousddBillDate").setParameter("accountId",accountId).setParameter("serviceTypeList",serviceTypeList).getSingleResult();
			
			logger.info("billdate::::::::::::::::"+billdate);
			logger.info("serviceStardate::::::::::::::::"+serviceStardate);
			Date servicedate=null;
			if(billdate==null){
				servicedate=serviceStardate;
			}else{
				servicedate=billdate;	
			}
			
			logger.info("::::::::::::servicedate::::::::::::"+servicedate);
			return servicedate;
		}
		@SuppressWarnings("unused")
		@Override
		public String tariffCalculationMultiSlabStringWater(WTRateMaster waterRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue) {float totalAmount = 0.0f;
		float slabDifference =0.0f;
		float lastSlabTo = 0.0f; 
		LocalDate fromDate =  new LocalDate(previousBillDate);
	    LocalDate toDate = new LocalDate(currentBillDate);
	    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
	    Period difference = new Period(fromDate, toDate, monthDay);
	    float billableMonths = difference.getMonths();
	    int daysAfterMonth = difference.getDays();
	    
	     Calendar cal1 = Calendar.getInstance();
		 cal1.setTime(previousBillDate);
		 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
		 float netMonth = daysToMonths + billableMonths;
		 netMonth = Math.round(netMonth*100)/100;
		 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
		 String waterSlabString="";
		List<WTRateSlabs> elRateSlabsList = entityManager.createNamedQuery("WTRateSlabs.getWTRateSlabListByRateMasterId",WTRateSlabs.class).setParameter("wtrmid", waterRateMaster1.getWtrmid()).getResultList();
		for (WTRateSlabs waterRateSlabs : elRateSlabsList)
		  {
		    if(lastSlabTo == 0)
		    {
		     slabDifference = (waterRateSlabs.getWtSlabTo() - waterRateSlabs.getWtSlabFrom());
		     lastSlabTo = waterRateSlabs.getWtSlabTo();
		    }
		    else
		    {
		    	slabDifference = (waterRateSlabs.getWtSlabTo() - lastSlabTo);
		    	lastSlabTo = waterRateSlabs.getWtSlabTo();
		    }
			if(waterRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
			{
				if(uomValue > slabDifference)
				{
					uomValue = uomValue - slabDifference;
					totalAmount = totalAmount + (slabDifference * (waterRateSlabs.getWtRate())) *netMonth;
					waterSlabString =waterSlabString+("("+slabDifference+"X"+netMonth+")"+" X"+(waterRateSlabs.getWtRate())+" = "+((slabDifference * (waterRateSlabs.getWtRate())))*netMonth +"</br>");
				}
				else
				{
					if(uomValue > 0)
					{
						totalAmount = totalAmount + (uomValue * (waterRateSlabs.getWtRate()))* netMonth;
						waterSlabString =waterSlabString+("("+uomValue+"X"+netMonth+")"+" X"+(waterRateSlabs.getWtRate())+" = "+((uomValue * (waterRateSlabs.getWtRate())))*netMonth +"</br>");
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if(waterRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
			{
				if(uomValue > slabDifference)
				{
					uomValue = uomValue - slabDifference;
					totalAmount = totalAmount + (slabDifference * (waterRateSlabs.getWtRate()/100)) *netMonth;
					waterSlabString =waterSlabString+("("+slabDifference+"X"+netMonth+")"+" X"+(waterRateSlabs.getWtRate()/100)+" = "+((slabDifference * (waterRateSlabs.getWtRate()/100)))*netMonth +"</br>");
				}
				else
				{
					if(uomValue > 0)
					{
						totalAmount = totalAmount + (uomValue * (waterRateSlabs.getWtRate()/100))* netMonth;
						waterSlabString =waterSlabString+("("+uomValue+"X"+netMonth+")"+" X"+(waterRateSlabs.getWtRate()/100)+" = "+((uomValue * (waterRateSlabs.getWtRate()/100)))*netMonth +"</br>");
						uomValue = uomValue - slabDifference;
					}
				}
			}
		  }
		if(waterRateMaster1.getWtMaxRate()!=0)
		{
			  totalAmount = (totalAmount > waterRateMaster1.getWtMaxRate()) ? waterRateMaster1.getWtMaxRate() : totalAmount;
		}
		else
		{
			  totalAmount = (totalAmount > waterRateMaster1.getWtMinRate()) ? totalAmount : waterRateMaster1.getWtMinRate();
		}
		return waterSlabString;
	}
		@Override
		public String tariffCalculationMultiSlabStringGas(GasRateMaster gasRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue) {
			float totalAmount = 0.0f;
			float slabDifference =0.0f;
			float lastSlabTo = 0.0f; 
			LocalDate fromDate =  new LocalDate(previousBillDate);
		    LocalDate toDate = new LocalDate(currentBillDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(previousBillDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 netMonth = Math.round(netMonth*100)/100;
			 String gasString="";
			 

			List<GasRateSlab> gasRateSlabsList = entityManager.createNamedQuery("GasRateSlab.getGasRateSlabListByRateMasterId",GasRateSlab.class).setParameter("gasrmId", gasRateMaster1.getGasrmid()).getResultList();
			for (GasRateSlab gasRateSlabs : gasRateSlabsList)
			  {
			    if(lastSlabTo == 0)
			    {
			     slabDifference = (gasRateSlabs.getGasSlabTo() - gasRateSlabs.getGasSlabFrom());
			     lastSlabTo = gasRateSlabs.getGasSlabTo();
			    }
			    else
			    {
			    	slabDifference = (gasRateSlabs.getGasSlabTo() - lastSlabTo);
			    	lastSlabTo = gasRateSlabs.getGasSlabTo();
			    }
				if(gasRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (gasRateSlabs.getGasRate())) *netMonth;
						gasString = gasString+("("+slabDifference+"X"+netMonth+")"+" X"+(gasRateSlabs.getGasRate())+" = "+((slabDifference * (gasRateSlabs.getGasRate())))*netMonth +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (gasRateSlabs.getGasRate()))* netMonth;
							gasString = gasString+("("+uomValue+"X"+netMonth+")"+" X"+(gasRateSlabs.getGasRate())+" = "+((uomValue * (gasRateSlabs.getGasRate())))*netMonth +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				if(gasRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (gasRateSlabs.getGasRate()/100)) *netMonth;
						gasString = gasString+("("+slabDifference+"X"+netMonth+")"+" X"+(gasRateSlabs.getGasRate()/100)+" = "+((slabDifference * (gasRateSlabs.getGasRate()/100)))*netMonth +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (gasRateSlabs.getGasRate()/100))* netMonth;
							gasString = gasString+("("+uomValue+"X"+netMonth+")"+" X"+(gasRateSlabs.getGasRate()/100)+" = "+((uomValue * (gasRateSlabs.getGasRate()/100)))*netMonth +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
			  }
			if(gasRateMaster1.getGasMaxRate()!=0)
			{
				  totalAmount = (totalAmount > gasRateMaster1.getGasMaxRate()) ? gasRateMaster1.getGasMaxRate() : totalAmount;
			}
			else
			{
				  totalAmount = (totalAmount > gasRateMaster1.getGasMinRate()) ? totalAmount : gasRateMaster1.getGasMinRate();
			}
			return gasString;
		}
		
		@Override
		@Transactional(propagation=Propagation.SUPPORTS)
		public List<?> getTowerNames(){
			
			List<?> towerNamesList = entityManager.createNamedQuery("OpenNewTicketEntity.getTowerNames").getResultList();
			
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			Map<String, Object> towerNameMap = null;
			towerNameMap = new HashMap<String, Object>();	
			towerNameMap.put("blockId", "111222");
			towerNameMap.put("blockName","All Blocks");
			result.add(towerNameMap);
			for (Iterator<?> iterator = towerNamesList.iterator(); iterator.hasNext();)
				{
					final Object[] values = (Object[]) iterator.next();
					towerNameMap = new HashMap<String, Object>();				
									
					towerNameMap.put("blockId", (Integer)values[0]);
					towerNameMap.put("blockName",(String)values[1]);				
					//towerNameMap.put("allBlock","All Blocks"); 
				result.add(towerNameMap);
		     }
			
	     return result;
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<Integer> getallBlocks() {
			
			return entityManager.createNamedQuery("Blocks.getAllBlocks").getResultList();
		}
		@Override
		public List<Integer> getProertyId(int blockId) {
			
			return entityManager.createNamedQuery("TariffCalc.getPropertyId",Integer.class).setParameter("blockId", blockId).getResultList();
		}
		
		
		@Override
		public String tariffCalculationMultiSlabStringAvgCount(ELRateMaster elRateMaster, Date previousBillDate,Date currentBillDate, Float uomValue, int avgCount) 
		{

			float totalAmount = 0.0f;
			float slabDifference =0.0f;
			float lastSlabTo = 0.0f; 
			LocalDate fromDate =  new LocalDate(previousBillDate);
		    LocalDate toDate = new LocalDate(currentBillDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(previousBillDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths+avgCount;
			 netMonth = Math.round(netMonth*100)/100;
			 String slabString = null;
			 if(netMonth == 0)
			 {
				 netMonth = daysToMonths;
				 logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "+netMonth);
			 }
			 
			List<ELRateSlabs> elRateSlabsList = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
			logger.info("::::::::::::::: Number of rate slab are ::::::::::::::: "+elRateSlabsList.size());
			for (ELRateSlabs elRateSlabs : elRateSlabsList)
			  {
				 if(lastSlabTo == 0)
				    {
				     slabDifference = Math.round((elRateSlabs.getSlabTo() * netMonth - elRateSlabs.getSlabFrom()* netMonth));
				     lastSlabTo = Math.round(elRateSlabs.getSlabTo()* netMonth)+1;
				    } else {
				    	slabDifference = Math.round((elRateSlabs.getSlabTo()* netMonth - lastSlabTo));
				    	lastSlabTo = Math.round(elRateSlabs.getSlabTo()* netMonth)+1;
				    }

				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()));
						slabString = slabString+ ("("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((slabDifference * (elRateSlabs.getRate())))*netMonth +"</br>");
					}else{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()));
							slabString = slabString+("("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((uomValue * (elRateSlabs.getRate())))*netMonth +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()/100)) ;
						slabString = slabString+("("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((slabDifference * (elRateSlabs.getRate()/100)))*netMonth +"</br>");
					}else{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()/100));
							slabString = slabString+("("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((uomValue * (elRateSlabs.getRate()/100)))*netMonth +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()/100));
						slabString = slabString+("("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((slabDifference * (elRateSlabs.getRate()/100)))*netMonth +"</br>");
					}else{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()/100));
							slabString = slabString+("("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((uomValue * (elRateSlabs.getRate()/100)))*netMonth +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()));
						slabString = slabString+("("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((slabDifference * (elRateSlabs.getRate())))*netMonth +"</br>");
					}else{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()));
							slabString = slabString+("("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((uomValue * (elRateSlabs.getRate())))*netMonth +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
			  }
			if(elRateMaster.getMaxRate()!=0)
			{
				  totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
			}else{
				  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
			}
			return slabString;
		}
		@Override
		public HashMap<Object, Object> tariffCalculationSingleSlabAvgCount(ELRateMaster elRateMaster,Date startDate,Date endDate, Float uomValue, int avgCount) 
		{

			float totalAmount = 0.0f;
			LocalDate fromDate =  new LocalDate(endDate);
		    LocalDate toDate = new LocalDate(startDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(startDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths+avgCount;
			 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
			 List<ELRateSlabs> elRateSlabsList = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
			for (ELRateSlabs elRateSlabs : elRateSlabsList)
			  {
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
					totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()))* netMonth;
				}
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
					totalAmount = totalAmount + (uomValue *(elRateSlabs.getRate()/100))* netMonth;
				}
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType()+"elRateSlabs.getRate() "+elRateSlabs.getRate());
					totalAmount = totalAmount + (uomValue  * (elRateSlabs.getRate()/100))* netMonth;
				}
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
					totalAmount = totalAmount + (uomValue  * (elRateSlabs.getRate()))* netMonth;
				}
			  }
			
			if(elRateMaster.getMaxRate()!=0)
			{
				 totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
			      consolidateBill.put("total", totalAmount);
			}
			else
			{
				  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
			      consolidateBill.put("total", totalAmount);
			}
		    return consolidateBill;
		}
		@Override
		public HashMap<Object, Object> tariffCalculationMultiSlabAvgCount(ELRateMaster elRateMaster, Date startDate,Date endDate, Float uomValue, int avgCount) {
			float totalAmount = 0.0f;
			float slabDifference =0.0f;
			float lastSlabTo = 0.0f; 
			LocalDate fromDate =  new LocalDate(startDate);
		    LocalDate toDate = new LocalDate(endDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    String slabString = null;
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(startDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 
			 float netMonth = daysToMonths + billableMonths+avgCount;
			 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
			List<ELRateSlabs> elRateSlabsList = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
			logger.info("::::::::::::::: Number of rate slab are ::::::::::::::: "+elRateSlabsList.size());
			for (ELRateSlabs elRateSlabs : elRateSlabsList)
			  {
				 if(lastSlabTo == 0)
				    {
				     slabDifference = Math.round((elRateSlabs.getSlabTo() * netMonth - elRateSlabs.getSlabFrom()* netMonth));
				     lastSlabTo = Math.round(elRateSlabs.getSlabTo()* netMonth)+1;
				    }
				    else
				    {
				    	slabDifference = Math.round((elRateSlabs.getSlabTo()* netMonth - lastSlabTo));
				    	lastSlabTo = Math.round(elRateSlabs.getSlabTo()* netMonth)+1;
				    }

				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()));
						logger.info("("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((slabDifference * (elRateSlabs.getRate()))) +"</br>");
						slabString = slabString+ ("("+slabDifference+")"+" X"+(elRateSlabs.getRate())+" = "+Math.round(((slabDifference * (elRateSlabs.getRate()))) *100f)/100f +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()));
							logger.info("("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((uomValue * (elRateSlabs.getRate()))) +"</br>");
							slabString = slabString+ ("("+uomValue+")"+" X"+(elRateSlabs.getRate())+" = "+Math.round(((uomValue * (elRateSlabs.getRate()))) *100f)/100f +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()/100));
						logger.info("("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((slabDifference * (elRateSlabs.getRate()/100))) +"</br>");
						slabString = slabString+ ("("+slabDifference+")"+" X"+((elRateSlabs.getRate()/100))+" = "+Math.round(((slabDifference * ((elRateSlabs.getRate()/100))))*100f)/100f+"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()/100));
							logger.info("("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((uomValue * (elRateSlabs.getRate()/100))) +"</br>");
							slabString = slabString+ ("("+uomValue+")"+" X"+((elRateSlabs.getRate()/100))+" = "+Math.round(((uomValue * ((elRateSlabs.getRate()/100)))) *100f)/100f +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()/100));
						logger.info("("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((slabDifference * (elRateSlabs.getRate()/100))) +"</br>");
						slabString = slabString+ ("("+slabDifference+")"+" X"+((elRateSlabs.getRate()/100))+" = "+Math.round(((slabDifference * ((elRateSlabs.getRate()/100))))*100f)/100f +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()/100));
							logger.info("("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((uomValue * (elRateSlabs.getRate()/100))) +"</br>");
							slabString = slabString+ ("("+uomValue+")"+" X"+((elRateSlabs.getRate()/100))+" = "+Math.round(((uomValue * ((elRateSlabs.getRate()/100)))) *100f)/100f +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				
				if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()));
						logger.info("("+slabDifference+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((slabDifference * (elRateSlabs.getRate()))) +"</br>");
						slabString = slabString+ ("("+slabDifference+")"+" X"+(elRateSlabs.getRate())+" = "+Math.round(((slabDifference * (elRateSlabs.getRate()))) *100f)/100f +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()));
							logger.info("("+uomValue+"X"+netMonth+")"+" X"+(elRateSlabs.getRate())+" = "+((uomValue * (elRateSlabs.getRate())))*netMonth +"</br>");
							slabString = slabString+ ("("+uomValue+")"+" X"+(elRateSlabs.getRate())+" = "+Math.round(((uomValue * (elRateSlabs.getRate()))) *100f)/100f +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
			  }
			if(elRateMaster.getMaxRate()!=0)
			{
				  totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
				  consolidateBill.put("total", totalAmount);
			}
			else
			{
				  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
				  consolidateBill.put("total", totalAmount);
			}
			consolidateBill.put("slabString", slabString);
			return consolidateBill;
		}
		@Override
		public HashMap<Object, Object> tariffCalculationRangeSlabAvgCount(ELRateMaster elRateMaster, Date endDate,Date startDate, Float uomValue, int avgCount) {

			float totalAmount = 0.0f;
			LocalDate fromDate =  new LocalDate(endDate);
		    LocalDate toDate = new LocalDate(startDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(startDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths+avgCount;
			 netMonth = Math.round(netMonth*100)/100;
			 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
			 List<ELRateSlabs> elRateSlabsList = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabBetween",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).setParameter("demandCharges", uomValue).getResultList();
			 if(elRateSlabsList.isEmpty())
			    {
			    	  logger.info("Invalid Phase"+"</br>");
			    }
				for (ELRateSlabs elRateSlabs : elRateSlabsList)
				  {
					
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + avgCount * uomValue *elRateSlabs.getRate() * netMonth;

					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount +  avgCount * uomValue *elRateSlabs.getRate()/100 * netMonth;
					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + avgCount * uomValue *elRateSlabs.getRate()/100 * netMonth;
					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + avgCount * uomValue *elRateSlabs.getRate() * netMonth;
					}
				  }
				if(elRateMaster.getMaxRate()!=0)
				{
					 totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
					 consolidateBill.put("total", totalAmount);
				}
				else
				{
					  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
					  consolidateBill.put("total", totalAmount);
				}
			 
			return consolidateBill;
		}
		@Override
		public float waterTariffCalculationMultiSlabAvgCount(WTRateMaster waterRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue) 
		{

			float totalAmount = 0.0f;
			float slabDifference =0.0f;
			float lastSlabTo = 0.0f; 
			LocalDate fromDate =  new LocalDate(previousBillDate);
		    LocalDate toDate = new LocalDate(currentBillDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(previousBillDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 netMonth = Math.round(netMonth*100)/100;
			 if(netMonth == 0)
			 {
				 netMonth = daysToMonths;
				 logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "+netMonth);
			 }
			List<WTRateSlabs> elRateSlabsList = entityManager.createNamedQuery("WTRateSlabs.getWTRateSlabListByRateMasterId",WTRateSlabs.class).setParameter("wtrmid", waterRateMaster1.getWtrmid()).getResultList();
			for (WTRateSlabs waterRateSlabs : elRateSlabsList)
			  {
			   /* if(lastSlabTo == 0)
			    {
			     slabDifference = (waterRateSlabs.getWtSlabTo() - waterRateSlabs.getWtSlabFrom());
			     lastSlabTo = waterRateSlabs.getWtSlabTo();
			    }
			    else
			    {
			    	slabDifference = (waterRateSlabs.getWtSlabTo() - lastSlabTo);
			    	lastSlabTo = waterRateSlabs.getWtSlabTo();
			    }*/
				 if(lastSlabTo == 0)
				    {
				     slabDifference = Math.round((waterRateSlabs.getWtSlabTo() * netMonth - waterRateSlabs.getWtSlabFrom()* netMonth));
				     lastSlabTo = Math.round(waterRateSlabs.getWtSlabTo()* netMonth)+1;
				    }
				    else
				    {
				    	slabDifference = Math.round((waterRateSlabs.getWtSlabTo()* netMonth - lastSlabTo));
				    	lastSlabTo = Math.round(waterRateSlabs.getWtSlabTo()* netMonth)+1;
				    }
				if(waterRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (waterRateSlabs.getWtRate()));
						logger.info("("+slabDifference+")"+" X"+(waterRateSlabs.getWtRate())+" = "+Math.round(((slabDifference * (waterRateSlabs.getWtRate()))) *100f)/100f +"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (waterRateSlabs.getWtRate()));
							logger.info("("+uomValue+")"+" X"+(waterRateSlabs.getWtRate())+" = "+Math.round(((uomValue * (waterRateSlabs.getWtRate()))) *100f)/100f +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
				if(waterRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
				{
					if(uomValue > slabDifference)
					{
						uomValue = uomValue - slabDifference;
						totalAmount = totalAmount + (slabDifference * (waterRateSlabs.getWtRate()/100));
						logger.info("("+slabDifference+")"+" X"+(waterRateSlabs.getWtRate()/100)+" = "+Math.rint(((slabDifference * (waterRateSlabs.getWtRate()/100))) *100f)/100f+"</br>");
					}
					else
					{
						if(uomValue > 0)
						{
							totalAmount = totalAmount + (uomValue * (waterRateSlabs.getWtRate()/100));
							logger.info("("+uomValue+"X"+netMonth+")"+" X"+(waterRateSlabs.getWtRate()/100)+" = "+Math.round(((uomValue * (waterRateSlabs.getWtRate()/100))) *100f)/100f +"</br>");
							uomValue = uomValue - slabDifference;
						}
					}
				}
			  }
			if(waterRateMaster1.getWtMaxRate()!=0)
			{
				  totalAmount = (totalAmount > waterRateMaster1.getWtMaxRate()) ? waterRateMaster1.getWtMaxRate() : totalAmount;
			}
			else
			{
				  totalAmount = (totalAmount > waterRateMaster1.getWtMinRate()) ? totalAmount : waterRateMaster1.getWtMinRate();
			}
			return totalAmount;
		
		}
		@Override
		public float waterTariffCalculationSingleSlabAvgCount(WTRateMaster waterRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue) {


			float totalAmount = 0.0f;
			LocalDate fromDate =  new LocalDate(previousBillDate);
		    LocalDate toDate = new LocalDate(currentBillDate);
		    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		    Period difference = new Period(fromDate, toDate, monthDay);
		    float billableMonths = difference.getMonths();
		    int daysAfterMonth = difference.getDays();
		    
		     Calendar cal1 = Calendar.getInstance();
			 cal1.setTime(previousBillDate);
			 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
			 float netMonth = daysToMonths + billableMonths;
			 netMonth = Math.round(netMonth*100)/100;
			 if(netMonth == 0)
			 {
				 netMonth = daysToMonths;
				 logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "+netMonth);
			 }
			 
			 List<WTRateSlabs> elRateSlabsList = entityManager.createNamedQuery("WTRateSlabs.getWTRateSlabListByRateMasterId",WTRateSlabs.class).setParameter("wtrmid", waterRateMaster1.getWtrmid()).getResultList();
			for (WTRateSlabs elRateSlabs : elRateSlabsList)
			  {
				if(elRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getWtSlabRateType());
					totalAmount = totalAmount + (uomValue * (elRateSlabs.getWtRate()))* netMonth;
				}
				if(elRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getWtSlabRateType());
					totalAmount = totalAmount + (uomValue * (elRateSlabs.getWtRate()/100))* netMonth;
				}
				if(elRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getWtSlabRateType());
					totalAmount = totalAmount + (uomValue * (elRateSlabs.getWtRate()/100))* netMonth;
				}
				if(elRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
				{
					logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getWtSlabRateType());
					totalAmount = totalAmount + (uomValue * (elRateSlabs.getWtRate()))* netMonth;
				}
			  }
			
			if(waterRateMaster1.getWtMaxRate()!=0)
			{
				 totalAmount = (totalAmount > waterRateMaster1.getWtMaxRate()) ? waterRateMaster1.getWtMaxRate() : totalAmount;
			}
			else
			{
				  totalAmount = (totalAmount > waterRateMaster1.getWtMinRate()) ? totalAmount : waterRateMaster1.getWtMinRate();
			}
		    return totalAmount;
		
		}

	@Override
	public Map<String, String> getPreviousStatusAlert(Integer accountId,String serviceType, Integer serviceId) {
		
		Integer elBillId = (Integer) entityManager.createNamedQuery("TariffCalc.GetBillId").setParameter("accountId", accountId).setParameter("serviceType", serviceType).getSingleResult();
		//List<ServiceMastersEntity> initialBillDate = entityManager.createNamedQuery("TariffCalc.GetBillInitialDate").setParameter("serviceId", serviceId).getResultList();
		Integer meterId = (Integer) entityManager.createNamedQuery("TariffCalc.GetMeterId").setParameter("accountId", accountId).setParameter("serviceType", serviceType).getSingleResult();
		List<?> initialReading = entityManager.createNamedQuery("TariffCalc.GetBillInitialReading").setParameter("meterId", meterId).getResultList();
		Map<String, String> response = new LinkedHashMap<>();

		if (elBillId != null) {
			logger.info(":::::::::::::::Not First Bill:::::::::::::::::");
		} else {
			for (Iterator<?> iterator = initialReading.iterator(); iterator.hasNext();) {
				
				final Object[] values = (Object[]) iterator.next();
				if (((String)values[0]).equalsIgnoreCase("Initial Reading")) {
					logger.info("meterparametervalue$$$$$$$"+ (String)values[1]);
				} else {
					response.put("Initial Reading", "Initial Reading Not Found");
				}
				if (((String)values[0]).equalsIgnoreCase("Meter Constant")) {
					logger.info("meterparametervalue@@@@@@@@@"+ (String)values[1]);
				} else {
					response.put("Meter Constant", "Meter Constant Not Found");
				}
			}
		}
		

		/*-------------------------------------------------------------------------------------------------------------------------*/
		List<Object> dgapplicable = todApplicable(serviceType, accountId);
		logger.info("dgapplicable size ------------ " + dgapplicable.size());
		if(dgapplicable.get(1)!=null){
			
			if (dgapplicable.get(1).equals("Yes")) {
				logger.info(":::::::::::Only DG Applicable::::::::::::: ");

				if (elBillId != null && (meterId != null)) {

				} else {

					for (Iterator<?> iterator = initialReading.iterator(); iterator.hasNext();) {
						
						final Object[] values = (Object[]) iterator.next();	
						{
							if (((String)values[0]).equalsIgnoreCase("DG Initial Reading")) {
								logger.info("DG Initial Reading"+ (String)values[1]);
							}else{
								response.put("DG Initial Reading", "DG Initial Reading Not Defined");
							}
							if (((String)values[0]).equalsIgnoreCase("DG Meter Constant")) {
								logger.info("DG Meter Constant"+ ((String)values[1]));

							}else{
								response.put("DG Meter Constant", "DG Meter Constant Not Defined");
							}

						}

					}
				}
			}
		}

		return response;
	}
	@Override
	public String getAccountMetered(int accountId, String serviceTypeList) {
		
		return (String) entityManager.createNamedQuery("BillingWizardEntity.getUnmetetedAccountId").setParameter("accountId",accountId).setParameter("serviceTypeList", serviceTypeList).getSingleResult();
	}
	@Override
	public int getAccountIdOnAccountNo(String accountno) {
		return  (int) entityManager.createQuery("select o.accountId from Account o where  o.accountNo='"+accountno+"'").getSingleResult();
	}
	@Override
	public String getMeterType(Integer accountId, String serviceType) {
		try {
			return (String) entityManager.createNamedQuery("ElectricityMetersEntity.getMeterType").setParameter("accountId",accountId).setParameter("serviceType", serviceType).getSingleResult();
		} catch (Exception e) 
		{
			logger.info("--------------------- with account id"+accountId + " And service type "+serviceType +" No Meter present");
			return null;
		}
		
	}
	@Override
	public HashMap<Object, Object> mdiCalculation(ELRateMaster elRateMaster,Date previousBillDate, Date currentBillDate, Float uomValue,float contractDemand) 
	{
		float totalAmount = 0.0f;
		LocalDate fromDate =  new LocalDate(previousBillDate);
	    LocalDate toDate = new LocalDate(currentBillDate);
	    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
	    Period difference = new Period(fromDate, toDate, monthDay);
	    float billableMonths = difference.getMonths();
	    int daysAfterMonth = difference.getDays();
	    
	     Calendar cal1 = Calendar.getInstance();
		 cal1.setTime(previousBillDate);
		 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
		 float netMonth = daysToMonths + billableMonths;
		 netMonth = Math.round(netMonth*100)/100;
		 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
		
		 if(contractDemand>uomValue)
		 {
			 float penaltyUnits = contractDemand -  uomValue;
			 List<ELRateSlabs> elRateSlabsList = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
				for (ELRateSlabs elRateSlabs : elRateSlabsList)
				  {
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + (penaltyUnits * (elRateSlabs.getRate()))* netMonth;
					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + (penaltyUnits * (elRateSlabs.getRate()/100))* netMonth;
					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType()+"elRateSlabs.getRate() "+elRateSlabs.getRate());
						totalAmount = totalAmount + (penaltyUnits * (elRateSlabs.getRate()/100))* netMonth;
					}
					if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
					{
						logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType());
						totalAmount = totalAmount + (penaltyUnits * (elRateSlabs.getRate()))* netMonth;
					}
				  }
		 }
		 else {
			logger.info(" -------------- Recorded Demand is less than contracted ------------ ");
		}
		 
		if(elRateMaster.getMaxRate()!=0)
		{
			 totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
			 consolidateBill.put("total", totalAmount);
		}
		else
		{
			  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
			  consolidateBill.put("total", totalAmount);
		}
		return consolidateBill;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getBillDetails(ServiceMastersEntity serviceMastersEntities, Account account,String serviceType,Date presentbilldate)
	{
		String postType="Bill";
		String status ="Cancelled";
		Calendar cal = Calendar.getInstance();
		cal.setTime(presentbilldate);
		int month = cal.get(Calendar.MONTH);
		int montOne =month +1;
		int year = cal.get(Calendar.YEAR);
		Map<String, Object> billParameters =  new LinkedHashMap<>();
		ElectricityBillEntity currentMonthBill; 
		
	  try
		{
		currentMonthBill= entityManager.createNamedQuery("ElectricityBillsEntity.getPreviousBillBack",ElectricityBillEntity.class).setParameter("typeOfService", serviceType).setParameter("accountId", account.getAccountId()).setParameter("month", montOne).setParameter("year", year).setParameter("postType",postType ).setParameter("status", status).getSingleResult();
		}
		catch(Exception exception)
		{
			logger.info("------------ Exception while getting current month bill -----------------------");
			currentMonthBill=null;
		}
	  if(currentMonthBill==null)
	  {
			Integer elBillId=(Integer) entityManager.createNamedQuery("TariffCalc.GetBillId").setParameter("accountId", account.getAccountId()).setParameter("serviceType", serviceType).getSingleResult();
			List<ServiceParametersEntity> parametersEntities = serviceParameterService.findAllByParentIdForDG(serviceMastersEntities.getServiceMasterId());
			logger.info("parametersEntities.size() --------------"+parametersEntities.size());
			if(elBillId!=null)
			{
				ElectricityBillEntity electricityBillEntity = electricityBillsService.find(elBillId);
				billParameters.put("previousBillDate",electricityBillEntity.getBillDate());
				List<String> billParametersEntities =entityManager.createNamedQuery("TariffCalc.GetBillStatus").setParameter("elBillId", elBillId).getResultList();
				for (Iterator<?> iterator = billParametersEntities.iterator(); iterator.hasNext();){
					
					final Object[] values = (Object[]) iterator.next();	
					
					if(parametersEntities.isEmpty())
					{
						billParameters.put("dgMeterConstant","NA");
						billParameters.put("dgPreviousReading","NA");
						billParameters.put("dgPresentReading","NA");
						billParameters.put("dgUnits","NA");
					}else {
						for (ServiceParametersEntity serviceParametersEntity : parametersEntities) 
						{
							if((serviceParametersEntity.getServiceParameterMaster().getSpmName().trim().equalsIgnoreCase("DG Applicable"))&&(serviceParametersEntity.getServiceParameterValue().trim().equalsIgnoreCase("Yes")))
							{
								logger.info("DG applicable if previous bill present");
								if(((String)values[0]).trim().equalsIgnoreCase("Present  DG reading")){
									   billParameters.put("dgPreviousReading",(String)values[1]);
									}
								if(((String)values[0]).trim().equalsIgnoreCase("DG Meter Constant")){
									   billParameters.put("dgMeterConstant",(String)values[1]);
									}
								billParameters.put("dgPresentReading","");
							}
						}
					}
				   if(((String)values[0]).equalsIgnoreCase("Previous status")){
					   billParameters.put("previousStatus",(String)values[1]);
					}
					if(((String)values[0]).equalsIgnoreCase("Present reading")){
					 billParameters.put("previousReading",(String)values[1]);
					}if(((String)values[0]).equalsIgnoreCase("Meter Constant")){
						 billParameters.put("meterConstant",(String)values[1]);
					}
				}
			}
			else
			{
				billParameters.put("previousBillDate",serviceMastersEntities.getServiceStartDate());
				billParameters.put("previousStatus", "Normal");
				logger.info("--------------- sitting intial bill parameter values ---------------------");
				Integer meterId=(Integer) entityManager.createNamedQuery("TariffCalc.GetMeterId").setParameter("accountId", account.getAccountId()).setParameter("serviceType", serviceType).getSingleResult();
				List<?> initialReading=entityManager.createNamedQuery("TariffCalc.GetBillInitialReading").setParameter("meterId", meterId).getResultList();
				
				for(Iterator<?> iterator = initialReading.iterator(); iterator.hasNext();) {
					
					final Object[] values = (Object[]) iterator.next();					
					if(parametersEntities.isEmpty())
					{
						logger.info("DG applicable no for intial bill parameter values ");
						billParameters.put("dgMeterConstant","NA");
						billParameters.put("dgPreviousReading","NA");
						billParameters.put("dgPresentReading","NA");
						billParameters.put("dgUnits","NA");
					}
					else {
						for (ServiceParametersEntity serviceParametersEntity : parametersEntities) 
						{
							if((serviceParametersEntity.getServiceParameterMaster().getSpmName().trim().equalsIgnoreCase("DG Applicable"))&&(serviceParametersEntity.getServiceParameterValue().trim().equalsIgnoreCase("Yes")))
							{
								if(((String)values[0]).equalsIgnoreCase("DG Initial Reading")){
									 billParameters.put("dgPreviousReading",(String)values[1]);
									}
									if(((String)values[0]).equalsIgnoreCase("DG Meter Constant")){
									billParameters.put("dgMeterConstant",(String)values[1]);
									}
									billParameters.put("dgPresentReading","");
							}
							
						}
					}
						if(((String)values[0]).equalsIgnoreCase("Initial Reading")){
							billParameters.put("previousReading",(String)values[1]);
						}
						if(((String)values[0]).equalsIgnoreCase("Meter Constant")){
							billParameters.put("meterConstant",(String)values[1]);
						}
				}
			}  
			
			billParameters.put("accountNo",account.getAccountNo());
			billParameters.put("serviceType",serviceType);
			billParameters.put("presentStatus","Normal");
			billParameters.put("presentbilldate",presentbilldate);
			
			if(!serviceMastersEntities.getTodApplicable().trim().equalsIgnoreCase("Yes")){
				billParameters.put("tod1","NA");
				billParameters.put("tod2","NA");
				billParameters.put("tod3","NA");
			}
			
			String typesofmeter = getMeterType(account.getAccountId(), serviceType);
			if(typesofmeter!=null)
			{
				if (!typesofmeter.trim().equalsIgnoreCase("Trivector Meter"))
				{
					billParameters.put("pfReading","NA");
					billParameters.put("recordedDemand","NA");
				}
			}
			
			if(serviceType.trim().equalsIgnoreCase("Water"))
			{
				billParameters.put("tod1","NA");
				billParameters.put("tod2","NA");
				billParameters.put("tod3","NA");
				billParameters.put("pfReading","NA");
				billParameters.put("recordedDemand","NA");
				billParameters.put("dgMeterConstant","NA");
				billParameters.put("dgPreviousReading","NA");
				billParameters.put("dgPresentReading","NA");
				billParameters.put("dgUnits","NA");
			}
			
			if(serviceType.trim().equalsIgnoreCase("Gas"))
			{
				billParameters.put("tod1","NA");
				billParameters.put("tod2","NA");
				billParameters.put("tod3","NA");
				billParameters.put("pfReading","NA");
				billParameters.put("recordedDemand","NA");
				billParameters.put("dgMeterConstant","NA");
				billParameters.put("dgPreviousReading","NA");
				billParameters.put("dgPresentReading","NA");
				billParameters.put("dgUnits","NA");
			}
			
			if(serviceType.trim().equalsIgnoreCase("Solid Waste"))
			{
				billParameters.put("presentStatus","NA");
				billParameters.put("previousStatus","NA");
				billParameters.put("pfReading","NA");
				billParameters.put("recordedDemand","NA");
				billParameters.put("previousReading","NA");
				billParameters.put("meterConstant","NA");
				billParameters.put("tod1","NA");
				billParameters.put("tod2","NA");
				billParameters.put("tod3","NA");
				billParameters.put("dgMeterConstant","NA");
				billParameters.put("dgPreviousReading","NA");
				billParameters.put("dgPresentReading","NA");
				billParameters.put("dgUnits","NA");
			}
			
			if(serviceType.trim().equalsIgnoreCase("Others"))
			{
				billParameters.put("presentStatus","NA");
				billParameters.put("presentReading","NA");
				billParameters.put("units","NA");
				billParameters.put("pfReading","NA");
				billParameters.put("recordedDemand","NA");
				billParameters.put("previousStatus","NA");
				billParameters.put("previousReading","NA");
				billParameters.put("meterConstant","NA");
				billParameters.put("tod1","NA");
				billParameters.put("tod2","NA");
				billParameters.put("tod3","NA");
				billParameters.put("dgMeterConstant","NA");
				billParameters.put("dgPreviousReading","NA");
				billParameters.put("dgPresentReading","NA");
				billParameters.put("dgUnits","NA");
			}
	  }
	  else 
	  {
		  logger.info(" -------------------- Bill is present for this month --------------- "+montOne);
	  }
		return billParameters;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getNewMeterDetails(String currentBillDate, String previousBillDate,
			String typeOfService, int accountId,float previousReding,float presentReading) {
		double initiareading=0;
		double finalreading=0;
		float newmetrreading=0;
		double newmeterInitailreading=0;
		double newmeterfinalreding=0;
		float oldMeterUnit=0;
		float	oldMeterUnit1=0;
		float oldmeterReding=0;
		float oldMeterTotalreading=0;
		int elmeterId=0;
		double units=0;
		Map<Object, Object> hmap=new HashMap<>();
		int count=0;
		int countFlag=0;
		String meterConstant="1";
		logger.info("currentBillDate"+currentBillDate+" previousBillDate"+previousBillDate+"typeOfService"+typeOfService+"accountId"+accountId);
		List<ElectricityMeterLocationEntity> meterLocationEntities=entityManager.createQuery("SELECT e  FROM ElectricityMeterLocationEntity e WHERE e.meterReleaseDate >=TO_DATE('"+previousBillDate+"', 'YYYY-MM-DD')AND e.meterReleaseDate <=TO_DATE('"+currentBillDate+"', 'YYYY-MM-DD') AND e.account.accountId ='"+accountId+"' AND e.electricityMetersEntity.elMeterId IN (SELECT el.elMeterId FROM ElectricityMetersEntity el WHERE el.typeOfServiceForMeters='"+typeOfService+"')").getResultList();
		for (ElectricityMeterLocationEntity electricityMeterLocationEntity : meterLocationEntities) {
			finalreading=electricityMeterLocationEntity.getFinalReading();
			initiareading=electricityMeterLocationEntity.getIntialReading();
			logger.info(":::::::::::finalreading:::::::::"+finalreading);
			logger.info(":::::::::::initiareading:::::::::"+initiareading);
			if(count>=1){
				logger.info(":::::::::::finalreading:::::::::"+finalreading+"initiareading"+initiareading);
				oldMeterTotalreading=(float)(oldMeterTotalreading+(finalreading-initiareading));				
				hmap.put("oldMeterTotalreading"+count+"", oldMeterTotalreading);
				logger.info("::::;;oldMeterTotalreading"+count+":::::::::::"+ oldMeterTotalreading);
			} if(count==0){
				logger.info(":::::::::::finalreading:::::::::"+finalreading+"previousReding"+previousReding);
				oldMeterUnit1=(float) (finalreading-previousReding);						
				hmap.put("oldmeterReading", oldMeterUnit1);
				logger.info(":::::::::::oldMeterUnit1:::::::::"+oldMeterUnit1);
			}
			 count++;
			 countFlag++;
		
		}
		
		if(count==1 && countFlag==meterLocationEntities.size()){
			oldMeterUnit=(float) (finalreading-previousReding);
			oldmeterReding=oldMeterUnit;
		}else	if(count>1 && countFlag==meterLocationEntities.size()){
			oldmeterReding=oldMeterUnit1+oldMeterTotalreading;
		}
		
		logger.info(":::::::::::oldmeterReding:::::::::"+oldmeterReding);
		List<ElectricityMeterLocationEntity> meterLocationEntities1=entityManager.createQuery("SELECT e  FROM ElectricityMeterLocationEntity e WHERE e.meterFixedDate >=TO_DATE('"+previousBillDate+"', 'YYYY-MM-DD') AND e.account.accountId ='"+accountId+"' AND e.electricityMetersEntity.elMeterId IN (SELECT el.elMeterId FROM ElectricityMetersEntity el WHERE el.typeOfServiceForMeters='"+typeOfService+"' AND el.meterStatus='In Service')").getResultList();
		for (ElectricityMeterLocationEntity electricityMeterLocationEntity : meterLocationEntities1) {
			newmeterInitailreading=newmeterInitailreading+electricityMeterLocationEntity.getIntialReading();
			newmeterfinalreding=newmeterfinalreding+electricityMeterLocationEntity.getFinalReading();
			elmeterId=electricityMeterLocationEntity.getElectricityMetersEntity().getElMeterId();
		}
		//List<ElectricityMeterParametersEntity> initialReading=entityManager.createNamedQuery("TariffCalc.GetBillInitialReading").setParameter("meterId", meterId).getResultList();
		//SELECT e FROM  ElectricityMeterParametersEntity e  WHERE e.electricityMetersEntity.elMeterId=:meterId ORDER BY e.parameterMasterObj.mpmSequence ASC
		/*if(meterParametersEntity.getParameterMasterObj().getMpmName().equalsIgnoreCase("Meter Constant")){
		put("Meter Constant",meterParametersEntity.getElMeterParameterValue());
		}*/
		try{
			meterConstant=(String) entityManager.createQuery("SELECT e.elMeterParameterValue FROM  ElectricityMeterParametersEntity e WHERE e.electricityMetersEntity.elMeterId='"+elmeterId+"' AND e.parameterMasterObj.mpmName='Meter Constant'").getSingleResult();
		}catch(NoResultException ex){
			
		}
		logger.info(":::::::::previousstaus::::::"+meterConstant);
		if(meterConstant==null){
			meterConstant="1";
		}
		
		newmetrreading=(float) (newmetrreading+(presentReading-newmeterInitailreading));
		hmap.put("newmetrreading", newmetrreading);
		units=newmetrreading+oldmeterReding;
		logger.info("oldmeterReding"+oldmeterReding+":::::::::::"+newmetrreading);
		hmap.put("Units", units);
		hmap.put("meterConstant",meterConstant);
		logger.info(":::::::::hmap:::::::::"+hmap);
		return hmap;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getDGNewMeterDetails(String currentBillDate, String previousBillDate,
			String typeOfService, int accountId,float previousReding,float presentReading) {
		double initiareading=0;
		double finalreading=0;
		float newmetrreading=0;
		double newmeterInitailreading=0;
		double newmeterfinalreding=0;
		float oldMeterUnit=0;
		float	oldMeterUnit1=0;
		float oldmeterReding=0;
		float oldMeterTotalreading=0;
		int elmeterId=0;
		double units=0;
		Map<Object, Object> hmap=new HashMap<>();
		int count=0;
		int countFlag=0;
		String meterConstant="1";
		logger.info("currentBillDate"+currentBillDate+" previousBillDate"+previousBillDate+"typeOfService"+typeOfService+"accountId"+accountId);
		List<ElectricityMeterLocationEntity> meterLocationEntities=entityManager.createQuery("SELECT e  FROM ElectricityMeterLocationEntity e WHERE e.meterReleaseDate >=TO_DATE('"+previousBillDate+"', 'YYYY-MM-DD')AND e.meterReleaseDate <=TO_DATE('"+currentBillDate+"', 'YYYY-MM-DD') AND e.account.accountId ='"+accountId+"' AND e.electricityMetersEntity.elMeterId IN (SELECT el.elMeterId FROM ElectricityMetersEntity el WHERE el.typeOfServiceForMeters='"+typeOfService+"')").getResultList();
		for (ElectricityMeterLocationEntity electricityMeterLocationEntity : meterLocationEntities) {
			finalreading=electricityMeterLocationEntity.getDgFinalReading();
			initiareading=electricityMeterLocationEntity.getDgIntitalReading();
			logger.info(":::::::::::finalreading:::::::::"+finalreading);
			logger.info(":::::::::::initiareading:::::::::"+initiareading);
			if(count>=1){
				logger.info(":::::::::::finalreading:::::::::"+finalreading+"initiareading"+initiareading);
				oldMeterTotalreading=(float)(oldMeterTotalreading+(finalreading-initiareading));				
				hmap.put("oldMeterTotalreading"+count+"", oldMeterTotalreading);
				logger.info("::::;;oldMeterTotalreading"+count+":::::::::::"+ oldMeterTotalreading);
			} if(count==0){
				logger.info(":::::::::::finalreading:::::::::"+finalreading+"previousReding"+previousReding);
				oldMeterUnit1=(float) (finalreading-previousReding);						
				hmap.put("oldmeterReading", oldMeterUnit1);
				logger.info(":::::::::::oldMeterUnit1:::::::::"+oldMeterUnit1);
			}
			 count++;
			 countFlag++;
		
		}
		
		if(count==1 && countFlag==meterLocationEntities.size()){
			oldMeterUnit=(float) (finalreading-previousReding);
			oldmeterReding=oldMeterUnit;
		}else	if(count>1 && countFlag==meterLocationEntities.size()){
			oldmeterReding=oldMeterUnit1+oldMeterTotalreading;
		}
		
		logger.info(":::::::::::oldmeterReding:::::::::"+oldmeterReding);
		List<ElectricityMeterLocationEntity> meterLocationEntities1=entityManager.createQuery("SELECT e  FROM ElectricityMeterLocationEntity e WHERE e.meterFixedDate >=TO_DATE('"+previousBillDate+"', 'YYYY-MM-DD') AND e.account.accountId ='"+accountId+"' AND e.electricityMetersEntity.elMeterId IN (SELECT el.elMeterId FROM ElectricityMetersEntity el WHERE el.typeOfServiceForMeters='"+typeOfService+"' AND el.meterStatus='In Service')").getResultList();
		for (ElectricityMeterLocationEntity electricityMeterLocationEntity : meterLocationEntities1) {
			newmeterInitailreading=newmeterInitailreading+electricityMeterLocationEntity.getDgIntitalReading();
			newmeterfinalreding=newmeterfinalreding+electricityMeterLocationEntity.getDgFinalReading();
			elmeterId=electricityMeterLocationEntity.getElectricityMetersEntity().getElMeterId();
		}
		//List<ElectricityMeterParametersEntity> initialReading=entityManager.createNamedQuery("TariffCalc.GetBillInitialReading").setParameter("meterId", meterId).getResultList();
		//SELECT e FROM  ElectricityMeterParametersEntity e  WHERE e.electricityMetersEntity.elMeterId=:meterId ORDER BY e.parameterMasterObj.mpmSequence ASC
		/*if(meterParametersEntity.getParameterMasterObj().getMpmName().equalsIgnoreCase("Meter Constant")){
		put("Meter Constant",meterParametersEntity.getElMeterParameterValue());
		}*/
		try{
			meterConstant=(String) entityManager.createQuery("SELECT e.elMeterParameterValue FROM  ElectricityMeterParametersEntity e WHERE e.electricityMetersEntity.elMeterId='"+elmeterId+"' AND e.parameterMasterObj.mpmName='Meter Constant'").getSingleResult();
		}catch(NoResultException ex){
			
		}
		logger.info(":::::::::previousstaus::::::"+meterConstant);
		if(meterConstant==null){
			meterConstant="1";
		}
		
		newmetrreading=(float) (newmetrreading+(presentReading-newmeterInitailreading));
		hmap.put("newmetrreading", newmetrreading);
		units=newmetrreading+oldmeterReding;
		logger.info("oldmeterReding"+oldmeterReding+":::::::::::"+newmetrreading);
		hmap.put("Units", units);
		hmap.put("meterConstant",meterConstant);
		logger.info(":::::::::hmap:::::::::"+hmap);
		return hmap;
	}
	@SuppressWarnings("unchecked")
	@Override
	public float claculateAvgforSimilarTypeofFlat(int accountId,
			Date currentBillDate, String typeOfService) {
		String propertyType=(String) entityManager.createQuery("SELECT p.propertyType FROM Property p WHERE p.propertyId IN(SELECT a.propertyId FROM Account a Where a.accountId='"+accountId+"')").getSingleResult();
		logger.info(":::::::::::::propertyType::::::::::"+propertyType);
		float avgUnit=0.0f;
		int count=0;
		float average=0.0f;
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentBillDate);
		int month1 = cal.get(Calendar.MONTH);
		int month = month1 + 1;
		int year = cal.get(Calendar.YEAR);
		List<Integer> listPropertyId=entityManager.createQuery("SELECT p.propertyId FROM Property p WHERE p.propertyType='"+propertyType+"' ").getResultList();
		for (Integer propertyId : listPropertyId) {
			logger.info("::::::::::propertyId::::::::::"+propertyId);
			List<Integer> accIdList= entityManager.createQuery("SELECT a.accountId FROM Account a WHERE a.propertyId='"+propertyId+"'").getResultList();
			logger.info(":::::::::::::::"+accIdList.size());
			if(accIdList.size()>0){
			logger.info("::::::::::accId::::::::::"+accIdList.get(0));
			int accId=accIdList.get(0);
			//List<String> avgString=entityManager.createQuery("SELECT s.serviceParameterValue FROM ServiceParametersEntity s  WHERE serviceParameterMaster.spmName='Average Unit' AND serviceParameterMaster.serviceType='"+typeOfService+"' AND s.serviceMastersEntity.accountObj.accountId='"+accId+"' AND s.serviceMastersEntity.accountObj.accountId!='"+accountId+"'").getResultList();
			List<String> avgString=entityManager.createQuery("SELECT e.elBillParameterValue FROM ElectricityBillParametersEntity e WHERE e.billParameterMasterEntity.bvmName='Units' AND e.electricityBillEntity.elBillId IN (SELECT el.elBillId FROM ElectricityBillEntity el WHERE el.accountId='"+accId+"' AND el.accountId!='"+accountId+"' AND el.typeOfService='"+typeOfService+"' AND EXTRACT(month FROM el.billDate)='"+month+"' AND EXTRACT(year FROM el.billDate)='"+year+"' )").getResultList();
					if(avgString.size()>0){
				logger.info(":::::::::::::avgString::::::::::"+avgString.get(0));
				avgUnit=avgUnit+Float.parseFloat(avgString.get(0));
				count++;
			}
			
			}
		}
		logger.info(":::::::::avgUnit:::::::"+avgUnit+"::::::::::count:::::"+count);
		average=avgUnit/count;
		logger.info(":::::::::average:::::::"+average);
		return average;
	}
	@SuppressWarnings("unchecked")
	@Override
	public float claculateAvgforthreeYearData(int accountId,
			Date currentBillDate, String typeOfService) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentBillDate);
		int month1 = cal.get(Calendar.MONTH);
		int month = month1 + 1;
		int year = cal.get(Calendar.YEAR);
		int yearCalc=0;
		int count=0;
	float avgUnit=0.0f;
	
		float average=0.0f;
		for (int i = 0; i < 2; i++) {
			yearCalc=(year-(i+1));
			logger.info("yearCalc::::::::::::::::"+yearCalc);
			List<String> avgString=entityManager.createQuery("SELECT e.elBillParameterValue FROM ElectricityBillParametersEntity e WHERE e.billParameterMasterEntity.bvmName='Units' AND e.electricityBillEntity.elBillId IN (SELECT el.elBillId FROM ElectricityBillEntity el WHERE el.accountId='"+accountId+"'  AND el.typeOfService='"+typeOfService+"' AND EXTRACT(month FROM el.billDate)='"+month+"' AND EXTRACT(year FROM el.billDate)='"+yearCalc+"' )").getResultList();
			if(avgString.size()>0){
				logger.info(":::::::::::::avgString::::::::::"+avgString.get(0));
				avgUnit=avgUnit+Float.parseFloat(avgString.get(0));
				count++;
			}
		}
		logger.info(":::::::::avgUnit:::::::"+avgUnit+"::::::::::count:::::"+count);
		average=avgUnit/count;
		logger.info(":::::::::average:::::::"+average);
		return average;
	}
	@Override
	public Integer getservicIdByAccountIdServiceType(int accountId,String serviceType) {
		
		return (Integer) entityManager.createQuery("SELECT e.serviceMasterId FROM ServiceMastersEntity e WHERE e.accountObj.accountId='"+accountId+"' AND e.typeOfService='"+serviceType+"'").getSingleResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public Float getConsumptionUnitBasedOnSqFt(int propId, Float consumptionUnit) {
		float areaOfFlat=0;
		List<Property> listPropertDeatil=entityManager.createQuery("SELECT p FROM Property p WHERE p.propertyId='"+propId+"' ").getResultList();
		for (Property property : listPropertDeatil) {
			areaOfFlat=property.getArea();
			logger.info("::::::::::::::::Area of Flat:::::::::"+areaOfFlat);
			logger.info("::::::::::::::::consumptionUnit:::::::::"+consumptionUnit);
		}
		float unitconsued=Math.round(areaOfFlat*consumptionUnit);
		logger.info(":::::::::unitconsued:::::::::"+unitconsued);
		return unitconsued;
	}
	@Override
	public String getPreviousBillStatus(int accountId, String serviceTypeList,Date currentBillDate) {		
		String previousstaus="Not Generated";
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentBillDate);
		int month1 = cal.get(Calendar.MONTH);
		int month = month1 + 1;
		int year = cal.get(Calendar.YEAR);
		try{
			//previousstaus=(String) entityManager.createQuery("SELECT MAX(b.status) FROM ElectricityBillEntity b WHERE b.accountId='"+accountId+"' AND b.typeOfService='"+serviceTypeList+"' AND b.postType='Bill' AND b.billType='Bulk bill' AND b.status NOT LIKE '%Cancelled%' AND EXTRACT(month FROM b.billDate)='"+month+"' AND EXTRACT(year FROM b.billDate)='"+year+"'").getSingleResult();
			previousstaus=(String) entityManager.createQuery("SELECT MAX(b.status) FROM ElectricityBillEntity b WHERE b.accountId='"+accountId+"' AND b.typeOfService='"+serviceTypeList+"' AND b.postType='Bill' AND EXTRACT(month FROM b.billDate)='"+month+"' AND EXTRACT(year FROM b.billDate)='"+year+"'").getSingleResult();
		}catch(NoResultException ex){
			
		}
		if(previousstaus==null){
			previousstaus="Not Generated";
		}
		return previousstaus;
	}
	@Override
	public String getAccontNoONAccountId(int accountId) {
		
		return (String) entityManager.createQuery("SELECT a.accountNo FROM Account a WHERE a.accountId='"+accountId+"'").getSingleResult();
	}
	
	@Override
	public int findAccountDetailByServiceType(Integer personId,Integer propartyId, String serviceType) 
	{
		List<Integer> integers=	entityManager.createNamedQuery("TariffCalc.findAccountDetailByServiceType",Integer.class).setParameter("personid",personId).setParameter("propertyid",propartyId).setParameter("servicetype", serviceType).getResultList();
		int accountId=0;
		for (Integer integer : integers)
		{
			accountId =integer;
		}
		return accountId;
	}
	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public float getMeterConstantForNewMeter(String pesentDate, String strDate,
			String typeOfService, int accountId, float previousReding,
			float presentReading) {
		float meterConstant=1;
		List<ElectricityMeterLocationEntity> meterLocationEntities1=entityManager.createQuery("SELECT e  FROM ElectricityMeterLocationEntity e WHERE e.meterFixedDate >=TO_DATE('"+strDate+"', 'YYYY-MM-DD') AND e.account.accountId ='"+accountId+"' AND e.electricityMetersEntity.elMeterId IN (SELECT el.elMeterId FROM ElectricityMetersEntity el WHERE el.typeOfServiceForMeters='"+typeOfService+"' AND el.meterStatus='In Service')").getResultList();
		return 0;
	}
	@Override
	public Float getdistributionLossUnit(Date previousBillDate,
			int accountId) {
		Float lossperrcentage=0.0f;
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		int month1 = cal.get(Calendar.MONTH);
		int month = month1 + 1;
		int year = cal.get(Calendar.YEAR);
		Float lossUnit=0.0f;
		Float previousUOMReading=0.0f;
		int elbillid=0;
		try{
			elbillid=(int) entityManager.createQuery("SELECT e.elBillId FROM ElectricityBillEntity e WHERE  e.typeOfService='Gas'  AND e.postType='Bill' AND e.status!='Cancelled' AND e.billType='Normal' AND e.accountId='"+accountId+"' AND EXTRACT(month FROM e.billDate)='"+month+"' AND EXTRACT(year FROM e.billDate)='"+year+"'").getSingleResult();
		}catch(NoResultException e){
			
		}
		if(elbillid!=0){
			
			String paraMeterName4 = "Units";
			String units = null;
			units = billParameterService.getParameterValue(elbillid,"Gas", paraMeterName4);
			logger.info("units -------------- " + units);
			previousUOMReading=Float.parseFloat(units);
		}else{
			return 0.0f;
		}
		
		try {
			lossperrcentage=(float) entityManager.createQuery("SELECT g.lossPercentage FROM GasDistributionLosses g WHERE EXTRACT(month FROM g.month)='"+month+"' AND EXTRACT(year FROM g.month)='"+year+"'").getSingleResult();
		logger.info(":::::::::lossperrcentage:::::::::::"+lossperrcentage);
		} catch (NoResultException e) {
		}
		if(lossperrcentage!=null ){
			lossUnit=((lossperrcentage*previousUOMReading)/100);	
		}else{
			
		}
		
		Double lunit = Math.round(lossUnit*100.0)/100.0;
		logger.info(":::::::lunit::::::"+lunit);
		
		return lunit.floatValue();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> getTallyDetailData(int billId) {
		Integer accountId=null;
		
		try {
			accountId=(Integer) entityManager.createQuery("SELECT e.accountId FROM ElectricityBillEntity e WHERE e.elBillId='"+billId+"'").getSingleResult();
		logger.info(":::::::::accountId:::::::::::"+accountId);
		} catch (NoResultException e) {
		}

		Map<String, Object> invoiceLineItemMap = null;
		List<Map<String, Object>> itemsList = new ArrayList<Map<String, Object>>();
		List<Object> account=entityManager.createQuery("sELECT a.accountNo,p.firstName,p.lastName,pt.property_No FROM Account a,Property pt INNER JOIN a.person p WHERE a.accountId="+accountId+" AND pt.propertyId=a.propertyId AND a.accountStatus='Active'").getResultList();
		for (Iterator iterator = account.iterator(); iterator.hasNext();) {
			final Object[] object = (Object[]) iterator.next();
			
			invoiceLineItemMap = new HashMap<String, Object>();
			invoiceLineItemMap.put("accountNo", (String) object[0]);
			invoiceLineItemMap.put("firstName", (String) object[1]);
			invoiceLineItemMap.put("lastName", (String) object[2]);
			invoiceLineItemMap.put("propertyNumber",(String) object[3]);
			
			itemsList.add(invoiceLineItemMap);
			
			
		}
		
		return itemsList;
	}
	

	@SuppressWarnings({ "unused", "unchecked" })
	public Map<Integer, Object> getAllUnits(int accountId, Date billdate, String typeOfService) {
	  Map<Integer, Object> map = new HashMap<>();
	  Calendar cal = Calendar.getInstance();
	  cal.setTime(billdate);
	  int month1 = cal.get(Calendar.MONTH);
	  int month = month1 + 1;
	  int year = cal.get(Calendar.YEAR);
	  int monthCalc=0;
	  int count=0;
	  float avgUnit=0.0f;
	
	  float average=0.0f;
	  for (int i = 0; i < 4; i++) {
	  monthCalc=(month-(i+1));
	  List<String> avgString=entityManager.createQuery("SELECT e.elBillParameterValue FROM ElectricityBillParametersEntity e WHERE e.billParameterMasterEntity.bvmName='Units' AND e.electricityBillEntity.elBillId IN (SELECT el.elBillId FROM ElectricityBillEntity el WHERE el.accountId='"+accountId+"'  AND el.typeOfService='"+typeOfService+"' AND EXTRACT(month FROM el.billDate)='"+month+"' AND EXTRACT(year FROM el.billDate)='"+year+"' )").getResultList();
	  avgUnit=avgUnit+Float.parseFloat(avgString.get(0));
	  map.put(monthCalc, avgUnit);
	  }
	  return map;
	 }
	@Override
	public String getCamPreviousBillstatus() {
		String status=null;
		List<Integer> cambill=entityManager.createQuery("SELECT elb.elBillId FROM ElectricityBillEntity elb WHERE elb.typeOfService='CAM' AND elb.status='Generated'").getResultList();
		
		if(cambill.size()>0){
			status="Generated";
		}else{
			status="Posted";
		}
		
		return status;
	}
	@Override
	public String getCamPreviousBillstatusSpecific(Integer accountId) {
		
		String status=null;
		
		try {
			status=(String) entityManager.createQuery("SELECT elb.status FROM ElectricityBillEntity elb WHERE elb.typeOfService='CAM' AND elb.accountId='"+accountId+"' AND elb.status!='Cancelled' ORDER BY elb.elBillId DESC").setMaxResults(1).getSingleResult();
		logger.info(":::::::::status:::::::::::"+status);
		} catch (NoResultException e) {
		}
		if(status==null){
			status="FirstBill";
		}
		return status;
	}
	@Override
	public List<SlabDetails> tariffCalculationMultiSlabDetails(ELRateMaster elRateMaster, Date startDate, Date endDate, Float uomValue) {

		float totalAmount = 0.0f;
		float slabDifference =0.0f;
		float lastSlabTo = 0.0f; 
		LocalDate fromDate =  new LocalDate(startDate);
	    LocalDate toDate = new LocalDate(endDate);
	    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
	    Period difference = new Period(fromDate, toDate, monthDay);
	    float billableMonths = difference.getMonths();
	    int daysAfterMonth = difference.getDays();
	    
	     Calendar cal1 = Calendar.getInstance();
		 cal1.setTime(startDate);
		 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
		 float netMonth = daysToMonths + billableMonths;
		
		 if(netMonth == 0)
		 {
			 netMonth = daysToMonths;
			 logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "+netMonth);
		 }
		 List<SlabDetails> slabDetailsList = new ArrayList<>();
		
		List<ELRateSlabs> elRateSlabsList = entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
		for (ELRateSlabs elRateSlabs : elRateSlabsList)
		  {
			SlabDetails slabDetails = new SlabDetails();
		   /* if(lastSlabTo == 0)
		    {
		     slabDifference = (elRateSlabs.getSlabTo() - elRateSlabs.getSlabFrom());
		     lastSlabTo = elRateSlabs.getSlabTo();
		    }
		    else
		    {
		    	slabDifference = (elRateSlabs.getSlabTo() - lastSlabTo);
		    	lastSlabTo = elRateSlabs.getSlabTo();
		    }*/
			 if(lastSlabTo == 0)
			    {
			     slabDifference = Math.round((elRateSlabs.getSlabTo() * netMonth - elRateSlabs.getSlabFrom()* netMonth));
			     lastSlabTo = Math.round(elRateSlabs.getSlabTo()* netMonth)+1;
			    }
			    else
			    {
			    	slabDifference = Math.round((elRateSlabs.getSlabTo()* netMonth - lastSlabTo));
			    	lastSlabTo = Math.round(elRateSlabs.getSlabTo()* netMonth)+1;
			    }
			if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
			{
				if(uomValue > slabDifference)
				{
					uomValue = uomValue - slabDifference;
					totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate())) *netMonth;
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate(elRateSlabs.getRate());
					slabDetails.setAmount((slabDifference)* (elRateSlabs.getRate()));
					slabDetailsList.add(slabDetails);
				}
				else
				{
					if(uomValue > 0)
					{
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()))* netMonth;
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getRate());
						slabDetails.setAmount(uomValue* (elRateSlabs.getRate()));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
			{
				if(uomValue > slabDifference)
				{
					uomValue = uomValue - slabDifference;
					totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()/100)) *netMonth;
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate((elRateSlabs.getRate()/100));
					slabDetails.setAmount(slabDifference * (elRateSlabs.getRate()/100));
					slabDetailsList.add(slabDetails);
				}
				else
				{
					if(uomValue > 0)
					{
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()/100))* netMonth;
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getRate()/100);
						slabDetails.setAmount(uomValue* (elRateSlabs.getRate()/100));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
			{
				if(uomValue > slabDifference)
				{
					uomValue = uomValue - slabDifference;
					totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate()/100)) *netMonth;
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate(elRateSlabs.getRate());
					slabDetails.setAmount(slabDifference* (elRateSlabs.getRate()/100));
					slabDetailsList.add(slabDetails);
				}
				else
				{
					if(uomValue > 0)
					{
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()/100))* netMonth;
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getRate());
						slabDetails.setAmount(uomValue* (elRateSlabs.getRate()/100));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}
			
			if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
			{
				if(uomValue > slabDifference)
				{
					uomValue = uomValue - slabDifference;
					totalAmount = totalAmount + (slabDifference * (elRateSlabs.getRate())) *netMonth;
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate(elRateSlabs.getRate());
					slabDetails.setAmount(slabDifference* (elRateSlabs.getRate()));
					slabDetailsList.add(slabDetails);
				}
				else
				{
					if(uomValue > 0)
					{
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getRate()))* netMonth;
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getRate());
						slabDetails.setAmount(uomValue* (elRateSlabs.getRate()));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}
		  }
		return slabDetailsList;
	}
	@Override
	public List<Object> getAdvanceBillOnDate(int date, int year,
			String serviceType, int accountId) {
		// TODO Auto-generated method stub
	return entityManager.createNamedQuery("TariffCalc.GetAdvanceBillOnDate").setParameter("date", date).setParameter("year", year).setParameter("serviceType", serviceType).setParameter("accountId", accountId).getResultList();
	}
	@Override
	public Map<Object,Object> getBatchBillDateOnBlockId(int blockId,String blockName,String serviceTypeList) {
		Date maxdate=null;
		if(blockName.equalsIgnoreCase("All Blocks")){
			logger.info("In All blocks Method"+serviceTypeList);
			logger.info("In All blocks Method");
			List<Date> currentdate=entityManager.createQuery("select max(b.billDate) from ElectricityBillEntity b where b.typeOfService='"+serviceTypeList+"' ").getResultList();
			for (Date date : currentdate) {
				logger.info("date::::::::::"+date);
				maxdate=date;
			}
		}else{
		List<Date> currentdate=entityManager.createQuery("select max(b.billDate) from ElectricityBillEntity b where b.typeOfService='"+serviceTypeList+"' and b.accountObj.accountId In (select a.accountId from Account a where a.propertyId In (select p.propertyId from Property p where p.blockId="+blockId+" ) )").getResultList();
		
		for (Date date : currentdate) {
			logger.info("date::::::::::"+date);
			maxdate=date;
		}
		}
		Map<Object,Object> mp=new HashMap<>();
		mp.put("date", maxdate);
				return mp;
	}
	@Override
	public List<SlabDetails> tariffCalculationMultiSlabDetailsWater(WTRateMaster wtRateMaster1, Date startDate, Date endDate, Float uomValue) {

		float totalAmount = 0.0f;
		float slabDifference =0.0f;
		float lastSlabTo = 0.0f; 
		LocalDate fromDate =  new LocalDate(startDate);
	    LocalDate toDate = new LocalDate(endDate);
	    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
	    Period difference = new Period(fromDate, toDate, monthDay);
	    float billableMonths = difference.getMonths();
	    int daysAfterMonth = difference.getDays();
	    
	     Calendar cal1 = Calendar.getInstance();
		 cal1.setTime(startDate);
		 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
		 float netMonth = daysToMonths + billableMonths;
		
		 if(netMonth == 0)
		 {
			 netMonth = daysToMonths;
			 logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "+netMonth);
		 }
		 List<SlabDetails> slabDetailsList = new ArrayList<>();
		
		 List<WTRateSlabs> elRateSlabsList = entityManager.createNamedQuery("WTRateSlabs.getWTRateSlabListByRateMasterId",WTRateSlabs.class).setParameter("wtrmid", wtRateMaster1.getWtrmid()).getResultList();
		for (WTRateSlabs elRateSlabs : elRateSlabsList)
		  {
			SlabDetails slabDetails = new SlabDetails();
		   /* if(lastSlabTo == 0)
		    {
		     slabDifference = (elRateSlabs.getSlabTo() - elRateSlabs.getSlabFrom());
		     lastSlabTo = elRateSlabs.getSlabTo();
		    }
		    else
		    {
		    	slabDifference = (elRateSlabs.getSlabTo() - lastSlabTo);
		    	lastSlabTo = elRateSlabs.getSlabTo();
		    }*/
			 if(lastSlabTo == 0)
			    {
			     slabDifference = Math.round((elRateSlabs.getWtSlabTo() * netMonth - elRateSlabs.getWtSlabFrom()* netMonth));
			     lastSlabTo = Math.round(elRateSlabs.getWtSlabTo()* netMonth)+1;
			    }
			    else
			    {
			    	slabDifference = Math.round((elRateSlabs.getWtSlabTo()* netMonth - lastSlabTo));
			    	lastSlabTo = Math.round(elRateSlabs.getWtSlabTo()* netMonth)+1;
			    }
			if(elRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
			{
				if(uomValue > slabDifference)
				{
					uomValue = uomValue - slabDifference;
					totalAmount = totalAmount + (slabDifference * (elRateSlabs.getWtRate())) *netMonth;
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate(elRateSlabs.getWtRate());
					slabDetails.setAmount((slabDifference)* (elRateSlabs.getWtRate()));
					slabDetailsList.add(slabDetails);
				}
				else
				{
					if(uomValue > 0)
					{
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getWtRate()))* netMonth;
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getWtRate());
						slabDetails.setAmount(uomValue* (elRateSlabs.getWtRate()));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if(elRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
			{
				if(uomValue > slabDifference)
				{
					uomValue = uomValue - slabDifference;
					totalAmount = totalAmount + (slabDifference * (elRateSlabs.getWtRate()/100)) *netMonth;
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate((elRateSlabs.getWtRate()/100));
					slabDetails.setAmount(slabDifference * (elRateSlabs.getWtRate()/100));
					slabDetailsList.add(slabDetails);
				}
				else
				{
					if(uomValue > 0)
					{
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getWtRate()/100))* netMonth;
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getWtRate()/100);
						slabDetails.setAmount(uomValue* (elRateSlabs.getWtRate()/100));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if(elRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
			{
				if(uomValue > slabDifference)
				{
					uomValue = uomValue - slabDifference;
					totalAmount = totalAmount + (slabDifference * (elRateSlabs.getWtRate()/100)) *netMonth;
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate(elRateSlabs.getWtRate());
					slabDetails.setAmount(slabDifference* (elRateSlabs.getWtRate()/100));
					slabDetailsList.add(slabDetails);
				}
				else
				{
					if(uomValue > 0)
					{
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getWtRate()/100))* netMonth;
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getWtRate());
						slabDetails.setAmount(uomValue* (elRateSlabs.getWtRate()/100));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}
			
			if(elRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
			{
				if(uomValue > slabDifference)
				{
					uomValue = uomValue - slabDifference;
					totalAmount = totalAmount + (slabDifference * (elRateSlabs.getWtRate())) *netMonth;
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate(elRateSlabs.getWtRate());
					slabDetails.setAmount(slabDifference* (elRateSlabs.getWtRate()));
					slabDetailsList.add(slabDetails);
				}
				else
				{
					if(uomValue > 0)
					{
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getWtRate()))* netMonth;
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getWtRate());
						slabDetails.setAmount(uomValue* (elRateSlabs.getWtRate()));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}
		  }
		return slabDetailsList;
	}
	@Override
	public List<SlabDetails> tariffCalculationMultiSlabDetailsGas(GasRateMaster gasRateMaster1, Date startDate,Date endDate, Float uomValue) {
		float totalAmount = 0.0f;
		float slabDifference =0.0f;
		float lastSlabTo = 0.0f; 
		LocalDate fromDate =  new LocalDate(startDate);
	    LocalDate toDate = new LocalDate(endDate);
	    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
	    Period difference = new Period(fromDate, toDate, monthDay);
	    float billableMonths = difference.getMonths();
	    int daysAfterMonth = difference.getDays();
	    
	     Calendar cal1 = Calendar.getInstance();
		 cal1.setTime(startDate);
		 float daysToMonths = (float)daysAfterMonth / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
		 float netMonth = daysToMonths + billableMonths;
		
		 if(netMonth == 0)
		 {
			 netMonth = daysToMonths;
			 logger.info("::::::::::::::: Net months if months less than one month ::::::::::: "+netMonth);
		 }
		 List<SlabDetails> slabDetailsList = new ArrayList<>();
		
		 List<GasRateSlab> gasRateSlabsList = entityManager.createNamedQuery("GasRateSlab.getGasRateSlabListByRateMasterId",GasRateSlab.class).setParameter("gasrmId", gasRateMaster1.getGasrmid()).getResultList();
		for (GasRateSlab elRateSlabs : gasRateSlabsList)
		  {
			SlabDetails slabDetails = new SlabDetails();
		   /* if(lastSlabTo == 0)
		    {
		     slabDifference = (elRateSlabs.getSlabTo() - elRateSlabs.getSlabFrom());
		     lastSlabTo = elRateSlabs.getSlabTo();
		    }
		    else
		    {
		    	slabDifference = (elRateSlabs.getSlabTo() - lastSlabTo);
		    	lastSlabTo = elRateSlabs.getSlabTo();
		    }*/
			 if(lastSlabTo == 0)
			    {
			     slabDifference = Math.round((elRateSlabs.getGasSlabTo() * netMonth - elRateSlabs.getGasSlabFrom()* netMonth));
			     lastSlabTo = Math.round(elRateSlabs.getGasSlabTo()* netMonth)+1;
			    }
			    else
			    {
			    	slabDifference = Math.round((elRateSlabs.getGasSlabTo()* netMonth - lastSlabTo));
			    	lastSlabTo = Math.round(elRateSlabs.getGasSlabTo()* netMonth)+1;
			    }
			if(elRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
			{
				if(uomValue > slabDifference)
				{
					uomValue = uomValue - slabDifference;
					totalAmount = totalAmount + (slabDifference * (elRateSlabs.getGasRate())) *netMonth;
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate(elRateSlabs.getGasRate());
					slabDetails.setAmount((slabDifference)* (elRateSlabs.getGasRate()));
					slabDetailsList.add(slabDetails);
				}
				else
				{
					if(uomValue > 0)
					{
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getGasRate()))* netMonth;
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getGasRate());
						slabDetails.setAmount(uomValue* (elRateSlabs.getGasRate()));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if(elRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
			{
				if(uomValue > slabDifference)
				{
					uomValue = uomValue - slabDifference;
					totalAmount = totalAmount + (slabDifference * (elRateSlabs.getGasRate()/100)) *netMonth;
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate((elRateSlabs.getGasRate()/100));
					slabDetails.setAmount(slabDifference * (elRateSlabs.getGasRate()/100));
					slabDetailsList.add(slabDetails);
				}
				else
				{
					if(uomValue > 0)
					{
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getGasRate()/100))* netMonth;
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getGasRate()/100);
						slabDetails.setAmount(uomValue* (elRateSlabs.getGasRate()/100));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}
			if(elRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
			{
				if(uomValue > slabDifference)
				{
					uomValue = uomValue - slabDifference;
					totalAmount = totalAmount + (slabDifference * (elRateSlabs.getGasRate()/100)) *netMonth;
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate(elRateSlabs.getGasRate());
					slabDetails.setAmount(slabDifference* (elRateSlabs.getGasRate()/100));
					slabDetailsList.add(slabDetails);
				}
				else
				{
					if(uomValue > 0)
					{
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getGasRate()/100))* netMonth;
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getGasRate());
						slabDetails.setAmount(uomValue* (elRateSlabs.getGasRate()/100));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}
			
			if(elRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
			{
				if(uomValue > slabDifference)
				{
					uomValue = uomValue - slabDifference;
					totalAmount = totalAmount + (slabDifference * (elRateSlabs.getGasRate())) *netMonth;
					slabDetails.setUnits(slabDifference);
					slabDetails.setRate(elRateSlabs.getGasRate());
					slabDetails.setAmount(slabDifference* (elRateSlabs.getGasRate()));
					slabDetailsList.add(slabDetails);
				}
				else
				{
					if(uomValue > 0)
					{
						totalAmount = totalAmount + (uomValue * (elRateSlabs.getGasRate()))* netMonth;
						slabDetails.setUnits(uomValue);
						slabDetails.setRate(elRateSlabs.getGasRate());
						slabDetails.setAmount(uomValue* (elRateSlabs.getGasRate()));
						slabDetailsList.add(slabDetails);
						uomValue = uomValue - slabDifference;
					}
				}
			}
		  }
		return slabDetailsList;
	}
	@Override
	public List<Map<String, Object>> getMinMaxDate1(int elTariffID,	String rateName, String category) {
		return getMinMaxDate(entityManager.createNamedQuery("ELRateMaster.getMinMaxDate1").setParameter("tariffMasterId", elTariffID).setParameter("rateName", rateName).setParameter("rateNameCategory", category).getResultList());
	}
	@Override
	public List<ELRateMaster> getRateMasterByIdName1(int elTariffID,String rateName, String category) {
			return entityManager.createNamedQuery("ELRateMaster.getRateMasterByIdName1",ELRateMaster.class).setParameter("elTariffID", elTariffID).setParameter("rateName",rateName).setParameter("rateNameCategory", category).getResultList();
	}
	@Override
	public Integer getAccountIdOnPropertyId(int propId, String serviceTypeList) {
Integer accounId=null;
		
		try {
			accounId=(Integer)entityManager.createQuery("SELECT bw.accountObj.accountId FROM BillingWizardEntity bw WHERE bw.serviceMastersEntity.typeOfService='Electricity' AND bw.accountObj.propertyId='"+propId+"'").setMaxResults(1).getSingleResult();
		logger.info(":::::::::accounId:::::::::::"+accounId);
		} catch (NoResultException e) {
		}
		if(accounId==null){
			accounId=0;
		}
		
		return accounId;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getPreviousBillReadingAmr(Integer accountId,String serviceType) {
		Integer elBillId = (Integer)entityManager.createNamedQuery("TariffCalc.GetBillId").setParameter("accountId", accountId).setParameter("serviceType", serviceType).getSingleResult();
		List<String> list = entityManager.createNamedQuery("TariffCalc.GetBillStatus").setParameter("elBillId", elBillId).getResultList();
		List<?> list1 = entityManager.createNamedQuery("TariffCalc.GetBillDate").setParameter("elBillId", elBillId).getResultList();
		//List<java.sql.Date> initialBillDate = entityManager.createNamedQuery("TariffCalc.GetBillInitialDate").setParameter("serviceId", serviceId).getResultList();
		Integer meterId = (Integer) entityManager.createNamedQuery("TariffCalc.GetMeterId").setParameter("accountId", accountId).setParameter("serviceType", serviceType).getSingleResult();
		List<?> initialReading = entityManager.createNamedQuery("TariffCalc.GetBillInitialReading").setParameter("meterId", meterId).getResultList();
		Date elbilldate=null;
		List<Map<String, Object>> servicePointMap =  new LinkedList<Map<String, Object>>();  
		List<Map<String, Object>> servicemasterMap =  new LinkedList<Map<String, Object>>();
		List<Map<String, Object>> servicUnmetered =  new LinkedList<Map<String, Object>>();
		List<Map<String, Object>> dgmetered =  new LinkedList<Map<String, Object>>();
		int year=0;
		int month=0;
		if(list1.size()>0){
			for (Iterator<?> iterator = list1.iterator(); iterator.hasNext();) {
				
				final Object[] values = (Object[]) iterator.next();	
				elbilldate = (java.sql.Date)values[0];
				Calendar cal = Calendar.getInstance();
				cal.setTime(elbilldate);
				int month1 = cal.get(Calendar.MONTH);
				month = month1 + 1;
				year = cal.get(Calendar.YEAR);
			}
		}
		logger.info(":::::::::month:::::::::"+month);
		logger.info(":::::::::year:::::::::"+year);
		double dgfrchangereading=0.0d;
		double utilityfrchangereading=0.0d;
		final List<Double> finalreading=entityManager.createQuery("SELECT f.finalReading,f.finalReadingDg FROM FRChange f WHERE f.accountId='"+accountId+"' AND f.typeOfService='"+serviceType+"' AND EXTRACT(month FROM f.billDate)='"+month+"' AND EXTRACT(year FROM f.billDate)='"+year+"' ").getResultList();
		System.out.println(":::::::::::"+finalreading);
		for (Iterator iterator = finalreading.iterator(); iterator.hasNext();) {
			Object[] double1 = (Object[]) iterator.next();
			dgfrchangereading=(Double)double1[1];
			utilityfrchangereading=(Double)double1[0];
			logger.info("@@@@@@@@@"+dgfrchangereading);
			
		}
		
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {//FRChange
			final double utilityfrreading=utilityfrchangereading;
			final Object[] values = (Object[]) iterator.next();		
			servicePointMap.add(new LinkedHashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{  
				if(((String)values[0]).equalsIgnoreCase("Present status")){
				put("previousStatus",(String)values[1]);
				}
				
				if(((String)values[0]).equalsIgnoreCase("Present reading")){
				if(finalreading.size()>0){
					put("InitialReading",String.valueOf(utilityfrreading));
				}else{
					put("InitialReading",(String)values[1]);	
				}					
				}
				
				if(((String)values[0]).equalsIgnoreCase("Meter Constant")){
				put("MeterConstant",(String)values[1]);	
				}
				
			}});
		}
		for (Iterator<?> iterator = list1.iterator(); iterator.hasNext();) {
			
			final Object[] values = (Object[]) iterator.next();
			servicePointMap.add(new LinkedHashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{  
			put("billdate",(java.sql.Date)values[0]);	
			
			}});
			
		}
		/**/
		
		@SuppressWarnings("rawtypes")
		List finallist=new LinkedList<>();
		Map<Object,Object> map=new LinkedHashMap<>();
		if(elBillId==null && (meterId!=null)){
			for(Iterator<?> iterator = initialReading.iterator(); iterator.hasNext();){
				
				final Object[] values = (Object[]) iterator.next();
				servicemasterMap.add(new LinkedHashMap<String, Object>() {					
				private static final long serialVersionUID = 1L;
				{  
					if(((String)values[0]).equalsIgnoreCase("Initial Reading")){
						put("InitialReading",(String)values[1]);
					}
					if(((String)values[0]).equalsIgnoreCase("Meter Constant")){
						put("MeterConstant",(String)values[1]);
					}
				}});
					
			}
		
			map.put("previousStatus", "Normal");
			for(Map<String, Object> l:servicemasterMap){
				Set<Entry<String, Object>> s=l.entrySet();
				Iterator<Entry<String, Object>> i=s.iterator();
				if(i.hasNext()){
					@SuppressWarnings("rawtypes")
					Map.Entry mentry2 = (Map.Entry)i.next();
					
					map.put("previousStatus", "Normal");
					finallist.add(mentry2.getValue());
					map.put(mentry2.getKey(), mentry2.getValue());
				}
			}
			
		}else if (elBillId==null && (meterId==null)) {
			finallist.add("Direct Connection");
			finallist.add("0");
			finallist.add("0");
			/*for(final java.sql.Date startDate : initialBillDate){
				servicUnmetered.add(new LinkedHashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{  
				put("billdate",startDate);	
				}});	
			}*/
			for(Map<String, Object> l:servicUnmetered){
				Set<Entry<String, Object>> s=l.entrySet();
				Iterator<Entry<String, Object>> i=s.iterator();
				if(i.hasNext()){
					@SuppressWarnings("rawtypes")
					Map.Entry mentry2 = (Map.Entry)i.next();
					finallist.add(mentry2.getValue());
					map.put(mentry2.getKey(), mentry2.getValue());
				}
			}
		}
		
		else{
		for(Map<String, Object> l:servicePointMap){
			Set<Entry<String, Object>> s=l.entrySet();
			Iterator<Entry<String, Object>> i=s.iterator();
			if(i.hasNext()){
				@SuppressWarnings("rawtypes")
				Map.Entry mentry2 = (Map.Entry)i.next();
				map.put(mentry2.getKey(), mentry2.getValue());
				finallist.add(mentry2.getValue());
			}
		}
		}
		if (elBillId != null) {
			for (Iterator<?> iterator = list1.iterator(); iterator.hasNext();) {
				final Object[] values = (Object[]) iterator.next();
				String billstatus = (String) values[1];
				map.put("billgenerationstatus", (String) values[1]);
				finallist.add(billstatus);

			}
		} else {
			finallist.add("Not Generated");
			map.put("billgenerationstatus", "Not Generated");
		}
		/*-------------------------------------------------------------------------------------------------------------------------*/
		 List<Object> dgapplicable=todApplicable(serviceType, accountId);
		String dgaplicable=null;
		String todApplicable=null;
		String dgAddeded="NotAdded";
		 if(dgapplicable.get(1)!=null)
		 {
			 todApplicable=(String)dgapplicable.get(0);;
			 dgaplicable=(String) dgapplicable.get(1);
			 map.put("dgapplicable", dgaplicable);
			 map.put("todApplicable", todApplicable);
			 if(dgapplicable.get(1).equals("Yes")){
				 logger.info(":::::::::::Only DG Applicable::::::::::::: ");
				 List<Object> initialReadingName=entityManager.createNamedQuery("TariffCalc.GetBillInitialReadingNamePresent").setParameter("elBillId", elBillId).getResultList();
				 for (Object electricityMeterParametersEntity : initialReadingName) {
					 logger.info("electricityMeterParametersEntity"+electricityMeterParametersEntity.toString());
					boolean eString=electricityMeterParametersEntity.equals("Present  DG reading");
					logger.info("::::::::::eString::::::::"+eString);
					if(eString){
						dgAddeded="Added";
						break;
					}
				}
				 if(elBillId!=null && (meterId!=null)){
					 logger.info("::::::::::::::Bill all ready Generated:::::::::::::");
					 final double dgfrread=dgfrchangereading;
					 for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
						 
						 final Object[] values = (Object[]) iterator.next();
						 dgmetered.add(new LinkedHashMap<String, Object>() {
							private static final long serialVersionUID = 1L;
							{  
								/*if(finalreading.size()>0){
									put("DGInitialReading",String.valueOf(dgfrread));
								}else{
									put("DGInitialReading", (String) values[1]);	
								}*/
								
								if (((String) values[0]).equalsIgnoreCase("Present  DG reading")) {
									if(finalreading.size()>0){
										put("DGInitialReading",String.valueOf(dgfrread));
									}else{
										put("DGInitialReading", (String) values[1]);	
									}	
									
									
								}
							
							if(((String)values[0]).equalsIgnoreCase("DG Meter Constant")){
								put("DGMeterConstant",(String)values[1]);
							}
							}});
							
						}
				 }else{
			
			for(Iterator<?> iterator = initialReading.iterator(); iterator.hasNext();){
				
				final Object[] values = (Object[]) iterator.next();	
				dgmetered.add(new LinkedHashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{  
				if(((String)values[0]).equalsIgnoreCase("DG Initial Reading")){
					put("DGInitialReading",(String)values[1]);
				}
				if(((String)values[0]).equalsIgnoreCase("DG Meter Constant")){
					put("DGMeterConstant",(String)values[1]);
				}
				}});
			}
			
			 }
				 
				 if(dgAddeded.equalsIgnoreCase("Added")){
						logger.info("in if block");
					}else{
						logger.info(":::::::::::::else block");
	                     for(Iterator<?> iterator = initialReading.iterator(); iterator.hasNext();){
							
							final Object[] values = (Object[]) iterator.next();	
							dgmetered.add(new LinkedHashMap<String, Object>() {
							private static final long serialVersionUID = 1L;
							{  
							if(((String)values[0]).equalsIgnoreCase("DG Initial Reading")){
								put("DGInitialReading",(String)values[1]);
							}
							if(((String)values[0]).equalsIgnoreCase("DG Meter Constant")){
								put("DGMeterConstant",(String)values[1]);
							}
							}});
						}	
					}
				 for(Map<String, Object> l:dgmetered){
						Set<Entry<String, Object>> s=l.entrySet();
						Iterator<Entry<String, Object>> i=s.iterator();
						if(i.hasNext()){
							@SuppressWarnings("rawtypes")
							Map.Entry mentry2 = (Map.Entry)i.next();
							map.put(mentry2.getKey(), mentry2.getValue());
							finallist.add(mentry2.getValue());
						}}
				 }
			 
			 else{
				 logger.info(":::::::::::::::::DG Not Applicable:::::::::::::::::::::::::");
			 }
		 }
		 
logger.info(":::::::accountId::::::"+accountId+"::::::::::::"+map.get("DGInitialReading"));
		return map;	

	}
	@Override
	public List<?> findPropertyNoAmr(int blockId) {
		List<?> propertyNumbersList = entityManager.createNamedQuery("TariffCalc.readPropertyNamesAmr").setParameter("blockId", blockId).getResultList();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> propertyNumberMap = null;
        propertyNumberMap = new HashMap<String, Object>();        		
		/*propertyNumberMap.put("propertyId",0);
		propertyNumberMap.put("property_No", "All Property");			
		result.add(propertyNumberMap);*/
		
		for (Iterator<?> iterator = propertyNumbersList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				propertyNumberMap = new HashMap<String, Object>();				
								
				
				propertyNumberMap.put("propertyId",(Integer)values[0]);
				propertyNumberMap.put("property_No", (String)values[1]);	
							
			
			result.add(propertyNumberMap);
	     }
     return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getallBlocksAmr() {
		
		List<Blocks> blokdetaillist=entityManager.createNamedQuery("Blocks.getAllBlocksAmr").getResultList();
		Map<String,Object> blokdetailmap= new HashMap<String, Object>();
		List<Map<String,Object>> ls=new ArrayList<Map<String,Object>>();
		for (Blocks blocks : blokdetaillist) {
			blokdetailmap.put("blockId", blocks.getBlockId());
			blokdetailmap.put("blockName",blocks.getBlockName());
			ls.add(blokdetailmap);
		}
		
		
		
		logger.info("All bloocks ::::::::::::"+blokdetailmap);
		return ls;
	}
	@Override
	public String getpropertyNoOnPropertyId(int propid) {
		
		return (String) entityManager.createQuery("SELECT p.property_No FROM Property p WHERE p.propertyId='"+propid+"' ").getSingleResult();
	}
	@Override
	public List<?> getAdvanceBillPreviousDate(String typeOfService,
			int accountId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Float getNewMeterDgReading(int accountId) {
       String dgInitialreading=null;
		
		try {
			
			dgInitialreading=(String)entityManager.createQuery("SELECT bw.elMeterParameterValue FROM ElectricityMeterParametersEntity bw WHERE bw.parameterMasterObj.mpmName='DG Initial Reading' AND bw.electricityMetersEntity.account.accountId='"+accountId+"'").setMaxResults(1).getSingleResult();
		logger.info(":::::::::accounId:::::::::::"+dgInitialreading);
		} catch (NoResultException e) {
		}
		if(dgInitialreading==null){
			
		}
		
		return Float.valueOf(dgInitialreading);
	}
	@Override
	public List<Map<String, Object>> getLedgerDataOnAccountWise(int accountId,
			String strDate, String pesentDate) {
	List<?> ledgerdata=entityManager.createNamedQuery("ElectricityLedgerEntity.findOnAccountWise").setParameter("accountId", accountId).setParameter("strDate", strDate).setParameter("pesentDate", pesentDate).getResultList();
	System.out.println("startdate::::::"+strDate+"::::::::pesentDate"+pesentDate);
	
	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	 Map<String, Object> wizardMap = null;
	 for (Iterator<?> iterator = ledgerdata.iterator(); iterator.hasNext();)
		{
			final Object[] values = (Object[]) iterator.next();
			wizardMap = new HashMap<String, Object>();
							
			wizardMap.put("elLedgerid", (Integer)values[0]);	
			wizardMap.put("accountId", (Integer)values[1]);
			wizardMap.put("ledgerPeriod", (String)values[2]);	
			wizardMap.put("ledgerDate", (Date)values[3]);
			wizardMap.put("postType", (String)values[4]);
			wizardMap.put("postReference", (String)values[5]);
			wizardMap.put("transactionCode", (String)values[6]);	
			wizardMap.put("amount", (Double)values[7]);
			wizardMap.put("balance", (Double)values[8]);	
			wizardMap.put("postedBillDate", (Timestamp)values[9]);
			wizardMap.put("transactionSequence", (Integer)values[10]);
			wizardMap.put("ledgerType", (String)values[11]);
			wizardMap.put("accountNo", (String)values[12]);
			wizardMap.put("transactionName", (String)values[13]);
			
			String personName = "";
			personName = personName.concat((String)values[14]);
			if((String)values[15] != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)values[15]);
			}
			wizardMap.put("personName", personName);
			wizardMap.put("property_No", (String)values[18]);
			wizardMap.put("remarks", (String)values[19]);
			
			/*String personType = (String)values[16];
			int personId = (Integer)values[17];
			if(personType.equals("Tenant")){
				wizardMap.put("property_No",getPropertyNoForTenant(personId));
			}else if(personType.equals("Owner")){
				wizardMap.put("property_No",getPropertyNoForOwner(personId));
			}*/
					
		result.add(wizardMap);
    }
 return result;
	
	
	
	
	}
	@Override
	public List<?> getcheckbounceDetail(int accountID) {
		// TODO Auto-generated method stub
	return	entityManager.createNamedQuery("ChequeBounceEntity.getCheckBounceDetailOnAccont").setParameter("accountID", accountID).getResultList();
	}

	@Override
	public List<?> getAllAccuntNumbersBasedOnProperty(int propertyId) {
		// TODO Auto-generated method stub
		return getAccountNumbersData(entityManager.createNamedQuery("BillGeneration.getAllAccuntNumbersBasedOnProperty").setParameter("propertyId", propertyId).getResultList());
	}
/*//***************************************************************************************************************
	 Methods for Webservices for PaytmApi
	 public String getValidMeterDetails(int siteId,String meterNumber)
	 {
		 	Map<String, String> record = new HashMap<String, String>();
		    String json = "";
		 	String query=
		 			"SELECT X.PROJECT_ID,Y.NAME,Y.A1,Y.A2,Y.PNO,Y.MN,Y.PID,Y.MID,X.PROJECT_NAME FROM " +
		 			" (SELECT PROJECT_ID,PROJECT_NAME FROM PROJECT WHERE PROJECT_ID='"+siteId+"')X, " +
		 			" (SELECT pe.FIRST_NAME||' '||pe.LAST_NAME AS name,a.ADDRESS1 AS a1,a.ADDRESS2 AS a2 , " +
		 			" PR.PROPERTY_NO AS pno,PM.METER_NUMBER AS mn,PR.PROPERTY_ID AS pid,PM.MID AS MID " +
		 			" FROM PERSON pe,PROPERTY pr,PREPAID_METERS pm,CONTACT c,ADDRESS a " +
		 			" WHERE PM.PROPERTY_ID=PR.PROPERTY_ID  AND pe.PERSON_ID=c.PERSON_ID AND c.PERSON_ID=a.PERSON_ID " +
		 			" AND PM.PERSON_ID=PE.PERSON_ID AND PM.METER_NUMBER="+meterNumber+
		 			" )Y WHERE ROWNUM=1";
		 	
		 		logger.info("Inside TariffCalculationServiceImpl.getValidMeterDetails():Query===========>"+query);
		 		Date currentDate=new Date();
		 		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 		SimpleDateFormat sdf1= new SimpleDateFormat("HH:mm:ss");
		 		Calendar cal = Calendar.getInstance();
		 		
		 		
		  try 
		  {
		 		Object[] gotAllDetail =   (Object[]) entityManager.createNativeQuery(query).getSingleResult();
				for(int i=0;i<gotAllDetail.length;i++)
				{
				  if(i==0) record.put("siteId", 	gotAllDetail[0]+"/"+gotAllDetail[8] );
				  if(i==1) record.put("personName", gotAllDetail[1]+"" );
				  if(i==2) record.put("addr_1", 	gotAllDetail[2]+"" );
				  if(i==3) record.put("addr_2",     gotAllDetail[3]+"" );
				  if(i==4) record.put("propertyNo", gotAllDetail[4]+"" );
				  if(i==5) record.put("meterNo",    gotAllDetail[5]+"" );
				  if(i==6) record.put("adminCharge", "Rs 12" );
				  if(i==7) record.put("responseCode","Success" );
				}   
				record.put("enquiry_date",sdf.format(currentDate));
				record.put("enquiry_time",sdf1.format(cal.getTime())+"");
				
				if(record.size()!=0)
				{
					json = new Gson().toJson(record);
					logger.info("JsonResult==>"+json);
				} 
		  } 
		  catch (Exception e) 
		  {
			  record.put("siteId", ""+siteId );
			  record.put("meterNo",""+meterNumber );
			  record.put("adminCharge", "Rs 12" );
			  record.put("responseCode","Filure! Wrong Property Number" );
			  record.put("enquiry_date",sdf.format(currentDate));
			  record.put("enquiry_time",sdf1.format(cal.getTime())+"");
			  
			  if(record.size()!=0)
			  {
				  json = new Gson().toJson(record);
				  logger.info("JsonResult==>"+json);
			  } 
		  }

		return json;
	 }
	 public String postThePayment(String uniqueID,String paymentDate,String paymentTime,String siteId,String uniqueTxnId,String amountPayable) throws ParseException
	 {
		 logger.info("*********************inside TariffCalculationServiceImpl.postThePayment()*************************");
		 logger.info("uniqueID="+uniqueID+" & paymentDate="+paymentDate+" & paymentTime="+paymentTime+"& siteId="+siteId+"  & uniqueTxnId="+uniqueTxnId+" & amountPayable="+amountPayable);
		 
		 String json = "";
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 Date payDateUtil = sdf.parse(paymentDate+" "+paymentTime);
		 Timestamp payDate = new java.sql.Timestamp(payDateUtil.getTime());
		 
		 logger.info("***********************payDate="+payDate+"*****************************"); 
		 
		 if(uniqueTxnId.equals(""))
		 {
			System.err.println("uniqueTxnId is null"); 
		 }
		 
		 if( !uniqueTxnId.equals(null) || !uniqueTxnId.equals(""))
		 {
			try 
			{
				
			 logger.info("********************uniqueTxnId="+uniqueTxnId+"***********************");
			 String getaccId ="SELECT ACCOUNT_ID FROM ACCOUNT WHERE PROPERTYID=(SELECT PROPERTY_ID FROM PROPERTY WHERE PROPERTY_NO='"+uniqueID+"')";
			 String accId = ""+ entityManager.createNativeQuery(getaccId).getSingleResult();
			 logger.info("getaccId Query-->"+getaccId);
			 
			 String conAndMeterNo="SELECT CONSUMER_ID,METER_NUMBER FROM PREPAID_METERS WHERE PROPERTY_ID=(SELECT PROPERTY_ID FROM PROPERTY WHERE PROPERTY_NO='"+uniqueID+"')";
			 Object[] conAndMeter =  (Object[]) entityManager.createNativeQuery(conAndMeterNo).getSingleResult();
			 logger.info("conAndMeterNo Query-->"+conAndMeterNo);
			
			 int accountId = Integer.parseInt(accId);
			 
			    OnlinePaymentTransactions onlinePaymentTransactions = new OnlinePaymentTransactions();
			 	onlinePaymentTransactions.setAccount_id(accountId); 
				onlinePaymentTransactions.setCreated_date(payDate);
				onlinePaymentTransactions.setMerchantId("PaytmApps");
				onlinePaymentTransactions.setPayment_status("Success");
				onlinePaymentTransactions.setTransaction_id(uniqueTxnId);
				onlinePaymentTransactions.setTx_amount(amountPayable);
				onlinePaymentTransactions.setService_type("Electricity");
				
				*//*****************************Next Sequence Generating******************************************//*
				int nextValueSeq=((BigDecimal)entityManager.createNativeQuery("SELECT PAYTM_APP_TRANSACTION_SEQ.nextval FROM dual").getSingleResult()).intValue();
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
				String year = sdf1.format(new Date());
				String trans_code_from_Skyon = "PAYTMAPP_"+year+nextValueSeq;
				onlinePaymentTransactions.setPayumoney_id(trans_code_from_Skyon); 
				
				onlinePaymentTransactionsService.save(onlinePaymentTransactions);
			
			    Account account = accountService.find(accountId);  
				Person person = account.getPerson();
				String personName = person.getFirstName()+""+person.getLastName();
				String fatherName = person.getFatherName();
				System.out.println("personName is "+personName+"  & fatherName is "+fatherName);
				
				PrepaidRechargeEntity prepaidRechargeEntity = new PrepaidRechargeEntity();
				prepaidRechargeEntity.setAccountId(accountId);
				prepaidRechargeEntity.setMerchantId("PayTmApps");
				prepaidRechargeEntity.setConsumer_Name(personName);
				prepaidRechargeEntity.setConsumer_Id(conAndMeter[0]+"");
				prepaidRechargeEntity.setMeterNumber(conAndMeter[1]+"");
				prepaidRechargeEntity.setFather_Name(fatherName);
				prepaidRechargeEntity.setModeOfPayment("PaytmApps");
				prepaidRechargeEntity.setRechargeAmount(Double.parseDouble(amountPayable)-12);
				prepaidRechargeEntity.setStatus("Token Not Generated");
				prepaidRechargeEntity.setTallyStatus("Not Posted");
				
				String txnDate=paymentDate;
				DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
				Date d1=dateFormat.parse(txnDate);
				
				prepaidRechargeEntity.setTxn_Date(d1);
				prepaidRechargeEntity.setTxn_ID(uniqueTxnId);
				prepaidRechargeEntity.setBankName("MobileApp");
				prepaidRechargeEntity.setTypeOfService("Electricity");

				prepaidRechargeService.save(prepaidRechargeEntity);
				
				*//**************************returnResponseToPayTm*********************************************//*
				String preRecLast="SELECT * FROM(SELECT METER_NUMBER,CONSUMER_NAME,RECHATGE_AMOUNT,TRANSACTION_ID,STATUS "
						        + "FROM PREPAID_RECHARGE WHERE ACCOUNT_ID="+accountId+" ORDER BY PRE_RECID DESC) WHERE ROWNUM=1";
				Object[] lastRecharge= (Object[]) entityManager.createNativeQuery(preRecLast).getSingleResult();
				logger.info(preRecLast);
				Map<String, String> record = new HashMap<String, String>();
				
				for(int i=0;i<lastRecharge.length;i++)
				{
				  if(i==0) record.put("usersID", 		lastRecharge[0]+"/"+lastRecharge[1]);
				  if(i==1) record.put("Payable_Amount", lastRecharge[2]+"");
				  if(i==2) record.put("transaction_id", lastRecharge[3]+"");
				  if(i==3) record.put("tran_status",	lastRecharge[4]+"");
				  if(i==4) record.put("ResponseCode",   "Success" );
				}   
				record.put("transaction_id_skyon",trans_code_from_Skyon);
				
				if(record.size()!=0)
				{
					json = new Gson().toJson(record);
					logger.info("JsonResult==>"+json);
				} 
			 } 
			 catch (Exception e) 
			 {
				 Map<String, String> record = new HashMap<String, String>();
				 record.put("ResponseCode",   "Something went wrong ! check" );
				 if(record.size()!=0)
				 {
					json = new Gson().toJson(record);
					logger.info("JsonResult==>"+json);
				 } 
			 }

		 }
		 else 
		 {
			 Map<String, String> record = new HashMap<String, String>();
			 record.put("ResponseCode",   "Invalid Transaction" );
			 if(record.size()!=0)
			 {
				json = new Gson().toJson(record);
				logger.info("JsonResult==>"+json);
			 } 
		}
		 
		 
		return json;
	 }
//***************************************************************************************************************
	@Override
	public String paymentStatusCheck(String uniqueID, String txnid) {
		String query="SELECT * FROM ONLINE_PAYMENT_TRANSACTIONS WHERE TRANSACTION_ID='"+txnid+"' "
			      	+ "and ACCOUNT_ID IN (SELECT ACCOUNT_ID FROM  ACCOUNT WHERE PROPERTYID IN"
			      	+ " (SELECT PROPERTY_ID FROM PROPERTY WHERE PROPERTY_NO LIKE '"+uniqueID+"'))";
		
		Map<String, String> maplist=null;
		int flag=0;
		try{
		OnlinePaymentTransactions  onlinePaymentTransactions=(OnlinePaymentTransactions) entityManager.createNamedQuery("ONLINEPAYMENTS.STATUSCHECK").setParameter("TRANSACTION_ID", txnid).setParameter("PROPERTY_NO", uniqueID).getSingleResult();
		flag=1;
		if(onlinePaymentTransactions!=null){
			maplist=new HashMap<>();
			maplist.put("Unique_id", uniqueID);
			maplist.put("Payable Amount", onlinePaymentTransactions.getTx_amount());
			maplist.put("unique_id", onlinePaymentTransactions.getTransaction_id());
			String date=new SimpleDateFormat("yyMMHHss").format(new Date());
			maplist.put("transact_id", date+""+onlinePaymentTransactions.getAccount_id());
			if(onlinePaymentTransactions.getPayment_status().toUpperCase().equalsIgnoreCase("SUCCESS")){
				maplist.put("tran_stat", "Y");
			}else{
				maplist.put("tran_stat", "N" +"");
			}
			
			maplist.put("Response Code", "Success");
		}
		}catch(Exception e){
			e.printStackTrace();
			if(flag==1){
				maplist=new HashMap<>();
				maplist.put("Unique_id", uniqueID);
				maplist.put("Payable Amount", "0.0");
				maplist.put("unique_id", txnid);
				String date=new SimpleDateFormat("yyMMHHss").format(new Date());
				maplist.put("transact_id", date);
				maplist.put("tran_stat", "N" +"");
				maplist.put("Response Code", "Failure" +"No recored found for given Paytm transaction id");
			}
			
			if(flag==0){
				maplist=new HashMap<>();
				maplist.put("Unique_id", uniqueID);
				maplist.put("Payable Amount", "0.0");
				maplist.put("unique_id", txnid);
				String date=new SimpleDateFormat("yyMMHHss").format(new Date());
				maplist.put("transact_id", date);
				maplist.put("tran_stat", "N");
				maplist.put("Response Code", "Failure" +"No recored found for given Paytm transaction id");
			}
		}
		return null;
	}*/
	@Override
	public double getMisChargesDetail(int accountID,java.sql.Date billmonth) {
		try{
			BigDecimal mis=(BigDecimal) entityManager.createNativeQuery("SELECT JV_AMOUNT FROM ADJUSTMENTS "
					+ "WHERE ADJUSTMENT_TYPE='Miscellaneous' AND "
					+ "ACCOUNT_ID='"+accountID+"' AND TO_CHAR(JV_DATE,'MMyyyy')='"+new SimpleDateFormat("MMyyyy").format(billmonth)+
					"' AND STATUS='Posted'").getSingleResult();
			return mis.doubleValue();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
			
	}
	
}

