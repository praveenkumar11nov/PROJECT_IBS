package com.bcits.bfm.service.helpDeskManagement;

import java.util.List;

import com.bcits.bfm.model.OpenNewTicketEntity;
import com.bcits.bfm.model.TicketDeptTransferEntity;
import com.bcits.bfm.service.GenericService;

public interface TicketDeptTransferService extends  GenericService<TicketDeptTransferEntity> {

	List<TicketDeptTransferEntity> findAllById(int ticketId);

	List<TicketDeptTransferEntity> findAllData();

	void deptIdUpdate(int ticketId, int dept_Id);

	List<TicketDeptTransferEntity> findAllByAscOrder(int ticketId);

	void updateTicketDeptAcceptanceStatus(int ticketId);

	void updateTicketStatus(int ticketId);

	void updateTicketLastResponse(int ticketId);

	void helpTopicUpdate(int ticketId, int topicId);
	List<TicketDeptTransferEntity> getDeptTransferSearchByMonth(java.util.Date fromDate,java.util.Date toDate);

}
