package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.Messages;
import com.bcits.bfm.patternMasterService.TransactionDetailService;
import com.bcits.bfm.service.ManpowerNotificationService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.userManagement.MessagesService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.view.BreadCrumbTreeService;

/**
 * Controller which includes all the business logic of messages
 * 
 * @author Pooja.K
 * @version %I%, %G%
 * @since 0.1
 */

@Controller
public class MessageController 
{
	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private BreadCrumbTreeService breadCrumbService;

	@Autowired
	MessagesService messagesService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private Validator validator;
	
	@Autowired
	private ManpowerNotificationService manpowerNotificationService;
	
	@Autowired
    private CommonController commonController;
	
	@Autowired
	private PersonService personService;
	
	
	
	private static final Log logger = LogFactory.getLog(MessageController.class);
	
	@RequestMapping(value = "/compose")
	public String composeIndex(HttpServletRequest request, ModelMap model) {
		model.addAttribute("ViewName", "Messages");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Compose", 1, request);
		return "messages/compose";
	}
	@RequestMapping(value = "/inbox")
	public String inboxIndex(HttpServletRequest request, ModelMap model) {
		model.addAttribute("ViewName", "Inbox");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Inbox", 1, request);
		return "messages/inbox";
	}
	
	@SuppressWarnings({ "serial", "unused" })
	@RequestMapping(value = "/inbox/unread")
	public @ResponseBody List<?> inboxUnread(HttpServletRequest request, ModelMap model) {
		List<Map<String, Object>> messagesList =  new ArrayList<Map<String, Object>>(); 
		HttpSession session = request.getSession(false);
		final String userName = (String)session.getAttribute("userId");
		int count = 0;
		int userId = usersService.getUserInstanceByLoginName(userName).getUrId();
		for (final Messages record : messagesService.getUserInboxMsg(userId+"", "INBOX")) {
			final int fromUsrId = Integer.parseInt(record.getFromUser());
			if(record.getRead_status()==0){
				count++;
				messagesList.add(new HashMap<String, Object>() {{				
					put("msg_id", record.getMsg_id());
					put("", "");
					String fromUserName="";
					if(record.getNotificationType().equalsIgnoreCase("Portal"))
					{
						fromUserName=""+personService.getPropertyNoBasedOnPersonId(fromUsrId);
					}
					else
					{
					 fromUserName = usersService.getLoginNameBasedOnId(fromUsrId);
					}
					if( fromUserName.equals(userName)){
						put("fromUser", "Me("+fromUserName+")");
					}else					
					put("fromUser", fromUserName);
					put("subject", record.getSubject());
					put("message", record.getMessage());
					put("read_status", record.getRead_status());
					put("lastUpdatedDate", ConvertDate.TimeStampString(record.getLastUpdatedDate()));
					put("notificationtype", record.getNotificationType());
				}});
			}
		}
		return messagesList;
	}

	@RequestMapping(value = "/sent")
	public String sentIndex(HttpServletRequest request, ModelMap model) {
		model.addAttribute("ViewName", "Sent");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Sent", 1, request);

		return "messages/sent";
	}
	@RequestMapping(value = "/drafts")
	public String draftsIndex(HttpServletRequest request, ModelMap model) {
		model.addAttribute("ViewName", "Drafts");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Drafts", 1, request);

		return "messages/drafts";
	}
	@RequestMapping(value = "/trash")
	public String trashIndex(HttpServletRequest request, ModelMap model) {
		model.addAttribute("ViewName", "Trash");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Trash", 1, request);

		return "messages/trash";
	}
	/**
	 * Fetching user's inbox messages from the messages table 
	 * 
	 * @return  user's inbox messages
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/messages/inboxMessages", method = RequestMethod.POST)
	public @ResponseBody List<?> inboxMessages(HttpServletRequest request,HttpServletResponse res)
	{

		List<Map<String, Object>> messagesList =  new ArrayList<Map<String, Object>>(); 
		HttpSession session = request.getSession(false);
		final String userName = (String)session.getAttribute("userId");
		int userId = usersService.getUserInstanceByLoginName(userName).getUrId();
		for (final Messages record : messagesService.getUserInboxMsg(userId+"", "INBOX")) {
			final int fromUsrId = Integer.parseInt(record.getFromUser());
			
			messagesList.add(new HashMap<String, Object>() {{
				
				put("msg_id", record.getMsg_id());
				put("", "");
				String fromUserName="";
				if(record.getNotificationType().equalsIgnoreCase("Portal"))
				{
					fromUserName=""+personService.getPropertyNoBasedOnPersonId(fromUsrId);
				}
				else
				{
				 fromUserName = usersService.getLoginNameBasedOnId(fromUsrId);
				}
				if( fromUserName.equals(userName)){
					put("fromUser", "Me("+fromUserName+")");
				}else
				put("fromUser", fromUserName);
				put("subject", record.getSubject());
				put("message", record.getMessage());
				put("read_status", record.getRead_status());
				put("lastUpdatedDate", ConvertDate.TimeStampString(record.getLastUpdatedDate()));

			}});
		}
		return messagesList;
	}

	/**
	 * Fetching user's sentmail from the messages table 
	 * 
	 * @return  user's sentmail details
	 */

