package com.bcits.bfm.service.servicePoints;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ServicePointEquipmentsEntity;
import com.bcits.bfm.service.GenericService;

public interface ServicePointEquipmentsService extends  GenericService<ServicePointEquipmentsEntity> {

	List<ServicePointEquipmentsEntity> findALL();
	void setServicePointStatus(int elrmid, String operation, HttpServletResponse response);
	List<ServicePointEquipmentsEntity> findAllById(int servicePointId);
	List<String> getAllEquipmentTypes();
	void updateEquipmentStatusFromInnerGrid(int spEquipmentId,
			HttpServletResponse response);
}
