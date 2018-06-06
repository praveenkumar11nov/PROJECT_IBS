package com.bcits.bfm.service.electricityBillsManagement;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface ElectricityAMRBillService  {

	float getPresentReading(String tableName, String columnName,
			Date previousDate,Date presentdate);

	List<?> findAMRDataReading(String columnName, String blockName);

	Timestamp findAMRDataDate(String columnName, String blockName);

	List<?> findAMRDGDataReading(String string, String string2);

	float getPresentDGReading(String tableName, String columnName,
			Date getbilldate, Date presentdate);

	List<?> findAMRDataReadingOnDate(String string, String string2, Date month, Date month1);

	List<?> findAMRDGDataReadingOnDate(String string, String string2,
			Date month, Date month1);

}
