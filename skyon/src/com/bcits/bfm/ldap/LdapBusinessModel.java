package com.bcits.bfm.ldap;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.bcits.bfm.model.Ldap;
import com.bcits.bfm.model.TreeViewItem;
import com.bcits.bfm.serviceImpl.facilityManagement.ParkingSlotsAllotmentServiceImpl;
import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPModification;
import com.novell.ldap.LDAPSearchResults;
import com.novell.ldap.util.Base64;

/**
 * @author Manjunath Kotagi
 *
 */
@Component
public class LdapBusinessModel {	

	@Value("${Unique.ldap.ldapHost}")
	private String ldapHost;

	@Value("${Unique.ldap.ldapPort}")
	private int ldapPort;

	@Value("${Unique.ldap.loginDN}")
	private String loginDN;

	@Value("${Unique.ldap.password}")
	private String password;
	
	@Value("${Unique.ldap.domain1}")
	private String dc1;
	 
	@Value("${Unique.ldap.domain2}")
	private String dc2;
	
	
	/*private String ldapHost="192.168.2.252";
	 
	 
	 private int ldapPort=389;
	 
	 
	 private String loginDN="cn=Directory Manager";
	 
	 
	 private String password="bcits"*/;

	 private final int ldapVersion = LDAPConnection.LDAP_V3;

	 private static final Log logger = LogFactory.getLog(ParkingSlotsAllotmentServiceImpl.class);

	/**
	 * Load Ldap connection details from properties file, Establish Ldap connection
	 * 
	 * @param
	 * @throws LDAPException
	 */
	private LDAPConnection connectLdap() throws LDAPException, Exception {
		LDAPConnection lc = new LDAPConnection();
		lc.connect(ldapHost, ldapPort);
		lc.bind(ldapVersion, loginDN, password.getBytes("UTF8"));
		return lc;
	}

	/**
	 * To Retrieve User Password
	 * 
	 * @param userIdInput : User Login name
	 * @param emailIdInput : User Email-id
	 * @param mobileNumberInput : User Mobile Number
	 * 
	 * Returns Map with Key value Pairs (UserId and Password) Or (Error)
	 * @throws LDAPException 
	 */
	public Map<String, String> getPassword(String userIdInput,
			String emailIdInput, String mobileNumberInput) throws LDAPException {
		Map<String, String> credential = new HashMap<String, String>();
		String encodedPassword = "";
		String userId = "";
		String mail = "";
		String telephoneNumber = "";
		byte[] decoded = null;
		String userDN = " cn=" + userIdInput + ",ou=Users,dc="+dc1+",dc="+dc2;
		LDAPConnection lc=null;
		try {
			lc = connectLdap();
			JSONObject obj = getLdapAttributes(lc, userDN);
			userId = obj.getString("cn");
			mail = obj.getString("mail");
			telephoneNumber = obj.getString("telephoneNumber");
			encodedPassword = obj.getString("userPassword");
			encodedPassword = encodedPassword.replace("{BASE64}", "");
			decoded = Base64.decode(encodedPassword);
			if (userId.equals(userIdInput) && mail.equals(emailIdInput)
					&& telephoneNumber.equals(mobileNumberInput)) {
				credential.put("UserId", userId);
				credential.put("Password", new String(decoded));
				credential.put("telephoneNumber", telephoneNumber);

			} else {
				credential.put("Error",
						"UserId,Email-id or ContactNumber Not Matched");
			}
			

		} catch (JSONException e) {
			logger.info(e.getMessage());
			credential.put("Error", "Please Provide Correct Details");
		} catch (LDAPException e) {
			logger.info(e.getLDAPErrorMessage());
			credential.put("Error", "Please Provide Correct Details");
		} catch (Exception e) {
			logger.info(e.getMessage());
			credential.put("Error", "Please Provide Correct Details");
		}
		finally{
			if(lc!=null)
				lc.disconnect();
		}
		return credential;

	}

	/**
	 * Reset User Password
	 * 
	 * @param userName : User Login Name
	 * @param newPassword : User Password
	 * @throws LDAPException 
	 */
	public void resetPassword(String userName, String newPassword) throws LDAPException {

		String userDn = "cn=" + userName + ",ou=Users,dc="+dc1+",dc="+dc2;
		LDAPConnection lc=null;
		try {

			lc= connectLdap();
			JSONObject obj = getLdapAttributes(lc, userDn);
			String userPassword = obj.getString("userPassword");
			String response = changePassword(userName, userPassword,newPassword);
			logger.info(response);
		} catch (Exception e) {

		}
		finally{
			if(lc!=null)
				lc.disconnect();
		}

	}

	/**
	 * Internal Supporting Method for
	 * getRoles,getLoginRoles,uniqueMembers,memberDescription,getGroups,getPassword
	 * 
	 * @param lc : Ldap Connection Object
	 * @param userDN : User Directory Name
	 * 
	 * Returns LDAP Attributes for Specified DN
	 */
	private JSONObject getLdapAttributes(LDAPConnection lc, String userDN)
			throws JSONException, LDAPException, Exception {

		JSONObject obj = new JSONObject();
		LDAPEntry nextEntry = null;

		LDAPSearchResults searchResults = lc.search(userDN,	LDAPConnection.SCOPE_BASE, "(objectclass=*)", null, false);
		while (searchResults.hasMore()) {
			nextEntry = searchResults.next();
			LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
			@SuppressWarnings("rawtypes")
			Iterator allAttributes = attributeSet.iterator();
			while (allAttributes.hasNext()) {
				LDAPAttribute attribute = (LDAPAttribute) allAttributes.next();
				String attributeName = attribute.getName();
				@SuppressWarnings("rawtypes")
				Enumeration allValues = attribute.getStringValues();
				if (allValues != null) {
					String value;
					while (allValues.hasMoreElements()) {
						value = (String) allValues.nextElement();
						if (Base64.isLDIFSafe(value)) {
						} else {
							value = Base64.encode(value.getBytes());
						}
						obj.put(attributeName, value);
					}
				}
			}
		}
		return obj;
	}

	/**
	 * To Change User Password
	 * 
	 * @param userid : User Login Name
	 * @param oldPassword : User Old Password
	 * @param newPassword : User New Password
	 * 
	 *Returns String Message (Password Modified or Error Message)
	 * @throws LDAPException 
	 */
	public String changePassword(String userid, String oldPassword,	String newPassword) throws LDAPException {
		String modifyDN = "cn=" + userid + ",ou=Users,dc="+dc1+",dc="+dc2;
		
		LDAPConnection lc=null;
		try {
			lc = connectLdap();

			LDAPModification[] modifications = new LDAPModification[2];

			LDAPAttribute deletePassword = new LDAPAttribute("userPassword",
					oldPassword);
			modifications[0] = new LDAPModification(LDAPModification.DELETE,
					deletePassword);
			LDAPAttribute addPassword = new LDAPAttribute("userPassword",
					newPassword);
			modifications[1] = new LDAPModification(LDAPModification.ADD,
					addPassword);
			lc.modify(modifyDN, modifications);			
			return "Your Password is Modified";
		}

		catch (LDAPException e) {
			if (e.getResultCode() == LDAPException.NO_SUCH_OBJECT) {
				return e.getLDAPErrorMessage();
			} else if (e.getResultCode() == LDAPException.INSUFFICIENT_ACCESS_RIGHTS) {
				return e.getLDAPErrorMessage();
			} else {
				return e.getLDAPErrorMessage();
			}
		} catch (Exception e) {
			return e.getMessage();
		}
		finally{
			if(lc!=null)
				lc.disconnect();
		}
	
	}

