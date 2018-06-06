package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Address;
import com.bcits.bfm.model.City;
import com.bcits.bfm.model.Country;
import com.bcits.bfm.model.State;
import com.bcits.bfm.service.customerOccupancyManagement.AddressService;
import com.bcits.bfm.service.customerOccupancyManagement.CityService;
import com.bcits.bfm.service.customerOccupancyManagement.CountryService;
import com.bcits.bfm.service.customerOccupancyManagement.StateService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.serviceImpl.facilityManagement.StaffTrainingServiceImpl;
import com.bcits.bfm.util.BfmLogger;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;

/**
 * A data access object (DAO) providing persistence and search support for
 * Address entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 */
@Repository
public class AddressServiceImpl extends GenericServiceImpl<Address> implements AddressService{

	DateTimeCalender dateTimeCalender = new DateTimeCalender(); 

	@Autowired
	private CountryService countryService;

	@Autowired
	private StateService stateService;

	@Autowired
	private CityService cityService;
	
	private static final Log logger = LogFactory.getLog(StaffTrainingServiceImpl.class);

	/** Read All the address for the person
	 * @param personId 		Person Id
	 * @see 				com.bcits.bfm.serviceImpl.customerOccupancyManagement.AddressService#findAll()
	 * @return 				List of Address
	 */
	@Override
	@SuppressWarnings({ "rawtypes" })
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> findAllAddressesBasedOnPersonID(int personId) {
		BfmLogger.logger.info("finding all Address instances based on personId");
		try {
			List addressesList = entityManager.createNamedQuery("Address.findAllAddressesBasedOnPersonID").setParameter("personId", personId).getResultList();
			return getAllAddressRequiredFilds(addressesList);
		} catch (RuntimeException re) {
			BfmLogger.logger.error("find all failed", re);
			throw re;
		}
	}

