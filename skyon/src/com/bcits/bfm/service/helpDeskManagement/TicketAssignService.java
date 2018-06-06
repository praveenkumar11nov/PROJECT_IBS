package com.bcits.bfm.service.helpDeskManagement;

import java.util.List;

import com.bcits.bfm.model.TicketAssignEntity;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.GenericService;

public interface TicketAssignService extends  GenericService<TicketAssignEntity> {

	List<TicketAssignEntity> findAllById(int ticketId);

	List<Users> findUsers(int dept_Id);

	List<TicketAssignEntity> findAllByAscOrder(int ticketId);

	List<TicketAssignEntity> findAllData();
	List<Object[]> findAll();
	List<?> findAllEscalatedReports();


}
