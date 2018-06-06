package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.Asset;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.JobCards;
import com.bcits.bfm.model.JobNotification;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.facilityManagement.AssetService;
import com.bcits.bfm.service.facilityManagement.BlocksService;
import com.bcits.bfm.service.facilityManagement.JobCardsService;
import com.bcits.bfm.service.facilityManagement.JobNotificationService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.EmailCredentialsDetailsBean;
import com.bcits.bfm.util.OpenJobNotificationMail;
import com.bcits.bfm.util.OpenJobNotificationSMS;
import com.bcits.bfm.util.SmsCredentialsDetailsBean;


/**
 * A data access object (DAO) providing persistence and search support for
 * JobNotification entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see .JobNotification
 * @author MyEclipse Persistence Tools
 */
@Repository
public class JobNotificationImpl extends GenericServiceImpl<JobNotification> implements JobNotificationService {
	private static final Log logger = LogFactory.getLog(JobNotificationService.class);
	
	@Autowired
	private JobCardsService jobCardsService;
	@Autowired
	private BlocksService blocksService;
	@Autowired
	private PropertyService propertyService;	
	@Autowired
	private PersonService personService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private AssetService AssetService;

	
	/**
	 * Find all JobNotification entities.
	 * 
	 * @return List<JobNotification> all JobNotification entities
	 */
	@SuppressWarnings("unchecked")
	public List<JobNotification> findAll() {
		logger.info("finding all JobNotification instances");
		
	   return entityManager.createNamedQuery("JobNotification.findJobNotificationList").getResultList();
	}

	@Override
	public List<?> readData(int jcId) {
		List<Map<String, Object>> jcNotification = new ArrayList<Map<String, Object>>();
		for (final JobNotification record :readJobNotification(jcId) ) {
			jcNotification.add(new HashMap<String, Object>() {				
				private static final long serialVersionUID = 1L;
				{				
					put("jnId", record.getJnId());
					//put("jobCards", record.getJobCards().getJcId());
					put("notificationType", record.getNotificationType());
					put("notification", record.getNotification());
					put("status", record.getStatus());
					
					if(record.getNotificationMembers()!=0){
						Person p=personService.find(record.getNotificationMembers());
						String lastname="";
						if(p.getLastName()!=null)
							lastname=p.getLastName();
						put("notificationMembers",p.getFirstName()+" "+lastname );
						put("notificationMembersId",record.getNotificationMembers() );
					}
					
					if(record.getProperty()!=null){
						put("flatforJob",record.getProperty().getProperty_No());
						put("flatforJobId",record.getProperty().getPropertyId());
					}
					
					put("blockforJob", record.getBlocks().getBlockName());			
					put("blockforJobId", record.getBlocks().getBlockId());			
					put("notify", record.getNotify());			
					put("jnDt", ConvertDate.TimeStampString(record.getJnDt()));	
					put("createdBy", record.getCreatedBy());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					put("lastUpdatedDate",ConvertDate.TimeStampString(record.getLastUpdatedDt()));
				}
			});
		}
		return jcNotification;	
	}

	public List<JobNotification> readJobNotification(int jcId) {
		@SuppressWarnings("unchecked")
		List<JobNotification> jcNotification = entityManager.createNamedQuery("JobCards.readJobNotification").setParameter("jcId", jobCardsService.find(jcId)).getResultList();
		return jcNotification;
	}

	@Override
	public JobCards getAllMembers(int jcId) {
		return jobCardsService.find(jcId);
		
	}

	@Override
	public JobCards getAllMembersGroups(int jcId) {
		return jobCardsService.find(jcId);
	}

