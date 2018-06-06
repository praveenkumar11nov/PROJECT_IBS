package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.CarMake;
import com.bcits.bfm.model.CarModel;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.ParkingSlots;
import com.bcits.bfm.model.ParkingSlotsAllotment;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.model.Vehicles;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.facilityManagement.CarMakeService;
import com.bcits.bfm.service.facilityManagement.CarModelService;
import com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService;
import com.bcits.bfm.service.facilityManagement.ParkingSlotsService;
import com.bcits.bfm.service.facilityManagement.VehicleService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

/**
 * @author Manjunath Kotagi
 *
 */
@Repository
public class VehiclesServiceImpl extends GenericServiceImpl<Vehicles> implements
		VehicleService {
	
	@Autowired
	private ParkingSlotsAllotmentService parkingSlotsAllotmentService;

	private static final Log logger = LogFactory.getLog(VehiclesServiceImpl.class);

	@Autowired
	private PersonService personService;
	@Autowired
	private ParkingSlotsService parkingSlotsService;
	@Autowired
	private CarMakeService carMakeService;
	@Autowired
	private CarModelService carModelService;
	@Autowired
	private PropertyService propertyService;

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.VehicleService#findAll()
	 * 
	 * Finding all Vehicle Details
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Vehicles> findAll() {
		logger.info("Finding Vehicles Details Started");
		try {
			return entityManager.createNamedQuery("Vehicle.findAll").getResultList();
		} catch (RuntimeException re) {
			logger.error("Find Vehicles Details Failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.VehicleService#getBeanObject(java.util.Map)
	 * 
	 * Creating Vehicle Object
	 */
	@SuppressWarnings("unused")
	@Override
	public Vehicles getBeanObject(Map<String, Object> map) {
		logger.info("Vehicles Details Set Started");
		CarMake carMake=null;
		CarModel carModel=null;	
		Vehicles vehicles = new Vehicles();
		if(map.get("vhMakeOther")!="" && map.get("vhMakeOther")!=null){
			carMake=new CarMake();
			String str=map.get("vhMakeOther").toString();
			String output = Character.toUpperCase(str.charAt(0))+(str.substring(1,str.length())).toLowerCase();
			carMake.setCarName(output);
			vehicles.setVhMake(output);
			carMakeService.save(carMake);			
		}if(map.get("vhModelOther")!="" && map.get("vhModelOther")!=null ){
			carModel=new CarModel();
			String str=map.get("vhModelOther").toString();
			String output = Character.toUpperCase(str.charAt(0))+(str.substring(1,str.length())).toLowerCase();
			carModel.setModelName(output);
			vehicles.setVhModel(output);
			if(carMake!=null){
			
				carModel.setCarMake(carMake);
				carModelService.save(carModel);
				
				CarModel carModel1=new CarModel();
				carModel1.setModelName("Others");
				carModel1.setCarMake(carMake);
				carModelService.save(carModel1);
			}else{
			
				CarMake vhMake=null;
				if (map.get("vhMake") instanceof String) {
					vhMake = carMakeService.findbyName(map.get("vhMake").toString());
				} else {
					 vhMake = carMakeService.find(Integer.parseInt(map.get("vhMake").toString()));
					
				}				
				carModel.setCarMake(vhMake);
				carModelService.save(carModel);
				
				CarModel carModel1=new CarModel();
				CarMake vhMake1=null;
				if (map.get("vhMake") instanceof String) {
					 vhMake1 = carMakeService.findbyName(map.get("vhMake").toString());
				} else {					
					vhMake1 = carMakeService.find(Integer.parseInt(map.get("vhMake").toString()));					
				}				
				carModel1.setCarMake(vhMake1);
				carModel1.setModelName("Others");
				carModelService.save(carModel);	
				
			}			
			
		}	
		
		if(map.get("vhMakeOther")=="" || map.get("vhMakeOther")==null){
			if (map.get("vhMake") instanceof String) {
				vehicles.setVhMake((String) map.get("vhMake"));
			} else {
				CarMake vhMake = carMakeService.find(Integer.parseInt(map.get("vhMake").toString()));
				vehicles.setVhMake(vhMake.getCarName());
			}
		}
		if(map.get("vhModelOther")=="" || map.get("vhModelOther")==null ){
			if (map.get("vhModel") instanceof String) {
				vehicles.setVhModel((String) map.get("vhModel"));
			} else {
				CarModel modelName = carModelService.find(Integer.parseInt(map.get("vhModel").toString()));
				vehicles.setVhModel(modelName.getModelName());
			}
		}		
		
		if (map.get("owner") instanceof String) {
			vehicles.setPerson(personService.find(Integer.parseInt(map.get("Idowner").toString())));
		} else {
			vehicles.setPerson(personService.find(Integer.parseInt(map.get("owner").toString())));
		}
		
		Property propertyid=null;
		if (map.get("property") instanceof String) {
			vehicles.setProperty(propertyService.find(Integer.parseInt(map.get("propertyId").toString())));
		} else {
			vehicles.setProperty(propertyService.find(Integer.parseInt(map.get("property").toString())));
		}
		

		logger.info("==================================Finding  property id"+map.get("propertyId"));
		logger.info("==================================Finding  property id"+map.get("Idowner"));
		logger.info("==================================Finding  property id"+map.get("property"));
		
		if (map.get("property") instanceof String) {
			
			 propertyid = propertyService.find(Integer.parseInt(map.get("propertyId").toString()));
		} else {
			 propertyid = propertyService.find(Integer.parseInt(map.get("property").toString()));
		}
		
	  
		//Object property=map.get("propertyId");
		String id = "";
		String no="";
	List<ParkingSlotsAllotment> parkingSlotsAllotments=parkingSlotsAllotmentService.executeSimpleQuery("SELECT p FROM ParkingSlotsAllotment p WHERE p.property='"+propertyid.getPropertyId()+"'");
		
		//int  id=parkingSlotsAllotments.get(0).getParkingSlots().getPsId();
		for (ParkingSlotsAllotment parkingSlotsAllotment : parkingSlotsAllotments) {
			id+=parkingSlotsAllotment.getParkingSlots().getPsId()+",";
			
			List<ParkingSlots> parkingSlots=parkingSlotsService.executeSimpleQuery("SELECT p FROM ParkingSlots p WHERE p.psId='"+parkingSlotsAllotment.getParkingSlots().getPsId()+"'");
			for (ParkingSlots parkingSlots2 : parkingSlots) {
				 no+=parkingSlots2.getPsSlotNo()+"    "+","+"   ";
				
		if (!(map.get("validSlotsNo").equals("")))
			vehicles.setValidSlotsNo((String) map.get("validSlotsNo"));
		else
			vehicles.setValidSlotsNo(String.valueOf(no));
			}
		}
		
		if (!(map.get("vhEndDate") == null))
			vehicles.setVhEndDate(ConvertDate.formattedDate(map.get("vhEndDate").toString()));	
		
		vehicles.setSlotType((String) map.get("slotType"));
		vehicles.setVhRegistrationNo(((String) map.get("vhRegistrationNo")).toUpperCase());
		vehicles.setVhStartDate(ConvertDate.formattedDate(map.get("vhStartDate").toString()));
		vehicles.setVhTagNo(((String) map.get("vhTagNo")).toUpperCase());
		vehicles.setStatus(map.get("status").toString());
		logger.info("Vehicles Details Set Completed");
		return vehicles;
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.VehicleService#addVehicles(com.bcits.bfm.model.Vehicles)
	 * 
	 * Cretaing Vehicle Record
	 */
	@Override
	public Vehicles addVehicles(Vehicles vehicles) {
		entityManager.persist(vehicles);
		logger.info("Vehicles Details Saved");
		return vehicles;
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.VehicleService#setvehicleStatus(int, java.lang.String, javax.servlet.http.HttpServletResponse)
	 * 
	 * Updating Vehicle Status
	 */
	@Override
	public void setvehicleStatus(int vhId, String status,
			HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			if (status.equalsIgnoreCase("activate")) {
				entityManager.createNamedQuery("Vehicles.UpdateStatus")
						.setParameter("Status", "true")
						.setParameter("vhId", vhId).executeUpdate();
				logger.info("Vehicles Status(Activate) Updated");
				out.write("Slot Activated for Vehicle");
			} else {
				entityManager.createNamedQuery("Vehicles.UpdateStatus")
						.setParameter("Status", "false")
						.setParameter("vhId", vhId).executeUpdate();
				logger.info("Vehicles Status(Deactivate) Updated");
				out.write("Slot De-Activated for Vehicle");
			}
		} catch (IOException e) {
			logger.info("Vehicles Status Updation Exception");
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.VehicleService#getVehicleId(java.lang.String)
	 * 
	 * Finding Vehicle Details based on tag no
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Vehicles> getVehicleId(String vhTagNo) {
		logger.info("Finding Vehicles Details Based on Tag No.. Started");
		List<Vehicles> al = entityManager
				.createNamedQuery("Vehicle.getBasedOnTagNumber")
				.setParameter("tagno", vhTagNo).getResultList();
		return al;
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.VehicleService#getallVehicles(int)
	 * 
	 * Finding Vehicle Details based on property id
	 */
	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	public List<?> getallVehicles(int propertyId) {
		final String queryString = "select model from Vehicles model where model.status = :status and model.property=:propertyId";
		Query query = entityManager.createQuery(queryString);
		Property p=propertyService.find(propertyId);
		query.setParameter("propertyId", p);	
		query.setParameter("status", "true");	
		List<Vehicles> veh=query.getResultList();			
		List<Map<String, Object>> vehicles = new ArrayList<Map<String, Object>>();
		for (final Vehicles record : veh) {
			vehicles.add(new HashMap<String, Object>() {
				{
					String str="";
					if(record.getPerson().getLastName()!=null){
						str=record.getPerson().getLastName();
					}
					put("vhId", record.getVhId());
					put("owner", record.getPerson().getFirstName() + " "+str );	
					
					String mobileNumber = "";
					List<Contact> contactList  = entityManager.createNamedQuery("Property.getPersonContactDetails").setParameter("personId", record.getPerson().getPersonId()).getResultList();
					if(contactList.size() > 0 && StringUtils.equalsIgnoreCase(contactList.get(0).getContactPrimary(), "Yes"))
					{
						for(Contact c :contactList)
						{
							mobileNumber = mobileNumber +"/"+ c.getContactContent();
						}
						
					}
					mobileNumber = mobileNumber.startsWith("/") ? mobileNumber.substring(1) : mobileNumber;
					if(mobileNumber == "")
					{
						mobileNumber = "No contact details";
					}

					put("contact",mobileNumber);					
					put("vhRegistrationNo", record.getVhRegistrationNo());
					put("vhMake", record.getVhMake());
					put("vhModel", record.getVhModel());
					put("vhTagNo", record.getVhTagNo());					
					put("slotType", record.getSlotType());
					put("validSlotsNo", record.getValidSlotsNo());
					put("property", record.getProperty().getProperty_No());					
				}
			});
		}
		return vehicles;
		
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.service.facilityManagement.VehicleService#getContacts(int)
	 * 
	 * finding person Contact
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getContacts(int personId) {
		String mobile="";
		String email="";
		List<Contact> contact = entityManager.createNamedQuery("WrongParking.getContacts").setParameter("personId", personId).getResultList();
		if (contact.size() == 0) {
			mobile="Not Found";
			email="Not Found";
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
				email="Not Found";
			} else if (mobile.equals("")) {
				mobile="Not Found";
			}
		}
		return mobile+":"+email;
	}

	@Override
	public List<?> findAllVehicals() {
		logger.info("Finding Vehicles Details Started");
		try {
			return entityManager.createNamedQuery("Vehicle.findAllVehicals").getResultList();
		} catch (RuntimeException re) {
			logger.error("Find Vehicles Details Failed", re);
			throw re;
		}
	}

}