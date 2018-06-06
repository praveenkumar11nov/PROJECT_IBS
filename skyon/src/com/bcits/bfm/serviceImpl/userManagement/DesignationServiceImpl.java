package com.bcits.bfm.serviceImpl.userManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Department;
import com.bcits.bfm.model.Designation;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.service.userManagement.DesignationService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.SessionData;

@Repository
public class DesignationServiceImpl extends GenericServiceImpl<Designation>	implements DesignationService {

	private static final Log logger = LogFactory.getLog(DesignationServiceImpl.class);	
	
	@Autowired
	private UsersService usersService;
	@Autowired
	private DepartmentService departmentService;
	
	Designation obj = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bcits.bfm.serviceImpl.userManagement.DesignationService#get(int)
	 */
	@Override
	public Designation get(int id) {
		return entityManager.find(Designation.class, id);
	}

	/**  
     * this method is used to find all  record of designation from the database
     * @return         : returns all records of designation
     * @since           1.0
     */
	@Override
	@SuppressWarnings("unchecked")
	public List<Designation> getAll() {
		return entityManager.createNamedQuery("Designation.findAll").getResultList();
	}

	/** 
     * this method is used to save the   record of designation and  to store in database
     * @return         : returns created record of designation
     * @since           1.0
     */
	@Override
	public void saveList(List<Designation> designations) {

		for (Designation designation : designations) {
			entityManager.persist(designation);
			;
		}
		entityManager.flush();
	}

	/** 
     * this method is used to update the   record of designation and  to store in database
     * @return         : returns updated record of designation
     * @since           1.0
     */
	@Override
	public void updateList(List<Designation> designations) {
		for (Designation designation : designations) {
			entityManager.merge(designation);			
		}
		entityManager.flush();
	}

	/** 
     * this method is used to delete the   record of designation and  to store in database
     * @return         : returns deleted Id of designation
     * @since           1.0
     */
	@Override
	public void delete(List<Designation> designations) {
		for (Designation designation : designations) {
			entityManager.createNamedQuery("Designation.Delete").setParameter("id", designation.getDn_Id()).executeUpdate();
		}
		entityManager.flush();
	}