	/**
	 * Developer Reference
	 */
	public static void main(String[] args) throws Exception {
		
	}	
	
	/**
	 * Internal Supporting Method for Manage Permission
	 * 
	 * @param searchIn : Directory Name Where User Modules Exist in LDAP
	 * 
	 *Returns the Roles and tasks for that role
	 * @throws JSONException 
	 * @throws LDAPException 
	 */
	@Cacheable(value="menuAttributes")
	public List<ModuleDetails> getMemberAttributesForMenu() throws JSONException, LDAPException {
		String searchIn="ou=Products,dc="+dc1+",dc="+dc2;
		List<String> listOfMembers = new ArrayList<String>();		
		List<ModuleDetails> mdetails = new ArrayList<ModuleDetails>();		
		
		LDAPConnection lc =null;
		try {
			
			lc = connectLdap();

			LDAPSearchResults searchResults = lc.search(searchIn,LDAPConnection.SCOPE_ONE, "(objectclass=*)", null, false);

			LDAPEntry nextEntry = null;

			while (searchResults.hasMore()) {
				nextEntry = searchResults.next();
				listOfMembers.add(nextEntry.getDN());
			}		
			
			for (final String record : listOfMembers) {
				ModuleDetails md=new ModuleDetails();
				md.setDescription(memberDescriptionforMenu(record));
				List<String> li=uniqueMembers(record);
				String role="";
				for(String str:li){
					role+=str+",";
				}
				md.setRole(role);
				md.setItems(getInnerModules(record));
				mdetails.add(md);
			}			

		} catch (LDAPException e) {
			e.getLDAPErrorMessage();
		} catch (Exception e) {
			e.getMessage();
		}	
		finally{
			if(lc!=null)
				lc.disconnect();
		}
		
		return mdetails;
	}
	
	public List<ItemDetails> getInnerModules(String searchIn) throws Exception {
		
		List<ItemDetails> obj = new ArrayList<ItemDetails>();	
		List<String> listOfMembers = new ArrayList<String>();
		LDAPConnection lc = null;
		try{
			
			lc = connectLdap();
	
			LDAPSearchResults searchResults = lc.search(searchIn,LDAPConnection.SCOPE_ONE, "(objectclass=*)", null, false);
	
			LDAPEntry nextEntry = null;
	
			while (searchResults.hasMore()) {
				nextEntry = searchResults.next();
				listOfMembers.add(nextEntry.getDN());
			}
			
			
			for (final String record : listOfMembers) {			
				
				ItemDetails id=new ItemDetails();
				List<String> li=uniqueMembers(record);
				String role="";
				for(String str:li){
					role+=str+",";
				}
				String des=memberDescriptionforMenu(record);			
				id.setRole(role);
				id.setText(getCN(record).toString());
				id.setUrl(des);
				
				obj.add(id);
			}	
		
		}catch(Exception e){
			
		}finally{
			if(lc!=null)
				lc.disconnect();
		}
		return obj;
	}
	
	public List<ThirdLevelDetails> thirdlevelMenu(String searchIn, String string) throws LDAPException, Exception {
		
		String modifyDN = "cn=" + string + ",cn="+searchIn+",ou=Products,dc="+dc1+",dc="+dc2;

		List<String> listOfMembers = new ArrayList<String>();
		
		LDAPConnection lc = null;
		
		List<ThirdLevelDetails> obj = new ArrayList<ThirdLevelDetails>();	
		
		try{
			
			lc = connectLdap();
	
			LDAPSearchResults searchResults = lc.search(modifyDN,LDAPConnection.SCOPE_ONE, "(objectclass=*)", null, false);
	
			LDAPEntry nextEntry = null;
	
			while (searchResults.hasMore()) {
				nextEntry = searchResults.next();
				listOfMembers.add(nextEntry.getDN());
			}
			
			
			for (final String record : listOfMembers) {			
				ThirdLevelDetails td=new ThirdLevelDetails();
				td.setText(getCN(record).toString());
				List<String> li=uniqueMembers(record);
				String role="";
				for(String str:li){
						role+=str+",";
				}
				td.setRole(role);
				td.setUrl(memberDescriptionforMenu(record));
				obj.add(td);
			}	
		
		}catch(Exception e){
			
		}finally{
			if(lc!=null)
				lc.disconnect();
		}		
		
		return obj;
	}
	

	public List<String> getAllRoles() throws LDAPException, Exception {
		
		String modifyDN = "ou=Roles,dc="+dc1+",dc="+dc2;
		
		List<String> listOfMembers = new ArrayList<String>();
		
		LDAPConnection lc = connectLdap();
		
		LDAPSearchResults searchResults = lc.search(modifyDN,LDAPConnection.SCOPE_ONE, "(objectclass=*)", null, false);
		
		LDAPEntry nextEntry = null;
		
		while (searchResults.hasMore()) {
			nextEntry = searchResults.next();
			listOfMembers.add(nextEntry.getDN());
		}
		
		List<String> roles=new ArrayList<String>();
		
		for (final String record : listOfMembers) {				
			String str=(String) getCN(record);
			roles.add(str);
		}	
		
		lc.disconnect();
		
		return roles;
	}

	/**
	 * To Un-assign Users 
	 * @param deactivatedRole : Role Name
	 */
	public void getMemberAttributesNext(String deactivatedRole) {
		String searchIn = "ou=Products,dc="+dc1+",dc="+dc2;
		List<String> listOfMembers = new ArrayList<String>();
		try {

			LDAPConnection lc = connectLdap();

			LDAPSearchResults searchResults = lc.search(searchIn,
					LDAPConnection.SCOPE_SUB, "(objectclass=*)", null, false);

			LDAPEntry nextEntry = null;

			while (searchResults.hasMore()) {
				nextEntry = searchResults.next();
				listOfMembers.add(nextEntry.getDN());
			}
			for (int i = 1; i < listOfMembers.size(); i++) {
				List<String> members = new ArrayList<String>();
				String description = memberDescription(listOfMembers.get(i));
				members = uniqueMembers(listOfMembers.get(i));

				Iterator<String> it = members.iterator();
				while (it.hasNext()) {
					String str = it.next();
					if (str.contains(deactivatedRole)) {
						removeMemberFromRolesAfterRoleDeactivation(str,
								description);
					}
				}

			}
			lc.disconnect();

		} catch (LDAPException e) {
			e.getLDAPErrorMessage();
		} catch (Exception e) {
			e.getMessage();
		}

	}

