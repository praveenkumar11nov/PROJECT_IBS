package com.bcits.bfm.service.helpDeskManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.OpenNewTicketEntity;
import com.bcits.bfm.model.TicketPostInternalNoteEntity;
import com.bcits.bfm.service.GenericService;

public interface PostInternalNoteService extends  GenericService<TicketPostInternalNoteEntity> {

	List<TicketPostInternalNoteEntity> findAllById(int ticketId);

	void ticketStatusUpdateOnPostInternalNote(int internalNoteID, HttpServletResponse response);

	List<TicketPostInternalNoteEntity> findAllByAscOrder(int ticketId);

	OpenNewTicketEntity getPersonIdBasedOnTicketId(int ticketId);

}
