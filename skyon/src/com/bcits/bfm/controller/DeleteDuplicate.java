package com.bcits.bfm.controller;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeleteDuplicate {

	@PersistenceContext(unitName="bfm")
	protected EntityManager entityManager;

	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value="/deleteConsumptionDuplicate/{afterdate}")
	public void deleteConsumptionDuplicate(@PathVariable String afterdate){	
		System.out.println("=================inside deleteConsumptionDuplicate()============");
		try 
		{
			int duplicateRecords = 0;
			List<?> duplicateList = null;

			String getAllDuplicates = 
					" SELECT * FROM( "+
					" SELECT COUNT(*)AS consmp_Count,METER_SR_NO,READING_DATE_TIME FROM PREPAID_DAILY_DATA p "+
					" WHERE TO_DATE(READING_DATE_TIME,'dd-MM-yyyy')>TO_DATE('"+afterdate+"','dd-MM-yyyy') "+
					" GROUP BY METER_SR_NO,READING_DATE_TIME ORDER BY TO_DATE(READING_DATE_TIME,'dd-MM-yyyy') DESC) "+
					" WHERE consmp_Count>1 ";

			try{
				duplicateList = entityManager.createNativeQuery(getAllDuplicates).getResultList();
			}catch (Exception e) {
				System.err.println("===========================No Duplicates Records Found============================");
				e.printStackTrace();
			}

			if(duplicateList.size()>0){

				for (Iterator<?> iterator = duplicateList.iterator(); iterator.hasNext();)
				{
					final Object[] values = (Object[]) iterator.next();
					for(int i=1;i<Integer.parseInt(values[0].toString());i++)
					{
						String deleteAllduplicates =
						" DELETE FROM PREPAID_DAILY_DATA WHERE INSTANT_ID IN("+
						" SELECT X.INSTANT_ID FROM "+
						" ("+
						" SELECT * FROM PREPAID_DAILY_DATA WHERE INSTANT_ID IN(SELECT MAX(INSTANT_ID) FROM PREPAID_DAILY_DATA pdd2 "+
						" WHERE TO_DATE(READING_DATE_TIME,'dd/MM/yyyy')=TO_DATE('"+values[2]+"','dd/MM/yyyy')AND METER_SR_NO='"+values[1]+"')"+
						" )X,"+
						" ( "+
						" SELECT * FROM PREPAID_DAILY_DATA WHERE INSTANT_ID IN(SELECT MIN(INSTANT_ID) FROM PREPAID_DAILY_DATA pdd2 "+
						" WHERE TO_DATE(READING_DATE_TIME,'dd/MM/yyyy')=TO_DATE('"+values[2]+"','dd/MM/yyyy')AND METER_SR_NO='"+values[1]+"')"+
						" )Y "+
						" WHERE x.INSTANT_ID!=y.INSTANT_ID AND x.METER_SR_NO=y.METER_SR_NO AND "+
						" TO_DATE(x.READING_DATE_TIME,'dd/MM/yyyy')=TO_DATE(y.READING_DATE_TIME,'dd/MM/yyyy') AND "+
						" x.balance=y.balance AND x.DAILY_CONSUMPTION_AMOUNT=y.DAILY_CONSUMPTION_AMOUNT AND x.CUM_FIXED_CHARGE_MAIN=y.CUM_FIXED_CHARGE_MAIN AND "+
						" x.CUM_FIXED_CHARGE_DG=y.CUM_FIXED_CHARGE_DG AND x.CUSTOMER_RECHRGE_AMOUNT=y.CUSTOMER_RECHRGE_AMOUNT AND x.CUM_KWH_MAIN=y.CUM_KWH_MAIN "+
						" ) ";

						entityManager.createNativeQuery(deleteAllduplicates).executeUpdate();
						duplicateRecords++;
					}
				}
			}
			System.err.println("Duplicate Record Deleted = " + duplicateRecords);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
