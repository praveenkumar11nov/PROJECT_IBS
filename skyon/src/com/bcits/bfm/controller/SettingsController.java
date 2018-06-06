package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.ldap.ModuleDetails;
import com.bcits.bfm.model.Ldap;
import com.bcits.bfm.model.TreeViewItem;
import com.bcits.bfm.util.MyBootstrapCacheLoaderFactory;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPModification;
import com.novell.ldap.LDAPSearchResults;
import com.novell.ldap.util.Base64;

@Controller
public class SettingsController {

	static String str;

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
	
	


	
	
	
	static int ldapVersion = LDAPConnection.LDAP_V3;

	@Autowired
	private BreadCrumbTreeService breadCrumbService;

	@Autowired
	private MyBootstrapCacheLoaderFactory myBootstrapCacheLoaderFactory;
	
	@Autowired
	private LdapBusinessModel ldapBusinessModel;
	

	private static Logger logger=Logger.getLogger(SettingsController.class);
	 /** 
    * To Retrieve Ldap Connection 
    * static variable ldapHost 
    * static variable ldapPort 
    * static variable loginDN 
    * static variable password 
    */	
	private LDAPConnection connectLdap() throws LDAPException, Exception {
		LDAPConnection lc = new LDAPConnection();
		lc.connect(ldapHost, ldapPort);
		lc.bind(ldapVersion, loginDN, password.getBytes("UTF8"));
		return lc;
	}

	
	
	/**
	 * All the LDAP instances will be send to the view
	 * 
	 * @param model
	 *            This will set message inside view
	 * @param request
	 *            Input from the view
	 * @return Control goes to respective view
	 * @since 0.1
	 */
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {

		model.addAttribute("ViewName", "System Security");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("System Security", 1, request);
		breadCrumbService.addNode("Product Access Management", 2, request);		
		model.addAttribute("items",getRoles());	
		return "setting";
	}

	@RequestMapping(value = "/ldaptree/flush", method = RequestMethod.POST)
	public @ResponseBody String flushChages(HttpServletResponse response) throws IOException, JSONException, LDAPException {
		PrintWriter out;
		myBootstrapCacheLoaderFactory.setMemberWithDesciption(ldapBusinessModel.getMemberAttributes());
		myBootstrapCacheLoaderFactory.setMemberWithDesciptionMenu(ldapBusinessModel.getMemberAttributesForMenu());
	    out = response.getWriter();
		out.write("Changes Saved Successfully");
		return null;
	}
	
	private Object getCN(String record) throws LDAPException, Exception {
		  LDAPConnection lc = connectLdap();
		  JSONObject obj = getLdapAttributes(lc, record);
		  String text = obj.getString("cn");     
		  lc.disconnect();
		  return text;
	}
	