	@Override
	public JobNotification setParameters(int jcId,JobNotification jobNotification, String userName,Map<String, Object> map) {
		if(map.get("createdBy")==null)
			jobNotification.setCreatedBy(userName);
		jobNotification.setJnDt(ConvertDate.formattedDate(map.get("jnDt").toString()));
		jobNotification.setJobCards(jobCardsService.find(jcId));
		jobNotification.setLastUpdatedBy(userName);
		jobNotification.setNotification((String) map.get("notification"));		
		jobNotification.setNotificationType((String) map.get("notificationType"));
		jobNotification.setNotify((String) map.get("notify"));
		jobNotification.setStatus((String) map.get("status"));
		if(map.get("blockforJob") instanceof String){
			jobNotification.setBlocks(blocksService.find(Integer.parseInt(map.get("blockforJobId").toString())));
		}else{
			jobNotification.setBlocks(blocksService.find(Integer.parseInt(map.get("blockforJob").toString())));
		}
		
		if(map.get("notify").toString().equalsIgnoreCase("OWNER")){
			if(map.get("notificationMembers") instanceof String){
				jobNotification.setNotificationMembers((int) map.get("notificationMembersId"));	
			}else{
				jobNotification.setNotificationMembers(Integer.parseInt(map.get("notificationMembers").toString()));	
			}
			if(map.get("flatforJob")!=null && map.get("flatforJob")!=""){
				
				if(map.get("flatforJob") instanceof String){
					jobNotification.setProperty(propertyService.find(Integer.parseInt(map.get("flatforJobId").toString())));
				}else{
					jobNotification.setProperty(propertyService.find(Integer.parseInt(map.get("flatforJob").toString())));
				}
			}
					
		}
		return jobNotification;
	}