	/** Read AddressId Based On Created By And LastUpdatedDt
	 * @param createdBy 		Created By
	 * @param lastUpdatedDt		Last Updated Date 
	 * @see com.bcits.bfm.service.customerOccupancyManagement.AddressService#getAddressIdBasedOnCreatedByAndLastUpdatedDt(java.lang.String, java.sql.Timestamp)
	 * @return Address Id
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Integer getAddressIdBasedOnCreatedByAndLastUpdatedDt(String createdBy, Timestamp lastUpdatedDt){
		return entityManager.createNamedQuery("Address.getAddressIdBasedOnCreatedByAndLastUpdatedDt", Integer.class).setParameter("createdBy", createdBy).setParameter("lastUpdatedDt", lastUpdatedDt).getSingleResult();		
	}

	/** Set the information from the view in to the object
	 * @param	map			Address details from the view
	 * @param	type		Save or Update
	 * @param	address		Address Object 
	 * @see com.bcits.bfm.service.customerOccupancyManagement.AddressService#getAddressObject(java.util.Map, java.lang.String, com.bcits.bfm.model.Address)
	 * @return 	Address Object
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Address getAddressObject(Map<String, Object> map, String type,
			Address address)
	{
		if(type == "update")
		{
			address.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
			address.setAddressId((Integer) map.get("addressId"));
			address.setPersonId((Integer) map.get("personId"));
			address.setCreatedBy((String) map.get("createdBy"));
			if(map.get("pincode") instanceof String)
				address.setPincode(Integer.parseInt((String) map.get("pincode").toString().trim()));
			if(map.get("pincode") instanceof Integer)
				address.setPincode((Integer) map.get("pincode"));
			
		}
		else if (type == "save")
		{
			logger.info("inside a Save-------- "+map.get("stateName1")+"--Country Name-----"+map.get("countryName1"));
			address.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
			address.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
			if(map.get("pincode") instanceof String)
				address.setPincode(Integer.parseInt((String) map.get("pincode").toString().trim()));
			if(map.get("pincode") instanceof Integer)
				address.setPincode((Integer) map.get("pincode"));
		}

		if(!StringUtils.isEmpty((String) map.get("address1")) && (String) map.get("address1") != null)
		address.setAddress1(((String) map.get("address1")).trim());
		
		if(!StringUtils.isEmpty((String) map.get("address2")) && (String) map.get("address2") != null)
		address.setAddress2(((String) map.get("address2")).trim());
		
		if(!StringUtils.isEmpty((String) map.get("address3")) && (String) map.get("address3") != null)
		address.setAddress3(((String) map.get("address3")).trim());
		address.setAddressContactId((Integer) map.get("addressContactId"));
		address.setAddressLocation((String) map.get("addressLocation"));
		address.setAddressPrimary((String) map.get("addressPrimary"));


		if(map.get("addressSeason") instanceof java.lang.String || map.get("addressSeason") == null){
			logger.info("Do Nothing");
		}
		else {
			if((boolean)map.get("addressSeason")){
				address.setAddressSeason("Yes");
				address.setAddressSeasonFrom(ConvertDate.formattedDate(map.get("addressSeasonFrom").toString()));
				address.setAddressSeasonTo(ConvertDate.formattedDate(map.get("addressSeasonTo").toString()));
			}
			else{
				address.setAddressSeason("No");
			}
		}
		List<State> stateli=null;
		if(map.get("stateId")!=""){
			stateli=stateService.findNamebyId((Integer)map.get("stateId"));
		}

		String stateName="";
		if(stateli!=null){
			stateName=stateli.get(0).getStateName();
		}

		List<City> cityli=null;
		if(map.get("cityId")!=""){
			cityli=cityService.findNamebyId((Integer)map.get("cityId"));
		}

		String cityName="";
		if(cityli!=null){
			cityName=cityli.get(0).getCityName();
		}	

		if(!(map.get("countryId") instanceof String) && (Integer)map.get("countryId") == 0 ){
			Country c=new Country();
			c.setCountryName((String)map.get("countryotherId"));
			countryService.save(c);
			List<Country> list=countryService.findIdbyName((String)map.get("countryotherId"));

			State s=new State();
			s.setStateName((String)map.get("stateotherId"));
			s.setCountryId(list.get(0).getCountryId());
			stateService.save(s);

			State s2=new State();
			s2.setStateId(0);
			s2.setStateName("Other");
			s2.setCountryId(list.get(0).getCountryId());
			stateService.save(s2);

			List<State> statelist=stateService.findIdbyName((String)map.get("stateotherId"));

			City city=new City();
			city.setCityName((String)map.get("cityotherId"));
			city.setStateId(statelist.get(0).getStateId());
			cityService.save(city);

			City city2=new City();
			city2.setCityName("Other");
			city2.setStateId(statelist.get(0).getStateId());
			cityService.save(city2);
			address.setCityId(cityService.getIdBasedOnCityName((String)map.get("cityotherId")));
		}
		else if(stateName.equalsIgnoreCase("Other")){

			State s=new State();
			s.setStateName((String)map.get("stateotherId"));
			s.setCountryId((Integer)map.get("countryId"));
			stateService.save(s);

			List<State> statelist=stateService.findIdbyName((String)map.get("stateotherId"));

			City city=new City();
			city.setCityName((String)map.get("cityotherId"));
			city.setStateId(statelist.get(0).getStateId());
			cityService.save(city);

			City city2=new City();
			city2.setCityName("Other");
			city2.setStateId(statelist.get(0).getStateId());
			cityService.save(city2);
			address.setCityId(cityService.getIdBasedOnCityName((String)map.get("cityotherId")));
		}
		else if(cityName.equalsIgnoreCase("Other")){
			City city=new City();
			city.setCityName((String)map.get("cityotherId"));
			city.setStateId((Integer)map.get("stateId"));
			cityService.save(city);
			address.setCityId(cityService.getIdBasedOnCityName((String)map.get("cityotherId")));
		}
		else if(map.get("countryId") != "" && map.get("stateId") != "" && map.get("cityId") != ""){
			if(map.get("stateId") instanceof java.lang.String){
				stateService.save(new State((String)map.get("stateId"), (Integer)map.get("countryId")));
				map.put("stateId", stateService.getIdBasedOnStateName((String)map.get("stateId")));
			}

			if(map.get("cityId") instanceof java.lang.String){
				cityService.save(new City((String)map.get("cityId"), (Integer)map.get("stateId")));
				address.setCityId(cityService.getIdBasedOnCityName((String)map.get("cityId")));
			}
			else
				address.setCityId((int) map.get("cityId"));

		}
		else{
			logger.info("Invalid Entry");
		}
		address.setLastUpdatedDt(new Timestamp(new Date().getTime()));
		address.setAddressNo((String) map.get("addressNo"));
		return address;
	}

	/** Read all the required field of address
	 * @param addressesList List of address
	 * @return Selected(Required) fields to the view
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(propagation = Propagation.SUPPORTS)
	private List<?> getAllAddressRequiredFilds(List addressesList)
	{
		List<Map<String, Object>> addresses =  new ArrayList<Map<String, Object>>();
		for (Iterator i = addressesList.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();
			Map<String, Object> addressMap = new HashMap<String, Object>();	
			addressMap.put("addressId", (Integer)values[0]);
			addressMap.put("personId", (Integer)values[1]);
			addressMap.put("addressLocation", (String)values[2]);
			addressMap.put("addressPrimary", (String)values[3]);
			addressMap.put("addressNo", (String)values[4]);
			addressMap.put("address1", (String)values[5]);
			addressMap.put("address2", (String)values[6]);
			addressMap.put("address3", (String)values[7]);
			addressMap.put("pincode", (Integer)values[8]);
			addressMap.put("addressContactId", (Integer)values[9]);
			StringBuilder sb = new StringBuilder();
			sb.append((String)values[4]+", "+ (String)values[5]);
			if((String)values[6] != null)
				sb.append(", "+(String)values[6]);
			if((String)values[7] != null)
				sb.append(","+(String)values[7]);
			addressMap.put("address",sb.toString());
			
			if((Date)values[11]!=null && (Date)values[12]!=null){
				addressMap.put("addressSeason", true);
			}
			else{
				addressMap.put("addressSeason", false);
			}
			if(values[11]!=null)
				addressMap.put("addressSeasonFrom", ConvertDate.TimeStampString((Timestamp)values[11]));
			if(values[12]!=null)
				addressMap.put("addressSeasonTo", ConvertDate.TimeStampString((Timestamp)values[12]));
			addressMap.put("createdBy", (String)values[13]);
			addressMap.put("lastUpdatedBy", (String)values[14]);
			addressMap.put("lastUpdatedDt", (Date)values[15]);
			addressMap.put("cityId", (Integer)values[16]);
			addressMap.put("cityName", (String)values[17]);
			addressMap.put("stateId", (Integer)values[18]);
			addressMap.put("stateName", (String)values[19]);
			addressMap.put("countryId", (Integer)values[20]);
			addressMap.put("countryName", (String)values[21]);
			addresses.add(addressMap);
		}	
		return addresses;
	}

	/** Update contact Id
	 * @param addressId 	Address Id
	 * @param contactId 	Contact Id
	 * @return none
	 */
	@Override
	public void updateContactId(int addressId, int contactId) {
		entityManager.createNamedQuery("Address.updateContactId").setParameter("addressId", addressId).setParameter("contactId", contactId).executeUpdate();
	}

	
	
		
	/** Finding all Address instances based on personId, Location and Primary 
	 * @param addrPrimary 		Address Primary
	 * @param personId			Person ID
	 * @return	List of Address
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Address> checkForUniquePrimary(int personId, String addrPrimary) {
		try {
			return entityManager.createNamedQuery("Address.checkForUniquePrimary").setParameter("personId", personId).setParameter("addrPrimary", addrPrimary).getResultList();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/** Finding Primary instances based on AddressId
	 * @param addressId 	Address Id
	 * @see com.bcits.bfm.service.customerOccupancyManagement.AddressService#getAddressPrimary(int)
	 * @return String consists of Address Primary
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getAddressPrimary(int addressId) {
		
			try {
				return entityManager.createNamedQuery("Address.getAddressPrimary",String.class).setParameter("addressId", addressId).getSingleResult();
				
			} catch (RuntimeException re) {
				BfmLogger.logger.error("find all failed", re);
				throw re;
			}
	}
}