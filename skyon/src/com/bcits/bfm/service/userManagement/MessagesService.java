package com.bcits.bfm.service.userManagement;

import java.util.List;

import com.bcits.bfm.model.Messages;
import com.bcits.bfm.service.GenericService;

public interface MessagesService extends GenericService<Messages> {
	
	
	 List<Messages> getUserInboxMsg(String toUsrId , String msgStatus);
	
	List<Messages> getAll();
	
	//public void updateInboxMessages(int userId , String fromUser , String msgStatus );
	
	//public int getMsgId(int userId , String fromUser , String msgStatus );
	
	 Integer updateStatusAsTrash(int msgId , String msgStatus );
	
	 Integer getReadStatusBasedOnId(int msgId);
	
	 Integer updateReadStatus( int msgId , int status );
	
	List<Messages> saveMessageList(List<Messages> msg);
	
	List<String> getFromUsers(String userId,String msgStatus);
	
	List<String> getToUsers(String userId,String msgStatus);

}
