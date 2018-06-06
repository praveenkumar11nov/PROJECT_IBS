package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.OpenNewTicketEntity;
import com.bcits.bfm.model.TicketPostInternalNoteEntity;
import com.bcits.bfm.service.helpDeskManagement.PostInternalNoteService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class PostInternalNoteServiceImpl extends GenericServiceImpl<TicketPostInternalNoteEntity> implements PostInternalNoteService  {

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<TicketPostInternalNoteEntity> findAllById(int ticketId) {
		return selectedList(entityManager.createNamedQuery("TicketPostInternalNoteEntity.findAllById",TicketPostInternalNoteEntity.class).setParameter("ticketId", ticketId).getResultList());
	}
	
	private List<TicketPostInternalNoteEntity> selectedList(List<TicketPostInternalNoteEntity> list) 
	{
		List<TicketPostInternalNoteEntity> listNew = new ArrayList<TicketPostInternalNoteEntity>();
		for (Iterator<TicketPostInternalNoteEntity> iterator = list.iterator(); iterator.hasNext();) {
			TicketPostInternalNoteEntity postInternalNoteEntity = (TicketPostInternalNoteEntity) iterator.next();
			postInternalNoteEntity.setOpenNewTicketEntity(null);
			listNew.add(postInternalNoteEntity);
		}
		return listNew;
	}

	@Override
	public void ticketStatusUpdateOnPostInternalNote(int internalNoteID, HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();			
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("ticketStatus");
			TicketPostInternalNoteEntity replyEntity = find(internalNoteID);			
			if(replyEntity.getTicketStatus()==null || replyEntity.getTicketStatus().equals("")|| !replyEntity.getTicketStatus().equals("Closed"))
			{
				entityManager.createNamedQuery("TicketPostInternalNoteEntity.ticketStatusUpdateOnPostInternalNote").setParameter("ticketStatus", "Closed").setParameter("internalNoteID", internalNoteID).executeUpdate();
				out.write("Ticket Closed");
			}else {
				out.write("Ticket Already Closed");
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}		
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<TicketPostInternalNoteEntity> findAllByAscOrder(int ticketId) {
		return selectedList(entityManager.createNamedQuery("TicketPostInternalNoteEntity.findAllByAscOrder",TicketPostInternalNoteEntity.class).setParameter("ticketId", ticketId).getResultList());
	}

	@Override
	public OpenNewTicketEntity getPersonIdBasedOnTicketId(int ticketId) {
		try{
			return (OpenNewTicketEntity)entityManager.createNamedQuery("TicketPostInternalNoteEntity.getPersonIdBasedOnTicketId").setParameter("ticketId", ticketId).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
}