	/**
	 * Remove User From Role
	 * 
	 * @param role : role Name
	 * @param userdn : userName
	 */
	public void removeMemberFromRolesAfterRoleDeactivation(String role,
			String userdn) {

		String roledn = "cn=" + role + ",ou=Roles,dc="+dc1+",dc="+dc2;
		// Add modifications to modRoles
		LDAPAttribute uniqueMember = new LDAPAttribute("uniqueMember", userdn);
		LDAPModification modRole = new LDAPModification(
				LDAPModification.DELETE, uniqueMember);

		// Modify the Role's attributes
		try {
			LDAPConnection lc = connectLdap();
			lc.modify(roledn, modRole);
			lc.disconnect();

		} catch (Exception e) {
		}
		logger.info("user Removed from roles");
	}

	/**
	 * Internal Supporting Method for editUsers
	 * 
	 * @param oldName : Old DirectoryName
	 * @param newName : New Directory Name
	 * 
	 * Returns Nothing
	 * @throws LDAPException 
	 */
	public void renameDn(String oldName, String newName) throws LDAPException {
		String dn = "cn=" + oldName + ",ou=Users,dc="+dc1+",dc="+dc2;

		LDAPConnection lc=null;
		try {
			lc = connectLdap();
			lc.rename(dn, "cn=" + newName, true);
		} catch (Exception e) {
		}
		finally{
			if(lc!=null)
				lc.disconnect();
		}
		logger.info("Entry " + dn + " has been renamed.");

	}
	
	/**
	 * Internal Supporting Method for Manage Permission
	 * 
	 * @param searchIn : Directory Name Where User Modules Exist in LDAP
	 * 
	 *Returns the Roles and tasks for that role
	 */
	@Cacheable(value = "messageCache", key = "#word")
	public List<JSONObject> getMemberAttributes() {
		String searchIn="ou=Products,dc="+dc1+",dc="+dc2;
		List<String> listOfMembers = new ArrayList<String>();
		
		List<JSONObject> map = new ArrayList<JSONObject>();	
		
		try {

			LDAPConnection lc = connectLdap();

			LDAPSearchResults searchResults = lc.search(searchIn,LDAPConnection.SCOPE_SUB, "(objectclass=*)", null, false);

			LDAPEntry nextEntry = null;

			while (searchResults.hasMore()) {
				nextEntry = searchResults.next();
				listOfMembers.add(nextEntry.getDN());
			}
			for (int i = 1; i < listOfMembers.size(); i++) {
				JSONObject jo=new JSONObject();
				jo.put("url",memberDescription(listOfMembers.get(i)));
				jo.put("role",uniqueMembers(listOfMembers.get(i)));
				map.add(jo);
			}			
			lc.disconnect();			

		} catch (LDAPException e) {
			e.getLDAPErrorMessage();
		} catch (Exception e) {
			e.getMessage();
		}
		return map;
	}

	

	public Object getCN(String record) throws LDAPException, Exception {
		LDAPConnection lc = connectLdap();
		JSONObject obj = getLdapAttributes(lc, record);
		String text = obj.getString("cn");					
		lc.disconnect();
		return text;
	}
	

	/**
	 * Internal Supporting Method for getMemberAttributes
	 * 
	 * Returns the Roles
	 */
	public List<String> getRoles() {

		String searchIn = "ou=Roles,dc="+dc1+",dc="+dc2;
		List<String> listOfRoles = new ArrayList<String>();
		List<String> listOfMembers = new ArrayList<String>();

		try {

			LDAPConnection lc = connectLdap();

			LDAPSearchResults searchResults = lc.search(searchIn,
					LDAPConnection.SCOPE_SUB, "(objectclass=*)", null, false);

			LDAPEntry nextEntry = null;

			while (searchResults.hasMore()) {
				nextEntry = searchResults.next();
				listOfMembers.add(nextEntry.getDN());
			}
			JSONObject obj;
			for (int i = 1; i < listOfMembers.size(); i++) {
				obj = getLdapAttributes(lc, listOfMembers.get(i));
				listOfRoles.add(obj.getString("cn"));
			}
			lc.disconnect();

		} catch (LDAPException e) {
			e.getLDAPErrorMessage();
		} catch (Exception e) {
			e.getMessage();
		}

		return listOfRoles;
	}

	/**
	 * Internal Supporting Method for getMemberAttributes
	 * 
	 * @param string : Modules Name
	 * 
	 * Returns the tasks
	 */
	private String memberDescription(String string) {
		String description = "";

		try {
			LDAPConnection lc = connectLdap();
			JSONObject obj = getLdapAttributes(lc, string);
			description = obj.getString("businessCategory");
			lc.disconnect();
		} catch (LDAPException e) {
			
		} catch (Exception e) {
			
		}

		return description;

	}
	/**
	 * Internal Supporting Method for getMemberAttributes
	 * 
	 * @param string : Modules Name
	 * 
	 * Returns the tasks
	 */
	private String memberDescriptionforMenu(String string) {
		String description = "";
		
		try {
			LDAPConnection lc = connectLdap();
			JSONObject obj = getLdapAttributes(lc, string);
			description = obj.getString("description");
			lc.disconnect();
		} catch (LDAPException e) {
			
		} catch (Exception e) {
			
		}
		
		return description;
		
	}

	/**
	 * Returns List of Users for Particular Role
	 * 
	 * @param String userDN : Directory name
	 * Returns List of Users
	 */
	public List<String> uniqueMembers(String userDN) {
		List<String> al = new ArrayList<String>();
		JSONObject obj;
		try {

			LDAPConnection lc = connectLdap();
			LDAPSearchResults searchResults = lc.search(userDN,
					LDAPConnection.SCOPE_BASE, "(objectclass=*)", null, false);

			while (searchResults.hasMore()) {
				LDAPEntry nextEntry = null;
				nextEntry = searchResults.next();

				LDAPAttribute atributes = nextEntry
						.getAttribute("uniqueMember");

				String[] arr = atributes.getStringValueArray();

				for (int i = 0; i < arr.length; i++) {
					obj = getLdapAttributes(lc, arr[i]);
					al.add(obj.getString("cn"));
				}
			}
			lc.disconnect();
		} catch (LDAPException e) {
			e.getLDAPErrorMessage();
		} catch (JSONException e) {

		} catch (Exception e) {

		}

		return al;

	}