	@RequestMapping(value = "/setting/ajaxtree", method = RequestMethod.POST)
	public @ResponseBody
	List<TreeViewItem> readAsset(HttpServletRequest request) throws Exception {
	
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

				staticroot.setFields("IREO_BFM", false);
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
									String usrlDn = "cn="+child12[1]+","+child1[0] +","+child1[1]+",ou=Products,dc="+dc1+",dc="+dc2; 									
									List<String> seventhLevelList = new ArrayList<String>();
									LDAPSearchResults searchSixthLevel = lc.search(usrlDn,LDAPConnection.SCOPE_ONE,"(objectclass=*)", null, false);
									
									if(searchSixthLevel.hasMore())
										level3.setFields("Forms = " + child12[1],true);
									else
										level3.setFields("Tasks = " + child12[1],true);
									
									
									
									while(searchSixthLevel.hasMore()){	
										LDAPEntry getSeventhLevel = null;
										try {
											getSeventhLevel = searchSixthLevel.next();
										} catch (LDAPException e) {
											if (e.getResultCode() == LDAPException.LDAP_TIMEOUT
													|| e.getResultCode() == LDAPException.CONNECT_ERROR) {
												break;
											} else {
												continue;
											}
										}
										seventhLevelList.add(getSeventhLevel.getDN());
									}									
									Iterator<String> iterarteSeventhLevel = seventhLevelList.iterator();
									
									int l=0;
									while (iterarteSeventhLevel.hasNext()) {
										iterarteSeventhLevel.next();
										String seven = seventhLevelList.get(l);									
										TreeViewItem level4 = level3.AddSubItem();					
										String usrlDnEight = "cn="+(String)getCN(seven)+","+usrlDn;										
										List<String> eighthLevelList = new ArrayList<String>();
										LDAPSearchResults searchEighthLevel = lc.search(usrlDnEight,LDAPConnection.SCOPE_ONE,"(objectclass=*)", null, false);
										
										if(searchEighthLevel.hasMore())
											level4.setFields("Forms = "+(String)getCN(seven), true);
										else
											level4.setFields("Tasks = "+(String)getCN(seven), true);
										
										while(searchEighthLevel.hasMore()){
		
											LDAPEntry getEighthLevel = null;
											try {
												getEighthLevel = searchEighthLevel.next();
											} catch (LDAPException e) {
												if (e.getResultCode() == LDAPException.LDAP_TIMEOUT
														|| e.getResultCode() == LDAPException.CONNECT_ERROR) {
													break;
												} else {
													continue;
												}
											}
											eighthLevelList.add(getEighthLevel.getDN());
										}
										
										Iterator<String> iterarteEightthLevel = eighthLevelList.iterator();
										
										int m=0;
										while (iterarteEightthLevel.hasNext()) {
											iterarteEightthLevel.next();
											String eight = eighthLevelList.get(m);
											
											level4.AddSubItem().setFields("Tasks = "+(String)getCN(eight), true);
											m++;
										}
										l++;
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
     * Editing roles assign to Tasks
     * @param directoryStructure		:Directory Hierarchy in the format of Module,Form,Task
     * @param mselect		:roles selected in multiselect, format: 0,1,2 (roles id)
     * Returns null
     */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/ldaptree/update", method = RequestMethod.POST)
	public String updateNode(@RequestParam("values") String directoryStructure,
			@RequestParam("mselect") String mselect,
			HttpServletResponse response) {

		StringBuffer sb = new StringBuffer();		
		String remBrace = mselect.substring(1, mselect.length() - 1);
		List<Ldap> tempList = ldapBusinessModel.getLdapRoles();
		String[] temp = remBrace.split(",");
		/* Matching roles id to roles name */
		for (int i = 0; i < temp.length; i++) {
			for (Iterator iterator = tempList.iterator(); iterator.hasNext();) {
				Ldap ldap = (Ldap) iterator.next();
				String checkText = ldap.getText();
				String arrayText = temp[i];
				int id = ldap.getId();
				String ldapid = Integer.toString(id);

				if (arrayText.trim().equalsIgnoreCase(ldapid.trim())) {
					sb.append(checkText + ",");
				}
			}
		}
		// var rolesName: Text of Roles, format: User,Admin,Approver....
		String rolesName = sb.toString();
		String splitval[] = directoryStructure.split(",");
		String userDN = "";
		
		for(int i = splitval.length-1 ;i> 0;i--){	
			String mod[] = splitval[i].split("=");
			userDN+="cn="+mod[1]+",";
		}
		userDN+="ou=Products,dc="+dc1+",dc="+dc2;		
		PrintWriter out;
		try {
			List<String> um = uniqueMembers(userDN);
			for(int i=0;i<um.size();i++)
			{
				removeMemberFromTask(userDN,um.get(i));				
			}
			String uniquemem[] = rolesName.split(",");

			for(int i=0;i<uniquemem.length;i++)
			{
				addMemberToTask(userDN,uniquemem[i]);				
			}
			

			try {
				out = response.getWriter();
				out.write(rolesName);
				// out.write(responsechar);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	/** 
     * Retrieving roles assign to Tasks
     * @param directoryStructure		:Directory Hierarchy in the format of Module,Form,Task
     *  
     * Returns List of Roles
     */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/ldaptree/taskroles", method = RequestMethod.POST)
	public @ResponseBody
	List<Ldap> taskRoles(@RequestParam("values") String directoryStructure,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {

		List<Ldap> data = new ArrayList<Ldap>();
		String[] splitval = directoryStructure.split(",");		
		String userDN = "";	
		for(int i = splitval.length-1 ;i> 0;i--){	
			String mod[] = splitval[i].split("=");
			
			logger.info("\n\n=====>"+mod.length);
			userDN+="cn="+mod[1]+",";
		}
		userDN+="ou=Products,dc="+dc1+",dc="+dc2;

		List<String> list = uniqueMembers(userDN);
			String strlist = list.toString();
			String listval = strlist.substring(1, strlist.length() - 1);

			List<Ldap> tempList = ldapBusinessModel.getLdapRoles();
			Ldap obj = null;
			String[] temp = listval.split(",");

			for (int i = 0; i < temp.length; i++) {
				obj = new Ldap();
				for (Iterator iterator = tempList.iterator(); iterator
						.hasNext();) {
					Ldap ldap = (Ldap) iterator.next();
					String checkText = ldap.getText();
					String arrayText = temp[i];
					int id = ldap.getId();

					if (arrayText.trim().equalsIgnoreCase(checkText.trim())) {
						obj.setId(id);
						obj.setText(arrayText);
					}
				}
				data.add(obj);
			}
		
		return data;
	}

	
	/** 
     * Retrieving All the roles
     *  
     * Returns List of Roles
     */
	public List<Ldap> getRoles() {
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
     * 	Returns List of Users for Particular Role
     *	@param String userDN   :Directory name
     *  
     * 	Returns List of Users 
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

					if (arr[i].contains("ou=Roles")) {
						obj = getLdapAttributes(lc, arr[i]);
						al.add(obj.getString("cn"));
					}
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
     * Internal Supporting Method for getRoles,getLoginRoles,uniqueMembers,memberDescription,getGroups,getPassword
     * @param lc      	:Ldap Connection Object
     * @param userDN	:User Directory Name
     * 
     * Returns LDAP Attributes for Specified DN
     */
	private JSONObject getLdapAttributes(LDAPConnection lc, String userDN)
			throws JSONException, LDAPException, Exception {

		JSONObject obj = new JSONObject();
		LDAPEntry nextEntry = null;

		LDAPSearchResults searchResults = lc.search(userDN,
				LDAPConnection.SCOPE_BASE, "(objectclass=*)", null, false);
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
     * Internal Supporting Method for deleting the roles assign to the particular task
     * @param compUrl      	:User Directory Name
     * @param role	:   role to delete
     * 
     */
	public void removeMemberFromTask(String compUrl, String role) {

		String memtoDelete = " cn="+role+",ou=Roles,dc="+dc1+",dc="+dc2;

		// Add modifications to modRoles
		LDAPAttribute uniqueMember = new LDAPAttribute("uniqueMember", memtoDelete);
		LDAPModification modRole = new LDAPModification(
				LDAPModification.DELETE, uniqueMember);

		// Modify the Role's attributes
		try {
			LDAPConnection lc = connectLdap();
			lc.modify(compUrl, modRole);
			lc.disconnect();

		} catch (Exception e) {
		}
		logger.info("user Removed from roles");
	}


	/** 
     * Internal Supporting Method for Adding the roles assign to the particular task
     * @param compUrl      	:User Directory Name
     * @param role	:   role to Add
     * 
     */
	public void addMemberToTask(String compUrl, String role) {

		String memtoAdd = " cn="+role+",ou=Roles,dc="+dc1+",dc="+dc2;

		// Add modifications to modRoles
		LDAPAttribute uniqueMember = new LDAPAttribute("uniqueMember", memtoAdd);
		LDAPModification modRole = new LDAPModification(LDAPModification.ADD,
				uniqueMember);

		try {
			// Modify the Role's attributes
			LDAPConnection lc = connectLdap();
			lc.modify(compUrl, modRole);
			lc.disconnect();
		} catch (Exception e) {
		}
		logger.info("member added to roles");

	}
}