	@Override
	public Object deleteJobObjective(JobNotification jobNotification) {
		logger.info("delete JobNotification instances");
		
		  return entityManager.createNamedQuery("JobNotification.deleteJobObjective").setParameter("jnId",jobNotification.getJnId()).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setStatus(Integer jnId, String operation,HttpServletResponse response,final Locale locale) throws IOException {
		PrintWriter out = response.getWriter();	
		
		if (operation.equalsIgnoreCase("NO")) {
			JobNotification jn=find(jnId);	
			List<Person> person=new ArrayList<>();
			
			if(jn.getNotify().equalsIgnoreCase("OWNER")){
				logger.info("Owner ---");
				person.add(personService.find(jn.getNotificationMembers()));
			}else{
				logger.info("Not Owner ---");
				
				List<Property> property=entityManager.createNamedQuery("Property.getPropertyDataBasedOnBlockId").setParameter("blockId",jn.getBlockId()).getResultList();
				for(Property prop:property){
					List<Person> p1 = entityManager.createNamedQuery("Person.getAllOwnersOnPropetyId",Person.class)
							.setParameter("propertyId", prop.getPropertyId())
							.getResultList();
					List<Person> p2 = entityManager.createNamedQuery("Person.getAllTenantOnPropetyId",Person.class)
							.setParameter("propertyId", prop.getPropertyId())
							.getResultList();
					person.addAll(p1);
					person.addAll(p2);
				}				
			}
			
			String email = "";
			String mobile = "";
			
			String status="";
			String per="";
			for(Person p:person){
				List<Contact> contact = entityManager.createNamedQuery("WrongParking.getContacts").setParameter("personId", p.getPersonId()).getResultList();

				if (contact.size() == 0) {
					String str= p.getLastName();
					String lastName="";
					if(str!=null)
						lastName=str;	
					per +=p.getFirstName()+" "+lastName+",";
				} else {
					Iterator<Contact> it = contact.iterator();
					while (it.hasNext()) {
						Contact c = it.next();
						if (c.getContactPrimary().equalsIgnoreCase("yes")) {
							String str = c.getContactType();
							if (str.equalsIgnoreCase("mobile")) {
								mobile = c.getContactContent();
							} else if (str.equalsIgnoreCase("email")) {
								email = c.getContactContent();
							}
						}
					}

					if (email.equals("")) {
						String str= p.getLastName();
						String lastName="";
						if(str!=null)
							lastName=str;
						per +=p.getFirstName()+" "+lastName+",";
					} else if (mobile.equals("")) {
						String str= p.getLastName();
						String lastName="";
						if(str!=null)
							lastName=str;
						per +=p.getFirstName()+" "+lastName+",";
					}else{
						
						logger.info(p.getFirstName()+" "+p.getLastName()+"------------------------");
						
						
						JobCards jobcard=jobCardsService.find(jn.getJobCards().getJcId());
						
						String assets[]=jobcard.getJobAssets().split(",");
						String assetName="";
						for(int i=0;i<assets.length;i++){
							Asset a=AssetService.find(Integer.parseInt(assets[i]));
							assetName +=a.getAssetName()+",";
						}
						String ownerName= p.getFirstName();
						if(p.getLastName()!=null || p.getLastName()!="")
							ownerName=ownerName+" "+p.getLastName();
						
						
						
						EmailCredentialsDetailsBean emailCredentialsDetailsBean = null;
						try {
							emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
						} catch (Exception e) {
							e.printStackTrace();
						}
						  SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();

					      String messageContent ="<html>"
									+ "<style type=\"text/css\">"
									+ "table.hovertable {"
									+ "font-family: verdana,arial,sans-serif;"
									+ "font-size:11px;"
									+ "color:#333333;"
									+ "border-width: 1px;"
									+ "border-collapse: collapse;"
									+ "}"
									+ "table.hovertable th {"
									+ "background-color:#c3dde0;"
									+ "border-width: 1px;"
									+ "padding: 8px;"
									+ "border-style: solid;"
									+ "border-color: #394c2e;"
									+ "}"
									+ "table.hovertable tr {"
									+ "background-color:#88ab74;"
									+ "}"
									+ "table.hovertable td {"
									+ "border-width: 1px;"
									+ "padding: 8px;"
									+ "border-style: solid;"
									+ "border-color: #394c2e;"
									+ "}"
									+ "</style>"
									+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon RWA MAINTENACE MANAGEMENT.</h2> <br/><br /> Dear "
									+ ownerName
									+ ",<br/> <br/> "
									+ "Please be informed that the Status of New Job Assigned. <br/> <br/>"
									+ "<table class=\"hovertable\">"
									+ "<tr>"
									+ "<td colspan='2'>Your Job Details are</td>"
									+ "</tr><tr></tr>"
									+ "<tr>"
									+ "<td> Job Name </td>"
									+ "<td>"
									+ jobcard.getJobName()
									+ "</td>"
									+ "</tr>"
									+ "<tr>"
									+ "<td> Asset Name </td>"
									+ "<td>"
									+ assetName
									+ "</td>"
									+ "</tr>"
									+ "<tr>"
									+ "<td> Notification Type </td>"
									+ "<td>"
									+ jn.getNotificationType()
									+ "</td>"
									+ "</tr>"
									+ "<tr>"
									+ "<td> Notification </td>"
									+ "<td>"
									+ jn.getNotification()
									+ "</td>"
									+ "</tr>"
									+ "</table>"
									+ "<br/><br/>"
									+ "</body></html>"
									+ "<br/><br/>"
									+ "<br/>Thanks,<br/>"
									+ "SKYON RWA MAINTENACE MANAGEMENT. <br/> <br/> This is Auto Generated Mail, Please Don't Revert Back"
									+"</body></html>";
					      
					      
					      
					        emailCredentialsDetailsBean.setToAddressEmail(email);
							emailCredentialsDetailsBean.setMessageContent(messageContent);
							new Thread(new OpenJobNotificationMail(emailCredentialsDetailsBean)).start();
				
							smsCredentialsDetailsBean.setNumber(mobile);
							smsCredentialsDetailsBean.setUserName(ownerName);
				
							new Thread(new OpenJobNotificationSMS(smsCredentialsDetailsBean)).start();
						
						
						status="Notification Sent Successfully";
						
					}
				}
			}
			if(status==""){
				out.write("Contact Not Found For "+per);
			}else{
				out.write(status);
			}
		}
		else{
			entityManager.createNamedQuery("JobCards.UpdateJobNotificationStatus").setParameter("Status", "YES").setParameter("jnId", jnId).executeUpdate();
			out.write("Notification Already Sent");
		}
	}
}