	/** 
     * this method is used to check the uniqueness of designation name  from the database
     * @param  dn_Name :dn_Name
     * @return         :returns boolean value(true/false) 
     * @since           1.0
     */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public boolean checkDesignationUnique(Designation designation, String type) {
		List<String> list_designation = entityManager
				.createNamedQuery("Designation.CheckGroup")
				.setParameter("dn_Name", designation.getDn_Name())
				.getResultList();
		if (type == "update") {
			if ((list_designation.size() > 0)) {
				if (list_designation.get(0).equals(
						find(designation.getDn_Id()).getDn_Name()))
					return true;
				else
					return false;
			} else
				return true;

		} else {
			if ((list_designation.size() > 0))
				return false;
			else
				return true;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.DesignationService#getBeanObject
	 * (java.util.Map, java.lang.String)
	 */
	public Designation getBeanObject(Map<String, Object> model, String type) {

		String username = (String) SessionData.getUserDetails().get("userID");
		obj = new Designation();

		if (type.equals("update")) {
			obj.setDr_Status(((Boolean) model.get("dr_Status")) + "");
			obj.setCreated_By((String) model.get("created_By"));
			obj.setLast_Updated_By(username);
		}

		if (type.equals("save")) {
			obj.setDr_Status("Inactive");
			obj.setCreated_By(username);
			obj.setLast_Updated_By(username);
		}

		obj.setDn_Name((String) model.get("dn_Name"));
		obj.setDn_Description((String) model.get("dn_Description"));
		obj.setDept_Id((Integer) model.get("dept_Id"));

		java.util.Date date= new java.util.Date();
		logger.info(new Timestamp(date.getTime()));
		
        obj.setLastUpdatedDt(new Timestamp(date.getTime()));
		//obj.setLast_Updated_Dt(new Date());
		if (type.equals("update")) {
			obj.setDn_Id((Integer) model.get("dn_Id"));
		}
		return obj;
	}

	
	/** 
     * this method is used to change the status of Designation and to store in database
     * @param dnId     : dnId
     * @return         : returns 'activated/deactivated' message
     * @since           1.0
     */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void setDesignationStatus(int dnId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale) {
		try {
			PrintWriter out = response.getWriter();

			if (operation.equalsIgnoreCase("activate")) {
				entityManager.createNamedQuery("Designation.UpdateStatus")
						.setParameter("dr_Status", "Active")
						.setParameter("dnId", dnId).executeUpdate();
				out.write("Designation Activated");
			} else {

			List<String> checkList = usersService.checkDesignationExistence(dnId);
			if(checkList.size() == 0)
			{
				entityManager.createNamedQuery("Designation.UpdateStatus")
				.setParameter("dr_Status", "Inactive")
				.setParameter("dnId", dnId).executeUpdate();
				out.write("Designation Deactivated");
			}
			else
				out.write("Cannot deactivate this designation since Users are already assigned");
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 
     * this method is used to read designation details based on designation Id from the database
     * @return         : returns designation information
     * @since           1.0
     */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Designation> getDesignationDetails(int id) {

		List<Designation> listDesignation = entityManager
				.createNamedQuery("designationDetails")
				.setParameter("DesignationId", id).getResultList();
		return listDesignation;
	}

	/** 
     * this method is used to find designation Id  from the database based on Designation Name
     * @return         : returns Designation Id 
     * @since           1.0
     */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> getDesignationIdBasedOnDesignationName(
			String designationName) {
		return entityManager
				.createNamedQuery(
						"Designation.getDesignationIdBasedOnDesignationName",
						Integer.class)
				.setParameter("designationName", designationName)
				.getResultList();
	}

	/*----------------------------------------by ravi-----------------------------*/

	@SuppressWarnings("rawtypes")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List getAllDesignationRequiredFields() {

		List designationlist = entityManager.createNamedQuery(
				"Designation.findAllfields").getResultList();
		return getDesignationList(designationlist);
	}

	
	@SuppressWarnings("rawtypes")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List getDesignationList(List designationlist) {
		Map<Integer, Designation> userMap = new HashMap<Integer, Designation>();

		for (Iterator i = designationlist.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();

			if (!(userMap.containsKey((Integer) values[0]))) {

				Designation designation = new Designation();

				designation.setDn_Id((Integer) values[0]);
				designation.setDn_Name((String) values[1]);
				
				
				designation.setDr_Status((String) values[2]);
				designation.setDn_Description((String) values[3]);
				designation.setCreated_By((String) values[4]);
				designation.setLast_Updated_By((String) values[5]);

				Department department = new Department();
				department.setDept_Id((Integer) values[6]);
				department.setDept_Name((String) values[7]);

				designation.setDepartment(department);
				userMap.put((Integer) values[0], designation);

			}

		}
		List<Map<String, Object>> desig_list = new ArrayList<Map<String, Object>>();

		Collection<Designation> col = userMap.values();
		for (Iterator<Designation> iterator = col.iterator(); iterator
				.hasNext();) {
			
			Map<String, Object> designationMap = new HashMap<String, Object>();

			Designation designationInstance = (Designation) iterator.next();
			
			if(!(designationInstance.getDn_Name().equalsIgnoreCase("Invalid")))
			{
				designationMap.put("dn_Id", designationInstance.getDn_Id());
				designationMap.put("dn_Name", designationInstance.getDn_Name());
				designationMap.put("dn_Description", designationInstance.getDn_Description());
				designationMap.put("dept_Name", designationInstance.getDepartment().getDept_Name());
				designationMap.put("dr_Status", designationInstance.getDr_Status());
				designationMap.put("created_By", designationInstance.getCreated_By());
				designationMap.put("last_Updated_By", designationInstance.getLast_Updated_By());

				desig_list.add(designationMap);
			}
			
		}

		return desig_list;

	}

	/** 
     * this method is used to find list of designation details from the database
     * @return         : returns list of designation details
     * @since           1.0
     */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Designation getDesignationObject(Map<String, Object> map, String type, Designation designation) {
		
		designation = getBeanObject(map, type, designation);

		return designation;
	}
	
	@Transactional(propagation = Propagation.NEVER)
	private Designation getBeanObject( Map<String, Object> map, String type,
			Designation designation) {
		 
		String username=(String) SessionData.getUserDetails().get("userID");
		logger.info("user name is="+username);
		
		if (type.equals("update")) {
			
			designation.setDn_Id((Integer) map.get("dn_Id"));
			
			String name = (String) map.get("dn_Name");
			name = name.substring(0, 1).toUpperCase()+ name.substring(1).toLowerCase();
			designation.setDn_Name(WordUtils.capitalizeFully((String) map.get("dn_Name")));
			
			String desc=(String) map.get("dn_Description");
			String desc_trim=desc.trim();
			designation.setDn_Description(desc_trim);
			
			designation.setDr_Status((String) map.get("dr_Status").toString());	
			if(map.get("dept_Name") instanceof String){
				designation.setDept_Id((int) map.get("dept_Id"));			
			}else{
				designation.setDept_Id((int) map.get("dept_Name"));			
			}
			
			designation.setLast_Updated_By(username);
			designation.setCreated_By((String)map.get("created_By"));
			
			java.util.Date date= new java.util.Date();
			logger.info(new Timestamp(date.getTime()));			
			designation.setLastUpdatedDt(new Timestamp(date.getTime()));
			
		}

		if (type.equals("save")) {
			
			String name = (String) map.get("dn_Name");
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			designation.setDn_Name(WordUtils.capitalizeFully((String) map.get("dn_Name")));
			
			if(map.get("dn_Description")==null){
			}else{
				String desc=(String) map.get("dn_Description");
				String desc_trim=desc.trim();
			designation.setDn_Description(desc_trim);
			}
			designation.setDr_Status("Inactive");
			logger.info("deaprtment is-"+ map.get("department"));
			
			if( map.get("dept_Name") instanceof java.lang.String )
			{
			}
			else{
				designation.setDept_Id((int) map.get("dept_Name"));
			}
			designation.setLast_Updated_By(username);
			designation.setCreated_By(username);
			java.util.Date date= new java.util.Date();
			designation.setLastUpdatedDt(new Timestamp(date.getTime()));
		}
		
		return designation;
	}

	@Override
	public List<?> getDistinctDepartments()
	{
		return entityManager.createNamedQuery("Designation.getDistinctDepartments").getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int setAllPrevDepartmentsBasedOnDeptId(int deptId) {
		 return entityManager.createNamedQuery("Designation.setAllPrevDepartmentsBasedOnDeptId")
				 .setParameter("deptId", deptId)
				 .executeUpdate();
		
	}
	
	
	/** 
     * this method is used to find list of all activated designation  from the database
     * @return         : returns list of activated  designation .
     * @since           1.0
     */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Designation> getAllActiveDesignations() {
		return entityManager.createNamedQuery("Designation.getAllActiveDesignations").getResultList();
				
	}
	
	
	/** 
     * this method is used to find list of designation name from the database
     * @return         : returns list of designation name
     * @since           1.0
     */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Designation> geteDesignationsNameForFilter() {
		return entityManager.createNamedQuery("desigantionName").getResultList();
	}

	/** 
     * this method is used to designation name based on Id from the database
     * @return         : returns designation name
     * @since           1.0
     */
	@Override
	 @Transactional(propagation=Propagation.SUPPORTS)
	public String getDesignationNameBasedOnId(int id) {
		return  entityManager.createNamedQuery("Designation.getDesignationNameBasedOnId",String.class)
				.setParameter("id", id)
				.getSingleResult();
		
     }
}
	