	/**
	 * Adding User Data to LDAP
	 * 
	 * @param userName : User Login Name
	 * @param userPassword : User Password
	 * @param email : User email_Id
	 * @param telNumber : User Contact Number
	 * @param roles : List Of Roles or single Role
	 * @param groups : List Of Groups or single Group
	 * 
	 * Returns String Message (Success or Error)
	 */
	public String addUsers(String userName, String userPassword, String email,
			String telNumber, Set<String> roles, Set<String> groups){
		
		String containerName = "ou=Users,dc="+dc1+",dc="+dc2;

		LDAPAttributeSet attributeSet = new LDAPAttributeSet();

		attributeSet.add(new LDAPAttribute("objectclass", new String(
				"inetOrgPerson")));

		attributeSet.add(new LDAPAttribute("cn", new String[] { userName }));

		attributeSet.add(new LDAPAttribute("sn", new String(userName)));

		attributeSet.add(new LDAPAttribute("telephonenumber", new String(
				telNumber)));

		attributeSet.add(new LDAPAttribute("mail", new String(email)));

		attributeSet.add(new LDAPAttribute("userpassword", new String(
				userPassword)));

		String dn = "cn=" + userName + "," + containerName;

		LDAPEntry newEntry = new LDAPEntry(dn, attributeSet);
		LDAPConnection lc = null;

		try {

			// connect to the server
			lc = connectLdap();
			lc.add(newEntry);

			@SuppressWarnings("rawtypes")
			Iterator roleIterator = roles.iterator();

			while (roleIterator.hasNext()) {
				memberToRoles((String) roleIterator.next(), userName);
			}

			@SuppressWarnings("rawtypes")
			Iterator groupIterator = groups.iterator();

			while (groupIterator.hasNext()) {
				memberToGorups((String) groupIterator.next(), userName);
			}
			logger.info("successfully added");
		}

		catch (LDAPException e) {
			logger.info("exception " + e.getLDAPErrorMessage());
			return e.getLDAPErrorMessage();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				// disconnect with the server
				lc.disconnect();
			} catch (LDAPException e) {
				e.getLDAPErrorMessage();
			}
		}
		return "User is Successfully Added to the Entry";
	}

	
	public String addCustomer(String userName, String userPassword, String email,
			String telNumber, Set<String> roles, Set<String> groups){
		
		String containerName = "ou=Customers,dc="+dc1+",dc="+dc2;

		LDAPAttributeSet attributeSet = new LDAPAttributeSet();

		attributeSet.add(new LDAPAttribute("objectclass", new String(
				"inetOrgPerson")));

		attributeSet.add(new LDAPAttribute("cn", new String[] { userName }));

		attributeSet.add(new LDAPAttribute("sn", new String(userName)));

		attributeSet.add(new LDAPAttribute("telephonenumber", new String(
				telNumber)));

		attributeSet.add(new LDAPAttribute("mail", new String(email)));

		attributeSet.add(new LDAPAttribute("userpassword", new String(
				userPassword)));

		String dn = "cn=" + userName + "," + containerName;

		LDAPEntry newEntry = new LDAPEntry(dn, attributeSet);
		LDAPConnection lc = null;

		try {

			// connect to the server
			lc = connectLdap();
			lc.add(newEntry);

			@SuppressWarnings("rawtypes")
			Iterator roleIterator = roles.iterator();

			while (roleIterator.hasNext()) {
				memberToRoles((String) roleIterator.next(), userName);
			}

			@SuppressWarnings("rawtypes")
			Iterator groupIterator = groups.iterator();

			while (groupIterator.hasNext()) {
				memberToGorups((String) groupIterator.next(), userName);
			}
			logger.info("successfully added");
		}

		catch (LDAPException e) {
			logger.info("exception " + e.getLDAPErrorMessage());
			return e.getLDAPErrorMessage();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				// disconnect with the server
				lc.disconnect();
			} catch (LDAPException e) {
				e.getLDAPErrorMessage();
			}
		}
		return "User is Successfully Added to the Entry";
	}
	/**
	 * Deleting User Data from LDAP
	 * 
	 * @param userName : User Login Name
	 * 
	 * Returns String Message (Success or Error)
	 * @param groupNames : groupNames
	 * @param roleNames : roleNames
	 */
	@SuppressWarnings("rawtypes")
	public String deleteUsers(String userName, Set<String> roleNames,
			Set<String> groupNames) {
		logger.info("inside delete Users");
		String containerName = "ou=Users,dc="+dc1+",dc="+dc2;

		String dn = "cn=" + userName + "," + containerName;

		Iterator it = roleNames.iterator();

		while (it.hasNext()) {
			removeMemberFromRoles((String) it.next(), dn);
		}

		Iterator it1 = groupNames.iterator();

		while (it1.hasNext()) {
			removeMemberFromGorups((String) it1.next(), dn);
		}

		// String newEntry = new LDAPEntry(dn);
		LDAPConnection lc = null;
		try {

			// connect to the server
			lc = connectLdap();
			logger.info("connection success");
			// lc.add(newEntry);
			lc.delete(dn);
			logger.info("successfully deleted");
		}

		catch (LDAPException e) {
			logger.info("exception " + e.getLDAPErrorMessage());
			return e.getLDAPErrorMessage();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				// disconnect with the server
				lc.disconnect();
			} catch (LDAPException e) {
				e.printStackTrace();
			}
		}
		return "User is Successfully deleted to the Entry";
	}

	/**
	 * Deleting Customers
	 * 
	 * 
	 */
	
	@SuppressWarnings("rawtypes")
	public String deleteCustomer(String userName, Set<String> roleNames,
			Set<String> groupNames) {
		logger.info("inside delete Customers");
		String containerName = "ou=Customers,dc="+dc1+",dc="+dc2;

		String dn = "cn=" + userName + "," + containerName;

		Iterator it = roleNames.iterator();

		while (it.hasNext()) {
			removeMemberFromRoles((String) it.next(), dn);
		}

		Iterator it1 = groupNames.iterator();

		while (it1.hasNext()) {
			removeMemberFromGorups((String) it1.next(), dn);
		}

		// String newEntry = new LDAPEntry(dn);
		LDAPConnection lc = null;
		try {

			// connect to the server
			lc = connectLdap();
			logger.info("connection success");
			// lc.add(newEntry);
			lc.delete(dn);
			logger.info("successfully deleted");
		}

		catch (LDAPException e) {
			logger.info("exception " + e.getLDAPErrorMessage());
			return e.getLDAPErrorMessage();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				// disconnect with the server
				lc.disconnect();
			} catch (LDAPException e) {
				e.printStackTrace();
			}
		}
		return "Customers is Successfully deleted to the Entry";
	}
	
	
	
	/**
	 * Adding Members to Roles
	 * 
	 * @param role : Role Name
	 * @param userdn : Member for that Role
	 * 
	 * Returns String Message (Success or Error)
	 */
	public void memberToRoles(String role, String userdn) {

		String roledn = "cn=" + role + ",ou=Roles,dc="+dc1+",dc="+dc2;
		userdn = "cn=" + userdn + ",ou=Users,dc="+dc1+",dc="+dc2;

		// Add modifications to modRoles
		LDAPAttribute uniqueMember = new LDAPAttribute("uniqueMember", userdn);
		LDAPModification modRole = new LDAPModification(LDAPModification.ADD,
				uniqueMember);

		try {
			// Modify the Role's attributes
			LDAPConnection lc = connectLdap();
			lc.modify(roledn, modRole);
			lc.disconnect();
		} catch (Exception e) {
		}
		logger.info("member added to roles");

	}

	/**
	 * Removing Members from Roles
	 * 
	 * @param role : Role Name
	 * @param userdn : Member for that Role
	 * 
	 * Returns String Message (Success or Error)
	 */
	public void removeMemberFromRoles(String role, String userdn) {

		String roledn = "cn=" + role + ",ou=Roles,dc="+dc1+",dc="+dc2;
		userdn = "cn=" + userdn + ",ou=Users,dc="+dc1+",dc="+dc2;

		// Add modifications to modRoles
		LDAPAttribute uniqueMember = new LDAPAttribute("uniqueMember", userdn);
		LDAPModification modRole = new LDAPModification(
				LDAPModification.DELETE, uniqueMember);

		// Modify the Role's attributes
		try {
			LDAPConnection lc = connectLdap();
			lc.modify(roledn, modRole);
			lc.disconnect();

		} catch (Exception e) {
		}
		logger.info("user Removed from roles");
	}

	/**
	 * Adding Members to Groups
	 * 
	 * @param group : Group Name
	 * @param userdn : Member for that Group
	 * 
	 * Returns String Message (Success or Error)
	 */
	public void memberToGorups(String group, String userdn) {
		String groupdn = "cn=" + group + ",ou=GeneralGroups,dc="+dc1+",dc="+dc2;
		userdn = "cn=" + userdn + ",ou=Users,dc="+dc1+",dc="+dc2;

		try {
			LDAPConnection lc = connectLdap();

			// Add modifications to modRoles
			LDAPAttribute uniqueMember = new LDAPAttribute("uniqueMember",
					userdn);
			LDAPModification modGroup = new LDAPModification(
					LDAPModification.ADD, uniqueMember);

			// Modify the Role's attributes
			lc.modify(groupdn, modGroup);
			lc.disconnect();

		} catch (Exception e) {
		}
		logger.info("member added to groups");

	}

	/**
	 * Removing Members from Groups
	 * 
	 * @param group : Group Name
	 * @param userdn : Member for that Group
	 * 
	 * Returns String Message (Success or Error)
	 */
	public void removeMemberFromGorups(String group, String userdn) {

		String groupdn = "cn=" + group + ",ou=GeneralGroups,dc="+dc1+",dc="+dc2;
		userdn = "cn=" + userdn + ",ou=Users,dc="+dc1+",dc="+dc2;

		logger.info(userdn + "" + group);
		// Add modifications to modRoles
		LDAPAttribute uniqueMember = new LDAPAttribute("uniqueMember", userdn);
		LDAPModification modGroup = new LDAPModification(
				LDAPModification.DELETE, uniqueMember);

		// Modify the Role's attributes
		LDAPConnection lc;
		try {
			lc = connectLdap();
			lc.modify(groupdn, modGroup);
			lc.disconnect();
		} catch (Exception e) {
		}
		logger.info("member removed from groups");

	}

	/**
	 * Editing Groups
	 * 
	 * @param groupName : Group Name
	 * @param description : Group Description
	 * 
	 * Returns String Message (Success or Error)
	 */
	public void editGorups(String groupName, String description) {
		String groupdn = "cn=" + groupName
				+ ",ou=GeneralGroups,dc="+dc1+",dc="+dc2;

		// Add modifications to modRoles
		LDAPModification[] modifications = new LDAPModification[2];

		LDAPAttribute cn = new LDAPAttribute("cn", groupName);
		modifications[0] = new LDAPModification(LDAPModification.REPLACE, cn);

		LDAPAttribute descriptionModify = new LDAPAttribute("description",
				description);
		modifications[1] = new LDAPModification(LDAPModification.REPLACE,
				descriptionModify);

		// Modify the Role's attributes
		LDAPConnection lc;
		try {
			lc = connectLdap();
			lc.modify(groupdn, modifications);
			lc.disconnect();
			logger.info("Group Modified");
		} catch (Exception e) {
		}
	}

	/**
	 * Editing User Data to LDAP
	 * 
	 * @param oldName : User's Old Login Name
	 * @param userName : User's New Login Name
	 * @param emailID : User email_Id
	 * @param phoneNumber : User Contact Number
	 * @param roleNames : List Of Roles or single Role
	 * @param groupNames : List Of Groups or single Group
	 * 
	 * Returns String Message (Success or Error)
	 */
	public void editUsers(String oldName, String loginName, String emailID,
			String phoneNumber, Set<String> roleNames, Set<String> groupNames) {

		String userDn = "cn=" + loginName + ",ou=Users,dc="+dc1+",dc="+dc2;

		List<String> allRoles = getRoles();
		logger.info("list of roles " + allRoles);

		List<String> allGroups = getGroups();
		logger.info("list of groups " + allGroups);

		@SuppressWarnings("rawtypes")
		Iterator allRole = allRoles.iterator();
		while (allRole.hasNext()) {
			removeMemberFromRoles((String) allRole.next(), oldName);
		}
		@SuppressWarnings("rawtypes")
		Iterator allGroup = allGroups.iterator();
		while (allGroup.hasNext()) {
			removeMemberFromGorups((String) allGroup.next(), oldName);
		}

		// Add modifications to modRoles
		LDAPModification[] modifications = new LDAPModification[2];

		LDAPAttribute emailId = new LDAPAttribute("mail", emailID);
		modifications[0] = new LDAPModification(LDAPModification.REPLACE,
				emailId);

		LDAPAttribute telNumber = new LDAPAttribute("telephonenumber",
				phoneNumber);
		modifications[1] = new LDAPModification(LDAPModification.REPLACE,
				telNumber);

		try {

			renameDn(oldName, loginName);

			LDAPConnection lc = connectLdap();
			lc.modify(userDn, modifications);
			logger.info("Users data Modified");

			// Modify the Role's attributes

			@SuppressWarnings("rawtypes")
			Iterator rolesIterator = roleNames.iterator();
			while (rolesIterator.hasNext()) {
				memberToRoles((String) rolesIterator.next(), loginName);
			}

			@SuppressWarnings("rawtypes")
			Iterator groupsIterator = roleNames.iterator();
			while (groupsIterator.hasNext()) {
				memberToGorups((String) groupsIterator.next(), loginName);
			}

			logger.info(" all members modified");
			lc.disconnect();
		} catch (Exception e) {
			logger.info("Exception " + e.getMessage());
		}
	}

	/**
	 * Adding Groups
	 * 
	 * @param groupName : Group Name
	 * @param description : Group Description
	 * 
	 * Returns String Message (Success or Error)
	 */
	public String addGroups(String groupName, String description) {
		logger.info("inside add groups");
		String containerName = "ou=GeneralGroups,dc="+dc1+",dc="+dc2;

		LDAPAttributeSet attributeSet = new LDAPAttributeSet();

		attributeSet.add(new LDAPAttribute("objectclass", new String(
				"groupOfUniqueNames")));
		attributeSet.add(new LDAPAttribute("objectclass", new String("top")));
		attributeSet.add(new LDAPAttribute("cn", new String[] { groupName }));
		attributeSet.add(new LDAPAttribute("description", new String(
				description)));

		String dn = "cn=" + groupName + "," + containerName;

		LDAPEntry newEntry = new LDAPEntry(dn, attributeSet);
		LDAPConnection lc = null;
		try {

			// connect to the server
			lc = connectLdap();
			logger.info("connection success");
			lc.add(newEntry);
			logger.info("added");
			logger.info("successfully added");
		}

		catch (LDAPException e) {
			logger.info("exception " + e.getLDAPErrorMessage());
			return e.getLDAPErrorMessage();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				// disconnect with the server
				lc.disconnect();
			} catch (LDAPException e) {
				e.printStackTrace();
			}
		}
		return "Group is Successfully Added to the Entry";
	}

	/**
	 * Deleting Groups
	 * 
	 * @param groupName : Group Name
	 * 
	 * Returns String Message (Success or Error)
	 */
	public String deleteGroups(String groupName) {
		logger.info("inside add groups");
		String containerName = "ou=GeneralGroups,dc="+dc1+",dc="+dc2;

		String dn = "cn=" + groupName + "," + containerName;

		// String newEntry = new LDAPEntry(dn);
		LDAPConnection lc = null;
		try {

			// connect to the server
			lc = connectLdap();
			logger.info("connection success");
			// lc.add(newEntry);
			lc.delete(dn);
			logger.info("successfully deleted");
		}

		catch (LDAPException e) {
			logger.info("exception " + e.getLDAPErrorMessage());
			return e.getLDAPErrorMessage();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				// disconnect with the server
				lc.disconnect();
			} catch (LDAPException e) {
				e.printStackTrace();
			}
		}
		return "Group is Successfully deleted to the Entry";
	}

	/**
	 * Adding Roles
	 * 
	 * @param roleName : Role Name
	 * @param description : Role Description
	 * 
	 * Returns String Message (Success or Error)
	 */
	public String addRoles(String roleName, String description) {
		logger.info("inside add groups");
		String containerName = "ou=Roles,dc="+dc1+",dc="+dc2;

		LDAPAttributeSet attributeSet = new LDAPAttributeSet();

		attributeSet.add(new LDAPAttribute("objectclass", new String(
				"groupOfUniqueNames")));
		attributeSet.add(new LDAPAttribute("objectclass", new String("top")));
		attributeSet.add(new LDAPAttribute("cn", new String[] { roleName }));
		attributeSet.add(new LDAPAttribute("description", new String(
				description)));

		String dn = "cn=" + roleName + "," + containerName;

		LDAPEntry newEntry = new LDAPEntry(dn, attributeSet);
		LDAPConnection lc = null;
		try {

			// connect to the server
			lc = connectLdap();
			logger.info("connection success");
			lc.add(newEntry);
			logger.info("successfully added");
		}

		catch (LDAPException e) {
			logger.info("exception " + e.getLDAPErrorMessage());
			return e.getLDAPErrorMessage();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				// disconnect with the server
				lc.disconnect();
			} catch (LDAPException e) {
				e.printStackTrace();
			}
		}
		return "Role is Successfully Added to the Entry";
	}

	/**
	 * Deleting Roles
	 * 
	 * @param roleName : Role Name
	 * 
	 * Returns String Message (Success or Error)
	 */
	public String deleteRoles(String roleName) {

		String containerName = "ou=Roles,dc="+dc1+",dc="+dc2;
		String dn = "cn=" + roleName + "," + containerName;

		// String newEntry = new LDAPEntry(dn);
		LDAPConnection lc = null;
		try {

			// connect to the server
			lc = connectLdap();
			logger.info("connection success");
			// lc.add(newEntry);
			lc.delete(dn);
			logger.info("successfully deleted");
		}

		catch (LDAPException e) {
			logger.info("exception " + e.getLDAPErrorMessage());
			return e.getLDAPErrorMessage();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				// disconnect with the server
				lc.disconnect();
			} catch (LDAPException e) {
				e.printStackTrace();
			}
		}
		return "Roles is Successfully deleted to the Entry";
	}

	/**
	 * Editing Roles
	 * 
	 * @param roleName : Role Name
	 * 
	 * Returns String Message (Success or Error)
	 */
	public void editRoles(String roleName) {
		String groupdn = "cn=" + roleName + ",ou=Roles,dc="+dc1+",dc="+dc2;

		// Add modifications to modRoles
		LDAPModification[] modifications = new LDAPModification[1];

		LDAPAttribute cn = new LDAPAttribute("cn", roleName);
		modifications[0] = new LDAPModification(LDAPModification.REPLACE, cn);

		// Modify the Role's attributes
		LDAPConnection lc;
		try {
			lc = connectLdap();
			lc.modify(groupdn, modifications);
			lc.disconnect();
			logger.info("Role Modified");
		} catch (Exception e) {
		}
	}

	/**
	 * Retrieving Login Roles
	 * 
	 * @param userId : User Login Name
	 * 
	 * Returns List Of Roles
	 */
	public List<String> getLoginRoles(String userId) {

		String userDN = "cn=" + userId + ",ou=Users,dc="+dc1+",dc="+dc2;
		String returnAttrs[] = { "isMemberOf" };
		String member = "";
		List<String> roles = new ArrayList<String>();
		String role = "";
		try {
			LDAPConnection lc = connectLdap();
			LDAPEntry entry = lc.read(userDN, returnAttrs);
			LDAPAttribute atributes = entry.getAttribute("isMemberOf");

			try {

				String[] arr = atributes.getStringValueArray();
				for (int i = 0; i < arr.length; i++) {
					if ((arr[i].contains("ou=Roles"))) {
						member = arr[i];
						JSONObject obj = getLdapAttributes(lc, member);
						role = obj.getString("cn");
						roles.add(role);

					}

				}

			} catch (Exception e) {
				// /e.printStackTrace();
				// logger.info("Role " + e.getMessage());
			}
			lc.disconnect();
		}

		catch (LDAPException e) {
			e.getMessage();
		} catch (Exception e1) {
			e1.getMessage();
		}
		return roles;

	}

	/**
	 * Retrieving Login groups
	 * 
	 * @param userId : User Login Name
	 * 
	 * Returns List Of groups
	 */
	public List<String> getLoginGroups(String userId) {

		String userDN = "cn=" + userId + ",ou=Users,dc="+dc1+",dc="+dc2;
		String returnAttrs[] = { "isMemberOf" };
		String member = "";
		List<String> groups = new ArrayList<String>();
		String group = "";
		try {
			LDAPConnection lc = connectLdap();
			LDAPEntry entry = lc.read(userDN, returnAttrs);
			LDAPAttribute atributes = entry.getAttribute("isMemberOf");

			try {

				String[] arr = atributes.getStringValueArray();
				for (int i = 0; i < arr.length; i++) {
					if ((arr[i].contains("ou=GeneralGroups"))) {
						member = arr[i];
						JSONObject obj = getLdapAttributes(lc, member);
						group = obj.getString("cn");
						groups.add(group);

					}

				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Problem " + e.getMessage());
			}
			lc.disconnect();
		}

		catch (LDAPException e) {
			e.getMessage();
		} catch (Exception e1) {
			e1.getMessage();
		}
		return groups;

	}

	/**
	 * Retrieving List Of Groups Returns List Of Groups
	 */
	public List<String> getGroups() {
		String searchIn = "ou=GeneralGroups,dc="+dc1+",dc="+dc2;
		List<String> listOfGroups = new ArrayList<String>();
		List<String> listOfMembers = new ArrayList<String>();

		try {

			LDAPConnection lc = connectLdap();

			LDAPSearchResults searchResults = lc.search(searchIn,
					LDAPConnection.SCOPE_SUB, "(objectclass=*)", null, false);

			LDAPEntry nextEntry = null;

			while (searchResults.hasMore()) {
				nextEntry = searchResults.next();
				listOfMembers.add(nextEntry.getDN());
			}
			JSONObject obj;
			for (int i = 1; i < listOfMembers.size(); i++) {
				obj = getLdapAttributes(lc, listOfMembers.get(i));
				listOfGroups.add(obj.getString("cn"));
			}
			lc.disconnect();

		} catch (LDAPException e) {
			e.getLDAPErrorMessage();
		} catch (Exception e) {
			e.getMessage();
		}

		return listOfGroups;
	}

	/**
	 * Retrieving User Image
	 * 
	 * @param userName : User Login Name
	 * 
	 * Returns Image in Bytes
	 */
	public byte[] getImage(String userName) throws LDAPException, Exception {
		System.out.println("=========================");
		byte[] imageInBytes = null;
		String containerName = "cn=" + userName
				+ ",ou=Users,dc="+dc1+",dc="+dc2;
		String returnAttrs[] = { "photo" };
		System.out.println(containerName);
		LDAPConnection lc = connectLdap();
		LDAPEntry entry = lc.read(containerName, returnAttrs);
		LDAPAttribute atributes = entry.getAttribute("photo");
		imageInBytes = atributes.getByteValue();
		lc.disconnect();

		return imageInBytes;
	}

	public byte[] getImageForFemale(String userName) throws LDAPException,
			Exception {
		byte[] imageInBytes = null;
		String containerName = "cn=" + userName
				+ ",ou=Users,dc="+dc1+",dc="+dc2;
		String returnAttrs[] = { "jpegPhoto" };

		LDAPConnection lc = connectLdap();
		LDAPEntry entry = lc.read(containerName, returnAttrs);
		LDAPAttribute atributes = entry.getAttribute("jpegPhoto");
		imageInBytes = atributes.getByteValue();
		lc.disconnect();

		return imageInBytes;
	}

	/**
	 * Adding User Image
	 * 
	 * @param userName : User Login Name
	 * @param imsgeBytes : Image in bytes
	 */
	public void setImage(String username, byte[] imsgeBytes) {
		String containerName = "cn=" + username
				+ ",ou=Users,dc="+dc1+",dc="+dc2;
		LDAPConnection lc;
		try {
			lc = connectLdap();
			logger.info("image bytes " + imsgeBytes);
			LDAPModification[] modifications = new LDAPModification[1];
			LDAPAttribute imageToLDAP = new LDAPAttribute("photo", imsgeBytes);
			modifications[0] = new LDAPModification(LDAPModification.REPLACE,
					imageToLDAP);

			lc.modify(containerName, modifications);
			logger.info("User image Modified");
			lc.disconnect();

		} catch (LDAPException e) {
			e.getLDAPErrorMessage();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public String getUserLoginName(String userID) {
		String userDN = " cn=" + userID + ",ou=Users,dc="+dc1+",dc="+dc2;
		String userName = "";
		try {
			LDAPConnection lc = connectLdap();
			JSONObject obj = getLdapAttributes(lc, userDN);
			userName = obj.getString("cn");
			lc.disconnect();
		} catch (JSONException e) {
			logger.info(e.getMessage());
		} catch (LDAPException e) {
			logger.info(e.getLDAPErrorMessage());
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		return userName;
	}

	/**
	 * Retrieving the directory structure from the LDAP in a TreeView
	 * 
	 * Returns List of LDAP Hierarchy in a tree format
	 */
	@Cacheable(value = "getData", key = "#word1")
	public List<TreeViewItem> getData() {

		List<String> listOfMembers = new ArrayList<String>();
		List<TreeViewItem> data = new ArrayList<TreeViewItem>();
		TreeViewItem staticroot = new TreeViewItem();
		TreeViewItem root = new TreeViewItem();

		try {

			LDAPConnection lc = connectLdap();

			LDAPSearchResults searchResults = lc.search(
					"ou=Products,dc="+dc1+",dc="+dc2, LDAPConnection.SCOPE_ONE,
					"(objectclass=*)", null, false);
			while (searchResults.hasMore()) {
				LDAPEntry nextEntry = null;
				try {
					nextEntry = searchResults.next();
				} catch (LDAPException e) {
					if (e.getResultCode() == LDAPException.LDAP_TIMEOUT
							|| e.getResultCode() == LDAPException.CONNECT_ERROR) {
						break;
					} else {
						continue;
					}
				}
				listOfMembers.add(nextEntry.getDN());
			}
			Iterator<String> it = listOfMembers.iterator();

			int i = 0;
			while (it.hasNext()) {

				String list = listOfMembers.get(i);
				String[] parts = list.split(",");

				staticroot.setFields(dc1, false);
				root.setFields("Product = BFM", true);

				if (parts.length == 4) {
					String parts2[] = parts[0].split("=");
					TreeViewItem level1 = root.AddSubItem();
					level1.setFields("Module = " + parts2[1], true);
					List<String> listOfchild = new ArrayList<String>();
					LDAPSearchResults searchChild = lc.search(parts[0]
							+ ",ou=Products,dc="+dc1+",dc="+dc2,
							LDAPConnection.SCOPE_ONE, "(objectclass=*)", null,
							false);

					while (searchChild.hasMore()) {
						LDAPEntry nextEntryChild = null;
						try {
							nextEntryChild = searchChild.next();
						} catch (LDAPException e) {
							if (e.getResultCode() == LDAPException.LDAP_TIMEOUT
									|| e.getResultCode() == LDAPException.CONNECT_ERROR) {
								break;
							} else {
								continue;
							}
						}
						listOfchild.add(nextEntryChild.getDN());
					}

					Iterator<String> itChilds = listOfchild.iterator();
					int j = 0;
					while (itChilds.hasNext()) {
						itChilds.next();
						String loc = listOfchild.get(j);
						String[] child1 = loc.split(",");
						if (child1.length == 5) {
							TreeViewItem level2 = level1.AddSubItem();
							String child11[] = child1[0].split("=");
							level2.setFields("Forms = " + child11[1], true);

							List<String> listOfchildofchild = new ArrayList<String>();
							LDAPSearchResults searchChildofchild = lc
									.search(child1[0] + "," + child1[1]
											+ ",ou=Products,dc="+dc1+",dc="+dc2,
											LDAPConnection.SCOPE_ONE,
											"(objectclass=*)", null, false);

							while (searchChildofchild.hasMore()) {
								LDAPEntry nextEntryChildofChild = null;
								try {
									nextEntryChildofChild = searchChildofchild
											.next();
								} catch (LDAPException e) {
									if (e.getResultCode() == LDAPException.LDAP_TIMEOUT
											|| e.getResultCode() == LDAPException.CONNECT_ERROR) {
										break;
									} else {
										continue;
									}
								}
								listOfchildofchild.add(nextEntryChildofChild
										.getDN());
							}
							Iterator<String> itChildofchild = listOfchildofchild
									.iterator();

							int k = 0;
							while (itChildofchild.hasNext()) {
								itChildofchild.next();
								String loc1 = listOfchildofchild.get(k);
								String[] child2 = loc1.split(",");
								if (child2.length == 6) {
									TreeViewItem level3 = level2.AddSubItem();
									String child12[] = child2[0].split("=");
									level3.setFields("Tasks = " + child12[1],
											true);

									String userDN = "cn=" + child12[1] + ",ou="
											+ child11[1] + ",ou=" + parts2[1]
											+ ",ou=Products,dc="+dc1+",dc="+dc2;

									List<String> listofroles = uniqueMembers(userDN);
									
									if (listofroles.size() > 0) {
										String roles = listofroles.toString();
										String finalroles = roles.substring(1,
												roles.length() - 1);

										level3.AddSubItem().setFields(
												"Roles = " + finalroles, true);
									}
								}
								k++;
							}
						}
						j++;
					}
				}
				i++;
			}
			lc.disconnect();

		} catch (LDAPException e) {
			e.getLDAPErrorMessage();
		} catch (Exception e) {
			e.getMessage();
		}
		data.add(staticroot);
		data.add(root);
		return data;
	}

	/**
	 * Retrieving All the roles
	 * 
	 * Returns List of Roles
	 */
	@Cacheable(value = "getLdapRoles", key = "#word2")
	public List<Ldap> getLdapRoles() {
		List<Ldap> data = new ArrayList<Ldap>();

		List<String> listOfMembers = new ArrayList<String>();

		try {

			LDAPConnection lc = connectLdap();

			LDAPSearchResults searchResults = lc.search(
					"ou=Roles,dc="+dc1+",dc="+dc2, LDAPConnection.SCOPE_ONE,
					"(objectclass=*)", null, false);
			while (searchResults.hasMore()) {
				LDAPEntry nextEntry = null;
				try {
					nextEntry = searchResults.next();
				} catch (LDAPException e) {
					if (e.getResultCode() == LDAPException.LDAP_TIMEOUT
							|| e.getResultCode() == LDAPException.CONNECT_ERROR) {
						break;
					} else {
						continue;
					}
				}
				listOfMembers.add(nextEntry.getDN());
			}

			Iterator<String> it = listOfMembers.iterator();
			int i = 0;
			while (it.hasNext()) {

				String list = listOfMembers.get(i);
				String[] parts = list.split(",");
				if (parts.length == 4) {
					String parts2[] = parts[0].split("=");

					data.add(new Ldap(i, parts2[1]));
				}
				i++;
			}
			lc.disconnect();

		} catch (Exception e) {

		}

		return data;

	}
	
	


	 /**
	  * Method for passing username and password
	  * 
	  * FOR MOBILE APP
	  */
	 public String loginCheck(String userid, String upassword) {
	    System.out.println("inside login check >>>>>>>>>>>> userid " + userid +" upassword " +  upassword);
	    
	    String  ldapHost = ResourceBundle.getBundle("application").getString("ldapHostAjax");
		 
	    int ldapPort = Integer.parseInt(ResourceBundle.getBundle("application").getString("ldapPortAjax"));
	    
	    String  dc1 = ResourceBundle.getBundle("application").getString("ldap.Ajax.domain1");
	    String  dc2 = ResourceBundle.getBundle("application").getString("ldap.Ajax.domain2");
	    
	   

	    String status = "";   
	    Map<String, String> credential = new HashMap<String, String>();

	    try {
	    
	     LDAPConnection lc = new LDAPConnection();
	     System.out.println("connecting...........");
	     lc.connect(ldapHost, ldapPort);
	    
	     String userDN = "cn=" + userid + ",ou=Users,dc="+dc1+",dc="+dc2; 
	     
	   
	     JSONObject obj = getLdapAttributes(lc, userDN);
	     String nam=obj.getString("cn");
	   
	     
	    lc.bind(ldapVersion, userDN, upassword.getBytes("UTF8"));

	    
	     status = loginCheck(lc, userDN, "{BASE64}" + Base64.encode(upassword.getBytes())); 
	  
	     
	     
	    } catch (LDAPException e) {
	     System.out.println(e.getLDAPErrorMessage());
	     credential.put("status", e.getMessage());
	    } catch (Exception e) {
	     System.out.println(e.getMessage());
	     credential.put("status", e.getMessage());
	    }
	    
	    
	    return status;
	   }
	 public String loginCheck(LDAPConnection lc, String userDN, String userPWD) {
		 System.out.println("loginCheck>>>>>>>>>>>>>>>>>>");
		  String status = "";
		  LDAPAttribute attribute;

		  try {
		   // check user's password
		   attribute = new LDAPAttribute("userPassword", userPWD);
		  
		   if (lc.compare(userDN, attribute)) {
		    status += "success";
		    
		   } else {
		    status += "Password is incorrect";
		   }
		  } 
		  catch (LDAPException e) {
		   System.out.println("first catch");      
		   return "User Not Registered";
		  }
		  catch (Exception e) {
		   System.out.println("second catch");   
		   status += e.getMessage();
		  }System.out.println("status : "+ status);
		     
		  return status;
		 }

	public String loginCheckForCustomers(String userid, String upassword) {
		 System.out.println("inside login check2 for customers >>>>>>>>>>>> userid " + userid +" upassword " +  upassword);
		 
		 String  ldapHost = ResourceBundle.getBundle("application").getString("ldapHostAjax");
		 int ldapPort = Integer.parseInt(ResourceBundle.getBundle("application").getString("ldapPortAjax"));
		 
		 String  dc1 = ResourceBundle.getBundle("application").getString("ldap.Ajax.domain1");
		 String  dc2 = ResourceBundle.getBundle("application").getString("ldap.Ajax.domain2");

		    String status = "";   
		    Map<String, String> credential = new HashMap<String, String>();

		    try {
		    
		     LDAPConnection lc = new LDAPConnection();
		     System.out.println("connecting...........");
		     lc.connect(ldapHost, ldapPort);
		    
		     String userDN = "cn=" + userid + ",ou=Customers,dc="+dc1+",dc="+dc2; 
		   
		     JSONObject obj = getLdapAttributes(lc, userDN);
		     String nam=obj.getString("cn");
		   
		     
		    lc.bind(ldapVersion, userDN, upassword.getBytes("UTF8"));
	
		    
		     status = loginCheck(lc, userDN, "{BASE64}" + Base64.encode(upassword.getBytes())); 
		  
		     
		     
		    } catch (LDAPException e) {
		     System.out.println(e.getLDAPErrorMessage());
		     credential.put("status", e.getMessage());
		    } catch (Exception e) {
		     System.out.println(e.getMessage());
		     credential.put("status", e.getMessage());
		    }
		    
		    
		    return status;
	}

}
