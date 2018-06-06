
package com.bcits.bfm.service.userManagement;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.GenericService;

/**
 * Interface for UserServiceImpl.
 * 
 * @author Manjunath Krishnappa
 * @version %I%, %G%
 * @since 0.1
 */
public interface UsersService extends GenericService<Users> {

	public List<Map<String, Object>> getAllUsersRequiredFilds();
	
	public List<Object[]> getAllUsers();
	
	@SuppressWarnings("rawtypes")
	public List getAllStaffRecords();

	public boolean checkUserLoginNameUniqueness(Users users, String type);

	public List<String> getAllUsersLoginNames();

	public Users getUserInstanceByLoginName(String userLoginName);

	public String setUserStatus(int userId, String operation, String mode, String name, String mobile, String email);

	public Users getUserObject(Map<String, Object> map, String type,
			Users users);

	public List<Map<String, Object>> getUserObjectAfterSaveOrUpdate(
			Users users);
	
	public int getPersonId(String urLoginName);

	void modifyToLdap(Users users, String type, String tempPass);

	public int setAllPrevDepartmentsBasedOnDeptId(int depId);

	public int setAllPrevDesignationsBasedOnDnId(int dnId);

	public Users getUserInstanceBasedOnPersonId(int personId);

	public List<String> checkDepartmentExistence(int depId);

	public List<String> checkDesignationExistence(int dnId);

	public List<String> findbyPersonId(int personId);

	List<?> getAllUserIdsAndPersonNames();
	
	String getLoginNameBasedOnId(int id);

	public List<Users> findAll();

	public Object findAllActiveUsers();

	public List<Users> getAllUsersBasedOnStatus();

	public void setUserStatus(int urId, String operation,
			HttpServletResponse response);
	
	
	public void updateApprovalStatus(int urId,String status);

	public List<Map<String, Object>> getAllUserApproval(String userId);

	public List<?> getRoleGroupFilter();

	public Map<String,Object> getUserDetails();

	public Map<String,Object> getPersonDetails(String type);

	public Map<String,Object> getParkingDetails();

	public Map<String,Object> getVisitorDetails();

	public Map<String,Object> getCustomerDetails();

	public Map<String,Object> getFileDrawingDetails();

	public Map<String,Object> getAssetsInventaryDetails();

	public Map<String,Object> getHelpDeskDetails();

	public List<Map<String,Object>> getChartData() throws ParseException;

	public  Map<String,Object> getOwnerDetails();

	public Map<String,Object> getVendorDetails();

	public List<Users> getDesigBasedOnPersonId(int personId);

	public List<Users> getPersonListBasedOnDeptDesigId(Integer deptId,
			Integer desigId);

	





}




/*package com.bcits.bfm.service.userManagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.GenericService;

*//**
 * Interface for UserServiceImpl.
 * 
 * @author Manjunath Krishnappa
 * @version %I%, %G%
 * @since 0.1
 *//*
public interface UsersService extends GenericService<Users> {

	public List<Map<String, Object>> getAllUsersRequiredFilds();
	
	@SuppressWarnings("rawtypes")
	public List getAllStaffRecords();

	public boolean checkUserLoginNameUniqueness(Users users, String type);

	public List<String> getAllUsersLoginNames();

	public Users getUserInstanceByLoginName(String userLoginName);

	public String setUserStatus(int userId, String operation, String mode, String name, String mobile, String email);

	public Users getUserObject(Map<String, Object> map, String type,
			Users users);

	public List<Map<String, Object>> getUserObjectAfterSaveOrUpdate(
			Users users);
	
	public int getPersonId(String urLoginName);

	void modifyToLdap(Users users, String type, String tempPass);

	public int setAllPrevDepartmentsBasedOnDeptId(int depId);

	public int setAllPrevDesignationsBasedOnDnId(int dnId);

	public Users getUserInstanceBasedOnPersonId(int personId);

	public List<String> checkDepartmentExistence(int depId);

	public List<String> checkDesignationExistence(int dnId);

	public List<String> findbyPersonId(int personId);

	List<?> getAllUserIdsAndPersonNames();
	
	String getLoginNameBasedOnId(int id);

	public List<Users> findAll();

	public Object findAllActiveUsers();

	public List<Users> getAllUsersBasedOnStatus();

	public void setUserStatus(int urId, String operation,
			HttpServletResponse response);
	
	
	public void updateApprovalStatus(int urId,String status);

	public List<Map<String, Object>> getAllUserApproval(String userId);



}*/