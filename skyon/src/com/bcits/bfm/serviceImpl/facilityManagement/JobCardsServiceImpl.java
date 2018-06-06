package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import com.bcits.bfm.model.Asset;
import com.bcits.bfm.model.AssetMaintenanceCost;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.JcLabourtasks;
import com.bcits.bfm.model.JcMaterials;
import com.bcits.bfm.model.JcObjectives;
import com.bcits.bfm.model.JcTeam;
import com.bcits.bfm.model.JcTools;
import com.bcits.bfm.model.JobCalender;
import com.bcits.bfm.model.JobCards;
import com.bcits.bfm.model.Messages;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.AssetMaintenanceCostService;
import com.bcits.bfm.service.facilityManagement.AssetService;
import com.bcits.bfm.service.facilityManagement.JcLabourtasksService;
import com.bcits.bfm.service.facilityManagement.JcMaterialsService;
import com.bcits.bfm.service.facilityManagement.JcObjectivesService;
import com.bcits.bfm.service.facilityManagement.JcTeamService;
import com.bcits.bfm.service.facilityManagement.JcToolsService;
import com.bcits.bfm.service.facilityManagement.JobCardsService;
import com.bcits.bfm.service.facilityManagement.JobTypesService;
import com.bcits.bfm.service.facilityManagement.MaintainanceTypesService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.service.userManagement.MessagesService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.EmailCredentialsDetailsBean;
import com.bcits.bfm.util.OpenJobNotificationMail;
import com.bcits.bfm.util.OpenJobNotificationSMS;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.util.SmsCredentialsDetailsBean;

/**
 * A data access object (DAO) providing persistence and search support for
 * JobCards entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see .JobCards
 * @author MyEclipse Persistence Tools
 */
