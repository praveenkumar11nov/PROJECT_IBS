/*package com.bcits.bfm.test;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.bcits.bfm.model.MailRoom;
import com.bcits.bfm.service.facilityManagement.MailRoomService;
import com.bcits.bfm.serviceImpl.facilityManagement.MailRoomServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebContent/WEB-INF/config/web-application-config.xml")
public class MailRoomTest 
{
	@Autowired
	private MailRoomService mailRoomService;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	
	@Test
	public void selectTest() 
	{
		List ug = mailRoomService.findAll();
	}
	
	@Test
	public void deleteTest() 
	{
		MailRoom m = new MailRoom();
		mailRoomService.delete(425);
	}
	
	@Test
	//@Rollback(false)
	public void updateTest() 
	{
		MailRoom m = new MailRoom(422, 33, "RRRR", "RRR", 0, null, "jjjj", "jjjj", "jjjj");
		mailRoomService.update(m);
	}
	
	@Test	
	public void saveTest() 
	{
		MailRoom m = new MailRoom(0, 34, "Shashi", "Shashi", 0, null, "Shashi", "Shashi", "Shashi");		
		mailRoomService.save(m);		
	}
	
}
*/