	@SuppressWarnings("serial")
	@RequestMapping(value = "/messages/sentMessages", method = RequestMethod.POST)
	public @ResponseBody List<?> sentMessages(HttpServletRequest request,HttpServletResponse res) {

		List<Map<String, Object>> messagesList =  new ArrayList<Map<String, Object>>(); 
		String toUserNames = "";
		String toUserName = "";
		HttpSession session = request.getSession(false);
		final String userName = (String)session.getAttribute("userId");
		int userId = usersService.getUserInstanceByLoginName(userName).getUrId();
		for (final Messages record : messagesService.getUserInboxMsg(userId+"", "SENT")) {
			String ids = record.getCcField();
			if( ids != null ){
				String toName = usersService.getLoginNameBasedOnId(Integer.parseInt(record.getToUser()));
				if( toName.equals(userName) ){
					toName = "Me("+toName+")";
				}
			toUserNames = toName+","; 
				String[] splitedIds = ids.split(",");
				for (int i = 0; i < splitedIds.length; i++) {
					int toUserId = Integer.parseInt(splitedIds[i]);
					if( Integer.parseInt(record.getToUser()) != toUserId ){
						
						toUserNames = toUserNames + usersService.getLoginNameBasedOnId(toUserId)+",";
					}
				}
				toUserName = toUserNames.substring(0, toUserNames.length() - 1);
			}
			else
				toUserName = usersService.getLoginNameBasedOnId(Integer.parseInt(record.getToUser()));
			final String toUsers = toUserName;

			messagesList.add(new HashMap<String, Object>() {{

				put("msg_id", record.getMsg_id());
				if( toUsers.trim().equals(userName) ){
					put("toUser","Me("+toUsers.trim()+")");
				}else
				put("toUser",toUsers.trim());
				put("subject", record.getSubject());
				put("message", record.getMessage());
				put("read_status", record.getRead_status());
				put("lastUpdatedDate", ConvertDate.TimeStampString(record.getLastUpdatedDate()));


			}});
		}

		return messagesList;
	} 

	/**
	 * Fetching user's draft messages from the messages table 
	 * 
	 * @return  user's draft messages
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/messages/draftMessages", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody List<?> draftMessages(HttpServletRequest request) {

		List<Map<String, Object>> messagesList =  new ArrayList<Map<String, Object>>(); 
		HttpSession session = request.getSession(false);
		final String userName = (String)session.getAttribute("userId");
		int userId = usersService.getUserInstanceByLoginName(userName).getUrId();
		for (final Messages record : messagesService.getUserInboxMsg(userId+"", "Draft")) {
			String ids = record.getToUser();
			String toUserNames = "";
			String toUserName = "";

			if( ids != null ){
				if( ids.contains(",") ){
					String[] splitedIds = ids.split(",");
					for (int i = 0; i < splitedIds.length; i++) {
						int toUserId = Integer.parseInt(splitedIds[i]);
						toUserNames = toUserNames + usersService.getLoginNameBasedOnId(toUserId)+",";

					}
					toUserName = toUserNames.substring(0, toUserNames.length() - 1);
				}
				else{
					toUserName = usersService.getLoginNameBasedOnId(Integer.parseInt(ids));
				}
				final String toUsers = toUserName;

				messagesList.add(new HashMap<String, Object>() {{

					put("msg_id", record.getMsg_id());
					if( toUsers.trim().equals(userName) ){
						put("toUser","Me("+toUsers.trim()+")");
					}else
					put("toUser",toUsers.trim());
					if( record.getSubject() == null || record.getSubject() == "" )
						put("subject", "(no subject)");
					else
						put("subject", record.getSubject());
					if( record.getMessage() == null || record.getMessage() == "" )
						put("message", "(no message)");
					else
						put("message", record.getMessage());
					put("msg_status", record.getMsg_status());
					put("read_status", record.getRead_status());
					put("lastUpdatedDate", ConvertDate.TimeStampString(record.getLastUpdatedDate()));

				}});
			}
			if( ids == null ){

				messagesList.add(new HashMap<String, Object>() {{
					put("msg_id", record.getMsg_id());
					put("toUser","");
					if( record.getSubject() == null || record.getSubject() == "" )
						put("subject", "(no subject)");
					else
						put("subject", record.getSubject());
					if( record.getMessage() == null )
						put("message", "(no message)");
					else
						put("message", record.getMessage());
					put("msg_status", record.getMsg_status());
					put("read_status", record.getRead_status());
					put("lastUpdatedDate", ConvertDate.TimeStampString(record.getLastUpdatedDate()));

				}});
			}
		}

		return messagesList;
	} 

	/**
	 * Fetching user's trash messages from the messages table 
	 * 
	 * @return  user's trash messages
	 */

