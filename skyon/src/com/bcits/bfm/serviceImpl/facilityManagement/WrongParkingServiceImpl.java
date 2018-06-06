package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.PrintWriter;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Vehicles;
import com.bcits.bfm.model.WrongParking;
import com.bcits.bfm.service.facilityManagement.VehicleService;
import com.bcits.bfm.service.facilityManagement.WrongParkingService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.EmailCredentialsDetailsBean;
import com.bcits.bfm.util.HelpDeskMailSender;
import com.bcits.bfm.util.OpenJobNotificationSMS;
import com.bcits.bfm.util.SmsCredentialsDetailsBean;
import com.bcits.bfm.util.WrongParkingNoticeMail;
import com.bcits.bfm.util.WrongParkingSMS;

/**
 * @author Manjunath Kotagi
 *
 */
@Repository
public class WrongParkingServiceImpl extends GenericServiceImpl<WrongParking>
		implements WrongParkingService {

	private static final Log logger = LogFactory.getLog(WrongParkingServiceImpl.class);

	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private MessageSource messageSource;

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.WrongParkingService#findAll()
	 * 
	 * Finding All Wrong Parking details
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<WrongParking> findAll() {
		logger.info("finding all WrongParking instances");
		try {
			return entityManager.createNamedQuery("WrongParking.findAll").getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.WrongParkingService#getTagNumbers(java.lang.String)
	 * 
	 * returns Tag Number
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String getTagNumbers(String str) {
		List al = entityManager.createNamedQuery("WrongParking.getTagNumbers")
				.setParameter("RegNo", str).getResultList();

		if (al.size() == 0) {
			return "FAIL";
		} else {
			return al.get(0).toString();
		}
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.WrongParkingService#freeAllocation(com.bcits.bfm.model.WrongParking)
	 * 
	 * Deleteing Wrong Parking
	 */
	@Override
	public void freeAllocation(WrongParking find) {
		entityManager.remove(entityManager.contains(find) ? find
				: entityManager.merge(find));

	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.WrongParkingService#setWrongParkingStatus(int, java.lang.String, javax.servlet.http.HttpServletResponse, java.util.Locale)
	 * 
	 * Updating Wrong Parking Status
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setWrongParkingStatus(int wpId, String operation,
			HttpServletResponse response, final Locale locale) {

		WrongParking wrongParking = find(wpId);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String string=wrongParking.getVehicles().getPerson().getLastName();
			String name="";
			if(string!=null)
				name=string;
			else
				name=wrongParking.getVehicles().getPerson().getFirstName();
			String ownerName = wrongParking.getVehicles().getPerson().getTitle()+ " "+ name;
			String parkedSlot = wrongParking.getPsSlotNo();
			String allotedSlot = wrongParking.getVehicles().getValidSlotsNo();
			String dateOfParking = wrongParking.getwpDt().toString();
			String vehicleRegno = wrongParking.getVehicles().getVhRegistrationNo();
			String actionTaken=wrongParking.getActionTaken();

			Person person = wrongParking.getVehicles().getPerson();
			int personId = person.getPersonId();

			String email = "";
			String mobile = "";

			List<Contact> contact = entityManager.createNamedQuery("WrongParking.getContacts").setParameter("personId", personId).getResultList();

			if (contact.size() == 0) {
				String str= person.getLastName();
				String lastName="";
				if(str!=null)
					lastName=str;					
				out.write("Contact Not Found For " + person.getFirstName()+ " " +lastName);
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
					String str= person.getLastName();
					String lastName="";
					if(str!=null)
						lastName=str;
					out.write("Email Not Found For " + person.getFirstName()+ " " + lastName);
				} else if (mobile.equals("")) {
					String str= person.getLastName();
					String lastName="";
					if(str!=null)
						lastName=str;
					out.write("Contact Number Not Found For "+ person.getFirstName() + " "+ lastName);
				} else {
					if (operation.equalsIgnoreCase("generateNotice")) {
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
						String formattedDate = sdf2.format(sdf1.parse(dateOfParking));
						String gateWayUserName = messageSource.getMessage("SMS.users.username", null, locale);
						String gateWayPassword = messageSource.getMessage("SMS.users.password", null, locale);
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
									+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon RWA SECURITY MANAGEMENT.</h2> <br/><br /> Dear "
									+ ownerName
									+ ",<br/> <br/> "
									+ "Please be advised that your Vehicle was found Unauthorizedly parked on reserved parking area. <br/> <br/>"
									+ "<table>"
									+ "<tr>"
									+ "<td colspan='2'>Your Parking Details are</td>"
									+ "</tr>"
									+ "<tr>"
									+ "<td> Registration Number </td>"
									+ "<td>"
									+ vehicleRegno
									+ "</td>"
									+

									"</tr>"
									+ "<tr>"
									+ "<td> Date of Parking </td>"
									+ "<td>"
									+ dateOfParking
									+ "</td>"
									+ "</tr>"
									+ "<tr>"
									+ "<td> Allocated Slot </td>"
									+ "<td>"
									+ allotedSlot
									+ "</td>"
									+ "</tr>"
									+ "<tr>"
									+ "<td> Parked Slot </td>"
									+ "<td>"
									+ parkedSlot
									+ "</td>"
									+ "</tr>"
									+ "<tr>"
									+ "<td> Action Taken </td>"
									+ "<td>"
									+ actionTaken
									+ "</td>"
									+ "</tr>"
									+ "</table>"
									+ "<br/><br/>"
									+ "Parking in this Skyon RWA COMMUNITY is allowed only in the parking slots allotted to you.Identification of your vehicle has been noted and if it is found to be parked illegally in the future,It will be towed without further notice at your expense.</body></html>"
									+ "<br/><br/>"
									+ "<br/>Thanks,<br/>"
									+ "SKYON RWA SECURITY MANAGEMENT. <br/> <br/> This is Auto Generated Mail, Please Don't Revert Back,"
							+"text/html; charset=ISO-8859-1";
					      
					      emailCredentialsDetailsBean.setToAddressEmail(email);
						  emailCredentialsDetailsBean.setMessageContent(messageContent);
						  new Thread(new WrongParkingNoticeMail(emailCredentialsDetailsBean)).start();
						//new Thread(new WrongParkingNoticeMail(email, ownerName,formattedDate, vehicleRegno, allotedSlot,parkedSlot,actionTaken)).start();
						 
						  String messageDepartment="Dear "+ownerName+",Please Be advised that your Vehicle was found Unauthorizedly parked on reserved parking area.Your Parking Details are....Registration Number : "+vehicleRegno+", Date of Parking : "+formattedDate+", Allocated Slot : "+allotedSlot+", Parked Slot : "+parkedSlot+", Action Taken : "+actionTaken+"...  (Skyon RWA SECURITY MANAGEMENT)";
						  smsCredentialsDetailsBean.setNumber(mobile);
						  smsCredentialsDetailsBean.setUserName(ownerName);
						  smsCredentialsDetailsBean.setMessage(messageDepartment);
						new Thread(new WrongParkingSMS(smsCredentialsDetailsBean)).start();
						
						entityManager.createNamedQuery("WrongParking.UpdateStatus").setParameter("wpStatus", "YES").setParameter("wpId", wpId).executeUpdate();						
						out.write("Notice Generated To " + email + " , "+ mobile);
					} else {
						out.write("Already Notice Generated To " + email+ " , " + mobile);
					}
				}

			}

		} catch (NullPointerException e) {
			out.write("Owner For Vehicle Not Found");
		} catch (Exception e) {
			e.printStackTrace();
			out.write("Unable to Generate Notice");
		}

	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.WrongParkingService#getBeanObject(java.util.Map)
	 * 
	 * Creating Wrong Parking Object
	 */
	@Override
	public WrongParking getBeanObject(Map<String, Object> map) {

		WrongParking wp = new WrongParking();
		String vregNumber = (String) map.get("vehicleNo");
		wp.setVehicleNo(vregNumber.toUpperCase());
		List<Vehicles> veh = new ArrayList<Vehicles>();
		if (map.get("vhTagNoWrong") != null) {
			veh = vehicleService.getVehicleId(((String) map.get("vhTagNoWrong")).toUpperCase());
			wp.setVhTagNo(((String) map.get("vhTagNoWrong")).toUpperCase());
		}
		if (veh.size() != 0) {
			wp.setVehicles(veh.get(0));
		}
		wp.setStatus("NO");
		wp.setActionTaken((String) map.get("actionTaken"));
		if (!(map.get("psSlotNo") instanceof String)) {
			@SuppressWarnings("unchecked")
			Map<String, String> a = (Map<String, String>) map.get("psSlotNo");
			wp.setPsSlotNo(a.get("psSlotNo"));
		} else
			wp.setPsSlotNo((String) map.get("psSlotNo"));
		wp.setwpDt(ConvertDate.formattedDate((String) map.get("wpDt")));
		return wp;
	}

	@Override
	public void uploadImage(int wpId, Blob blob) {
		entityManager.createNamedQuery("WrongParking.updateImage").setParameter("wpId", wpId).setParameter("image",blob).executeUpdate();
		
	}

	@Override
	public List<?> findAllList() {
		return entityManager.createNamedQuery("WrongParking.findAllList").getResultList();

	}
}