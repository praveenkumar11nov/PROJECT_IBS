package com.bcits.bfm.securitycontroller;

import java.util.Collection;
import java.util.List;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.util.MyBootstrapCacheLoaderFactory;

public class CustomAccessDecisionManager extends AbstractAccessDecisionManager  {

	@Autowired
	private MyBootstrapCacheLoaderFactory myBootstrapCacheLoaderFactory;
	
	@Autowired
	private LdapBusinessModel ldapBusinessModel;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void decide(Authentication authentication, Object filter,Collection<ConfigAttribute> configAttributes){

		if ((filter == null) || !this.supports(filter.getClass())) {
			throw new IllegalArgumentException("Object must be a FilterInvocation");
		}

	    String url = ((FilterInvocation) filter).getRequestUrl();
	    @SuppressWarnings("unused")
		String contexto = ((FilterInvocation) filter).getRequest().getContextPath();	    
	    Collection<ConfigAttribute> roles = getAttributes(url);//service.getConfigAttributesFromSecuredUris(contexto, url);
	    
	    int deny = 0;

	    for (AccessDecisionVoter voter : getDecisionVoters()) {
	    	int result = voter.vote(authentication, filter, roles);

	    	if (logger.isDebugEnabled()) {
	    		logger.debug("Voter: " + voter + ", returned: " + result);
	    	}

	    	switch (result) {
	    		case AccessDecisionVoter.ACCESS_GRANTED:
	    			return;

	    		case AccessDecisionVoter.ACCESS_DENIED:
	    			deny++;
	    			break;
		        default:
		            break;
	    	}
	    }

	    if (deny > 0) {
	        throw new AccessDeniedException(messages.getMessage("AbstractAccessDecisionManager.accessDenied","Access is denied"));
	    }

	    // To get this far, every AccessDecisionVoter abstained
	    checkAllowIfAllAbstainDecisions();
}

public Collection<ConfigAttribute> getAttributes(String url){	
	List<JSONObject> memberWithDesciption=myBootstrapCacheLoaderFactory.getMemberWithDesciption();
	String roles[] =new String[memberWithDesciption.size()];	
	try{
			
		if(url.equals("/home")){				
			List<String> allRoles=ldapBusinessModel.getRoles();		
			String[] roles1 = new String[allRoles.size()];	
			for(int i=0;i<allRoles.size();i++){
				roles1[i]="ROLE_"+allRoles.get(i).toUpperCase();				
			}
			return SecurityConfig.createList(roles1);
			
		}else if(url.equals("/getMenu")){
			List<String> allRoles=ldapBusinessModel.getRoles();			
			String[] roles1 = new String[allRoles.size()];	
			for(int i=0;i<allRoles.size();i++){
				roles1[i]="ROLE_"+allRoles.get(i).toUpperCase();			
			}
			return SecurityConfig.createList(roles1);
		}
		else{					
			
			for(JSONObject entry:memberWithDesciption){								
				if(entry.get("url").toString().contains(url)){				
					String ldapRoles=entry.get("role").toString();					
					ldapRoles=ldapRoles.replace("[", "");
					ldapRoles=ldapRoles.replace("]", "");
					ldapRoles=ldapRoles.replace("{", "");
					ldapRoles=ldapRoles.replace("}", "");
					roles=ldapRoles.split(",");
					int i=0;
					for(String str:roles){
							roles[i]="ROLE_"+str.toUpperCase();
							roles[i]=roles[i].replace("\"", "");						
							i++;
							
						}					
					break;
				}
				
			}		
		}	
		
		return SecurityConfig.createList(roles);	  
	}catch(Exception e){
	
	}
	return null;
}

}