	@SuppressWarnings("serial")
	@RequestMapping(value = "/messages/trashMessages", method = RequestMethod.POST)
	public @ResponseBody List<?> trashMessages(HttpServletRequest request) {

		List<Map<String, Object>> messagesList =  new ArrayList<Map<String, Object>>(); 
		HttpSession session = request.getSession(false);
		String userName = (String)session.getAttribute("userId");
		int userId = usersService.getUserInstanceByLoginName(userName).getUrId();
		for (final Messages record : messagesService.getUserInboxMsg(userId+"", "TRASH")) {
			messagesList.add(new HashMap<String, Object>() {{
				
				put("msg_id", record.getMsg_id());
				put("", "");
				if( record.getSubject() == null || record.getSubject() == "" )
					put("subject", "(no subject)");
				else
					put("subject", record.getSubject());
				if( record.getMessage() == null )
					put("message", "(no message)");
				else
					put("message", record.getMessage());
				put("read_status", record.getRead_status());
				put("lastUpdatedDate", ConvertDate.TimeStampString(record.getLastUpdatedDate()));
				

				/*put("select", "select");*/

			}});
		}

		return messagesList;
	} 
	
	/**
	 * deleting user's inbox message and move that message to trash 
	 * 
	 * @return  user's inbox messages
	 */


