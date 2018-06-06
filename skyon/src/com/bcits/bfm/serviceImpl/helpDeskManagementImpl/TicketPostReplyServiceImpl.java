package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.TicketPostReplyEntity;
import com.bcits.bfm.service.helpDeskManagement.TicketPostReplyService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class TicketPostReplyServiceImpl extends GenericServiceImpl<TicketPostReplyEntity> implements TicketPostReplyService  {

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<TicketPostReplyEntity> findAllById(int ticketId) {
		return selectedList(entityManager.createNamedQuery("TicketPostReplyEntity.findAllById",TicketPostReplyEntity.class).setParameter("ticketId", ticketId).getResultList());
	}
	
	private List<TicketPostReplyEntity> selectedList(List<TicketPostReplyEntity> list) 
	{
		List<TicketPostReplyEntity> listNew = new ArrayList<TicketPostReplyEntity>();
		for (Iterator<TicketPostReplyEntity> iterator = list.iterator(); iterator.hasNext();) {
			TicketPostReplyEntity postReplyEntity = (TicketPostReplyEntity) iterator.next();
			postReplyEntity.setOpenNewTicketEntity(null);
			listNew.add(postReplyEntity);
		}
		return listNew;
	}

	@Override
	public List<TicketPostReplyEntity> findALL() {
		return null;
	}

	@Override
	public void ticketStatusUpdate(int postReplyId, HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();			
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("ticketStatus");
			TicketPostReplyEntity replyEntity = find(postReplyId);			
			if(replyEntity.getTicketStatus()==null || replyEntity.getTicketStatus().equals("")|| !replyEntity.getTicketStatus().equals("Closed"))
			{
				entityManager.createNamedQuery("TicketPostReplyEntity.ticketStatusUpdate").setParameter("ticketStatus", "Closed").setParameter("postReplyId", postReplyId).executeUpdate();
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
	public List<TicketPostReplyEntity> findAllByAscOrder(int ticketId) {
		return selectedList(entityManager.createNamedQuery("TicketPostReplyEntity.findAllByAscOrder",TicketPostReplyEntity.class).setParameter("ticketId", ticketId).getResultList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findAllIds(String className, String attributeName, int ticketId) {
		
		final StringBuffer queryString = new StringBuffer("SELECT o."+attributeName+" FROM ");
	    queryString.append(className).append(" o ");
	    queryString.append("WHERE o.ticketId = ");
	    queryString.append(ticketId);
	    final Query query = this.entityManager.createQuery(queryString.toString());
		return (List<Integer>) query.getResultList();
	}
}
