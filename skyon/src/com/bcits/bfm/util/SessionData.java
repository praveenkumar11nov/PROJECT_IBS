package com.bcits.bfm.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bcits.bfm.model.Person;

public class SessionData {

	public static Map<String, Object> getUserDetails() {
		Map<String, Object> userSessionDetails = new HashMap<String, Object>();
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String userID = auth.getName();
		userSessionDetails.put("userID", userID);
		return userSessionDetails;
	}
	
	

	
	
	

}