	@RequestMapping(value = "/messages/deleteMessage/{msgId}/{agree}",  method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	void deleteInboxMessage(@PathVariable List<Integer> msgId , @PathVariable boolean agree , HttpServletRequest request , HttpServletResponse res) {


		PrintWriter out = null;
		if( agree == true ){
			//int count = 0;
			for (Iterator<Integer> iterator = msgId.iterator(); iterator.hasNext();) {
				Integer msgIdss = (Integer) iterator.next();
				messagesService.updateStatusAsTrash(msgIdss,"TRASH");

			}

			try {
				out = res.getWriter();
				out.write("SUCCESS");
			} catch (IOException e) {

			}
		}
		else{

			try {
				out = res.getWriter();
				out.write("FAIL");
			} catch (IOException e) {

			}

		}

	}

	/**
	 * deleting user's sent message and move that message to trash 
	 * 
	 * @return  user's sent messages
	 */
	@RequestMapping(value = "/messages/deleteSentMessage", method = RequestMethod.POST)
	public @ResponseBody
	Messages deleteSentMessage(@RequestBody Map<String, Object> map,HttpServletRequest request) {

		Messages messages = new Messages();
		HttpSession session = request.getSession(false);
		String userName = (String)session.getAttribute("userId");
		int userId = usersService.getUserInstanceByLoginName(userName).getUrId();

		int msgId = (Integer)map.get("msg_id");
		String subject = (String)map.get("subject");
		String message = (String)map.get("message");
		String toUser = (String)map.get("toUser");
		int toUserId = usersService.getUserInstanceByLoginName(toUser).getUrId();

		messages.setMsg_id(msgId);
		messages.setUsr_id(userId+"");
		messages.setToUser(toUserId+"");
		messages.setFromUser("");
		messages.setSubject(subject);
		messages.setMessage(message);
		messages.setMsg_status("TRASH");
		messages.setRead_status(0);

		messagesService.update(messages);
		return messages;

	}

	/**
	 * deleting user's Draft message and move that message to trash 
	 * 
	 * @return  user's Draft messages
	 */
	@RequestMapping(value = "/messages/deleteDraftMessage", method = RequestMethod.POST)
	public @ResponseBody
	Messages deleteDraftMessage(@RequestBody Map<String, Object> map,HttpServletRequest request) {

		Messages messages = new Messages();
		HttpSession session = request.getSession(false);
		String userName = (String)session.getAttribute("userId");
		int userId = usersService.getUserInstanceByLoginName(userName).getUrId();

		int msgId = (Integer)map.get("msg_id");
		String subject = (String)map.get("subject");
		String message = (String)map.get("message");

		messages.setMsg_id(msgId);
		messages.setUsr_id(userId+"");
		messages.setToUser("");
		messages.setFromUser("");
		messages.setSubject(subject);
		messages.setMessage(message);
		messages.setMsg_status("TRASH");
		messages.setRead_status(0);

		messagesService.update(messages);
		return messages;


	}
	/**
	 * deleting user's message permanently from the trash 
	 * 
	 * @return  user's trash messages
	 */

	@RequestMapping(value = "/messages/deleteTrashMessages/{msgId}/{agree}",  method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	void deleteTrashMessages(@PathVariable List<Integer> msgId ,@PathVariable boolean agree, HttpServletRequest request , HttpServletResponse res) {


		PrintWriter out = null;

		if( agree == true ){
			//int count = 0;
			for (Iterator<Integer> iterator = msgId.iterator(); iterator.hasNext();) {
				Integer msgIdss = (Integer) iterator.next();
				messagesService.delete(msgIdss);

			}

			try {
				out = res.getWriter();
				out.write("SUCCESS");
			} catch (IOException e) {

			}
		}
		else{

			try {
				out = res.getWriter();
				out.write("FAIL");
			} catch (IOException e) {

			}
		}

	}

	/**
	 * fetching all user names from messages table and send to view 
	 * 
	 * @return  user object
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value="/messages/getUserName",method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody List<?> getUserName()
	{

		List<Map<String, String>> list =  new ArrayList<Map<String, String>>();  
		List<String> string = usersService.getAllUsersLoginNames();
		for (Iterator<String> iterator = string.iterator(); iterator.hasNext();) {
			final String string2 = (String) iterator.next();
			list.add(new HashMap<String, String>() {{
				put("usr_id",string2);

			}});
		}

		return list;
	}
	@SuppressWarnings("serial")
	@RequestMapping(value="/messages/getAllLoginNames",method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody List<?> getAllLoginNames()
	{

		List<Map<String, String>> list =  new ArrayList<Map<String, String>>();  
		List<String> string = usersService.getAllUsersLoginNames();
		for (Iterator<String> iterator = string.iterator(); iterator.hasNext();) {
			final String string2 = (String) iterator.next();
			list.add(new HashMap<String, String>() {{
				put("loginnName",string2);

			}});
		}

		return list;
	}
	@RequestMapping(value = "/messages/updateReadStatusAsOne/{msgId}",  method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody void updateReadStatus(@PathVariable int msgId , HttpServletRequest request , HttpServletResponse res) {

		messagesService.updateReadStatus(msgId , 1 );

	}
	@RequestMapping(value = "/messages/updateReadStatusAsZero/{msgId}",  method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	void updateReadStatusAsZero(@PathVariable int msgId , HttpServletRequest request , HttpServletResponse res) {

		messagesService.updateReadStatus(msgId , 0 );

	}
	@RequestMapping(value = "/messages/changeReadStatus/{id}",  method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	void changeReadStatus(@PathVariable int id , HttpServletRequest request , HttpServletResponse res) {

		int statusNo = messagesService.getReadStatusBasedOnId(id);
		if( statusNo == 0 ){
			messagesService.updateReadStatus(id , 1 );
		}

	}

	@RequestMapping(value = {"/messages/getSelectionValue"}, method = RequestMethod.GET)
	public String getSelectionValue(Model model) {

		model.addAttribute("getSelectionValueUrl", "select"); 
		return "messages/getSelectionValue";
	}

	@SuppressWarnings({ "unused", "null" })
	@RequestMapping(value = "/messages/replyToSender/{names}/{cc}/{sub}/{msg}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody void replyToSender(@PathVariable String names,@PathVariable String cc,@PathVariable String sub,@PathVariable String msg,HttpServletRequest request,HttpServletResponse res) {

		if( cc.equals("null") )
			cc = "";
		if( msg.equals("null") )
			msg = "";

		Set<Integer> idsSet = new HashSet<Integer>();
		String ccId = null;
		String toUserIds = "" ;
		String[] toUsers = null;
		String userIdsInCc = "" ;
		String[] usersInCc = null;
		int userIdInCc ;
		int toUserId ;
		Messages messages1 = new Messages();
		Messages messages2 = new Messages();
		Messages msgs = null;
		HttpSession session = request.getSession(false);
		String userName = (String)session.getAttribute("userId");

		usersService.getUserInstanceByLoginName(userName);
		int userId = usersService.getUserInstanceByLoginName(userName).getUrId();
		List<Messages> messsageList = new ArrayList<Messages>();
		toUsers = names.split(",");

		for (int i = 0; i < toUsers.length; i++) {
			String string = toUsers[i];
			toUserId = usersService.getUserInstanceByLoginName(string).getUrId();
			idsSet.add(toUserId);
			toUserIds = toUserIds + toUserId + ",";

		}
		String id = toUserIds.substring(0, toUserIds.length()-1);
		messages1.setUsr_id(id);
		messages2.setToUser(id);


		if( cc != "" ){
			usersInCc = cc.split(",");

			for (int i = 0; i < usersInCc.length; i++) {
				String string = usersInCc[i];
				userIdInCc = usersService.getUserInstanceByLoginName(string).getUrId();
				idsSet.add(userIdInCc);
				userIdsInCc = userIdsInCc + userIdInCc + ",";

			}
			ccId = userIdsInCc.substring(0, userIdsInCc.length() - 1);
		}
		if( cc == "" ){

			messages1.setUsr_id(id);
			messages1.setFromUser(userId+"");
			messages1.setToUser("");
			messages1.setSubject(sub);
			messages1.setMessage(msg);
			messages1.setMsg_status("INBOX");
			messages1.setRead_status(0);
			messages1.setCcField("");
			messages1.setNotificationType("MESSAGE");
			//messsageList.add(messages1);
			msgs = messagesService.save(messages1);
		}
		else if( cc != "" ){
			Iterator< Integer> idddd = idsSet.iterator();
			while( idddd.hasNext() ){
				Messages messages3 = new Messages();
				messages3.setUsr_id(idddd.next()+"");
				messages3.setFromUser(userId+"");
				messages3.setToUser("");
				messages3.setSubject(sub);
				messages3.setMessage(msg);
				messages3.setMsg_status("INBOX");
				messages3.setRead_status(0);
				messages3.setCcField(ccId);
				messages3.setNotificationType("MESSAGE");
				msgs = messagesService.save(messages3);
				messages2.setCcField(userIdsInCc);

			}
		}
		messages2.setUsr_id(userId+"");
		messages2.setFromUser("");
		messages2.setToUser(id);
		messages2.setSubject(sub);
		messages2.setMessage(msg);
		messages2.setMsg_status("SENT");
		messages2.setRead_status(0);
		messages2.setCcField(ccId);
		messages2.setNotificationType("MESSAGE");
		msgs = messagesService.save(messages2);

		PrintWriter out = null;
		try {
			//List<Messages> msgs = messagesService.saveMessageList(messsageList);
			if( msgs instanceof Messages){
				out = res.getWriter();
				out.write("SUCCESS");
			}
		} catch (IOException e) {
			out.write("ERROR");
		}

	} 
	@SuppressWarnings("null")
	@RequestMapping(value = "/messages/saveToDraft/{names}/{cc}/{sub}/{msg}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody void saveToDraft(@PathVariable String names,@PathVariable String cc,@PathVariable String sub,@PathVariable String msg,HttpServletRequest request,HttpServletResponse res) {

		if( cc.equals("null") )
			cc = "";
		if( msg.equals("null") )
			msg = "";
		if( names.equals("null") )
			names = "";
		if( sub.equals("null") )
			sub = "";
		Set<Integer> idsSet = new HashSet<Integer>();
		String ccId = null;
		String toUserIds = "" ;
		String[] toUsers = null;
		String userIdsInCc = "" ;
		String id = "";
		String[] usersInCc = null;
		int userIdInCc ;
		int toUserId ;
		HttpSession session = request.getSession(false);
		String userName = (String)session.getAttribute("userId");

		usersService.getUserInstanceByLoginName(userName);
		int userId = usersService.getUserInstanceByLoginName(userName).getUrId();
		Messages messages = new Messages();

		if( names != "" ){
			toUsers = names.split(",");
			for (int i = 0; i < toUsers.length; i++) {
				String string = toUsers[i];
				toUserId = usersService.getUserInstanceByLoginName(string).getUrId();
				idsSet.add(toUserId);
				toUserIds = toUserIds + toUserId + ",";

			}
			id = toUserIds.substring(0, toUserIds.length()-1);
		}
		if( cc != "" ){
			usersInCc = cc.split(",");
			for (int i = 0; i < usersInCc.length; i++) {
				String string = usersInCc[i];
				userIdInCc = usersService.getUserInstanceByLoginName(string).getUrId();
				idsSet.add(userIdInCc);
				userIdsInCc = userIdsInCc + userIdInCc + ",";

			}
			ccId = userIdsInCc.substring(0, userIdsInCc.length() - 1);
		}

		messages.setUsr_id(userId+"");
		messages.setFromUser("");
		messages.setToUser(id);
		messages.setSubject(sub);
		messages.setMessage(msg);
		messages.setMsg_status("Draft");
		messages.setRead_status(0);
		messages.setCcField(ccId);
		messages.setNotificationType("MESSAGE");

		PrintWriter out = null;
		try {
			messagesService.save(messages);
			out = res.getWriter();
			out.write("DRAFTSUCCESS");
		} catch (IOException e) {
			out.write("DRAFTERROR");
		}
	} 

	/**
	 * composing new message and save it in messages table 
	 * 
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/messages/composeNewMsg/{names}/{cc}/{sub}/{msg}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody void composeNewMsg(@PathVariable String names,@PathVariable String cc,@PathVariable String sub,@PathVariable String msg, HttpServletRequest request,HttpServletResponse res) {

		if( cc.equals("null") )
			cc = "";
		if( msg.equals("null") )
			msg = "";
		Set<Integer> idsSet = new HashSet<Integer>();
		String ccId = null;
		String toUserIds = "" ;
		String[] toUsers = null;
		String userIdsInCc = "" ;
		String[] usersInCc = null;
		int userIdInCc ;
		int toUserId ;
		Messages messages1 = new Messages();
		Messages messages2 = new Messages();
		Messages msgs = null;
		HttpSession session = request.getSession(false);
		String userName = (String)session.getAttribute("userId");

		usersService.getUserInstanceByLoginName(userName);
		int userId = usersService.getUserInstanceByLoginName(userName).getUrId();
		toUsers = names.split(",");

		for (int i = 0; i < toUsers.length; i++) {
			String string = toUsers[i];
			toUserId = usersService.getUserInstanceByLoginName(string).getUrId();
			idsSet.add(toUserId);
			toUserIds = toUserIds + toUserId + ",";

		}
		String id = toUserIds.substring(0, toUserIds.length()-1);
		if( cc != "" ){
			usersInCc = cc.split(",");
			for (int i = 0; i < usersInCc.length; i++) {
				String string = usersInCc[i];
				userIdInCc = usersService.getUserInstanceByLoginName(string).getUrId();
				idsSet.add(userIdInCc);
				userIdsInCc = userIdsInCc + userIdInCc + ",";

			}
			ccId = userIdsInCc.substring(0, userIdsInCc.length() - 1);
		}

		if( cc == "" ){
			messages1.setUsr_id(id);
			messages1.setFromUser(userId+"");
			messages1.setToUser("");
			messages1.setSubject(sub);
			messages1.setMessage(msg);
			messages1.setMsg_status("INBOX");
			messages1.setRead_status(0);
			messages1.setCcField("");
			messages1.setNotificationType("MESSAGE");
			//messsageList.add(messages1);
			msgs = messagesService.save(messages1);
			messages2.setCcField("");
		}

		else if( cc != "" ){
			Iterator< Integer> idddd = idsSet.iterator();
			while( idddd.hasNext() ){
				Messages messages3 = new Messages();
				messages3.setUsr_id(idddd.next()+"");
				messages3.setFromUser(userId+"");
				messages3.setToUser("");
				messages3.setSubject(sub);
				messages3.setMessage(msg);
				messages3.setMsg_status("INBOX");
				messages3.setRead_status(0);
				messages3.setCcField(ccId);
				messages3.setNotificationType("MESSAGE");
				msgs = messagesService.save(messages3);
				//messsageList.add(messages3);

				messages2.setCcField(ccId);
			}
		}
		messages2.setUsr_id(userId+"");
		messages2.setFromUser("");
		messages2.setToUser(id);
		messages2.setSubject(sub);
		messages2.setMessage(msg);
		messages2.setMsg_status("SENT");
		messages2.setRead_status(0);
		messages2.setCcField(ccId);
		messages2.setNotificationType("MESSAGE");
		msgs = messagesService.save(messages2);

		PrintWriter out = null;
		try {
			//List<Messages> msgs = messagesService.saveMessageList(messsageList);
			if( msgs instanceof Messages){
				out = res.getWriter();
				out.write("SUCCESS");

			}

		} catch (IOException e) {
			out.write("ERROR");
		}
	} 

	@RequestMapping(value = "/messages/getFromNameList", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getFromNameList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String userName = (String)session.getAttribute("userId");
		int userId = usersService.getUserInstanceByLoginName(userName).getUrId();
		Set<String> result = new HashSet<String>();
		for(String fromUserId : messagesService.getFromUsers(userId+"","INBOX")){

			result.add(usersService.getLoginNameBasedOnId(Integer.parseInt(fromUserId)));

		}
		return result;
	}
	@RequestMapping(value = "/messages/getToNameList", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getToNameList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		final String userName = (String)session.getAttribute("userId");
		int userId = usersService.getUserInstanceByLoginName(userName).getUrId();
		Set<String> result = new HashSet<String>();
		
		String toUserNames = "";
		String toUserName = "";
		for (final Messages record : messagesService.getUserInboxMsg(userId+"", "SENT")) {
			String ids = record.getCcField();
			if( ids != null ){
				String toName = usersService.getLoginNameBasedOnId(Integer.parseInt(record.getToUser()));
				if( toName.equals(userName) ){
					toName = "Me("+toName+")";
				}
			toUserNames = toName+","; 
				String[] splitedIds = ids.split(",");
				for (int i = 0; i < splitedIds.length; i++) {
					int toUserId = Integer.parseInt(splitedIds[i]);
					if( Integer.parseInt(record.getToUser()) != toUserId ){
						
						toUserNames = toUserNames + usersService.getLoginNameBasedOnId(toUserId)+",";
					}
				}
				toUserName = toUserNames.substring(0, toUserNames.length() - 1);
			}
			else
				toUserName = usersService.getLoginNameBasedOnId(Integer.parseInt(record.getToUser()));
			final String toUsers = toUserName;
			if( toUsers.equals(userName) ){
				result.add("Me("+toUsers+")");
			}
			else
			result.add(toUsers);
		}

		return result;
	}
	
	
	
	
   //ManPower Notification Controller
	@SuppressWarnings({ "unused" })
	@RequestMapping(value = "/manpower/notification/unread")
	public @ResponseBody List<?> manpowerNotificationUnread(HttpServletRequest request, ModelMap model,final Locale locale) {
		
		List<Map<String, Object>> messagesList =  new ArrayList<Map<String, Object>>(); 
		HttpSession session = request.getSession(false);
		String userName=(String) session.getAttribute("userId");
		int userId = usersService.getUserInstanceByLoginName(userName).getUrId();
		logger.info("**********UserID**********"+userId);
		int designationId=manpowerNotificationService.getDesignationId(userName);
		
        /*List<?> manpowerNotificationUnread = commonController.getSortedList(usersService.getAllUserApproval(userId), "urId");
	    return manpowerNotificationUnread*/;
	   		
		return manpowerNotificationService.manpowerNotificationUnread(designationId);
		
	}
	
	
	@RequestMapping(value = "/manPowerNotification/updateStatus/{manPowerId}/{desigId}",method ={ RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody void updateManPowerNotificationStatus(@PathVariable int manPowerId,@PathVariable int desigId) {
	
		manpowerNotificationService.updateManPowerNotificationStatus(manPowerId,desigId);

	}

}