@Repository
public class JobCardsServiceImpl extends GenericServiceImpl<JobCards> implements
		JobCardsService {

	private static final Log logger = LogFactory.getLog(JobCardsService.class);

	@Autowired
	private PersonService personService;
	
	@Autowired
	private Validator validator;
	@Autowired
	MessagesService messagesService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private AssetService assetService;
	@Autowired
	private MaintainanceTypesService maintainanceTypesService;
	@Autowired
	private JobTypesService jobTypesService;	
	@Autowired
	private JcObjectivesService JcObjectivesService;	
	@Autowired
	private JcTeamService jcTeamService;	
	@Autowired
	private JcToolsService jcToolsService;	
	@Autowired
	private JcMaterialsService jcMaterialsService;	
	@Autowired
	private JcLabourtasksService jcLabourtasksService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private AssetMaintenanceCostService assetMaintenanceCostService;
	@Override
	@SuppressWarnings("unchecked")
	public List<JobCards> findAll() {
		logger.info("Finding Job Cards Details");
	    return entityManager.createNamedQuery("JobCards.findAllJobCardsList").getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<JobCalender> findAllJobCalenderId() {
		logger.info("Finding Job Calender Details");
		return entityManager.createNamedQuery("JobCalender.findAllJobCalendersList").getResultList();
	}

	@Override
	public List<?> readData() {
		logger.info("Job Cards Reading Records");
		List<Map<String, Object>> jobCards = new ArrayList<Map<String, Object>>();
		List<?> list = entityManager.createNamedQuery("JobCards.findAllList").getResultList();

		
		for (final Iterator<?> i = list.iterator(); i.hasNext();) {
			
			jobCards.add(new HashMap<String, Object>() {				
				private static final long serialVersionUID = 1L;				{				
					final Object[] values = (Object[])i.next();

					put("jcId", (Integer)values[0]);
					put("jobGeneratedby", (String)values[1]);
					put("jobNo", (String)values[2]);
					put("jobName", (String)values[3]);
					/*put("jobGroup", record.getJobGroup());*/
					put("jobDescription", (String)values[4]);					
					put("jobDt", ConvertDate.TimeStampString((Timestamp)values[5]));					
					put("jobDepartment", (String)values[6]);
					put("jobDepartmentId", (Integer)values[7]);
					put("jobExpectedSla", (Integer)values[8]);					
					if((String)values[9]!=null){
					put("jobSla", (String)values[9]);}
					else{
					put("jobSla", "N/A");}
					put("jobMtId", (Integer)values[10]);
					put("jobMt", (String)values[11]);
					if((Timestamp)values[12]!=null)
						put("jobStartDt",ConvertDate.TimeStampString((Timestamp)values[12]));
					if((Timestamp)values[13]!=null)
						put("jobEndDt",ConvertDate.TimeStampString((Timestamp)values[13]));	
					List<Map<String,Object>> assetList = new ArrayList<Map<String,Object>>();					
					if((String)values[14]!=null){
						String assets[]=((String)values[14]).split(",");
						Map<String,Object> mapobject = null;
						for (String value : assets) {
							mapobject = new HashMap<String,Object>();
							try{
								Asset a= assetService.find(Integer.parseInt(value));
								mapobject.put("assetName", a.getAssetName());
								mapobject.put("assetId", a.getAssetId());
								assetList.add(mapobject);
							}catch(Exception e){
								
							}
							
						}						
					}					
					put("jobAssets",assetList);	
					put("jobAssetsDummy",assetList);
					
					String lastname="";
					
					if((String)values[16]!=null)
						lastname=(String)values[16];
					
					put("pn_Name", (String)values[15]+" "+lastname);
					put("personId", (Integer)values[17]);
					
					put("jobCcId", (Integer)values[18]);
					put("jobBmsId", (Integer)values[19]);
					put("jobAmsId", (Integer)values[20]);					
					put("jobType", (String)values[21]);
					put("jobTypeId", (Integer)values[22]);
					put("jobPriority", (String)values[23]);					
					put("status",(String)values[24]);					
					put("suspendStatus",(String)values[25]);					
					put("createdBy", (String)values[26]);
					put("lastUpdatedBy", (String)values[27]);
					if((Timestamp)values[28]!=null){
					put("expDate", ConvertDate.TimeStampString((Timestamp)values[28]));}
					else{
					put("expDate", "N/A");}
					if((Timestamp)values[29]!=null){
					put("lastUpdatedDate",ConvertDate.TimeStampString((Timestamp)values[29]));}
					put("jobCalender",(Integer)values[30]);
					
				}
			});
		}
		return jobCards;		
	}

	@Override
	public void setStatus(int jcId, String operation,HttpServletResponse response,final Locale locale) throws Exception {
		PrintWriter out = response.getWriter();
		JobCards jcard=find(jcId);
		if(jcard.getSuspendStatus().equalsIgnoreCase("RESUME")){
			if(operation.equalsIgnoreCase("INIT")){				
				List<JcObjectives> jo= JcObjectivesService.readJobObjective(jcId);		
				List<String> jcm=jcMaterialsService.readJobMaterialsStatus(jcId);
				String status=null;
				
				for(String jmaterials:jcm){
					if(jmaterials.equals("Issue")){
						status="Materials Are Not Approved";
						break;
					}
				}
				if(jo.size()<1){
					out.write("Objective Not Specified");										
				}else if(status!=null){
					out.write(status);
				}
				else{
					Date todaysDate = new Date();
					entityManager.createNamedQuery("JobCards.UpdateStatus").setParameter("Status", "OPEN").setParameter("startDate", todaysDate).setParameter("jcId", jcId).executeUpdate();
					out.write("JOB Is Open");
					
					String userName = (String) SessionData.getUserDetails().get("userID");
					
					int userId = usersService.getUserInstanceByLoginName(userName).getUrId();
					
					Messages messages3 = new Messages();
					messages3.setUsr_id(jcard.getPerson().getUsers().getUrId()+"");
					messages3.setFromUser(userId+"");
					messages3.setToUser("");
					messages3.setSubject("You Have Job To Achieve ");
					messages3.setMessage("");
					messages3.setMsg_status("INBOX");
					messages3.setRead_status(0);				
					messages3.setNotificationType("Job Cards");
					
					messagesService.save(messages3);
					
					sendNotifiactionjobOpen(jcard.getPerson(),jcard.getJobExpectedSla(),jcard.getJobExpectedSla(),jcard.getJobName(),jcard.getJobNo(), locale);			
				}			
				
				
			}else if(operation.equalsIgnoreCase("OPEN")){
				entityManager.createNamedQuery("JobCards.UpdateStatusOnJob").setParameter("Status", "ON JOB").setParameter("jcId", jcId).executeUpdate();
				out.write("JOB Is Under Progress");				
				
			}else if(operation.equalsIgnoreCase("ON JOB")){
				Date presentDate = new Date();
				JobCards jc=find(jcId);
				Date presentDate1 = new Date();
				String days=compareTwoTimeStamps(new Timestamp(presentDate1.getTime()),jc.getJobStartDt());	
				String str[]=days.split(" ");	
				if(Integer.parseInt(str[0])>=jcard.getJobExpectedSla()){					
					sendNotifiactionMoreDays(jcard.getPerson(),days,jcard.getJobExpectedSla(),jcard.getJobName(),jcard.getJobNo(), locale);
				}
				entityManager.createNamedQuery("JobCards.UpdateStatusOnClose").setParameter("Status", "CLOSED").setParameter("endDate", presentDate).setParameter("sla",days).setParameter("jcId", jcId).executeUpdate();
				out.write("Job is Completed in "+days.toString()+", Job SLA was "+jcard.getJobExpectedSla()+" days \nJOB Is CLOSED");
				updateAssets(jcId);
			}else if(operation.equalsIgnoreCase("CLOSED")){
				out.write("JOB Is Already Closed");
			}
		}else{
			out.write("JOB Is Suspended Plese Resume The Job");
		}
		
	}
	private void sendNotifiactionjobOpen(Person person, int jobExpectedSla,
			int jobExpectedSla2, String jobName, String jobNo, Locale locale) throws Exception {
		String email = "";
		String mobile = "";
		String lastName="";
		if(person.getLastName()!=null)
			lastName=person.getLastName();
		String ownerName=person.getTitle()+" "+person.getFirstName()+" "+lastName;
		@SuppressWarnings("unchecked")
		List<Contact> contact = entityManager.createNamedQuery("WrongParking.getContacts").setParameter("personId", person.getPersonId()).getResultList();

		if (contact.size() == 0) {						
			logger.info("Contact Not Found For " + ownerName);
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
				 logger.info("Email Not Found For " + ownerName);
			} else if (mobile.equals("")) {				
				logger.info("Contact Number Not Found For "+ ownerName);
			} else {
				
				      EmailCredentialsDetailsBean emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
					  SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();

				      String messageContent = 
								"<html>"
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
										+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">SKYON RWA MAINTENACE MANAGEMENT.</h2> <br/><br /> Dear "
										+ ownerName
										+ ",<br/> <br/> "
										+ "Please be advised that the New Job Assigned to you,It Should be completed whithin Expected SLA. <br/> <br/>"
										+ "<table class=\"hovertable\">"
										+ "<tr>"
										+ "<td colspan='2'>Your Job Details are</td>"
										+ "</tr><tr></tr>"
										+ "<tr>"
										+ "<td> Job Number </td>"
										+ "<td>"
										+ jobNo
										+ "</td>"
										+

										"</tr>"
										+ "<tr>"
										+ "<td> Job Name </td>"
										+ "<td>"
										+ jobName
										+ "</td>"
										+ "</tr>"
										+ "<tr>"
										+ "<td> Job Expected SLA </td>"
										+ "<td>"
										+ jobExpectedSla
										+ "</td>"
										+ "</tr>"
										+ "</table>"
										+ "<br/><br/>"
										+ "</body></html>"
										+ "<br/><br/>"
										+ "<br/>Thanks,<br/>"
										+ "Skyon RWA MAINTENACE MANAGEMENT. <br/> <br/> This is Auto Generated Mail, Please Don't Revert Back"
										+"</body></html>";
				
				        emailCredentialsDetailsBean.setToAddressEmail(email);
						emailCredentialsDetailsBean.setMessageContent(messageContent);
						new Thread(new OpenJobNotificationMail(emailCredentialsDetailsBean)).start();

						smsCredentialsDetailsBean.setNumber(mobile);
						smsCredentialsDetailsBean.setUserName(ownerName);
						
						new Thread(new OpenJobNotificationSMS(smsCredentialsDetailsBean)).start();
						logger.info("Notice Generated To " + email + " , "+ mobile);				
			}

		}
		
	}

	@SuppressWarnings("unchecked")
	private String sendNotifiactionMoreDays(Person person,String dateOfcompletion,int jobSla,String jobName,String jobNumber, Locale locale) throws Exception {
		String email = "";
		String mobile = "";
		String lastName="";
		if(person.getLastName()!=null)
			lastName=person.getLastName();
		String ownerName=person.getTitle()+" "+person.getFirstName()+" "+lastName;
		List<Contact> contact = entityManager.createNamedQuery("WrongParking.getContacts").setParameter("personId", person.getPersonId()).getResultList();

		if (contact.size() == 0) {						
			return "Contact Not Found For " + ownerName;
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
				return "Email Not Found For " + ownerName;
			} else if (mobile.equals("")) {				
				return "Contact Number Not Found For "+ ownerName;
			} else {
				
						  EmailCredentialsDetailsBean emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
						  SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();

					      String messageContent = "<html>"
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
									+ "Please be advised that the Job Assigned to you is not completed whithin Expected SLA. <br/> <br/>"
									+ "<table class=\"hovertable\">"
									+ "<tr>"
									+ "<td colspan='2'>Your Job Details are</td>"
									+ "</tr><tr></tr>"
									+ "<tr>"
									+ "<td> Job Number </td>"
									+ "<td>"
									+ jobNumber
									+ "</td>"
									+
		
									"</tr>"
									+ "<tr>"
									+ "<td> Job Name </td>"
									+ "<td>"
									+ jobName
									+ "</td>"
									+ "</tr>"
									+ "<tr>"
									+ "<td> Job Expected SLA </td>"
									+ "<td>"
									+ jobSla
									+ "</td>"
									+ "</tr>"
									+ "<tr>"
									+ "<td> Job SLA </td>"
									+ "<td>"
									+ dateOfcompletion
									+ "</td>"
									+ "</tr>"
									+ "</table>"
									+ "<br/><br/>"
									+ "</body></html>"
									+ "<br/><br/>"
									+ "<br/>Thanks,<br/>"
									+ "Skyon RWA MAINTENACE MANAGEMENT. <br/> <br/> This is Auto Generated Mail, Please Don't Revert Back"
									+"</body></html>";
						
						
					        emailCredentialsDetailsBean.setToAddressEmail(email);
							emailCredentialsDetailsBean.setMessageContent(messageContent);
							new Thread(new OpenJobNotificationMail(emailCredentialsDetailsBean)).start();
				
							smsCredentialsDetailsBean.setNumber(mobile);
							smsCredentialsDetailsBean.setUserName(ownerName);
				
							new Thread(new OpenJobNotificationSMS(smsCredentialsDetailsBean)).start();

							return "Notice Generated To " + email + " , "+ mobile;	
							
			
			}

		}
		
	}

	public static String compareTwoTimeStamps(java.sql.Timestamp currentTime, java.sql.Timestamp oldTime)
	{
		long milliseconds1 = oldTime.getTime();
		long milliseconds2 = currentTime.getTime();
		long diff = milliseconds2 - milliseconds1;
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		return diffDays+" days "+diffHours+":"+diffMinutes+":"+diffSeconds;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JobCards setParameters(JobCards jobCards,Map<String, Object> map) {
		String ids="";		
		
		List<Map<String, Object>> listAssets = (ArrayList<Map<String, Object>>) map.get("jobAssets");// this is what you have already
		for (Map<String, Object> map1 :listAssets) {
		    for (Map.Entry<String, Object> entry : map1.entrySet()) {		    	
		    	if(entry.getKey().equalsIgnoreCase("assetId")){
		    		ids+=entry.getValue()+",";
		    	}
		    }
		}
		
		jobCards.setJobAssets(ids);
		
		if(map.get("jobMt") instanceof String){
			jobCards.setMaintainanceTypes(maintainanceTypesService.find(Integer.parseInt(map.get("jobMtId").toString())));
		}else{
			jobCards.setMaintainanceTypes(maintainanceTypesService.find(Integer.parseInt(map.get("jobMt").toString())));
		}
		jobCards.setJobName((String) map.get("jobName"));
		jobCards.setJobDescription((String) map.get("jobDescription"));
		if(map.get("jobDepartment") instanceof String){
			jobCards.setJobDepartment(departmentService.find(Integer.parseInt(map.get("jobDepartmentId").toString())));
		}else{
			jobCards.setJobDepartment(departmentService.find(Integer.parseInt(map.get("jobDepartment").toString())));
		}		
		/*jobCards.setJobGroup((String) map.get("jobGroup"));*/
		jobCards.setJobExpectedSla(Integer.parseInt(map.get("jobExpectedSla").toString()));
		jobCards.setJobDt(ConvertDate.formattedDate(map.get("jobDt").toString()));
		jobCards.setSuspendStatus((String) map.get("suspendStatus"));		
		 
		if(map.get("pn_Name") instanceof String)
			jobCards.setPerson(personService.find(Integer.parseInt(map.get("personId").toString())));
		else{			
			jobCards.setPerson(personService.find(Integer.parseInt(map.get("pn_Name").toString())));	       
		}			
		
		if(map.get("jobType") instanceof String)
			jobCards.setJobTypes(jobTypesService.find(Integer.parseInt(map.get("jobTypeId").toString())));
		else
			jobCards.setJobTypes(jobTypesService.find(Integer.parseInt(map.get("jobType").toString())));
			
		jobCards.setJobPriority( (String) map.get("jobPriority"));		
		jobCards.setStatus((String) map.get("status"));
		
		if(map.get("jobNo")==null || map.get("jobNo").toString()=="" ){			
			jobCards.setJobNo(RandomStringUtils.randomAlphanumeric(7).toUpperCase());
		}else{
			jobCards.setJobNo(map.get("jobNo").toString());
		}
		jobCards.setJobGeneratedby((String) map.get("jobGeneratedby"));	
		if(map.get("expDate")!=null)
			jobCards.setExpectedDate(ConvertDate.formattedDate(map.get("expDate").toString()));		
		
		return jobCards;
	}

	@Override
	public void deleteCards(JobCards jobCards) {
		entityManager.remove(entityManager.contains(jobCards) ? jobCards : entityManager.merge(jobCards));		
	}

	@Override
	public void setsupendStatus(int jcId, String operation,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		JobCards jcard=find(jcId);
		if(!(jcard.getStatus().equalsIgnoreCase("CLOSED"))){
			if(operation.equalsIgnoreCase("SUSPEND")){
				entityManager.createNamedQuery("JobCards.UpdateSupendStatus").setParameter("Status", "RESUME").setParameter("jcId", jcId).executeUpdate();
				out.write("JOB Is Resumed");
			}else{
				entityManager.createNamedQuery("JobCards.UpdateSupendStatus").setParameter("Status", "SUSPEND").setParameter("jcId", jcId).executeUpdate();
				out.write("JOB Is Suspended");
			}
		}else{
			out.write("JOB Is Already Closed You Cannot Suspend");
		}
		
	}

	
	@Override
	public void getAllDetails(int jcId,HttpServletResponse response) throws IOException, JSONException {
				
		List<JcMaterials> jm= jcMaterialsService.readJobMaterials(jcId);
		List<JcTeam> jteam= jcTeamService.readJobTeam(jcId);
		List<JcTools> jtool= jcToolsService.readJobTools(jcId);
		List<JcLabourtasks> jl= jcLabourtasksService.readJobLabourTask(jcId);
		
		int jobLabourRate = 0;
		String totalTime="";		
		int hour=0;
		Iterator<JcLabourtasks> jlIterator=jl.iterator();
		while(jlIterator.hasNext()){
			JcLabourtasks jc=jlIterator.next();
			int rate=jc.getJclRate();
			int value=getMinutes(jc.getJclHours());
			hour=hour+value;
			jobLabourRate=jobLabourRate+(rate*value)/60;
		}
		totalTime=getWorkTime(hour);	
		
		String jobMaterials = "";
		Iterator<JcMaterials> jobMaterialsIterator=jm.iterator();		
		while(jobMaterialsIterator.hasNext()){
			JcMaterials jc=jobMaterialsIterator.next();				
			jobMaterials +=jc.getItemMaster().getImName()+",";
		}
		
		String teamSize=jteam.size()+"";
		String labourSize=jl.size()+"";
		
		String jcTools = "";
		Iterator<JcTools> jtoolIterator=jtool.iterator();
		while(jtoolIterator.hasNext()){
			JcTools jc=jtoolIterator.next();
			jcTools +=jc.getToolMaster().getTmName()+",";
		}
		
		JSONObject json = new JSONObject();		
		json.put("jobMaterials", jobMaterials);
		json.put("teamSize", teamSize);
		json.put("labourSize", labourSize);
		json.put("jcTools", jcTools);
		json.put("jobLabourRate", jobLabourRate);
		json.put("totalTime", totalTime);
		
		response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);		
		
	}

	private String getWorkTime(int totalMinutesInt) {		
        int hours = totalMinutesInt / 60;
        int hoursToDisplay = hours;

        if (hours > 12) {
            hoursToDisplay = hoursToDisplay - 12;
        }

        int minutesToDisplay = totalMinutesInt - (hours * 60);

        String minToDisplay = null;
        if(minutesToDisplay == 0 ) minToDisplay = "00";     
        else if( minutesToDisplay < 10 ) minToDisplay = "0" + minutesToDisplay ;
        else minToDisplay = "" + minutesToDisplay ;

        String displayValue = hoursToDisplay + ":" + minToDisplay;
		return displayValue;
	}

	private int getMinutes(String jclHours) {
		String strArray[]=jclHours.split(".");
		
		if(strArray.length==0){
			int hour=Integer.parseInt(jclHours)*60;
			return hour;
		}else{
			int hour=Integer.parseInt(strArray[0])*60;
			int minutes=Integer.parseInt(strArray[1]);
			return hour+minutes;
		}
		
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<?> getRequiredCardsAndMaterials(String status)
	{
		return entityManager.createNamedQuery("JobCards.getRequiredCardsAndMaterials").setParameter("status", status).getResultList();
	}

	@Override
	public void saveRecords(JobCards jcards) {
		save(jcards);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JobCards> getId(JobCalender jobCalenderId) {
		logger.info("Finding Job Cards Details");
		
	    return entityManager.createNamedQuery("JobCards.getJobCardsBasedOnId").setParameter("jobCalender",jobCalenderId).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<JobCards> findByProperty(String propertyName,final Object value) {
		logger.info("finding Metermaster instance with property: "	+ propertyName + ", value: " + value);
		
	    return entityManager.createNamedQuery("JobCards.findByProperty").setParameter("maintainanceTypesId",value).getResultList();

	}
	
	public void updateAssets(int jcId){
		
		List<JcMaterials> jm= jcMaterialsService.readJobMaterials(jcId);
		List<JcLabourtasks> jl= jcLabourtasksService.readJobLabourTask(jcId);
		
		int jobLabourRate = 0;		
		Iterator<JcLabourtasks> jlIterator=jl.iterator();
		while(jlIterator.hasNext()){
			JcLabourtasks jc=jlIterator.next();
			int rate=jc.getJclRate();
			int value=getMinutes(jc.getJclHours());			
			jobLabourRate=jobLabourRate+(rate*value)/60;
		}
			
		int materialRate=0;
		Iterator<JcMaterials> jobMaterialsIterator=jm.iterator();		
		while(jobMaterialsIterator.hasNext()){
			JcMaterials jc=jobMaterialsIterator.next();				
			materialRate +=jc.getRate();
		}	
		int totalrate=jobLabourRate+materialRate;
		
		JobCards jcard=find(jcId);
		String str=jcard.getJobAssets();
		String strArray[]=str.split(",");
		for(int i=0;i<strArray.length;i++){
			AssetMaintenanceCost amc=new AssetMaintenanceCost();
			amc.setAmcDate(new Date());
			amc.setAssetId(Integer.parseInt(strArray[i]));
			amc.setCostIncurred(totalrate);
			amc.setMcType("Maintenance");			
			assetMaintenanceCostService.save(amc);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private String sendNotifiactionAlert(Person person,int jobSla,String jobName,String jobNumber, Locale locale) throws Exception {
		String email = "";
		String mobile = "";
		String lastName="";
		if(person.getLastName()!=null)
			lastName=person.getLastName();
		String ownerName=person.getTitle()+" "+person.getFirstName()+" "+lastName;
		List<Contact> contact = entityManager.createNamedQuery("WrongParking.getContacts").setParameter("personId", person.getPersonId()).getResultList();

		if (contact.size() == 0) {						
			return "Contact Not Found For " + ownerName;
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
				return "Email Not Found For " + ownerName;
			} else if (mobile.equals("")) {				
				return "Contact Number Not Found For "+ ownerName;
			} else {
				
				
				  EmailCredentialsDetailsBean emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
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
							+ "Please be advised that the New Job Assigned to you,It Should be completed whithin Expected SLA. <br/> <br/>"
							+ "<table class=\"hovertable\">"
							+ "<tr>"
							+ "<td colspan='2'>Your Job Details are</td>"
							+ "</tr><tr></tr>"
							+ "<tr>"
							+ "<td> Job Number </td>"
							+ "<td>"
							+ jobNumber
							+ "</td>"
							+

							"</tr>"
							+ "<tr>"
							+ "<td> Job Name </td>"
							+ "<td>"
							+ jobName
							+ "</td>"
							+ "</tr>"
							+ "<tr>"
							+ "<td> Job Expected SLA </td>"
							+ "<td>"
							+ jobSla
							+ "</td>"
							+ "</tr>"
							+ "</table>"
							+ "<br/><br/>"
							+ "</body></html>"
							+ "<br/><br/>"
							+ "<br/>Thanks,<br/>"
							+ "Skyon RWA MAINTENACE MANAGEMENT. <br/> <br/> This is Auto Generated Mail, Please Don't Revert Back"
							+"</body></html>";
				
				
			        emailCredentialsDetailsBean.setToAddressEmail(email);
					emailCredentialsDetailsBean.setMessageContent(messageContent);
					new Thread(new OpenJobNotificationMail(emailCredentialsDetailsBean)).start();
		
					smsCredentialsDetailsBean.setNumber(mobile);
					smsCredentialsDetailsBean.setUserName(ownerName);
		
					new Thread(new OpenJobNotificationSMS(smsCredentialsDetailsBean)).start();

					return "Notice Generated To " + email + " , "+ mobile;
							
			}

		}
		
	}

	@Override
	public void sendAlert(JobCards jcard,final Locale locale){
		Person person=personService.find(jcard.getPersonId());
		try {
			sendNotifiactionAlert(person,jcard.getJobExpectedSla(),jcard.getJobName(),jcard.getJobNo(), locale);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<JobCards> readDataforMobi(String userId) {
		Users user=usersService.getUserInstanceByLoginName(userId);
		Person p=user.getPerson();
		logger.info("Finding Job Cards Details");
		return entityManager.createNamedQuery("JobCards.readDataforMobi").setParameter("person",p).setParameter("status", "INIT").getResultList();
	}
	
	@SuppressWarnings("unused")
	@Override
	public List<?> readDataforMobile(String userId) {
		logger.info("Job Cards Reading Records");
		List<Map<String, Object>> jobCards = new ArrayList<Map<String, Object>>();
		for (final JobCards record :readDataforMobi(userId)) {
			jobCards.add(new HashMap<String, Object>() {				
				private static final long serialVersionUID = 1L;				{				

					
					put("jcId", record.getJcId());
					put("jobGeneratedby", record.getJobGeneratedby());
					put("jobNo", record.getJobNo());
					put("jobName", record.getJobName());
					put("jobGroup", record.getJobGroup());
					put("jobDescription", record.getJobDescription());					
					put("jobDt", ConvertDate.TimeStampString(record.getJobDt()));					
					put("jobDepartment", record.getJobDepartment().getDept_Name());
					put("jobDepartmentId", record.getJobDepartment().getDept_Id());
					put("jobExpectedSla", record.getJobExpectedSla());					
					String str=record.getJobSla()+"";
					if(str=="")
						put("jobSla", "");
					put("jobSla", record.getJobSla());
					put("jobMtId", record.getMaintainanceTypes().getMtId());
					put("jobMt", record.getMaintainanceTypes().getMaintainanceType());
					if(record.getJobStartDt()!=null)
						put("jobStartDt",ConvertDate.TimeStampString(record.getJobStartDt()));
					if(record.getJobEndDt()!=null)
						put("jobEndDt",ConvertDate.TimeStampString(record.getJobEndDt()));	
					List<Map<String,Object>> assetList = new ArrayList<Map<String,Object>>();					
					if(record.getJobAssets()!=null){
						String assets[]=record.getJobAssets().split(",");
						Map<String,Object> mapobject = null;
						for (String value : assets) {
							mapobject = new HashMap<String,Object>();
							try{
								Asset a= assetService.find(Integer.parseInt(value));
								mapobject.put("assetName", a.getAssetName());
								mapobject.put("assetId", a.getAssetId());
								assetList.add(mapobject);
							}catch(Exception e){

							  }
							
						}						

					}					
					put("jobAssets",assetList);	
					put("jobAssetsDummy",assetList);						
					String str2="";
					if(record.getPerson().getLastName()!=null)
						str2=record.getPerson().getLastName();
					
					Map<String,Object> mapjobOwner=new HashMap<String,Object>();
					mapjobOwner.put("pn_Name", record.getPerson().getFirstName()+" "+record.getPerson().getLastName());

					mapjobOwner.put("personId", record.getPerson().getPersonId());
					put("pn_Name", mapjobOwner);
					
					put("jobCcId", record.getJobCcId());
					put("jobBmsId", record.getJobBmsId());
					put("jobAmsId", record.getJobAmsId());					
					put("jobType", record.getJobTypes().getJtType());
					put("jobTypeId", record.getJobTypes().getJtId());
					put("jobPriority", record.getJobPriority());					
					put("status",record.getStatus());					
					put("suspendStatus",record.getSuspendStatus());					
					put("createdBy", record.getCreatedBy());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					if(record.getExpectedDate()!=null)
						put("expDate", ConvertDate.TimeStampString(record.getExpectedDate()));
					put("lastUpdatedDate",ConvertDate.TimeStampString(record.getLastUpdatedDt()));
					if(record.getJobCalender()!=null){
						put("jobCalender",record.getJobCalender().getJobCalenderId());

					}

				}
			});

		}
		return jobCards;		

	}

	@Override
	public void deleteJobCard(int jobCardId) {
		entityManager.createNamedQuery("JobCards.deleteJobCard").setParameter("jobCardId", jobCardId).executeUpdate();

		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> readAllJobCards() {
		return entityManager.createNamedQuery("JobCards.findAllList").getResultList();
	}
	
	
}