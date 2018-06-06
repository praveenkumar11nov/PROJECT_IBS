package com.bcits.bfm.service.helpDeskManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.TicketPostReplyEntity;
import com.bcits.bfm.service.GenericService;

public interface TicketPostReplyService extends  GenericService<TicketPostReplyEntity> {

	List<TicketPostReplyEntity> findALL();

	List<TicketPostReplyEntity> findAllById(int ticketId);

	void ticketStatusUpdate(int postReplyId, HttpServletResponse response);

	List<TicketPostReplyEntity> findAllByAscOrder(int ticketId);

	List<Integer> findAllIds(String className, String attributeName, int ticketId);

}
