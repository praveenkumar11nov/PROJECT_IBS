package com.bcits.bfm.ldap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcits.bfm.serviceImpl.facilityManagement.ParkingSlotsAllotmentServiceImpl;

@Component
public class SecurityVerification {

	@Autowired
	private LdapBusinessModel ldapBusinessModel;
	private static final Log logger = LogFactory
			.getLog(ParkingSlotsAllotmentServiceImpl.class);

	public String checkVerification(
			Map<String, List<String>> memberWithDesciption, String methodName,
			String role) {

		String status = "";
		try {
			String otherRequest;
			logger.info("Requested URL " + methodName);
			logger.info("Entry Exists in LDAP "
					+ memberWithDesciption.containsKey(methodName));
			if (memberWithDesciption.containsKey(methodName)) {
				Set<String> descriptionValues = memberWithDesciption.keySet();
				Iterator<String> it = descriptionValues.iterator();
				while (it.hasNext()) {
					String description = it.next();
					if (description.equals(methodName)) {
						List<String> members = memberWithDesciption
								.get(description);
						List<String> checkmethod = getString(members);
						Iterator<String> it1 = checkmethod.iterator();
						boolean result = false;
						while (it1.hasNext()) {
							String str = it1.next().toString();
							logger.info("Required Role " + str);
							logger.info("User Role " + role);
							String[] roles = role.split(",");
							for (int i = 0; i < roles.length; i++) {
								if (str.equals(roles[i])) {
									result = true;
									break;
								} else {
									result = false;
								}
							}
							if (result == true) {
								status = "true";
								break;
							} else {
								status = "false";
							}
						}
					}
				}
				logger.info("Verification Status " + status);
				return status;
			} else {
				otherRequest = "true";
				return otherRequest;
			}
		} catch (Exception e) {
			logger.info("Exception In LDAP Connection "
					+ e.getLocalizedMessage());
		}
		return status;
	}

	private List<String> getString(List<String> members) {
		Iterator<String> it = members.iterator();
		List<String> str = new ArrayList<String>();
		while (it.hasNext()) {
			str.add(it.next());
		}
		return str;
	}
}
